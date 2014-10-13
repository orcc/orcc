/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.impl.DfPackageImpl;
import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Cfg;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeBool;
import net.sf.orcc.ir.TypeFloat;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.TypeVoid;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.impl.MocPackageImpl;
import net.sf.orcc.util.UtilPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class IrPackageImpl extends EPackageImpl implements IrPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeBoolEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeFloatEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeIntEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeListEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeStringEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeUintEClass = null;
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeVoidEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instructionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass procedureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paramEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockBasicEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockIfEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockWhileEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instAssignEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instCallEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instLoadEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instPhiEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instReturnEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instStoreEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argByRefEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argByValEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass varEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass useEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprBinaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprBoolEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprFloatEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprIntEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprListEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprStringEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprUnaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprVarEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass defEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass predicateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cfgEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cfgNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum opBinaryEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum opUnaryEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see net.sf.orcc.ir.IrPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IrPackageImpl() {
		super(eNS_URI, IrFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link IrPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IrPackage init() {
		if (isInited)
			return (IrPackage) EPackage.Registry.INSTANCE
					.getEPackage(IrPackage.eNS_URI);

		// Obtain or create and register package
		IrPackageImpl theIrPackage = (IrPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof IrPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new IrPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		UtilPackage.eINSTANCE.eClass();
		GraphPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		DfPackageImpl theDfPackage = (DfPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(DfPackage.eNS_URI) instanceof DfPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(DfPackage.eNS_URI) : DfPackage.eINSTANCE);
		MocPackageImpl theMocPackage = (MocPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(MocPackage.eNS_URI) instanceof MocPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(MocPackage.eNS_URI) : MocPackage.eINSTANCE);

		// Create package meta-data objects
		theIrPackage.createPackageContents();
		theDfPackage.createPackageContents();
		theMocPackage.createPackageContents();

		// Initialize created meta-data
		theIrPackage.initializePackageContents();
		theDfPackage.initializePackageContents();
		theMocPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIrPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IrPackage.eNS_URI, theIrPackage);
		return theIrPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeBool() {
		return typeBoolEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeFloat() {
		return typeFloatEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeFloat_Size() {
		return (EAttribute) typeFloatEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeInt() {
		return typeIntEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeInt_Size() {
		return (EAttribute) typeIntEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeList() {
		return typeListEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeList_SizeExpr() {
		return (EReference) typeListEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTypeList_Type() {
		return (EReference) typeListEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeString() {
		return typeStringEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeString_Size() {
		return (EAttribute) typeStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeUint() {
		return typeUintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTypeUint_Size() {
		return (EAttribute) typeUintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeVoid() {
		return typeVoidEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstruction() {
		return instructionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstruction_Predicate() {
		return (EReference) instructionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInstruction_LineNumber() {
		return (EAttribute) instructionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPredicate() {
		return predicateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPredicate_Expressions() {
		return (EReference) predicateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCfg() {
		return cfgEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCfg_Entry() {
		return (EReference) cfgEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCfg_Exit() {
		return (EReference) cfgEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCfgNode() {
		return cfgNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCfgNode_Node() {
		return (EReference) cfgNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProcedure() {
		return procedureEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProcedure_Name() {
		return (EAttribute) procedureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProcedure_ReturnType() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProcedure_Cfg() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParam() {
		return paramEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParam_Variable() {
		return (EReference) paramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getParam_ByRef() {
		return (EAttribute) paramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlock() {
		return blockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlock_CfgNode() {
		return (EReference) blockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockBasic() {
		return blockBasicEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockBasic_Instructions() {
		return (EReference) blockBasicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockIf() {
		return blockIfEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockIf_Condition() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockIf_ElseBlocks() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockIf_JoinBlock() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockIf_LineNumber() {
		return (EAttribute) blockIfEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockIf_ThenBlocks() {
		return (EReference) blockIfEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockWhile() {
		return blockWhileEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockWhile_Condition() {
		return (EReference) blockWhileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockWhile_JoinBlock() {
		return (EReference) blockWhileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockWhile_LineNumber() {
		return (EAttribute) blockWhileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBlockWhile_Blocks() {
		return (EReference) blockWhileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProcedure_Native() {
		return (EAttribute) procedureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProcedure_Blocks() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProcedure_Locals() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProcedure_Parameters() {
		return (EReference) procedureEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProcedure_LineNumber() {
		return (EAttribute) procedureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstAssign() {
		return instAssignEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstAssign_Target() {
		return (EReference) instAssignEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstAssign_Value() {
		return (EReference) instAssignEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstCall() {
		return instCallEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstCall_Arguments() {
		return (EReference) instCallEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstCall_Procedure() {
		return (EReference) instCallEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstCall_Target() {
		return (EReference) instCallEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstLoad() {
		return instLoadEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstLoad_Indexes() {
		return (EReference) instLoadEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstLoad_Source() {
		return (EReference) instLoadEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstLoad_Target() {
		return (EReference) instLoadEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstPhi() {
		return instPhiEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstPhi_OldVariable() {
		return (EReference) instPhiEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstPhi_Target() {
		return (EReference) instPhiEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstPhi_Values() {
		return (EReference) instPhiEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstReturn() {
		return instReturnEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstReturn_Value() {
		return (EReference) instReturnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInstStore() {
		return instStoreEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstStore_Indexes() {
		return (EReference) instStoreEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstStore_Target() {
		return (EReference) instStoreEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInstStore_Value() {
		return (EReference) instStoreEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArg() {
		return argEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArgByRef() {
		return argByRefEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArgByRef_Indexes() {
		return (EReference) argByRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArgByRef_Use() {
		return (EReference) argByRefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArgByVal() {
		return argByValEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArgByVal_Value() {
		return (EReference) argByValEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVar() {
		return varEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVar_Index() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVar_InitialValue() {
		return (EReference) varEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVar_Name() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVar_Type() {
		return (EReference) varEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVar_Value() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVar_Assignable() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVar_Global() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVar_Uses() {
		return (EReference) varEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVar_Defs() {
		return (EReference) varEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVar_LineNumber() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVar_Local() {
		return (EAttribute) varEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUse() {
		return useEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUse_Variable() {
		return (EReference) useEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprBinary() {
		return exprBinaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprBinary_E1() {
		return (EReference) exprBinaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprBinary_E2() {
		return (EReference) exprBinaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExprBinary_Op() {
		return (EAttribute) exprBinaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprBinary_Type() {
		return (EReference) exprBinaryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprBool() {
		return exprBoolEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExprBool_Value() {
		return (EAttribute) exprBoolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprFloat() {
		return exprFloatEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExprFloat_Value() {
		return (EAttribute) exprFloatEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprInt() {
		return exprIntEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExprInt_Value() {
		return (EAttribute) exprIntEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprInt_Type() {
		return (EReference) exprIntEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprList() {
		return exprListEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprList_Value() {
		return (EReference) exprListEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprString() {
		return exprStringEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExprString_Value() {
		return (EAttribute) exprStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprUnary() {
		return exprUnaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprUnary_Expr() {
		return (EReference) exprUnaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExprUnary_Op() {
		return (EAttribute) exprUnaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprUnary_Type() {
		return (EReference) exprUnaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExprVar() {
		return exprVarEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExprVar_Use() {
		return (EReference) exprVarEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDef() {
		return defEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDef_Variable() {
		return (EReference) defEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getOpBinary() {
		return opBinaryEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getOpUnary() {
		return opUnaryEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IrFactory getIrFactory() {
		return (IrFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		procedureEClass = createEClass(PROCEDURE);
		createEAttribute(procedureEClass, PROCEDURE__LINE_NUMBER);
		createEReference(procedureEClass, PROCEDURE__LOCALS);
		createEAttribute(procedureEClass, PROCEDURE__NAME);
		createEAttribute(procedureEClass, PROCEDURE__NATIVE);
		createEReference(procedureEClass, PROCEDURE__BLOCKS);
		createEReference(procedureEClass, PROCEDURE__PARAMETERS);
		createEReference(procedureEClass, PROCEDURE__RETURN_TYPE);
		createEReference(procedureEClass, PROCEDURE__CFG);

		paramEClass = createEClass(PARAM);
		createEReference(paramEClass, PARAM__VARIABLE);
		createEAttribute(paramEClass, PARAM__BY_REF);

		blockEClass = createEClass(BLOCK);
		createEReference(blockEClass, BLOCK__CFG_NODE);

		blockBasicEClass = createEClass(BLOCK_BASIC);
		createEReference(blockBasicEClass, BLOCK_BASIC__INSTRUCTIONS);

		blockIfEClass = createEClass(BLOCK_IF);
		createEReference(blockIfEClass, BLOCK_IF__CONDITION);
		createEReference(blockIfEClass, BLOCK_IF__ELSE_BLOCKS);
		createEReference(blockIfEClass, BLOCK_IF__JOIN_BLOCK);
		createEAttribute(blockIfEClass, BLOCK_IF__LINE_NUMBER);
		createEReference(blockIfEClass, BLOCK_IF__THEN_BLOCKS);

		blockWhileEClass = createEClass(BLOCK_WHILE);
		createEReference(blockWhileEClass, BLOCK_WHILE__CONDITION);
		createEReference(blockWhileEClass, BLOCK_WHILE__JOIN_BLOCK);
		createEAttribute(blockWhileEClass, BLOCK_WHILE__LINE_NUMBER);
		createEReference(blockWhileEClass, BLOCK_WHILE__BLOCKS);

		instructionEClass = createEClass(INSTRUCTION);
		createEAttribute(instructionEClass, INSTRUCTION__LINE_NUMBER);
		createEReference(instructionEClass, INSTRUCTION__PREDICATE);

		instAssignEClass = createEClass(INST_ASSIGN);
		createEReference(instAssignEClass, INST_ASSIGN__TARGET);
		createEReference(instAssignEClass, INST_ASSIGN__VALUE);

		instCallEClass = createEClass(INST_CALL);
		createEReference(instCallEClass, INST_CALL__ARGUMENTS);
		createEReference(instCallEClass, INST_CALL__PROCEDURE);
		createEReference(instCallEClass, INST_CALL__TARGET);

		instLoadEClass = createEClass(INST_LOAD);
		createEReference(instLoadEClass, INST_LOAD__INDEXES);
		createEReference(instLoadEClass, INST_LOAD__SOURCE);
		createEReference(instLoadEClass, INST_LOAD__TARGET);

		instPhiEClass = createEClass(INST_PHI);
		createEReference(instPhiEClass, INST_PHI__OLD_VARIABLE);
		createEReference(instPhiEClass, INST_PHI__TARGET);
		createEReference(instPhiEClass, INST_PHI__VALUES);

		instReturnEClass = createEClass(INST_RETURN);
		createEReference(instReturnEClass, INST_RETURN__VALUE);

		instStoreEClass = createEClass(INST_STORE);
		createEReference(instStoreEClass, INST_STORE__INDEXES);
		createEReference(instStoreEClass, INST_STORE__TARGET);
		createEReference(instStoreEClass, INST_STORE__VALUE);

		argEClass = createEClass(ARG);

		argByRefEClass = createEClass(ARG_BY_REF);
		createEReference(argByRefEClass, ARG_BY_REF__INDEXES);
		createEReference(argByRefEClass, ARG_BY_REF__USE);

		argByValEClass = createEClass(ARG_BY_VAL);
		createEReference(argByValEClass, ARG_BY_VAL__VALUE);

		expressionEClass = createEClass(EXPRESSION);

		exprBinaryEClass = createEClass(EXPR_BINARY);
		createEReference(exprBinaryEClass, EXPR_BINARY__E1);
		createEReference(exprBinaryEClass, EXPR_BINARY__E2);
		createEAttribute(exprBinaryEClass, EXPR_BINARY__OP);
		createEReference(exprBinaryEClass, EXPR_BINARY__TYPE);

		exprBoolEClass = createEClass(EXPR_BOOL);
		createEAttribute(exprBoolEClass, EXPR_BOOL__VALUE);

		exprFloatEClass = createEClass(EXPR_FLOAT);
		createEAttribute(exprFloatEClass, EXPR_FLOAT__VALUE);

		exprIntEClass = createEClass(EXPR_INT);
		createEAttribute(exprIntEClass, EXPR_INT__VALUE);
		createEReference(exprIntEClass, EXPR_INT__TYPE);

		exprListEClass = createEClass(EXPR_LIST);
		createEReference(exprListEClass, EXPR_LIST__VALUE);

		exprStringEClass = createEClass(EXPR_STRING);
		createEAttribute(exprStringEClass, EXPR_STRING__VALUE);

		exprUnaryEClass = createEClass(EXPR_UNARY);
		createEReference(exprUnaryEClass, EXPR_UNARY__EXPR);
		createEAttribute(exprUnaryEClass, EXPR_UNARY__OP);
		createEReference(exprUnaryEClass, EXPR_UNARY__TYPE);

		exprVarEClass = createEClass(EXPR_VAR);
		createEReference(exprVarEClass, EXPR_VAR__USE);

		typeEClass = createEClass(TYPE);

		typeBoolEClass = createEClass(TYPE_BOOL);

		typeFloatEClass = createEClass(TYPE_FLOAT);
		createEAttribute(typeFloatEClass, TYPE_FLOAT__SIZE);

		typeIntEClass = createEClass(TYPE_INT);
		createEAttribute(typeIntEClass, TYPE_INT__SIZE);

		typeListEClass = createEClass(TYPE_LIST);
		createEReference(typeListEClass, TYPE_LIST__SIZE_EXPR);
		createEReference(typeListEClass, TYPE_LIST__TYPE);

		typeStringEClass = createEClass(TYPE_STRING);
		createEAttribute(typeStringEClass, TYPE_STRING__SIZE);

		typeUintEClass = createEClass(TYPE_UINT);
		createEAttribute(typeUintEClass, TYPE_UINT__SIZE);

		typeVoidEClass = createEClass(TYPE_VOID);

		defEClass = createEClass(DEF);
		createEReference(defEClass, DEF__VARIABLE);

		varEClass = createEClass(VAR);
		createEAttribute(varEClass, VAR__ASSIGNABLE);
		createEReference(varEClass, VAR__DEFS);
		createEAttribute(varEClass, VAR__GLOBAL);
		createEAttribute(varEClass, VAR__INDEX);
		createEReference(varEClass, VAR__INITIAL_VALUE);
		createEAttribute(varEClass, VAR__LINE_NUMBER);
		createEAttribute(varEClass, VAR__LOCAL);
		createEAttribute(varEClass, VAR__NAME);
		createEReference(varEClass, VAR__TYPE);
		createEReference(varEClass, VAR__USES);
		createEAttribute(varEClass, VAR__VALUE);

		useEClass = createEClass(USE);
		createEReference(useEClass, USE__VARIABLE);

		predicateEClass = createEClass(PREDICATE);
		createEReference(predicateEClass, PREDICATE__EXPRESSIONS);

		cfgEClass = createEClass(CFG);
		createEReference(cfgEClass, CFG__ENTRY);
		createEReference(cfgEClass, CFG__EXIT);

		cfgNodeEClass = createEClass(CFG_NODE);
		createEReference(cfgNodeEClass, CFG_NODE__NODE);

		// Create enums
		opBinaryEEnum = createEEnum(OP_BINARY);
		opUnaryEEnum = createEEnum(OP_UNARY);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UtilPackage theUtilPackage = (UtilPackage) EPackage.Registry.INSTANCE
				.getEPackage(UtilPackage.eNS_URI);
		GraphPackage theGraphPackage = (GraphPackage) EPackage.Registry.INSTANCE
				.getEPackage(GraphPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		procedureEClass.getESuperTypes().add(theUtilPackage.getAttributable());
		paramEClass.getESuperTypes().add(theUtilPackage.getAttributable());
		blockEClass.getESuperTypes().add(theUtilPackage.getAttributable());
		blockBasicEClass.getESuperTypes().add(this.getBlock());
		blockIfEClass.getESuperTypes().add(this.getBlock());
		blockWhileEClass.getESuperTypes().add(this.getBlock());
		instructionEClass.getESuperTypes()
				.add(theUtilPackage.getAttributable());
		instAssignEClass.getESuperTypes().add(this.getInstruction());
		instCallEClass.getESuperTypes().add(this.getInstruction());
		instLoadEClass.getESuperTypes().add(this.getInstruction());
		instPhiEClass.getESuperTypes().add(this.getInstruction());
		instReturnEClass.getESuperTypes().add(this.getInstruction());
		instStoreEClass.getESuperTypes().add(this.getInstruction());
		argEClass.getESuperTypes().add(theUtilPackage.getAttributable());
		argByRefEClass.getESuperTypes().add(this.getArg());
		argByValEClass.getESuperTypes().add(this.getArg());
		expressionEClass.getESuperTypes().add(theUtilPackage.getAttributable());
		exprBinaryEClass.getESuperTypes().add(this.getExpression());
		exprBoolEClass.getESuperTypes().add(this.getExpression());
		exprFloatEClass.getESuperTypes().add(this.getExpression());
		exprIntEClass.getESuperTypes().add(this.getExpression());
		exprListEClass.getESuperTypes().add(this.getExpression());
		exprStringEClass.getESuperTypes().add(this.getExpression());
		exprUnaryEClass.getESuperTypes().add(this.getExpression());
		exprVarEClass.getESuperTypes().add(this.getExpression());
		typeBoolEClass.getESuperTypes().add(this.getType());
		typeFloatEClass.getESuperTypes().add(this.getType());
		typeIntEClass.getESuperTypes().add(this.getType());
		typeListEClass.getESuperTypes().add(this.getType());
		typeStringEClass.getESuperTypes().add(this.getType());
		typeUintEClass.getESuperTypes().add(this.getType());
		typeVoidEClass.getESuperTypes().add(this.getType());
		varEClass.getESuperTypes().add(theUtilPackage.getAttributable());
		cfgEClass.getESuperTypes().add(theGraphPackage.getGraph());
		cfgNodeEClass.getESuperTypes().add(theGraphPackage.getVertex());

		// Initialize classes and features; add operations and parameters
		initEClass(procedureEClass, Procedure.class, "Procedure", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProcedure_LineNumber(), ecorePackage.getEInt(),
				"lineNumber", null, 0, 1, Procedure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_Locals(), this.getVar(), null, "locals",
				null, 0, -1, Procedure.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProcedure_Name(), ecorePackage.getEString(), "name",
				null, 0, 1, Procedure.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getProcedure_Native(), ecorePackage.getEBoolean(),
				"native", null, 0, 1, Procedure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_Blocks(), this.getBlock(), null, "blocks",
				null, 0, -1, Procedure.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_Parameters(), this.getParam(), null,
				"parameters", null, 0, -1, Procedure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_ReturnType(), this.getType(), null,
				"returnType", null, 0, 1, Procedure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcedure_Cfg(), this.getCfg(), null, "cfg", null, 0,
				1, Procedure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(paramEClass, Param.class, "Param", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParam_Variable(), this.getVar(), null, "variable",
				null, 0, 1, Param.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParam_ByRef(), ecorePackage.getEBoolean(), "byRef",
				null, 0, 1, Param.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(blockEClass, Block.class, "Block", IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlock_CfgNode(), this.getCfgNode(),
				this.getCfgNode_Node(), "cfgNode", null, 0, 1, Block.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(blockBasicEClass, BlockBasic.class, "BlockBasic",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockBasic_Instructions(), this.getInstruction(),
				null, "instructions", null, 0, -1, BlockBasic.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(blockIfEClass, BlockIf.class, "BlockIf", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockIf_Condition(), this.getExpression(), null,
				"condition", null, 0, 1, BlockIf.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockIf_ElseBlocks(), this.getBlock(), null,
				"elseBlocks", null, 0, -1, BlockIf.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockIf_JoinBlock(), this.getBlockBasic(), null,
				"joinBlock", null, 0, 1, BlockIf.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockIf_LineNumber(), ecorePackage.getEInt(),
				"lineNumber", null, 0, 1, BlockIf.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getBlockIf_ThenBlocks(), this.getBlock(), null,
				"thenBlocks", null, 0, -1, BlockIf.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockWhileEClass, BlockWhile.class, "BlockWhile",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlockWhile_Condition(), this.getExpression(), null,
				"condition", null, 0, 1, BlockWhile.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBlockWhile_JoinBlock(), this.getBlockBasic(), null,
				"joinBlock", null, 0, 1, BlockWhile.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlockWhile_LineNumber(), ecorePackage.getEInt(),
				"lineNumber", null, 0, 1, BlockWhile.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getBlockWhile_Blocks(), this.getBlock(), null, "blocks",
				null, 0, -1, BlockWhile.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instructionEClass, Instruction.class, "Instruction",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInstruction_LineNumber(), ecorePackage.getEInt(),
				"lineNumber", null, 0, 1, Instruction.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getInstruction_Predicate(), this.getPredicate(), null,
				"predicate", null, 0, 1, Instruction.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instAssignEClass, InstAssign.class, "InstAssign",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstAssign_Target(), this.getDef(), null, "target",
				null, 0, 1, InstAssign.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstAssign_Value(), this.getExpression(), null,
				"value", null, 0, 1, InstAssign.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instCallEClass, InstCall.class, "InstCall", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstCall_Arguments(), this.getArg(), null,
				"arguments", null, 0, -1, InstCall.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstCall_Procedure(), this.getProcedure(), null,
				"procedure", null, 0, 1, InstCall.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstCall_Target(), this.getDef(), null, "target",
				null, 0, 1, InstCall.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instLoadEClass, InstLoad.class, "InstLoad", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstLoad_Indexes(), this.getExpression(), null,
				"indexes", null, 0, -1, InstLoad.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstLoad_Source(), this.getUse(), null, "source",
				null, 0, 1, InstLoad.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstLoad_Target(), this.getDef(), null, "target",
				null, 0, 1, InstLoad.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instPhiEClass, InstPhi.class, "InstPhi", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstPhi_OldVariable(), this.getVar(), null,
				"oldVariable", null, 0, 1, InstPhi.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstPhi_Target(), this.getDef(), null, "target",
				null, 0, 1, InstPhi.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstPhi_Values(), this.getExpression(), null,
				"values", null, 0, -1, InstPhi.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instReturnEClass, InstReturn.class, "InstReturn",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstReturn_Value(), this.getExpression(), null,
				"value", null, 0, 1, InstReturn.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instStoreEClass, InstStore.class, "InstStore", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstStore_Indexes(), this.getExpression(), null,
				"indexes", null, 0, -1, InstStore.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstStore_Target(), this.getDef(), null, "target",
				null, 0, 1, InstStore.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInstStore_Value(), this.getExpression(), null,
				"value", null, 0, 1, InstStore.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argEClass, Arg.class, "Arg", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(argByRefEClass, ArgByRef.class, "ArgByRef", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgByRef_Indexes(), this.getExpression(), null,
				"indexes", null, 0, -1, ArgByRef.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArgByRef_Use(), this.getUse(), null, "use", null, 0,
				1, ArgByRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(argByValEClass, ArgByVal.class, "ArgByVal", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgByVal_Value(), this.getExpression(), null,
				"value", null, 0, 1, ArgByVal.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionEClass, Expression.class, "Expression",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(exprBinaryEClass, ExprBinary.class, "ExprBinary",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprBinary_E1(), this.getExpression(), null, "e1",
				null, 0, 1, ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExprBinary_E2(), this.getExpression(), null, "e2",
				null, 0, 1, ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExprBinary_Op(), this.getOpBinary(), "op", null, 0,
				1, ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getExprBinary_Type(), this.getType(), null, "type",
				null, 0, 1, ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprBoolEClass, ExprBool.class, "ExprBool", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprBool_Value(), ecorePackage.getEBoolean(),
				"value", null, 0, 1, ExprBool.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(exprFloatEClass, ExprFloat.class, "ExprFloat", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprFloat_Value(), ecorePackage.getEBigDecimal(),
				"value", null, 0, 1, ExprFloat.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(exprIntEClass, ExprInt.class, "ExprInt", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprInt_Value(), ecorePackage.getEBigInteger(),
				"value", null, 0, 1, ExprInt.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getExprInt_Type(), this.getType(), null, "type", null,
				0, 1, ExprInt.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprListEClass, ExprList.class, "ExprList", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprList_Value(), this.getExpression(), null,
				"value", null, 0, -1, ExprList.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprStringEClass, ExprString.class, "ExprString",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprString_Value(), ecorePackage.getEString(),
				"value", null, 0, 1, ExprString.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(exprUnaryEClass, ExprUnary.class, "ExprUnary", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprUnary_Expr(), this.getExpression(), null, "expr",
				null, 0, 1, ExprUnary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExprUnary_Op(), this.getOpUnary(), "op", null, 0, 1,
				ExprUnary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExprUnary_Type(), this.getType(), null, "type", null,
				0, 1, ExprUnary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exprVarEClass, ExprVar.class, "ExprVar", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExprVar_Use(), this.getUse(), null, "use", null, 0,
				1, ExprVar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(typeEClass, Type.class, "Type", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(typeBoolEClass, TypeBool.class, "TypeBool", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(typeFloatEClass, TypeFloat.class, "TypeFloat", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeFloat_Size(), ecorePackage.getEInt(), "size",
				"32", 0, 1, TypeFloat.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(typeIntEClass, TypeInt.class, "TypeInt", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeInt_Size(), ecorePackage.getEInt(), "size", "32",
				0, 1, TypeInt.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(typeListEClass, TypeList.class, "TypeList", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeList_SizeExpr(), this.getExpression(), null,
				"sizeExpr", null, 0, 1, TypeList.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeList_Type(), this.getType(), null, "type", null,
				0, 1, TypeList.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeStringEClass, TypeString.class, "TypeString",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeString_Size(), ecorePackage.getEInt(), "size",
				null, 0, 1, TypeString.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(typeUintEClass, TypeUint.class, "TypeUint", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeUint_Size(), ecorePackage.getEInt(), "size",
				"32", 0, 1, TypeUint.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(typeVoidEClass, TypeVoid.class, "TypeVoid", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(defEClass, Def.class, "Def", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDef_Variable(), this.getVar(), this.getVar_Defs(),
				"variable", null, 0, 1, Def.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(varEClass, Var.class, "Var", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVar_Assignable(), ecorePackage.getEBoolean(),
				"assignable", null, 0, 1, Var.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getVar_Defs(), this.getDef(), this.getDef_Variable(),
				"defs", null, 0, -1, Var.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Global(), ecorePackage.getEBoolean(), "global",
				null, 0, 1, Var.class, IS_TRANSIENT, IS_VOLATILE,
				!IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getVar_Index(), ecorePackage.getEInt(), "index", null,
				0, 1, Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVar_InitialValue(), this.getExpression(), null,
				"initialValue", null, 0, 1, Var.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_LineNumber(), ecorePackage.getEInt(),
				"lineNumber", null, 0, 1, Var.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Local(), ecorePackage.getEBoolean(), "local",
				null, 0, 1, Var.class, IS_TRANSIENT, IS_VOLATILE,
				!IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getVar_Name(), ecorePackage.getEString(), "name", null,
				0, 1, Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVar_Type(), this.getType(), null, "type", null, 0, 1,
				Var.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getVar_Uses(), this.getUse(), this.getUse_Variable(),
				"uses", null, 0, -1, Var.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVar_Value(), ecorePackage.getEJavaObject(), "value",
				null, 0, 1, Var.class, IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(useEClass, Use.class, "Use", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUse_Variable(), this.getVar(), this.getVar_Uses(),
				"variable", null, 0, 1, Use.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(predicateEClass, Predicate.class, "Predicate", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPredicate_Expressions(), this.getExpression(), null,
				"expressions", null, 0, -1, Predicate.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cfgEClass, Cfg.class, "Cfg", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCfg_Entry(), this.getCfgNode(), null, "entry", null,
				0, 1, Cfg.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getCfg_Exit(), this.getCfgNode(), null, "exit", null, 0,
				1, Cfg.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(cfgNodeEClass, CfgNode.class, "CfgNode", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCfgNode_Node(), this.getBlock(),
				this.getBlock_CfgNode(), "node", null, 0, 1, CfgNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(opBinaryEEnum, OpBinary.class, "OpBinary");
		addEEnumLiteral(opBinaryEEnum, OpBinary.BITAND);
		addEEnumLiteral(opBinaryEEnum, OpBinary.BITOR);
		addEEnumLiteral(opBinaryEEnum, OpBinary.BITXOR);
		addEEnumLiteral(opBinaryEEnum, OpBinary.DIV);
		addEEnumLiteral(opBinaryEEnum, OpBinary.DIV_INT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.EQ);
		addEEnumLiteral(opBinaryEEnum, OpBinary.EXP);
		addEEnumLiteral(opBinaryEEnum, OpBinary.GE);
		addEEnumLiteral(opBinaryEEnum, OpBinary.GT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LE);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LOGIC_AND);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LOGIC_OR);
		addEEnumLiteral(opBinaryEEnum, OpBinary.LT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.MINUS);
		addEEnumLiteral(opBinaryEEnum, OpBinary.MOD);
		addEEnumLiteral(opBinaryEEnum, OpBinary.NE);
		addEEnumLiteral(opBinaryEEnum, OpBinary.PLUS);
		addEEnumLiteral(opBinaryEEnum, OpBinary.SHIFT_LEFT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.SHIFT_RIGHT);
		addEEnumLiteral(opBinaryEEnum, OpBinary.TIMES);

		initEEnum(opUnaryEEnum, OpUnary.class, "OpUnary");
		addEEnumLiteral(opUnaryEEnum, OpUnary.BITNOT);
		addEEnumLiteral(opUnaryEEnum, OpUnary.LOGIC_NOT);
		addEEnumLiteral(opUnaryEEnum, OpUnary.MINUS);
		addEEnumLiteral(opUnaryEEnum, OpUnary.NUM_ELTS);

		// Create resource
		createResource(eNS_URI);
	}

} // IrPackageImpl
