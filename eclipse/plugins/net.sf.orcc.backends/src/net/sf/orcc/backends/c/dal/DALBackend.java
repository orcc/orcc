package net.sf.orcc.backends.c.dal;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;

/**
 * DAL C backend targeting ETHZ Distributed Application Layer
 * 
 * Based on Orcc C backend
 * 
 * @author Jani Boutellier
 */
public class DALBackend extends CBackend {

	protected boolean outputBuffering;
	protected boolean inputBuffering;

	private NetworkCPrinter networkCPrinter;
	private NetworkMPrinter mappingPrinter;

	private InstanceHSPrinter instanceHSPrinter;
	private InstanceCSPrinter instanceCSPrinter;
	private InstanceCPrinter instanceCPrinter;
	private InstanceHPrinter instanceHPrinter;

	public DALBackend() {
		networkCPrinter = new NetworkCPrinter();
		mappingPrinter = new NetworkMPrinter();
		instanceHSPrinter = new InstanceHSPrinter();
		instanceCSPrinter = new InstanceCSPrinter();
		instanceCPrinter = new InstanceCPrinter();
		instanceHPrinter = new InstanceHPrinter();
	}

	@Override
	protected void doInitializeOptions() {
		super.doInitializeOptions();

		networkCPrinter.setOptions(getOptions());
		instanceCPrinter.setOptions(getOptions());

		inputBuffering = getOption("net.sf.orcc.backends.c.dal.inputBuffering",
				false);
		outputBuffering = getOption(
				"net.sf.orcc.backends.c.dal.outputBuffering", false);
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
		optimizer.computeTokenSizes(network);

		network.computeTemplateMaps();
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

	@Override
	protected Result doGenerateNetwork(Network network) {

		networkCPrinter.setNetwork(network);
		mappingPrinter.setNetwork(network, fifoSize);

		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(networkCPrinter.getNetworkFileContent(),
				outputPath, "pn.xml"));
		result.merge(FilesManager.writeFile(
				networkCPrinter.getFifoSizeHeaderContent(), srcPath, "fifosize.h"));
		result.merge(FilesManager.writeFile(
				mappingPrinter.getMappingFileContent(mapping), outputPath, "mapping1.xml"));

		return result;
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		boolean printSDF = false;
		CharSequence headerFile;
		CharSequence sourceFile;
		if (actor.getMoC() != null) {
			if (actor.getMoC().isSDF() && actor.getInputs().size() > 0 && actor.getOutputs().size() > 0) {
				printSDF = true;
			}
		}
		if (printSDF) {
			instanceHSPrinter.setActor(actor);
			instanceCSPrinter.setActor(actor);
			headerFile = instanceHSPrinter.getFileContent();
			sourceFile = instanceCSPrinter.getFileContent();
		} else {
			instanceHPrinter.setActor(actor);
			instanceCPrinter.setActor(actor);
			headerFile = instanceHPrinter.getFileContent();
			sourceFile = instanceCPrinter.getFileContent();
		}

		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(sourceFile, srcPath, actor.getName() + ".c"));
		result.merge(FilesManager.writeFile(headerFile, srcPath, actor.getName() + ".h"));
		return result;
	}
}
