package net.sf.orcc.tools.merger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.dftools.graph.Vertex;
import net.sf.dftools.graph.visit.ReversePostOrder;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

public class MergerHsdf extends DfSwitch<Actor> {

	private class ChangeFifoArrayAccess extends AbstractActorVisitor<Object> {

		private Map<Var, Integer> loads;
		private Map<Var, Integer> stores;

		private Pattern inputPattern;

		private Pattern outputPattern;

		private Map<Port, Var> buffersMap;

		public ChangeFifoArrayAccess(Pattern inputPattern,
				Pattern outputPattern, Map<Port, Var> buffersMap) {
			this.inputPattern = inputPattern;
			this.outputPattern = outputPattern;
			this.buffersMap = buffersMap;
		}

		@Override
		public Object caseActor(Actor actor) {
			for (Procedure proc : actor.getProcs()) {
				loads = new HashMap<Var, Integer>();
				stores = new HashMap<Var, Integer>();

				doSwitch(proc);
			}
			return null;
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			Port port = inputPattern.getVarToPortMap().get(var);

			if (var.isLocal() && port != null) {
				var = buffersMap.get(port);
				int cns = inputPattern.getNumTokens(port);
				loads.put(var, cns);
				use.setVariable(var);
			}

			return null;
		}

		@Override
		public Object caseInstStore(InstStore store) {
			Def def = store.getTarget();
			Var var = def.getVariable();
			Port port = outputPattern.getVarToPortMap().get(var);
			if (var.isLocal() && port != null) {
				var = buffersMap.get(port);
				int prd = outputPattern.getNumTokens(port);
				stores.put(var, prd);
				def.setVariable(var);
			}
			return null;
		}

	}

	private static int index;

	private static final String ACTION_NAME = "static_schedule";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private Actor superActor;

	private AbstractScheduler scheduler;

	private Network network;

	private Map<Port, Port> portsMap = new HashMap<Port, Port>();

	private Map<Port, Var> inputToVarMap = new HashMap<Port, Var>();

	private Map<Port, Var> buffersMap = new HashMap<Port, Var>();

	public MergerHsdf(AbstractScheduler scheduler) {
		this.scheduler = scheduler;
	}

	private void addBuffer(String name, int size, Type eltType) {
		IrFactory factory = IrFactory.eINSTANCE;
		Type type = factory.createTypeList(size, eltType);
		Var var = factory.createVar(0, type, name, true, true);
		superActor.getStateVars().add(var);
	}

	/**
	 * Creates input and output ports of the merged actor.
	 * 
	 */
	private void createPorts() {
		Copier copier = new Copier();
		SDFMoC sdfMoC = MocFactory.eINSTANCE.createSDFMoC();
		superActor.setMoC(sdfMoC);
		superActor.getInputs().addAll(copier.copyAll(network.getInputs()));
		superActor.getOutputs().addAll(copier.copyAll(network.getOutputs()));
		copier.copyReferences();

		for (Port port : network.getInputs()) {
			Connection connection = (Connection) port.getOutgoing().get(0);
			Vertex tgt = connection.getTarget();
			CSDFMoC moc = (CSDFMoC) ((Instance) tgt).getMoC();
			int cns = moc.getNumTokensConsumed(connection.getTargetPort());
			Port portCopy = (Port) copier.get(port);
			sdfMoC.getInputPattern().setNumTokens(portCopy, cns);
			portsMap.put(portCopy, connection.getTargetPort());
		}

		for (Port port : network.getOutputs()) {
			Connection connection = (Connection) port.getIncoming().get(0);
			Vertex src = connection.getSource();
			CSDFMoC moc = (CSDFMoC) ((Instance) src).getMoC();
			int prd = moc.getNumTokensProduced(connection.getSourcePort());
			Port portCopy = (Port) copier.get(port);
			sdfMoC.getOutputPattern().setNumTokens(portCopy, prd);
			portsMap.put(portCopy, connection.getSourcePort());
		}
	}

	private void copyStateVariables() {
		for (Instance instance : network.getInstances()) {
			String id = instance.getName();
			Actor actor = instance.getActor();
			for (Var var : new ArrayList<Var>(actor.getStateVars())) {
				String name = var.getName();
				actor.getStateVar(name).setName(id + "_" + name);
				superActor.getStateVars().add(var);
			}
		}
	}

