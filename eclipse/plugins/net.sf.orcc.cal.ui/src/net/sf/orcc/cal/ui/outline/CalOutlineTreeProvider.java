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
package net.sf.orcc.cal.ui.outline;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Inequality;
import net.sf.orcc.cal.cal.Priority;
import net.sf.orcc.cal.cal.ScheduleFsm;
import net.sf.orcc.cal.cal.Variable;

import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;

import com.google.inject.Inject;

/**
 * customization of the default outline structure
 * 
 */
public class CalOutlineTreeProvider extends DefaultOutlineTreeProvider {

	@Inject
	private IImageHelper imageHelper;

	protected void _createNode(IOutlineNode parentNode, AstActor actor) {
		createEStructuralFeatureNode(parentNode, actor,
				CalPackage.eINSTANCE.getAstActor_Parameters(), null,
				"parameters", false);

		createEStructuralFeatureNode(parentNode, actor,
				CalPackage.eINSTANCE.getAstActor_Inputs(), null, "input ports",
				false);

		createEStructuralFeatureNode(parentNode, actor,
				CalPackage.eINSTANCE.getAstActor_Outputs(), null,
				"output ports", false);

		createEStructuralFeatureNode(parentNode, actor,
				CalPackage.eINSTANCE.getAstActor_StateVariables(), null,
				"state variables", false);

		if (!actor.getFunctions().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor,
					CalPackage.eINSTANCE.getAstActor_Functions(), null,
					"functions", false);
		}

		if (!actor.getProcedures().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor,
					CalPackage.eINSTANCE.getAstActor_Procedures(), null,
					"procedures", false);
		}

		createEStructuralFeatureNode(parentNode, actor,
				CalPackage.eINSTANCE.getAstActor_Actions(), null, "actions",
				false);

		if (actor.getScheduleFsm() != null) {
			createEStructuralFeatureNode(parentNode, actor,
					CalPackage.eINSTANCE.getAstActor_ScheduleFsm(), null,
					"FSM", false);
		}

		if (!actor.getPriorities().isEmpty()) {
			createEStructuralFeatureNode(parentNode, actor,
					CalPackage.eINSTANCE.getAstActor_Priorities(), null,
					"priorities", false);
		}
	}

	protected void _createNode(IOutlineNode parentNode, AstEntity entity) {
		createEStructuralFeatureNode(parentNode, entity,
				CalPackage.eINSTANCE.getAstEntity_Package(), _image(entity),
				entity.getPackage(), true);

		createEStructuralFeatureNode(parentNode, entity,
				CalPackage.eINSTANCE.getAstEntity_Imports(),
				imageHelper.getImage("impc_obj.gif"), "import declarations",
				false);

		String name = entity.getName();
		if (entity.getActor() != null) {
			createEStructuralFeatureNode(parentNode, entity,
					CalPackage.eINSTANCE.getAstEntity_Actor(),
					_image(entity.getActor()), name, false);
		} else if (entity.getUnit() != null) {
			createEStructuralFeatureNode(parentNode, entity,
					CalPackage.eINSTANCE.getAstEntity_Unit(), null, name, false);
		}
	}

	protected void _createNode(IOutlineNode parentNode, AstUnit unit) {
		createEStructuralFeatureNode(parentNode, unit,
				CalPackage.eINSTANCE.getAstUnit_Variables(), null, "variables",
				false);

		if (!unit.getFunctions().isEmpty()) {
			createEStructuralFeatureNode(parentNode, unit,
					CalPackage.eINSTANCE.getAstUnit_Functions(), null,
					"functions", false);
		}

		if (!unit.getProcedures().isEmpty()) {
			createEStructuralFeatureNode(parentNode, unit,
					CalPackage.eINSTANCE.getAstUnit_Procedures(), null,
					"procedures", false);
		}
	}

	protected void _createNode(IOutlineNode parentNode, Priority priority) {
		for (Inequality inequality : priority.getInequalities()) {
			createNode(parentNode, inequality);
		}
	}

	protected void _createNode(IOutlineNode parentNode, ScheduleFsm schedule) {
		for (AstTransition transition : schedule.getContents().getTransitions()) {
			createNode(parentNode, transition);
		}
	}

	protected boolean _isLeaf(AstAction action) {
		return true;
	}

	protected boolean _isLeaf(AstPort port) {
		return true;
	}

	protected boolean _isLeaf(AstProcedure procedure) {
		return true;
	}

	protected boolean _isLeaf(AstTransition transition) {
		return true;
	}

	protected boolean _isLeaf(Function function) {
		return true;
	}

	protected boolean _isLeaf(Variable variable) {
		return true;
	}

}
