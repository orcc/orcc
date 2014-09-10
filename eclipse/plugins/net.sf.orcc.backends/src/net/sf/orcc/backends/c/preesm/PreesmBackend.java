/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.preesm;

import java.io.File;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.util.NetworkValidator;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;

/**
 * C backend targeting rapid prototyping for embedded systems using Preesm.
 * @see <a href="http://preesm.sourceforge.net/">Preesm website</a>
 * 
 * @author Maxime Pelcat
 * @author Karol Desnos
 */
public class PreesmBackend extends AbstractBackend {

	private final ActorPrinter actorPrinter;
	private final NetworkPrinter networkPrinter;

	public PreesmBackend() {
		super(true);

		actorPrinter = new ActorPrinter();
		networkPrinter = new NetworkPrinter();
	}

	@Override
	protected void doInitializeOptions() {

		actorPrinter.setOptions(getOptions());
		networkPrinter.setOptions(getOptions());

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		networkTransfos.add(new Instantiator(false));
		networkTransfos.add(new NetworkFlattener());
		networkTransfos.add(new Classifier());
	}

	/**
	 * Remove the fifo size check validation step
	 */
	@Override
	protected void doValidate(Network network) {
		Validator.checkTopLevel(network);

		new NetworkValidator().doSwitch(network);
	}

	@Override
	protected void beforeGeneration(Network network) {
		network.computeTemplateMaps();

		for (Actor actor : network.getAllActors()) {
			final MoC moc = actor.getMoC();
			if (!(moc.isSDF() || moc.isCSDF())) {
				throw new OrccRuntimeException(
						"The network is not SDF. Other models are not yet supported.");
			}
		}
	}

	@Override
	protected Result doGenerateNetwork(Network network) {
		networkPrinter.setNetwork(network);
		final Result result = Result.newInstance();

		result.merge(FilesManager.writeFile(networkPrinter.getSizesCSV(), path,
				network.getName() + ".varSizes.csv"));

		result.merge(FilesManager.writeFile(networkPrinter.getNetworkContent(),
				path + File.separator + "Algo", network.getName() + ".graphml"));

		return result;
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		actorPrinter.setActor(actor);
		final Result result = Result.newInstance();
		result.merge(FilesManager.writeFile(actorPrinter.getActorIDLContent(),
				path + File.separator + "Code" + File.separator + "IDL",
				actor.getSimpleName() + ".idl"));

		result.merge(FilesManager.writeFile(actorPrinter.getActorCContent(),
				path + File.separator + "Code" + File.separator + "src",
				actor.getSimpleName() + ".c"));
		return result;
	}

	// TODO: delete when all backends will have been migrated
	@Override
	protected boolean printActor(Actor actor) {
		return false;
	}
	@Override
	protected void doTransformActor(Actor actor) {
	}
	@Override
	protected void doXdfCodeGeneration(Network network) {
	}
}
