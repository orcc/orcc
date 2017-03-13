/*
 * Copyright (c) 2016-2017, Heriot-Watt University Edinburgh
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
package net.sf.orcc.df.transform;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.util.DfUtil;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Void;
import net.sf.orcc.df.util.TransformPreconditionPredicates;

/*
* @author Rob Stewart
* @author Idris Ibrahim
*/
public class SplitJoin extends DfVisitor<Void> {
	private static DfFactory dfFactory = DfFactory.eINSTANCE;
	private static IrFactory irFactory = IrFactory.eINSTANCE;

	int dataParallelismCopies;
	Instance instanceToTransform;
	List<Actor> actorsToWriteToFile = new ArrayList<Actor>();

	public SplitJoin(int numDataParallelCopies, Instance instanceToTransform) {
		dataParallelismCopies = numDataParallelCopies;
		this.instanceToTransform = instanceToTransform;
	}

	/**
	 * 
	 * @return the list of new or modified actors to be serialised to CAL source
	 *         files.
	 */
	public List<Actor> actorsToFile() {
		return actorsToWriteToFile;
	}

	public static boolean canFire(Network network, List<Instance> selectedInstances) {
		boolean canFire = true;

		List<Instance> allInstances = new ArrayList<Instance>();
		for (Vertex vert : network.getVertices()) {
			if (vert instanceof Instance) {
				allInstances.add((Instance) vert);
			}
		}

		Actor actor;
		for (Instance inst : selectedInstances) {
			actor = inst.getActor();
			/*
			 * all selected instances must have exactly one input and one output
			 * port
			 */
			boolean oneInputOneOutput = TransformPreconditionPredicates.hasOneInputPort(actor)
					&& TransformPreconditionPredicates.hasOneOutputPort(actor);

			/* exactly one successor instance of the selected instances */
			boolean oneSuccessor = rightMostInstance(allInstances, selectedInstances).getOutgoing().size() == 1;

			/* exactly one predecessor instance to the selected instances */
			boolean onePredecessor = leftMostInstance(allInstances, selectedInstances).getIncoming().size() == 1;

			/* only one action */
			boolean oneAction = TransformPreconditionPredicates.hasOneAction(actor);

			/*
			 * if any precondition checks fail for any selected instance, then
			 * the fan out/in transformation is disabled.
			 */
			if (!(oneInputOneOutput && oneSuccessor && onePredecessor && oneAction)) {
				canFire = false;
			}
		}
		return canFire;
	}

	protected Network network;

