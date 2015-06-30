package net.sf.orcc.backends.c.dal

import java.util.Map
import net.sf.orcc.backends.c.CTemplate
import net.sf.orcc.df.Network

/**
 * Generate and print actor mapping file for DAL backend.
 *  
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C NetworkPrinter
 */
class NetworkMPrinter extends CTemplate {
	
	Network network
	
	int fifoSize;

	def setNetwork(Network network, int fifoSize) {
		this.network = network;
		this.fifoSize = fifoSize;
	}

	def protected getMappingFileContent(Map<String, String> mapping) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<mapping xmlns="http://www.tik.ee.ethz.ch/~euretile/schema/MAPPING" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		  xsi:schemaLocation="http://www.tik.ee.ethz.ch/~euretile/schema/MAPPING     http://www.tik.ee.ethz.ch/~euretile/schema/mapping.xsd" name="mapping1" processnetwork="APP1">
			«FOR vertex : network.children»
				<binding name="«vertex.label»">
					<process name="«vertex.label»"/>
					«IF mapping.get(network.name + "_" + vertex.label) != null»
						«IF mapping.get(network.name + "_" + vertex.label).equals("")»
							<processor name="core_0"/>
						«ELSE»
							<processor name="«mapping.get(network.name + "_" + vertex.label)»"/>
						«ENDIF»
						«IF mapping.get(network.name + "_" + vertex.label).startsWith("gpu_")»
							<target><opencl workgroups="«fifoSize»" workitems="256"/></target>
						«ENDIF»
					«ELSE»
						<processor name="core_0"/>
					«ENDIF»
				</binding> 
			«ENDFOR»
		</mapping>
	'''
	
}