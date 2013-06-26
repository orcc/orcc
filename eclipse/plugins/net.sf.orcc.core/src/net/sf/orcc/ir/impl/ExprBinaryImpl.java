/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Expr Binary</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ExprBinaryImpl#getE1 <em>E1</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ExprBinaryImpl#getE2 <em>E2</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ExprBinaryImpl#getOp <em>Op</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ExprBinaryImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExprBinaryImpl extends ExpressionImpl implements ExprBinary {

	/**
	 * The cached value of the '{@link #getE1() <em>E1</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getE1()
	 * @generated
	 * @ordered
	 */
	protected Expression e1;

	/**
	 * The cached value of the '{@link #getE2() <em>E2</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getE2()
	 * @generated
	 * @ordered
	 */
	protected Expression e2;

	/**
	 * The default value of the '{@link #getOp() <em>Op</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOp()
	 * @generated
	 * @ordered
	 */
	protected static final OpBinary OP_EDEFAULT = OpBinary.BITAND;

	/**
	 * The cached value of the '{@link #getOp() <em>Op</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOp()
	 * @generated
	 * @ordered
	 */
	protected OpBinary op = OP_EDEFAULT;

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
	protected ExprBinaryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetE1(Expression newE1, NotificationChain msgs) {
		Expression oldE1 = e1;
		e1 = newE1;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.EXPR_BINARY__E1, oldE1, newE1);
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
	public NotificationChain basicSetE2(Expression newE2, NotificationChain msgs) {
		Expression oldE2 = e2;
		e2 = newE2;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.EXPR_BINARY__E2, oldE2, newE2);
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
					Notification.SET, IrPackage.EXPR_BINARY__TYPE, oldType,
					newType);
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
		case IrPackage.EXPR_BINARY__E1:
			return getE1();
		case IrPackage.EXPR_BINARY__E2:
			return getE2();
		case IrPackage.EXPR_BINARY__OP:
			return getOp();
		case IrPackage.EXPR_BINARY__TYPE:
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
		case IrPackage.EXPR_BINARY__E1:
			return basicSetE1(null, msgs);
		case IrPackage.EXPR_BINARY__E2:
			return basicSetE2(null, msgs);
		case IrPackage.EXPR_BINARY__TYPE:
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
		case IrPackage.EXPR_BINARY__E1:
			return e1 != null;
		case IrPackage.EXPR_BINARY__E2:
			return e2 != null;
		case IrPackage.EXPR_BINARY__OP:
			return op != OP_EDEFAULT;
		case IrPackage.EXPR_BINARY__TYPE:
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
		case IrPackage.EXPR_BINARY__E1:
			setE1((Expression) newValue);
			return;
		case IrPackage.EXPR_BINARY__E2:
			setE2((Expression) newValue);
			return;
		case IrPackage.EXPR_BINARY__OP:
			setOp((OpBinary) newValue);
			return;
		case IrPackage.EXPR_BINARY__TYPE:
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
		return IrPackage.Literals.EXPR_BINARY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.EXPR_BINARY__E1:
			setE1((Expression) null);
			return;
		case IrPackage.EXPR_BINARY__E2:
			setE2((Expression) null);
			return;
		case IrPackage.EXPR_BINARY__OP:
			setOp(OP_EDEFAULT);
			return;
		case IrPackage.EXPR_BINARY__TYPE:
			setType((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getE1() {
		return e1;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getE2() {
		return e2;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public OpBinary getOp() {
		return op;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getType() {
		return type;
	}

	@Override
	public boolean isExprBinary() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setE1(Expression newE1) {
		if (newE1 != e1) {
			NotificationChain msgs = null;
			if (e1 != null)
				msgs = ((InternalEObject) e1).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_BINARY__E1,
						null, msgs);
			if (newE1 != null)
				msgs = ((InternalEObject) newE1).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_BINARY__E1,
						null, msgs);
			msgs = basicSetE1(newE1, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPR_BINARY__E1, newE1, newE1));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setE2(Expression newE2) {
		if (newE2 != e2) {
			NotificationChain msgs = null;
			if (e2 != null)
				msgs = ((InternalEObject) e2).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_BINARY__E2,
						null, msgs);
			if (newE2 != null)
				msgs = ((InternalEObject) newE2).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_BINARY__E2,
						null, msgs);
			msgs = basicSetE2(newE2, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPR_BINARY__E2, newE2, newE2));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOp(OpBinary newOp) {
		OpBinary oldOp = op;
		op = newOp == null ? OP_EDEFAULT : newOp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPR_BINARY__OP, oldOp, op));
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
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_BINARY__TYPE,
						null, msgs);
			if (newType != null)
				msgs = ((InternalEObject) newType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.EXPR_BINARY__TYPE,
						null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.EXPR_BINARY__TYPE, newType, newType));
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
		result.append(" (op: ");
		result.append(op);
		result.append(')');
		return result.toString();
	}

} // ExprBinaryImpl
