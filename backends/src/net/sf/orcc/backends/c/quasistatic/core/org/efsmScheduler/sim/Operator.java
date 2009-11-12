/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim;

import java.util.HashMap;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.OperatorParsingException;

/**
 *
 * @author vimartin
 */
/**
 * Possible types of operator
 */
enum Operator {
	constOp, varOp, uminus, notOp, // unary minus
	plus, minus, multOp, divOp, and, or, lt, gt, leq, geq, eq, neq, // < , > ,
																	// <= , >=,
																	// == , !=
																	// respectively
	;
	private static HashMap<String, Operator> stringMap = null;

	/**
	 *
	 * @param str identification of operator
	 * @return Operator
	 * @throws OperatorParsingException
	 */
	public static Operator parse(String str) throws OperatorParsingException {
		//If stringMap is not initialized
		if (stringMap == null) {
			formStringMap();
		}
		//Take the Operator
		Operator op = stringMap.get(str);
		if(op != null)
			return op;
		//If str is not a key of stringMap
		throw new OperatorParsingException(str);

	}

	/**
	 * Fill the stringMap table
	 */
	private static void formStringMap() {
		stringMap = new HashMap<String, Operator>();
		stringMap.put("+", plus);
		stringMap.put("-", minus);
		stringMap.put("*", multOp);
		stringMap.put("/", divOp);

		stringMap.put("and", and);
		stringMap.put("or", or);

		stringMap.put("&lt;", lt);// is this required?
		stringMap.put("<", lt);
		stringMap.put("&lt;=", leq);
		stringMap.put("<=", leq);

		stringMap.put("&gt;", gt);
		stringMap.put(">", gt);
		stringMap.put("&gt;=", geq);
		stringMap.put(">=", geq);
		stringMap.put("!=", neq);
		stringMap.put("=", eq);
		stringMap.put("!", notOp);
		stringMap.put("not", notOp);
	}

	/**
	 * Operator to string
	 */
    @Override
	public String toString() {
		String str = null;
		switch (this) {
		case plus:
			str = "+";
			break;
		case minus:
			str = "-";
			break;
		case multOp:
			str = "*";
			break;
		case divOp:
			str = "/";
			break;

		case and:
			str = "and";
			break;
		case or:
			str = "or";
			break;
		case lt:
			str = "<";
			break;
		case gt:
			str = ">";
			break;
		case leq:
			str = "<=";
			break;
		case geq:
			str = ">=";
			break;
		case eq:
			str = "==";
			break;
		case neq:
			str = "!=";
			break;

		case uminus:
			str = "-";
			break;
		case notOp:
			str = "!";
			break;
		case constOp:
			str = "";
			break;
		case varOp:
			str = "";
			break;
		default:
			System.err.println("Wrong operator " + this);

		}
		return str;
	}
}
