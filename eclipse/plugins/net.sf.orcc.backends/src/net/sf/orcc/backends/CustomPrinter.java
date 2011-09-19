/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import java.io.File;

import org.stringtemplate.v4.ST;

/**
 * This class defines a printer for arbitrary objects. When printing standard
 * objects such as actors, instances, and networks, the StandardPrinter class
 * should be used instead of this one.
 * 
 * @author Matthieu Wipliez
 * @see StandardPrinter
 */
public class CustomPrinter extends AbstractPrinter {

	/**
	 * Creates a new printer.
	 * 
	 * @param fullPath
	 *            the full path of the template
	 */
	public CustomPrinter(String fullPath) {
		super(fullPath);
	}

	/**
	 * Prints the given network to a file whose name and path are given.
	 * 
	 * @param fileName
	 *            name of the output file
	 * @param path
	 *            path of the output file
	 * @param tmplName
	 *            name of the template to apply
	 * @param attributes
	 *            attributes to the template name1, val1, name2, val2...
	 */
	public void print(String fileName, String path, String tmplName,
			Object... attributes) {
		ST template = group.getInstanceOf(tmplName);
		for (int i = 0; i < attributes.length - 1; i += 2) {
			template.add(String.valueOf(attributes[i]), attributes[i + 1]);
		}

		printTemplate(template, path + File.separator + fileName);
	}

}
