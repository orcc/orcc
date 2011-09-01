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
package net.sf.orcc.backends.tta.architecture.impl;

import java.util.Map;

import net.sf.orcc.backends.tta.architecture.AddressSpace;
import net.sf.orcc.backends.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.Bridge;
import net.sf.orcc.backends.tta.architecture.Bus;
import net.sf.orcc.backends.tta.architecture.Element;
import net.sf.orcc.backends.tta.architecture.ExprBinary;
import net.sf.orcc.backends.tta.architecture.ExprFalse;
import net.sf.orcc.backends.tta.architecture.ExprTrue;
import net.sf.orcc.backends.tta.architecture.ExprUnary;
import net.sf.orcc.backends.tta.architecture.Extension;
import net.sf.orcc.backends.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.tta.architecture.Guard;
import net.sf.orcc.backends.tta.architecture.Implementation;
import net.sf.orcc.backends.tta.architecture.OpBinary;
import net.sf.orcc.backends.tta.architecture.OpUnary;
import net.sf.orcc.backends.tta.architecture.Operation;
import net.sf.orcc.backends.tta.architecture.Port;
import net.sf.orcc.backends.tta.architecture.Reads;
import net.sf.orcc.backends.tta.architecture.RegisterFile;
import net.sf.orcc.backends.tta.architecture.Resource;
import net.sf.orcc.backends.tta.architecture.Segment;
import net.sf.orcc.backends.tta.architecture.ShortImmediate;
import net.sf.orcc.backends.tta.architecture.Socket;
import net.sf.orcc.backends.tta.architecture.SocketType;
import net.sf.orcc.backends.tta.architecture.TTA;
import net.sf.orcc.backends.tta.architecture.Term;
import net.sf.orcc.backends.tta.architecture.TermBool;
import net.sf.orcc.backends.tta.architecture.TermUnit;
import net.sf.orcc.backends.tta.architecture.Writes;
import net.sf.orcc.backends.tta.architecture.util.ArchitectureMemoryStats;
import net.sf.orcc.network.Instance;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ArchitectureFactoryImpl extends EFactoryImpl implements
		ArchitectureFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ArchitecturePackage getPackage() {
		return ArchitecturePackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
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
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public ArchitectureFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertExtensionToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertOpBinaryToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertOpUnaryToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertSocketTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
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
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case ArchitecturePackage.TTA:
			return createTTA();
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
		case ArchitecturePackage.PORT:
			return createPort();
		case ArchitecturePackage.SOCKET:
			return createSocket();
		case ArchitecturePackage.OPERATION:
			return createOperation();
		case ArchitecturePackage.ADDRESS_SPACE:
			return createAddressSpace();
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
		case ArchitecturePackage.PORT_TO_INDEX_MAP_ENTRY:
			return (EObject) createPortToIndexMapEntry();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AddressSpace createAddressSpace() {
		AddressSpaceImpl addressSpace = new AddressSpaceImpl();
		return addressSpace;
	}

	@Override
	public AddressSpace createAddressSpace(String name, int width,
			int minAddress, int maxAddress) {
		AddressSpaceImpl addressSpace = new AddressSpaceImpl();
		addressSpace.setName(name);
		addressSpace.setWidth(width);
		addressSpace.setMinAddress(minAddress);
		addressSpace.setMaxAddress(maxAddress);
		return addressSpace;
	}

	@Override
	public FunctionUnit createAluUnit(TTA tta) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		functionUnit.setName("ALU");
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket("ALU_i1", segments);
		Socket i2 = createInputSocket("ALU_i2", segments);
		Socket o1 = createOutputSocket("ALU_o1", segments);
		// Port
		Port in1t = createPort("in1t", 32, true, true);
		Port in2 = createPort("in2", 32, false, false);
		Port out1 = createPort("out1", 32, false, false);
		in1t.connect(i1);
		in2.connect(i2);
		out1.connect(o1);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(in2);
		functionUnit.getPorts().add(out1);
		// Implementation
		Implementation aluImpl = createImplementation("asic_130nm_1.5V.hdb",
				375);
		functionUnit.setImplementation(aluImpl);
		tta.getHardwareDatabase().add(aluImpl);

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
	 * 
	 * @generated
	 */
	public Bridge createBridge() {
		BridgeImpl bridge = new BridgeImpl();
		return bridge;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * 
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
	 * 
	 * @generated
	 */
	public ExprFalse createExprFalse() {
		ExprFalseImpl exprFalse = new ExprFalseImpl();
		return exprFalse;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExprTrue createExprTrue() {
		ExprTrueImpl exprTrue = new ExprTrueImpl();
		return exprTrue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * 
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
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
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
	 * 
	 * @generated
	 */
	public FunctionUnit createFunctionUnit() {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		return functionUnit;
	}

	@Override
	public FunctionUnit createFunctionUnit(TTA tta, String name,
			Implementation implementation) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket i2 = createInputSocket(name + "_i2", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		// Port
		Port in1t = createPort("in1t", 32, true, true);
		Port in2 = createPort("in2", 32, false, false);
		Port out1 = createPort("out1", 32, false, false);
		in1t.connect(i1);
		in2.connect(i2);
		out1.connect(o1);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(in2);
		functionUnit.getPorts().add(out1);
		// Implementation
		functionUnit.setImplementation(implementation);
		return functionUnit;
	}

	@Override
	public FunctionUnit createFunctionUnit(TTA tta, String name,
			String[] operations1, String[] operations2,
			Implementation implementation) {
		FunctionUnit functionUnit = createFunctionUnit(tta, name,
				implementation);
		EList<Port> ports = functionUnit.getPorts();
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
	 * 
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
	public GlobalControlUnit createGlobalControlUnitDefault(TTA tta) {
		GlobalControlUnit gcu = createGlobalControlUnit(3, 1);
		gcu.setAddressSpace(tta.getProgram());

		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket gcu_i1 = createInputSocket("gcu_i1", segments);
		Socket gcu_i2 = createInputSocket("gcu_i2", segments);
		Socket gcu_o1 = createOutputSocket("gcu_o1", segments);

		// Ports
		Port pc = createPort("pc", 13, true, true);
		pc.connect(gcu_i1);
		Port ra = createPort("ra", 13, false, false);
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
	 * 
	 * @generated
	 */
	public Implementation createImplementation() {
		ImplementationImpl implementation = new ImplementationImpl();
		return implementation;
	}

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
	public FunctionUnit createLSU(TTA tta, Implementation implementation) {
		FunctionUnit LSU = createFunctionUnit(tta, "LSU", implementation);
		// Operations
		EList<Port> ports = LSU.getPorts();
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
		LSU.setAddressSpace(tta.getData());
		return LSU;
	}

	@Override
	public FunctionUnit createMultiplier(TTA tta, Implementation implementation) {
		FunctionUnit multiplier = createFunctionUnit(tta, "Mul", implementation);
		multiplier.getOperations().add(
				createOperationMul("mul", multiplier.getPorts().get(0),
						multiplier.getPorts().get(1), multiplier.getPorts()
								.get(2)));
		multiplier.setAddressSpace(tta.getData());
		return multiplier;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * 
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
	public Operation createOperationCtrl(String name, Port port) {
		Operation operation = new OperationImpl();
		operation.setName(name);
		operation.setControl(true);
		operation.getPipeline().add(createReads(port, 0, 1));
		operation.getPortToIndexMap().put(port, 1);
		return operation;
	}

	private Operation createOperationDefault(String name, Port port1,
			int startCycle1, int cycle1, boolean isReads1, Port port2,
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

	private Operation createOperationDefault(String name, Port in1, Port out1) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createWrites(out1, 0, 1));
		return operation;
	}

	private Operation createOperationDefault(String name, Port in1, Port in2,
			Port out1) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createReads(in2, 0, 1));
		operation.getPipeline().add(createWrites(out1, 0, 1));
		return operation;
	}

	private Operation createOperationIn(String name, Port in, Port out) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createResource("res0", 0, 3));
		operation.getPipeline().add(createReads(in, 0, 1));
		operation.getPipeline().add(createWrites(out, 1, 1));
		return operation;
	}

	private Operation createOperationInPeek(String name, Port in, Port out) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createResource("res0", 0, 3));
		operation.getPipeline().add(createReads(in, 0, 1));
		operation.getPipeline().add(createWrites(out, 1, 1));
		return operation;
	}

	private Operation createOperationInStatus(String name, Port in, Port out) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createResource("res0", 0, 3));
		operation.getPipeline().add(createReads(in, 0, 1));
		operation.getPipeline().add(createWrites(out, 1, 1));
		return operation;
	}

	private Operation createOperationLoad(String name, Port in, Port out) {
		return createOperationDefault(name, in, 0, 1, true, out, 2, 1, false);
	}

	private Operation createOperationMul(String name, Port in1, Port in2,
			Port out) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in1, 0, 1));
		operation.getPipeline().add(createReads(in2, 0, 1));
		operation.getPipeline().add(createWrites(out, 1, 1));
		return operation;
	}

	private Operation createOperationOut(String name, Port in, Port out) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createResource("res0", 0, 3));
		operation.getPipeline().add(createReads(in, 0, 1));
		return operation;
	}

	private Operation createOperationOutStatus(String name, Port in, Port out) {
		Operation operation = createOperation(name);
		operation.setControl(false);
		operation.getPipeline().add(createReads(in, 0, 1));
		operation.getPipeline().add(createWrites(out, 0, 1));
		return operation;
	}

	private Operation createOperationStore(String name, Port in1, Port in2) {
		return createOperationDefault(name, in1, 0, 1, true, in2, 0, 1, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	public Socket createOutputSocket(String name, EList<Segment> segments) {
		Socket socket = createSocket(name, segments);
		socket.setType(SocketType.OUTPUT);
		return socket;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	@Override
	public Port createPort(String name) {
		PortImpl port = new PortImpl();
		port.setName(name);
		return port;
	}

	@Override
	public Port createPort(String name, int width, boolean isOpcodeSelector,
			boolean isTrigger) {
		Port port = createPort(name);
		port.setWidth(width);
		port.setOpcodeSelector(isOpcodeSelector);
		port.setTrigger(isTrigger);
		return port;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<Port, Integer> createPortToIndexMapEntry() {
		PortToIndexMapEntryImpl portToIndexMapEntry = new PortToIndexMapEntryImpl();
		return portToIndexMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Reads createReads() {
		ReadsImpl reads = new ReadsImpl();
		return reads;
	}

	@Override
	public Reads createReads(Port port, int startCycle, int cycle) {
		ReadsImpl reads = new ReadsImpl();
		reads.setPort(port);
		reads.setStartCycle(startCycle);
		reads.setCycles(cycle);
		return reads;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RegisterFile createRegisterFile() {
		RegisterFileImpl registerFile = new RegisterFileImpl();
		return registerFile;
	}

	@Override
	public RegisterFile createRegisterFile(String name, int size, int width,
			int maxReads, int maxWrites, Implementation implementation) {
		RegisterFileImpl registerFile = new RegisterFileImpl();
		registerFile.setName(name);
		registerFile.setSize(size);
		registerFile.setWidth(width);
		registerFile.setMaxReads(maxReads);
		registerFile.setMaxWrites(maxWrites);
		registerFile.setImplementation(implementation);
		return registerFile;
	}

	@Override
	public RegisterFile createRegisterFileDefault(TTA tta, String name,
			int size, int width, Implementation implementation) {
		RegisterFile registerFile = createRegisterFile(name, size, width, 1, 1,
				implementation);

		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);

		// Ports
		Port wr = createPort("wr");
		wr.connect(o1);
		Port rd = createPort("rd");
		rd.connect(i1);
		registerFile.getPorts().add(wr);
		registerFile.getPorts().add(rd);

		return registerFile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * 
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
	 * 
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
	 * 
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
		EcoreHelper.getContainerOfType(segments.get(0), TTA.class).getSockets()
				.add(socket);
		return socket;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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

	@Override
	public FunctionUnit createStreamInput(TTA tta, int index) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		String name = "STREAM_IN_" + index;
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		// Port
		Port in1t = createPort("in1t", 32, true, true);
		Port out1 = createPort("out1", 32, false, false);
		in1t.connect(i1);
		out1.connect(o1);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(out1);
		// Operations
		functionUnit.getOperations().add(
				createOperationIn("stream_in_v" + index, in1t, out1));
		functionUnit.getOperations().add(
				createOperationInPeek("stream_in_peek_v" + index, in1t, out1));
		functionUnit.getOperations().add(
				createOperationInStatus("stream_in_status_v" + index, in1t,
						out1));
		// Implementation
		Implementation streamImpl = createImplementation("many_streams.hdb",
				8 + index);
		functionUnit.setImplementation(streamImpl);
		tta.getHardwareDatabase().add(streamImpl);
		return functionUnit;
	}

	@Override
	public FunctionUnit createStreamOutput(TTA tta, int index) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		String name = "STREAM_OUT_" + index;
		functionUnit.setName(name);
		// Sockets
		EList<Segment> segments = getAllSegments(tta.getBuses());
		Socket i1 = createInputSocket(name + "_i1", segments);
		Socket o1 = createOutputSocket(name + "_o1", segments);
		// Port
		Port in1t = createPort("in1t", 32, true, true);
		Port out1 = createPort("out1", 32, false, false);
		in1t.connect(i1);
		out1.connect(o1);
		functionUnit.getPorts().add(in1t);
		functionUnit.getPorts().add(out1);
		// Operations
		functionUnit.getOperations().add(
				createOperationOut("stream_out_v" + index, in1t, out1));
		functionUnit.getOperations().add(
				createOperationOutStatus("stream_out_status_v" + index, in1t,
						out1));
		// Implementation
		Implementation streamImpl = createImplementation("many_streams.hdb",
				index);
		functionUnit.setImplementation(streamImpl);
		tta.getHardwareDatabase().add(streamImpl);
		return functionUnit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * 
	 * @generated
	 */
	public TermUnit createTermUnit() {
		TermUnitImpl termUnit = new TermUnitImpl();
		return termUnit;
	}

	@Override
	public TermUnit createTermUnit(FunctionUnit unit, Port port) {
		TermUnitImpl termUnit = new TermUnitImpl();
		termUnit.setFunctionUnit(unit);
		termUnit.setPort(port);
		return termUnit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TTA createTTA() {
		TTAImpl tta = new TTAImpl();
		return tta;
	}

	@Override
	public TTA createTTA(String name) {
		TTAImpl tta = new TTAImpl();
		tta.setName(name);
		return tta;
	}

	@Override
	public TTA createTTADefault(String name) {
		TTA tta = createTTA(name);
		// Address spaces
		tta.setData(createAddressSpace("data", 8, 0, 131071));
		tta.setProgram(createAddressSpace("instructions", 8, 0, 8191));
		// Buses
		Bus bus0 = createBusDefault(0, 32);
		Bus bus1 = createBusDefault(1, 32);
		tta.getBuses().add(bus0);
		tta.getBuses().add(bus1);
		// Global Control Unit
		tta.setGcu(createGlobalControlUnitDefault(tta));
		// Register files
		Implementation registerImpl = createImplementation(
				"asic_130nm_1.5V.hdb", 96);
		RegisterFile bool = createRegisterFileDefault(tta, "BOOL", 2, 1,
				registerImpl);
		RegisterFile rf1 = createRegisterFileDefault(tta, "RF_1", 12, 32,
				registerImpl);
		RegisterFile rf2 = createRegisterFileDefault(tta, "RF_2", 12, 32,
				registerImpl);
		tta.getHardwareDatabase().add(registerImpl);
		tta.getRegisterFiles().add(bool);
		tta.getRegisterFiles().add(rf1);
		tta.getRegisterFiles().add(rf2);
		// Guards
		bus0.getGuards().addAll(createGuardsDefault(bool));
		bus1.getGuards().addAll(createGuardsDefault(bool));
		// Functional units
		EList<FunctionUnit> units = tta.getFunctionUnits();
		// * ALU
		units.add(createAluUnit(tta));
		// * LSU
		Implementation lsuImpl = createImplementation("stratixII.hdb", 2);
		units.add(createLSU(tta, lsuImpl));
		tta.getHardwareDatabase().add(lsuImpl);
		// * Mul
		Implementation mulImpl = createImplementation("asic_130nm_1.5V.hdb", 88);
		units.add(createMultiplier(tta, mulImpl));
		tta.getHardwareDatabase().add(mulImpl);
		// * And-ior-xor
		Implementation logicImpl = createImplementation("asic_130nm_1.5V.hdb",
				22);
		String[] aixOperations2 = { "and", "ior", "xor" };
		units.add(createFunctionUnit(tta, "And_ior_xor", null, aixOperations2,
				logicImpl));
		tta.getHardwareDatabase().add(logicImpl);
		return tta;
	}

	@Override
	public TTA createTTASpecialized(String name, Instance instance) {
		// Create default TTA processor
		TTA tta = createTTADefault(name);
		if (instance.isActor()) {
			// Add needed stream units
			for (int i = 0; i < instance.getActor().getInputs().size(); i++) {
				tta.getFunctionUnits().add(createStreamInput(tta, i + 1));
			}
			for (int i = 0; i < instance.getActor().getOutputs().size(); i++) {
				tta.getFunctionUnits().add(createStreamOutput(tta, i + 1));
			}
			// Set ram size = memory estimation / word size + error margin
			int ramSize = ArchitectureMemoryStats
					.computeNeededMemorySize(instance.getActor());
			if (ramSize == 0) {
				tta.getData().setMaxAddress(256);
			} else {
				tta.getData().setMaxAddress(quantizeUp(ramSize / 8 + 256));
			}

		} else if (instance.isBroadcast()) {
			// Add needed stream units
			tta.getFunctionUnits().add(createStreamInput(tta, 1));
			for (int i = 0; i < instance.getBroadcast().getNumOutputs(); i++) {
				tta.getFunctionUnits().add(createStreamOutput(tta, i + 1));
			}
			// Set ram size = memory estimation / word size + error margin
			tta.getData().setMaxAddress(64);
		}
		return tta;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Writes createWrites() {
		WritesImpl writes = new WritesImpl();
		return writes;
	}

	@Override
	public Writes createWrites(Port port, int startCycle, int cycle) {
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
	 * 
	 * @generated
	 */
	public ArchitecturePackage getArchitecturePackage() {
		return (ArchitecturePackage) getEPackage();
	}

	/**
	 * Round up to next power of 2 for example 30000 -> 32768
	 * 
	 * @param value
	 *            the value to round up
	 * @return the next power of 2 after the value
	 */
	private int quantizeUp(int value) {
		double tmp = Math.log(value) / Math.log(2.0);
		return (int) (Math.pow(2, (Math.floor(tmp) + 1.0)) - 1.0);
	}

} // ArchitectureFactoryImpl
