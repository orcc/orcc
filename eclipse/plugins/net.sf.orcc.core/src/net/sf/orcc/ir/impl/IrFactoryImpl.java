/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.*;
import net.sf.orcc.ir.util.TypeUtil;

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
			case IrPackage.ENTITY: return createEntity();
			case IrPackage.ACTOR: return createActor();
			case IrPackage.UNIT: return createUnit();
			case IrPackage.PORT: return createPort();
			case IrPackage.FSM: return createFSM();
			case IrPackage.STATE: return createState();
			case IrPackage.TRANSITIONS: return createTransitions();
			case IrPackage.TRANSITION: return createTransition();
			case IrPackage.ACTION: return createAction();
			case IrPackage.TAG: return createTag();
			case IrPackage.PATTERN: return createPattern();
			case IrPackage.PROCEDURE: return createProcedure();
			case IrPackage.NODE_BLOCK: return createNodeBlock();
			case IrPackage.NODE_IF: return createNodeIf();
			case IrPackage.NODE_WHILE: return createNodeWhile();
			case IrPackage.INST_ASSIGN: return createInstAssign();
			case IrPackage.INST_CALL: return createInstCall();
			case IrPackage.INST_LOAD: return createInstLoad();
			case IrPackage.INST_PHI: return createInstPhi();
			case IrPackage.INST_RETURN: return createInstReturn();
			case IrPackage.INST_STORE: return createInstStore();
			case IrPackage.EXPR_BINARY: return createExprBinary();
			case IrPackage.EXPR_BOOL: return createExprBool();
			case IrPackage.EXPR_FLOAT: return createExprFloat();
			case IrPackage.EXPR_INT: return createExprInt();
			case IrPackage.EXPR_LIST: return createExprList();
			case IrPackage.EXPR_STRING: return createExprString();
			case IrPackage.EXPR_UNARY: return createExprUnary();
			case IrPackage.EXPR_VAR: return createExprVar();
			case IrPackage.TYPE_BOOL: return createTypeBool();
			case IrPackage.TYPE_FLOAT: return createTypeFloat();
			case IrPackage.TYPE_INT: return createTypeInt();
			case IrPackage.TYPE_LIST: return createTypeList();
			case IrPackage.TYPE_STRING: return createTypeString();
			case IrPackage.TYPE_UINT: return createTypeUint();
			case IrPackage.TYPE_VOID: return createTypeVoid();
			case IrPackage.DEF: return createDef();
			case IrPackage.ANNOTATION: return createAnnotation();
			case IrPackage.VAR: return createVar();
			case IrPackage.USE: return createUse();
			case IrPackage.PREDICATE: return createPredicate();
			case IrPackage.PORT_TO_EINTEGER_OBJECT_MAP_ENTRY: return (EObject)createPortToEIntegerObjectMapEntry();
			case IrPackage.PORT_TO_VAR_MAP_ENTRY: return (EObject)createPortToVarMapEntry();
			case IrPackage.VAR_TO_PORT_MAP_ENTRY: return (EObject)createVarToPortMapEntry();
			case IrPackage.ESTRING_TO_ESTRING_MAP_ENTRY: return (EObject)createEStringToEStringMapEntry();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Action createAction() {
		ActionImpl action = new ActionImpl();
		return action;
	}

	@Override
	public Action createAction(Tag tag, Pattern inputPattern,
			Pattern outputPattern, Pattern peekedPattern, Procedure scheduler,
			Procedure body) {
		ActionImpl action = new ActionImpl();
		action.setBody(body);
		action.setInputPattern(inputPattern);
		action.setOutputPattern(outputPattern);
		action.setPeekPattern(peekedPattern);
		action.setScheduler(scheduler);
		action.setTag(tag);
		return action;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Actor createActor() {
		ActorImpl actor = new ActorImpl();
		return actor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Annotation createAnnotation() {
		AnnotationImpl annotation = new AnnotationImpl();
		return annotation;
	}

	@Override
	public Annotation createAnnotation(String name) {
		AnnotationImpl annotation = new AnnotationImpl();
		annotation.setName(name);
		return annotation;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity createEntity() {
		EntityImpl entity = new EntityImpl();
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createEStringToEStringMapEntry() {
		EStringToEStringMapEntryImpl eStringToEStringMapEntry = new EStringToEStringMapEntryImpl();
		return eStringToEStringMapEntry;
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	public InstAssign createInstAssign(int lineNumber, Var target,
			Expression value) {
		InstAssignImpl instAssign = new InstAssignImpl();
		instAssign.setLineNumber(lineNumber);
		instAssign.setTarget(IrFactory.eINSTANCE.createDef(target));
		instAssign.setValue(value);
		return instAssign;
	}

	@Override
	public InstAssign createInstAssign(Var target, Expression value) {
		return createInstAssign(0, target, value);
	}

	@Override
	public InstAssign createInstAssign(Var target, int value) {
		return createInstAssign(target, createExprInt(value));
	}

	@Override
	public InstAssign createInstAssign(Var target, long value) {
		return createInstAssign(target, createExprInt(value));
	}

	@Override
	public InstAssign createInstAssign(Var target, Var value) {
		return createInstAssign(target, createExprVar(value));
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
	public InstCall createInstCall(int lineNumber, Var target,
			Procedure procedure, List<Expression> parameters) {
		InstCallImpl instCall = new InstCallImpl();
		instCall.setLineNumber(lineNumber);
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
		return createInstCall(0, target, procedure, parameters);
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
	public InstLoad createInstLoad(int lineNumber, Def target, Use source,
			List<Expression> indexes) {
		InstLoadImpl instLoad = new InstLoadImpl();
		instLoad.setLineNumber(lineNumber);
		instLoad.setTarget(target);
		instLoad.setSource(source);
		instLoad.getIndexes().addAll(indexes);
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(int lineNumber, Var target, Var source,
			List<Expression> indexes) {
		return createInstLoad(lineNumber,
				IrFactory.eINSTANCE.createDef(target),
				IrFactory.eINSTANCE.createUse(source), indexes);
	}

	@Override
	public InstLoad createInstLoad(Var target, Var source) {
		InstLoadImpl instLoad = new InstLoadImpl();
		instLoad.setTarget(IrFactory.eINSTANCE.createDef(target));
		instLoad.setSource(IrFactory.eINSTANCE.createUse(source));
		return instLoad;
	}

	@Override
	public InstLoad createInstLoad(Var target, Var source,
			List<Expression> indexes) {
		return createInstLoad(0, IrFactory.eINSTANCE.createDef(target),
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
	public InstPhi createInstPhi(int lineNumber, Def target,
			List<Expression> values) {
		InstPhiImpl instPhi = new InstPhiImpl();
		instPhi.setLineNumber(lineNumber);
		instPhi.setTarget(target);
		instPhi.getValues().addAll(values);
		return instPhi;
	}

	@Override
	public InstPhi createInstPhi(int lineNumber, Var target,
			List<Expression> values) {
		return createInstPhi(lineNumber, IrFactory.eINSTANCE.createDef(target),
				values);
	}

	@Override
	public InstPhi createInstPhi(Var target, List<Expression> values) {
		return createInstPhi(0, IrFactory.eINSTANCE.createDef(target), values);
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
		instReturn.setValue(value);
		return instReturn;
	}

	@Override
	public InstReturn createInstReturn(int lineNumber, Expression value) {
		InstReturnImpl instReturn = new InstReturnImpl();
		instReturn.setLineNumber(lineNumber);
		instReturn.setValue(value);
		return instReturn;
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
	public InstStore createInstStore(int lineNumber, Def target,
			List<Expression> indexes, Expression value) {
		InstStoreImpl instStore = new InstStoreImpl();
		instStore.setLineNumber(lineNumber);
		instStore.setTarget(target);
		instStore.setValue(value);
		instStore.getIndexes().addAll(indexes);
		return instStore;
	}

	@Override
	public InstStore createInstStore(int lineNumber, Var target,
			List<Expression> indexes, Expression value) {
		return createInstStore(lineNumber,
				IrFactory.eINSTANCE.createDef(target), indexes, value);
	}
	
	@Override
	public InstStore createInstStore(Var target, Expression value) {
		InstStoreImpl instStore = new InstStoreImpl();
		instStore.setTarget(IrFactory.eINSTANCE.createDef(target));
		instStore.setValue(value);
		return instStore;
	}
	
	@Override
	public InstStore createInstStore(Var target, int value) {
		return createInstStore(target, createExprInt(value));
	}
	
	@Override
	public InstStore createInstStore(Var target, int index,
			Expression source) {
		List<Expression> indexes = new ArrayList<Expression>(1);
		indexes.add(createExprInt(index));
		return createInstStore(target, indexes, source);
	}
	
	@Override
	public InstStore createInstStore(Var target, int index,
			Var source) {
		List<Expression> indexes = new ArrayList<Expression>(1);
		indexes.add(createExprInt(index));
		return createInstStore(target, indexes, createExprVar(source));
	}
	
	@Override
	public InstStore createInstStore(Var target, List<Expression> indexes,
			Expression value) {
		return createInstStore(0, target, indexes, value);
	}
	
	@Override
	public InstStore createInstStore(Var target, List<Expression> indexes,
			int value) {
		return createInstStore(target, indexes, createExprInt(value));
	}

	@Override
	public InstStore createInstStore(Var target, List<Expression> indexes,
			Var source) {
		return createInstStore(target, indexes, createExprVar(source));
	}

	@Override
	public InstStore createInstStore(Var target, Var source) {
		return createInstStore(target, createExprVar(source));
	}

	@Override
	public InstStore createInstStore(Var target, Var index,
			Expression source) {
		List<Expression> indexes = new ArrayList<Expression>(1);
		indexes.add(createExprVar(index));
		return createInstStore(target, indexes, source);
	}

	@Override
	public InstStore createInstStore(Var target, Var index,
			Var source) {
		List<Expression> indexes = new ArrayList<Expression>(1);
		indexes.add(createExprVar(index));
		return createInstStore(target, indexes, createExprVar(source));
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern createPattern() {
		PatternImpl pattern = new PatternImpl();
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	@Override
	public Port createPort(Port port) {
		return EcoreUtil.copy(port);
	}

	@Override
	public Port createPort(Type type, String name) {
		PortImpl port = new PortImpl();
		port.setName(name);
		port.setType(type);
		return port;
	}

	@Override
	public Port createPort(Type type, String name, boolean native_) {
		PortImpl port = new PortImpl();
		port.setName(name);
		port.setNative(native_);
		port.setType(type);
		return port;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Port, Integer> createPortToEIntegerObjectMapEntry() {
		PortToEIntegerObjectMapEntryImpl portToEIntegerObjectMapEntry = new PortToEIntegerObjectMapEntryImpl();
		return portToEIntegerObjectMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	public Predicate createPredicate() {
		PredicateImpl predicate = new PredicateImpl();
		return predicate;
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
	public Procedure createProcedure(String name, int lineNumber,
			Type returnType) {
		ProcedureImpl procedure = new ProcedureImpl();

		procedure.setLineNumber(lineNumber);
		procedure.setName(name);
		procedure.setReturnType(EcoreUtil.copy(returnType));

		return procedure;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	@Override
	public State createState(String name) {
		StateImpl state = new StateImpl();
		state.setName(name);
		return state;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	@Override
	public Transition createTransition(Action action, State target) {
		TransitionImpl transition = new TransitionImpl();
		transition.setAction(action);
		transition.setState(target);
		return transition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Transitions createTransitions() {
		TransitionsImpl transitions = new TransitionsImpl();
		return transitions;
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
	 */
	public TypeInt createTypeInt() {
		TypeIntImpl typeInt = new TypeIntImpl();
		typeInt.setSize(32);
		return typeInt;
	}

	@Override
	public TypeInt createTypeInt(int size) {
		TypeIntImpl typeInt = new TypeIntImpl();
		typeInt.setSize(size);
		return typeInt;
	}

	@Override
	public Type createTypeIntOrUint(BigInteger value) {
		int size = TypeUtil.getSize(value);
		if (value.compareTo(BigInteger.ZERO) >= 0) {
			return IrFactory.eINSTANCE.createTypeUint(size);
		} else {
			return IrFactory.eINSTANCE.createTypeInt(size);
		}
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
		TypeListImpl typeList = new TypeListImpl();
		typeList.setSizeExpr(size);
		typeList.setType(EcoreUtil.copy(type));
		return typeList;
	}

	@Override
	public TypeList createTypeList(int size, Type type) {
		TypeListImpl typeList = new TypeListImpl();
		typeList.setSize(size);
		typeList.setType(EcoreUtil.copy(type));
		return typeList;
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
	 */
	public TypeUint createTypeUint() {
		TypeUintImpl typeUint = new TypeUintImpl();
		typeUint.setSize(32);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Unit createUnit() {
		UnitImpl unit = new UnitImpl();
		return unit;
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
	public Var createVar(int lineNumber, Type type, String name,
			boolean global, boolean assignable) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(global);
		var.setLineNumber(lineNumber);
		var.setName(name);
		var.setType(EcoreUtil.copy(type));
		return var;
	}

	@Override
	public Var createVar(int lineNumber, Type type, String name,
			boolean assignable, Expression initialValue) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(true);
		var.setInitialValue(initialValue);
		var.setLineNumber(lineNumber);
		var.setName(name);
		var.setType(type);
		return var;
	}

	@Override
	public Var createVar(int lineNumber, Type type, String name,
			boolean assignable, int index) {
		VarImpl var = new VarImpl();
		var.setAssignable(assignable);
		var.setGlobal(false);
		var.setIndex(index);
		var.setLineNumber(lineNumber);
		var.setName(name);
		var.setType(EcoreUtil.copy(type));
		return var;
	}

	@Override
	public Var createVar(Type type, String name, boolean assignable, int index) {
		return createVar(0, type, name, assignable, index);
	}

	@Override
	public Var createVarInt(String name, boolean assignable, int index) {
		return createVar(createTypeInt(), name, assignable, index);
	}

	@Override
	public Var createVarInt(String name, int size, boolean assignable, int index) {
		return createVar(createTypeInt(size), name, assignable, index);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
