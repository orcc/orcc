/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Printer;
import net.sf.orcc.network.Instance;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

/**
 * This class defines a printer that uses StringTemplate.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class STPrinter extends Printer {

	final protected STGroup group;

	/**
	 * Creates a new StringTemplate printer with the given template group name.
	 * 
	 * @param groupNames
	 *            names of the template groups
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected STPrinter(String... groupNames) {
		group = TemplateGroupLoader.loadGroup(groupNames);

		// registers this printer as the default printer
		Printer.register(this);
	}

	/**
	 * Prints the given actor to a file whose name is given.
	 * 
	 * @param fileName
	 *            output file name
	 * @param actor
	 *            the actor
	 * @throws IOException
	 */
	public void printActor(String fileName, Actor actor) throws IOException {
		if (!actor.isSystem()) {
			ST template = group.getInstanceOf("actor");

			template.add("actor", actor);

			byte[] b = template.render(80).getBytes();
			OutputStream os = new FileOutputStream(fileName);
			os.write(b);
			os.close();
		}
	}

	/**
	 * Prints the given instance to a file whose name is given.
	 * 
	 * @param fileName
	 *            output file name
	 * @param instance
	 *            the instance
	 * @throws IOException
	 */
	public void printInstance(String fileName, Instance instance)
			throws IOException {
		if (!instance.getActor().isSystem()) {
			ST template = group.getInstanceOf("instance");

			template.add("instance", instance);

			byte[] b = template.render(80).getBytes();
			OutputStream os = new FileOutputStream(fileName);
			os.write(b);
			os.close();
		}
	}

}
