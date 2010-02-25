package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.util.HashMap;

public class TokensPattern {
	
	
	private String actorName;
	/**
	 * remaingMap: <Port_Name>, <No tokens consumed when is fired>
	 */
	private HashMap<String, Integer> consumptionMap;
	/**
	 * remaingMap: <Port_Name>, <Remaining_Tokens>
	 */
	private HashMap<String, Integer> initialMap;
	
	/**
	 * remaingMap: <Port_Name>, <Remaining_Tokens>
	 */
	private HashMap<String, Integer> remainingMap;
	
	public TokensPattern(String actorName){
		this(actorName, null, null);
	}
	
	public TokensPattern(String actorName, HashMap<String, Integer> remainingMap, HashMap<String, Integer> consumptionMap){
		this.actorName = actorName;
		this.remainingMap = remainingMap;
		this.initialMap = new HashMap<String, Integer>(remainingMap);
		this.consumptionMap = consumptionMap;
	}
	
	/**
	 * 
	 * @param port
	 * @return if it could be fired 
	 */
	public boolean firePort(String port){
		int remainingTokens = remainingMap.get(port);
		int consumptionTokens = consumptionMap.get(port);
		
		if(remainingTokens == 0  || remainingTokens < consumptionTokens){
			return false;
		}
		
		remainingTokens -= consumptionTokens;
		remainingMap.put(port, remainingTokens);
		return true;
	}
	
	public String getActorName(){
		return actorName;
	}
	
	public void restoreTokenPattern(){
		remainingMap =  new HashMap<String, Integer>(initialMap);
	}
}
