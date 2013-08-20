package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
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

public class ScheduleParser {

	private List<Schedule> scheduleList;

	private String definitionFile;

	private Network network;
	
	private String superActorName;

	public ScheduleParser(String definitionFile, Network network) {
		this.definitionFile = definitionFile;
		this.network = network;
		scheduleList = new ArrayList<Schedule>();
	}
	
	public List<Schedule> parse(String name) {
		superActorName = name;
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document);
			is.close();
			return scheduleList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void parseSuperactorList(Document doc) {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (node.getNodeName().equals("superactor") && element.getAttribute("name").equals(superActorName)) {
					parseSuperactor(element, element.getAttribute("name"));
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

	private Vertex findActor(String id) {
		for (Vertex vertex : network.getVertices()) {
			if(vertex.getLabel().equals(id))
				return vertex;
		}		
		return null;
	}

	private Action findAction(Actor actor, String id) {
		for(Action action : actor.getActions()) {
			if(action.getName().equals(id)) {
				return action;
			}
		}
		for(Action action : actor.getActionsOutsideFsm()) {
			if(action.getName().equals(id)) {
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

}
