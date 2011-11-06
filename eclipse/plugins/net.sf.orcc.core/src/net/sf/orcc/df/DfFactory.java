/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df;

import java.util.List;

import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see net.sf.orcc.df.DfPackage
 * @generated
 */
public interface DfFactory extends EFactory {

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	DfFactory eINSTANCE = net.sf.orcc.df.impl.DfFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	Attribute createAttribute(String name, EObject value);

	/**
	 * Returns a new object of class '<em>Broadcast</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Broadcast</em>'.
	 * @generated
	 */
	Broadcast createBroadcast();

	/**
	 * Returns a new object of class '<em>Wrapper String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wrapper String</em>'.
	 * @generated
	 */
	WrapperString createWrapperString();

	WrapperString createWrapperString(String value);

	/**
	 * Returns a new object of class '<em>Wrapper Xml</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wrapper Xml</em>'.
	 * @generated
	 */
	WrapperXml createWrapperXml();

	/**
	 * Returns a new object of class '<em>Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vertex</em>'.
	 * @generated
	 */
	Vertex createVertex();
	
	Vertex createVertex(Instance instance);
	
	Vertex createVertex(Port port);

	/**
	 * Creates a new broadcast with the given number of outputs and
	 * type. Type is copied.
	 * 
	 * @param numOutput
	 *            number of outputs
	 * @param type
	 *            type of this broadcast
	 */
	Broadcast createBroadcast(int numOutputs, Type type);

	/**
	 * Returns a new object of class '<em>Connection</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection</em>'.
	 * @generated
	 */
	Connection createConnection();

	/**
	 * Creates a connection from source port to target port with the given
	 * attributes.
	 * 
	 * @param source
	 *            source port
	 * @param target
	 *            target port
	 */
	Connection createConnection(Port source, Port target);

	/**
	 * Creates a connection from source port to target port with the given size.
	 * This will create a connection with the {@link #BUFFER_SIZE} attribute set
	 * to size.
	 * 
	 * @param source
	 *            source port
	 * @param target
	 *            target port
	 * @param size
	 *            the size of this FIFO
	 */
	Connection createConnection(Port source, Port target, int size);

	/**
	 * Creates a connection from source port to target port with the given
	 * attributes. Attributes are copied.
	 * 
	 * @param source
	 *            source port
	 * @param target
	 *            target port
	 * @param attributes
	 *            attributes to associate with the new connection
	 */
	Connection createConnection(Port source, Port target,
			List<Attribute> attributes);
	
	/**
	 * Returns a new object of class '<em>Instance</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Instance</em>'.
	 * @generated
	 */
	Instance createInstance();

	Instance createInstance(String id, EObject contents);

	/**
	 * Returns a new object of class '<em>Network</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Network</em>'.
	 * @generated
	 */
	Network createNetwork();

	/**
	 * Creates a network defined in the given file.
	 * 
	 * @param fileName
	 * @return a new network
	 */
	Network createNetwork(String fileName);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DfPackage getDfPackage();

} // DfFactory
