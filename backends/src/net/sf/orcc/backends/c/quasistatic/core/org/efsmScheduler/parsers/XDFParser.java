/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
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
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
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

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.XDFParsingException;
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
 * The XDFParser class represents a parser for .xdf files.<br/> xdf file format
 * is the XML equivalent of nl(Network Language) file format.
 * </p>
 * <p>
 * 
 */
public class XDFParser {
	static String xdfFileName;
	static Network network;
	static Document dom;
    private static DocumentBuilder db = null;

	public XDFParser() {
		super();
    }

	/**
	 * @param xnlFileName
	 *            name of the .xnl file to be parsed.
	 * @return the network described by xnlFileName
	 */
	public static Network parse(String networkName, String xdfFileName) throws XDFParsingException {
		XDFParser.xdfFileName = xdfFileName;
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
            dom = db.parse(xdfFileName);
        } catch (SAXException ex) {
            Logger.getLogger(XDFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XDFParser.class.getName()).log(Level.SEVERE, null, ex);
        }
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

	private static void domToNetwork() throws XDFParsingException {

        Element docRoot = dom.getDocumentElement();
		// add actors
		NodeList entityList = docRoot.getElementsByTagName("Instance");
		for (int i = 0; i < entityList.getLength(); i++) {

			Element entityDeclEle = (Element) entityList.item(i);
			String actorRef = entityDeclEle.getAttribute("id");

			NodeList entityExprList = entityDeclEle
					.getElementsByTagName("Class");

			Element actorEle = (Element) entityExprList.item(0);
			String longName = actorEle.getAttribute("name");
            int index = numberOfOcurrencesForActor(longName);
            
            NetworkActor actor = new NetworkActor(actorRef, longName,index);
			actor.setParentNetwork(network);
			actor.setNetworkPath(network.getNetworkName());
			network.addActor(actor);
			
		}
		// add connections

		NodeList strucStmtList = docRoot.getElementsByTagName("Connection");
		for (int i = 0; i < strucStmtList.getLength(); i++) {
			Element strucStmtEle = (Element) strucStmtList.item(i);
            String toActorName = strucStmtEle.getAttribute("dst");
            String fromActorName = strucStmtEle.getAttribute("src");
            String toPortName = strucStmtEle.getAttribute("dst-port");
            String fromPortName = strucStmtEle.getAttribute("src-port");
            Port fromPort = new Port(fromActorName, fromPortName, PortType.Output, -1);
            Port toPort = new Port(toActorName, toPortName, PortType.Input, -1);
			
            fromPort.setNetworkPath(network.getNetworkName());
            toPort.setNetworkPath(network.getNetworkName());
			fromPort.setType(PortType.Output);

			Connection newConn = new Connection(fromPort, toPort);

			network.addConnection(newConn);

			//adding ports of newConn transfered into addConnection.
		}
	}


	public static void main(String[] args) {

		try {
			Network net = parse("Motion", "MPEG_SP/parser.xdf");
			System.out.println(net.toString());

		} catch (XDFParsingException e) {
			e.printStackTrace();
		}
	}
}
