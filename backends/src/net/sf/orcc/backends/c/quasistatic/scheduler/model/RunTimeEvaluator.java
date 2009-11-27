package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.Graph;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.FSM.NextStateInfo;

public class RunTimeEvaluator {
	
	public static boolean validate(String btype, Graph graph){
		return true;
	}
	
	public static Graph evaluateActorGraph(ActorGraph actor, String btype){
		List<Graph> elegibleGraphs = new ArrayList<Graph>();
		for(Graph graph: actor.getGraphs()){
			if(validate(btype,graph)){
				elegibleGraphs.add(graph);
			}
		}
		
		//If there is only one graph, returns it
		if(elegibleGraphs.size() == 1){
			return elegibleGraphs.get(0);
		}
		
		//TODO: in other case, it should select one using priorities
		return elegibleGraphs.get(0);
	}
	
	public static NextStateInfo nextTransition(List<NextStateInfo> nextStateInfos, TokensPattern tokensPattern){
    	
		List<NextStateInfo> firableTransitions = new ArrayList<NextStateInfo>();
		
    	//Takes the actions which can be fired 
    	for(NextStateInfo nextStateInfo: nextStateInfos){
    		if(canBeFired(nextStateInfo.getAction(), tokensPattern)){
    			firableTransitions.add(nextStateInfo);
    		}
    	}
    	
    	//if there are more than 1 firable action, checks priorities
    	if(firableTransitions.size() == 1){
    		return firableTransitions.get(0);
    	}
    	if(firableTransitions.size() == 0){
    		return null;
    	}
    	return getMorePriorityTransition(firableTransitions);
    	
    }
    
    public static boolean canBeFired(Action action, TokensPattern tokensPattern){
    	return isArtificialAction(action) || (evaluateGuards(action) && hasTokens(action, tokensPattern));
    }
    
    public static boolean isArtificialAction(Action action){
    	Tag actionTag = action.getTag();
    	return actionTag.size() > 0 && actionTag.get(0).equals("artf");
    }
    
    private static boolean hasTokens(Action action, TokensPattern tokensPattern) {
		Map<Port, Integer> inputPattern = action.getInputPattern();
		for(Port port: inputPattern.keySet()){
			String portName = port.getName();
			if(!tokensPattern.firePort(portName)){
				return false;
			}	
		}
		return true;
	}

	private static boolean evaluateGuards(Action action) {
		return true;
	}

	public static NextStateInfo getMorePriorityTransition(List<NextStateInfo> nextStateInfos){
    	return nextStateInfos.get(0);
    }
}
