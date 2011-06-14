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
public interface ArchitectureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArchitectureFactory eINSTANCE = net.sf.orcc.backends.tta.architecture.impl.ArchitectureFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Address Space</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Address Space</em>'.
	 * @generated
	 */
	AddressSpace createAddressSpace();
	
	AddressSpace createAddressSpace(String name, int minAddress,
			int maxAddress);
	
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
	
	Bus createBus(int index, int width);
	
	/**
	 * Returns a new object of class '<em>Expr Binary</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr Binary</em>'.
	 * @generated
	 */
	ExprBinary createExprBinary();
	
	ExprBinary createExprBinary(OpBinary op, ExprUnary e1, ExprUnary e2);
	
	/**
	 * Returns a new object of class '<em>Expr False</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr False</em>'.
	 * @generated
	 */
	ExprFalse createExprFalse();
	
	/**
	 * Returns a new object of class '<em>Expr True</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr True</em>'.
	 * @generated
	 */
	ExprTrue createExprTrue();
	
	/**
	 * Returns a new object of class '<em>Expr Unary</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expr Unary</em>'.
	 * @generated
	 */
	ExprUnary createExprUnary();
	
	ExprUnary createExprUnary(boolean isInverted, Term term);
	
	/**
	 * Returns a new object of class '<em>Function Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Unit</em>'.
	 * @generated
	 */
	FunctionUnit createFunctionUnit();
	
	FunctionUnit createFunctionUnit(String name,
			EList<Operation> operations, EList<Port> ports);
	
	/**
	 * Returns a new object of class '<em>Global Control Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Control Unit</em>'.
	 * @generated
	 */
	GlobalControlUnit createGlobalControlUnit();
	
	GlobalControlUnit createGlobalControlUnit(int delaySlots,
			int guardLatency);
	
	Socket createInputSocket(String name, EList<Segment> segments);
	
	FunctionUnit createLSU(TTA tta);
	
	/**
	 * Returns a new object of class '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation</em>'.
	 * @generated
	 */
	Operation createOperation();
	
	Operation createOperation(String name);
	
	Operation createOperationLoad(String name, Port in, Port out);
	
	Operation createOperationStore(String name, Port in1, Port in2);
	
	Socket createOutputSocket(String name, EList<Segment> segments);
	
	/**
	 * Returns a new object of class '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Port</em>'.
	 * @generated
	 */
	Port createPort();
	
	Port createPort(String name);
	
	Port createPort(String name, int width, boolean opcodeSelector,
			boolean isTrigger);
	
	/**
	 * Returns a new object of class '<em>Reads</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reads</em>'.
	 * @generated
	 */
	Reads createReads();
	
	Reads createReads(Port port, int startCycle, int cycle);
	
	/**
	 * Returns a new object of class '<em>Register File</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Register File</em>'.
	 * @generated
	 */
	RegisterFile createRegisterFile();
	
	RegisterFile createRegisterFile(String name, int size, int width,
			int maxReads, int maxWrites);
	
	/**
	 * Returns a new object of class '<em>Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource</em>'.
	 * @generated
	 */
	Resource createResource();
	
	/**
	 * Returns a new object of class '<em>Segment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Segment</em>'.
	 * @generated
	 */
	Segment createSegment();
	
	Segment createSegment(String name);
	
	/**
	 * Returns a new object of class '<em>Short Immediate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Short Immediate</em>'.
	 * @generated
	 */
	ShortImmediate createShortImmediate();
	
	ShortImmediate createShortImmediate(int width, Extension extension);

	Bus createSimpleBus(int index, int width);

	Operation createSimpleCtrlOperation(String name, Port port);

	FunctionUnit createSimpleFonctionUnit(TTA tta, String name);
	
	FunctionUnit createSimpleFonctionUnit(TTA tta, String name,
			String[] operations1, String[] operations2);

	GlobalControlUnit createSimpleGlobalControlUnit(TTA tta);

	EList<Guard> createSimpleGuards(RegisterFile register);
	
	Operation createSimpleOperation(String name, Port port1,
			int startCycle1, int cycle1, boolean isReads1, Port port2,
			int startCycle2, int cycle2, boolean isReads2);

	Operation createSimpleOperation(String name, Port in1, Port out1);

	Operation createSimpleOperation(String name, Port in1, Port in2,
			Port out1);

	RegisterFile createSimpleRegisterFile(TTA tta, String name,
			int size, int width);

	TTA createSimpleTTA(String name);

	/**
	 * Returns a new object of class '<em>Socket</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Socket</em>'.
	 * @generated
	 */
	Socket createSocket();

	Socket createSocket(String name, EList<Segment> segments);

	/**
	 * Returns a new object of class '<em>Term Bool</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Term Bool</em>'.
	 * @generated
	 */
	TermBool createTermBool();

	TermBool createTermBool(RegisterFile register, int index);

	/**
	 * Returns a new object of class '<em>Term Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Term Unit</em>'.
	 * @generated
	 */
	TermUnit createTermUnit();

	TermUnit createTermUnit(FunctionUnit unit, Port port);

	/**
	 * Returns a new object of class '<em>TTA</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TTA</em>'.
	 * @generated
	 */
	TTA createTTA();

	TTA createTTA(String name);

	/**
	 * Returns a new object of class '<em>Writes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Writes</em>'.
	 * @generated
	 */
	Writes createWrites();

	Writes createWrites(Port port, int startCycle, int cycle);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ArchitecturePackage getArchitecturePackage();

} //ArchitectureFactory
