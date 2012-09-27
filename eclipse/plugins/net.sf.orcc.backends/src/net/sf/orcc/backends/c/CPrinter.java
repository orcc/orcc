/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c;

import java.io.File;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.CommonPrinter;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

import org.eclipse.core.resources.IFile;

/**
 * @author Antoine Lorence
 * 
 */
public class CPrinter extends CommonPrinter {

	/**
	 * 
	 */
	public CPrinter(boolean keepUnchangedFiles) {
		super(keepUnchangedFiles);
		exprPrinter = new CExpressionPrinter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.CommonPrinter#print(java.lang.String,
	 * net.sf.orcc.df.Instance)
	 */
	@Override
	public boolean print(String folder, Instance instance) {
		String file = folder + File.separator + instance.getName() + ".c";

		IFile irFile = instance.getActor().getFile();
		File targetActorFile = new File(file);

		if (!needToReplace(targetActorFile, irFile)) {
			return true;
		}
		CharSequence sequence = "";
		// CharSequence sequence = new ActorPrinter(instance)
		// .getActorFileContent();
		if (!printFile(sequence, file)) {
			throw new OrccRuntimeException("Unable to write file " + file);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.CommonPrinter#print(java.lang.String,
	 * net.sf.orcc.df.Network)
	 */
	@Override
	public boolean print(String folder, Network network) {

		String targetNetworkPath = folder + File.separator
				+ network.getSimpleName() + ".c";

		IFile newFile = network.getFile();
		File targetFile = new File(targetNetworkPath);

		if (!needToReplace(targetFile, newFile))
			return true;

		CharSequence sequence = new NetworkPrinter(network, options)
				.getNetworkFileContent();

		if (!printFile(sequence, targetNetworkPath)) {
			throw new OrccRuntimeException("Unable to write file "
					+ targetNetworkPath);
		}
		return false;
	}

}
