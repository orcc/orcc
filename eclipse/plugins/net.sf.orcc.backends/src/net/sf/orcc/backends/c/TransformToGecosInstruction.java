package net.sf.orcc.backends.c;

import java.util.List;

import net.sf.orcc.OrccRuntimeException;
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
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import fr.irisa.cairn.gecos.model.factory.GecosUserCoreFactory;
import fr.irisa.cairn.gecos.model.factory.GecosUserInstructionFactory;
import fr.irisa.cairn.gecos.model.factory.GecosUserTypeFactory;
import gecos.instrs.SimpleArrayInstruction;


public class TransformToGecosInstruction {
	
	public static gecos.types.Type convertToGeCosType(Type type, gecos.core.Procedure proc) {
		if ( type.isBool() )
			return GecosUserTypeFactory.BOOL();
		else if ( type.isInt() ) 
			return GecosUserTypeFactory.INT();
		else if ( type.isFloat() ) {
			return GecosUserTypeFactory.FLOAT();
		} else if ( type.isUint() ) {
			return GecosUserTypeFactory.UINT();
		} else if ( type.isVoid() ) {
			return GecosUserTypeFactory.VOID();
		} else if ( type.isString() ) {
			return GecosUserTypeFactory.ARRAY(GecosUserTypeFactory.CHAR(), type.getSizeInBits());
		} else if ( type.isList() ) {
			TypeList list = (TypeList)type;
			return GecosUserTypeFactory.ARRAY(convertToGeCosType(list.getType(), proc), convertToGecosInst(list.getSizeExpr(), proc));
		}
		return null;
	}
	
	public static gecos.types.Type convertToGeCosType(Type type) {
		if ( type.isBool() )
			return GecosUserTypeFactory.BOOL();
		else if ( type.isInt() ) 
			return GecosUserTypeFactory.INT();
		else if ( type.isFloat() ) {
			return GecosUserTypeFactory.FLOAT();
		} else if ( type.isUint() ) {
			return GecosUserTypeFactory.UINT();
		} else if ( type.isVoid() ) {
			return GecosUserTypeFactory.VOID();
		} else if ( type.isString() ) {
			return GecosUserTypeFactory.ARRAY(GecosUserTypeFactory.CHAR(), type.getSizeInBits());
		} else if ( type.isList() ) {
			TypeList list = (TypeList)type;
			//return GecosUserTypeFactory.PTR(convertToGeCosType(list.getType()));
			//FIXME:  The expression should be parsed and used
			return GecosUserTypeFactory.ARRAY(convertToGeCosType(list.getType()), list.getSize());
		}
		return null;
	}

		
	static gecos.instrs.Instruction processBinaryExpression(ExprBinary expr, gecos.core.Procedure proc) {
		OpBinary op = expr.getOp();
		gecos.instrs.Instruction a = convertToGecosInst(expr.getE1(), proc);
		gecos.instrs.Instruction b = convertToGecosInst(expr.getE2(), proc);
		gecos.types.Type type = convertToGeCosType(expr.getType(),proc);
		proc.getScope().getTypes().add(type);
		if ( op == OpBinary.BITAND ) {
			 
		} else if ( op == OpBinary.BITOR ) {
			
		} else if ( op == OpBinary.BITXOR ) {
			
		} else if ( op == OpBinary.SHIFT_LEFT ) {
			return GecosUserInstructionFactory.shl(a , b);
		} else if (op == OpBinary.SHIFT_RIGHT ) {
			return GecosUserInstructionFactory.shr(a, b);
		} else if ( (op == OpBinary.DIV) || (op == OpBinary.DIV_INT) ) {
			 return GecosUserInstructionFactory.div(a, b);
		} else if  ( op == OpBinary.MINUS) {
			return GecosUserInstructionFactory.sub(a, b);
		} else if ( op == OpBinary.EXP ) {
			throw new UnsupportedOperationException("Operation EXP not supported ");
		} else if ( op == OpBinary.PLUS ) {
			gecos.instrs.Instruction instr = GecosUserInstructionFactory.add(a, b);
			instr.setType(type);
			return instr;
		} else if ( op == OpBinary.MOD ) {
			throw new UnsupportedOperationException("Operation MOD not supported ");
		} else if ( op == OpBinary.TIMES ) {
			return GecosUserInstructionFactory.mul(a, b);
		} else if ( op == OpBinary.EQ ) {
			return GecosUserInstructionFactory.eq(a, b, type ); 
		} else if ( op == OpBinary.GE ) {
			return GecosUserInstructionFactory.ge(a, b, type);
		} else if ( op == OpBinary.GT ) {
			return GecosUserInstructionFactory.gt(a, b, type);
		} else if ( op == OpBinary.LE ) {
			return GecosUserInstructionFactory.le(a, b, type);
		} else if ( op == OpBinary.LT ) {
			return GecosUserInstructionFactory.lt(a, b, type);
		} else if ( op == OpBinary.NE ) {
			//return GecosUserInstructionFactory. (a, b, convertToGeCosType(expr.getType()));
		} else if ( op == OpBinary.LOGIC_AND ) {
			return GecosUserInstructionFactory.land(a, b);
		} else if ( op == OpBinary.LOGIC_OR ) {
			return GecosUserInstructionFactory.lor(a, b);
		}
		return null;
	}
	
