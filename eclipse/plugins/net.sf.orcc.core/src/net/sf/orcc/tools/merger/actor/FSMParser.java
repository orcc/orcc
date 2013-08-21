package net.sf.orcc.tools.merger.actor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.State;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.util.DomUtil;

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

public class FSMParser {

	private Actor superActor;
	
	private String definitionFile;
	
	private FSM fsm;
	
	public FSMParser(String definitionFile, Actor superActor) {
		this.definitionFile = definitionFile;
		this.superActor = superActor;
		this.fsm = DfFactory.eINSTANCE.createFSM();
	}
	
	public FSM parse(String name) {
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document, name);
			is.close();
			return fsm;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
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
		State initial = MergerUtil.findState(fsm, initialState);
		if (initial != null) {
			fsm.setInitialState(initial);
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
					fsm.addTransition(
							addStateIfMissing(fsm, elt.getAttribute("src")), 
							addSuperactionIfMissing(superActor, elt.getAttribute("action")), 
							addStateIfMissing(fsm, elt.getAttribute("dst")));
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private Action addSuperactionIfMissing(Actor actor, String actionName) {
		Action action = MergerUtil.findAction(actor, actionName);
		if (action == null) {
			action = DfFactory.eINSTANCE.createAction();
			action.setTag(DfFactory.eINSTANCE.createTag(actionName));
			superActor.getActions().add(action);
		}
		return action;
	}

	private State addStateIfMissing(FSM fsm, String stateName) {
		State state = MergerUtil.findState(fsm, stateName);
		if(state == null) {
			state = (State) DfFactory.eINSTANCE.createState();
			state.setName(stateName);
			fsm.getStates().add(state);
		}
		return state;
	}

}
