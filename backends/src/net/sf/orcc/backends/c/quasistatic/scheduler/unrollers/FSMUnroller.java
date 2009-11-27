package net.sf.orcc.backends.c.quasistatic.scheduler.unrollers;

import java.util.ArrayList;
import java.util.List;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.RunTimeEvaluator;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.TokensPattern;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.Graph;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.FSM.Transition;

public class FSMUnroller implements AbstractFSMUnroller {

	private FSM fsm;
	private TokensPattern tokensPattern;
	
	public FSMUnroller(FSM fsm, TokensPattern tokensPattern){
		this.fsm = fsm;
		this.tokensPattern = tokensPattern;
	}
	
	/**
	 * Unrolls the FSM, generating a set of SDF graphs
	 * 
	 * @return the list of SDF graphs
	 */
	public List<Graph> unroll() throws QuasiStaticSchedulerException {
		List<Graph> actorSubgraphs = new ArrayList<Graph>();
        List<NextStateInfo> nextStateInfos = getTransitionsFrom(fsm.getInitialState());
		
        //Generates a subgraph for each initial transition
		for(NextStateInfo nextStateInfo: nextStateInfos ){
			tokensPattern.restoreTokenPattern();
			Graph actorSg = unrollSubgraph(nextStateInfo);
            actorSubgraphs.add(actorSg);
		}
		
		return actorSubgraphs;
	}
	
	private List<NextStateInfo> getTransitionsFrom(State state){
		List<Transition> transitions = fsm.getTransitions();
		List<NextStateInfo> transitionsFromState = new ArrayList<NextStateInfo>();
		for(Transition transition: transitions){
			if(transition.getSourceState().compareTo(state) == 0){
				transitionsFromState.addAll(transition.getNextStateInfo());
			}
		}
		
		return transitionsFromState;
	}
	
	/**
     * unrolls the subgraph originating from initEdge
     *
     * @param initEdge
     * @return the unrolled subgraph
	 * @throws QuasiStaticSchedulerException 
     */
    private Graph unrollSubgraph(NextStateInfo nSInfo) throws QuasiStaticSchedulerException {
    	
    	int iterations = 0;
    	Graph graph = new Graph();
    	State nextState; 
    	Action prevAct = null, actAct= null;
    	String initAction = nSInfo.getAction().toString();
    	System.out.println("Subgraph - init action: " + initAction);
		do{
			nextState = nSInfo.getTargetState();
			actAct = nSInfo.getAction();
			graph.addVertex(actAct);
			if(prevAct != null){
				graph.addEdge(prevAct, actAct);
			}
			nSInfo = RunTimeEvaluator.nextTransition(getTransitionsFrom(nextState), tokensPattern);
			if(nSInfo == null){
				System.out.println("Invalid graph");
				break;
			}
			prevAct = actAct;
			iterations++;	
		}while(!nextState.equals(fsm.getInitialState()) && iterations < 20);
    	
		System.out.println(graph.toString());
		
    	return graph;
    	
    }
    
    
}
