/*
 * Copyright (c) 2009-2011, Artemis SudParis-IETR/INSA of Rennes
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
package net.sf.orcc.backends.llvm.aot;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.StandardPrinter;
import net.sf.orcc.backends.llvm.transformations.BlockNumbering;
import net.sf.orcc.backends.llvm.transformations.BoolToIntTransformation;
import net.sf.orcc.backends.llvm.transformations.GetElementPtrAdder;
import net.sf.orcc.backends.llvm.transformations.ListInitializer;
import net.sf.orcc.backends.llvm.transformations.StringTransformation;
import net.sf.orcc.backends.transformations.CastAdder;
import net.sf.orcc.backends.transformations.EmptyBlockRemover;
import net.sf.orcc.backends.transformations.InstPhiTransformation;
import net.sf.orcc.backends.transformations.UnitImporter;
import net.sf.orcc.backends.transformations.ssa.ConstantPropagator;
import net.sf.orcc.backends.transformations.ssa.CopyPropagator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transformations.Instantiator;
import net.sf.orcc.df.transformations.NetworkFlattener;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.transformations.BlockCombine;
import net.sf.orcc.ir.transformations.CfgBuilder;
import net.sf.orcc.ir.transformations.DeadCodeElimination;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.transformations.DeadVariableRemoval;
import net.sf.orcc.ir.transformations.RenameTransformation;
import net.sf.orcc.ir.transformations.SSATransformation;
import net.sf.orcc.ir.transformations.TacTransformation;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;

/**
 * LLVM back-end.
 * 
 * @author Herve Yviquel
 * 
 */
public class LLVMBackendImpl extends AbstractBackend {

	private final Map<String, String> transformations;
	private StandardPrinter printer;

	/**
	 * Creates a new instance of the LLVM back-end. Initializes the
	 * transformation hash map.
	 */
	public LLVMBackendImpl() {
		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_");
		transformations.put("getw", "getw_");
		transformations.put("index", "index_");
		transformations.put("min", "min_");
		transformations.put("max", "max_");
		transformations.put("select", "select_");
	}

	@Override
	public void doInitializeOptions() {

	}

	@Override
	protected void doTransformActor(Actor actor) throws OrccException {

		DfSwitch<?>[] transformations = { new UnitImporter(),
				new DfVisitor<Void>(new SSATransformation()),
				new DeadGlobalElimination(),
				new DfVisitor<Void>(new DeadCodeElimination()),
				new DfVisitor<Void>(new DeadVariableRemoval()),
				new BoolToIntTransformation(), new StringTransformation(),
				new RenameTransformation(this.transformations),
				new DfVisitor<Expression>(new TacTransformation()),
				new DfVisitor<Void>(new CopyPropagator()),
				new DfVisitor<Void>(new ConstantPropagator()),
				new DfVisitor<Void>(new InstPhiTransformation()),
				new DfVisitor<Void>(new GetElementPtrAdder()),
				new DfVisitor<Expression>(new CastAdder(false)),
				new DfVisitor<Void>(new EmptyBlockRemover()),
				new DfVisitor<Void>(new BlockCombine()),
				new DfVisitor<CfgNode>(new CfgBuilder()),
				new DfVisitor<Void>(new ListInitializer()),
				new DfVisitor<Void>(new BlockNumbering()) };

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
		}

		// Organize metadata information for the current actor
		actor.setTemplateData(new LLVMTemplateData(actor));
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) throws OrccException {
		// do not generate a VTL
	}

	protected Network doTransformNetwork(Network network) throws OrccException {
		// instantiate and flattens network
		write("Instantiating...\n");
		network = new Instantiator(fifoSize).doSwitch(network);
		write("Flattening...\n");
		new NetworkFlattener().doSwitch(network);

		return network;
	}

	@Override
	protected void doXdfCodeGeneration(Network network) throws OrccException {
		network = doTransformNetwork(network);

		transformActors(network.getAllActors());

		network.computeTemplateMaps();

		// print instances
		printInstances(network);

		// print network
		write("Printing network...\n");
		StandardPrinter printer = new StandardPrinter(
				"net/sf/orcc/backends/llvm/aot/Network.stg");
		printer.setExpressionPrinter(new LLVMExpressionPrinter());
		printer.setTypePrinter(new LLVMTypePrinter());
		printer.print(network.getSimpleName() + ".ll", path, network);
	}

	@Override
	protected boolean printActor(Actor actor) {
		// Create folder if necessary
		String folder = path + File.separator + OrccUtil.getFolder(actor);
		new File(folder).mkdirs();

		return printer.print(actor.getSimpleName(), folder, actor);
	}

}