	static gecos.instrs.Instruction processUnaryExpression(ExprUnary expr, gecos.core.Procedure proc) {
		OpUnary op = expr.getOp();
		gecos.instrs.Instruction a = convertToGecosInst(expr.getExpr(), proc);
		if ( op == OpUnary.BITNOT ) {
			return GecosUserInstructionFactory.not(a);
		} else if ( op == OpUnary.LOGIC_NOT ) {
			return GecosUserInstructionFactory.lnot(a);
		} else if ( op == OpUnary.MINUS ) {
			return GecosUserInstructionFactory.neg(a);
		}
		return null;
	}
	
	static gecos.instrs.Instruction convertToGecosInst(Expression expr, gecos.core.Procedure proc) {
		gecos.types.Type type = convertToGeCosType(expr.getType());
		proc.getScope().getTypes().add(type);
		if ( expr instanceof ExprBinary) {
			return processBinaryExpression((ExprBinary)expr, proc);
		} else if ( expr instanceof ExprUnary ) {
			return processUnaryExpression((ExprUnary)expr, proc);
		} else if ( (expr instanceof ExprBool) || (expr instanceof ExprInt)) {
			long val = 0;
			if ( expr instanceof ExprInt) {
				val = ((ExprInt)expr).getLongValue();
			}
			return GecosUserInstructionFactory.Int(val);
		} else if ( expr instanceof ExprFloat ) {
			throw new OrccRuntimeException("Unhandled expression type in actor splitting pass");
		} else if ( expr instanceof ExprString ) {
			throw new OrccRuntimeException("Unhandled expression type in actor splitting pass");
		} else if ( expr instanceof ExprVar ) {
			ExprVar exprVar = (ExprVar)expr;
			String name = exprVar.getUse().getVariable().getName();
			Var v = exprVar.getUse().getVariable();
			if ( !v.isAssignable() ) {
				Expression e = v.getInitialValue();
				if ( e instanceof ExprInt ) {
					long val = ((ExprInt)e).getLongValue();
					return GecosUserInstructionFactory.Int(val);
				}
			}
			return createSymbolInst(name, expr.getType(), proc);
		} else if ( expr instanceof ExprList ) {
			throw new OrccRuntimeException("Unhandled expression type in actor splitting pass");
		} else {
			throw new OrccRuntimeException("Unhandled expression type in actor splitting pass");
		}
	}
	
	static gecos.instrs.SymbolInstruction createSymbolInst(String name, Type type, gecos.core.Procedure proc) {
		gecos.core.Scope s = proc.getBody().getScope();
		gecos.core.Symbol sym = s.lookup(name);
		if ( sym == null ) {
			sym = GecosUserCoreFactory.symbol(name, convertToGeCosType(type), proc.getBody().getScope());
			proc.getBody().getScope().getSymbols().add(sym);
		}
		return GecosUserInstructionFactory.symbref(sym);
	}
	
