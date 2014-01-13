/*
 * Copyright (c) 2012, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
 
package net.sf.orcc.backends.opencl

import java.text.SimpleDateFormat
import java.util.Date
import java.util.List
import net.sf.orcc.df.Action
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.ir.BlockBasic
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.InstAssign
import net.sf.orcc.ir.InstLoad
import net.sf.orcc.ir.InstStore
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.Var
import net.sf.orcc.ir.BlockIf
import net.sf.orcc.ir.BlockWhile

/*
 * OpenCL Actor Printer
 * 
 * @author Endri Bezati
 */
class ActorPrinter extends BasePrinter {
	Instance currentInstance;
	
	def printHeader(Instance instance){
		var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		var date = new Date();
		'''
		// ////////////////////////////////////////////////////////////////////////////
		// EPFL - OpenCL Backend
		// --
		// Date :  «dateFormat.format(date)»
		// CAL Actor Header File: "«instance.getActor.file»"
		// Actor: «instance.simpleName»
		// ////////////////////////////////////////////////////////////////////////////
		#ifndef __«instance.name.toUpperCase»_HPP__
		#define __«instance.name.toUpperCase»_HPP__
		
		
		#include "CAL/DeviceManager.hpp"
		«IF !instance.getActor.stateVars.empty»
		// «instance.name» state variables
		«printStateVars(instance, instance.getActor.stateVars)»
		«ENDIF»
		
		class «instance.name»{
		public:
			// Constuctor & Destructor
			«instance.name»(DeviceManager);
			~«instance.name»();
			
			// Methods
			void initialize();
			cl_uint schedule();
		private:
			DeviceManager deviceManager;
			«IF !instance.getActor.stateVars.empty»
			«instance.name»_stateVars sv_«instance.name»;
			«ENDIF»	
		};
		
		#endif // __«instance.name.toUpperCase»_HPP__
		'''
	}
	
	def printInstance(Instance instance)  {
		var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		var date = new Date();
		var actor = instance.getActor;
		currentInstance = instance;
		'''
		// ////////////////////////////////////////////////////////////////////////////
		// EPFL - OpenCL Backend
		// --
		// Date :  «dateFormat.format(date)»
		// CAL Actor Source File: "«instance.getActor.file»"
		// Actor: «instance.simpleName»
		// ////////////////////////////////////////////////////////////////////////////
		#include "«instance.name».hpp"
		#include <iostream>
		
		«instance.name»::«instance.name»(DeviceManager deviceManager)
			:deviceManager(deviceManager)
		{
			«FOR v : actor.stateVars.filter(v|v.initialValue != null)»sv_«instance.name».«printArg(v.type, v.name, v.initialValue)»«ENDFOR»
		}
		
		«instance.name»::~«instance.name»(){
		}
		
		
		void «instance.name»::initialize(){
		}
		
		cl_uint «instance.name»::schedule(){
		}
		'''
	}
	
	def printKernel(Instance instance)  {
		var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		var date = new Date();
		var actor = instance.getActor;
		currentInstance = instance;
		'''
		// ////////////////////////////////////////////////////////////////////////////
		// EPFL - OpenCL Backend
		// --
		// Date :  «dateFormat.format(date)»
		// CAL Actor Kernel File: "«instance.getActor.file»"
		// Actor: «instance.simpleName»
		// ////////////////////////////////////////////////////////////////////////////
		
		// List of actions
		«FOR action : actor.actions SEPARATOR "\n"»«action.printKernelAction(instance)»«ENDFOR»
		
		// The kernel
		__kernel void «instance.simpleName»(
			«IF !instance.getActor.stateVars.empty»
			__global «instance.name»_stateVars sv_«instance.name»,
			«ENDIF»
			«printPort(instance.getActor.inputs,"pIn_","__global const")»
			«printPort(instance.getActor.outputs,"pOut_","__global")»
			)
		{
			int gid = get_global_id(0);
		}
		'''
	}
	

