/*
 * Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.vhdl;

import static net.sf.orcc.OrccLaunchConstants.DEBUG_MODE;
import static net.sf.orcc.preferences.PreferenceConstants.P_VHDL_LIB;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.orcc.OrccActivator;
import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.CustomPrinter;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.transformations.StoreOnceTransformation;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.backends.transformations.VariableRenamer;
import net.sf.orcc.backends.vhdl.ram.RAMTransformation;
import net.sf.orcc.backends.vhdl.transformations.BoolExprTransformation;
import net.sf.orcc.backends.vhdl.transformations.ListDeclarationTransformation;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.BroadcastAdder;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.ir.util.ActorInterpreter;
import net.sf.orcc.ir.util.ActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * VHDL back-end.
 * 
 * @author Nicolas Siret
 * 
 */
public class VHDLBackendImpl extends AbstractBackend {

	public static Pattern adjacentUnderscores = Pattern.compile("_+");

	private StandardPrinter actorPrinter;

	private boolean debugMode;

	private List<String> entities;

	private HashSet<String> entitySet;

	private int minSizeRam;

	private int numPortsRam;

	private final Map<String, String> transformations;

	public VHDLBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_1");
		transformations.put("access", "access_1");
		transformations.put("component", "component_1");
		transformations.put("select", "select_1");
	}

	private void computeEntityList(Instance instance) {
		if (instance.isActor()) {
			Actor actor = instance.getActor();
			String name = actor.getName();
			if (!entitySet.contains(name)) {
				entitySet.add(name);
				entities.add(name);
			}
		} else if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			String name = network.getName();
			if (!entitySet.contains(name)) {
				for (Instance subInstance : network.getInstances()) {
					computeEntityList(subInstance);
				}

				entitySet.add(name);
				entities.add(name);
			}
		}
	}

	@Override
	public void doInitializeOptions() {
		debugMode = getAttribute(DEBUG_MODE, true);
		numPortsRam = getInteger("number of ports per RAM",
				"net.sf.orcc.backends.vhdl.numPortsRam", 2, 1, 256);
		minSizeRam = getInteger("minimum size of RAM",
				"net.sf.orcc.backends.vhdl.minSizeRam", 256, 0, 65536);
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		evaluateInitializeActions(actor);

		// set the template data before transformations so that
		// RAM transformation can update it (with the RAM map)
		VHDLTemplateData templateData = new VHDLTemplateData();
		actor.setTemplateData(templateData);

		// transformations on the code
		ActorVisitor<?>[] transformationsCodegen = {
				new UnitImporter(),

				// cleanup code
				new DeadGlobalElimination(),
				new DeadCodeElimination(),
				new DeadVariableRemoval(),

				// array to RAM transformation
				new RAMTransformation(numPortsRam, minSizeRam),

				// transform "b := a > b;" statements to if conditionals
				new BoolExprTransformation(),

				// makes sure there is at most one store per variable per cycle
				new StoreOnceTransformation(),

				// flattens declarations of multi-dimensional arrays
				new ListDeclarationTransformation(),

				// renames variables so we can inline them in the template
				// should remain after other transformations
				new VariableRenamer(),

				// renames reserved keywords and replaces adjacent underscores
				// by a single underscore
				new RenameTransformation(this.transformations),
				new RenameTransformation(adjacentUnderscores, "_") };

		// applies transformations
		for (ActorVisitor<?> transformation : transformationsCodegen) {
			transformation.doSwitch(actor);

			// serializes a clone of the actor so that we don't change the
			// eResource() of the actor being transformed
			ResourceSet set = new ResourceSetImpl();
			Actor clonedActor = IrUtil.copy(actor);
			if (debugMode && !IrUtil.serializeActor(set, path, clonedActor)) {
				System.out.println("oops " + transformation + " "
						+ actor.getName());
			}
		}

		// compute init value (depends on ListDeclarationTransformation)
		templateData.computeInitValue(actor);

		// compute sensitivity list (depends on RamTransformation)
		templateData.computeSensitivityList(actor);
	}

	private void doTransformNetwork(Network network) throws OrccException {
		new BroadcastAdder().transform(network);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a VHDL VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		actorPrinter = new StandardPrinter(
				"net/sf/orcc/backends/vhdl/VHDL_actor.stg", !debugMode);
		actorPrinter.setExpressionPrinter(new VHDLExpressionPrinter());
		actorPrinter.setTypePrinter(new VHDLTypePrinter());

		// checks output folder exists, and if not creates it
		File folder = new File(path + File.separator + "Design");
		if (!folder.exists()) {
			folder.mkdir();
		}

		network.computeTemplateMaps();

		List<Actor> actors = network.getActors();
		transformActors(actors);
		printActors(actors);

		// print network and subnetworks
		write("Printing network and subnetworks...\n");
		doTransformNetwork(network);
		printNetwork(network);
	}

	/**
	 * Evaluates the initialize actions of the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	private void evaluateInitializeActions(Actor actor) {
		// initializes the actor
		Map<String, Expression> parameters = Collections.emptyMap();
		ActorInterpreter interpreter = new ActorInterpreter(actor, parameters);
		try {
			interpreter.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// remove initialize actions
		IrUtil.delete(actor.getInitializes());

		// remove initialization procedure (we could do better)
		Procedure initProc = actor.getProcedure("_initialize");
		if (initProc != null) {
			IrUtil.delete(initProc);
		}
	}

	/**
	 * Returns the integer value associated with the attribute whose name is
	 * given.
	 * 
	 * @param name
	 *            user-friendly name of the attribute
	 * @param attributeName
	 *            attribute name
	 * @param defaultValue
	 *            default value
	 * @param min
	 *            minimum value allowed (inclusive)
	 * @param max
	 *            maximum value allowed (inclusive)
	 * @return integer value associated with the attribute whose name is given
	 */
	private int getInteger(String name, String attributeName, int defaultValue,
			int min, int max) {
		String attrValue = getAttribute(attributeName,
				String.valueOf(defaultValue));
		int value;
		try {
			value = Integer.parseInt(attrValue);
			if (value < min || value > max) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			write("Invalid value " + attrValue + " for " + name + "\n");
			write("Will use default value " + defaultValue + "\n");
			value = defaultValue;
		}
		return value;
	}

	@Override
	protected boolean printActor(Actor actor) {
		return actorPrinter.print(actor.getName() + ".vhd", path
				+ File.separator + "Design", actor);
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private void printNetwork(Network network) throws OrccException {
		File folder = new File(path + File.separator + "Testbench");
		if (!folder.exists()) {
			folder.mkdir();
		}

		StandardPrinter instancePrinter = new StandardPrinter(
				"net/sf/orcc/backends/vhdl/VHDL_testbench.stg");
		Instance instance = DfFactory.eINSTANCE.createInstance(
				network.getName(), network);
		instance.setContents(network);
		printTestbench(instancePrinter, instance);
		printTCL(instance);

		network.computeTemplateMaps();

		StandardPrinter networkPrinter = new StandardPrinter(
				"net/sf/orcc/backends/vhdl/VHDL_network.stg");
		networkPrinter.setExpressionPrinter(new VHDLExpressionPrinter());
		networkPrinter.setTypePrinter(new VHDLTypePrinter());
		networkPrinter.getOptions().put("fifoSize", fifoSize);

		networkPrinter.print(network.getName() + ".vhd", path + File.separator
				+ "Design", network);

		for (Network subNetwork : network.getNetworks()) {
			networkPrinter.print(subNetwork.getName() + ".vhd", path
					+ File.separator + "Design", subNetwork);
		}
	}

	private void printTCL(Instance instance) {
		CustomPrinter printer = new CustomPrinter(
				"net/sf/orcc/backends/vhdl/VHDL_TCLLists.stg");

		entities = new ArrayList<String>();
		entitySet = new HashSet<String>();
		computeEntityList(instance);

		String lib = OrccActivator.getDefault().getPreference(P_VHDL_LIB, "");
		if (lib == null || lib.isEmpty()) {
			write("Warning: The path to VHDL libraries is not set!\n"
					+ "Go to Window > Preferences > Orcc to edit them.\n");
		}

		printer.print("TCLLists.tcl", path, "TCLLists", "name", instance
				.getNetwork().getName(), "entities", entities,
				"vhdlLibraryPath", lib);
	}

	private void printTestbench(StandardPrinter printer, Instance instance) {
		printer.print(instance.getId() + "_tb.vhd", path + File.separator
				+ "Testbench", instance);

		if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			for (Instance subInstance : network.getInstances()) {
				printTestbench(printer, subInstance);
			}
		}
	}

}
