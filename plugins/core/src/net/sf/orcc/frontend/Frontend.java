/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.frontend;

import java.io.File;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.frontend.transforms.AstToIR;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRWriter;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Frontend {

	/**
	 * output folder
	 */
	private File outputFolder;

	public Frontend(String outputFolder) {
		this.outputFolder = new File(outputFolder);
	}

	public void compile(String file, AstActor astActor) throws OrccException {
		// only compile if actor has no errors
		List<Diagnostic> errors = astActor.eResource().getErrors();
		if (errors.isEmpty()) {
			try {
				Actor actor = new AstToIR().transform(file, astActor);
				new IRWriter(actor).write(outputFolder.toString());
			} catch (OrccRuntimeException e) {
				throw new OrccException(e.getMessage(), e);
			}
		}
	}

}
