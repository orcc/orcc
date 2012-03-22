/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.impl.VertexImpl;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Entity</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.EntityImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EntityImpl extends VertexImpl implements Entity {

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> parameters;

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
	 * @generated
	 */
	protected String name = NAME_EDEFAULT;

	private Object templateData;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.ENTITY__PARAMETERS:
			return getParameters();
		case DfPackage.ENTITY__NAME:
			return getName();
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
		case DfPackage.ENTITY__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd,
					msgs);
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
		case DfPackage.ENTITY__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case DfPackage.ENTITY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
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
		case DfPackage.ENTITY__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends Var>) newValue);
			return;
		case DfPackage.ENTITY__NAME:
			setName((String) newValue);
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
		return DfPackage.Literals.ENTITY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DfPackage.ENTITY__PARAMETERS:
			getParameters().clear();
			return;
		case DfPackage.ENTITY__NAME:
			setName(NAME_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public List<String> getHierarchicalId() {
		List<String> ids = new ArrayList<String>();
		for (Entity entity : getHierarchy()) {
			ids.add(entity.getName());
		}
		ids.add(getName());
		return ids;
	}

	@Override
	public String getHierarchicalName() {
		StringBuilder builder = new StringBuilder();
		for (Entity entity : getHierarchy()) {
			builder.append(entity.getName());
			builder.append('_');
		}
		builder.append(getName());
		return builder.toString();
	}

	@Override
	public List<Entity> getHierarchy() {
		List<Entity> entities = new ArrayList<Entity>();
		EObject obj = eContainer();
		while (obj != null) {
			entities.add(0, (Entity) obj);
			obj = obj.eContainer();
		}
		return entities;
	}

	@Override
	public Map<Port, Connection> getIncomingPortMap() {
		Map<Port, Connection> map = new HashMap<Port, Connection>();
		for (Edge edge : getIncoming()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				map.put(connection.getTargetPort(), connection);
			}
		}
		return map;
	}

	@Override
	public Port getInput(String name) {
		List<Port> inputs = EcoreHelper.getList(this, "inputs");
		for (Port port : inputs) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	public String getName() {
		return name;
	}

	@Override
	public Map<Port, List<Connection>> getOutgoingPortMap() {
		Map<Port, List<Connection>> map = new HashMap<Port, List<Connection>>();
		for (Edge edge : getOutgoing()) {
			if (edge instanceof Connection) {
				Connection connection = (Connection) edge;
				Port source = connection.getSourcePort();
				List<Connection> conns = map.get(source);
				if (conns == null) {
					conns = new ArrayList<Connection>(1);
					map.put(source, conns);
				}
				conns.add(connection);
			}
		}
		return map;
	}

	@Override
	public Port getOutput(String name) {
		List<Port> outputs = EcoreHelper.getList(this, "outputs");
		for (Port port : outputs) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
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
	public Var getParameter(String name) {
		for (Var var : getParameters()) {
			if (var.getName().equals(name)) {
				return var;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Var> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Var>(Var.class, this,
					DfPackage.ENTITY__PARAMETERS);
		}
		return parameters;
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
	public String getSimpleName() {
		int index = name.lastIndexOf('.');
		String simpleName = name;
		if (index != -1) {
			simpleName = name.substring(index + 1);
		}
		return simpleName;
	}

	@Override
	public Object getTemplateData() {
		return templateData;
	}

	@Override
	public boolean isActor() {
		return false;
	}

	@Override
	public boolean isBroadcast() {
		return false;
	}

	public boolean isEntity() {
		return true;
	}

	@Override
	public boolean isNetwork() {
		return false;
	}

	/**
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.ENTITY__NAME, oldName, name));
	}

	@Override
	public void setTemplateData(Object templateData) {
		this.templateData = templateData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // EntityImpl
