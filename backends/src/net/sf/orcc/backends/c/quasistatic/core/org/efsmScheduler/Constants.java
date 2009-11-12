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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;

/**
 * Global constants
 * 
 * @author Victor Martin
 */
public class Constants {
	/**
	 * For variable token rate
	 */
	private static int VARIABLE_TOKEN_RATE = Integer.MIN_VALUE;
	private static int BLOCKSIZE = 8;

	/**
	 * Kind of networks files
	 */
	public static final String NL_FILE = "nl";
	public static final String XDF_FILE = "xdf";

	/**
	 * For user interface
	 */
	public static final int STOPPED_PERCENT = 0;
	public static final int PARSING_XML_FILES = 5;
	public static final int PARSING_CALML_FILES = 9;
	public static final int SEPARATING_ACTORS = 14;
	public static final int CREATING_SCHEDULE_FOR_BTYPE = 15;
	public static final int CREATING_DSE_SCHEDULING = 90;
	public static final int PROCESS_FINISHED = 100;

	public static final String STOPPED_LABEL = "Stopped";
	public static final String STARTED_LABEL = "Started";
	public static final String PARSING_XML_FILES_LABEL = "Parsing XML files";
	public static final String PARSING_CALML_FILES_LABEL = "Parsing CALML files";
	public static final String SEPARATING_ACTORS_LABEL = "Separating_Actors";
	public static final String CREATING_SCHEDULE_FOR_BTYPE_LABEL = "Creating schedule";
	public static final String CREATING_DSE_SCHEDULING_LABEL = "Creating DSE Scheduling";
	public static final String FINISHED_LABEL = "Process finished";

	/**
	 * For properties file
	 */
	public static final String PROJECT_NAME_PROPERTY_NAME = "PROJECT_NAME";
	public static final String PROJECT_VERSION_PROPERTY_NAME = "PROJECT_VERSION";
	public static final String PROJECT_LAST_UPDATE_PROPERTY_NAME = "LAST_UPDATE";
	public static final String PROJECT_DEFAULT_NL_FILE_PATH_PROPERTY_NAME = "DEFAULT_NL_FILE_PATH";
	public static final String PROJECT_DEFAULT_CALML_FILES_PATH_PROPERTY_NAME = "DEFAULT_CALML_FILES_PATH";
	public static final String PROJECT_DEFAULT_WORKING_DIRECTORY_PATH_PROPERTY_NAME = "DEFAULT_WORKING_DIRECTORY_PATH";
	public static final String PROJECT_DEFAULT_OUTPUT_FILES_PATH_PROPERTY_NAME = "DEFAULT_OUTPUT_FILES_PATH";
	public static final String VARIABLE_TOKEN = "VARIABLE_TOKEN";

	/**
	 * To represent kind of actors
	 */
	public static final String STATIC_ACTOR = "Static Actor";
	public static final String BORDERLINE_ACTOR = "Borderline Actor";
	public static final String ND_ACTOR = "ND Actor";

	public static final String NEWVOP = "NEWVOP";
	public static final String INTRA = "INTRA";
	public static final String INTRA_AND_INTER = "COMBINED";
	public static final String MOTION = "MOTION";
	public static final String INTER = "INTER";
	public static final String ACCODED = "ACCODED";
	public static final String FOURMV = "FOURMV";
	public static final String ZEROMV = "ZEROMV";
	public static final String NOT_ASSIGNED = "not assigned";

	/**
	 * Return the next percent which the progress bar will show
	 * 
	 * @param lastPercent
	 * @return next value which will be shown in the progress bar
	 */
	public static int getNextProgressBarPercent(int lastPercent) {
		switch (lastPercent) {
		case STOPPED_PERCENT:
			return PARSING_XML_FILES;
		case PARSING_XML_FILES:
			return PARSING_CALML_FILES;
		case PARSING_CALML_FILES:
			return SEPARATING_ACTORS;
		case SEPARATING_ACTORS:
			return CREATING_SCHEDULE_FOR_BTYPE;
		case CREATING_SCHEDULE_FOR_BTYPE:
			return CREATING_DSE_SCHEDULING;
		case CREATING_DSE_SCHEDULING:
			return PROCESS_FINISHED;
		case PROCESS_FINISHED:
			return STOPPED_PERCENT;
		default:
			return STOPPED_PERCENT;
		}
	}

