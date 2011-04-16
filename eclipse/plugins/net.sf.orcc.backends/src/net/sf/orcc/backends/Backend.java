/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import net.sf.orcc.OrccException;
import net.sf.orcc.plugins.Plugin;
import net.sf.orcc.util.WriteListener;

/**
 * This interface defines a back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface Backend extends Plugin {

	/**
	 * Compiles the VTL by loading IR files, transforming actors and printing
	 * them.
	 * 
	 * @param vtlFolders
	 *            absolute path of folders that contains IR files
	 * @throws OrccException
	 *             if something goes wrong
	 */
	void compileVTL(List<String> vtlFolders) throws OrccException;

	/**
	 * Loads a hierarchical XDF network and compile it. Compilation may include
	 * instantiation, flattening, transforming, printing the network, or a
	 * subset of these steps.
	 * 
	 * @param inputFile
	 *            absolute path of top-level input network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	void compileXDF(String inputFile) throws OrccException;

	/**
	 * Sets the options of this backends.
	 * 
	 * @param outputFolder
	 *            output folder
	 */
	public void setOptions() throws OrccException;

	/**
	 * Sets the output folder of this back-end. This is the folder where files
	 * will be generated.
	 * 
	 * @param outputFolder
	 *            output folder
	 */
	public void setOutputFolder(String outputFolder);

	/**
	 * Sets the progress monitor used by this back-end.
	 * 
	 * @param monitor
	 *            a progress monitor
	 */
	void setProgressMonitor(IProgressMonitor monitor);

	/**
	 * Sets the write listener used by this back-end.
	 * 
	 * @param monitor
	 *            a write listener
	 */
	void setWriteListener(WriteListener listener);

}
