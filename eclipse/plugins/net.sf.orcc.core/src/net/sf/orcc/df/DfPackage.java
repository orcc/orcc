/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df;

import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.util.UtilPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.orcc.df.DfFactory
 * @model kind="package"
 * @generated
 */
public interface DfPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "df";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/model/2011/Df";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.df";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	DfPackage eINSTANCE = net.sf.orcc.df.impl.DfPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.NetworkImpl <em>Network</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.NetworkImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getNetwork()
	 * @generated
	 */
	int NETWORK = 4;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.ConnectionImpl
	 * <em>Connection</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.df.impl.ConnectionImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 6;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.InstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.InstanceImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 2;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.BroadcastImpl <em>Broadcast</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.BroadcastImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getBroadcast()
	 * @generated
	 */
	int BROADCAST = 5;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.ActionImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 7;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.ActorImpl <em>Actor</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.ActorImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getActor()
	 * @generated
	 */
	int ACTOR = 3;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.FSMImpl <em>FSM</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.FSMImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getFSM()
	 * @generated
	 */
	int FSM = 8;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PatternImpl <em>Pattern</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.PatternImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPattern()
	 * @generated
	 */
	int PATTERN = 9;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.PortImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 1;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.StateImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getState()
	 * @generated
	 */
	int STATE = 10;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.TagImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 11;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.TransitionImpl
	 * <em>Transition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see net.sf.orcc.df.impl.TransitionImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 12;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl <em>Port To EInteger Object Map Entry</em>}' class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToEIntegerObjectMapEntry()
	 * @generated
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY = 13;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.PortToVarMapEntryImpl <em>Port To Var Map Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see net.sf.orcc.df.impl.PortToVarMapEntryImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToVarMapEntry()
	 * @generated
	 */
	int PORT_TO_VAR_MAP_ENTRY = 14;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.VarToPortMapEntryImpl <em>Var To Port Map Entry</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see net.sf.orcc.df.impl.VarToPortMapEntryImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getVarToPortMapEntry()
	 * @generated
	 */
	int VAR_TO_PORT_MAP_ENTRY = 15;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.ArgumentImpl <em>Argument</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.ArgumentImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getArgument()
	 * @generated
	 */
	int ARGUMENT = 16;

	/**
	 * The meta object id for the '{@link net.sf.orcc.df.impl.UnitImpl <em>Unit</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see net.sf.orcc.df.impl.UnitImpl
	 * @see net.sf.orcc.df.impl.DfPackageImpl#getUnit()
	 * @generated
	 */
	int UNIT = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__CONSTANTS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNIT__FILE_NAME = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNIT__LINE_NUMBER = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNIT__NAME = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Procedures</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__PROCEDURES = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Unit</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNIT_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__ATTRIBUTES = GraphPackage.VERTEX__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT__INCOMING = GraphPackage.VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__LABEL = GraphPackage.VERTEX__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NUMBER = GraphPackage.VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT__OUTGOING = GraphPackage.VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = GraphPackage.VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Num Tokens Consumed</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NUM_TOKENS_CONSUMED = GraphPackage.VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Num Tokens Produced</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NUM_TOKENS_PRODUCED = GraphPackage.VERTEX_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT__TYPE = GraphPackage.VERTEX_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Port</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = GraphPackage.VERTEX_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ATTRIBUTES = GraphPackage.VERTEX__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INSTANCE__INCOMING = GraphPackage.VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__LABEL = GraphPackage.VERTEX__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__NUMBER = GraphPackage.VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INSTANCE__OUTGOING = GraphPackage.VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ARGUMENTS = GraphPackage.VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INSTANCE__ENTITY = GraphPackage.VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INSTANCE__NAME = GraphPackage.VERTEX_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Instance</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = GraphPackage.VERTEX_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ACTIONS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Actions Outside Fsm</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__ACTIONS_OUTSIDE_FSM = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTOR__FILE_NAME = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Fsm</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTOR__FSM = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Initializes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__INITIALIZES = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__INPUTS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Line Number</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTOR__LINE_NUMBER = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Mo C</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTOR__MO_C = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Native</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTOR__NATIVE = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTOR__NAME = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__OUTPUTS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__PARAMETERS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Procs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__PROCS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>State Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__STATE_VARS = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Template Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTOR__TEMPLATE_DATA = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 14;

	/**
	 * The number of structural features of the '<em>Actor</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTOR_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__ATTRIBUTES = GraphPackage.GRAPH__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NETWORK__INCOMING = GraphPackage.GRAPH__INCOMING;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__LABEL = GraphPackage.GRAPH__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__NUMBER = GraphPackage.GRAPH__NUMBER;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NETWORK__OUTGOING = GraphPackage.GRAPH__OUTGOING;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__EDGES = GraphPackage.GRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__VERTICES = GraphPackage.GRAPH__VERTICES;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__ENTITIES = GraphPackage.GRAPH_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NETWORK__FILE_NAME = GraphPackage.GRAPH_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__INPUTS = GraphPackage.GRAPH_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__INSTANCES = GraphPackage.GRAPH_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Mo C</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NETWORK__MO_C = GraphPackage.GRAPH_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NETWORK__NAME = GraphPackage.GRAPH_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__OUTPUTS = GraphPackage.GRAPH_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__PARAMETERS = GraphPackage.GRAPH_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Template Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__TEMPLATE_DATA = GraphPackage.GRAPH_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK__VARIABLES = GraphPackage.GRAPH_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Network</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NETWORK_FEATURE_COUNT = GraphPackage.GRAPH_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST__ATTRIBUTES = GraphPackage.VERTEX__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BROADCAST__INCOMING = GraphPackage.VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST__LABEL = GraphPackage.VERTEX__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST__NUMBER = GraphPackage.VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BROADCAST__OUTGOING = GraphPackage.VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST__INPUTS = GraphPackage.VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BROADCAST__NAME = GraphPackage.VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BROADCAST__OUTPUTS = GraphPackage.VERTEX_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Broadcast</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BROADCAST_FEATURE_COUNT = GraphPackage.VERTEX_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ATTRIBUTES = GraphPackage.EDGE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__LABEL = GraphPackage.EDGE__LABEL;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = GraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET = GraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Source Port</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE_PORT = GraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Port</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET_PORT = GraphPackage.EDGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = GraphPackage.EDGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__ATTRIBUTES = UtilPackage.ATTRIBUTABLE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTION__BODY = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Input Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__INPUT_PATTERN = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__OUTPUT_PATTERN = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Peek Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PEEK_PATTERN = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Scheduler</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__SCHEDULER = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Tag</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTION__TAG = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Action</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = UtilPackage.ATTRIBUTABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__ATTRIBUTES = GraphPackage.GRAPH__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__INCOMING = GraphPackage.GRAPH__INCOMING;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__LABEL = GraphPackage.GRAPH__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__NUMBER = GraphPackage.GRAPH__NUMBER;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__OUTGOING = GraphPackage.GRAPH__OUTGOING;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__EDGES = GraphPackage.GRAPH__EDGES;

	/**
	 * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FSM__VERTICES = GraphPackage.GRAPH__VERTICES;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FSM__INITIAL_STATE = GraphPackage.GRAPH_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>FSM</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FSM_FEATURE_COUNT = GraphPackage.GRAPH_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Num Tokens Map</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__NUM_TOKENS_MAP = 0;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__PORTS = 1;

	/**
	 * The feature id for the '<em><b>Port To Var Map</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__PORT_TO_VAR_MAP = 2;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATTERN__VARIABLES = 3;

	/**
	 * The feature id for the '<em><b>Var To Port Map</b></em>' map. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__VAR_TO_PORT_MAP = 4;

	/**
	 * The number of structural features of the '<em>Pattern</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ATTRIBUTES = GraphPackage.VERTEX__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATE__INCOMING = GraphPackage.VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__LABEL = GraphPackage.VERTEX__LABEL;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NUMBER = GraphPackage.VERTEX__NUMBER;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATE__OUTGOING = GraphPackage.VERTEX__OUTGOING;

	/**
	 * The number of structural features of the '<em>State</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = GraphPackage.VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Identifiers</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TAG__IDENTIFIERS = 0;

	/**
	 * The number of structural features of the '<em>Tag</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ATTRIBUTES = GraphPackage.EDGE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__LABEL = GraphPackage.EDGE__LABEL;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE = GraphPackage.EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET = GraphPackage.EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Action</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ACTION = GraphPackage.EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = GraphPackage.EDGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Port To EInteger Object Map Entry</em>' class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_EINTEGER_OBJECT_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_TO_VAR_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PORT_TO_VAR_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Port To Var Map Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_TO_VAR_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_TO_PORT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_TO_PORT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Var To Port Map Entry</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TO_PORT_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__VARIABLE = 1;

	/**
	 * The number of structural features of the '<em>Argument</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Network <em>Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Network</em>'.
	 * @see net.sf.orcc.df.Network
	 * @generated
	 */
	EClass getNetwork();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Network#getMoC <em>Mo C</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mo C</em>'.
	 * @see net.sf.orcc.df.Network#getMoC()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_MoC();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Network#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.Network#getName()
	 * @see #getNetwork()
	 * @generated
	 */
	EAttribute getNetwork_Name();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Network#getVariables <em>Variables</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Variables</em>'.
	 * @see net.sf.orcc.df.Network#getVariables()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Variables();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Network#getInstances <em>Instances</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Instances</em>'.
	 * @see net.sf.orcc.df.Network#getInstances()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Instances();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Network#getFileName <em>File Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see net.sf.orcc.df.Network#getFileName()
	 * @see #getNetwork()
	 * @generated
	 */
	EAttribute getNetwork_FileName();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Network#getEntities <em>Entities</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Entities</em>'.
	 * @see net.sf.orcc.df.Network#getEntities()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Entities();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Network#getInputs <em>Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Inputs</em>'.
	 * @see net.sf.orcc.df.Network#getInputs()
	 * @see #getNetwork()
	 * @generated
	 */
	EReference getNetwork_Inputs();

	/**
	 * Returns the meta object for the reference list '{@link net.sf.orcc.df.Network#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outputs</em>'.
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
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Network#getTemplateData <em>Template Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Data</em>'.
	 * @see net.sf.orcc.df.Network#getTemplateData()
	 * @see #getNetwork()
	 * @generated
	 */
	EAttribute getNetwork_TemplateData();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see net.sf.orcc.df.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Connection#getSourcePort <em>Source Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Port</em>'.
	 * @see net.sf.orcc.df.Connection#getSourcePort()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_SourcePort();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.df.Connection#getTargetPort <em>Target Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Port</em>'.
	 * @see net.sf.orcc.df.Connection#getTargetPort()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_TargetPort();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see net.sf.orcc.df.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Instance#getArguments <em>Arguments</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Arguments</em>'.
	 * @see net.sf.orcc.df.Instance#getArguments()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Arguments();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.df.Instance#getEntity <em>Entity</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see net.sf.orcc.df.Instance#getEntity()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Entity();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Instance#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.Instance#getName()
	 * @see #getInstance()
	 * @generated
	 */
	EAttribute getInstance_Name();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Broadcast <em>Broadcast</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Broadcast#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.Broadcast#getName()
	 * @see #getBroadcast()
	 * @generated
	 */
	EAttribute getBroadcast_Name();

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
	 * Returns the meta object for class '{@link net.sf.orcc.df.Action <em>Action</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see net.sf.orcc.df.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see net.sf.orcc.df.Action#getBody()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Body();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getInputPattern <em>Input Pattern</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Input Pattern</em>'.
	 * @see net.sf.orcc.df.Action#getInputPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_InputPattern();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getOutputPattern <em>Output Pattern</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Output Pattern</em>'.
	 * @see net.sf.orcc.df.Action#getOutputPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_OutputPattern();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.df.Action#getPeekPattern <em>Peek Pattern</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '
	 *         <em>Peek Pattern</em>'.
	 * @see net.sf.orcc.df.Action#getPeekPattern()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_PeekPattern();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.df.Action#getScheduler <em>Scheduler</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Scheduler</em>
	 *         '.
	 * @see net.sf.orcc.df.Action#getScheduler()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Scheduler();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Action#getTag <em>Tag</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tag</em>'.
	 * @see net.sf.orcc.df.Action#getTag()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Tag();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Actor <em>Actor</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Actor</em>'.
	 * @see net.sf.orcc.df.Actor
	 * @generated
	 */
	EClass getActor();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Actor#getActions <em>Actions</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Actions</em>'.
	 * @see net.sf.orcc.df.Actor#getActions()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Actions();

	/**
	 * Returns the meta object for the reference list '
	 * {@link net.sf.orcc.df.Actor#getActionsOutsideFsm
	 * <em>Actions Outside Fsm</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the reference list '
	 *         <em>Actions Outside Fsm</em>'.
	 * @see net.sf.orcc.df.Actor#getActionsOutsideFsm()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_ActionsOutsideFsm();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Actor#getFsm <em>Fsm</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fsm</em>'.
	 * @see net.sf.orcc.df.Actor#getFsm()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Fsm();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Actor#getInitializes <em>Initializes</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Initializes</em>'.
	 * @see net.sf.orcc.df.Actor#getInitializes()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Initializes();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Actor#getMoC <em>Mo C</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mo C</em>'.
	 * @see net.sf.orcc.df.Actor#getMoC()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_MoC();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Actor#getProcs <em>Procs</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Procs</em>'.
	 * @see net.sf.orcc.df.Actor#getProcs()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_Procs();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Actor#getStateVars <em>State Vars</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>State Vars</em>'.
	 * @see net.sf.orcc.df.Actor#getStateVars()
	 * @see #getActor()
	 * @generated
	 */
	EReference getActor_StateVars();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Actor#getTemplateData <em>Template Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Data</em>'.
	 * @see net.sf.orcc.df.Actor#getTemplateData()
	 * @see #getActor()
	 * @generated
	 */
	EAttribute getActor_TemplateData();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Actor#isNative <em>Native</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Native</em>'.
	 * @see net.sf.orcc.df.Actor#isNative()
	 * @see #getActor()
	 * @generated
	 */
	EAttribute getActor_Native();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Actor#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.Actor#getName()
	 * @see #getActor()
	 * @generated
	 */
	EAttribute getActor_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Actor#getFileName <em>File Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see net.sf.orcc.df.Actor#getFileName()
	 * @see #getActor()
	 * @generated
	 */
	EAttribute getActor_FileName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Actor#getLineNumber <em>Line Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.df.Actor#getLineNumber()
	 * @see #getActor()
	 * @generated
	 */
	EAttribute getActor_LineNumber();

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
	 * Returns the meta object for class '{@link net.sf.orcc.df.FSM <em>FSM</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>FSM</em>'.
	 * @see net.sf.orcc.df.FSM
	 * @generated
	 */
	EClass getFSM();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.df.FSM#getInitialState <em>Initial State</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Initial State</em>'.
	 * @see net.sf.orcc.df.FSM#getInitialState()
	 * @see #getFSM()
	 * @generated
	 */
	EReference getFSM_InitialState();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Pattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pattern</em>'.
	 * @see net.sf.orcc.df.Pattern
	 * @generated
	 */
	EClass getPattern();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.df.Pattern#getNumTokensMap <em>Num Tokens Map</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Num Tokens Map</em>'.
	 * @see net.sf.orcc.df.Pattern#getNumTokensMap()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_NumTokensMap();

	/**
	 * Returns the meta object for the reference list '
	 * {@link net.sf.orcc.df.Pattern#getPorts <em>Ports</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Ports</em>'.
	 * @see net.sf.orcc.df.Pattern#getPorts()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_Ports();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.df.Pattern#getPortToVarMap <em>Port To Var Map</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Port To Var Map</em>'.
	 * @see net.sf.orcc.df.Pattern#getPortToVarMap()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_PortToVarMap();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Pattern#getVariables <em>Variables</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Variables</em>'.
	 * @see net.sf.orcc.df.Pattern#getVariables()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_Variables();

	/**
	 * Returns the meta object for the map '{@link net.sf.orcc.df.Pattern#getVarToPortMap <em>Var To Port Map</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Var To Port Map</em>'.
	 * @see net.sf.orcc.df.Pattern#getVarToPortMap()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_VarToPortMap();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Port <em>Port</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see net.sf.orcc.df.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Port#getNumTokensConsumed
	 * <em>Num Tokens Consumed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Num Tokens Consumed</em>'.
	 * @see net.sf.orcc.df.Port#getNumTokensConsumed()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_NumTokensConsumed();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Port#getNumTokensProduced
	 * <em>Num Tokens Produced</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Num Tokens Produced</em>'.
	 * @see net.sf.orcc.df.Port#getNumTokensProduced()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_NumTokensProduced();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.df.Port#getType <em>Type</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see net.sf.orcc.df.Port#getType()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Type();

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
	 * Returns the meta object for class '{@link net.sf.orcc.df.State <em>State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see net.sf.orcc.df.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see net.sf.orcc.df.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link net.sf.orcc.df.Tag#getIdentifiers <em>Identifiers</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Identifiers</em>'.
	 * @see net.sf.orcc.df.Tag#getIdentifiers()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_Identifiers();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see net.sf.orcc.df.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.df.Transition#getAction <em>Action</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Action</em>'.
	 * @see net.sf.orcc.df.Transition#getAction()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Action();

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToEIntegerObjectMapEntry()
	 * @generated
	 */
	EReference getPortToEIntegerObjectMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToEIntegerObjectMapEntry()
	 * @generated
	 */
	EAttribute getPortToEIntegerObjectMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Port To Var Map Entry</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Port To Var Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="net.sf.orcc.df.Port"
	 *        valueType="net.sf.orcc.ir.Var"
	 * @generated
	 */
	EClass getPortToVarMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToVarMapEntry()
	 * @generated
	 */
	EReference getPortToVarMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPortToVarMapEntry()
	 * @generated
	 */
	EReference getPortToVarMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Var To Port Map Entry</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Var To Port Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="net.sf.orcc.ir.Var"
	 *        valueType="net.sf.orcc.df.Port"
	 * @generated
	 */
	EClass getVarToPortMapEntry();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getVarToPortMapEntry()
	 * @generated
	 */
	EReference getVarToPortMapEntry_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getVarToPortMapEntry()
	 * @generated
	 */
	EReference getVarToPortMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument</em>'.
	 * @see net.sf.orcc.df.Argument
	 * @generated
	 */
	EClass getArgument();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link net.sf.orcc.df.Argument#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see net.sf.orcc.df.Argument#getValue()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Value();

	/**
	 * Returns the meta object for the reference '
	 * {@link net.sf.orcc.df.Argument#getVariable <em>Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see net.sf.orcc.df.Argument#getVariable()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Variable();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.df.Unit <em>Unit</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit</em>'.
	 * @see net.sf.orcc.df.Unit
	 * @generated
	 */
	EClass getUnit();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Unit#getConstants <em>Constants</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Constants</em>'.
	 * @see net.sf.orcc.df.Unit#getConstants()
	 * @see #getUnit()
	 * @generated
	 */
	EReference getUnit_Constants();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link net.sf.orcc.df.Unit#getProcedures <em>Procedures</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>Procedures</em>'.
	 * @see net.sf.orcc.df.Unit#getProcedures()
	 * @see #getUnit()
	 * @generated
	 */
	EReference getUnit_Procedures();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Unit#getFileName <em>File Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see net.sf.orcc.df.Unit#getFileName()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_FileName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link net.sf.orcc.df.Unit#getLineNumber <em>Line Number</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Line Number</em>'.
	 * @see net.sf.orcc.df.Unit#getLineNumber()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_LineNumber();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.df.Unit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see net.sf.orcc.df.Unit#getName()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_Name();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DfFactory getDfFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link net.sf.orcc.df.impl.NetworkImpl <em>Network</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see net.sf.orcc.df.impl.NetworkImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getNetwork()
		 * @generated
		 */
		EClass NETWORK = eINSTANCE.getNetwork();

		/**
		 * The meta object literal for the '<em><b>Mo C</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__MO_C = eINSTANCE.getNetwork_MoC();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NETWORK__NAME = eINSTANCE.getNetwork_Name();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__VARIABLES = eINSTANCE.getNetwork_Variables();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__INSTANCES = eINSTANCE.getNetwork_Instances();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NETWORK__FILE_NAME = eINSTANCE.getNetwork_FileName();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__ENTITIES = eINSTANCE.getNetwork_Entities();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK__INPUTS = eINSTANCE.getNetwork_Inputs();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' reference list feature.
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
		 * The meta object literal for the '<em><b>Template Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NETWORK__TEMPLATE_DATA = eINSTANCE.getNetwork_TemplateData();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.ConnectionImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Source Port</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SOURCE_PORT = eINSTANCE
				.getConnection_SourcePort();

		/**
		 * The meta object literal for the '<em><b>Target Port</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__TARGET_PORT = eINSTANCE
				.getConnection_TargetPort();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.InstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.InstanceImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__ARGUMENTS = eINSTANCE.getInstance_Arguments();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__ENTITY = eINSTANCE.getInstance_Entity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTANCE__NAME = eINSTANCE.getInstance_Name();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.BroadcastImpl <em>Broadcast</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BROADCAST__NAME = eINSTANCE.getBroadcast_Name();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BROADCAST__OUTPUTS = eINSTANCE.getBroadcast_Outputs();

		/**
		 * The meta object literal for the '
		 * {@link net.sf.orcc.df.impl.ActionImpl <em>Action</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see net.sf.orcc.df.impl.ActionImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__BODY = eINSTANCE.getAction_Body();

		/**
		 * The meta object literal for the '<em><b>Input Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference ACTION__INPUT_PATTERN = eINSTANCE.getAction_InputPattern();

		/**
		 * The meta object literal for the '<em><b>Output Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference ACTION__OUTPUT_PATTERN = eINSTANCE.getAction_OutputPattern();

		/**
		 * The meta object literal for the '<em><b>Peek Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference ACTION__PEEK_PATTERN = eINSTANCE.getAction_PeekPattern();

		/**
		 * The meta object literal for the '<em><b>Scheduler</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference ACTION__SCHEDULER = eINSTANCE.getAction_Scheduler();

		/**
		 * The meta object literal for the '<em><b>Tag</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__TAG = eINSTANCE.getAction_Tag();

		/**
		 * The meta object literal for the '
		 * {@link net.sf.orcc.df.impl.ActorImpl <em>Actor</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see net.sf.orcc.df.impl.ActorImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getActor()
		 * @generated
		 */
		EClass ACTOR = eINSTANCE.getActor();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__ACTIONS = eINSTANCE.getActor_Actions();

		/**
		 * The meta object literal for the '<em><b>Actions Outside Fsm</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__ACTIONS_OUTSIDE_FSM = eINSTANCE
				.getActor_ActionsOutsideFsm();

		/**
		 * The meta object literal for the '<em><b>Fsm</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__FSM = eINSTANCE.getActor_Fsm();

		/**
		 * The meta object literal for the '<em><b>Initializes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__INITIALIZES = eINSTANCE.getActor_Initializes();

		/**
		 * The meta object literal for the '<em><b>Mo C</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__MO_C = eINSTANCE.getActor_MoC();

		/**
		 * The meta object literal for the '<em><b>Procs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__PROCS = eINSTANCE.getActor_Procs();

		/**
		 * The meta object literal for the '<em><b>State Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__STATE_VARS = eINSTANCE.getActor_StateVars();

		/**
		 * The meta object literal for the '<em><b>Template Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTOR__TEMPLATE_DATA = eINSTANCE.getActor_TemplateData();

		/**
		 * The meta object literal for the '<em><b>Native</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTOR__NATIVE = eINSTANCE.getActor_Native();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTOR__NAME = eINSTANCE.getActor_Name();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTOR__FILE_NAME = eINSTANCE.getActor_FileName();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTOR__LINE_NUMBER = eINSTANCE.getActor_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTOR__INPUTS = eINSTANCE.getActor_Inputs();

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
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.FSMImpl <em>FSM</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.FSMImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getFSM()
		 * @generated
		 */
		EClass FSM = eINSTANCE.getFSM();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference FSM__INITIAL_STATE = eINSTANCE.getFSM_InitialState();

		/**
		 * The meta object literal for the '
		 * {@link net.sf.orcc.df.impl.PatternImpl <em>Pattern</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see net.sf.orcc.df.impl.PatternImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPattern()
		 * @generated
		 */
		EClass PATTERN = eINSTANCE.getPattern();

		/**
		 * The meta object literal for the '<em><b>Num Tokens Map</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__NUM_TOKENS_MAP = eINSTANCE
				.getPattern_NumTokensMap();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__PORTS = eINSTANCE.getPattern_Ports();

		/**
		 * The meta object literal for the '<em><b>Port To Var Map</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__PORT_TO_VAR_MAP = eINSTANCE
				.getPattern_PortToVarMap();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__VARIABLES = eINSTANCE.getPattern_Variables();

		/**
		 * The meta object literal for the '<em><b>Var To Port Map</b></em>' map feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATTERN__VAR_TO_PORT_MAP = eINSTANCE
				.getPattern_VarToPortMap();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.PortImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Num Tokens Consumed</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NUM_TOKENS_CONSUMED = eINSTANCE
				.getPort_NumTokensConsumed();

		/**
		 * The meta object literal for the '<em><b>Num Tokens Produced</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NUM_TOKENS_PRODUCED = eINSTANCE
				.getPort_NumTokensProduced();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__TYPE = eINSTANCE.getPort_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NAME = eINSTANCE.getPort_Name();

		/**
		 * The meta object literal for the '
		 * {@link net.sf.orcc.df.impl.StateImpl <em>State</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see net.sf.orcc.df.impl.StateImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.TagImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Identifiers</b></em>' attribute list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__IDENTIFIERS = eINSTANCE.getTag_Identifiers();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.TransitionImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__ACTION = eINSTANCE.getTransition_Action();

		/**
		 * The meta object literal for the '
		 * {@link net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl
		 * <em>Port To EInteger Object Map Entry</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see net.sf.orcc.df.impl.PortToEIntegerObjectMapEntryImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToEIntegerObjectMapEntry()
		 * @generated
		 */
		EClass PORT_TO_EINTEGER_OBJECT_MAP_ENTRY = eINSTANCE
				.getPortToEIntegerObjectMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__KEY = eINSTANCE
				.getPortToEIntegerObjectMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_TO_EINTEGER_OBJECT_MAP_ENTRY__VALUE = eINSTANCE
				.getPortToEIntegerObjectMapEntry_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.PortToVarMapEntryImpl <em>Port To Var Map Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see net.sf.orcc.df.impl.PortToVarMapEntryImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getPortToVarMapEntry()
		 * @generated
		 */
		EClass PORT_TO_VAR_MAP_ENTRY = eINSTANCE.getPortToVarMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_TO_VAR_MAP_ENTRY__KEY = eINSTANCE
				.getPortToVarMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_TO_VAR_MAP_ENTRY__VALUE = eINSTANCE
				.getPortToVarMapEntry_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.VarToPortMapEntryImpl <em>Var To Port Map Entry</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see net.sf.orcc.df.impl.VarToPortMapEntryImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getVarToPortMapEntry()
		 * @generated
		 */
		EClass VAR_TO_PORT_MAP_ENTRY = eINSTANCE.getVarToPortMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_TO_PORT_MAP_ENTRY__KEY = eINSTANCE
				.getVarToPortMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_TO_PORT_MAP_ENTRY__VALUE = eINSTANCE
				.getVarToPortMapEntry_Value();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.ArgumentImpl <em>Argument</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.ArgumentImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getArgument()
		 * @generated
		 */
		EClass ARGUMENT = eINSTANCE.getArgument();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT__VALUE = eINSTANCE.getArgument_Value();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT__VARIABLE = eINSTANCE.getArgument_Variable();

		/**
		 * The meta object literal for the '{@link net.sf.orcc.df.impl.UnitImpl <em>Unit</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see net.sf.orcc.df.impl.UnitImpl
		 * @see net.sf.orcc.df.impl.DfPackageImpl#getUnit()
		 * @generated
		 */
		EClass UNIT = eINSTANCE.getUnit();

		/**
		 * The meta object literal for the '<em><b>Constants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference UNIT__CONSTANTS = eINSTANCE.getUnit_Constants();

		/**
		 * The meta object literal for the '<em><b>Procedures</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference UNIT__PROCEDURES = eINSTANCE.getUnit_Procedures();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__FILE_NAME = eINSTANCE.getUnit_FileName();

		/**
		 * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__LINE_NUMBER = eINSTANCE.getUnit_LineNumber();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__NAME = eINSTANCE.getUnit_Name();

	}

} // DfPackage
