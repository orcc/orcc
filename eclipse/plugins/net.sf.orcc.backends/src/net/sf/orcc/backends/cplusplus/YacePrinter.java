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
package net.sf.orcc.backends.cplusplus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.backends.util.XcfPrinter;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

/**
 * This class defines a printer for "standard" objects, namely actors,
 * instances, and networks. This class supports caching in order not to
 * regenerate all files all the time, which can be annoying.
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class YacePrinter extends CommonPrinter {

	private boolean keepUnchangedFiles;

	/**
	 * Creates a new network printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	public YacePrinter() {
		super();
	}

	public YacePrinter(boolean keepUnchangedFiles) {
		super(keepUnchangedFiles);
	}

	@Override
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
	@Override
	public boolean print(String path, Instance instance) {
		String file = path + File.separator + instance.getName() + ".h";
		if (instance.isNetwork()
				|| (instance.isActor() && !instance.getActor().isNative())) {
			if (keepUnchangedFiles) {
				// if source file is older than target file, do not generate
				long sourceLastModified;
				if (instance.isActor()) {
					sourceLastModified = getLastModified(instance.getActor());
				} else {
					sourceLastModified = getLastModified(instance.getNetwork());
				}
				File targetFile = new File(file);
				long targetLastModified = targetFile.lastModified();
				if (sourceLastModified < targetLastModified) {
					return true;
				}
			}
			CharSequence sequence = new ActorPrinter()
					.compileInstance(instance);
			printFile(sequence, file);
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
	@Override
	public boolean print(String path, Network network) {
		String file = path + File.separator + network.getSimpleName() + ".cpp";
		if (keepUnchangedFiles) {
			// if source file is older than target file, do not generate
			long sourceTimeStamp = network.getFile().getLocalTimeStamp();
			File targetFile = new File(file);
			if (sourceTimeStamp < targetFile.lastModified()) {
				return true;
			}
		}
		CharSequence sequence = new NetworkPrinter().compileNetwork(network,
				options);
		printFile(sequence, file);

		if (options.containsKey("threads")) {
			@SuppressWarnings("unchecked")
			Map<String, List<Instance>> instanceToCoreMap = (Map<String, List<Instance>>) options
					.get("threads");

			new XcfPrinter(instanceToCoreMap).printXcfFile(path
					+ File.separator + network.getSimpleName() + ".xcf");
		}

		file = path + File.separator + "CMakeLists.txt";
		sequence = new NetworkPrinter().compileCmakeLists(network, options);
		printFile(sequence, file);
		return false;
	}
}
