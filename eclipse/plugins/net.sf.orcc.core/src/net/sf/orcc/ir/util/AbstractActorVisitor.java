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
package net.sf.orcc.ir.util;

import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockSpecific;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * This abstract class implements a no-op visitor on an actor. This class should
 * be extended by classes that implement actor visitors and transformations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractActorVisitor<T> extends DfSwitch<T> {

	protected class IrSwitchDelegate extends IrSwitch<T> {

		@Override
		public T caseArgByRef(ArgByRef arg) {
			return AbstractActorVisitor.this.caseArgByRef(arg);
		}

		@Override
		public T caseArgByVal(ArgByVal arg) {
			return AbstractActorVisitor.this.caseArgByVal(arg);
		}

		@Override
		public T caseExprBinary(ExprBinary expr) {
			return AbstractActorVisitor.this.caseExprBinary(expr);
		}

		@Override
		public T caseExprBool(ExprBool expr) {
			return AbstractActorVisitor.this.caseExprBool(expr);
		}

		@Override
		public T caseExprFloat(ExprFloat expr) {
			return AbstractActorVisitor.this.caseExprFloat(expr);
		}

		@Override
		public T caseExprInt(ExprInt expr) {
			return AbstractActorVisitor.this.caseExprInt(expr);
		}

		@Override
		public T caseExprList(ExprList expr) {
			return AbstractActorVisitor.this.caseExprList(expr);
		}

		@Override
		public T caseExprString(ExprString expr) {
			return AbstractActorVisitor.this.caseExprString(expr);
		}

		@Override
		public T caseExprUnary(ExprUnary expr) {
			return AbstractActorVisitor.this.caseExprUnary(expr);
		}

		@Override
		public T caseExprVar(ExprVar expr) {
			return AbstractActorVisitor.this.caseExprVar(expr);
		}

		@Override
		public T caseInstAssign(InstAssign assign) {
			return AbstractActorVisitor.this.caseInstAssign(assign);
		}

		@Override
		public T caseInstCall(InstCall call) {
			return AbstractActorVisitor.this.caseInstCall(call);
		}

		@Override
		public T caseInstLoad(InstLoad load) {
			return AbstractActorVisitor.this.caseInstLoad(load);
		}

		@Override
		public T caseInstPhi(InstPhi phi) {
			return AbstractActorVisitor.this.caseInstPhi(phi);
		}

		@Override
		public T caseInstReturn(InstReturn returnInstr) {
			return AbstractActorVisitor.this.caseInstReturn(returnInstr);
		}

		@Override
		public T caseInstruction(Instruction instruction) {
			return AbstractActorVisitor.this.caseInstruction(instruction);
		}

		@Override
		public T caseInstSpecific(InstSpecific inst) {
			return AbstractActorVisitor.this.caseInstSpecific(inst);
		}

		@Override
		public T caseInstStore(InstStore store) {
			return AbstractActorVisitor.this.caseInstStore(store);
		}

		@Override
		public T caseBlockBasic(BlockBasic block) {
			return AbstractActorVisitor.this.caseNodeBlock(block);
		}
		
		@Override
		public T caseBlockSpecific(BlockSpecific block) {
			return AbstractActorVisitor.this.caseNodeSpecific(block);
		}

		@Override
		public T caseBlockIf(BlockIf nodeIf) {
			return AbstractActorVisitor.this.caseNodeIf(nodeIf);
		}

		@Override
		public T caseBlockWhile(BlockWhile nodeWhile) {
			return AbstractActorVisitor.this.caseNodeWhile(nodeWhile);
		}

		@Override
		public T caseProcedure(Procedure procedure) {
			return AbstractActorVisitor.this.caseProcedure(procedure);
		}

		@Override
		public T caseVar(Var var) {
			return AbstractActorVisitor.this.caseVar(var);
		}

	}

	protected Actor actor;

	protected int indexInst;

	protected int indexNode;

	protected final IrSwitch<T> irSwitch = new IrSwitchDelegate();

	/**
	 * current procedure being visited
	 */
	protected Procedure procedure;

	private final boolean visitFull;

	/**
	 * Creates a new abstract actor visitor that visits all nodes and
	 * instructions of all procedures (including the ones referenced by
	 * actions).
	 */
	public AbstractActorVisitor() {
		visitFull = false;
	}

	/**
	 * Creates a new abstract actor visitor that visits all nodes and
	 * instructions of all procedures (including the ones referenced by
	 * actions), and may also visit all the expressions if
	 * <code>visitFull</code> is <code>true</code>.
	 * 
	 * @param visitFull
	 *            when <code>true</code>, visits all the expressions
	 */
	public AbstractActorVisitor(boolean visitFull) {
		this.visitFull = visitFull;
	}

	@Override
	public T caseAction(Action action) {
		doSwitch(action.getInputPattern());
		doSwitch(action.getOutputPattern());
		doSwitch(action.getPeekPattern());
		doSwitch(action.getScheduler());
		doSwitch(action.getBody());

		return null;
	}

	@Override
	public T caseActor(Actor actor) {
		for (Var parameter : actor.getParameters()) {
			doSwitch(parameter);
		}

		for (Var stateVar : actor.getStateVars()) {
			doSwitch(stateVar);
		}

		for (Procedure procedure : actor.getProcs()) {
			doSwitch(procedure);
		}

		for (Action action : actor.getActions()) {
			doSwitch(action);
		}

		for (Action initialize : actor.getInitializes()) {
			doSwitch(initialize);
		}

		return null;
	}

	public T caseArgByRef(ArgByRef arg) {
		return null;
	}

	public T caseArgByVal(ArgByVal arg) {
		doSwitch(arg.getValue());
		return null;
	}

	public T caseExprBinary(ExprBinary expr) {
		doSwitch(expr.getE1());
		doSwitch(expr.getE2());
		return null;
	}

	public T caseExprBool(ExprBool expr) {
		return null;
	}

	public T caseExprFloat(ExprFloat expr) {
		return null;
	}

	public T caseExprInt(ExprInt expr) {
		return null;
	}

	public T caseExprList(ExprList expr) {
		return null;
	}

	public T caseExprString(ExprString expr) {
		return null;
	}

	public T caseExprUnary(ExprUnary expr) {
		doSwitch(expr.getExpr());
		return null;
	}

	public T caseExprVar(ExprVar var) {
		return null;
	}

	public T caseInstAssign(InstAssign assign) {
		if (visitFull) {
			doSwitch(assign.getValue());
		}
		return null;
	}

	public T caseInstCall(InstCall call) {
		if (visitFull) {
			for (Arg arg : call.getParameters()) {
				doSwitch(arg);
			}
		}
		return null;
	}

	public T caseInstLoad(InstLoad load) {
		if (visitFull) {
			for (Expression expr : load.getIndexes()) {
				doSwitch(expr);
			}
		}
		return null;
	}

	public T caseInstPhi(InstPhi phi) {
		if (visitFull) {
			for (Expression expr : phi.getValues()) {
				doSwitch(expr);
			}
		}
		return null;
	}

	public T caseInstReturn(InstReturn returnInstr) {
		if (visitFull) {
			Expression expr = returnInstr.getValue();
			if (expr != null) {
				doSwitch(expr);
			}
		}
		return null;
	}

	public T caseInstruction(Instruction instruction) {
		return null;
	}

	public T caseInstSpecific(InstSpecific inst) {
		// default implementation does nothing
		return null;
	}
	
	public T caseNodeSpecific(BlockSpecific node) {
		// default implementation does nothing
		return null;
	}

	public T caseInstStore(InstStore store) {
		if (visitFull) {
			for (Expression expr : store.getIndexes()) {
				doSwitch(expr);
			}

			doSwitch(store.getValue());
		}
		return null;
	}

	public T caseNodeBlock(BlockBasic block) {
		int oldIndexInst = indexInst;
		List<Instruction> instructions = block.getInstructions();
		T result = null;
		for (indexInst = 0; indexInst < instructions.size() && result == null; indexInst++) {
			Instruction inst = instructions.get(indexInst);
			result = doSwitch(inst);
		}

		// restore old index
		indexInst = oldIndexInst;
		return result;
	}

	public T caseNodeIf(BlockIf nodeIf) {
		if (visitFull) {
			doSwitch(nodeIf.getCondition());
		}

		doSwitch(nodeIf.getThenNodes());
		doSwitch(nodeIf.getElseNodes());
		doSwitch(nodeIf.getJoinNode());
		return null;
	}

	public T caseNodeWhile(BlockWhile nodeWhile) {
		if (visitFull) {
			doSwitch(nodeWhile.getCondition());
		}

		doSwitch(nodeWhile.getNodes());
		doSwitch(nodeWhile.getJoinNode());
		return null;
	}

	@Override
	public T casePattern(Pattern pattern) {
		return null;
	}

	public T caseProcedure(Procedure procedure) {
		this.procedure = procedure;
		return doSwitch(procedure.getNodes());
	}

	public T caseVar(Var var) {
		return null;
	}

	@Override
	protected T doSwitch(EClass eClass, EObject eObject) {
		if (irSwitch.isSwitchFor(eClass.getEPackage())) {
			return irSwitch.doSwitch(eClass.getClassifierID(), eObject);
		} else {
			return super.doSwitch(eClass, eObject);
		}
	}

	@Override
	public final T doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}
		return doSwitch(eObject.eClass(), eObject);
	}

	/**
	 * Visits the nodes of the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes that belong to a procedure
	 */
	public T doSwitch(List<Block> nodes) {
		int oldIndexNode = indexNode;
		T result = null;
		for (indexNode = 0; indexNode < nodes.size() && result == null; indexNode++) {
			Block node = nodes.get(indexNode);
			result = irSwitch.doSwitch(node);
		}

		indexNode = oldIndexNode;
		return result;
	}

	/**
	 * Returns the value of the <code>actor</code> attribute. This may be
	 * <code>null</code> if the visitor did not set it.
	 * 
	 * @return the value of the <code>actor</code> attribute
	 */
	final public Actor getActor() {
		return actor;
	}

}
