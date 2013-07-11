package net.sf.orcc.tools.merger.actor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.State;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
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
	
	public ActorMergerQS(Network network, Copier copier, String definitionFile) {
		this.network = network;
		this.copier = copier;
		this.definitionFile = definitionFile;
	}

	public Actor createMergedActor() {
		superActor = dfFactory.createActor();
		buffersMap = new HashMap<Port, Var>();
		portsMap = new HashMap<Port, Port>();
		superActor.setName(network.getName());
		superActor.setFsm(dfFactory.createFSM());

		// Create input/output ports
		for (Port port : network.getInputs()) {
			Port portCopy = (Port) copier.copy(port);
			Connection connection = (Connection) port.getOutgoing().get(0);
			superActor.getInputs().add(portCopy);
			portsMap.put(connection.getTargetPort(), portCopy);
		}

		for (Port port : network.getOutputs()) {
			Port portCopy = (Port) copier.copy(port);
			Connection connection = (Connection) port.getIncoming().get(0);
			superActor.getOutputs().add(portCopy);
			portsMap.put(connection.getSourcePort(), portCopy);
		}

		copyVariables(network);
		copyProcedures(network);
		constructFSM();

		return superActor;
	}
	
	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Action createSuperaction(String actionName) {

		ScheduleParser scheduler = new ScheduleParser(definitionFile, network);
		SuperAction superAction = scheduler.parse(network.getName(), actionName);
		superAction.computePatterns(network);

		createInputPattern(superAction);
		createOutputPattern(superAction);
		
		GuardParser guardParser = new GuardParser(definitionFile, actionName, superActor);
		guardParser.parse(network.getName(), actionName);
		
		Procedure body = createBody(actionName, scheduler.getMaxTokens(), scheduler.getDepth());
		createStaticSchedule(body, scheduler.getSchedule(), body.getBlocks());
		body.getLast().add(irFactory.createInstReturn());
	
		Action action = dfFactory.createAction(actionName, inputPattern,
				outputPattern, guardParser.getPeekPattern(), guardParser.getGuard(), body);
	
		superActor.getActions().add(action);
		
		return action;
	}

	private void createInputPattern(SuperAction superAction) {
		inputPattern = dfFactory.createPattern();
		for (Port port : superActor.getInputs()) {
			int portIndex = MergerUtil.findPort(superAction.getInputPattern().getPorts(), port.getName());
			if(portIndex >= 0) {
				int cns = superAction.getInputPattern().getPorts().get(portIndex).
						getNumTokensConsumed();
				inputPattern.setNumTokens(port, cns);
				inputPattern.setVariable(port, irFactory.createVar(0, irFactory.createTypeList(cns,
						EcoreUtil.copy(port.getType())), port.getName(), true));
				port.setNumTokensConsumed(cns);
			} else {
				port.setNumTokensConsumed(0);
			}
		}	
	}

	private void createOutputPattern(SuperAction superAction) {
		outputPattern = dfFactory.createPattern();
		for (Port port : superActor.getOutputs()) {
			int portIndex = MergerUtil.findPort(superAction.getOutputPattern().getPorts(), port.getName());
			if (portIndex >= 0) {
				int prd = superAction.getOutputPattern().getPorts().get(portIndex).
						getNumTokensProduced();
				outputPattern.setNumTokens(port, prd);
				outputPattern.setVariable(port, irFactory.createVar(0, irFactory.createTypeList(prd,
						EcoreUtil.copy(port.getType())), port.getName(), true));
				port.setNumTokensProduced(prd);
			} else {
				port.setNumTokensProduced(0);
			}
		}
	}
	
	/**
	 * Create the procedural code of a static schedule.
	 * 
	 * @param procedure
	 *            the associated procedure
	 * @param schedule
	 *            the current schedule
	 * @param blocks
	 *            the current list of blocks
	 */
	private void createStaticSchedule(Procedure procedure, SuperAction schedule,
			List<Block> blocks) {
		for (ActionInvocation iterand : schedule.getActionInvocations()) {
			addActionToBody(procedure, IrUtil.copy(iterand.getAction()), blocks);
		}
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