	public Void caseNetwork(Network network) {
		this.network = network;

		/* all instances in the network */
		List<Instance> allInstances = new ArrayList<Instance>();
		for (Vertex vert : network.getVertices()) {
			if (vert instanceof Instance) {
				allInstances.add((Instance) vert);
			}
		}

		/* this code was originally designed for pipelines of selected actor */
		List<Instance> selected = new ArrayList<>();
		selected.add(instanceToTransform);
		Instance leftMost = leftMostInstance(allInstances, selected);
		Instance rightMost = rightMostInstance(allInstances, selected);
		List<Instance> leftToRight = leftToRight(leftMost, selected, allInstances);

		/*
		 * look at the network to find the actor that is feeding data into the
		 * left most actor, the precessor actor to this pipeline.
		 */
		Instance predecessor = whatFeeds(network, leftMost);

		/*
		 * we need to look at the `network`, to find the actor that is fed data
		 * from the right most actor, i.e. the successor actor to this pipeline.
		 */
		Instance successor = whatIsFed(network, rightMost);

		// do the transformation
		String projectRoot = "/" + network.getFileName().split("/")[1];

		/*
		 * token type should the type of the tokens be what the left most actor
		 * was receiving prior to transformation
		 */
		Type fanOutTokenType = leftMost.getActor().getInputs().get(0).getType();

		String fanOutName = "split" + leftMost.getSimpleName() + rightMost.getSimpleName();

		/*
		 * remove previous versions of fan out between left most and right most
		 */
		for (Actor actor : network.getAllActors()) {
			if (actor.getSimpleName().equals(fanOutName)) {
				network.remove(actor);
			}
		}
		for (Vertex vert : network.getAllActors()) {
			if (vert instanceof Instance) {
				Instance inst = (Instance) vert;
				if (inst.getSimpleName().equals(fanOutName)) {
					network.remove(inst);
				}
			}
		}

		/* assumes that the selected actor instance has one action */
		int consumptionRate = consumptionRate(instanceToTransform.getActor().getActions().get(0));
		int productionRate = productionRate(instanceToTransform.getActor().getActions().get(0));

		Actor splitActor = createSplitActor(fanOutName, dataParallelismCopies, fanOutTokenType, consumptionRate,
				projectRoot);
		Instance splitInstance = dfFactory.createInstance(DfUtil.getSimpleName(splitActor.getName()), splitActor);
		network.add(splitActor);
		network.add(splitInstance);
		/* the ports between predecessor and the fanout actor */
		Port outputPortFromPredecessor = predecessor.getOutgoingPortMap().entrySet().iterator().next().getKey();
		Port inputPortOfFanOutActor = splitActor.getInputs().get(0);

		/* remove existing connection between predecessor and leftMost actor */
		int i = 0;
		boolean removed = false;
		while (!removed && i < network.getConnections().size()) {
			Connection c = network.getConnections().get(i);
			Vertex v = c.getSource();
			if (v instanceof Actor) {
				Actor a = (Actor) v;
				if (a.getName().equals(predecessor.getName())) {
					network.getConnections().remove(i);
					removed = true;
				}
			}
			i++;
		}

		Connection connBetweenPredecessorAndFanOut = dfFactory.createConnection(predecessor, outputPortFromPredecessor,
				splitInstance, inputPortOfFanOutActor);
		network.getConnections().add(connBetweenPredecessorAndFanOut);

		Type fanInTokenType = rightMost.getActor().getOutputs().get(0).getType();

		String fanInName = "join" + leftMost.getSimpleName() + rightMost.getSimpleName();

		/*
		 * remove previous versions of fan in between left most and right most
		 */
		for (Actor actor : network.getAllActors()) {
			if (actor.getSimpleName().equals(fanInName)) {
				network.remove(actor);
			}
		}
		for (Vertex vert : network.getAllActors()) {
			if (vert instanceof Instance) {
				Instance inst = (Instance) vert;
				if (inst.getSimpleName().equals(fanInName)) {
					network.remove(inst);
				}
			}
		}

		Actor joinActor = createJoinActor(fanInName, dataParallelismCopies, fanInTokenType, productionRate,
				projectRoot);
		Instance joinInstance = dfFactory.createInstance(DfUtil.getSimpleName(joinActor.getName()), joinActor);
		network.add(joinActor);
		network.add(joinInstance);

		/* the ports between fanin actor and the sucessor */
		Port inputPortofSucessor = successor.getIncomingPortMap().entrySet().iterator().next().getKey();
		Port outputPortFromFanInActor = joinActor.getOutputs().get(0);

		Connection connBetweensuccessorAndFanIn = dfFactory.createConnection(joinInstance, outputPortFromFanInActor,
				successor, inputPortofSucessor);
		network.getConnections().add(connBetweensuccessorAndFanIn);

		/* remove the originally selected actors from the network */
		for (Instance inst : leftToRight) {
			network.remove(inst);
		}

		List<Instance> predecessors = new ArrayList<Instance>();
		List<Port> predecessorPorts = new ArrayList<Port>();

		/* initialise with split details */
		for (int k = 0; k < dataParallelismCopies; k++) {
			predecessors.add(k, splitInstance);
			predecessorPorts.add(k, splitActor.getOutputs().get(k));
		}

		/*
		 * walk along sorted list of selected actors, attaching copies to
		 * predecessor k and preceddor ports for k.
		 */
		for (Instance nextInstance : leftToRight) {
			for (int k = 0; k < dataParallelismCopies; k++) {
				Instance instanceClone = IrUtil.copy(nextInstance);
				instanceClone.setLabel(DfUtil.getSimpleName(nextInstance.getName()) + k);
				network.add(instanceClone);
				Port inputP = nextInstance.getActor().getInputs().get(0);
				Port outputP = nextInstance.getActor().getOutputs().get(0);
				Connection conn = dfFactory.createConnection(predecessors.get(k), predecessorPorts.get(k),
						instanceClone, inputP);
				network.add(conn);

				/* update precessors and precedessorPorts */
				predecessors.set(k, instanceClone);
				predecessorPorts.set(k, outputP);
			}
		}

		/* complete the process by connecting predecessors with fanin actor */
		for (int k = 0; k < dataParallelismCopies; k++) {
			Connection conn = dfFactory.createConnection(predecessors.get(k), predecessorPorts.get(k), joinInstance,
					joinActor.getInputs().get(k));
			network.add(conn);
		}

		return null;
	}

