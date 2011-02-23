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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.tools;

import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;

public interface NetworkAnalyzer {
	/**
	 * Analyzes the VTL by loading IR files, transforming actors and printing
	 * them.
	 * 
	 * @param process
	 *            the process that launched the back-end, so we can report
	 *            messages to it
	 * @param vtlFolders
	 *            absolute path of folders that contains IR files
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void analyzeVTL(OrccProcess process, List<String> vtlFolders)
			throws OrccException;

	/**
	 * Loads a hierarchical XDF network and analyze it. Analyze may include
	 * instantiation of the network, flattening it, transforming it, analyze it
	 * and printing the result, or a subset of these steps.
	 * 
	 * @param process
	 *            the process that launched the analyzer, so we can report
	 *            messages to it
	 * @param inputFile
	 *            absolute path of top-level input network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void analyzeXDF(OrccProcess process, String inputFile) throws OrccException;

	/**
	 * Sets the output folder of this back-end. This is the folder where files
	 * will be generated.
	 * 
	 * @param outputFolder
	 *            output folder
	 */
	public void setOutputFolder(String outputFolder);
}
