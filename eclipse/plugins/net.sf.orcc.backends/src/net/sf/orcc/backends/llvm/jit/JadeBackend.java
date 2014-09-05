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
package net.sf.orcc.backends.llvm.jit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.llvm.transform.ListInitializer;
import net.sf.orcc.backends.llvm.transform.StringTransformation;
import net.sf.orcc.backends.llvm.transform.TemplateInfoComputing;
import net.sf.orcc.backends.transform.CastAdder;
import net.sf.orcc.backends.transform.EmptyBlockRemover;
import net.sf.orcc.backends.transform.InstPhiTransformation;
import net.sf.orcc.backends.transform.ssa.ConstantPropagator;
import net.sf.orcc.backends.transform.ssa.CopyPropagator;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.backends.util.Validator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.df.transform.TypeResizer;
import net.sf.orcc.df.transform.UnitImporter;
import net.sf.orcc.df.util.DfUtil;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.transform.BlockCombine;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.transform.DeadCodeElimination;
import net.sf.orcc.ir.transform.DeadGlobalElimination;
import net.sf.orcc.ir.transform.DeadVariableRemoval;
import net.sf.orcc.ir.transform.RenameTransformation;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.ir.transform.SSAVariableRenamer;
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.Result;
import net.sf.orcc.util.Void;

/**
 * Jade back-end.
 * 
 * @author Jerome GORIN
 * @author Herve Yviquel
 * 
 */
public class JadeBackend extends AbstractBackend {

	private boolean bitAccurate;

	private ActorPrinter printer;

	/**
	 * Creates a new instance of the LLVM back-end.
	 */
	public JadeBackend() {
		// This back-end must generate a VTL. Configure this option with the
		// specific constructor call.
		super(true);

		printer = new ActorPrinter();
	}

	@Override
	protected void doInitializeOptions() {

		// Load options map into the code generator class
		printer.setOptions(getOptions());

		// Is the Jade back-end supposed to generate bit-accurate code ?
		bitAccurate = getOption(BackendsConstants.JIT_BIT_ACCURATE,
				BackendsConstants.JIT_BIT_ACCURATE_DEFAULT);

		// Configure the map used in RenameTransformation
		final Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_");
		renameMap.put("getw", "getw_");
		renameMap.put("index", "index_");
		renameMap.put("min", "min_");
		renameMap.put("max", "max_");
		renameMap.put("select", "select_");

		// -----------------------------------------------------
		// Transformations that will be applied on the Network
		// -----------------------------------------------------
		networkTransfos.add(new Instantiator(false));
		networkTransfos.add(new NetworkFlattener());
		// If a port is renamed in an actor, the same renaming have to be
		// applied on the network's connection
		networkTransfos.add(new RenameTransformation(renameMap));

		// -----------------------------------------------------
		// Transformations that will be applied on VTL Actors
		// -----------------------------------------------------
		if (classify) {
			childrenTransfos.add(new Classifier());
		}
		if (mergeActions) {
			childrenTransfos.add(new ActionMerger());
		}
		childrenTransfos.add(new UnitImporter());
		childrenTransfos.add(new DfVisitor<Void>(new SSATransformation()));
		childrenTransfos.add(new DeadGlobalElimination());
		if (!bitAccurate) {
			childrenTransfos.add(new TypeResizer(true, false, false, false));
		}
		childrenTransfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
		childrenTransfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
		childrenTransfos.add(new StringTransformation());
		childrenTransfos.add(new RenameTransformation(renameMap));
		childrenTransfos
				.add(new DfVisitor<Expression>(new TacTransformation()));
		childrenTransfos.add(new DfVisitor<Void>(new CopyPropagator()));
		childrenTransfos.add(new DfVisitor<Void>(new ConstantPropagator()));
		childrenTransfos.add(new DfVisitor<Void>(new InstPhiTransformation()));
		childrenTransfos.add(new DfVisitor<Expression>(new CastAdder(false,
				true)));
		childrenTransfos.add(new DfVisitor<Void>(new EmptyBlockRemover()));
		childrenTransfos.add(new DfVisitor<Void>(new BlockCombine()));
		childrenTransfos.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
		childrenTransfos.add(new DfVisitor<Void>(new ListInitializer()));
		childrenTransfos.add(new DfVisitor<Void>(new TemplateInfoComputing()));
		// computes names of local variables
		childrenTransfos.add(new DfVisitor<Void>(new SSAVariableRenamer()));
	}

	@Override
	protected void doValidate(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize, false);
	}

	@Override
	protected Result doGenerateNetwork(Network network) {
		// Generate the flattened network (.xdf file)
		OutputStream outputStream = new ByteArrayOutputStream();
		try {
			network.eResource().save(outputStream, Collections.emptyMap());
			return FilesManager.writeFile(outputStream.toString(), path,
					network.getSimpleName() + ".xdf");
		} catch (IOException e) {
			e.printStackTrace();
			throw new OrccRuntimeException(
					"Unable to serialialize the flattened network");
		}
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		// Generates the .xcf mapping file
		final CharSequence content = new Mapping(network, mapping)
				.getContentFile();
		return FilesManager.writeFile(content, path, network.getSimpleName()
				+ ".xcf");
	}

	@Override
	protected Result doGenerateActor(Actor actor) {
		final File targetFolder = new File(path, DfUtil.getFolder(actor));
		printer.setActor(actor);
		return FilesManager.writeFile(printer.getContent(),
				targetFolder.getAbsolutePath(), actor.getSimpleName());
	}
}
