/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import java.util.Collection;
import java.util.List;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MocPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>CSDF Mo C</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getInputPattern <em>Input Pattern</em>}</li>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getNumberOfPhases <em>Number Of Phases</em>}</li>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getOutputPattern <em>Output Pattern</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CSDFMoCImpl extends MoCImpl implements CSDFMoC {
	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> actions;

	/**
	 * The cached value of the '{@link #getInputPattern() <em>Input Pattern</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getInputPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern inputPattern;

	/**
	 * The default value of the '{@link #getNumberOfPhases() <em>Number Of Phases</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getNumberOfPhases()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_PHASES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfPhases() <em>Number Of Phases</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getNumberOfPhases()
	 * @generated
	 * @ordered
	 */
	protected int numberOfPhases = NUMBER_OF_PHASES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutputPattern() <em>Output Pattern</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getOutputPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern outputPattern;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CSDFMoCImpl() {
		super();
	}

	/**
	 * Adds the given action to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action
	 *            an action
	 */
	public void addAction(Action action) {
		getActions().add(action);
	}

	/**
	 * Adds the given actions to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action
	 *            an action
	 */
	public void addActions(List<Action> actions) {
		getActions().addAll(actions);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MocPackage.CSDF_MO_C__ACTIONS:
				return getActions();
			case MocPackage.CSDF_MO_C__INPUT_PATTERN:
				return getInputPattern();
			case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
				return getNumberOfPhases();
			case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
				return getOutputPattern();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MocPackage.CSDF_MO_C__ACTIONS:
				return actions != null && !actions.isEmpty();
			case MocPackage.CSDF_MO_C__INPUT_PATTERN:
				return inputPattern != null;
			case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
				return numberOfPhases != NUMBER_OF_PHASES_EDEFAULT;
			case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
				return outputPattern != null;
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
			case MocPackage.CSDF_MO_C__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends Action>)newValue);
				return;
			case MocPackage.CSDF_MO_C__INPUT_PATTERN:
				setInputPattern((Pattern)newValue);
				return;
			case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
				setNumberOfPhases((Integer)newValue);
				return;
			case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
				setOutputPattern((Pattern)newValue);
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
		return MocPackage.Literals.CSDF_MO_C;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MocPackage.CSDF_MO_C__ACTIONS:
				getActions().clear();
				return;
			case MocPackage.CSDF_MO_C__INPUT_PATTERN:
				setInputPattern((Pattern)null);
				return;
			case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
				setNumberOfPhases(NUMBER_OF_PHASES_EDEFAULT);
				return;
			case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
				setOutputPattern((Pattern)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Action> getActions() {
		if (actions == null) {
			actions = new EObjectResolvingEList<Action>(Action.class, this, MocPackage.CSDF_MO_C__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getInputPattern() {
		return inputPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInputPattern(Pattern newInputPattern, NotificationChain msgs) {
		Pattern oldInputPattern = inputPattern;
		inputPattern = newInputPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MocPackage.CSDF_MO_C__INPUT_PATTERN, oldInputPattern, newInputPattern);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfPhases() {
		return numberOfPhases;
	}

	/**
	 * Returns the number of tokens consumed by this port.
	 * 
	 * @param port
	 *            an input port
	 * @return the number of tokens consumed by this port.
	 */
	public int getNumTokensConsumed(Port port) {
		return inputPattern.getNumTokens(port);
	}

	/**
	 * Returns the number of tokens written to this port.
	 * 
	 * @param port
	 *            an output port
	 * @return the number of tokens written to this port.
	 */
	public int getNumTokensProduced(Port port) {
		return outputPattern.getNumTokens(port);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getOutputPattern() {
		return outputPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutputPattern(Pattern newOutputPattern, NotificationChain msgs) {
		Pattern oldOutputPattern = outputPattern;
		outputPattern = newOutputPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MocPackage.CSDF_MO_C__OUTPUT_PATTERN, oldOutputPattern, newOutputPattern);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	@Override
	public boolean isCSDF() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputPattern(Pattern newInputPattern) {
		if (newInputPattern != inputPattern) {
			NotificationChain msgs = null;
			if (inputPattern != null)
				msgs = ((InternalEObject)inputPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MocPackage.CSDF_MO_C__INPUT_PATTERN, null, msgs);
			if (newInputPattern != null)
				msgs = ((InternalEObject)newInputPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MocPackage.CSDF_MO_C__INPUT_PATTERN, null, msgs);
			msgs = basicSetInputPattern(newInputPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MocPackage.CSDF_MO_C__INPUT_PATTERN, newInputPattern, newInputPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfPhases(int newNumberOfPhases) {
		int oldNumberOfPhases = numberOfPhases;
		numberOfPhases = newNumberOfPhases;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MocPackage.CSDF_MO_C__NUMBER_OF_PHASES, oldNumberOfPhases, numberOfPhases));
	}

	@Override
	public void setNumTokensConsumed(Actor actor) {
		for (Port port : actor.getInputs()) {
			inputPattern.setNumTokens(port, port.getNumTokensConsumed());
		}
	}

	@Override
	public void setNumTokensProduced(Actor actor) {
		for (Port port : actor.getOutputs()) {
			outputPattern.setNumTokens(port, port.getNumTokensProduced());
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputPattern(Pattern newOutputPattern) {
		if (newOutputPattern != outputPattern) {
			NotificationChain msgs = null;
			if (outputPattern != null)
				msgs = ((InternalEObject)outputPattern).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MocPackage.CSDF_MO_C__OUTPUT_PATTERN, null, msgs);
			if (newOutputPattern != null)
				msgs = ((InternalEObject)newOutputPattern).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MocPackage.CSDF_MO_C__OUTPUT_PATTERN, null, msgs);
			msgs = basicSetOutputPattern(newOutputPattern, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MocPackage.CSDF_MO_C__OUTPUT_PATTERN, newOutputPattern, newOutputPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MocPackage.CSDF_MO_C__INPUT_PATTERN:
				return basicSetInputPattern(null, msgs);
			case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
				return basicSetOutputPattern(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CSDF => ");
		builder.append(getInputPattern());
		builder.append(", ");
		builder.append(getOutputPattern());
		return builder.toString();
	}

} // CSDFMoCImpl
