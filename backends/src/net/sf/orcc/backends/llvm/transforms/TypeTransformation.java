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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNode;
import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BitcastNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.GetElementPtrNode;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.LoadFifo;
import net.sf.orcc.backends.llvm.nodes.PhiNode;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.backends.llvm.nodes.SextNode;
import net.sf.orcc.backends.llvm.nodes.TruncNode;
import net.sf.orcc.backends.llvm.nodes.ZextNode;
import net.sf.orcc.backends.llvm.type.LLVMAbstractType;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.StateVar;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExprEvaluateException;
import net.sf.orcc.ir.expr.ExprVisitor;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.TypeExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.Util;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WriteNode;
import net.sf.orcc.ir.type.AbstractType;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.UintType;

/**
 * Verify coherence type for every nodes.
 * 
 * @author Jérôme GORIN
 * 
 */
public class TypeTransformation extends AbstractLLVMNodeVisitor implements
		ExprVisitor {

	ListIterator<AbstractNode> it;

	private int nodeCount;

	private Hashtable<String, Integer> portIndex;

	public TypeTransformation(Actor actor) {

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

	// VarDef must be cast into i8* for accessing fifo
	public VarDef castFifo(VarDef readVar, String fifoName) {
		int index = (Integer) portIndex.get(fifoName);
		LLVMAbstractType addrType = new PointType(new IntType(new IntExpr(
				new Location(), 8)));

		it.previous();

		VarDef varDef = varDefCreate(addrType);

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

	private AbstractLLVMNode castNodeCreate(VarDef var, VarDef targetVar) {

		// Get source size and target size
		int sourceSize = sizeOf(var.getType());
		AbstractType targetType = targetVar.getType();
		int targetSize = sizeOf(targetType);

		// Create target expr for bitcast
		VarUse varUse = new VarUse(var, null);
		VarExpr expr = new VarExpr(new Location(), varUse);

		// Select the type of cast (trunc if smaller, zext otherwise)
		if (sourceSize < targetSize) {
			if (var.getType() instanceof UintType) {
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
	private void fillPorts(List<VarDef> ports) {
		for (VarDef port : ports) {
			portIndex.put(port.getName(), new Integer(0));
		}
	}

	public IExpr removeUnaryExpr(UnaryExpr expr) {
		// Unary expression doesn't exists in LLVM
		Location loc = expr.getLocation();
		AbstractType type = expr.getType();
		IExpr exprE1 = expr.getExpr();
		UnaryOp op = expr.getOp();

		switch (op) {
		case MINUS:
			IntExpr constExpr = new IntExpr(new Location(), 0);
			BinaryExpr varExpr = new BinaryExpr(loc, constExpr, BinaryOp.MINUS,
					exprE1, type);
			return varExpr;
		default:

		}
		return expr;
	}

	private int sizeOf(AbstractType type) {
		if (type instanceof IntType) {
			try {
				return Util.evaluateAsInteger(((IntType) type).getSize());
			} catch (ExprEvaluateException e) {
				e.printStackTrace();
				return 32;
			}
		} else if (type instanceof UintType) {
			try {
				return Util.evaluateAsInteger(((UintType) type).getSize());
			} catch (ExprEvaluateException e) {
				e.printStackTrace();
				return 32;
			}
		} else if (type instanceof ListType) {
			return sizeOf(((ListType) type).getType());
		} else if (type instanceof BoolType) {
			return 1;
		} else {
			throw new NullPointerException();
		}
	}

	private VarDef varDefCreate(AbstractType type) {
		return new VarDef(false, false, 0, new Location(), "", null, null,
				nodeCount++, type);
	}

	/**
	 * Nodes visitor.
	 * 
	 */

	@Override
	public void visit(AssignVarNode node, Object... args) {
		VarDef varDef = node.getVar();
		IExpr expr = node.getValue();

		// Change unary expression into binary expression
		if (expr instanceof UnaryExpr) {
			node.setValue(removeUnaryExpr((UnaryExpr) expr));
		}

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
			// recover the reference type from the current node
			AbstractType refType = (AbstractType) args[0];

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

			if ((expr.getE1() instanceof VarExpr)) {
				VarExpr e1 = (VarExpr) expr.getE1();
				expr.getE2().accept(this, e1.getVar().getVarDef().getType());
			} else if (expr.getE1() instanceof VarExpr) {
				VarExpr e2 = (VarExpr) expr.getE2();
				expr.getE2().accept(this, e2.getVar().getVarDef().getType());
			}
			return;

		case DIV:
		case DIV_INT:
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
		List<VarDef> procParams = proc.getParameters();
		List<IExpr> parameters = node.getParameters();

		for (IExpr parameter : parameters) {
			VarDef procParam = procParams.get(tmpCnt);
			parameter.accept(this, procParam.getType());

			tmpCnt++;
		}

	}

	@Override
	public void visit(GetElementPtrNode node, Object... args) {
		// Set every index to i32 (mandatory in llvm)
		for (IExpr index : node.getIndexes()) {
			if (index instanceof VarExpr) {
				VarExpr indExpr = (VarExpr) index;
				VarUse indUse = indExpr.getVar();
				VarDef indVar = indUse.getVarDef();
				AbstractType indType = indVar.getType();

				if (indType instanceof IntType) {
					IntType type = (IntType) indType;
					try {
						int size = Util.evaluateAsInteger(type.getSize());
						if (size != 32) {
							it.previous();

							VarDef vardef = varDefCreate(new IntType(
									new IntExpr(new Location(), 32)));
							it.add(castNodeCreate(indVar, vardef));

							it.next();

							indUse.setVarDef(vardef);
						}
					} catch (ExprEvaluateException e) {
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
		VarDef sourceVar = node.getSource().getVarDef();
		VarDef targetVar = node.getTarget();

		AbstractType targetType = targetVar.getType();
		PointType sourceType = (PointType) sourceVar.getType();
		AbstractType type;

		if (sourceType.getType() instanceof ListType) {
			type = ((ListType) sourceType.getType()).getType();
		} else {
			type = sourceType.getType();
		}

		if (!(targetType.equals(type))) {
			VarDef castVar = new VarDef(sourceVar);
			castVar.setName("");
			castVar.setIndex(0);
			castVar.setType(sourceType.getType());
			castVar.setSuffix(nodeCount++);
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
		BrNode brNode = (BrNode) args[0];

		AbstractType varType = node.getType();
		Map<VarDef, LabelNode> assignements = node.getAssignements();
		Map<VarDef, LabelNode> tmpAssignements = new HashMap<VarDef, LabelNode>();

		for (Map.Entry<VarDef, LabelNode> assignement : assignements.entrySet()) {
			VarDef varKey = assignement.getKey();
			LabelNode labelNode = assignement.getValue();

			if (!(varType.equals(varKey.getType()))) {
				VarDef castVar = varDefCreate(varType);
				AbstractLLVMNode castNode = castNodeCreate(varKey, castVar);

				// it.add(castNode);
				// it.next();
				/*
				 * if
				 * (labelNode.getLabelName().compareTo(brNode.getLabelTrueNode
				 * ().getLabelName()) ==0 ){
				 * brNode.getThenNodes().add(castNode); }else if
				 * (labelNode.getLabelName
				 * ().compareTo(brNode.getLabelFalseNode().getLabelName()) ==0
				 * ){ brNode.getElseNodes().add(castNode); }else{
				 * 
				 * }
				 */

				varKey = castVar;
			}
			tmpAssignements.put(varKey, labelNode);
		}
		node.setAssignements(tmpAssignements);
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
	public void visit(SelectNode node, Object... args) {
		List<PhiAssignment> phis = node.getPhis();
		for (PhiAssignment phi : phis) {
			VarDef varDef = phi.getVarDef();
			AbstractType typeRef = varDef.getType();
			List<VarUse> varUses = phi.getVars();

			for (VarUse varUse : varUses) {
				AbstractType type = varUse.getVarDef().getType();

				if (!(type.equals(typeRef))) {
					it.previous();
					VarDef castVar = varDefCreate(typeRef);
					it.add(castNodeCreate(varUse.getVarDef(), castVar));
					varUse.setVarDef(castVar);
					it.next();
				}

			}

		}
	}

	@Override
	public void visit(StoreNode node, Object... args) {
		PointType type = (PointType) node.getTarget().getVarDef().getType();
		node.getValue().accept(this, type.getType());
	}

	@Override
	public void visit(StringExpr expr, Object... args) {

	}

	@Override
	public void visit(TypeExpr expr, Object... args) {

	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		// Unary expression doesn't exists in LLVM
		if (args[0] instanceof AssignVarNode) {
			AssignVarNode node = (AssignVarNode) args[0];
			Location loc = expr.getLocation();
			AbstractType type = expr.getType();
			IExpr exprE1 = expr.getExpr();
			UnaryOp op = expr.getOp();

			switch (op) {
			case MINUS:
				IntExpr constExpr = new IntExpr(new Location(), 0);
				BinaryExpr varExpr = new BinaryExpr(loc, constExpr,
						BinaryOp.MINUS, exprE1, type);
				node.setValue(varExpr);
				varExpr.accept(this, args);
			default:

			}
		}
	}

	public void visit(VarExpr expr, Object... args) {
		// recover the reference type from the current node
		AbstractType refType = (AbstractType) args[0];
		VarDef var = expr.getVar().getVarDef();
		AbstractType varType = var.getType();

		if (!(refType.equals(varType))) {
			// Add cast node before the current expression
			it.previous();

			VarDef vardef = varDefCreate(refType);
			it.add(castNodeCreate(var, vardef));
			VarUse varUse = new VarUse(vardef, null);
			expr.setVar(varUse);

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

	private void visitLocals(List<VarDef> locals) {
		for (VarDef local : locals) {
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

		visitLocals(proc.getLocals());
		clearPorts();
		visitNodes(proc.getNodes());
	}

	private void visitStateVars(List<StateVar> stateVars) {
		for (StateVar stateVar : stateVars) {
			VarDef vardef = stateVar.getDef();

			PointType newType = new PointType(vardef.getType());
			vardef.setType(newType);
		}
	}

}
