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
package net.sf.orcc.backends.llvm.tta.architecture.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.impl.VertexImpl;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.llvm.tta.architecture.Component;
import net.sf.orcc.backends.llvm.tta.architecture.Link;
import net.sf.orcc.backends.llvm.tta.architecture.Port;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Component</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.ComponentImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.ComponentImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.impl.ComponentImpl#getOutputs <em>Outputs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentImpl extends VertexImpl implements Component {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> inputs;
	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> outputs;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl() {
		super();
	}

	@Override
	public void addInput(Port port) {
		getInputs().add(port);
	}

	@Override
	public void addOutput(Port port) {
		getOutputs().add(port);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.COMPONENT__NAME:
			return getName();
		case ArchitecturePackage.COMPONENT__INPUTS:
			return getInputs();
		case ArchitecturePackage.COMPONENT__OUTPUTS:
			return getOutputs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ArchitecturePackage.COMPONENT__INPUTS:
			return ((InternalEList<?>) getInputs()).basicRemove(otherEnd, msgs);
		case ArchitecturePackage.COMPONENT__OUTPUTS:
			return ((InternalEList<?>) getOutputs())
					.basicRemove(otherEnd, msgs);
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
		case ArchitecturePackage.COMPONENT__NAME:
			return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT
					.equals(getName());
		case ArchitecturePackage.COMPONENT__INPUTS:
			return inputs != null && !inputs.isEmpty();
		case ArchitecturePackage.COMPONENT__OUTPUTS:
			return outputs != null && !outputs.isEmpty();
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
		case ArchitecturePackage.COMPONENT__NAME:
			setName((String) newValue);
			return;
		case ArchitecturePackage.COMPONENT__INPUTS:
			getInputs().clear();
			getInputs().addAll((Collection<? extends Port>) newValue);
			return;
		case ArchitecturePackage.COMPONENT__OUTPUTS:
			getOutputs().clear();
			getOutputs().addAll((Collection<? extends Port>) newValue);
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
		return ArchitecturePackage.Literals.COMPONENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.COMPONENT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ArchitecturePackage.COMPONENT__INPUTS:
			getInputs().clear();
			return;
		case ArchitecturePackage.COMPONENT__OUTPUTS:
			getOutputs().clear();
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Map<Port, Link> getIncomingPortMap() {
		Map<Port, Link> map = new HashMap<Port, Link>();
		for (Edge edge : getIncoming()) {
			if (edge instanceof Link) {
				Link link = (Link) edge;
				map.put(link.getTargetPort(), link);
			}
		}
		return map;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getInputs() {
		if (inputs == null) {
			inputs = new EObjectContainmentEList<Port>(Port.class, this,
					ArchitecturePackage.COMPONENT__INPUTS);
		}
		return inputs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public String getName() {
		return getLabel();
	}

	@Override
	public Map<Port, Link> getOutgoingPortMap() {
		Map<Port, Link> map = new HashMap<Port, Link>();
		for (Edge edge : getOutgoing()) {
			if (edge instanceof Link) {
				Link link = (Link) edge;
				map.put(link.getSourcePort(), link);
			}
		}
		return map;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectContainmentEList<Port>(Port.class, this,
					ArchitecturePackage.COMPONENT__OUTPUTS);
		}
		return outputs;
	}

	@Override
	public boolean isProcessor() {
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public void setName(String newName) {
		setLabel(newName);
	}

} // ComponentImpl
