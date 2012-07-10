/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities.impl;

import net.sf.orcc.backends.cplusplus.entities.Communicator;
import net.sf.orcc.backends.cplusplus.entities.Interface;
import net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet;
import net.sf.orcc.backends.cplusplus.entities.Receiver;
import net.sf.orcc.backends.cplusplus.entities.Sender;
import net.sf.orcc.backends.cplusplus.entities.YaceEntitiesFactory;
import net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage;

import net.sf.orcc.df.DfPackage;

import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.ir.IrPackage;

import net.sf.orcc.moc.MocPackage;

import net.sf.orcc.util.UtilPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class YaceEntitiesPackageImpl extends EPackageImpl implements YaceEntitiesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass senderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass receiverEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interfaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interfaceEthernetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicatorEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private YaceEntitiesPackageImpl() {
		super(eNS_URI, YaceEntitiesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link YaceEntitiesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static YaceEntitiesPackage init() {
		if (isInited) return (YaceEntitiesPackage)EPackage.Registry.INSTANCE.getEPackage(YaceEntitiesPackage.eNS_URI);

		// Obtain or create and register package
		YaceEntitiesPackageImpl theYaceEntitiesPackage = (YaceEntitiesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof YaceEntitiesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new YaceEntitiesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		DfPackage.eINSTANCE.eClass();
		IrPackage.eINSTANCE.eClass();
		MocPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theYaceEntitiesPackage.createPackageContents();

		// Initialize created meta-data
		theYaceEntitiesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theYaceEntitiesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(YaceEntitiesPackage.eNS_URI, theYaceEntitiesPackage);
		return theYaceEntitiesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSender() {
		return senderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSender_Input() {
		return (EReference)senderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReceiver() {
		return receiverEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReceiver_Output() {
		return (EReference)receiverEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterface() {
		return interfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterface_Id() {
		return (EAttribute)interfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterfaceEthernet() {
		return interfaceEthernetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterfaceEthernet_Ip() {
		return (EAttribute)interfaceEthernetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterfaceEthernet_PortNumber() {
		return (EAttribute)interfaceEthernetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInterfaceEthernet_Server() {
		return (EAttribute)interfaceEthernetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunicator() {
		return communicatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommunicator_Intf() {
		return (EReference)communicatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public YaceEntitiesFactory getYaceEntitiesFactory() {
		return (YaceEntitiesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		senderEClass = createEClass(SENDER);
		createEReference(senderEClass, SENDER__INPUT);

		receiverEClass = createEClass(RECEIVER);
		createEReference(receiverEClass, RECEIVER__OUTPUT);

		interfaceEClass = createEClass(INTERFACE);
		createEAttribute(interfaceEClass, INTERFACE__ID);

		interfaceEthernetEClass = createEClass(INTERFACE_ETHERNET);
		createEAttribute(interfaceEthernetEClass, INTERFACE_ETHERNET__IP);
		createEAttribute(interfaceEthernetEClass, INTERFACE_ETHERNET__PORT_NUMBER);
		createEAttribute(interfaceEthernetEClass, INTERFACE_ETHERNET__SERVER);

		communicatorEClass = createEClass(COMMUNICATOR);
		createEReference(communicatorEClass, COMMUNICATOR__INTF);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		DfPackage theDfPackage = (DfPackage)EPackage.Registry.INSTANCE.getEPackage(DfPackage.eNS_URI);
		UtilPackage theUtilPackage = (UtilPackage)EPackage.Registry.INSTANCE.getEPackage(UtilPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		GraphPackage theGraphPackage = (GraphPackage)EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		senderEClass.getESuperTypes().add(theDfPackage.getActor());
		senderEClass.getESuperTypes().add(this.getCommunicator());
		senderEClass.getESuperTypes().add(theUtilPackage.getAdaptable());
		receiverEClass.getESuperTypes().add(theDfPackage.getActor());
		receiverEClass.getESuperTypes().add(this.getCommunicator());
		receiverEClass.getESuperTypes().add(theUtilPackage.getAdaptable());
		interfaceEthernetEClass.getESuperTypes().add(this.getInterface());
		communicatorEClass.getESuperTypes().add(theGraphPackage.getVertex());
		communicatorEClass.getESuperTypes().add(theUtilPackage.getAdaptable());

		// Initialize classes and features; add operations and parameters
		initEClass(senderEClass, Sender.class, "Sender", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSender_Input(), theDfPackage.getPort(), null, "input", null, 0, 1, Sender.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(receiverEClass, Receiver.class, "Receiver", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReceiver_Output(), theDfPackage.getPort(), null, "output", null, 0, 1, Receiver.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(interfaceEClass, Interface.class, "Interface", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInterface_Id(), theEcorePackage.getEString(), "id", null, 0, 1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(interfaceEthernetEClass, InterfaceEthernet.class, "InterfaceEthernet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInterfaceEthernet_Ip(), theEcorePackage.getEString(), "ip", null, 0, 1, InterfaceEthernet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInterfaceEthernet_PortNumber(), theEcorePackage.getELong(), "portNumber", null, 0, 1, InterfaceEthernet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInterfaceEthernet_Server(), theEcorePackage.getEBoolean(), "server", null, 0, 1, InterfaceEthernet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(communicatorEClass, Communicator.class, "Communicator", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommunicator_Intf(), this.getInterface(), null, "intf", null, 0, 1, Communicator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //YaceEntitiesPackageImpl
