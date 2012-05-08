package net.sf.orcc.backends.llvm.tta;

import java.util.HashMap;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.orcc.backends.TemplateData;
import net.sf.orcc.backends.llvm.tta.architecture.Buffer;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;

import org.eclipse.emf.ecore.EObject;

public class TTAProcessorTemplateData implements TemplateData {

	Map<Buffer, Integer> bufferToIdMap;

	public TTAProcessorTemplateData() {
		bufferToIdMap = new HashMap<Buffer, Integer>();
	}

	@Override
	public TemplateData compute(EObject object) {
		Processor processor = (Processor) object;
		computeBufferToIdMap(processor);
		return this;
	}

	private void computeBufferToIdMap(Processor processor) {
		int i = 1;
		for (Edge edge : processor.getIncoming()) {
			if (edge instanceof Buffer) {
				Buffer buffer = (Buffer) edge;
				bufferToIdMap.put(buffer, i);
				i++;
			}
		}
		for (Edge edge : processor.getOutgoing()) {
			if (edge instanceof Buffer) {
				Buffer buffer = (Buffer) edge;
				bufferToIdMap.put(buffer, i);
				i++;
			}
		}
	}

	public Map<Buffer, Integer> getBufferToIdMap() {
		return bufferToIdMap;
	}

}
