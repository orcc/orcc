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
package net.sf.orcc.backends.opencl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * @author Endri Bezati
 * 
 */
public class OpenCLPrinter {

	private boolean keepUnchangedFiles;

	protected Map<String, Object> options;

	/**
	 * Creates a new network printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	public OpenCLPrinter() {
		options = new HashMap<String, Object>();
	}

	public OpenCLPrinter(boolean keepUnchangedFiles) {
		this();
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

	public Map<String, Object> getOptions() {
		return options;
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
	public boolean print(String srcPath, String kernelPath, String includePath,
			Instance instance) {
		String file = srcPath + File.separator + instance.getName() + ".cpp";
		String includeFile = includePath + File.separator + instance.getName()
				+ ".hpp";
		String kernelFile = kernelPath + File.separator + instance.getName()
				+ ".cl";
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
			try {
				// Print Host Header file
				CharSequence sequence = new ActorPrinter()
						.printHeader(instance);
				PrintStream ps = new PrintStream(new FileOutputStream(
						includeFile));
				ps.print(sequence.toString());
				ps.close();

				// Print Host source file
				sequence = new ActorPrinter().printInstance(instance);
				ps = new PrintStream(new FileOutputStream(file));
				ps.print(sequence.toString());
				ps.close();

				// Print Kernel
				sequence = new ActorPrinter().printKernel(instance);
				ps = new PrintStream(new FileOutputStream(kernelFile));
				ps.print(sequence.toString());
				ps.close();
			} catch (FileNotFoundException e) {
			}
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
	public boolean print(String path, String srcPath, Network network) {
		try {
			String file = srcPath + File.separator + network.getSimpleName()
					+ ".cpp";
			if (keepUnchangedFiles) {
				// if source file is older than target file, do not generate
				long sourceTimeStamp = network.getFile().getLocalTimeStamp();
				File targetFile = new File(file);
				if (sourceTimeStamp < targetFile.lastModified()) {
					return true;
				}
			}
			CharSequence sequence = new NetworkPrinter().printNetwork(network,
					options);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.print(sequence.toString());
			ps.close();

			file = path + File.separator + "CMakeLists.txt";
			sequence = new NetworkPrinter().printCmakeLists(network, options);
			ps = new PrintStream(new FileOutputStream(file));
			ps.print(sequence.toString());
			ps.close();

		} catch (FileNotFoundException e) {
		}
		return false;
	}

}