	/**
	 * 
	 * @param lastLabel
	 *            last shown label
	 * @return
	 */
	public static String getNextProgressBarLabel(String lastLabel) {
		if (lastLabel.contains(STOPPED_LABEL))
			return PARSING_XML_FILES_LABEL;
		if (lastLabel.contains(PARSING_XML_FILES_LABEL))
			return PARSING_CALML_FILES_LABEL;
		if (lastLabel.contains(PARSING_CALML_FILES_LABEL))
			return SEPARATING_ACTORS_LABEL;
		if (lastLabel.contains(SEPARATING_ACTORS_LABEL))
			return CREATING_SCHEDULE_FOR_BTYPE_LABEL;
		if (lastLabel.contains(CREATING_SCHEDULE_FOR_BTYPE_LABEL))
			return CREATING_DSE_SCHEDULING_LABEL;
		if (lastLabel.contains(CREATING_DSE_SCHEDULING_LABEL))
			return FINISHED_LABEL;
		return STOPPED_LABEL;
	}

	/**
	 * 
	 * @param label
	 * @return
	 */
	public static int getProgressBarPercent(String label) {
		if (label.contains(STOPPED_LABEL))
			return STOPPED_PERCENT;
		if (label.contains(PARSING_XML_FILES_LABEL))
			return PARSING_XML_FILES;
		if (label.contains(PARSING_CALML_FILES_LABEL))
			return PARSING_CALML_FILES;
		if (label.contains(SEPARATING_ACTORS_LABEL))
			return SEPARATING_ACTORS;
		if (label.contains(CREATING_SCHEDULE_FOR_BTYPE_LABEL))
			return CREATING_SCHEDULE_FOR_BTYPE;
		if (label.contains(CREATING_DSE_SCHEDULING_LABEL))
			return CREATING_DSE_SCHEDULING;
		if (label.contains(FINISHED_LABEL))
			return PROCESS_FINISHED;
		return STOPPED_PERCENT;
	}

	/**
	 * Return the next label which the user interface should shows
	 * 
	 * @param lastPercent
	 *            value of progress var
	 * @return the string which matches with the value of the progress bar
	 */
	public static String getProgressBarLabel(int lastPercent) {
		switch (lastPercent) {
		case STOPPED_PERCENT:
			return STOPPED_LABEL;
		case PARSING_XML_FILES:
			return PARSING_XML_FILES_LABEL;
		case PARSING_CALML_FILES:
			return PARSING_CALML_FILES_LABEL;
		case SEPARATING_ACTORS:
			return SEPARATING_ACTORS_LABEL;
		case CREATING_SCHEDULE_FOR_BTYPE:
			return CREATING_SCHEDULE_FOR_BTYPE_LABEL;
		case CREATING_DSE_SCHEDULING:
			return CREATING_DSE_SCHEDULING_LABEL;
		case PROCESS_FINISHED:
			return FINISHED_LABEL;
		default:
			return STOPPED_LABEL;
		}
	}

	/**
	 * @return value of veriable token rate
	 */
	public static int getVariableTokenRate() {
		return VARIABLE_TOKEN_RATE;
	}

	/**
	 * @return block size
	 */
	public static int getBlocksize() {
		return BLOCKSIZE;
	}

	/**
	 * Determines what kind of actor is using the following rules: 1. Static
	 * actor: static input and output. 2. Borderline Actor: Non-deterministic
	 * input, static output 3. Non-deterministic Actors: ND input and output
	 */
	public static String getActorKind(boolean existsNDInput,
			boolean existsNDOutput, String machineName) {

		if (!PropertiesParser.existsMachineOnPropertiesFile(machineName))
			return ND_ACTOR;

		if (!existsNDInput && !existsNDOutput)
			return STATIC_ACTOR;
		if (existsNDInput && !existsNDOutput)
			return BORDERLINE_ACTOR;

		return ND_ACTOR;
	}

	/**
	 * 
	 * @param BTYPE
	 * @return
	 */
	public static int getIndexOfBTYPE(String BTYPE) {
		if (BTYPE.equals(ZEROMV))
			return 1;
		if (BTYPE.equals(INTER))
			return 2;
		if (BTYPE.equals(INTRA))
			return 3;
		if (BTYPE.equals(INTRA_AND_INTER))
			return 4;
		return 0;

	}

}
