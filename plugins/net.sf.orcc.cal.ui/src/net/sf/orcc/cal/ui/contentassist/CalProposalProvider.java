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

import java.util.List;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstInequality;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstPriority;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.util.CalActionList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Predicate;

/**
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#contentAssist on
 * how to customize content assistant
 */
public class CalProposalProvider extends AbstractCalProposalProvider {

	public void completeInequality_Tags(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (model instanceof AstPriority) {
			proposeAllTags(model, context, acceptor);
		}
	}

	public void completeInputPattern_Port(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		AstActor actor = (AstActor) model.eContainer();
		proposePorts(actor.getInputs(), assignment, context, acceptor);
	}

	public void completeOutputPattern_Port(EObject model,
			Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		AstActor actor = (AstActor) model.eContainer();
		proposePorts(actor.getOutputs(), assignment, context, acceptor);
	}

	public void completeTag_Identifiers(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (model instanceof AstInequality || model instanceof AstTransition) {
			proposeAllTags(model.eContainer(), context, acceptor);
		} else if (model instanceof AstTag) {
			AstTag tag = (AstTag) model;
			EObject parent = tag.eContainer();
			if (parent instanceof AstInequality
					|| parent instanceof AstTransition) {
				proposeTagAfter(tag, parent.eContainer(), context, acceptor);
			}
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
		List<AstAction> actions = actor.getActions();
		List<AstTag> tags = new CalActionList(actions).getTags(1);
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
		List<AstAction> actions = actor.getActions();
		int n = tag.getIdentifiers().size() - 1;
		List<String> identifiers = tag.getIdentifiers().subList(0, n);

		actions = new CalActionList(actions).getActions(identifiers);
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

}
