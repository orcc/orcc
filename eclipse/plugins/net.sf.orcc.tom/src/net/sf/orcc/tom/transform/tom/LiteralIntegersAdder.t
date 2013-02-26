package net.sf.orcc.tom.transform;

import java.util.*;
import tom.library.sl.*;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.*;

import net.sf.orcc.df.*;
import net.sf.orcc.ir.*;
import net.sf.orcc.util.OrccLogger;

public class LiteralIntegersAdder {

	%include { sl.tom }
	%include { orcc_terminals.tom }
	%include { orcc_procedure.tom }
	%include { orcc_blocks.tom }
	%include { orcc_expressions.tom }
	%include { orcc_instructions.tom }

	%strategy printLiteral() extends Fail() {
	  visit Expression {
	    exprInt(x) -> { OrccLogger.traceln("cst: " + `x); }
	  }
	}

	public void doSwitch(Instance instance) {
		if(instance.isActor()) {
			doSwitch(instance.getActor());
		}
	}

	public void doSwitch(Actor actor) {
		for(Action action : actor.getActions()) {
			Procedure actionBody = action.getBody();

			try {
				`TopDown(Try(printLiteral())).visit(actionBody);
			} catch (VisitFailure e) {
				System.out.println("strategy failed");
			}
		}
	}
}