/*
 * Copyright (c) 2011, IRISA
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
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

import org.eclipse.emf.ecore.EObject;
import org.stringtemplate.v4.ST;

/**
 * This class defines a printer for "standard" objects, namely actors,
 * instances, and networks. This class supports caching in order not to
 * regenerate all files all the time, which can be annoying.
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * 
 */
public class StandardPrinter extends AbstractPrinter {

	private boolean keepUnchangedFiles;

	protected Map<String, Object> options;

	private boolean printBroadcasts;

	/**
	 * Creates a new network printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	public StandardPrinter(String templateName) {
		super(templateName);

		options = new HashMap<String, Object>();
	}

	/**
	 * Creates a new network printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 * @param keepUnchangedFiles
	 *            if the printer must keep printing files from unchanged
	 *            instances
	 */
	public StandardPrinter(String templateName, boolean keepUnchangedFiles) {
		this(templateName);
		this.keepUnchangedFiles = keepUnchangedFiles;
	}

	/**
	 * Creates a new instance printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 * @param keepUnchangedFiles
	 *            if the printer must keep printing files from unchanged
	 *            instances
	 * @param printBroadcasts
	 *            if the printer have to print broadcast instances
	 */
	public StandardPrinter(String templateName, boolean keepUnchangedFiles,
			boolean printBroadcasts) {
		this(templateName, keepUnchangedFiles);
		this.printBroadcasts = printBroadcasts;
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
			if (actor.getFileName() == null) {
				// if source file does not exist, force to generate
				instanceModified = Long.MAX_VALUE;
			} else {
				instanceModified = instance.getFile().getLocalTimeStamp();
			}
		} else if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			instanceModified = network.getFile().getLocalTimeStamp();
		}

		EObject cter = instance.eContainer();
		if (cter instanceof Network) {
			Network network = instance.getNetwork();
			long parentModif = network.getFile().getLocalTimeStamp();
			return Math.max(parentModif, instanceModified);
		} else {
			return instanceModified;
		}
	}

	public Map<String, Object> getOptions() {
		return options;
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
	 * @return <code>true</code> if the actor was cached
	 * @throws IOException
	 *             if there is an I/O error
	 */
	public boolean print(String fileName, String path, Actor actor) {
		if (!actor.isNative()) {
			String file = path + File.separator + fileName;
			if (keepUnchangedFiles) {
				// if source file is older than target file, do not generate
				long sourceTimeStamp = 0;
				if (actor.getFileName() == null) {
					sourceTimeStamp = Long.MAX_VALUE;
				} else {
					sourceTimeStamp = actor.getFile().getLocalTimeStamp();
				}
				File targetFile = new File(file);
				if (sourceTimeStamp < targetFile.lastModified()) {
					return true;
				}
			}

			ST template = group.getInstanceOf("actor");
			template.add("actor", actor);
			template.add("options", options);
			printTemplate(template, file);
		}
		return false;
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
	 * @return <code>true</code> if the instance was cached
	 */
	public boolean print(String fileName, String path, Instance instance) {
		String file = path + File.separator + fileName;
		if (instance.isNetwork()
				|| (instance.isActor() && !instance.getActor().isNative())
				|| (instance.isBroadcast() && printBroadcasts)) {
			if (keepUnchangedFiles) {
				// if source file is older than target file, do not generate
				long sourceLastModified = getLastModifiedHierarchy(instance);
				File targetFile = new File(file);
				long targetLastModified = targetFile.lastModified();
				if (sourceLastModified < targetLastModified) {
					return true;
				}
			}

			ST template = group.getInstanceOf("instance");
			template.add("instance", instance);
			template.add("options", options);
			printTemplate(template, file);
		}
		return false;
	}

	/**
	 * Prints the given network to a file whose name and path are given.
	 * 
	 * @param fileName
	 *            name of the output file
	 * @param path
	 *            path of the output file
	 * @param network
	 *            the network to generate code for
	 * @param instanceName
	 *            name of the root ST rule
	 * @return <code>true</code> if the network was cached
	 * @throws IOException
	 *             if there is an I/O error
	 */
	public boolean print(String fileName, String path, Network network) {
		String file = path + File.separator + fileName;
		if (keepUnchangedFiles) {
			// if source file is older than target file, do not generate
			long sourceTimeStamp = network.getFile().getLocalTimeStamp();
			File targetFile = new File(file);
			if (sourceTimeStamp < targetFile.lastModified()) {
				return true;
			}
		}

		ST template = group.getInstanceOf("network");
		template.add("network", network);
		template.add("options", options);
		printTemplate(template, file);

		return false;
	}

}
