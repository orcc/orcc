/*
 * Copyright (c) 2012, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta.architecture.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.llvm.tta.architecture.Bus;
import net.sf.orcc.backends.llvm.tta.architecture.ExprBinary;
import net.sf.orcc.backends.llvm.tta.architecture.ExprUnary;
import net.sf.orcc.backends.llvm.tta.architecture.Extension;
import net.sf.orcc.backends.llvm.tta.architecture.FuPort;
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Guard;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.OpBinary;
import net.sf.orcc.backends.llvm.tta.architecture.OpUnary;
import net.sf.orcc.backends.llvm.tta.architecture.Operation;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.Reads;
import net.sf.orcc.backends.llvm.tta.architecture.RegisterFile;
import net.sf.orcc.backends.llvm.tta.architecture.Resource;
import net.sf.orcc.backends.llvm.tta.architecture.Segment;
import net.sf.orcc.backends.llvm.tta.architecture.ShortImmediate;
import net.sf.orcc.backends.llvm.tta.architecture.Socket;
import net.sf.orcc.backends.llvm.tta.architecture.Term;
import net.sf.orcc.backends.llvm.tta.architecture.TermBool;
import net.sf.orcc.backends.llvm.tta.architecture.TermUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Writes;
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Herve Yviquel
 * 
 */
public class AdfParser {

	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;
	private Processor processor;
	private Map<String, Memory> memoryMap;
	private Map<FunctionUnit, String> fuToMemoryMap;
	private String romName;
	private Map<TermBool, String> termToRfMap;

	private Operation getOperation(Element element, EList<FuPort> ports) {
		Operation op = factory.createOperation();

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("name")) {
				op.setName(DomUtil.getNodeValue(child));
			} else if (name.equals("bind")) {
				int index = DomUtil.getNodeIntAttr("name", child);
				FuPort port = getPort(ports, DomUtil.getNodeValue(child));
				op.getPortToIndexMap().put(port, index);
			} else if (name.equals("pipeline")) {
				op.getPipeline().addAll(getPipeline(child));
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}

