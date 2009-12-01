package net.sf.orcc.backends.c.quasistatic.scheduler.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.TokensPattern;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.Constants;
import net.sf.orcc.util.DomUtil;

public class InputXDFParser {
	
	Document document;
	
	
	/**
	 * Creates a new parser of quasi-static input.
	 * 
	 * @param fileName
	 *            absolute file name of an XDF file
	 * @throws OrccException 
	 */
	public InputXDFParser(String fileName) throws OrccException {
		parseInput(new File(fileName));
	}
	
	private void parseInput(File file) throws OrccException{
		// input
		InputStream is;
		try {
			is = new FileInputStream(file);
			document = DomUtil.parseDocument(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkFile(Element root) throws OrccException{
		if (!root.getNodeName().equals("XDF")) {
			throw new OrccException("Expected \"XDF\" start element");
		}

		String name = root.getAttribute("name");
		if (name.isEmpty()) {
			throw new OrccException("Expected a \"name\" attribute");
		}
		return true;
	}
	
	private NodeList parseGroupNodes(String groupName) throws OrccException{
		Element root = document.getDocumentElement();
		checkFile(root);
		
		Node node = null;
		NodeList rootChilds = root.getChildNodes();
		for(int i = 0 ; i < rootChilds.getLength() ; i++){
			Node child = rootChilds.item(i);
			if(child.getNodeName().equals(groupName)){
				node = child;
				break;
			}
		}
		if(node == null){
			throw new OrccException("Expected a " + groupName + " element");
		}
		
		NodeList nodes = node.getChildNodes();
		return nodes;
	}
	
	private List<String> parseActorsNames(Node btypeNode){
		NodeList actorsNodes = btypeNode.getChildNodes();
		List<String> actorsNames = new ArrayList<String>();
		for(int i = 0; i < actorsNodes.getLength() ; i++){
			Node actorNode = actorsNodes.item(i);
			if(actorNode.getNodeName().equals("Actor")){
				String actorName = ((Element)actorNode).getAttribute("name");
				actorsNames.add(actorName);
			}
		}
		return actorsNames;
	}
	
	private List<TokensPattern> parseActorsPatterns(Node btypeNode){
		NodeList actorsNodes = btypeNode.getChildNodes();
		List<TokensPattern> tokensPatterns = new ArrayList<TokensPattern>();
		for(int i = 0; i < actorsNodes.getLength() ; i++){
			Node actorNode = actorsNodes.item(i);
			if(actorNode.getNodeName().equals("Actor")){
				TokensPattern tokensPattern = parsePortsPattern(actorNode);
				tokensPatterns.add(tokensPattern);
			}
		}
		return tokensPatterns;
	}
	
	private String fixStatement(String stmt){
		stmt = stmt.replace("BET", ">=");
		stmt = stmt.replace("LET", "<=");
		stmt = stmt.replace("BT", ">");
		stmt = stmt.replace("LT", "<");
		stmt = stmt.replace("AND", "&&");
		
		return stmt;
	}
	
	private TokensPattern parsePortsPattern(Node actorNode){
		String actorName = ((Element)actorNode).getAttribute("name");
		NodeList portsNodes = actorNode.getChildNodes();
		HashMap<String, Integer> remainingMap = new HashMap<String, Integer>();;
		HashMap<String, Integer> consumptionMap = new HashMap<String, Integer>();
		for(int i = 0 ; i < portsNodes.getLength() ; i++){
			Node portNode = portsNodes.item(i);
			if(portNode.getNodeName().equals("Port")){
				Element portElement = (Element) portNode;
				String portName = portElement.getAttribute("name");
				int noReads = Integer.parseInt(portElement.getAttribute("reads"));
				int consumptionTokens = Integer.parseInt(portElement.getAttribute("consumption"));
				remainingMap.put(portName, noReads * consumptionTokens);
				consumptionMap.put(portName, consumptionTokens);
			}
		}
		
		return new TokensPattern(actorName, remainingMap, consumptionMap);
	}
	
	public HashMap<String, String> parseCustomIndividualBuffersSizes() throws OrccException{
		HashMap<String, String> customBuffersSize = new HashMap<String, String>();
		
		NodeList buffersNodes = parseGroupNodes(Constants.CUSTOM_INDIVIDUAL_BUFFERS_SIZES);
		
		for(int i = 0; i < buffersNodes.getLength() ; i++){
			Node bufferNode = buffersNodes.item(i);
			if(bufferNode.getNodeName().equals("Buffer")){
				Element bufferElement = (Element) bufferNode;
				String actorName = bufferElement.getAttribute("actor");
				String portName = bufferElement.getAttribute("port");
				String size = bufferElement.getAttribute("size");
				customBuffersSize.put(actorName + "_" + portName, size);
			}
		}
		return customBuffersSize;
		
	}
	
	public Integer parseCustomGeneralBufferSize() throws OrccException{
		
		NodeList buffersNodes = parseGroupNodes(Constants.CUSTOM_GENERAL_BUFFER_SIZE);
		
		for(int i = 0; i < buffersNodes.getLength() ; i++){
			Node bufferNode = buffersNodes.item(i);
			if(bufferNode.getNodeName().equals("Size")){
				Element bufferElement = (Element) bufferNode;
				String size = bufferElement.getAttribute("value");
				return Integer.parseInt(size);
			}
		}
		return null;
		
	}
	
	public List<String> parseQSSchedulerStmts() throws OrccException{
		List<String> stmts = new ArrayList<String>();
		NodeList stmtsNodes = parseGroupNodes(Constants.QS_SCHEDULER);
		
		for(int i = 0; i < stmtsNodes.getLength() ; i++){
			Node stmtNode = stmtsNodes.item(i);
			if(stmtNode.getNodeName().equals("Statement")){
				Element stmtElement = (Element) stmtNode;
				String stmt = fixStatement(stmtElement.getAttribute("content"));
				stmts.add(stmt);
			}
		}
		return stmts;
		
	}
	
	public List<String> parseSchedulableActorsList() throws OrccException{
		
		NodeList btypesNodes = parseGroupNodes(Constants.TOKENS_PATTERN);
		List<String> actorsList = new ArrayList<String>();
		for(int i = 0; i < btypesNodes.getLength() ; i++){
			Node btypeNode = btypesNodes.item(i);
			if(btypeNode.getNodeName().equals("Btype")){
				actorsList = parseActorsNames(btypeNode);
				break;
			}
		}
		return actorsList;
	}
	
	
	public HashMap<String, List<TokensPattern>> parseTokensPattern() throws OrccException{
		NodeList btypesNodes = parseGroupNodes(Constants.TOKENS_PATTERN);
		
		HashMap<String, List<TokensPattern>> pattern = new HashMap<String, List<TokensPattern>>();
		for(int i = 0; i < btypesNodes.getLength() ; i++){
			Node btypeNode = btypesNodes.item(i);
			if(btypeNode.getNodeName().equals("Btype")){
				Element btypeElement = (Element)btypeNode;
				String btypeName =  btypeElement.getAttribute("name");
				List<TokensPattern> tokensPatterns = parseActorsPatterns(btypeNode);
				pattern.put(btypeName, tokensPatterns);
			}
		}
		
		return pattern;
	}
}
