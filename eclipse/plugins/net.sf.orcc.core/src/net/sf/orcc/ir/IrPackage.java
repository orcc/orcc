/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ExpressionImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 0;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 1;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = 0;


	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeBoolImpl <em>Type Bool</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeBoolImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeBool()
	 * @generated
	 */
	int TYPE_BOOL = 2;

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
	int TYPE_FLOAT = 3;

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
	int TYPE_INT = 4;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Int</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_INT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeListImpl <em>Type List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeListImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeList()
	 * @generated
	 */
	int TYPE_LIST = 5;

	/**
	 * The feature id for the '<em><b>Size Expr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__SIZE_EXPR = TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST__TYPE = TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_LIST_FEATURE_COUNT = TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeStringImpl <em>Type String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeStringImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeString()
	 * @generated
	 */
	int TYPE_STRING = 6;

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
	int TYPE_UINT = 7;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT__SIZE = TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Uint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_UINT_FEATURE_COUNT = TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.TypeVoidImpl <em>Type Void</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.TypeVoidImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeVoid()
	 * @generated
	 */
	int TYPE_VOID = 8;

	/**
	 * The number of structural features of the '<em>Type Void</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VOID_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.UserImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getUser()
	 * @generated
	 */
	int USER = 10;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.InstructionImpl <em>Instruction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.InstructionImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstruction()
	 * @generated
	 */
	int INSTRUCTION = 9;

	/**
	 * The number of structural features of the '<em>Instruction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTRUCTION_FEATURE_COUNT = USER_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.NodeImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 15;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = USER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.NodeBlockImpl <em>Node Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.NodeBlockImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNodeBlock()
	 * @generated
	 */
	int NODE_BLOCK = 11;

	/**
	 * The feature id for the '<em><b>Instructions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_BLOCK__INSTRUCTIONS = NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_BLOCK_FEATURE_COUNT = NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.NodeIfImpl <em>Node If</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.NodeIfImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNodeIf()
	 * @generated
	 */
	int NODE_IF = 12;

	/**
	 * The feature id for the '<em><b>Else Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_IF__ELSE_NODES = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Join Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_IF__JOIN_NODE = NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Then Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_IF__THEN_NODES = NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Node If</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_IF_FEATURE_COUNT = NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.NodeWhileImpl <em>Node While</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.NodeWhileImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNodeWhile()
	 * @generated
	 */
	int NODE_WHILE = 13;

	/**
	 * The feature id for the '<em><b>Join Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_WHILE__JOIN_NODE = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_WHILE__NODES = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Node While</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_WHILE_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.ValueContainer <em>Value Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.ValueContainer
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getValueContainer()
	 * @generated
	 */
	int VALUE_CONTAINER = 14;

	/**
	 * The number of structural features of the '<em>Value Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_CONTAINER_FEATURE_COUNT = 0;


	/**
	 * The meta object id for the '{@link net.sf.orcc.ir.impl.ProcedureImpl <em>Procedure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.impl.ProcedureImpl
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getProcedure()
	 * @generated
	 */
	int PROCEDURE = 16;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__LOCATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__NODES = 2;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__RETURN_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Native</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE__NATIVE = 4;

	/**
	 * The number of structural features of the '<em>Procedure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCEDURE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '<em>Location</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.ir.Location
	 * @see net.sf.orcc.ir.impl.IrPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 17;


	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see net.sf.orcc.ir.Expression
	 * @generated
	 */
	EClass getExpression();

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
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeList <em>Type List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type List</em>'.
	 * @see net.sf.orcc.ir.TypeList
	 * @generated
	 */
	EClass getTypeList();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.ir.TypeList#getSizeExpr <em>Size Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Size Expr</em>'.
	 * @see net.sf.orcc.ir.TypeList#getSizeExpr()
	 * @see #getTypeList()
	 * @generated
	 */
	EReference getTypeList_SizeExpr();

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
	 * Returns the meta object for class '{@link net.sf.orcc.ir.TypeVoid <em>Type Void</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Void</em>'.
	 * @see net.sf.orcc.ir.TypeVoid
	 * @generated
	 */
	EClass getTypeVoid();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Instruction <em>Instruction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instruction</em>'.
	 * @see net.sf.orcc.ir.Instruction
	 * @generated
	 */
	EClass getInstruction();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see net.sf.orcc.ir.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.NodeBlock <em>Node Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Block</em>'.
	 * @see net.sf.orcc.ir.NodeBlock
	 * @generated
	 */
	EClass getNodeBlock();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.NodeBlock#getInstructions <em>Instructions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Instructions</em>'.
	 * @see net.sf.orcc.ir.NodeBlock#getInstructions()
	 * @see #getNodeBlock()
	 * @generated
	 */
	EReference getNodeBlock_Instructions();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.NodeIf <em>Node If</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node If</em>'.
	 * @see net.sf.orcc.ir.NodeIf
	 * @generated
	 */
	EClass getNodeIf();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.NodeIf#getElseNodes <em>Else Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Else Nodes</em>'.
	 * @see net.sf.orcc.ir.NodeIf#getElseNodes()
	 * @see #getNodeIf()
	 * @generated
	 */
	EReference getNodeIf_ElseNodes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.NodeIf#getJoinNode <em>Join Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Node</em>'.
	 * @see net.sf.orcc.ir.NodeIf#getJoinNode()
	 * @see #getNodeIf()
	 * @generated
	 */
	EReference getNodeIf_JoinNode();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.NodeIf#getThenNodes <em>Then Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Then Nodes</em>'.
	 * @see net.sf.orcc.ir.NodeIf#getThenNodes()
	 * @see #getNodeIf()
	 * @generated
	 */
	EReference getNodeIf_ThenNodes();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.NodeWhile <em>Node While</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node While</em>'.
	 * @see net.sf.orcc.ir.NodeWhile
	 * @generated
	 */
	EClass getNodeWhile();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.NodeWhile#getJoinNode <em>Join Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Join Node</em>'.
	 * @see net.sf.orcc.ir.NodeWhile#getJoinNode()
	 * @see #getNodeWhile()
	 * @generated
	 */
	EReference getNodeWhile_JoinNode();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.NodeWhile#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see net.sf.orcc.ir.NodeWhile#getNodes()
	 * @see #getNodeWhile()
	 * @generated
	 */
	EReference getNodeWhile_Nodes();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.ValueContainer <em>Value Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Container</em>'.
	 * @see net.sf.orcc.ir.ValueContainer
	 * @model instanceClass="net.sf.orcc.ir.ValueContainer"
	 * @generated
	 */
	EClass getValueContainer();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see net.sf.orcc.ir.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.ir.Procedure <em>Procedure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Procedure</em>'.
	 * @see net.sf.orcc.ir.Procedure
	 * @generated
	 */
	EClass getProcedure();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Procedure#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see net.sf.orcc.ir.Procedure#getLocation()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_Location();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Procedure#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.ir.Procedure#getName()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.ir.Procedure#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see net.sf.orcc.ir.Procedure#getNodes()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_Nodes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.ir.Procedure#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Return Type</em>'.
	 * @see net.sf.orcc.ir.Procedure#getReturnType()
	 * @see #getProcedure()
	 * @generated
	 */
	EReference getProcedure_ReturnType();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.ir.Procedure#isNative <em>Native</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Native</em>'.
	 * @see net.sf.orcc.ir.Procedure#isNative()
	 * @see #getProcedure()
	 * @generated
	 */
	EAttribute getProcedure_Native();

	/**
	 * Returns the meta object for data type '{@link net.sf.orcc.ir.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Location</em>'.
	 * @see net.sf.orcc.ir.Location
	 * @model instanceClass="net.sf.orcc.ir.Location"
	 * @generated
	 */
	EDataType getLocation();

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
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ExpressionImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

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
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeListImpl <em>Type List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeListImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeList()
		 * @generated
		 */
		EClass TYPE_LIST = eINSTANCE.getTypeList();

		/**
		 * The meta object literal for the '<em><b>Size Expr</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_LIST__SIZE_EXPR = eINSTANCE.getTypeList_SizeExpr();

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
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.TypeVoidImpl <em>Type Void</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.TypeVoidImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getTypeVoid()
		 * @generated
		 */
		EClass TYPE_VOID = eINSTANCE.getTypeVoid();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.InstructionImpl <em>Instruction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.InstructionImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getInstruction()
		 * @generated
		 */
		EClass INSTRUCTION = eINSTANCE.getInstruction();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.UserImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.NodeBlockImpl <em>Node Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.NodeBlockImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNodeBlock()
		 * @generated
		 */
		EClass NODE_BLOCK = eINSTANCE.getNodeBlock();

		/**
		 * The meta object literal for the '<em><b>Instructions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_BLOCK__INSTRUCTIONS = eINSTANCE.getNodeBlock_Instructions();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.NodeIfImpl <em>Node If</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.NodeIfImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNodeIf()
		 * @generated
		 */
		EClass NODE_IF = eINSTANCE.getNodeIf();

		/**
		 * The meta object literal for the '<em><b>Else Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_IF__ELSE_NODES = eINSTANCE.getNodeIf_ElseNodes();

		/**
		 * The meta object literal for the '<em><b>Join Node</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_IF__JOIN_NODE = eINSTANCE.getNodeIf_JoinNode();

		/**
		 * The meta object literal for the '<em><b>Then Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_IF__THEN_NODES = eINSTANCE.getNodeIf_ThenNodes();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.NodeWhileImpl <em>Node While</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.NodeWhileImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNodeWhile()
		 * @generated
		 */
		EClass NODE_WHILE = eINSTANCE.getNodeWhile();

		/**
		 * The meta object literal for the '<em><b>Join Node</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_WHILE__JOIN_NODE = eINSTANCE.getNodeWhile_JoinNode();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_WHILE__NODES = eINSTANCE.getNodeWhile_Nodes();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.ValueContainer <em>Value Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.ValueContainer
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getValueContainer()
		 * @generated
		 */
		EClass VALUE_CONTAINER = eINSTANCE.getValueContainer();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.NodeImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.ir.impl.ProcedureImpl <em>Procedure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.impl.ProcedureImpl
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getProcedure()
		 * @generated
		 */
		EClass PROCEDURE = eINSTANCE.getProcedure();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCEDURE__LOCATION = eINSTANCE.getProcedure_Location();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCEDURE__NAME = eINSTANCE.getProcedure_Name();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCEDURE__NODES = eINSTANCE.getProcedure_Nodes();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCEDURE__RETURN_TYPE = eINSTANCE.getProcedure_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Native</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCEDURE__NATIVE = eINSTANCE.getProcedure_Native();

		/**
		 * The meta object literal for the '<em>Location</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.ir.Location
		 * @see net.sf.orcc.ir.impl.IrPackageImpl#getLocation()
		 * @generated
		 */
		EDataType LOCATION = eINSTANCE.getLocation();


	}

} //IrPackage
