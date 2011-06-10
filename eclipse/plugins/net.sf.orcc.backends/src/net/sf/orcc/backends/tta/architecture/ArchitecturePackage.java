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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.tta.architecture.ArchitectureFactory
 * @model kind="package"
 * @generated
 */
public interface ArchitecturePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "architecture";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/backends/tta/architecture/TTA_architecture";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.backends.tta.architecture";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArchitecturePackage eINSTANCE = net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl <em>TTA</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.TTAImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getTTA()
	 * @generated
	 */
	int TTA = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA__NAME = 0;

	/**
	 * The feature id for the '<em><b>Buses</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA__BUSES = 1;

	/**
	 * The feature id for the '<em><b>Sockets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA__SOCKETS = 2;

	/**
	 * The feature id for the '<em><b>Functional Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA__FUNCTIONAL_UNITS = 3;

	/**
	 * The feature id for the '<em><b>Register Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA__REGISTER_FILES = 4;

	/**
	 * The feature id for the '<em><b>Program</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA__PROGRAM = 5;

	/**
	 * The feature id for the '<em><b>Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA__DATA = 6;

	/**
	 * The number of structural features of the '<em>TTA</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TTA_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.BusImpl <em>Bus</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.BusImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getBus()
	 * @generated
	 */
	int BUS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUS__NAME = 0;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUS__WIDTH = 1;

	/**
	 * The number of structural features of the '<em>Bus</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl <em>Global Control Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getGlobalControlUnit()
	 * @generated
	 */
	int GLOBAL_CONTROL_UNIT = 2;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONTROL_UNIT__PORTS = 0;

	/**
	 * The feature id for the '<em><b>Program</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONTROL_UNIT__PROGRAM = 1;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONTROL_UNIT__OPERATIONS = 2;

	/**
	 * The feature id for the '<em><b>Delay Slots</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONTROL_UNIT__DELAY_SLOTS = 3;

	/**
	 * The feature id for the '<em><b>Guard Latency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONTROL_UNIT__GUARD_LATENCY = 4;

	/**
	 * The number of structural features of the '<em>Global Control Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONTROL_UNIT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.FunctionalUnitImpl <em>Functional Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.FunctionalUnitImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getFunctionalUnit()
	 * @generated
	 */
	int FUNCTIONAL_UNIT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_UNIT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_UNIT__OPERATIONS = 1;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_UNIT__PORTS = 2;

	/**
	 * The number of structural features of the '<em>Functional Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTIONAL_UNIT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl <em>Register File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getRegisterFile()
	 * @generated
	 */
	int REGISTER_FILE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTER_FILE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTER_FILE__SIZE = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTER_FILE__WIDTH = 2;

	/**
	 * The feature id for the '<em><b>Max Reads</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTER_FILE__MAX_READS = 3;

	/**
	 * The feature id for the '<em><b>Max Writes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTER_FILE__MAX_WRITES = 4;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTER_FILE__PORTS = 5;

	/**
	 * The number of structural features of the '<em>Register File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTER_FILE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.PortImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getPort()
	 * @generated
	 */
	int PORT = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Connected Socket</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__CONNECTED_SOCKET = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__WIDTH = 2;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.SocketImpl <em>Socket</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.SocketImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getSocket()
	 * @generated
	 */
	int SOCKET = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCKET__NAME = 0;

	/**
	 * The number of structural features of the '<em>Socket</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCKET_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.OperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.OperationImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getOperation()
	 * @generated
	 */
	int OPERATION = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__NAME = 0;

	/**
	 * The number of structural features of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.OperationCtrlImpl <em>Operation Ctrl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.OperationCtrlImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getOperationCtrl()
	 * @generated
	 */
	int OPERATION_CTRL = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_CTRL__NAME = OPERATION__NAME;

	/**
	 * The number of structural features of the '<em>Operation Ctrl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_CTRL_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.tta.architecture.impl.AdressSpaceImpl <em>Adress Space</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.tta.architecture.impl.AdressSpaceImpl
	 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getAdressSpace()
	 * @generated
	 */
	int ADRESS_SPACE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESS_SPACE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESS_SPACE__WIDTH = 1;

	/**
	 * The number of structural features of the '<em>Adress Space</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADRESS_SPACE_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.TTA <em>TTA</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TTA</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA
	 * @generated
	 */
	EClass getTTA();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.TTA#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA#getName()
	 * @see #getTTA()
	 * @generated
	 */
	EAttribute getTTA_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.TTA#getBuses <em>Buses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Buses</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA#getBuses()
	 * @see #getTTA()
	 * @generated
	 */
	EReference getTTA_Buses();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.TTA#getSockets <em>Sockets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sockets</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA#getSockets()
	 * @see #getTTA()
	 * @generated
	 */
	EReference getTTA_Sockets();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.TTA#getFunctionalUnits <em>Functional Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functional Units</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA#getFunctionalUnits()
	 * @see #getTTA()
	 * @generated
	 */
	EReference getTTA_FunctionalUnits();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.TTA#getRegisterFiles <em>Register Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Register Files</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA#getRegisterFiles()
	 * @see #getTTA()
	 * @generated
	 */
	EReference getTTA_RegisterFiles();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.tta.architecture.TTA#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Program</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA#getProgram()
	 * @see #getTTA()
	 * @generated
	 */
	EReference getTTA_Program();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.backends.tta.architecture.TTA#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.TTA#getData()
	 * @see #getTTA()
	 * @generated
	 */
	EReference getTTA_Data();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.Bus <em>Bus</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bus</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Bus
	 * @generated
	 */
	EClass getBus();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.Bus#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Bus#getName()
	 * @see #getBus()
	 * @generated
	 */
	EAttribute getBus_Name();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.Bus#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Bus#getWidth()
	 * @see #getBus()
	 * @generated
	 */
	EAttribute getBus_Width();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit <em>Global Control Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Control Unit</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.GlobalControlUnit
	 * @generated
	 */
	EClass getGlobalControlUnit();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getPorts()
	 * @see #getGlobalControlUnit()
	 * @generated
	 */
	EReference getGlobalControlUnit_Ports();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Program</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getProgram()
	 * @see #getGlobalControlUnit()
	 * @generated
	 */
	EReference getGlobalControlUnit_Program();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operations</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getOperations()
	 * @see #getGlobalControlUnit()
	 * @generated
	 */
	EReference getGlobalControlUnit_Operations();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getDelaySlots <em>Delay Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delay Slots</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getDelaySlots()
	 * @see #getGlobalControlUnit()
	 * @generated
	 */
	EAttribute getGlobalControlUnit_DelaySlots();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getGuardLatency <em>Guard Latency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guard Latency</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.GlobalControlUnit#getGuardLatency()
	 * @see #getGlobalControlUnit()
	 * @generated
	 */
	EAttribute getGlobalControlUnit_GuardLatency();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.FunctionalUnit <em>Functional Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Functional Unit</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.FunctionalUnit
	 * @generated
	 */
	EClass getFunctionalUnit();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.FunctionalUnit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.FunctionalUnit#getName()
	 * @see #getFunctionalUnit()
	 * @generated
	 */
	EAttribute getFunctionalUnit_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.FunctionalUnit#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.FunctionalUnit#getOperations()
	 * @see #getFunctionalUnit()
	 * @generated
	 */
	EReference getFunctionalUnit_Operations();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.FunctionalUnit#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.FunctionalUnit#getPorts()
	 * @see #getFunctionalUnit()
	 * @generated
	 */
	EReference getFunctionalUnit_Ports();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.RegisterFile <em>Register File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Register File</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile
	 * @generated
	 */
	EClass getRegisterFile();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile#getName()
	 * @see #getRegisterFile()
	 * @generated
	 */
	EAttribute getRegisterFile_Name();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile#getSize()
	 * @see #getRegisterFile()
	 * @generated
	 */
	EAttribute getRegisterFile_Size();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile#getWidth()
	 * @see #getRegisterFile()
	 * @generated
	 */
	EAttribute getRegisterFile_Width();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxReads <em>Max Reads</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Reads</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxReads()
	 * @see #getRegisterFile()
	 * @generated
	 */
	EAttribute getRegisterFile_MaxReads();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxWrites <em>Max Writes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Writes</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile#getMaxWrites()
	 * @see #getRegisterFile()
	 * @generated
	 */
	EAttribute getRegisterFile_MaxWrites();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.backends.tta.architecture.RegisterFile#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile#getPorts()
	 * @see #getRegisterFile()
	 * @generated
	 */
	EReference getRegisterFile_Ports();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.Port#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Port#getName()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Name();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.backends.tta.architecture.Port#getConnectedSocket <em>Connected Socket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Connected Socket</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Port#getConnectedSocket()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_ConnectedSocket();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.Port#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Port#getWidth()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Width();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.Socket <em>Socket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Socket</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Socket
	 * @generated
	 */
	EClass getSocket();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.Socket#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Socket#getName()
	 * @see #getSocket()
	 * @generated
	 */
	EAttribute getSocket_Name();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Operation
	 * @generated
	 */
	EClass getOperation();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.Operation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.Operation#getName()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_Name();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.OperationCtrl <em>Operation Ctrl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Ctrl</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.OperationCtrl
	 * @generated
	 */
	EClass getOperationCtrl();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.tta.architecture.AdressSpace <em>Adress Space</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adress Space</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.AdressSpace
	 * @generated
	 */
	EClass getAdressSpace();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.AdressSpace#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.AdressSpace#getName()
	 * @see #getAdressSpace()
	 * @generated
	 */
	EAttribute getAdressSpace_Name();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.tta.architecture.AdressSpace#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see net.sf.orcc.backends.tta.architecture.AdressSpace#getWidth()
	 * @see #getAdressSpace()
	 * @generated
	 */
	EAttribute getAdressSpace_Width();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ArchitectureFactory getArchitectureFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.TTAImpl <em>TTA</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.TTAImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getTTA()
		 * @generated
		 */
		EClass TTA = eINSTANCE.getTTA();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TTA__NAME = eINSTANCE.getTTA_Name();

		/**
		 * The meta object literal for the '<em><b>Buses</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TTA__BUSES = eINSTANCE.getTTA_Buses();

		/**
		 * The meta object literal for the '<em><b>Sockets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TTA__SOCKETS = eINSTANCE.getTTA_Sockets();

		/**
		 * The meta object literal for the '<em><b>Functional Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TTA__FUNCTIONAL_UNITS = eINSTANCE.getTTA_FunctionalUnits();

		/**
		 * The meta object literal for the '<em><b>Register Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TTA__REGISTER_FILES = eINSTANCE.getTTA_RegisterFiles();

		/**
		 * The meta object literal for the '<em><b>Program</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TTA__PROGRAM = eINSTANCE.getTTA_Program();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TTA__DATA = eINSTANCE.getTTA_Data();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.BusImpl <em>Bus</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.BusImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getBus()
		 * @generated
		 */
		EClass BUS = eINSTANCE.getBus();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUS__NAME = eINSTANCE.getBus_Name();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUS__WIDTH = eINSTANCE.getBus_Width();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl <em>Global Control Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.GlobalControlUnitImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getGlobalControlUnit()
		 * @generated
		 */
		EClass GLOBAL_CONTROL_UNIT = eINSTANCE.getGlobalControlUnit();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_CONTROL_UNIT__PORTS = eINSTANCE.getGlobalControlUnit_Ports();

		/**
		 * The meta object literal for the '<em><b>Program</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_CONTROL_UNIT__PROGRAM = eINSTANCE.getGlobalControlUnit_Program();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_CONTROL_UNIT__OPERATIONS = eINSTANCE.getGlobalControlUnit_Operations();

		/**
		 * The meta object literal for the '<em><b>Delay Slots</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_CONTROL_UNIT__DELAY_SLOTS = eINSTANCE.getGlobalControlUnit_DelaySlots();

		/**
		 * The meta object literal for the '<em><b>Guard Latency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_CONTROL_UNIT__GUARD_LATENCY = eINSTANCE.getGlobalControlUnit_GuardLatency();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.FunctionalUnitImpl <em>Functional Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.FunctionalUnitImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getFunctionalUnit()
		 * @generated
		 */
		EClass FUNCTIONAL_UNIT = eINSTANCE.getFunctionalUnit();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTIONAL_UNIT__NAME = eINSTANCE.getFunctionalUnit_Name();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTIONAL_UNIT__OPERATIONS = eINSTANCE.getFunctionalUnit_Operations();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTIONAL_UNIT__PORTS = eINSTANCE.getFunctionalUnit_Ports();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl <em>Register File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.RegisterFileImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getRegisterFile()
		 * @generated
		 */
		EClass REGISTER_FILE = eINSTANCE.getRegisterFile();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGISTER_FILE__NAME = eINSTANCE.getRegisterFile_Name();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGISTER_FILE__SIZE = eINSTANCE.getRegisterFile_Size();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGISTER_FILE__WIDTH = eINSTANCE.getRegisterFile_Width();

		/**
		 * The meta object literal for the '<em><b>Max Reads</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGISTER_FILE__MAX_READS = eINSTANCE.getRegisterFile_MaxReads();

		/**
		 * The meta object literal for the '<em><b>Max Writes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGISTER_FILE__MAX_WRITES = eINSTANCE.getRegisterFile_MaxWrites();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGISTER_FILE__PORTS = eINSTANCE.getRegisterFile_Ports();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.PortImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NAME = eINSTANCE.getPort_Name();

		/**
		 * The meta object literal for the '<em><b>Connected Socket</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__CONNECTED_SOCKET = eINSTANCE.getPort_ConnectedSocket();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__WIDTH = eINSTANCE.getPort_Width();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.SocketImpl <em>Socket</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.SocketImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getSocket()
		 * @generated
		 */
		EClass SOCKET = eINSTANCE.getSocket();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOCKET__NAME = eINSTANCE.getSocket_Name();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.OperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.OperationImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getOperation()
		 * @generated
		 */
		EClass OPERATION = eINSTANCE.getOperation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__NAME = eINSTANCE.getOperation_Name();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.OperationCtrlImpl <em>Operation Ctrl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.OperationCtrlImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getOperationCtrl()
		 * @generated
		 */
		EClass OPERATION_CTRL = eINSTANCE.getOperationCtrl();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.tta.architecture.impl.AdressSpaceImpl <em>Adress Space</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.tta.architecture.impl.AdressSpaceImpl
		 * @see net.sf.orcc.backends.tta.architecture.impl.ArchitecturePackageImpl#getAdressSpace()
		 * @generated
		 */
		EClass ADRESS_SPACE = eINSTANCE.getAdressSpace();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESS_SPACE__NAME = eINSTANCE.getAdressSpace_Name();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADRESS_SPACE__WIDTH = eINSTANCE.getAdressSpace_Width();

	}

} //ArchitecturePackage
