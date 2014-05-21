package net.sf.orcc.tools.mapping;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;

/**
 * This class defines an XML buffer size configuration loader and importer. In
 * works only for flattened networks (ref. to {@link NetworkFlattener}
 * transformation). The xml file format is basically the following:
 * 
 * <pre>
 * &lt;bxdf network="qualifiedName" default-size="value"&gt;
 *   &lt;connection source-actor="name" source-port="name" target-actor="name" target-port="name" size="value"/&gt;
 *   &lt;connection source-actor="name" source-port="name" target-actor="name" target-port="name" size="value"/&gt;
 * &lt;/bxdf&gt;
 * </pre>
 * 
 * @author Simone Casale Brunet
 * @author Endri Bezati
 * 
 */
public class XmlBufferSizeConfiguration {

	private class BufferSizeAttributeReader extends DfVisitor<Void> {

		@Override
		public Void caseConnection(Connection connection) {

			if (connection.getSource() instanceof Actor
					&& connection.getTarget() instanceof Actor
					&& connection.getSize() != null) {

				String sourceActor = ((Actor) connection.getSource()).getName();
				String sourcePort = connection.getSourcePort().getName();
				String targetActor = ((Actor) connection.getTarget()).getName();
				String targetPort = connection.getTargetPort().getName();
				int size = connection.getSize();

				XmlConnection xmlConnection = new XmlConnection(sourceActor,
						sourcePort, targetActor, targetPort, size);
				connections.add(xmlConnection);
			}

			return null;
		}
	}

	private class BufferSizeAttributeWriter extends DfVisitor<Void> {

		@Override
		public Void caseConnection(Connection connection) {

			if (connection.getSource() instanceof Actor
					&& connection.getTarget() instanceof Actor) {

				int size = defaultSize;
				for (XmlConnection c : connections) {
					if (c.equal(connection)) {
						size = c.size;
						break;
					}
				}

				connection.setAttribute(Connection.BUFFER_SIZE,
						IrFactory.eINSTANCE.createExprInt(size));
			}

			return null;
		}
	}

	private class XmlConnection implements Comparable<XmlConnection> {

		private final String sourceActor;
		private final String sourcePort;
		private final String targetActor;
		private final String targetPort;
		private final int size;

		private XmlConnection(String sourceActor, String sourcePort,
				String targetActor, String targetPort, int size) {
			this.sourceActor = sourceActor;
			this.sourcePort = sourcePort;
			this.targetActor = targetActor;
			this.targetPort = targetPort;
			this.size = size;
		}

		@Override
		public int compareTo(XmlConnection o) {
			return o != null ? toString().compareTo(o.toString()) : -1;
		}

		private boolean equal(Connection connection) {
			if (connection.getSource() instanceof Actor
					&& connection.getTarget() instanceof Actor) {
				if (!((Actor) connection.getSource()).getName().equals(
						sourceActor)) {
					return false;
				}

				if (!connection.getSourcePort().getName().equals(sourcePort)) {
					return false;
				}

				if (!((Actor) connection.getTarget()).getName().equals(
						targetActor)) {
					return false;
				}

				if (!connection.getTargetPort().getName().equals(targetPort)) {
					return false;
				}

				return true;
			} else {
				return false;
			}
		}

		public String toString() {
			StringBuffer b = new StringBuffer();
			b.append(sourceActor != null ? sourceActor : "");
			b.append(",");
			b.append(sourcePort != null ? sourcePort : "");
			b.append(",");
			b.append(targetActor != null ? targetActor : "");
			b.append(",");
			b.append(targetPort != null ? targetPort : "");
			return b.toString();
		}

	}

	private static final String XML_BXDF = "bxdf";
	private static final String XML_NETWORK = "network";
	private static final String XML_DEFAULT_SIZE = "default-size";
	private static final String XML_CONNECTION = "connection";
	private static final String XML_SOURCE_ACTOR = "source-actor";
	private static final String XML_TARGET_ACTOR = "target-actor";

	private static final String XML_SOURCE_PORT = "source-port";

	private static final String XML_TARGET_PORT = "target-port";

	private static final String XML_SIZE = "size";

	private List<XmlConnection> connections;
	private int defaultSize;
	private String networkName;

