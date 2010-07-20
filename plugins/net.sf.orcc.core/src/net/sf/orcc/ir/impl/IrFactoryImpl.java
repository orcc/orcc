/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IrFactoryImpl extends EFactoryImpl implements IrFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IrFactory init() {
		try {
			IrFactory theIrFactory = (IrFactory)EPackage.Registry.INSTANCE.getEFactory("http:///net/sf/orcc/ir.ecore"); 
			if (theIrFactory != null) {
				return theIrFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IrFactoryImpl();
	}

	@Override
	public TypeInt createTypeInt(int size) {
		TypeIntImpl intType = new TypeIntImpl();
		intType.setSize(size);
		return intType;
	}

	@Override
	public TypeList createTypeList(int size, Type type) {
		TypeListImpl listType = new TypeListImpl();
		listType.setSize(size);
		listType.setType(type);
		return listType;
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IrFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IrPackage.TYPE_BOOL: return createTypeBool();
			case IrPackage.TYPE_FLOAT: return createTypeFloat();
			case IrPackage.TYPE_INT: return createTypeInt();
			case IrPackage.TYPE_LIST: return createTypeList();
			case IrPackage.TYPE_STRING: return createTypeString();
			case IrPackage.TYPE_UINT: return createTypeUint();
			case IrPackage.TYPE_VOID: return createTypeVoid();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeBool createTypeBool() {
		TypeBoolImpl typeBool = new TypeBoolImpl();
		return typeBool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeFloat createTypeFloat() {
		TypeFloatImpl typeFloat = new TypeFloatImpl();
		return typeFloat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeInt createTypeInt() {
		TypeIntImpl typeInt = new TypeIntImpl();
		return typeInt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeList createTypeList() {
		TypeListImpl typeList = new TypeListImpl();
		return typeList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeString createTypeString() {
		TypeStringImpl typeString = new TypeStringImpl();
		return typeString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeUint createTypeUint() {
		TypeUintImpl typeUint = new TypeUintImpl();
		return typeUint;
	}

	@Override
	public TypeUint createTypeUint(int size) {
		TypeUintImpl typeUint = new TypeUintImpl();
		typeUint.setSize(size);
		return typeUint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeVoid createTypeVoid() {
		TypeVoidImpl typeVoid = new TypeVoidImpl();
		return typeVoid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IrPackage getIrPackage() {
		return (IrPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IrPackage getPackage() {
		return IrPackage.eINSTANCE;
	}

} //IrFactoryImpl
