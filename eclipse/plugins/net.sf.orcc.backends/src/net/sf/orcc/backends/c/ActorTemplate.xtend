package net.sf.orcc.backends.c

import fr.irisa.cairn.gecos.gscop.GScopBlock
import fr.irisa.cairn.gecos.gscop.GScopFSM
import fr.irisa.cairn.gecos.gscop.GScopFSMCommand
import fr.irisa.cairn.gecos.gscop.GScopFSMState
import fr.irisa.cairn.gecos.gscop.GScopFSMTransition
import fr.irisa.cairn.gecos.gscop.GScopInstruction
import fr.irisa.cairn.gecos.gscop.LoopIterator
import fr.irisa.cairn.gecos.model.c.generator.ExtendableBlockCGenerator
import gecos.core.Symbol
import java.io.File
import java.util.List
import net.sf.orcc.util.OrccUtil
import org.polymodel.algebra.IntConstraintSystem
import org.polymodel.algebra.IntExpression
import fr.irisa.cairn.gecos.model.c.generator.ExtendableTypeCGenerator
import org.eclipse.emf.common.util.EList
import gecos.core.ParameterSymbol
import gecos.core.ProcedureSymbol
import fr.irisa.cairn.gecos.model.c.generator.ExtendableCoreCGenerator
import java.util.ArrayList

class ActorTemplate  {
	
	def printActor(String name, GScopFSM scopFSM, GScopFSM gatherFSM, String pkgName, String actorName, List<ActorPortInfo> ipportList, List<ActorPortInfo> opportList, List<ActorPortInfo> writeoutList) {
		val file = new File(name)
		OrccUtil::printFile(generate(scopFSM, gatherFSM, pkgName, actorName, ipportList, opportList, writeoutList), file)
	}
	
	def printScatterActor(String name, List<GScopFSM> scopFSMs, String pkgName, String actorName, List<ActorPortInfo> ipportList, List<List<ActorPortInfo>> opportList) {
		val file = new File(name)
		var id = 0
		var listNames = new ArrayList<String>();
		while (id < scopFSMs.size) {
			listNames.add("done_signal_"+id);
			id = id + 1;
		}
		OrccUtil::printFile(generateScatter(scopFSMs, pkgName, actorName, ipportList, opportList, listNames), file)
	}
	
	def printGatherActor(String name, GScopFSM scopFSM, GScopFSM gatherFSM, String pkgName, String actorName, List<ActorPortInfo> ipportList, List<ActorPortInfo> opportList, List<ActorPortInfo> writeoutList) {
		val file = new File(name)
		OrccUtil::printFile(generateGather(scopFSM, pkgName, actorName, ipportList, opportList, writeoutList), file)
	}


