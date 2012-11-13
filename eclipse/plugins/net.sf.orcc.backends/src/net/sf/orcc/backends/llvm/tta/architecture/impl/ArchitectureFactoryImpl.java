/*
 * Copyright (c) 2011, IRISA
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
package net.sf.orcc.backends.llvm.tta.architecture.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import net.sf.orcc.backends.llvm.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.llvm.tta.architecture.Bridge;
import net.sf.orcc.backends.llvm.tta.architecture.Bus;
import net.sf.orcc.backends.llvm.tta.architecture.Component;
import net.sf.orcc.backends.llvm.tta.architecture.Design;
import net.sf.orcc.backends.llvm.tta.architecture.Element;
import net.sf.orcc.backends.llvm.tta.architecture.ExprBinary;
import net.sf.orcc.backends.llvm.tta.architecture.ExprFalse;
import net.sf.orcc.backends.llvm.tta.architecture.ExprTrue;
import net.sf.orcc.backends.llvm.tta.architecture.ExprUnary;
import net.sf.orcc.backends.llvm.tta.architecture.Extension;
import net.sf.orcc.backends.llvm.tta.architecture.FuPort;
import net.sf.orcc.backends.llvm.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.llvm.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Guard;
import net.sf.orcc.backends.llvm.tta.architecture.Implementation;
import net.sf.orcc.backends.llvm.tta.architecture.Memory;
import net.sf.orcc.backends.llvm.tta.architecture.OpBinary;
import net.sf.orcc.backends.llvm.tta.architecture.OpUnary;
import net.sf.orcc.backends.llvm.tta.architecture.Operation;
import net.sf.orcc.backends.llvm.tta.architecture.Port;
import net.sf.orcc.backends.llvm.tta.architecture.Processor;
import net.sf.orcc.backends.llvm.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.llvm.tta.architecture.Reads;
import net.sf.orcc.backends.llvm.tta.architecture.RegisterFile;
import net.sf.orcc.backends.llvm.tta.architecture.Resource;
import net.sf.orcc.backends.llvm.tta.architecture.Segment;
import net.sf.orcc.backends.llvm.tta.architecture.ShortImmediate;
import net.sf.orcc.backends.llvm.tta.architecture.Signal;
import net.sf.orcc.backends.llvm.tta.architecture.Socket;
import net.sf.orcc.backends.llvm.tta.architecture.SocketType;
import net.sf.orcc.backends.llvm.tta.architecture.Term;
import net.sf.orcc.backends.llvm.tta.architecture.TermBool;
import net.sf.orcc.backends.llvm.tta.architecture.TermUnit;
import net.sf.orcc.backends.llvm.tta.architecture.Writes;
import net.sf.orcc.backends.llvm.tta.architecture.util.AdfParser;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class ArchitectureFactoryImpl extends EFactoryImpl implements
		ArchitectureFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ArchitecturePackage getPackage() {
		return ArchitecturePackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static ArchitectureFactory init() {
		try {
			ArchitectureFactory theArchitectureFactory = (ArchitectureFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://orcc.sf.net/backends/tta/architecture/TTA_architecture");
			if (theArchitectureFactory != null) {
				return theArchitectureFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ArchitectureFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public ArchitectureFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertExtensionToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOpBinaryToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOpUnaryToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProcessorConfigurationToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSocketTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case ArchitecturePackage.PROCESSOR_CONFIGURATION:
			return convertProcessorConfigurationToString(eDataType,
					instanceValue);
		case ArchitecturePackage.SOCKET_TYPE:
			return convertSocketTypeToString(eDataType, instanceValue);
		case ArchitecturePackage.EXTENSION:
			return convertExtensionToString(eDataType, instanceValue);
		case ArchitecturePackage.OP_UNARY:
			return convertOpUnaryToString(eDataType, instanceValue);
		case ArchitecturePackage.OP_BINARY:
			return convertOpBinaryToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '"
					+ eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case ArchitecturePackage.DESIGN:
			return createDesign();
		case ArchitecturePackage.SIGNAL:
			return createSignal();
		case ArchitecturePackage.COMPONENT:
			return createComponent();
		case ArchitecturePackage.PORT:
			return createPort();
		case ArchitecturePackage.MEMORY:
			return createMemory();
		case ArchitecturePackage.PROCESSOR:
			return createProcessor();
		case ArchitecturePackage.BUS:
			return createBus();
		case ArchitecturePackage.BRIDGE:
			return createBridge();
		case ArchitecturePackage.SEGMENT:
			return createSegment();
		case ArchitecturePackage.GLOBAL_CONTROL_UNIT:
			return createGlobalControlUnit();
		case ArchitecturePackage.FUNCTION_UNIT:
			return createFunctionUnit();
		case ArchitecturePackage.REGISTER_FILE:
			return createRegisterFile();
		case ArchitecturePackage.FU_PORT:
			return createFuPort();
		case ArchitecturePackage.SOCKET:
			return createSocket();
		case ArchitecturePackage.OPERATION:
			return createOperation();
		case ArchitecturePackage.READS:
			return createReads();
		case ArchitecturePackage.WRITES:
			return createWrites();
		case ArchitecturePackage.RESOURCE:
			return createResource();
		case ArchitecturePackage.SHORT_IMMEDIATE:
			return createShortImmediate();
		case ArchitecturePackage.EXPR_UNARY:
			return createExprUnary();
		case ArchitecturePackage.EXPR_BINARY:
			return createExprBinary();
		case ArchitecturePackage.EXPR_TRUE:
			return createExprTrue();
		case ArchitecturePackage.EXPR_FALSE:
			return createExprFalse();
		case ArchitecturePackage.TERM_BOOL:
			return createTermBool();
		case ArchitecturePackage.TERM_UNIT:
			return createTermUnit();
		case ArchitecturePackage.IMPLEMENTATION:
			return createImplementation();
		case ArchitecturePackage.TYPE_TO_IMPL_MAP_ENTRY:
			return (EObject) createTypeToImplMapEntry();
		case ArchitecturePackage.PORT_TO_INDEX_MAP_ENTRY:
			return (EObject) createPortToIndexMapEntry();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	public FunctionUnit createAlu2Unit(Processor tta, String name) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket i2 = createInputSocket(name + "_i2", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		Socket o2 = createOutputSocket(name + "_o2", segments);
		// Port
		FuPort in1t = createFuPort("in1t", 32, true, true);
		FuPort in2 = createFuPort("in2", 32, false, false);
		FuPort out1 = createFuPort("out1", 32, false, false);
		FuPort out2 = createFuPort("out2", 32, false, false);
		in1t.connect(i1);
		in2.connect(i2);
		out1.connect(o1);
		out2.connect(o2);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(in2);
		functionUnit.getPorts().add(out1);
		functionUnit.getPorts().add(out2);
		functionUnit.setImplementation("ALU");

		// Operations (Shift operations use inverse binding)
		String[] oneInputOps = { "abs", "sxhw", "sxqw", "neg" };
		String[] twoInputOps = { "add", "and", "andn", "eq", "gt", "gtu", "lt",
				"ltu", "ior", "sub", "xor" };
		String[] twoInputOutputOps = { "addsub" };
		String[] shiftOps = { "shl", "shr", "shru" };
		for (String operation : oneInputOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in1t, out1));
		}
		for (String operation : twoInputOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in1t, in2, out1));
		}
		for (String operation : twoInputOutputOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in1t, in2, out1, out2));
		}
		for (String operation : shiftOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in2, in1t, out1));
		}
		return functionUnit;
	}

	public FunctionUnit createOrccFU(Processor tta) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		String name = "ORCC_FU";
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket i2 = createInputSocket(name + "_i2", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		// Port
		FuPort in1t = createFuPort("in1t", 32, true, true);
		FuPort in2 = createFuPort("in2", 32, false, false);
		FuPort out = createFuPort("out", 32, false, false);
		in1t.connect(i1);
		in2.connect(i2);
		out.connect(o1);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(in2);
		functionUnit.getPorts().add(out);
		functionUnit.setImplementation(name);

		String[] oneInputOneOutputOps = { "source_readbyte",
				"source_sizeoffile", "source_ismaxloopsreached" };
		String[] oneInputOps = { "source_init", "source_rewind",
				"source_decrementnbloops", "print_cyclecount" };
		String[] twoInputOps = { "tta_print", "source_exit", "display_checksum" };
		for (String operation : oneInputOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in1t));
		}
		for (String operation : oneInputOneOutputOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in1t, out));
		}
		for (String operation : twoInputOps) {
			functionUnit.getOperations().add(
					createOperationDefault2(operation, in1t, in2));
		}
		return functionUnit;
	}

	public FunctionUnit createIoFU(Processor tta) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		String name = "IO";
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		// Port
		FuPort in1t = createFuPort("T", 8, true, true);
		in1t.connect(i1);
		functionUnit.getPorts().add(in1t);
		functionUnit.setImplementation(name);
		functionUnit.getOperations()
				.add(createOperationDefault("stdout", in1t));
		return functionUnit;
	}

	@Override
	public FunctionUnit createAluUnit(Processor tta, String name) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket i2 = createInputSocket(name + "_i2", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		// Port
		FuPort in1t = createFuPort("in1t", 32, true, true);
		FuPort in2 = createFuPort("in2", 32, false, false);
		FuPort out1 = createFuPort("out1", 32, false, false);
		in1t.connect(i1);
		in2.connect(i2);
		out1.connect(o1);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(in2);
		functionUnit.getPorts().add(out1);
		functionUnit.setImplementation("ALU");

		// Operations (Shift operations use inverse binding)
		String[] oneInputOps = { "sxqw", "sxhw" };
		String[] twoInputOps = { "add", "and", "eq", "gt", "gtu", "ior", "sub",
				"xor" };
		String[] shiftOps = { "shl", "shr", "shru" };
		for (String operation : oneInputOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in1t, out1));
		}
		for (String operation : twoInputOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in1t, in2, out1));
		}
		for (String operation : shiftOps) {
			functionUnit.getOperations().add(
					createOperationDefault(operation, in2, in1t, out1));
		}
		return functionUnit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Bridge createBridge() {
		BridgeImpl bridge = new BridgeImpl();
		return bridge;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Bus createBus() {
		BusImpl bus = new BusImpl();
		return bus;
	}

	@Override
	public Bus createBus(int index, int width) {
		BusImpl bus = new BusImpl();
		bus.setName("Bus" + index);
		bus.setWidth(width);
		return bus;
	}

	@Override
	public Bus createBusDefault(int index, int width) {
		Bus bus = createBus(index, width);
		bus.getSegments().add(createSegment("segment0"));
		bus.setShortImmediate(createShortImmediate(width, Extension.ZERO));
		return bus;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Component createComponent() {
		ComponentImpl component = new ComponentImpl();
		return component;
	}

	@Override
	public Component createComponent(String name) {
		ComponentImpl component = new ComponentImpl();
		component.setName(name);
		return component;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public Design createDesign() {
		DesignImpl design = new DesignImpl();
		EMap<String, Implementation> hwDb = design.getHardwareDatabase();
		hwDb.put("ALU", createImplementation("asic_130nm_1.5V.hdb", 375));
		hwDb.put("RF", createImplementation("asic_130nm_1.5V.hdb", 96));
		hwDb.put("LSU", createImplementation("stratixII.hdb", 2));
		hwDb.put("MUL", createImplementation("asic_130nm_1.5V.hdb", 88));
		hwDb.put("LU", createImplementation("asic_130nm_1.5V.hdb", 22));
		hwDb.put("SIG_OUT", createImplementation("stratixIII.hdb", 2));
		return design;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprBinary createExprBinary() {
		ExprBinaryImpl exprBinary = new ExprBinaryImpl();
		return exprBinary;
	}

	@Override
	public ExprBinary createExprBinary(OpBinary op, ExprUnary e1, ExprUnary e2) {
		ExprBinaryImpl exprBinary = new ExprBinaryImpl();
		exprBinary.setE1(e1);
		exprBinary.setE2(e2);
		exprBinary.setOperator(op);
		return exprBinary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprFalse createExprFalse() {
		ExprFalseImpl exprFalse = new ExprFalseImpl();
		return exprFalse;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprTrue createExprTrue() {
		ExprTrueImpl exprTrue = new ExprTrueImpl();
		return exprTrue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ExprUnary createExprUnary() {
		ExprUnaryImpl exprUnary = new ExprUnaryImpl();
		return exprUnary;
	}

	@Override
	public ExprUnary createExprUnary(boolean isInverted, Term term) {
		ExprUnaryImpl exprUnary = new ExprUnaryImpl();
		if (isInverted) {
			exprUnary.setOperator(OpUnary.INVERTED);
		} else {
			exprUnary.setOperator(OpUnary.SIMPLE);
		}
		exprUnary.setTerm(term);
		return exprUnary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Extension createExtensionFromString(EDataType eDataType,
			String initialValue) {
		Extension result = Extension.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case ArchitecturePackage.PROCESSOR_CONFIGURATION:
			return createProcessorConfigurationFromString(eDataType,
					initialValue);
		case ArchitecturePackage.SOCKET_TYPE:
			return createSocketTypeFromString(eDataType, initialValue);
		case ArchitecturePackage.EXTENSION:
			return createExtensionFromString(eDataType, initialValue);
		case ArchitecturePackage.OP_UNARY:
			return createOpUnaryFromString(eDataType, initialValue);
		case ArchitecturePackage.OP_BINARY:
			return createOpBinaryFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '"
					+ eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionUnit createFunctionUnit() {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		return functionUnit;
	}

	@Override
	public FunctionUnit createFunctionUnit(Processor tta, String name,
			String type) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket i2 = createInputSocket(name + "_i2", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		// Port
		FuPort in1t = createFuPort("in1t", 32, true, true);
		FuPort in2 = createFuPort("in2", 32, false, false);
		FuPort out1 = createFuPort("out1", 32, false, false);
		in1t.connect(i1);
		in2.connect(i2);
		out1.connect(o1);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(in2);
		functionUnit.getPorts().add(out1);
		functionUnit.setImplementation(type);
		return functionUnit;
	}

	@Override
	public FunctionUnit createFunctionUnit(Processor tta, String name,
			String[] operations1, String[] operations2, String type) {
		FunctionUnit functionUnit = createFunctionUnit(tta, name, type);
		EList<FuPort> ports = functionUnit.getPorts();
		// Operations
		if (operations1 != null) {
			for (String operation : operations1) {
				functionUnit.getOperations().add(
						createOperationDefault(operation, ports.get(0),
								ports.get(2)));
			}
		}
		if (operations2 != null) {
			for (String operation : operations2) {
				functionUnit.getOperations().add(
						createOperationDefault(operation, ports.get(0),
								ports.get(1), ports.get(2)));
			}
		}
		return functionUnit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FuPort createFuPort() {
		FuPortImpl fuPort = new FuPortImpl();
		return fuPort;
	}

	@Override
	public FuPort createFuPort(String name) {
		FuPortImpl port = new FuPortImpl();
		port.setName(name);
		return port;
	}

	@Override
	public FuPort createFuPort(String name, int width,
			boolean isOpcodeSelector, boolean isTrigger) {
		FuPort port = createFuPort(name);
		port.setWidth(width);
		port.setOpcodeSelector(isOpcodeSelector);
		port.setTrigger(isTrigger);
		return port;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalControlUnit createGlobalControlUnit() {
		GlobalControlUnitImpl globalControlUnit = new GlobalControlUnitImpl();
		return globalControlUnit;
	}

	@Override
	public GlobalControlUnit createGlobalControlUnit(int delaySlots,
			int guardLatency) {
		GlobalControlUnitImpl globalControlUnit = new GlobalControlUnitImpl();
		globalControlUnit.setDelaySlots(delaySlots);
		globalControlUnit.setGuardLatency(guardLatency);
		return globalControlUnit;
	}

	@Override
	public GlobalControlUnit createGlobalControlUnitDefault(Processor tta,
			Memory program) {
		GlobalControlUnit gcu = createGlobalControlUnit(3, 1);
		gcu.setName("gcu");
		gcu.setAddressSpace(program);

		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket gcu_i1 = createInputSocket("gcu_i1", segments);
		Socket gcu_i2 = createInputSocket("gcu_i2", segments);
		Socket gcu_o1 = createOutputSocket("gcu_o1", segments);

		// Ports
		FuPort pc = createFuPort("pc", 32, true, true);
		pc.connect(gcu_i1);
		FuPort ra = createFuPort("ra", 32, false, false);
		ra.connect(gcu_i2);
		ra.connect(gcu_o1);

		gcu.setReturnAddress(ra);
		gcu.getPorts().add(pc);

		// Control operations
		gcu.getOperations().add(createOperationCtrl("jump", pc));
		gcu.getOperations().add(createOperationCtrl("call", pc));

		return gcu;
	}

	@Override
	public EList<Guard> createGuardsDefault(RegisterFile register) {
		EList<Guard> guards = new BasicEList<Guard>();
		guards.add(createExprTrue());
		guards.add(createExprUnary(false, createTermBool(register, 0)));
		guards.add(createExprUnary(true, createTermBool(register, 0)));
		guards.add(createExprUnary(false, createTermBool(register, 1)));
		guards.add(createExprUnary(true, createTermBool(register, 1)));
		return guards;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Implementation createImplementation() {
		ImplementationImpl implementation = new ImplementationImpl();
		return implementation;
	}

	@Override
	public Implementation createImplementation(String hdbFile, int id) {
		ImplementationImpl implementation = new ImplementationImpl();
		implementation.setHdbFile(hdbFile);
		implementation.setId(id);
		return implementation;
	}

	@Override
	public Socket createInputSocket(String name, EList<Segment> segments) {
		Socket socket = createSocket(name, segments);
		socket.setType(SocketType.INPUT);
		return socket;
	}

	@Override
	public FunctionUnit createLSU(int id, Processor tta, Memory addressSpace) {
		FunctionUnit LSU = createFunctionUnit(tta, "LSU_" + id, "LSU");
		// Operations
		EList<FuPort> ports = LSU.getPorts();
		String[] loadOperations = { "ldw", "ldq", "ldh", "ldqu", "ldhu" };
		for (String loadOperation : loadOperations) {
			LSU.getOperations().add(
					createOperationLoad(loadOperation, ports.get(0),
							ports.get(2)));
		}
		String[] storeOperations = { "stw", "stq", "sth" };
		for (String storeOperation : storeOperations) {
			LSU.getOperations().add(
					createOperationStore(storeOperation, ports.get(0),
							ports.get(1)));
		}
		LSU.setAddressSpace(addressSpace);
		return LSU;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Memory createMemory() {
		MemoryImpl memory = new MemoryImpl();
		return memory;
	}

	@Override
	public Memory createMemory(String name) {
		Memory addressSpace = new MemoryImpl();
		addressSpace.setName(name);
		return addressSpace;
	}

	@Override
	public FunctionUnit createMultiplier(String name, Processor tta) {
		FunctionUnit multiplier = createFunctionUnit(tta, name, "MUL");
		multiplier.getOperations().add(
				createOperationMul("mul", multiplier.getPorts().get(0),
						multiplier.getPorts().get(1), multiplier.getPorts()
								.get(2)));
		return multiplier;
	}

	public FunctionUnit createMultiplier2(String name, Processor tta) {
		FunctionUnit multiplier = createFunctionUnit(tta, name, "MUL");
		multiplier.getOperations().add(
				createOperationMul("mul", multiplier.getPorts().get(0),
						multiplier.getPorts().get(1), multiplier.getPorts()
								.get(2)));
		return multiplier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public OpBinary createOpBinaryFromString(EDataType eDataType,
			String initialValue) {
		OpBinary result = OpBinary.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Operation createOperation() {
		OperationImpl operation = new OperationImpl();
		return operation;
	}

	@Override
	public Operation createOperation(String name) {
		OperationImpl operation = new OperationImpl();
		operation.setName(name);
		return operation;
	}

	@Override
	public Operation createOperationCtrl(String name, FuPort port) {
		Operation operation = new OperationImpl();
		operation.setName(name);
		operation.setControl(true);
		operation.getPipeline().add(createReads(port, 0, 1));
		operation.getPortToIndexMap().put(port, 1);
		return operation;
	}

	private Operation createOperationDefault(String name, FuPort in1,
			FuPort out1) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createWrites(out1, 0, 1));
		return operation;
	}

	private Operation createOperationDefault(String name, FuPort in1) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		return operation;
	}

	private Operation createOperationDefault(String name, FuPort in1,
			FuPort in2, FuPort out1) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createReads(in2, 0, 1));
		operation.getPipeline().add(createWrites(out1, 0, 1));
		return operation;
	}

	private Operation createOperationDefault2(String name, FuPort in1,
			FuPort in2) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createReads(in2, 0, 1));
		return operation;
	}

	private Operation createOperationDefault(String name, FuPort in1,
			FuPort in2, FuPort out1, FuPort out2) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createReads(in2, 0, 1));
		operation.getPipeline().add(createWrites(out1, 0, 1));
		operation.getPipeline().add(createWrites(out2, 0, 1));
		return operation;
	}

	private Operation createOperationDefault(String name, FuPort port1,
			int startCycle1, int cycle1, boolean isReads1, FuPort port2,
			int startCycle2, int cycle2, boolean isReads2) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		Element element1, element2;
		if (isReads1) {
			element1 = createReads(port1, startCycle1, cycle1);
		} else {
			element1 = createWrites(port1, startCycle1, cycle1);
		}
		if (isReads2) {
			element2 = createReads(port2, startCycle2, cycle2);
		} else {
			element2 = createWrites(port2, startCycle2, cycle2);
		}
		operation.getPipeline().add(element1);
		operation.getPipeline().add(element2);
		return operation;
	}

	private Operation createOperationLoad(String name, FuPort in, FuPort out) {
		return createOperationDefault(name, in, 0, 1, true, out, 2, 1, false);
	}

	private Operation createOperationMul(String name, FuPort in1, FuPort in2,
			FuPort out) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createReads(in2, 0, 1));
		operation.getPipeline().add(createWrites(out, 1, 1));
		return operation;
	}

	private Operation createOperationStore(String name, FuPort in1, FuPort in2) {
		return createOperationDefault(name, in1, 0, 1, true, in2, 0, 1, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public OpUnary createOpUnaryFromString(EDataType eDataType,
			String initialValue) {
		OpUnary result = OpUnary.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	@Override
	public FunctionUnit createOutputSignalUnit(Processor tta, String signalName) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		String name = "SIG_OUT_" + signalName;
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		// Port
		FuPort in1t = createFuPort("in1t", 8, true, true);
		in1t.connect(i1);
		functionUnit.getPorts().add(in1t);
		// Operation
		Operation write = createOperation("leds");
		write.setControl(false);
		write.getPipeline().add(createReads(in1t, 0, 1));
		functionUnit.getOperations().add(write);
		functionUnit.setImplementation("SIG_OUT");
		return functionUnit;
	}

	@Override
	public Socket createOutputSocket(String name, EList<Segment> segments) {
		Socket socket = createSocket(name, segments);
		socket.setType(SocketType.OUTPUT);
		return socket;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	@Override
	public Port createPort(net.sf.orcc.df.Port oldPort) {
		PortImpl port = new PortImpl();
		port.setName(oldPort.getName());
		port.addAttribute("native");
		port.setAttribute("size", oldPort.getType().getSizeInBits());
		return port;
	}

	@Override
	public Port createPort(String name) {
		PortImpl port = new PortImpl();
		port.setName(name);
		port.addAttribute("native");
		return port;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<FuPort, Integer> createPortToIndexMapEntry() {
		PortToIndexMapEntryImpl portToIndexMapEntry = new PortToIndexMapEntryImpl();
		return portToIndexMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Processor createProcessor() {
		ProcessorImpl processor = new ProcessorImpl();
		return processor;
	}

	@Override
	public Processor createProcessor(String name, ProcessorConfiguration conf,
			int ramSize) {
		Processor processor = null;

		if (conf == ProcessorConfiguration.FAST) {
			try {
				FileInputStream fis = new FileInputStream(
						"/home/hyviquel/Workspaces/experimental/orcc/eclipse/plugins/net.sf.orcc.backends/runtime/TTA/adf/clustered_mul4.adf");
				processor = new AdfParser().parseProcessor(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			processor = createProcessor();

			// Address spaces
			Memory program = createMemory("instructions");
			Memory data = createMemory("data");
			processor.setROM(program);
			processor.getLocalRAMs().add(data);
			// Buses
			for (int i = 0; i < conf.getBusNb(); i++) {
				Bus bus = createBusDefault(i, 32);
				processor.getBuses().add(bus);
			}
			// Global Control Unit
			processor
					.setGcu(createGlobalControlUnitDefault(processor, program));
			// Register files
			RegisterFile bool = createRegisterFileDefault(processor, "BOOL",
					conf.getBoolRfSize(), 1);
			processor.getRegisterFiles().add(bool);
			for (int i = 0; i < conf.getIntRfNb(); i++) {
				RegisterFile rf = createRegisterFileDefault(processor, "RF_"
						+ i, conf.getIntRfSize(), 32);
				processor.getRegisterFiles().add(rf);
			}
			// Guards
			for (Bus bus : processor.getBuses()) {
				bus.getGuards().addAll(createGuardsDefault(bool));
			}
			// Functional units
			EList<FunctionUnit> units = processor.getFunctionUnits();
			// * ALU
			for (int i = 0; i < conf.getAluNb(); i++) {
				FunctionUnit alu = createAluUnit(processor, "ALU_" + i);
				units.add(alu);
			}
			// * LSU
			for (int i = 0; i < conf.getLsuNb(); i++) {
				units.add(createLSU(i, processor, data));
			}
			// * Mul
			for (int i = 0; i < conf.getMulNb(); i++) {
				units.add(createMultiplier("Mul_" + i, processor));
			}
			// * And-ior-xor
			String[] aixOperations2 = { "and", "ior", "xor" };
			units.add(createFunctionUnit(processor, "And_ior_xor", null,
					aixOperations2, "LU"));
		}

		processor.setName(name);

		return processor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessorConfiguration createProcessorConfigurationFromString(
			EDataType eDataType, String initialValue) {
		ProcessorConfiguration result = ProcessorConfiguration
				.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Reads createReads() {
		ReadsImpl reads = new ReadsImpl();
		return reads;
	}

	@Override
	public Reads createReads(FuPort port, int startCycle, int cycle) {
		ReadsImpl reads = new ReadsImpl();
		reads.setPort(port);
		reads.setStartCycle(startCycle);
		reads.setCycles(cycle);
		return reads;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RegisterFile createRegisterFile() {
		RegisterFileImpl registerFile = new RegisterFileImpl();
		return registerFile;
	}

	@Override
	public RegisterFile createRegisterFile(String name, int size, int width,
			int maxReads, int maxWrites) {
		RegisterFileImpl registerFile = new RegisterFileImpl();
		registerFile.setName(name);
		registerFile.setSize(size);
		registerFile.setWidth(width);
		registerFile.setMaxReads(maxReads);
		registerFile.setMaxWrites(maxWrites);
		registerFile.setImplementation("RF");
		return registerFile;
	}

	@Override
	public RegisterFile createRegisterFileDefault(Processor tta, String name,
			int size, int width) {
		RegisterFile registerFile = createRegisterFile(name, size, width, 1, 1);

		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);

		// Ports
		FuPort wr = createFuPort("wr");
		wr.connect(o1);
		FuPort rd = createFuPort("rd");
		rd.connect(i1);
		registerFile.getPorts().add(wr);
		registerFile.getPorts().add(rd);

		return registerFile;
	}

	public RegisterFile createRegisterFileDefault2(Processor tta, String name,
			int size, int width) {
		RegisterFile registerFile = createRegisterFile(name, size, width, 2, 1);

		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		Socket o2 = createOutputSocket(name + "_o2", segments);

		// Ports
		FuPort wr = createFuPort("wr");
		wr.connect(o1);
		FuPort wr2 = createFuPort("wr2");
		wr2.connect(o2);
		FuPort rd = createFuPort("rd");
		rd.connect(i1);
		registerFile.getPorts().add(wr);
		registerFile.getPorts().add(rd);
		registerFile.getPorts().add(wr2);

		return registerFile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Resource createResource() {
		ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	@Override
	public Resource createResource(String name, int startCycle, int cycles) {
		ResourceImpl resource = new ResourceImpl();
		resource.setName(name);
		resource.setStartCycle(startCycle);
		resource.setCycles(cycles);
		return resource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Segment createSegment() {
		SegmentImpl segment = new SegmentImpl();
		return segment;
	}

	@Override
	public Segment createSegment(String name) {
		SegmentImpl segment = new SegmentImpl();
		segment.setName(name);
		return segment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ShortImmediate createShortImmediate() {
		ShortImmediateImpl shortImmediate = new ShortImmediateImpl();
		return shortImmediate;
	}

	@Override
	public ShortImmediate createShortImmediate(int width, Extension extension) {
		ShortImmediateImpl shortImmediate = new ShortImmediateImpl();
		shortImmediate.setWidth(width);
		shortImmediate.setExtension(extension);
		return shortImmediate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Signal createSignal() {
		SignalImpl signal = new SignalImpl();
		return signal;
	}

	@Override
	public Signal createSignal(String name, int size, Vertex source,
			Vertex target, Port sourcePort, Port targetPort) {
		SignalImpl signal = new SignalImpl();
		signal.setLabel(name);
		signal.setSize(size);
		signal.setSourcePort(sourcePort);
		signal.setTargetPort(targetPort);
		signal.setSource(source);
		signal.setTarget(target);
		return signal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Socket createSocket() {
		SocketImpl socket = new SocketImpl();
		return socket;
	}

	@Override
	public Socket createSocket(String name, EList<Segment> segments) {
		SocketImpl socket = new SocketImpl();
		socket.setName(name);
		socket.getConnectedSegments().addAll(segments);
		EcoreHelper.getContainerOfType(segments.get(0), Processor.class)
				.getSockets().add(socket);
		return socket;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SocketType createSocketTypeFromString(EDataType eDataType,
			String initialValue) {
		SocketType result = SocketType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName()
					+ "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TermBool createTermBool() {
		TermBoolImpl termBool = new TermBoolImpl();
		return termBool;
	}

	@Override
	public TermBool createTermBool(RegisterFile register, int index) {
		TermBoolImpl termBool = new TermBoolImpl();
		termBool.setRegister(register);
		termBool.setIndex(index);
		return termBool;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TermUnit createTermUnit() {
		TermUnitImpl termUnit = new TermUnitImpl();
		return termUnit;
	}

	@Override
	public TermUnit createTermUnit(FunctionUnit unit, FuPort port) {
		TermUnitImpl termUnit = new TermUnitImpl();
		termUnit.setFunctionUnit(unit);
		termUnit.setPort(port);
		return termUnit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Implementation> createTypeToImplMapEntry() {
		TypeToImplMapEntryImpl typeToImplMapEntry = new TypeToImplMapEntryImpl();
		return typeToImplMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Writes createWrites() {
		WritesImpl writes = new WritesImpl();
		return writes;
	}

	@Override
	public Writes createWrites(FuPort port, int startCycle, int cycle) {
		WritesImpl writes = new WritesImpl();
		writes.setPort(port);
		writes.setStartCycle(startCycle);
		writes.setCycles(cycle);
		return writes;
	}

	private EList<Segment> getAllSegments(EList<Bus> buses) {
		EList<Segment> segments = new BasicEList<Segment>();
		for (Bus bus : buses) {
			segments.addAll(bus.getSegments());
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitecturePackage getArchitecturePackage() {
		return (ArchitecturePackage) getEPackage();
	}

} // ArchitectureFactoryImpl
