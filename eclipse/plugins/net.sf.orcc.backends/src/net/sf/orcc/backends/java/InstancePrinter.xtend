package net.sf.orcc.backends.java

import net.sf.orcc.df.Instance
import java.util.Map

import static net.sf.orcc.backends.OrccBackendsConstants.*
import static net.sf.orcc.OrccLaunchConstants.*

class InstancePrinter extends JavaTemplate {
	Instance instance
	Map<String,Object> options
	
	new(Instance instance, Map<String,Object> options){
		this.instance = instance
		this.options = options
		overwriteAllFiles = options.get(DEBUG_MODE) as Boolean
	}
	
	def printInstance(String targetFolder){
		val ActorPrinter actorPrinter = new ActorPrinter(instance.actor, options)
		actorPrinter.print(targetFolder);
	}
}