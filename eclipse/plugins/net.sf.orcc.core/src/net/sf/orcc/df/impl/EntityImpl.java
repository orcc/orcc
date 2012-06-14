/**
 * <copyright>
 * Copyright (c) 2009-2012, IETR/INSA of Rennes
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
 * </copyright>
 */
package net.sf.orcc.df.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfUtil;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getIncomingPortMap <em>Incoming Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getOutgoingPortMap <em>Outgoing Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getOutputs <em>Outputs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntityImpl extends EObjectImpl implements Entity {
	/**
	 * The cached value of the '{@link #getIncomingPortMap() <em>Incoming Port Map</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingPortMap()
	 * @generated
	 * @ordered
	 */
	protected Map<Port, Connection> incomingPortMap;

	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> inputs;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoingPortMap() <em>Outgoing Port Map</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingPortMap()
	 * @generated
	 * @ordered
	 */
	protected Map<Port, List<Connection>> outgoingPortMap;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> outputs;

	private Vertex vertex;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	protected EntityImpl(Vertex vertex, EList<Port> inputs, EList<Port> outputs) {
		super();

		this.vertex = vertex;
		this.inputs = inputs;
		this.outputs = outputs;
	}

	/**
	 * Computes the incoming port map of vertex.
	 */
	private void computeIncomingPortMap() {
		incomingPortMap = new HashMap<Port, Connection>();
		for (Edge edge : vertex.getIncoming()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				incomingPortMap.put(connection.getTargetPort(), connection);
			}
		}
	}

	/**
	 * Computes the outgoing port map of vertex.
	 */
	private void computeOutgoingPortMap() {
		outgoingPortMap = new HashMap<Port, List<Connection>>();
		for (Edge edge : vertex.getOutgoing()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				Port source = connection.getSourcePort();
				List<Connection> conns = outgoingPortMap.get(source);
				if (conns == null) {
					conns = new ArrayList<Connection>(1);
					outgoingPortMap.put(source, conns);
				}
				conns.add(connection);
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.ENTITY__INCOMING_PORT_MAP:
			return getIncomingPortMap();
		case DfPackage.ENTITY__INPUTS:
			return getInputs();
		case DfPackage.ENTITY__NAME:
			return getName();
		case DfPackage.ENTITY__OUTGOING_PORT_MAP:
			return getOutgoingPortMap();
		case DfPackage.ENTITY__OUTPUTS:
			return getOutputs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DfPackage.ENTITY__INCOMING_PORT_MAP:
			return incomingPortMap != null;
		case DfPackage.ENTITY__INPUTS:
			return inputs != null && !inputs.isEmpty();
		case DfPackage.ENTITY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case DfPackage.ENTITY__OUTGOING_PORT_MAP:
			return outgoingPortMap != null;
		case DfPackage.ENTITY__OUTPUTS:
			return outputs != null && !outputs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.ENTITY;
	}

	@Override
	public Map<Port, Connection> getIncomingPortMap() {
		if (incomingPortMap == null) {
			computeIncomingPortMap();
		}
		return incomingPortMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getInputs() {
		if (inputs == null) {
			inputs = new EObjectResolvingEList<Port>(Port.class, this,
					DfPackage.ENTITY__INPUTS);
		}
		return inputs;
	}

	@Override
	public String getName() {
		if (name == null) {
			name = vertex.getLabel();
		}
		return name;
	}

	@Override
	public Map<Port, List<Connection>> getOutgoingPortMap() {
		if (outgoingPortMap == null) {
			computeOutgoingPortMap();
		}
		return outgoingPortMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectResolvingEList<Port>(Port.class, this,
					DfPackage.ENTITY__OUTPUTS);
		}
		return outputs;
	}

	@Override
	public String getSimpleName() {
		return DfUtil.getSimpleName(getName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (incomingPortMap: ");
		result.append(incomingPortMap);
		result.append(", name: ");
		result.append(name);
		result.append(", outgoingPortMap: ");
		result.append(outgoingPortMap);
		result.append(')');
		return result.toString();
	}

} //EntityImpl
