/*
* Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the EPFL and University of Oulu nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.parsers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.XNLParsingException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Connection;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Network;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.NetworkActor;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.Port;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.PortType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <p>
 * The XNLParser class represents a parser for .xnl files.<br/> xnl file format
 * is the XML equivalent of nl(Network Language) file format.
 * </p>
 * <p>
 * 
 */
public class XNLParser {
	static String xnlFileName;
	static Network network;
	static Document dom;
    private static DocumentBuilder db = null;

	public XNLParser() {
		super();
    }

	/**
	 * @param xnlFileName
	 *            name of the .xnl file to be parsed.
	 * @return the network described by xnlFileName
	 */
	public static Network parse(String networkName, String xnlFileName) throws XNLParsingException {
		XNLParser.xnlFileName = xnlFileName;
        initDocumentBuilder();
		network = new Network(networkName);
        getDom();
        domToNetwork();
        return network;
	}

    private static void initDocumentBuilder() {
        if(db!= null) return;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
    }

	private static void getDom() {
        try {
            dom = db.parse(xnlFileName);
        } catch (SAXException ex) {
            Logger.getLogger(XNLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XNLParser.class.getName()).log(Level.SEVERE, null, ex);
        }

		/*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(xnlFileName);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}*/
	}

    private static int numberOfOcurrencesForActor(String actorName){
        int n = 0 ;
        for(NetworkActor actor: network.getActors()){
            String longName = actor.getLongName();
            if(longName.equals(actorName))
                n++;
        }
        return n;
    }

	private static void domToNetwork() throws XNLParsingException {

        Element docRoot = dom.getDocumentElement();
		// add actors
		NodeList entityList = docRoot.getElementsByTagName("EntityDecl");
		for (int i = 0; i < entityList.getLength(); i++) {

			Element entityDeclEle = (Element) entityList.item(i);
			String actorRef = entityDeclEle.getAttribute("name");

			NodeList entityExprList = entityDeclEle
					.getElementsByTagName("EntityExpr");

			if (entityExprList.getLength() != 1) {
				throw new XNLParsingException(
						"Number of EnitityExpr elements ="
								+ entityExprList.getLength() + "!= 1  for "
								+ actorRef);
			}
			Element actorEle = (Element) entityExprList.item(0);
			String longName = actorEle.getAttribute("name");
            int index = numberOfOcurrencesForActor(longName);
            
            NetworkActor actor = new NetworkActor(actorRef, longName,index);
			actor.setParentNetwork(network);
			actor.setNetworkPath(network.getNetworkName());
			network.addActor(actor);
			
		}
		// add connections

		NodeList strucStmtList = docRoot.getElementsByTagName("StructureStmt");
		for (int i = 0; i < strucStmtList.getLength(); i++) {
			Element strucStmtEle = (Element) strucStmtList.item(i);
			String structuteStmtKind = strucStmtEle.getAttribute("kind");
			if (!structuteStmtKind.equals("Connection"))
				throw new XNLParsingException("StructureStmts of kind"
						+ structuteStmtKind + "are not handled yet.");
			NodeList portSpecList = strucStmtEle
					.getElementsByTagName("PortSpec");
			if (portSpecList.getLength() != 2)
				throw new XNLParsingException(
						"Number of PortSpec elements != 2");
			Element fromPortEle = (Element) portSpecList.item(0);
			Element toPortEle = (Element) portSpecList.item(1);
			Port fromPort = getPortFromEle(fromPortEle);
			Port toPort = getPortFromEle(toPortEle);
            fromPort.setNetworkPath(network.getNetworkName());
            toPort.setNetworkPath(network.getNetworkName());
			fromPort.setType(PortType.Output);

			Connection newConn = new Connection(fromPort, toPort);

			network.addConnection(newConn);

			//adding ports of newConn transfered into addConnection.
		}
	}

	private static Port getPortFromEle(Element portEle)
			throws XNLParsingException {
		String portKind, actorName, portRef;
		portKind = portEle.getAttribute("kind");
		if (portKind.equals("Local")) {
			actorName = null;
		} else if (portKind.equals("Entity")) {
			NodeList actorRefList = portEle.getElementsByTagName("EntityRef");
			if (actorRefList.getLength() != 1)
				throw new XNLParsingException("Number of EntityRefs != 1");
			actorName = ((Element) actorRefList.item(0)).getAttribute("name");
		} else
			throw new XNLParsingException(
					"Port kind should be either Local or Entity");

		NodeList portRefList = portEle.getElementsByTagName("PortRef");
		if (portRefList.getLength() != 1) {
			throw new XNLParsingException("Number of EntityRefs != 1");
		}
		portRef = ((Element) portRefList.item(0)).getAttribute("name");

		return new Port(actorName, portRef, PortType.Input, -1);
	}

	public static void main(String[] args) {

		try {
			Network net = parse("Motion", "mpegXNL/motion.xnl");
			System.out.println(net.toString());

		} catch (XNLParsingException e) {
			e.printStackTrace();
		}
	}
}
