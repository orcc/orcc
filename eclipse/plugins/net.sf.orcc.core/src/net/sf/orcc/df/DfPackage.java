/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df;

import net.sf.orcc.ir.IrPackage;

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
 * @see net.sf.orcc.df.DfFactory
 * @model kind="package"
 * @generated
 */
public interface DfPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "df";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///net/sf/orcc/df.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.df";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DfPackage eINSTANCE = net.sf.orcc.df.impl.DfPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.NetworkImpl <em>Network</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.NetworkImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getNetwork()
	 * @generated
	 */
	int NETWORK = 0;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__FILE_NAME = IrPackage.ENTITY__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__LINE_NUMBER = IrPackage.ENTITY__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__NAME = IrPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__INPUTS = IrPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mo C</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__MO_C = IrPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__OUTPUTS = IrPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__PARAMETERS = IrPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__VARIABLES = IrPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__CONNECTIONS = IrPackage.ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__INSTANCES = IrPackage.ENTITY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__VERTICES = IrPackage.ENTITY_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Network</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_FEATURE_COUNT = IrPackage.ENTITY_FEATURE_COUNT + 8;


	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.AttributeImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.ConnectionImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ATTRIBUTES = 0;

	/**
	 * The feature id for the '<em><b>Fifo Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__FIFO_ID = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET = 3;

	/**
	 * The feature id for the '<em><b>Source Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE_PORT = 4;

	/**
	 * The feature id for the '<em><b>Target Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET_PORT = 5;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.InstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.InstanceImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ATTRIBUTES = 0;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__CONTENTS = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ID = 2;

	/**
	 * The number of structural features of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.BroadcastImpl <em>Broadcast</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.BroadcastImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getBroadcast()
	 * @generated
	 */
	int BROADCAST = 4;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST__INPUTS = 0;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST__OUTPUTS = 1;

	/**
	 * The number of structural features of the '<em>Broadcast</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.WrapperStringImpl <em>Wrapper String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.WrapperStringImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getWrapperString()
	 * @generated
	 */
	int WRAPPER_STRING = 5;

	/**
	 * The feature id for the '<em><b>String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRAPPER_STRING__STRING = 0;

	/**
	 * The number of structural features of the '<em>Wrapper String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRAPPER_STRING_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.WrapperXmlImpl <em>Wrapper Xml</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.WrapperXmlImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getWrapperXml()
	 * @generated
	 */
	int WRAPPER_XML = 6;

	/**
	 * The feature id for the '<em><b>Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRAPPER_XML__XML = 0;

	/**
	 * The number of structural features of the '<em>Wrapper Xml</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRAPPER_XML_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.VertexImpl <em>Vertex</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.VertexImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getVertex()
	 * @generated
	 */
	int VERTEX = 7;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__CONTENTS = 0;

	/**
	 * The feature id for the '<em><b>Predecessors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__PREDECESSORS = 1;

	/**
	 * The feature id for the '<em><b>Successors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__SUCCESSORS = 2;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__INCOMING_EDGES = 3;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__OUTGOING_EDGES = 4;

	/**
	 * The number of structural features of the '<em>Vertex</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX_FEATURE_COUNT = 5;


	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.ActionImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 8;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__BODY = 0;

	/**
	 * The feature id for the '<em><b>Input Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__INPUT_PATTERN = 1;

	/**
	 * The feature id for the '<em><b>Output Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__OUTPUT_PATTERN = 2;

	/**
	 * The feature id for the '<em><b>Peek Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PEEK_PATTERN = 3;

	/**
	 * The feature id for the '<em><b>Scheduler</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__SCHEDULER = 4;

	/**
	 * The feature id for the '<em><b>Tag</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__TAG = 5;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.ActorImpl <em>Actor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.ActorImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getActor()
	 * @generated
	 */
	int ACTOR = 9;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__FILE_NAME = IrPackage.ENTITY__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__LINE_NUMBER = IrPackage.ENTITY__LINE_NUMBER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__NAME = IrPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ACTIONS = IrPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Actions Outside Fsm</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ACTIONS_OUTSIDE_FSM = IrPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fsm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__FSM = IrPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Initializes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__INITIALIZES = IrPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__INPUTS = IrPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Mo C</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__MO_C = IrPackage.ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__OUTPUTS = IrPackage.ENTITY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__PARAMETERS = IrPackage.ENTITY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Procs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__PROCS = IrPackage.ENTITY_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>State Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__STATE_VARS = IrPackage.ENTITY_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Native</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__NATIVE = IrPackage.ENTITY_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Actor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR_FEATURE_COUNT = IrPackage.ENTITY_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.FSMImpl <em>FSM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.FSMImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getFSM()
	 * @generated
	 */
	int FSM = 10;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__INITIAL_STATE = 0;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__STATES = 1;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__TRANSITIONS = 2;

	/**
	 * The number of structural features of the '<em>FSM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PatternImpl <em>Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.PatternImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPattern()
	 * @generated
	 */
	int PATTERN = 11;

	/**
	 * The feature id for the '<em><b>Num Tokens Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__NUM_TOKENS_MAP = 0;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__PORTS = 1;

	/**
	 * The feature id for the '<em><b>Port To Var Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__PORT_TO_VAR_MAP = 2;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__VARIABLES = 3;

	/**
	 * The feature id for the '<em><b>Var To Port Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__VAR_TO_PORT_MAP = 4;

	/**
	 * The number of structural features of the '<em>Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.PortImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Num Tokens Consumed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NUM_TOKENS_CONSUMED = 1;

	/**
	 * The feature id for the '<em><b>Num Tokens Produced</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NUM_TOKENS_PRODUCED = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Native</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NATIVE = 4;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.StateImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getState()
	 * @generated
	 */
	int STATE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = 0;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.TagImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 14;

	/**
	 * The feature id for the '<em><b>Identifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__IDENTIFIERS = 0;

	/**
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.TransitionImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 15;

	/**
	 * The feature id for the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ACTION = 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__STATE = 1;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.TransitionsImpl <em>Transitions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.TransitionsImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getTransitions()
	 * @generated
	 */
	int TRANSITIONS = 16;

	/**
	 * The feature id for the '<em><b>List</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITIONS__LIST = 0;

	/**
	 * The feature id for the '<em><b>Source State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITIONS__SOURCE_STATE = 1;

	/**
	 * The number of structural features of the '<em>Transitions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITIONS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl <em>Port To EInteger Object Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToEIntegerObjectMapEntry()
	 * @generated
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY = 17;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Port To EInteger Object Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PortToVarMapEntryImpl <em>Port To Var Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.PortToVarMapEntryImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToVarMapEntry()
	 * @generated
	 */
	int PORT_TO_VAR_MAP_ENTRY = 18;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_VAR_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_VAR_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Port To Var Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_VAR_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.VarToPortMapEntryImpl <em>Var To Port Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.VarToPortMapEntryImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getVarToPortMapEntry()
	 * @generated
	 */
	int VAR_TO_PORT_MAP_ENTRY = 19;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TO_PORT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TO_PORT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Var To Port Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TO_PORT_MAP_ENTRY_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Network <em>Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Network</em>'.
	 * @see net.sf.orcc.df.Network
	 * @generated
	 */
	EClass getNetwork();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Network#getInputs <em>Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inputs</em>'.
	 * @see net.sf.orcc.df.Network#getInputs()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Inputs();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Network#getMoC <em>Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mo C</em>'.
	 * @see net.sf.orcc.df.Network#getMoC()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_MoC();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Network#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see net.sf.orcc.df.Network#getOutputs()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Outputs();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Network#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see net.sf.orcc.df.Network#getParameters()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Network#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see net.sf.orcc.df.Network#getVariables()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Variables();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Network#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connections</em>'.
	 * @see net.sf.orcc.df.Network#getConnections()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Connections();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Network#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Instances</em>'.
	 * @see net.sf.orcc.df.Network#getInstances()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Instances();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Network#getVertices <em>Vertices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vertices</em>'.
	 * @see net.sf.orcc.df.Network#getVertices()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Vertices();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see net.sf.orcc.df.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Attribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.Attribute#getName()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Name();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see net.sf.orcc.df.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see net.sf.orcc.df.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Connection#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see net.sf.orcc.df.Connection#getAttributes()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Attributes();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Connection#getFifoId <em>Fifo Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fifo Id</em>'.
	 * @see net.sf.orcc.df.Connection#getFifoId()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_FifoId();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see net.sf.orcc.df.Connection#getSource()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Connection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see net.sf.orcc.df.Connection#getTarget()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Target();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Connection#getSourcePort <em>Source Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Port</em>'.
	 * @see net.sf.orcc.df.Connection#getSourcePort()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_SourcePort();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Connection#getTargetPort <em>Target Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Port</em>'.
	 * @see net.sf.orcc.df.Connection#getTargetPort()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_TargetPort();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see net.sf.orcc.df.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Instance#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see net.sf.orcc.df.Instance#getAttributes()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Attributes();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Instance#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contents</em>'.
	 * @see net.sf.orcc.df.Instance#getContents()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Contents();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Instance#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see net.sf.orcc.df.Instance#getId()
	 * @see #getInstance()
	 * @generated
	 */
	EAttribute getInstance_Id();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Broadcast <em>Broadcast</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Broadcast</em>'.
	 * @see net.sf.orcc.df.Broadcast
	 * @generated
	 */
	EClass getBroadcast();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Broadcast#getInputs <em>Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inputs</em>'.
	 * @see net.sf.orcc.df.Broadcast#getInputs()
	 * @see #getBroadcast()
	 * @generated
	 */
	EReference getBroadcast_Inputs();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Broadcast#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see net.sf.orcc.df.Broadcast#getOutputs()
	 * @see #getBroadcast()
	 * @generated
	 */
	EReference getBroadcast_Outputs();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.WrapperString <em>Wrapper String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wrapper String</em>'.
	 * @see net.sf.orcc.df.WrapperString
	 * @generated
	 */
	EClass getWrapperString();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.WrapperString#getString <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String</em>'.
	 * @see net.sf.orcc.df.WrapperString#getString()
	 * @see #getWrapperString()
	 * @generated
	 */
	EAttribute getWrapperString_String();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.WrapperXml <em>Wrapper Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wrapper Xml</em>'.
	 * @see net.sf.orcc.df.WrapperXml
	 * @generated
	 */
	EClass getWrapperXml();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.WrapperXml#getXml <em>Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml</em>'.
	 * @see net.sf.orcc.df.WrapperXml#getXml()
	 * @see #getWrapperXml()
	 * @generated
	 */
	EAttribute getWrapperXml_Xml();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Vertex <em>Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vertex</em>'.
	 * @see net.sf.orcc.df.Vertex
	 * @generated
	 */
	EClass getVertex();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Vertex#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contents</em>'.
	 * @see net.sf.orcc.df.Vertex#getContents()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Contents();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Vertex#getPredecessors <em>Predecessors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Predecessors</em>'.
	 * @see net.sf.orcc.df.Vertex#getPredecessors()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Predecessors();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Vertex#getSuccessors <em>Successors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Successors</em>'.
	 * @see net.sf.orcc.df.Vertex#getSuccessors()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Successors();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Vertex#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Edges</em>'.
	 * @see net.sf.orcc.df.Vertex#getIncomingEdges()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_IncomingEdges();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Vertex#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Edges</em>'.
	 * @see net.sf.orcc.df.Vertex#getOutgoingEdges()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_OutgoingEdges();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see net.sf.orcc.df.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see net.sf.orcc.df.Action#getBody()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Body();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getInputPattern <em>Input Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Input Pattern</em>'.
	 * @see net.sf.orcc.df.Action#getInputPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_InputPattern();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getOutputPattern <em>Output Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Output Pattern</em>'.
	 * @see net.sf.orcc.df.Action#getOutputPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_OutputPattern();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getPeekPattern <em>Peek Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Peek Pattern</em>'.
	 * @see net.sf.orcc.df.Action#getPeekPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_PeekPattern();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getScheduler <em>Scheduler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scheduler</em>'.
	 * @see net.sf.orcc.df.Action#getScheduler()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Scheduler();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getTag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tag</em>'.
	 * @see net.sf.orcc.df.Action#getTag()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Tag();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Actor <em>Actor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Actor</em>'.
	 * @see net.sf.orcc.df.Actor
	 * @generated
	 */
	EClass getActor();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Actor#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see net.sf.orcc.df.Actor#getActions()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Actions();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Actor#getActionsOutsideFsm <em>Actions Outside Fsm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Actions Outside Fsm</em>'.
	 * @see net.sf.orcc.df.Actor#getActionsOutsideFsm()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_ActionsOutsideFsm();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Actor#getFsm <em>Fsm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fsm</em>'.
	 * @see net.sf.orcc.df.Actor#getFsm()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Fsm();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Actor#getInitializes <em>Initializes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Initializes</em>'.
	 * @see net.sf.orcc.df.Actor#getInitializes()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Initializes();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Actor#getInputs <em>Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inputs</em>'.
	 * @see net.sf.orcc.df.Actor#getInputs()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Inputs();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Actor#getMoC <em>Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mo C</em>'.
	 * @see net.sf.orcc.df.Actor#getMoC()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_MoC();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Actor#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see net.sf.orcc.df.Actor#getOutputs()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Outputs();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Actor#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see net.sf.orcc.df.Actor#getParameters()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Actor#getProcs <em>Procs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Procs</em>'.
	 * @see net.sf.orcc.df.Actor#getProcs()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Procs();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Actor#getStateVars <em>State Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State Vars</em>'.
	 * @see net.sf.orcc.df.Actor#getStateVars()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_StateVars();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Actor#isNative <em>Native</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Native</em>'.
	 * @see net.sf.orcc.df.Actor#isNative()
	 * @see #getActor()
	 * @generated
	 */
	EAttribute getActor_Native();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.FSM <em>FSM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FSM</em>'.
	 * @see net.sf.orcc.df.FSM
	 * @generated
	 */
	EClass getFSM();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.FSM#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initial State</em>'.
	 * @see net.sf.orcc.df.FSM#getInitialState()
	 * @see #getFSM()
	 * @generated
	 */
	EReference getFSM_InitialState();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.FSM#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see net.sf.orcc.df.FSM#getStates()
	 * @see #getFSM()
	 * @generated
	 */
	EReference getFSM_States();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.FSM#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transitions</em>'.
	 * @see net.sf.orcc.df.FSM#getTransitions()
	 * @see #getFSM()
	 * @generated
	 */
	EReference getFSM_Transitions();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Pattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pattern</em>'.
	 * @see net.sf.orcc.df.Pattern
	 * @generated
	 */
	EClass getPattern();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.df.Pattern#getNumTokensMap <em>Num Tokens Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Num Tokens Map</em>'.
	 * @see net.sf.orcc.df.Pattern#getNumTokensMap()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_NumTokensMap();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Pattern#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ports</em>'.
	 * @see net.sf.orcc.df.Pattern#getPorts()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_Ports();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.df.Pattern#getPortToVarMap <em>Port To Var Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Port To Var Map</em>'.
	 * @see net.sf.orcc.df.Pattern#getPortToVarMap()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_PortToVarMap();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Pattern#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see net.sf.orcc.df.Pattern#getVariables()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_Variables();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.df.Pattern#getVarToPortMap <em>Var To Port Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Var To Port Map</em>'.
	 * @see net.sf.orcc.df.Pattern#getVarToPortMap()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_VarToPortMap();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see net.sf.orcc.df.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Port#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.Port#getName()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Name();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Port#getNumTokensConsumed <em>Num Tokens Consumed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num Tokens Consumed</em>'.
	 * @see net.sf.orcc.df.Port#getNumTokensConsumed()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_NumTokensConsumed();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Port#getNumTokensProduced <em>Num Tokens Produced</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num Tokens Produced</em>'.
	 * @see net.sf.orcc.df.Port#getNumTokensProduced()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_NumTokensProduced();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Port#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see net.sf.orcc.df.Port#getType()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Type();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Port#isNative <em>Native</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Native</em>'.
	 * @see net.sf.orcc.df.Port#isNative()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Native();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see net.sf.orcc.df.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.State#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.State#getName()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Name();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see net.sf.orcc.df.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the attribute list '{@link net.sf.orcc.df.Tag#getIdentifiers <em>Identifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Identifiers</em>'.
	 * @see net.sf.orcc.df.Tag#getIdentifiers()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_Identifiers();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see net.sf.orcc.df.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Transition#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Action</em>'.
	 * @see net.sf.orcc.df.Transition#getAction()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Action();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Transition#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>State</em>'.
	 * @see net.sf.orcc.df.Transition#getState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_State();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Transitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transitions</em>'.
	 * @see net.sf.orcc.df.Transitions
	 * @generated
	 */
	EClass getTransitions();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.df.Transitions#getList <em>List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>List</em>'.
	 * @see net.sf.orcc.df.Transitions#getList()
	 * @see #getTransitions()
	 * @generated
	 */
	EReference getTransitions_List();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Transitions#getSourceState <em>Source State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source State</em>'.
	 * @see net.sf.orcc.df.Transitions#getSourceState()
	 * @see #getTransitions()
	 * @generated
	 */
	EReference getTransitions_SourceState();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Port To EInteger Object Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port To EInteger Object Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="net.sf.orcc.df.Port"
	 *        valueDataType="org.eclipse.emf.ecore.EIntegerObject"
	 * @generated
	 */
	EClass getPortToEIntegerObjectMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToEIntegerObjectMapEntry()
	 * @generated
	 */
	EReference getPortToEIntegerObjectMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToEIntegerObjectMapEntry()
	 * @generated
	 */
	EAttribute getPortToEIntegerObjectMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Port To Var Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port To Var Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="net.sf.orcc.df.Port"
	 *        valueType="net.sf.orcc.ir.Var"
	 * @generated
	 */
	EClass getPortToVarMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToVarMapEntry()
	 * @generated
	 */
	EReference getPortToVarMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToVarMapEntry()
	 * @generated
	 */
	EReference getPortToVarMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Var To Port Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var To Port Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="net.sf.orcc.ir.Var"
	 *        valueType="net.sf.orcc.df.Port"
	 * @generated
	 */
	EClass getVarToPortMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getVarToPortMapEntry()
	 * @generated
	 */
	EReference getVarToPortMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getVarToPortMapEntry()
	 * @generated
	 */
	EReference getVarToPortMapEntry_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DfFactory getDfFactory();

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
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.NetworkImpl <em>Network</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.NetworkImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getNetwork()
		 * @generated
		 */
		EClass NETWORK = eINSTANCE.getNetwork();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__INPUTS = eINSTANCE.getNetwork_Inputs();

		/**
		 * The meta object literal for the '<em><b>Mo C</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__MO_C = eINSTANCE.getNetwork_MoC();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__OUTPUTS = eINSTANCE.getNetwork_Outputs();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__PARAMETERS = eINSTANCE.getNetwork_Parameters();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__VARIABLES = eINSTANCE.getNetwork_Variables();

		/**
		 * The meta object literal for the '<em><b>Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__CONNECTIONS = eINSTANCE.getNetwork_Connections();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__INSTANCES = eINSTANCE.getNetwork_Instances();

		/**
		 * The meta object literal for the '<em><b>Vertices</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__VERTICES = eINSTANCE.getNetwork_Vertices();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.AttributeImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.ConnectionImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__ATTRIBUTES = eINSTANCE.getConnection_Attributes();

		/**
		 * The meta object literal for the '<em><b>Fifo Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__FIFO_ID = eINSTANCE.getConnection_FifoId();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SOURCE = eINSTANCE.getConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__TARGET = eINSTANCE.getConnection_Target();

		/**
		 * The meta object literal for the '<em><b>Source Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SOURCE_PORT = eINSTANCE.getConnection_SourcePort();

		/**
		 * The meta object literal for the '<em><b>Target Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__TARGET_PORT = eINSTANCE.getConnection_TargetPort();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.InstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.InstanceImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__ATTRIBUTES = eINSTANCE.getInstance_Attributes();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__CONTENTS = eINSTANCE.getInstance_Contents();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTANCE__ID = eINSTANCE.getInstance_Id();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.BroadcastImpl <em>Broadcast</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.BroadcastImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getBroadcast()
		 * @generated
		 */
		EClass BROADCAST = eINSTANCE.getBroadcast();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BROADCAST__INPUTS = eINSTANCE.getBroadcast_Inputs();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BROADCAST__OUTPUTS = eINSTANCE.getBroadcast_Outputs();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.WrapperStringImpl <em>Wrapper String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.WrapperStringImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getWrapperString()
		 * @generated
		 */
		EClass WRAPPER_STRING = eINSTANCE.getWrapperString();

		/**
		 * The meta object literal for the '<em><b>String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WRAPPER_STRING__STRING = eINSTANCE.getWrapperString_String();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.WrapperXmlImpl <em>Wrapper Xml</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.WrapperXmlImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getWrapperXml()
		 * @generated
		 */
		EClass WRAPPER_XML = eINSTANCE.getWrapperXml();

		/**
		 * The meta object literal for the '<em><b>Xml</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WRAPPER_XML__XML = eINSTANCE.getWrapperXml_Xml();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.VertexImpl <em>Vertex</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.VertexImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getVertex()
		 * @generated
		 */
		EClass VERTEX = eINSTANCE.getVertex();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__CONTENTS = eINSTANCE.getVertex_Contents();

		/**
		 * The meta object literal for the '<em><b>Predecessors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__PREDECESSORS = eINSTANCE.getVertex_Predecessors();

		/**
		 * The meta object literal for the '<em><b>Successors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__SUCCESSORS = eINSTANCE.getVertex_Successors();

		/**
		 * The meta object literal for the '<em><b>Incoming Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__INCOMING_EDGES = eINSTANCE.getVertex_IncomingEdges();

		/**
		 * The meta object literal for the '<em><b>Outgoing Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__OUTGOING_EDGES = eINSTANCE.getVertex_OutgoingEdges();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.ActionImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__BODY = eINSTANCE.getAction_Body();

		/**
		 * The meta object literal for the '<em><b>Input Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__INPUT_PATTERN = eINSTANCE.getAction_InputPattern();

		/**
		 * The meta object literal for the '<em><b>Output Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__OUTPUT_PATTERN = eINSTANCE.getAction_OutputPattern();

		/**
		 * The meta object literal for the '<em><b>Peek Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__PEEK_PATTERN = eINSTANCE.getAction_PeekPattern();

		/**
		 * The meta object literal for the '<em><b>Scheduler</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__SCHEDULER = eINSTANCE.getAction_Scheduler();

		/**
		 * The meta object literal for the '<em><b>Tag</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__TAG = eINSTANCE.getAction_Tag();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.ActorImpl <em>Actor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.ActorImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getActor()
		 * @generated
		 */
		EClass ACTOR = eINSTANCE.getActor();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__ACTIONS = eINSTANCE.getActor_Actions();

		/**
		 * The meta object literal for the '<em><b>Actions Outside Fsm</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__ACTIONS_OUTSIDE_FSM = eINSTANCE.getActor_ActionsOutsideFsm();

		/**
		 * The meta object literal for the '<em><b>Fsm</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__FSM = eINSTANCE.getActor_Fsm();

		/**
		 * The meta object literal for the '<em><b>Initializes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__INITIALIZES = eINSTANCE.getActor_Initializes();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__INPUTS = eINSTANCE.getActor_Inputs();

		/**
		 * The meta object literal for the '<em><b>Mo C</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__MO_C = eINSTANCE.getActor_MoC();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__OUTPUTS = eINSTANCE.getActor_Outputs();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__PARAMETERS = eINSTANCE.getActor_Parameters();

		/**
		 * The meta object literal for the '<em><b>Procs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__PROCS = eINSTANCE.getActor_Procs();

		/**
		 * The meta object literal for the '<em><b>State Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__STATE_VARS = eINSTANCE.getActor_StateVars();

		/**
		 * The meta object literal for the '<em><b>Native</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTOR__NATIVE = eINSTANCE.getActor_Native();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.FSMImpl <em>FSM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.FSMImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getFSM()
		 * @generated
		 */
		EClass FSM = eINSTANCE.getFSM();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FSM__INITIAL_STATE = eINSTANCE.getFSM_InitialState();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FSM__STATES = eINSTANCE.getFSM_States();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FSM__TRANSITIONS = eINSTANCE.getFSM_Transitions();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.PatternImpl <em>Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.PatternImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPattern()
		 * @generated
		 */
		EClass PATTERN = eINSTANCE.getPattern();

		/**
		 * The meta object literal for the '<em><b>Num Tokens Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__NUM_TOKENS_MAP = eINSTANCE.getPattern_NumTokensMap();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__PORTS = eINSTANCE.getPattern_Ports();

		/**
		 * The meta object literal for the '<em><b>Port To Var Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__PORT_TO_VAR_MAP = eINSTANCE.getPattern_PortToVarMap();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__VARIABLES = eINSTANCE.getPattern_Variables();

		/**
		 * The meta object literal for the '<em><b>Var To Port Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__VAR_TO_PORT_MAP = eINSTANCE.getPattern_VarToPortMap();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.PortImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPort()
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
		 * The meta object literal for the '<em><b>Num Tokens Consumed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NUM_TOKENS_CONSUMED = eINSTANCE.getPort_NumTokensConsumed();

		/**
		 * The meta object literal for the '<em><b>Num Tokens Produced</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NUM_TOKENS_PRODUCED = eINSTANCE.getPort_NumTokensProduced();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__TYPE = eINSTANCE.getPort_Type();

		/**
		 * The meta object literal for the '<em><b>Native</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NATIVE = eINSTANCE.getPort_Native();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.StateImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__NAME = eINSTANCE.getState_Name();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.TagImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Identifiers</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__IDENTIFIERS = eINSTANCE.getTag_Identifiers();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.TransitionImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__ACTION = eINSTANCE.getTransition_Action();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__STATE = eINSTANCE.getTransition_State();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.TransitionsImpl <em>Transitions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.TransitionsImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getTransitions()
		 * @generated
		 */
		EClass TRANSITIONS = eINSTANCE.getTransitions();

		/**
		 * The meta object literal for the '<em><b>List</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITIONS__LIST = eINSTANCE.getTransitions_List();

		/**
		 * The meta object literal for the '<em><b>Source State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITIONS__SOURCE_STATE = eINSTANCE.getTransitions_SourceState();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl <em>Port To EInteger Object Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToEIntegerObjectMapEntry()
		 * @generated
		 */
		EClass PORT_TO_EINTEGER_OBJECT_MAP_ENTRY = eINSTANCE.getPortToEIntegerObjectMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__KEY = eINSTANCE.getPortToEIntegerObjectMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__VALUE = eINSTANCE.getPortToEIntegerObjectMapEntry_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.PortToVarMapEntryImpl <em>Port To Var Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.PortToVarMapEntryImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToVarMapEntry()
		 * @generated
		 */
		EClass PORT_TO_VAR_MAP_ENTRY = eINSTANCE.getPortToVarMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_TO_VAR_MAP_ENTRY__KEY = eINSTANCE.getPortToVarMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_TO_VAR_MAP_ENTRY__VALUE = eINSTANCE.getPortToVarMapEntry_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.VarToPortMapEntryImpl <em>Var To Port Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.VarToPortMapEntryImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getVarToPortMapEntry()
		 * @generated
		 */
		EClass VAR_TO_PORT_MAP_ENTRY = eINSTANCE.getVarToPortMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_TO_PORT_MAP_ENTRY__KEY = eINSTANCE.getVarToPortMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_TO_PORT_MAP_ENTRY__VALUE = eINSTANCE.getVarToPortMapEntry_Value();

	}

} //DfPackage
