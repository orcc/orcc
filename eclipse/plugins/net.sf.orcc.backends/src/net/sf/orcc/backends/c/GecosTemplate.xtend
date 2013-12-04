package net.sf.orcc.backends.c

import fr.irisa.cairn.gecos.model.extensions.generators.IGecosCodeGenerator
import org.eclipse.emf.ecore.EObject

class GecosTemplate implements IGecosCodeGenerator {
	
	def generate(EObject o) {
		null
	}
	
	override generate(Object o) {
		val object = (o as EObject).generate
		if (object != null) {
			return object.toString
		}
		null
	}
}