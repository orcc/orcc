/*
 * Copyright (c) 2012, IRISA
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
package net.sf.orcc.tools.merger.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.tools.classifier.GuardSatChecker;

/**
 * This class defines a merger for QSDF actors. In other words, a new action is
 * created for each configuration of the QSDF model of computation.
 * 
 * @author Herve Yviquel
 * 
 */
public class ActionMergerQSDF {

	private static IrFactory factory = IrFactory.eINSTANCE;

	private Actor actor;

	public void merge(Actor actor) {
		this.actor = actor;
		MoC clasz = actor.getMoC();
		if (clasz.isQuasiStatic()) {
			QSDFMoC qsdfmoc = (QSDFMoC) clasz;

			// Remove FSM
			actor.setFsm(null);
			// Remove all actions from action scheduler
			actor.getActionsOutsideFsm().clear();
			actor.getActions().clear();

			GuardSatChecker checker = new GuardSatChecker(actor);
			List<Action> previousActions = new ArrayList<Action>();

			for (Action action : qsdfmoc.getActions()) {
				MoC moc = qsdfmoc.getMoC(action);

				Action currCopy = IrUtil.copy(action);

				for (Action previous : previousActions) {
					@SuppressWarnings("unused")
					boolean checkCompatibility = false;
					Pattern prevPattern = previous.getInputPattern();
					Pattern currPattern = action.getInputPattern();

					// FIXME: better implementation using isSuperSetOf()
					for (Port port : qsdfmoc.getConfigurationPorts()) {
						if (prevPattern.getNumTokens(port) == currPattern
								.getNumTokens(port)) {
							checkCompatibility = true;
						}
					}
					if (checker.checkSat(action, previous)) {
						Action prevCopy = IrUtil.copy(previous);
						removeCompatibility(currCopy, prevCopy);
					}
				}

				if (moc.isSDF()) {
					actor.getActions().add(currCopy);
					actor.getActionsOutsideFsm().add(currCopy);
				} else if (moc.isCSDF()) {
					CSDFMoC csdfMoc = (CSDFMoC) moc;

					Action newAction = new ActionMergerCSDF().merge(
							action.getName(), csdfMoc, actor);
					newAction.setPeekPattern(currCopy.getPeekPattern());
					newAction.setScheduler(currCopy.getScheduler());

					actor.getActions().add(newAction);
					actor.getActionsOutsideFsm().add(newAction);
				} else {
					throw new OrccRuntimeException("Uncompatible MoC");
				}
				previousActions.add(action);
			}
		}
	}

	private void removeCompatibility(Action initial, Action previous) {
		Procedure initSched = initial.getScheduler();
		Procedure prevSched = previous.getScheduler();

		// Update the guard to remove the compatibility between both actions
		// New guard = guard(init) and not(guard(previous))
		List<Instruction> initInstrs = IrUtil.getLast(initSched.getBlocks())
				.getInstructions();
		List<Instruction> prevInstrs = IrUtil.getLast(prevSched.getBlocks())
				.getInstructions();
		InstReturn initRet = (InstReturn) initInstrs.get(initInstrs.size() - 1);
		InstReturn prevRet = (InstReturn) prevInstrs
				.remove(prevInstrs.size() - 1);
		Expression notPrev = factory.createExprUnary(OpUnary.LOGIC_NOT,
				prevRet.getValue(), factory.createTypeBool());
		initRet.setValue(factory.createExprBinary(initRet.getValue(),
				OpBinary.LOGIC_AND, notPrev, factory.createTypeBool()));

		// Rename variables
		for (Var var : prevSched.getLocals()) {
			String varName = var.getName();
			for (int i = 0; isExistingVar(varName, initSched); i++) {
				varName = var.getName() + i;
			}
			var.setName(varName);
		}

		// Merge both schedulers
		initSched.getLocals().addAll(prevSched.getLocals());
		initInstrs.addAll(0, prevInstrs);

		// Update pattern
		Pattern initPeeked = initial.getPeekPattern();
		Pattern prevPeeked = previous.getPeekPattern();
		for (Port port : prevPeeked.getPorts()) {
			if (initPeeked.contains(port)) {
				// Update uses
				for (Use prevUse : new ArrayList<Use>(prevPeeked.getVariable(
						port).getUses())) {
					prevUse.setVariable(initPeeked.getVariable(port));
				}
			} else {
				// Add port and variable
				initPeeked.setVariable(port, prevPeeked.getVariable(port));
				initPeeked.setNumTokens(port, prevPeeked.getNumTokens(port));
			}
		}

	}

	private boolean isExistingVar(String varName, Procedure procedure) {
		return procedure.getLocal(varName) != null
				|| actor.getStateVar(varName) != null;
	}

}
