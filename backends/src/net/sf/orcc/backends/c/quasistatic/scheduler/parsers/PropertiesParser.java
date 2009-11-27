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

package net.sf.orcc.backends.c.quasistatic.scheduler.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.orcc.backends.c.quasistatic.scheduler.model.Switch;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;

/**
 * 
 * Properties parser
 * 
 * @author vimartin
 */
public class PropertiesParser {

	public static final String SHORT_NAME_PROPERTY_KEY = "SHORT_NAME_";

	private static String SYSTEM_PROPERTIES_FILE_PATH = "config"
			+ File.separator + "system.properties";
	

	private static String configDirectoryPath = "config";

	private static Properties systemProperties = null;

	/**
	 * 
	 * @return system properties
	 */
	public static Properties getSystemProperties() {
		if (systemProperties == null) {
			loadSystemPropertiesFile();
			//initActorsNamesMap();
		}
		return systemProperties;
	}

	private static void updatePropertiesFilesPaths() {
		SYSTEM_PROPERTIES_FILE_PATH = configDirectoryPath + File.separator
				+ "system.properties";
		//CSchedulerUtils.PROPERTIES_FILE_PATH = configDirectoryPath
		//		+ File.separator + "actionsIdentifiers.properties";
	}

	public static void setConfigDirectoryPath(String newPath) {
		configDirectoryPath = newPath;
		updatePropertiesFilesPaths();
	}

	/**
	 * Parse the properties file
	 */
	private static void loadSystemPropertiesFile() {
		try {
			systemProperties = new Properties();
			File propertiesFile = new File(SYSTEM_PROPERTIES_FILE_PATH);
			FileInputStream fis = new FileInputStream(propertiesFile);
			systemProperties.load(fis);
			fis.close();
		} catch (IOException ex) {
			Logger.getLogger(PropertiesParser.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, Integer> getNoReadsMap(String machineName) {
		HashMap<String, Integer> patterns = new HashMap<String, Integer>();
		Properties system_properties = getSystemProperties();
		Enumeration propertiesNames = system_properties.propertyNames();
		while (propertiesNames.hasMoreElements()) {
			String key = (String) propertiesNames.nextElement();
			
			String portKey = Switch.btype + "_" + machineName + "_PORT_";
			if (key.startsWith(portKey)) {
				String value = system_properties.getProperty(key);
				Integer Token_value = value.equals(Constants.VARIABLE_TOKEN) ? Constants
						.getVariableTokenRate()
						: Integer.parseInt(value.split(" ")[1]);
				key = key.split(portKey)[1];
				
				patterns.put(key, Token_value);
			}
		}
		return patterns;
	}

	/**
	 * 
	 * @param machineName
	 * @return Map for machine X -> |portname : number of tokens that has to be
	 *         consumed when the actor | | is fired |
	 *         |___________________________________________________________________
	 *         |
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Integer> getConsumptionTokenPortsMap(
			String machineName) {

		HashMap<String, Integer> patterns = new HashMap<String, Integer>();
		Properties system_properties = getSystemProperties();
		Enumeration propertiesNames = system_properties.propertyNames();
		while (propertiesNames.hasMoreElements()) {
			String key = (String) propertiesNames.nextElement();
			// If property name starts with XNLFileName + "_" +efsm.getName() +
			// "_PORT_"
			String portKey = Switch.btype + "_" + machineName + "_PORT_";
			if (key.startsWith(portKey)) {
				String value = system_properties.getProperty(key);
				Integer Token_value = value.equals(Constants.VARIABLE_TOKEN) ? 1
						: Integer.parseInt(value.split(" ")[0]);
				key = key.split(portKey)[1];
				patterns.put(key, Token_value);
			}
		}
		return patterns;
	}

	/**
	 * 
	 * @param machineName
	 * @return true if the machine name appears into system.properties file
	 */
	@SuppressWarnings("unchecked")
	public static boolean existsMachineOnPropertiesFile(String machineName) {
		Properties system_properties = getSystemProperties();
		
		try {
			Enumeration propertiesNames = system_properties.propertyNames();
			while (propertiesNames.hasMoreElements()) {
				String key = (String) propertiesNames.nextElement();
				if (key.contains(machineName)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
