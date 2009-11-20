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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Constants;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.SDFScheduler;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Switch;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Util;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.dse.DSEScheduler;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.dse.DSESchedulerUtils;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Network;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.NetworkActor;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.SDFGraph;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.XDFParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.XNLParser;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.FileUtilities;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.OutputResultUtilities;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.SimpleFileFilter;

/**
 * This class performs the system's process
 * 
 * @author Victor Martin
 */
public class Scheduler_Simulator {

	public static int NORMAL_SIMULATION = 0;
	public static int STEP_BY_STEP_SIMULATION = 1;
	public static int CONSOLE_SIMULATION_MODE = 2;

	public static String DEFAULT_WORKING_DIRECTORY_PATH;
	public static final String DEFAULT_OUTPUT_FILES_PATH = "output"
			+ File.separator;
	public static String DEFAULT_MODEL_TOP_FILE_PATH;

	private static final String CALML_FILES_FOLDER_PATH_EXTENSION = File.separator
			+ "CALML files";
	private static final String XNL_FILES_FOLDER_PATH_EXTENSION = File.separator
			+ "XNL files";
	private static final String XDF_FILES_FOLDER_PATH_EXTENSION = File.separator
			+ "XDF files";

	// Simulation Path files
	private File networkFile;
	private File outputDirectory;
	private File workingDirectory;

	private int mode = NORMAL_SIMULATION;
	private String kindOfTopModelFile;

	private Network net;

	// Simulator instance
	private static Scheduler_Simulator instance;

	public static Scheduler_Simulator getInstance() {
		if (instance == null) {
			instance = new Scheduler_Simulator();
		}
		return instance;
	}

	public int getMode() {
		return mode;
	}

	/**
	 * @return the network file name
	 */
	public String getNetworkName() {

		return (networkFile == null || networkFile.getName().isEmpty()) ? "None"
				: Util.removeFileExtension(networkFile.getName());
	}

	/**
	 * Default constructor
	 */
	public Scheduler_Simulator() {
		mode = NORMAL_SIMULATION;
	}

	/**
	 * Get the CALML path
	 * 
	 * @return the CALML directory path
	 */
	public File getCALMLDirectory() {
		return new File(workingDirectory.getAbsolutePath()
				+ CALML_FILES_FOLDER_PATH_EXTENSION);
	}

	/**
	 * 
	 * @return the outpurt directory path
	 */
	public File getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * 
	 * @param XNLPathFile
	 *            set the top model file
	 */
	public void setTopModelFile(File networkFile) {
		this.networkFile = networkFile;

		String networkFileName = networkFile.getName();

		kindOfTopModelFile = networkFileName.endsWith(Constants.NL_FILE) ? Constants.NL_FILE
				: Constants.XDF_FILE;
	}

	/**
	 * 
	 * @return
	 */
	public String getKindOfTopModelFile() {
		return kindOfTopModelFile;
	}

	/**
	 * 
	 * @param kindOfTopModelFile
	 */
	public void setKindOfTopModelFile(String kindOfTopModelFile) {
		this.kindOfTopModelFile = kindOfTopModelFile;
	}

	/**
	 * Sets the output directory path
	 * 
	 * @param OutputPathFile
	 */
	public void setOutputDirectory(File outputFile) {
		this.outputDirectory = outputFile;
	}

	/**
	 * 
	 * @return the network file (XNL file)
	 */
	public File getXNLTopModelFile() {

		return new File(workingDirectory.getAbsolutePath()
				+ XNL_FILES_FOLDER_PATH_EXTENSION + File.separator
				+ getNetworkName() + ".xnl");
	}

	/**
	 * 
	 * @return the network file (XDF file)
	 */
	public File getXDFTopModelFile() {

		return new File(workingDirectory.getAbsolutePath()
				+ XDF_FILES_FOLDER_PATH_EXTENSION + File.separator
				+ getNetworkName() + ".xdf");
	}

	public File getXNLDirectory() {
		return new File(workingDirectory.getAbsolutePath()
				+ XNL_FILES_FOLDER_PATH_EXTENSION);
	}

	public File getXDFDirectory() {
		return new File(workingDirectory.getAbsolutePath()
				+ XDF_FILES_FOLDER_PATH_EXTENSION);
	}

	/**
	 * 
	 * @return working directoty file
	 */
	public File getWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * 
	 * @param workingDirectory
	 *            mew working directory
	 */
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/**
	 * 
	 * @return top model file (network file). The extension of this file is .nl
	 */
	public File getTopModelFile() {
		return networkFile;
	}

