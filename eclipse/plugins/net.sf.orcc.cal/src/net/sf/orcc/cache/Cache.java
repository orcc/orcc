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
package net.sf.orcc.cache;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a cache associated to an entity.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Cache extends EObject {

	/**
	 * Returns all the expressions stored in this cache. This method should only
	 * be called internally to make types belong to this cache.
	 * 
	 * @return all the types stored in this cache
	 * @model containment="true"
	 */
	EList<Expression> getExpressions();

	/**
	 * Returns a String to Expression map.
	 * 
	 * @return a String to Expression map
	 * @model keyType="String" valueType="Expression"
	 */
	EMap<String, Expression> getExpressionsMap();

	/**
	 * Returns all the types stored in this cache. This method should only be
	 * called internally to make types belong to this cache.
	 * 
	 * @return all the types stored in this cache
	 * @model containment="true"
	 */
	EList<Type> getTypes();

	/**
	 * Returns a String to Type map.
	 * 
	 * @return a String to Type map
	 * @model keyType="String" valueType="Type"
	 */
	EMap<String, Type> getTypesMap();

}
