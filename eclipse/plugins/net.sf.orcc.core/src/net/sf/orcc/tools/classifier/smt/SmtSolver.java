/*
 * Copyright (c) 2011, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.tools.classifier.smt;

import static net.sf.orcc.OrccActivator.getDefault;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER;
import static net.sf.orcc.preferences.PreferenceConstants.P_SOLVER_OPTIONS;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.sexp.SExp;
import net.sf.orcc.util.sexp.SExpList;
import net.sf.orcc.util.sexp.SExpParser;
import net.sf.orcc.util.sexp.SExpSymbol;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

/**
 * This class defines an interface to an SMT solver compatible with SMT-LIB v2.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SmtSolver {

	static boolean hasFailed;

	private class SmtSolverOutputProcessor implements Runnable {

		private Reader reader;

		public SmtSolverOutputProcessor(InputStream in) {
			reader = new InputStreamReader(in);
		}

		/**
		 * Parse the assertion encoded as the given list s-expression.
		 * 
		 * @param list
		 *            a list s-expression
		 */
		private Object getExpression(SExp exp) {
			if (exp.isSymbol()) {
				SExpSymbol symbol = (SExpSymbol) exp;
				if ("true".equals(symbol.getContents())) {
					return true;
				} else if ("false".equals(symbol.getContents())) {
					return false;
				}
			} else if (exp.isList()) {
				SExpList list = (SExpList) exp;
				if (list.size() == 2) {
					SExpSymbol bv = list.getSymbol(1);
					// remove "bv" from the symbol
					String contents = bv.getContents().substring(2);
					BigInteger value = new BigInteger(contents, 16);
					return value;
				}
			}

			return null;
		}

		@Override
		public void run() {
			hasFailed = false;
			StringBuilder builder = new StringBuilder();
			try {
				char[] cbuf = new char[8192];
				int n = reader.read(cbuf);
				while (n > 0) {
					builder.append(cbuf, 0, n);
					n = reader.read(cbuf);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}

			if (builder.toString().contains("error")) {
				OrccLogger.warnln("Solving of actor " + actor.getName() + ":");
				hasFailed = true;
				String error[] = builder.toString().split("\n");
				for (int i = 0; i < error.length; i++) {
					if (!error[i].equals("sat")) {
						OrccLogger.traceln(error[i]);
					}
				}
				return;
			}

			SExpParser parser = new SExpParser(builder.toString());
			SExp exp = parser.read();
			SExpSymbol symbol = (SExpSymbol) exp;
			satisfied = "sat".equals(symbol.getContents());

			// parse assertions (if there are any)
			if (satisfied && action != null && ports != null) {
				exp = parser.read();
				if (exp != null && exp.isList()) {
					SExpList list = (SExpList) exp;

					Pattern pattern = action.getPeekPattern();
					int index = 0;
					for (Port port : ports) {
						exp = list.get(index);
						index++;

						Object value;
						int numTokens = pattern.getNumTokens(port);
						if (numTokens > 1) {
							List<Object> values = new ArrayList<Object>();
							SExpList portList = (SExpList) exp;
							for (int i = 0; i < numTokens; i++) {
								SExp subExpr = portList.get(i);
								values.add(getExpression(subExpr));
							}
							value = values.toArray();
						} else {
							value = getExpression(exp);
						}
						assertions.put(port.getName(), value);
					}
				}
			}
		}
	}

	private Action action;

	private Actor actor;

	private Map<String, Object> assertions;

	private List<String> options;

	private IFolder output;

	private List<Port> ports;

	private boolean satisfied;

	private String solver;

	/**
	 * Creates a new solver. The solver writes SMT-LIB files that are given the
	 * name of the actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	public SmtSolver(Actor actor) {
		assertions = new HashMap<String, Object>();

		this.actor = actor;
		IFile file = actor.getFile();
		output = OrccUtil.getOutputFolder(file.getProject());

		String solverPath = getDefault().getPreference(P_SOLVER, "");
		solverPath = FilesManager.sanitize(solverPath);
		if (!solverPath.isEmpty()) {
			File solverFile = new File(solverPath);
			if (solverFile.exists()) {
				if(solverFile.canExecute()) {
					solver = solverPath;
				} else {
					throw new OrccRuntimeException("solver " + solverPath
							+ " is not executable");
				}
			} else {
				throw new OrccRuntimeException("solver executable not found!");
			}
		} else {
			throw new OrccRuntimeException("path of solver is empty!");
		}

		String strOptions = getDefault().getPreference(P_SOLVER_OPTIONS, "");
		options = Arrays.asList(strOptions.split(" "));
	}

	/**
	 * Checks if the given script is satisfiable.
	 * 
	 * @param script
	 *            an SMT script
	 * @return <code>true</code> if the given script is satisfiable
	 */
	public boolean checkSat(SmtScript script) {
		return checkSat(script, null, null);
	}

	public boolean hasFailed() {
		return hasFailed;
	}

	/**
	 * Checks if the given script is satisfiable, and retrieve the value of the
	 * tokens present on the control ports.
	 * 
	 * @param script
	 *            an SMT script
	 * @param action
	 *            if present, will get the tokens read on these ports
	 * @param ports
	 *            the list of control ports
	 * @return <code>true</code> if the given script is satisfiable
	 */
	public boolean checkSat(SmtScript script, Action action, List<Port> ports) {
		try {
			this.action = action;
			this.ports = ports;

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bos);
			for (String command : script.getCommands()) {
				ps.println(command);
			}

			if (action != null && ports != null) {
				ps.print("(get-value (");
				Pattern pattern = action.getPeekPattern();
				for (Port port : ports) {
					int numTokens = pattern.getNumTokens(port);
					if (numTokens > 1) {
						ps.print("(");
						for (int i = 0; i < numTokens; i++) {
							ps.print("(select " + port.getName() + " (_ bv" + i
									+ " 32)) ");
						}
						ps.print(")");
					} else {
						ps.print("(select " + port.getName() + " (_ bv0 32)) ");
					}
				}
				ps.println("))");
			}
			ps.close();

			File file = new File(output.getLocation().toOSString(), actor.getSimpleName() + "_"
					+ System.currentTimeMillis() + ".smt2");
			FilesManager.writeFile(bos.toString(), file);
			launchSolver(file);
		} catch (Exception e) {
			throw new OrccRuntimeException("could not execute solver", e);
		}

		return satisfied;
	}

	/**
	 * Returns the assertions as a map between ports and associated values.
	 * 
	 * @return the assertions as a map between ports and associated values
	 */
	public Map<String, Object> getAssertions() {
		return assertions;
	}

	/**
	 * Launch the solver on the given file.
	 * 
	 * @param file
	 *            a file
	 * @throws IOException
	 */
	private void launchSolver(File file) throws IOException {
		List<String> allOptions = new ArrayList<String>(options.size() + 2);
		allOptions.add(solver);
		allOptions.addAll(options);
		allOptions.add(file.getCanonicalPath());

		ProcessBuilder pb = new ProcessBuilder(allOptions);
		final Process process = pb.start();

		// Output error message
		SmtSolverOutputProcessor processor = new SmtSolverOutputProcessor(
				process.getInputStream());

		Thread thErr = new Thread() {
			public void run() {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getErrorStream()));
				try {
					String line = reader.readLine();
					while (line != null) {
						OrccLogger.traceln(line);
						line = reader.readLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thErr.start();

		Thread thread = new Thread(processor);
		thread.start();

		try {
			process.waitFor();

			// wait for all output to be processed
			thread.join();
			thErr.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