	private int consumptionRate(Action action) {
		int rate = 0;
		for (Instruction inst : action.getBody().getFirst().getInstructions()) {
			if (inst.isInstStore()) {
				rate++;
			}
		}
		return rate;
	}

	private int productionRate(Action action) {
		int rate = 0;
		for (Instruction inst : action.getBody().getFirst().getInstructions()) {
			if (inst.isInstLoad()) {
				rate++;
			}
		}
		return rate;
	}

	/**
	 * 
	 * @param network
	 * @param leftMost
	 *            the left most (earliest in the stream) selected instance
	 * @return the instance that is the predecessor to the left most instance
	 */
	private Instance whatFeeds(Network network, Instance leftMost) {
		Vertex v = leftMost.getIncoming().get(0).getSource();

		if (v instanceof Instance) {
			return ((Instance) v);
		} else
			return null;
	}

	/**
	 * 
	 * @param network
	 * @param rightMost
	 *            the right most (last in the stream) selected instance
	 * @return the instance that is the successor to the right most instance
	 */
	private Instance whatIsFed(Network network, Instance rightMost) {
		Vertex v = rightMost.getOutgoing().get(0).getTarget();
		if (v instanceof Instance) {
			return ((Instance) v);
		} else
			return null;
	}

	/**
	 * 
	 * @param leftMost
	 *            the instance earliest in the stream
	 * @param selectedInstances
	 *            the graphically selected instances
	 * @param allInstances
	 *            all instances in the network
	 * @return list of selected instances, ordered early to late in the stream
	 */
	private List<Instance> leftToRight(Instance leftMost, List<Instance> selectedInstances,
			List<Instance> allInstances) {
		List<Instance> ordered = new ArrayList<Instance>();
		ordered.add(leftMost);
		selectedInstances.remove(leftMost);
		if (!selectedInstances.isEmpty()) {
			Instance nextLeftMost = leftMostInstance(allInstances, selectedInstances);
			ordered.addAll(leftToRight(nextLeftMost, selectedInstances, allInstances));
		}
		return ordered;
	}

	/**
	 * 
	 * @param allInstances
	 *            all instance in the network
	 * @param selectedInstances
	 *            graphically selected instances
	 * @return the instance earliest in the stream
	 */
	private static Instance leftMostInstance(List<Instance> allInstances, List<Instance> selectedInstances) {
		Instance leftMost = null;
		for (Instance next : selectedInstances) {
			List<Instance> predecessors = allPredecesorsOfOneInstance(next);
			boolean anyInstancesInPredecessors = false;
			for (Instance act : selectedInstances) {
				if (predecessors.contains(act)) {
					anyInstancesInPredecessors = true;
				}
			}
			if (!anyInstancesInPredecessors) {
				leftMost = next;
			}
		}

		if (leftMost == null) {
			OrccLogger.severeln("Unable to find left most in leftMostInstance()");
		}

		return leftMost;
	}

