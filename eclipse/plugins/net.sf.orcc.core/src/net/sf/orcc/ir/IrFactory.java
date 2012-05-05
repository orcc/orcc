/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see net.sf.orcc.ir.IrPackage
 * @generated
 */
public interface IrFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	IrFactory eINSTANCE = net.sf.orcc.ir.impl.IrFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Arg By Ref</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Arg By Ref</em>'.
	 * @generated
	 */
	ArgByRef createArgByRef();

	/**
	 * Returns a new object of class '<em>Arg By Val</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Arg By Val</em>'.
	 * @generated
	 */
	ArgByVal createArgByVal();

	/**
	 * Creates a new argument by value with the given expression.
	 * 
	 * @param value
	 *            an expression
	 * @return a new argument by value with the given expression
	 */
	Arg createArgByVal(Expression value);

	/**
	 * Creates a new argument by value whose value is the given variable.
	 * 
	 * @param variable
	 *            a variable to pass as value
	 * @return a new argument by value whose value is the given variable
	 */
	Arg createArgByVal(Var variable);

	/**
	 * Creates new arguments by value with the given expressions.
	 * 
	 * @param values
	 *            a list of expressions
	 * @return new arguments by value with the given expressions
	 */
	List<Arg> createArgsByVal(List<Expression> values);

	/**
	 * Returns a new object of class '<em>Def</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Def</em>'.
	 * @generated
	 */
	Def createDef();

	/**
	 * Creates a new definition of the given variable.
	 * 
	 * @param variable
	 *            a variable
	 * @return a new definition of the given variable
	 */
	Def createDef(Var variable);

	/**
	 * Returns a new object of class '<em>Expr Binary</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Binary</em>'.
	 * @generated
	 */
	ExprBinary createExprBinary();

	/**
	 * Creates a new ExprBinary. Type is copied.
	 * 
	 * @param e1
	 * @param op
	 * @param e2
	 * @param type
	 * @return
	 */
	ExprBinary createExprBinary(Expression e1, OpBinary op, Expression e2,
			Type type);

	/**
	 * Returns a new object of class '<em>Expr Bool</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Bool</em>'.
	 * @generated
	 */
	ExprBool createExprBool();

	ExprBool createExprBool(boolean value);

	/**
	 * Returns a new object of class '<em>Expr Float</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Float</em>'.
	 * @generated
	 */
	ExprFloat createExprFloat();

	ExprFloat createExprFloat(BigDecimal value);

	ExprFloat createExprFloat(float value);

	/**
	 * Returns a new object of class '<em>Expr Int</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Int</em>'.
	 * @generated
	 */
	ExprInt createExprInt();

	ExprInt createExprInt(BigInteger value);

	ExprInt createExprInt(int value);

	ExprInt createExprInt(long value);

	/**
	 * Returns a new object of class '<em>Expr List</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr List</em>'.
	 * @generated
	 */
	ExprList createExprList();

	ExprList createExprList(ExprList l1, ExprList l2);

	ExprList createExprList(List<Expression> exprs);

	/**
	 * Returns a new object of class '<em>Expr String</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr String</em>'.
	 * @generated
	 */
	ExprString createExprString();

	ExprString createExprString(String value);

	/**
	 * Returns a new object of class '<em>Expr Unary</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Unary</em>'.
	 * @generated
	 */
	ExprUnary createExprUnary();

	/**
	 * Creates a new ExprUnary. Type is copied.
	 * 
	 * @param op
	 * @param expression
	 * @param type
	 * @return
	 */
	ExprUnary createExprUnary(OpUnary op, Expression expression, Type type);

	/**
	 * Returns a new object of class '<em>Expr Var</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expr Var</em>'.
	 * @generated
	 */
	ExprVar createExprVar();

	ExprVar createExprVar(Use use);

	ExprVar createExprVar(Var variable);

	/**
	 * Returns a new object of class '<em>Inst Assign</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Assign</em>'.
	 * @generated
	 */
	InstAssign createInstAssign();

	/**
	 * Creates an InstAssign with the given location, target and value.
	 * 
	 * @param lineNumber
	 *            number of line of this assign
	 * @param target
	 *            target variable
	 * @param value
	 *            value
	 * @return an InstAssign with the given location, target and value.
	 */
	InstAssign createInstAssign(int lineNumber, Var target, Expression value);

	/**
	 * Creates an InstAssign with the given target and value.
	 * 
	 * @param target
	 *            target variable
	 * @param value
	 *            value
	 * @return an InstAssign with the given location, target and value.
	 */
	InstAssign createInstAssign(Var target, Expression value);

	/**
	 * Creates an InstAssign with the given target and integer value.
	 * 
	 * @param target
	 *            target variable
	 * @param value
	 *            integer value
	 * @return an InstAssign with the given target and value.
	 */
	InstAssign createInstAssign(Var target, int value);

	/**
	 * Creates an InstAssign with the given target and integer value.
	 * 
	 * @param target
	 *            target variable
	 * @param value
	 *            integer value
	 * @return an InstAssign with the given target and value.
	 */
	InstAssign createInstAssign(Var target, long value);

	/**
	 * Creates an InstAssign with the given target and source.
	 * 
	 * @param target
	 *            target variable
	 * @param value
	 *            source variable
	 * @return an InstAssign with the given target and value.
	 */
	InstAssign createInstAssign(Var target, Var value);

	/**
	 * Returns a new object of class '<em>Inst Call</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Call</em>'.
	 * @generated
	 */
	InstCall createInstCall();

	/**
	 * Creates an InstCall with the given location, target, procedure,
	 * parameters.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param procedure
	 * @param parameters
	 * @return a call
	 */
	InstCall createInstCall(int lineNumber, Var target, Procedure procedure,
			List<Expression> parameters);

	/**
	 * Creates an InstCall with the given target, procedure, parameters.
	 * 
	 * @param location
	 * @param target
	 * @param procedure
	 * @param parameters
	 * @return a call
	 */
	InstCall createInstCall(Var target, Procedure procedure,
			List<Expression> parameters);

	/**
	 * Returns a new object of class '<em>Inst Load</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Load</em>'.
	 * @generated
	 */
	InstLoad createInstLoad();

	/**
	 * Creates an InstLoad with the given location, target, source, indexes.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a load
	 */
	InstLoad createInstLoad(int lineNumber, Def target, Use source,
			List<Expression> indexes);

	/**
	 * Creates an InstLoad with the given location, target, source, indexes.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a load
	 */
	InstLoad createInstLoad(int lineNumber, Var target, Var source,
			List<Expression> indexes);

	/**
	 * Creates an InstLoad with the given target and source.
	 * 
	 * @param target
	 * @param source
	 * @return a load
	 */
	InstLoad createInstLoad(Var target, Var source);

	/**
	 * Creates an InstLoad with the given target, source, and indexes.
	 * 
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a load
	 */
	InstLoad createInstLoad(Var target, Var source, List<Expression> indexes);

	/**
	 * Returns a new object of class '<em>Inst Phi</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Phi</em>'.
	 * @generated
	 */
	InstPhi createInstPhi();

	/**
	 * Creates an InstPhi with the given location, target, and values.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param values
	 * @return a phi
	 */
	InstPhi createInstPhi(int lineNumber, Def target, List<Expression> values);

	/**
	 * Creates an InstPhi with the given location, target, and values.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param values
	 * @return a phi
	 */
	InstPhi createInstPhi(int lineNumber, Var target, List<Expression> values);

	/**
	 * Creates an InstPhi with the given target and values.
	 * 
	 * @param target
	 * @param values
	 * @return a phi
	 */
	InstPhi createInstPhi(Var target, List<Expression> values);

	/**
	 * Returns a new object of class '<em>Inst Return</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Return</em>'.
	 * @generated
	 */
	InstReturn createInstReturn();

	/**
	 * Creates an InstReturn with the given location and value.
	 * 
	 * @param value
	 * @return a return
	 */
	InstReturn createInstReturn(Expression value);

	/**
	 * Creates an InstReturn with the given location and value.
	 * 
	 * @param lineNumber
	 * @param value
	 * @return a return
	 */
	InstReturn createInstReturn(int lineNumber, Expression value);

	/**
	 * Returns a new object of class '<em>Inst Store</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Inst Store</em>'.
	 * @generated
	 */
	InstStore createInstStore();

	/**
	 * Creates an InstStore with the given location, target, indexes, value.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a store
	 */
	InstStore createInstStore(int lineNumber, Def target,
			List<Expression> indexes, Expression value);

	/**
	 * Creates an InstStore with the given location, target, indexes, value.
	 * 
	 * @param lineNumber
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a store
	 */
	InstStore createInstStore(int lineNumber, Var target,
			List<Expression> indexes, Expression value);

	/**
	 * Creates an InstStore with the given target and source.
	 * 
	 * @param target
	 * @param value
	 * @return a store
	 */
	InstStore createInstStore(Var target, Expression value);

	/**
	 * Creates an InstStore with the given target and value.
	 * 
	 * @param target
	 * @param value
	 * @return a store
	 */
	InstStore createInstStore(Var target, int value);

	/**
	 * Creates an InstStore with the given target, index and source.
	 * 
	 * @param target
	 * @param source
	 * @param index
	 * @return a store
	 */
	InstStore createInstStore(Var target, int index, Expression source);

	/**
	 * Creates an InstStore with the given target, index and source.
	 * 
	 * @param target
	 * @param source
	 * @param index
	 * @return a store
	 */
	InstStore createInstStore(Var target, int index, Var source);

	/**
	 * Creates an InstStore with the given target, indexes, value.
	 * 
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a store
	 */
	InstStore createInstStore(Var target, List<Expression> indexes,
			Expression value);

	/**
	 * Creates an InstStore with the given target, indexes, value.
	 * 
	 * @param target
	 * @param value
	 * @param indexes
	 * @return a store
	 */
	InstStore createInstStore(Var target, List<Expression> indexes, int value);

	/**
	 * Creates an InstStore with the given target, indexes, value.
	 * 
	 * @param target
	 * @param source
	 * @param indexes
	 * @return a store
	 */
	InstStore createInstStore(Var target, List<Expression> indexes, Var source);

	/**
	 * Creates an InstStore with the given target and source.
	 * 
	 * @param target
	 * @param source
	 * @return a store
	 */
	InstStore createInstStore(Var target, Var source);

	/**
	 * Creates an InstStore with the given target, index and source.
	 * 
	 * @param target
	 * @param source
	 * @param index
	 * @return a store
	 */
	InstStore createInstStore(Var target, Var index, Expression source);

	/**
	 * Creates an InstStore with the given target, index and source.
	 * 
	 * @param target
	 * @param source
	 * @param index
	 * @return a store
	 */
	InstStore createInstStore(Var target, Var index, Var source);

	/**
	 * Returns a new object of class '<em>Block Basic</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block Basic</em>'.
	 * @generated
	 */
	BlockBasic createBlockBasic();

	/**
	 * Returns a new object of class '<em>Block If</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block If</em>'.
	 * @generated
	 */
	BlockIf createBlockIf();

	/**
	 * Returns a new object of class '<em>Block While</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block While</em>'.
	 * @generated
	 */
	BlockWhile createBlockWhile();

	/**
	 * Returns a new object of class '<em>Param</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Param</em>'.
	 * @generated
	 */
	Param createParam();

	/**
	 * Creates a parameter that contains the given variable.
	 * 
	 * @param variable
	 *            a variable
	 * @return a parameter that contains the given variable
	 */
	Param createParam(Var variable);

	/**
	 * Returns a new object of class '<em>Predicate</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Predicate</em>'.
	 * @generated
	 */
	Predicate createPredicate();

	/**
	 * Returns a new object of class '<em>Cfg</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cfg</em>'.
	 * @generated
	 */
	Cfg createCfg();

	/**
	 * Returns a new object of class '<em>Cfg Node</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Cfg Node</em>'.
	 * @generated
	 */
	CfgNode createCfgNode();

	CfgNode createCfgNode(Block node);

	/**
	 * Returns a new object of class '<em>Procedure</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Procedure</em>'.
	 * @generated
	 */
	Procedure createProcedure();

	/**
	 * Creates a new procedure, not external, with empty parameters, locals, and
	 * nodes. Return type is copied.
	 * 
	 * @param name
	 *            The procedure name.
	 * @param lineNumber
	 *            the number of the line at which the procedure is defined
	 * @param returnType
	 *            The procedure return type.
	 */
	Procedure createProcedure(String name, int lineNumber, Type returnType);

	/**
	 * Returns a new object of class '<em>Type Bool</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type Bool</em>'.
	 * @generated
	 */
	TypeBool createTypeBool();

	/**
	 * Returns a new object of class '<em>Type Float</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type Float</em>'.
	 * @generated
	 */
	TypeFloat createTypeFloat();

	/**
	 * Creates a new float type with the given size (the size could only be 16,
	 * 32 or 64).
	 * 
	 * @param size
	 *            the size of this float type
	 */
	TypeFloat createTypeFloat(int size);

	/**
	 * Returns a new object of class '<em>Type Int</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type Int</em>'.
	 * @generated
	 */
	TypeInt createTypeInt();

	/**
	 * Creates a new int type with the given size.
	 * 
	 * @param size
	 *            the size of this int type
	 */
	TypeInt createTypeInt(int size);

	/**
	 * Creates a TypeInt or TypeUint depending on the sign of value. 0 is
	 * considered positive.
	 * 
	 * @param value
	 *            a value
	 * @return a TypeInt or TypeUint depending on the sign of value
	 */
	Type createTypeIntOrUint(BigInteger value);

	/**
	 * Returns a new object of class '<em>Type List</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type List</em>'.
	 * @generated
	 */
	TypeList createTypeList();

	/**
	 * Creates a new list type with the given size and element type. Type is
	 * copied.
	 * 
	 * @param size
	 *            the size of this list type
	 * @param type
	 *            the type of this list's elements
	 */
	TypeList createTypeList(Expression size, Type type);

	/**
	 * Creates a new list type with the given size and element type. Type is
	 * copied.
	 * 
	 * @param size
	 *            the size of this list type
	 * @param type
	 *            the type of this list's elements
	 */
	TypeList createTypeList(int size, Type type);

	/**
	 * Returns a new object of class '<em>Type String</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type String</em>'.
	 * @generated
	 */
	TypeString createTypeString();

	/**
	 * Returns a new object of class '<em>Type Uint</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type Uint</em>'.
	 * @generated
	 */
	TypeUint createTypeUint();

	/**
	 * Creates a new uint type with the given size.
	 * 
	 * @param size
	 *            the size of this uint type
	 */
	TypeUint createTypeUint(int size);

	/**
	 * Returns a new object of class '<em>Type Void</em>'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Type Void</em>'.
	 * @generated
	 */
	TypeVoid createTypeVoid();

	/**
	 * Returns a new object of class '<em>Use</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Use</em>'.
	 * @generated
	 */
	Use createUse();

	/**
	 * Creates a new use of the given variable.
	 * 
	 * @param variable
	 *            a variable
	 * @return a new use of the given variable
	 */
	Use createUse(Var variable);

	/**
	 * Returns a new object of class '<em>Var</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Var</em>'.
	 * @generated
	 */
	Var createVar();

	/**
	 * Creates a new variable with the given location, type, and name. The
	 * variable may be global or local, and assignable or not. Type is copied.
	 * 
	 * @param lineNumber
	 *            the number of the line at which the variable is defined
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param global
	 *            whether this variable is global
	 * @param assignable
	 *            whether this variable is assignable
	 */
	Var createVar(int lineNumber, Type type, String name, boolean global,
			boolean assignable);

	/**
	 * Creates a new global variable with the given location, type, name,
	 * initial value. The variable may be assignable or not.
	 * 
	 * @param lineNumber
	 *            the number of the line at which the variable is defined
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 * @param initialValue
	 *            initial value of this variable
	 */
	Var createVar(int lineNumber, Type type, String name, boolean assignable,
			Expression initialValue);

	/**
	 * Creates a new local variable with the given location, type, name, index.
	 * The variable may be assignable or not. Type is copied.
	 * 
	 * @param lineNumber
	 *            the number of the line at which the variable is defined
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 * @param index
	 *            index of this variable
	 */
	Var createVar(int lineNumber, Type type, String name, boolean assignable,
			int index);

	/**
	 * Creates a new local variable with the given type, name, index. The
	 * variable may be assignable or not. Type is copied.
	 * 
	 * @param type
	 *            the variable type
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 * @param index
	 *            index of this variable
	 */
	Var createVar(Type type, String name, boolean assignable, int index);

	/**
	 * Creates a new local integer variable with the given name, index. The
	 * variable may be assignable or not.
	 * 
	 * @param name
	 *            the variable name
	 * @param assignable
	 *            whether this variable is assignable
	 * @param index
	 *            index of this variable
	 */
	Var createVarInt(String name, boolean assignable, int index);

	/**
	 * Creates a new local integer variable with the given name, size, index.
	 * The variable may be assignable or not.
	 * 
	 * @param name
	 *            the variable name
	 * @param size
	 *            the size of the integer variable
	 * @param assignable
	 *            whether this variable is assignable
	 * @param index
	 *            index of this variable
	 */
	Var createVarInt(String name, int size, boolean assignable, int index);

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	IrPackage getIrPackage();

} // IrFactory
