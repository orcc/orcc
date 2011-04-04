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
package net.sf.orcc.ir;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import java.util.List;

import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a procedure.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Procedure extends EObject {

	/**
	 * Returns the CFG of this procedure. The CFG must be set by calling
	 * {@link #setGraph(CFG)}.
	 * 
	 * @return the CFG of this procedure
	 */
	CFG getCFG();

	/**
	 * Returns the first block in the list of nodes of the given procedure. A
	 * new block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	NodeBlock getFirst();

	/**
	 * Returns the first block in the given list of nodes. A new block is
	 * created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes of the given procedure
	 * @return a block
	 */
	NodeBlock getFirst(List<Node> nodes);

	/**
	 * Returns the last block in the list of nodes of the given procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	NodeBlock getLast();

	/**
	 * Returns the last block in the given list of nodes. A new block is created
	 * if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes that are a subset of the given procedure's
	 *            nodes
	 * @return a block
	 */
	NodeBlock getLast(List<Node> nodes);

	/**
	 * Computes and returns the list of scalar variables loaded by this
	 * procedure.
	 * 
	 * @return the list of scalar variables loaded by this procedure
	 */
	List<VarGlobal> getLoadedVariables();

	/**
	 * Returns the local variables of this procedure as an ordered map.
	 * 
	 * @return the local variables of this procedure as an ordered map
	 */
	OrderedMap<String, VarLocal> getLocals();

	/**
	 * Returns the location of this procedure.
	 * 
	 * @return the location of this procedure
	 * @model
	 */
	Location getLocation();

	/**
	 * Returns the name of this procedure.
	 * 
	 * @return the name of this procedure
	 * @model
	 */
	String getName();

	/**
	 * Returns the list of nodes of this procedure.
	 * 
	 * @return the list of nodes of this procedure
	 * @model containment="true"
	 */
	EList<Node> getNodes();

	/**
	 * Returns the parameters of this procedure as an ordered map.
	 * 
	 * @return the parameters of this procedure as an ordered map
	 */
	OrderedMap<String, VarLocal> getParameters();

	/**
	 * Returns the result of this procedure.
	 * 
	 * @return
	 */
	Expression getResult();

	/**
	 * Returns the return type of this procedure.
	 * 
	 * @return the return type of this procedure
	 * @model containment="true"
	 */
	Type getReturnType();

	/**
	 * Computes and returns the list of scalar variables stored by this
	 * procedure.
	 * 
	 * @return the list of scalar variables stored by this procedure
	 */
	List<VarGlobal> getStoredVariables();

	/**
	 * Returns <code>true</code> if this procedure is native.
	 * 
	 * @return <code>true</code> if this procedure is native
	 * @model
	 */
	boolean isNative();

	/**
	 * Creates a new local variable that can be used to hold intermediate
	 * results. The variable is added to {@link #procedure}'s locals.
	 * 
	 * @param file
	 *            the file in which this procedure resides
	 * @param type
	 *            type of the variable
	 * @param name
	 *            hint for the variable name
	 * @return a new local variable
	 */
	VarLocal newTempLocalVariable(String file, Type type, String hint);

	/**
	 * Set the CFG of this procedure.
	 * 
	 * @param the
	 *            CFG of this procedure
	 */
	void setGraph(CFG graph);

	/**
	 * Sets the local variables of this procedure as an ordered map.
	 * 
	 * @param locals
	 *            the local variables of this procedure as an ordered map
	 */
	void setLocals(OrderedMap<String, VarLocal> locals);

	void setLocation(Location location);

	/**
	 * Sets the name of this procedure.
	 * 
	 * @param name
	 *            the new name
	 */
	void setName(String name);

	/**
	 * Sets this procedure as "native".
	 * 
	 * @param nativeFlag
	 *            value of native flag
	 */
	void setNative(boolean nativeFlag);

	/**
	 * Sets the parameters of this procedure as an ordered map.
	 * 
	 * @param parameters
	 *            the parameters of this procedure as an ordered map
	 */
	void setParameters(OrderedMap<String, VarLocal> parameters);

	void setResult(Expression result);

	/**
	 * Sets the return type of this procedure
	 * 
	 * @param returnType
	 *            a type
	 */
	void setReturnType(Type returnType);

}
