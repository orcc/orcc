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

package net.sf.orcc.backends.c.quasistatic.scheduler.util;

import java.io.File;



/**
 * Global constants
 * 
 * @author Victor Martin
 */
public class Constants {
	
	/**
	 * For parse input XDF file
	 */
	public static String INPUT_FILE_NAME = "QSB_input.xdf";
	public static String TOKENS_PATTERN = "tokens_pattern";
	public static String CUSTOM_INDIVIDUAL_BUFFERS_SIZES = "custom_individual_buffers_sizes";
	public static String CUSTOM_GENERAL_BUFFER_SIZE = "custom_general_buffer_size";
	public static String QS_SCHEDULER = "qs_scheduler";

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
	 * Back-end constants
	 */
	public static final String PATH_NOT_ASSIGNED = "Path not assigned";

	// Paths
	public static final String SCHEDULE_FILES_PATH = "schedule files"
			+ File.separator + "generated" + File.separator;
	public static final String CALML_FILE_PATH = "CALML files" + File.separator;
	public static final String XDF_FILE_PATH = "XDF files" + File.separator;
	public static final String CONFIG_PATH = "config";
	public static final String DSE_INPUT_PATH = "DSE Input";
	
	

	

}
