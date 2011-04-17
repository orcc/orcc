/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.simulators.codegen;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.V1_6;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import net.sf.orcc.network.Network;

import org.eclipse.core.runtime.IPath;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

/**
 * This class defines a network code generator using ASM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NetworkCodeGenerator {

	/**
	 * Returns bytecode for the given actor.
	 * 
	 * @param actor
	 *            an actor
	 * @return the bytecode for the given actor
	 */
	public InputStream generate(IPath path, Network network) {
		// String networkFileName = path.lastSegment();
		String networkName = path.removeFileExtension().lastSegment();

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		// should be cw.visit(V1_6, ACC_PUBLIC, networkName, null,
		// "java/lang/Object", null);
		cw.visit(V1_6, ACC_PUBLIC, "MPEG/MPEG4/part2/Algo_Add", null,
				"java/lang/Object", null);
		// should be cw.visitSource(networkFileName, null);
		cw.visitSource("Algo_Add.cal", null);

		// creates a GeneratorAdapter for the (implicit) constructor
		Method m = Method.getMethod("void <init> ()");
		GeneratorAdapter mg = new GeneratorAdapter(ACC_PUBLIC, m, null, null,
				cw);
		mg.loadThis();
		mg.invokeConstructor(Type.getType(Object.class), m);
		mg.returnValue();
		mg.endMethod();

		// creates a GeneratorAdapter for the 'main' method
		m = Method.getMethod("void main (String[])");
		mg = new GeneratorAdapter(ACC_PUBLIC + ACC_STATIC, m, null, null, cw);

		Label l1 = new Label();
		mg.visitLabel(l1);
		mg.visitLineNumber(73, l1);
		mg.getStatic(Type.getType(System.class), "out",
				Type.getType(PrintStream.class));
		mg.push("in " + networkName + " class!");
		mg.invokeVirtual(Type.getType(PrintStream.class),
				Method.getMethod("void println (String)"));

		Label l2 = new Label();
		mg.visitLabel(l2);
		mg.visitLineNumber(74, l2);
		mg.getStatic(Type.getType(System.class), "out",
				Type.getType(PrintStream.class));
		mg.push("Only there is no code here yet :-/");
		mg.invokeVirtual(Type.getType(PrintStream.class),
				Method.getMethod("void println (String)"));

		// end main
		mg.returnValue();
		mg.endMethod();

		cw.visitEnd();

		// return bytecode
		byte[] code = cw.toByteArray();
		return new ByteArrayInputStream(code);
	}

}
