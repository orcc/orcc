/*
 * Copyright (c) 2008-2011, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.commands;

import net.sf.orcc.graphiti.model.AbstractObject;

import org.eclipse.gef.commands.Command;

/**
 * This class provides a command that changes the value of the currently
 * selected parameter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ParameterChangeValueCommand extends Command {

	final private String label;

	/**
	 * Set by {@link #setValue(String, Object)}.
	 */
	private String name;

	/**
	 * The new value.
	 */
	private Object newValue;

	/**
	 * The old value.
	 */
	private Object oldValue;

	/**
	 * The property bean we're modifying.
	 */
	private AbstractObject source;

	/**
	 * Creates a new add parameter command.
	 * 
	 * @param newValue
	 *            The value.
	 */
	public ParameterChangeValueCommand(AbstractObject source, String label) {
		this.source = source;
		this.label = label;
	}

	@Override
	public void execute() {
		oldValue = source.setValue(name, newValue);
	}

	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the value of the parameter whose name is given to the given
	 * value.
	 * 
	 * @param name
	 *            The parameter name.
	 * @param value
	 *            Its new value.
	 */
	public void setValue(String name, Object value) {
		this.name = name;
		this.newValue = value;
	}

	@Override
	public void undo() {
		source.setValue(name, oldValue);
	}

}