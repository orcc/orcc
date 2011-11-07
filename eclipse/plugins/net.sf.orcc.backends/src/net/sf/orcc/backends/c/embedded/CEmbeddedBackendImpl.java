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
package net.sf.orcc.backends.c.embedded;

import java.util.List;

import org.eclipse.core.resources.IFile;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.c.CExpressionPrinter;
import net.sf.orcc.backends.c.CNetworkTemplateData;
import net.sf.orcc.backends.c.CTypePrinter;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.moc.SDFMoC;

/**
 * C backend targetting embedded systems
 * 
 * @author mpelcat
 */
public class CEmbeddedBackendImpl extends AbstractBackend {

	@Override
	protected void doInitializeOptions() {

	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {

	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		// Transform all actors of the network
		transformActors(network.getActors());
		printActors(network.getActors());

		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/c/embedded/Network.stg");
		printer.setTypePrinter(new CTypePrinter());

		// print network
		
		// This call is needed to associate instances to network vertices
		network.computeTemplateMaps();

		CNetworkTemplateData data = new CNetworkTemplateData();
		data.computeHierarchicalTemplateMaps(network);
		network.setTemplateData(data);

		network.flatten();

		// The classification gives production and consumption information from the graph
		write("Starting classification of actors... ");
		network.classify();
		write("done\n");
		
		if(network.getMoC().isCSDF()){
			SDFMoC moc = (SDFMoC)network.getActors().get(0).getMoC();
			moc.toString();
			write("Printing network...\n");
			printer.print(network.getName() + ".graphml", path, network);
		}
		else{
			write("The network is not SDF. Other models are not yet supported.");
		}
	}

	/**
	 * Instead of printing actors' instances like in the C backend, we
	 * wish to print actors and reference them from the network generated code.
	 */
	@Override
	protected boolean printActor(Actor actor) throws OrccException {
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/c/embedded/Actor.stg", false);
		printer.setExpressionPrinter(new CExpressionPrinter());
		printer.setTypePrinter(new CTypePrinter());
		return printer.print(actor.getSimpleName() + ".c", path, actor);
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate an embedded C VTL
	}
}
