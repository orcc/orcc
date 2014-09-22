package net.sf.orcc.backends.c

import fr.irisa.cairn.gecos.model.c.generator.ExtendableTypeCGenerator
import gecos.blocks.BasicBlock
import gecos.blocks.Block
import gecos.blocks.CompositeBlock
import gecos.blocks.ForBlock
import gecos.blocks.ForC99Block
import gecos.blocks.IfBlock
import gecos.blocks.LoopBlock
import gecos.blocks.SimpleForBlock
import gecos.blocks.SwitchBlock
import gecos.blocks.WhileBlock
import gecos.core.Procedure
import gecos.core.Scope
import gecos.core.Symbol
import gecos.instrs.DummyInstruction
import gecos.instrs.Instruction
import gecos.instrs.LabelInstruction
import org.eclipse.emf.ecore.EObject
import fr.irisa.cairn.gecos.gscop.GScopBlock
import org.polymodel.scop.AbstractScopNode
import fr.irisa.cairn.gecos.gscop.GScopFor
import fr.irisa.cairn.gecos.gscop.GScopGuard
import org.polymodel.algebra.IntConstraintSystem

class ActorBlockTemplate {

	String comma;
	String jump;
		
	public static ActorBlockTemplate eInstance = new ActorBlockTemplate();

	def dispatch generate(BasicBlock basicBlock) {
		val buffer = new StringBuffer()
		buffer.append('''
		''')
		for (Instruction instruction : basicBlock.instructions) {
			buffer.append('''«ActorInstructionTemplate::eInstance.generate(instruction)»''')
			if (!(instruction instanceof DummyInstruction || instruction instanceof LabelInstruction))
				buffer.append('''«comma»''')
			if (!(instruction instanceof DummyInstruction))
				buffer.append('''«jump»''')
		}
		'''«buffer»'''
	}

	def dispatch generate(CompositeBlock compositeBlock) {
		comma = ";"
		jump = " 
"
		if (compositeBlock.eContainer instanceof Procedure) {
			'''«ActorBlockTemplate::eInstance.generate(compositeBlock.children.get(1))»'''
		} else {
			val buffer = new StringBuffer()
			for (Block block : compositeBlock.children) {
				val charSequence = ActorBlockTemplate::eInstance.generate(block)
				buffer.append('''«charSequence»''')
			}
			'''«buffer.toString»'''
		}
	}

	def dispatch generate(ForBlock forBlock) {
		comma = ""
		jump = ""
		val buffer = new StringBuffer()
		buffer.append('''
		''')
		buffer.append('''«ActorBlockTemplate::eInstance.generate(forBlock.initBlock)»''')
		buffer.append('''while («ActorBlockTemplate::eInstance.generate(forBlock.testBlock)») do''');
		
		comma = ";"
		jump = "
"
		buffer.append('''«ActorBlockTemplate::eInstance.generate(forBlock.bodyBlock)»''')
		buffer.append('''«ActorBlockTemplate::eInstance.generate(forBlock.stepBlock)»''');
		buffer.append('''end''');
		'''«buffer»
		'''
	}

	def dispatch generate(ForC99Block forC99Block) {
		comma = ""
		jump = ""
		val buffer = new StringBuffer()
		buffer.append('''«printForScope(forC99Block.scope)»; ''')
		buffer.append('''«ActorBlockTemplate::eInstance.generate(forC99Block.testBlock)»; «ActorBlockTemplate::eInstance.generate(forC99Block.stepBlock)»)''')
		comma = ";"
		jump = "
"

		if (forC99Block.bodyBlock instanceof CompositeBlock) {
			buffer.append(''' ''')
			buffer.append('''«ActorBlockTemplate::eInstance.generate(forC99Block.bodyBlock)»''')
		}else if(forC99Block.bodyBlock instanceof BasicBlock && (forC99Block.bodyBlock as BasicBlock).instructionCount > 1) {
			buffer.append(''' {
				''')
			buffer.append('''	«ActorBlockTemplate::eInstance.generate(forC99Block.bodyBlock)»''')
			buffer.append('''
			}''')		
		}else
			buffer.append('''
				
				«ActorBlockTemplate::eInstance.generate(forC99Block.bodyBlock)»''')
		'''«buffer»
		'''
	}

