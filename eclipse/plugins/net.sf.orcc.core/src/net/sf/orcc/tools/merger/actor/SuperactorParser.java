package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.State;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.DomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class provides a way to import schedules from XML files.
 * The class is analogous to SASLoopScheduler.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class SuperactorParser {

	private List<Schedule> scheduleList;

	private Network network;

	private Actor superActor;
	
	private FSM fsm;
	
	public SuperactorParser(Network network, Actor superActor) {
		this.network = network;
		this.superActor = superActor;
		this.scheduleList = new ArrayList<Schedule>();
		this.fsm = DfFactory.eINSTANCE.createFSM();
	}
	
	public void parse(String definitionFile) {
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Schedule> getScheduleList() {
		return scheduleList;
	}

	public FSM getFSM() {
		return fsm;
	}

	private void parseSuperactorList(Document doc) {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (node.getNodeName().equals("superactor") && element.getAttribute("name").equals(network.getName())) {
					parseSuperactor(element, element.getAttribute("name"));
					parseFSM(element);
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private void parseSuperactor(Element element, String superactor) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (elt.getTagName().equals("superaction")) {
					Schedule schedule = new Schedule();
					schedule.setName(elt.getAttribute("name"));
					schedule.setOwner(superactor);
					parseSuperaction(schedule, elt);
					scheduleList.add(schedule);
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private void parseSuperaction(Schedule schedule, Element element) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (elt.getTagName().equals("iterand")) {
					Actor actor = findActor(elt.getAttribute("actor")).getAdapter(Actor.class);
					addActorActionToSchedule(schedule, actor, findAction(actor, elt.getAttribute("action")),
							Integer.parseInt(elt.getAttribute("repetitions")));
				}
			}
			node = node.getNextSibling();
		}
	}

	/*
	 * Schedule parsing specific methods
	 */
	
	private Vertex findActor(String id) {
		for (Vertex vertex : network.getVertices()) {
			if(vertex.getLabel().equals(id))
				return vertex;
		}		
		return null;
	}

	private Action findAction(Actor actor, String id) {
		for(Action action : actor.getActions()) {
			if (action.getName().equals(id)) {
				return action;
			}
		}
		for(Action action : actor.getActionsOutsideFsm()) {
			if (action.getName().equals(id)) {
				return action;
			}
		}
		return null;
	}

	private void addActorActionToSchedule(Schedule schedule, Actor actor, Action action, int rep) {
		if ((actor != null) && (action != null)) {
			for (int i = 0; i < rep; i++) {
				schedule.add(new Iterand(action));
			}
		}
	}

	/*
	 * FSM parsing specific methods
	 */
	
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
