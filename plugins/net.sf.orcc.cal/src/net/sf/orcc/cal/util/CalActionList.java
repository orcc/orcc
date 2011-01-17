/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.cal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.CalFactory;

/**
 * This class defines a list of action as an ordered map, except keys are tags
 * (list of strings).
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalActionList implements Iterable<AstAction> {

	final private List<AstAction> actionList;

	final private Map<List<String>, List<AstAction>> tagMap;

	final private List<AstAction> untaggedList;

	/**
	 * Creates an empty action list.
	 */
	public CalActionList() {
		actionList = new ArrayList<AstAction>();
		tagMap = new HashMap<List<String>, List<AstAction>>();
		untaggedList = new ArrayList<AstAction>();
	}

	/**
	 * Adds the given action to this action list.
	 * 
	 * @param action
	 *            an action
	 */
	private void add(AstAction action) {
		actionList.add(action);

		AstTag tag = action.getTag();
		if (tag == null || tag.getIdentifiers().isEmpty()) {
			untaggedList.add(action);
		} else {
			// a tag has the form a.b.c
			// we add the action to the tagMap for entries:
			// [a]; [a, b]; [a, b, c]

			int size = tag.getIdentifiers().size();
			List<String> identifiers = new ArrayList<String>(size);
			for (String id : tag.getIdentifiers()) {
				identifiers.add(id);

				// add the action to the list of actions associated with tag
				List<AstAction> actions = tagMap.get(identifiers);
				if (actions == null) {
					actions = new ArrayList<AstAction>();
					tagMap.put(identifiers, actions);
				}
				actions.add(action);

				// creates a new list and copies the tag in it
				identifiers = new ArrayList<String>(identifiers);
			}
		}
	}

	/**
	 * Creates an action list.
	 */
	public void addActions(List<AstAction> actions) {
		for (AstAction action : actions) {
			add(action);
		}
	}

	/**
	 * Returns the list of actions (tagged or untagged)
	 * 
	 * @return the list of actions (tagged or untagged)
	 */
	public List<AstAction> getAllActions() {
		return actionList;
	}

	/**
	 * Returns the list of actions that match the given list of identifiers.
	 * 
	 * @param identifiers
	 *            a list of identifiers
	 * @return the list of actions that match the given list of identifiers
	 */
	public List<AstAction> getTaggedActions(List<String> identifiers) {
		return tagMap.get(identifiers);
	}

	/**
	 * Returns the list of tags.
	 * 
	 * @return the list of tags
	 */
	public List<AstTag> getTags() {
		List<AstTag> tags = new ArrayList<AstTag>();
		for (List<String> identifiers : tagMap.keySet()) {
			AstTag tag = CalFactory.eINSTANCE.createAstTag();
			tag.getIdentifiers().addAll(identifiers);
			tags.add(tag);
		}
		return tags;
	}

	/**
	 * Returns the list of tags whose length is given.
	 * 
	 * @return the list of tags whose length is given
	 */
	public List<AstTag> getTags(int length) {
		if (length == 0) {
			return getTags();
		} else {
			List<AstTag> tags = new ArrayList<AstTag>();
			for (List<String> identifiers : tagMap.keySet()) {
				if (identifiers.size() == length) {
					AstTag tag = CalFactory.eINSTANCE.createAstTag();
					tag.getIdentifiers().addAll(identifiers);
					tags.add(tag);
				}
			}

			return tags;
		}
	}

	/**
	 * Returns the list of untagged actions.
	 * 
	 * @return the list of untagged actions
	 */
	public List<AstAction> getUntaggedActions() {
		return untaggedList;
	}

	@Override
	public Iterator<AstAction> iterator() {
		return actionList.iterator();
	}

}
