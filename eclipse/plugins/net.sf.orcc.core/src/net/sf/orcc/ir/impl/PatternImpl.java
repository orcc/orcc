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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Pattern</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getNumTokensMap <em>Num Tokens Map</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getPortToVarMap <em>Port To Var Map</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.PatternImpl#getVarToPortMap <em>Var To Port Map</em>}</li>
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
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;
	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> variables;

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
	 * The cached value of the '{@link #getPortToVarMap() <em>Port To Var Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortToVarMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Port, Var> portToVarMap;
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

	/**
	 * Checks if the given variable is present in the {@link #variables} list,
	 * and adds it if necessary.
	 * 
	 * @param port
	 *            a port
	 */
	private void checkVarPresence(Var var) {
		if (!getVariables().contains(var)) {
			getVariables().add(var);
		}
	}

	@Override
	public void clear() {
		getPorts().clear();
		getNumTokensMap().clear();
		getVarToPortMap().clear();
		getPortToVarMap().clear();
	}

	@Override
	public boolean contains(Port port) {
		return getPorts().contains(port);
	}

	@Override
	public boolean contains(Var var) {
		return getVariables().contains(var);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.PATTERN__PORTS:
				return getPorts();
			case IrPackage.PATTERN__VARIABLES:
				return getVariables();
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				if (coreType) return getNumTokensMap();
				else return getNumTokensMap().map();
			case IrPackage.PATTERN__PORT_TO_VAR_MAP:
				if (coreType) return getPortToVarMap();
				else return getPortToVarMap().map();
			case IrPackage.PATTERN__VAR_TO_PORT_MAP:
				if (coreType) return getVarToPortMap();
				else return getVarToPortMap().map();
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
			case IrPackage.PATTERN__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				return ((InternalEList<?>)getNumTokensMap()).basicRemove(otherEnd, msgs);
			case IrPackage.PATTERN__PORT_TO_VAR_MAP:
				return ((InternalEList<?>)getPortToVarMap()).basicRemove(otherEnd, msgs);
			case IrPackage.PATTERN__VAR_TO_PORT_MAP:
				return ((InternalEList<?>)getVarToPortMap()).basicRemove(otherEnd, msgs);
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
			case IrPackage.PATTERN__PORTS:
				return ports != null && !ports.isEmpty();
			case IrPackage.PATTERN__VARIABLES:
				return variables != null && !variables.isEmpty();
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				return numTokensMap != null && !numTokensMap.isEmpty();
			case IrPackage.PATTERN__PORT_TO_VAR_MAP:
				return portToVarMap != null && !portToVarMap.isEmpty();
			case IrPackage.PATTERN__VAR_TO_PORT_MAP:
				return varToPortMap != null && !varToPortMap.isEmpty();
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
			case IrPackage.PATTERN__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case IrPackage.PATTERN__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Var>)newValue);
				return;
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				((EStructuralFeature.Setting)getNumTokensMap()).set(newValue);
				return;
			case IrPackage.PATTERN__PORT_TO_VAR_MAP:
				((EStructuralFeature.Setting)getPortToVarMap()).set(newValue);
				return;
			case IrPackage.PATTERN__VAR_TO_PORT_MAP:
				((EStructuralFeature.Setting)getVarToPortMap()).set(newValue);
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.PATTERN__PORTS:
				getPorts().clear();
				return;
			case IrPackage.PATTERN__VARIABLES:
				getVariables().clear();
				return;
			case IrPackage.PATTERN__NUM_TOKENS_MAP:
				getNumTokensMap().clear();
				return;
			case IrPackage.PATTERN__PORT_TO_VAR_MAP:
				getPortToVarMap().clear();
				return;
			case IrPackage.PATTERN__VAR_TO_PORT_MAP:
				getVarToPortMap().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Integer getNumTokens(Port port) {
		return getNumTokensMap().get(port);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		return getPortToVarMap().get(port);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Var>(Var.class, this, IrPackage.PATTERN__VARIABLES);
		}
		return variables;
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
	public EMap<Port, Var> getPortToVarMap() {
		if (portToVarMap == null) {
			portToVarMap = new EcoreEMap<Port,Var>(IrPackage.Literals.PORT_TO_VAR_MAP_ENTRY, PortToVarMapEntryImpl.class, this, IrPackage.PATTERN__PORT_TO_VAR_MAP);
		}
		return portToVarMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Var, Port> getVarToPortMap() {
		if (varToPortMap == null) {
			varToPortMap = new EcoreEMap<Var,Port>(IrPackage.Literals.VAR_TO_PORT_MAP_ENTRY, VarToPortMapEntryImpl.class, this, IrPackage.PATTERN__VAR_TO_PORT_MAP);
		}
		return varToPortMap;
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
		Var var = getPortToVarMap().removeKey(port);

		// Remove variable entry from portOfVar
		getVarToPortMap().remove(var);
	}

	@Override
	public void setNumTokens(Port port, int numTokens) {
		checkPortPresence(port);
		getNumTokensMap().put(port, numTokens);
	}

	@Override
	public void setVariable(Port port, Var var) {
		checkPortPresence(port);
		checkVarPresence(var);
		getPortToVarMap().put(port, var);
		getVarToPortMap().put(var, port);
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
