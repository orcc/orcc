package net.sf.orcc.tools.merger.actor;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.BinOpSeqParser;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.DomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class provides a way to import guards from XML files.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class GuardParser {

	private String definitionFile;

	private Procedure scheduler;

	private Pattern peekPattern;

	private Actor superActor;

	private int peekCount;

	private List<Port> peekPorts;

	private List<Var> peekVariables;

	/**
	 * This class defines a parser of XDF expressions.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ExprParser {

		/**
		 * Parses the given node as an expression and returns the matching
		 * Expression expression.
		 * 
		 * @param node
		 *            a node whose expected to be, or whose sibling is expected
		 *            to be, a DOM element named "Expr".
		 * @return an expression
		 */
		public Expression parseExpr(Node node) {
			ParseContinuation<Expression> cont = parseExprCont(node);
			Expression expr = cont.getResult();
			if (expr == null) {
				throw new OrccRuntimeException("Expected an Expr element");
			} else {
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
						Expression unaryExpr = parseExpr(cont.getNode());
						expr = IrFactory.eINSTANCE.createExprUnary(op,
								unaryExpr, null);
						break;
					} else if (kind.equals("Var")) {
						String name = elt.getAttribute("name");
						// look up variable, in variables scope, and if not
						// found in parameters scope
						Var var = superActor.getStateVar(name);
						if (var == null) {
							var = findPortVariable(name);
						}

						if (var == null) {
							throw new OrccRuntimeException("In superactor \""
									+ superActor.getName()
									+ "\": unknown variable: \"" + name + "\"");
						}
						expr = IrFactory.eINSTANCE.createExprVar(var);
						break;
					} else {
						throw new OrccRuntimeException("In network \""
								+ superActor.getName()
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
					exprs.add(parseExpr(node));
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

	public GuardParser(String definitionFile, String actionName,
			Actor superactor) {
		this.definitionFile = definitionFile;
		this.superActor = superactor;
		this.scheduler = eINSTANCE.createProcedure("isSchedulable_"
				+ actionName, 0, eINSTANCE.createTypeBool());
		this.peekPattern = DfFactory.eINSTANCE.createPattern();
		peekVariables = new ArrayList<Var>();
		peekPorts = new ArrayList<Port>();
		peekCount = 0;
	}

	public void parse(String superactor, String superaction) {
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document, superactor, superaction);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Procedure getGuard() {
		return scheduler;
	}

	public Pattern getPeekPattern() {
		return peekPattern;
	}

	private void parseSuperactorList(Document doc, String superactor,
			String superaction) {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (node.getNodeName().equals("superactor")
						&& (element.getAttribute("name").equals(superactor))) {
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
				if (node.getNodeName().equals("superaction")
						&& (elt.getAttribute("name").equals(superaction))) {
					Expression comparison = parseSuperaction(elt);
					createPeekInstructions();
					scheduler.getLast().add(
							IrFactory.eINSTANCE.createInstReturn(comparison));
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}

	private void createPeekInstructions() {
		for (int i = 0; i < peekCount; i++) {
			scheduler.getLast().add(
					createPeekInstruction(peekVariables.get(i),
							peekPorts.get(i)));
		}
	}

	private Instruction createPeekInstruction(Var target, Port port) {
		return IrFactory.eINSTANCE.createInstLoad(target, IrFactory.eINSTANCE
				.createVar(0,
						IrFactory.eINSTANCE.createTypeList(1, port.getType()),
						"tokens_" + port.getName(), true, 0),
				createPeekIndex(port));
	}

	private List<Expression> createPeekIndex(Port port) {
		Var sizeVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String("SIZE_" + port.getName()), true, 0);
		Var indexVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String("index_" + port.getName()), true, 0);
		List<Expression> indexExpression = new ArrayList<Expression>(1);
		indexExpression.add(eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(indexVar), OpBinary.MOD,
				IrFactory.eINSTANCE.createExprVar(sizeVar), port.getType()));
		return indexExpression;
	}

	private Expression parseSuperaction(Element element) {
		Expression guard = null;
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elt = (Element) node;
				if (node.getNodeName().equals("guard")) {
					guard = parseGuard(elt);
				}
			}
			node = node.getNextSibling();
		}
		if (guard == null) {
			guard = IrFactory.eINSTANCE.createExprBool(true);
		}
		return guard;
	}

	private Expression parseGuard(Element element) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				return new ExprParser().parseExpr(node);
			}

			node = node.getNextSibling();
		}
		return null;
	}

	private Var findPortVariable(String name) {
		for (Port port : superActor.getInputs()) {
			if (port.getName().equals(name)) {
				return addToPeekList(port);
			}
		}
		return null;
	}

	private Var addToPeekList(Port port) {
		createVariableIntroduction(port);
		Var peekVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String(port.getName() + "_peek"), true, 0);
		peekVariables.add(peekVar);
		peekCount++;
		peekPorts.add(port);
		peekPattern.setNumTokens(port, 1);
		peekPattern.setVariable(port, peekVar);
		return peekVar;
	}

	private void createVariableIntroduction(Port port) {
		scheduler.newTempLocalVariable(IrFactory.eINSTANCE.createTypeInt(32),
				port.getName() + "_peek");
	}

}
