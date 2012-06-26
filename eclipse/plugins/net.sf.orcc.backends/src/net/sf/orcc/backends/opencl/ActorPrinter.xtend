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

import net.sf.orcc.backends.opencl.BasePrinter
import net.sf.orcc.df.Instance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.List
import net.sf.orcc.ir.Var
import net.sf.orcc.ir.Type
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.TypeList
import net.sf.orcc.df.Port

/*
 * OpenCL Actor Printer
 * 
 * @author Endri Bezati
 */
class ActorPrinter extends BasePrinter {
	def printInstance(Instance instance)  {
		var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		var date = new Date();
		var actor = instance.actor;
		'''
		// ////////////////////////////////////////////////////////////////////////////
		// EPFL - OpenCL Backend
		// --
		// Date :  «dateFormat.format(date)»
		// CAL Actor Source File: "«instance.actor.file»"
		// Actor: «instance.simpleName»
		// ////////////////////////////////////////////////////////////////////////////
		#include "«instance.name».hpp"
		#include <iostream>
		
		«instance.name»::«instance.name»(DeviceManager deviceManager)
			:deviceManager(deviceManager)
		{
			«FOR v : actor.stateVars.filter(v|v.initialValue != null)»sv_«instance.name».«printArg(v.type, v.indexedName, v.initialValue)»«ENDFOR»
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
		'''
		__kernel void «instance.simpleName»(
			«instance.printKernelIO()»
			)
		{
			
		}
		'''
	}
	
	def printKernelIO(Instance instance){
		'''
		«IF !instance.actor.stateVars.empty»
		__global «instance.name»_stateVars sv_«instance.name»,
		«ENDIF»
		«FOR port : instance.actor.inputs SEPARATOR ","» 
		__global const «port.type.doSwitch» *pIn_«port.name» 
		«ENDFOR»
		«FOR port : instance.actor.outputs SEPARATOR ","» 
		__global «port.type.doSwitch» *pIn_«port.name» 
		«ENDFOR»
		'''
	}
	
	def printPort(Port port, Integer size, Integer fanout){
		'''
		<«port.type.doSwitch», «fanout»«IF size != null», «size»«ENDIF»> port_«port.name»;
		'''
	}
	
	def printHeader(Instance instance){
		var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		var date = new Date();
		'''
		// ////////////////////////////////////////////////////////////////////////////
		// EPFL - OpenCL Backend
		// --
		// Date :  «dateFormat.format(date)»
		// CAL Actor Header File: "«instance.actor.file»"
		// Actor: «instance.simpleName»
		// ////////////////////////////////////////////////////////////////////////////
		#ifndef __«instance.name.toUpperCase»_HPP__
		#define __«instance.name.toUpperCase»_HPP__
		
		
		#include "CAL/DeviceManager.hpp"
		«IF !instance.actor.stateVars.empty»
		// «instance.name» state variables
		«printStateVars(instance, instance.actor.stateVars)»
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
			«IF !instance.actor.stateVars.empty»
			«instance.name»_stateVars sv_«instance.name»;
			«ENDIF»	
		};
		
		#endif // __«instance.name.toUpperCase»_HPP__
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
	
}