	/**
	 * 
	 * @param allInstances
	 *            all instance in the network
	 * @param selectedInstances
	 *            graphically selected instances
	 * @return the instance last in the stream
	 */
	private static Instance rightMostInstance(List<Instance> allInstances, List<Instance> selectedInstances) {
		Instance rightMost = null;
		for (Instance next : selectedInstances) {
			List<Instance> sucessors = allSuccessorsOfOneInstance(next);
			boolean anyInstancesInSuccessors = false;
			for (Instance act : selectedInstances) {
				if (sucessors.contains(act)) {
					anyInstancesInSuccessors = true;
				}
			}
			if (!anyInstancesInSuccessors) {
				rightMost = next;
			}
		}

		if (rightMost == null) {
			OrccLogger.severeln("Unable to find right most in rightMostInstance()");
		}

		return rightMost;
	}

	/**
	 * 
	 * @param thisInstance
	 * @return the list of instances connected as predecessors to the instance
	 */
	private static List<Instance> allPredecesorsOfOneInstance(Instance thisInstance) {
		List<Instance> allPredecessors = new ArrayList<Instance>();
		for (Edge e : thisInstance.getIncoming()) {
			Vertex v = e.getSource();
			if (v instanceof Instance) {
				Instance inst = (Instance) v;
				allPredecessors.add(inst);
				allPredecessors.addAll(allPredecesorsOfOneInstance(inst));
			}
		}
		return allPredecessors;
	}

	/**
	 * 
	 * @param thisInstance
	 * @return the list of instances connected as successors to the instance
	 */
	private static List<Instance> allSuccessorsOfOneInstance(Instance thisInstance) {
		List<Instance> allSuccessors = new ArrayList<Instance>();
		for (Edge e : thisInstance.getOutgoing()) {
			Vertex v = e.getTarget();
			if (v instanceof Instance) {
				Instance inst = (Instance) v;
				allSuccessors.add(inst);
				allSuccessors.addAll(allSuccessorsOfOneInstance(inst));
			}
		}
		return allSuccessors;
	}

