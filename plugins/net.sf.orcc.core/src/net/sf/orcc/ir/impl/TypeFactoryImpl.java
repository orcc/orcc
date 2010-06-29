/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.type.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TypeFactoryImpl extends EFactoryImpl implements TypeFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TypePackage getPackage() {
		return TypePackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static TypeFactory init() {
		try {
			TypeFactory theTypeFactory = (TypeFactory) EPackage.Registry.INSTANCE
					.getEFactory("http:///net/sf/orcc/ir/type.ecore");
			if (theTypeFactory != null) {
				return theTypeFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TypeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case TypePackage.BOOL_TYPE:
			return createTypeBool();
		case TypePackage.FLOAT_TYPE:
			return createTypeFloat();
		case TypePackage.INT_TYPE:
			return createTypeInt();
		case TypePackage.UINT_TYPE:
			return createTypeUint();
		case TypePackage.VOID_TYPE:
			return createTypeVoid();
		case TypePackage.STRING_TYPE:
			return createTypeString();
		case TypePackage.LIST_TYPE:
			return createTypeList();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeBool createTypeBool() {
		TypeBoolImpl boolType = new TypeBoolImpl();
		return boolType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeFloat createTypeFloat() {
		TypeFloatImpl floatType = new TypeFloatImpl();
		return floatType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeInt createTypeInt() {
		TypeIntImpl intType = new TypeIntImpl();
		return intType;
	}

	@Override
	public TypeInt createTypeInt(int size) {
		TypeIntImpl intType = new TypeIntImpl();
		intType.setSize(size);
		return intType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeList createTypeList() {
		TypeListImpl listType = new TypeListImpl();
		return listType;
	}

	@Override
	public TypeList createTypeList(int size, Type type) {
		TypeListImpl listType = new TypeListImpl();
		listType.setSize(size);
		listType.setType(type);
		return listType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeString createTypeString() {
		TypeStringImpl stringType = new TypeStringImpl();
		return stringType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeUint createTypeUint() {
		TypeUintImpl uintType = new TypeUintImpl();
		return uintType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeVoid createTypeVoid() {
		TypeVoidImpl voidType = new TypeVoidImpl();
		return voidType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypePackage getTypePackage() {
		return (TypePackage) getEPackage();
	}

} // TypeFactoryImpl
