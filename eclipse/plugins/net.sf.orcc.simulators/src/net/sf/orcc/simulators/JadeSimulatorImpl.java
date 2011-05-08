/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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

import static net.sf.orcc.OrccLaunchConstants.INPUT_STIMULUS;
import static net.sf.orcc.OrccLaunchConstants.REFERENCE_FILE;
import static net.sf.orcc.OrccLaunchConstants.TRACES_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.VTL_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;
import static net.sf.orcc.preferences.PreferenceConstants.P_JADE;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccActivator;
import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.network.serialize.XDFWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * This class implements a simulator with the Just-In-Time Adaptive Decoder
 * Engine (Jade).
 * 
 * @author Jerome Gorin
 * @author Matthieu Wipliez
 * 
 */
public class JadeSimulatorImpl extends AbstractSimulator {

	/**
	 * Path of Jade executable
	 */
	private String execJade;

	/**
	 * Master caller associated process for jade I/O access.
	 */
	private Process jadeProcess;

	/**
	 * Reference video file
	 */
	private String refVideo;

	/**
	 * Input stimulus file name
	 */
	private String stimulusFile;

	/**
	 * Traces folder
	 */
	private String tracesFolder;

	/**
	 * Folder where the VTL is
	 */
	private String vtlFolder;

	/**
	 * input XDF network file name
	 */
	private String xdfFile;

	/**
	 * XDF network flatten file
	 */
	private File xdfFlattenFile;

	private void flatten() {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFile file = root.getFile(new Path(xdfFile));

			Network network = new XDFParser(file).parseNetwork();
			network.flatten();

			XDFWriter writer = new XDFWriter();
			xdfFlattenFile = writer.write(new File(vtlFolder), network);
		} catch (OrccException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initializeOptions() {
		// Get configuration attributes
		stimulusFile = getAttribute(INPUT_STIMULUS, "");
		vtlFolder = getAttribute(VTL_FOLDER, "");
		xdfFile = getAttribute(XDF_FILE, "");
		tracesFolder = getAttribute(TRACES_FOLDER, "");
		refVideo = getAttribute(REFERENCE_FILE, "");
		execJade = new InstanceScope().getNode(OrccActivator.PLUGIN_ID).get(
				P_JADE, "");

		// Jade location has not been set
		if (execJade.equals("")) {
			throw new OrccRuntimeException(
					"Jade path must first be set in window->Preference->Orcc");
		}
	}

	@Override
	public void start(String mode) {
		// Flatten the network
		flatten();

		// Preparing command line
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(execJade);
		cmdList.add("-xdf");
		cmdList.add(xdfFlattenFile.getAbsolutePath());
		cmdList.add("-i");
		cmdList.add(stimulusFile);
		cmdList.add("-L");
		cmdList.add(vtlFolder);

		if (!tracesFolder.equals("")) {
			cmdList.add("-w");
			cmdList.add(tracesFolder);
		}

		if (!refVideo.equals("")) {
			cmdList.add("-o");
			cmdList.add(refVideo);
		}

		String[] cmd = cmdList.toArray(new String[] {});

		// Run application
		Runtime run = Runtime.getRuntime();

		try {
			jadeProcess = run.exec(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Output application message
		new Thread() {
			@Override
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(jadeProcess.getInputStream()));
					try {
						String line = reader.readLine();
						if (line != null) {
							write(line + "\n");
						}
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

		// Output error message
		new Thread() {
			@Override
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(jadeProcess.getErrorStream()));
					try {
						String line = reader.readLine();
						if (line != null) {
							write("Generation error :" + line + "\n");
						}
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

		try {
			jadeProcess.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
