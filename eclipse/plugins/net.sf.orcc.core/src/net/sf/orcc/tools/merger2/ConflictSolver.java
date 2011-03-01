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
package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a transformation that remove all possible conflicts
 * between the composite actor and the candidate to merge with. Conflicts are resolved by calling the
 * RenameTransformation on names detected as conflicted.
 * 
 * @author Jerome Gorin
 * 
 */
public class ConflictSolver extends AbstractActorVisitor {
	private OrderedMap<String, Procedure> refProcs;
	private Map<String, String> replacementMap;
	private Actor composite;

	public ConflictSolver(Actor composite) {
		this.refProcs = new OrderedMap<String, Procedure>();
		this.replacementMap = new HashMap<String, String>();
		this.composite = composite;
		
		// Store all procedure names of the composite actor
		refProcs.putAll(composite.getProcs());
		getProcedures(composite.getActions());
		getProcedures(composite.getInitializes());
	}

	private void compareVariables(
			OrderedMap<String, GlobalVariable> refVariables,
			OrderedMap<String, GlobalVariable> variables) {
		for (Variable variable : variables) {
			String name = variable.getName();

			// Check if Global variable name is already used in the
			// reference actor
			if (refVariables.contains(name)) {
				// Name is used set a unique index to the name
				int index = 0;

				while (refVariables.contains(name + "_" + index)) {
					index++;
				}

				// Store result in replacement map
				replacementMap.put(name, name + "_" + index);
			}
		}
	}

	private void getProcedures(List<Action> actions) {
		for (Action action : actions) {
			// Get all procedures contain in an action
			Procedure scheduler = action.getScheduler();
			Procedure body = action.getBody();
			refProcs.put(scheduler.getName(), scheduler);
			refProcs.put(body.getName(), body);
		}
	}

	public void resolve(Actor actor) {
		// Check conflicts on global variables
		compareVariables(composite.getParameters(), actor.getParameters());
		compareVariables(composite.getStateVars(), actor.getStateVars());

		// Check all procedures
		visit(actor);

		if (!replacementMap.isEmpty()) {
			// Rename all conflicts founds
			new RenameTransformation(replacementMap).visit(actor);
		}
		
		
		//Check potential conflicts in FSM
		ActionScheduler actionScheduler = actor.getActionScheduler();
		if(actionScheduler.hasFsm()){
			FSM fsm = compareFSM(actionScheduler.getFsm());
			actionScheduler.setFsm(fsm);
		}
	}

	public FSM resolveFSM(FSM fsm, Map<String, String> conflictedStates){
		//Resolve state conflicts
		FSM resolvedFSM = new FSM();
		
		//Add resolved states
		for (String state : fsm.getStates()){
			if (conflictedStates.containsKey(state)){
				resolvedFSM.addState(conflictedStates.get(state));
			}else{
				resolvedFSM.addState(state);
			}
		}
		
		//Add initial State
		String initialState = fsm.getInitialState().toString();
		if (conflictedStates.containsKey(initialState)){
			initialState = conflictedStates.get(initialState);
		}
		resolvedFSM.setInitialState(initialState);
		
		
		//Add transitions with resolved states
		for (Transition transition : fsm.getTransitions()){
			String source = transition.getSourceState().toString();
			if (conflictedStates.containsKey(source)){
				source = conflictedStates.get(source);
			}
			
			for (NextStateInfo nextState : transition.getNextStateInfo()){
				String target = nextState.getTargetState().toString();
				
				if (conflictedStates.containsKey(target)){
					target = conflictedStates.get(target);
				}
				
				resolvedFSM.addTransition(source, nextState.getAction(), target);
			}
		}
					
		return resolvedFSM;
	}
	
	public FSM compareFSM (FSM fsm){		
		//Composite actor has no FSM, no conflict
		if (!composite.getActionScheduler().hasFsm()){
			return fsm;
		}
		
		// Check for any conflict in state name
		 FSM compositeFSM = composite.getActionScheduler().getFsm();
		 List<String> compositeStates = compositeFSM.getStates();
		 List<String> conflictedStates = new ArrayList<String>();
		
		for (String state : fsm.getStates()){
			if (compositeStates.contains(state)){
				//The current state enter in conflict with a composite state
				conflictedStates.add(state);
			}
		}
		
		if(compositeStates.isEmpty()){
			//No conflict between the two FSM
			return fsm;
		}
		
		//Conflicts found, resolve it
		Map<String, String> replacementState = new HashMap<String, String>();
		for(String conflictedState : conflictedStates ){
			//Find a new unused name for the current state
			int index = 0;
			
			while(compositeStates.contains(conflictedState + "_" + index)){
				index++;
			}
			
			//Set a new name for this state
			replacementState.put(conflictedState, conflictedState + "_" + index);
		}
		
		return resolveFSM(fsm, replacementState);
	}
	
	@Override
	public void visit(Procedure procedure) {
		String name = procedure.getName();

		// Check if procedure name is already used in the reference actor
		if (refProcs.contains(name)) {
			// Name is used set a unique index to the name
			int index = 0;

			while (refProcs.contains(name + "_" + index)) {
				index++;
			}

			// Store result in replacement map
			replacementMap.put(name, name + "_" + index);
		}
	}

}