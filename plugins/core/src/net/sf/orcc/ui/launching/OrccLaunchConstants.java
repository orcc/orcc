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
package net.sf.orcc.ui.launching;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants associated with an Orcc launch configuration.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface OrccLaunchConstants {

	/**
	 * Back-end associated with an Orcc configuration.
	 */
	public static final String BACKEND = "net.sf.orcc.backend";

	/**
	 * When set, Orcc frontend will be launched in debug mode.
	 */
	public static final String DEBUG_MODE = "net.sf.orcc.debugMode";

	/**
	 * Default value for {@value #ENABLE_CACHE}.
	 */
	public static final boolean DEFAULT_CACHE = false;

	/**
	 * Default value for {@value #DEBUG_MODE}.
	 */
	public static final boolean DEFAULT_DEBUG = false;

	/**
	 * Default value for {@value #DOT_CFG}.
	 */
	public static final boolean DEFAULT_DOT_CFG = false;

	/**
	 * Default value for {@value #FIFO_SIZE}.
	 */
	public static final int DEFAULT_FIFO_SIZE = 10000;

	/**
	 * Default value for {@value #KEEP_INTERMEDIATE}.
	 */
	public static final boolean DEFAULT_KEEP = false;

	/**
	 * Default value for {@value #ENABLE_TRACES}.
	 */
	public static final boolean DEFAULT_TRACES = false;

	/**
	 * When enabled, frontend prints DOT files showing CFG information.
	 */
	public static final String DOT_CFG = "net.sf.orcc.dotCfg";

	/**
	 * When enabled, code is only generated for actors that were modified after
	 * code was last generated.
	 */
	public static final String ENABLE_CACHE = "net.sf.orcc.enableCache";

	/**
	 * When enabled, interpreter traces each time data is written to
	 * (output)FIFO
	 */
	public static final String ENABLE_TRACES = "net.sf.orcc.enableTraces";

	/**
	 * Size of FIFO channels when not specified by the network.
	 */
	public static final String FIFO_SIZE = "net.sf.orcc.fifoSize";

	/**
	 * Input file associated with an Orcc configuration. Must be kept in sync
	 * with options in the back-ends plug-in.
	 */
	public static final String INPUT_FILE = "net.sf.orcc.backends.xdfInputFile";

	/**
	 * Input stimulus file associated with an Orcc configuration.
	 */
	public static final String INPUT_STIMULUS = "net.sf.orcc.inputStimulus";

	/**
	 * Whether intermediate files should be kept.
	 */
	public static final String KEEP_INTERMEDIATE = "net.sf.orcc.keepIntermediate";

	/**
	 * Options associated to the backend.
	 */
	public static final Map<String, String> OPTIONS = new HashMap<String, String>();

	/**
	 * Output folder associated with an Orcc configuration.
	 */
	public static final String OUTPUT_FOLDER = "net.sf.orcc.outputFolder";

	/**
	 * Parameters associated with an Orcc configuration.
	 */
	public static final String PARAMETERS = "net.sf.orcc.parameters";

	/**
	 * Orcc run configuration type.
	 */
	public static final String RUN_CONFIG_TYPE = "net.sf.orcc.runLaunchConfigurationType";

	/**
	 * Orcc simulation configuration type.
	 */
	public static final String SIMULATION_CONFIG_TYPE = "net.sf.orcc.simuLaunchConfigurationType";

}
