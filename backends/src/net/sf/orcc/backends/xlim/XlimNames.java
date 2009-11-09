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

import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.Use;

public class XlimNames {
	
	
	
	public XlimNames(){
		params = new TreeMap<String, String>();
	}
	
	public XlimNames(XlimNames parent, Map<String, String> params){
		this.params = params;
	}
	
	private static int temp = -1;
	private static int decision = -1;
	
	//private static Map<String, Integer> varcounts = new TreeMap<String, Integer>();
	private Map<String, String> params;
	
	public String getVarName(Variable var){
		/*Integer count = varcounts.get(var.getName());
		if(count == null){
			count = 0;
		}
		else{
			count++;
		}
		varcounts.put(var.getName(),count);*/
		return getVarTemplate(var);
	}
	
	public String getVarName(Use use){
		String var = use.getVariable().getName();
		if(params.containsKey(var)){
			return params.get(var);
		}
		return getVarTemplate(use.getVariable());
	}
	
	private String getVarTemplate(Variable var){
		return "var_" + var.getName();// + "_" + varcounts.get(var.getName());
	}
	
	public String putTempName(){
		return "temp_" + (++temp);
	}
	
	public String getTempName(){
		return "temp_" + (temp);
	}
	
	public String putDecision(){
		return "decision_" + (++decision);
	}
	
	public String getPortName(Use use, String actionName){
		return "port_" + use.toString() + "_" + actionName;
	}
	
	public String getPortName(Port port, String actionName){
		return "port_" + port.getName() + "_" + actionName;
	}
}
