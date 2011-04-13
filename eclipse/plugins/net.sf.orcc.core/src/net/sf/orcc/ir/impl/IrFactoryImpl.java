/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
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
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class IrFactoryImpl extends EFactoryImpl implements IrFactory {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IrPackage getPackage() {
		return IrPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static IrFactory init() {
		try {
			IrFactory theIrFactory = (IrFactory)EPackage.Registry.INSTANCE.getEFactory("http://orcc.sf.net/ir/Ir"); 
			if (theIrFactory != null) {
				return theIrFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IrFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public IrFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOpBinaryToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOpUnaryToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case IrPackage.OP_BINARY:
				return convertOpBinaryToString(eDataType, instanceValue);
			case IrPackage.OP_UNARY:
				return convertOpUnaryToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
			case IrPackage.NODE_BLOCK: return createNodeBlock();
			case IrPackage.NODE_IF: return createNodeIf();
			case IrPackage.NODE_WHILE: return createNodeWhile();
			case IrPackage.PROCEDURE: return createProcedure();
			case IrPackage.INST_ASSIGN: return createInstAssign();
			case IrPackage.INST_CALL: return createInstCall();
			case IrPackage.INST_LOAD: return createInstLoad();
			case IrPackage.INST_PHI: return createInstPhi();
			case IrPackage.INST_RETURN: return createInstReturn();
			case IrPackage.INST_SPECIFIC: return createInstSpecific();
			case IrPackage.INST_STORE: return createInstStore();
			case IrPackage.LOCATION: return createLocation();
			case IrPackage.VAR: return createVar();
			case IrPackage.USE: return createUse();
			case IrPackage.EXPR_BINARY: return createExprBinary();
			case IrPackage.EXPR_BOOL: return createExprBool();
			case IrPackage.EXPR_FLOAT: return createExprFloat();
			case IrPackage.EXPR_INT: return createExprInt();
			case IrPackage.EXPR_LIST: return createExprList();
			case IrPackage.EXPR_STRING: return createExprString();
			case IrPackage.EXPR_UNARY: return createExprUnary();
			case IrPackage.EXPR_VAR: return createExprVar();
			case IrPackage.DEF: return createDef();
			case IrPackage.ACTION: return createAction();
			case IrPackage.ACTOR: return createActor();
			case IrPackage.PATTERN: return createPattern();
			case IrPackage.PORT: return createPort();
			case IrPackage.TAG: return createTag();
			case IrPackage.FSM: return createFSM();
			case IrPackage.VAR_TO_PORT_MAP_ENTRY: return (EObject)createVarToPortMapEntry();
			case IrPackage.PORT_TO_VAR_MAP_ENTRY: return (EObject)createPortToVarMapEntry();
			case IrPackage.PORT_TO_EINTEGER_OBJECT_MAP_ENTRY: return (EObject)createPortToEIntegerObjectMapEntry();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action createAction() {
		ActionImpl action = new ActionImpl();
		return action;
	}

	@Override
	public Action createAction(Location location, Tag tag,
			Pattern inputPattern, Pattern outputPattern, Pattern peekedPattern, Procedure scheduler,
			Procedure body) {
		ActionImpl action = new ActionImpl();
		action.setBody(body);
		action.setInputPattern(inputPattern);
		action.setLocation(location);
		action.setOutputPattern(outputPattern);
		action.setPeekPattern(peekedPattern);
		action.setScheduler(scheduler);
		action.setTag(tag);
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Actor createActor() {
		ActorImpl actor = new ActorImpl();
		return actor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Def createDef() {
		DefImpl def = new DefImpl();
		return def;
	}

	@Override
	public Def createDef(Var variable) {
		DefImpl def = new DefImpl();
		def.setVariable(variable);
		return def;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprBinary createExprBinary() {
		ExprBinaryImpl exprBinary = new ExprBinaryImpl();
		return exprBinary;
	}

	@Override
	public ExprBinary createExprBinary(Expression e1, OpBinary op,
			Expression e2, Type type) {
		ExprBinaryImpl exprBinary = new ExprBinaryImpl();
		exprBinary.setE1(e1);
		exprBinary.setE2(e2);
		exprBinary.setOp(op);
		exprBinary.setType(EcoreUtil.copy(type));
		return exprBinary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprBool createExprBool() {
		ExprBoolImpl exprBool = new ExprBoolImpl();
		return exprBool;
	}

	@Override
	public ExprBool createExprBool(boolean value) {
		ExprBoolImpl exprBool = new ExprBoolImpl();
		exprBool.setValue(value);
		return exprBool;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprFloat createExprFloat() {
		ExprFloatImpl exprFloat = new ExprFloatImpl();
		return exprFloat;
	}

	@Override
	public ExprFloat createExprFloat(float value) {
		ExprFloatImpl exprFloat = new ExprFloatImpl();
		exprFloat.setValue(BigDecimal.valueOf(value));
		return exprFloat;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprInt createExprInt() {
		ExprIntImpl exprInt = new ExprIntImpl();
		return exprInt;
	}

	@Override
	public ExprInt createExprInt(BigInteger value) {
		ExprIntImpl exprInt = new ExprIntImpl();
		exprInt.setValue(value);
		return exprInt;
	}

	@Override
	public ExprInt createExprInt(int value) {
		ExprIntImpl exprInt = new ExprIntImpl();
		exprInt.setValue(BigInteger.valueOf(value));
		return exprInt;
	}

	@Override
	public ExprInt createExprInt(long value) {
		ExprIntImpl exprInt = new ExprIntImpl();
		exprInt.setValue(BigInteger.valueOf(value));
		return exprInt;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprList createExprList() {
		ExprListImpl exprList = new ExprListImpl();
		return exprList;
	}

	@Override
	public ExprList createExprList(ExprList l1, ExprList l2) {
		ExprListImpl exprList = new ExprListImpl();
		exprList.getValue().addAll(l1.getValue());
		exprList.getValue().addAll(l2.getValue());
		return exprList;
	}

	@Override
	public ExprList createExprList(List<Expression> exprs) {
		ExprListImpl exprList = new ExprListImpl();
		exprList.getValue().addAll(exprs);
		return exprList;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprString createExprString() {
		ExprStringImpl exprString = new ExprStringImpl();
		return exprString;
	}

	@Override
	public ExprString createExprString(String value) {
		ExprStringImpl exprString = new ExprStringImpl();
		exprString.setValue(value);
		return exprString;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprUnary createExprUnary() {
		ExprUnaryImpl exprUnary = new ExprUnaryImpl();
		return exprUnary;
	}

	@Override
	public ExprUnary createExprUnary(OpUnary op, Expression expression,
			Type type) {
		ExprUnaryImpl exprUnary = new ExprUnaryImpl();
		exprUnary.setExpr(expression);
		exprUnary.setOp(op);
		exprUnary.setType(EcoreUtil.copy(type));
		return exprUnary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprVar createExprVar() {
		ExprVarImpl exprVar = new ExprVarImpl();
		return exprVar;
	}

	@Override
	public ExprVar createExprVar(Use use) {
		ExprVarImpl exprVar = new ExprVarImpl();
		exprVar.setUse(use);
		return exprVar;
	}

	@Override
	public ExprVar createExprVar(Var variable) {
		ExprVarImpl exprVar = new ExprVarImpl();
		exprVar.setUse(IrFactory.eINSTANCE.createUse(variable));
		return exprVar;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case IrPackage.OP_BINARY:
				return createOpBinaryFromString(eDataType, initialValue);
			case IrPackage.OP_UNARY:
				return createOpUnaryFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FSM createFSM() {
		FSMImpl fsm = new FSMImpl();
		return fsm;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstAssign createInstAssign() {
		InstAssignImpl instAssign = new InstAssignImpl();
		return instAssign;
	}

	@Override
	public InstAssign createInstAssign(Location location, Var target,
			Expression value) {
		InstAssignImpl instAssign = new InstAssignImpl();
		instAssign.setLocation(location);
		instAssign.setTarget(IrFactory.eINSTANCE.createDef(target));
		instAssign.setValue(value);
		return instAssign;
	}

	@Override
	public InstAssign createInstAssign(Var target, Expression value) {
		return createInstAssign(IrFactory.eINSTANCE.createLocation(), target,
				value);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstCall createInstCall() {
		InstCallImpl instCall = new InstCallImpl();
		return instCall;
	}

	@Override
	public InstCall createInstCall(Location location, Var target,
			Procedure procedure, List<Expression> parameters) {
		InstCallImpl instCall = new InstCallImpl();
		instCall.setLocation(location);
		if (target != null) {
			instCall.setTarget(IrFactory.eINSTANCE.createDef(target));
		}
		instCall.setProcedure(procedure);
		if (parameters != null) {
			instCall.getParameters().addAll(parameters);
		}
		return instCall;
	}

	@Override
	public InstCall createInstCall(Var target, Procedure procedure,
			List<Expression> parameters) {
		return createInstCall(IrFactory.eINSTANCE.createLocation(), target,
				procedure, parameters);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstLoad createInstLoad() {
		InstLoadImpl instLoad = new InstLoadImpl();
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(Location location, Def target, Use source,
			List<Expression> indexes) {
		InstLoadImpl instLoad = new InstLoadImpl();
		instLoad.setLocation(location);
		instLoad.setTarget(target);
		instLoad.setSource(source);
		instLoad.getIndexes().addAll(indexes);
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(Location location, Var target, Var source,
			List<Expression> indexes) {
		return createInstLoad(location, IrFactory.eINSTANCE.createDef(target),
				IrFactory.eINSTANCE.createUse(source), indexes);
	}

	@Override
	public InstLoad createInstLoad(Var target, Var source) {
		InstLoadImpl instLoad = new InstLoadImpl();
		instLoad.setLocation(IrFactory.eINSTANCE.createLocation());
		instLoad.setTarget(IrFactory.eINSTANCE.createDef(target));
		instLoad.setSource(IrFactory.eINSTANCE.createUse(source));
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(Var target, Var source,
			List<Expression> indexes) {
		return createInstLoad(IrFactory.eINSTANCE.createLocation(),
				IrFactory.eINSTANCE.createDef(target),
				IrFactory.eINSTANCE.createUse(source), indexes);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstPhi createInstPhi() {
		InstPhiImpl instPhi = new InstPhiImpl();
		return instPhi;
	}

	@Override
	public InstPhi createInstPhi(Location location, Def target,
			List<Expression> values) {
		InstPhiImpl instPhi = new InstPhiImpl();
		instPhi.setLocation(location);
		instPhi.setTarget(target);
		instPhi.getValues().addAll(values);
		return instPhi;
	}

	@Override
	public InstPhi createInstPhi(Location location, Var target,
			List<Expression> values) {
		return createInstPhi(location, IrFactory.eINSTANCE.createDef(target),
				values);
	}

	@Override
	public InstPhi createInstPhi(Var target, List<Expression> values) {
		return createInstPhi(IrFactory.eINSTANCE.createLocation(),
				IrFactory.eINSTANCE.createDef(target), values);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstReturn createInstReturn() {
		InstReturnImpl instReturn = new InstReturnImpl();
		return instReturn;
	}

	@Override
	public InstReturn createInstReturn(Expression value) {
		InstReturnImpl instReturn = new InstReturnImpl();
		instReturn.setLocation(IrFactory.eINSTANCE.createLocation());
		instReturn.setValue(value);
		return instReturn;
	}

	@Override
	public InstReturn createInstReturn(Location location, Expression value) {
		InstReturnImpl instReturn = new InstReturnImpl();
		instReturn.setLocation(location);
		instReturn.setValue(value);
		return instReturn;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstSpecific createInstSpecific() {
		InstSpecificImpl instSpecific = new InstSpecificImpl();
		return instSpecific;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstStore createInstStore() {
		InstStoreImpl instStore = new InstStoreImpl();
		return instStore;
	}

	@Override
	public InstStore createInstStore(Location location, Def target,
			List<Expression> indexes, Expression value) {
		InstStoreImpl instStore = new InstStoreImpl();
		instStore.setLocation(location);
		instStore.setTarget(target);
		instStore.setValue(value);
		instStore.getIndexes().addAll(indexes);
		return instStore;
	}

	@Override
	public InstStore createInstStore(Location location, Var target,
			List<Expression> indexes, Expression value) {
		return createInstStore(location, IrFactory.eINSTANCE.createDef(target),
				indexes, value);
	}

	@Override
	public InstStore createInstStore(Var target, Expression value) {
		InstStoreImpl instStore = new InstStoreImpl();
		instStore.setLocation(IrFactory.eINSTANCE.createLocation());
		instStore.setTarget(IrFactory.eINSTANCE.createDef(target));
		instStore.setValue(value);
		return instStore;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Location createLocation() {
		LocationImpl location = new LocationImpl();
		return location;
	}

	@Override
	public Location createLocation(int startLine, int startColumn, int endColumn) {
		LocationImpl location = new LocationImpl();
		location.setStartLine(startLine);
		location.setStartColumn(startColumn);
		location.setEndColumn(endColumn);
		return location;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NodeBlock createNodeBlock() {
		NodeBlockImpl nodeBlock = new NodeBlockImpl();
		return nodeBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NodeIf createNodeIf() {
		NodeIfImpl nodeIf = new NodeIfImpl();
		return nodeIf;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NodeWhile createNodeWhile() {
		NodeWhileImpl nodeWhile = new NodeWhileImpl();
		return nodeWhile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public OpBinary createOpBinaryFromString(EDataType eDataType,
			String initialValue) {
		OpBinary result = OpBinary.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public OpUnary createOpUnaryFromString(EDataType eDataType,
			String initialValue) {
		OpUnary result = OpUnary.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern createPattern() {
		PatternImpl pattern = new PatternImpl();
		return pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	@Override
	public Port createPort(Location location, Type type, String name) {
		PortImpl port = new PortImpl();
		port.setLocation(location);
		port.setName(name);
		port.setType(type);
		return port;
	}

	@Override
	public Port createPort(Port port) {
		return EcoreUtil.copy(port);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Port, Integer> createPortToEIntegerObjectMapEntry() {
		PortToEIntegerObjectMapEntryImpl portToEIntegerObjectMapEntry = new PortToEIntegerObjectMapEntryImpl();
		return portToEIntegerObjectMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Port, Var> createPortToVarMapEntry() {
		PortToVarMapEntryImpl portToVarMapEntry = new PortToVarMapEntryImpl();
		return portToVarMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Procedure createProcedure() {
		ProcedureImpl procedure = new ProcedureImpl();
		return procedure;
	}

	@Override
	public Procedure createProcedure(String name, Location location,
			Type returnType) {
		ProcedureImpl procedure = new ProcedureImpl();

		procedure.setLocation(location);
		procedure.setName(name);
		procedure.setReturnType(EcoreUtil.copy(returnType));

		return procedure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tag createTag() {
		TagImpl tag = new TagImpl();
		return tag;
	}

	@Override
	public Tag createTag(List<String> identifiers) {
		TagImpl tag = new TagImpl();
		tag.getIdentifiers().addAll(identifiers);
		return tag;
	}

	@Override
	public Tag createTag(String tagName) {
		TagImpl tag = new TagImpl();
		tag.getIdentifiers().add(tagName);
		return tag;
	}

	@Override
	public Tag createTag(Tag tag) {
		TagImpl newTag = new TagImpl();
		newTag.getIdentifiers().addAll(tag.getIdentifiers());
		return newTag;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TypeBool createTypeBool() {
		TypeBoolImpl typeBool = new TypeBoolImpl();
		return typeBool;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TypeFloat createTypeFloat() {
		TypeFloatImpl typeFloat = new TypeFloatImpl();
		return typeFloat;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TypeInt createTypeInt() {
		TypeIntImpl typeInt = new TypeIntImpl();
		return typeInt;
	}

	@Override
	public TypeInt createTypeInt(int size) {
		TypeIntImpl intType = new TypeIntImpl();
		intType.setSize(size);
		return intType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TypeList createTypeList() {
		TypeListImpl typeList = new TypeListImpl();
		return typeList;
	}

	@Override
	public TypeList createTypeList(Expression size, Type type) {
		TypeListImpl listType = new TypeListImpl();
		listType.setSizeExpr(size);
		listType.setType(EcoreUtil.copy(type));
		return listType;
	}

	@Override
	public TypeList createTypeList(int size, Type type) {
		TypeListImpl listType = new TypeListImpl();
		listType.setSize(size);
		listType.setType(EcoreUtil.copy(type));
		return listType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TypeString createTypeString() {
		TypeStringImpl typeString = new TypeStringImpl();
		return typeString;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TypeVoid createTypeVoid() {
		TypeVoidImpl typeVoid = new TypeVoidImpl();
		return typeVoid;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Use createUse() {
		UseImpl use = new UseImpl();
		return use;
	}

	@Override
	public Use createUse(Var variable) {
		UseImpl use = new UseImpl();
		use.setVariable(variable);
		return use;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Var createVar() {
		VarImpl var = new VarImpl();
		return var;
	}

	@Override
	public Var createVar(Location location, Type type, String name,
			boolean global, boolean assignable) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(global);
		var.setLocation(location);
		var.setName(name);
		var.setType(EcoreUtil.copy(type));
		return var;
	}

	@Override
	public Var createVar(Location location, Type type, String name,
			boolean assignable, Expression initialValue) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(true);
		var.setInitialValue(initialValue);
		var.setLocation(location);
		var.setName(name);
		var.setType(type);
		return var;
	}

	@Override
	public Var createVar(Location location, Type type, String name,
			boolean assignable, int index) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(false);
		var.setIndex(index);
		var.setLocation(location);
		var.setName(name);
		var.setType(EcoreUtil.copy(type));
		return var;
	}

	@Override
	public Var createVar(Type type, String name, boolean assignable, int index) {
		return createVar(IrFactory.eINSTANCE.createLocation(), type, name,
				assignable, index);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Var, Port> createVarToPortMapEntry() {
		VarToPortMapEntryImpl varToPortMapEntry = new VarToPortMapEntryImpl();
		return varToPortMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IrPackage getIrPackage() {
		return (IrPackage)getEPackage();
	}

} // IrFactoryImpl
