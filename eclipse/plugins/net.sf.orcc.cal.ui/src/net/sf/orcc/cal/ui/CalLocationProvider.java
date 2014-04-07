/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.cal.ui;

import java.util.Collections;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstState;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.CalPackage;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.util.ITextRegion;

/**
 * This class provides location for the objects of the AST.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalLocationProvider extends DefaultLocationInFileProvider {

	@Override
	protected EStructuralFeature getIdentifierFeature(EObject obj) {
		// The AstTag is a reference in an AstAction, and this reference is not
		// named 'name' or 'id'. By default, Xtext will select the first ':'
		// char just after the tag if an action is selected in the "Outline"
		// tab. We must configure the AstTag as default identifier for any
		// action
		if (obj instanceof AstAction) {
			return CalPackage.eINSTANCE.getAstAction_Tag();
		}

		return super.getIdentifierFeature(obj);
	}

	@Override
	protected ITextRegion getTextRegion(EObject obj, boolean isSignificant) {
		final RegionDescription query = isSignificant ? RegionDescription.SIGNIFICANT
				: RegionDescription.FULL;

		if (obj instanceof AstState) {
			final INode stateNode = (INode) ((AstState) obj).getNode();
			return createRegion(Collections.singletonList(stateNode), query);

		} else if (obj instanceof AstTransition) {
			final INode transitionNode = NodeModelUtils.getNode(obj);
			return createRegion(Collections.singletonList(transitionNode),
					query);
		}
		return super.getTextRegion(obj, isSignificant);
	}
}
