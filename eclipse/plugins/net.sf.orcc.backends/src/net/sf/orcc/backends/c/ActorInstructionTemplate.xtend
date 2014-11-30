package net.sf.orcc.backends.c

import fr.irisa.cairn.gecos.gscop.GScopInstruction
import fr.irisa.cairn.gecos.gscop.GScopIntConstraintSystem
import fr.irisa.cairn.gecos.gscop.GScopIntExpressionInstr
import fr.irisa.cairn.gecos.gscop.GScopRead
import fr.irisa.cairn.gecos.gscop.GScopWrite
import fr.irisa.cairn.gecos.model.c.generator.OperatorPrecedenceTools
import gecos.instrs.AddressInstruction
import gecos.instrs.ArrayInstruction
import gecos.instrs.ArrayValueInstruction
import gecos.instrs.BreakInstruction
import gecos.instrs.CallInstruction
import gecos.instrs.CondInstruction
import gecos.instrs.ContinueInstruction
import gecos.instrs.ConvertInstruction
import gecos.instrs.DummyInstruction
import gecos.instrs.EnumeratorInstruction
import gecos.instrs.FieldInstruction
import gecos.instrs.FloatInstruction
import gecos.instrs.GenericInstruction
import gecos.instrs.GotoInstruction
import gecos.instrs.IndirInstruction
import gecos.instrs.Instruction
import gecos.instrs.IntInstruction
import gecos.instrs.LabelInstruction
import gecos.instrs.RetInstruction
import gecos.instrs.SetInstruction
import gecos.instrs.SimpleArrayInstruction
import gecos.instrs.SizeofTypeInstruction
import gecos.instrs.SizeofValueInstruction
import gecos.instrs.SymbolInstruction
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.polymodel.algebra.ComparisonOperator
import org.polymodel.algebra.IntConstraint
import org.polymodel.algebra.IntConstraintSystem
import org.polymodel.algebra.IntExpression
import org.polymodel.algebra.Variable
import org.polymodel.algebra.affine.AffineExpression
import org.polymodel.algebra.affine.AffineTerm
import org.polymodel.algebra.prettyprinter.algebra.OUTPUT_FORMAT
import org.polymodel.algebra.quasiAffine.NestedQuasiAffineTerm
import org.polymodel.algebra.quasiAffine.QuasiAffineExpression
import org.polymodel.algebra.quasiAffine.QuasiAffineOperator
import org.polymodel.algebra.quasiAffine.QuasiAffineTerm
import org.polymodel.algebra.quasiAffine.SimpleQuasiAffineTerm
import org.polymodel.algebra.reductions.ReductionExpression
import org.polymodel.algebra.reductions.ReductionOperator
import org.polymodel.scop.ScopAssignment
import org.polymodel.algebra.CompositeIntExpression
import org.polymodel.algebra.CompositeOperator


import java.util.ArrayList
import gecos.types.Type
import gecos.types.*
import org.polymodel.algebra.FuzzyBoolean

class ActorInstructionTemplate {
	
	public static ActorInstructionTemplate eInstance = new ActorInstructionTemplate();

    def printInst(GScopInstruction inst ) {
    	'''«inst.generate»'''
    }
    
