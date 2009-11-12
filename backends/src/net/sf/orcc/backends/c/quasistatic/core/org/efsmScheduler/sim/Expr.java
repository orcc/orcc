/*
 * Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
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
 * THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Util;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.OperatorParsingException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnhandledCaseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Defines an expression which can be unary or binary
 * 
 * @author Victor Martin 5.12.2008
 * 
 */
public class Expr {
	private Arity arity;
	private int value;

	private Operator op;
	private Expr leftTree;
	private Expr rightTree;

	private Set<String> varNames;

	// for application type
	String appName;
	Vector<Expr> args;

	/**
	 * Default Constructor
	 */
	public Expr() {
		varNames = new HashSet<String>();
		arity = Arity.ZERO;
		leftTree = rightTree = null;
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param op
	 * @param left
	 * @param right
	 */
	public Expr(Operator op, Expr left, Expr right) {
		if (op == null) {
			System.err.println("Operator null");
		}
		if (left == null) {
			varNames = new HashSet<String>();
			arity = Arity.ZERO;
			leftTree = rightTree = null;
		}
		if (left != null && right == null) {
			varNames = new HashSet<String>(left.getVarNames());
			arity = Arity.UNARY;
			leftTree = left;
			rightTree = null;
			this.op = op;
		} else {
			varNames = Util.union(left.getVarNames(), right.getVarNames());
			arity = Arity.BINARY;
			leftTree = left;
			rightTree = right;
			this.op = op;
		}
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param exprEle
	 * @throws UnhandledCaseException
	 */
	public Expr(Element exprEle) throws UnhandledCaseException {

		varNames = new HashSet<String>();
		// Literal, Var, Application, BinOpSeq
		String kind = exprEle.getAttribute("kind");
		if (kind.equals("Literal")) {
			arity = Arity.ZERO;
			op = Operator.constOp;
			value = Integer.parseInt(exprEle.getAttribute("value"));
		} else if (kind.equals("Var")) {
			arity = Arity.ZERO;
			op = Operator.varOp;
			varNames.add(exprEle.getAttribute("name"));

		} else if (kind.equals("UnaryOp")) {
			arity = Arity.UNARY;

			NodeList childrenWithText = exprEle.getChildNodes();
			Vector<Node> children = Util.removeTextNodes(childrenWithText);
			Element opEle = (Element) children.get(0);
			try {
				op = Operator.parse(opEle.getAttribute("name"));
				if (op == Operator.minus)
					op = Operator.uminus;
			} catch (OperatorParsingException ope) {
				ope.printStackTrace();
			}

			leftTree = new Expr((Element) children.get(1));
			rightTree = null;
		} else if (kind.equals("BinOpSeq")) {
			// TODO: precedences and associativity
			arity = Arity.BINARY;
			NodeList childrenWithText = exprEle.getChildNodes();
			Vector<Node> children = Util.removeTextNodes(childrenWithText);
			int numChildren = children.size();
			if (numChildren == 3) {
				leftTree = new Expr((Element) children.get(0));
				rightTree = new Expr((Element) children.get(2));
				Element opEle = (Element) children.get(1);
				try {
					op = Operator.parse(opEle.getAttribute("name"));
				} catch (OperatorParsingException ope) {
					ope.printStackTrace();
				}
			}
			if (numChildren == 7) {
				Expr lleft = new Expr((Element) children.get(0));
				Expr lright = new Expr((Element) children.get(2));
				Element lopEle = (Element) children.get(1);
				Operator lop = null;
				try {
					lop = Operator.parse(lopEle.getAttribute("name"));
				} catch (OperatorParsingException ope) {
					ope.printStackTrace();
				}
				leftTree = new Expr(lop, lleft, lright);

				Element opEle = (Element) children.get(3);
				try {
					op = Operator.parse(opEle.getAttribute("name"));
					System.out.println("Operator middle :" + op);
				} catch (OperatorParsingException ope) {
					ope.printStackTrace();
				}

				Expr rleft = new Expr((Element) children.get(4));
				Expr rright = new Expr((Element) children.get(6));
				Element ropEle = (Element) children.get(5);
				Operator rop = null;
				try {
					rop = Operator.parse(ropEle.getAttribute("name"));
				} catch (OperatorParsingException ope) {
					ope.printStackTrace();
				}
				rightTree = new Expr(rop, rleft, rright);

			}
            if(leftTree != null && rightTree != null)
			varNames = Util.union(leftTree.getVarNames(), rightTree
					.getVarNames());

		} else if (kind.equals("Application")) {
			arity = Arity.APPLICATION;
			leftTree = rightTree = null;

			NodeList childrenWithText = exprEle.getChildNodes();
			Vector<Node> children = Util.removeTextNodes(childrenWithText);
			// children size = 2: appName, args
			appName = ((Element) children.get(0)).getAttribute("name");
			if (!appName.equals("bitand")) {
				args = null;
			} else {
				args = new Vector<Expr>();
				Element argsEle = (Element) children.get(1);
				// get args
				NodeList argsWithText = argsEle.getChildNodes();
				Vector<Node> argNodes = Util.removeTextNodes(argsWithText);
				for (Node n : argNodes) {
					Element ele = (Element) n;
					Expr e = new Expr(ele);
					args.add(e);
				}
			}
		} else if (kind.equals("If")) {// TODO : arity for If op = 3
			arity = Arity.APPLICATION;
			leftTree = rightTree = null;
		} else if (kind.equals("Indexer")) {// TODO : ditch this
			arity = Arity.APPLICATION;
			leftTree = rightTree = null;
		} else if (kind.equals("List")) {// TODO : ditch this
			arity = Arity.APPLICATION;
			leftTree = rightTree = null;
		} else {
			throw new UnhandledCaseException("Unknown Expr kind :" + kind);
		}
	}

	/**
	 * Return the variable names
	 * 
	 * @return varNames
	 */
	public Set<String> getVarNames() {
		return varNames;
	}

	/**
	 * Expression to string
	 */
	public String toString() {
		      String str = "";
        switch (arity) {
            case ZERO:
                if (varNames != null) {
                    if (varNames.isEmpty()) {
                        str += value;
                    } else {
                        str += varNames.iterator().next();
                    }
                }
                break;
            case UNARY:
                if (leftTree != null && op != null) {
                    str += op.toString() + "(" + leftTree.toString() + ")";
                }
                break;
            case BINARY:
                if (leftTree != null && rightTree != null && op != null) {
                    str += "(" + leftTree.toString() + op.toString() + rightTree.toString() + ")";
                }
                break;
            case APPLICATION:
                if (appName != null && appName.equals("bitand")) {
                    str += appName + "(" + args.get(0) + "," + args.get(1) + ")";
                }
                break;
            default:
                System.err.println("Wrong arity");
        }
        return str;
	}

	/**
	 * @return Expression�s arity
	 */
	public Arity getArity() {
		return arity;
	}

	/**
	 * @return Expression�s value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return Expression�s operator
	 */
	public Operator getOp() {
		return op;
	}

	/**
	 * @return Expression�s left-site
	 */
	public Expr getLeftTree() {
		return leftTree;
	}

	/**
	 * @return Expression�s right-site
	 */
	public Expr getRightTree() {
		return rightTree;
	}

}

// Arity
enum Arity {
	ZERO, UNARY, BINARY, APPLICATION
}