package net.sf.orcc.tools.classifier.smt;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.util.sexp.SExp;
import net.sf.orcc.util.sexp.SExpList;
import net.sf.orcc.util.sexp.SExpParser;
import net.sf.orcc.util.sexp.SExpSymbol;

/**
 * This class implements a runnable that processes the output of the SMT solver.
 * 
 * @author Matthieu Wwipliez
 * 
 */
public class SmtSolverOutputProcessor implements Runnable {

	private Map<String, Expression> assertions;

	private BufferedReader reader;

	private boolean satisfied;

	public SmtSolverOutputProcessor(InputStream in) {
		assertions = new HashMap<String, Expression>();
		reader = new BufferedReader(new InputStreamReader(in));
	}

	/**
	 * Returns the assertions as a map between variable names and associated
	 * values.
	 * 
	 * @return the assertions as a map between variable names and associated
	 *         values
	 */
	public Map<String, Expression> getAssertions() {
		return assertions;
	}

	/**
	 * Returns <code>true</code> if the assertions are satisfied.
	 * 
	 * @return <code>true</code> if the assertions are satisfied
	 */
	public boolean isSatisfied() {
		return satisfied;
	}

	/**
	 * Parse the assertion encoded as the given list s-expression.
	 * 
	 * @param list
	 *            a list s-expression
	 */
	private void parseAssertion(SExpList list) {
		if (list.size() == 2) {
			SExp exp = list.get(1);
			if (exp.isSymbol()) {
				// a single value => associate a symbol with "true"
				String name = ((SExpSymbol) exp).getContents();
				assertions.put(name, eINSTANCE.createExprBool(true));
			} else if (exp.isList()) {
				SExpList expList = (SExpList) exp;
				if (expList.size() == 2
						&& expList.startsWith(new SExpSymbol("not"))) {
					// unary not
					String name = expList.getSymbol(1).getContents();
					assertions.put(name, eINSTANCE.createExprBool(false));
				} else if (expList.size() == 3
						&& expList.startsWith(new SExpSymbol("="))) {
					// binary equals
					String name = expList.getSymbol(1).getContents();
					int value = parseValue(expList.get(2));
					assertions.put(name, eINSTANCE.createExprInt(value));
				}
			}
		}
	}

	private int parseValue(SExp sexp) {
		if (sexp.isSymbol()) {
			String contents = ((SExpSymbol) sexp).getContents();
			return Integer.parseInt(contents);
		} else if (sexp.isList()) {
			SExpList list = (SExpList) sexp;
			if (list.startsWith(new SExpSymbol("_"))) {
				SExpSymbol bv = list.getSymbol(1);
				String contents = bv.getContents().substring(2);
				BigInteger value = new BigInteger(contents);
				if (value.compareTo(BigInteger.valueOf(2147483647)) > 0) {
					value = value.subtract(BigInteger.valueOf(4294967296L));
				}
				
				return value.intValue();
			}
		}

		return 0;
	}

	/**
	 * Parses expressions with the form <code>(assert expr)</code>
	 * 
	 * @throws IOException
	 */
	private void parseAssertions() throws IOException {
		String line = reader.readLine();
		while (line != null) {
			SExpParser parser = new SExpParser(line);
			SExp exp = parser.read();
			if (exp != null && exp.isList()) {
				SExpList list = (SExpList) exp;
				if (list.startsWith(new SExpSymbol("assert"))) {
					parseAssertion(list);
				}
			}

			line = reader.readLine();
		}
	}

	@Override
	public void run() {
		try {
			String line = reader.readLine();
			while (line != null) {
				SExpParser parser = new SExpParser(line);
				SExp exp = parser.read();
				if (exp != null && exp.isSymbol()) {
					SExpSymbol symbol = (SExpSymbol) exp;
					satisfied = "sat".equals(symbol.getContents());

					// parse assertions (if there are any)
					if (satisfied) {
						parseAssertions();
						return;
					}
				}

				line = reader.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}

}