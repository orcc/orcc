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
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getActionsOutsideFsm <em>Actions Outside Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getFsm <em>Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getInitializes <em>Initializes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getMoC <em>Mo C</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#isNative <em>Native</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getProcs <em>Procs</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ActorImpl#getStateVars <em>State Vars</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActorImpl extends EObjectImpl implements Actor {
	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> actions;

	private Map<String, Port> mapInputs;

	private Map<String, Port> mapOutputs;

	private Map<String, Var> mapParameters;

	private Map<String, Procedure> mapProcedures;

	private Map<String, Var> mapStateVars;

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
	 * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileName()
	 * @generated
	 * @ordered
	 */
	protected String fileName = FILE_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getMoC() <em>Mo C</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMoC()
	 * @generated
	 * @ordered
	 */
	protected MoC moC;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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

		mapInputs = new HashMap<String, Port>();
		mapOutputs = new HashMap<String, Port>();
		mapParameters = new HashMap<String, Var>();
		mapProcedures = new HashMap<String, Procedure>();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__FSM, oldFsm, newFsm);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMoC(MoC newMoC, NotificationChain msgs) {
		MoC oldMoC = moC;
		moC = newMoC;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__MO_C, oldMoC, newMoC);
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
			case IrPackage.ACTOR__ACTIONS:
				return getActions();
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				return getActionsOutsideFsm();
			case IrPackage.ACTOR__FILE_NAME:
				return getFileName();
			case IrPackage.ACTOR__FSM:
				return getFsm();
			case IrPackage.ACTOR__INITIALIZES:
				return getInitializes();
			case IrPackage.ACTOR__INPUTS:
				return getInputs();
			case IrPackage.ACTOR__LINE_NUMBER:
				return getLineNumber();
			case IrPackage.ACTOR__MO_C:
				return getMoC();
			case IrPackage.ACTOR__NAME:
				return getName();
			case IrPackage.ACTOR__NATIVE:
				return isNative();
			case IrPackage.ACTOR__OUTPUTS:
				return getOutputs();
			case IrPackage.ACTOR__PARAMETERS:
				return getParameters();
			case IrPackage.ACTOR__PROCS:
				return getProcs();
			case IrPackage.ACTOR__STATE_VARS:
				return getStateVars();
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
			case IrPackage.ACTOR__FSM:
				return basicSetFsm(null, msgs);
			case IrPackage.ACTOR__INITIALIZES:
				return ((InternalEList<?>)getInitializes()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__INPUTS:
				return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__MO_C:
				return basicSetMoC(null, msgs);
			case IrPackage.ACTOR__OUTPUTS:
				return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__PROCS:
				return ((InternalEList<?>)getProcs()).basicRemove(otherEnd, msgs);
			case IrPackage.ACTOR__STATE_VARS:
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
			case IrPackage.ACTOR__ACTIONS:
				return actions != null && !actions.isEmpty();
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				return actionsOutsideFsm != null && !actionsOutsideFsm.isEmpty();
			case IrPackage.ACTOR__FILE_NAME:
				return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
			case IrPackage.ACTOR__FSM:
				return fsm != null;
			case IrPackage.ACTOR__INITIALIZES:
				return initializes != null && !initializes.isEmpty();
			case IrPackage.ACTOR__INPUTS:
				return inputs != null && !inputs.isEmpty();
			case IrPackage.ACTOR__LINE_NUMBER:
				return lineNumber != LINE_NUMBER_EDEFAULT;
			case IrPackage.ACTOR__MO_C:
				return moC != null;
			case IrPackage.ACTOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IrPackage.ACTOR__NATIVE:
				return native_ != NATIVE_EDEFAULT;
			case IrPackage.ACTOR__OUTPUTS:
				return outputs != null && !outputs.isEmpty();
			case IrPackage.ACTOR__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case IrPackage.ACTOR__PROCS:
				return procs != null && !procs.isEmpty();
			case IrPackage.ACTOR__STATE_VARS:
				return stateVars != null && !stateVars.isEmpty();
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
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				getActionsOutsideFsm().clear();
				getActionsOutsideFsm().addAll((Collection<? extends Action>)newValue);
				return;
			case IrPackage.ACTOR__FILE_NAME:
				setFileName((String)newValue);
				return;
			case IrPackage.ACTOR__FSM:
				setFsm((FSM)newValue);
				return;
			case IrPackage.ACTOR__INITIALIZES:
				getInitializes().clear();
				getInitializes().addAll((Collection<? extends Action>)newValue);
				return;
			case IrPackage.ACTOR__INPUTS:
				getInputs().clear();
				getInputs().addAll((Collection<? extends Port>)newValue);
				return;
			case IrPackage.ACTOR__LINE_NUMBER:
				setLineNumber((Integer)newValue);
				return;
			case IrPackage.ACTOR__MO_C:
				setMoC((MoC)newValue);
				return;
			case IrPackage.ACTOR__NAME:
				setName((String)newValue);
				return;
			case IrPackage.ACTOR__NATIVE:
				setNative((Boolean)newValue);
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
			case IrPackage.ACTOR__ACTIONS_OUTSIDE_FSM:
				getActionsOutsideFsm().clear();
				return;
			case IrPackage.ACTOR__FILE_NAME:
				setFileName(FILE_NAME_EDEFAULT);
				return;
			case IrPackage.ACTOR__FSM:
				setFsm((FSM)null);
				return;
			case IrPackage.ACTOR__INITIALIZES:
				getInitializes().clear();
				return;
			case IrPackage.ACTOR__INPUTS:
				getInputs().clear();
				return;
			case IrPackage.ACTOR__LINE_NUMBER:
				setLineNumber(LINE_NUMBER_EDEFAULT);
				return;
			case IrPackage.ACTOR__MO_C:
				setMoC((MoC)null);
				return;
			case IrPackage.ACTOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IrPackage.ACTOR__NATIVE:
				setNative(NATIVE_EDEFAULT);
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

	@Override
	public IFile getFile() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getFile(new Path(getFileName()));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileName() {
		return fileName;
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
		return mapInputs.get(name);
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

	public Map<String, Port> getInputsMap() {
		return mapInputs;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MoC getMoC() {
		return moC;
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
		return mapOutputs.get(name);
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

	public Map<String, Port> getOutputsMap() {
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
		return mapParameters.get(name);
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

	public Map<String, Var> getParametersMap() {
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
		return mapProcedures.get(name);
	}

	public Map<String, Procedure> getProceduresMap() {
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
		return mapStateVars.get(name);
	}

	public Map<String, Var> getStateVariablesMap() {
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
		return moC != null;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileName(String newFileName) {
		String oldFileName = fileName;
		fileName = newFileName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__FILE_NAME, oldFileName, fileName));
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__LINE_NUMBER, oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMoC(MoC newMoC) {
		if (newMoC != moC) {
			NotificationChain msgs = null;
			if (moC != null)
				msgs = ((InternalEObject)moC).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTOR__MO_C, null, msgs);
			if (newMoC != null)
				msgs = ((InternalEObject)newMoC).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.ACTOR__MO_C, null, msgs);
			msgs = basicSetMoC(newMoC, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.ACTOR__MO_C, newMoC, newMoC));
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
		result.append(" (fileName: ");
		result.append(fileName);
		result.append(", lineNumber: ");
		result.append(lineNumber);
		result.append(", name: ");
		result.append(name);
		result.append(", native: ");
		result.append(native_);
		result.append(')');
		return result.toString();
	}

} // ActorImpl
