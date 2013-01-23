package net.sf.orcc.ui.editor

import java.io.IOException
import java.io.OutputStream
import java.util.ArrayList
import java.util.List
import java.util.Map
import net.sf.orcc.graphiti.io.ITransformation
import net.sf.orcc.graphiti.model.Edge
import net.sf.orcc.graphiti.model.Graph
import net.sf.orcc.graphiti.model.ObjectType
import net.sf.orcc.graphiti.model.Vertex
import org.eclipse.core.resources.IFile

class NLTransformation implements ITransformation {

	override transform(IFile file) {
		throw new UnsupportedOperationException("Auto-generated function stub")
	}

	override transform(Graph graph, OutputStream out) throws IOException {

		val id = graph.getValue(ObjectType::PARAMETER_ID)

		val List<Vertex> instances = new ArrayList<Vertex>
		val List<Vertex> inputs = new ArrayList<Vertex>
		val List<Vertex> outputs = new ArrayList<Vertex>

		for (vertex : graph.vertexSet) {
			if ("Input port".equals(vertex.type.name)) {
				inputs.add(vertex);
			} else if ("Output port".equals(vertex.type.name)) {
				outputs.add(vertex);
			} else {
				instances.add(vertex);
			}
		}

		val List<Edge> connections = new ArrayList<Edge>(graph.edgeSet());

		val result = '''
			network «id»() «inputs.print» ==> «outputs.print» :

			entities
				«FOR instance : instances»
					«instance.getAttribute("id")» = «instance.getAttribute("refinement")»(«(instance.getAttribute("instance parameter") as Map<Object, Object>).printParameters»);
				«ENDFOR»
			structure
				«FOR conn : connections»
					«conn.source.getAttribute("id")»«IF conn.getAttribute("source port") != null».«conn.getAttribute("source port")»«ENDIF» --> «conn.target.getAttribute("id")»«IF conn.getAttribute("target port") != null».«conn.getAttribute("target port")»«ENDIF»;
				«ENDFOR»
			end
		'''
		out.write(result.toString.bytes)
		out.close
	}

	def printParameters(Map<Object, Object> map) {
		map.entrySet.join(", ", ['''«key» = «value»'''])
	}


	def print(List<Vertex> ports) {
		ports.join(", ", ['''«getAttribute("port type")» «getAttribute("id")»'''])
	}
}