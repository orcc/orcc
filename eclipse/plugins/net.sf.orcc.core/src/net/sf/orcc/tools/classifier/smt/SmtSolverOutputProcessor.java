package net.sf.orcc.tools.classifier.smt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.orcc.util.sexp.SExp;
import net.sf.orcc.util.sexp.SExpAtom;
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

	private InputStream in;

	private boolean satisfied;

	public SmtSolverOutputProcessor(InputStream in) {
		this.in = in;
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
	 * Parses expressions with the form <code>(assert expr)</code>
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private void parseAssertions(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while (line != null) {
			SExpParser parser = new SExpParser(line);
			SExp exp = parser.read();
			if (exp != null && exp.isList()) {
				SExpList list = (SExpList) exp;
				if (!list.getExpressions().isEmpty()) {
					SExp first = list.getExpressions().get(0);
				}
			}

			line = reader.readLine();
		}
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try {
			String line = reader.readLine();
			while (line != null) {
				SExpParser parser = new SExpParser(line);
				SExp exp = parser.read();
				if (exp != null && exp.isAtom() && ((SExpAtom) exp).isSymbol()) {
					SExpSymbol symbol = (SExpSymbol) exp;
					satisfied = "sat".equals(symbol.getContents());

					// parse assertions (if there are any)
					if (satisfied) {
						parseAssertions(reader);
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