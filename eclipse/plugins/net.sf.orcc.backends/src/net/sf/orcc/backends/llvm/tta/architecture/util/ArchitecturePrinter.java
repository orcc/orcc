/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta.architecture.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.AbstractPrinter;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;

import org.stringtemplate.v4.ST;

/**
 * This class defines a printer for architecture objects, namely designs, and
 * processors.
 * 
 * @author Herve Yviquel
 * 
 */
public class ArchitecturePrinter extends AbstractPrinter {

	protected Map<String, Object> options;

	/**
	 * Creates a new network printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	public ArchitecturePrinter(String templateName) {
		super(templateName);

		options = new HashMap<String, Object>();
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	/**
	 * Prints the given design to a file whose name and path are given.
	 * 
	 * @param fileName
	 *            name of the output file
	 * @param path
	 *            path of the output file
	 * @param design
	 *            the design to generate code for
	 * @return always <code>false</code>
	 */
	public boolean print(String fileName, String path, Design design) {
		String file = path + File.separator + fileName;

		ST template = group.getInstanceOf("printDesign");
		template.add("design", design);
		template.add("options", options);
		printTemplate(template, file);

		return false;
	}

	/**
	 * Prints the given design to a file whose name and path are given.
	 * 
	 * @param fileName
	 *            name of the output file
	 * @param path
	 *            path of the output file
	 * @param design
	 *            the design to generate code for
	 * @return always <code>false</code>
	 */
	public boolean print(String fileName, String path, Processor processor) {
		String file = path + File.separator + fileName;

		ST template = group.getInstanceOf("printProcessor");
		template.add("processor", processor);
		template.add("options", options);
		printTemplate(template, file);

		return false;
	}

}