	def generateGather(GScopFSM scopFSM, String pkgName, String actorName, 
		List<ActorPortInfo> ipportList, List<ActorPortInfo> opportList, List<ActorPortInfo> writeoutList) 
	{
		'''
			«printInitialActor(scopFSM,pkgName,actorName,ipportList,opportList,writeoutList)»
				gather: action  «ipportList.join(",",[p|p.generateActionPort])» ==>
						«opportList.join(",",[p|p.generateActionPort])»
				guard
					state = 1 
				var
					int done,
					«scopFSM.scope.allSymbolInScope.filter[s | !(s.name.contains("tmpBuffer") || s instanceof ProcedureSymbol || s instanceof ParameterSymbol)].join(",\n",[s|s.generateSymbol])»«IF opportList.size > 0 || scopFSM.iterators.size > 0 »,«ENDIF» 
					«opportList.join(",",[p|p.baseType+"  "+p.varName+"["+p.size+"]"])»«IF opportList.size > 0 && scopFSM.iterators.size > 0»,«ENDIF»
					«scopFSM.iterators.join(",\n", [i|"int "+i.name+"_next"])»
				do 
					«scopFSM.scope.allSymbolInScope.filter[s|s.name.contains("buffer_index")].join("\n",[s|s.name+" := 0;"])»
					done := 0;
					while ( done != 1) do
						«scopFSM.states.drop(1).join("", [s|s.generate(scopFSM,"done",1)])»
						«IF scopFSM.iterators.size > 0»
							«scopFSM.iterators.join("\n",[i|i.name+" := "+i.name+"_next;"])»
						«ENDIF»
					end
					state := 0;
				end //compute Action
				
			end //Actor
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
		
	def generateScatter(List<GScopFSM> scopFSMs, String pkgName, String actorName, 
		List<ActorPortInfo> ipportList, List<List<ActorPortInfo>> opportList, List<String> done_signal) 
	{
		'''
			package «pkgName»;
			actor «actorName»()  «ipportList.join(",",[p|p.generateActorPort])»  ==>
				«opportList.join(",",[l|l.join(",",[p|p.generateActorPort])])» :
							
				int state := 0;
				«scopFSMs.get(0).scope.allSymbolInScope.filter[s | s instanceof ParameterSymbol].join(";\n",[s|s.generateSymbol])»;
				«scopFSMs.join("\n",[scopFSM|scopFSM.iterators.join("\n",[i|"int "+i.name+";"])])»
				«done_signal.join("\n",[d|"int "+d+";"])»
				
				«printFunctions()»
										
				//FSM Initializaton
				initAction: action ==>  						
				guard
					state = 0
				do
					«val dit = done_signal.listIterator»
					«scopFSMs.join("",[scopFSM|scopFSM.initialState.generateInitialization(scopFSM, dit.next, 0)])»
					state := 1;
				end
			
				collect: action  «ipportList.join(",",[p|p.generateActionPort])» ==>
				guard
					state = 1
				do
					state := 2;
					«ipportList.join("\n",[p|p.portName + " := " + p.varName+";"])»
				end
				
				«var portit = opportList.listIterator»
				«val dsit = done_signal.listIterator»
				«val signal = done_signal.listIterator»
				«FOR scopFSM : scopFSMs»
					«val opportListAtActor = portit.next»
					action   ==>
							«opportListAtActor.join(",",[p|p.generateActionPort])»
					guard
						(state = 2) && («signal.next» = 0) 
					var
						«scopFSM.scope.symbols.filter[s | !(s.name.contains("tmpBuffer") || s instanceof ProcedureSymbol || s instanceof ParameterSymbol)].join(",\n",[s|s.generateSymbol])»«IF opportList.size > 0 || scopFSM.iterators.size > 0 »,«ENDIF» 
						«opportListAtActor.join(",",[p|p.baseType+"  "+p.varName+"["+p.size+"]"])»«IF opportList.size > 0 && scopFSM.iterators.size > 0»,«ENDIF»
						«scopFSM.iterators.join(",\n", [i|"int "+i.name+"_next"])»
					do 
						«scopFSM.states.drop(1).join("", [s|s.generate(scopFSM,dsit.next,1)])»
						«IF scopFSM.iterators.size > 0»
							«scopFSM.iterators.join("\n",[i|i.name+" := "+i.name+"_next;"])»
						«ENDIF»
					end //compute Action
				«ENDFOR»
				
				action ==>
				guard
					«done_signal.join(" && ",[d|d+" = 1 "])»
				do
					state := 0;
					«done_signal.join("\n",[d|d+" := 0;"])»
				end
				
			end //Actor
		'''
	}
		
	def generate(GScopFSM scopFSM, GScopFSM gatherFSM, String pkgName, String actorName, 
		List<ActorPortInfo> ipportList, List<ActorPortInfo> opportList, List<ActorPortInfo> writeoutList
	) {
		'''
			«printInitialActor(scopFSM,pkgName,actorName,ipportList,opportList,writeoutList)»
				compute: action  «ipportList.join(",",[p|p.generateActionPort])» ==> 
						«opportList.join(",",[p|p.generateActionPort])»
				guard
					state = 1 
				var
					«scopFSM.scope.allSymbolInScope.filter[s | !(s.name.contains("tmpBuffer") || s instanceof ProcedureSymbol || s instanceof ParameterSymbol)].join(",\n",[s|s.generateSymbol])»«IF opportList.size > 0 || scopFSM.iterators.size > 0 »,«ENDIF» 
					«opportList.join(",",[p|p.baseType+"  "+p.varName+"["+p.size+"]"])»«IF opportList.size > 0 && scopFSM.iterators.size > 0»,«ENDIF»
					«scopFSM.iterators.join(",\n", [i|"int "+i.name+"_next"])»
				do 
					«scopFSM.states.drop(1).join("", [s|s.generate(scopFSM,"state",2)])»
					«IF scopFSM.iterators.size > 0»
						«scopFSM.iterators.join("\n",[i|i.name+" := "+i.name+"_next;"])»
					«ENDIF»
				end //compute Action
				
				«IF gatherFSM != null»
				writeOut: action ==> 
					«writeoutList.join(",",[p|p.generateActionPort])»
				guard
					state = 2
				var
					«writeoutList.join(",",[p|p.baseType+"  "+p.varName+"["+p.size+"]"])»«IF writeoutList.size > 0 && gatherFSM.iterators.size > 0»,«ENDIF»
					«IF gatherFSM.iterators.size > 0»
						«gatherFSM.iterators.join(",\n", [i|"int "+i.name+"_next"])»,
					«ENDIF»
					«val root = gatherFSM.parent»
					«val block = root.parent.parent»
					«val sc = root.scope» 
					«sc.symbols.join(",\n",[s|s.generateSymbol])»«IF sc.symbols.size > 0»,«ENDIF»
					int done
				do 
					«sc.symbols.join("\n",[s|s.name+" := 0;"])»
					«gatherFSM.initialState.generateInitialization(gatherFSM, "done", 0)»
					while ( done != 1 ) do
						«gatherFSM.states.drop(1).join("", [s|s.generate(gatherFSM, "done", 1)])»
						«IF gatherFSM.iterators.size > 0»
							«gatherFSM.iterators.join("\n",[i|i.name+" := "+i.name+"_next;"])»
						«ENDIF»
					end
					state := 0;
				end //writeOutAction
				«ENDIF»
			end //Actor
		'''
	}
	
	def printInitialActor(GScopFSM scopFSM, String pkgName, String actorName, 
		List<ActorPortInfo> ipportList, List<ActorPortInfo> opportList, List<ActorPortInfo> writeoutList) {
		'''
		package «pkgName»;
			actor «actorName»()  «ipportList.join(",",[p|p.generateActorPort])»  ==>
				«opportList.join(",",[p|p.generateActorPort])» «IF (opportList.size > 0 && writeoutList.size > 0) », «ENDIF» «writeoutList.join(",",[p|p.generateActorPort])» :
							
				int state := 0;
				«scopFSM.scope.allSymbolInScope.filter[s | s instanceof ParameterSymbol].join(";\n",[s|s.generateSymbol])»;
				«FOR i : scopFSM.iterators »
					int «i.name»;
				«ENDFOR»
				
				«printFunctions()»
										
				//FSM Initializaton
				initAction: action ==>  						
				guard
					state = 0
				do
					«scopFSM.initialState.generateInitialization(scopFSM, "state", 1)»
				end
			'''
	}
	
	def printFunctions() {
		'''
		function floord (int a, int b) --> int :
			a/b
		end
				
		function ceild (int a, int b) --> int :
			a/b
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
	
	def generateActionPort(ActorPortInfo p) {
		'''	«p.portName»:[«p.varName»] repeat «p.size»	'''		
	}
	
	def generateSymbol(Symbol s) {
		//'''«ExtendableTypeCGenerator::eInstance.generate(s.type)»  «s.name»'''
		'''«ExtendableCoreCGenerator::eInstance.generate(s)»'''
	}
	
	def name(LoopIterator li) {
		'''«li.name»'''
	}
	
	def generateInitialization(GScopFSMState scopFSMstate, GScopFSM scopFSM, String varName, int value) {
		'''
			«scopFSMstate.commands.join("", [c|c.generate])»
			«scopFSMstate.transitions.join(" else ", [t|t.generateInitialization(scopFSM, varName, value)])»
		'''
	}
	
	def generate(GScopFSMState scopFSMstate, GScopFSM scopFSM, String varName, int value) {
		'''
			//FSM Commands
			«IF scopFSM.exclusiveCommandDomains»
				«scopFSMstate.commands.generateCommand(0)»
			«ELSE»
				«scopFSMstate.commands.join("", [c|c.generate])»
			«ENDIF»
			
			//FSM Transitions
			«scopFSMstate.transitions.generateTransition(0, scopFSM, varName, value)»
			
		'''
	}
	
	def generateInitialization(GScopFSMTransition scopFSMtransition, GScopFSM scopFSM, String varName, int finalValue) {
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
				«varName» := «finalValue»;
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