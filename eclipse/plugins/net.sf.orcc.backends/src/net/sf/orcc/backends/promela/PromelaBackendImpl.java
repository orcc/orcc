/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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

package net.sf.orcc.backends.promela;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.c.CExpressionPrinter;
import net.sf.orcc.backends.promela.transformations.GuardsExtractor;
import net.sf.orcc.backends.promela.transformations.NetworkStateDefExtractor;
import net.sf.orcc.backends.promela.transformations.PromelaDeadGlobalElimination;
import net.sf.orcc.backends.transformations.Inliner;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.BroadcastAdder;
import net.sf.orcc.df.transformations.Instantiator;
import net.sf.orcc.df.transformations.NetworkFlattener;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.transformations.PhiRemoval;
import net.sf.orcc.ir.transformations.RenameTransformation;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a template-based PROMELA back-end.
 * 
 * @author Ghislain Roquier
 * 
 */
public class PromelaBackendImpl extends AbstractBackend {

	private Map<Action, List<Expression>> guards = new HashMap<Action, List<Expression>>();

	private Map<EObject, List<Action>> priority = new HashMap<EObject, List<Action>>();

	private Map<Action, List<InstLoad>> loadPeeks = new HashMap<Action, List<InstLoad>>();

	private StandardPrinter instancePrinter;

	private final Map<String, String> transformations;

	private NetworkStateDefExtractor netStateDef;

	/**
	 * Creates a new instance of the Promela back-end. Initializes the
	 * transformation hash map.
	 */
	public PromelaBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_");
		transformations.put("getw", "getw_");
		transformations.put("index", "index_");
		transformations.put("max", "max_");
		transformations.put("min", "min_");
		transformations.put("select", "select_");
	}

	@Override
	protected void doInitializeOptions() {
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		DfSwitch<?>[] transformations = {
				new UnitImporter(),
				new Inliner(true, false),
				// new ListFlattener(), //Promela does not support multi
				// dimensional arrays
				new RenameTransformation(this.transformations),
				new GuardsExtractor(guards, priority, loadPeeks),
				new PhiRemoval(),
				new PromelaDeadGlobalElimination(
						netStateDef.getVarsUsedInScheduling(),
						netStateDef.getPortsUsedInScheduling()),
				new DeadCodeElimination(), new DeadVariableRemoval() };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a PROMELA VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		// instantiate and flattens network
		new Instantiator().doSwitch(network);
		new NetworkFlattener().doSwitch(network);

		instancePrinter = new StandardPrinter(
				"net/sf/orcc/backends/promela/PROMELA_actor.stg");
		instancePrinter.setExpressionPrinter(new CExpressionPrinter());
		instancePrinter.setTypePrinter(new PromelaTypePrinter());
		instancePrinter.getOptions().put("guards", guards);
		instancePrinter.getOptions().put("priority", priority);
		instancePrinter.getOptions().put("loadPeeks", loadPeeks);
		instancePrinter.getOptions().put("network", network);

		netStateDef = new NetworkStateDefExtractor();
		netStateDef.doSwitch(network);

		List<Actor> actors = network.getActors();
		transformActors(actors);
		printInstances(network);

		new BroadcastAdder().doSwitch(network);

		network.computeTemplateMaps();
		printNetwork(network);
	}

	@Override
	protected boolean printInstance(Instance instance) {
		return instancePrinter.print(instance.getName() + ".pml", path,
				instance);
	}

	/**
	 * Prints the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private void printNetwork(Network network) {
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/promela/PROMELA_network.stg");
		printer.setTypePrinter(new PromelaTypePrinter());
		printer.print("main_" + network.getName() + ".pml", path, network);
	}

}
