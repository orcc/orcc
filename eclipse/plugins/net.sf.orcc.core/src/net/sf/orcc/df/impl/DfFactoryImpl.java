/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import net.sf.orcc.df.*;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Attribute;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.WrapperString;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;

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
			DfFactory theDfFactory = (DfFactory)EPackage.Registry.INSTANCE.getEFactory("http:///net/sf/orcc/df.ecore"); 
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
			case DfPackage.VERTEX: return createVertex();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
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

		broadcast.getInputs().add(
				IrFactory.eINSTANCE.createPort(EcoreUtil.copy(type), "input"));
		for (int i = 0; i < numOutputs; i++) {
			String name = "output_" + i;
			broadcast.getOutputs().add(
					IrFactory.eINSTANCE.createPort(EcoreUtil.copy(type), name));
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
	public Connection createConnection(Port source, Port target) {
		ConnectionImpl connection = new ConnectionImpl();
		connection.setSourcePort(source);
		connection.setTargetPort(target);
		return connection;
	}

	@Override
	public Connection createConnection(Port source, Port target, int size) {
		ConnectionImpl connection = new ConnectionImpl();
		connection.setSourcePort(source);
		connection.setTargetPort(target);
		connection.getAttributes().add(
				createAttribute(Connection.BUFFER_SIZE,
						IrFactory.eINSTANCE.createExprInt(size)));
		return connection;
	}

	@Override
	public Connection createConnection(Port source, Port target,
			List<Attribute> attributes) {
		ConnectionImpl connection = new ConnectionImpl();
		connection.setSourcePort(source);
		connection.setTargetPort(target);
		connection.getAttributes().addAll(EcoreUtil.copyAll(attributes));
		return connection;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex createVertex() {
		VertexImpl vertex = new VertexImpl();
		return vertex;
	}

	@Override
	public Vertex createVertex(Instance instance) {
		VertexImpl vertex = new VertexImpl();
		vertex.setContents(instance);
		return vertex;
	}

	@Override
	public Vertex createVertex(Port port) {
		VertexImpl vertex = new VertexImpl();
		vertex.setContents(port);
		return vertex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
