/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Util;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.CALMLParsingException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnhandledCaseException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Action;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.EFSM;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Port;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.PortType;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.PriorityChain;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.State;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Transition;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Expr;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Stmt;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.VarType;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Variable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * CalmlParser is for parsing CALML files and creating EFSMs out of them.
 */
public class CalmlParser {

	static final long serialVersionUID = 10000002L;
	private static Document dom;
	private static EFSM efsm;
	private static String calmlFileName;
	private static DocumentBuilder db = null;

	public static EFSM getMachine() {
		return efsm;
	}

	/**
	 * parses file with name calmlFileName and constructs efsm
	 */
	public static EFSM parse(String calmlFileName, EFSM efsm)
			throws CALMLParsingException, IOException, UnhandledCaseException {
		CalmlParser.calmlFileName = calmlFileName;
		CalmlParser.efsm = efsm;
		initDocumentBuilder();
		// get the dom
		parseCalmlFile();

		// dom to efsm
		domToEfsm();

		// printGraph
		printEfsm();

		return efsm;

	}

	private static void initDocumentBuilder() {
		if (db != null)
			return;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException ex) {
			Logger.getLogger(CalmlParser.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private static void parseCalmlFile() throws IOException {
		try {
			dom = db.parse(calmlFileName);
		} catch (SAXException ex) {
			ex.printStackTrace();
		}

		/*
		 * // get the factory DocumentBuilderFactory dbf =
		 * DocumentBuilderFactory.newInstance();
		 * 
		 * try {
		 * 
		 * // Using factory get an instance of document builder DocumentBuilder
		 * db = dbf.newDocumentBuilder();
		 * 
		 * // parse using builder to get DOM representation of the XML file dom
		 * = db.parse(calmlFileName);
		 * 
		 * } catch (ParserConfigurationException pce) { pce.printStackTrace(); }
		 * catch (SAXException se) { se.printStackTrace(); }
		 */

	}

	private static void domToEfsm() throws CALMLParsingException,
			UnhandledCaseException {

		Element docRoot = dom.getDocumentElement();

		// ports on 23/5/08

		setName(docRoot);
		// System.out.println("\nParsing Actor " + efsm.getEFSMName() + ":" );
		setPorts(docRoot);
		setDeclaredVars(docRoot);
		// action elements
		NodeList actionList = docRoot.getElementsByTagName("Action");
		// table key=QID, value = action
		HashMap<String, Action> actionTable = setActionTable(actionList);
		setPriorities(docRoot);
		// System.out.println("Priorities: " + "\n\t" + efsm.getPriorities());
		// get the list of schedule elements,almost one element is allowed in
		// this list
		NodeList scheduleList = docRoot.getElementsByTagName("Schedule");
		NodeList transitionList;
		boolean hasNoTransitions = scheduleList == null
				|| scheduleList.getLength() == 0;
		if (!hasNoTransitions) {
			Element scheduleEle = (Element) (scheduleList.item(0));
			transitionList = scheduleEle.getElementsByTagName("Transition");
			hasNoTransitions = (transitionList == null || transitionList
					.getLength() == 0);
		}
		efsm.hasNoTransitions = hasNoTransitions;

		/**
		 * When an actor has not any transition, then system creates an
		 * artifical efsm
		 */
		if (hasNoTransitions) {

			String efsmName = efsm.getEFSMName();
			// System.out.println("Actor "+ efsmName
			// +" has not any transition");

			// Creates initial state
			State startState = new State(efsmName + "_start");
			startState.setToInitialState();
			efsm.addVertex(startState);
			efsm.setInitialState(startState);

			// Creates proccess state
			String procStateName = efsmName + "_proc";
			State procState = new State(procStateName);
			efsm.addVertex(procState);

			// Creates an edge between start state and proccess state: start
			// action
			Action startAction = new Action("start");
			efsm.addEdge(startState, procState);

			Set<Transition> T1 = efsm.getAllEdges(startState, procState);
			Transition t = T1.iterator().next();
			t.setFrom(startState.getName());
			t.setTo(procState.getName());
			t.addActionTag("start");
			t.addAction(startAction);
			efsm.addAction(startAction);
			startAction.setArtificial();

			// Creates an edge between proccess state and initial state: done
			// action
			Action done = new Action("done");
			efsm.addEdge(procState, startState);
			T1 = efsm.getAllEdges(procState, startState);
			t = T1.iterator().next();
			t.setFrom(procState.getName());
			t.setTo(startState.getName());
			t.addActionTag("done");
			t.addAction(done);
			efsm.addAction(done);
			done.setArtificial();

			Set<PriorityChain> priorities = efsm.getPriorities();// new
																	// HashSet<PriorityChain>();

			// Creates one action per each action of actor and sets priorities
			int numActions = actionList.getLength();
			for (int i = numActions; i > 0; i--) {
				efsm.addEdge(procState, procState);
			}
			Set<Transition> actionTransitions = efsm.getAllEdges(procState,
					procState);
			int i = 0;
			boolean needsCreatePriority = priorities.size() == 0;
			PriorityChain priority = needsCreatePriority ? new PriorityChain()
					: priorities.iterator().next();
			for (Transition transition : actionTransitions) {
				transition.setFrom(procStateName);
				transition.setTo(procStateName);
				Element actionEle = (Element) actionList.item(i);
				String qid = getActionQID(actionEle);
				if (qid == null) {
					qid = "act" + i;
				}
				Action action = new Action(qid);
				transition.addActionTag(qid);
				efsm.addAction(action);
				mapTransitionToActions(transition, actionTable);
				if (needsCreatePriority)
					priority.put(qid, 0);
				i++;
			}
			// adds priority for done action
			priority.put(done.getQID(), Integer.MAX_VALUE);
			priorities.add(priority);

			efsm.setPriorities(priorities);
		}
		// only one schedule element is allowed in a calml file
		else if (scheduleList.getLength() == 1) {

			Element schedule = (Element) (scheduleList.item(0));

			String initState = schedule.getAttribute("initial-state");
			// System.err.println("Initial state =" + initState);
			// no need of this, already calculated
			transitionList = schedule.getElementsByTagName("Transition");

			if (transitionList != null && transitionList.getLength() > 0) {
				for (int i = 0; i < transitionList.getLength(); i++) {

					Element transEle = (Element) transitionList.item(i);
					String from = transEle.getAttribute("from");
					String to = transEle.getAttribute("to");

					State fromState = new State(from);
					State toState = new State(to);
					// check whether the states are in the graph already
					// if present then fromInGraph = already formed state
					// otherwise fromInGraph = null
					State fromInGraph = efsm.getState(fromState);
					State toInGraph = efsm.getState(toState);
					fromState = (fromInGraph == null) ? fromState : fromInGraph;
					toState = (toInGraph == null) ? toState : toInGraph;

					efsm.addVertex(fromState);
					efsm.addVertex(toState);

					efsm.addEdge(fromState, toState);

					if (fromInGraph == null && initState.equals(from)) {
						fromState.setToInitialState();
						efsm.setInitialState(fromState);
					}

					// set up the transitions
					Set<Transition> TS = efsm.getAllEdges(fromState, toState);
					loop: for (Transition T : TS) {
						if (T.getFrom() != null)
							continue;
						T.setFrom(from);
						T.setTo(to);
						// add the action tags i.e QIDs of the transEle to T
						String qid = getActionQID(transEle);
						if (!existsQID(qid)) {
							ArrayList<String> qids = getActionQIDStartsWith(qid);
							qid = qids.size() > 0 ? qids.get(0) : qid;
							if (qids.size() > 1) {
								T.addActionTag(qids.get(0));
								mapTransitionToActions(T, actionTable);
								for (int t = 1; t < qids.size(); t++) {
									Transition newT = new Transition();
									newT.setFrom(from);
									newT.setTo(to);
									newT.addActionTag(qids.get(t));
									efsm.addEdge(fromState, toState, newT);
									mapTransitionToActions(newT, actionTable);
								}
								break loop;
							}
						}
						T.addActionTag(qid);
						// for each transition element, find the corresponding
						// actions
						mapTransitionToActions(T, actionTable);

						// System.out.println(T.toString() + "\t"
						// + T.actions.toString());
					}
				}
			}

		} else {
			throw new CALMLParsingException("Number of schedules is "
					+ scheduleList.getLength() + " > 1");
		}

	}

	/**
	 * Gets the actions corresponding to the actionTags of <code>trans</code>
	 * and adds them to the <code>actions</code> of <code>trans</code>.
	 * 
	 * @param trans
	 *            Transition to which actions are added
	 * @param actionTable
	 *            Table associating action tags with actions
	 */
	private static void mapTransitionToActions(Transition trans,
			HashMap<String, Action> actionTable) {
		if (trans.getActionTags() == null || actionTable == null)
			return; // silent

		for (String TTag : trans.getActionTags()) {

			trans.addAction(actionTable.get(TTag));

		}

	}

	private static HashMap<String, Action> setActionTable(NodeList actionList)
			throws UnhandledCaseException {

		HashMap<String, Action> table = new HashMap<String, Action>();
		Set<Action> actions = new HashSet<Action>();
		if (actionList == null)
			return table;

		// System.out.println("Action Table:");
		for (int i = 0; i < actionList.getLength(); i++) {
			Element actionEle = (Element) actionList.item(i);
			Action action = new Action();
			// get the QID
			setActionQID(action, actionEle);
			if (action.getQID() == null) {
				action.setQID("act" + i);
			}
			setActionGuards(action, actionEle);
			// System.out.println("\tAction: "+ action.getQID());
			// System.out.println("\t\tGuards: " + action.getGuards());
			setActionBody(action, actionEle);
			// System.out.println("\t\tBody:");
			// for(Stmt stmt : action.getBody()){
			// System.out.println("\t\t\t"+stmt);
			// }
			// System.out.println();
			// System.out.println("inserting into actiontable");
			table.put(action.getQID(), action);
			actions.add(action);
			// get the Input and Output
			NodeList InputList = actionEle.getElementsByTagName("Input");
			NodeList OutputList = actionEle.getElementsByTagName("Output");

			if (InputList != null) {
				for (int j = 0; j < InputList.getLength(); j++) {
					Element inputEle = (Element) InputList.item(j);
					String portName = inputEle.getAttribute("port");
					action.addInputPort(portName);
				}
			}
			if (OutputList != null) {
				for (int j = 0; j < OutputList.getLength(); j++) {
					Element outputEle = (Element) OutputList.item(j);
					String portName = outputEle.getAttribute("port");
					action.addOutputPort(portName);
				}
			}
			// System.out.println(action.QID + "--" + action.inputPorts + "--"
			// + action.outputPorts);
		}
		efsm.setActionTable(table);
		efsm.setActions(actions);
		return table;
	}

	// TODO: use getActionQID to implement this method
	private static void setActionQID(Action action, Element actionEle) {
		NodeList QIDList = actionEle.getElementsByTagName("QID");
		String QID = null;
		if (QIDList != null) {

			Element QIDEle;
			if (QIDList.getLength() > 0) {
				QIDEle = (Element) QIDList.item(0);
				QID = QIDEle.getAttribute("name");
				action.setQID(QID);
			}
			// add the QID to transition

		}
	}

	private static void setActionGuards(Action action, Element actionEle)
			throws UnhandledCaseException {
		NodeList GuardsList = actionEle.getElementsByTagName("Guards");
		if (GuardsList != null && GuardsList.getLength() == 1) {
			Element guardsEle = (Element) GuardsList.item(0);
			Vector<Node> guards = Util.removeTextNodes(guardsEle
					.getChildNodes());
			for (Node node : guards) {
				Element guardExprEle = (Element) node;
				Expr guard = new Expr(guardExprEle);
				action.addGuard(guard);
			}
		}
	}

	// TODO : complex statements, which contain statements within them possible?
	private static void setActionBody(Action action, Element actionEle)
			throws UnhandledCaseException {
		NodeList chT = actionEle.getChildNodes();
		Vector<Node> StmtList = new Vector<Node>();
		for (int i = 0; i < chT.getLength(); i++) {
			if (chT.item(i).getNodeName().equals("Stmt"))
				StmtList.add(chT.item(i));
		}

		for (Node stmtNode : StmtList) {
			Element stmtEle = (Element) stmtNode;
			Stmt stmt = new Stmt(stmtEle);
			action.addStmtToBody(stmt);
		}
	}

	private static boolean existsQID(String qid) {
		for (Action action : efsm.getActions()) {
			if (action.getQID().equals(qid))
				return true;
		}
		return false;
	}

	private static ArrayList<String> getActionQIDStartsWith(String qid) {
		ArrayList<String> actions = new ArrayList<String>();
		for (Action action : efsm.getActions()) {
			if (action.getQID().startsWith(qid))
				actions.add(action.getQID());
		}
		return actions;
	}

	private static void setPriorities(Element root) {

		Set<PriorityChain> priorities = new HashSet<PriorityChain>();
		NodeList priList = root.getElementsByTagName("Priority");
		int numPrioritiyChains = priList.getLength();
		for (int i = 0; i < numPrioritiyChains; i++) {
			Element priEle = (Element) priList.item(i);
			NodeList chain = priEle.getElementsByTagName("QID");
			PriorityChain priority = new PriorityChain();
			int chainLen = chain.getLength();
			for (int rank = 0; rank < chainLen; rank++) {
				Element qidEle = (Element) chain.item(rank);
				String qid = qidEle.getAttribute("name");
				if (existsQID(qid))
					priority.put(qid, rank);
				else {
					ArrayList<String> qids = getActionQIDStartsWith(qid);
					for (int quidIndex = 0; quidIndex < qids.size(); quidIndex++)
						priority.put(qids.get(quidIndex), rank);
				}
			}
			priorities.add(priority);
		}
		efsm.setPriorities(priorities);
	}

	/**
	 * extracts the QID from the element actionEle
	 * 
	 * @param actionEle
	 *            the element from which the QID should be extracted
	 * @return name of the QID if a QID child exists for the actionEle, null
	 *         otherwise. If there are multiple QIDs, then the first one is
	 *         returned.
	 */
	private static String getActionQID(Element actionEle) {
		NodeList QIDList = actionEle.getElementsByTagName("QID");
		String QID = null;
		if (QIDList != null) {

			Element QIDEle;
			if (QIDList.getLength() > 0) {
				QIDEle = (Element) QIDList.item(0);
				QID = QIDEle.getAttribute("name");
			}
		}
		return QID;
	}

	private static void printEfsm() {
		// System.out.println(efsm.toString());
	}

	private static void setName(Element docRoot) {
		efsm.setEFSMName(docRoot.getAttribute("name"));
	}

	private static void setPorts(Element docRoot) {
		NodeList portList = docRoot.getElementsByTagName("Port");
		Set<Port> inputPortSet = new HashSet<Port>();
		Set<Port> outputPortSet = new HashSet<Port>();

		for (int i = 0; i < portList.getLength(); i++) {
			Element portEle = (Element) portList.item(i);
			String portKind = portEle.getAttribute("kind");
			String portName = portEle.getAttribute("name");

			PortType portType = PortType.getType(portKind);
			Port port = new Port(efsm.getEFSMName(), portName, portType, -1);
			port.setNetworkPath(efsm.getNetworkPath());
			if (portType == PortType.Input)
				inputPortSet.add(port);
			else
				outputPortSet.add(port);
		}
		efsm.setInputPorts(inputPortSet);
		efsm.setOutputPorts(outputPortSet);
	}

	/**
	 * finds the variables which are declared in the tree rooted docRoot
	 * 
	 * @param docRoot
	 */
	private static void setDeclaredVars(Element docRoot) {
		NodeList decList = docRoot.getElementsByTagName("Decl");
		Set<String> declVars = new HashSet<String>();
		HashMap<String, Variable> decTable = new HashMap<String, Variable>();
		int numDecls = decList.getLength();
		for (int i = 0; i < numDecls; i++) {

			Element decEle = (Element) decList.item(i);
			decEle.getElementsByTagName("Expr");
			String assignable = decEle.getAttribute("assignable");
			String kind = decEle.getAttribute("kind");
			String value = decEle.getAttribute("value");
			if (assignable != null && kind != null && assignable.equals("Yes")
					&& kind.equals("Variable")) {

				String varName = decEle.getAttribute("name");
				declVars.add(varName);
				// get the initial value

				int initValue = value != null && !value.equals("") ? Integer
						.parseInt(value) : 0;
				NodeList ch = decEle.getChildNodes();

				Element initExprEle = null;
				for (int j = 0; j < ch.getLength(); j++) {
					if (ch.item(j).getNodeName().equals("Expr"))
						initExprEle = (Element) ch.item(j);
				}
				// If the value is an expresion(i.e. an Unary operation like
				// negative values)
				if (initExprEle != null) {
					try {
						if (initExprEle.getAttribute("kind").equals("UnaryOp")) {
							NodeList exprChildNodes = initExprEle
									.getChildNodes();
							String operation = null, exprValue = null;
							for (int j = 0; j < exprChildNodes.getLength(); j++) {
								if (operation != null && exprValue != null)
									break;
								String nodeName = exprChildNodes.item(j)
										.getNodeName();
								if (nodeName.equals("Op"))
									operation = ((Element) exprChildNodes
											.item(j)).getAttribute("name");
								else if (nodeName.equals("Expr"))
									exprValue = ((Element) exprChildNodes
											.item(j)).getAttribute("value");
							}
							if (operation != null && exprValue != null)
								initValue = (operation.equals("-") ? -1 : 1)
										* Integer.parseInt(exprValue);
						}
						// In other case, the value should be into the node's
						// field "value"
						else
							initValue = Integer.parseInt(initExprEle
									.getAttribute("value"));
					} catch (NumberFormatException e) {
						// do nothing
					}
				}
				decTable.put(varName, new Variable(varName, VarType.INT,
						initValue));
			}

		}
		efsm.declaredVariables = declVars;
		efsm.decTable = decTable;
		// System.out.println("Declared Variables: ");
		/*
		 * Iterator it = decTable.keySet().iterator(); while(it.hasNext()){
		 * Object key = it.next(); Object value = decTable.get(key);
		 * System.out.println("\t" + value.toString()); } System.out.println();
		 */
	}
}
