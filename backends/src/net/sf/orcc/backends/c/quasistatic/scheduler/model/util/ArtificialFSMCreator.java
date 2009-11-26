package net.sf.orcc.backends.c.quasistatic.scheduler.model.util;

import java.util.List;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Tag;

public class ArtificialFSMCreator {
	
	public static FSM createArtificialFSM(List<Action> actions){
		FSM fsm = new FSM();
		
		//creates two artificial states: start and proc
		fsm.addState("start");
		fsm.addState("proc");
		fsm.setInitialState("start");
		//creates two artificial actions
		Tag startTag = new Tag();
		startTag.add("artf");
		startTag.add("start");
		Action start = new Action(null, startTag, null, null, null, null);
		Tag doneTag = new Tag();
		doneTag.add("artf");
		doneTag.add("done");
		Action done = new Action(null, doneTag, null, null, null, null);
		
		//Inserts the real actions 
		for(Action action: actions){
			fsm.addTransition("proc", "proc", action);
		}
		
		//Insert two new transitions between both artificial states
		fsm.addTransition("start", "proc", start);
		fsm.addTransition("proc", "start", done);
		
		return fsm;
	}
	
}
