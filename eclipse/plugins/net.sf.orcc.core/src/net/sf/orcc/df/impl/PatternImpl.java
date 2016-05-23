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

import java.util.Collection;

import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.PatternImpl#getExprTokensMap <em>Expr Tokens Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.PatternImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.PatternImpl#getPortToVarMap <em>Port To Var Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.PatternImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.PatternImpl#getVarToPortMap <em>Var To Port Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PatternImpl extends EObjectImpl implements Pattern {
	/**
	 * The cached value of the '{@link #getExprTokensMap() <em>Expr Tokens Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExprTokensMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Port, Expression> exprTokensMap;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * The cached value of the '{@link #getPortToVarMap() <em>Port To Var Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortToVarMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Port, Var> portToVarMap;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> variables;

	/**
	 * The cached value of the '{@link #getVarToPortMap() <em>Var To Port Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarToPortMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Var, Port> varToPortMap;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Port, Expression> getExprTokensMap() {
		if (exprTokensMap == null) {
			exprTokensMap = new EcoreEMap<Port, Expression>(DfPackage.Literals.PORT_TO_EXPRESSION_MAP_ENTRY,
					PortToExpressionMapEntryImpl.class, this, DfPackage.PATTERN__EXPR_TOKENS_MAP);
		}
		return exprTokensMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectResolvingEList<Port>(Port.class, this, DfPackage.PATTERN__PORTS);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Port, Var> getPortToVarMap() {
		if (portToVarMap == null) {
			portToVarMap = new EcoreEMap<Port, Var>(DfPackage.Literals.PORT_TO_VAR_MAP_ENTRY,
					PortToVarMapEntryImpl.class, this, DfPackage.PATTERN__PORT_TO_VAR_MAP);
		}
		return portToVarMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Var>(Var.class, this, DfPackage.PATTERN__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Var, Port> getVarToPortMap() {
		if (varToPortMap == null) {
			varToPortMap = new EcoreEMap<Var, Port>(DfPackage.Literals.VAR_TO_PORT_MAP_ENTRY,
					VarToPortMapEntryImpl.class, this, DfPackage.PATTERN__VAR_TO_PORT_MAP);
		}
		return varToPortMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DfPackage.PATTERN__EXPR_TOKENS_MAP:
			return ((InternalEList<?>) getExprTokensMap()).basicRemove(otherEnd, msgs);
		case DfPackage.PATTERN__PORT_TO_VAR_MAP:
			return ((InternalEList<?>) getPortToVarMap()).basicRemove(otherEnd, msgs);
		case DfPackage.PATTERN__VARIABLES:
			return ((InternalEList<?>) getVariables()).basicRemove(otherEnd, msgs);
		case DfPackage.PATTERN__VAR_TO_PORT_MAP:
			return ((InternalEList<?>) getVarToPortMap()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.PATTERN__EXPR_TOKENS_MAP:
			if (coreType)
				return getExprTokensMap();
			else
				return getExprTokensMap().map();
		case DfPackage.PATTERN__PORTS:
			return getPorts();
		case DfPackage.PATTERN__PORT_TO_VAR_MAP:
			if (coreType)
				return getPortToVarMap();
			else
				return getPortToVarMap().map();
		case DfPackage.PATTERN__VARIABLES:
			return getVariables();
		case DfPackage.PATTERN__VAR_TO_PORT_MAP:
			if (coreType)
				return getVarToPortMap();
			else
				return getVarToPortMap().map();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DfPackage.PATTERN__EXPR_TOKENS_MAP:
			((EStructuralFeature.Setting) getExprTokensMap()).set(newValue);
			return;
		case DfPackage.PATTERN__PORTS:
			getPorts().clear();
			getPorts().addAll((Collection<? extends Port>) newValue);
			return;
		case DfPackage.PATTERN__PORT_TO_VAR_MAP:
			((EStructuralFeature.Setting) getPortToVarMap()).set(newValue);
			return;
		case DfPackage.PATTERN__VARIABLES:
			getVariables().clear();
			getVariables().addAll((Collection<? extends Var>) newValue);
			return;
		case DfPackage.PATTERN__VAR_TO_PORT_MAP:
			((EStructuralFeature.Setting) getVarToPortMap()).set(newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case DfPackage.PATTERN__EXPR_TOKENS_MAP:
			getExprTokensMap().clear();
			return;
		case DfPackage.PATTERN__PORTS:
			getPorts().clear();
			return;
		case DfPackage.PATTERN__PORT_TO_VAR_MAP:
			getPortToVarMap().clear();
			return;
		case DfPackage.PATTERN__VARIABLES:
			getVariables().clear();
			return;
		case DfPackage.PATTERN__VAR_TO_PORT_MAP:
			getVarToPortMap().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DfPackage.PATTERN__EXPR_TOKENS_MAP:
			return exprTokensMap != null && !exprTokensMap.isEmpty();
		case DfPackage.PATTERN__PORTS:
			return ports != null && !ports.isEmpty();
		case DfPackage.PATTERN__PORT_TO_VAR_MAP:
			return portToVarMap != null && !portToVarMap.isEmpty();
		case DfPackage.PATTERN__VARIABLES:
			return variables != null && !variables.isEmpty();
		case DfPackage.PATTERN__VAR_TO_PORT_MAP:
			return varToPortMap != null && !varToPortMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PatternImpl
