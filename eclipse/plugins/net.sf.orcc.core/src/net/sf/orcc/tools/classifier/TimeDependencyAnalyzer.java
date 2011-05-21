/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.tools.classifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Transition;
import net.sf.orcc.ir.Transitions;

/**
 * This class defines a configuration analyzer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TimeDependencyAnalyzer {

	private Actor actor;

	/**
	 * Creates a new configuration analyzer.
	 */
	public TimeDependencyAnalyzer() {
	}

	/**
	 * Tries to evaluate the guards to check if they are compatible.
	 * 
	 * @param previous
	 *            the action that occurs before <code>action</code>
	 * @param action
	 *            an action
	 * @return <code>true</code> if the guards of the given actions are
	 *         compatible
	 */
	private boolean areGuardsCompatible(Action previous, Action action) {
		GuardSatChecker checker = new GuardSatChecker(actor);
		try {
			if (checker.checkSat(previous, action)) {
				System.out.println(actor.getName() + ": guards of actions "
						+ previous.getName() + " and " + action.getName()
						+ " are compatible");
				return true;
			}

			return false;
		} catch (OrccRuntimeException e) {
			System.err.println(actor.getName()
					+ ": could not check time-dependency");
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * Returns <code>true</code> if this actor has a time-dependent behavior.
	 * 
	 * @return <code>true</code> if this actor has a time-dependent behavior
	 */
	public boolean isTimeDependent(Actor actor) {
		this.actor = actor;

		if (actor.hasFsm()) {
			FSM fsm = actor.getFsm();
			for (Transitions transitions : fsm.getTransitions()) {
				List<Action> actions = new ArrayList<Action>();
				for (Transition transition : transitions.getList()) {
					actions.add(transition.getAction());
				}

				if (isTimeDependent(actions)) {
					return true;
				}
			}

			return false;
		} else {
			return isTimeDependent(actor.getActionsOutsideFsm());
		}
	}

	/**
	 * Returns <code>true</code> if the given action list has a time-dependent
	 * behavior.
	 * 
	 * @param actions
	 *            a list of actions
	 * @return <code>true</code> if the given action list has a time-dependent
	 *         behavior
	 */
	private boolean isTimeDependent(List<Action> actions) {
		Iterator<Action> it = actions.iterator();

		// Create the reference action that will be compared to the list of
		// action

		Pattern higherPriorityPattern = IrFactory.eINSTANCE.createPattern();
		List<Action> higherPriorityActions = new ArrayList<Action>();

		if (it.hasNext()) {
			Action higherPriorityAction = it.next();
			higherPriorityPattern.updatePattern(higherPriorityAction
					.getInputPattern());
			higherPriorityActions.add(higherPriorityAction);
			while (it.hasNext()) {
				Action lowerPriorityAction = it.next();
				Pattern lowerPriorityPattern = lowerPriorityAction
						.getInputPattern();

				if (!lowerPriorityPattern.isSupersetOf(higherPriorityPattern)) {
					for (Action action : higherPriorityActions) {
						// it may still be ok if guards are mutually
						// exclusive
						if (areGuardsCompatible(action, lowerPriorityAction)) {
							return true;
						}
					}
				}

				// Add the current action to higherPriorityActions
				higherPriorityActions.add(lowerPriorityAction);
				higherPriorityPattern.updatePattern(lowerPriorityAction
						.getInputPattern());
			}
		}

		return false;
	}

}
