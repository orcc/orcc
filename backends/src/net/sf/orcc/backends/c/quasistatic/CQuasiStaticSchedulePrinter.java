package net.sf.orcc.backends.c.quasistatic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.orcc.backends.TemplateGroupLoader;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

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
public class CQuasiStaticSchedulePrinter {

	protected StringTemplateGroup group;

	protected HashMap<String, List<String>> scheduleMap;

	protected StringTemplate template;

	/**
	 * Creates a new network printer with the template "C.st".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CQuasiStaticSchedulePrinter() throws IOException {
		this("C_quasistatic_core");
	}

	/**
	 * Creates a new network printer using the given template file name.
	 * 
	 * @param name
	 *            The template file name.
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected CQuasiStaticSchedulePrinter(String name) throws IOException {
		group = new TemplateGroupLoader().loadGroup(name);
	}

	private List<String> getListOfMethods() {
		List<String> methods = new ArrayList<String>();
		for (String key : scheduleMap.keySet()) {
			List<String> actions = scheduleMap.get(key);
			for (int index = 0; index < actions.size(); index++) {
				String action = actions.get(index);
				if (!(methods.contains(action)))
					methods.add(action);
			}
		}
		return methods;
	}

	public void printSchedule(String fileName,
			HashMap<String, List<String>> scheduleMap) throws IOException {
		this.scheduleMap = scheduleMap;
		template = group.getInstanceOf("scheduling");
		setDeclarations();
		setSchedule();

		byte[] b = template.toString(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

	private void setDeclarations() {
		List<String> methods = getListOfMethods();
		for (String method : methods) {
			StringTemplate inputTmpl = group.getInstanceOf("inputTmpl");
			inputTmpl.setAttribute("methodName", method);
			template.setAttribute("declarations", inputTmpl);
		}
	}

	private void setSchedule() {
		for (String key : scheduleMap.keySet()) {
			List<String> statements = scheduleMap.get(key);
			StringTemplate btype_method = group.getInstanceOf("btype_method");
			btype_method.setAttribute("btypeName", key);
			btype_method.setAttribute("statements", statements);
			template.setAttribute("scheduleCode", btype_method);
		}
	}
}
