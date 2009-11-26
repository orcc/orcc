package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.ArtificialFSMCreator;
import net.sf.orcc.backends.c.quasistatic.scheduler.model.util.Graph;
import net.sf.orcc.backends.c.quasistatic.scheduler.parsers.PropertiesParser;
import net.sf.orcc.backends.c.quasistatic.scheduler.unrollers.FSMUnroller;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.FileUtilities;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Port;

public class ActorGraph {
	private Actor actor;
	private List<Graph> graphs;
	private TokensPattern tokensPattern;
	
	public ActorGraph(Actor actor) {
		this.actor = actor;
		this.tokensPattern = new TokensPattern(getMachineName());
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	public String getName(){
		return actor.getName();
	}
	
	public List<Graph> getGraphs() {
		return graphs;
	}

	public void unrollFSM() throws QuasiStaticSchedulerException{
		FSM fsm = actor.getActionScheduler().getFsm();
		List<Action> actions = actor.getActions();
		if(fsm == null){
			fsm = ArtificialFSMCreator.createArtificialFSM(actions);
		}
		System.out.println("********* Unrolling actor " + getMachineName() + " *********");
		graphs = new FSMUnroller(fsm, tokensPattern).unroll();
	}
	
	public Graph getGraph(String btype){
		return RunTimeEvaluator.evaluateActorGraph(this,btype);
	}
	
	public String getMachineName(){
		return FileUtilities.getFileName(new File(actor.getFile()));
	}
	
	public boolean isStaticActor(){
		return PropertiesParser.existsMachineOnPropertiesFile(getMachineName());
	}
	
	public String toString(){
		return getMachineName() + ": " + (isStaticActor()? "Static":"Non-static");
	}
	
	public void restoreTokenPattern(){
		tokensPattern.restoreTokenPattern();
	}
	
	public void firePort(String port){
		tokensPattern.firePort(port);
	}
	
	public boolean equals(Object other){
		if(!(other instanceof ActorGraph)){
			return false;
		}
		
		ActorGraph otherActor = (ActorGraph) other;
		return actor.getName().equals(otherActor.getActor().getName());
	}
	
	public List<Action> getActionsWithPort(Port port){
		List<Action> actions = new ArrayList<Action>();
		for(Action action: actor.getActions()){
			if(action.getInputPattern().containsKey(port) ||
			   action.getOutputPattern().containsKey(port)){
				actions.add(action);
			}
		}
		return actions;
	}
	
	public boolean contains(Actor actor){
		return this.actor.getName().equals(actor.getName());
	}
	
}
