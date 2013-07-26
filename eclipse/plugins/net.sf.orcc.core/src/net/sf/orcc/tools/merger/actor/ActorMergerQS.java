package net.sf.orcc.tools.merger.actor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.State;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.DomUtil;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class merges actors based on a merging specification in
 * an XML file. The class is analogous to ActorMergerSDF.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class ActorMergerQS extends ActorMergerBase {

	private Network network;
	
	private String definitionFile;

	public class ActionUpdater extends AbstractIrVisitor<Void> {

		private Pattern oldInputPattern;

		private Pattern oldOutputPattern;

		/**
		 * Create a new visitor able to update the FIFO accesses.
		 * 
		 * @param action
		 *            the action containing the old patterns
		 */
		
		public ActionUpdater(Action action) {
			this.oldInputPattern = action.getInputPattern();
			this.oldOutputPattern = action.getOutputPattern();
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			Port port = oldInputPattern.getPort(load.getSource().getVariable());
			if (port != null) {
				List<Expression> indexes = load.getIndexes();
				String portName = port.getAttribute("shortName").getStringValue();
				Expression e1 = irFactory.createExprVar(irFactory.createVar(irFactory.createTypeInt(32), "offset_" + portName, false, 0));
				Expression e2 = IrUtil.copy(indexes.get(0));
				Expression e3 = irFactory.createExprVar(irFactory.createVar(irFactory.createTypeInt(32), "SIZE_" + port.getName(), false, 0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				if (!buffersMap.containsKey(port)) {
					indexes.set(0, irFactory.createExprBinary(bop, OpBinary.MOD,
							e3, e1.getType()));
				} else {
					indexes.set(0, bop);
				}
			}
			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Port port = oldOutputPattern.getPort(store.getTarget().getVariable());
			if (port != null) {
				List<Expression> indexes = store.getIndexes();
				String portName = port.getAttribute("shortName").getStringValue();
				Expression e1 = irFactory.createExprVar(irFactory.createVar(irFactory.createTypeInt(32), "offset_" + portName, false, 0));
				Expression e2 = IrUtil.copy(indexes.get(0));
				Expression e3 = irFactory.createExprVar(irFactory.createVar(irFactory.createTypeInt(32), "SIZE_" + port.getName(), false, 0));
				Expression bop = irFactory.createExprBinary(e1, OpBinary.PLUS,
						e2, e1.getType());
				if (!buffersMap.containsKey(port)) {
					indexes.set(0, irFactory.createExprBinary(bop, OpBinary.MOD,
							e3, e1.getType()));
				} else {
					indexes.set(0, bop);
				}
			}
			return null;
		}

		@Override
		public Void caseProcedure(Procedure procedure) {
			super.caseProcedure(procedure);
			return null;
		}
	}
	
	private class ActionProcedureMap {

		private class ActionProcedurePair {
			Procedure procedure;
			Action action;
			
			ActionProcedurePair(Action action, Procedure procedure) {
				this.action = action;
				this.procedure = procedure;
			}
			
			Action getAction() {
				return action;
			}
			
			Procedure getProcedure() {
				return procedure;
			}
		}
		
		private List<ActionProcedurePair> list; 
		
		/**
		 * Keep track of actions that have been transformed to procedures.
		 * 
		 */
		public ActionProcedureMap() {
			this.list = new ArrayList<ActionProcedurePair>();
		}
		
		public void add(Action action, Procedure procedure) {
			list.add(new ActionProcedurePair(action, procedure));
		}
		
		public Procedure getProcedure(Action action) {
			for (ActionProcedurePair pair : list) {
				if (pair.getAction().equals(action)) {
					return pair.getProcedure();
				}
			}
			return null;
		}
	}
	
	ActionProcedureMap correspondences;
	
	public ActorMergerQS(Network network, Copier copier, String definitionFile) {
		this.network = network;
		this.copier = copier;
		this.definitionFile = definitionFile;
		this.correspondences = new ActionProcedureMap();
	}

	public Actor createMergedActor() {
		superActor = dfFactory.createActor();
		buffersMap = new HashMap<Port, Var>();
		superActor.setName(network.getName());
		superActor.setFsm(dfFactory.createFSM());

		copyPorts(network);
		copyVariables(network);
		copyProcedures(network);
		copyActions(network);
		constructFSM();
		modifyMemAccesses(network);

		return superActor;
	}
	
	private void copyPorts(Network network) {
		// Create input/output ports
		for (Port port : network.getInputs()) {
			superActor.getInputs().add((Port)copier.copy(port));
		}
		for (Port port : network.getOutputs()) {
			superActor.getOutputs().add((Port)copier.copy(port));
		}
	}

	private void copyActions(Network network) {
		// Transform actions into procedures
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			for (Action action : actor.getActions()) {
				Procedure actionCopy = IrUtil.copy(action.getBody());
				actionCopy.setName(new String(actor.getName() + "_" + action.getName() + "_p"));
				createParameters(actionCopy, action);
				correspondences.add(action, actionCopy);
				superActor.getProcs().add(actionCopy);
			}
		}
	}

	private void modifyMemAccesses(Network network) {
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			for (Action action : actor.getActions()) {
				new ActionUpdater(action).doSwitch(correspondences.getProcedure(action));
			}
		}
	}

	private void createParameters(Procedure procedure, Action action) {
		Pattern inputPattern = action.getInputPattern();
		for(Port port : inputPattern.getPorts()) {
			createParameterPair(procedure, inputPattern.getVariable(port));
		}
		Pattern outputPattern = action.getOutputPattern();
		for(Port port : outputPattern.getPorts()) {
			createParameterPair(procedure, outputPattern.getVariable(port));
		}
	}
	
	private void createParameterPair(Procedure procedure, Var var) {
		Param param = IrFactory.eINSTANCE.createParam(var);
		param.setByRef(true);
		procedure.getParameters().add(param);
		param = IrFactory.eINSTANCE.createParam(IrFactory.eINSTANCE.createVar(
				IrFactory.eINSTANCE.createTypeInt(32),
				"offset_" + var.getName(), true, 0));
		param.setByRef(false);
		procedure.getParameters().add(param);
	}
	
	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Action createSuperaction(String actionName) {
		ScheduleParser scheduler = new ScheduleParser(definitionFile, network);
		Schedule superAction = scheduler.parse(network.getName(), actionName);
		Pattern inputPattern = computeScheduleInputPattern(network, superAction.getIterands());
		Pattern outputPattern = computeScheduleOutputPattern(network, superAction.getIterands());

		GuardParser guardParser = new GuardParser(definitionFile, actionName, superActor);
		guardParser.parse(network.getName(), actionName);
		
		Procedure body = irFactory.createProcedure(actionName, 0,
				irFactory.createTypeVoid());
		createBuffers(body, scheduler.getMaxTokens());
		createCounters(body, inputPattern, outputPattern);
		createStaticSchedule(body, scheduler.getSchedule());
		body.getLast().add(irFactory.createInstReturn());
	
		Action action = dfFactory.createAction(actionName, inputPattern,
				outputPattern, guardParser.getPeekPattern(), guardParser.getGuard(), body);
	
		superActor.getActions().add(action);
		
		return action;
	}

	/*
	 * Each superaction instance has an input and output pattern
	 * that is visible to the actors neighboring its superactor. 
	 * The pattern need to be determined from the invocations.
	 */
	
	public Pattern computeScheduleInputPattern(Network subNetwork, List<Iterand> invocations) {
		Pattern inputPattern = dfFactory.createPattern();
		for (Iterand iterand : invocations) {
			Pattern iterandPattern = iterand.getAction().getInputPattern();
			for (Port port : iterandPattern.getPorts()) {
				if (MergerUtil.findPort(subNetwork.getInputs(), port.getName()) >= 0) {
					if (!inputPattern.contains(port)) {
						inputPattern.getPorts().add(port);
					}
					Port superactionPort = inputPattern.getPorts().get(
							MergerUtil.findPort(inputPattern.getPorts(), port.getName()));
					inputPattern.setNumTokens(superactionPort, 
							inputPattern.getNumTokens(superactionPort) + iterandPattern.getNumTokens(port));
				}
			}
		}
		return createInputPattern(inputPattern);
	}

	public Pattern computeScheduleOutputPattern(Network subNetwork, List<Iterand> invocations) {
		Pattern outputPattern = dfFactory.createPattern();
		for (Iterand iterand : invocations) {
			Pattern iterandPattern = iterand.getAction().getOutputPattern();
			for (Port port : iterandPattern.getPorts()) {
				if (MergerUtil.findPort(subNetwork.getOutputs(), port.getName()) >= 0) {
					if(!outputPattern.contains(port)) {
						outputPattern.getPorts().add(port);
					}
					Port superactionPort = outputPattern.getPorts().get(
							MergerUtil.findPort(outputPattern.getPorts(), port.getName()));
					outputPattern.setNumTokens(superactionPort, 
							outputPattern.getNumTokens(superactionPort) + iterandPattern.getNumTokens(port));
				}
			}
		}
		return createOutputPattern(outputPattern);
	}

	private void createCounters(Procedure body, Pattern inputPattern, Pattern outputPattern) {
		// Create counters for inputs
		for (Port port : inputPattern.getPorts()) {
			createCounter(body, port, "_r");
		}
		// Create counters for outputs
		for (Port port : outputPattern.getPorts()) {
			createCounter(body, port, "_w");
		}
	}
	
	private void createCounter(Procedure body, Port port, String suffix) {
		Var readIdx = body.newTempLocalVariable(
				irFactory.createTypeInt(32), port.getName() + suffix);
		if (buffersMap.containsKey(port)) {
			body.getLast().add(irFactory.createInstAssign(readIdx, irFactory.createExprInt(0)));
		} else {
			body.getLast().add(irFactory.createInstAssign(readIdx, MergerUtil.createIndexVar(port)));
		}
	}

	private Pattern createInputPattern(Pattern actionInputPattern) {
		Pattern inputPattern = dfFactory.createPattern();
		for (Port port : superActor.getInputs()) {
			port.setNumTokensConsumed(createPortPattern(inputPattern, 
					actionInputPattern, port));
		}
		return inputPattern;
	}

	private Pattern createOutputPattern(Pattern actionOutputPattern) {
		Pattern outputPattern = dfFactory.createPattern();
		for (Port port : superActor.getOutputs()) {
			port.setNumTokensProduced(createPortPattern(outputPattern, 
					actionOutputPattern, port));
		}
		return outputPattern;
	}

	private int createPortPattern(Pattern targetPattern, Pattern sourcePattern, Port port) {
		int portIndex = MergerUtil.findPort(sourcePattern.getPorts(), port.getName());
		if (portIndex >= 0) {
			int prd = sourcePattern.getNumTokens(sourcePattern.getPorts().get(portIndex));
			targetPattern.setNumTokens(port, prd);
			targetPattern.setVariable(port, irFactory.createVar(0, irFactory.createTypeList(prd,
					EcoreUtil.copy(port.getType())), port.getName(), true));
			return prd;
		} else {
			return 0;
		}
	}
	
	/**
	 * Create the procedural code of a static schedule.
	 * 
	 * @param procedure
	 *            the associated procedure
	 * @param schedule
	 *            the current schedule
	 */
	private void createStaticSchedule(Procedure procedure, Schedule schedule) {
		for (Iterand iterand : schedule.getIterands()) {
			List<Expression> procParams = new ArrayList<Expression>();
			BlockBasic increments = IrFactory.eINSTANCE.createBlockBasic();
			processInputs(increments, iterand.getAction(), procParams);
			processOutputs(increments, iterand.getAction(), procParams);
			Instruction instruction = IrFactory.eINSTANCE.createInstCall(
					null, correspondences.getProcedure(iterand.getAction()), procParams);
			BlockBasic block = procedure.getLast();
			block.add(instruction);
			procedure.getBlocks().add(block);
			procedure.getBlocks().add(increments);
		}
	}

	private void processInputs(BlockBasic increments, Action action, List<Expression> procParams) {
		for(Port port : action.getInputPattern().getPorts()) {
			processPort(increments, procParams, port, "_r", action.getInputPattern().getNumTokens(port));
		}
	}

	private void processOutputs(BlockBasic increments, Action action, List<Expression> procParams) {
		for(Port port : action.getOutputPattern().getPorts()) {
			processPort(increments, procParams, port, "_w", action.getOutputPattern().getNumTokens(port));
		}
	}
	
	private void processPort(BlockBasic increments, List<Expression> procParams, Port port, String suffix, int tokenRate) {
		Var memVar = null;
		String indexVarName = null;
		if (buffersMap.containsKey(port)) {
			memVar = buffersMap.get(port);
			indexVarName = memVar.getName() + suffix;
		} else {
			memVar = IrFactory.eINSTANCE.createVar(0,
					IrFactory.eINSTANCE.createTypeList(1, port.getType()),
					"tokens_" + port.getName(), true, 0);
			indexVarName = port.getName() + suffix;
		}
		Var indexVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				indexVarName, true, 0);
		increments.add(MergerUtil.createBinOpStore(indexVar, OpBinary.PLUS, tokenRate));
		procParams.add(IrFactory.eINSTANCE.createExprVar(memVar));
		procParams.add(IrFactory.eINSTANCE.createExprVar(indexVar));
	}
	
	private void constructFSM() {
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document, network.getName());
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void parseSuperactorList(Document doc, String superactor) {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (node.getNodeName().equals("superactor") && (element.getAttribute("name").equals(superactor))) {
					parseFSM(element);
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}

	private void parseFSM(Element element) {
		String initialStateName = null;
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (node.getNodeName().equals("fsm")) {
					initialStateName = elt.getAttribute("initial");
					parseTransitions(elt);			
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
		setInitialState(initialStateName);
	}

	private void setInitialState(String initialState) {
		State initial = MergerUtil.findState(superActor.getFsm(), initialState);
		if (initial != null) {
			superActor.getFsm().setInitialState(initial);
		} else {
			// TODO: manage error
		}
	}
	
	private void parseTransitions(Element element) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (elt.getTagName().equals("transition")) {
					superActor.getFsm().addTransition(
							addStateIfMissing(superActor.getFsm(), elt.getAttribute("src")), 
							addSuperactionIfMissing(superActor, elt.getAttribute("action")), 
							addStateIfMissing(superActor.getFsm(), elt.getAttribute("dst")));
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private Action addSuperactionIfMissing(Actor actor, String actionName) {
		Action action = MergerUtil.findAction(actor, actionName);
		if (action == null) {
			action = createSuperaction(actionName);
		}
		return action;
	}

	private State addStateIfMissing(FSM fsm, String stateName) {
		State state = MergerUtil.findState(superActor.getFsm(), stateName);
		if(state == null) {
			state = (State) dfFactory.createState();
			state.setName(stateName);
			fsm.getStates().add(state);
		}
		return state;
	}

}
