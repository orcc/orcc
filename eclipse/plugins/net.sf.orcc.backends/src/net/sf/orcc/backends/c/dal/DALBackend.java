package net.sf.orcc.backends.c.dal;

import static net.sf.orcc.backends.BackendsConstants.ADDITIONAL_TRANSFOS;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.DeadVariableRemoval;
import net.sf.orcc.backends.transform.DisconnectedOutputPortRemoval;
import net.sf.orcc.backends.transform.DivisionSubstitution;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.Inliner;
import net.sf.orcc.backends.transform.InlinerByAnnotation;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.InstTernaryAdder;
import net.sf.orcc.backends.transform.ListFlattener;
import net.sf.orcc.backends.transform.LoopUnrolling;
import net.sf.orcc.backends.transform.Multi2MonoToken;
import net.sf.orcc.backends.transform.ParameterImporter;
import net.sf.orcc.backends.transform.StoreOnceTransformation;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.ArgumentEvaluator;
import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.transform.BlockCombine;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.transform.DeadCodeElimination;
import net.sf.orcc.ir.transform.DeadGlobalElimination;
import net.sf.orcc.ir.transform.PhiRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.ir.transform.SSAVariableRenamer;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.actor.ActorMerger;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.Void;

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

	private NetworkCPrinter cprinter;

	public DALBackend() {
		cprinter = new NetworkCPrinter();
	}

	@Override
	protected void doInitializeOptions() {
		super.doInitializeOptions();

		cprinter.setOptions(getOptions());

		inputBuffering = getOption("net.sf.orcc.backends.c.dal.inputBuffering",
				false);
		outputBuffering = getOption(
				"net.sf.orcc.backends.c.dal.outputBuffering", false);

		// Configure the map used in RenameTransformation
		final Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_replaced");
		renameMap.put("getw", "getw_replaced");
		renameMap.put("exit", "exit_replaced");
		renameMap.put("index", "index_replaced");
		renameMap.put("log2", "log2_replaced");
		renameMap.put("max", "max_replaced");
		renameMap.put("min", "min_replaced");
		renameMap.put("select", "select_replaced");
		renameMap.put("OUT", "OUT_REPLACED");
		renameMap.put("IN", "IN_REPLACED");
		renameMap.put("SIZE", "SIZE_REPLACED");

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
		networkTransfos.add(new DisconnectedOutputPortRemoval());

		// FIXME: this list should be stored ibn the C backend, and inherited in
		// this backend
		// -----------------------------------------------------
		// Transformations that will be applied on VTL Actors
		// -----------------------------------------------------
		childrenTransfos.add(new TypeResizer(true, false, true, false));
		childrenTransfos.add(new RenameTransformation(renameMap));
		childrenTransfos.add(new DfVisitor<Void>(new InlinerByAnnotation()));
		childrenTransfos.add(new DfVisitor<Void>(new LoopUnrolling()));

		// If "-t" option is passed to command line, apply additional
		// transformations
		if (getOption(ADDITIONAL_TRANSFOS, false)) {
			childrenTransfos.add(new StoreOnceTransformation());
			childrenTransfos.add(new DfVisitor<Void>(new SSATransformation()));
			childrenTransfos.add(new DfVisitor<Void>(new PhiRemoval()));
			childrenTransfos.add(new Multi2MonoToken());
			childrenTransfos.add(new DivisionSubstitution());
			childrenTransfos.add(new ParameterImporter());
			childrenTransfos.add(new DfVisitor<Void>(new Inliner(true, true)));

			// transformations.add(new UnaryListRemoval());
			// transformations.add(new GlobalArrayInitializer(true));

			childrenTransfos.add(new DfVisitor<Void>(new InstTernaryAdder()));
			childrenTransfos.add(new DeadGlobalElimination());

			childrenTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			childrenTransfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
			childrenTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
			childrenTransfos.add(new DfVisitor<Void>(new ListFlattener()));
			childrenTransfos.add(new DfVisitor<Expression>(
					new TacTransformation()));
			childrenTransfos.add(new DfVisitor<CfgNode>(
					new ControlFlowAnalyzer()));
			childrenTransfos
					.add(new DfVisitor<Void>(new InstPhiTransformation()));
			childrenTransfos.add(new DfVisitor<Void>(new EmptyBlockRemover()));
			childrenTransfos.add(new DfVisitor<Void>(new BlockCombine()));

			childrenTransfos.add(new DfVisitor<Expression>(new CastAdder(true,
					true)));
			childrenTransfos.add(new DfVisitor<Void>(new SSAVariableRenamer()));
		}
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

	@Override
	protected Result doGenerateNetwork(Network network) {

		cprinter.setNetwork(network);

		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(cprinter.getNetworkFileContent(),
				path, "pn.xml"));
		result.merge(FilesManager.writeFile(
				cprinter.getFifoSizeHeaderContent(), srcPath, "fifosize.h"));

		NetworkMPrinter mprinter = new NetworkMPrinter(network, mapping);
		mprinter.print(path);

		return result;
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		new InstanceCPrinter(getOptions()).print(path, actor);
		new InstanceHPrinter().print(path, actor);
		return Result.newInstance();
	}

	// FIXME: following methods will be deleted when C backend is migrated
	@Override
	protected void doXdfCodeGeneration(Network network) {
		// Do nothing
	}

	@Override
	protected boolean printActor(Actor actor) {
		return false;
	}

	@Override
	protected boolean printInstance(Instance instance) {
		return false;
	}
}