		return op;
	}

	private FuPort getPort(Element element) {
		FuPort port = factory.createFuPort();
		port.setName(DomUtil.getNodeAttr("name", element));

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("width")) {
				int width = DomUtil.getNodeIntValue(child);
				port.setWidth(width);
			} else if (name.equals("triggers")) {
				port.setTrigger(true);
			} else if (name.equals("sets-opcode")) {
				port.setOpcodeSelector(true);
			} else if (name.equals("connects-to")) {
				// TODO
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}

		return port;
	}

	private ShortImmediate getShortImmediate(Element element) {
		ShortImmediate shortImmediate = factory.createShortImmediate();

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("width")) {
				int width = DomUtil.getNodeIntValue(child);
				shortImmediate.setWidth(width);
			} else if (name.equals("extension")) {
				Extension extension = Extension
						.get(DomUtil.getNodeValue(child));
				shortImmediate.setExtension(extension);
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}

		return shortImmediate;
	}

	private Segment getSegment(Element element) {
		Segment segment = factory.createSegment();
		return segment;
	}

	private Guard getGuard(Element element) {
		Element child = DomUtil.getFirstElementChild(element);

		String name = child.getNodeName();
		if (name.equals("simple-expr") || name.equals("inverted-expr")) {
			return getExprUnary(child);
		} else if (name.equals("and-expr") || name.equals("or-expr")) {
			return getExprBinary(child);
		} else if (name.equals("always-true")) {
			return factory.createExprTrue();
		} else if (name.equals("always-false")) {
			return factory.createExprFalse();
		} else {
			throw new OrccRuntimeException("invalid node \"" + name + "\"");
		}
	}

	private Term getTerm(Element element) {
		String name = element.getNodeName();
		if (name.equals("bool")) {
			TermBool term = factory.createTermBool();
			Element child = DomUtil.getFirstElementChild(element);
			String rfName = DomUtil.getNodeValue(child);
			termToRfMap.put(term, rfName);
			child = DomUtil.getNextElementSibling(child);
			int index = DomUtil.getNodeIntValue(child);
			term.setIndex(index);
			return term;
		} else if (name.equals("unit")) {
			TermUnit term = factory.createTermUnit();
			// TODO
			return term;
		} else {
			throw new OrccRuntimeException("invalid node \"" + name + "\"");
		}
	}

	private EList<net.sf.orcc.backends.llvm.tta.architecture.Element> getPipeline(
			Element element) {
		EList<net.sf.orcc.backends.llvm.tta.architecture.Element> pipeline = new BasicEList<net.sf.orcc.backends.llvm.tta.architecture.Element>();
		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("reads")) {
				Reads reads = factory.createReads();
				reads.setStartCycle(DomUtil.getChildIntValue("start-cycle",
						child));
				reads.setCycles(DomUtil.getChildIntValue("cycles", child));
				pipeline.add(reads);
			} else if (name.equals("writes")) {
				Writes writes = factory.createWrites();
				writes.setStartCycle(DomUtil.getChildIntValue("start-cycle",
						child));
				writes.setCycles(DomUtil.getChildIntValue("cycles", child));
				pipeline.add(writes);
			} else if (name.equals("resource")) {
				Resource resource = factory.createResource();
				pipeline.add(resource);
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}
		return pipeline;
	}

	private ExprUnary getExprUnary(Element element) {
		ExprUnary expr = factory.createExprUnary();

		String name = element.getNodeName();
		if (name.equals("simple-expr")) {
			expr.setOperator(OpUnary.SIMPLE);
		} else if (name.equals("inverted-expr")) {
			expr.setOperator(OpUnary.INVERTED);
		}

		Term term = getTerm(DomUtil.getFirstElementChild(element));
		expr.setTerm(term);

		return expr;
	}

	private ExprBinary getExprBinary(Element element) {
		ExprBinary expr = factory.createExprBinary();

		String name = element.getNodeName();
		if (name.equals("and-expr")) {
			expr.setOperator(OpBinary.AND);
		} else if (name.equals("or-expr")) {
			expr.setOperator(OpBinary.OR);
		}

		Element child = DomUtil.getFirstElementChild(element);
		ExprUnary e1 = getExprUnary(child);
		child = DomUtil.getNextElementSibling(child);
		ExprUnary e2 = getExprUnary(child);

		expr.setE1(e1);
		expr.setE2(e2);

		return expr;
	}

	private void parseAddressSpace(Element element) {
		Memory memory = factory.createMemory();
		String memName = DomUtil.getNodeAttr("name", element);
		memory.setName(memName);

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("width")) {
				int width = DomUtil.getNodeIntValue(child);
				memory.setWordWidth(width);
			} else if (name.equals("min-address")) {
				int min = DomUtil.getNodeIntValue(child);
				memory.setMinAddress(min);
			} else if (name.equals("max-address")) {
				int max = DomUtil.getNodeIntValue(child);
				memory.setMinAddress(max);
			} else if (name.equals("numerical-id")) {
				// FIXME: Something to do ?
			} else if (name.equals("shared-memory")) {
				throw new OrccRuntimeException(
						"Parsing ADF with shared memory is not supported");
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}

		memoryMap.put(memName, memory);
	}

	/**
	 * Parses the given document as an architecture description file (ADF).
	 * 
	 * @param doc
	 *            a DOM document that supposedly represent an XDF network
	 */
	private void parseADF(Document doc) throws OrccRuntimeException {
		Element adfElement = doc.getDocumentElement();
		if (!adfElement.getNodeName().equals("adf")) {
			throw new OrccRuntimeException("Expected \"adf\" start element");
		}

		processor = ArchitectureFactory.eINSTANCE.createProcessor();
		memoryMap = new HashMap<String, Memory>();
		fuToMemoryMap = new HashMap<FunctionUnit, String>();
		termToRfMap = new HashMap<TermBool, String>();

		parseBody(adfElement);
	}

	/**
	 * Parses the body of the ADF document. The body can contain any element
	 * among the supported elements. Supported elements are: Bus, Socket,
	 * Function-unit, Global-control-unit, Address-space and Register-file.
	 * 
	 * @param root
	 */
	private void parseBody(Element root) {
		Element element = DomUtil.getFirstElementChild(root);
		while (element != null) {
			String name = element.getNodeName();
			if (name.equals("bus")) {
				parseBus(element);
			} else if (name.equals("socket")) {
				parseSocket(element);
			} else if (name.equals("function-unit")) {
				parseFU(element);
			} else if (name.equals("global-control-unit")) {
				parseGCU(element);
			} else if (name.equals("address-space")) {
				parseAddressSpace(element);
			} else if (name.equals("register-file")) {
				parseRF(element);
			} else if (name.equals("immediate-unit")) {
				// TODO
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			element = DomUtil.getNextElementSibling(element);
		}

		// Connect local memories
		for (FunctionUnit fu : fuToMemoryMap.keySet()) {
			String name = fuToMemoryMap.get(fu);
			Memory memory = memoryMap.get(name);
			if (memory == null) {
				OrccLogger.severeln("Unknow address space \"" + name + "\".");
			}
			processor.getLocalRAMs().add(memory);
			fu.setAddressSpace(memory);
		}

		// Connect rom
		Memory memory = memoryMap.get(romName);
		if (memory == null) {
			OrccLogger.severeln("Unknow address space \"" + romName + "\".");
		}
		processor.setROM(memory);
		processor.getGcu().setAddressSpace(memory);

		for (TermBool term : termToRfMap.keySet()) {
			term.setRegister(processor.getRegisterFile(termToRfMap.get(term)));
		}
	}

	private void parseBus(Element element) {
		Bus bus = factory.createBus();
		bus.setName(DomUtil.getNodeAttr("name", element));

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("width")) {
				int width = DomUtil.getNodeIntValue(child);
				bus.setWidth(width);
			} else if (name.equals("guard")) {
				bus.getGuards().add(getGuard(child));
			} else if (name.equals("segment")) {
				bus.getSegments().add(getSegment(child));
			} else if (name.equals("short-immediate")) {
				bus.setShortImmediate(getShortImmediate(child));
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}
		processor.getBuses().add(bus);
	}

	private void parseFU(Element element) {
		FunctionUnit fu = factory.createFunctionUnit();
		fu.setName(DomUtil.getNodeAttr("name", element));

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("port")) {
				fu.getPorts().add(getPort(child));
			} else if (name.equals("operation")) {
				fu.getOperations().add(getOperation(child, fu.getPorts()));
			} else if (name.equals("address-space")) {
				String asName = DomUtil.getNodeValue(child);
				if (!asName.isEmpty()) {
					fuToMemoryMap.put(fu, DomUtil.getNodeValue(child));
				}
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}
		processor.getFunctionUnits().add(fu);
	}

	private void parseGCU(Element element) {
		GlobalControlUnit gcu = factory.createGlobalControlUnit();
		gcu.setName(DomUtil.getNodeAttr("name", element));

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("port")) {
				gcu.getPorts().add(getPort(child));
			} else if (name.equals("special-port")) {
				gcu.getPorts().add(getPort(child));
			} else if (name.equals("ctrl-operation")) {
				gcu.getOperations().add(getOperation(child, gcu.getPorts()));
			} else if (name.equals("address-space")) {
				romName = DomUtil.getNodeValue(child);
			} else if (name.equals("delay-slots")) {
				int delay = DomUtil.getNodeIntValue(child);
				gcu.setDelaySlots(delay);
			} else if (name.equals("guard-latency")) {
				int latency = DomUtil.getNodeIntValue(child);
				gcu.setDelaySlots(latency);
			} else if (name.equals("return-address")) {
				FuPort port = gcu.getPort(DomUtil.getNodeValue(child));
				gcu.setReturnAddress(port);
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}

		processor.setGcu(gcu);
	}

	/**
	 * Parses the file given to the constructor of this class.
	 * 
	 * @return a processor
	 */
	public Processor parseProcessor(InputStream inputStream) {
		try {
			// input
			Document document = DomUtil.parseDocument(inputStream);

			// parse the input, return the network
			parseADF(document);
			return processor;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new OrccRuntimeException(
						"I/O error when parsing processor", e);
			}
		}
	}

	private void parseRF(Element element) {
		RegisterFile rf = factory.createRegisterFile();
		rf.setName(DomUtil.getNodeAttr("name", element));

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("width")) {
				int width = DomUtil.getNodeIntValue(child);
				rf.setWidth(width);
			} else if (name.equals("size")) {
				int size = DomUtil.getNodeIntValue(child);
				rf.setSize(size);
			} else if (name.equals("max-reads")) {
				int maxReads = DomUtil.getNodeIntValue(child);
				rf.setMaxReads(maxReads);
			} else if (name.equals("max-writes")) {
				int maxWrites = DomUtil.getNodeIntValue(child);
				rf.setMaxWrites(maxWrites);
			} else if (name.equals("port")) {
				rf.getPorts().add(getPort(child));
			} else if (name.equals("type")) {
				// FIXME: Something to do...
			} else if (name.equals("guard-latency")) {
				// FIXME: Something to do...
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}

		processor.getRegisterFiles().add(rf);
	}

	private void parseSocket(Element element) {
		Socket socket = factory.createSocket();
		socket.setName(DomUtil.getNodeAttr("name", element));

		Element child = DomUtil.getFirstElementChild(element);
		while (child != null) {
			String name = child.getNodeName();
			if (name.equals("writes-to")) {
				// TODO
			} else if (name.equals("reads-from")) {
				// TODO
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
			child = DomUtil.getNextElementSibling(child);
		}

		processor.getSockets().add(socket);
	}

	private FuPort getPort(EList<FuPort> ports, String name) {
		for (FuPort port : ports) {
			if (port.getName().equals(name)) {
				return port;
			}
		}
		return null;
	}

}
