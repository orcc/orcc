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
package net.sf.orcc.backends.llvm.transforms;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.backends.llvm.nodes.GetElementPtrNode;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.AbstractExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AbstractNodeVisitor;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.type.ListType;

/**
 * Change every load on array into getElementPtrNode.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ArrayListTransformation extends AbstractNodeVisitor {

	public ArrayListTransformation(Actor actor) {

		for (Procedure proc : actor.getProcs()) {
			visitProc(proc);
		}

		for (Action action : actor.getActions()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}
	}

	@SuppressWarnings("unchecked")
	public void visit(LoadNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		
		List<AbstractExpr> indexes = node.getIndexes();
		
		if (indexes.size() > 0){
			AbstractExpr expr = indexes.get(0);
			try {
				
				//Check if the array is an only value
				if ((Integer.parseInt(expr.toString()) == 0)&& (indexes.size()==1)) {
					return;
				}
			} catch (NumberFormatException e) {

			}
			
			it.previous();
			
			
			//Adding the getElementPtrNode
			VarUse sourceUse = node.getSource();
			VarDef sourceVar = sourceUse.getVarDef();
			ListType sourceType = (ListType)sourceVar.getType();
			VarDef targetVar = node.getTarget();
			
			VarDef varDef = new VarDef(false, false, targetVar.getIndex()+1, new Location(),
					targetVar.getName(), null, null, targetVar.getSuffix(), new PointType(sourceType.getType()));
			VarUse varUse = new VarUse(varDef, node);
							
			//Create and insert the new node
			GetElementPtrNode elementPtrNode = new GetElementPtrNode(0, new Location(), varDef, node.getSource(), indexes);
			
			it.add(elementPtrNode);
			it.next();
			
			
			//Insert the new VarDef in the load node
			node.setSource(varUse);
		}
		
		
	}

	
	private void visitNodes(List<AbstractNode> nodes) {
		ListIterator<AbstractNode> it = nodes.listIterator();

		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	private void visitProc(Procedure proc) {
		List<AbstractNode> nodes = proc.getNodes();

		visitNodes(nodes);
	}
}
