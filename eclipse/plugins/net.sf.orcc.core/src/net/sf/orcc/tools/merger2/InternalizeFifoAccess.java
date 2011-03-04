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
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.util.OrderedMap;

public class InternalizeFifoAccess extends AbstractActorVisitor {
	public class LinksToStateVar extends AbstractActorVisitor {	
		@Override
		public void visit(Load load) {
			Use use = load.getSource();
			Variable var = use.getVariable();
			if (localFifoVars.contains(var)) {
				load.setSource(new Use(variable, load));
				//updateIndex(var, load, load.getIndexes());
			}
		}
		
		@Override
		public void visit(Procedure procedure) {
			super.visit(procedure);
			
			//Remove locals from procedure
			OrderedMap<String, LocalVariable> locals = procedure.getLocals();
			
			for (Variable fifoVar : localFifoVars){
				locals.remove(fifoVar.getName());
			}
		}

		@Override
		public void visit(Store store) {
			Variable var = store.getTarget();
			if (localFifoVars.contains(var)) {
				store.setTarget(variable);
				//updateIndex(var, store, store.getIndexes());
			}
		}
	}
	public class RemoveReadWrite extends AbstractActorVisitor {	
		private void removeInstruction(Instruction instruction){
			BlockNode node = instruction.getBlock();		
			List<Instruction> instructions = node.getInstructions();			
			itInstruction.remove();
			instructions.remove(instruction);
		}
		
		@Override
		public void visit(Peek peek) {
			Port source = peek.getPort();
			if(port.getName() == source.getName()){
				removeInstruction(peek);
				localFifoVars.add(peek.getTarget());
			}
		}
		
		@Override
		public void visit(Read read) {
			Port source = read.getPort();
			if(port.getName() == source.getName()){
				removeInstruction(read);
				localFifoVars.add(read.getTarget());
			}
		}
		@Override
		public void visit(Write write) {
			Port target = write.getPort();
			if(port.getName() == target.getName()){
				removeInstruction(write);
				localFifoVars.add(write.getTarget());
			}
		}
	}
	private List<Variable> localFifoVars;
	
	private Port port;
	
	private GlobalVariable variable;
	
	public InternalizeFifoAccess(Port port, GlobalVariable variable) {
		this.port = port;
		this.variable = variable;
		this.localFifoVars = new ArrayList<Variable>();		
	}

	@Override
	public void visit(Action action) {
		//Update action pattern
		action.getInputPattern().remove(port);
		action.getOutputPattern().remove(port);
					
		new RemoveReadWrite().visit(action);
		new LinksToStateVar().visit(action);
	}
}