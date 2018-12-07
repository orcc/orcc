/*
 * Copyright (c) 2016, Heriot-Watt University Edinburgh
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF YUSE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.cal

import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Map.Entry
import net.sf.orcc.OrccRuntimeException
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.ir.Arg
import net.sf.orcc.ir.ArgByVal
import net.sf.orcc.ir.Block
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile
import net.sf.orcc.ir.ExprBinary
import net.sf.orcc.ir.ExprBool
import net.sf.orcc.ir.ExprFloat
import net.sf.orcc.ir.ExprInt
import net.sf.orcc.ir.ExprList
import net.sf.orcc.ir.ExprString
import net.sf.orcc.ir.ExprUnary
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstCall
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstPhi
import net.sf.orcc.ir.InstReturn
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Instruction
import net.sf.orcc.ir.OpBinary
import net.sf.orcc.ir.OpUnary
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeFloat
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.Var
import net.sf.orcc.util.Attributable
import org.eclipse.emf.common.util.EList

/**
 * Generate and print instance source files for the CAL actors,
 * primarily to serve the dataflow transformations in 
 * net.sf.orcc.df.transform driven by the Graphiti interface.
 *  
 * @author Rob Stewart
 * 
 */
class InstancePrinter extends CALTemplate {

	protected var String packageDir
	protected var String actorName
	protected var Instance instance
	protected var Actor actor
	protected var Attributable attributable
	protected var Map<Port, Connection> incomingPortMap
	protected var Map<Port, List<Connection>> outgoingPortMap
	protected var String entityName

	def public getFileContent() {

		var int numInputs = actor.getInputs().size()
		var int numOutputs = actor.getOutputs().size()
		packageDir = actor.package.replaceAll("\\.", "/") + "/"

		'''
package «actor.package»;
	
actor «actorName» ()
	
	«IF numInputs > 0»
		  «printType(actor.getInputs().get(0).type)» «actor.getInputs().get(0).name»
	«ENDIF»
	«IF numInputs > 1»
	«FOR input : actor.getInputs().subList(1,numInputs)»
	, «printType(input.type)» «input.name»
    «ENDFOR»
    «ENDIF»
    ==>
    «IF numOutputs > 0»
    	  «printType(actor.getOutputs().get(0).type)»  «actor.getOutputs().get(0).name»
    «ENDIF»
    «IF numOutputs > 1»
    «FOR output : actor.getOutputs().subList(1,numOutputs)»
    , «printType(output.type)»  «output.name»
    «ENDFOR»
    «ENDIF»
  :
  «FOR theVar : actor.stateVars»
    «printType(theVar.type)» «theVar.name» := «printExpression(theVar.initialValue)»;	
  «ENDFOR»

  «FOR action : actor.actions»
   «printAction(action)»
  «ENDFOR»
  
  «IF actor.fsm !== null»
  schedule fsm «actor.fsm.initialState.name» :
  «FOR trans : actor.fsm.transitions»
    «trans.source.name» («trans.action.name») --> «trans.target.name»; 
  «ENDFOR»
  «ENDIF»
  end
  
end
	'''

	}

	def protected CharSequence printExpression(Expression exp) {
		val String expPrinted = switch exp {
			ExprBinary: printBinaryExpr(exp).toString
			ExprBool: if(exp.isValue) "true" else "false"
			ExprFloat: exp.value.toString
			ExprInt: Integer.toString(exp.intValue)
			ExprList: printList(exp)
			ExprString: "\"" + exp.value + "\""
			ExprUnary: printUnaryOp(exp.op).toString
			ExprVar: printLocalVarExpr(exp)
		}
		'''«expPrinted»'''
	}

	def protected printList(ExprList list) {
		var List<String> elems = new ArrayList<String>()
		for (Expression exp : list.value) {
			elems.add(printExpression(exp).toString)
		}
		delimitWith(elems, ",")
	}

	def protected printBinaryExpr(ExprBinary exp) {
		val e1 = exp.getE1()
		val e1WellFormed = if (e1.isExprBinary)
				("(" + printExpression(e1).toString + ")")
			else
				printExpression(e1).toString

		val e2 = exp.getE2()
		val e2WellFormed = if (e2.isExprBinary)
				("(" + printExpression(e2).toString + ")")
			else
				printExpression(e2).toString
		val expWellFormed = e1WellFormed + " " + printBinaryOp(exp.getOp) + " " + e2WellFormed

		'''«expWellFormed»'''
	}

