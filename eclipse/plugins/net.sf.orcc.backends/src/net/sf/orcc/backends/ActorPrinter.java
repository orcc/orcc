/*
 * Copyright (c) 2011, IRISA
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
package net.sf.orcc.backends;

import java.io.File;
import java.io.IOException;

import net.sf.orcc.ir.Actor;

import org.stringtemplate.v4.ST;

/**
 * This class defines a actor printer.
 * 
 * @author Herve Yviquel
 * 
 */
public class ActorPrinter extends Printer {

	private boolean keepUnchangedFiles = false;

	/**
	 * Creates a new actor printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	public ActorPrinter(String templateName) {
		this(templateName, false);
	}

	/**
	 * Creates a new actor printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 * @param keepUnchangedFiles
	 *            if the printer must keep printing files from unchanged actor
	 */
	public ActorPrinter(String templateName, boolean keepUnchangedFiles) {
		super(templateName);
		this.keepUnchangedFiles = keepUnchangedFiles;
	}

	/**
	 * Prints the given actor to a file whose name and path are given.
	 * 
	 * @param fileName
	 *            name of the output file
	 * @param path
	 *            path of the output file
	 * @param actor
	 *            the actor to generate code for
	 * @param instanceName
	 *            name of the root ST rule
	 * @return <code>true</code> if the actor was cached
	 * @throws IOException
	 *             if there is an I/O error
	 */
	public boolean print(String fileName, String path, Actor actor,
			String instanceName) {
		if (!actor.isNative()) {
			String file = path + File.separator + fileName;
			if (keepUnchangedFiles) {
				// if source file is older than target file, do not generate
				long sourceTimeStamp = 0;
				if(actor.getFileName() == null) {
					sourceTimeStamp = Long.MAX_VALUE;
				} else {
					sourceTimeStamp = actor.getFile().getLocalTimeStamp();					
				}
				File targetFile = new File(file);
				if (sourceTimeStamp < targetFile.lastModified()) {
					return true;
				}
			}
			ST template = group.getInstanceOf(instanceName);
			template.add("actor", actor);
			printTemplate(template, file);
		}
		return false;
	}

}
