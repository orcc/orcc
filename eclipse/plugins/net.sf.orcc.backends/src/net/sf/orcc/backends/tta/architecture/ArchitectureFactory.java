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
package net.sf.orcc.backends.tta.architecture;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage
 * @generated
 */
/**
 * @author hyviquel
 * 
 */
public interface ArchitectureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	ArchitectureFactory eINSTANCE = net.sf.orcc.backends.tta.architecture.impl.ArchitectureFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Design</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Design</em>'.
	 * @generated
	 */
	Design createDesign();

	/**
	 * Returns a new object of class '<em>Fifo</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fifo</em>'.
	 * @generated
	 */
	Fifo createFifo();

	/**
	 * Returns a new object of class '<em>Signal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Signal</em>'.
	 * @generated
	 */
	Signal createSignal();

	/**
	 * Returns a new object of class '<em>Component</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Component</em>'.
	 * @generated
	 */
	Component createComponent();

	/**
	 * Returns a new object of class '<em>External Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Port</em>'.
	 * @generated
	 */
	ExternalPort createExternalPort();

	/**
	 * Returns a new object of class '<em>Processor</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Processor</em>'.
	 * @generated
	 */
	Processor createProcessor();

	/**
	 * Returns a new object of class '<em>Address Space</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Address Space</em>'.
	 * @generated
	 */
	AddressSpace createAddressSpace();

	/**
	 * Returns a new address space with the given parameters
	 * 
	 * @param name
	 *            the name of the AddressSpace
	 * @param the
	 *            width of the AdressSpace
	 * @param minAddress
	 *            the minimum address
	 * @param maxAddress
	 *            the maximum address
	 * @return an AddressSpace with the given parameters
	 */
	AddressSpace createAddressSpace(String name, int width, int minAddress,
			int maxAddress);

	/**
	 * Returns a new ALU unit
	 * 
	 * @param tta
	 *            the containing TTA
	 * @param name
	 *            the name of the ALU
	 * @return a new ALU unit
	 */
	FunctionUnit createAluUnit(Processor tta, String name);

	/**
	 * Returns a new object of class '<em>Bridge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bridge</em>'.
	 * @generated
	 */
	Bridge createBridge();

	/**
	 * Returns a new object of class '<em>Bus</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bus</em>'.
	 * @generated
	 */
	Bus createBus();

	/**
	 * Returns a new bus with the given parameters
	 * 
	 * @param index
	 *            the index of the bus
	 * @param width
	 *            the width of the bus
	 * @return a Bus with the given parameters
	 */
	Bus createBus(int index, int width);

	/**
	 * Returns a new simple bus containing only one segment, using zero
	 * extension mode and initialized with the given arguments
	 * 
	 * @param index
	 *            the index of the bus
	 * @param width
	 *            the width of the bus
	 * @return a Bus containing only one segment, using zero extension mode and
	 *         initialized with the given arguments
	 */
	Bus createBusDefault(int index, int width);

	/**
	 * Returns a new object of class '<em>Expr Binary</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr Binary</em>'.
	 * @generated
	 */
	ExprBinary createExprBinary();

	/**
	 * Returns a new binary expression with the given parameters
	 * 
	 * @param op
	 *            the operator of the binary expression
	 * @param e1
	 *            the first unary expression
	 * @param e2
	 *            the second unary expression
	 * @return an ExprBinary with the given parameters
	 */
	ExprBinary createExprBinary(OpBinary op, ExprUnary e1, ExprUnary e2);

	/**
	 * Returns a new object of class '<em>Expr False</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr False</em>'.
	 * @generated
	 */
	ExprFalse createExprFalse();

	/**
	 * Returns a new object of class '<em>Expr True</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr True</em>'.
	 * @generated
	 */
	ExprTrue createExprTrue();

	/**
	 * Returns a new object of class '<em>Expr Unary</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr Unary</em>'.
	 * @generated
	 */
	ExprUnary createExprUnary();

	/**
	 * Returns a new unary expression with the given parameters
	 * 
	 * @param isInverted
	 *            whether the unary expression is an inversion
	 * @param e
	 *            the contained term
	 * @return an ExprUnary with the given parameters
	 */
	ExprUnary createExprUnary(boolean isInverted, Term term);

	/**
	 * Returns a new object of class '<em>Function Unit</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Function Unit</em>'.
	 * @generated
	 */
	FunctionUnit createFunctionUnit();

	/**
	 * Returns a new simple function unit with 2 input ports and 1 output ports
	 * without operation
	 * 
	 * @param tta
	 *            the containing TTA processor
	 * @param name
	 *            the name of the function unit
	 * @return a simple FunctionUnit
	 */
	FunctionUnit createFunctionUnit(Processor tta, String name,
			Implementation implementation);

	/**
	 * Returns a new simple function unit with 2 input ports and 1 output ports
	 * with operations
	 * 
	 * @param tta
	 *            the containing TTA processor
	 * @param name
	 *            the name of the function unit
	 * @param operations1
	 *            the names of 1-input/1-output operations
	 * @param operations2
	 *            the names of 2-input/1-output operations
	 * @return a simple FunctionUnit
	 */
	FunctionUnit createFunctionUnit(Processor tta, String name,
			String[] operations1, String[] operations2,
			Implementation implementation);

	/**
	 * Returns a new object of class '<em>Global Control Unit</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Global Control Unit</em>'.
	 * @generated
	 */
	GlobalControlUnit createGlobalControlUnit();

	/**
	 * Returns a new Global Control Unit with the given parameters
	 * 
	 * @param delaySlots
	 *            the delay slots
	 * @param guardLatency
	 *            the guard latency
	 * @return a GlobalControlUnit with the given parameters
	 */
	GlobalControlUnit createGlobalControlUnit(int delaySlots, int guardLatency);

	/**
	 * Returns a new global control unit corresponding to default TTA processor
	 * include in the TCE tools
	 * 
	 * @param tta
	 *            the containing TTA processor
	 * @return a simple GlobalControlUnit
	 */
	GlobalControlUnit createGlobalControlUnitDefault(Processor tta);

	/**
	 * Returns a new list of guards corresponding to default TTA processor
	 * include in the TCE tools
	 * 
	 * @param register
	 *            the referenced BOOL register
	 * @return a simple list of guards
	 */
	EList<Guard> createGuardsDefault(RegisterFile register);

	/**
	 * Returns a new object of class '<em>Implementation</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Implementation</em>'.
	 * @generated
	 */
	Implementation createImplementation();

	/**
	 * Return a new input socket with the given parameters
	 * 
	 * @param name
	 *            the name of the socket
	 * @param segments
	 *            the connected segments of the socket
	 * @return an Input Socket with the given parameters
	 */
	Socket createInputSocket(String name, EList<Segment> segments);

	/**
	 * Return a new Load/Store unit with the given parameters
	 * 
	 * @param tta
	 *            the containing TTA processor
	 * @return an LSU with the given parameters
	 */
	FunctionUnit createLSU(String name, Processor tta,
			Implementation implementation);

	/**
	 * Return a new multiplier unit with the given parameters
	 * 
	 * @param tta
	 *            the containing TTA processor
	 * @return a multiplier with the given parameters
	 */
	FunctionUnit createMultiplier(String name, Processor tta,
			Implementation implementation);

	/**
	 * Returns a new object of class '<em>Operation</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation</em>'.
	 * @generated
	 */
	Operation createOperation();

	/**
	 * Returns a new operation
	 * 
	 * @param name
	 *            the name of the load operation
	 * @return a Operation with the given parameters
	 */
	Operation createOperation(String name);

	/**
	 * Returns a new simple control operation containing only one read operation
	 * 
	 * @param name
	 *            the name of the operation
	 * @param port
	 *            the port read by this operation
	 * @return a control Operation
	 */
	Operation createOperationCtrl(String name, Port port);

	/**
	 * Return a new output socket with the given parameters
	 * 
	 * @param name
	 *            the name of the socket
	 * @param segments
	 *            the connected segments of the socket
	 * @return an Output Socket with the given parameters
	 */
	Socket createOutputSocket(String name, EList<Segment> segments);

	/**
	 * Returns a new object of class '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Port</em>'.
	 * @generated
	 */
	Port createPort();

	/**
	 * Return a new Port with the given name
	 * 
	 * @param name
	 *            the name of the port
	 * @return a named port
	 */
	Port createPort(String name);

	/**
	 * Return a new Port with the given parameters
	 * 
	 * @param name
	 *            the name of the port
	 * @param width
	 *            the width of the port
	 * @param isOpcodeSelector
	 *            whether the port selects the opcode
	 * @param isTrigger
	 *            whether the port is a trigger one
	 * @return a Port with the given parameters
	 */
	Port createPort(String name, int width, boolean isOpcodeSelector,
			boolean isTrigger);

	/**
	 * Returns a new object of class '<em>Reads</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reads</em>'.
	 * @generated
	 */
	Reads createReads();

	/**
	 * Return a new reads with the given parameter
	 * 
	 * @param port
	 *            the port to read
	 * @param startCycle
	 *            the cycle when the read start
	 * @param cycle
	 *            the duration of the read
	 * @return a Reads with the given parameters
	 */
	Reads createReads(Port port, int startCycle, int cycle);

	/**
	 * Returns a new object of class '<em>Register File</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Register File</em>'.
	 * @generated
	 */
	RegisterFile createRegisterFile();

	/**
	 * Returns a new register file with the given parameters
	 * 
	 * @param name
	 *            the name of the register file
	 * @param size
	 *            the size of the register file
	 * @param width
	 *            the width of the register file
	 * @param maxReads
	 *            the maximum number of reads from this register file by cycle
	 * @param maxWrites
	 *            the maximum number of writes to this register file by cycle
	 * @return a RegisterFile initialized with the given parameters
	 */
	RegisterFile createRegisterFile(String name, int size, int width,
			int maxReads, int maxWrites, Implementation implementation);

	/**
	 * Returns a new register file corresponding to default TTA processor
	 * include in the TCE tools
	 * 
	 * @param tta
	 *            the containing TTA processor
	 * @param name
	 *            the name of the register file
	 * @param size
	 *            the size of the register file
	 * @param width
	 *            the width of the register file
	 * @return a new simple RegisterFile
	 */
	RegisterFile createRegisterFileDefault(Processor tta, String name,
			int size, int width, Implementation implementation);

	/**
	 * Returns a new object of class '<em>Resource</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource</em>'.
	 * @generated
	 */
	Resource createResource();

	/**
	 * Return a new resource initialized with the given parameters
	 * 
	 * @param name
	 *            the name of the resource
	 * @param startCycle
	 *            the start of the resource use
	 * @param cycle
	 *            the duration of the resource use
	 * @return a Resource initialized with the given parameters
	 */
	Resource createResource(String name, int startCycle, int cycles);

	/**
	 * Returns a new object of class '<em>Segment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Segment</em>'.
	 * @generated
	 */
	Segment createSegment();

	/**
	 * Returns a new named segment
	 * 
	 * @param name
	 *            the name of the segment
	 * @return a named Segment
	 */
	Segment createSegment(String name);

	/**
	 * Returns a new object of class '<em>Short Immediate</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Short Immediate</em>'.
	 * @generated
	 */
	ShortImmediate createShortImmediate();

	/**
	 * Returns a new short immediate which defines how inline immediates are
	 * supported by the containing bus
	 * 
	 * @param width
	 *            the number of bits of inline immediates that are encoded in
	 *            the source field of the instruction slot
	 * @param extension
	 *            the extension mode applied to the inline immediate word when
	 *            it is less wide than the bus that transports it
	 * @return
	 */
	ShortImmediate createShortImmediate(int width, Extension extension);

	/**
	 * Returns a new object of class '<em>Socket</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Socket</em>'.
	 * @generated
	 */
	Socket createSocket();

	/**
	 * Returns a new name socket connected to segments
	 * 
	 * @param name
	 *            the name of the socket
	 * @param segments
	 *            the connected segments
	 * @return a Socket initialized with the given parameters
	 */
	Socket createSocket(String name, EList<Segment> segments);

	/**
	 * Return a new input Stream unit
	 * 
	 * @param tta
	 *            the containing tta processor
	 * @return a new input Stream unit
	 */
	FunctionUnit createStreamInput(Processor tta);

	/**
	 * Return a new output Stream unit
	 * 
	 * @param tta
	 *            the containing TTA processor
	 * @return a new output Stream unit
	 */
	FunctionUnit createStreamOutput(Processor tta);

	/**
	 * Returns a new object of class '<em>Term Bool</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Term Bool</em>'.
	 * @generated
	 */
	TermBool createTermBool();

	/**
	 * Returns a new TermBool which reference a register used to compute the
	 * conditional value of the guard expression
	 * 
	 * @param register
	 *            the register file
	 * @param index
	 *            the index of the referenced register
	 * @return a TermBool initialized with the given parameters
	 */
	TermBool createTermBool(RegisterFile register, int index);

	/**
	 * Returns a new object of class '<em>Term Unit</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Term Unit</em>'.
	 * @generated
	 */
	TermUnit createTermUnit();

	/**
	 * Returns a new TermUnit which referenced the port used to compute the
	 * conditional value of the guard expression
	 * 
	 * @param unit
	 *            the functional unit which contained the port
	 * @param port
	 *            the referenced port
	 * @return A TermUnit initialized with the given parameters
	 */
	TermUnit createTermUnit(FunctionUnit unit, Port port);

	/**
	 * Returns a simple TTA processor corresponding to default TTA processor
	 * include in the TCE tools
	 * 
	 * @param name
	 *            the name of the TTA
	 * @param conf
	 *            the configuration of the processor
	 * @param ramSize
	 *            the size of the ram
	 * @return a simple TTA
	 */
	public Processor createProcessor(String name, ProcessorConfiguration conf,
			int ramSize);

	/**
	 * Returns a new object of class '<em>Writes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Writes</em>'.
	 * @generated
	 */
	Writes createWrites();

	/**
	 * Return a new writes with the given parameter
	 * 
	 * @param port
	 *            the port to write
	 * @param startCycle
	 *            the cycle when the write start
	 * @param cycle
	 *            the duration of the write
	 * @return a Writes with the given parameters
	 */
	Writes createWrites(Port port, int startCycle, int cycle);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ArchitecturePackage getArchitecturePackage();

} // ArchitectureFactory
