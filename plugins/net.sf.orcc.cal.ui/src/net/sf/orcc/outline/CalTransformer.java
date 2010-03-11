/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.outline;

import java.util.List;

import net.sf.orcc.cal.Action;
import net.sf.orcc.cal.Actor;
import net.sf.orcc.cal.Priority;
import net.sf.orcc.cal.Schedule;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.common.editor.outline.ContentOutlineNode;
import org.eclipse.xtext.ui.common.editor.outline.CreateNode;
import org.eclipse.xtext.ui.common.editor.outline.transformer.AbstractDeclarativeSemanticModelTransformer;

/**
 * customization of the default outline structure
 * 
 */
public class CalTransformer extends AbstractDeclarativeSemanticModelTransformer {

	@CreateNode
	public ContentOutlineNode createNode(Actor actor,
			ContentOutlineNode parentNode) {
		ContentOutlineNode node = super.newOutlineNode(actor, parentNode);

		createNodes(node, "Parameters", actor.getParameters());
		createNodes(node, "Input ports", actor.getInputs());
		createNodes(node, "Output ports", actor.getOutputs());
		createNodes(node, "State variables", actor.getStateVariables());
		createNodes(node, "Functions", actor.getFunctions());
		createNodes(node, "Procedures", actor.getProcedures());
		createNodes(node, "Actions", actor.getActions());
		
		Schedule schedule = actor.getSchedule();
		if (schedule != null) {
			createNodes(node, "FSM", schedule.getTransitions());
		}

		List<Priority> priorities = actor.getPriorities();
		if (!priorities.isEmpty()) {
			createNodes(node, "Priorities", priorities.get(0).getInequalities());
		}

		return node;
	}

	private void createNodes(ContentOutlineNode parent, String name,
			EList<?> objects) {
		if (!objects.isEmpty()) {
			ContentOutlineNode node = new ContentOutlineNode(name);
			parent.addChildren(node);
			for (Object obj : objects) {
				createNode((EObject) obj, node);
			}
		}
	}

	public List<EObject> getChildren(Action action) {
		return NO_CHILDREN;
	}

	public List<EObject> getChildren(Actor actor) {
		return NO_CHILDREN;
	}

}
