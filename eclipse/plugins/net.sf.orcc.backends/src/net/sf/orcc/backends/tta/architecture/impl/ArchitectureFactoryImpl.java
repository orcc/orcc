/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture.impl;

import net.sf.orcc.backends.tta.architecture.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArchitectureFactoryImpl extends EFactoryImpl implements ArchitectureFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ArchitectureFactory init() {
		try {
			ArchitectureFactory theArchitectureFactory = (ArchitectureFactory)EPackage.Registry.INSTANCE.getEFactory("http://orcc.sf.net/backends/tta/architecture/TTA_architecture"); 
			if (theArchitectureFactory != null) {
				return theArchitectureFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ArchitectureFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ArchitecturePackage.TTA: return createTTA();
			case ArchitecturePackage.BUS: return createBus();
			case ArchitecturePackage.GLOBAL_CONTROL_UNIT: return createGlobalControlUnit();
			case ArchitecturePackage.FUNCTIONAL_UNIT: return createFunctionalUnit();
			case ArchitecturePackage.REGISTER_FILE: return createRegisterFile();
			case ArchitecturePackage.PORT: return createPort();
			case ArchitecturePackage.SOCKET: return createSocket();
			case ArchitecturePackage.OPERATION: return createOperation();
			case ArchitecturePackage.OPERATION_CTRL: return createOperationCtrl();
			case ArchitecturePackage.ADRESS_SPACE: return createAdressSpace();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TTA createTTA() {
		TTAImpl tta = new TTAImpl();
		return tta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Bus createBus() {
		BusImpl bus = new BusImpl();
		return bus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalControlUnit createGlobalControlUnit() {
		GlobalControlUnitImpl globalControlUnit = new GlobalControlUnitImpl();
		return globalControlUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionalUnit createFunctionalUnit() {
		FunctionalUnitImpl functionalUnit = new FunctionalUnitImpl();
		return functionalUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RegisterFile createRegisterFile() {
		RegisterFileImpl registerFile = new RegisterFileImpl();
		return registerFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Socket createSocket() {
		SocketImpl socket = new SocketImpl();
		return socket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation createOperation() {
		OperationImpl operation = new OperationImpl();
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationCtrl createOperationCtrl() {
		OperationCtrlImpl operationCtrl = new OperationCtrlImpl();
		return operationCtrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdressSpace createAdressSpace() {
		AdressSpaceImpl adressSpace = new AdressSpaceImpl();
		return adressSpace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitecturePackage getArchitecturePackage() {
		return (ArchitecturePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ArchitecturePackage getPackage() {
		return ArchitecturePackage.eINSTANCE;
	}

} //ArchitectureFactoryImpl
