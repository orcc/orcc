package net.sf.orcc.tom.transform;

import net.sf.orcc.df.*;
import net.sf.orcc.ir.*;
import net.sf.orcc.util.OrccLogger;

/**
* This simple class print the list of procedures named "untagged_0" in an actor
*/
public class SimpleProcedureMatch {

	%include { orcc_procedure.tom }
	%include { orcc_terminals.tom }

	public void exec(Actor actor) {

		OrccLogger.traceln(actor.getName());
		
		for(Procedure procedure : actor.getProcs()) {
			%match (procedure){
				p@proc("untagged_0", _, _) -> {
					OrccLogger.traceln(procedure.getName());
				}
			}
		}
	}
}
