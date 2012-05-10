/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.Collection;

import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Unit;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.impl.AttributableImpl;

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
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Unit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.UnitImpl#getConstants <em>Constants</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.UnitImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.UnitImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.UnitImpl#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.impl.UnitImpl#getProcedures <em>Procedures</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UnitImpl extends AttributableImpl implements Unit {
	/**
	 * The cached value of the '{@link #getConstants() <em>Constants</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConstants()
	 * @generated
	 * @ordered
	 */
	protected EList<Var> constants;

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
	 * The cached value of the '{@link #getProcedures() <em>Procedures</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProcedures()
	 * @generated
	 * @ordered
	 */
	protected EList<Procedure> procedures;

	/**
	 * holds template-specific data.
	 */
	private Object templateData;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected UnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.UNIT__CONSTANTS:
			return getConstants();
		case DfPackage.UNIT__FILE_NAME:
			return getFileName();
		case DfPackage.UNIT__LINE_NUMBER:
			return getLineNumber();
		case DfPackage.UNIT__NAME:
			return getName();
		case DfPackage.UNIT__PROCEDURES:
			return getProcedures();
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
		case DfPackage.UNIT__CONSTANTS:
			return ((InternalEList<?>) getConstants()).basicRemove(otherEnd,
					msgs);
		case DfPackage.UNIT__PROCEDURES:
			return ((InternalEList<?>) getProcedures()).basicRemove(otherEnd,
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
		case DfPackage.UNIT__CONSTANTS:
			return constants != null && !constants.isEmpty();
		case DfPackage.UNIT__FILE_NAME:
			return FILE_NAME_EDEFAULT == null ? fileName != null
					: !FILE_NAME_EDEFAULT.equals(fileName);
		case DfPackage.UNIT__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case DfPackage.UNIT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case DfPackage.UNIT__PROCEDURES:
			return procedures != null && !procedures.isEmpty();
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
		case DfPackage.UNIT__CONSTANTS:
			getConstants().clear();
			getConstants().addAll((Collection<? extends Var>) newValue);
			return;
		case DfPackage.UNIT__FILE_NAME:
			setFileName((String) newValue);
			return;
		case DfPackage.UNIT__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case DfPackage.UNIT__NAME:
			setName((String) newValue);
			return;
		case DfPackage.UNIT__PROCEDURES:
			getProcedures().clear();
			getProcedures().addAll((Collection<? extends Procedure>) newValue);
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
		return DfPackage.Literals.UNIT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DfPackage.UNIT__CONSTANTS:
			getConstants().clear();
			return;
		case DfPackage.UNIT__FILE_NAME:
			setFileName(FILE_NAME_EDEFAULT);
			return;
		case DfPackage.UNIT__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case DfPackage.UNIT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DfPackage.UNIT__PROCEDURES:
			getProcedures().clear();
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Var getConstant(String name) {
		for (Var var : getConstants()) {
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
	public EList<Var> getConstants() {
		if (constants == null) {
			constants = new EObjectContainmentEList<Var>(Var.class, this,
					DfPackage.UNIT__CONSTANTS);
		}
		return constants;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFileName() {
		return fileName;
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
	public String getName() {
		return name;
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
	public String getSimpleName() {
		int index = name.lastIndexOf('.');
		String simpleName = name;
		if (index != -1) {
			simpleName = name.substring(index + 1);
		}
		return simpleName;
	}

	@Override
	public Procedure getProcedure(String name) {
		for (Procedure procedure : getProcedures()) {
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
	public EList<Procedure> getProcedures() {
		if (procedures == null) {
			procedures = new EObjectContainmentEList<Procedure>(
					Procedure.class, this, DfPackage.UNIT__PROCEDURES);
		}
		return procedures;
	}

	@Override
	public Object getTemplateData() {
		return templateData;
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					DfPackage.UNIT__FILE_NAME, oldFileName, fileName));
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
					DfPackage.UNIT__LINE_NUMBER, oldLineNumber, lineNumber));
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
					DfPackage.UNIT__NAME, oldName, name));
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
		result.append(" (fileName: ");
		result.append(fileName);
		result.append(", lineNumber: ");
		result.append(lineNumber);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // UnitImpl
