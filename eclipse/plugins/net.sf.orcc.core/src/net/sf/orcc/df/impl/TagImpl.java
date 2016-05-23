/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.Collection;

import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Tag;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Tag</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.df.impl.TagImpl#getIdentifiers <em>Identifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TagImpl extends EObjectImpl implements Tag {
	/**
	 * The cached value of the '{@link #getIdentifiers() <em>Identifiers</em>}' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIdentifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<String> identifiers;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TagImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.TAG;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getIdentifiers() {
		if (identifiers == null) {
			identifiers = new EDataTypeUniqueEList<String>(String.class, this, DfPackage.TAG__IDENTIFIERS);
		}
		return identifiers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DfPackage.TAG__IDENTIFIERS:
			return getIdentifiers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DfPackage.TAG__IDENTIFIERS:
			getIdentifiers().clear();
			getIdentifiers().addAll((Collection<? extends String>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DfPackage.TAG__IDENTIFIERS:
			getIdentifiers().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DfPackage.TAG__IDENTIFIERS:
			return identifiers != null && !identifiers.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(" (identifiers: ");
		result.append(identifiers);
		result.append(')');
		return result.toString();
	}

	/**
	 * Adds an identifier to this tag.
	 * 
	 * @param identifier
	 *            an identifier
	 */
	public void add(String identifier) {
		getIdentifiers().add(identifier);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			return getIdentifiers().equals(((Tag) obj).getIdentifiers());
		} else {
			return false;
		}
	}

	/**
	 * Returns the identifier at the given index.
	 * 
	 * @param index
	 *            index of the identifier to return
	 * @return the identifier at the given index
	 */
	public String get(int index) {
		return getIdentifiers().get(index);
	}

	@Override
	public int hashCode() {
		return getIdentifiers().hashCode();
	}

	/**
	 * Returns true if this tag is empty.
	 * 
	 * @return true if this tag is empty
	 */
	public boolean isEmpty() {
		return getIdentifiers().isEmpty();
	}

	/**
	 * Returns the number of identifiers in this tag.
	 * 
	 * @return the number of identifiers in this tag
	 */
	public int size() {
		return getIdentifiers().size();
	}

} // TagImpl
