/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.orcc.ir.IrFactory
 * @model kind="package"
 * @generated
 */
public interface IrPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ir";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///net/sf/orcc/ir.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.ir";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IrPackage eINSTANCE = net.sf.orcc.ir.impl.IrPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 0;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__DIMENSIONS = 0;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__BOOL = 1;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__FLOAT = 2;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__INT = 3;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__LIST = 4;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__STRING = 5;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__UINT = 6;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__VOID = 7;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 8;


	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeBoolImpl <em>Type Bool</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeBoolImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeBool()
	 * @generated
	 */
	int TYPE_BOOL = 1;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__DIMENSIONS = TYPE__DIMENSIONS;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__BOOL = TYPE__BOOL;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__FLOAT = TYPE__FLOAT;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__INT = TYPE__INT;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__LIST = TYPE__LIST;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__STRING = TYPE__STRING;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__UINT = TYPE__UINT;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL__VOID = TYPE__VOID;

	/**
	 * The number of structural features of the '<em>Type Bool</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_BOOL_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeFloatImpl <em>Type Float</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeFloatImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeFloat()
	 * @generated
	 */
	int TYPE_FLOAT = 2;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__DIMENSIONS = TYPE__DIMENSIONS;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__BOOL = TYPE__BOOL;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__FLOAT = TYPE__FLOAT;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__INT = TYPE__INT;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__LIST = TYPE__LIST;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__STRING = TYPE__STRING;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__UINT = TYPE__UINT;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT__VOID = TYPE__VOID;

	/**
	 * The number of structural features of the '<em>Type Float</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FLOAT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeIntImpl <em>Type Int</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeIntImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeInt()
	 * @generated
	 */
	int TYPE_INT = 3;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__DIMENSIONS = TYPE__DIMENSIONS;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__BOOL = TYPE__BOOL;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__FLOAT = TYPE__FLOAT;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__INT = TYPE__INT;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__LIST = TYPE__LIST;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__STRING = TYPE__STRING;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__UINT = TYPE__UINT;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__VOID = TYPE__VOID;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Long</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__LONG = TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Int</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeListImpl <em>Type List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeListImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeList()
	 * @generated
	 */
	int TYPE_LIST = 4;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__DIMENSIONS = TYPE__DIMENSIONS;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__BOOL = TYPE__BOOL;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__FLOAT = TYPE__FLOAT;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__INT = TYPE__INT;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__LIST = TYPE__LIST;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__STRING = TYPE__STRING;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__UINT = TYPE__UINT;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__VOID = TYPE__VOID;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__ELEMENT_TYPE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__SIZE = TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Size Iterator</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__SIZE_ITERATOR = TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__TYPE = TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Type List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST_FEATURE_COUNT = TYPE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeStringImpl <em>Type String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeStringImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeString()
	 * @generated
	 */
	int TYPE_STRING = 5;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__DIMENSIONS = TYPE__DIMENSIONS;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__BOOL = TYPE__BOOL;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__FLOAT = TYPE__FLOAT;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__INT = TYPE__INT;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__LIST = TYPE__LIST;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__STRING = TYPE__STRING;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__UINT = TYPE__UINT;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__VOID = TYPE__VOID;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_STRING_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeUintImpl <em>Type Uint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeUintImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeUint()
	 * @generated
	 */
	int TYPE_UINT = 6;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__DIMENSIONS = TYPE__DIMENSIONS;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__BOOL = TYPE__BOOL;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__FLOAT = TYPE__FLOAT;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__INT = TYPE__INT;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__LIST = TYPE__LIST;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__STRING = TYPE__STRING;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__UINT = TYPE__UINT;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__VOID = TYPE__VOID;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Long</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__LONG = TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Uint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeVoidImpl <em>Type Void</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeVoidImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeVoid()
	 * @generated
	 */
	int TYPE_VOID = 7;

	/**
	 * The feature id for the '<em><b>Dimensions</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__DIMENSIONS = TYPE__DIMENSIONS;

	/**
	 * The feature id for the '<em><b>Bool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__BOOL = TYPE__BOOL;

	/**
	 * The feature id for the '<em><b>Float</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__FLOAT = TYPE__FLOAT;

	/**
	 * The feature id for the '<em><b>Int</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__INT = TYPE__INT;

	/**
	 * The feature id for the '<em><b>List</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__LIST = TYPE__LIST;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__STRING = TYPE__STRING;

	/**
	 * The feature id for the '<em><b>Uint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__UINT = TYPE__UINT;

	/**
	 * The feature id for the '<em><b>Void</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID__VOID = TYPE__VOID;

	/**
	 * The number of structural features of the '<em>Type Void</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see net.sf.orcc.ir.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the attribute list '{@link net.sf.orcc.ir.Type#getDimensions <em>Dimensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Dimensions</em>'.
	 * @see net.sf.orcc.ir.Type#getDimensions()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Dimensions();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isBool <em>Bool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bool</em>'.
	 * @see net.sf.orcc.ir.Type#isBool()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Bool();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isFloat <em>Float</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float</em>'.
	 * @see net.sf.orcc.ir.Type#isFloat()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Float();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isInt <em>Int</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int</em>'.
	 * @see net.sf.orcc.ir.Type#isInt()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Int();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isList <em>List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>List</em>'.
	 * @see net.sf.orcc.ir.Type#isList()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_List();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isString <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String</em>'.
	 * @see net.sf.orcc.ir.Type#isString()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_String();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isUint <em>Uint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uint</em>'.
	 * @see net.sf.orcc.ir.Type#isUint()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Uint();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Type#isVoid <em>Void</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Void</em>'.
	 * @see net.sf.orcc.ir.Type#isVoid()
	 * @see #getType()
	 * @generated
	 */
	EAttribute getType_Void();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeBool <em>Type Bool</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Bool</em>'.
	 * @see net.sf.orcc.ir.TypeBool
	 * @generated
	 */
	EClass getTypeBool();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeFloat <em>Type Float</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Float</em>'.
	 * @see net.sf.orcc.ir.TypeFloat
	 * @generated
	 */
	EClass getTypeFloat();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeInt <em>Type Int</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Int</em>'.
	 * @see net.sf.orcc.ir.TypeInt
	 * @generated
	 */
	EClass getTypeInt();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.TypeInt#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeInt#getSize()
	 * @see #getTypeInt()
	 * @generated
	 */
	EAttribute getTypeInt_Size();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.TypeInt#isLong <em>Long</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long</em>'.
	 * @see net.sf.orcc.ir.TypeInt#isLong()
	 * @see #getTypeInt()
	 * @generated
	 */
	EAttribute getTypeInt_Long();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeList <em>Type List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type List</em>'.
	 * @see net.sf.orcc.ir.TypeList
	 * @generated
	 */
	EClass getTypeList();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.TypeList#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element Type</em>'.
	 * @see net.sf.orcc.ir.TypeList#getElementType()
	 * @see #getTypeList()
	 * @generated
	 */
	EReference getTypeList_ElementType();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.TypeList#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeList#getSize()
	 * @see #getTypeList()
	 * @generated
	 */
	EAttribute getTypeList_Size();

	/**
	 * Returns the meta object for the attribute list '{@link net.sf.orcc.ir.TypeList#getSizeIterator <em>Size Iterator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Size Iterator</em>'.
	 * @see net.sf.orcc.ir.TypeList#getSizeIterator()
	 * @see #getTypeList()
	 * @generated
	 */
	EAttribute getTypeList_SizeIterator();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.TypeList#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see net.sf.orcc.ir.TypeList#getType()
	 * @see #getTypeList()
	 * @generated
	 */
	EReference getTypeList_Type();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeString <em>Type String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type String</em>'.
	 * @see net.sf.orcc.ir.TypeString
	 * @generated
	 */
	EClass getTypeString();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.TypeString#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeString#getSize()
	 * @see #getTypeString()
	 * @generated
	 */
	EAttribute getTypeString_Size();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeUint <em>Type Uint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Uint</em>'.
	 * @see net.sf.orcc.ir.TypeUint
	 * @generated
	 */
	EClass getTypeUint();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.TypeUint#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.ir.TypeUint#getSize()
	 * @see #getTypeUint()
	 * @generated
	 */
	EAttribute getTypeUint_Size();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.TypeUint#isLong <em>Long</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long</em>'.
	 * @see net.sf.orcc.ir.TypeUint#isLong()
	 * @see #getTypeUint()
	 * @generated
	 */
	EAttribute getTypeUint_Long();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeVoid <em>Type Void</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Void</em>'.
	 * @see net.sf.orcc.ir.TypeVoid
	 * @generated
	 */
	EClass getTypeVoid();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IrFactory getIrFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Dimensions</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__DIMENSIONS = eINSTANCE.getType_Dimensions();

		/**
		 * The meta object literal for the '<em><b>Bool</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__BOOL = eINSTANCE.getType_Bool();

		/**
		 * The meta object literal for the '<em><b>Float</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__FLOAT = eINSTANCE.getType_Float();

		/**
		 * The meta object literal for the '<em><b>Int</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__INT = eINSTANCE.getType_Int();

		/**
		 * The meta object literal for the '<em><b>List</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__LIST = eINSTANCE.getType_List();

		/**
		 * The meta object literal for the '<em><b>String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__STRING = eINSTANCE.getType_String();

		/**
		 * The meta object literal for the '<em><b>Uint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__UINT = eINSTANCE.getType_Uint();

		/**
		 * The meta object literal for the '<em><b>Void</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE__VOID = eINSTANCE.getType_Void();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeBoolImpl <em>Type Bool</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeBoolImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeBool()
		 * @generated
		 */
		EClass TYPE_BOOL = eINSTANCE.getTypeBool();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeFloatImpl <em>Type Float</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeFloatImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeFloat()
		 * @generated
		 */
		EClass TYPE_FLOAT = eINSTANCE.getTypeFloat();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeIntImpl <em>Type Int</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeIntImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeInt()
		 * @generated
		 */
		EClass TYPE_INT = eINSTANCE.getTypeInt();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_INT__SIZE = eINSTANCE.getTypeInt_Size();

		/**
		 * The meta object literal for the '<em><b>Long</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_INT__LONG = eINSTANCE.getTypeInt_Long();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeListImpl <em>Type List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeListImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeList()
		 * @generated
		 */
		EClass TYPE_LIST = eINSTANCE.getTypeList();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_LIST__ELEMENT_TYPE = eINSTANCE.getTypeList_ElementType();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_LIST__SIZE = eINSTANCE.getTypeList_Size();

		/**
		 * The meta object literal for the '<em><b>Size Iterator</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_LIST__SIZE_ITERATOR = eINSTANCE.getTypeList_SizeIterator();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_LIST__TYPE = eINSTANCE.getTypeList_Type();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeStringImpl <em>Type String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeStringImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeString()
		 * @generated
		 */
		EClass TYPE_STRING = eINSTANCE.getTypeString();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_STRING__SIZE = eINSTANCE.getTypeString_Size();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeUintImpl <em>Type Uint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeUintImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeUint()
		 * @generated
		 */
		EClass TYPE_UINT = eINSTANCE.getTypeUint();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_UINT__SIZE = eINSTANCE.getTypeUint_Size();

		/**
		 * The meta object literal for the '<em><b>Long</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_UINT__LONG = eINSTANCE.getTypeUint_Long();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeVoidImpl <em>Type Void</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeVoidImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeVoid()
		 * @generated
		 */
		EClass TYPE_VOID = eINSTANCE.getTypeVoid();

	}

} //IrPackage
