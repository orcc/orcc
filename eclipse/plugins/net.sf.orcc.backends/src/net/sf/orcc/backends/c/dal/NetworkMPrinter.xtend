package net.sf.orcc.backends.c.dal

import java.io.File
import java.util.Map
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.backends.c.CTemplate

/**
 * Generate and print actor mapping file for DAL backend.
 *  
 * @author Jani Boutellier (University of Oulu)
 * 
 * Modified from Orcc C NetworkPrinter
 */
class NetworkMPrinter extends CTemplate {
	
	Network network
	Map<String, String> mapping
	
	new(Network network, Map<String, String> map) {
		this.network = network
		this.mapping = map
	}
	
	def print(String targetFolder) {
		
		val content = networkFileContent
		val file = new File(targetFolder + File::separator + "mapping1.xml")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def protected getNetworkFileContent() '''
		<?xml version="1.0" encoding="UTF-8"?>
		<mapping xmlns="http://www.tik.ee.ethz.ch/~euretile/schema/MAPPING" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		  xsi:schemaLocation="http://www.tik.ee.ethz.ch/~euretile/schema/MAPPING     http://www.tik.ee.ethz.ch/~euretile/schema/mapping.xsd" name="mapping1" processnetwork="APP1">
			«FOR vertex : network.children»
				<binding name="«vertex.label»">
					<process name="«vertex.label»"/>
					«IF mapping.get(network.name + "_" + vertex.label) != null»
						«IF mapping.get(network.name + "_" + vertex.label).equals("")»
							<processor name="core_2"/> 
						«ELSE»
							<processor name="«mapping.get(network.name + "_" + vertex.label)»"/>
						«ENDIF»
					«ELSE»
						<processor name="core_2"/> 
					«ENDIF»
				</binding> 
			«ENDFOR»
		</mapping>
	'''
	
}