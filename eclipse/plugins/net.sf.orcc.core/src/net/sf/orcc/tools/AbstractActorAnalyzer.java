/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.tools;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.WriteListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * This class defines an abstract actor analyzer.
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractActorAnalyzer implements ActorAnalyzer {

	/**
	 * 
	 * @param args
	 */
	public static void main(Class<? extends AbstractActorAnalyzer> clasz,
			String[] args) {
		if (args.length == 3) {
			String inputFile = args[0];
			String vtlFolder = args[1];
			String outputFolder = args[2];

			try {
				AbstractActorAnalyzer analyzer = clasz.newInstance();
				analyzer.setOutputFolder(outputFolder);
				analyzer.analyzeFunctionalUnit(inputFile, vtlFolder);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err.println("Usage: " + clasz.getSimpleName()
					+ " <input functionnal unit> <VTL folder> <output folder>");
		}
	}

	/**
	 * the process that launched this analyzer
	 */
	protected WriteListener listener;

	private IProgressMonitor monitor;

	/**
	 * Analyze the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract public void analyze(Actor actor) throws OrccException;

	@Override
	public void analyzeFunctionalUnit(String inputFile, String vtlFolder)
			throws OrccException {
		listener.writeText("Parsing Actor...\n");

		Actor actor;

		IFile file = null;
		ResourceSet set = new ResourceSetImpl();
		actor = (Actor) IrUtil.deserializeEntity(set, file);

		if (isCanceled()) {
			return;
		}

		doFunctionnalUnitAnalyzer(actor);
	}

	abstract protected void doFunctionnalUnitAnalyzer(Actor actor);

	/**
	 * Returns true if this process has been canceled.
	 * 
	 * @return true if this process has been canceled
	 */
	protected boolean isCanceled() {
		if (monitor == null) {
			return false;
		} else {
			return monitor.isCanceled();
		}
	}

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void setWriteListener(WriteListener listener) {
		this.listener = listener;
	}

	/**
	 * Transforms the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract public void transform(Actor actor) throws OrccException;

}
