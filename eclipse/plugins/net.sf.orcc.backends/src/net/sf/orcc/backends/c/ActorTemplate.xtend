package net.sf.orcc.backends.c.compa


import gecos.core.Symbol
import java.util.List
import gecos.core.Procedure
import gecos.instrs.Instruction
import gecos.core.ParameterSymbol
import fr.irisa.cairn.gecos.model.c.generator.ExtendableCoreCGenerator
import fr.irisa.cairn.gecos.gscop.LoopIterator
import fr.irisa.cairn.gecos.gscop.GScopInstruction
import fr.irisa.cairn.gecos.gscop.GScopBlock
import org.polymodel.algebra.IntConstraintSystem
import fr.irisa.cairn.gecos.gscop.GScopFSMCommand
import org.eclipse.emf.common.util.EList
import fr.irisa.cairn.gecos.gscop.GScopFSMTransition
import fr.irisa.cairn.gecos.gscop.GScopFSM
import org.polymodel.algebra.IntExpression
import fr.irisa.cairn.gecos.gscop.GScopFSMState
import fr.irisa.cairn.gecos.gscop.GScopRoot
import gecos.blocks.Block
import java.util.Map

class ActorTemplate  {
	
	public static Map <Symbol, List<Instruction>>  arraySizeMap;
	
	def printActorSignature(String pkgName, String actorName, 
				List<ActorPortInfo> ipportList, List<ActorPortInfo> opportList ) {
		'''
		package «pkgName»;
			actor «actorName»()  «ipportList.join(",",[p|p.generateActorPort])»  ==>
				«opportList.join(",",[p|p.generateActorPort])»  :
			«printFunctions()»
		'''
	}
	
	def printFunctions() {
		'''
		function floord (int a, int b) --> int :
			if ( ((a >= 0 ) && (b < 0)) || ((b >= 0) && (a < 0)) ) then
				if ( a mod b = 0 ) then
					a/b
				else
					a/b-1
				end
			else
				a/b 
			end
		end
				
		function ceild (int a, int b) --> int :
			if ( ((a >= 0 ) && (b < 0)) || ((b >= 0) && (a < 0)) ) then
				a/b
			else if ( a mod b = 0) then
				a/b
			else 
				a/b + 1
			end
			end
		end
		
		function max (int a, int b) --> int :
			if ( a > b ) then
				a
			else
				b
			end
		end
				
		function min (int a, int b) --> int :
			if (  a <  b ) then
				a
			else
				b
			end
		end
		'''
	}
	
	def generateActorPort(ActorPortInfo p) {
		'''	«p.baseType» «p.portName» 	'''		
	}
	
	
	def printActorSymbols(List<Symbol> symList) {
		'''
		«symList.join("",[sym|sym.generateSymbol+";\n"])»
		int done := 0;
		'''		
	}
	
	
	def printAction(String name, Procedure proc, GScopFSM scopFSM, List<ActorPortInfo> ipportList, 
		List<ActorPortInfo> opportList, Instruction gaurd, Instruction stateChange, boolean scatterInit ) {
		'''
			«name»: action  «ipportList.join(",",[p|p.generateActionPort])» ==>
						«opportList.join(",",[p|p.generateActionPort])»
				«IF gaurd != null»
				guard
					«ActorInstructionTemplate::eInstance.generate(gaurd)» «IF scatterInit» && done = 0 «ENDIF»
				«ENDIF»
				«IF proc.scope.symbols.size > 0 || opportList.size > 0 || scopFSM.iterators.size > 0»var«ENDIF»
					«proc.scope.symbols.join(",\n",[s|s.generateSymbol])»«IF (proc.scope.symbols.size > 0) && ((opportList.size > 0 || scopFSM.iterators.size > 0))»,«ENDIF»
					«opportList.join(",",[p|p.baseType+"  "+p.varName+"["+p.size+"]"])»«IF (opportList.size > 0 && scopFSM.iterators.size > 0)»,«ENDIF»
					«IF scopFSM.iterators.size > 0»
						«scopFSM.iterators.join(",\n",[i|"int "+i.name+"_next"])»
					«ENDIF»
				do
					«IF scatterInit»
						«ipportList.join("",[p|p.portName+" := "+p.varName+";\n"])»
					«ENDIF»
					«scopFSM.states.drop(1).join("", [s|s.generate(scopFSM,"done",1)])»
					«IF stateChange != null»
						«ActorInstructionTemplate::eInstance.generate(stateChange)»;
					«ENDIF»
				end
				
		'''
	}
	
