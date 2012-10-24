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
import net.sf.orcc.backends.llvm.tta.architecture.RegisterFile;
import net.sf.orcc.backends.llvm.tta.architecture.Segment;
import net.sf.orcc.backends.llvm.tta.architecture.ShortImmediate;
import net.sf.orcc.backends.llvm.tta.architecture.Socket;
import net.sf.orcc.backends.llvm.tta.architecture.Term;
import net.sf.orcc.util.DomUtil;
import net.sf.orcc.util.OrccLogger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Herve Yviquel
 * 
 */
public class AdfParser {

	private ArchitectureFactory factory = ArchitectureFactory.eINSTANCE;
	private Processor processor;
	private Map<String, Memory> memoryMap;
	private Map<FunctionUnit, String> fuToMemoryMap;

	private Operation getOperation(Element element) {
		Operation op = factory.createOperation();

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = child.getNodeName();
				if (name.equals("name")) {
					op.setName(child.getNodeValue());
				} else if (name.equals("bind")) {
					// TODO
				} else if (name.equals("pipeline")) {
					// TODO
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}

		return op;
	}

	private FuPort getPort(Element element) {
		FuPort port = factory.createFuPort();
		port.setName(element.getAttribute("name"));

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = child.getNodeName();
				if (name.equals("width")) {
					int width = Integer.parseInt(child.getNodeValue());
					port.setWidth(width);
				} else if (name.equals("trigger")) {
					port.setTrigger(true);
				} else if (name.equals("sets-optcode")) {
					port.setOpcodeSelector(true);
				} else if (name.equals("connects-to")) {
					// TODO
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}

		return port;
	}

	private ShortImmediate getShortImmediate(Element element) {
		ShortImmediate shortImmediate = factory.createShortImmediate();

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = child.getNodeName();
				if (name.equals("width")) {
					int width = Integer.parseInt(child.getNodeValue());
					shortImmediate.setWidth(width);
				} else if (name.equals("extension")) {
					Extension extension = Extension.get(child.getNodeValue());
					shortImmediate.setExtension(extension);
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}

		return shortImmediate;
	}

	private Segment getSegment(Element element) {
		Segment segment = factory.createSegment();
		return segment;
	}

	private Guard getGuard(Element element) {
		Node node = element.getFirstChild();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element child = (Element) node;
			String name = child.getNodeName();
			if (name.equals("simple-expr")) {
				ExprUnary expr = getExprUnary(child);
				expr.setOperator(OpUnary.SIMPLE);
				return expr;
			} else if (name.equals("inverted-expr")) {
				ExprUnary expr = getExprUnary(child);
				expr.setOperator(OpUnary.INVERTED);
				return expr;
			} else if (name.equals("and-expr")) {
				ExprBinary expr = getExprBinary(child);
				expr.setOperator(OpBinary.AND);
				return expr;
			} else if (name.equals("or-expr")) {
				ExprBinary expr = getExprBinary(child);
				expr.setOperator(OpBinary.OR);
				return expr;
			} else if (name.equals("always-true")) {
				return factory.createExprTrue();
			} else if (name.equals("always-false")) {
				return factory.createExprFalse();
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
		} else {
			throw new OrccRuntimeException("invalid node");
		}
	}

