/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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

package net.sf.orcc.backends.xlim;

import net.sf.orcc.backends.xlim.templates.XlimAttributeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimModuleTemplate;
import net.sf.orcc.backends.xlim.templates.XlimNodeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimOperationTemplate;
import net.sf.orcc.backends.xlim.templates.XlimTypeTemplate;
import net.sf.orcc.backends.xlim.templates.XlimValueTemplate;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.ReadEnd;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.instructions.WriteEnd;

import org.w3c.dom.Element;

/**
 * XlimNodeVisitor prints Phi instructions nodes in XLIM
 * 
 * @author Samuel Keller EPFL
 */
public class XlimPhiInstructionVisitor implements InstructionVisitor,
		XlimOperationTemplate, XlimAttributeTemplate, XlimModuleTemplate,
		XlimValueTemplate, XlimTypeTemplate {

	/**
	 * Current action name
	 */
	private String actionName;

	/**
	 * Names templates
	 */
	private XlimNames names;

	/**
	 * Root element where to add everything
	 */
	private Element root;

	/**
	 * XlimInstructionVisitor Constructor
	 * 
	 * @param names
	 *            Names templates
	 * @param root
	 *            Root element where to add everything
	 * @param actionName
	 *            Current action name
	 * @param inputs
	 *            Vector of inputs names
	 * @param writeMap
	 *            Temporary mapping for outputs
	 */
	public XlimPhiInstructionVisitor(XlimNames names, Element root,
			String actionName) {
		this.names = names;
		this.root = root;
		this.actionName = actionName;
	}

	/**
	 * Add assignment node
	 * 
	 * @param node
	 *            Assignment node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Assign node, Object... args) {
	}

	/**
	 * Add call node
	 * 
	 * @param node
	 *            Call node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Call node, Object... args) {
	}

	/**
	 * Add has tokens node
	 * 
	 * @param node
	 *            Has tokens node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(HasTokens node, Object... args) {
	}

	/**
	 * Add load node
	 * 
	 * @param node
	 *            Load node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Load node, Object... args) {
	}

	/**
	 * Add peek node
	 * 
	 * @param node
	 *            Peek node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Peek node, Object... args) {
	}

	@Override
	public void visit(PhiAssignment phi, Object... args) {
		Element phiE = XlimNodeTemplate.newPHI(root);
		System.out.println("CHECK PHIS");

		XlimNodeTemplate.newInPHIPort(phiE, names.getVarName(phi.getVars().get(
				0), actionName), THEN);
		XlimNodeTemplate.newInPHIPort(phiE, names.getVarName(phi.getVars().get(
				1), actionName), ELSE);

		Element portO = XlimNodeTemplate.newOutPort(phiE, names.getVarName(phi
				.getTarget(), actionName), phi.getTarget().getType());
		phi.getTarget().getType().accept(new XlimTypeSizeVisitor(portO));
	}

	/**
	 * Add read node
	 * 
	 * @param node
	 *            Read node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Read node, Object... args) {
	}

	/**
	 * Add read end node
	 * 
	 * @param node
	 *            Read end node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(ReadEnd node, Object... args) {
	}

	/**
	 * Add return node
	 * 
	 * @param node
	 *            Return node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Return node, Object... args) {
	}

	@Override
	public void visit(SpecificInstruction node, Object... args) {
	}

	/**
	 * Add store node
	 * 
	 * @param node
	 *            Store node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Store node, Object... args) {
	}

	/**
	 * Add write node
	 * 
	 * @param node
	 *            Write node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(Write node, Object... args) {
	}

	/**
	 * Add write end node
	 * 
	 * @param node
	 *            Write end node to add
	 * @param args
	 *            Arguments sent (not used)
	 */
	public void visit(WriteEnd node, Object... args) {
	}

}