	/**
	 * 
	 * @param parallelismDegree
	 *            how many data parallel actors
	 * @param tokenType
	 *            the type of the tokens to be fanned out
	 * @param consumptionRate
	 *            how many tokens are consumed by the one action in each replica
	 * @param projectRoot
	 *            needed to create filename for the new actor
	 * @return the fan out actor
	 */
	private Actor createSplitActor(String name, int parallelismDegree, Type tokenType, int consumptionRate,
			String projectRoot) {
		/* Create a new actor */
		Actor splitActor = dfFactory.createActor();
		splitActor.setName("gen." + name);

		/* create one split input port */
		Port inputPort = dfFactory.createPort(tokenType, "In1");
		splitActor.getInputs().add(inputPort);

		/* create multiple split output ports */
		for (int n = 1; n <= parallelismDegree; n++) {
			Port outputPort = dfFactory.createPort(tokenType, "Out" + n);
			splitActor.getOutputs().add(outputPort);
		}

		// create output Pattern
		Pattern splitOutPattern = null;

		List<Action> actionsForFSM = new ArrayList<Action>();
		for (int n = 1; n <= parallelismDegree; n++) {
			/* create action */
			Procedure proc = irFactory.createProcedure("split" + n, 0, irFactory.createTypeVoid());
			BlockBasic blockSplit = IrUtil.getLast(proc.getBlocks());

			/* create input Pattern */
			Pattern splitInPattern = dfFactory.createPattern();
			splitInPattern.setNumTokens(inputPort, consumptionRate);

			/* create the output pattern */
			splitOutPattern = dfFactory.createPattern();
			splitOutPattern.setNumTokens(splitActor.getPort("Out" + n), consumptionRate);

			// create port variable
			Var variableIn = eINSTANCE.createVar(0, eINSTANCE.createTypeList(consumptionRate, inputPort.getType()),
					inputPort.getName(), true, 0);

			Var variableOut = eINSTANCE.createVar(0, eINSTANCE.createTypeList(consumptionRate, inputPort.getType()),
					splitActor.getPort("Out" + n).getName(), true, 0);

			splitInPattern.setVariable(inputPort, variableIn);

			for (int i = 0; i < consumptionRate; i++) {
				List<Expression> indexesIn = new ArrayList<Expression>(1);
				indexesIn.add(eINSTANCE.createExprInt(i));
				int lineNumber = variableIn.getLineNumber();
				splitInPattern.setVariable(inputPort, variableIn);
				Var vIn = irFactory.createVar(tokenType, "v" + i, false, 0);
				InstLoad instLoad = eINSTANCE.createInstLoad(lineNumber, vIn, variableIn, indexesIn);
				blockSplit.add(instLoad);

				Var vOut = irFactory.createVar(tokenType, "v" + i, false, 0);
				List<Expression> indexesOut = new ArrayList<Expression>(1);
				indexesOut.add(eINSTANCE.createExprInt(i));
				Expression vOutExpr = irFactory.createExprVar(vOut);
				InstStore instStore = eINSTANCE.createInstStore(lineNumber, variableOut, indexesOut, vOutExpr);
				blockSplit.add(instStore);
			}

			Procedure splitScheduler = irFactory.createProcedure("isSchedulable_split" + n, 0,
					irFactory.createTypeBool());

			/* add the action */
			Action splitAction = dfFactory.createAction("split" + n, splitInPattern, splitOutPattern,
					dfFactory.createPattern(), splitScheduler, proc);

			splitAction.getBody().getBlocks().add(blockSplit);
			splitActor.getActions().add(splitAction);
			actionsForFSM.add(splitAction);
		}

		/* create the split pipelining FSM */
		FSM splitFSM = dfFactory.createFSM();
		splitFSM.setInitialState(dfFactory.createState("s0"));
		for (int s = 0; s < parallelismDegree - 1; s++) {
			State srcState = dfFactory.createState("s" + s);
			State destState = dfFactory.createState("s" + (s + 1));
			splitFSM.addTransition(srcState, actionsForFSM.get(s), destState);
		}
		/* loop back to the initial state */
		State srcState = dfFactory.createState("s" + (parallelismDegree - 1));
		State destState = dfFactory.createState("s0");
		splitFSM.addTransition(srcState, actionsForFSM.get((parallelismDegree - 1)), destState);

		splitActor.setFsm(splitFSM);

		/* writes split actor to src/gen/ */
		String actorFilename = projectRoot + "/src/gen/" + DfUtil.getSimpleName(splitActor.getName()) + ".cal";
		splitActor.setFileName(actorFilename);
		actorsToWriteToFile.add(splitActor);
		return splitActor;
	}

