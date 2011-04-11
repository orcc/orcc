/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.MapAdapter;
import net.sf.orcc.moc.MoC;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Actor</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getFile <em>File</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getInitializes <em>Initializes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getProcs <em>Procs</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getStateVars <em>State Vars</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#isNative <em>Native</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getActionsOutsideFsm <em>Actions Outside Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getFsm <em>Fsm</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActorImpl extends EObjectImpl implements Actor {
	/**
	 * The default value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isNative() <em>Native</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isNative()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NATIVE_EDEFAULT = false;

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
	 * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected String file = FILE_EDEFAULT;

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

	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> inputs;

	private Map<String, Integer> mapInputs;

	private Map<String, Integer> mapOutputs;

	private Map<String, Integer> mapParameters;

	private Map<String, Integer> mapProcedures;

	private Map<String, Integer> mapStateVars;

	/**
	 * the class of this actor. Initialized to unknown.
	 */
	private MoC moc;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #isNative() <em>Native</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isNative()
	 * @generated
	 * @ordered
	 */
	protected boolean native_ = NATIVE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> outputs;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> parameters;

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
	 * holds template-specific data.
	 */
	private Object templateData;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	protected ActorImpl() {
		super();

		mapInputs = new HashMap<String, Integer>();
		mapOutputs = new HashMap<String, Integer>();
		mapParameters = new HashMap<String, Integer>();
		mapProcedures = new HashMap<String, Integer>();
		mapStateVars = new HashMap<String, Integer>();

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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__FSM, oldFsm, newFsm);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	@Override
	public int compareTo(Actor o) {
		return getName().compareTo(o.getName());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.ACTOR__ACTIONS:
				return getActions();
			case IrPackage.ACTOR__FILE:
				return getFile();
			case IrPackage.ACTOR__INITIALIZES:
				return getInitializes();
			case IrPackage.ACTOR__INPUTS:
				return getInputs();
			case IrPackage.ACTOR__NAME:
				return getName();
			case IrPackage.ACTOR__OUTPUTS:
				return getOutputs();
			case IrPackage.ACTOR__PARAMETERS:
				return getParameters();
			case IrPackage.ACTOR__PROCS:
				return getProcs();
			case IrPackage.ACTOR__STATE_VARS:
				return getStateVars();
			case IrPackage.ACTOR__NATIVE:
				return isNative();
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				return getActionsOutsideFsm();
			case IrPackage.ACTOR__FSM:
				return getFsm();
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
			case IrPackage.ACTOR__ACTIONS:
				return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__INITIALIZES:
				return ((InternalEList<?>)getInitializes()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__INPUTS:
				return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__OUTPUTS:
				return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__PROCS:
				return ((InternalEList<?>)getProcs()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__STATE_VARS:
				return ((InternalEList<?>)getStateVars()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__FSM:
				return basicSetFsm(null, msgs);
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
			case IrPackage.ACTOR__ACTIONS:
				return actions != null && !actions.isEmpty();
			case IrPackage.ACTOR__FILE:
				return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
			case IrPackage.ACTOR__INITIALIZES:
				return initializes != null && !initializes.isEmpty();
			case IrPackage.ACTOR__INPUTS:
				return inputs != null && !inputs.isEmpty();
			case IrPackage.ACTOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IrPackage.ACTOR__OUTPUTS:
				return outputs != null && !outputs.isEmpty();
			case IrPackage.ACTOR__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case IrPackage.ACTOR__PROCS:
				return procs != null && !procs.isEmpty();
			case IrPackage.ACTOR__STATE_VARS:
				return stateVars != null && !stateVars.isEmpty();
			case IrPackage.ACTOR__NATIVE:
				return native_ != NATIVE_EDEFAULT;
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				return actionsOutsideFsm != null && !actionsOutsideFsm.isEmpty();
			case IrPackage.ACTOR__FSM:
				return fsm != null;
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
			case IrPackage.ACTOR__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends Action>)newValue);
				return;
			case IrPackage.ACTOR__FILE:
				setFile((String)newValue);
				return;
			case IrPackage.ACTOR__INITIALIZES:
				getInitializes().clear();
				getInitializes().addAll((Collection<? extends Action>)newValue);
				return;
			case IrPackage.ACTOR__INPUTS:
				getInputs().clear();
				getInputs().addAll((Collection<? extends Port>)newValue);
				return;
			case IrPackage.ACTOR__NAME:
				setName((String)newValue);
				return;
			case IrPackage.ACTOR__OUTPUTS:
				getOutputs().clear();
				getOutputs().addAll((Collection<? extends Port>)newValue);
				return;
			case IrPackage.ACTOR__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Var>)newValue);
				return;
			case IrPackage.ACTOR__PROCS:
				getProcs().clear();
				getProcs().addAll((Collection<? extends Procedure>)newValue);
				return;
			case IrPackage.ACTOR__STATE_VARS:
				getStateVars().clear();
				getStateVars().addAll((Collection<? extends Var>)newValue);
				return;
			case IrPackage.ACTOR__NATIVE:
				setNative((Boolean)newValue);
				return;
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				getActionsOutsideFsm().clear();
				getActionsOutsideFsm().addAll((Collection<? extends Action>)newValue);
				return;
			case IrPackage.ACTOR__FSM:
				setFsm((FSM)newValue);
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
		return IrPackage.Literals.ACTOR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.ACTOR__ACTIONS:
				getActions().clear();
				return;
			case IrPackage.ACTOR__FILE:
				setFile(FILE_EDEFAULT);
				return;
			case IrPackage.ACTOR__INITIALIZES:
				getInitializes().clear();
				return;
			case IrPackage.ACTOR__INPUTS:
				getInputs().clear();
				return;
			case IrPackage.ACTOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IrPackage.ACTOR__OUTPUTS:
				getOutputs().clear();
				return;
			case IrPackage.ACTOR__PARAMETERS:
				getParameters().clear();
				return;
			case IrPackage.ACTOR__PROCS:
				getProcs().clear();
				return;
			case IrPackage.ACTOR__STATE_VARS:
				getStateVars().clear();
				return;
			case IrPackage.ACTOR__NATIVE:
				setNative(NATIVE_EDEFAULT);
				return;
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				getActionsOutsideFsm().clear();
				return;
			case IrPackage.ACTOR__FSM:
				setFsm((FSM)null);
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
			actions = new EObjectContainmentEList<Action>(Action.class, this, IrPackage.ACTOR__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Action> getActionsOutsideFsm() {
		if (actionsOutsideFsm == null) {
			actionsOutsideFsm = new EObjectResolvingEList<Action>(Action.class, this, IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM);
		}
		return actionsOutsideFsm;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FSM getFsm() {
		return fsm;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Action> getInitializes() {
		if (initializes == null) {
			initializes = new EObjectContainmentEList<Action>(Action.class, this, IrPackage.ACTOR__INITIALIZES);
		}
		return initializes;
	}

	@Override
	public Port getInput(String name) {
		return getInputs().get(mapInputs.get(name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getInputs() {
		if (inputs == null) {
			inputs = new EObjectContainmentEList<Port>(Port.class, this, IrPackage.ACTOR__INPUTS);
		}
		return inputs;
	}

	public Map<String, Integer> getInputsMap() {
		return mapInputs;
	}

	@Override
	public MoC getMoC() {
		return moc;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	@Override
	public Port getOutput(String name) {
		return getOutputs().get(mapOutputs.get(name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectContainmentEList<Port>(Port.class, this, IrPackage.ACTOR__OUTPUTS);
		}
		return outputs;
	}

	public Map<String, Integer> getOutputsMap() {
		return mapOutputs;
	}

	@Override
	public String getPackage() {
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return name.substring(0, index);
		}
	}

	@Override
	public List<String> getPackageAsList() {
		String[] segments = name.split("\\.");
		List<String> list = new ArrayList<String>(segments.length - 1);
		for (int i = 0; i < segments.length - 1; i++) {
			list.add(segments[i]);
		}
		return list;
	}

	@Override
	public Var getParameter(String name) {
		return getParameters().get(mapParameters.get(name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Var>(Var.class, this, IrPackage.ACTOR__PARAMETERS);
		}
		return parameters;
	}

	public Map<String, Integer> getParametersMap() {
		return mapParameters;
	}

	@Override
	public Port getPort(String name) {
		Port port = getInput(name);
		if (port != null) {
			return port;
		}

		return getOutput(name);
	}

	@Override
	public Procedure getProcedure(String name) {
		return getProcs().get(mapProcedures.get(name));
	}

	public Map<String, Integer> getProceduresMap() {
		return mapProcedures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Procedure> getProcs() {
		if (procs == null) {
			procs = new EObjectContainmentEList<Procedure>(Procedure.class, this, IrPackage.ACTOR__PROCS);
		}
		return procs;
	}

	@Override
	public String getSimpleName() {
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return name;
		} else {
			return name.substring(index + 1);
		}
	}

	@Override
	public Var getStateVar(String name) {
		return getStateVars().get(mapStateVars.get(name));
	}

	public Map<String, Integer> getStateVariablesMap() {
		return mapStateVars;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getStateVars() {
		if (stateVars == null) {
			stateVars = new EObjectContainmentEList<Var>(Var.class, this, IrPackage.ACTOR__STATE_VARS);
		}
		return stateVars;
	}

	/**
	 * Returns an object with template-specific data.
	 * 
	 * @return an object with template-specific data
	 */
	public Object getTemplateData() {
		return templateData;
	}

	@Override
	public boolean hasFsm() {
		return fsm != null;
	}

	@Override
	public boolean hasMoC() {
		return moc != null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNative() {
		return native_;
	}

	@Override
	public void resetTokenConsumption() {
		for (Port port : inputs) {
			port.resetTokenConsumption();
		}
	}

	@Override
	public void resetTokenProduction() {
		for (Port port : outputs) {
			port.resetTokenProduction();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setFile(String newFile) {
		String oldFile = file;
		file = newFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__FILE, oldFile, file));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setFsm(FSM newFsm) {
		if (newFsm != fsm) {
			NotificationChain msgs = null;
			if (fsm != null)
				msgs = ((InternalEObject)fsm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTOR__FSM, null, msgs);
			if (newFsm != null)
				msgs = ((InternalEObject)newFsm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTOR__FSM, null, msgs);
			msgs = basicSetFsm(newFsm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__FSM, newFsm, newFsm));
	}

	@Override
	public void setMoC(MoC moc) {
		this.moc = moc;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNative(boolean newNative) {
		boolean oldNative = native_;
		native_ = newNative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__NATIVE, oldNative, native_));
	}

	@Override
	public void setTemplateData(Object templateData) {
		this.templateData = templateData;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (file: ");
		result.append(file);
		result.append(", name: ");
		result.append(name);
		result.append(", native: ");
		result.append(native_);
		result.append(')');
		return result.toString();
	}

} // ActorImpl
