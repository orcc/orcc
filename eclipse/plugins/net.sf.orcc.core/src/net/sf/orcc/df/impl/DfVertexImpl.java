/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.impl.VertexImpl;
import net.sf.dftools.util.Nameable;
import net.sf.dftools.util.UtilPackage;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.DfVertex;
import org.eclipse.emf.common.notify.Notification;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Port;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.DfVertexImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DfVertexImpl extends VertexImpl implements DfVertex {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * holds template-specific data.
	 */
	private Object templateData;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DfVertexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Nameable.class) {
			switch (derivedFeatureID) {
			case DfPackage.DF_VERTEX__NAME:
				return UtilPackage.NAMEABLE__NAME;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Nameable.class) {
			switch (baseFeatureID) {
			case UtilPackage.NAMEABLE__NAME:
				return DfPackage.DF_VERTEX__NAME;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.DF_VERTEX__NAME:
			return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DfPackage.DF_VERTEX__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DfPackage.DF_VERTEX__NAME:
			setName((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.DF_VERTEX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DfPackage.DF_VERTEX__NAME:
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	public String getPackage() {
		if (name == null) {
			return null;
		}
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return name.substring(0, index);
		}
	}

	@Override
	public List<String> getPackageAsList() {
		String[] segments = name == null ? new String[0] : name.split("\\.");
		List<String> list = new ArrayList<String>(segments.length - 1);
		for (int i = 0; i < segments.length - 1; i++) {
			list.add(segments[i]);
		}
		return list;
	}

	@Override
	public String getSimpleName() {
		if (name == null) {
			return null;
		}
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return name;
		} else {
			return name.substring(index + 1);
		}
	}

	@Override
	public Object getTemplateData() {
		return templateData;
	}

	@Override
	public boolean isEntity() {
		return false;
	}

	@Override
	public boolean isInstance() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.DF_VERTEX__NAME, oldName, name));
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

} //DfVertexImpl
