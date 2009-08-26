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
package net.sf.orcc.backends.c;


/**
 * Actor printer.
 * 
 * @author Mathieu Wippliez
 * 
 */
public class ActorPrinter {

//	private void printActionTest(Action action, PrinterCallback callback) {
//		String tag = action.getTagAsString();
//		if (tag.isEmpty()) {
//			tag = action.getBody().getName();
//		}
//
//		pp.indent();
//		pp.println("if (isSchedulable_" + tag + "()) {");
//
//		Map<VarDef, Integer> op = action.getOutputPattern();
//		if (!op.isEmpty()) {
//			printActionTestOutput(new TreeMap<VarDef, Integer>(op));
//		}
//
//		varDefPrinter.printName(tag);
//		pp.println("();");
//		if (callback != null) {
//			callback.print();
//		}
//		pp.unindent();
//		pp.println("res = 1;");
//
//		if (!op.isEmpty()) {
//			pp.indent();
//			pp.println("} else {");
//			pp.unindent();
//			pp.println("res = 0;");
//			pp.unindent();
//			pp.println("}");
//		}
//
//		pp.indent();
//		pp.println("} else {");
//	}
//
//	private void printActionTestOutput(Map<VarDef, Integer> op) {
//		pp.indent();
//		pp.print("if (");
//
//		// check if there is room
//		Object[] entries = op.entrySet().toArray();
//		int n = entries.length - 1;
//		for (int i = 0; i < n; i++) {
//			VarDef varDef = (VarDef) ((Entry<?, ?>) entries[i]).getKey();
//			int numTokens = (Integer) ((Entry<?, ?>) entries[i]).getValue();
//			pp.print("hasRoom(" + actorName + "_");
//			varDefPrinter.printVarDefName(varDef);
//			pp.print(", " + numTokens + ") && ");
//		}
//
//		VarDef varDef = (VarDef) ((Entry<?, ?>) entries[n]).getKey();
//		int numTokens = (Integer) ((Entry<?, ?>) entries[n]).getValue();
//		pp.print("hasRoom(" + actorName + "_");
//		varDefPrinter.printVarDefName(varDef);
//		pp.print(", " + numTokens + ")");
//
//		pp.println(") {");
//	}
//

}