	def setInstance(Instance instance) {
		if (!instance.isActor) {
			throw new OrccRuntimeException("Instance " + entityName + " is not an Actor's instance")
		}

		this.instance = instance
		this.entityName = instance.name
		this.actor = instance.getActor
		this.attributable = instance
		this.incomingPortMap = instance.incomingPortMap
		this.outgoingPortMap = instance.outgoingPortMap
		actorName = takeAfterLast(this.actor.name, "\\.")
	}

	def setActor(Actor actor) {
		this.entityName = actor.name
		this.actor = actor
		this.attributable = actor
		this.incomingPortMap = actor.incomingPortMap
		this.outgoingPortMap = actor.outgoingPortMap
		actorName = takeAfterLast(this.actor.name, "\\.")
	}

	def protected printAction(Action action) {
		val actionNameTag = if (action.name.startsWith("untagged"))
				""
			else
				action.name + ":"

		'''
			«actionNameTag» action
			  «printInputPattern(action)»
			    ==>
			  «printOutputPattern(action)»
			  «printGuard(action)»
			  «printLocalVars(action)»
			  do
			  «printActionBody(action)»
			 end
		'''
	}

	def protected printLocalVars(Action action) {
		val EList<Var> locals = action.body.locals
		var List<String> varDecls = new ArrayList<String>()
		var List<String> inPatternVars = inputPatternVars(action)
		for (Var localVar : locals) {
			if (!inPatternVars.contains(localVar.name))
				varDecls.add(printType(localVar.type) + " " + formatVarName(localVar.name))
		}
		if (varDecls.size() > 0) '''
			var
			«delimitWith(varDecls,",")»
		''' else ''''''
	}

	def protected printType(Type theType) {
		switch theType {
			TypeBool: "bool"
			TypeFloat: "float"
			TypeInt: "int(size=" + theType.size + ")"
			TypeList: ""
			TypeString: "string"
			TypeUint: "uint(size=" + theType.size + ")"
			TypeVoid: "void"
			default: "int(size=8)" // why?
		}
	}

	def protected printActionBody(Action action) {
		var List<String> printedBlocks = new ArrayList<String>()
		val EList<Block> blocks = action.body.blocks
		for (Block block : blocks) {
			val String printedBlock = printBlock(block).toString
			printedBlocks.add(printedBlock)
		}

		'''
			«FOR block : printedBlocks»
				«block»
			«ENDFOR»
		'''
	}

	def protected CharSequence printBlock(Block block) {
		switch block {
			BlockIf: printBlockIf(block).toString
			BlockWhile: printBlockWhile(block).toString
			BlockBasic: printBlockBasic(block).toString
		}
	}

	def protected printBlockIf(BlockIf blockIf) {
		'''
			if («printExpression(blockIf.condition)»)
			then
			«FOR block : blockIf.thenBlocks»
				«printBlock(block)»
			«ENDFOR»
			else
			«FOR block : blockIf.elseBlocks»
				«printBlock(block)»
			«ENDFOR»
			end
		'''
	}

	def protected printBlockWhile(BlockWhile blockWhile) {
		'''
			while («printExpression(blockWhile.condition)») do
			«FOR block : blockWhile.blocks»
				«printBlock(block)»
			«ENDFOR»
			end
		'''
	}

	def protected printBlockBasic(BlockBasic blockBasic) {
		'''
			«FOR inst : blockBasic.instructions»
				«printInstruction(inst)»
			«ENDFOR»
		'''
	}

	def protected printGuard(Action action) {
		var guard = ""
		var BlockBasic block = action.scheduler.first
		for (Instruction inst : block.instructions) {
			if (inst instanceof InstAssign) {
				val instAssign = inst as InstAssign
				if (instAssign.value.isExprBinary) {
					val guardBinary = instAssign.value as ExprBinary
					guard = "guard " + printExpression(guardBinary)
				}
			}
		}
		'''«guard»'''
	}

