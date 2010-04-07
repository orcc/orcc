/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.xlim;

import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;

/**
 * XlimNames provides additional naming
 * 
 * @author Samuel Keller
 */
public class XlimNames {

	/**
	 * Decision id
	 */
	private static int decision = -1;

	/**
	 * Temp ip
	 */
	private static int temp = -1;

	/**
	 * Parameters map
	 */
	private Map<String, String> params;

	/**
	 * XlimNames default constructor
	 */
	public XlimNames() {
		params = new TreeMap<String, String>();
	}

	/**
	 * XlimNames full constructor to be used in function call
	 * 
	 * @param parent
	 *            Parents XlimNames
	 * @param params
	 *            Parameters sended
	 */
	public XlimNames(XlimNames parent, Map<String, String> params) {
		this.params = params;
	}

	/**
	 * Get the name of a port (port name + action name)
	 * 
	 * @param use
	 *            Use of Port to print
	 * @param actionName
	 *            Action containing the Port
	 * @return Formatted name
	 */
	private String getPortName(Use use, String actionName) {
		return "port_" + use.getVariable().getName() + "_" + actionName;
	}

	/**
	 * Get the name of a port (port name + action name)
	 * 
	 * @param port
	 *            Port to print
	 * @param actionName
	 *            Action containing the Port
	 * @return Formatted name
	 */
	private String getPortName(Variable port, String actionName) {
		return "port_" + port.getName() + "_" + actionName;
	}

	/**
	 * Get current temporary name
	 * 
	 * @return current temporary name
	 */
	public String getTempName() {
		return "temp_" + (temp);
	}

	/**
	 * Get the name of a variable
	 * 
	 * @param use
	 *            Use of Variable
	 * @param actionName
	 *            Action containing the Variable
	 * @return Variable name
	 */
	public String getVarName(Use use, String actionName) {
		Variable var = use.getVariable();
		String varName = var.getName();
		if (params.containsKey(varName)) {
			return params.get(varName);
		}
		if (use.getVariable().isPort()) {
			return getPortName(use, actionName);
		}

		return getVarTemplate(var, actionName);
	}

	/**
	 * Get the name of a variable
	 * 
	 * @param var
	 *            Variable
	 * @param actionName
	 *            Action containing the Variable
	 * @return Variable name
	 */
	public String getVarName(Variable var, String actionName) {
		if (var.isPort()) {
			return getPortName(var, actionName);
		}
		return getVarTemplate(var, actionName);
	}

	/**
	 * Template for getting the name of a variable
	 * 
	 * @param var
	 *            Variable
	 * @param actionName
	 *            Action containing the Variable
	 * @return Variable name
	 */
	private String getVarTemplate(Variable var, String actionName) {
		return "var_" + var.getName()
				+ ((var.isGlobal() ? "" : "_" + actionName));
	}

	/**
	 * Get decision name (based on decision unique id)
	 * 
	 * @return decision name
	 */
	public String putDecision() {
		return "decision_" + (++decision);
	}

	/**
	 * Get new temporary name
	 * 
	 * @return new temporary name
	 */
	public String putTempName() {
		return "temp_" + (++temp);
	}
}
