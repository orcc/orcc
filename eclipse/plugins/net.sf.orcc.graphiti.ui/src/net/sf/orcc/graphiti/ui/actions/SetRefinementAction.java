/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.actions;

import net.sf.orcc.graphiti.ui.commands.refinement.SetRefinementCommand;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This class provides a way to create a vertex refinement.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SetRefinementAction extends SelectionAction {

	private static final String ID = "net.sf.orcc.graphiti.ui.actions.SetRefinementAction";

	/**
	 * Returns this action identifier.
	 * 
	 * @return This action identifier.
	 */
	public static String getActionId() {
		return ID;
	}

	private SetRefinementCommand command;

	/**
	 * Creates a {@link SetRefinementCommand} action.
	 * 
	 * @param part
	 */
	public SetRefinementAction(IWorkbenchPart part) {
		super(part);
		command = new SetRefinementCommand();
	}

	public boolean calculateEnabled() {
		command.setSelection(getSelection());
		return command.canExecute();
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	protected void init() {
		setId(getId());
		setText("Set/Update Refinement");
		setToolTipText("Set/Update Refinement");
	}

	@Override
	public void run() {
		if (command.run()) {
			execute(command);
		}
	}
}