	def protected printBinaryOp(OpBinary op) {
		val String opPrinted = switch (op) {
			case BITAND: "&"
			case BITOR: "|"
			case BITXOR: "^"
			case DIV: "/"
			case DIV_INT: "/"
			case EQ: "="
			case GE: ">="
			case GT: ">"
			case LE: "<"
			case LOGIC_AND: "&&"
			case LOGIC_OR: "||"
			case LT: "<"
			case MINUS: "-"
			case MOD: "mod"
			case NE: "!="
			case PLUS: "+"
			case SHIFT_LEFT: "<<"
			case SHIFT_RIGHT: ">>"
			case TIMES: "*"
			default: ""
		}
		'''«opPrinted»'''
	}

	def protected printUnaryOp(OpUnary op) {
		val String opPrinted = switch (op) {
			case BITNOT: "not"
			case LOGIC_NOT: "not"
			case MINUS: "-"
			case NUM_ELTS: ""
			default: ""
		}
		'''«opPrinted»'''
	}

	def protected printLocalVarExpr(ExprVar exp) {
		exp.use.variable.name.replace("local_", "")
	}

	def protected formatVarName(String s) {
		s.replace("local_", "")
	}

	def protected printInstruction(Instruction inst) {
		// TODO implement printer for all instructions
		val String instPrinted = switch inst {
			InstAssign:
				inst.target.variable.name + " := " + printExpression(inst.value) + ";"
			InstCall:
				inst.procedure.name + "(" + printArgs(inst.arguments) + ");"
			InstLoad:
				"" // loads are ignored.
			InstPhi:
				""
			InstReturn:
				""
			InstStore:
				if (isTargetOutputPort(inst.target.variable.name))
					""
				else
					inst.target.variable.name + " := " + printExpression(inst.value) + ";"
			default:
				"" // covers backend specific instructions
		}

		'''«instPrinted»'''
	}

	def protected printArgs(EList<Arg> args) {
		var List<String> argsStrs = new ArrayList()
		for (Arg arg : args) {
			if (arg.isByVal()) {
				val exp = (arg as ArgByVal).value
				argsStrs.add(printExpression(exp).toString)
			}
		}
		delimitWith(argsStrs, "+")
	}

	def protected isTargetInputPort(String str) {
		var boolean b
		for (Port p : actor.inputs) {
			if (str.equals(p.name)) {
				b = true
			}
		}
		return b
	}

	def protected isTargetOutputPort(String str) {
		var boolean b
		for (Port p : actor.outputs) {
			if (str.equals(p.name)) {
				b = true
			}
		}
		return b
	}

	def protected inputPatternVars(Action action) {
		var Map<String, List<String>> inputMap = new HashMap
		for (Instruction inst : action.body.first.instructions) {
			if (inst instanceof InstLoad) {
				var instLoad = inst as InstLoad
				if (isTargetInputPort(instLoad.source.variable.name)) {
					var List<String> previousList = new ArrayList
					if (inputMap.get(instLoad.source.variable.name) !== null) {
						previousList = inputMap.get(instLoad.source.variable.name)
					}
					val token = formatVarName(instLoad.target.variable.name)
					previousList.add(token)
					inputMap.put(instLoad.source.variable.name, previousList)
				}
			}
		}

		var List<String> inPatternVars = new ArrayList
		for (Entry<String,List<String>> entry : inputMap.entrySet) {
			for (String varStr : entry.value) {
				inPatternVars.add(varStr)
			}
		}
		inPatternVars
	}
	
		def protected printInputPattern(Action actn) {
		// val EMap<Port, Var> map = actn.inputPattern.portToVarMap
		var Map<String, List<String>> inputMap = new HashMap

		var Map<String, Map<Integer, Var>> loadLookup = new HashMap

		var List<Instruction> actionInstructions = new ArrayList
		for (Block block : actn.body.blocks) {
			for (Instruction inst : allInstructionsInBlock(block)) {
				actionInstructions.add(inst)
			}
		}

		for (Instruction inst : actionInstructions) {
			if (inst instanceof InstLoad) {
				var instLoad = inst as InstLoad
				if (!instLoad.indexes.isEmpty) {
						var varListAtPort = if (loadLookup.get(instLoad.source.variable.name) === null)
								new HashMap
							else
								loadLookup.get(instLoad.source.variable.name)
						varListAtPort.put(intFromExpr(instLoad.indexes.get(0)), instLoad.target.variable)
						loadLookup.put(instLoad.source.variable.name, varListAtPort)
				}
			}
		}
		

		for (Entry<String, Map<Integer,Var>> entry : loadLookup.entrySet) {
			inputMap.put(entry.key, new ArrayList)
			for (Entry<Integer,Var> token : entry.value.entrySet) {
				var listStrTokens = inputMap.get(entry.key)
				listStrTokens.add(token.value.name)
				inputMap.put(entry.key, listStrTokens)
			}
		}

		var List<String> inputPattern = newArrayList
		for (Entry<String,List<String>> entry : inputMap.entrySet) {
			inputPattern.add(entry.key + ":[" + delimitWith(entry.value, ",") + "]")
		}

		val inputPatternStr = if (!inputPattern.isEmpty)
				delimitWith(inputPattern, ",")
			else ""

		'''
			«inputPatternStr»
		'''
	}


