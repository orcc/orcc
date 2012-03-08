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

import java.io.File;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.c.CExpressionPrinter;
import net.sf.orcc.backends.c.CNetworkTemplateData;
import net.sf.orcc.backends.c.CTypePrinter;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.Instantiator;
import net.sf.orcc.df.transformations.NetworkFlattener;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.core.resources.IFile;

/**
 * C backend targetting embedded systems
 * 
 * @author mpelcat
 */
public class CEmbeddedBackendImpl extends AbstractBackend {

	@Override
	protected void doInitializeOptions() {
		// Set Algo, Code, Code/src and Code/IDL directory
		File algoDir = new File(path + "/Algo");
		File codeDir = new File(path + "/Code");
		File srcDir = new File(path + "/Code/src");
		File idlDir = new File(path + "/Code/IDL");

		// If directories don't exist, create them
		if (!algoDir.exists()) {
			algoDir.mkdirs();
		}

		if (!codeDir.exists()) {
			codeDir.mkdirs();
		}

		if (!srcDir.exists()) {
			srcDir.mkdirs();
		}

		if (!idlDir.exists()) {
			idlDir.mkdirs();
		}
	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {

	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate an embedded C VTL
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		// Transform all actors of the network
		transformActors(network.getAllActors());
		printActors(network.getAllActors());

		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/c/embedded/Network.stg");
		printer.setTypePrinter(new CTypePrinter());

		// print network

		// This call is needed to associate instances to network vertices
		network.computeTemplateMaps();

		CNetworkTemplateData data = new CNetworkTemplateData();
		data.computeHierarchicalTemplateMaps(network);
		network.setTemplateData(data);

		// instantiate and flattens network
		network = new Instantiator().doSwitch(network);
		new NetworkFlattener().doSwitch(network);

		// The classification gives production and consumption information from
		// the graph
		write("Starting classification of actors... ");
		network.classify();
		write("done\n");

		if (network.getMoC().isCSDF()) {
			SDFMoC moc = (SDFMoC) network.getAllActors().get(0).getMoC();
			moc.toString();
			write("Printing network...\n");
			printer.print("./Algo/" + network.getName() + ".graphml", path,
					network);
		} else {
			write("The network is not SDF. Other models are not yet supported.\n");
		}
	}

	/**
	 * Instead of printing actors' instances like in the C backend, we wish to
	 * print actors and reference them from the network generated code.
	 */
	@Override
	protected boolean printActor(Actor actor) throws OrccException {
		boolean result = false;

		// print IDL
		StandardPrinter printerIDL = new StandardPrinter(
				"net/sf/orcc/backends/c/embedded/ActorIDL.stg", false);
		printerIDL.setExpressionPrinter(new CExpressionPrinter());
		printerIDL.setTypePrinter(new CTypePrinter());
		result = printerIDL.print("./Code/IDL/" + actor.getSimpleName()
				+ ".idl", path, actor);

		// Print C code
		StandardPrinter printerC = new StandardPrinter(
				"net/sf/orcc/backends/c/embedded/ActorC.stg", false);
		printerC.setExpressionPrinter(new CExpressionPrinter());
		printerC.setTypePrinter(new CTypePrinter());
		result |= printerC.print("./Code/src/" + actor.getSimpleName() + ".c",
				path, actor);

		return result;
	}
}
