/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.ir.CFG;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.ir.util.MapAdapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Procedure</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getLocals <em>Locals</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#isNative <em>Native</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getReturnType <em>Return Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcedureImpl extends EObjectImpl implements Procedure {

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	private CFG graph;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLocals() <em>Locals</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLocals()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> locals;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * A map from name to index in the locals list.
	 */
	private Map<String, Var> mapLocals;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isNative() <em>Native</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isNative()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NATIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNative() <em>Native</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isNative()
	 * @generated
	 * @ordered
	 */
	protected boolean native_ = NATIVE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Param> parameters;

	private Expression result;

	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected Type returnType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	protected ProcedureImpl() {
		super();

		mapLocals = new HashMap<String, Var>();

		eAdapters().add(new MapAdapter());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturnType(Type newReturnType,
			NotificationChain msgs) {
		Type oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.PROCEDURE__RETURN_TYPE,
					oldReturnType, newReturnType);
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
		case IrPackage.PROCEDURE__LINE_NUMBER:
			return getLineNumber();
		case IrPackage.PROCEDURE__LOCALS:
			return getLocals();
		case IrPackage.PROCEDURE__NAME:
			return getName();
		case IrPackage.PROCEDURE__NATIVE:
			return isNative();
		case IrPackage.PROCEDURE__NODES:
			return getNodes();
		case IrPackage.PROCEDURE__PARAMETERS:
			return getParameters();
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return getReturnType();
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
		case IrPackage.PROCEDURE__LOCALS:
			return ((InternalEList<?>) getLocals()).basicRemove(otherEnd, msgs);
		case IrPackage.PROCEDURE__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case IrPackage.PROCEDURE__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd,
					msgs);
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return basicSetReturnType(null, msgs);
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
		case IrPackage.PROCEDURE__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case IrPackage.PROCEDURE__LOCALS:
			return locals != null && !locals.isEmpty();
		case IrPackage.PROCEDURE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case IrPackage.PROCEDURE__NATIVE:
			return native_ != NATIVE_EDEFAULT;
		case IrPackage.PROCEDURE__NODES:
			return nodes != null && !nodes.isEmpty();
		case IrPackage.PROCEDURE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return returnType != null;
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
		case IrPackage.PROCEDURE__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case IrPackage.PROCEDURE__LOCALS:
			getLocals().clear();
			getLocals().addAll((Collection<? extends Var>) newValue);
			return;
		case IrPackage.PROCEDURE__NAME:
			setName((String) newValue);
			return;
		case IrPackage.PROCEDURE__NATIVE:
			setNative((Boolean) newValue);
			return;
		case IrPackage.PROCEDURE__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends Node>) newValue);
			return;
		case IrPackage.PROCEDURE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends Param>) newValue);
			return;
		case IrPackage.PROCEDURE__RETURN_TYPE:
			setReturnType((Type) newValue);
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
		return IrPackage.Literals.PROCEDURE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.PROCEDURE__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case IrPackage.PROCEDURE__LOCALS:
			getLocals().clear();
			return;
		case IrPackage.PROCEDURE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case IrPackage.PROCEDURE__NATIVE:
			setNative(NATIVE_EDEFAULT);
			return;
		case IrPackage.PROCEDURE__NODES:
			getNodes().clear();
			return;
		case IrPackage.PROCEDURE__PARAMETERS:
			getParameters().clear();
			return;
		case IrPackage.PROCEDURE__RETURN_TYPE:
			setReturnType((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * Returns the CFG of this procedure. The CFG must be set by calling
	 * {@link #setGraph(CFG)}.
	 * 
	 * @return the CFG of this procedure
	 */
	public CFG getCFG() {
		return graph;
	}

	/**
	 * Returns the first block in the list of nodes of this procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @return the first block in the list of nodes of this procedure
	 */
	public NodeBlock getFirst() {
		return IrUtil.getFirst(getNodes());
	}

	/**
	 * Returns the last block in the list of nodes of this procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @return the last block in the list of nodes of this procedure
	 */
	public NodeBlock getLast() {
		return IrUtil.getLast(getNodes());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public Var getLocal(String name) {
		return mapLocals.get(name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getLocals() {
		if (locals == null) {
			locals = new EObjectContainmentEList<Var>(Var.class, this,
					IrPackage.PROCEDURE__LOCALS);
		}
		return locals;
	}

	public Map<String, Var> getLocalsMap() {
		return mapLocals;
	}

	/**
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<Node>(Node.class, this,
					IrPackage.PROCEDURE__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Param> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Param>(Param.class, this,
					IrPackage.PROCEDURE__PARAMETERS);
		}
		return parameters;
	}

	public Expression getResult() {
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNative() {
		return native_;
	}

	/**
	 * Creates a new local variable that can be used to hold intermediate
	 * results. The variable is added to {@link #procedure}'s locals.
	 * 
	 * @param type
	 *            type of the variable
	 * @param name
	 *            hint for the variable name
	 * @return a new local variable
	 */
	public Var newTempLocalVariable(Type type, String hint) {
		String name = hint;
		Var variable = getLocal(name);
		int i = 0;
		while (variable != null) {
			name = hint + i;
			variable = getLocal(name);
			i++;
		}

		variable = IrFactory.eINSTANCE.createVar(0, type, name, true, 0);
		getLocals().add(variable);
		return variable;
	}

	/**
	 * Set the CFG of this procedure.
	 * 
	 * @param the
	 *            CFG of this procedure
	 */
	public void setGraph(CFG graph) {
		this.graph = graph;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.PROCEDURE__LINE_NUMBER, oldLineNumber, lineNumber));
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
					IrPackage.PROCEDURE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNative(boolean newNative) {
		boolean oldNative = native_;
		native_ = newNative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.PROCEDURE__NATIVE, oldNative, native_));
	}

	public void setResult(Expression result) {
		this.result = result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnType(Type newReturnType) {
		if (newReturnType != returnType) {
			NotificationChain msgs = null;
			if (returnType != null)
				msgs = ((InternalEObject) returnType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrPackage.PROCEDURE__RETURN_TYPE, null, msgs);
			if (newReturnType != null)
				msgs = ((InternalEObject) newReturnType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrPackage.PROCEDURE__RETURN_TYPE, null, msgs);
			msgs = basicSetReturnType(newReturnType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.PROCEDURE__RETURN_TYPE, newReturnType,
					newReturnType));
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
		result.append(" (lineNumber: ");
		result.append(lineNumber);
		result.append(", name: ");
		result.append(name);
		result.append(", native: ");
		result.append(native_);
		result.append(')');
		return result.toString();
	}

} // ProcedureImpl
