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

import net.sf.orcc.ir.Cfg;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.ir.util.MapAdapter;
import net.sf.orcc.util.impl.AttributableImpl;

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
 * <em><b>Procedure</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getLocals <em>Locals</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#isNative <em>Native</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getBlocks <em>Blocks</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getCfg <em>Cfg</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcedureImpl extends AttributableImpl implements Procedure {

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

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
	 * The cached value of the '{@link #getBlocks() <em>Blocks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlocks()
	 * @generated
	 * @ordered
	 */
	protected EList<Block> blocks;

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
	 * The cached value of the '{@link #getCfg() <em>Cfg</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCfg()
	 * @generated
	 * @ordered
	 */
	protected Cfg cfg;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCfg(Cfg newCfg, NotificationChain msgs) {
		Cfg oldCfg = cfg;
		cfg = newCfg;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.PROCEDURE__CFG, oldCfg, newCfg);
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
		case IrPackage.PROCEDURE__BLOCKS:
			return getBlocks();
		case IrPackage.PROCEDURE__PARAMETERS:
			return getParameters();
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return getReturnType();
		case IrPackage.PROCEDURE__CFG:
			return getCfg();
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
		case IrPackage.PROCEDURE__BLOCKS:
			return ((InternalEList<?>) getBlocks()).basicRemove(otherEnd, msgs);
		case IrPackage.PROCEDURE__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd,
					msgs);
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return basicSetReturnType(null, msgs);
		case IrPackage.PROCEDURE__CFG:
			return basicSetCfg(null, msgs);
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
		case IrPackage.PROCEDURE__BLOCKS:
			return blocks != null && !blocks.isEmpty();
		case IrPackage.PROCEDURE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IrPackage.PROCEDURE__RETURN_TYPE:
			return returnType != null;
		case IrPackage.PROCEDURE__CFG:
			return cfg != null;
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
		case IrPackage.PROCEDURE__BLOCKS:
			getBlocks().clear();
			getBlocks().addAll((Collection<? extends Block>) newValue);
			return;
		case IrPackage.PROCEDURE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends Param>) newValue);
			return;
		case IrPackage.PROCEDURE__RETURN_TYPE:
			setReturnType((Type) newValue);
			return;
		case IrPackage.PROCEDURE__CFG:
			setCfg((Cfg) newValue);
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
		case IrPackage.PROCEDURE__BLOCKS:
			getBlocks().clear();
			return;
		case IrPackage.PROCEDURE__PARAMETERS:
			getParameters().clear();
			return;
		case IrPackage.PROCEDURE__RETURN_TYPE:
			setReturnType((Type) null);
			return;
		case IrPackage.PROCEDURE__CFG:
			setCfg((Cfg) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Block> getBlocks() {
		if (blocks == null) {
			blocks = new EObjectContainmentEList<Block>(Block.class, this,
					IrPackage.PROCEDURE__BLOCKS);
		}
		return blocks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Cfg getCfg() {
		return cfg;
	}

	/**
	 * Returns the first block in the list of nodes of this procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @return the first block in the list of nodes of this procedure
	 */
	public BlockBasic getFirst() {
		return IrUtil.getFirst(getNodes());
	}

	/**
	 * Returns the last block in the list of nodes of this procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @return the last block in the list of nodes of this procedure
	 */
	public BlockBasic getLast() {
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

	@Override
	public EList<Block> getNodes() {
		return getBlocks();
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCfg(Cfg newCfg) {
		if (newCfg != cfg) {
			NotificationChain msgs = null;
			if (cfg != null)
				msgs = ((InternalEObject) cfg).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__CFG,
						null, msgs);
			if (newCfg != null)
				msgs = ((InternalEObject) newCfg).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__CFG,
						null, msgs);
			msgs = basicSetCfg(newCfg, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.PROCEDURE__CFG, newCfg, newCfg));
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
