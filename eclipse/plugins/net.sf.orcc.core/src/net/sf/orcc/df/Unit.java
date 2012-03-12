/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.df;

import net.sf.dftools.util.Nameable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

/**
 * This class defines a unit.
 * 
 * @author Matthieu Wipliez
 * @model extends="Entity"
 */
public interface Unit extends Nameable {

	/**
	 * Returns the constant with the given name.
	 * 
	 * @param name
	 *            name of a constant
	 * @return the constant with the given name
	 */
	Var getConstant(String name);

	/**
	 * Returns the constants contained in this unit.
	 * 
	 * @return the constants contained in this unit
	 * @model containment="true"
	 */
	EList<Var> getConstants();

	/**
	 * Returns the file this unit is defined in.
	 * 
	 * @return the file this unit is defined in
	 */
	IFile getFile();

	/**
	 * Returns the name of the file this unit is defined in.
	 * 
	 * @return the name of the file this unit is defined in
	 * @model
	 */
	String getFileName();

	/**
	 * Returns the line number on which this actor starts.
	 * 
	 * @return the line number on which this actor starts
	 * @model
	 */
	int getLineNumber();

	/**
	 * Returns a procedure of this unit whose name matches the given name.
	 * 
	 * @param name
	 *            the procedure name
	 * @return a procedure whose name matches the given name
	 */
	Procedure getProcedure(String name);

	/**
	 * Returns the functions/procedures contained in this unit.
	 * 
	 * @return the functions/procedures contained in this unit
	 * @model containment="true"
	 */
	EList<Procedure> getProcedures();

	/**
	 * Returns an object with template-specific data.
	 * 
	 * @return an object with template-specific data
	 */
	Object getTemplateData();

	/**
	 * Sets the name of the file in which this unit is defined.
	 * 
	 * @param fileName
	 *            name of the file in which this unit is defined
	 */
	void setFileName(String fileName);

	/**
	 * Sets the line number on which this unit starts.
	 * 
	 * @param newLineNumber
	 *            the line number on which this unit starts
	 */
	void setLineNumber(int newLineNumber);

	/**
	 * Sets the template data associated with this vertex. Template data should
	 * hold data that is specific to a given template.
	 * 
	 * @param templateData
	 *            an object with template-specific data
	 */
	void setTemplateData(Object templateData);

}
