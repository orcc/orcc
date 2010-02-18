/*
 * Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.orcc.backends.TemplateGroupLoader;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Printer;
import net.sf.orcc.ir.Type;
import net.sf.orcc.util.INameable;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * This class defines a VHDL actor printer.
 * 
 * @author Nicolas Siret
 * 
 */
public final class VHDLActorPrinter extends Printer {

	public static Pattern adjacent_ = Pattern.compile("_+");

	private StringTemplateGroup group;

	private Map<String, String> transformations;

	/**
	 * Creates a new network printer with the template "VHDL_actor".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public VHDLActorPrinter() {
		this("VHDL_actor");

		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_1");
		transformations.put("access", "access_1");
		transformations.put("component", "component_1");
		transformations.put("select", "select_1");
	}

	/**
	 * Creates a new network printer using the given template file name.
	 * 
	 * @param name
	 *            The template file name.
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected VHDLActorPrinter(String name) {
		group = new TemplateGroupLoader().loadGroup(name);

		// registers this printer as the default printer
		Printer.register(this);
	}

	/**
	 * Prints the given actor to a file whose name is given.
	 * 
	 * @param fileName
	 *            output file name
	 * @param id
	 *            the instance id
	 * @param actor
	 *            actor to print
	 * @throws IOException
	 */
	public void printActor(String fileName, String id, Actor actor)
			throws IOException {
		StringTemplate template = group.getInstanceOf("actor");

		template.setAttribute("actorName", id);
		template.setAttribute("actor", actor);

		byte[] b = template.toString(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

	@Override
	public String toString(Constant constant) {
		VHDLConstPrinter printer = new VHDLConstPrinter(group);
		constant.accept(printer);
		return printer.toString();
	}

	@Override
	public String toString(Expression expression) {
		VHDLExpressionPrinter printer = new VHDLExpressionPrinter();
		expression.accept(printer, Integer.MAX_VALUE);
		return printer.toString();
	}

	@Override
	public String toString(INameable nameable) {
		String name = nameable.getName();
		if (transformations.containsKey(name)) {
			return transformations.get(name);
		} else {
			// replaces adjacent underscores by a single underscore
			return adjacent_.matcher(name).replaceAll("_");
		}
	}

	@Override
	public String toString(Type type) {
		VHDLTypePrinter printer = new VHDLTypePrinter();
		type.accept(printer);
		return printer.toString();
	}

}
