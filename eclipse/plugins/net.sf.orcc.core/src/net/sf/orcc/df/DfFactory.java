/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df;

import java.util.Collection;
import java.util.List;

import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.Attribute;

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
	 * Returns a new object of class '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action</em>'.
	 * @generated
	 */
	Action createAction();

	/**
	 * Creates a new action.
	 * 
	 * @param tag
	 *            action tag name
	 * @param inputPattern
	 *            input pattern
	 * @param outputPattern
	 *            output pattern
	 * @param peekedPattern
	 *            peeked pattern
	 * @param scheduler
	 *            procedure that computes scheduling information
	 * @param body
	 *            procedure that holds the body of the action
	 */
	Action createAction(String tag, Pattern inputPattern,
			Pattern outputPattern, Pattern peekedPattern, Procedure scheduler,
			Procedure body);
	
	/**
	 * Creates a new action with empty patterns, the given tag, scheduler and
	 * body.
	 * 
	 * @param tag
	 *            action tag
	 * @param scheduler
	 *            procedure that computes scheduling information
	 * @param body
	 *            procedure that holds the body of the action
	 */
	Action createAction(String tag, Procedure scheduler, Procedure body);

	/**
	 * Creates a new action.
	 * 
	 * @param tag
	 *            action tag name
	 * @param inputPattern
	 *            input pattern
	 * @param outputPattern
	 *            output pattern
	 * @param peekedPattern
	 *            peeked pattern
	 * @param scheduler
	 *            procedure that computes scheduling information
	 * @param body
	 *            procedure that holds the body of the action
	 */
	Action createAction(Tag tagName, Pattern inputPattern,
			Pattern outputPattern, Pattern peekedPattern, Procedure scheduler,
			Procedure body);

	/**
	 * Returns a new object of class '<em>Actor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Actor</em>'.
	 * @generated
	 */
	Actor createActor();

	/**
	 * Returns a new object of class '<em>Argument</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Argument</em>'.
	 * @generated
	 */
	Argument createArgument();

	Argument createArgument(Var variable, Expression value);

	/**
	 * Returns a new object of class '<em>Broadcast</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Broadcast</em>'.
	 * @generated
	 */
	Broadcast createBroadcast();

	/**
	 * Creates a new broadcast with the given number of outputs and type. Type
	 * is copied.
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
	 * Creates a connection from source (and source port) to target (and target
	 * port).
	 * 
	 * @param source
	 *            source vertex
	 * @param sourcePort
	 *            source port
	 * @param target
	 *            target vertex
	 * @param targetPort
	 *            target port
	 */
	Connection createConnection(Vertex source, Port sourcePort, Vertex target,
			Port targetPort);

	/**
	 * Creates a connection from source (and source port) to target (and target
	 * port)with the given attributes. Attributes are copied.
	 * 
	 * @param source
	 *            source vertex
	 * @param sourcePort
	 *            source port
	 * @param target
	 *            target vertex
	 * @param targetPort
	 *            target port
	 * @param attributes
	 *            attributes to associate with the new connection
	 */
	Connection createConnection(Vertex source, Port sourcePort, Vertex target,
			Port targetPort, Collection<Attribute> attributes);

	/**
	 * Creates a connection from source (and source port) to target (and target
	 * port) with the given size. This will create a connection with the
	 * {@link #BUFFER_SIZE} attribute set to size.
	 * 
	 * @param source
	 *            source vertex
	 * @param sourcePort
	 *            source port
	 * @param target
	 *            target vertex
	 * @param targetPort
	 *            target port
	 */
	Connection createConnection(Vertex source, Port sourcePort, Vertex target,
			Port targetPort, int size);

	/**
	 * Returns a new object of class '<em>FSM</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>FSM</em>'.
	 * @generated
	 */
	FSM createFSM();

	/**
	 * Returns a new object of class '<em>Instance</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Instance</em>'.
	 * @generated
	 */
	Instance createInstance();

	Instance createInstance(String id, EObject entity);

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
	 * Returns a new object of class '<em>Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pattern</em>'.
	 * @generated
	 */
	Pattern createPattern();

	/**
	 * Returns a new object of class '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Port</em>'.
	 * @generated
	 */
	Port createPort();

	Port createPort(Port port);

	Port createPort(Type type, String name);

	Port createPort(Type type, String name, boolean native_);

	/**
	 * Returns a new object of class '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State</em>'.
	 * @generated
	 */
	State createState();

	/**
	 * Creates a state with the given name.
	 * 
	 * @param name
	 *            name of the state
	 * @return a state with the given name
	 */
	State createState(String name);

	/**
	 * Returns a new object of class '<em>Tag</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tag</em>'.
	 * @generated
	 */
	Tag createTag();

	Tag createTag(List<String> identifiers);

	Tag createTag(String tagName);

	Tag createTag(Tag tag);

	/**
	 * Returns a new object of class '<em>Transition</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition</em>'.
	 * @generated
	 */
	Transition createTransition();

	Transition createTransition(State source, Action action, State target);

	/**
	 * Returns a new object of class '<em>Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unit</em>'.
	 * @generated
	 */
	Unit createUnit();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DfPackage getDfPackage();

} // DfFactory