	/**
	 * 
	 * @param parallelismDegree
	 *            how many data parallel actors
	 * @param tokenType
	 *            the type of the tokens to be fanned in
	 * @param productionRate
	 *            how many tokens are produced by the one action in each replica
	 * @param projectRoot
	 *            needed to create filename for the new actor
	 * @return the fan in actor
	 */
	private Actor createJoinActor(String name, int parallelismDegree, Type tokenType, int productionRate,
			String projectRoot) {
		/* Create a new actor */
		Actor joinActor = dfFactory.createActor();
		joinActor.setName("gen." + name);

		/* create multiple join input port */
		for (int n = 1; n <= parallelismDegree; n++) {
			Port inputPort = dfFactory.createPort(tokenType, "In" + n);
			joinActor.getInputs().add(inputPort);
		}

		/* create one join output ports */
		Port outputPort = dfFactory.createPort(tokenType, "Out1");
		joinActor.getOutputs().add(outputPort);

		// create output Pattern
		Pattern joinOutPattern = null;

		List<Action> actionsForFSM = new ArrayList<Action>();
		for (int n = 1; n <= parallelismDegree; n++) {
			/* create action */
			Procedure proc = irFactory.createProcedure("join" + n, 0, irFactory.createTypeVoid());
			BlockBasic blockJoin = IrUtil.getLast(proc.getBlocks());

			/* create output Pattern */
			Pattern joinInPattern = dfFactory.createPattern();
			joinInPattern.setNumTokens(outputPort, productionRate);

			/* create the inp[ut pattern */
			joinOutPattern = dfFactory.createPattern();
			joinOutPattern.setNumTokens(joinActor.getPort("In" + n), productionRate);

			// create port variable
			Var variableIn = eINSTANCE.createVar(0, eINSTANCE.createTypeList(productionRate, outputPort.getType()),
					joinActor.getPort("In" + n).getName(), true, 0);

			Var variableOut = eINSTANCE.createVar(0, eINSTANCE.createTypeList(productionRate, outputPort.getType()),
					outputPort.getName(), true, 0);

			joinOutPattern.setVariable(outputPort, variableIn);

			for (int i = 0; i < productionRate; i++) {
				List<Expression> indexesIn = new ArrayList<Expression>(1);
				indexesIn.add(eINSTANCE.createExprInt(i));
				int lineNumber = variableIn.getLineNumber();
				joinInPattern.setVariable(outputPort, variableIn);
				Var vIn = irFactory.createVar(tokenType, "v" + i, false, 0);
				InstLoad instLoad = eINSTANCE.createInstLoad(lineNumber, vIn, variableIn, indexesIn);
				blockJoin.add(instLoad);

				Var vOut = irFactory.createVar(tokenType, "v" + i, false, 0);
				List<Expression> indexesOut = new ArrayList<Expression>(1);
				indexesOut.add(eINSTANCE.createExprInt(i));
				Expression vOutExpr = irFactory.createExprVar(vOut);
				InstStore instStore = eINSTANCE.createInstStore(lineNumber, variableOut, indexesOut, vOutExpr);
				blockJoin.add(instStore);
			}

			Procedure joinScheduler = irFactory.createProcedure("isSchedulable_join" + n, 0,
					irFactory.createTypeBool());

			/* add the action */
			Action joinAction = dfFactory.createAction("join" + n, joinInPattern, joinOutPattern,
					dfFactory.createPattern(), joinScheduler, proc);

			joinAction.getBody().getBlocks().add(blockJoin);

			joinActor.getActions().add(joinAction);
			actionsForFSM.add(joinAction);
		}

		/* create the split pipelining FSM */
		FSM splitFSM = dfFactory.createFSM();
		splitFSM.setInitialState(dfFactory.createState("s0"));
		for (int s = 0; s < parallelismDegree - 1; s++) {
			State srcState = dfFactory.createState("s" + s);
			State destState = dfFactory.createState("s" + (s + 1));
			splitFSM.addTransition(srcState, actionsForFSM.get(s), destState);
		}
		/* loop back to the initial state */
		State srcState = dfFactory.createState("s" + (parallelismDegree - 1));
		State destState = dfFactory.createState("s0");
		splitFSM.addTransition(srcState, actionsForFSM.get((parallelismDegree - 1)), destState);

		joinActor.setFsm(splitFSM);

		/* writes split actor to src/gen/ */
		String actorFilename = projectRoot + "/src/gen/" + DfUtil.getSimpleName(joinActor.getName()) + ".cal";
		joinActor.setFileName(actorFilename);
		actorsToWriteToFile.add(joinActor);
		return joinActor;
	}

}