	private void createInputFiles() {
		boolean createdCALMLDirectory = FileUtilities
				.createDirectory(getCALMLDirectory().getAbsolutePath());
		System.out.println("Creating inputs files in " + workingDirectory);
		ArrayList<String> extensions;
		if (createdCALMLDirectory) {
			extensions = new ArrayList<String>();
			extensions.add("cal");
			FileUtilities.parseFiles(getCALMLDirectory(), workingDirectory
					.listFiles(new SimpleFileFilter(extensions, true)),
					FileUtilities.CAL_TO_CALML);
			System.out.println("CALML files has been created successfully");
		}

		boolean createdXDFDirectory = FileUtilities
				.createDirectory(getXDFDirectory().getAbsolutePath());
		if (createdXDFDirectory) {
			extensions = new ArrayList<String>();
			extensions.add("xdf");
			File[] files = workingDirectory.listFiles(new SimpleFileFilter(
					extensions, true));
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				File destinationFile = new File(getXDFDirectory()
						.getAbsolutePath()
						+ File.separator + fileName);
				FileUtilities.copyFile(files[i], destinationFile);
			}

			System.out.println("XDF files has been created successfully");
		}
	}

	/**
	 * Starts the simulation in other thread.
	 * 
	 * @param simulationType
	 *            : Type of simulation selected by user.
	 */
	public HashMap<String, List<String>> start(final int simulationMode) {
		System.out.println(toString());
		System.out.println("Scheduler started");
		ArrayList<String> switchValues = Switch.getSwitchValues();
		Scheduler_Simulator sim = Scheduler_Simulator.getInstance();
		sim.setSimulationMode(simulationMode);
		sim.createInputFiles();
		sim.parseXNLFiles();
		sim.parseCALMLFiles();
		sim.separateActors();
		int noSwitchs = switchValues.size();
		// performs schedule for each different btype value
		for (int i = 0; i < noSwitchs; i++) {
			String btype = switchValues.get(i);
			// Sets switch value
			Switch.getInstance().setNewSwitchValue(btype);
			// performs schedule
			sim.setTokensPatterns();
			sim.unrollActors();
			// show results
			sim.showVisualReports();
		}
		HashMap<String, List<String>> scheduleMap = sim.performDSESchedule();
		scheduleMap = trasformScheduleMap(scheduleMap);
		System.out.println("Scheduler has finished");
		return scheduleMap;

	}

	private HashMap<String, List<String>> trasformScheduleMap(
			HashMap<String, List<String>> map) {

		List<String> schedule = map.get(Constants.NEWVOP.toLowerCase());
		int div = schedule.size() / 3;
		int mod = schedule.size() % 3;
		int pivot = div + mod;
		ArrayList<ArrayList<String>> newSchedule = new ArrayList<ArrayList<String>>(
				3);
		newSchedule.add(0, new ArrayList<String>());
		newSchedule.add(1, new ArrayList<String>());
		newSchedule.add(2, new ArrayList<String>());

		for (int i = 0; i < schedule.size(); i++) {
			int index = (i >= 0 && i < pivot) ? 0 : (i < pivot + div) ? 1 : 2;
			newSchedule.get(index).add(schedule.get(i));
		}
		map.put(new String("btype_phase0"), newSchedule.get(0));
		map.put(new String("btype_phase1"), newSchedule.get(1));
		map.put(new String("btype_phase2"), newSchedule.get(2));
		map.remove(Constants.NEWVOP.toLowerCase());

		return map;
	}

	private void parseXNLFiles() {
		try {
			// Parsing XNL file (Network description)
			// MainFrame.getInstance().nextStep(false);
			String networkName = Scheduler_Simulator.getInstance()
					.getNetworkName();
			System.out.println("Parsing " + networkName + " file ");
			net = (kindOfTopModelFile.equals(Constants.NL_FILE)) ? XNLParser
					.parse(networkName, getXNLTopModelFile().getAbsolutePath())
					: XDFParser.parse(networkName, getXDFTopModelFile()
							.getAbsolutePath());
			System.out.println(net.toString());

		} catch (Exception ex) {
			Logger.getLogger(Scheduler_Simulator.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	private void parseCALMLFiles() {
		// MainFrame.getInstance().nextStep(false);
		System.out.println("Parsing Calml files");
		net.parseCalml();
	}

	private void separateActors() {
		// Separating static, borderline and Non-Deterministic actors
		// MainFrame.getInstance().nextStep(false);
		System.out.println("Separating static and Non-Deterministic actors ");
		net.separateActors();
		net.printKindOfActors();
	}

	private void unrollActors() {
		String btypeName = Switch.getInstance().getSwitchType();
		// Unroll EFSMs to DFGs
		System.out.println("Unrolling EFSMs to DFGs graphs for BTYPE = "
				+ btypeName);
		net.unrollActors();

	}

	private void setTokensPatterns() {
		String btypeName = Switch.getInstance().getSwitchType();
		// Unroll EFSMs to DFGs
		System.out.println("Setting patterns of tokens for BTYPE = "
				+ btypeName);
		// true -> hard coded unroller, false -> automatic unroller.
		net.setTokensPatterns();
	}

	private HashMap<String, List<String>> performDSESchedule() {
		// MainFrame.getInstance().nextStep(false);
		DSEScheduler dseScheduler = new DSEScheduler();
		dseScheduler.createInputFiles();

		int noProcessors = SDFScheduler.getNumberOfProcessors();
		int noClusters = 5;
		HashMap<String, Integer> clusterMap = new HashMap<String, Integer>();
		Set<NetworkActor> actorsSet = net.getActors();
		for (NetworkActor actor : actorsSet) {
			if (!actor.getKind().equals(Constants.STATIC_ACTOR))
				continue;
			if (actor.isCompound())
				continue;

			String actorName = PropertiesParser.getActorShortName(actor
					.getName(), actor.getLongName());
			String actorId = actorName + "@" + actor.getActorIndex();
			int cluster = DSESchedulerUtils.getNoCluster(actorName);
			clusterMap.put(actorId, cluster);
		}
		OutputResultUtilities.createMappingFile(noProcessors, noClusters + 1,
				clusterMap);
		System.out.println("Creating DSE Schedule");
		HashMap<String, List<String>> scheduleMap = dseScheduler.schedule();
		System.out.println("Schedule created successfully");

		return scheduleMap;
	}

	private void showVisualReports() {
		String btypeName = Switch.getInstance().getSwitchType();
		// Now, combine the subgraphs to create the system-level graph
		System.out.println("Creating System-level graph for BTYPE: "
				+ btypeName);
		SDFGraph sysGraph = net.getSystemLevelGraph();
		OutputResultUtilities.createSummary(sysGraph);

	}

	public HashMap<String, List<String>> runConsoleMode(
			String workingDirectoryPath, String networkFilePath,
			String outputDirectoryPath) {
		this.setTopModelFile(new File(networkFilePath));
		this.setWorkingDirectory(new File(workingDirectoryPath));
		this.setOutputDirectory(new File(outputDirectoryPath));
		return start(Scheduler_Simulator.CONSOLE_SIMULATION_MODE);

	}

	public static void main(String[] args) {
		Scheduler_Simulator
				.getInstance()
				.runConsoleMode(
						"C:\\Documents and Settings\\vimartin\\Desktop\\Open DF projects\\CALNeS Project\\MPEG_SP\\",
						"C:\\Documents and Settings\\vimartin\\Desktop\\Open DF projects\\CALNeS Project\\MPEG_SP\\testbed.xdf",
						"C:\\output\\");
	}

	public static HashMap<String, List<String>> run(
			String workingDirectoryPath, String networkFilePath,
			String outputDirectoryPath) {
		return Scheduler_Simulator.getInstance().runConsoleMode(
				workingDirectoryPath, networkFilePath, outputDirectoryPath);
	}

	public void setSimulationMode(int mode) {
		this.mode = mode;
	}

	public Network getNetwork() {
		return net;
	}

	public String toString() {
		return "***********************************************************************************"
				+ "\n"
				+ PropertiesParser
						.getProperty(Constants.PROJECT_NAME_PROPERTY_NAME)
				+ "\n"
				+ "Version: "
				+ PropertiesParser
						.getProperty(Constants.PROJECT_VERSION_PROPERTY_NAME)
				+ "\n"
				+ "Last update: "
				+ PropertiesParser
						.getProperty(Constants.PROJECT_LAST_UPDATE_PROPERTY_NAME)
				+ "\n"
				+ "Mode: "
				+ getMode()
				+ "\n"
				+ "Top model file: "
				+ getTopModelFile().getAbsolutePath()
				+ "\n"
				+ "Output directory: "
				+ getOutputDirectory().getAbsolutePath()
				+ "\n"
				+ "***********************************************************************************"
				+ "\n";
	}
}
