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

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.network.Network;

/**
 * This class define a network analyzer that compute instruction statistics
 * about actors.
 * 
 * @author Herve Yviquel
 * 
 */
public class InstructionStats {

	private class InstructionStatsBuilder extends AbstractActorVisitor {

		private InstructionStatsElement statsElement;
		
		public InstructionStatsBuilder() {
			super(true);
		}

		public void buildInstructionStats(Actor actor,
				InstructionStatsElement statsElement) {
			this.statsElement = statsElement;
			visit(actor);
		}

		@Override
		public void visit(BinaryExpr expr, Object... args) {
			int count = statsElement.binaryOpNbMap.get(expr.getOp()) + 1;
			statsElement.binaryOpNbMap.put(expr.getOp(), count);
			super.visit(expr, args);
		}

		@Override
		public void visit(IfNode ifNode) {
			statsElement.ifNb++;
			super.visit(ifNode);
		}

		@Override
		public void visit(UnaryExpr expr, Object... args) {
			int count = statsElement.unaryOpNbMap.get(expr.getOp()) + 1;
			statsElement.unaryOpNbMap.put(expr.getOp(), count);
			super.visit(expr, args);
		}

		@Override
		public void visit(WhileNode whileNode) {
			statsElement.whileNb++;
			super.visit(whileNode);
		}
	}

	public class InstructionStatsElement {
		private Map<BinaryOp, Integer> binaryOpNbMap;
		private int ifNb;
		private Map<UnaryOp, Integer> unaryOpNbMap;
		private int whileNb;

		public InstructionStatsElement() {
			ifNb = 0;
			whileNb = 0;
			initializeMaps();
		}

		public Map<BinaryOp, Integer> getBinaryOpNbMap() {
			return binaryOpNbMap;
		}

		public int getIfNb() {
			return ifNb;
		}

		public Map<UnaryOp, Integer> getUnaryOpNbMap() {
			return unaryOpNbMap;
		}

		public int getWhileNb() {
			return whileNb;
		}

		private void initializeMaps() {
			// UnaryOp map
			unaryOpNbMap = new HashMap<UnaryOp, Integer>();
			unaryOpNbMap.put(UnaryOp.BITNOT, 0);
			unaryOpNbMap.put(UnaryOp.LOGIC_NOT, 0);
			unaryOpNbMap.put(UnaryOp.MINUS, 0);
			unaryOpNbMap.put(UnaryOp.NUM_ELTS, 0);

			// BinaryOp map
			binaryOpNbMap = new HashMap<BinaryOp, Integer>();
			binaryOpNbMap.put(BinaryOp.BITAND, 0);
			binaryOpNbMap.put(BinaryOp.BITOR, 0);
			binaryOpNbMap.put(BinaryOp.BITXOR, 0);
			binaryOpNbMap.put(BinaryOp.DIV, 0);
			binaryOpNbMap.put(BinaryOp.DIV_INT, 0);
			binaryOpNbMap.put(BinaryOp.EQ, 0);
			binaryOpNbMap.put(BinaryOp.EXP, 0);
			binaryOpNbMap.put(BinaryOp.GE, 0);
			binaryOpNbMap.put(BinaryOp.GT, 0);
			binaryOpNbMap.put(BinaryOp.LE, 0);
			binaryOpNbMap.put(BinaryOp.LOGIC_AND, 0);
			binaryOpNbMap.put(BinaryOp.LOGIC_OR, 0);
			binaryOpNbMap.put(BinaryOp.LT, 0);
			binaryOpNbMap.put(BinaryOp.MINUS, 0);
			binaryOpNbMap.put(BinaryOp.MOD, 0);
			binaryOpNbMap.put(BinaryOp.NE, 0);
			binaryOpNbMap.put(BinaryOp.PLUS, 0);
			binaryOpNbMap.put(BinaryOp.SHIFT_LEFT, 0);
			binaryOpNbMap.put(BinaryOp.SHIFT_RIGHT, 0);
			binaryOpNbMap.put(BinaryOp.TIMES, 0);
		}
	}

	private InstructionStatsBuilder instrStatsBuilder;
	private Map<Actor, InstructionStatsElement> instructionStatsMap;

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
