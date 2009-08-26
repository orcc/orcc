/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
package net.sf.orcc.backends.cllvm;

import java.io.IOException;

import net.sf.orcc.backends.multicore.MultiCoreNetworkPrinter;

/**
 * LLVM Network printer.
 * 
 * @author Jérôme GORIN
 * 
 */
public class CLLVMNetworkPrinter extends MultiCoreNetworkPrinter {

	public CLLVMNetworkPrinter() throws IOException {
		super("C_LLVM.st");
	}

//	private void printActorInit(DirectedMultigraph<Instance, Connection> graph) {
//		printSeparator();
//		pp.println("// Actor init");
//		pp.indent();
//		pp.println();
//		pp.println("static void init() {");
//
//		Set<Instance> instances = new TreeSet<Instance>(graph.vertexSet());
//		Object[] instArray = instances.toArray();
//		int n = instArray.length - 1;
//		for (int i = 0; i <= n; i++) {
//			if (!((Instance) instArray[i]).isBroadcast()) {
//				String instArrayId = ((Instance) instArray[i]).getId();
//				if (!instArrayId.contentEquals("source")
//						&& !instArrayId.contentEquals("display")) {
//					pp.println(instArrayId
//							+ "_scheduler = (int(*)())initModule(\""
//							+ instArrayId + "\", " + instArrayId + "_In, "
//							+ instArrayId + "_Out);");
//				}
//			}
//		}
//
//		pp.unindent();
//		pp.println("");
//		pp.println("}");
//
//	}
//
//	private void printActorStruct(DirectedMultigraph<Instance, Connection> graph) {
//		printSeparator();
//		pp.println("// Actor structure");
//		pp.indent();
//		pp.println();
//
//		PrinterCallback AdrressPrinter = new PrinterCallback() {
//
//			@Override
//			public void print(Object... args) {
//				varDefPrinter.printName(((VarDef) args[0]).getName());
//			}
//
//		};
//
//		Set<Instance> instances = new TreeSet<Instance>(graph.vertexSet());
//		Object[] instArray = instances.toArray();
//		int n = instArray.length - 1;
//
//		for (int i = 0; i <= n; i++) {
//			if (!((Instance) instArray[i]).isBroadcast()) {
//				Instance actorInst = (Instance) instArray[i];
//				String instArrayId = actorInst.getId();
//
//				List<VarDef> instInput = actorInst.getActor().getInputs();
//				List<VarDef> instOutput = actorInst.getActor().getOutputs();
//
//				if (!instArrayId.contentEquals("source")
//						&& !instArrayId.contentEquals("display")) {
//					pp.print("lff_t* " + instArrayId + "_In["
//							+ instInput.size() + "] = {" + instArrayId + "_");
//					pp.printList(", " + instArrayId + "_", instInput,
//							AdrressPrinter);
//					pp.println("};");
//
//					pp.print("lff_t* " + instArrayId + "_Out["
//							+ instOutput.size() + "]= {" + instArrayId + "_");
//					pp.printList(", " + instArrayId + "_", instOutput,
//							AdrressPrinter);
//					pp.println("};");
//				}
//			}
//
//		}
//		pp.unindent();
//		pp.println("");
//
//	}
//
//	@Override
//	protected void printConnectionPointers(
//			DirectedMultigraph<Instance, Connection> graph) {
//		pp.println("// FIFO pointer assignments");
//		pp.println();
//
//		Set<Instance> instances = new TreeSet<Instance>(graph.vertexSet());
//		for (Instance instance : instances) {
//			Set<Connection> connections = graph.outgoingEdgesOf(instance);
//			for (Connection connection : connections) {
//				Instance source = graph.getEdgeSource(connection);
//				Instance target = graph.getEdgeTarget(connection);
//
//				int fifoCount = fifos.get(connection);
//				if (source.getId().contentEquals("source")) {
//					pp.print("extern \"C\" lff_t *" + source.getId() + "_");
//				} else {
//					pp.print("lff_t *" + source.getId() + "_");
//				}
//				varDefPrinter.printVarDefName(connection.getSource());
//				pp.println(" = &fifo_" + fifoCount + ";");
//
//				if (target.getId().contentEquals("display")) {
//					pp.print("extern \"C\" lff_t *" + target.getId() + "_");
//				} else {
//					pp.print("lff_t *" + target.getId() + "_");
//				}
//				varDefPrinter.printVarDefName(connection.getTarget());
//				pp.println(" = &fifo_" + fifoCount + ";");
//			}
//		}
//	}
//

}
