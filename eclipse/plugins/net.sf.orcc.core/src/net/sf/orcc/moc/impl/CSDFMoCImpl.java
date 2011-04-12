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
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MocPackage;

import org.eclipse.emf.common.notify.Notification;
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
	 * The cached value of the '{@link #getInputPattern() <em>Input Pattern</em>}' reference.
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
	 * The cached value of the '{@link #getOutputPattern() <em>Output Pattern</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getOutputPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern outputPattern;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	protected CSDFMoCImpl() {
		super();
		inputPattern = IrFactory.eINSTANCE.createPattern();
		outputPattern = IrFactory.eINSTANCE.createPattern();
	}

	/**
	 * Adds the given action to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action
	 *            an action
	 */
	public void addAction(Action action) {
		actions.add(action);
	}

	/**
	 * Adds the given actions to the list of actions that can be scheduled
	 * statically.
	 * 
	 * @param action
	 *            an action
	 */
	public void addActions(List<Action> actions) {
		this.actions.addAll(actions);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern basicGetInputPattern() {
		return inputPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern basicGetOutputPattern() {
		return outputPattern;
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
				if (resolve) return getInputPattern();
				return basicGetInputPattern();
			case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
				return getNumberOfPhases();
			case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
				if (resolve) return getOutputPattern();
				return basicGetOutputPattern();
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
		if (inputPattern != null && inputPattern.eIsProxy()) {
			InternalEObject oldInputPattern = (InternalEObject)inputPattern;
			inputPattern = (Pattern)eResolveProxy(oldInputPattern);
			if (inputPattern != oldInputPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MocPackage.CSDF_MO_C__INPUT_PATTERN, oldInputPattern, inputPattern));
			}
		}
		return inputPattern;
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
		Integer numTokens = inputPattern.getNumTokens(port);
		if (numTokens == null) {
			return 0;
		}
		return numTokens;
	}

	/**
	 * Returns the number of tokens written to this port.
	 * 
	 * @param port
	 *            an output port
	 * @return the number of tokens written to this port.
	 */
	public int getNumTokensProduced(Port port) {
		Integer numTokens = outputPattern.getNumTokens(port);
		if (numTokens == null) {
			return 0;
		}
		return numTokens;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getOutputPattern() {
		if (outputPattern != null && outputPattern.eIsProxy()) {
			InternalEObject oldOutputPattern = (InternalEObject)outputPattern;
			outputPattern = (Pattern)eResolveProxy(oldOutputPattern);
			if (outputPattern != oldOutputPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MocPackage.CSDF_MO_C__OUTPUT_PATTERN, oldOutputPattern, outputPattern));
			}
		}
		return outputPattern;
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
		Pattern oldInputPattern = inputPattern;
		inputPattern = newInputPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MocPackage.CSDF_MO_C__INPUT_PATTERN, oldInputPattern, inputPattern));
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
		Pattern oldOutputPattern = outputPattern;
		outputPattern = newOutputPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MocPackage.CSDF_MO_C__OUTPUT_PATTERN, oldOutputPattern, outputPattern));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CSDF input ports: ");
		builder.append(inputPattern);
		builder.append('\n');
		builder.append("CSDF output ports: ");
		builder.append(outputPattern);
		return builder.toString();
	}

} // CSDFMoCImpl
