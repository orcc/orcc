/*
 * Copyright (c) 2016, Heriot-Watt University Edinburgh
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
package net.sf.orcc.df.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

/*
 * This class contains static methods to be used in conjunction with
 * {@link org.eclipse.graphiti.features.custom.AbstractCustomFeature#canExecute(ICustomContext)}
 * so that structural checks can be performed before dataflow transformations are
 * enabled. These preconditions prevent users from easily breaking their
 * dataflow programs e.g. by injecting deadlocks, or losing token stream orders,
 * or changing causal action firing order. They essentially restrict the
 * transformations to static, synchronous and deterministic subsets of CAL.
 * 
 * @author Rob Stewart
 * @author Idris Ibrahim
 */
public class TransformPreconditionPredicates {

	/**
	 * 
	 * @param myActor
	 *            the actor in question
	 * @return returns <code>true</code> if the actor has one input port
	 */
	public static boolean hasOneInputPort(Actor myActor) {
		boolean hasOneInport = false;
		EList<Port> incomingMap = myActor.getInputs();
		if (incomingMap.size() == 1) {
			hasOneInport = true;
		} else {
			hasOneInport = false;
		}
		;
		return hasOneInport;
	}

	/**
	 * 
	 * @param myActor
	 *            the actor in question
	 * @return returns <code>true</code> if the actor has one output port
	 */
	public static boolean hasOneOutputPort(Actor myActor) {
		boolean hasOneOutport = false;
		EList<Port> outgoingMap = myActor.getOutputs();
		if (outgoingMap.size() == 1) {
			hasOneOutport = true;
		} else {
			hasOneOutport = false;
		}
		;
		return hasOneOutport;
	}

	/**
	 * 
	 * @param myActor
	 *            the actor in question
	 * @return returns <code>true</code> if the actor has one action
	 */
	public static boolean hasOneAction(Actor myActor) {
		boolean hasOneAction = false;
		EList<Action> outgoingMap = myActor.getActions();
		if (outgoingMap.size() == 1) {
			hasOneAction = true;
		}
		return hasOneAction;
	}

	/**
	 * 
	 * @param myActor
	 *            the actor in question
	 * @return returns <code>true</code> if the actor has any internal global
	 *         state variables
	 */
	public static boolean hasAnyActorLevelInternalVars(Actor myActor) {
		boolean hasInternalVars = false;
		EList<Var> stateVars = myActor.getStateVars();
		if (stateVars.isEmpty()) {
			hasInternalVars = false;
		} else {
			hasInternalVars = true;
		}
		;
		return hasInternalVars;
	}

	/**
	 * 
	 * @param myAction
	 *            the action in question
	 * @return returns <code>true</code> if the action has any internal state
	 *         variables
	 */
	public static boolean hasAnyActionLevelInternalVars(Action myAction) {
		boolean hasInternalVars = false;
		EList<Var> stateVars = myAction.getBody().getLocals();
		if (!stateVars.isEmpty()) {
			Var stateVar = stateVars.get(0);
			if (!isPortVar(stateVar, myAction)) {
				hasInternalVars = false;
			} else {
				hasInternalVars = true;
			}
			;
		}
		return hasInternalVars;
	}

	public static boolean isPortVar(Var var, Action myAction) {
		boolean isPortVariable = false;
		String varName = var.getName();
		EList<Var> tokenVariables = myAction.getInputPattern().getVariables();
		for (int i = 0; i < tokenVariables.size(); i++) {
			if (tokenVariables.get(i).getName() == varName) {
				isPortVariable = true;
			}
		}
		return isPortVariable;
	}

