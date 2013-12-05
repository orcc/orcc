package net.sf.orcc.tools.merger.actor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.graph.GraphFactory;
import net.sf.orcc.util.DomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class provides a way to import static regions from XML files.
 * The class is analogous to StaticRegionDetector.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class RegionParser {

	private String definitionFile;

	private Network network;

	private List<List<Vertex>> actorList;

	public RegionParser(String definitionFile, Network network) {
		this.definitionFile = definitionFile;
		this.network = network;
		actorList = new ArrayList<List<Vertex>>();
	}

	public List<List<Vertex>> parse() {
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return actorList;
	}

	private void parseSuperactorList(Document doc) {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (node.getNodeName().equals("superactor")) {
					List<Vertex> localList = new ArrayList<Vertex>();
					parseSuperactor(element, localList);
					if (!localList.isEmpty()) {
						addNameVertex(localList, element.getAttribute("name"));
						actorList.add(localList);
					}
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}

	/*
	 * When static regions (= superactors) are imported from an XML file
	 * the region name is piggybacked in a special "name vertex" that
	 * is created here. The vertex is deleted in ActorMerger.transformNetwork
	 */
	
	private void addNameVertex(List<Vertex> actorList, String name) {
		Vertex nameVertex = GraphFactory.eINSTANCE.createVertex();
		nameVertex.setLabel(name);
		nameVertex.setAttribute("isNameVertex", true);
		actorList.add(nameVertex);
	}
	
	private void parseSuperactor(Element element, List<Vertex> actorList) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (elt.getTagName().equals("actor")) {
					Vertex v = belongsToNetwork(elt.getAttribute("name"));
					if(v != null) {
						actorList.add(v);
					}
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private Vertex belongsToNetwork(String id) {
		for (Vertex vertex : network.getVertices()) {
			if(vertex.getLabel().equals(id))
				return vertex;
		}
		return null;
	}

}
