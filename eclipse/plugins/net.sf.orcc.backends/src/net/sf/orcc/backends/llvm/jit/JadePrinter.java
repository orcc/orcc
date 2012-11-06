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
package net.sf.orcc.backends.llvm.jit;

import java.io.File;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.llvm.aot.LLVMPrinter;
import net.sf.orcc.df.Actor;

/**
 * @author Antoine Lorence
 * 
 */
public class JadePrinter extends LLVMPrinter {

	/**
	 * @param keepUnchangedFiles
	 */
	public JadePrinter(boolean keepUnchangedFiles) {
		super(keepUnchangedFiles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.CommonPrinter#print(java.lang.String,
	 * net.sf.orcc.df.Actor)
	 */
	@Override
	public boolean print(String folder, Actor actor) {
		String file = folder + File.separator + actor.getSimpleName();
		File targetActorFile = new File(file);

		if (!actor.isNative()) {

			CharSequence sequence = new ActorPrinter(actor)
					.getActorFileContent();

			if (!needToReplace(targetActorFile, sequence.toString())) {
				return true;
			}
			if (!printFile(sequence, file)) {
				throw new OrccRuntimeException("Unable to write file " + file);
			}
		}
		return false;
	}

}