	/**
	 * Load the buffer size configuration contained in this file. If there are
	 * no buffer size defined for a {@link Connection}, than the
	 * <code>default-size</code> defined in the file will be used. After loading
	 * the file you can retrieve the connection size using
	 * {@link Connection#getSize()}.
	 * 
	 * @param file
	 * @param network
	 */
	public void load(File file, Network network) {
		connections = new ArrayList<XmlConnection>();
		networkName = OrccUtil.getQualifiedName(network.getFile());

		loadXmlConnections(file);
		new BufferSizeAttributeWriter().doSwitch(network);
	}

	private void loadXmlConnections(File file) {
		try {
			// parse the input file
			InputStream stream = new FileInputStream(file);
			stream = new BufferedInputStream(stream);

			XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
			XMLStreamReader reader = xmlFactory.createXMLStreamReader(stream);
			while (reader.hasNext()) {
				reader.next();
				if (reader.getEventType() == XMLStreamReader.START_ELEMENT) {
					if (reader.getLocalName().equals(XML_CONNECTION)) {
						try {
							String source = reader.getAttributeValue("",
									XML_SOURCE_ACTOR);
							String target = reader.getAttributeValue("",
									XML_TARGET_ACTOR);
							String sourcePort = reader.getAttributeValue("",
									XML_SOURCE_PORT);
							String targetPort = reader.getAttributeValue("",
									XML_TARGET_PORT);
							String size = reader
									.getAttributeValue("", XML_SIZE);

							if (source != null && target != null
									&& sourcePort != null && targetPort != null
									&& OrccUtil.isNumeric(size)) {
								// it seams ok: add it to the list
								XmlConnection c = new XmlConnection(source,
										sourcePort, target, targetPort,
										Integer.parseInt(size));
								connections.add(c);
							}

						} catch (Exception e) {
							// FIXME add a warning?
							e.printStackTrace();
						}
					} else if (reader.getLocalName().equals(XML_BXDF)) {
						String name = reader.getAttributeValue("", XML_NETWORK);
						if (name == null || !name.equals(networkName)) {
							OrccLogger
									.warn("The network name of the configuration does not match with the network you are using");
						}

						String size = reader.getAttributeValue("",
								XML_DEFAULT_SIZE);
						if (!OrccUtil.isNumeric(size)) {
							OrccLogger
									.warn("The default buffer size value is not valid: 512 will be assigned by default");
							defaultSize = 512;
						} else {
							defaultSize = Integer.parseInt(size);
						}
					}

				} else if (reader.getEventType() == XMLStreamReader.END_ELEMENT
						&& reader.getLocalName().equals(XML_BXDF)) {
					break;
				}
			}

		} catch (FileNotFoundException e) {
			OrccLogger.severeln("Buffer size configuration file not found: "
					+ e.getMessage());
		} catch (Exception e) {
			OrccLogger
					.severeln("Error parsing the buffer size configuration file: "
							+ e.getMessage());
		}
	}

	/**
	 * Write the xml configuration for the given network and the given default
	 * buffer size. Note that only buffers that have the size attribute defined
	 * will be exported.
	 * 
	 * @param file
	 * @param network
	 * @param defaultSize
	 */
	public void write(File file, Network network, int defaultSize) {
		this.defaultSize = defaultSize;
		networkName = OrccUtil.getQualifiedName(network.getFile());
		connections = new ArrayList<XmlConnection>();

		new BufferSizeAttributeReader().doSwitch(network);
		writeXmlConnections(file);
	}

	private void writeXmlConnections(File file) {
		try {
			OutputStream stream = new FileOutputStream(file);
			stream = new BufferedOutputStream(stream);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = factory.createXMLStreamWriter(stream);

			writer.writeStartDocument();

			writer.writeStartElement(XML_BXDF);
			writer.writeAttribute(XML_NETWORK, networkName);
			writer.writeAttribute(XML_DEFAULT_SIZE,
					Integer.toString(defaultSize));

			// sort the xml connections
			Collections.sort(connections);

			// write all the registered connections
			for (XmlConnection c : connections) {
				writer.writeEmptyElement(XML_CONNECTION);
				writer.writeAttribute(XML_SOURCE_ACTOR, c.sourceActor);
				writer.writeAttribute(XML_SOURCE_PORT, c.sourcePort);
				writer.writeAttribute(XML_TARGET_ACTOR, c.targetActor);
				writer.writeAttribute(XML_TARGET_PORT, c.targetPort);
				writer.writeAttribute(XML_SIZE, Integer.toString(c.size));
			}

			writer.writeEndElement(); // end bxdf

			writer.writeEndDocument();

			writer.close();
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
