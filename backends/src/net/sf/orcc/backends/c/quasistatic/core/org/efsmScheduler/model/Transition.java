/*
* Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the EPFL and University of Oulu nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnhandledCaseException;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * Represents the transition elements of the CALML file.
 * 
 */
public class Transition extends DefaultEdge {
	static final long serialVersionUID = 12345678L;
	String from, to;
	List<String> actionTags;
	List<Action> actions;
	ActionType actionType;

	public Transition() {
		super();
		actionTags = new ArrayList<String>();
		actions = new ArrayList<Action>();
		actionType = ActionType.NotSet;
	}

	/*
	 * public Transition(String from, String to){ super(); actionTags = new
	 * ArrayList<String>(); actions = new ArrayList<Action>(); this.from
	 * =from; this.to = to;
	 * 
	 *  }
	 */
	public void addActionTag(String tag) {
		actionTags.add(tag);
	}

	public void addAction(Action action) {
		actions.add(action);
	}

	public void addActionTags(Element transEle) {
		NodeList QIDList = transEle.getElementsByTagName("QID");
		if (QIDList != null) {
			String QID;
			Element QIDEle;
			for (int j = 0; j < QIDList.getLength(); j++) {
				// add the QID to transition
				QIDEle = (Element) QIDList.item(j);
				QID = QIDEle.getAttribute("name");
				this.addActionTag(QID);
			}
		}
	}

	/**
	 * 
	 * @param at
	 *            type of this transition. Either Processing or Configure.
	 */
	public void setActionType(ActionType at) {
		actionType = at;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public boolean isSelfLoop() {
		return from.equals(to);
	}

	public String toString() {
		if(actionTags == null || actionTags.isEmpty())
			return actionType.toString() + ":" + from +"->"+ to;
		else
			return actionType.toString() + ":" + actionTags.get(0) ;
	}

	/**
	 * @return the actionTags
	 */
	public List<String> getActionTags() {
		return actionTags;
	}

	/**
	 * @param actionTags
	 *            the actionTags to set
	 */
	public void setActionTags(List<String> actionTags) {
		this.actionTags = actionTags;
	}

	/**
	 * @return the actions
	 */
	public List<Action> getActions() {
		return actions;
	}
	/**
	 * <ul>
	 * <li>If the transition contains only one action, then that action is returned.</li>
	 * <li>If it contains no actions, then this method returns null.</li>
	 * <li>If it contains more than one action, then any one of them is returned.</li>
	 * 
	 */
	public Action getAction() {
		if(actions != null ){
			if(actions.size() == 0)
				return null;
			if(actions.size() == 1)
				return actions.get(0);
			else{
				new UnhandledCaseException(this + "has multiple actions "+actions.size()).printStackTrace();
				return null;//for compiling
			}
				
		}
		else return null;
	}
	/**
	 * @param actions
	 *            the actions to set
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

}
