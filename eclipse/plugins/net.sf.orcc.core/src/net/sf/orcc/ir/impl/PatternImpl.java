/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.Map.Entry;

import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Pattern</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getInverseVariableMap <em>Inverse Variable Map</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getNumTokensMap <em>Num Tokens Map</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getVariableMap <em>Variable Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PatternImpl extends EObjectImpl implements Pattern {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The cached value of the '{@link #getInverseVariableMap() <em>Inverse Variable Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInverseVariableMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Var, Port> inverseVariableMap;
	/**
	 * The cached value of the '{@link #getNumTokensMap() <em>Num Tokens Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumTokensMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Port, Integer> numTokensMap;
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
	 * The cached value of the '{@link #getVariableMap() <em>Variable Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariableMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Port, Var> variableMap;

	/**
	 * @generated
	 */
	protected PatternImpl() {
		super();
	}

	/**
	 * Checks if the given port is present in the {@link #ports} list, and adds
	 * it if necessary.
	 * 
	 * @param port
	 *            a port
	 */
	private void checkPortPresence(Port port) {
		if (!getPorts().contains(port)) {
			getPorts().add(port);
		}
	}

	@Override
	public void clear() {
		getPorts().clear();
		getNumTokensMap().clear();
		getVariableMap().clear();
		getInverseVariableMap().clear();
	}

	@Override
	public boolean contains(Port port) {
		return getPorts().contains(port);
	}

	@Override
	public boolean contains(Var var) {
		return getInverseVariableMap().containsKey(var);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.PATTERN__INVERSE_VARIABLE_MAP:
				if (coreType) return getInverseVariableMap();
				else return getInverseVariableMap().map();
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				if (coreType) return getNumTokensMap();
				else return getNumTokensMap().map();
			case IrPackage.PATTERN__PORTS:
				return getPorts();
			case IrPackage.PATTERN__VARIABLE_MAP:
				if (coreType) return getVariableMap();
				else return getVariableMap().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.PATTERN__INVERSE_VARIABLE_MAP:
				return ((InternalEList<?>)getInverseVariableMap()).basicRemove(otherEnd, msgs);
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				return ((InternalEList<?>)getNumTokensMap()).basicRemove(otherEnd, msgs);
			case IrPackage.PATTERN__VARIABLE_MAP:
				return ((InternalEList<?>)getVariableMap()).basicRemove(otherEnd, msgs);
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
			case IrPackage.PATTERN__INVERSE_VARIABLE_MAP:
				return inverseVariableMap != null && !inverseVariableMap.isEmpty();
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				return numTokensMap != null && !numTokensMap.isEmpty();
			case IrPackage.PATTERN__PORTS:
				return ports != null && !ports.isEmpty();
			case IrPackage.PATTERN__VARIABLE_MAP:
				return variableMap != null && !variableMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PatternImpl) {
			return ((PatternImpl) obj).getNumTokensMap().equals(numTokensMap);
		} else {
			return false;
		}
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
			case IrPackage.PATTERN__INVERSE_VARIABLE_MAP:
				((EStructuralFeature.Setting)getInverseVariableMap()).set(newValue);
				return;
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				((EStructuralFeature.Setting)getNumTokensMap()).set(newValue);
				return;
			case IrPackage.PATTERN__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case IrPackage.PATTERN__VARIABLE_MAP:
				((EStructuralFeature.Setting)getVariableMap()).set(newValue);
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
		return IrPackage.Literals.PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.PATTERN__INVERSE_VARIABLE_MAP:
				getInverseVariableMap().clear();
				return;
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				getNumTokensMap().clear();
				return;
			case IrPackage.PATTERN__PORTS:
				getPorts().clear();
				return;
			case IrPackage.PATTERN__VARIABLE_MAP:
				getVariableMap().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Var, Port> getInverseVariableMap() {
		if (inverseVariableMap == null) {
			inverseVariableMap = new EcoreEMap<Var,Port>(IrPackage.Literals.VAR_TO_PORT_MAP_ENTRY, VarToPortMapEntryImpl.class, this, IrPackage.PATTERN__INVERSE_VARIABLE_MAP);
		}
		return inverseVariableMap;
	}

	@Override
	public Integer getNumTokens(Port port) {
		return getNumTokensMap().get(port);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Port, Integer> getNumTokensMap() {
		if (numTokensMap == null) {
			numTokensMap = new EcoreEMap<Port,Integer>(IrPackage.Literals.PORT_TO_EINTEGER_OBJECT_MAP_ENTRY, PortToEIntegerObjectMapEntryImpl.class, this, IrPackage.PATTERN__NUM_TOKENS_MAP);
		}
		return numTokensMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectResolvingEList<Port>(Port.class, this, IrPackage.PATTERN__PORTS);
		}
		return ports;
	}

	@Override
	public Var getVariable(Port port) {
		return getVariableMap().get(port);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Port, Var> getVariableMap() {
		if (variableMap == null) {
			variableMap = new EcoreEMap<Port,Var>(IrPackage.Literals.PORT_TO_VAR_MAP_ENTRY, PortToVarMapEntryImpl.class, this, IrPackage.PATTERN__VARIABLE_MAP);
		}
		return variableMap;
	}

	@Override
	public boolean isEmpty() {
		return getPorts().isEmpty();
	}

	@Override
	public boolean isSupersetOf(Pattern other) {
		if (this.getNumTokensMap().keySet()
				.containsAll(other.getNumTokensMap().keySet())) {
			// OK we read from at least the same ports as the other pattern

			// let's check the consumption
			for (Entry<Port, Integer> entry : other.getNumTokensMap()
					.entrySet()) {
				// if this pattern consumes less than the other pattern then
				// this pattern is not a superset
				if (this.getNumTokens(entry.getKey()) < entry.getValue()) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void remove(Port port) {
		getPorts().remove(port);
		getNumTokensMap().remove(port);
		Var var = getVariableMap().removeKey(port);

		// Remove peek and variable entry from inverseVariableMap
		getInverseVariableMap().remove(var);
	}

	@Override
	public void setNumTokens(Port port, int numTokens) {
		checkPortPresence(port);
		getNumTokensMap().put(port, numTokens);
	}

	@Override
	public void setVariable(Port port, Var var) {
		checkPortPresence(port);
		getVariableMap().put(port, var);
		getInverseVariableMap().put(var, port);
	}

	@Override
	public String toString() {
		return getNumTokensMap().toString();
	}

	@Override
	public void updatePattern(Pattern pattern) {
		for (Entry<Port, Integer> entry : pattern.getNumTokensMap().entrySet()) {
			// Get number of tokens comsuption/production
			Port port = entry.getKey();
			Integer numTokens = entry.getValue();

			if (contains(port)) {
				if (numTokens < getNumTokens(port)) {
					// Don't update pattern
					continue;
				}
			}

			// Update the pattern
			setNumTokens(port, numTokens);
		}
	}

} // PatternImpl
