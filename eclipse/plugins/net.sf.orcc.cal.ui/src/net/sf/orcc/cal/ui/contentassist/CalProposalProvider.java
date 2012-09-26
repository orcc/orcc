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
package net.sf.orcc.cal.ui.contentassist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.Inequality;
import net.sf.orcc.cal.cal.InputPattern;
import net.sf.orcc.cal.cal.Priority;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.util.CalActionList;
import net.sf.orcc.cal.util.Util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Predicate;

/**
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#contentAssist on
 * how to customize content assistant
 */
public class CalProposalProvider extends AbstractCalProposalProvider {

	/**
	 * Adds the name of units that contain objects referenceable from the model
	 * using the given reference.
	 * 
	 * @param model
	 *            the model
	 * @param reference
	 *            a reference
	 * @param units
	 *            a set of units
	 */
	private void addUnits(EObject model, EReference reference, Set<String> units) {
		IScope scope = getScopeProvider().getScope(model, reference);
		Iterable<IEObjectDescription> candidates = scope.getAllElements();
		for (IEObjectDescription candidate : candidates) {
			URI uri = candidate.getEObjectURI();
			uri = uri.trimFragment();
			EObject obj = model.eResource().getResourceSet()
					.getResource(uri, true).getContents().get(0);
			if (obj instanceof AstEntity) {
				AstEntity entity = (AstEntity) obj;
				AstUnit unit = entity.getUnit();
				if (unit != null) {
					String name = Util.getQualifiedName(entity);
					units.add(name);
				}
			}
		}
	}

	@Override
	public void complete_QualifiedNameWithWildCard(EObject model,
			RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		if (model != null) {
			// the set of units that contain variables
			Set<String> units = new HashSet<String>();

			// find all variables to which we have access and are in units
			EReference reference = CalPackage.eINSTANCE
					.getVariableReference_Variable();
			addUnits(model, reference, units);

			// find all functions to which we have access and are in units
			reference = CalPackage.eINSTANCE.getExpressionCall_Function();
			addUnits(model, reference, units);

			for (String unit : units) {
				if (!acceptor.canAcceptMoreProposals())
					return;
				ICompletionProposal proposal = createCompletionProposal(unit
						+ ".*", context);
				acceptor.accept(proposal);
			}
		}
	}

	@Override
	public void completeAstTag_Identifiers(EObject model,
			Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		if (model instanceof Inequality || model instanceof AstTransition) {
			proposeAllTags(model.eContainer(), context, acceptor);
		} else if (model instanceof AstTag) {
			AstTag tag = (AstTag) model;
			EObject parent = tag.eContainer();
			if (parent instanceof Inequality || parent instanceof AstTransition) {
				proposeTagAfter(tag, parent.eContainer(), context, acceptor);
			}
		}
	}

	@Override
	public void completeInequality_Tags(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (model instanceof Priority) {
			proposeAllTags(model, context, acceptor);
		}
	}

	@Override
	public void completeInputPattern_Port(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		AstActor actor = (AstActor) model.eContainer();
		proposePorts(actor.getInputs(), assignment, context, acceptor);
	}

	@Override
	public void completeOutputPattern_Port(EObject model,
			Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		AstActor actor = (AstActor) model.eContainer();
		proposePorts(actor.getOutputs(), assignment, context, acceptor);
	}

	@Override
	public void completeVariableReference_Variable(EObject model,
			Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		AstEntity entity = EcoreUtil2
				.getContainerOfType(model, AstEntity.class);
		if (entity != null) {
			// TODO: propose the imported variables as well.
		}
		AstUnit unit = EcoreUtil2.getContainerOfType(model, AstUnit.class);
		if (unit != null) {
			proposeVariable(unit.getVariables(), context, acceptor);
		}
		AstActor actor = EcoreUtil2.getContainerOfType(model, AstActor.class);
		if (actor != null) {
			proposeVariable(actor.getStateVariables(), context, acceptor);
		}
		AstAction action = EcoreUtil2
				.getContainerOfType(model, AstAction.class);
		if (action != null) {
			proposeVariable(action.getVariables(), context, acceptor);
			for (InputPattern input : action.getInputs()) {
				proposeVariable(input.getTokens(), context, acceptor);
			}
		}
		AstProcedure proc = EcoreUtil2.getContainerOfType(model,
				AstProcedure.class);
		if (proc != null) {
			proposeVariable(proc.getVariables(), context, acceptor);
		}
	}

	/**
	 * Proposes all tags of length 1.
	 * 
	 * @param model
	 * @param context
	 * @param acceptor
	 */
	private void proposeAllTags(EObject model, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		AstActor actor = (AstActor) model.eContainer();
		CalActionList actionList = new CalActionList();
		actionList.addActions(actor.getActions());
		List<AstTag> tags = actionList.getTags(1);
		for (AstTag tag : tags) {
			String tagName = getLabelProvider().getText(tag);
			ICompletionProposal proposal = createCompletionProposal(tagName,
					context);
			acceptor.accept(proposal);
		}
	}

	/**
	 * Proposes the ports from the given port list.
	 * 
	 * @param ports
	 * @param assignment
	 * @param context
	 * @param acceptor
	 */
	private void proposePorts(final EList<AstPort> ports,
			Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		lookupCrossReference(((CrossReference) assignment.getTerminal()),
				context, acceptor, new Predicate<IEObjectDescription>() {

					@Override
					public boolean apply(IEObjectDescription objDesc) {
						return ports.contains(objDesc.getEObjectOrProxy());
					}
				});
	}

	/**
	 * Proposes the tags that are immediate completions of the given tag.
	 * <ol>
	 * <li>build action list</li>
	 * <li>get actions that match the tag minus its last component</li>
	 * <li>for each action, propose the next component</li>
	 * </ol>
	 * 
	 * @param tag
	 * @param context
	 * @param acceptor
	 */
	private void proposeTagAfter(AstTag tag, EObject parent,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		AstActor actor = (AstActor) parent.eContainer();
		int n = tag.getIdentifiers().size() - 1;
		List<String> identifiers = tag.getIdentifiers().subList(0, n);

		CalActionList actionList = new CalActionList();
		actionList.addActions(actor.getActions());
		List<AstAction> actions = actionList.getTaggedActions(identifiers);
		for (AstAction action : actions) {
			identifiers = action.getTag().getIdentifiers();
			if (n < identifiers.size()) {
				List<String> last = identifiers.subList(n, n + 1);
				AstTag proposedTag = CalFactory.eINSTANCE.createAstTag();
				proposedTag.getIdentifiers().addAll(last);

				String tagName = getLabelProvider().getText(proposedTag);
				ICompletionProposal proposal = createCompletionProposal(
						tagName, context);
				acceptor.accept(proposal);
			}
		}
	}

	private void proposeVariable(EList<Variable> vars,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		for (Variable var : vars) {
			String proposed = var.getName();
			if (proposed.startsWith(context.getPrefix())) {
				ICompletionProposal proposal = createCompletionProposal(
						proposed, context);
				acceptor.accept(proposal);
			}
		}
	}

}
