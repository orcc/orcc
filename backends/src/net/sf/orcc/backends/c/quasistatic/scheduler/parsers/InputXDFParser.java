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
	
	private NodeList parseBtypeNodes(Element root) throws OrccException{
		Node tokensPatternNode = null;
		NodeList rootChilds = root.getChildNodes();
		for(int i = 0 ; i < rootChilds.getLength() ; i++){
			Node node = rootChilds.item(i);
			if(node.getNodeName().equals(Constants.TOKENS_PATTERN)){
				tokensPatternNode = node;
				break;
			}
		}
		if(tokensPatternNode == null){
			throw new OrccException("Expected a " + Constants.TOKENS_PATTERN + " element");
		}
		
		NodeList btypesNodes = tokensPatternNode.getChildNodes();
		return btypesNodes;
	}
	
	public List<String> parseSchedulableActorsList() throws OrccException{
		Element root = document.getDocumentElement();
		checkFile(root);
		
		NodeList btypesNodes = parseBtypeNodes(root);
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
		Element root = document.getDocumentElement();
		checkFile(root);
		
		NodeList btypesNodes = parseBtypeNodes(root);
		
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
				TokensPattern tokensPattern = getPortsPattern(actorNode);
				tokensPatterns.add(tokensPattern);
			}
		}
		return tokensPatterns;
	}
	
	public TokensPattern getPortsPattern(Node actorNode){
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
	
	public static void main(String[] args){
		try {
			new InputXDFParser("C:\\quasistatic_input.xdf").parseTokensPattern();
		} catch (OrccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