	static gecos.instrs.ArrayInstruction createArrayInst(String name, Type type, List<Expression> indices, gecos.core.Procedure proc) {
		gecos.core.Scope s = proc.getBody().getScope();
		gecos.core.Symbol sym = s.lookup(name);
		if ( sym == null ) {
			sym = GecosUserCoreFactory.symbol(name, convertToGeCosType(type, proc)); //, proc.getBody().getScope());
			proc.getBody().getScope().getSymbols().add(sym);
		}
		gecos.instrs.Instruction[] indexList = new gecos.instrs.Instruction[indices.size()];
		int i = 0;
		for ( Expression expr : indices ) {
			indexList[i] = convertToGecosInst(expr, proc);
			i++;
		}
		SimpleArrayInstruction res = GecosUserInstructionFactory.array(sym, indexList);
		res.setType(convertToGeCosType(type, proc));
		return res;
	}
		
	static gecos.instrs.Instruction convertToGecosInst(Instruction inst, gecos.core.Procedure proc) {
		if ( inst instanceof InstAssign) {
			InstAssign assignInst = (InstAssign)inst;
			Def tgt = assignInst.getTarget();
			gecos.types.Type type = convertToGeCosType(tgt.getVariable().getType());
			proc.getScope().getTypes().add(type);
			gecos.instrs.SymbolInstruction symInst = createSymbolInst(tgt.getVariable().getName(), 
														tgt.getVariable().getType(), proc);
			return GecosUserInstructionFactory.set(symInst, convertToGecosInst(assignInst.getValue(), proc), symInst.getType());
		} else if ( inst instanceof InstLoad ) {
			InstLoad ld = (InstLoad)inst;
			Def tgt = ld.getTarget();
			String name = tgt.getVariable().getName();
			gecos.types.Type type = convertToGeCosType(tgt.getVariable().getType());
			proc.getScope().getTypes().add(type);
			gecos.instrs.SymbolInstruction symInst = createSymbolInst(name, tgt.getVariable().getType(), proc);
			Use src = ld.getSource();
			if ( !src.getVariable().isAssignable() )
				System.out.println("Value is " + src.getVariable().getInitialValue());
			String src_name = src.getVariable().getName();
			Type src_type = src.getVariable().getType();
			gecos.instrs.Instruction value = null;
			gecos.types.Type stype = convertToGeCosType(src_type);
			proc.getScope().getTypes().add(stype);
			if ( ld.getIndexes().size() != 0 ) {
				value = createArrayInst(src_name, src_type, ld.getIndexes(), proc);
			} else {
				value = createSymbolInst(src_name, src_type, proc);
			}
			return GecosUserInstructionFactory.set(symInst, value); 
		} else if ( inst instanceof InstStore ) {
			InstStore st = (InstStore)inst;
			Def tgt = st.getTarget();
			String tgt_name = tgt.getVariable().getName();
			Type tgt_type = tgt.getVariable().getType();
			gecos.types.Type ttype = convertToGeCosType(tgt_type);
			proc.getScope().getTypes().add(ttype);
			gecos.instrs.Instruction sym = null;
			if ( st.getIndexes().size() != 0 ) {
				sym = createArrayInst(tgt_name, tgt_type, st.getIndexes(), proc);
			} else {
				sym = createSymbolInst(tgt_name, tgt_type, proc);
			}
			return GecosUserInstructionFactory.set(sym, convertToGecosInst(st.getValue(), proc));
		} else if ( inst instanceof InstCall ) {
			throw new UnsupportedOperationException("Call not yet implemented");
		} else if ( inst instanceof InstReturn ) {
			InstReturn ret = (InstReturn)inst;
			gecos.instrs.Instruction retValue = null;
			if ( ret.getValue() != null ) {
				retValue = convertToGecosInst(ret.getValue(), proc);
			} else {
				retValue = GecosUserInstructionFactory.Int(0);
			}
			return GecosUserInstructionFactory.ret(retValue);
		} else {
			throw new OrccRuntimeException("Unhandled Instruction type in actor splitting pass");
		}

	}
	
}
