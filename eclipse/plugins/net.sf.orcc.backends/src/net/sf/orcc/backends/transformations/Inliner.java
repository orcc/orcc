/*
 * Copyright (c) 2010-2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.util.util.EcoreHelper;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an actor transformation that inline function(s) and/or
 * procedure(s)
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * @author Thavot Richard
 * 
 */
public class Inliner extends AbstractActorVisitor<Object> {

	private class CallGetter extends AbstractActorVisitor<Object> {

		private boolean inlineFunction;
		private boolean inlineProcedure;

		private List<InstCall> instCallList = new ArrayList<InstCall>();

		public CallGetter(boolean inlineProcedure, boolean inlineFunction) {
			this.inlineProcedure = inlineProcedure;
			this.inlineFunction = inlineFunction;
		}

		@Override
		public Object caseInstCall(InstCall call) {
			// Function case
			Procedure procedure = call.getProcedure();
			Type returnType = procedure.getReturnType();

			if (returnType.isVoid() && inlineProcedure || !returnType.isVoid()
					&& inlineFunction) {
				instCallList.add(call);
			}
			return null;
		}

		public List<InstCall> getCalls() {
			return instCallList;
		}
	}

	private CallGetter callGetter;
	private List<InstCall> instCallList;

	private InstCall currentCall;
	private Map<Var, Var> localToLocalsMap;
	private InstReturn instReturn;

	public Inliner(boolean inlineProcedure, boolean inlineFunction) {
		callGetter = new CallGetter(inlineProcedure, inlineFunction);
	}

	/**
	 * Inline the procedure/function
	 * 
	 * @param call
	 */
	public Inliner(InstCall call) {
		this.instCallList = new ArrayList<InstCall>();
		this.instCallList.add(call);
	}

	/**
	 * Inline the procedure(s)/function(s)
	 * 
	 * @param calls
	 */
	public Inliner(List<InstCall> calls) {
		this.instCallList = new ArrayList<InstCall>();
	}

	public Object caseActor(Actor actor) {
		if (callGetter != null) {
			callGetter.doSwitch(actor);
			this.instCallList = callGetter.getCalls();
		}
		for (InstCall call : instCallList) {
			if (!call.getProcedure().isNative()) {
				this.currentCall = call;
				inlineProcedure();
			}
		}
		return null;
	}

	/**
	 * Inline the currentCall;
	 */
	private void inlineProcedure() {
		// 1. Clone procedure
		Procedure calledProc = IrUtil.copy(currentCall.getProcedure());
		// 2. Get current procedure
		Procedure callerProc = EcoreHelper.getContainerOfType(currentCall,
				Procedure.class);
		// 3.1 Create a new local variable
		localToLocalsMap = new HashMap<Var, Var>();
		for (Var var : calledProc.getLocals()) {
			Var newVar = callerProc.newTempLocalVariable(var.getType(),
					"inlined_" + var.getName());
			newVar.setIndex(var.getIndex());
			newVar.setLineNumber(var.getLineNumber());
			newVar.setAssignable(var.isAssignable());
			localToLocalsMap.put(var, newVar);
		}
		// 3.2
		int i = 0;
		NodeBlock assignBlock = IrFactory.eINSTANCE.createNodeBlock();
		for (Arg arg : currentCall.getParameters()) {
			Param param = calledProc.getParameters().get(i);
			if (arg.isByRef()) {
				ArgByRef ref = (ArgByRef) arg;
				localToLocalsMap.put(param.getVariable(), ref.getUse()
						.getVariable());
			} else {
				ArgByVal val = (ArgByVal) arg;
				if (val.getValue().isExprVar()) {
					Var var = ((ExprVar) val.getValue()).getUse().getVariable();
					localToLocalsMap.put(param.getVariable(), var);
				} else {
					Var newVar = callerProc.newTempLocalVariable(param
							.getVariable().getType(), "inlined_"
							+ param.getVariable().getName());
					newVar.setAssignable(true);
					localToLocalsMap.put(param.getVariable(), newVar);
					InstAssign instAssign = IrFactory.eINSTANCE
							.createInstAssign(newVar, val.getValue());
					assignBlock.add(instAssign);
				}
			}
			i++;
		}
		// 4. Rename local variable
		super.doSwitch(calledProc);
		// 5. Transform return
		if ((currentCall.getTarget() == null)
				| calledProc.getReturnType().isVoid()) {
			IrUtil.delete(instReturn);
		} else {
			InstAssign instAssign = IrFactory.eINSTANCE.createInstAssign(
					currentCall.getTarget().getVariable(),
					IrUtil.copy(instReturn.getValue()));
			EcoreUtil.replace(instReturn, instAssign);
		}
		// 6. Cut the block containing call instruction in two parts
		NodeBlock beginningBlock = currentCall.getBlock();
		NodeBlock followingBlock = IrFactory.eINSTANCE.createNodeBlock();
		indexInst = 0;
		while (indexInst < beginningBlock.getInstructions().size()) {
			if (beginningBlock.getInstructions().get(indexInst)
					.equals(currentCall))
				break;
			indexInst++;
		}
		while (indexInst < beginningBlock.getInstructions().size()) {
			followingBlock.add(beginningBlock.getInstructions().get(indexInst));
		}
		// 7. Add all inlined blocks
		List<Node> nodes = findNode(callerProc.getNodes(), beginningBlock);
		indexNode = nodes.indexOf(beginningBlock);
		List<Node> inlined = calledProc.getNodes();
		if (!assignBlock.getInstructions().isEmpty()) {
			inlined.add(0, assignBlock);
		}
		inlined.add(followingBlock);
		nodes.addAll(indexNode + 1, inlined);
		// 8.
		IrUtil.delete(currentCall);
	}