	def protected printOutputPattern(Action actn) {
		var Map<String, List<String>> outputMap = new HashMap

		var Map<String, Map<Integer, Expression>> storeLookup = new HashMap

		var List<Instruction> actionInstructions = new ArrayList
		for (Block block : actn.body.blocks) {
			for (Instruction inst : allInstructionsInBlock(block)) {
				actionInstructions.add(inst)
			}
		}

		for (Instruction inst : actionInstructions) {
			if (inst instanceof InstStore) {
				var instStore = inst as InstStore
				if (!instStore.indexes.isEmpty) {
					if (isTargetOutputPort(instStore.target.variable.name)) {
						var varListAtPort = if (storeLookup.get(instStore.target.variable.name) === null)
								new HashMap
							else
								storeLookup.get(instStore.target.variable.name)
						varListAtPort.put(intFromExpr(instStore.indexes.get(0)), instStore.value)
						storeLookup.put(instStore.target.variable.name, varListAtPort)
					}
				}
			}
		}

		for (Entry<String, Map<Integer, Expression>> entry : storeLookup.entrySet) {
			outputMap.put(entry.key, new ArrayList)
			for (Entry<Integer,Expression> token : entry.value.entrySet) {
				var listStrTokens = outputMap.get(entry.key)
				listStrTokens.add(printExpression(token.value).toString) // append list
				outputMap.put(entry.key, listStrTokens)
			}
		}

		var List<String> outputPattern = newArrayList
		for (Entry<String,List<String>> entry : outputMap.entrySet) {
			outputPattern.add(entry.key + ":[" + delimitWith(entry.value, ",") + "]")
		}

		val outputPatternStr = if (!outputPattern.isEmpty) 
				delimitWith(outputPattern, ",")
			else
				""

		'''
			«outputPatternStr»
		'''
	}

	def protected List<Instruction> allInstructionsInBlock(Block block) {
		switch block {
			BlockBasic: instructionsFromBlockBasic(block)
			BlockIf: instructionsFromBlockIf(block)
			BlockWhile: instructionsFromBlockWhile(block)
		}
	}

	def protected instructionsFromBlockBasic(BlockBasic block) {
		block.instructions
	}

	def protected instructionsFromBlockIf(BlockIf block) {
		var List<Instruction> instructions = new ArrayList
		for (Block thenBlock : block.thenBlocks) {
			instructions.addAll(allInstructionsInBlock(thenBlock))
		}
		for (Block elseBlock : block.elseBlocks) {
			instructions.addAll(allInstructionsInBlock(elseBlock))
		}
		instructions
	}

	def protected instructionsFromBlockWhile(BlockWhile block) {
		var List<Instruction> instructions = new ArrayList
		for (Block subBlock : block.blocks) {
			instructions.addAll(allInstructionsInBlock(subBlock))
		}
		instructions
	}


	def protected intFromExpr(Expression exp) {
		switch exp {
			ExprInt: new Integer(exp.intValue)
			default: -1 // unexpected.
		}
	}

	def protected delimitWith(List<String> strList, String delimChar) {
		var StringBuilder sb = new StringBuilder();
		var String delim = "";
		for (String i : strList) {
			sb.append(delim).append(i);
			delim = delimChar;
		}
		sb.toString()
	}

// for extracting actor name name from actor.name (which includes package prefix)
	def protected takeAfterLast(String s, String c) {
		var String[] ss = s.split(c)
		var it = Arrays.asList(ss).iterator();
		var String lastOne
		while (it.hasNext)
			lastOne = it.next
		lastOne
	}
}
