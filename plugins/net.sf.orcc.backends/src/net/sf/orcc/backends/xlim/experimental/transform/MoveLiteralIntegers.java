package net.sf.orcc.backends.xlim.experimental.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.util.OrderedMap;

public class MoveLiteralIntegers extends AbstractActorTransformation {

	private static int index;

	private OrderedMap<Variable> locals;

	private List<Instruction> instructions;

	@Override
	public void visit(BlockNode node, Object... args) {
		instructions = node.getInstructions();
		ListIterator<Instruction> it = new ArrayList<Instruction>(instructions)
				.listIterator();
		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	@Override
	public void visit(Store store, Object... args) {
		ListIterator<Expression> it = store.getIndexes().listIterator();
		while (it.hasNext()) {
			Expression expr = it.next();
			if (expr.isIntExpr()) {
				String name = "lit_int_" + index++;
				LocalVariable var = new LocalVariable(true, 0, new Location(),
						name, null, new IntType(32));
				locals.add(var.getName(), var);

				int index = instructions.indexOf(store);
				instructions.add(index, new Assign(var, expr));

				it.set(new VarExpr(new Use(var)));
			}
		}
	}

	@Override
	public void visitProcedure(Procedure procedure) {
		index = 0;
		locals = procedure.getLocals();
		List<CFGNode> nodes = procedure.getNodes();
		visit(nodes);
	}

}
