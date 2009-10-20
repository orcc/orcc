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
package net.sf.orcc.backends.llvm.transforms;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNode;
import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BitcastNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.GetElementPtrNode;
import net.sf.orcc.backends.llvm.nodes.LoadFifo;
import net.sf.orcc.backends.llvm.nodes.PhiNode;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.backends.llvm.nodes.SextNode;
import net.sf.orcc.backends.llvm.nodes.TruncNode;
import net.sf.orcc.backends.llvm.nodes.ZextNode;
import net.sf.orcc.backends.llvm.type.LLVMAbstractType;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.common.LocalUse;
import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.Port;
import net.sf.orcc.common.Variable;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.StateVar;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExprVisitor;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.TypeExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.Util;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WriteNode;
import net.sf.orcc.ir.transforms.IActorTransformation;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.util.OrderedMap;

/**
 * Verify coherence type for every nodes.
 * 
 * @author Jérôme GORIN
 * 
 */
public class TypeTransformation extends AbstractLLVMNodeVisitor implements
		ExprVisitor, IActorTransformation {

	ListIterator<AbstractNode> it;

	private int nodeCount;

	private Hashtable<String, Integer> portIndex;

	private Procedure procedure;

	// VarDef must be cast into i8* for accessing fifo
	public LocalVariable castFifo(LocalVariable readVar, String fifoName) {
		int index = (Integer) portIndex.get(fifoName);
		LLVMAbstractType addrType = new PointType(new IntType(new IntExpr(
				new Location(), 8)));

		it.previous();

		LocalVariable varDef = varDefCreate(addrType);

		// Create bitcast node
		BitcastNode bitcast = new BitcastNode(0, new Location(), varDef,
				readVar);
		it.add(bitcast);

		// Create a load fifo node
		LoadFifo loadfifo = new LoadFifo(0, new Location(), fifoName, readVar,
				index);
		it.add(loadfifo);

		it.next();

		return varDef;

	}

	private AbstractLLVMNode castNodeCreate(LocalVariable var,
			LocalVariable targetVar) {

		// Get source size and target size
		int sourceSize = sizeOf(var.getType());
		IType targetType = targetVar.getType();
		int targetSize = sizeOf(targetType);

		// Create target expr for bitcast
		LocalUse localUse = new LocalUse(var, null);
		VarExpr expr = new VarExpr(new Location(), localUse);

		// Select the type of cast (trunc if smaller, zext otherwise)
		if (sourceSize < targetSize) {
			if (var.getType().getType() == IType.UINT) {
				return new ZextNode(0, new Location(), targetVar, expr);
			} else {
				return new SextNode(0, new Location(), targetVar, expr);
			}
		} else {
			return new TruncNode(0, new Location(), targetVar, expr);
		}
	}

	@SuppressWarnings("unchecked")
	private void clearPorts() {
		Set entries = portIndex.entrySet();
		Iterator itr = entries.iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry) itr.next();
			entry.setValue(0);
		}
	}

	/**
	 * Store ports of the current actor
	 * 
	 */
	private void fillPorts(OrderedMap<Port> ports) {
		for (Port port : ports) {
			portIndex.put(port.getName(), new Integer(0));
		}
	}

	private int sizeOf(IType type) {
		if (type.getType() == IType.INT) {
			try {
				return Util.evaluateAsInteger(((IntType) type).getSize());
			} catch (OrccException e) {
				e.printStackTrace();
				return 32;
			}
		} else if (type.getType() == IType.UINT) {
			try {
				return Util.evaluateAsInteger(((UintType) type).getSize());
			} catch (OrccException e) {
				e.printStackTrace();
				return 32;
			}
		} else if (type.getType() == IType.LIST) {
			return sizeOf(((ListType) type).getElementType());
		} else if (type.getType() == IType.BOOLEAN) {
			return 1;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void transform(Actor actor) {
		portIndex = new Hashtable<String, Integer>();

		fillPorts(actor.getInputs());
		fillPorts(actor.getOutputs());

		visitStateVars(actor.getStateVars());

		for (Procedure proc : actor.getProcs()) {
			visitProc(proc);
		}

		for (Action action : actor.getActions()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}

		portIndex.clear();
	}

	private LocalVariable varDefCreate(IType type) {
		return new LocalVariable(false, false, 0, new Location(), "", null,
				null, nodeCount++, type);
	}

	/**
	 * Nodes visitor.
	 * 
	 */

	@Override
	public void visit(AssignVarNode node, Object... args) {
		LocalVariable varDef = node.getVar();

		// Visit expr
		node.getValue().accept(this, varDef.getType());

	}

	/**
	 * Expressions visitor.
	 * 
	 */

	public void visit(BinaryExpr expr, Object... args) {
		BinaryOp op = expr.getOp();
		switch (op) {
		case BAND:
		case LAND:
		case BOR:
		case LOR:
		case MINUS:
		case TIMES:
		case PLUS:
		case SHIFT_LEFT:
		case SHIFT_RIGHT:
		case BXOR:
		case DIV:
		case DIV_INT:
			// recover the reference type from the current node
			IType refType = (IType) args[0];

			expr.getE1().accept(this, args);
			expr.getE2().accept(this, args);
			expr.setType(refType);
			return;

		case EQ:
		case GE:
		case GT:
		case LE:
		case LT:
		case NE:

			if ((expr.getE1().getType() == IExpr.VAR)) {
				VarExpr e1 = (VarExpr) expr.getE1();
				expr.getE2().accept(this, e1.getVar().getVariable().getType());
			} else if (expr.getE2().getType() == IExpr.VAR) {
				VarExpr e2 = (VarExpr) expr.getE2();
				expr.getE2().accept(this, e2.getVar().getVariable().getType());
			}
			return;

		case MOD:
		case EXP:

		default:

		}

	}

	@Override
	public void visit(BooleanExpr expr, Object... args) {

	}

	@Override
	public void visit(BrNode node, Object... args) {

		ListIterator<AbstractNode> tmpit = it;
		visitNodes(node.getConditionNodes());
		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
		it = tmpit;
		for (PhiNode phiNode : node.getPhiNodes()) {
			visit(phiNode, node);
		}
	}

	@Override
	public void visit(CallNode node, Object... args) {
		int tmpCnt = 0;

		Procedure proc = node.getProcedure();
		IType returnType = proc.getReturnType();
		LocalVariable returnVar = node.getRes();
		List<LocalVariable> procParams = proc.getParameters();
		List<IExpr> parameters = node.getParameters();
		for (IExpr parameter : parameters) {
			LocalVariable procParam = procParams.get(tmpCnt);
			parameter.accept(this, procParam.getType());

			tmpCnt++;
		}

		if (returnVar != null) {
			if (!returnType.equals(returnVar.getType())) {
				LocalVariable castVar = varDefCreate(returnType);
				node.setRes(castVar);
				it.add(castNodeCreate(castVar, returnVar));
			}
		}

	}

	@Override
	public void visit(GetElementPtrNode node, Object... args) {
		// Set every index to i32 (mandatory in llvm)
		for (IExpr index : node.getIndexes()) {
			if (index.getType() == IExpr.VAR) {
				VarExpr indExpr = (VarExpr) index;

				// we can safely cast because in a VarExpr in an actor, only local
				// variables are used (globals must be load'ed first)
				LocalUse indUse = (LocalUse) indExpr.getVar();
				
				LocalVariable indVar = indUse.getLocalVariable();
				IType indType = indVar.getType();

				if (indType.getType() == IType.INT) {
					IntType type = (IntType) indType;
					try {
						int size = Util.evaluateAsInteger(type.getSize());
						if (size != 32) {
							it.previous();

							LocalVariable vardef = varDefCreate(new IntType(
									new IntExpr(new Location(), 32)));
							it.add(castNodeCreate(indVar, vardef));

							it.next();

							indUse.setLocalVariable(vardef);
						}
					} catch (OrccException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void visit(IntExpr expr, Object... args) {

	}

	@Override
	public void visit(ListExpr expr, Object... args) {

	}

	@Override
	public void visit(LoadNode node, Object... args) {
		Variable sourceVar = node.getSource().getVariable();
		LocalVariable targetVar = node.getTarget();

		IType targetType = targetVar.getType();
		PointType sourceType = (PointType) sourceVar.getType();
		IType type;

		if (sourceType.getElementType().getType() == IType.LIST) {
			type = ((ListType) sourceType.getElementType()).getElementType();
		} else {
			type = sourceType.getElementType();
		}

		if (!(targetType.equals(type))) {
			LocalVariable castVar = varDefCreate(sourceType.getElementType());
			node.setTarget(castVar);
			it.add(castNodeCreate(castVar, targetVar));
		}
	}

	@Override
	public void visit(PeekNode node, Object... args) {
		node.setVar(castFifo(node.getVarDef(), node.getFifoName()));
	}

	@Override
	public void visit(PhiNode node, Object... args) {

	}

	@Override
	public void visit(ReadNode node, Object... args) {
		String fifoName = node.getFifoName();

		node.setVar(castFifo(node.getVarDef(), fifoName));
		int index = (Integer) portIndex.get(fifoName);
		node.setFifoName(fifoName + "_" + Integer.toString(index));
		portIndex.remove(fifoName);
		portIndex.put(fifoName, new Integer(++index));

	}

	@Override
	public void visit(ReturnNode node, Object... args) {
		node.getValue().accept(this, procedure.getReturnType());
	}

	@Override
	public void visit(SelectNode node, Object... args) {
		List<PhiAssignment> phis = node.getPhis();
		for (PhiAssignment phi : phis) {
			LocalVariable varDef = phi.getVarDef();
			IType typeRef = varDef.getType();
			List<LocalUse> localUses = phi.getVars();

			for (LocalUse localUse : localUses) {
				IType type = localUse.getLocalVariable().getType();

				if (!(type.equals(typeRef))) {
					it.previous();
					LocalVariable castVar = varDefCreate(typeRef);
					it
							.add(castNodeCreate(localUse.getLocalVariable(),
									castVar));
					localUse.setLocalVariable(castVar);
					it.next();
				}

			}

		}
	}

	@Override
	public void visit(StoreNode node, Object... args) {
		PointType type = (PointType) node.getTarget().getVariable().getType();
		node.getValue().accept(this, type.getElementType());
	}

	@Override
	public void visit(StringExpr expr, Object... args) {

	}

	@Override
	public void visit(TypeExpr expr, Object... args) {

	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {

	}

	public void visit(VarExpr expr, Object... args) {
		// recover the reference type from the current node
		IType refType = (IType) args[0];

		// we can safely cast because in a VarExpr in an actor, only local
		// variables are used (globals must be load'ed first)
		LocalVariable var = (LocalVariable) expr.getVar().getVariable();
		IType varType = var.getType();

		if (!(refType.equals(varType))) {
			// Add cast node before the current expression
			it.previous();

			LocalVariable vardef = varDefCreate(refType);
			it.add(castNodeCreate(var, vardef));
			LocalUse localUse = new LocalUse(vardef, null);
			expr.setVar(localUse);

			it.next();
		}
	}

	@Override
	public void visit(WriteNode node, Object... args) {
		String fifoName = node.getFifoName();

		node.setVarDef(castFifo(node.getVarDef(), fifoName));
		int index = (Integer) portIndex.get(fifoName);
		node.setFifoName(fifoName + "_" + Integer.toString(index));
		portIndex.remove(fifoName);
		portIndex.put(fifoName, new Integer(++index));

	}

	/**
	 * Variable visitor.
	 * 
	 */

	private void visitLocals(List<LocalVariable> locals) {
		for (LocalVariable local : locals) {
			if (portIndex.containsKey(local.getName())) {
				// PointType newType = new PointType(local.getType());
				PointType newType = new PointType(new IntType(new IntExpr(
						new Location(), 32)));
				local.setType(newType);
			}
		}
	}

	private void visitNodes(List<AbstractNode> nodes) {
		it = nodes.listIterator();
		while (it.hasNext()) {
			it.next().accept(this);
		}
	}

	private void visitProc(Procedure proc) {
		nodeCount = 0;
		this.procedure = proc;
		visitLocals(proc.getLocals());
		clearPorts();
		visitNodes(proc.getNodes());
	}

	private void visitStateVars(List<StateVar> stateVars) {
		for (StateVar stateVar : stateVars) {
			LocalVariable vardef = stateVar.getDef();

			PointType newType = new PointType(vardef.getType());
			vardef.setType(newType);
		}
	}

}
