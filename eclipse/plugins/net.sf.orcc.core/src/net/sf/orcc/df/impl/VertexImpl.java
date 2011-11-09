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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> incoming;
	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> outgoing;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected VertexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DfPackage.VERTEX__INCOMING:
				return getIncoming();
			case DfPackage.VERTEX__OUTGOING:
				return getOutgoing();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DfPackage.VERTEX__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
			case DfPackage.VERTEX__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DfPackage.VERTEX__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case DfPackage.VERTEX__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DfPackage.VERTEX__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case DfPackage.VERTEX__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DfPackage.VERTEX__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends Connection>)newValue);
				return;
			case DfPackage.VERTEX__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends Connection>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.VERTEX;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DfPackage.VERTEX__INCOMING:
				getIncoming().clear();
				return;
			case DfPackage.VERTEX__OUTGOING:
				getOutgoing().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, DfPackage.VERTEX__INCOMING, DfPackage.CONNECTION__TARGET);
		}
		return incoming;
	}

	@Override
	public Map<Port, Connection> getIncomingPortMap() {
		Map<Port, Connection> map = new HashMap<Port, Connection>();
		for (Connection connection : getIncoming()) {
			map.put(connection.getTargetPort(), connection);
		}
		return map;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, DfPackage.VERTEX__OUTGOING, DfPackage.CONNECTION__SOURCE);
		}
		return outgoing;
	}

	@Override
	public Map<Port, List<Connection>> getOutgoingPortMap() {
		Map<Port, List<Connection>> map = new HashMap<Port, List<Connection>>();
		for (Connection connection : getOutgoing()) {
			Port source = connection.getSourcePort();
			List<Connection> conns = map.get(source);
			if (conns == null) {
				conns = new ArrayList<Connection>(1);
				map.put(source, conns);
			}
			conns.add(connection);
		}
		return map;
	}

	@Override
	public List<Vertex> getPredecessors() {
		List<Vertex> predecessors = new ArrayList<Vertex>();
		for (Connection connection : getIncoming()) {
			Vertex source = connection.getSource();
			if (!predecessors.contains(source)) {
				predecessors.add(source);
			}
		}
		return predecessors;
	}

	@Override
	public List<Vertex> getSuccessors() {
		List<Vertex> successors = new ArrayList<Vertex>();
		for (Connection connection : getOutgoing()) {
			Vertex target = connection.getTarget();
			if (!successors.contains(target)) {
				successors.add(target);
			}
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
