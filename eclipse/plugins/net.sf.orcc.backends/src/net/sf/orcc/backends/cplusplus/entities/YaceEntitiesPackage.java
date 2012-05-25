/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

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
 * @see net.sf.orcc.backends.cplusplus.entities.YaceEntitiesFactory
 * @model kind="package"
 * @generated
 */
public interface YaceEntitiesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "entities";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/backends/cplusplus/entities/YaceEntities";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.backends.cplusplus.entities";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	YaceEntitiesPackage eINSTANCE = net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.CommunicatorImpl <em>Communicator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.CommunicatorImpl
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getCommunicator()
	 * @generated
	 */
	int COMMUNICATOR = 4;

	/**
	 * The feature id for the '<em><b>Intf</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATOR__INTF = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Communicator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATOR_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.SenderImpl <em>Sender</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.SenderImpl
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getSender()
	 * @generated
	 */
	int SENDER = 0;

	/**
	 * The feature id for the '<em><b>Intf</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDER__INTF = COMMUNICATOR__INTF;

	/**
	 * The feature id for the '<em><b>Input</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDER__INPUT = COMMUNICATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sender</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SENDER_FEATURE_COUNT = COMMUNICATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.ReceiverImpl <em>Receiver</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.ReceiverImpl
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getReceiver()
	 * @generated
	 */
	int RECEIVER = 1;

	/**
	 * The feature id for the '<em><b>Intf</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVER__INTF = COMMUNICATOR__INTF;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVER__OUTPUT = COMMUNICATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Receiver</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECEIVER_FEATURE_COUNT = COMMUNICATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.InterfaceImpl <em>Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.InterfaceImpl
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getInterface()
	 * @generated
	 */
	int INTERFACE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE__ID = 0;

	/**
	 * The number of structural features of the '<em>Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.InterfaceEthernetImpl <em>Interface Ethernet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.InterfaceEthernetImpl
	 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getInterfaceEthernet()
	 * @generated
	 */
	int INTERFACE_ETHERNET = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_ETHERNET__ID = INTERFACE__ID;

	/**
	 * The feature id for the '<em><b>Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_ETHERNET__IP = INTERFACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Port Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_ETHERNET__PORT_NUMBER = INTERFACE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Server</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_ETHERNET__SERVER = INTERFACE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Interface Ethernet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_ETHERNET_FEATURE_COUNT = INTERFACE_FEATURE_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.cplusplus.entities.Sender <em>Sender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sender</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Sender
	 * @generated
	 */
	EClass getSender();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.backends.cplusplus.entities.Sender#getInput <em>Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Input</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Sender#getInput()
	 * @see #getSender()
	 * @generated
	 */
	EReference getSender_Input();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.cplusplus.entities.Receiver <em>Receiver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Receiver</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Receiver
	 * @generated
	 */
	EClass getReceiver();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.backends.cplusplus.entities.Receiver#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Output</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Receiver#getOutput()
	 * @see #getReceiver()
	 * @generated
	 */
	EReference getReceiver_Output();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.cplusplus.entities.Interface <em>Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Interface
	 * @generated
	 */
	EClass getInterface();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.cplusplus.entities.Interface#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Interface#getId()
	 * @see #getInterface()
	 * @generated
	 */
	EAttribute getInterface_Id();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet <em>Interface Ethernet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface Ethernet</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet
	 * @generated
	 */
	EClass getInterfaceEthernet();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getIp <em>Ip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ip</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getIp()
	 * @see #getInterfaceEthernet()
	 * @generated
	 */
	EAttribute getInterfaceEthernet_Ip();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getPortNumber <em>Port Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port Number</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#getPortNumber()
	 * @see #getInterfaceEthernet()
	 * @generated
	 */
	EAttribute getInterfaceEthernet_PortNumber();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#isServer <em>Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Server</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.InterfaceEthernet#isServer()
	 * @see #getInterfaceEthernet()
	 * @generated
	 */
	EAttribute getInterfaceEthernet_Server();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.backends.cplusplus.entities.Communicator <em>Communicator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communicator</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Communicator
	 * @generated
	 */
	EClass getCommunicator();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.backends.cplusplus.entities.Communicator#getIntf <em>Intf</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Intf</em>'.
	 * @see net.sf.orcc.backends.cplusplus.entities.Communicator#getIntf()
	 * @see #getCommunicator()
	 * @generated
	 */
	EReference getCommunicator_Intf();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	YaceEntitiesFactory getYaceEntitiesFactory();

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
		 * The meta object literal for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.SenderImpl <em>Sender</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.SenderImpl
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getSender()
		 * @generated
		 */
		EClass SENDER = eINSTANCE.getSender();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SENDER__INPUT = eINSTANCE.getSender_Input();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.ReceiverImpl <em>Receiver</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.ReceiverImpl
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getReceiver()
		 * @generated
		 */
		EClass RECEIVER = eINSTANCE.getReceiver();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECEIVER__OUTPUT = eINSTANCE.getReceiver_Output();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.InterfaceImpl <em>Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.InterfaceImpl
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getInterface()
		 * @generated
		 */
		EClass INTERFACE = eINSTANCE.getInterface();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERFACE__ID = eINSTANCE.getInterface_Id();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.InterfaceEthernetImpl <em>Interface Ethernet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.InterfaceEthernetImpl
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getInterfaceEthernet()
		 * @generated
		 */
		EClass INTERFACE_ETHERNET = eINSTANCE.getInterfaceEthernet();

		/**
		 * The meta object literal for the '<em><b>Ip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERFACE_ETHERNET__IP = eINSTANCE.getInterfaceEthernet_Ip();

		/**
		 * The meta object literal for the '<em><b>Port Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERFACE_ETHERNET__PORT_NUMBER = eINSTANCE.getInterfaceEthernet_PortNumber();

		/**
		 * The meta object literal for the '<em><b>Server</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERFACE_ETHERNET__SERVER = eINSTANCE.getInterfaceEthernet_Server();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.backends.cplusplus.entities.impl.CommunicatorImpl <em>Communicator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.CommunicatorImpl
		 * @see net.sf.orcc.backends.cplusplus.entities.impl.YaceEntitiesPackageImpl#getCommunicator()
		 * @generated
		 */
		EClass COMMUNICATOR = eINSTANCE.getCommunicator();

		/**
		 * The meta object literal for the '<em><b>Intf</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATOR__INTF = eINSTANCE.getCommunicator_Intf();

	}

} //YaceEntitiesPackage
