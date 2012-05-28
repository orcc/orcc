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
package net.sf.orcc.backends.llvm.tta.architecture.util;

import net.sf.orcc.backends.llvm.tta.architecture.Bus;
import net.sf.orcc.backends.llvm.tta.architecture.Component;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.RegisterFile;
import net.sf.orcc.backends.llvm.tta.architecture.Segment;

public class ArchitectureVisitor<T> extends ArchitectureSwitch<T> {

	@Override
	public T caseBus(Bus object) {
		return null;
	}

	@Override
	public T caseComponent(Component component) {
		return null;
	}

	@Override
	public T caseDesign(Design design) {
		for (Memory buffer : design.getSharedMemories()) {
			doSwitch(buffer);
		}
		for (Processor processor : design.getProcessors()) {
			doSwitch(processor);
		}
		for (Component component : design.getComponents()) {
			doSwitch(component);
		}
		return null;
	}

	@Override
	public T caseFunctionUnit(FunctionUnit object) {
		return null;
	}

	@Override
	public T caseSegment(Segment object) {
		return null;
	}

	@Override
	public T caseGlobalControlUnit(GlobalControlUnit object) {
		return null;
	}

	@Override
	public T caseMemory(Memory buffer) {
		return null;
	}

	@Override
	public T caseProcessor(Processor processor) {
		for (Memory memory : processor.getLocalRAMs()) {
			doSwitch(memory);
		}
		for (FunctionUnit functionUnit : processor.getFunctionUnits()) {
			doSwitch(functionUnit);
		}
		for (RegisterFile registerFile : processor.getRegisterFiles()) {
			doSwitch(registerFile);
		}
		for (Bus bus : processor.getBuses()) {
			doSwitch(bus);
		}
		doSwitch(processor.getGcu());
		return null;
	}

	@Override
	public T caseRegisterFile(RegisterFile object) {
		return null;
	}
}
