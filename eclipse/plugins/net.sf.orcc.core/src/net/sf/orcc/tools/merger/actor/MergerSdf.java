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
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

public class MergerSdf extends DfSwitch<Actor> {

	private static int index;

	private static final String ACTION_NAME = "static_schedule";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private Actor superActor;

	private Procedure actionBody;

	private AbstractScheduler scheduler;

	private Network network;

	private Copier copier;

	private Map<Port, Port> portsMap = new HashMap<Port, Port>();

	private int depth;

	private Map<Port, Var> buffersMap = new HashMap<Port, Var>();

	public MergerSdf(AbstractScheduler scheduler, Copier copier) {
		this.scheduler = scheduler;
		this.copier = copier;
	}

	private void addBuffer(String name, int size, Type eltType) {
		IrFactory factory = IrFactory.eINSTANCE;
		Type type = factory.createTypeList(size, eltType);
		Var var = factory.createVar(0, type, name, true, true);
		actionBody.getLocals().add(var);
	}

	private void addCounter(String name) {
		IrFactory factory = IrFactory.eINSTANCE;
		Var varCount = factory.createVar(0, factory.createTypeInt(32), name,
				true, factory.createExprInt(0));
		actionBody.getLocals().add(varCount);
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
		copier.copyReferences();

		for (Port port : network.getInputs()) {
			Connection connection = (Connection) port.getOutgoing().get(0);
			Actor tgt = connection.getTarget().getAdapter(Actor.class);
			CSDFMoC moc = (CSDFMoC) tgt.getMoC();
			int cns = scheduler.getRepetitions().get(tgt)
					* moc.getNumTokensConsumed(connection.getTargetPort());
			Port portCopy = (Port) copier.get(port);
			sdfMoC.getInputPattern().setNumTokens(portCopy, cns);
			portsMap.put(portCopy, connection.getTargetPort());
		}

		for (Port port : network.getOutputs()) {
			Connection connection = (Connection) port.getIncoming().get(0);
			Actor src = connection.getSource().getAdapter(Actor.class);
			CSDFMoC moc = (CSDFMoC) src.getMoC();
			int prd = scheduler.getRepetitions().get(src)
					* moc.getNumTokensProduced(connection.getSourcePort());
			Port portCopy = (Port) copier.get(port);
			sdfMoC.getOutputPattern().setNumTokens(portCopy, prd);
			portsMap.put(portCopy, connection.getSourcePort());
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
				proc.setName(id + "_" + proc.getName());
				superActor.getProcs().add(proc);
			}
		}
	}

	/**
	 * Create global list variables to replace the FIFOs.
	 * 
	 */
	private void createInnerBuffers() {
		buffersMap = new HashMap<Port, Var>();

		int index = 0;
		// Create counters for inputs
		for (Port port : superActor.getInputs()) {
			addCounter(port.getName() + "_r");
		}

		// Create counters for outputs
		for (Port port : superActor.getOutputs()) {
			addCounter(port.getName() + "_w");
		}

		// Create buffers and counters for inner connections
		for (Map.Entry<Connection, Integer> entry : scheduler.getMaxTokens()
				.entrySet()) {
			String name = "buffer_" + index++;
			Connection conn = entry.getKey();
			addBuffer(name, entry.getValue(), conn.getSourcePort().getType());
			addCounter(name + "_w");
			addCounter(name + "_r");
			Var var = actionBody.getLocal(name);
			buffersMap.put(conn.getSourcePort(), var);
			buffersMap.put(conn.getTargetPort(), var);
		}
	}

	/**
	 * Creates the input pattern of the static action.
	 * 
	 * @return ip
	 */
	private Pattern createInputPattern() {
		Pattern ip = DfFactory.eINSTANCE.createPattern();
		SDFMoC moc = (SDFMoC) superActor.getMoC();
		for (Port port : superActor.getInputs()) {
			int numTokens = moc.getNumTokensConsumed(port);
			Type type = IrFactory.eINSTANCE.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = IrFactory.eINSTANCE.createVar(0, type, port.getName(),
					false, true);
			ip.setNumTokens(port, numTokens);
			ip.setVariable(port, var);
		}
		return ip;
	}

	/**
	 * Creates the output pattern of the static action.
	 * 
	 * @return op
	 */
	private Pattern createOutputPattern() {
		IrFactory factory = IrFactory.eINSTANCE;
		Pattern op = DfFactory.eINSTANCE.createPattern();
		SDFMoC moc = (SDFMoC) superActor.getMoC();
		for (Port port : superActor.getOutputs()) {
			int numTokens = moc.getNumTokensProduced(port);
			Type type = factory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = factory.createVar(0, type, port.getName(), false, true);
			op.setNumTokens(port, numTokens);
			op.setVariable(port, var);
		}
		return op;
	}

	/**
	 * Creates the scheduler of the static action.
	 * 
	 * @return the scheduler of the static action
	 */
	private Procedure createScheduler() {
		final IrFactory factory = IrFactory.eINSTANCE;
		Procedure procedure = IrFactory.eINSTANCE.createProcedure(
				SCHEDULER_NAME, 0, IrFactory.eINSTANCE.createTypeBool());
		InstReturn returnInstr = factory.createInstReturn(factory
				.createExprBool(true));
		procedure.getLast().add(returnInstr);
		return procedure;
	}

	private void copyLocalVariables(String id, Action action) {
		List<Var> vars = new ArrayList<Var>(action.getBody().getLocals());
		for (Var var : vars) {
			var.setName(id + "_" + action.getName() + "_" + var.getName());
			actionBody.getLocals().add(var);
		}
	}

	/**
	 * Create the static schedule of the action
	 * 
	 * @param procedure
	 * @param schedule
	 * @param nodes
	 */
	private void createStaticSchedule(Procedure procedure, Schedule schedule,
			List<Block> nodes) {
		IrFactory factory = IrFactory.eINSTANCE;
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isActor()) {
				Actor actor = iterand.getActor();
				CSDFMoC moc = (CSDFMoC) actor.getMoC();
				for (Invocation invocation : moc.getInvocations()) {
					Action action = invocation.getAction();
					copyLocalVariables(actor.getName(), action);
					new ChangeFifoArrayAccess(action.getInputPattern(),
							action.getOutputPattern(), buffersMap, actionBody)
							.doSwitch(action.getBody());

					nodes.addAll(action.getBody().getBlocks());
				}

			} else {
				Schedule sched = iterand.getSchedule();
				Var loopVar = procedure.getLocal("idx_" + depth);

				// init counter
				BlockBasic block = IrUtil.getLast(nodes);
				block.add(factory.createInstAssign(loopVar,
						factory.createExprInt(0)));

				// while loop
				Expression condition = factory.createExprBinary(
						factory.createExprVar(loopVar), OpBinary.LT,
						factory.createExprInt(sched.getIterationCount()),
						factory.createTypeBool());

				BlockWhile nodeWhile = factory.createBlockWhile();
				nodeWhile.setJoinBlock(factory.createBlockBasic());
				nodeWhile.setCondition(condition);
				nodes.add(nodeWhile);

				depth++;
				// recursion
				createStaticSchedule(procedure, sched, nodeWhile.getBlocks());
				depth--;

				// Increment current while loop variable
				Expression expr = factory.createExprBinary(
						factory.createExprVar(loopVar), OpBinary.PLUS,
						factory.createExprInt(1), factory.createTypeInt(32));
				InstAssign assign = factory.createInstAssign(loopVar, expr);
				IrUtil.getLast(nodeWhile.getBlocks()).add(assign);
			}
		}
	}

	/**
	 * 
	 * @param ip
	 * @param op
	 * @return
	 */
	private Procedure createBody(Pattern ip, Pattern op) {
		IrFactory factory = IrFactory.eINSTANCE;

		actionBody = factory.createProcedure(ACTION_NAME, 0,
				IrFactory.eINSTANCE.createTypeVoid());

		createInnerBuffers();
		// Add loop counter(s)
		int i = 0;
		do { // one loop var is required even if the schedule as a depth of 0
			Var counter = factory.createVar(0, factory.createTypeInt(32),
					"idx_" + i, false, true);
			actionBody.getLocals().add(counter);
			i++;
		} while (i < scheduler.getDepth());

		// Add temp vars to load/store data from ports to buffers
		for (Port port : ip.getPorts()) {
			Type type = port.getType();
			String name = port.getName();
			Var tmp = factory.createVar(0, type, "tmp_" + name, false, true);
			actionBody.getLocals().add(tmp);
		}

		for (Port port : op.getPorts()) {
			Type type = port.getType();
			String name = port.getName();
			Var tmp = factory.createVar(0, type, "tmp_" + name, false, true);
			actionBody.getLocals().add(tmp);
		}

		// Initialize read/write counters
		for (Var var : new HashSet<Var>(buffersMap.values())) {
			Var read = actionBody.getLocal(var.getName() + "_r");
			Var write = actionBody.getLocal(var.getName() + "_w");

			BlockBasic block = actionBody.getLast();
			if (read != null) {
				block.add(factory.createInstStore(read,
						factory.createExprInt(0)));
			}
			if (write != null) {
				block.add(factory.createInstStore(write,
						factory.createExprInt(0)));
			}
		}

		createStaticSchedule(actionBody, scheduler.getSchedule(),
				actionBody.getBlocks());

		return actionBody;
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 */
	private void createStaticAction() {
		Action action = DfFactory.eINSTANCE.createAction();
		Pattern ip = createInputPattern();
		Pattern op = createOutputPattern();
		Procedure scheduler = createScheduler();
		Procedure body = createBody(ip, op);

		action.setTag(DfFactory.eINSTANCE.createTag());
		action.setInputPattern(ip);
		action.setOutputPattern(op);
		action.setPeekPattern(DfFactory.eINSTANCE.createPattern());
		action.setScheduler(scheduler);
		action.setBody(body);

		superActor.getActions().add(action);
		superActor.getActionsOutsideFsm().add(action);
	}

	@Override
	public Actor caseNetwork(Network network) {
		this.network = network;
		superActor = DfFactory.eINSTANCE.createActor();
		String name = "Cluster" + index++;
		superActor.setName(name);

		createPorts();
		copyStateVariables();
		copyProcedures();
		createStaticAction();

		return superActor;
	}
}
