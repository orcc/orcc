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
package net.sf.orcc.ir;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an entity. An entity has a package, a name, and may be
 * either an actor or a unit.
 * 
 * @author Matthieu Wipliez
 * @model abstract="true"
 */
public interface Entity extends EObject {

	/**
	 * Returns the file this actor is defined in.
	 * 
	 * @return the file this actor is defined in
	 */
	IFile getFile();

	/**
	 * Returns the name of the file this actor is defined in.
	 * 
	 * @return the name of the file this actor is defined in
	 * @model dataType="org.eclipse.emf.ecore.EString"
	 */
	String getFileName();

	/**
	 * Returns the line number on which this actor starts.
	 * 
	 * @return the line number on which this actor starts
	 * @model
	 */
	public int getLineNumber();

	/**
	 * Returns the name of this actor.
	 * 
	 * @return the name of this actor
	 * @model dataType="org.eclipse.emf.ecore.EString"
	 */
	String getName();

	/**
	 * Returns the package of this actor.
	 * 
	 * @return the package of this actor
	 */
	String getPackage();

	/**
	 * Returns the package of this actor as a list of strings.
	 * 
	 * @return the package of this actor as a list of strings
	 */
	List<String> getPackageAsList();

	/**
	 * Returns the simple name of this actor.
	 * 
	 * @return the simple name of this actor
	 */
	String getSimpleName();

	/**
	 * Returns an object with template-specific data.
	 * 
	 * @return an object with template-specific data
	 */
	Object getTemplateData();

	/**
	 * Returns <code>true</code> if this entity is an actor.
	 * 
	 * @return <code>true</code> if this entity is an actor
	 */
	boolean isActor();

	/**
	 * Returns <code>true</code> if this entity is a network.
	 * 
	 * @return <code>true</code> if this entity is a network
	 */
	boolean isNetwork();

	/**
	 * Returns <code>true</code> if this entity is a unit.
	 * 
	 * @return <code>true</code> if this entity is a unit
	 */
	boolean isUnit();

	/**
	 * Sets the name of the file in which this actor is defined.
	 * 
	 * @param fileName
	 *            name of the file in which this actor is defined
	 */
	void setFileName(String fileName);

	/**
	 * Sets the line number on which this actor starts.
	 * 
	 * @param newLineNumber
	 *            the line number on which this actor starts
	 */
	public void setLineNumber(int newLineNumber);

	/**
	 * Sets the name of this actor.
	 * 
	 * @param name
	 *            the new name of this actor
	 */
	void setName(String name);

	/**
	 * Sets the template data associated with this actor. Template data should
	 * hold data that is specific to a given template.
	 * 
	 * @param templateData
	 *            an object with template-specific data
	 */
	void setTemplateData(Object templateData);

}
