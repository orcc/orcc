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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta.transform;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * 
 * This class defines an action concatener which create a new action from the
 * concatenation of the two given actions.
 * 
 * @author Herve Yviquel
 * 
 */
public class ActionConcatener {

	private Copier copier;

	/**
	 * Returns a new action resulting of the concatenation of the two actions
	 * given in parameter. The patterns of the new action correspond to the
	 * merge of the input one. The NumTokens are eventually increased if both
	 * actions access to the same ports. The 'body' and 'scheduler' procedures
	 * correspond to the concatenation of the input ones with an update of the
	 * names and the use/def of initial variables.
	 * 
	 * @param name
	 *            the name of the new action
	 * @param a1
	 *            the first given action
	 * @param a2
	 *            the second given action
	 * @return the new action resulting of the concatenation of a1 and a2.
	 */
	public Action concat(String name, Action a1, Action a2) {
		copier = new Copier();
		
		Pattern input = concat(a1.getInputPattern(), a2.getInputPattern());
		Pattern output = concat(a1.getOutputPattern(), a2.getOutputPattern());
		Pattern peeked = concat(a1.getPeekPattern(), a2.getPeekPattern());

		Procedure sched = concat(a1.getScheduler(), a2.getScheduler());
		Procedure body = concat(a1.getBody(), a2.getBody());

		return DfFactory.eINSTANCE.createAction(name, input, output, peeked,
				sched, body);
	}

	private Pattern concat(Pattern p1, Pattern p2) {
		Pattern pattern = IrUtil.copy(copier, p1);
		Pattern tmp = IrUtil.copy(copier, p2);
		for (Port port : tmp.getPorts()) {
			if (pattern.contains(port)) {
				update(pattern.getVariable(port), tmp.getVariable(port));
				pattern.setNumTokens(port,
						pattern.getNumTokens(port) + tmp.getNumTokens(port));
			} else {
				pattern.setVariable(port, tmp.getVariable(port));
				pattern.setNumTokens(port, tmp.getNumTokens(port));
			}
		}
		return pattern;
	}

	private Procedure concat(Procedure p1, Procedure p2) {
		Procedure proc = IrUtil.copy(copier, p1);
		Procedure tmp = IrUtil.copy(copier, p2);
		proc.getLocals().addAll(tmp.getLocals());
		proc.getBlocks().addAll(tmp.getBlocks());
		return proc;
	}

	private void update(Var newVar, Var oldVar) {
		TreeIterator<EObject> it = EcoreUtil.getAllContents(oldVar, true);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof Def) {
				Def def = (Def) object;
				def.setVariable(newVar);
			} else if (object instanceof Use) {
				Use use = (Use) object;
				use.setVariable(newVar);
			}
		}
	}

}
