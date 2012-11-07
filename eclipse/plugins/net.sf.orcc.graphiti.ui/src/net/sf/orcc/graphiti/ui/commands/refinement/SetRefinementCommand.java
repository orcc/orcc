/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.commands.refinement;

import net.sf.orcc.graphiti.model.IRefinementPolicy;
import net.sf.orcc.graphiti.model.Vertex;
import net.sf.orcc.graphiti.ui.editparts.VertexEditPart;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * This class provides a way to create a vertex refinement.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SetRefinementCommand extends Command {

	private String refinement;

	private Vertex vertex;

	private IRefinementPolicy policy;

	@Override
	public boolean canExecute() {
		if (vertex == null) {
			return false;
		} else {
			return policy.isRefinable(vertex);
		}
	}

	@Override
	public void execute() {
		// save old value of refinement in refinement
		// allows execute() to be executed by undo()
		refinement = policy.setRefinement(vertex, refinement);
	}

	@Override
	public String getLabel() {
		return "Set refinement";
	}

	/**
	 * Runs this command, but do not actually change the refinement. This will
	 * be done by execute.
	 */
	public boolean run() {
		String refinement = policy.getRefinement(vertex);
		String newRefinement = policy.getNewRefinement(vertex);
		if (newRefinement != null && !newRefinement.equals(refinement)) {
			this.refinement = newRefinement;
			return true;
		}
		
		return false;
	}

	/**
	 * @see RefinementManager#setSelection(ISelection)
	 */
	public void setSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			if (obj instanceof VertexEditPart) {
				// we are dealing with a vertex edit part
				vertex = (Vertex) ((VertexEditPart) obj).getModel();
				policy = vertex.getConfiguration().getRefinementPolicy();
			}
		}
	}

	@Override
	public void undo() {
		execute();
	}

}
