package net.sf.orcc.backends.c.compa

import java.io.File
import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil

class TopFilePrinter extends CommonPrinter {	
	boolean[][] currentMap
	Network network
	int nbProcessors 
	
	new(Network network, boolean[][] currentMap, int nbProcessors){
		this.currentMap = currentMap
		this.network = network
		this.nbProcessors = nbProcessors
	}
	
	def protected print(String targetFolder) {
//		checkConnectivy
		var File file
		var String procFolder
		var CharSequence content
		for (i : 0 ..< nbProcessors) {
			if (i<10){
//				procFolder = targetFolder + File::separator + "Top_0" + i + ".c"
				procFolder = targetFolder + File::separator + "Top_0" + i + File::separator + "Top_0" + i + ".c"
			}
			else{
//				procFolder = targetFolder + File::separator + "Top_" + i + ".c"
				procFolder = targetFolder + File::separator + "Top_" + i + File::separator + "Top_" + i + ".c"
			}
			
			if(i < currentMap.length){
				content = i.fileContent
			}
			else{
				content = i.fileContentBootloop
			}
			
			file = new File(procFolder)
			
			if(needToWriteFile(content, file) || needToWriteFile(content, file)) 
			{
				OrccUtil::printFile(content, file)
			}
		}
	}
	
	def protected getFileContent(int procNb){
		'''
			#include "xil_cache.h"
			#include "fifoAllocations.h"
			
			/////////////////////////////////////////////////
			// Action initializes
			«FOR child : network.children»
				«IF currentMap.get(procNb).get(network.children.indexOf(child))»
					extern int «child.label»_initialize();
				«ENDIF»
			«ENDFOR»
			
			/////////////////////////////////////////////////
			// Action schedulers
			«FOR child : network.children»
				«IF currentMap.get(procNb).get(network.children.indexOf(child))»
					extern int «child.label»_scheduler();
				«ENDIF»
			«ENDFOR»
			
			/////////////////////////////////////////////////
			// Actor scheduler
			void main() {
				
				microblaze_disable_dcache();
				
				«FOR child : network.children»
					«IF currentMap.get(procNb).get(network.children.indexOf(child))»
						«child.label»_initialize();
					«ENDIF»
				«ENDFOR»
				
				int i;
				while(1) {
					i = 0;
					«FOR child : network.children»
						«IF currentMap.get(procNb).get(network.children.indexOf(child))»
							i = «child.label»_scheduler();
						«ENDIF»
					«ENDFOR»
				}
			}	
		'''
	}
	
	def protected getFileContentBootloop(int procNb){
		'''
			void main() {
				while(1);
			}
		'''
	}
}