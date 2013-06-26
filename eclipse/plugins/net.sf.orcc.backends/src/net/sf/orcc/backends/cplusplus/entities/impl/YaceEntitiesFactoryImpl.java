/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.cplusplus.entities.impl;

import net.sf.orcc.backends.cplusplus.entities.*;

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
public class YaceEntitiesFactoryImpl extends EFactoryImpl implements YaceEntitiesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static YaceEntitiesFactory init() {
		try {
			YaceEntitiesFactory theYaceEntitiesFactory = (YaceEntitiesFactory)EPackage.Registry.INSTANCE.getEFactory("http://orcc.sf.net/backends/cplusplus/entities/YaceEntities"); 
			if (theYaceEntitiesFactory != null) {
				return theYaceEntitiesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new YaceEntitiesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public YaceEntitiesFactoryImpl() {
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
			case YaceEntitiesPackage.SENDER: return createSender();
			case YaceEntitiesPackage.RECEIVER: return createReceiver();
			case YaceEntitiesPackage.INTERFACE_ETHERNET: return createInterfaceEthernet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sender createSender() {
		SenderImpl sender = new SenderImpl();
		return sender;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Receiver createReceiver() {
		ReceiverImpl receiver = new ReceiverImpl();
		return receiver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterfaceEthernet createInterfaceEthernet() {
		InterfaceEthernetImpl interfaceEthernet = new InterfaceEthernetImpl();
		return interfaceEthernet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public YaceEntitiesPackage getYaceEntitiesPackage() {
		return (YaceEntitiesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static YaceEntitiesPackage getPackage() {
		return YaceEntitiesPackage.eINSTANCE;
	}

} //YaceEntitiesFactoryImpl
