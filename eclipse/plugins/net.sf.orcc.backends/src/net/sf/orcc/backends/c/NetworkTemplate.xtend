package net.sf.orcc.backends.c.compa

import java.io.File
import net.sf.orcc.util.OrccUtil
import java.util.List

class NetworkTemplate  {
	
	def printNetwork(String name, List<String> instanceList, String classPrefix, List<EdgeInfo> edgeList, List<NetworkPortInfo> portList, String path ) {
		val file = new File(path+name+".xdf")
		OrccUtil::printFile(generate(name, instanceList, classPrefix, edgeList, portList), file)
	}
	
	def generate(String networkName, List<String> instanceList, String classPrefix, List<EdgeInfo> edgeList, List<NetworkPortInfo> portList) {
		'''
		<?xml version="1.0" encoding="UTF-8"?>
		<XDF name="«networkName»">
			«portList.join("",[p|p.printPort()])»
			«instanceList.join("",[i|i.printInstance(classPrefix)])»
			«portList.join("",[p|p.printEdge()])»
			«edgeList.join("",[e|e.printEdge])»
		</XDF>
		'''
	}
	
	def printPort(NetworkPortInfo p) {
	''' 
		<Port kind="«p.kind»" name="«p.portName»">
			<Type name="«p.baseType»">
				<Entry kind="Expr" name="size">
					<Expr kind="Literal" literal-kind="Integer" value="«p.size»"/>
				</Entry>
			</Type>
		</Port>
	'''	
	}
	
	def printEdge(NetworkPortInfo p) {
		'''
			«IF p.kind == NetworkPortInfo::NetworkPortType::Input»
				<Connection dst="«p.actorName»" dst-port="«p.portName»" src="" src-port="«p.portName»"/>
			«ELSE»
				<Connection dst="" dst-port="«p.portName»" src="«p.actorName»" src-port="«p.portName»"/>
			«ENDIF»
		'''
	}
	
	def printInstance(String name, String classPrefix) {
		'''
		<Instance id="«name»">
			<Class name="«classPrefix+"."+name»"/>
		</Instance>
		'''
	}
	
	def printEdge(EdgeInfo e) {
		'''
			<Connection dst="«e.dstActorName»" dst-port="«e.dstPortName»" src="«e.srcActorName»" src-port="«e.srcPortName»"/>
		'''
		
	}
}