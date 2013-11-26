package net.sf.orcc.backends.c.dal;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.c.InstancePrinter;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * DAL C backend targeting ETHZ
 * Distributed Application Layer
 * 
 * @author Jani Boutellier
 * 
 * Based on Orcc C backend
 */
public class DALBackend extends CBackend {

	/**
	 * Path to target "src" folder
	 */
	protected String srcPath;
	
	@Override
	protected void doInitializeOptions() {
		srcPath = path;
	}

	protected void doTransformNetwork(Network network) {
		OrccLogger.traceln("Instantiating network...");
		new Instantiator(false, fifoSize).doSwitch(network);
		OrccLogger.traceln("Flattening...");
		new NetworkFlattener().doSwitch(network);

		OrccLogger.traceln("Adding broadcasts...");
		new BroadcastAdder().doSwitch(network);

		OrccLogger.traceln("Instantiating actors...");
		new Instantiator(true, fifoSize).doSwitch(network);
	
		new UnitImporter().doSwitch(network);

		if (classify) {
			OrccLogger.traceln("Classification of actors...");
			new Classifier().doSwitch(network);
		}
		if (mergeActors) {
			OrccLogger.traceln("Merging of actors...");
			new ActorMerger().doSwitch(network);
		}

		new ArgumentEvaluator().doSwitch(network);
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);
		
		doTransformNetwork(network);

		if (debug) {
			// Serialization of the actors will break proxy link
			EcoreUtil.resolveAll(network);
		}
		transformActors(network.getAllActors());

		network.computeTemplateMaps();

		enumeratePorts(network);

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
		for(Actor actor : network.getAllActors()) {
			for(Port port : actor.getInputs()) {
				port.setNumber(index++);
			}
			for(Port port : actor.getOutputs()) {
				port.setNumber(index++);
			}
		}
	}

	@Override
	protected boolean exportRuntimeLibrary() {
		return true;
	}

	private boolean printNetwork(Network network, String srcPath) {
		boolean successC, successM;
		successC = new NetworkCPrinter(network, options).print(srcPath) > 0;		
		successM = new NetworkMPrinter(network, options).print(srcPath) > 0;		
		return successC & successM;
	}
	
	@Override
	protected boolean printActor(Actor actor) {
		boolean successC, successH;
		successC = new InstanceCPrinter(options).print(srcPath, actor) > 0;
		successH = new InstanceHPrinter(options).print(srcPath, actor) > 0;
		return successC & successH;
	}

	@Override
	protected boolean printInstance(Instance instance) {
		return new InstancePrinter(options).print(srcPath, instance) > 0;
	}

}
