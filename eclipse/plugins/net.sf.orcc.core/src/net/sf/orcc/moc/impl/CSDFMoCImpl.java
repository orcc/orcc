/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import java.util.Collection;

import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MocPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>CSDF Mo C</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getInputPattern <em>Input Pattern</em>}</li>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getNumberOfPhases <em>Number Of Phases</em>}</li>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getOutputPattern <em>Output Pattern</em>}</li>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getInvocations <em>Invocations</em>}</li>
 *   <li>{@link net.sf.orcc.moc.impl.CSDFMoCImpl#getDelayPattern <em>Delay Pattern</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CSDFMoCImpl extends MoCImpl implements CSDFMoC {
	/**
	 * The cached value of the '{@link #getInputPattern() <em>Input Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern outputPattern;

	/**
	 * The cached value of the '{@link #getInvocations() <em>Invocations</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInvocations()
	 * @generated
	 * @ordered
	 */
	protected EList<Invocation> invocations;

	/**
	 * The cached value of the '{@link #getDelayPattern() <em>Delay Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelayPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern delayPattern;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected CSDFMoCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDelayPattern(Pattern newDelayPattern,
			NotificationChain msgs) {
		Pattern oldDelayPattern = delayPattern;
		delayPattern = newDelayPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, MocPackage.CSDF_MO_C__DELAY_PATTERN,
					oldDelayPattern, newDelayPattern);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInputPattern(Pattern newInputPattern,
			NotificationChain msgs) {
		Pattern oldInputPattern = inputPattern;
		inputPattern = newInputPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, MocPackage.CSDF_MO_C__INPUT_PATTERN,
					oldInputPattern, newInputPattern);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutputPattern(Pattern newOutputPattern,
			NotificationChain msgs) {
		Pattern oldOutputPattern = outputPattern;
		outputPattern = newOutputPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, MocPackage.CSDF_MO_C__OUTPUT_PATTERN,
					oldOutputPattern, newOutputPattern);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case MocPackage.CSDF_MO_C__INPUT_PATTERN:
			return getInputPattern();
		case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
			return getNumberOfPhases();
		case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
			return getOutputPattern();
		case MocPackage.CSDF_MO_C__INVOCATIONS:
			return getInvocations();
		case MocPackage.CSDF_MO_C__DELAY_PATTERN:
			return getDelayPattern();
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
		case MocPackage.CSDF_MO_C__INPUT_PATTERN:
			return basicSetInputPattern(null, msgs);
		case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
			return basicSetOutputPattern(null, msgs);
		case MocPackage.CSDF_MO_C__INVOCATIONS:
			return ((InternalEList<?>) getInvocations()).basicRemove(otherEnd,
					msgs);
		case MocPackage.CSDF_MO_C__DELAY_PATTERN:
			return basicSetDelayPattern(null, msgs);
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
		case MocPackage.CSDF_MO_C__INPUT_PATTERN:
			return inputPattern != null;
		case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
			return numberOfPhases != NUMBER_OF_PHASES_EDEFAULT;
		case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
			return outputPattern != null;
		case MocPackage.CSDF_MO_C__INVOCATIONS:
			return invocations != null && !invocations.isEmpty();
		case MocPackage.CSDF_MO_C__DELAY_PATTERN:
			return delayPattern != null;
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
		case MocPackage.CSDF_MO_C__INPUT_PATTERN:
			setInputPattern((Pattern) newValue);
			return;
		case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
			setNumberOfPhases((Integer) newValue);
			return;
		case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
			setOutputPattern((Pattern) newValue);
			return;
		case MocPackage.CSDF_MO_C__INVOCATIONS:
			getInvocations().clear();
			getInvocations()
					.addAll((Collection<? extends Invocation>) newValue);
			return;
		case MocPackage.CSDF_MO_C__DELAY_PATTERN:
			setDelayPattern((Pattern) newValue);
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
		case MocPackage.CSDF_MO_C__INPUT_PATTERN:
			setInputPattern((Pattern) null);
			return;
		case MocPackage.CSDF_MO_C__NUMBER_OF_PHASES:
			setNumberOfPhases(NUMBER_OF_PHASES_EDEFAULT);
			return;
		case MocPackage.CSDF_MO_C__OUTPUT_PATTERN:
			setOutputPattern((Pattern) null);
			return;
		case MocPackage.CSDF_MO_C__INVOCATIONS:
			getInvocations().clear();
			return;
		case MocPackage.CSDF_MO_C__DELAY_PATTERN:
			setDelayPattern((Pattern) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Pattern getDelayPattern() {
		return delayPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Pattern getInputPattern() {
		return inputPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Invocation> getInvocations() {
		if (invocations == null) {
			invocations = new EObjectContainmentEList<Invocation>(
					Invocation.class, this, MocPackage.CSDF_MO_C__INVOCATIONS);
		}
		return invocations;
	}

	@Override
	public String getShortName() {
		return "CSDF";
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
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
	@Override
	public int getNumTokensProduced(Port port) {
		return outputPattern.getNumTokens(port);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Pattern getOutputPattern() {
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
	@Override
	public void setDelayPattern(Pattern newDelayPattern) {
		if (newDelayPattern != delayPattern) {
			NotificationChain msgs = null;
			if (delayPattern != null)
				msgs = ((InternalEObject) delayPattern).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- MocPackage.CSDF_MO_C__DELAY_PATTERN, null,
						msgs);
			if (newDelayPattern != null)
				msgs = ((InternalEObject) newDelayPattern).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- MocPackage.CSDF_MO_C__DELAY_PATTERN, null,
						msgs);
			msgs = basicSetDelayPattern(newDelayPattern, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					MocPackage.CSDF_MO_C__DELAY_PATTERN, newDelayPattern,
					newDelayPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInputPattern(Pattern newInputPattern) {
		if (newInputPattern != inputPattern) {
			NotificationChain msgs = null;
			if (inputPattern != null)
				msgs = ((InternalEObject) inputPattern).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- MocPackage.CSDF_MO_C__INPUT_PATTERN, null,
						msgs);
			if (newInputPattern != null)
				msgs = ((InternalEObject) newInputPattern).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- MocPackage.CSDF_MO_C__INPUT_PATTERN, null,
						msgs);
			msgs = basicSetInputPattern(newInputPattern, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					MocPackage.CSDF_MO_C__INPUT_PATTERN, newInputPattern,
					newInputPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumberOfPhases(int newNumberOfPhases) {
		int oldNumberOfPhases = numberOfPhases;
		numberOfPhases = newNumberOfPhases;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					MocPackage.CSDF_MO_C__NUMBER_OF_PHASES, oldNumberOfPhases,
					numberOfPhases));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOutputPattern(Pattern newOutputPattern) {
		if (newOutputPattern != outputPattern) {
			NotificationChain msgs = null;
			if (outputPattern != null)
				msgs = ((InternalEObject) outputPattern).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- MocPackage.CSDF_MO_C__OUTPUT_PATTERN, null,
						msgs);
			if (newOutputPattern != null)
				msgs = ((InternalEObject) newOutputPattern).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- MocPackage.CSDF_MO_C__OUTPUT_PATTERN, null,
						msgs);
			msgs = basicSetOutputPattern(newOutputPattern, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					MocPackage.CSDF_MO_C__OUTPUT_PATTERN, newOutputPattern,
					newOutputPattern));
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
