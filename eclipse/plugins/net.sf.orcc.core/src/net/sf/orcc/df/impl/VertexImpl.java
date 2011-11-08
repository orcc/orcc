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
package net.sf.orcc.df.impl;

import java.util.Collection;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Vertex;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * This class defines a vertex in an XDF network. A vertex is either an input
 * port, an output port, or an instance.
 * 
 * @author Matthieu Wipliez
 * @generated
 */
public abstract class VertexImpl extends EObjectImpl implements Vertex {

	/**
	 * The cached value of the '{@link #getPredecessors() <em>Predecessors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredecessors()
	 * @generated
	 * @ordered
	 */
	protected EList<Vertex> predecessors;
	/**
	 * The cached value of the '{@link #getSuccessors() <em>Successors</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuccessors()
	 * @generated
	 * @ordered
	 */
	protected EList<Vertex> successors;
	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> incomingEdges;
	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> outgoingEdges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VertexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DfPackage.VERTEX__PREDECESSORS:
				return getPredecessors();
			case DfPackage.VERTEX__SUCCESSORS:
				return getSuccessors();
			case DfPackage.VERTEX__INCOMING_EDGES:
				return getIncoming();
			case DfPackage.VERTEX__OUTGOING_EDGES:
				return getOutgoing();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DfPackage.VERTEX__PREDECESSORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPredecessors()).basicAdd(otherEnd, msgs);
			case DfPackage.VERTEX__SUCCESSORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSuccessors()).basicAdd(otherEnd, msgs);
			case DfPackage.VERTEX__INCOMING_EDGES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
			case DfPackage.VERTEX__OUTGOING_EDGES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DfPackage.VERTEX__PREDECESSORS:
				return ((InternalEList<?>)getPredecessors()).basicRemove(otherEnd, msgs);
			case DfPackage.VERTEX__SUCCESSORS:
				return ((InternalEList<?>)getSuccessors()).basicRemove(otherEnd, msgs);
			case DfPackage.VERTEX__INCOMING_EDGES:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case DfPackage.VERTEX__OUTGOING_EDGES:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DfPackage.VERTEX__PREDECESSORS:
				return predecessors != null && !predecessors.isEmpty();
			case DfPackage.VERTEX__SUCCESSORS:
				return successors != null && !successors.isEmpty();
			case DfPackage.VERTEX__INCOMING_EDGES:
				return incomingEdges != null && !incomingEdges.isEmpty();
			case DfPackage.VERTEX__OUTGOING_EDGES:
				return outgoingEdges != null && !outgoingEdges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DfPackage.VERTEX__PREDECESSORS:
				getPredecessors().clear();
				getPredecessors().addAll((Collection<? extends Vertex>)newValue);
				return;
			case DfPackage.VERTEX__SUCCESSORS:
				getSuccessors().clear();
				getSuccessors().addAll((Collection<? extends Vertex>)newValue);
				return;
			case DfPackage.VERTEX__INCOMING_EDGES:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends Connection>)newValue);
				return;
			case DfPackage.VERTEX__OUTGOING_EDGES:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends Connection>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.VERTEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DfPackage.VERTEX__PREDECESSORS:
				getPredecessors().clear();
				return;
			case DfPackage.VERTEX__SUCCESSORS:
				getSuccessors().clear();
				return;
			case DfPackage.VERTEX__INCOMING_EDGES:
				getIncoming().clear();
				return;
			case DfPackage.VERTEX__OUTGOING_EDGES:
				getOutgoing().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getIncoming() {
		if (incomingEdges == null) {
			incomingEdges = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, DfPackage.VERTEX__INCOMING_EDGES, DfPackage.CONNECTION__TARGET);
		}
		return incomingEdges;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getOutgoing() {
		if (outgoingEdges == null) {
			outgoingEdges = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, DfPackage.VERTEX__OUTGOING_EDGES, DfPackage.CONNECTION__SOURCE);
		}
		return outgoingEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Vertex> getPredecessors() {
		if (predecessors == null) {
			predecessors = new EObjectWithInverseResolvingEList.ManyInverse<Vertex>(Vertex.class, this, DfPackage.VERTEX__PREDECESSORS, DfPackage.VERTEX__SUCCESSORS);
		}
		return predecessors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Vertex> getSuccessors() {
		if (successors == null) {
			successors = new EObjectWithInverseResolvingEList.ManyInverse<Vertex>(Vertex.class, this, DfPackage.VERTEX__SUCCESSORS, DfPackage.VERTEX__PREDECESSORS);
		}
		return successors;
	}

	@Override
	public boolean isInstance() {
		return (this instanceof Instance);
	}

	@Override
	public boolean isPort() {
		return (this instanceof Port);
	}

}
