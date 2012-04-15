/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import net.sf.dftools.graph.impl.VertexImpl;
import net.sf.dftools.util.Attribute;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Port</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.PortImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.PortImpl#getNumTokensConsumed <em>Num Tokens Consumed</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.PortImpl#getNumTokensProduced <em>Num Tokens Produced</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.PortImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortImpl extends VertexImpl implements Port {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getNumTokensConsumed() <em>Num Tokens Consumed</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getNumTokensConsumed()
	 * @generated
	 * @ordered
	 */
	protected static final int NUM_TOKENS_CONSUMED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumTokensConsumed() <em>Num Tokens Consumed</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getNumTokensConsumed()
	 * @generated
	 * @ordered
	 */
	protected int numTokensConsumed = NUM_TOKENS_CONSUMED_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumTokensProduced() <em>Num Tokens Produced</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getNumTokensProduced()
	 * @generated
	 * @ordered
	 */
	protected static final int NUM_TOKENS_PRODUCED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumTokensProduced() <em>Num Tokens Produced</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getNumTokensProduced()
	 * @generated
	 * @ordered
	 */
	protected int numTokensProduced = NUM_TOKENS_PRODUCED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Type type;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected PortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(Type newType, NotificationChain msgs) {
		Type oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, DfPackage.PORT__TYPE, oldType, newType);
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
		case DfPackage.PORT__NAME:
			return getName();
		case DfPackage.PORT__NUM_TOKENS_CONSUMED:
			return getNumTokensConsumed();
		case DfPackage.PORT__NUM_TOKENS_PRODUCED:
			return getNumTokensProduced();
		case DfPackage.PORT__TYPE:
			return getType();
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
		case DfPackage.PORT__TYPE:
			return basicSetType(null, msgs);
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
		case DfPackage.PORT__NAME:
			return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT
					.equals(getName());
		case DfPackage.PORT__NUM_TOKENS_CONSUMED:
			return numTokensConsumed != NUM_TOKENS_CONSUMED_EDEFAULT;
		case DfPackage.PORT__NUM_TOKENS_PRODUCED:
			return numTokensProduced != NUM_TOKENS_PRODUCED_EDEFAULT;
		case DfPackage.PORT__TYPE:
			return type != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DfPackage.PORT__NAME:
			setName((String) newValue);
			return;
		case DfPackage.PORT__NUM_TOKENS_CONSUMED:
			setNumTokensConsumed((Integer) newValue);
			return;
		case DfPackage.PORT__NUM_TOKENS_PRODUCED:
			setNumTokensProduced((Integer) newValue);
			return;
		case DfPackage.PORT__TYPE:
			setType((Type) newValue);
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
		return DfPackage.Literals.PORT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DfPackage.PORT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DfPackage.PORT__NUM_TOKENS_CONSUMED:
			setNumTokensConsumed(NUM_TOKENS_CONSUMED_EDEFAULT);
			return;
		case DfPackage.PORT__NUM_TOKENS_PRODUCED:
			setNumTokensProduced(NUM_TOKENS_PRODUCED_EDEFAULT);
			return;
		case DfPackage.PORT__TYPE:
			setType((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumTokensConsumed() {
		return numTokensConsumed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumTokensProduced() {
		return numTokensProduced;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getType() {
		return type;
	}

	@Override
	public void increaseTokenConsumption(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		numTokensConsumed += n;
	}

	@Override
	public void increaseTokenProduction(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		numTokensProduced += n;
	}

	@Override
	public boolean isNative() {
		Attribute attribute = getAttribute("native");
		return attribute != null;
	}

	/**
	 * Returns true. Only used in templates.
	 * 
	 * @return
	 */
	public boolean isPort() {
		return true;
	}

	@Override
	public void resetTokenConsumption() {
		numTokensConsumed = 0;
	}

	@Override
	public void resetTokenProduction() {
		numTokensProduced = 0;
	}

	@Override
	public String getName() {
		return getLabel();
	}

	@Override
	public void setName(String newName) {
		setLabel(newName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumTokensConsumed(int newNumTokensConsumed) {
		int oldNumTokensConsumed = numTokensConsumed;
		numTokensConsumed = newNumTokensConsumed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.PORT__NUM_TOKENS_CONSUMED, oldNumTokensConsumed,
					numTokensConsumed));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumTokensProduced(int newNumTokensProduced) {
		int oldNumTokensProduced = numTokensProduced;
		numTokensProduced = newNumTokensProduced;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.PORT__NUM_TOKENS_PRODUCED, oldNumTokensProduced,
					numTokensProduced));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Type newType) {
		if (newType != type) {
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject) type).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DfPackage.PORT__TYPE, null,
						msgs);
			if (newType != null)
				msgs = ((InternalEObject) newType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DfPackage.PORT__TYPE, null,
						msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.PORT__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (numTokensConsumed: ");
		result.append(numTokensConsumed);
		result.append(", numTokensProduced: ");
		result.append(numTokensProduced);
		result.append(')');
		return result.toString();
	}

} // PortImpl
