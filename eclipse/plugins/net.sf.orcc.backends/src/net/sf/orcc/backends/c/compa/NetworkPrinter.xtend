/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.compa

import java.util.Map
import net.sf.orcc.df.Network

/**
 * Generate and print network source file for COMPA backend.
 *  
 * @author Antoine Lorence
 * 
 */
class NetworkPrinter extends net.sf.orcc.backends.c.NetworkPrinter {
	
	new(Network network, Map<String, Object> options) {
		super(network, options)
	}
	
	override protected getNetworkFileContent() '''
		// Generated from "«network.name»"

		#include <locale.h>
		#include <stdio.h>
		#include <stdlib.h>
		
		#ifndef _WIN32
		#define __USE_GNU
		#endif
		
		#include "types.h"
		#include "fifo.h"
		#include "scheduler.h"
		#include "util.h"
		
		#define SIZE «fifoSize»
		// #define PRINT_FIRINGS

		/////////////////////////////////////////////////
		// FIFO allocation
		«FOR vertice : network.children.actorInstances»
			«vertice.allocateFifos»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// FIFO pointer assignments
		«FOR instance : network.children.actorInstances»
			«instance.assignFifo»
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Action initializes
		«FOR instance : network.children.actorInstances»
			extern void «instance.name»_initialize();
		«ENDFOR»
		
		/////////////////////////////////////////////////
		// Action schedulers
		«FOR instance : network.children.actorInstances»
			extern int «instance.name»_scheduler();
		«ENDFOR»

		/////////////////////////////////////////////////
		// Actor scheduler
		static void scheduler() {
			int stop = 0;

			«FOR instance : network.children.actorInstances»
				«instance.name»_initialize();
			«ENDFOR»

			int i;
			while(!stop) {
				i = 0;
				«FOR instance : network.children.actorInstances»
					i += «instance.name»_scheduler();
				«ENDFOR»

				stop = stop || (i == 0);
			}
		}

		////////////////////////////////////////////////////////////////////////////////
		// Main
		int main(int argc, char *argv[]) {
			init_orcc(argc, argv);

			scheduler();

			printf("End of simulation !\n");
			return compareErrors;
		}
	'''

}