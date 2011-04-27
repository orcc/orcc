package net.sf.orcc.tools.merger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

import org.eclipse.emf.ecore.util.EcoreUtil;

public class ChangeFifoArrayAccess extends AbstractActorVisitor<Object> {

	private Map<Var, Integer> loads;
	private Map<Var, Integer> stores;
	private NodeBlock currentBlock;

	private Actor superActor;

	private Pattern inputPattern;

	private Pattern outputPattern;

	private Map<Port, Port> portsMap;

	public ChangeFifoArrayAccess(Pattern inputPattern,
			Pattern outputPattern, Map<Port, Port> portsMap) {
		this.inputPattern = inputPattern;
		this.outputPattern = outputPattern;
		this.portsMap = portsMap;
	}

	@Override
	public Object caseActor(Actor actor) {
		superActor = actor;
		for (Procedure proc : actor.getProcs()) {
			loads = new HashMap<Var, Integer>();
			stores = new HashMap<Var, Integer>();

			doSwitch(proc);

			currentBlock = proc.getLast();

			updateLoadIndex();
			updateStoreIndex();
		}
		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		final IrFactory factory = IrFactory.eINSTANCE;
		Use use = load.getSource();
		Var var = use.getVariable();
		Port port = inputPattern.getVarToPortMap().get(var);

		if (var.isLocal() && port != null) {
			int cns = portsMap.get(port).getNumTokensConsumed();
			var = superActor.getStateVar(portsMap.get(port).getName());

			loads.put(var, cns);

			use.setVariable(var);

			List<Expression> indexes = load.getIndexes();

			Expression e1 = factory.createExprVar(factory.createUse(superActor
					.getStateVar(var.getName() + "_r")));
			Expression e2 = EcoreUtil.copy(indexes.get(0));
			Expression bop = factory.createExprBinary(e1, OpBinary.PLUS, e2,
					null);
			indexes.set(0, bop);
		}

		return null;
	}

	@Override
	public Object caseInstStore(InstStore store) {
		final IrFactory factory = IrFactory.eINSTANCE;

		Def def = store.getTarget();
		Var var = def.getVariable();
		Port port = outputPattern.getVarToPortMap().get(var);

		if (var.isLocal() && port != null) {
			int prd = portsMap.get(port).getNumTokensProduced();
			var = superActor.getStateVar(portsMap.get(port).getName());

			stores.put(var, prd);

			def.setVariable(var);

			Expression e1 = factory.createExprVar(factory.createUse(superActor
					.getStateVar(var.getName() + "_w")));
			Expression e2 = EcoreUtil.copy(store.getIndexes().get(0));
			Expression bop = factory.createExprBinary(e1, OpBinary.PLUS, e2,
					null);
			store.getIndexes().set(0, bop);
		}

		return null;
	}

	private void updateLoadIndex() {
		for (Map.Entry<Var, Integer> entry : loads.entrySet()) {
			Var var = entry.getKey();
			int cns = entry.getValue();

			Var readVar = superActor.getStateVar(var.getName() + "_r");
			IrFactory factory = IrFactory.eINSTANCE;
			ExprBinary incr = factory.createExprBinary(
					factory.createExprVar(factory.createUse(readVar)),
					OpBinary.PLUS, factory.createExprInt(cns), null);

			InstStore store = factory.createInstStore(readVar, incr);
			currentBlock.add(store);
		}
	}

	private void updateStoreIndex() {
		for (Map.Entry<Var, Integer> entry : stores.entrySet()) {
			Var var = entry.getKey();
			int prd = entry.getValue();

			Var readVar = superActor.getStateVar(var.getName() + "_w");

			IrFactory factory = IrFactory.eINSTANCE;
			ExprBinary incr = factory.createExprBinary(
					factory.createExprVar(factory.createUse(readVar)),
					OpBinary.PLUS, factory.createExprInt(prd), null);

			InstStore store = factory.createInstStore(readVar, incr);
			currentBlock.add(store);
		}
	}

}
