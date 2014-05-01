/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.cal.ui.editor.hyperlinking;

import java.util.List;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstTransition;
import net.sf.orcc.cal.cal.Inequality;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Tweak the default HyperlinkHelper
 * 
 * At the moment, this version only generate hyperlinks from AstTag contained in
 * a "fsm" or "priority" block to 1 or more actions defined in the same actor.
 * 
 * @author Antoine Lorence
 * 
 */
public class CalHyperlinkHelper extends HyperlinkHelper {

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Override
	public IHyperlink[] createHyperlinksByOffset(
			XtextResource resource, int offset, boolean createMultipleHyperlinks) {

		final EObject obj = eObjectAtOffsetHelper.resolveContainedElementAt(
				resource, offset);
		final EObject container = obj.eContainer();

		if (obj instanceof AstTag
				&& (container instanceof AstTransition || container instanceof Inequality)) {
			// Ok, user ctrl+click (or similar) on a transition/priority tag,
			// try to find corresponding actions
			
			final List<IHyperlink> links = Lists.newArrayList();
			final IHyperlinkAcceptor acceptor = new HyperlinkAcceptor(links);
			
			final String currentTag = OrccUtil.toString(((AstTag)obj).getIdentifiers(), ".");
			final ICompositeNode tagNode = NodeModelUtils.getNode(obj);
			final Region tagRegion = new Region(tagNode.getOffset(),
					tagNode.getLength());

			final AstActor actor = EcoreUtil2.getContainerOfType(obj,
					AstActor.class);
			for (final AstAction action : actor.getActions()) {
				// We don't care untagged actions
				if (action.getTag() == null)
					continue;

				final String actionName = OrccUtil.toString(action.getTag().getIdentifiers(), ".");
				if (actionName.equals(currentTag)
						|| actionName.startsWith(currentTag + '.')) {
					createHyperlinksTo(resource, tagRegion, action, acceptor);
				}
			}
			// We must not return an empty array or eclipse will throws assert
			// exceptions
			return links.isEmpty() ? null : Iterables.toArray(links,
					IHyperlink.class);
		}

		return super.createHyperlinksByOffset(resource, offset,
				createMultipleHyperlinks);
	};
}
