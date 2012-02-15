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

import net.sf.dftools.graph.GraphPackage;
import net.sf.dftools.util.UtilPackage;
import net.sf.orcc.backends.tta.architecture.AddressSpace;
import net.sf.orcc.backends.tta.architecture.ArchitectureFactory;
import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.Bridge;
import net.sf.orcc.backends.tta.architecture.Bus;
import net.sf.orcc.backends.tta.architecture.Design;
import net.sf.orcc.backends.tta.architecture.Element;
import net.sf.orcc.backends.tta.architecture.ExprBinary;
import net.sf.orcc.backends.tta.architecture.ExprFalse;
import net.sf.orcc.backends.tta.architecture.ExprTrue;
import net.sf.orcc.backends.tta.architecture.ExprUnary;
import net.sf.orcc.backends.tta.architecture.Extension;
import net.sf.orcc.backends.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.tta.architecture.Guard;
import net.sf.orcc.backends.tta.architecture.HwFifo;
import net.sf.orcc.backends.tta.architecture.Implementation;
import net.sf.orcc.backends.tta.architecture.OpBinary;
import net.sf.orcc.backends.tta.architecture.OpUnary;
import net.sf.orcc.backends.tta.architecture.Operation;
import net.sf.orcc.backends.tta.architecture.Port;
import net.sf.orcc.backends.tta.architecture.Processor;
import net.sf.orcc.backends.tta.architecture.Reads;
import net.sf.orcc.backends.tta.architecture.RegisterFile;
import net.sf.orcc.backends.tta.architecture.Resource;
import net.sf.orcc.backends.tta.architecture.Segment;
import net.sf.orcc.backends.tta.architecture.ShortImmediate;
import net.sf.orcc.backends.tta.architecture.Socket;
import net.sf.orcc.backends.tta.architecture.SocketType;
import net.sf.orcc.backends.tta.architecture.Term;
import net.sf.orcc.backends.tta.architecture.TermBool;
import net.sf.orcc.backends.tta.architecture.TermUnit;
import net.sf.orcc.backends.tta.architecture.Writes;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class ArchitecturePackageImpl extends EPackageImpl implements
		ArchitecturePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hwFifoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass busEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bridgeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass segmentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalControlUnitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionUnitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass registerFileEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass socketEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addressSpaceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass guardEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprUnaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprBinaryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprTrueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exprFalseEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass termEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass termBoolEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass termUnitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass implementationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass writesEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portToIndexMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass shortImmediateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum socketTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum extensionEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum opUnaryEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum opBinaryEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ArchitecturePackageImpl() {
		super(eNS_URI, ArchitectureFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link ArchitecturePackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ArchitecturePackage init() {
		if (isInited)
			return (ArchitecturePackage) EPackage.Registry.INSTANCE
					.getEPackage(ArchitecturePackage.eNS_URI);

		// Obtain or create and register package
		ArchitecturePackageImpl theArchitecturePackage = (ArchitecturePackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof ArchitecturePackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new ArchitecturePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		GraphPackage.eINSTANCE.eClass();
		UtilPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theArchitecturePackage.createPackageContents();

		// Initialize created meta-data
		theArchitecturePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theArchitecturePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ArchitecturePackage.eNS_URI,
				theArchitecturePackage);
		return theArchitecturePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesign() {
		return designEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHwFifo() {
		return hwFifoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessor() {
		return processorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_Gcu() {
		return (EReference) processorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_Buses() {
		return (EReference) processorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_Bridges() {
		return (EReference) processorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_Sockets() {
		return (EReference) processorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_FunctionUnits() {
		return (EReference) processorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_RegisterFiles() {
		return (EReference) processorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_Program() {
		return (EReference) processorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_Data() {
		return (EReference) processorEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessor_HardwareDatabase() {
		return (EReference) processorEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBus() {
		return busEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBus_Name() {
		return (EAttribute) busEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBus_Width() {
		return (EAttribute) busEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBus_Guards() {
		return (EReference) busEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBus_Segments() {
		return (EReference) busEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBus_ShortImmediate() {
		return (EReference) busEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBridge() {
		return bridgeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBridge_InputBus() {
		return (EReference) bridgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBridge_OutputBus() {
		return (EReference) bridgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSegment() {
		return segmentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSegment_Name() {
		return (EAttribute) segmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalControlUnit() {
		return globalControlUnitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalControlUnit_Ports() {
		return (EReference) globalControlUnitEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalControlUnit_ReturnAddress() {
		return (EReference) globalControlUnitEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalControlUnit_AddressSpace() {
		return (EReference) globalControlUnitEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGlobalControlUnit_Operations() {
		return (EReference) globalControlUnitEClass.getEStructuralFeatures()
				.get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalControlUnit_DelaySlots() {
		return (EAttribute) globalControlUnitEClass.getEStructuralFeatures()
				.get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalControlUnit_GuardLatency() {
		return (EAttribute) globalControlUnitEClass.getEStructuralFeatures()
				.get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFunctionUnit() {
		return functionUnitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFunctionUnit_Name() {
		return (EAttribute) functionUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionUnit_Operations() {
		return (EReference) functionUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionUnit_Ports() {
		return (EReference) functionUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionUnit_AddressSpace() {
		return (EReference) functionUnitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFunctionUnit_Implementation() {
		return (EReference) functionUnitEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRegisterFile() {
		return registerFileEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRegisterFile_Name() {
		return (EAttribute) registerFileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRegisterFile_Size() {
		return (EAttribute) registerFileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRegisterFile_Width() {
		return (EAttribute) registerFileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRegisterFile_MaxReads() {
		return (EAttribute) registerFileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRegisterFile_MaxWrites() {
		return (EAttribute) registerFileEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegisterFile_Ports() {
		return (EReference) registerFileEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegisterFile_Implementation() {
		return (EReference) registerFileEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPort() {
		return portEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPort_Name() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPort_InputSocket() {
		return (EReference) portEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPort_OutputSocket() {
		return (EReference) portEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPort_Width() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPort_Trigger() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPort_OpcodeSelector() {
		return (EAttribute) portEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSocket() {
		return socketEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSocket_Name() {
		return (EAttribute) socketEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSocket_ConnectedSegments() {
		return (EReference) socketEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSocket_Type() {
		return (EAttribute) socketEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperation() {
		return operationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperation_Name() {
		return (EAttribute) operationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_Pipeline() {
		return (EReference) operationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperation_Control() {
		return (EAttribute) operationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddressSpace() {
		return addressSpaceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddressSpace_Name() {
		return (EAttribute) addressSpaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddressSpace_MinAddress() {
		return (EAttribute) addressSpaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddressSpace_MaxAddress() {
		return (EAttribute) addressSpaceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddressSpace_Width() {
		return (EAttribute) addressSpaceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGuard() {
		return guardEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExprUnary() {
		return exprUnaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExprUnary_Operator() {
		return (EAttribute) exprUnaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExprUnary_Term() {
		return (EReference) exprUnaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExprBinary() {
		return exprBinaryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExprBinary_Operator() {
		return (EAttribute) exprBinaryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExprBinary_E1() {
		return (EReference) exprBinaryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExprBinary_E2() {
		return (EReference) exprBinaryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExprTrue() {
		return exprTrueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExprFalse() {
		return exprFalseEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTerm() {
		return termEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTermBool() {
		return termBoolEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermBool_Register() {
		return (EReference) termBoolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTermBool_Index() {
		return (EAttribute) termBoolEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTermUnit() {
		return termUnitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermUnit_FunctionUnit() {
		return (EReference) termUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermUnit_Port() {
		return (EReference) termUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImplementation() {
		return implementationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_HdbFile() {
		return (EAttribute) implementationEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImplementation_Id() {
		return (EAttribute) implementationEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPortToIndexMapEntry() {
		return portToIndexMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPortToIndexMapEntry_Key() {
		return (EReference) portToIndexMapEntryEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPortToIndexMapEntry_Value() {
		return (EAttribute) portToIndexMapEntryEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElement() {
		return elementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElement_StartCycle() {
		return (EAttribute) elementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElement_Cycles() {
		return (EAttribute) elementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReads() {
		return readsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReads_Port() {
		return (EReference) readsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWrites() {
		return writesEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWrites_Port() {
		return (EReference) writesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResource() {
		return resourceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResource_Name() {
		return (EAttribute) resourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getShortImmediate() {
		return shortImmediateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShortImmediate_Extension() {
		return (EAttribute) shortImmediateEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getShortImmediate_Width() {
		return (EAttribute) shortImmediateEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSocketType() {
		return socketTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getExtension() {
		return extensionEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOpUnary() {
		return opUnaryEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOpBinary() {
		return opBinaryEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureFactory getArchitectureFactory() {
		return (ArchitectureFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		designEClass = createEClass(DESIGN);

		hwFifoEClass = createEClass(HW_FIFO);

		processorEClass = createEClass(PROCESSOR);
		createEReference(processorEClass, PROCESSOR__GCU);
		createEReference(processorEClass, PROCESSOR__BUSES);
		createEReference(processorEClass, PROCESSOR__BRIDGES);
		createEReference(processorEClass, PROCESSOR__SOCKETS);
		createEReference(processorEClass, PROCESSOR__FUNCTION_UNITS);
		createEReference(processorEClass, PROCESSOR__REGISTER_FILES);
		createEReference(processorEClass, PROCESSOR__PROGRAM);
		createEReference(processorEClass, PROCESSOR__DATA);
		createEReference(processorEClass, PROCESSOR__HARDWARE_DATABASE);

		busEClass = createEClass(BUS);
		createEAttribute(busEClass, BUS__NAME);
		createEAttribute(busEClass, BUS__WIDTH);
		createEReference(busEClass, BUS__GUARDS);
		createEReference(busEClass, BUS__SEGMENTS);
		createEReference(busEClass, BUS__SHORT_IMMEDIATE);

		bridgeEClass = createEClass(BRIDGE);
		createEReference(bridgeEClass, BRIDGE__INPUT_BUS);
		createEReference(bridgeEClass, BRIDGE__OUTPUT_BUS);

		segmentEClass = createEClass(SEGMENT);
		createEAttribute(segmentEClass, SEGMENT__NAME);

		globalControlUnitEClass = createEClass(GLOBAL_CONTROL_UNIT);
		createEReference(globalControlUnitEClass, GLOBAL_CONTROL_UNIT__PORTS);
		createEReference(globalControlUnitEClass,
				GLOBAL_CONTROL_UNIT__RETURN_ADDRESS);
		createEReference(globalControlUnitEClass,
				GLOBAL_CONTROL_UNIT__ADDRESS_SPACE);
		createEReference(globalControlUnitEClass,
				GLOBAL_CONTROL_UNIT__OPERATIONS);
		createEAttribute(globalControlUnitEClass,
				GLOBAL_CONTROL_UNIT__DELAY_SLOTS);
		createEAttribute(globalControlUnitEClass,
				GLOBAL_CONTROL_UNIT__GUARD_LATENCY);

		functionUnitEClass = createEClass(FUNCTION_UNIT);
		createEAttribute(functionUnitEClass, FUNCTION_UNIT__NAME);
		createEReference(functionUnitEClass, FUNCTION_UNIT__OPERATIONS);
		createEReference(functionUnitEClass, FUNCTION_UNIT__PORTS);
		createEReference(functionUnitEClass, FUNCTION_UNIT__ADDRESS_SPACE);
		createEReference(functionUnitEClass, FUNCTION_UNIT__IMPLEMENTATION);

		registerFileEClass = createEClass(REGISTER_FILE);
		createEAttribute(registerFileEClass, REGISTER_FILE__NAME);
		createEAttribute(registerFileEClass, REGISTER_FILE__SIZE);
		createEAttribute(registerFileEClass, REGISTER_FILE__WIDTH);
		createEAttribute(registerFileEClass, REGISTER_FILE__MAX_READS);
		createEAttribute(registerFileEClass, REGISTER_FILE__MAX_WRITES);
		createEReference(registerFileEClass, REGISTER_FILE__PORTS);
		createEReference(registerFileEClass, REGISTER_FILE__IMPLEMENTATION);

		portEClass = createEClass(PORT);
		createEAttribute(portEClass, PORT__NAME);
		createEReference(portEClass, PORT__INPUT_SOCKET);
		createEReference(portEClass, PORT__OUTPUT_SOCKET);
		createEAttribute(portEClass, PORT__WIDTH);
		createEAttribute(portEClass, PORT__TRIGGER);
		createEAttribute(portEClass, PORT__OPCODE_SELECTOR);

		socketEClass = createEClass(SOCKET);
		createEAttribute(socketEClass, SOCKET__NAME);
		createEReference(socketEClass, SOCKET__CONNECTED_SEGMENTS);
		createEAttribute(socketEClass, SOCKET__TYPE);

		operationEClass = createEClass(OPERATION);
		createEAttribute(operationEClass, OPERATION__NAME);
		createEReference(operationEClass, OPERATION__PIPELINE);
		createEAttribute(operationEClass, OPERATION__CONTROL);

		addressSpaceEClass = createEClass(ADDRESS_SPACE);
		createEAttribute(addressSpaceEClass, ADDRESS_SPACE__NAME);
		createEAttribute(addressSpaceEClass, ADDRESS_SPACE__MIN_ADDRESS);
		createEAttribute(addressSpaceEClass, ADDRESS_SPACE__MAX_ADDRESS);
		createEAttribute(addressSpaceEClass, ADDRESS_SPACE__WIDTH);

		elementEClass = createEClass(ELEMENT);
		createEAttribute(elementEClass, ELEMENT__START_CYCLE);
		createEAttribute(elementEClass, ELEMENT__CYCLES);

		readsEClass = createEClass(READS);
		createEReference(readsEClass, READS__PORT);

		writesEClass = createEClass(WRITES);
		createEReference(writesEClass, WRITES__PORT);

		resourceEClass = createEClass(RESOURCE);
		createEAttribute(resourceEClass, RESOURCE__NAME);

		shortImmediateEClass = createEClass(SHORT_IMMEDIATE);
		createEAttribute(shortImmediateEClass, SHORT_IMMEDIATE__EXTENSION);
		createEAttribute(shortImmediateEClass, SHORT_IMMEDIATE__WIDTH);

		guardEClass = createEClass(GUARD);

		exprUnaryEClass = createEClass(EXPR_UNARY);
		createEAttribute(exprUnaryEClass, EXPR_UNARY__OPERATOR);
		createEReference(exprUnaryEClass, EXPR_UNARY__TERM);

		exprBinaryEClass = createEClass(EXPR_BINARY);
		createEAttribute(exprBinaryEClass, EXPR_BINARY__OPERATOR);
		createEReference(exprBinaryEClass, EXPR_BINARY__E1);
		createEReference(exprBinaryEClass, EXPR_BINARY__E2);

		exprTrueEClass = createEClass(EXPR_TRUE);

		exprFalseEClass = createEClass(EXPR_FALSE);

		termEClass = createEClass(TERM);

		termBoolEClass = createEClass(TERM_BOOL);
		createEReference(termBoolEClass, TERM_BOOL__REGISTER);
		createEAttribute(termBoolEClass, TERM_BOOL__INDEX);

		termUnitEClass = createEClass(TERM_UNIT);
		createEReference(termUnitEClass, TERM_UNIT__FUNCTION_UNIT);
		createEReference(termUnitEClass, TERM_UNIT__PORT);

		implementationEClass = createEClass(IMPLEMENTATION);
		createEAttribute(implementationEClass, IMPLEMENTATION__HDB_FILE);
		createEAttribute(implementationEClass, IMPLEMENTATION__ID);

		portToIndexMapEntryEClass = createEClass(PORT_TO_INDEX_MAP_ENTRY);
		createEReference(portToIndexMapEntryEClass,
				PORT_TO_INDEX_MAP_ENTRY__KEY);
		createEAttribute(portToIndexMapEntryEClass,
				PORT_TO_INDEX_MAP_ENTRY__VALUE);

		// Create enums
		socketTypeEEnum = createEEnum(SOCKET_TYPE);
		extensionEEnum = createEEnum(EXTENSION);
		opUnaryEEnum = createEEnum(OP_UNARY);
		opBinaryEEnum = createEEnum(OP_BINARY);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		GraphPackage theGraphPackage = (GraphPackage) EPackage.Registry.INSTANCE
				.getEPackage(GraphPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		designEClass.getESuperTypes().add(theGraphPackage.getGraph());
		hwFifoEClass.getESuperTypes().add(theGraphPackage.getEdge());
		processorEClass.getESuperTypes().add(theGraphPackage.getVertex());
		readsEClass.getESuperTypes().add(this.getElement());
		writesEClass.getESuperTypes().add(this.getElement());
		resourceEClass.getESuperTypes().add(this.getElement());
		exprUnaryEClass.getESuperTypes().add(this.getGuard());
		exprBinaryEClass.getESuperTypes().add(this.getGuard());
		exprTrueEClass.getESuperTypes().add(this.getGuard());
		exprFalseEClass.getESuperTypes().add(this.getGuard());
		termBoolEClass.getESuperTypes().add(this.getTerm());
		termUnitEClass.getESuperTypes().add(this.getTerm());

		// Initialize classes and features; add operations and parameters
		initEClass(designEClass, Design.class, "Design", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(hwFifoEClass, HwFifo.class, "HwFifo", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(processorEClass, Processor.class, "Processor", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProcessor_Gcu(), this.getGlobalControlUnit(), null,
				"gcu", null, 0, 1, Processor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcessor_Buses(), this.getBus(), null, "buses",
				null, 0, -1, Processor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcessor_Bridges(), this.getBridge(), null,
				"bridges", null, 0, -1, Processor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcessor_Sockets(), this.getSocket(), null,
				"sockets", null, 0, -1, Processor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcessor_FunctionUnits(), this.getFunctionUnit(),
				null, "functionUnits", null, 0, -1, Processor.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getProcessor_RegisterFiles(), this.getRegisterFile(),
				null, "registerFiles", null, 0, -1, Processor.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getProcessor_Program(), this.getAddressSpace(), null,
				"program", null, 0, 1, Processor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcessor_Data(), this.getAddressSpace(), null,
				"data", null, 0, 1, Processor.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcessor_HardwareDatabase(),
				this.getImplementation(), null, "hardwareDatabase", null, 0,
				-1, Processor.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(busEClass, Bus.class, "Bus", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBus_Name(), ecorePackage.getEString(), "name", null,
				0, 1, Bus.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBus_Width(), ecorePackage.getEInt(), "width", null,
				0, 1, Bus.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBus_Guards(), this.getGuard(), null, "guards", null,
				0, -1, Bus.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getBus_Segments(), this.getSegment(), null, "segments",
				null, 0, -1, Bus.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBus_ShortImmediate(), this.getShortImmediate(), null,
				"shortImmediate", null, 0, 1, Bus.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bridgeEClass, Bridge.class, "Bridge", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBridge_InputBus(), this.getBus(), null, "inputBus",
				null, 0, 1, Bridge.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBridge_OutputBus(), this.getBus(), null, "outputBus",
				null, 0, 1, Bridge.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(segmentEClass, Segment.class, "Segment", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSegment_Name(), ecorePackage.getEString(), "name",
				null, 0, 1, Segment.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		addEOperation(segmentEClass, this.getBus(), "getBus", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		initEClass(globalControlUnitEClass, GlobalControlUnit.class,
				"GlobalControlUnit", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalControlUnit_Ports(), this.getPort(), null,
				"ports", null, 0, -1, GlobalControlUnit.class, IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGlobalControlUnit_ReturnAddress(), this.getPort(),
				null, "returnAddress", null, 0, 1, GlobalControlUnit.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getGlobalControlUnit_AddressSpace(),
				this.getAddressSpace(), null, "addressSpace", null, 0, 1,
				GlobalControlUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGlobalControlUnit_Operations(), this.getOperation(),
				null, "operations", null, 0, -1, GlobalControlUnit.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getGlobalControlUnit_DelaySlots(),
				ecorePackage.getEInt(), "delaySlots", null, 0, 1,
				GlobalControlUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getGlobalControlUnit_GuardLatency(),
				ecorePackage.getEInt(), "guardLatency", null, 0, 1,
				GlobalControlUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(functionUnitEClass, FunctionUnit.class, "FunctionUnit",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFunctionUnit_Name(), ecorePackage.getEString(),
				"name", null, 0, 1, FunctionUnit.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionUnit_Operations(), this.getOperation(), null,
				"operations", null, 0, -1, FunctionUnit.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionUnit_Ports(), this.getPort(), null, "ports",
				null, 0, -1, FunctionUnit.class, IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionUnit_AddressSpace(), this.getAddressSpace(),
				null, "addressSpace", null, 0, 1, FunctionUnit.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getFunctionUnit_Implementation(),
				this.getImplementation(), null, "implementation", null, 0, 1,
				FunctionUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(registerFileEClass, RegisterFile.class, "RegisterFile",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRegisterFile_Name(), ecorePackage.getEString(),
				"name", null, 0, 1, RegisterFile.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRegisterFile_Size(), ecorePackage.getEInt(), "size",
				null, 0, 1, RegisterFile.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getRegisterFile_Width(), ecorePackage.getEInt(),
				"width", null, 0, 1, RegisterFile.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRegisterFile_MaxReads(), ecorePackage.getEInt(),
				"maxReads", null, 0, 1, RegisterFile.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRegisterFile_MaxWrites(), ecorePackage.getEInt(),
				"maxWrites", null, 0, 1, RegisterFile.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getRegisterFile_Ports(), this.getPort(), null, "ports",
				null, 0, -1, RegisterFile.class, IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRegisterFile_Implementation(),
				this.getImplementation(), null, "implementation", null, 0, 1,
				RegisterFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(portEClass, Port.class, "Port", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPort_Name(), ecorePackage.getEString(), "name", null,
				0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPort_InputSocket(), this.getSocket(), null,
				"inputSocket", null, 0, 1, Port.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPort_OutputSocket(), this.getSocket(), null,
				"outputSocket", null, 0, 1, Port.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPort_Width(), ecorePackage.getEInt(), "width", null,
				0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPort_Trigger(), ecorePackage.getEBoolean(),
				"trigger", null, 0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getPort_OpcodeSelector(), ecorePackage.getEBoolean(),
				"opcodeSelector", null, 0, 1, Port.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(portEClass, null, "connect", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSocket(), "socket", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		initEClass(socketEClass, Socket.class, "Socket", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSocket_Name(), ecorePackage.getEString(), "name",
				null, 0, 1, Socket.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getSocket_ConnectedSegments(), this.getSegment(), null,
				"connectedSegments", null, 0, -1, Socket.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSocket_Type(), this.getSocketType(), "type", null, 0,
				1, Socket.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(socketEClass, ecorePackage.getEBoolean(), "isInput", 0,
				1, IS_UNIQUE, IS_ORDERED);

		addEOperation(socketEClass, ecorePackage.getEBoolean(), "isOutput", 0,
				1, IS_UNIQUE, IS_ORDERED);

		initEClass(operationEClass, Operation.class, "Operation", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOperation_Name(), ecorePackage.getEString(), "name",
				null, 0, 1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getOperation_Pipeline(), this.getElement(), null,
				"pipeline", null, 0, -1, Operation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOperation_Control(), ecorePackage.getEBoolean(),
				"control", null, 0, 1, Operation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		addEOperation(operationEClass, this.getPort(), "getPorts", 0, -1,
				IS_UNIQUE, IS_ORDERED);

		addEOperation(operationEClass, this.getPortToIndexMapEntry(),
				"getPortToIndexMap", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(addressSpaceEClass, AddressSpace.class, "AddressSpace",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAddressSpace_Name(), ecorePackage.getEString(),
				"name", null, 0, 1, AddressSpace.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressSpace_MinAddress(), ecorePackage.getEInt(),
				"minAddress", null, 0, 1, AddressSpace.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressSpace_MaxAddress(), ecorePackage.getEInt(),
				"maxAddress", null, 0, 1, AddressSpace.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddressSpace_Width(), ecorePackage.getEInt(),
				"width", null, 0, 1, AddressSpace.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(elementEClass, Element.class, "Element", IS_ABSTRACT,
				IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getElement_StartCycle(), ecorePackage.getEInt(),
				"startCycle", null, 0, 1, Element.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getElement_Cycles(), ecorePackage.getEInt(), "cycles",
				null, 1, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		addEOperation(elementEClass, ecorePackage.getEBoolean(), "isReads", 0,
				1, IS_UNIQUE, IS_ORDERED);

		addEOperation(elementEClass, ecorePackage.getEBoolean(), "isWrites", 0,
				1, IS_UNIQUE, IS_ORDERED);

		addEOperation(elementEClass, ecorePackage.getEBoolean(), "isResource",
				0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(readsEClass, Reads.class, "Reads", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReads_Port(), this.getPort(), null, "port", null, 0,
				1, Reads.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(writesEClass, Writes.class, "Writes", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWrites_Port(), this.getPort(), null, "port", null, 0,
				1, Writes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(resourceEClass, Resource.class, "Resource", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResource_Name(), ecorePackage.getEString(), "name",
				null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(shortImmediateEClass, ShortImmediate.class,
				"ShortImmediate", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getShortImmediate_Extension(), this.getExtension(),
				"extension", null, 0, 1, ShortImmediate.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getShortImmediate_Width(), ecorePackage.getEInt(),
				"width", null, 0, 1, ShortImmediate.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(guardEClass, Guard.class, "Guard", IS_ABSTRACT,
				IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(guardEClass, ecorePackage.getEBoolean(), "isExprUnary",
				0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(guardEClass, ecorePackage.getEBoolean(), "isExprBinary",
				0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(guardEClass, ecorePackage.getEBoolean(), "isExprTrue", 0,
				1, IS_UNIQUE, IS_ORDERED);

		addEOperation(guardEClass, ecorePackage.getEBoolean(), "isExprFalse",
				0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(exprUnaryEClass, ExprUnary.class, "ExprUnary", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprUnary_Operator(), this.getOpUnary(), "operator",
				"", 0, 1, ExprUnary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getExprUnary_Term(), this.getTerm(), null, "term", null,
				0, 1, ExprUnary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(exprUnaryEClass, ecorePackage.getEBoolean(),
				"isInverted", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(exprUnaryEClass, ecorePackage.getEBoolean(), "isSimple",
				0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(exprBinaryEClass, ExprBinary.class, "ExprBinary",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExprBinary_Operator(), this.getOpBinary(),
				"operator", "", 0, 1, ExprBinary.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getExprBinary_E1(), this.getExprUnary(), null, "e1",
				null, 0, 1, ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExprBinary_E2(), this.getExprUnary(), null, "e2",
				null, 0, 1, ExprBinary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(exprBinaryEClass, ecorePackage.getEBoolean(), "isOr", 0,
				1, IS_UNIQUE, IS_ORDERED);

		addEOperation(exprBinaryEClass, ecorePackage.getEBoolean(), "isAnd", 0,
				1, IS_UNIQUE, IS_ORDERED);

		initEClass(exprTrueEClass, ExprTrue.class, "ExprTrue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(exprFalseEClass, ExprFalse.class, "ExprFalse", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(termEClass, Term.class, "Term", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		addEOperation(termEClass, ecorePackage.getEBoolean(), "isTermBool", 0,
				1, IS_UNIQUE, IS_ORDERED);

		addEOperation(termEClass, ecorePackage.getEBoolean(), "isTermUnit", 0,
				1, IS_UNIQUE, IS_ORDERED);

		initEClass(termBoolEClass, TermBool.class, "TermBool", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTermBool_Register(), this.getRegisterFile(), null,
				"register", null, 0, 1, TermBool.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTermBool_Index(), ecorePackage.getEInt(), "index",
				null, 0, 1, TermBool.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(termUnitEClass, TermUnit.class, "TermUnit", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTermUnit_FunctionUnit(), this.getFunctionUnit(),
				null, "functionUnit", null, 0, 1, TermUnit.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTermUnit_Port(), this.getPort(), null, "port", null,
				0, 1, TermUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(implementationEClass, Implementation.class,
				"Implementation", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImplementation_HdbFile(), ecorePackage.getEString(),
				"hdbFile", null, 0, 1, Implementation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getImplementation_Id(), ecorePackage.getEInt(), "id",
				null, 0, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(portToIndexMapEntryEClass, Map.Entry.class,
				"PortToIndexMapEntry", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPortToIndexMapEntry_Key(), this.getPort(), null,
				"key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPortToIndexMapEntry_Value(),
				ecorePackage.getEIntegerObject(), "value", null, 0, 1,
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(socketTypeEEnum, SocketType.class, "SocketType");
		addEEnumLiteral(socketTypeEEnum, SocketType.INPUT);
		addEEnumLiteral(socketTypeEEnum, SocketType.OUTPUT);

		initEEnum(extensionEEnum, Extension.class, "Extension");
		addEEnumLiteral(extensionEEnum, Extension.SIGN);
		addEEnumLiteral(extensionEEnum, Extension.ZERO);

		initEEnum(opUnaryEEnum, OpUnary.class, "OpUnary");
		addEEnumLiteral(opUnaryEEnum, OpUnary.SIMPLE);
		addEEnumLiteral(opUnaryEEnum, OpUnary.INVERTED);

		initEEnum(opBinaryEEnum, OpBinary.class, "OpBinary");
		addEEnumLiteral(opBinaryEEnum, OpBinary.AND);
		addEEnumLiteral(opBinaryEEnum, OpBinary.OR);

		// Create resource
		createResource(eNS_URI);
	}

} // ArchitecturePackageImpl