	def dispatch generate(IfBlock ifBlock) {
		comma = ""
		jump = ""
		val buffer = new StringBuffer()
		buffer.append('''
		if («ActorBlockTemplate::eInstance.generate(ifBlock.testBlock)»)''')
		comma = ";"
		jump = "
"

		buffer.append('''then
		''')
		buffer.append('''«ActorBlockTemplate::eInstance.generate(ifBlock.thenBlock)»''')
		buffer.append('''end''')
				
		if (ifBlock.elseBlock != null) {
			buffer.append('''else ''')
			buffer.append('''«ActorBlockTemplate::eInstance.generate(ifBlock.elseBlock)»''')
			buffer.append('''end''')
		}
		'''«buffer.toString»
		'''
	}
	
	//FIXME if the bodyBlock is a basicBlock with more than 1 instruction then add {}
	def dispatch generate(LoopBlock loopBlock) {
		val buffer = new StringBuffer()
		buffer.append('''
		do «ActorBlockTemplate::eInstance.generate(loopBlock.bodyBlock)» while (''')
		comma = ""
		jump = ""
		buffer.append('''«ActorBlockTemplate::eInstance.generate(loopBlock.testBlock)»
		''')
		buffer.append(''');
		''')
		comma = ";"
		jump = "
"
		'''«buffer»'''
	}

	def dispatch generate(SimpleForBlock simpleForBlock) {
		comma = ""
		jump = ""
		val buffer = new StringBuffer()
		buffer.append('''for(''')
		if ((simpleForBlock.initBlock as BasicBlock).instructionCount == 0)
			buffer.append('''«printForScope(simpleForBlock.scope)»; ''')
		else
			buffer.append('''«ActorBlockTemplate::eInstance.generate(simpleForBlock.initBlock)»; ''')
		buffer.append('''«ActorBlockTemplate::eInstance.generate(simpleForBlock.testBlock)»; «ActorBlockTemplate::eInstance.generate(simpleForBlock.stepBlock)»)''')
		comma = ";"
		jump = "
"
		if (simpleForBlock.bodyBlock instanceof CompositeBlock) {
			buffer.append(''' ''')
			buffer.append('''«ActorBlockTemplate::eInstance.generate(simpleForBlock.bodyBlock)»''')
		}else if(simpleForBlock.bodyBlock instanceof BasicBlock && (simpleForBlock.bodyBlock as BasicBlock).instructionCount > 1) {
			buffer.append(''' {
				''')
			buffer.append('''	«ActorBlockTemplate::eInstance.generate(simpleForBlock.bodyBlock)»
			''')
			buffer.append('''
			}''')		
		}else
			buffer.append('''
			
				«ActorBlockTemplate::eInstance.generate(simpleForBlock.bodyBlock)»''')
		'''«buffer»
		'''
	}

	def dispatch generate(SwitchBlock switchBlock) {
		''''''
	}

	def dispatch generate(WhileBlock whileBlock) {
		comma = ""
		jump = ""
		val buffer = new StringBuffer()
		buffer.append('''while («ActorBlockTemplate::eInstance.generate(whileBlock.testBlock)»)  do''')
		buffer.append('''	«ActorBlockTemplate::eInstance.generate(whileBlock.bodyBlock)»''')
		buffer.append('''end''')
		comma = ";"
		jump = "
"
		'''«buffer»'''
	}

	def dispatch generate(GScopBlock scopBlock) {
		val buffer = new StringBuffer()
		//if (scopBlock.scope != null && !scopBlock.scope.symbols.empty)
		//	buffer.append('''	«ExtendableCGenerator::eInstance.generate(scopBlock.scope)»
		//		''')
		for (AbstractScopNode abstractScopNode : scopBlock.statements) {
			if (abstractScopNode instanceof Block)
				buffer.append('''	«ActorBlockTemplate::eInstance.generate(abstractScopNode)»
			''')
			else
				buffer.append('''	«ActorInstructionTemplate::eInstance.generate(abstractScopNode)»
			''')
		}
		'''«buffer.toString»'''
	}
	
