package net.sf.orcc.tools.merger;

import static net.sf.orcc.moc.MocFactory.eINSTANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.util.EcoreUtil;

public class SdfToHsdf extends DfSwitch<Network> {

	private Map<Vertex, Integer> repetitions;

	private int joinIndex;

	private int splitIndex;

	private Actor actor;

	private Pattern inputPattern;

	private Pattern outputPattern;

	public SdfToHsdf(Map<Vertex, Integer> repetitions) {
		this.repetitions = repetitions;
	}

	private Procedure createJoinScheduler() {
		Procedure sched = IrFactory.eINSTANCE.createProcedure();
		return sched;
	}

	private Procedure createJoinBody() {
		final IrFactory factory = IrFactory.eINSTANCE;
		Procedure body = factory.createProcedure();
		Var outVar = outputPattern.getVariable(actor.getOutput("out"));

		Var loop = factory.createVar(0, factory.createTypeInt(32), "idx",
				false, true);

		body.getLast().add(
				factory.createInstAssign(loop, factory.createExprInt(0)));

		int cns = inputPattern.getNumTokens(actor.getInputs().get(0));

		Expression condition = factory.createExprBinary(
				factory.createExprVar(loop), OpBinary.LT,
				factory.createExprInt(cns), factory.createTypeBool());

		NodeWhile nodeWhile = factory.createNodeWhile();
		nodeWhile.setJoinNode(factory.createNodeBlock());
		nodeWhile.setCondition(condition);
		body.getNodes().add(nodeWhile);

		NodeBlock whileBlock = IrUtil.getLast(nodeWhile.getNodes());

		int ind = 0;
		for (Port input : actor.getInputs()) {
			Var tmp = factory.createVar(0, EcoreUtil.copy(input.getType()),
					"tmp_" + ind, false, true);
			body.getLocals().add(tmp);

			Var var = inputPattern.getVariable(input);
			List<Expression> indexes = new ArrayList<Expression>();
			indexes.add(factory.createExprVar(loop));
			InstLoad load = factory.createInstLoad(tmp, var, indexes);

			whileBlock.add(load);

			Expression offset = IrFactory.eINSTANCE.createExprBinary(
					factory.createExprInt(ind), OpBinary.TIMES,
					factory.createExprInt(cns), factory.createTypeInt(32));

			offset = factory.createExprInt((Integer) new ExpressionEvaluator()
					.doSwitch(offset));

			Expression index = IrFactory.eINSTANCE.createExprBinary(
					factory.createExprVar(loop), OpBinary.PLUS, offset,
					factory.createTypeInt(32));

			indexes = new ArrayList<Expression>();
			indexes.add(index);

			InstStore store = factory.createInstStore(0, outVar, indexes,
					factory.createExprVar(tmp));
			whileBlock.add(store);
			ind++;
		}
		Expression expr = factory.createExprBinary(factory.createExprVar(loop),
				OpBinary.PLUS, factory.createExprInt(1),
				factory.createTypeInt(32));
		InstAssign assign = factory.createInstAssign(loop, expr);
		whileBlock.add(assign);

		return body;
	}

	private Action createJoinAction() {
		Action action = DfFactory.eINSTANCE.createAction();
		action.setTag(DfFactory.eINSTANCE.createTag("join"));

		setPattern(action);

		action.setScheduler(createJoinScheduler());
		action.setBody(createJoinBody());

		return action;
	}

	private void setPattern(Action action) {
		// set sdf moc
		SDFMoC moc = (SDFMoC) actor.getMoC();
		inputPattern = EcoreUtil.copy(moc.getInputPattern());
		outputPattern = EcoreUtil.copy(moc.getOutputPattern());
		action.setInputPattern(inputPattern);
		action.setOutputPattern(outputPattern);

		Port output = actor.getOutput("out");
		int prd = outputPattern.getNumTokens(output);
		Var outVar = IrFactory.eINSTANCE.createVar(
				0,
				IrFactory.eINSTANCE.createTypeList(
						IrFactory.eINSTANCE.createExprInt(prd),
						output.getType()), "out", true, 0);
		outputPattern.setNumTokens(output, prd);
		outputPattern.setVariable(output, outVar);
		for (Port input : actor.getInputs()) {
			int cns = inputPattern.getNumTokens(input);
			// add input var
			Var var = IrFactory.eINSTANCE.createVar(
					0,
					IrFactory.eINSTANCE.createTypeList(
							IrFactory.eINSTANCE.createExprInt(cns),
							input.getType()), input.getName(), false, true);
			inputPattern.setNumTokens(input, cns);
			inputPattern.setVariable(input, var);
		}
	}

