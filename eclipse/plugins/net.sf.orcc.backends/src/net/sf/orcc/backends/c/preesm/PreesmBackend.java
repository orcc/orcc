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

import java.util.List;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.resources.IFile;

/**
 * C backend targetting embedded systems
 * 
 * @author mpelcat
 */
public class PreesmBackend extends AbstractBackend {

	@Override
	protected void doInitializeOptions() {
	}

	@Override
	protected void doTransformActor(Actor actor) {
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		// do not generate an embedded C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		// Print actors
		printActors(network.getAllActors());

		// instantiate and flattens network
		new Instantiator(false).doSwitch(network);
		new NetworkFlattener().doSwitch(network);

		// This call is needed to associate instances to network vertices
		network.computeTemplateMaps();

		// The classification gives production and consumption information from
		// the graph
		OrccLogger.traceln("Starting classification of actors... ");
		new Classifier().doSwitch(network);
		OrccLogger.traceln("done");

		// Check that all actors have SDF MoC
		// or CSDF (converted to SDF)
		boolean isSDF = true;
		for (Actor actor : network.getAllActors()) {
			MoC moc = actor.getMoC();

			if (moc.isSDF()) {
				// This is what we want, do nothing
			} else {
				if (moc.isCSDF()) {
					// TODO CSDF actor will be converted into SDF
				} else {
					// Actor is neither SDF nor CSDF. Cannot use the backend
					isSDF = false;
				}
			}
		}

		// Print network
		if (isSDF) {
			SDFMoC moc = (SDFMoC) network.getAllActors().get(0).getMoC();
			moc.toString();
			OrccLogger.traceln("Printing network...");

			new NetworkPrinter(network).printNetwork(path);

		} else {
			OrccLogger
					.traceln("The network is not SDF. Other models are not yet supported.");
		}
	}

	/**
	 * Instead of printing actors' instances like in the C backend, we wish to
	 * print actors and reference them from the network generated code.
	 */
	@Override
	protected boolean printActor(Actor actor) {
		return new ActorPrinter(options).print(path, actor) != 0;
	}
}
