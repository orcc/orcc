/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Attribute;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Tag;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.Transitions;
import net.sf.orcc.df.Unit;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.WrapperString;
import net.sf.orcc.df.WrapperXml;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class DfFactoryImpl extends EFactoryImpl implements DfFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DfPackage getPackage() {
		return DfPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static DfFactory init() {
		try {
			DfFactory theDfFactory = (DfFactory)EPackage.Registry.INSTANCE.getEFactory("http://orcc.sf.net/model/2011/Df"); 
			if (theDfFactory != null) {
				return theDfFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DfFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public DfFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DfPackage.NETWORK: return createNetwork();
			case DfPackage.ATTRIBUTE: return createAttribute();
			case DfPackage.CONNECTION: return createConnection();
			case DfPackage.INSTANCE: return createInstance();
			case DfPackage.BROADCAST: return createBroadcast();
			case DfPackage.WRAPPER_STRING: return createWrapperString();
			case DfPackage.WRAPPER_XML: return createWrapperXml();
			case DfPackage.ACTION: return createAction();
			case DfPackage.ACTOR: return createActor();
			case DfPackage.FSM: return createFSM();
			case DfPackage.PATTERN: return createPattern();
			case DfPackage.PORT: return createPort();
			case DfPackage.STATE: return createState();
			case DfPackage.TAG: return createTag();
			case DfPackage.TRANSITION: return createTransition();
			case DfPackage.TRANSITIONS: return createTransitions();
			case DfPackage.PORT_TO_EINTEGER_OBJECT_MAP_ENTRY: return (EObject)createPortToEIntegerObjectMapEntry();
			case DfPackage.PORT_TO_VAR_MAP_ENTRY: return (EObject)createPortToVarMapEntry();
			case DfPackage.VAR_TO_PORT_MAP_ENTRY: return (EObject)createVarToPortMapEntry();
			case DfPackage.ARGUMENT: return createArgument();
			case DfPackage.UNIT: return createUnit();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Action createAction() {
		ActionImpl action = new ActionImpl();
		return action;
	}

	@Override
	public Action createAction(String tagName, Procedure scheduler,
			Procedure body) {
		ActionImpl action = new ActionImpl();
		action.setBody(body);
		action.setInputPattern(createPattern());
		action.setOutputPattern(createPattern());
		action.setPeekPattern(createPattern());
		action.setScheduler(scheduler);
		action.setTag(createTag(tagName));
		return action;
	}

	@Override
	public Action createAction(Tag tag, Pattern inputPattern,
			Pattern outputPattern, Pattern peekedPattern, Procedure scheduler,
			Procedure body) {
		ActionImpl action = new ActionImpl();
		action.setBody(body);
		action.setInputPattern(inputPattern);
		action.setOutputPattern(outputPattern);
		action.setPeekPattern(peekedPattern);
		action.setScheduler(scheduler);
		action.setTag(tag);
		return action;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Actor createActor() {
		ActorImpl actor = new ActorImpl();
		return actor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Argument createArgument() {
		ArgumentImpl argument = new ArgumentImpl();
		return argument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Unit createUnit() {
		UnitImpl unit = new UnitImpl();
		return unit;
	}

	@Override
	public Argument createArgument(Var variable, Expression value) {
		ArgumentImpl argument = new ArgumentImpl();
		argument.setVariable(variable);
		argument.setValue(value);
		return argument;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	@Override
	public Attribute createAttribute(String name, EObject value) {
		AttributeImpl attribute = new AttributeImpl();
		attribute.setName(name);
		attribute.setValue(value);
		return attribute;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Broadcast createBroadcast() {
		BroadcastImpl broadcast = new BroadcastImpl();
		return broadcast;
	}

	@Override
	public Broadcast createBroadcast(int numOutputs, Type type) {
		BroadcastImpl broadcast = new BroadcastImpl();
		type = EcoreUtil.copy(type);

		broadcast.getInputs().add(createPort(EcoreUtil.copy(type), "input"));
		for (int i = 0; i < numOutputs; i++) {
			String name = "output_" + i;
			broadcast.getOutputs().add(createPort(EcoreUtil.copy(type), name));
		}

		Map<Port, Integer> portMap = broadcast.getPortMap();
		portMap.put(broadcast.getInput(), 1);
		for (int i = 0; i < numOutputs; i++) {
			portMap.put(broadcast.getOutputs().get(i), i + 1);
		}

		return broadcast;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Connection createConnection() {
		ConnectionImpl connection = new ConnectionImpl();
		return connection;
	}

	@Override
	public Connection createConnection(Vertex source, Port sourcePort,
			Vertex target, Port targetPort) {
		ConnectionImpl connection = new ConnectionImpl();
		connection.setSource(source);
		connection.setSourcePort(sourcePort);
		connection.setTarget(target);
		connection.setTargetPort(targetPort);
		return connection;
	}

	@Override
	public Connection createConnection(Vertex source, Port sourcePort,
			Vertex target, Port targetPort, int size) {
		Connection connection = createConnection(source, sourcePort, target,
				targetPort);
		connection.setAttribute(Connection.BUFFER_SIZE, size);
		return connection;
	}

	@Override
	public Connection createConnection(Vertex source, Port sourcePort,
			Vertex target, Port targetPort, Collection<Attribute> attributes) {
		Connection connection = createConnection(source, sourcePort, target,
				targetPort);
		connection.getAttributes().addAll(EcoreUtil.copyAll(attributes));
		return connection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FSM createFSM() {
		FSMImpl fsm = new FSMImpl();
		return fsm;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Instance createInstance() {
		InstanceImpl instance = new InstanceImpl();
		return instance;
	}

	@Override
	public Instance createInstance(String id, EObject contents) {
		InstanceImpl instance = new InstanceImpl();
		instance.setId(id);
		instance.setContents(contents);
		return instance;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Network createNetwork() {
		NetworkImpl network = new NetworkImpl();
		return network;
	}

	@Override
	public Network createNetwork(String fileName) {
		NetworkImpl network = new NetworkImpl();
		network.setFileName(fileName);
		return network;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern createPattern() {
		PatternImpl pattern = new PatternImpl();
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	@Override
	public Port createPort(Port port) {
		return EcoreUtil.copy(port);
	}

	@Override
	public Port createPort(Type type, String name) {
		PortImpl port = new PortImpl();
		port.setName(name);
		port.setType(type);
		return port;
	}

	@Override
	public Port createPort(Type type, String name, boolean native_) {
		PortImpl port = new PortImpl();
		port.setName(name);
		port.setNative(native_);
		port.setType(type);
		return port;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Port, Integer> createPortToEIntegerObjectMapEntry() {
		PortToEIntegerObjectMapEntryImpl portToEIntegerObjectMapEntry = new PortToEIntegerObjectMapEntryImpl();
		return portToEIntegerObjectMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Port, Var> createPortToVarMapEntry() {
		PortToVarMapEntryImpl portToVarMapEntry = new PortToVarMapEntryImpl();
		return portToVarMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	@Override
	public State createState(String name) {
		StateImpl state = new StateImpl();
		state.setName(name);
		return state;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Tag createTag() {
		TagImpl tag = new TagImpl();
		return tag;
	}

	@Override
	public Tag createTag(List<String> identifiers) {
		TagImpl tag = new TagImpl();
		tag.getIdentifiers().addAll(identifiers);
		return tag;
	}

	@Override
	public Tag createTag(String tagName) {
		TagImpl tag = new TagImpl();
		tag.getIdentifiers().add(tagName);
		return tag;
	}

	@Override
	public Tag createTag(Tag tag) {
		TagImpl newTag = new TagImpl();
		newTag.getIdentifiers().addAll(tag.getIdentifiers());
		return newTag;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	@Override
	public Transition createTransition(Action action, State target) {
		TransitionImpl transition = new TransitionImpl();
		transition.setAction(action);
		transition.setState(target);
		return transition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Transitions createTransitions() {
		TransitionsImpl transitions = new TransitionsImpl();
		return transitions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Var, Port> createVarToPortMapEntry() {
		VarToPortMapEntryImpl varToPortMapEntry = new VarToPortMapEntryImpl();
		return varToPortMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public WrapperString createWrapperString() {
		WrapperStringImpl wrapperString = new WrapperStringImpl();
		return wrapperString;
	}

	@Override
	public WrapperString createWrapperString(String value) {
		WrapperStringImpl wrapperString = new WrapperStringImpl();
		wrapperString.setString(value);
		return wrapperString;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public WrapperXml createWrapperXml() {
		WrapperXmlImpl wrapperXml = new WrapperXmlImpl();
		return wrapperXml;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public DfPackage getDfPackage() {
		return (DfPackage)getEPackage();
	}

} // DfFactoryImpl
