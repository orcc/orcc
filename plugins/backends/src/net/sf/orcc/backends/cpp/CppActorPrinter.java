/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.cpp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
 * This class defines a C++ actor printer.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public final class CppActorPrinter extends Printer {

	private StringTemplateGroup group;

	/**
	 * Creates a new actor printer with the given template.
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CppActorPrinter(String name) {
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
		if (!actor.isSystem()) {
			StringTemplate template = group.getInstanceOf("actor");

			template.setAttribute("actorName", id);
			template.setAttribute("actor", actor);

			byte[] b = template.toString(80).getBytes();
			OutputStream os = new FileOutputStream(fileName);
			os.write(b);
			os.close();
		}
	}

	@Override
	public String toString(Constant constant) {
		CppConstPrinter printer = new CppConstPrinter(group);
		constant.accept(printer);
		return printer.toString();
	}

	@Override
	public String toString(Expression expression) {
		CppExprPrinter printer = new CppExprPrinter();
		expression.accept(printer, Integer.MAX_VALUE);
		return printer.toString();
	}

	@Override
	public String toString(INameable nameable) {
		return nameable.getName();
	}

	@Override
	public String toString(Type type) {
		CppTypePrinter printer = new CppTypePrinter();
		type.accept(printer);
		return printer.toString();
	}

}
