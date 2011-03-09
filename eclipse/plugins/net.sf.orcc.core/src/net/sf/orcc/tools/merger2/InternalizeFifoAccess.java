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

package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;

public class InternalizeFifoAccess extends AbstractActorVisitor {
	private List<Variable> localFifoVars;
	private Port port;

	private Procedure body;
	private GlobalVariable readCount;
	private GlobalVariable writeCount;
	private GlobalVariable variable;

	public InternalizeFifoAccess(Port port, GlobalVariable variable,
			GlobalVariable readCount, GlobalVariable writeCount) {
		this.port = port;
		this.variable = variable;
		this.readCount = readCount;
		this.writeCount = writeCount;
		this.localFifoVars = new ArrayList<Variable>();
	}

	private void setIndex(GlobalVariable count, Instruction instr, List<Expression> indexes) {

		if (indexes.size() < 2) {
			Use use = new Use(count, instr);
			BinaryExpr expr = new BinaryExpr(new VarExpr(use), BinaryOp.PLUS,
					indexes.get(0), IrFactory.eINSTANCE.createTypeInt(32));

			indexes.set(0, expr);

		} else {
			System.err.println("TODO index");
		}
	}

	@Override
	public void visit(Action action) {	
		// Get action pattern
		this.body = action.getBody();
		int consumed = updatePattern(action.getInputPattern());
		int produced = updatePattern(action.getOutputPattern());
		
		// Port has not been found in both patterns
		if (localFifoVars.size()==0){
			// Nothing to do
			return;
		}
		
		super.visit(action);

		// Update counter
		updateCounter(readCount, consumed);
		updateCounter(writeCount, produced);
	}

	private void updateCounter(GlobalVariable counter, int nbTokens){
		if (nbTokens == 0 ){
			// Update is useless
			return;
		}
		
		BlockNode node = BlockNode.getLast(body);
		
		Use use = new Use(counter);
		
		Store store = new Store(counter, new BinaryExpr(new VarExpr(
				use), BinaryOp.PLUS, new IntExpr(nbTokens),
				IrFactory.eINSTANCE.createTypeInt(32)));
		node.add(store);
		
	}
	
	private int updatePattern(Pattern pattern){
		if (!pattern.contains(port)){
			//Current port is not contains in the pattern
			return 0;
		}
		
		//Get information from pattern
		int tokens = pattern.getNumTokens(port);
		localFifoVars.add(pattern.getVariable(port));
		localFifoVars.add(pattern.getPeeked(port));
		
		//Remove pattern
		pattern.remove(port);
		
		return tokens;
	}
	
	@Override
	public void visit(Load load) {
		Use use = load.getSource();
		Variable var = use.getVariable();
		if (localFifoVars.contains(var)) {
			load.setSource(new Use(variable, load));
			setIndex(readCount, load, load.getIndexes());
		}
	}

	@Override
	public void visit(Store store) {
		Variable var = store.getTarget();
		if (localFifoVars.contains(var)) {
			store.setTarget(variable);
			setIndex(writeCount, store, store.getIndexes());
		}
	}
}