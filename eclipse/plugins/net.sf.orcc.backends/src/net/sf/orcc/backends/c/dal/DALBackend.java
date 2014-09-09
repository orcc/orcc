package net.sf.orcc.backends.c.dal;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;

/**
 * DAL C backend targeting ETHZ Distributed Application Layer
 * 
 * Based on Orcc C backend
 * 
 * @author Jani Boutellier
 */
public class DALBackend extends CBackend {

	/**
	 * Path to target "src" folder
	 */
	protected String srcPath;

	protected boolean outputBuffering;
	protected boolean inputBuffering;

	@Override
	protected void doInitializeOptions() {
		srcPath = path;
		inputBuffering = getOption("net.sf.orcc.backends.c.dal.inputBuffering",
				false);
		outputBuffering = getOption("net.sf.orcc.backends.c.dal.outputBuffering",
				false);

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		networkTransfos.add(new Instantiator(false));
		networkTransfos.add(new NetworkFlattener());
		networkTransfos.add(new BroadcastAdder());
		networkTransfos.add(new Instantiator(true));
		networkTransfos.add(new UnitImporter());
		if (classify) {
			networkTransfos.add(new Classifier());
		}
		if (mergeActors) {
			networkTransfos.add(new ActorMerger());
		}
		networkTransfos.add(new ArgumentEvaluator());
	}

	@Override
	protected void beforeGeneration(Network network) {
		KPNValidator validator = new KPNValidator();
		validator.validate(network);
		validator.analyzeInputs(network);
		validator.analyzeOutputs(network);

		ActorOptimizer optimizer = new ActorOptimizer();
		optimizer.optimizeInput(network, inputBuffering, fifoSize);
		optimizer.optimizeOutput(network, outputBuffering, fifoSize);

		labelPeekPorts(network);

		network.computeTemplateMaps();

		enumeratePorts(network);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		transformActors(network.getAllActors());
		// print instances
		printChildren(network);

		// print network
		OrccLogger.trace("Printing network... ");
		if (printNetwork(network, srcPath)) {
			OrccLogger.traceRaw("Cached\n");
		} else {
			OrccLogger.traceRaw("Done\n");
		}
	}

	private void enumeratePorts(Network network) {
		int index = 0;
		for (Actor actor : network.getAllActors()) {
			for (Port port : actor.getInputs()) {
				port.setNumber(index++);
			}
			for (Port port : actor.getOutputs()) {
				port.setNumber(index++);
			}
		}
	}

	private void labelPeekPorts(Network network) {
		for (Actor actor : network.getAllActors()) {
			for (Port port : actor.getInputs()) {
				for (Action action : actor.getActions()) {
					if (action.getPeekPattern().contains(port)) {
						port.addAttribute("peekPort");
					}
				}
			}
		}
	}

	@Override
	protected Result doLibrariesExtraction() {
		// Never extract libraries (Note: we can also force attribute
		// NO_LIBRARY_EXPORT to true)
		return Result.newInstance();
	}

	private boolean printNetwork(Network network, String srcPath) {
		boolean successC, successM;
		successC = new NetworkCPrinter(network, getOptions()).print(srcPath) > 0;		
		successM = new NetworkMPrinter(network, mapping).print(srcPath) > 0;		
		return successC & successM;
	}
	
	@Override
	protected boolean printActor(Actor actor) {
		boolean successC, successH;
		successC = new InstanceCPrinter(getOptions()).print(srcPath, actor) > 0;
		successH = new InstanceHPrinter().print(srcPath, actor) > 0;
		return successC & successH;
	}
}
