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

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.serialize.IRWriter;
import net.sf.orcc.ir.transformations.SSATransformation;

import com.google.inject.Inject;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Frontend {

	@Inject
	private ActorTransformer actorTransformer;

	/**
	 * output folder
	 */
	private File outputFolder;

	/**
	 * Pretty-print the IR
	 */
	private boolean prettyPrint;

	public Frontend() {
	}

	/**
	 * Compiles the given actor which is defined in the given file, and writes
	 * IR to the output folder defined by {@link #setOutputFolder(String)}.
	 * <p>
	 * Note that callers of this method must ensure that the actor has no errors
	 * for it to be properly compiled.
	 * </p>
	 * 
	 * @param file
	 *            name of the file where the actor is defined
	 * @param astActor
	 *            AST of the actor
	 * @throws OrccException
	 */
	public void compile(String file, AstActor astActor) throws OrccException {
		try {
			Actor actor = actorTransformer.transform(file, astActor);
			new SSATransformation().visit(actor);
			new IRWriter(actor).write(outputFolder.toString(), prettyPrint);
		} catch (OrccRuntimeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the output folder to the given absolute path. The method will create
	 * the folder if it does not exist.
	 * 
	 * @param outputFolder
	 *            absolute path of an output folder
	 */
	public void setOutputFolder(String outputFolder) {
		this.outputFolder = new File(outputFolder);
		if (!this.outputFolder.exists()) {
			this.outputFolder.mkdir();
		}
	}

	public void setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}

}
