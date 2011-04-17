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
package net.sf.orcc.simulators;

import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION;

import java.io.InputStream;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRParser;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.simulators.codegen.ActorCodeGenerator;
import net.sf.orcc.simulators.codegen.NetworkCodeGenerator;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

/**
 * This class defines a fast interpreter that generates Java bytecode and let
 * JDT provide run and debug support.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FastInterpreter extends AbstractSimulator {

	private List<IFolder> binFolders;

	private List<String> vtlFolders;

	private IFile xdfFile;

	private IProject project;

	private void generateActors() {
		write("Parsing actors and generating bytecode...\n");
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			for (IFile file : OrccUtil.getAllFiles(binFolders)) {
				Actor actor = new IRParser().parseActor(file.getContents());
				IFile outputFile = root.getFile(file.getFullPath()
						.removeFileExtension().addFileExtension("class"));

				if (outputFile.exists()) {
					outputFile.delete(true, null);
					// later, use file.getModificationStamp()
				}

				InputStream codeStream = new ActorCodeGenerator()
						.generate(actor);
				outputFile.create(codeStream, true, null);
			}
		} catch (Exception e) {
			throw new OrccRuntimeException("oops", e);
		}
	}

	private void generateNetwork() throws CoreException, OrccException {
		// parses top network
		write("Parsing XDF network...\n");
		Network network = new XDFParser(xdfFile.getLocation()
				.toPortableString()).parseNetwork();
		network.updateIdentifiers();
		if (isCanceled()) {
			return;
		}

		write("Instantiating actors...\n");
		network.instantiate(vtlFolders);
		Network.clearActorPool();
		write("Instantiation done\n");

		IFolder outputFolder = OrccUtil.getOutputFolder(project);
		// should be IFile outputFile =
		// outputFolder.getFile(xdfFile.getFullPath()
		// .removeFileExtension().addFileExtension("class").lastSegment());
		IFile outputFile = outputFolder
				.getFile("MPEG/MPEG4/part2/Algo_Add.class");
		if (outputFile.exists()) {
			outputFile.delete(true, null);
		}

		// should be outputFile.create(new NetworkCodeGenerator().generate(
		// xdfFile.getFullPath(), network), true, null);

		outputFile.create(new NetworkCodeGenerator().generate(
				xdfFile.getFullPath(), network), true, null);
	}

	@Override
	protected void initializeOptions() {
		String name = getAttribute(PROJECT, "");
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		project = root.getProject(name);
		xdfFile = root.getFileForLocation(new Path(getAttribute(XDF_FILE, "")));

		try {
			binFolders = OrccUtil.getOutputFolders(project);
			vtlFolders = OrccUtil.getOutputPaths(project);

			// set FIFO size
			// this.fifoSize = getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);

			generateActors();
			generateNetwork();
		} catch (CoreException e) {
			throw new OrccRuntimeException("oops", e);
		} catch (OrccException e) {
			throw new OrccRuntimeException("oops", e);
		}
	}

	@Override
	public void start(String mode) {
		write("Launching...");
		try {
			ILaunchManager manager = DebugPlugin.getDefault()
					.getLaunchManager();
			ILaunchConfigurationType type = manager
					.getLaunchConfigurationType(ID_JAVA_APPLICATION);

			ILaunchConfigurationWorkingCopy wc = type.newInstance(null,
					"JDT - " + xdfFile.getName());

			wc.setAttribute(ATTR_PROJECT_NAME, project.getName());

			// should be wc.setAttribute(ATTR_MAIN_TYPE_NAME,
			// xdfFile.getFullPath().removeFileExtension().lastSegment());
			wc.setAttribute(ATTR_MAIN_TYPE_NAME, "MPEG/MPEG4/part2/Algo_Add");
			ILaunchConfiguration config = wc.doSave();
			config.launch(mode, null);
		} catch (CoreException e) {
			throw new OrccRuntimeException("oops", e);
		}
	}

}
