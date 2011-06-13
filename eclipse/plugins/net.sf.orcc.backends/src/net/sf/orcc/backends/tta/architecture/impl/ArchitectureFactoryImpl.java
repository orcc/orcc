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
import net.sf.orcc.backends.tta.architecture.ExprBinary;
import net.sf.orcc.backends.tta.architecture.ExprFalse;
import net.sf.orcc.backends.tta.architecture.ExprTrue;
import net.sf.orcc.backends.tta.architecture.ExprUnary;
import net.sf.orcc.backends.tta.architecture.Extension;
import net.sf.orcc.backends.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.tta.architecture.GlobalControlUnit;
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
import net.sf.orcc.backends.tta.architecture.TermBool;
import net.sf.orcc.backends.tta.architecture.TermUnit;
import net.sf.orcc.backends.tta.architecture.Writes;

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
	public OpUnary createOpUnaryFromString(EDataType eDataType,
			String initialValue) {
		OpUnary result = OpUnary.get(initialValue);
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
	public String convertOpUnaryToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
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
	public String convertOpBinaryToString(EDataType eDataType,
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
		case ArchitecturePackage.PORT_TO_INDEX_MAP_ENTRY:
			return (EObject) createportToIndexMapEntry();
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

	public AddressSpace createAddressSpace(String name, int minAddress,
			int maxAddress) {
		AddressSpaceImpl addressSpace = new AddressSpaceImpl();
		addressSpace.setName(name);
		addressSpace.setMinAddress(minAddress);
		addressSpace.setMaxAddress(maxAddress);
		return addressSpace;
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

	public Bus createBus(int index, int width) {
		BusImpl bus = new BusImpl();
		bus.setName("Bus" + index);
		bus.setWidth(width);
		return bus;
	}

	public Bus createSimpleBus(int index, int width) {
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

	public FunctionUnit createFunctionUnit(String name,
			EList<Operation> operations, EList<Port> ports) {
		FunctionUnitImpl functionUnit = new FunctionUnitImpl();
		functionUnit.setName(name);
		functionUnit.getOperations().addAll(operations);
		functionUnit.getPorts().addAll(ports);
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

	public GlobalControlUnit createGlobalControlUnit(int delaySlots,
			int guardLatency) {
		GlobalControlUnitImpl globalControlUnit = new GlobalControlUnitImpl();
		globalControlUnit.setDelaySlots(delaySlots);
		globalControlUnit.setGuardLatency(guardLatency);
		return globalControlUnit;
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

	public Operation createOperation(String name) {
		OperationImpl operation = new OperationImpl();
		operation.setName(name);
		return operation;
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

	public Port createPort(String name) {
		PortImpl port = new PortImpl();
		port.setName(name);
		return port;
	}

	public Port createPort(String name, int width) {
		Port port = createPort(name);
		port.setWidth(width);
		return port;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<Port, Integer> createportToIndexMapEntry() {
		portToIndexMapEntryImpl portToIndexMapEntry = new portToIndexMapEntryImpl();
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

	public RegisterFile createRegisterFile(String name, int size, int width,
			int maxReads, int maxWrites) {
		RegisterFileImpl registerFile = new RegisterFileImpl();
		registerFile.setName(name);
		registerFile.setSize(size);
		registerFile.setWidth(width);
		registerFile.setMaxReads(maxReads);
		registerFile.setMaxWrites(maxWrites);
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

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Segment createSegment() {
		SegmentImpl segment = new SegmentImpl();
		return segment;
	}

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
	public ExprUnary createExprUnary() {
		ExprUnaryImpl exprUnary = new ExprUnaryImpl();
		return exprUnary;
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
	public ExprFalse createExprFalse() {
		ExprFalseImpl exprFalse = new ExprFalseImpl();
		return exprFalse;
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

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TermUnit createTermUnit() {
		TermUnitImpl termUnit = new TermUnitImpl();
		return termUnit;
	}

	public GlobalControlUnit createSimpleGlobalControlUnit(
			AddressSpace addressSpace) {
		GlobalControlUnit gcu = createGlobalControlUnit(3, 1);
		gcu.setAddressSpace(addressSpace);

		// Ports
		Port ra = createPort("ra", 13);
		Port pc = createPort("pc", 13);
		gcu.setReturnAddress(ra);
		gcu.getPorts().add(ra);
		gcu.getPorts().add(pc);

		// Control operations
		gcu.getOperations().add(createSimpleCtrlOperation("jump", pc));
		gcu.getOperations().add(createSimpleCtrlOperation("call", pc));

		return gcu;
	}

	public RegisterFile createSimpleRegisterFile(String name, int size,
			int width) {
		RegisterFile registerFile = createRegisterFile(name, size, width, 1, 1);
		registerFile.getPorts().add(createPort("wr"));
		registerFile.getPorts().add(createPort("rd"));
		return registerFile;
	}

	public TTA createSimpleTTA(String name) {
		TTA tta = createTTA(name);
		// Address spaces
		tta.setData(createAddressSpace());
		tta.setProgram(createAddressSpace());
		// Global Control Unit
		tta.setGcu(createSimpleGlobalControlUnit(tta.getProgram()));
		// Buses
		tta.getBuses().add(createSimpleBus(0, 32));
		tta.getBuses().add(createSimpleBus(1, 32));
		// Register files
		tta.getRegisterFiles().add(createSimpleRegisterFile("RF_1", 32, 12));
		tta.getRegisterFiles().add(createSimpleRegisterFile("RF_2", 32, 12));
		tta.getRegisterFiles().add(createSimpleRegisterFile("BOOL", 1, 2));
		// Fonctional units
		return tta;
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

	public Socket createSocket(String name, EList<Segment> segments) {
		SocketImpl socket = new SocketImpl();
		socket.setName(name);
		socket.getConnectedSegments().addAll(segments);
		return socket;
	}

	public Socket createOutputSocket(String name, EList<Segment> segments) {
		Socket socket = createSocket(name, segments);
		socket.setType(SocketType.OUTPUT);
		return socket;
	}

	public Socket createInputSocket(String name, EList<Segment> segments) {
		Socket socket = createSocket(name, segments);
		socket.setType(SocketType.INPUT);
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

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TTA createTTA() {
		TTAImpl tta = new TTAImpl();
		return tta;
	}

	public TTA createTTA(String name) {
		TTAImpl tta = new TTAImpl();
		tta.setName(name);
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

	public Writes createWrites(Port port, int startCycle, int cycle) {
		WritesImpl writes = new WritesImpl();
		writes.setPort(port);
		writes.setStartCycle(startCycle);
		writes.setCycles(cycle);
		return writes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ArchitecturePackage getArchitecturePackage() {
		return (ArchitecturePackage) getEPackage();
	}

	public Operation createSimpleCtrlOperation(String name, Port port) {
		Operation operation = new OperationImpl();
		operation.setName(name);
		operation.setControl(true);
		operation.getPipeline().add(createReads(port, 0, 1));
		operation.getPortToIndexMap().put(port, 1);
		return operation;
	}

} // ArchitectureFactoryImpl