	/**
	 * 
	 * @param myAction
	 *            the action in question
	 * @return returns <code>true</code> if the action has one input pattern
	 *         with one token
	 */
	public static boolean hasOneinToken(Action myAction) {
		boolean oneinToken = false;

		Pattern inputTokenPatterns = myAction.getInputPattern();
		EMap<Port, Integer> inputTokenPattern = inputTokenPatterns.getNumTokensMap();
		if (!inputTokenPattern.isEmpty()) {
			Entry<Port, Integer> entry = inputTokenPattern.get(0);
			Integer numTokensInTheOnlyPort = entry.getValue();
			if (numTokensInTheOnlyPort == 1) {
				oneinToken = true;
			} else {
				oneinToken = false;
			}
			;
		}

		return oneinToken;
	}

	/**
	 * 
	 * @param myAction
	 *            the action in question
	 * @return returns <code>true</code> if the action has one output pattern
	 *         with one token
	 */
	public static boolean hasOneoutToken(Action myAction) {
		boolean oneoutToken = false;
		Pattern outTokenPatterns = myAction.getOutputPattern();
		EMap<Port, Integer> outTokenPattern = outTokenPatterns.getNumTokensMap();
		if (!outTokenPattern.isEmpty()) {
			Entry<Port, Integer> entry = outTokenPattern.get(0);
			Integer numTokensOutTheOnlyPort = entry.getValue();
			if (numTokensOutTheOnlyPort == 1) {
				oneoutToken = true;
			} else {
				oneoutToken = false;
			}
			;
		}
		return oneoutToken;
	}

	public static boolean containsOnlyBinaryExpr(Action myAction) {
		boolean hasOnlyBinaryExprInAction = false;
		Procedure actionBody = myAction.getBody();
		Block firstBlock = actionBody.getBlocks().get(0);
		ListIterator<EObject> contents = firstBlock.eContents().listIterator();
		while (contents.hasNext()) {
			Instruction myObj = (Instruction) contents.next();
			if (myObj instanceof InstAssign) {
				InstAssign instrAssign = (InstAssign) myObj;
				if (instrAssign.getValue().isExprBinary()) {
					ExprBinary expr = (ExprBinary) instrAssign.getValue();
					if (expr.isExprBinary()) {
						hasOnlyBinaryExprInAction = true;
					}
				}
			}
		}
		return hasOnlyBinaryExprInAction;
	}

	/**
	 * 
	 * @param expr
	 *            a CAL expression
	 * @return the list of variable names nested in that expression
	 */
	@SuppressWarnings("unused")
	private static List<String> getVarNamesInExpr(Expression expr) {
		List<String> varNamesInExpr = new ArrayList<String>();
		List<String> xs = new ArrayList<String>();
		List<String> ys = new ArrayList<String>();
		if (expr.isExprBinary()) {
			ExprBinary BinaryExp = (ExprBinary) expr;
			xs = getVarNamesInExpr(BinaryExp.getE1());
			ys = getVarNamesInExpr(BinaryExp.getE2());
			for (String s : xs)
				varNamesInExpr.add(s);

			for (String s : ys)
				varNamesInExpr.add(s);
		}
		if (expr.isExprVar()) {
			ExprVar VarExp = (ExprVar) expr;
			varNamesInExpr.add(VarExp.getUse().getVariable().getName());
		}

		return varNamesInExpr;
	}

	public static boolean outputTokensPatternContainsBinaryExpr(EList<Block> blocks) {
		boolean containsExpr = false;

		Block myBlock;
		for (int i = 0; i < blocks.size(); i++) {
			myBlock = blocks.get(i);
			ListIterator<EObject> contents = myBlock.eContents().listIterator();
			EObject obj;
			while (contents.hasNext()) {
				obj = contents.next();
				if (obj instanceof Instruction) {
					Instruction myObj = (Instruction) obj;
					if (myObj instanceof InstStore) {
						InstStore storeOp = (InstStore) myObj;
						Expression rhsExpr = storeOp.getValue();
						if (rhsExpr instanceof ExprBinary) {
							containsExpr = true;
						}
					}
				}
			}
		}
		return containsExpr;
	}
}
