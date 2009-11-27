package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.util.HashMap;

import net.sf.orcc.backends.c.quasistatic.scheduler.parsers.PropertiesParser;

public class TokensPattern {
	
	/**
	 * remaingMap: <Port_Name>, <Remaining_Tokens>
	 */
	HashMap<String, Integer> remainingMap;
	/**
	 * remaingMap: <Port_Name>, <No tokens consumed when is fired>
	 */
	HashMap<String, Integer> consumptionMap;
	
	String machineName;
	
	public TokensPattern(String machineName){
		this.machineName = machineName;
	}
	
	public void restoreTokenPattern(){
		consumptionMap = PropertiesParser.getConsumptionTokenPortsMap(machineName);
		initRemainingMap();
	}
	
	/**
	 * Inits the remaining tokens map.
	 */
	private void initRemainingMap(){
		HashMap<String, Integer> noReadsMap = PropertiesParser.getNoReadsMap(machineName);
		remainingMap = new HashMap<String, Integer>();
		for(String portName: noReadsMap.keySet()){
			int consumption = consumptionMap.get(portName);
			int noReads = noReadsMap.get(portName);
			remainingMap.put(portName, noReads * consumption);
		}
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
}
