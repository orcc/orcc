package net.sf.orcc.backends.xlim.experimental.transform;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.util.OrderedMap;

public class MoveLiteralIntegers extends AbstractActorTransformation {

	private class MyExpressionInterpreter implements ExpressionInterpreter {

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Expression e1 = (Expression) expr.getE1().accept(this, args);
			Expression e2 = (Expression) expr.getE2().accept(this, args);
			return new BinaryExpr(e1, expr.getOp(), e2, expr.getType());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			String name = "lit_bool_" + index++;
			LocalVariable var = new LocalVariable(true, 0, new Location(),
					name, null, IrFactory.eINSTANCE.createTypeBool());
			locals.add(var.getName(), var);

			it.previous();
			it.add(new Assign(var, expr));
			it.next();
			return new VarExpr(new Use(var));
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object interpret(IntExpr expr, Object... args) {
			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			String name = "lit_int_" + index++;
			LocalVariable var = new LocalVariable(true, 0, new Location(),
					name, null, IrFactory.eINSTANCE.createTypeInt(32));

			locals.add(var.getName(), var);

			it.previous();
			it.add(new Assign(var, expr));
			it.next();
			return new VarExpr(new Use(var));
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			return expr;
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			return expr;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			Expression e = (Expression) expr.getExpr().accept(this, args);
			return new UnaryExpr(expr.getOp(), e, expr.getType());
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			return expr;
		}
	}

	private static int index;

	private OrderedMap<Variable> locals;

	@Override
	public void visit(Assign assign, Object... args) {
		assign.setValue((Expression) assign.getValue().accept(
				new MyExpressionInterpreter(), args));
	}

	@Override
	public void visit(BlockNode node, Object... args) {
		ListIterator<Instruction> it = node.getInstructions().listIterator();
		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	@Override
	public void visit(Store store, Object... args) {
		MyExpressionInterpreter interpret = new MyExpressionInterpreter();
		ListIterator<Expression> it = store.getIndexes().listIterator();
		while (it.hasNext()) {
			it.set((Expression) it.next().accept(interpret, args));
		}
		store.setValue((Expression) store.getValue().accept(interpret, args));
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		index = 0;
		locals = procedure.getLocals();
		List<CFGNode> nodes = procedure.getNodes();
		visit(nodes);
	}

}
