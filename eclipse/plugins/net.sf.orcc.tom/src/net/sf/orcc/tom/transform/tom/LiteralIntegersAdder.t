package net.sf.orcc.tom.transform;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.*;

import net.sf.orcc.df.*;
import net.sf.orcc.ir.*;
import net.sf.orcc.util.OrccLogger;

public class LiteralIntegersAdder {

	%include { orcc_terminals.tom }
	%include { orcc_procedure.tom }
	%include { orcc_blocks.tom }
	%include { orcc_expressions.tom }
	%include { orcc_instructions.tom }

	public void doSwitch(Instance instance) {
		if(instance.isActor()) {
			doSwitch(instance.getActor());
		}
	}

	public void doSwitch(Actor actor) {
		for(Action action : actor.getActions()) {
			Procedure actionBody = action.getBody();
			%match (actionBody) {
				proc(_, _, _, BlockL(blockBasic(InstructionL(_*, assign(_, _)))))
				-> {
					OrccLogger.traceln("");
				}
			}
		}
		
	}

}