	def printKernelAction(Action action, Instance instance){
		'''
		«action.body.returnType.doSwitch» «action.body.name» (
			int gid,
			«IF !instance.getActor.stateVars.empty»
			«instance.name»_stateVars sv_«instance.name»,
			«ENDIF»
			«printPort(action.inputPattern.ports,"","")»
			«printPort(action.outputPattern.ports,"","")»
			) 
		{
			«action.body.doSwitch»
		}
		'''
	}
	
	def printPort(List<Port> ports,String type, String prefix){
		'''
		«FOR port : ports SEPARATOR ","» 
		«IF !prefix.equals("")»«prefix» «ENDIF»«port.type.doSwitch» *«type»«port.name» 
		«ENDFOR»
		'''
	}
	
	
	
	def printStateVars(Instance instance,List<Var> stateVars){
		''' 	
		typedef struct{
			«FOR variable: stateVars SEPARATOR "\n"»«variable.varDecl»;«ENDFOR»
		} «instance.name»_stateVars;
		'''	
	}
	
	def private varDecl(Var v) {
		'''«v.type.doSwitch» «v.name»«FOR dim : v.type.dimensions»[«dim»]«ENDFOR»'''
	}
	
	def dispatch printArg(Type type, String name, Expression expr) {
		'''
		«name» = «expr.doSwitch»;
		'''
	}
	
	def dispatch printArg(TypeList type, String name, Expression expr){ 
		'''
		// C++11 allows array initializer in initialization list but not yet implemented in VS10... use that instead.
		«type.doSwitch» tmp_«name»«FOR dim:type.dimensions»[«dim»]«ENDFOR»= «expr.doSwitch»;
		'''
	}
	
	// Expressions
	
	override caseExprVar(ExprVar expr){
		'''«IF expr.use.variable.global»«currentInstance.name».«ENDIF»«expr.use.variable.name»'''
	} 
	
	// Procedure
	
	override caseProcedure(Procedure procedure) '''
		«FOR variable : procedure.locals SEPARATOR "\n"»«variable.varDecl»;«ENDFOR»
		«FOR node : procedure.blocks»«node.doSwitch»«ENDFOR»
	'''
	
	
	
	// Assign, Load, Store
	
	override caseInstAssign(InstAssign inst){
		'''
		«IF inst.target.variable.global»«currentInstance.name».«ENDIF»«inst.target.variable.name» = «inst.value.doSwitch»;
		'''
	}
	
	override caseInstLoad(InstLoad inst){ 
		'''
		«IF inst.target.variable.global»sv_«currentInstance.name».«ENDIF»«inst.target.variable.name» = «IF inst.source.variable.global»sv_«currentInstance.name».«ENDIF»«inst.source.variable.name»«FOR index : inst.indexes»[«index.doSwitch»]«ENDFOR»;
		''' 
	}
	
	override caseInstStore(InstStore inst){
		'''
		«IF inst.target.variable.global»sv_«currentInstance.name».«ENDIF»«inst.target.variable.name»«FOR index : inst.indexes»[«index.doSwitch»]«ENDFOR» = «inst.value.doSwitch»;
		'''
	} 
		
	// Blocks
	override caseBlockBasic(BlockBasic node){ 
		'''
		«FOR inst : node.instructions»«inst.doSwitch»«ENDFOR»
		'''
	}
	override caseBlockIf(BlockIf block)'''
		if («block.condition.doSwitch») {
			«FOR thenBlock : block.thenBlocks»
				«thenBlock.doSwitch»
			«ENDFOR»
		}«IF block.elseRequired» else {
			«FOR elseBlock : block.elseBlocks»
				«elseBlock.doSwitch»
			«ENDFOR»
		}
		«ENDIF»
	'''
	
	override caseBlockWhile(BlockWhile blockWhile)'''
		while («blockWhile.condition.doSwitch») {
			«FOR block : blockWhile.blocks»
				«block.doSwitch»
			«ENDFOR»
		}
	'''
}