	private void copyProcedures() {
		for (Instance instance : network.getInstances()) {
			String id = instance.getName();
			Actor actor = instance.getActor();
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
	private void createStateVariables() {
		buffersMap = new HashMap<Port, Var>();
		inputToVarMap = new HashMap<Port, Var>();
		SDFMoC sdfMoc = (SDFMoC) superActor.getMoC();

		int index = 0;
		// Create buffers for inputs
		for (Port port : superActor.getInputs()) {
			String name = "buffer_" + index++;
			int numTokens = sdfMoc.getNumTokensConsumed(port);
			addBuffer(name, numTokens, port.getType());
			inputToVarMap.put(port, superActor.getStateVar(name));
			buffersMap.put(portsMap.get(port), superActor.getStateVar(name));
		}

		// Create buffers for outputs
		for (Port port : superActor.getOutputs()) {
			String name = "buffer_" + index++;
			int numTokens = sdfMoc.getNumTokensProduced(port);
			addBuffer(name, numTokens, port.getType());
			inputToVarMap.put(port, superActor.getStateVar(name));
			buffersMap.put(portsMap.get(port), superActor.getStateVar(name));
		}

		// Create buffers for connections inside the sub-graph
		for (Connection connection : network.getConnections()) {
			Vertex src = connection.getSource();
			Vertex tgt = connection.getTarget();
			if (src instanceof Instance && tgt instanceof Instance) {
				String name = "buffer_" + index++;
				Port srcPort = connection.getSourcePort();
				Port tgtPort = connection.getTargetPort();
				int prd = ((CSDFMoC) ((Instance) src).getMoC())
						.getNumTokensProduced(srcPort);
				addBuffer(name, prd, srcPort.getType());
				buffersMap.put(srcPort, superActor.getStateVar(name));
				buffersMap.put(tgtPort, superActor.getStateVar(name));
			}
		}
	}

	/**
	 * Turns actions of CSDF actors into procedures
	 * 
	 */
	private void createProcedures() {
		IrFactory factory = IrFactory.eINSTANCE;
		for (Instance instance : network.getInstances()) {
			CSDFMoC moc = (CSDFMoC) instance.getMoC();
			Iterator<Invocation> it = moc.getInvocations().iterator();

			Set<Action> alreadyExists = new HashSet<Action>();
			while (it.hasNext()) {
				Action action = it.next().getAction();
				if (!alreadyExists.contains(action)) {
					alreadyExists.add(action);
					String name = instance.getName() + "_" + action.getName();
					Procedure proc = factory.createProcedure(name, 0,
							factory.createTypeVoid());

					Procedure body = action.getBody();
					List<Block> nodes = body.getBlocks();
					proc.getLocals().addAll(body.getLocals());
					proc.getBlocks().addAll(nodes);
					IrUtil.getLast(nodes).add(factory.createInstReturn());
					superActor.getProcs().add(proc);
					new ChangeFifoArrayAccess(action.getInputPattern(),
							action.getOutputPattern(), buffersMap)
							.doSwitch(superActor);

				}
			}
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
	 * copies the value of source into target
	 * 
	 * @param procedure
	 * @param source
	 * @param target
	 */
	private void createCopies(Procedure procedure, String name, Var source,
			Var target) {
		IrFactory factory = IrFactory.eINSTANCE;

		List<Block> nodes = procedure.getBlocks();

		int size = ((TypeList) source.getType()).getSize();

		Var tmpVar = procedure.getLocal("tmp_" + name);

		if (size == 1) {
			List<Expression> indexes = new ArrayList<Expression>();
			indexes.add(factory.createExprInt(0));
			InstLoad load = factory.createInstLoad(tmpVar, source, indexes);
			indexes = new ArrayList<Expression>();
			indexes.add(factory.createExprInt(0));
			InstStore store = factory.createInstStore(0, target, indexes,
					factory.createExprVar(tmpVar));
			BlockBasic node = IrUtil.getLast(nodes);
			node.add(load);
			node.add(store);
		} else {
			Var loop = procedure.getLocal("idx_0");
			BlockBasic block = IrUtil.getLast(nodes);
			block.add(factory.createInstAssign(loop, factory.createExprInt(0)));

			Expression condition = factory.createExprBinary(
					factory.createExprVar(loop), OpBinary.LT,
					factory.createExprInt(size), factory.createTypeBool());

			BlockWhile nodeWhile = factory.createBlockWhile();
			nodeWhile.setJoinBlock(factory.createBlockBasic());
			nodeWhile.setCondition(condition);
			nodes.add(nodeWhile);

			List<Expression> indexes = new ArrayList<Expression>();
			indexes.add(factory.createExprVar(loop));
			InstLoad load = factory.createInstLoad(tmpVar, source, indexes);

			indexes = new ArrayList<Expression>();
			indexes.add(factory.createExprVar(loop));
			InstStore store = factory.createInstStore(0, target, indexes,
					factory.createExprVar(tmpVar));
			BlockBasic childBlock = IrUtil.getLast(nodeWhile.getBlocks());
			childBlock.add(load);
			childBlock.add(store);

			// increment loop counter
			Expression expr = factory.createExprBinary(
					factory.createExprVar(loop), OpBinary.PLUS,
					factory.createExprInt(1), factory.createTypeInt(32));
			InstAssign assign = factory.createInstAssign(loop, expr);
			childBlock.add(assign);
		}
	}

	private void createCopiesFromInputs(Procedure procedure, Pattern ip) {
		for (Port port : superActor.getInputs()) {
			Var input = ip.getVariable(port);
			Var buffer = inputToVarMap.get(port);

			createCopies(procedure, port.getName(), input, buffer);
		}
	}

	private void createCopiesToOutputs(Procedure procedure, Pattern op) {
		for (Port port : superActor.getOutputs()) {
			Var output = op.getVariable(port);
			Var buffer = inputToVarMap.get(port);

			createCopies(procedure, port.getName(), buffer, output);
		}
	}

	/**
	 * Creates a return instruction that uses the results of the hasTokens tests
	 * previously created. Returns true right now.
	 * 
	 * @param block
	 *            block to which return is to be added
	 */
	private void createInputCondition(BlockBasic block) {
		final IrFactory factory = IrFactory.eINSTANCE;
		InstReturn returnInstr = factory.createInstReturn(factory
				.createExprBool(true));
		block.add(returnInstr);
	}

	/**
	 * Creates the scheduler of the static action.
	 * 
	 * @return the scheduler of the static action
	 */
	private Procedure createScheduler() {
		IrFactory factory = IrFactory.eINSTANCE;
		Procedure procedure = IrFactory.eINSTANCE.createProcedure(
				SCHEDULER_NAME, 0, IrFactory.eINSTANCE.createTypeBool());

		BlockBasic block = factory.createBlockBasic();
		procedure.getBlocks().add(block);
		createInputCondition(block);
		return procedure;
	}

	/**
	 * Create the static schedule of the action
	 * 
	 * @param procedure
	 */
	private void createStaticSchedule(Procedure procedure) {
		for (Vertex vertex : new ReversePostOrder(network, network.getInputs())) {
			Instance instance = (Instance) vertex;
			CSDFMoC moc = (CSDFMoC) instance.getMoC();
			BlockBasic block = procedure.getLast();
			for (Invocation invocation : moc.getInvocations()) {
				Action action = invocation.getAction();
				Procedure proc = superActor.getProcedure(instance.getName()
						+ "_" + action.getName());
				block.add(IrFactory.eINSTANCE.createInstCall(0, null, proc,
						new ArrayList<Expression>()));
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

		Procedure procedure = factory.createProcedure(ACTION_NAME, 0,
				IrFactory.eINSTANCE.createTypeVoid());

		// Add temp vars to load/store data from ports to buffers
		for (Port port : ip.getPorts()) {
			Type type = port.getType();
			String name = port.getName();
			Var tmp = factory.createVar(0, type, "tmp_" + name, false, true);
			procedure.getLocals().add(tmp);
		}

		for (Port port : op.getPorts()) {
			Type type = port.getType();
			String name = port.getName();
			Var tmp = factory.createVar(0, type, "tmp_" + name, false, true);
			procedure.getLocals().add(tmp);
		}

		createCopiesFromInputs(procedure, ip);

		createStaticSchedule(procedure);

		createCopiesToOutputs(procedure, op);

		return procedure;
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 */
	private void createStaticAction() {
		Pattern ip = createInputPattern();
		Pattern op = createOutputPattern();
		Procedure scheduler = createScheduler();
		Procedure body = createBody(ip, op);

		Action action = DfFactory.eINSTANCE.createAction(
				DfFactory.eINSTANCE.createTag(), ip, op,
				DfFactory.eINSTANCE.createPattern(), scheduler, body);

		superActor.getActions().add(action);
		superActor.getActionsOutsideFsm().add(action);
	}

	@Override
	public Actor caseNetwork(Network network) {
		this.network = network;
		superActor = DfFactory.eINSTANCE.createActor();
		String name = "cluster" + index;
		superActor.setName(name);

		new SdfToHsdf(scheduler.getRepetitions()).doSwitch(network);

		new UniqueInstantiator().doSwitch(network);

		createPorts();
		copyStateVariables();
		copyProcedures();
		createStateVariables();
		createProcedures();
		createStaticAction();

		return superActor;
	}
}
