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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.AbstractBackend;
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
import net.sf.orcc.df.util.DfSwitch;
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
import net.sf.orcc.ir.transform.TacTransformation;
import net.sf.orcc.tools.classifier.Classifier;
import net.sf.orcc.tools.merger.action.ActionMerger;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * LLVM back-end.
 * 
 * @author Jerome GORIN
 * @author Herve Yviquel
 * 
 */
public class JadeBackend extends AbstractBackend {

	private boolean bitAccurate;

	private final Map<String, String> renameMap;

	/**
	 * Creates a new instance of the LLVM back-end. Initializes the
	 * transformation hash map.
	 */
	public JadeBackend() {
		renameMap = new HashMap<String, String>();
		renameMap.put("abs", "abs_");
		renameMap.put("getw", "getw_");
		renameMap.put("index", "index_");
		renameMap.put("min", "min_");
		renameMap.put("max", "max_");
		renameMap.put("select", "select_");
	}

	@Override
	public void doInitializeOptions() {
		bitAccurate = getAttribute("net.sf.orcc.backends.llvm.jit.bitaccurate", false);
	}

	@Override
	protected void doTransformActor(Actor actor) {
		if (classify) {
			new Classifier().doSwitch(actor);
		}
		if (mergeActions) {
			new ActionMerger().doSwitch(actor);
		}

		new UnitImporter().doSwitch(actor);
		new DfVisitor<Void>(new SSATransformation()).doSwitch(actor);
		new DeadGlobalElimination().doSwitch(actor);

		if (!bitAccurate) {
			new TypeResizer(true, false, false, false).doSwitch(actor);
		}

		List<DfSwitch<?>> transfos = new ArrayList<DfSwitch<?>>();

		transfos.add(new DfVisitor<Void>(new DeadCodeElimination()));
		transfos.add(new DfVisitor<Void>(new DeadVariableRemoval()));
		transfos.add(new StringTransformation());
		transfos.add(new RenameTransformation(this.renameMap));
		transfos.add(new DfVisitor<Expression>(new TacTransformation()));
		transfos.add(new DfVisitor<Void>(new CopyPropagator()));
		transfos.add(new DfVisitor<Void>(new ConstantPropagator()));
		transfos.add(new DfVisitor<Void>(new InstPhiTransformation()));
		transfos.add(new DfVisitor<Expression>(new CastAdder(false, true)));
		transfos.add(new DfVisitor<Void>(new EmptyBlockRemover()));
		transfos.add(new DfVisitor<Void>(new BlockCombine()));
		transfos.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
		transfos.add(new DfVisitor<Void>(new ListInitializer()));
		transfos.add(new DfVisitor<Void>(new TemplateInfoComputing()));

		for (DfSwitch<?> transformation : transfos) {
			transformation.doSwitch(actor);
		}
	}

	@Override
	protected void doVtlCodeGeneration(List<IFile> files) {
		List<Actor> actors = parseActors(files);

		// transforms and prints actors
		transformActors(actors);
		printActors(actors);

		if (isCanceled()) {
			return;
		}

		// Finalize actor generation
		OrccLogger.traceln("Finalize actors...");
	}

	@Override
	protected void doXdfCodeGeneration(Network network) {
		Validator.checkTopLevel(network);
		Validator.checkMinimalFifoSize(network, fifoSize);

		// instantiate and flattens network
		new Instantiator(false).doSwitch(network);
		new NetworkFlattener().doSwitch(network);

		// print network
		OrccLogger.traceln("Printing network...");
		String pathName = path + "/" + network.getSimpleName() + ".xdf";
		URI uri = URI.createFileURI(pathName);

		ResourceSet set = new ResourceSetImpl();
		Resource resource = set.createResource(uri);
		resource.getContents().add(network);
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Mapping(network, mapping).print(path);
	}

	@Override
	protected boolean printActor(Actor actor) {
		String folder = path + File.separator + OrccUtil.getFolder(actor);
		return new ActorPrinter(options).print(folder, actor) > 0;
	}
}
