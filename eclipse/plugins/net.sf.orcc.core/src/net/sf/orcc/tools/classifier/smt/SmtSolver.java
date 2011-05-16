/*
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
package net.sf.orcc.tools.classifier.smt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

/**
 * This class defines an interface to an SMT solver compatible with SMT-LIB v2.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SmtSolver {

	private IFolder output;

	private String prefix;

	public SmtSolver(IFile file) {
		output = OrccUtil.getOutputFolder(file.getProject());
		prefix = file.getFullPath().removeFileExtension().lastSegment();
	}

	public boolean checkSat(List<SmtScript> scripts) {
		int i = 0;

		try {
			for (SmtScript script : scripts) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(bos);
				ps.println("(set-logic " + String.valueOf(script.getLogic())
						+ ")");
				for (String command : script.getCommands()) {
					ps.println(command);
				}

				ps.close();

				IFile file = output.getFile(prefix + "_" + i + ".smt2");
				InputStream source = new ByteArrayInputStream(bos.toByteArray());
				OrccUtil.setFileContents(file, source);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return false;
	}

}
