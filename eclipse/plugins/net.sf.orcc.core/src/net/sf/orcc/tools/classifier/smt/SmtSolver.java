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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

/**
 * This class defines an interface to an SMT solver compatible with SMT-LIB v2.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SmtSolver {

	private Actor actor;

	private Map<String, Expression> assertions;

	private IFolder output;

	private boolean satisfied;

	private String solver;

	private List<String> options;

	/**
	 * Creates a new solver. The solver writes SMT-LIB files that are given the
	 * name of the actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	public SmtSolver(Actor actor) {
		assertions = new HashMap<String, Expression>();

		this.actor = actor;
		IFile file = actor.getFile();
		output = OrccUtil.getOutputFolder(file.getProject());

		String solverPath = getDefault().getPreference(P_SOLVER, "");
		if (!solverPath.isEmpty()) {
			File solverFile = new File(solverPath);
			if (solverFile.exists()) {
				solver = solverPath;
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
	 * Checks if the given scripts are satisfiable.
	 * 
	 * @param scripts
	 * @return
	 */
	public boolean checkSat(SmtScript script) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bos);
			ps.println("(set-logic QF_AUFBV)");
			for (String command : script.getCommands()) {
				ps.println(command);
			}

			ps.close();

			IFile file = output.getFile(actor.getSimpleName() + ".smt2");
			InputStream source = new ByteArrayInputStream(bos.toByteArray());
			OrccUtil.setFileContents(file, source);

			launchSolver(file);
		} catch (Exception e) {
			throw new OrccRuntimeException("could not execute solver", e);
		}

		return satisfied;
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
	 * Launch the solver on the given file.
	 * 
	 * @param file
	 *            a file
	 * @throws IOException
	 */
	private void launchSolver(IFile file) throws IOException {
		List<String> allOptions = new ArrayList<String>(options.size() + 2);
		allOptions.add(solver);
		allOptions.addAll(options);
		allOptions.add(file.getLocation().toOSString());

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
						System.err.println(line);
						line = reader.readLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
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

		// update satisfied and assertions fields
		satisfied = processor.isSatisfied();
		if (satisfied) {
			assertions.putAll(processor.getAssertions());
		}
	}
}
