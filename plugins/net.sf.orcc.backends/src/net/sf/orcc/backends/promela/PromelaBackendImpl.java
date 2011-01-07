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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.STPrinter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.transformations.PhiRemoval;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;

/**
 * This class defines a template-based PROMELA back-end.
 * 
 * @author Ghislain Roquier
 * 
 */
public class PromelaBackendImpl extends AbstractBackend {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		main(PromelaBackendImpl.class, args);
	}

	private STPrinter printer;

	private Map<Action, List<Expression>> guards = new HashMap<Action, List<Expression>>();
	private Map<Action, List<Peek>> peeks = new HashMap<Action, List<Peek>>();
	private Map<Action, List<Load>> loads = new HashMap<Action, List<Load>>();
	
	@Override
	protected void doTransformActor(Actor actor) throws OrccException {
		ActorTransformation[] transformations = { 
				new GuardsExtractor(guards, peeks, loads), new PhiRemoval(),
				new DeadCodeElimination(),
				new DeadVariableRemoval(false)};

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<File> files) throws OrccException {
		// do not generate a PROMELA VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network.flatten();

		printer = new STPrinter();
		printer.loadGroups("PROMELA_actor");
		printer.setExpressionPrinter(PromelaExprPrinter.class);
		printer.setTypePrinter(PromelaTypePrinter.class);

		printer.setOptions(getAttributes()); // need to check what this one does
		printer.getOptions().put("guards", guards);
		printer.getOptions().put("peeks", peeks);
		printer.getOptions().put("loads", loads);
		List<Actor> actors = network.getActors();
		transformActors(actors);
		printInstances(network);
		printNetwork(network);
	}

	@Override
	protected boolean printInstance(Instance actor) throws OrccException {
		String name = actor.getId();
		String outputName = path + File.separator + name + ".pml";
		try {
			printer.printInstance(outputName, actor);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
		return false;
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
		String outputName = path + File.separator + "main_" + network.getName()
				+ ".pml";
		printer.loadGroups("PROMELA_network");
		try {
			printer.printNetwork(outputName, network, false, fifoSize);
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}
}
