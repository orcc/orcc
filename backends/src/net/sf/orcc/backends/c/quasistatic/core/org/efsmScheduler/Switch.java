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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers.PropertiesParser;

/**
 * Represents the switch of the system(also called Parameter P).
 * 
 * @author vimartin
 */
public class Switch {

	private static final String TOKEN_VALUE = "TOKEN_VALUE";

	private static Properties systemProperties = PropertiesParser
			.getSystemProperties();

	private HashMap<String, Integer> values;

	private int token;

	private static Switch instance;

	/**
	 * 
	 * @return the singleton instance of Switch
	 */
	public static Switch getInstance() {
		if (instance == null)
			instance = new Switch();

		return instance;
	}

	/**
	 * resets the singleton instance
	 */
	public static void resetInstance() {
		instance = null;
		systemProperties = PropertiesParser.getSystemProperties();
	}

	/**
	 * Default constructor
	 */
	public Switch() {
		init();
		token = getTokenValueFromPropertiesFile();
	}

	/**
	 * Constructor
	 * 
	 * @param token
	 *            Token value
	 */
	public Switch(int token) {
		this.token = token;
		init();
	}

	/**
	 * 
	 * @return Token value
	 */
	private int getTokenValueFromPropertiesFile() {
		return Integer.parseInt(systemProperties.getProperty(TOKEN_VALUE), 2);
	}

	/**
	 * Init of Switch
	 */
	private void init() {
		fillValuesFromPropertiesFile();
	}

	/**
	 * 
	 * @return True if exists a switch parameter in the network
	 */
	public boolean existsSwitchInNetwork() {
		String exists = systemProperties.getProperty("SWITCH");
		return Boolean.parseBoolean(exists);
	}

	/**
	 * Fills values from system.properties file
	 */
	@SuppressWarnings("unchecked")
	private void fillValuesFromPropertiesFile() {
		values = new HashMap<String, Integer>();
		String switchName = systemProperties.getProperty("SWITCH_NAME");
		Enumeration propertiesNames = systemProperties.propertyNames();
		while (propertiesNames.hasMoreElements()) {
			String key = (String) propertiesNames.nextElement();
			// If property name starts with <XNLFileName>_<switchName>, then it
			// is a key of a switch value
			if (key.startsWith(switchName)) {
				Integer value = Integer.parseInt(systemProperties
						.getProperty(key));
				key = key.split("_")[1];
				values.put(key, value);
			}
		}

	}

	/**
	 * 
	 * @param key
	 *            property name.
	 * @return value of property or null if this property does not exist
	 */
	public Integer getValue(String key) {
		if (values.containsKey(key))
			return values.get(key);
		else
			return null;
	}

	/**
	 * 
	 * @return value of parameter P. This value is gotten from system.properties
	 *         file
	 */
	public String getSwitchName() {
		return systemProperties.getProperty("SWITCH_NAME");
	}

	/**
	 * 
	 * @return value of token
	 */
	public int getToken(String varName) {
		int value = token;
		String switchValue = getSwitchType();
		if (varName.equals("i")) {
			value = (switchValue.equals(Constants.INTRA) || switchValue
					.equals(Constants.INTRA_AND_INTER)) ? 1 : -1;
		} else if (varName.equals("s")) {
			if (switchValue.equals(Constants.NEWVOP))
				value = -2;
			else if ((switchValue.equals(Constants.ZEROMV) || switchValue
					.equals(Constants.INTER)))
				value = -1;
			else
				value = 0;
		}
		return value;
	}

	/**
	 * Sets the token value
	 * 
	 * @param token
	 *            new token value
	 */
	public void setToken(int token) {
		this.token = token;
	}

	/**
	 * Takes the actual name of the switch and updates the value of token
	 * 
	 * @param newSwitch
	 *            name of switch
	 */
	public void setNewSwitchValue(String newSwitch) {
		int newValue = -1;
		if (newSwitch.equals(Constants.NEWVOP))
			newValue = Integer.parseInt("100000000000", 2);
		else if (newSwitch.equals(Constants.ZEROMV))
			newValue = Integer.parseInt("000000000000", 2);
		else if (newSwitch.equals(Constants.INTER))
			newValue = Integer.parseInt("000000001000", 2);
		else if (newSwitch.equals(Constants.INTRA))
			newValue = Integer.parseInt("010000000000", 2);
		else if (newSwitch.equals(Constants.INTRA_AND_INTER))
			newValue = Integer.parseInt("000000001010", 2);
		setToken(newValue);
	}

	public static int getSwitchValue(String newSwitch) {
		int newValue = -1;
		if (newSwitch.equals(Constants.NEWVOP))
			newValue = Integer.parseInt("100000000000", 2);
		else if (newSwitch.equals(Constants.ZEROMV))
			newValue = Integer.parseInt("000000000000", 2);
		else if (newSwitch.equals(Constants.INTER))
			newValue = Integer.parseInt("000000001000", 2);
		else if (newSwitch.equals(Constants.INTRA))
			newValue = Integer.parseInt("010000000000", 2);
		else if (newSwitch.equals(Constants.INTRA_AND_INTER))
			newValue = Integer.parseInt("000000001010", 2);
		return newValue;
	}

	public static ArrayList<String> getSwitchValues() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(Constants.INTER);
		list.add(Constants.INTRA);
		list.add(Constants.INTRA_AND_INTER);
		list.add(Constants.ZEROMV);
		list.add(Constants.NEWVOP);
		return list;
	}

	/**
	 * @return the switch type using token and values
	 * 
	 */
	public String getSwitchType() {
		String type;
		if (MathExtended.bitAndNotEqualsZero(token, 2048))
			type = Constants.NEWVOP;
		else {
			if (MathExtended.bitAndNotEqualsZero(token, 1024)) {
				type = Constants.INTRA;
			} else {
				if (MathExtended.bitAndNotEqualsZero(token, 8)) {
					if (MathExtended.bitAndNotEqualsZero(token, 2))
						type = Constants.INTRA_AND_INTER;
					else
						type = Constants.INTER;
				} else {
					type = Constants.ZEROMV;
				}
			}
		}
		return type;
	}

	/**
	 * 
	 * @return binary value of token
	 */
	@Override
	public String toString() {
		return Integer.toBinaryString(token);
	}

}