	def printScatterOut(List<ActorPortInfo> opportList, int actorId ) {
		'''
			send_out: action   ==>
						«opportList.join(",",[p|p.generateActionPort])»
				guard
					done = 1
				var
					«opportList.join(",",[p|p.baseType+"  "+p.varName+"["+p.size+"]"])»
				do
					«opportList.join("",[p|p.varName+" := "+p.portName+";\n"])»
					done := 0;
					state_«actorId» := 0;
				end
				
		'''
	}
	
	def printAction(String name, Procedure proc, GScopRoot scop, List<ActorPortInfo> ipportList, 
		List<ActorPortInfo> opportList, Instruction gaurd, Instruction stateChange, boolean scatterInit ) {
		'''
			«name»: action  «ipportList.join(",",[p|p.generateActionPort])» ==>
						«opportList.join(",",[p|p.generateActionPort])»
				«IF gaurd != null»
				guard
					«ActorInstructionTemplate::eInstance.generate(gaurd)» «IF scatterInit» && done = 0 «ENDIF»
				«ENDIF»
				«IF proc.scope.symbols.size > 0 || opportList.size > 0 »var«ENDIF»
					«proc.scope.symbols.join(",\n",[s|s.generateSymbol])»«IF proc.scope.symbols.size >0 && opportList.size > 0»,«ENDIF»
					«opportList.join(",",[p|p.baseType+"  "+p.varName+"["+p.size+"]"])»
				do
					«IF scatterInit»
						«ipportList.join("",[p|p.portName+" := "+p.varName+";\n"])»
					«ENDIF»
					«FOR s : scop.statements»
						«IF (s instanceof Block)»
							«ActorBlockTemplate::eInstance.generate(s)»
						«ELSE»	
							«ActorInstructionTemplate::eInstance.generate(s)»
						«ENDIF»	
					«ENDFOR»
					«IF stateChange != null»
						«ActorInstructionTemplate::eInstance.generate(stateChange)»;
					«ENDIF»
				end
				
		'''
	}
	
	
	def generateActionPort(ActorPortInfo p) {
		'''	«p.portName»:[«p.varName»] repeat «p.size»	'''		
	}
	
	def printActorEnd() {
		'''end'''
	}
	
	def printInitialization(List<GScopFSM> list) {
		'''
			«list.join("\n",[fsm|fsm.generateInitialization(fsm.getStates.get(0))])»
		'''
	}
	
	
	def printStateVariables(int size) {
		val buffer = new StringBuffer()
		var id = 0
		while (id < size) {
			buffer.append("done_signal_"+id);
			id = id + 1;
		}
		'''«buffer.toString»'''
	}
		
	
	
	def generateSymbol(Symbol s) {
		//'''«ExtendableTypeCGenerator::eInstance.generate(s.type)»  «s.name»'''
		'''«ExtendableCoreCGenerator::eInstance.generate(s)»'''
	}
	
	def name(LoopIterator li) {
		'''«li.name»'''
	}
	
	def generateInitialization(GScopFSM scopFSM, GScopFSMState scopFSMstate) {
		'''
			«scopFSMstate.commands.join("", [c|c.generate])»
			«scopFSMstate.transitions.join(" else ", [t|t.generateInitialization(scopFSM)])»
		'''
	}
	
	def generate(GScopFSMState scopFSMstate, GScopFSM scopFSM, String varName, int value) {
		'''
			//FSM Transitions
			«scopFSMstate.transitions.generateTransition(0, scopFSM, varName, value)»
			
			«IF scopFSM.iterators.size > 0»
				«scopFSM.iterators.join("\n",[i|i.name+" := "+i.name+"_next;"])»
			«ENDIF»
			
			//FSM Commands
			«IF scopFSM.exclusiveCommandDomains»
				«scopFSMstate.commands.generateCommand(0)»
			«ELSE»
				«scopFSMstate.commands.join("", [c|c.generate])»
			«ENDIF»
			
		'''
	}
	
