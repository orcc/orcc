package net.sf.orcc.tom.transform;

import net.sf.orcc.df.*;
import net.sf.orcc.ir.*;
import net.sf.orcc.util.OrccLogger;

public class LitteralInteger {

	%include { string.tom }
	%include { boolean.tom }
	%include { orcc_procedure.tom }

	public void exec(Actor actor) {

		OrccLogger.traceln(actor.getName());
		
		for(Procedure procedure : actor.getProcs()) {
			%match (procedure){
				p@proc("untagged_0", _) -> {
					OrccLogger.traceln(procedure.getName());
				}
			}
		}
	}
}
