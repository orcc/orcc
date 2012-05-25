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