	def dispatch generate(GScopFor scopFor) {
		val buffer = new StringBuffer()
		val lowerBound = new StringBuffer()
		val upperBound = new StringBuffer()
		val stride = new StringBuffer();
		stride.append('''«ActorInstructionTemplate::eInstance.generate(scopFor.stride)»''');
		
		if ( stride.toString.compareTo("1") == 0) {
			if (stride.toString.contains("-")) {
				lowerBound.append('''«ActorInstructionTemplate::eInstance.generate(scopFor.UB)»''')
				upperBound.append('''«ActorInstructionTemplate::eInstance.generate(scopFor.LB)»''')
			} else {
				lowerBound.append('''«ActorInstructionTemplate::eInstance.generate(scopFor.LB)»''')
				upperBound.append('''«ActorInstructionTemplate::eInstance.generate(scopFor.UB)»''')
			}
			buffer.append('''foreach int «scopFor.iterator.name» in «lowerBound» .. «upperBound» ''')
			buffer.append('''do
			''')
			if (scopFor.body instanceof Block) {
				buffer.append(" " + ActorBlockTemplate::eInstance.generate(scopFor.body) + '''
				''')
			} else {
				buffer.append('''

				''')
				buffer.append('''	«ActorInstructionTemplate::eInstance.generate(scopFor.body)»
				''')
			}	
			buffer.append('''end''')
		} else {
			if (stride.toString.contains("-")) {
				lowerBound.append('''«ActorInstructionTemplate::eInstance.generate(scopFor.UB)»''')
				upperBound.append(''' >= «ActorInstructionTemplate::eInstance.generate(scopFor.LB)»''')
			} else {
				lowerBound.append('''«ActorInstructionTemplate::eInstance.generate(scopFor.LB)»''')
				upperBound.append(''' <= «ActorInstructionTemplate::eInstance.generate(scopFor.UB)»''')
			}
			buffer.append('''«scopFor.iterator.name» := «lowerBound»;
			''')
			buffer.append('''while (''')
			buffer.append('''«scopFor.iterator.name»«upperBound») do
			''')
			if (scopFor.body instanceof Block) {
				buffer.append(" " + ActorBlockTemplate::eInstance.generate(scopFor.body) + '''
				''')
			} else {
				buffer.append('''

				''')
				buffer.append('''	«ActorInstructionTemplate::eInstance.generate(scopFor.body)»
				''')
			}
			buffer.append('''«scopFor.iterator.name» := «scopFor.iterator.name» + «stride»;
			''')
			buffer.append('''end''')
		}
		'''«buffer.toString»'''
	}
	
	def dispatch generate(GScopGuard scopGuard) {
		val buffer = new StringBuffer()
		buffer.append('''if (''')
		for (IntConstraintSystem intConstraintSystem : scopGuard.constraintSystems) {
			if (intConstraintSystem != scopGuard.constraintSystems.get(0))
				buffer.append(''' || ''')
			buffer.append('''«ActorInstructionTemplate::eInstance.generate(intConstraintSystem)»''')
		}
		if (scopGuard.thenBody instanceof Block)
			buffer.append(''') then 
			«ActorBlockTemplate::eInstance.generate(scopGuard.thenBody)»
			end ''')
		else
			buffer.append(''') then
			«ActorInstructionTemplate::eInstance.generate(scopGuard.thenBody)»
			end''')
		if (scopGuard.elseBody != null)
			buffer.append('''else	«ActorBlockTemplate::eInstance.generate(scopGuard.elseBody)»
			end''')
		'''«buffer.toString»'''
	}
	
		
	def dispatch generate(EObject object) {
		null
	}
	
		
	def printForScope(Scope scope) {
		val buffer = new StringBuffer()
		for(Symbol symbol : scope.symbols) {
			if (symbol.equals(scope.symbols.get(0)))
				buffer.append('''«ExtendableTypeCGenerator::eInstance.generate(symbol.type)»''')
			buffer.append(''' «symbol.name»''')
			if (symbol.value != null)
				buffer.append(''' = «ActorInstructionTemplate::eInstance.generate(symbol.value)»''')
			if (!symbol.equals(scope.symbols.get(scope.symbols.size - 1)))
				buffer.append(''',''')
		}
		buffer.append('''''')
	}
	
}