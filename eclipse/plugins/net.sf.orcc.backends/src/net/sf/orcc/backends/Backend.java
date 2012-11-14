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

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This interface defines a back-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface Backend {

	/**
	 * Launches a compilation using the options provided to this back-end.
	 */
	void compile();

	/**
	 * Export runtime library used by source produced. Should be overridden by
	 * back-ends that produce code source which need third party libraries at
	 * runtime.
	 * 
	 * @return <code>true</code> if the libraries were correctly exported
	 */
	boolean exportRuntimeLibrary();

	/**
	 * Register options set on eclipse "Run configuration" or command line to
	 * use it while code generation process.
	 * 
	 * @param options
	 *            a map of string to object
	 */
	public void setOptions(Map<String, Object> options);

	/**
	 * Sets the progress monitor used by this back-end.
	 * 
	 * @param monitor
	 *            a progress monitor
	 */
	void setProgressMonitor(IProgressMonitor monitor);

}