	private Term getTerm(Element element) {
		Node node = element.getFirstChild();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element child = (Element) node;
			String name = child.getNodeName();
			if (name.equals("bool")) {
				Term term = factory.createTermBool();
				return term;
			} else if (name.equals("inverted-expr")) {
				Term term = factory.createTermUnit();
				return term;
			} else {
				throw new OrccRuntimeException("invalid node \"" + name + "\"");
			}
		} else {
			throw new OrccRuntimeException("invalid node");
		}
	}

	private ExprUnary getExprUnary(Element element) {
		ExprUnary expr = factory.createExprUnary();
		
		Node node = element.getFirstChild();
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			OrccLogger.severeln("invalid node");
		}
		expr.setTerm(getTerm((Element) node));
		
		return expr;
	}

	private ExprBinary getExprBinary(Element element) {
		ExprBinary expr = factory.createExprBinary();
		
		Node node = element.getFirstChild();
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			OrccLogger.severeln("invalid node");
		}
		expr.setE1(getExprUnary((Element) node));
		
		node = element.getNextSibling();
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			OrccLogger.severeln("invalid node");
		}
		expr.setE2(getExprUnary((Element) node));
		
		return expr;
	}

	private void parseAddressSpace(Element element) {
		Memory memory = factory.createMemory();
		memory.setName(element.getAttribute("name"));

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = child.getNodeName();
				if (name.equals("width")) {
					int width = Integer.parseInt(child.getNodeValue());
					memory.setWordWidth(width);
				} else if (name.equals("min-address")) {
					int min = Integer.parseInt(child.getNodeValue());
					memory.setMinAddress(min);
				} else if (name.equals("max-address")) {
					int max = Integer.parseInt(child.getNodeValue());
					memory.setMinAddress(max);
				} else if (name.equals("numerical-id")) {
					// FIXME: Something to do ?
				} else if (name.equals("shared-memory")) {
					OrccLogger
							.severeln("Parsing ADF with shared memory is not supported");
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}

		memoryMap.put(memory.getName(), memory);
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

		String name = adfElement.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccRuntimeException("Expected a \"name\" attribute");
		}

		processor = ArchitectureFactory.eINSTANCE.createProcessor();
		memoryMap = new HashMap<String, Memory>();
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
		Node node = root.getFirstChild();
		while (node != null) {
			// this test allows us to skip #text nodes
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = node.getNodeName();
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
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}

			node = node.getNextSibling();
		}

		for (FunctionUnit fu : fuToMemoryMap.keySet()) {
			String name = fuToMemoryMap.get(fu);
			Memory memory = memoryMap.get(name);
			if (memory == null) {
				OrccLogger.severeln("Unknow address space \"" + name + "\".");
			}
			fu.setAddressSpace(memory);
		}
	}

	private void parseBus(Element element) {
		Bus bus = factory.createBus();
		bus.setName(element.getAttribute("name"));

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = child.getNodeName();
				if (name.equals("width")) {
					int width = Integer.parseInt(child.getNodeValue());
					bus.setWidth(width);
				} else if (name.equals("guard")) {
					bus.getGuards().add(getGuard(child));
				} else if (name.equals("segment")) {
					bus.getSegments().add(getSegment(child));
				} else if (name.equals("short-immediate")) {
					bus.setShortImmediate(getShortImmediate(child));
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}
		processor.getBuses().add(bus);
	}

	private void parseFU(Element element) {
		FunctionUnit fu = factory.createFunctionUnit();
		fu.setName(element.getAttribute("name"));

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = node.getNodeName();
				if (name.equals("port")) {
					fu.getPorts().add(getPort(child));
				} else if (name.equals("operation")) {
					fu.getOperations().add(getOperation(child));
				} else if (name.equals("address-space")) {
					fuToMemoryMap.put(fu, child.getNodeValue());
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}
		processor.getFunctionUnits().add(fu);
	}

	private void parseGCU(Element element) {
		GlobalControlUnit gcu = factory.createGlobalControlUnit();
		gcu.setName(element.getAttribute("name"));

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = node.getNodeName();
				if (name.equals("port")) {
					gcu.getPorts().add(getPort(child));
				} else if (name.equals("special-port")) {
					FuPort port = getPort(child);
					gcu.setReturnAddress(port);
				} else if (name.equals("ctrl-operation")) {
					gcu.getOperations().add(getOperation(child));
				} else if (name.equals("address-space")) {
					// TODO
				} else if (name.equals("delay-slot")) {
					int delay = Integer.parseInt(child.getNodeValue());
					gcu.setDelaySlots(delay);
				} else if (name.equals("guard-latency")) {
					int latency = Integer.parseInt(child.getNodeValue());
					gcu.setDelaySlots(latency);
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
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
		rf.setName(element.getAttribute("name"));

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = node.getNodeName();
				if (name.equals("width")) {
					int width = Integer.parseInt(child.getNodeValue());
					rf.setWidth(width);
				} else if (name.equals("size")) {
					int size = Integer.parseInt(child.getNodeValue());
					rf.setSize(size);
				} else if (name.equals("max-reads")) {
					int maxReads = Integer.parseInt(child.getNodeValue());
					rf.setMaxReads(maxReads);
				} else if (name.equals("max-writes")) {
					int maxWrites = Integer.parseInt(child.getNodeValue());
					rf.setMaxWrites(maxWrites);
				} else if (name.equals("port")) {
					rf.getPorts().add(getPort(child));
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}

		processor.getRegisterFiles().add(rf);
	}

	private void parseSocket(Element element) {
		Socket socket = factory.createSocket();
		socket.setName(element.getAttribute("name"));

		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				String name = child.getNodeName();
				if (name.equals("writes-to")) {
					// TODO
				} else if (name.equals("reads-from")) {
					// TODO
				} else {
					throw new OrccRuntimeException("invalid node \"" + name
							+ "\"");
				}
			}
			node = node.getNextSibling();
		}

		processor.getSockets().add(socket);
	}

}
