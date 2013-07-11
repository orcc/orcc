package net.sf.orcc.tools.merger.actor;

import java.util.LinkedList;
import java.util.List;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Pattern;

/**
 * This class defines a SuperAction, which is analogous to a static schedule.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class SuperAction {

	private LinkedList<ActionInvocation> invocations;

	private Pattern inputPattern;

	private Pattern outputPattern;

	public Pattern getInputPattern() {
		return inputPattern;
	}

	public Pattern getOutputPattern() {
		return outputPattern;
	}

	public SuperAction() {
		invocations = new LinkedList<ActionInvocation>();
		inputPattern = DfFactory.eINSTANCE.createPattern();
		outputPattern = DfFactory.eINSTANCE.createPattern();
	}

	public void add(ActionInvocation iterand) {
		invocations.offer(iterand);
	}

	public List<ActionInvocation> getActionInvocations() {
		return invocations;
	}

	/*
	 * Each superaction instance has an input and output pattern
	 * that is visible to the actors neighboring its superactor. 
	 * The pattern need to be determined from the invocations.
	 */
	
	public void computePatterns(Network subNetwork) {
		for (ActionInvocation iterand : invocations) {
			Pattern actionPattern = iterand.getAction().getInputPattern();
			for (Port port : actionPattern.getPorts()) {
				if (MergerUtil.findPort(subNetwork.getInputs(), port.getName()) >= 0) {
					if (!inputPattern.contains(port)) {
						inputPattern.getPorts().add(port);
					} else {
						int portIndex = MergerUtil.findPort(inputPattern.getPorts(), port.getName());
						int actionPortIndex = MergerUtil.findPort(actionPattern.getPorts(), port.getName());
						for (int i = 0; i < actionPattern.getPorts().size(); i++) {
							int oldCns = inputPattern.getPorts().get(portIndex).getNumTokensConsumed();
							int newCns = actionPattern.getPorts().get(actionPortIndex).getNumTokensConsumed();
							inputPattern.getPorts().get(portIndex).setNumTokensConsumed(oldCns + newCns);
						}
					}
				}
			}

			actionPattern = iterand.getAction().getOutputPattern();
			for (Port port : actionPattern.getPorts()) {
				if (MergerUtil.findPort(subNetwork.getOutputs(), port.getName()) >= 0) {
					if(!outputPattern.contains(port)) {
						outputPattern.getPorts().add(port);
					} else {
						int portIndex = MergerUtil.findPort(outputPattern.getPorts(), port.getName());
						int actionPortIndex = MergerUtil.findPort(actionPattern.getPorts(), port.getName());
						for (int i = 0; i < actionPattern.getPorts().size(); i++) {
							int oldPrd = outputPattern.getPorts().get(portIndex).getNumTokensProduced();
							int newPrd = actionPattern.getPorts().get(actionPortIndex).getNumTokensProduced();
							outputPattern.getPorts().get(portIndex).setNumTokensProduced(oldPrd + newPrd);
						}
					}
				}
			}
		}
	}

}