	/**
	 * 
	 * @param connection
	 * @return
	 */
	private Actor createJoin(Connection connection) {
		final DfFactory factory = DfFactory.eINSTANCE;
		Actor join = factory.createActor();
		join.setName("join" + joinIndex++);

		SDFMoC joinMoc = MocFactory.eINSTANCE.createSDFMoC();
		join.setMoC(joinMoc);

		Instance src = (Instance) connection.getSource();
		int rep = repetitions.get(src);
		Port srcPort = connection.getSourcePort();
		int prd = ((CSDFMoC) src.getMoC()).getNumTokensProduced(srcPort);

		// output pattern
		Port joinPort = factory.createPort(EcoreUtil.copy(srcPort.getType()),
				"out");
		join.getOutputs().add(joinPort);
		joinMoc.getOutputPattern().setNumTokens(joinPort, rep * prd);

		// input pattern
		for (int i = 0; i < rep; i++) {
			Port tgtPort = factory.createPort(
					EcoreUtil.copy(srcPort.getType()), "in" + i);
			join.getInputs().add(tgtPort);
			joinMoc.getInputPattern().setNumTokens(tgtPort, prd);
		}

		actor = join;
		Action action = createJoinAction();
		join.getActions().add(action);
		Invocation invocation = eINSTANCE.createInvocation(action);
		joinMoc.getInvocations().add(invocation);
		return join;
	}

	/**
	 * 
	 * @param connection
	 * @return
	 */
	private Actor createSplit(Connection connection) {
		final DfFactory factory = DfFactory.eINSTANCE;
		Actor split = factory.createActor();
		split.setName("split" + splitIndex++);
		SDFMoC splitMoc = MocFactory.eINSTANCE.createSDFMoC();
		split.setMoC(splitMoc);

		Instance tgt = (Instance) connection.getTarget();
		int rep = repetitions.get(tgt);
		Port tgtPort = connection.getTargetPort();
		int cns = ((CSDFMoC) tgt.getMoC()).getNumTokensConsumed(tgtPort);

		// input pattern
		Port splitPort = factory.createPort(EcoreUtil.copy(tgtPort.getType()),
				"in");
		split.getInputs().add(splitPort);
		splitMoc.getInputPattern().setNumTokens(splitPort, rep * cns);

		// output pattern
		for (int i = 0; i < rep; i++) {
			Port srcPort = factory.createPort(
					EcoreUtil.copy(tgtPort.getType()), "out" + i);
			split.getOutputs().add(srcPort);
			splitMoc.getOutputPattern().setNumTokens(srcPort, cns);
		}

		return split;
	}

	/**
	 * 
	 */
	@Override
	public Network caseNetwork(Network network) {
		List<Instance> instancesToRemove = new ArrayList<Instance>();
		List<Connection> connections = new ArrayList<Connection>(
				network.getConnections());

		DfFactory factory = DfFactory.eINSTANCE;
		for (Connection connection : connections) {
			Vertex src = connection.getSource();
			if (src.isInstance()) {
				int rep = repetitions.get(src);
				if (rep > 1) {
					// add join node and repeated instances
					Actor join = createJoin(connection);
					Instance instJoin = factory.createInstance(join.getName(),
							join);
					network.getInstances().add(instJoin);

					for (int i = 0; i < rep; i++) {
						Instance srcCopy = network.getInstance(src.getName()
								+ i);
						if (srcCopy == null) {
							srcCopy = EcoreUtil.copy((Instance) src);
							srcCopy.setName(srcCopy.getName() + i);
							network.getInstances().add(srcCopy);
						}
						Connection connectionNew = factory.createConnection(
								srcCopy, connection.getSourcePort(), instJoin,
								join.getInput("in" + i));
						network.getConnections().add(connectionNew);
					}

					// update current connection
					connection.setSource(instJoin);
					connection.setSourcePort(join.getOutput("out"));

					instancesToRemove.add((Instance) src);
				}
			}

			Vertex tgt = connection.getTarget();
			if (tgt.isInstance()) {
				int rep = repetitions.get(tgt);
				if (rep > 1) {
					Actor split = createSplit(connection);
					Instance splitInst = factory.createInstance(
							split.getName(), split);
					network.getInstances().add(splitInst);

					for (int i = 0; i < rep; i++) {
						Instance tgtCopy = network.getInstance(tgt.getName()
								+ i);
						if (tgtCopy == null) {
							tgtCopy = EcoreUtil.copy((Instance) tgt);
							tgtCopy.setName(tgtCopy.getName() + i);
							network.getInstances().add(tgtCopy);
						}
						Connection connectionNew = factory.createConnection(
								splitInst, split.getOutput("out" + i), tgtCopy,
								connection.getTargetPort());
						network.getConnections().add(connectionNew);
					}

					// update current connection
					connection.setTarget(splitInst);
					connection.setTargetPort(split.getInput("in"));

					instancesToRemove.add((Instance) tgt);
				}
			}
		}

		network.getInstances().removeAll(instancesToRemove);

		return network;
	}
}
