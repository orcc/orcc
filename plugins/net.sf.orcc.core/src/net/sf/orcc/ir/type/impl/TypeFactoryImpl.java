/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.type.impl;

import net.sf.orcc.ir.Type;
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
	public BoolType createTypeBool() {
		BoolTypeImpl boolType = new BoolTypeImpl();
		return boolType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FloatType createTypeFloat() {
		FloatTypeImpl floatType = new FloatTypeImpl();
		return floatType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IntType createTypeInt() {
		IntTypeImpl intType = new IntTypeImpl();
		return intType;
	}

	@Override
	public IntType createTypeInt(int size) {
		IntTypeImpl intType = new IntTypeImpl();
		intType.setSize(size);
		return intType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ListType createTypeList() {
		ListTypeImpl listType = new ListTypeImpl();
		return listType;
	}

	@Override
	public ListType createTypeList(int size, Type type) {
		ListTypeImpl listType = new ListTypeImpl();
		listType.setSize(size);
		listType.setType(type);
		return listType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StringType createTypeString() {
		StringTypeImpl stringType = new StringTypeImpl();
		return stringType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UintType createTypeUint() {
		UintTypeImpl uintType = new UintTypeImpl();
		return uintType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VoidType createTypeVoid() {
		VoidTypeImpl voidType = new VoidTypeImpl();
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
