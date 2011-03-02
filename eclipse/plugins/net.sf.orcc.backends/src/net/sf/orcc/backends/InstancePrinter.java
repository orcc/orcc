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

import net.sf.orcc.ir.Actor;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;

import org.stringtemplate.v4.ST;

/**
 * This class defines an instance printer.
 * 
 * @author Herve Yviquel
 * 
 */
public class InstancePrinter extends Printer {

	private boolean keepUnchangedFiles = false;

	/**
	 * Creates a new instance printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	public InstancePrinter(String templateName) {
		this(templateName, false);
	}

	/**
	 * Creates a new instance printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 * @param keepUnchangedFiles
	 *            if the printer must keep printing files from unchanged
	 *            instances
	 */
	public InstancePrinter(String templateName, boolean keepUnchangedFiles) {
		super(templateName);
		this.keepUnchangedFiles = keepUnchangedFiles;
	}

	/**
	 * Returns the time of the most recently modified file in the hierarchy.
	 * 
	 * @param instance
	 *            an instance
	 * @return the time of the most recently modified file in the hierarchy
	 */
	private long getLastModifiedHierarchy(Instance instance) {
		long instanceModified = 0;
		if (instance.isActor()) {
			Actor actor = instance.getActor();
			File actorFile = new File(actor.getFile());
			instanceModified = actorFile.lastModified();
		} else if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			File networkFile = new File(network.getFile());
			instanceModified = networkFile.lastModified();
		}

		Instance parent = instance.getParent();
		if (parent != null) {
			long parentModif = getLastModifiedHierarchy(parent);
			return Math.max(parentModif, instanceModified);
		} else {
			return instanceModified;
		}
	}

	/**
	 * Prints the given instance to a file whose name and path are given.
	 * 
	 * @param fileName
	 *            name of the output file
	 * @param path
	 *            path of the output file
	 * @param instance
	 *            the instance to generate code for
	 * @param instanceName
	 *            name of the root ST rule
	 * @return <code>true</code> if the instance was cached
	 */
	public boolean print(String fileName, String path, Instance instance,
			String instanceName) {
		String file = path + File.separator + fileName;
		if (instance.isNetwork()
				|| (instance.isActor() && !instance.getActor().isNative())) {
			if (keepUnchangedFiles) {
				// if source file is older than target file, do not generate
				long sourceLastModified = getLastModifiedHierarchy(instance);
				File targetFile = new File(file);
				long targetLastModified = targetFile.lastModified();
				if (sourceLastModified < targetLastModified) {
					return true;
				}
			}
			ST template = group.getInstanceOf(instanceName);
			template.add("instance", instance);
			printTemplate(template, file);
		}
		return false;
	}

}
