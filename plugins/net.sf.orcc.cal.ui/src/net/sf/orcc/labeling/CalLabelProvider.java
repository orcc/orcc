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
package net.sf.orcc.labeling;

import java.util.Iterator;

import net.sf.orcc.cal.Action;
import net.sf.orcc.cal.Inequality;
import net.sf.orcc.cal.Tag;
import net.sf.orcc.cal.Transition;

import org.eclipse.xtext.ui.core.DefaultLabelProvider;

/**
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class CalLabelProvider extends DefaultLabelProvider {

	public String text(Action action) {
		Tag tag = action.getTag();
		if (tag == null) {
			return "(untagged)";
		} else {
			return getText(tag);
		}
	}

	public String text(Inequality inequality) {
		Iterator<Tag> it = inequality.getTags().iterator();
		StringBuilder builder = new StringBuilder();
		if (it.hasNext()) {
			builder.append(text(it.next()));
			while (it.hasNext()) {
				builder.append(" > ");
				builder.append(getText(it.next()));
			}
			builder.append(';');
		}

		return builder.toString();
	}

	public String text(Tag tag) {
		Iterator<String> it = tag.getIdentifiers().iterator();
		StringBuilder builder = new StringBuilder();
		if (it.hasNext()) {
			builder.append(it.next());
			while (it.hasNext()) {
				builder.append('.');
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

	public String text(Transition transition) {
		return transition.getSource() + " (" + getText(transition.getTag())
				+ ") --> " + transition.getTarget();
	}

}