	@Override
	public Object caseInstAssign(InstAssign assign) {
		// Replace the local variable name by visiting caseExprVar
		super.doSwitch(assign.getValue());
		Var var = assign.getTarget().getVariable();
		if (localToLocalsMap.containsKey(var)) {
			assign.getTarget().setVariable(localToLocalsMap.get(var));
		}
		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		// Replace the local variable name by visiting caseExprVar
		Var var = load.getTarget().getVariable();
		if (localToLocalsMap.containsKey(var)) {
			load.getTarget().setVariable(localToLocalsMap.get(var));
		}
		var = load.getSource().getVariable();
		if (localToLocalsMap.containsKey(var)) {
			load.getSource().setVariable(localToLocalsMap.get(var));
		}
		for(Expression e : load.getIndexes()){
			super.doSwitch(e);
		}
		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		// Replace the local variable name by visiting caseExprVar
		super.doSwitch(store.getValue());
		Var var = store.getTarget().getVariable();
		if (localToLocalsMap.containsKey(var)) {
			store.getTarget().setVariable(localToLocalsMap.get(var));
		}
		for(Expression e : store.getIndexes()){
			super.doSwitch(e);
		}
		return null;
	}

	@Override
	public Object caseInstReturn(InstReturn instReturn) {
		// Replace the local variable name by visiting caseExprVar
		super.doSwitch(instReturn.getValue());
		this.instReturn = instReturn;
		return null;
	}

	@Override
	public Object caseNodeIf(NodeIf nodeif) {
		// Replace the local variable name by visiting caseExprVar
		super.doSwitch(nodeif.getCondition());
		super.caseNodeIf(nodeif);
		return null;
	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {
		// Replace the local variable name by visiting caseExprVar
		super.doSwitch(nodeWhile.getCondition());
		super.caseNodeWhile(nodeWhile);
		return null;
	}

	/**
	 * Replace the local variable name if a reference already exists to a global
	 * variable
	 */
	@Override
	public Object caseExprVar(ExprVar exprVar) {
		Var var = exprVar.getUse().getVariable();
		if (localToLocalsMap.containsKey(var)) {
			exprVar.getUse().setVariable(localToLocalsMap.get(var));
		}
		return null;
	}

	/**
	 * Find the given node into the given nodes location.
	 * 
	 * @param locationNodes
	 * @param nodeToFind
	 * @return
	 */
	public List<Node> findNode(List<Node> locationNodes, Node nodeToFind) {
		if (locationNodes.contains(nodeToFind)) {
			return locationNodes;
		} else {
			List<Node> n = null;
			for (Node node : locationNodes) {
				if (node.isNodeIf()) {
					n = findNode(((NodeIf) node).getElseNodes(), nodeToFind);
					if (n == null)
						n = findNode(((NodeIf) node).getThenNodes(), nodeToFind);
				} else if (node.isNodeWhile()) {
					n = findNode(((NodeWhile) node).getNodes(), nodeToFind);
				}
				if (n != null)
					return n;
			}
		}
		return null;
	}

}
