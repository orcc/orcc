/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.MapAdapter;
import net.sf.orcc.moc.MoC;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Actor</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getActionsOutsideFsm <em>Actions Outside Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getFsm <em>Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getInitializes <em>Initializes</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getMoC <em>Mo C</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getProcs <em>Procs</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getStateVars <em>State Vars</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#isNative <em>Native</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.ActorImpl#getLineNumber <em>Line Number</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActorImpl extends EntityImpl implements Actor {
	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> actions;

	/**
	 * The cached value of the '{@link #getActionsOutsideFsm() <em>Actions Outside Fsm</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionsOutsideFsm()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> actionsOutsideFsm;
	/**
	 * The cached value of the '{@link #getFsm() <em>Fsm</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFsm()
	 * @generated
	 * @ordered
	 */
	protected FSM fsm;
	/**
	 * The cached value of the '{@link #getInitializes() <em>Initializes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInitializes()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> initializes;

	private Map<String, Var> mapStateVars;

	/**
	 * The cached value of the '{@link #getMoC() <em>Mo C</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMoC()
	 * @generated
	 * @ordered
	 */
	protected MoC moC;

	/**
	 * The cached value of the '{@link #getProcs() <em>Procs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProcs()
	 * @generated
	 * @ordered
	 */
	protected EList<Procedure> procs;

	/**
	 * The cached value of the '{@link #getStateVars() <em>State Vars</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getStateVars()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> stateVars;

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
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	protected ActorImpl() {
		super();

		mapStateVars = new HashMap<String, Var>();

		eAdapters().add(new MapAdapter());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFsm(FSM newFsm, NotificationChain msgs) {
		FSM oldFsm = fsm;
		fsm = newFsm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DfPackage.ACTOR__FSM, oldFsm, newFsm);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMoC(MoC newMoC, NotificationChain msgs) {
		MoC oldMoC = moC;
		moC = newMoC;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DfPackage.ACTOR__MO_C, oldMoC, newMoC);
			if (msgs == null) msgs = notification; else msgs.add(notification);
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
			case DfPackage.ACTOR__ACTIONS:
				return getActions();
			case DfPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				return getActionsOutsideFsm();
			case DfPackage.ACTOR__FSM:
				return getFsm();
			case DfPackage.ACTOR__INITIALIZES:
				return getInitializes();
			case DfPackage.ACTOR__MO_C:
				return getMoC();
			case DfPackage.ACTOR__PROCS:
				return getProcs();
			case DfPackage.ACTOR__STATE_VARS:
				return getStateVars();
			case DfPackage.ACTOR__NATIVE:
				return isNative();
			case DfPackage.ACTOR__FILE_NAME:
				return getFileName();
			case DfPackage.ACTOR__LINE_NUMBER:
				return getLineNumber();
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
			case DfPackage.ACTOR__ACTIONS:
				return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
			case DfPackage.ACTOR__FSM:
				return basicSetFsm(null, msgs);
			case DfPackage.ACTOR__INITIALIZES:
				return ((InternalEList<?>)getInitializes()).basicRemove(otherEnd, msgs);
			case DfPackage.ACTOR__MO_C:
				return basicSetMoC(null, msgs);
			case DfPackage.ACTOR__PROCS:
				return ((InternalEList<?>)getProcs()).basicRemove(otherEnd, msgs);
			case DfPackage.ACTOR__STATE_VARS:
				return ((InternalEList<?>)getStateVars()).basicRemove(otherEnd, msgs);
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
			case DfPackage.ACTOR__ACTIONS:
				return actions != null && !actions.isEmpty();
			case DfPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				return actionsOutsideFsm != null && !actionsOutsideFsm.isEmpty();
			case DfPackage.ACTOR__FSM:
				return fsm != null;
			case DfPackage.ACTOR__INITIALIZES:
				return initializes != null && !initializes.isEmpty();
			case DfPackage.ACTOR__MO_C:
				return moC != null;
			case DfPackage.ACTOR__PROCS:
				return procs != null && !procs.isEmpty();
			case DfPackage.ACTOR__STATE_VARS:
				return stateVars != null && !stateVars.isEmpty();
			case DfPackage.ACTOR__NATIVE:
				return native_ != NATIVE_EDEFAULT;
			case DfPackage.ACTOR__FILE_NAME:
				return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
			case DfPackage.ACTOR__LINE_NUMBER:
				return lineNumber != LINE_NUMBER_EDEFAULT;
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
			case DfPackage.ACTOR__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends Action>)newValue);
				return;
			case DfPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				getActionsOutsideFsm().clear();
				getActionsOutsideFsm().addAll((Collection<? extends Action>)newValue);
				return;
			case DfPackage.ACTOR__FSM:
				setFsm((FSM)newValue);
				return;
			case DfPackage.ACTOR__INITIALIZES:
				getInitializes().clear();
				getInitializes().addAll((Collection<? extends Action>)newValue);
				return;
			case DfPackage.ACTOR__MO_C:
				setMoC((MoC)newValue);
				return;
			case DfPackage.ACTOR__PROCS:
				getProcs().clear();
				getProcs().addAll((Collection<? extends Procedure>)newValue);
				return;
			case DfPackage.ACTOR__STATE_VARS:
				getStateVars().clear();
				getStateVars().addAll((Collection<? extends Var>)newValue);
				return;
			case DfPackage.ACTOR__NATIVE:
				setNative((Boolean)newValue);
				return;
			case DfPackage.ACTOR__FILE_NAME:
				setFileName((String)newValue);
				return;
			case DfPackage.ACTOR__LINE_NUMBER:
				setLineNumber((Integer)newValue);
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
		return DfPackage.Literals.ACTOR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DfPackage.ACTOR__ACTIONS:
				getActions().clear();
				return;
			case DfPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				getActionsOutsideFsm().clear();
				return;
			case DfPackage.ACTOR__FSM:
				setFsm((FSM)null);
				return;
			case DfPackage.ACTOR__INITIALIZES:
				getInitializes().clear();
				return;
			case DfPackage.ACTOR__MO_C:
				setMoC((MoC)null);
				return;
			case DfPackage.ACTOR__PROCS:
				getProcs().clear();
				return;
			case DfPackage.ACTOR__STATE_VARS:
				getStateVars().clear();
				return;
			case DfPackage.ACTOR__NATIVE:
				setNative(NATIVE_EDEFAULT);
				return;
			case DfPackage.ACTOR__FILE_NAME:
				setFileName(FILE_NAME_EDEFAULT);
				return;
			case DfPackage.ACTOR__LINE_NUMBER:
				setLineNumber(LINE_NUMBER_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Action> getActions() {
		if (actions == null) {
			actions = new EObjectContainmentEList<Action>(Action.class, this, DfPackage.ACTOR__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Action> getActionsOutsideFsm() {
		if (actionsOutsideFsm == null) {
			actionsOutsideFsm = new EObjectResolvingEList<Action>(Action.class, this, DfPackage.ACTOR__ACTIONS_OUTSIDE_FSM);
		}
		return actionsOutsideFsm;
	}

	@Override
	public IFile getFile() {
		String fileName = getFileName();
		if (fileName == null) {
			return null;
		}
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getFile(new Path(fileName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FSM getFsm() {
		return fsm;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Action> getInitializes() {
		if (initializes == null) {
			initializes = new EObjectContainmentEList<Action>(Action.class, this, DfPackage.ACTOR__INITIALIZES);
		}
		return initializes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MoC getMoC() {
		return moC;
	}

	@Override
	public Procedure getProcedure(String name) {
		for (Procedure procedure : getProcs()) {
			if (procedure.getName().equals(name)) {
				return procedure;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Procedure> getProcs() {
		if (procs == null) {
			procs = new EObjectContainmentEList<Procedure>(Procedure.class, this, DfPackage.ACTOR__PROCS);
		}
		return procs;
	}

	@Override
	public Var getStateVar(String name) {
		return mapStateVars.get(name);
	}

	public Map<String, Var> getStateVariablesMap() {
		return mapStateVars;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Var> getStateVars() {
		if (stateVars == null) {
			stateVars = new EObjectContainmentEList<Var>(Var.class, this, DfPackage.ACTOR__STATE_VARS);
		}
		return stateVars;
	}

	@Override
	public boolean hasFsm() {
		return fsm != null;
	}

	@Override
	public boolean hasMoC() {
		return moC != null;
	}

	@Override
	public boolean isActor() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNative() {
		return native_;
	}

	@Override
	public void resetTokenConsumption() {
		for (Port port : getInputs()) {
			port.resetTokenConsumption();
		}
	}

	@Override
	public void resetTokenProduction() {
		for (Port port : getOutputs()) {
			port.resetTokenProduction();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DfPackage.ACTOR__FILE_NAME, oldFileName, fileName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFsm(FSM newFsm) {
		if (newFsm != fsm) {
			NotificationChain msgs = null;
			if (fsm != null)
				msgs = ((InternalEObject)fsm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DfPackage.ACTOR__FSM, null, msgs);
			if (newFsm != null)
				msgs = ((InternalEObject)newFsm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DfPackage.ACTOR__FSM, null, msgs);
			msgs = basicSetFsm(newFsm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DfPackage.ACTOR__FSM, newFsm, newFsm));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DfPackage.ACTOR__LINE_NUMBER, oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMoC(MoC newMoC) {
		if (newMoC != moC) {
			NotificationChain msgs = null;
			if (moC != null)
				msgs = ((InternalEObject)moC).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DfPackage.ACTOR__MO_C, null, msgs);
			if (newMoC != null)
				msgs = ((InternalEObject)newMoC).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DfPackage.ACTOR__MO_C, null, msgs);
			msgs = basicSetMoC(newMoC, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DfPackage.ACTOR__MO_C, newMoC, newMoC));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNative(boolean newNative) {
		boolean oldNative = native_;
		native_ = newNative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DfPackage.ACTOR__NATIVE, oldNative, native_));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (native: ");
		result.append(native_);
		result.append(", fileName: ");
		result.append(fileName);
		result.append(", lineNumber: ");
		result.append(lineNumber);
		result.append(')');
		return result.toString();
	}

} // ActorImpl
