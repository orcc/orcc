/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc;

/**
 * Constants associated with an Orcc launch configuration.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface OrccLaunchConstants {

	public static final String ACTIVATE_BACKEND = "net.sf.orcc.simulators.activateBackend";

	/**
	 * Back-end associated with an Orcc configuration.
	 */
	public static final String BACKEND = "net.sf.orcc.backend";

	/**
	 * Whether we should compile the given XDF input file. Must be kept in sync
	 * with options in the back-ends plug-in.
	 */
	public static final String COMPILE_XDF = "net.sf.orcc.plugins.compileXDF";

	/**
	 * When set, Orcc frontend will be launched in debug mode.
	 */
	public static final String DEBUG_MODE = "net.sf.orcc.debugMode";

	/**
	 * Default value for {@value #ACTIVATE_BACKEND}.
	 */
	public static final boolean DEFAULT_BACKEND = false;

	/**
	 * Default value for {@value #DEBUG_MODE}.
	 */
	public static final boolean DEFAULT_DEBUG = false;

	/**
	 * Default value for {@value #FIFO_SIZE}.
	 */
	public static final int DEFAULT_FIFO_SIZE = 512;

	/**
	 * Default value for {@value #ENABLE_TRACES}.
	 */
	public static final boolean DEFAULT_TRACES = false;

	/**
	 * When enabled, interpreter compare the output of the decoder with a
	 * reference video.
	 */
	public static final String ENABLE_COMP = "net.sf.orcc.simulators.enableComparison";

	/**
	 * When enabled, interpreter traces each time data is written to
	 * (output)FIFO
	 */
	public static final String ENABLE_TRACES = "net.sf.orcc.simulators.enableTraces";

	/**
	 * Size of FIFO channels when not specified by the network.
	 */
	public static final String FIFO_SIZE = "net.sf.orcc.fifoSize";

	/**
	 * When set, a golden reference has to be compared with the ouput data of the network.
	 */
	public static final String GOLDEN_REFERENCE = "net.sf.orcc.simulators.goldenReference";

	/**
	 * Golden reference file associated with an Orcc configuration.
	 */
	public static final String GOLDEN_REFERENCE_FILE = "net.sf.orcc.simulators.goldenReferenceFile";

	/**
	 * Input stimulus file associated with an Orcc configuration.
	 */
	public static final String INPUT_STIMULUS = "net.sf.orcc.simulators.inputStimulus";

	/**
	 * Parameter used to set max number of times an input file is read.
	 */
	public static final String LOOP_NUMBER = "net.sf.orcc.simulators.loopNumber";

	/**
	 * Mapping associated with an Orcc configuration.
	 */
	public static final String MAPPING = "net.sf.orcc.plugins.mapping";

	/**
	 * If this parameter is set to true, the library files should not be
	 * exported when executing a backend.
	 */
	public static final String NO_LIBRARY_EXPORT = "net.sf.orcc.dontExportLibrary";

	/**
	 * Output folder associated with an Orcc configuration.
	 */
	public static final String OUTPUT_FOLDER = "net.sf.orcc.outputFolder";

	/**
	 * Parameters associated with an Orcc configuration.
	 */
	public static final String PARAMETERS = "net.sf.orcc.parameters";

	/**
	 * Project associated with an Orcc configuration.
	 */
	public static final String PROJECT = "net.sf.orcc.project";

	/**
	 * Simulator reference video for testing video output
	 */
	public static final String REFERENCE_FILE = "net.sf.orcc.simulators.compareFile";

	/**
	 * Orcc run configuration type.
	 */
	public static final String RUN_CONFIG_TYPE = "net.sf.orcc.runLaunchConfigurationType";

	/**
	 * Orcc simulation configuration type.
	 */
	public static final String SIMU_CONFIG_TYPE = "net.sf.orcc.simuLaunchConfigurationType";

	/**
	 * Simulator associated with an Orcc configuration.
	 */
	public static final String SIMULATOR = "net.sf.orcc.simulator";

	/**
	 * Simulator traces folder associated with an Orcc configuration
	 */
	public static final String TRACES_FOLDER = "net.sf.orcc.simulators.tracesFolder";

	/**
	 * Folder where the compiled Actors can be found.
	 */
	public static final String VTL_FOLDER = "net.sf.orcc.simulators.VTLFolder";

	/**
	 * Input file associated with an Orcc configuration. Must be kept in sync
	 * with options in the back-ends plug-in.
	 */
	public static final String XDF_FILE = "net.sf.orcc.core.xdfFile";

}
