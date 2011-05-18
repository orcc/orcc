package net.sf.orcc.tools.classifier.smt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.orcc.util.sexp.SExprParser;

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

	private void parseLine(String line) {
		SExprParser parser = new SExprParser(line);
		parser.parse();
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try {
			// first find out if the answer is sat or unsat
			String line = reader.readLine();
			while (line != null) {
				if ("sat".equals(line)) {
					satisfied = true;

					// parse assertions (if there are any)
					line = reader.readLine();
					while (line != null) {
						System.out.println(line);
						parseLine(line);
						line = reader.readLine();
					}
					break;
				} else if ("unsat".equals(line)) {
					return;
				} else {
					line = reader.readLine();
				}
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