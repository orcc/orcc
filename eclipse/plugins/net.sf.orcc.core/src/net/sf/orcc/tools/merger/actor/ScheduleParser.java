package net.sf.orcc.tools.merger.actor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
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

	private Map<Connection, Integer> maxTokens;

	private SuperAction schedule;

	private String definitionFile;

	private Network network;

	private Map<Connection, Integer> tokens;

	public ScheduleParser(String definitionFile, Network network) {
		this.definitionFile = definitionFile;
		this.network = network;
		schedule = new SuperAction();
	}
	
	public SuperAction parse(String superactor, String superaction) {
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document, superactor, superaction);
			is.close();
			return schedule;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void parseSuperactorList(Document doc, String superactor, String superaction) {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (node.getNodeName().equals("superactor") && (element.getAttribute("name").equals(superactor))) {
					parseSuperactor(element, superaction);			
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private void parseSuperactor(Element element, String superaction) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (elt.getTagName().equals("superaction") && (elt.getAttribute("name").equals(superaction))) {
					parseSuperaction(elt);			
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private void parseSuperaction(Element element) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (elt.getTagName().equals("iterand")) {
					Actor actor = findActor(elt.getAttribute("actor")).getAdapter(Actor.class);
					addActorActionToSchedule(actor, findAction(actor, elt.getAttribute("action")),
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

	private void addActorActionToSchedule(Actor actor, Action action, int rep) {
		if ((actor != null) && (action != null)) {
			for (int i = 0; i < rep; i++) {
				schedule.add(new ActionInvocation(actor, action));
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getDepth() {
		return 1;
	}
	
	/**
	 * Returns the scheduling computed by this scheduler.
	 * 
	 * @return the pre-computed schedule
	 */
	public SuperAction getSchedule() {
		return schedule;
	}

	/**
	 * @return
	 */
	public Map<Connection, Integer> getMaxTokens() {
		if (maxTokens == null) {
			maxTokens = new HashMap<Connection, Integer>();
			tokens = new HashMap<Connection, Integer>();

			for (Connection connection : network.getConnections()) {
				Actor src = connection.getSource().getAdapter(Actor.class);
				Actor tgt = connection.getTarget().getAdapter(Actor.class);
				if (src != null && tgt != null)
					maxTokens.put(connection, 0);
				tokens.put(connection, 0);
			}
			computeMemoryBound(schedule);
		}
		return maxTokens;
	}

	/**
	 * @param schedule
	 */
	private void computeMemoryBound(SuperAction schedule) {
		for (ActionInvocation iterand : schedule.getActionInvocations()) {
			Actor actor = iterand.getActor();
			for (Edge edge : actor.getIncoming()) {
				Connection conn = (Connection) edge;
				Actor src = conn.getSource().getAdapter(Actor.class);
				if (src != null) {
					Connection connection = (Connection) edge;
					Pattern inputPattern = iterand.getAction().getInputPattern();
					Port targetPort = connection.getTargetPort();
					int cns = inputPattern.getNumTokens(targetPort);
					tokens.put(connection, tokens.get(connection) - cns);
				}
			}

			for (Edge edge : actor.getOutgoing()) {
				Connection conn = (Connection) edge;
				Actor tgt = conn.getTarget().getAdapter(Actor.class);
				if (tgt != null) {
					Connection connection = (Connection) edge;
					Pattern outputPattern = iterand.getAction().getOutputPattern();
					Port sourcePort = connection.getSourcePort();
					int current = tokens.get(connection);
					int max = maxTokens.get(connection);
					int prd = outputPattern.getNumTokens(sourcePort);
					tokens.put(connection, current + prd);
					maxTokens.put(connection, max + prd);
				}
			}
		}
	}
	
}
