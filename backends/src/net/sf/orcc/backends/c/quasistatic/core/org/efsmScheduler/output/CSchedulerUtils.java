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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.main.Scheduler_Simulator;

/**
 * 
 * @author Victor Martin
 */
public class CSchedulerUtils {
	public static String PROPERTIES_FILE_PATH = "config" + File.separator
			+ "actionsIdentifiers.properties";
	public static final Integer KEY_NOT_FOUND = -1;

	private static ArrayList<String> schedule;

	private static Properties systemProperties;

	/**
	 * public methods
	 */
	public static void init() {
		loadSystemPropertiesFile();
		schedule = new ArrayList<String>();

	}

	public static boolean addAction(String actorShortName, String actionName) {
		Integer actionId = getActionIdentifier(actorShortName, actionName);
		if (actionId == KEY_NOT_FOUND)
			return false;
		return schedule.add(actionId.toString());
	}

	public static void printOutput(String switchName) {
		try {
			File outputFile = new File(Scheduler_Simulator.getInstance()
					.getOutputDirectory().getAbsolutePath()
					+ File.separator + switchName.toLowerCase() + ".txt");
			outputFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
			bw.write(schedule.size() + " ");
			for (int i = 0; i < schedule.size(); i++) {
				bw.write(schedule.get(i) + ",");
			}
			bw.close();
		} catch (IOException ex) {
			Logger.getLogger(CSchedulerUtils.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	/**
	 * Private methods
	 */

	/**
     *
     */
	private static void loadSystemPropertiesFile() {
		try {
			systemProperties = new Properties();
			File propertiesFile = new File(PROPERTIES_FILE_PATH);
			FileInputStream fis = new FileInputStream(propertiesFile);
			systemProperties.load(fis);
			fis.close();
		} catch (IOException ex) {
			Logger.getLogger(CSchedulerUtils.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private static int getActionIdentifier(String actorShortName,
			String actionName) {
		if (systemProperties == null)
			loadSystemPropertiesFile();
		String key = actorShortName + "." + actionName;
		String value = systemProperties.getProperty(key);
		return value == null ? KEY_NOT_FOUND : Integer.parseInt(value);

	}

}
