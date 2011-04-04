/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.CFG;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrderedMap;

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
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ProcedureImpl#isNative <em>Native</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcedureImpl extends EObjectImpl implements Procedure {
	/**
	 * This class visits the procedure to find the state variables used.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ProcVisitor extends AbstractActorVisitor {

		private Set<Var> loadedVariables;

		private Set<Var> storedVariables;

		public ProcVisitor() {
			storedVariables = new HashSet<Var>();
			loadedVariables = new HashSet<Var>();
		}

		public List<Var> getLoadedVariables() {
			return new ArrayList<Var>(loadedVariables);
		}

		public List<Var> getStoredVariables() {
			return new ArrayList<Var>(storedVariables);
		}

		@Override
		public void visit(InstLoad node) {
			Var var = node.getSource().getVariable();
			if (!var.getType().isList()) {
				loadedVariables.add((Var) var);
			}
		}

		@Override
		public void visit(InstStore store) {
			Var var = store.getTarget();
			if (!var.getType().isList()) {
				storedVariables.add((Var) var);
			}
		}

	}

	private CFG graph;

	/**
	 * ordered map of local variables
	 */
	private OrderedMap<String, Var> locals;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected Location location;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes;

	/**
	 * ordered map of parameters
	 */
	private OrderedMap<String, Var> parameters;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcedureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.PROCEDURE__LOCATION:
				return getLocation();
			case IrPackage.PROCEDURE__NAME:
				if (resolve) return getName();
				return basicGetName();
			case IrPackage.PROCEDURE__NODES:
				return getNodes();
			case IrPackage.PROCEDURE__RETURN_TYPE:
				return getReturnType();
			case IrPackage.PROCEDURE__NATIVE:
				return isNative();
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
			case IrPackage.PROCEDURE__LOCATION:
				return location != null;
			case IrPackage.PROCEDURE__NAME:
				return name != null;
			case IrPackage.PROCEDURE__NODES:
				return nodes != null && !nodes.isEmpty();
			case IrPackage.PROCEDURE__RETURN_TYPE:
				return returnType != null;
			case IrPackage.PROCEDURE__NATIVE:
				return native_ != NATIVE_EDEFAULT;
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
			case IrPackage.PROCEDURE__LOCATION:
				setLocation((Location)newValue);
				return;
			case IrPackage.PROCEDURE__NAME:
				setName((String)newValue);
				return;
			case IrPackage.PROCEDURE__NODES:
				getNodes().clear();
				getNodes().addAll((Collection<? extends Node>)newValue);
				return;
			case IrPackage.PROCEDURE__RETURN_TYPE:
				setReturnType((Type)newValue);
				return;
			case IrPackage.PROCEDURE__NATIVE:
				setNative((Boolean)newValue);
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
			case IrPackage.PROCEDURE__LOCATION:
				setLocation((Location)null);
				return;
			case IrPackage.PROCEDURE__NAME:
				setName((String)null);
				return;
			case IrPackage.PROCEDURE__NODES:
				getNodes().clear();
				return;
			case IrPackage.PROCEDURE__RETURN_TYPE:
				setReturnType((Type)null);
				return;
			case IrPackage.PROCEDURE__NATIVE:
				setNative(NATIVE_EDEFAULT);
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
	 * Returns the first block in the list of nodes of the given procedure. A
	 * new block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	public NodeBlock getFirst() {
		return getFirst(getNodes());
	}

	/**
	 * Returns the first block in the given list of nodes. A new block is
	 * created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes of the given procedure
	 * @return a block
	 */
	public NodeBlock getFirst(List<Node> nodes) {
		NodeBlock block;
		if (nodes.isEmpty()) {
			block = IrFactoryImpl.eINSTANCE.createNodeBlock();
			nodes.add(block);
		} else {
			Node node = nodes.get(0);
			if (node.isBlockNode()) {
				block = (NodeBlock) node;
			} else {
				block = IrFactoryImpl.eINSTANCE.createNodeBlock();
				nodes.add(0, block);
			}
		}

		return block;
	}

	/**
	 * Returns the last block in the list of nodes of the given procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	public NodeBlock getLast() {
		return getLast(getNodes());
	}

	/**
	 * Returns the last block in the given list of nodes. A new block is created
	 * if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes that are a subset of the given procedure's
	 *            nodes
	 * @return a block
	 */
	public NodeBlock getLast(List<Node> nodes) {
		NodeBlock block;
		if (nodes.isEmpty()) {
			block = IrFactoryImpl.eINSTANCE.createNodeBlock();
			nodes.add(block);
		} else {
			Node node = nodes.get(nodes.size() - 1);
			if (node.isBlockNode()) {
				block = (NodeBlock) node;
			} else {
				block = IrFactoryImpl.eINSTANCE.createNodeBlock();
				nodes.add(block);
			}
		}

		return block;
	}

	/**
	 * Computes and returns the list of scalar variables loaded by this
	 * procedure.
	 * 
	 * @return the list of scalar variables loaded by this procedure
	 */
	public List<Var> getLoadedVariables() {
		ProcVisitor visitor = new ProcVisitor();
		visitor.visit(nodes);
		return visitor.getLoadedVariables();
	}

	/**
	 * Returns the local variables of this procedure as an ordered map.
	 * 
	 * @return the local variables of this procedure as an ordered map
	 */
	public OrderedMap<String, Var> getLocals() {
		return locals;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocation(Location newLocation,
			NotificationChain msgs) {
		Location oldLocation = location;
		location = newLocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__LOCATION, oldLocation, newLocation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String basicGetName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<Node>(Node.class, this, IrPackage.PROCEDURE__NODES);
		}
		return nodes;
	}

	/**
	 * Returns the parameters of this procedure as an ordered map.
	 * 
	 * @return the parameters of this procedure as an ordered map
	 */
	public OrderedMap<String, Var> getParameters() {
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
	public NotificationChain basicSetReturnType(Type newReturnType,
			NotificationChain msgs) {
		Type oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__RETURN_TYPE, oldReturnType, newReturnType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Computes and returns the list of scalar variables stored by this
	 * procedure.
	 * 
	 * @return the list of scalar variables stored by this procedure
	 */
	public List<Var> getStoredVariables() {
		ProcVisitor visitor = new ProcVisitor();
		visitor.visit(nodes);
		return visitor.getStoredVariables();
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
	 * @param file
	 *            the file in which this procedure resides
	 * @param type
	 *            type of the variable
	 * @param name
	 *            hint for the variable name
	 * @return a new local variable
	 */
	public Var newTempLocalVariable(String file, Type type, String hint) {
		String name = hint;
		Var variable = locals.get(name);
		int i = 0;
		while (variable != null) {
			name = hint + i;
			variable = locals.get(name);
			i++;
		}

		variable = IrFactory.eINSTANCE.createVar(
				IrFactory.eINSTANCE.createLocation(), type, name, true, 0);
		locals.put(file, variable.getLocation(), variable.getName(), variable);
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

	@Override
	public void setLocals(OrderedMap<String, Var> locals) {
		this.locals = locals;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(Location newLocation) {
		if (newLocation != location) {
			NotificationChain msgs = null;
			if (location != null)
				msgs = ((InternalEObject)location).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__LOCATION, null, msgs);
			if (newLocation != null)
				msgs = ((InternalEObject)newLocation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__LOCATION, null, msgs);
			msgs = basicSetLocation(newLocation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__LOCATION, newLocation, newLocation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNative(boolean newNative) {
		boolean oldNative = native_;
		native_ = newNative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__NATIVE, oldNative, native_));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.PROCEDURE__LOCATION:
				return basicSetLocation(null, msgs);
			case IrPackage.PROCEDURE__NODES:
				return ((InternalEList<?>)getNodes()).basicRemove(otherEnd, msgs);
			case IrPackage.PROCEDURE__RETURN_TYPE:
				return basicSetReturnType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public void setParameters(OrderedMap<String, Var> parameters) {
		this.parameters = parameters;
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
				msgs = ((InternalEObject)returnType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__RETURN_TYPE, null, msgs);
			if (newReturnType != null)
				msgs = ((InternalEObject)newReturnType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.PROCEDURE__RETURN_TYPE, null, msgs);
			msgs = basicSetReturnType(newReturnType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.PROCEDURE__RETURN_TYPE, newReturnType, newReturnType));
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
		result.append(')');
		return result.toString();
	}

} // ProcedureImpl
