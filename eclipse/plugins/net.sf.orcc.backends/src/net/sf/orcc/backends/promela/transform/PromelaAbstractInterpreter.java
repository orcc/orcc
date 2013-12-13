package net.sf.orcc.backends.promela.transform;

import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.tools.classifier.AbstractInterpreter;

public class PromelaAbstractInterpreter extends AbstractInterpreter {

	protected Action nextPath = null;
	
	public void setNextPath(Action nextPath) {
		this.nextPath = nextPath;
	}


	@Override
	protected void execute(Action action) {
		executedAction = action;
		Pattern inputPattern = action.getInputPattern();
		for (Port port : inputPattern.getPorts()) {
			int numTokens = inputPattern.getNumTokens(port);
			port.increaseTokenConsumption(numTokens);
		}

		// allocate output pattern (but not input pattern)
		Pattern outputPattern = action.getOutputPattern();
		allocatePattern(outputPattern);

		// execute action
		doSwitch(action.getBody());

		// update token production
		for (Port port : outputPattern.getPorts()) {
			int numTokens = outputPattern.getNumTokens(port);
			port.increaseTokenProduction(numTokens);
		}
	}
	
	
	@Override
	protected boolean isSchedulable(Action action) {
		// don't care about the guard this time
		if (nextPath != null) {
			if (nextPath == originalActions.get(action)) {
				nextPath=null;
				return true;
			} else {
				return false;
			}
		}
		/*Object result = doSwitch(action.getScheduler());
		if (result == null) {
			throw new OrccRuntimeException("could not determine if action "
					+ action.toString() + " is schedulable");
		}
		return ValueUtil.isTrue(result);*/
		return super.isSchedulable(action);
	}
	
	public State getCurrChoiseState(Set<State> states) {
		for (State s : states) {
			if (copier.get(s) == getFsmState()) {return s;}
		}
		return null;
	}

	public State getFsmStateOrig() {
		if (getFsmState()==null) {return null;}
		for (Entry<EObject, EObject> entry : copier.entrySet()) {
			if (getFsmState().equals(entry.getValue())) {
				return (State)entry.getKey();
			}
		}
		return null;
	}
	
	public PromelaAbstractInterpreter(Actor actor) {
		super(actor);
	}
	
	@Override
	public Object caseBlockWhile(BlockWhile block) {
		int oldBranch = branch;
		branch = 0;
		doSwitch(block.getJoinBlock());

		Object condition = exprInterpreter.doSwitch(block.getCondition());

		int timeout=0;
		if (ValueUtil.isBool(condition)) {
			branch = 1;
			while (ValueUtil.isTrue(condition)) {
				doSwitch(block.getBlocks());
				doSwitch(block.getJoinBlock());
				
				condition = exprInterpreter.doSwitch(block.getCondition());
				timeout++;
				if (!ValueUtil.isBool(condition)||timeout>1000) {
					nullWasNormal=false;
					throw new OrccRuntimeException(
							"Condition not boolean at line "
									+ block.getLineNumber() + "\n");
				}
			}
		}
		branch = oldBranch;
		return null;
	}
	
	private boolean nullWasNormal = true;
	
	public boolean nullWasNormal() {
		return nullWasNormal;
	}
}
