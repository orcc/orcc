/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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

import java.util.List;
import java.util.Map;

import net.sf.orcc.graph.Vertex;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.util.Adaptable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->This class defines an instance. An instance has an id,
 * a class, parameters and attributes. The class of the instance points to an
 * actor or a network.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.df.Instance#getArguments <em>Arguments</em>}</li>
 *   <li>{@link net.sf.orcc.df.Instance#getEntity <em>Entity</em>}</li>
 *   <li>{@link net.sf.orcc.df.Instance#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.df.DfPackage#getInstance()
 * @model
 * @generated
 */
public interface Instance extends Vertex, Adaptable {

	/**
	 * Returns the actor referenced by this instance.
	 * 
	 * @return the actor referenced by this instance, or <code>null</code> if
	 *         this instance does not reference an actor
	 */
	Actor getActor();

	/**
	 * Returns the argument of this instance that has the given name, or
	 * <code>null</code> if no such argument exists.
	 * 
	 * @param name
	 *            name of an argument
	 * @return an argument, or <code>null</code>
	 */
	Argument getArgument(String name);

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.orcc.df.Argument}. <!-- begin-user-doc --><!-- end-user-doc
	 * -->
	 * 
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getInstance_Arguments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Argument> getArguments();

	/**
	 * Returns the value of the '<em><b>Entity</b></em>' reference. <!--
	 * begin-user-doc --><!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entity</em>' reference.
	 * @see #setEntity(EObject)
	 * @see net.sf.orcc.df.DfPackage#getInstance_Entity()
	 * @model
	 * @generated
	 */
	EObject getEntity();

	List<String> getHierarchicalId();

	String getHierarchicalName();

	Map<Port, Connection> getIncomingPortMap();

	/**
	 * Returns the classification class of this instance.
	 * 
	 * @return the classification class of this instance
	 */
	MoC getMoC();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --><!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.df.DfPackage#getInstance_Name()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the network referenced by this instance.
	 * 
	 * @return the network referenced by this instance, or <code>null</code> if
	 *         this instance does not reference a network
	 */
	Network getNetwork();

	Map<Port, List<Connection>> getOutgoingPortMap();

	String getPackage();

	String getSimpleName();

	/**
	 * Returns <code>true</code> if this instance references an actor.
	 * 
	 * @return <code>true</code> if this instance references an actor
	 */
	boolean isActor();

	/**
	 * Returns <code>true</code> if this instance references a network.
	 * 
	 * @return <code>true</code> if this instance references a network
	 */
	boolean isNetwork();

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Instance#getEntity
	 * <em>Entity</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Entity</em>' reference.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(EObject value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Instance#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

}
