/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Unit;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.impl.AttributableImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Var</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#isAssignable <em>Assignable</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getDefs <em>Defs</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#isGlobal <em>Global</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#isLocal <em>Local</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getType <em>Type</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getUses <em>Uses</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.VarImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VarImpl extends AttributableImpl implements Var {
	/**
	 * The default value of the '{@link #isAssignable() <em>Assignable</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAssignable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ASSIGNABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAssignable() <em>Assignable</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAssignable()
	 * @generated
	 * @ordered
	 */
	protected boolean assignable = ASSIGNABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDefs() <em>Defs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefs()
	 * @generated
	 * @ordered
	 */
	protected EList<Def> defs;

	/**
	 * The default value of the '{@link #isGlobal() <em>Global</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isGlobal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GLOBAL_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInitialValue() <em>Initial Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialValue()
	 * @generated
	 * @ordered
	 */
	protected Expression initialValue;

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #isLocal() <em>Local</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLocal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOCAL_EDEFAULT = false;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Type type;

	/**
	 * The cached value of the '{@link #getUses() <em>Uses</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUses()
	 * @generated
	 * @ordered
	 */
	protected EList<Use> uses;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Object value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected VarImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialValue(Expression newInitialValue,
			NotificationChain msgs) {
		Expression oldInitialValue = initialValue;
		initialValue = newInitialValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.VAR__INITIAL_VALUE,
					oldInitialValue, newInitialValue);
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
	public NotificationChain basicSetType(Type newType, NotificationChain msgs) {
		Type oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.VAR__TYPE, oldType, newType);
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
		case IrPackage.VAR__ASSIGNABLE:
			return isAssignable();
		case IrPackage.VAR__DEFS:
			return getDefs();
		case IrPackage.VAR__GLOBAL:
			return isGlobal();
		case IrPackage.VAR__INDEX:
			return getIndex();
		case IrPackage.VAR__INITIAL_VALUE:
			return getInitialValue();
		case IrPackage.VAR__LINE_NUMBER:
			return getLineNumber();
		case IrPackage.VAR__LOCAL:
			return isLocal();
		case IrPackage.VAR__NAME:
			return getName();
		case IrPackage.VAR__TYPE:
			return getType();
		case IrPackage.VAR__USES:
			return getUses();
		case IrPackage.VAR__VALUE:
			return getValue();
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
		case IrPackage.VAR__DEFS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getDefs())
					.basicAdd(otherEnd, msgs);
		case IrPackage.VAR__USES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getUses())
					.basicAdd(otherEnd, msgs);
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
		case IrPackage.VAR__DEFS:
			return ((InternalEList<?>) getDefs()).basicRemove(otherEnd, msgs);
		case IrPackage.VAR__INITIAL_VALUE:
			return basicSetInitialValue(null, msgs);
		case IrPackage.VAR__TYPE:
			return basicSetType(null, msgs);
		case IrPackage.VAR__USES:
			return ((InternalEList<?>) getUses()).basicRemove(otherEnd, msgs);
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
		case IrPackage.VAR__ASSIGNABLE:
			return assignable != ASSIGNABLE_EDEFAULT;
		case IrPackage.VAR__DEFS:
			return defs != null && !defs.isEmpty();
		case IrPackage.VAR__GLOBAL:
			return isGlobal() != GLOBAL_EDEFAULT;
		case IrPackage.VAR__INDEX:
			return index != INDEX_EDEFAULT;
		case IrPackage.VAR__INITIAL_VALUE:
			return initialValue != null;
		case IrPackage.VAR__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case IrPackage.VAR__LOCAL:
			return isLocal() != LOCAL_EDEFAULT;
		case IrPackage.VAR__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case IrPackage.VAR__TYPE:
			return type != null;
		case IrPackage.VAR__USES:
			return uses != null && !uses.isEmpty();
		case IrPackage.VAR__VALUE:
			return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT
					.equals(value);
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
		case IrPackage.VAR__ASSIGNABLE:
			setAssignable((Boolean) newValue);
			return;
		case IrPackage.VAR__DEFS:
			getDefs().clear();
			getDefs().addAll((Collection<? extends Def>) newValue);
			return;
		case IrPackage.VAR__INDEX:
			setIndex((Integer) newValue);
			return;
		case IrPackage.VAR__INITIAL_VALUE:
			setInitialValue((Expression) newValue);
			return;
		case IrPackage.VAR__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case IrPackage.VAR__NAME:
			setName((String) newValue);
			return;
		case IrPackage.VAR__TYPE:
			setType((Type) newValue);
			return;
		case IrPackage.VAR__USES:
			getUses().clear();
			getUses().addAll((Collection<? extends Use>) newValue);
			return;
		case IrPackage.VAR__VALUE:
			setValue(newValue);
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
		return IrPackage.Literals.VAR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.VAR__ASSIGNABLE:
			setAssignable(ASSIGNABLE_EDEFAULT);
			return;
		case IrPackage.VAR__DEFS:
			getDefs().clear();
			return;
		case IrPackage.VAR__INDEX:
			setIndex(INDEX_EDEFAULT);
			return;
		case IrPackage.VAR__INITIAL_VALUE:
			setInitialValue((Expression) null);
			return;
		case IrPackage.VAR__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case IrPackage.VAR__NAME:
			setName(NAME_EDEFAULT);
			return;
		case IrPackage.VAR__TYPE:
			setType((Type) null);
			return;
		case IrPackage.VAR__USES:
			getUses().clear();
			return;
		case IrPackage.VAR__VALUE:
			setValue(VALUE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Def> getDefs() {
		if (defs == null) {
			defs = new EObjectWithInverseResolvingEList<Def>(Def.class, this,
					IrPackage.VAR__DEFS, IrPackage.DEF__VARIABLE);
		}
		return defs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getIndex() {
		return index;
	}

	@Override
	public String getIndexedName() {
		if (index == 0) {
			return name;
		} else {
			return name + "_" + index;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getInitialValue() {
		return initialValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Use> getUses() {
		if (uses == null) {
			uses = new EObjectWithInverseResolvingEList<Use>(Use.class, this,
					IrPackage.VAR__USES, IrPackage.USE__VARIABLE);
		}
		return uses;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAssignable() {
		return assignable;
	}

	@Override
	public boolean isDefined() {
		return !getDefs().isEmpty();
	}

	@Override
	public boolean isGlobal() {
		EObject cter = eContainer();
		return (cter instanceof Actor || cter instanceof Network || cter instanceof Unit);
	}

	@Override
	public boolean isInitialized() {
		return initialValue != null;
	}

	@Override
	public boolean isLocal() {
		EObject cter = eContainer();
		return (cter instanceof Procedure || cter instanceof Param || cter instanceof Pattern);
	}

	@Override
	public boolean isParam() {
		return eContainingFeature() == IrPackage.Literals.PARAM__VARIABLE;
	}

	@Override
	public boolean isUsed() {
		return !getUses().isEmpty();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssignable(boolean newAssignable) {
		boolean oldAssignable = assignable;
		assignable = newAssignable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.VAR__ASSIGNABLE, oldAssignable, assignable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(int newIndex) {
		int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.VAR__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialValue(Expression newInitialValue) {
		if (newInitialValue != initialValue) {
			NotificationChain msgs = null;
			if (initialValue != null)
				msgs = ((InternalEObject) initialValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.VAR__INITIAL_VALUE,
						null, msgs);
			if (newInitialValue != null)
				msgs = ((InternalEObject) newInitialValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.VAR__INITIAL_VALUE,
						null, msgs);
			msgs = basicSetInitialValue(newInitialValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.VAR__INITIAL_VALUE, newInitialValue,
					newInitialValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.VAR__LINE_NUMBER, oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.VAR__NAME, oldName, name));
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
						EOPPOSITE_FEATURE_BASE - IrPackage.VAR__TYPE, null,
						msgs);
			if (newType != null)
				msgs = ((InternalEObject) newType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.VAR__TYPE, null,
						msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.VAR__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(Object newValue) {
		Object oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.VAR__VALUE, oldValue, value));
	}

	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer();
		result.append(type);
		result.append(" ");
		result.append(getIndexedName());
		result.append(" <assignable: ");
		result.append(assignable);
		result.append(", global: ");
		result.append(isGlobal());
		result.append('>');
		if (value != null) {
			result.append(" value = ");
			result.append(value);
		}
		return result.toString();
	}

}
