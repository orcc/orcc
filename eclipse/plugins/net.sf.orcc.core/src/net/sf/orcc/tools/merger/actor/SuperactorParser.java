package net.sf.orcc.tools.merger.actor;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.util.BinOpSeqParser;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class parses a given superactor definition in an XML file
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

	private List<Action> guardList;

	/**
	 * This class defines a parser of XDF expressions.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ExprParser {

		private Action thisGuard;
		/**
		 * Parses the given node as an expression and returns the matching
		 * Expression expression.
		 * 
		 * @param node
		 *            a node whose expected to be, or whose sibling is expected
		 *            to be, a DOM element named "Expr".
		 * @return an expression
		 */
		public Expression parseExpr(Action thisGuard, Node node) {
			this.thisGuard = thisGuard;
			ParseContinuation<Expression> cont = parseExprCont(node);
			Expression expr = cont.getResult();
			if (expr == null) {
				throw new OrccRuntimeException("Expected an Expr element");
			} else {
				if(expr.isExprBinary()) {
					((ExprBinary)expr).setType(IrFactory.eINSTANCE.createTypeBool());
				}
				return expr;
			}
		}

		/**
		 * Parses the given node as a binary operator and returns a parse
		 * continuation with the operator parsed.
		 * 
		 * @param node
		 *            a node that is expected, or whose sibling is expected, to
		 *            be a DOM element named "Op".
		 * @return a parse continuation with the operator parsed
		 */
		private ParseContinuation<OpBinary> parseExprBinaryOp(Node node) {
			while (node != null) {
				if (node.getNodeName().equals("Op")) {
					Element op = (Element) node;
					String name = op.getAttribute("name");
					return new ParseContinuation<OpBinary>(node,
							OpBinary.getOperator(name));
				}

				node = node.getNextSibling();
			}

			return new ParseContinuation<OpBinary>(node, null);
		}

		/**
		 * Parses the given node and its siblings as a sequence of binary
		 * operations, aka "BinOpSeq". A BinOpSeq is a sequence of expr, op,
		 * expr, op, expr...
		 * 
		 * @param node
		 *            the first child node of a Expr kind="BinOpSeq" element
		 * @return a parse continuation with a BinaryExpr
		 */
		private ParseContinuation<Expression> parseExprBinOpSeq(Node node) {
			List<Expression> expressions = new ArrayList<Expression>();
			List<OpBinary> operators = new ArrayList<OpBinary>();

			ParseContinuation<Expression> contE = parseExprCont(node);
			expressions.add(contE.getResult());

			while (node != null) {
				ParseContinuation<OpBinary> contO = parseExprBinaryOp(node);
				OpBinary op = contO.getResult();
				node = contO.getNode();
				if (op != null) {
					operators.add(op);

					contE = parseExprCont(node);
					Expression expr = contE.getResult();
					if (expr == null) {
						throw new OrccRuntimeException(
								"Expected an Expr element");
					}

					expressions.add(expr);
					node = contE.getNode();
				}
			}

			Expression expr = BinOpSeqParser.parse(expressions, operators);
			return new ParseContinuation<Expression>(node, expr);
		}

		/**
		 * Parses the given node as an expression and returns the matching
		 * Expression expression.
		 * 
		 * @param node
		 *            a node whose sibling is expected to be a DOM element named
		 *            "Expr".
		 * @return an expression
		 */
		private ParseContinuation<Expression> parseExprCont(Node node) {
			Expression expr = null;
			while (node != null) {
				if (node.getNodeName().equals("Expr")) {
					Element elt = (Element) node;
					String kind = elt.getAttribute("kind");
					if (kind.equals("BinOpSeq")) {
						return parseExprBinOpSeq(elt.getFirstChild());
					} else if (kind.equals("Literal")) {
						expr = parseExprLiteral(elt);
						break;
					} else if (kind.equals("List")) {
						List<Expression> exprs = parseExprs(node
								.getFirstChild());
						expr = IrFactory.eINSTANCE.createExprList(exprs);
						break;
					} else if (kind.equals("UnaryOp")) {
						ParseContinuation<OpUnary> cont = parseExprUnaryOp(node
								.getFirstChild());
						OpUnary op = cont.getResult();
						Expression unaryExpr = parseExpr(thisGuard, cont.getNode());
						expr = IrFactory.eINSTANCE.createExprUnary(op,
								unaryExpr, null);
						break;
					} else if (kind.equals("Var")) {
						String name = elt.getAttribute("name");
						// look up variable, in variables scope, and if not
						// found in parameters scope
						Var var = findStateVar(network, name, thisGuard);
						if (var == null) {
							var = findPortVariable(network, name, thisGuard);
						}

						if (var == null) {
							throw new OrccRuntimeException("In superactor \""
									+ network.getName()
									+ "\": unknown variable: \"" + name + "\"");
						}
						expr = IrFactory.eINSTANCE.createExprVar(var);
						break;
					} else {
						throw new OrccRuntimeException("In network \""
								+ network.getName()
								+ "\": Unsupported Expr kind: \"" + kind + "\"");
					}
				}

				node = node.getNextSibling();
			}

			return new ParseContinuation<Expression>(node, expr);
		}

		/**
		 * Parses the given "Expr" element as a literal and returns the matching
		 * Expression expression.
		 * 
		 * @param elt
		 *            a DOM element named "Expr"
		 * @return an expression
		 */
		private Expression parseExprLiteral(Element elt) {
			String kind = elt.getAttribute("literal-kind");
			String value = elt.getAttribute("value");
			if (kind.equals("Boolean")) {
				return IrFactory.eINSTANCE.createExprBool(Boolean
						.parseBoolean(value));
			} else if (kind.equals("Character")) {
				throw new OrccRuntimeException("Characters not supported yet");
			} else if (kind.equals("Integer")) {
				return IrFactory.eINSTANCE.createExprInt(Long.parseLong(value));
			} else if (kind.equals("Real")) {
				return IrFactory.eINSTANCE.createExprFloat(Float
						.parseFloat(value));
			} else if (kind.equals("String")) {
				return IrFactory.eINSTANCE.createExprString(value);
			} else {
				throw new OrccRuntimeException("Unsupported Expr "
						+ "literal kind: \"" + kind + "\"");
			}
		}

		private List<Expression> parseExprs(Node node) {
			List<Expression> exprs = new ArrayList<Expression>();
			while (node != null) {
				if (node.getNodeName().equals("Expr")) {
					exprs.add(parseExpr(thisGuard, node));
				}

				node = node.getNextSibling();
			}

			return exprs;
		}

		/**
		 * Parses the given node as a unary operator and returns a parse
		 * continuation with the operator parsed.
		 * 
		 * @param node
		 *            a node that is expected, or whose sibling is expected, to
		 *            be a DOM element named "Op".
		 * @return a parse continuation with the operator parsed
		 */
		private ParseContinuation<OpUnary> parseExprUnaryOp(Node node) {
			while (node != null) {
				if (node.getNodeName().equals("Op")) {
					Element op = (Element) node;
					String name = op.getAttribute("name");
					return new ParseContinuation<OpUnary>(node,
							OpUnary.getOperator(name));
				}

				node = node.getNextSibling();
			}

			throw new OrccRuntimeException("Expected an Op element");
		}

	}

	/**
	 * This class defines a parse continuation, by storing the next node that
	 * shall be parsed along with the result already computed.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private static class ParseContinuation<T> {

		final private Node node;

		final private T result;

		/**
		 * Creates a new parse continuation with the given DOM node and result.
		 * The constructor stores the next sibling of node.
		 * 
		 * @param node
		 *            a node that will be used to resume parsing after the
		 *            result has been stored
		 * @param result
		 *            the result
		 */
		public ParseContinuation(Node node, T result) {
			if (node == null) {
				this.node = null;
			} else {
				this.node = node.getNextSibling();
			}
			this.result = result;
		}

		/**
		 * Returns the node stored in this continuation.
		 * 
		 * @return the node stored in this continuation
		 */
		public Node getNode() {
			return node;
		}

		/**
		 * Returns the result stored in this continuation.
		 * 
		 * @return the result stored in this continuation
		 */
		public T getResult() {
			return result;
		}

	}
	
	public SuperactorParser(Network network, Actor superActor) {
		this.network = network;
		this.superActor = superActor;
		this.scheduleList = new ArrayList<Schedule>();
		this.fsm = DfFactory.eINSTANCE.createFSM();
		this.guardList = new ArrayList<Action>();
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

	public List<Action> getGuardList() {
		return guardList;
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
					Action guard = DfFactory.eINSTANCE.createAction();
					guard.setBody(eINSTANCE.createProcedure("isSchedulable_"
							+ elt.getAttribute("name"), 0, eINSTANCE.createTypeBool()));
					guard.setPeekPattern(DfFactory.eINSTANCE.createPattern());
					Schedule schedule = new Schedule();
					schedule.setName(elt.getAttribute("name"));
					schedule.setOwner(superactor);
					parseSuperaction(guard, schedule, elt);
					scheduleList.add(schedule);
					guard.addAttribute("actorName");
					guard.setAttribute("actorName", network.getName());
					guard.addAttribute("actionName");
					guard.setAttribute("actionName", elt.getAttribute("name"));
					guardList.add(guard);
				}
				if (elt.getTagName().equals("regfeedback")) {
					superActor.addAttribute("RegisterFeedback");
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private void parseSuperaction(Action guard, Schedule schedule, Element element) {
		Expression guardExpression = null;
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (elt.getTagName().equals("iterand")) {
					Vertex vertex = findActor(elt.getAttribute("actor"));
					if (vertex != null) {
						Actor actor = vertex.getAdapter(Actor.class);
						addActorActionToSchedule(schedule, actor, findAction(actor, elt.getAttribute("action")),
								Integer.parseInt(elt.getAttribute("repetitions")));
					} else {
						OrccLogger.warnln("unknown actor " + elt.getAttribute("actor") + " in schedule");
					}
				}
				if (node.getNodeName().equals("guard")) {
					Expression expr = parseGuard(guard, elt);
					if (expr != null) {
						Var resultVar = guard.getBody().newTempLocalVariable(
								IrFactory.eINSTANCE.createTypeBool(), "result");
						guard.getBody().getLast().add(
								IrFactory.eINSTANCE.createInstAssign(
										resultVar, expr));
						guardExpression = IrFactory.eINSTANCE.createExprVar(resultVar);
					} else {
						guardExpression = null;
					}
					
				}
			}
			node = node.getNextSibling();
		}
		if (guardExpression == null) {
			guardExpression = IrFactory.eINSTANCE.createExprBool(true);
		}
		guard.getBody().getLast().add(
				IrFactory.eINSTANCE.createInstReturn(guardExpression));
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
		for(Action action : actor.getInitializes()) {
			if (action.getName().equals(id)) {
				return action;
			}
		}							
		OrccLogger.warnln("action " + id + " not found in actor " + actor.getName());
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
			throw new OrccRuntimeException("Trying to set " + initialState + 
					" as initial state, but the state does not exist");
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
	
	/*
	 * Guard parser specific methods
	 */

	private Var createIndexVar(Port port) {
		return IrFactory.eINSTANCE.createVar(0,
				EcoreUtil.copy(port.getType()),
				new String("index_" + port.getName()), true, 0);
	}

	private Instruction createPeekInstruction(Var target, Port port) {
		Var indexVar = createIndexVar(port);
		return IrFactory.eINSTANCE.createInstLoad(target, IrFactory.eINSTANCE
				.createVar(0, EcoreUtil.copy(port.getType()),
						"tokens_" + port.getName(), true, 0),
				MergerUtil.createModuloIndex(port, indexVar));
	}
	
	private Expression parseGuard(Action guard, Element element) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				return new ExprParser().parseExpr(guard, node);
			}

			node = node.getNextSibling();
		}
		return null;
	}

	private Var findStateVar(Network network, String name, Action guard) {
		for (Actor actor : network.getAllActors()) {
			for (Var var : actor.getStateVars()) {
				if (var.getName().equals(name)) {
					Var peekVar = guard.getBody().newTempLocalVariable(
							EcoreUtil.copy(var.getType()), name + "_peek");
					guard.getBody().getLast().add(IrFactory.eINSTANCE.createInstLoad(peekVar, var));
					return peekVar;
				}
			}
		}
		return null;
	}
	
	private Var findPortVariable(Network network, String name, Action guard) {
		for (Actor actor : network.getAllActors()) {
			for (Port port : actor.getInputs()) {
				if (port.getName().equals(name)) {
					return addToPeekList(guard, port);
				}
			}
		}
		return null;
	}

	private Var addToPeekList(Action guard, Port port) {
		createVariableIntroduction(guard, port);
		Var peekVar = IrFactory.eINSTANCE.createVar(0,
				EcoreUtil.copy(port.getType()),
				new String(port.getName() + "_peek"), true, 0);
		guard.getBody().getLast().add(
				createPeekInstruction(peekVar, port));
		guard.getPeekPattern().setNumTokens(port, 1);
		guard.getPeekPattern().setVariable(port, peekVar);
		return peekVar;
	}

	private void createVariableIntroduction(Action guard, Port port) {
		guard.getBody().newTempLocalVariable(IrFactory.eINSTANCE.createTypeInt(32),
				port.getName() + "_peek");
	}
}
