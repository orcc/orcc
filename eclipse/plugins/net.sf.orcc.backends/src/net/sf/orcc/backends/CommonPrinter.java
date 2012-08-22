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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a printer for "standard" objects, namely actors,
 * instances, and networks. This class supports caching in order not to
 * regenerate all files all the time, which can be annoying.
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * @author Antoine Lorence
 * 
 */
public class CommonPrinter {

	protected boolean keepUnchangedFiles;

	protected Map<String, Object> options;

	/**
	 * Creates a new network printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	protected CommonPrinter() {
		options = new HashMap<String, Object>();
	}

	protected CommonPrinter(boolean keepUnchangedFiles) {
		this();
		this.keepUnchangedFiles = keepUnchangedFiles;
	}

	/**
	 * Returns the map containing options passed to backend printer
	 * 
	 * @return the options map
	 */
	public Map<String, Object> getOptions() {
		return options;
	}

	/**
	 * Returns the time of the most recently modified file in the hierarchy.
	 * 
	 * @deprecated this method should not work anymore. Use
	 *             <code>getLastModified(Actor)</code> or
	 *             <code>getLastModified(Network)</code> instead
	 * @param instance
	 *            an instance
	 * @return the time of the most recently modified file in the hierarchy
	 */
	@Deprecated
	protected long getLastModifiedHierarchy(Instance instance) {
		long instanceModified = 0;
		if (instance.isActor()) {
			Actor actor = instance.getActor();
			if (actor.getFileName() == null) {
				// if source file does not exist, force to generate
				instanceModified = Long.MAX_VALUE;
			} else {
				IFile file;
				if (instance.isActor()) {
					file = instance.getActor().getFile();
				} else if (instance.isNetwork()) {
					file = instance.getNetwork().getFile();
				} else {
					return Long.MAX_VALUE;
				}
				instanceModified = file.getLocalTimeStamp();
			}
		} else if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			instanceModified = network.getFile().getLocalTimeStamp();
		}

		EObject cter = instance.eContainer();
		if (cter instanceof Network) {
			Network network = (Network) cter;
			long parentModif;
			if (network.getFile() != null) {
				parentModif = network.getFile().getLocalTimeStamp();
			} else {
				parentModif = Long.MAX_VALUE;
			}
			return Math.max(parentModif, instanceModified);
		} else {
			return instanceModified;
		}
	}

	/**
	 * Returns the time of the last modification on an Actor
	 * 
	 * @param actor
	 *            an actor
	 * @return the time of the last modification
	 */
	protected long getLastModified(Actor actor) {
		IFile actorFile = actor.getFile();
		if (actorFile == null) {
			// if source file does not exist, force to generate
			return Long.MAX_VALUE;
		} else {
			return actorFile.getLocalTimeStamp();
		}

	}

	/**
	 * Returns the time of the last modification on a Network
	 * 
	 * @param network
	 *            a network
	 * @return the time of the last modification
	 */
	protected long getLastModified(Network network) {
		IFile networkFile = network.getFile();
		if (networkFile == null) {
			// if source file does not exist, force to generate
			return Long.MAX_VALUE;
		} else {
			return networkFile.getLocalTimeStamp();
		}

	}

	/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param filePath
	 *            complete path of the file
	 * @return true if the file has correctly been written
	 */
	public static boolean printFile(String content, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(filePath));
			ps.print(content);
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param filePath
	 *            complete path of the file
	 * @return true if the file has correctly been written
	 */
	public static boolean printFile(CharSequence content, String filePath) {
		return printFile(content.toString(), filePath);
	}
}
