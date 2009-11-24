/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
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
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.sf.orcc.backends.c.quasistatic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.main.Scheduler_Simulator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.output.ConfigFilesCreator;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.FileUtilities;
import net.sf.orcc.backends.c.quasistatic.parsers.CQuasiStaticActorParser;
import net.sf.orcc.backends.c.quasistatic.parsers.CQuasiStaticNetworksParser;
import net.sf.orcc.backends.c.quasistatic.utils.CQuasiStaticConstants;
import net.sf.orcc.backends.c.transforms.IncrementPeephole;
import net.sf.orcc.backends.c.transforms.MoveReadsWritesTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transforms.BroadcastAdder;

public class CQuasiStaticBackendImpl extends AbstractBackend {

	private String actorsSourcePath;
	private String networkFilePath;
	private String outputDirectoryPath;
	private String workingDirectoryPath;
	private CQuasiStaticActorPrinter printer;

	private void createConfigFiles() {
		new File(workingDirectoryPath + CQuasiStaticConstants.CONFIG_PATH)
				.mkdirs();
		ConfigFilesCreator.createPropertiesFiles(workingDirectoryPath
				+ CQuasiStaticConstants.CONFIG_PATH);
	}

	private void createOutputDirectories() {

		// Directories' creation
		new File(workingDirectoryPath
				+ CQuasiStaticConstants.SCHEDULE_FILES_PATH).mkdirs();
		new File(workingDirectoryPath + CQuasiStaticConstants.XDF_FILE_PATH)
				.mkdirs();
		new File(workingDirectoryPath + CQuasiStaticConstants.CALML_FILE_PATH)
				.mkdirs();
		new File(workingDirectoryPath + CQuasiStaticConstants.DSE_INPUT_PATH)
				.mkdirs();
	}

	public void generateCode(String fileName, int fifoSize) throws Exception {
		super.generateCode(fileName, 64);
		networkFilePath = fileName;
		// print schedule
		printSchedule();
	}

	@Override
	protected void init() throws IOException {
		printer = new CQuasiStaticActorPrinter();

		// Init scheduler's stuff
		prepareScheduler();
	}

	/**
	 * 
	 * @param id
	 * @param actor
	 * @throws Exception
	 */
	protected void parseActor(String id, Actor actor) throws Exception {
		File actorFile = new File(actor.getFile());

		// Gets the parent path: working directory
		if (actorsSourcePath.equals(CQuasiStaticConstants.PATH_NOT_ASSIGNED))
			actorsSourcePath = actorFile.getParentFile().getAbsolutePath()
					+ File.separator;

		CQuasiStaticActorParser parser = new CQuasiStaticActorParser(actorFile
				.getAbsolutePath(), workingDirectoryPath
				+ CQuasiStaticConstants.CALML_FILE_PATH);
		parser.parse();

	}

	protected void parseNetworkFiles() throws Exception {

		CQuasiStaticNetworksParser parser = new CQuasiStaticNetworksParser(
				actorsSourcePath, workingDirectoryPath
						+ CQuasiStaticConstants.XDF_FILE_PATH);
		parser.parse();

	}

	protected void prepareScheduler() {
		actorsSourcePath = CQuasiStaticConstants.PATH_NOT_ASSIGNED;
		workingDirectoryPath = path + File.separator + "schedule"
				+ File.separator;
		outputDirectoryPath = workingDirectoryPath
				+ CQuasiStaticConstants.SCHEDULE_FILES_PATH;

		createOutputDirectories();
		createConfigFiles();
	}

	@Override
	protected void printActor(String id, Actor actor) throws Exception {
		ActorTransformation[] transformations = { new DeadGlobalElimination(),
				new PhiRemoval(), new IncrementPeephole(),
				new MoveReadsWritesTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		String outputName = path + File.separator + id + ".c";
		printer.printActor(outputName, id, actor);

		parseActor(id, actor);
	}

	@Override
	protected void printNetwork(Network network) throws Exception {
		CQuasiStaticNetworkPrinter networkPrinter = new CQuasiStaticNetworkPrinter();
		String outputName = path + File.separator + network.getName() + ".c";

		// Add broadcasts before printing
		new BroadcastAdder().transform(network);

		networkPrinter.printNetwork(outputName, network, false, fifoSize);
		parseNetworkFiles();
	}

	protected void printSchedule() throws IOException {
		CQuasiStaticSchedulePrinter schedulePrinter = new CQuasiStaticSchedulePrinter();
		String outputName = path + File.separator + "scheduling.c";
		HashMap<String, List<String>> scheduleMap = Scheduler_Simulator.run(
				workingDirectoryPath, networkFilePath, outputDirectoryPath);
		removeOutputDirectories();
		schedulePrinter.printSchedule(outputName, scheduleMap);
	}

	/**
	 * 
	 */
	private void removeOutputDirectories() {
		FileUtilities.deleteFile(new File(workingDirectoryPath));
	}

}
