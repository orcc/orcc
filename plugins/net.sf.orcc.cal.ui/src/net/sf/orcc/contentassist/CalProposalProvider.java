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
package net.sf.orcc.contentassist;

import java.util.List;

import net.sf.orcc.cal.Action;
import net.sf.orcc.cal.Actor;
import net.sf.orcc.cal.CalFactory;
import net.sf.orcc.cal.Inequality;
import net.sf.orcc.cal.Port;
import net.sf.orcc.cal.Priority;
import net.sf.orcc.cal.Tag;
import net.sf.orcc.cal.Transition;
import net.sf.orcc.util.ActionList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.core.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.core.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Predicate;

/**
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#contentAssist on
 * how to customize content assistant
 */
public class CalProposalProvider extends AbstractCalProposalProvider {

	public void completeInequality_Tags(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (model instanceof Priority) {
			proposeAllTags(model, context, acceptor);
		}
	}

	public void completeInputPattern_Port(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		Actor actor = (Actor) model.eContainer();
		proposePorts(actor.getInputs(), assignment, context, acceptor);
	}

	public void completeOutputPattern_Port(EObject model,
			Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		Actor actor = (Actor) model.eContainer();
		proposePorts(actor.getOutputs(), assignment, context, acceptor);
	}

	public void completeTag_Identifiers(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		if (model instanceof Inequality || model instanceof Transition) {
			proposeAllTags(model.eContainer(), context, acceptor);
		} else if (model instanceof Tag) {
			Tag tag = (Tag) model;
			EObject parent = tag.eContainer();
			if (parent instanceof Inequality || parent instanceof Transition) {
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
		Actor actor = (Actor) model.eContainer();
		List<Action> actions = actor.getActions();
		List<Tag> tags = new ActionList(actions).getTags(1);
		for (Tag tag : tags) {
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
	private void proposePorts(final EList<Port> ports, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
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
	private void proposeTagAfter(Tag tag, EObject parent,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		Actor actor = (Actor) parent.eContainer();
		List<Action> actions = actor.getActions();
		int n = tag.getIdentifiers().size() - 1;
		List<String> identifiers = tag.getIdentifiers().subList(0, n);

		actions = new ActionList(actions).getActions(identifiers);
		for (Action action : actions) {
			identifiers = action.getTag().getIdentifiers();
			if (n < identifiers.size()) {
				List<String> last = identifiers.subList(n, n + 1);
				Tag proposedTag = CalFactory.eINSTANCE.createTag();
				proposedTag.getIdentifiers().addAll(last);

				String tagName = getLabelProvider().getText(proposedTag);
				ICompletionProposal proposal = createCompletionProposal(
						tagName, context);
				acceptor.accept(proposal);
			}
		}
	}

}