    def dispatch generate(GScopInstruction scopInstruction) {
		val buffer = new StringBuffer()
		for (Instruction instruction : scopInstruction.children) {
			buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»;''')
		}
		'''«buffer.toString»'''
	}
	
	def dispatch generate(ScopAssignment assign) {
		'''«assign.LHS.name» := «ActorInstructionTemplate::eInstance.generate(assign.RHS)»;
		'''
	}

	def dispatch generate(GScopIntExpressionInstr scopIntExpressionInstr) {
		'''«ActorInstructionTemplate::eInstance.generate(scopIntExpressionInstr.expression)»'''
	}
	
	def dispatch generate(GScopIntConstraintSystem scopIntConstraintSystem) {
		'''/* gScopIntConstraintSystem */'''
	}
	
	
	
	def dispatch generate(GScopWrite scopWrite) {
		val buffer = new StringBuffer()
		buffer.append('''«scopWrite.getSymbol().name»''')
		val numDims = scopWrite.index.size;
		var List<Instruction> size = ActorTemplate.arraySizeMap.get(scopWrite.getSymbol());//.getType());
		if ( numDims > 0 )
			buffer.append("[")
		var i = 0;
			
		for(IntExpression intExpression : scopWrite.index) {
			buffer.append('''(«ActorInstructionTemplate::eInstance.generate(intExpression)») ''')
			if ( i < (numDims - 1) ) {
				buffer.append('''*  «ActorInstructionTemplate::eInstance.generate(size.get(i))»  +''');
			}
			i = i + 1;
		}
		if ( numDims > 0 )
			buffer.append("]");
		'''«buffer.toString»'''
	}
	
	def dispatch generate(GScopRead scopRead) {
		val buffer = new StringBuffer()
		buffer.append('''«scopRead.getSymbol().name»''')
		val numDims = scopRead.index.size;
		var List<Instruction> size = ActorTemplate.arraySizeMap.get(scopRead.getSymbol());//getSizeArray(scopRead.getSymbol().getType());
		if ( numDims > 0 )
			buffer.append("[");
		
		var i = 0;
		for(IntExpression intExpression : scopRead.index) {
			buffer.append('''(«ActorInstructionTemplate::eInstance.generate(intExpression)»)''')
			if ( i < (numDims - 1) ) {
				buffer.append('''*  «ActorInstructionTemplate::eInstance.generate(size.get(i))»  +''');
			}
			i = i + 1;
		}
		if ( numDims > 0 )
			buffer.append("]");
		'''«buffer.toString»'''
	}
	
//	def List<Integer> getSizeArray(Type arrayType) {
//		var sizeArray = new ArrayList<Integer>();
//		var type = arrayType;
//		while ( type instanceof ArrayType ) {
//			type = (type as ArrayType).getBase();
//			var size = 1L;
//			if ( type instanceof ArrayType ) {
//				var Instruction expr = (type as ArrayType).getSizeExpr();
//		
//				if ( expr instanceof IntInstruction ) {
//					size = (expr as IntInstruction).getValue();
//				} else if ( expr instanceof GScopIntExpressionInstr) {
//					var IntExpression intExpr = (expr as GScopIntExpressionInstr).getExpression();
//					if ( intExpr.isConstant() == FuzzyBoolean.YES ) {
//						size = intExpr.toAffine().getConstantTerm().getCoef();
//					} else {
//						throw new UnsupportedOperationException();
//					}
//				} else {
//					size = type.getSize();
//				}
//				System.out.println("Size: " + size);
//			}
//			var List<Integer> newSizeArray = new ArrayList<Integer>();
//			var i = 0;
//			for ( i = 0; i < sizeArray.size(); i++ ) {
//				var a = sizeArray.get(i) * size;
//				var Integer a1 = new Integer(a as int);
//				newSizeArray.add(a1);
//			}
//			sizeArray.clear();
//			sizeArray.addAll(newSizeArray);
//			var Integer a2 = new Integer(size as int);
//			sizeArray.add(a2);
//	}
//	sizeArray
//		
//	}
//    
	def dispatch generate(AddressInstruction addressInstruction) {
		'''«ActorInstructionTemplate::eInstance.generate(addressInstruction.address)»'''
	} 

	def dispatch generate(ArrayInstruction arrayInstruction) {
		val buffer = new StringBuffer()
		buffer.append('''«ActorInstructionTemplate::eInstance.generate(arrayInstruction.dest)»''')
//		for (Instruction instruction:arrayInstruction.index) {
//			buffer.append('''[«ActorInstructionTemplate::eInstance.generate(instruction)»]''')
//		}
		val numDims = arrayInstruction.index.size;
		var List<Instruction> size = ActorTemplate.arraySizeMap.get((arrayInstruction.dest as SymbolInstruction).getSymbol());//getSizeArray(scopRead.getSymbol().getType());
		var i = 0;
		buffer.append("[");
		for(Instruction inst : arrayInstruction.index) {
			buffer.append('''(«ActorInstructionTemplate::eInstance.generate(inst)»)''')
			if ( i < (numDims - 1) ) {
				buffer.append('''*  «ActorInstructionTemplate::eInstance.generate(size.get(i))»  +''');
			}
			i = i + 1;
		}
		buffer.append("]");
		'''«buffer»'''
	}

	def dispatch generate(ArrayValueInstruction arrayValueInstruction) {
		val buffer = new StringBuffer()
		buffer.append('''{''')
		for (Instruction instruction:arrayValueInstruction.children) {
			buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
			if (arrayValueInstruction.children.indexOf(instruction) < arrayValueInstruction.children.size - 1)
				buffer.append(", ")
		}
		buffer.append('''}''')
	}
	
	def dispatch generate(BreakInstruction breakInstruction) {
		'''break'''
	}

	def dispatch generate(CallInstruction callInstruction) {
		val buffer = new StringBuffer()
		if ( callInstruction.address instanceof SymbolInstruction ) {
			buffer.append(addBufferCopyCode(callInstruction, callInstruction.address));	
		} 
		if ( buffer.length == 0 ) {
			buffer.append('''«ActorInstructionTemplate::eInstance.generate(callInstruction.address)»''')
			buffer.append("(")
			for (Instruction instruction : callInstruction.args) {
				buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
				if (callInstruction.children.indexOf(instruction) < callInstruction.children.size - 1)
					buffer.append(", ")
			}
			buffer.append(")")
		}
		'''«buffer»'''
	}
	
	def dispatch addBufferCopyCode(CallInstruction callInstruction, SymbolInstruction sym) {
		val buffer = new StringBuffer()
		if (sym.symbolName.contains("copyAndIncrement") ) {
			val value = '''«ActorInstructionTemplate::eInstance.generate(callInstruction.args.get(0))»'''
			val addr = '''«ActorInstructionTemplate::eInstance.generate(callInstruction.args.get(1))»'''
			val index = '''«ActorInstructionTemplate::eInstance.generate(callInstruction.args.get(2))»'''
			buffer.append(addr+" := "+value+";\n")
			buffer.append(index+" := "+index+" + 1")
		}
		'''«buffer»'''
	}
	
	def dispatch addBufferCopyCode(CallInstruction callInstruction, Instruction sym) {
		val buffer = new StringBuffer()
		'''«buffer»'''
	}

	def dispatch generate(CondInstruction condInstruction) {
		'''«generate(condInstruction.cond)»'''
	}
	
	def dispatch generate(ContinueInstruction continueInstruction) {
		'''continue'''
	}

	def dispatch generate(ConvertInstruction convertInstruction) {
		val buffer = new StringBuffer()
		val needParenthesis = OperatorPrecedenceTools::needParenthesis(convertInstruction)
		if (needParenthesis)
			buffer.append('''(''')
//		if (convertInstruction.eContainer instanceof Instruction && !(convertInstruction.eContainer instanceof RetInstruction))
//			buffer.append('''((«gecosTypeTemplate.generate(convertInstruction.type, stringBuffer)»)«ActorInstructionTemplate::eInstance.generate(convertInstruction.expr)»)''')
//		else
			//buffer.append('''(«ExtendableTypeCGenerator::eInstance.generate(convertInstruction.type)»)«ActorInstructionTemplate::eInstance.generate(convertInstruction.expr)»''')
			buffer.append('''«ActorInstructionTemplate::eInstance.generate(convertInstruction.expr)»''')			
		if (needParenthesis)
			buffer.append(''')''')
		'''«buffer.toString»'''
	}

	def dispatch generate(DummyInstruction dummyInstruction) {
		''''''
	}

 	def dispatch generate(EnumeratorInstruction enumeratorInstruction) {
		''''''
	} 

	def dispatch generate(FieldInstruction fieldInstruction) {
		''''''
	} 

	def dispatch generate(FloatInstruction floatInstruction) {
		'''«floatInstruction.value»'''
	}

	def dispatch generate(GenericInstruction genericInstruction) {
		val buffer = new StringBuffer()
		val needParenthesis = OperatorPrecedenceTools::needParenthesis(genericInstruction)
		if (needParenthesis)
			buffer.append('''(''')
		switch (genericInstruction.name) {
			case "add" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "+")»''')
			case "sub" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "-")»''')
			case "mul" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "*")»''')
			case "div" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "/")»''')
			case "shl" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "<<")»''')
			case "shr" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, ">>")»''')
			case "neg" : buffer.append('''-«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))»''')
			case "not" : buffer.append('''~«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))»''')
			case "lnot" : buffer.append('''!«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))»''')
			case "eq" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "=")»''')
			case "ne" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "!=")»''')
			case "neq" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "!=")»''')
			case "lt" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "<")»''')
			case "le" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, "<=")»''')
			case "gt" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, ">")»''')
			case "ge" : buffer.append('''«generateInstructionWithMultipleSubInstruction(genericInstruction.operands, ">=")»''')
			case "land" :{
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(" && ")
				}
			}
			case "and" :{
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(" & ")
				}
			}
			case "lor" :{
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(" || ")
				}
			}
			case "or" :{
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(" | ")
				}
			}
			case "xor" :{
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(" ^ ")
				}
			}
			case "mux" :{
				if (genericInstruction.operands.size == 3) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))»''')
					buffer.append(" ? ")
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(1))»''')
					buffer.append(" : ")
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(2))»''')
				}
			}
			case "sequence" :{
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(", ")
				}
			}
			case "min" :{
				//in order to generate min macro
				genericInstruction.basicBlock.containingProcedureSet.setAnnotation("need_define_min", null);
				
				for(i : 0..genericInstruction.operands.size()-2)
					buffer.append("min(")
					
				var tmp=false
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if(!tmp) 
						tmp = true
					else
						buffer.append(")")
						
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(", ")
				}
			}
			case "max" :{
				//in order to generate max macro
				genericInstruction.basicBlock.containingProcedureSet.setAnnotation("need_define_max", null);
				
				for(i : 0..genericInstruction.operands.size()-2)
					buffer.append("max(")
					
				var tmp=false
				for (Instruction instruction : genericInstruction.operands) {
					buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
					if(!tmp) 
						tmp = true
					else
						buffer.append(")")
						
					if (instruction !== (genericInstruction.operands.get(genericInstruction.operands.size - 1)))
						buffer.append(", ")
				}
			}
			case "mod" : {
				genericInstruction.basicBlock.containingProcedureSet.setAnnotation("need_define_mod", null);
				buffer.append('''_custom_mod_(«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))», «ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(1))»)''')
			}
			case "mmod" : {
				genericInstruction.basicBlock.containingProcedureSet.setAnnotation("need_define_mod", null);
				buffer.append('''_custom_mod_(«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))», «ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(1))»)''')
			}
			case "floor" : {
				//in order to generate floor macro
				genericInstruction.basicBlock.containingProcedureSet.setAnnotation("need_define_floor", null);
				buffer.append('''floord(«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))», «ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(1))»)''')
			}
			case "ceil" : {
				//in order to generate ceil macro
				genericInstruction.basicBlock.containingProcedureSet.setAnnotation("need_define_ceil", null);
				buffer.append('''ceild(«ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(0))», «ActorInstructionTemplate::eInstance.generate(genericInstruction.operands.get(1))»)''')
			}
			
			default : 
					buffer.append('''/*GenericInstruction : «genericInstruction»*/''')
		}
		if (needParenthesis)
			buffer.append(''')''')
		'''«buffer.toString»'''
	}
	
	def generateInstructionWithMultipleSubInstruction(List<Instruction> subInstructions, String operator) {
		val buffer = new StringBuffer()
		var first = true
		for (instruction : subInstructions) {
			if (first) first = false
			else buffer.append(''' «operator» ''')
			buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
		}
		'''«buffer»'''
	}

	def dispatch generate(GotoInstruction gotoInstruction) {
		'''goto «gotoInstruction.labelName»'''
	}

	def dispatch generate(IndirInstruction indirInstruction) {
		'''*«ActorInstructionTemplate::eInstance.generate(indirInstruction.address)»'''
	}

	def dispatch generate(IntInstruction intInstruction) {
		'''«intInstruction.value»'''
	}

	def dispatch generate(LabelInstruction labelInstruction) {
		'''«labelInstruction.name»:'''
	}

	def dispatch generate(RetInstruction retInstruction) {
		'''return «ActorInstructionTemplate::eInstance.generate(retInstruction.children.get(0))»'''
	}

	def dispatch generate(SetInstruction setInstruction) {
		val buffer = new StringBuffer()
		val needParenthesis = OperatorPrecedenceTools::needParenthesis(setInstruction)
		if (needParenthesis)
			buffer.append('''(''')
		buffer.append('''«ActorInstructionTemplate::eInstance.generate(setInstruction.dest)» := «ActorInstructionTemplate::eInstance.generate(setInstruction.source)»''')
		if (needParenthesis)
			buffer.append(''')''')
		'''«buffer»'''
	}

	def dispatch generate(SimpleArrayInstruction simpleArrayInstruction) {
		val buffer = new StringBuffer()
		buffer.append('''«ActorInstructionTemplate::eInstance.generate(simpleArrayInstruction.dest)»''')
//		for(Instruction instruction : simpleArrayInstruction.index) {
//			buffer.append('''[«ActorInstructionTemplate::eInstance.generate(instruction)»]''')
//		}
		val numDims = simpleArrayInstruction.index.size;
		var List<Instruction> size = ActorTemplate.arraySizeMap.get((simpleArrayInstruction.dest as SymbolInstruction).getSymbol());//getSizeArray(scopRead.getSymbol().getType());
		var i = 0;
		buffer.append("[");
		for(Instruction inst : simpleArrayInstruction.index) {
			buffer.append('''(«ActorInstructionTemplate::eInstance.generate(inst)»)''')
			if ( i < (numDims - 1) ) {
				buffer.append('''*  «ActorInstructionTemplate::eInstance.generate(size.get(i))»  +''');
			}
			i = i + 1;
		}
		buffer.append("]")
		'''«buffer»'''
	}

	def dispatch generate(SizeofTypeInstruction sizeofTypeInstruction) {
		'''4'''
	}

	def dispatch generate(SizeofValueInstruction sizeofValueInstruction) {
		
		'''sizeof «ActorInstructionTemplate::eInstance.generate(sizeofValueInstruction.targetInst)»'''
	}

	def dispatch generate(SymbolInstruction symbolInstruction) {
		'''«symbolInstruction.symbolName»'''
	}
	
	def dispatch generate(AffineExpression affineExpression) {
//		val buffer = new StringBuffer()
//		for (AffineTerm affineTerm : affineExpression.terms) {
//			buffer.append('''«affineTerm.generate»''')
//			if (affineTerm != affineExpression.terms.last)
//				buffer.append(''' + ''')
//		}
//		'''«buffer.toString»'''
		'''«affineExpression.toString(OUTPUT_FORMAT::C)»'''
	}
	
	def dispatch generate(AffineTerm affineTerm) {
		val buffer = new StringBuffer()
		if (affineTerm.coef == new Long(1) && affineTerm.variable != null)
			buffer.append('''«affineTerm.variable.generate»''')
		else {
			buffer.append('''«affineTerm.coef»''')
			if (affineTerm.coef != 0 && affineTerm.variable != null)
				buffer.append(''' * «affineTerm.variable.generate»''')
		}
		'''«buffer.toString»'''
	}
		
	def dispatch generate(QuasiAffineExpression quasiAffineExpression) {
		val buffer = new StringBuffer()
		for (QuasiAffineTerm quasiAffineTerm : quasiAffineExpression.terms) {
			buffer.append('''«quasiAffineTerm.generate»''')
			if (quasiAffineTerm != quasiAffineExpression.terms.last)
				buffer.append(''' + ''')
		}
		'''«buffer.toString»'''
	}
	
	def generateQuasiAffineTerm(QuasiAffineTerm quasiAffineTerm, IntExpression expression) {
		val buffer = new StringBuffer()
		if ( expression != null ) {
		switch quasiAffineTerm.operator {
			case QuasiAffineOperator::CEIL : buffer.append('''ceild(«expression.generate», «quasiAffineTerm.coef»)''')
			case QuasiAffineOperator::DIV : buffer.append('''(«expression.generate») / «quasiAffineTerm.coef»''')
			case QuasiAffineOperator::FLOOR : buffer.append('''floord(«expression.generate», «quasiAffineTerm.coef»)''')
			case QuasiAffineOperator::MOD : buffer.append('''_custom_mod(«expression.generate», «quasiAffineTerm.coef»)''')
			case QuasiAffineOperator::MUL : buffer.append('''(«expression.generate») * «quasiAffineTerm.coef»''')
		}
		} else {
			buffer.append("null");
		}
		'''«buffer.toString»'''
	}
	
	def dispatch generate(SimpleQuasiAffineTerm simpleQuasiAffineTerm) {
		val buffer = new StringBuffer()
		buffer.append('''«simpleQuasiAffineTerm.generateQuasiAffineTerm(simpleQuasiAffineTerm.expression)»''')
		'''«buffer.toString»'''
	}
	
	def dispatch generate(NestedQuasiAffineTerm nestedQuasiAffineTerm) {
		val buffer = new StringBuffer()
		buffer.append('''«nestedQuasiAffineTerm.generateQuasiAffineTerm(nestedQuasiAffineTerm.expression)»''')
		'''«buffer.toString»'''
	}
	
	def dispatch generate(CompositeIntExpression expr) {
		val buffer = new StringBuffer()
		switch(expr.operator) {
		case CompositeOperator.DIV:  buffer.append('''(«expr.left») / («expr.right»)''')
		case CompositeOperator.MOD: buffer.append('''_custom_mod(«expr.left», «expr.right»)''')
		}
		'''«buffer.toString»'''
	}
	
	def dispatch generate(ReductionExpression reductionExpression) {
		val buffer = new StringBuffer()
		val separator = new StringBuffer()
		if (reductionExpression.operator == ReductionOperator::PROD)
			separator.append(" * ")
		else if (reductionExpression.operator == ReductionOperator::SUM)
			separator.append(" + ")
		else {
			var i = 0;
			while ( i < reductionExpression.expressions.size - 1 ) {
				buffer.append('''«reductionExpression.operator.generateReductionOperator»(''')
				i = i + 1;	
			}
			var first = 1;
			for (IntExpression intExpression : reductionExpression.expressions) {
				buffer.append('''«ActorInstructionTemplate::eInstance.generate(intExpression)»''')
				if ( first == 1) {
					buffer.append(''',''')
				} else if (intExpression != reductionExpression.expressions.last) {
					buffer.append('''),''')
				} else 
					buffer.append(''')''')
				first = 0;
			}
		}
		if ( (reductionExpression.operator == ReductionOperator::PROD) || 
				(reductionExpression.operator == ReductionOperator::SUM) ) {
			for (IntExpression intExpression : reductionExpression.expressions) {
				buffer.append('''«ActorInstructionTemplate::eInstance.generate(intExpression)»''')
				if (intExpression != reductionExpression.expressions.last)
					buffer.append('''«separator.toString»''')
			}
		}
		'''«buffer.toString»'''
	}
	def generateReductionOperator(ReductionOperator reductionOperator) {
		val buffer = new StringBuffer()
		switch reductionOperator {
			case ReductionOperator::MAX : buffer.append('''max''')
			case ReductionOperator::MIN : buffer.append('''min''')
		}
		'''«buffer.toString»'''
	}
	
	def dispatch generate(IntConstraintSystem intConstraintSystem) {
		val buffer = new StringBuffer()
		if ( intConstraintSystem.constraints.size == 0 ) {
			buffer.append('''true''')
		}
		for (IntConstraint intConstraint : intConstraintSystem.constraints) {
			if (intConstraint != intConstraintSystem.constraints.get(0))
				buffer.append(''' && ''')
			buffer.append('''(«ActorInstructionTemplate::eInstance.generate(intConstraint)»)''')
		}
		'''«buffer.toString»'''
//		'''«intConstraintSystem.toString(OUTPUT_FORMAT::C)»'''
	}
	
	def dispatch generate(IntConstraint intConstraint) {
		val buffer = new StringBuffer()
		buffer.append('''(«ActorInstructionTemplate::eInstance.generate(intConstraint.lhs)»)''')
		switch intConstraint.comparisonOperator {
			case ComparisonOperator::EQ : buffer.append(''' = ''')
			case ComparisonOperator::GE : buffer.append(''' >= ''')
			case ComparisonOperator::GT : buffer.append(''' > ''')
			case ComparisonOperator::LE : buffer.append(''' <= ''')
			case ComparisonOperator::LT : buffer.append(''' < ''')
			case ComparisonOperator::NE : buffer.append(''' != ''')
		}
		buffer.append('''(«ActorInstructionTemplate::eInstance.generate(intConstraint.rhs)»)''')
		'''«buffer.toString»'''
//		'''«intConstraint.toString(OUTPUT_FORMAT::C)»'''
	}
	
	def dispatch generate(Variable variable) {
		'''«variable.name»'''
	}

	def dispatch generate(EObject object) {
		//org.polymodel.algebra.generator.c.ExpressionTemplate.generate(object);
		return null;
	}
}