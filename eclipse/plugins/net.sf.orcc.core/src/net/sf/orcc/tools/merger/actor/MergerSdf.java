package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

public class MergerSdf extends DfSwitch<Actor> {

	public class ChangeFifoArrayAccess extends AbstractIrVisitor<Object> {

		@Override
		public Object caseInstLoad(InstLoad load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			Port port = action.getInputPattern().getVarToPortMap().get(var);
			if (port != null) {
				if (buffersMap.containsKey(port)) {
					var = buffersMap.get(port);
				} else {
					var = inputPattern.getPortToVarMap()
							.get(portsMap.get(port));
				}
				int cns = action.getInputPattern().getNumTokens(port);
				loads.put(var, cns);
				use.setVariable(var);
				List<Expression> indexes = load.getIndexes();
				Expression e1 = irFactory.createExprVar(irFactory
						.createUse(body.getLocal(var.getName() + "_r")));
				Expression e2 = IrUtil.copy(indexes.get(0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				indexes.set(0, bop);
			}
			return null;
		}

		@Override
		public Object caseInstStore(InstStore store) {
			Def def = store.getTarget();
			Var var = def.getVariable();
			Port port = action.getOutputPattern().getVarToPortMap().get(var);
			if (port != null) {
				if (buffersMap.containsKey(port)) {
					var = buffersMap.get(port);
				} else {
					var = outputPattern.getPortToVarMap().get(
							portsMap.get(port));
				}
				int prd = action.getOutputPattern().getNumTokens(port);
				stores.put(var, prd);
				def.setVariable(var);
				Expression e1 = irFactory.createExprVar(irFactory
						.createUse(body.getLocal(var.getName() + "_w")));
				Expression e2 = IrUtil.copy(store.getIndexes().get(0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				store.getIndexes().set(0, bop);
			}

			port = action.getInputPattern().getVarToPortMap().get(var);
			if (port != null) {
				if (buffersMap.containsKey(port)) {
					var = buffersMap.get(port);
				}
				int cns = action.getInputPattern().getNumTokens(port);
				stores.put(var, cns);
				def.setVariable(var);
				Expression e1 = irFactory.createExprVar(irFactory
						.createUse(body.getLocal(var.getName() + "_r")));
				Expression e2 = IrUtil.copy(store.getIndexes().get(0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				store.getIndexes().set(0, bop);
			}
			return null;
		}

		private Map<Var, Integer> loads;

		private Map<Var, Integer> stores;

		private Procedure body;

		private Action action;

		public ChangeFifoArrayAccess(Action action, Procedure body) {
			this.action = action;
			this.body = body;
		}

		@Override
		public Object caseProcedure(Procedure procedure) {
			this.procedure = procedure;
			loads = new HashMap<Var, Integer>();
			stores = new HashMap<Var, Integer>();
			super.caseProcedure(procedure);

			updateLoadIndex();
			updateStoreIndex();
			return null;
		}

		private void updateLoadIndex() {
			for (Map.Entry<Var, Integer> entry : loads.entrySet()) {
				Var var = entry.getKey();
				int cns = entry.getValue();

				Var readVar = body.getLocal(var.getName() + "_r");
				IrFactory factory = IrFactory.eINSTANCE;
				ExprBinary incr = factory.createExprBinary(
						factory.createExprVar(factory.createUse(readVar)),
						OpBinary.PLUS, factory.createExprInt(cns),
						readVar.getType());

				InstStore store = factory.createInstStore(readVar, incr);
				BlockBasic block = procedure.getLast();
				int index = block.getInstructions().size() - 1;
				block.add(index, store);
			}
		}

		private void updateStoreIndex() {
			for (Map.Entry<Var, Integer> entry : stores.entrySet()) {
				Var var = entry.getKey();
				int prd = entry.getValue();

				Var readVar = body.getLocal(var.getName() + "_w");
				IrFactory factory = IrFactory.eINSTANCE;
				ExprBinary incr = factory.createExprBinary(
						factory.createExprVar(factory.createUse(readVar)),
						OpBinary.PLUS, factory.createExprInt(prd),
						readVar.getType());

				InstStore store = factory.createInstStore(readVar, incr);
				BlockBasic block = procedure.getLast();
				int index = block.getInstructions().size() - 1;
				block.add(index, store);
			}
		}

	}

	private static final String ACTION_NAME = "static_schedule";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private final DfFactory dfFactory = DfFactory.eINSTANCE;

	private final IrFactory irFactory = IrFactory.eINSTANCE;

	private Actor superActor;

	private AbstractScheduler scheduler;

	private Network network;

	private Copier copier;

	private Map<Port, Port> portsMap = new HashMap<Port, Port>();

	private int depth;

	private Map<Port, Var> buffersMap = new HashMap<Port, Var>();

	private Pattern inputPattern;

	private Pattern outputPattern;

	public MergerSdf(AbstractScheduler scheduler, Copier copier) {
		this.scheduler = scheduler;
		this.copier = copier;
	}

	/**
	 * Creates input and output ports of the merged actor.
	 * 
	 */
	private void createPorts() {
		SDFMoC sdfMoC = MocFactory.eINSTANCE.createSDFMoC();
		superActor.setMoC(sdfMoC);
		superActor.getInputs().addAll(copier.copyAll(network.getInputs()));
		superActor.getOutputs().addAll(copier.copyAll(network.getOutputs()));

		for (Port port : network.getInputs()) {
			Connection connection = (Connection) port.getOutgoing().get(0);
			Actor tgt = connection.getTarget().getAdapter(Actor.class);
			CSDFMoC moc = (CSDFMoC) tgt.getMoC();
			int cns = scheduler.getRepetitions().get(tgt)
					* moc.getNumTokensConsumed(connection.getTargetPort());
			Port portCopy = (Port) copier.get(port);
			sdfMoC.getInputPattern().setNumTokens(portCopy, cns);
			portsMap.put(connection.getTargetPort(), portCopy);
		}

		for (Port port : network.getOutputs()) {
			Connection connection = (Connection) port.getIncoming().get(0);
			Actor src = connection.getSource().getAdapter(Actor.class);
			CSDFMoC moc = (CSDFMoC) src.getMoC();
			int prd = scheduler.getRepetitions().get(src)
					* moc.getNumTokensProduced(connection.getSourcePort());
			Port portCopy = (Port) copier.get(port);
			sdfMoC.getOutputPattern().setNumTokens(portCopy, prd);
			portsMap.put(connection.getSourcePort(), portCopy);
		}
	}

	private void copyStateVariables() {
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			String id = actor.getName();
			for (Var var : new ArrayList<Var>(actor.getStateVars())) {
				String name = var.getName();
				actor.getStateVar(name).setName(id + "_" + name);
				superActor.getStateVars().add(var);
			}
		}
	}

	private void copyProcedures() {
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			String id = actor.getName();
			for (Procedure proc : new ArrayList<Procedure>(actor.getProcs())) {
				if (!proc.isNative()) {
					proc.setName(id + "_" + proc.getName());
					superActor.getProcs().add(proc);
				}
			}
		}
	}

	/**
	 * Create global list variables to replace the FIFOs.
	 * 
	 */
	private void createInnerBuffers(Procedure body) {
		buffersMap = new HashMap<Port, Var>();

		// Create counters for inputs
		for (Port port : superActor.getInputs()) {
			Var readIdx = irFactory.createVar(0, irFactory.createTypeInt(32),
					port.getName() + "_r", true, irFactory.createExprInt(0));
			body.getLocals().add(readIdx);
		}

		// Create counters for outputs
		for (Port port : superActor.getOutputs()) {
			Var writeIdx = irFactory.createVar(0, irFactory.createTypeInt(32),
					port.getName() + "_w", true, irFactory.createExprInt(0));
			body.getLocals().add(writeIdx);
		}

		int index = 0;
		// Create buffers and counters for inner connections
		for (Map.Entry<Connection, Integer> entry : scheduler.getMaxTokens()
				.entrySet()) {
			String name = "buffer_" + index++;
			Connection conn = entry.getKey();

			// create inner buffer
			int size = entry.getValue();
			Type eltType = conn.getSourcePort().getType();
			Type type = irFactory.createTypeList(size, eltType);
			Var buffer = irFactory.createVar(0, type, name, true);
			body.getLocals().add(buffer);

			// create write counter
			Var writeIdx = irFactory.createVar(0, irFactory.createTypeInt(32),
					name + "_w", true, irFactory.createExprInt(0));
			body.getLocals().add(writeIdx);

			// create write counter
			Var readIdx = irFactory.createVar(0, irFactory.createTypeInt(32),
					name + "_r", true, irFactory.createExprInt(0));
			body.getLocals().add(readIdx);

			buffersMap.put(conn.getSourcePort(), buffer);
			buffersMap.put(conn.getTargetPort(), buffer);
		}
	}

	/**
	 * Creates the input pattern of the static action.
	 * 
	 * @param inputPattern
	 * 
	 * @return ip
	 */
	private Pattern createInputPattern() {
		Pattern inputPattern = dfFactory.createPattern();
		SDFMoC moc = (SDFMoC) superActor.getMoC();
		for (Port port : superActor.getInputs()) {
			int numTokens = moc.getNumTokensConsumed(port);
			Type type = irFactory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = irFactory.createVar(0, type, port.getName(), true);
			inputPattern.setNumTokens(port, numTokens);
			inputPattern.setVariable(port, var);
		}
		return inputPattern;
	}

	/**
	 * Creates the output pattern of the static action.
	 * 
	 * @return op
	 */
	private Pattern createOutputPattern() {
		Pattern outputPattern = dfFactory.createPattern();
		SDFMoC moc = (SDFMoC) superActor.getMoC();
		for (Port port : superActor.getOutputs()) {
			int numTokens = moc.getNumTokensProduced(port);
			Type type = irFactory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = irFactory.createVar(0, type, port.getName(), true);
			outputPattern.setNumTokens(port, numTokens);
			outputPattern.setVariable(port, var);
		}
		return outputPattern;
	}

	/**
	 * Creates the scheduler of the static action.
	 * 
	 * @return the scheduler of the static action
	 */
	private Procedure createScheduler() {
		Procedure scheduler = irFactory.createProcedure(SCHEDULER_NAME, 0,
				irFactory.createTypeBool());
		scheduler.getLast().add(
				irFactory.createInstReturn(irFactory.createExprBool(true)));
		return scheduler;
	}

	/**
	 * Create the static schedule of the action
	 * 
	 * @param procedure
	 * @param schedule
	 * @param blocks
	 */
	private void createStaticSchedule(Procedure procedure, Schedule schedule,
			List<Block> blocks) {
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isActor()) {
				Actor actor = iterand.getActor();
				CSDFMoC moc = (CSDFMoC) actor.getMoC();
				for (Invocation invocation : moc.getInvocations()) {
					Action action = invocation.getAction();

					// Copy local variable
					for (Var var : new ArrayList<Var>(action.getBody().getLocals())) {
						procedure.addLocal(var);
					}
					
					new ChangeFifoArrayAccess(action, procedure)
							.doSwitch(action.getBody());
					blocks.addAll(action.getBody().getBlocks());
				}

			} else {
				Schedule sched = iterand.getSchedule();
				Var loopVar = procedure.getLocal("idx_" + depth);

				// init counter
				BlockBasic block = IrUtil.getLast(blocks);
				block.add(irFactory.createInstAssign(loopVar,
						irFactory.createExprInt(0)));

				// while loop
				Expression condition = irFactory.createExprBinary(
						irFactory.createExprVar(loopVar), OpBinary.LT,
						irFactory.createExprInt(sched.getIterationCount()),
						irFactory.createTypeBool());

				BlockWhile blockWhile = irFactory.createBlockWhile();
				blockWhile.setJoinBlock(irFactory.createBlockBasic());
				blockWhile.setCondition(condition);
				blocks.add(blockWhile);

				depth++;
				// recursion
				createStaticSchedule(procedure, sched, blockWhile.getBlocks());
				depth--;

				// Increment current while loop variable
				Expression expr = irFactory
						.createExprBinary(irFactory.createExprVar(loopVar),
								OpBinary.PLUS, irFactory.createExprInt(1),
								irFactory.createTypeInt(32));
				InstAssign assign = irFactory.createInstAssign(loopVar, expr);
				IrUtil.getLast(blockWhile.getBlocks()).add(assign);
			}
		}
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Procedure createBody() {
		Procedure body = irFactory.createProcedure(ACTION_NAME, 0,
				irFactory.createTypeVoid());
		createInnerBuffers(body);

		// Add loop counter(s)
		int i = 0;
		do { // one loop var is required even if the schedule as a depth of 0
			Var counter = irFactory.createVar(0, irFactory.createTypeInt(32),
					"idx_" + i, true);
			body.getLocals().add(counter);
			i++;
		} while (i < scheduler.getDepth());

		// Initialize read/write counters
		for (Var var : new HashSet<Var>(buffersMap.values())) {
			BlockBasic block = body.getLast();

			Var read = body.getLocal(var.getName() + "_r");
			if (read != null) {
				block.add(irFactory.createInstStore(read,
						irFactory.createExprInt(0)));
			}

			Var write = body.getLocal(var.getName() + "_w");
			if (write != null) {
				block.add(irFactory.createInstStore(write,
						irFactory.createExprInt(0)));
			}
		}

		createStaticSchedule(body, scheduler.getSchedule(), body.getBlocks());

		return body;
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 */
	private void createStaticAction() {
		inputPattern = createInputPattern();
		outputPattern = createOutputPattern();
		Pattern peekPattern = dfFactory.createPattern();

		Procedure scheduler = createScheduler();
		Procedure body = createBody();

		Action action = dfFactory.createAction(ACTION_NAME, inputPattern,
				outputPattern, peekPattern, scheduler, body);

		superActor.getActions().add(action);
		superActor.getActionsOutsideFsm().add(action);
	}

	@Override
	public Actor caseNetwork(Network network) {
		this.network = network;
		this.superActor = dfFactory.createActor();
		superActor.setName(network.getName());

		createPorts();
		copyStateVariables();
		copyProcedures();
		createStaticAction();

		return superActor;
	}
}
