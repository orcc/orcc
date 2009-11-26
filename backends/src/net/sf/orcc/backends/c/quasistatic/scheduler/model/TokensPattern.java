package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.util.HashMap;

import net.sf.orcc.backends.c.quasistatic.scheduler.parsers.PropertiesParser;

public class TokensPattern {
	
	HashMap<String, Integer> tokensMap;
	HashMap<String, Integer> consumptionMap;
	
	String machineName;
	
	public TokensPattern(String machineName){
		this.machineName = machineName;
	}
	
	public void restoreTokenPattern(){
		tokensMap = PropertiesParser.getPortsMap(machineName);
		consumptionMap = PropertiesParser.getConsumptionTokenPortsMap(machineName);
	}
	
	/**
	 * 
	 * @param port
	 * @return if it could be fired 
	 */
	public boolean firePort(String port){
		int remainingTokens = tokensMap.get(port);
		int consumptionTokens = consumptionMap.get(port);
		
		if(remainingTokens == 0  || remainingTokens < consumptionTokens){
			return false;
		}
		
		remainingTokens -= consumptionTokens;
		tokensMap.put(port, remainingTokens);
		return true;
	}
}