	def generateInitialization(GScopFSMTransition scopFSMtransition, GScopFSM scopFSM) {
		'''
			«IF scopFSMtransition.guards.size > 0 »
				if «scopFSMtransition.guards.guardConstraints» then
			«ENDIF»
				«FOR intExpr : scopFSMtransition.nextIteratorValue»
					«val i = scopFSMtransition.nextIteratorValue.indexOf(intExpr)»
					«val iterator = scopFSM.iterators.get(i).name»
					«val value = intExpr.generate»
					«IF !iterator.equals(value)»
						«iterator» := «value»;
					«ENDIF»
				«ENDFOR»
			«IF scopFSMtransition.guards.size > 0 »
				end
			«ENDIF»
		'''
	}
	
	def generate(IntExpression expr) {
		''' «ActorInstructionTemplate::eInstance.generate(expr)» '''
	}
	
	def generate(GScopFSMTransition scopFSMtransition, GScopFSM scopFSM) {
		'''
			«IF scopFSMtransition.guards.size > 0 »
				if «scopFSMtransition.guards.guardConstraints» then
			«ENDIF»
				«FOR intExpr : scopFSMtransition.nextIteratorValue»
					«val i = scopFSMtransition.nextIteratorValue.indexOf(intExpr)»
					«scopFSM.iterators.get(i).name»_next := «intExpr.generate»;
				«ENDFOR»
		'''
	}
	
	def generateTransition(EList<GScopFSMTransition> scopFSMtransitionList, int i, GScopFSM scopFSM, String varName, int value) {
		'''
			«IF scopFSMtransitionList.size > i »
			«IF scopFSMtransitionList.get(i).guards.size > 0 »
				if «scopFSMtransitionList.get(i).guards.guardConstraints» then
			«ENDIF»
			«FOR intExpr : scopFSMtransitionList.get(i).nextIteratorValue»
				«val index = scopFSMtransitionList.get(i).nextIteratorValue.indexOf(intExpr)»
					«scopFSM.iterators.get(index).name»_next := «intExpr.generate»;
			«ENDFOR»
			«IF scopFSMtransitionList.size > i + 1»
				else «scopFSMtransitionList.generateTransition(i+1,scopFSM, varName, value)»
			«ELSE»
				else 
					«varName» := «value»;
			«ENDIF»
			«IF scopFSMtransitionList.get(i).guards.size > 0 »
				end
			«ENDIF»
			«ENDIF»
		'''
	}
	
	def generate(GScopFSMCommand scopFSMcommand) {
		'''
			«IF !scopFSMcommand.guards.empty»
				if «scopFSMcommand.guards.guardConstraints» then
			«ENDIF»
				«scopFSMcommand.instrs.join("", [inst|generateInstr(inst)])»
			«IF !scopFSMcommand.guards.empty»
				end
			«ENDIF»
		'''
	}
	
	def generateCommand(EList<GScopFSMCommand> scopFSMcommandList, int i) {
		'''
			«IF !scopFSMcommandList.get(i).guards.empty»
				if «scopFSMcommandList.get(i).guards.guardConstraints» then
			«ENDIF»
				«scopFSMcommandList.get(i).instrs.join("", [inst|generateInstr(inst)])»
			«IF scopFSMcommandList.size >  i+1»
				else «scopFSMcommandList.generateCommand(i+1)»
			«ENDIF»
			«IF !scopFSMcommandList.get(i).guards.empty»
				end
			«ENDIF»
		'''
	}
	
	def dispatch generateInstr(GScopInstruction inst) {
		'''
		«ActorInstructionTemplate::eInstance.printInst(inst)»'''
	}
	
	
	def dispatch generateInstr(GScopBlock inst) {
		'''
		«ActorBlockTemplate::eInstance.generate(inst)»'''
	}
	
	def protected guardConstraints(List<IntConstraintSystem> guards) {
		val prefix  = if (guards.empty) "(" else "";
		val postfix = if (guards.empty) ")" else "";
		'''(«guards.join(prefix, " || ", postfix, [g|ActorInstructionTemplate::eInstance.generate(g)])»)'''
	}
}