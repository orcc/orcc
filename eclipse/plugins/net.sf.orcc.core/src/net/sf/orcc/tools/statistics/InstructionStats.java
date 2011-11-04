/*
 * Copyright (c) 2011, IRISA
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
package net.sf.orcc.tools.statistics;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Network;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class define a network analyzer that compute instruction statistics
 * about actors.
 * 
 * @author Herve Yviquel
 * 
 */
public class InstructionStats {

	private class InstructionStatsBuilder extends AbstractActorVisitor<Object> {

		private InstructionStatsElement statsElement;

		public InstructionStatsBuilder() {
			super(true);
		}

		public void buildInstructionStats(Actor actor,
				InstructionStatsElement statsElement) {
			this.statsElement = statsElement;
			doSwitch(actor);
		}

		@Override
		public Object caseExprBinary(ExprBinary expr) {
			int count = statsElement.binaryOpNbMap.get(expr.getOp()) + 1;
			statsElement.binaryOpNbMap.put(expr.getOp(), count);
			return super.caseExprBinary(expr);
		}

		@Override
		public Object caseNodeIf(NodeIf nodeIf) {
			statsElement.ifNb++;
			return super.caseNodeIf(nodeIf);
		}

		@Override
		public Object caseExprUnary(ExprUnary expr) {
			int count = statsElement.unaryOpNbMap.get(expr.getOp()) + 1;
			statsElement.unaryOpNbMap.put(expr.getOp(), count);
			return super.caseExprUnary(expr);
		}

		@Override
		public Object caseNodeWhile(NodeWhile nodeWhile) {
			statsElement.whileNb++;
			return super.caseNodeWhile(nodeWhile);
		}
	}

	public class InstructionStatsElement {
		private Map<OpBinary, Integer> binaryOpNbMap;
		private int ifNb;
		private Map<OpUnary, Integer> unaryOpNbMap;
		private int whileNb;

		public InstructionStatsElement() {
			ifNb = 0;
			whileNb = 0;
			initializeMaps();
		}

		public Map<OpBinary, Integer> getBinaryOpNbMap() {
			return binaryOpNbMap;
		}

		public int getIfNb() {
			return ifNb;
		}

		public Map<OpUnary, Integer> getUnaryOpNbMap() {
			return unaryOpNbMap;
		}

		public int getWhileNb() {
			return whileNb;
		}

		private void initializeMaps() {
			// UnaryOp map
			unaryOpNbMap = new HashMap<OpUnary, Integer>();
			unaryOpNbMap.put(OpUnary.BITNOT, 0);
			unaryOpNbMap.put(OpUnary.LOGIC_NOT, 0);
			unaryOpNbMap.put(OpUnary.MINUS, 0);
			unaryOpNbMap.put(OpUnary.NUM_ELTS, 0);

			// BinaryOp map
			binaryOpNbMap = new HashMap<OpBinary, Integer>();
			binaryOpNbMap.put(OpBinary.BITAND, 0);
			binaryOpNbMap.put(OpBinary.BITOR, 0);
			binaryOpNbMap.put(OpBinary.BITXOR, 0);
			binaryOpNbMap.put(OpBinary.DIV, 0);
			binaryOpNbMap.put(OpBinary.DIV_INT, 0);
			binaryOpNbMap.put(OpBinary.EQ, 0);
			binaryOpNbMap.put(OpBinary.EXP, 0);
			binaryOpNbMap.put(OpBinary.GE, 0);
			binaryOpNbMap.put(OpBinary.GT, 0);
			binaryOpNbMap.put(OpBinary.LE, 0);
			binaryOpNbMap.put(OpBinary.LOGIC_AND, 0);
			binaryOpNbMap.put(OpBinary.LOGIC_OR, 0);
			binaryOpNbMap.put(OpBinary.LT, 0);
			binaryOpNbMap.put(OpBinary.MINUS, 0);
			binaryOpNbMap.put(OpBinary.MOD, 0);
			binaryOpNbMap.put(OpBinary.NE, 0);
			binaryOpNbMap.put(OpBinary.PLUS, 0);
			binaryOpNbMap.put(OpBinary.SHIFT_LEFT, 0);
			binaryOpNbMap.put(OpBinary.SHIFT_RIGHT, 0);
			binaryOpNbMap.put(OpBinary.TIMES, 0);
		}
	}

	private InstructionStatsBuilder instrStatsBuilder;
	private Map<Actor, InstructionStatsElement> instructionStatsMap = new HashMap<Actor, InstructionStatsElement>();

	public InstructionStats() {
		instrStatsBuilder = new InstructionStatsBuilder();
	}

	public void computeInstructionStats(Network network) {
		for (Actor actor : network.getActors()) {
			InstructionStatsElement statsElement = new InstructionStatsElement();
			instrStatsBuilder.buildInstructionStats(actor, statsElement);
			instructionStatsMap.put(actor, statsElement);
		}
	}

	public Map<Actor, InstructionStatsElement> getInstructionStatsMap() {
		return instructionStatsMap;
	}

}
