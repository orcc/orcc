/*
 * Copyright (c) 2009-2013, IETR/INSA of Rennes
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
package net.sf.orcc.backends.c.hmpp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.BackendsConstants;
import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.c.hmpp.transformations.CodeletInliner;
import net.sf.orcc.backends.c.hmpp.transformations.ConstantRegisterCleaner;
import net.sf.orcc.backends.c.hmpp.transformations.DisableAnnotations;
import net.sf.orcc.backends.c.hmpp.transformations.PrepareHMPPAnnotations;
import net.sf.orcc.backends.c.hmpp.transformations.SetHMPPAnnotations;
import net.sf.orcc.backends.transform.BlockForAdder;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * HMPP back-end.
 * 
 * @author Jérôme Gorin
 * 
 */
public class HMPPBackend extends CBackend {

	protected boolean disableAnnotation;
	protected String srcPath;

	@Override
	public void doInitializeOptions() {
		super.doInitializeOptions();

		disableAnnotation = getAttribute(BackendsConstants.HMPP_NO_PRAGMAS,
				false);

		srcPath = path + File.separator + "src";
	}

	@Override
	protected void doTransformActor(Actor actor) {
		super.doTransformActor(actor);

		List<DfSwitch<?>> transformations = new ArrayList<DfSwitch<?>>();

		// Must be applied before CodeletInliner:
		transformations.add(new DfVisitor<Void>(new PrepareHMPPAnnotations()));
		transformations.add(new DfVisitor<Void>(new ConstantRegisterCleaner()));

		// Must be applied after PrepareHMPPAnnotations:
		transformations.add(new DfVisitor<Void>(new CodeletInliner()));
		// Must be applied after CodeletInliner:
		transformations.add(new SetHMPPAnnotations());

		transformations.add(new DfVisitor<CfgNode>(new ControlFlowAnalyzer()));
		transformations.add(new BlockForAdder());

		for (DfSwitch<?> transformation : transformations) {
			transformation.doSwitch(actor);
			ResourceSet set = new ResourceSetImpl();
			if (debug && !IrUtil.serializeActor(set, path, actor)) {
				OrccLogger.warnln("Error with " + transformation + " on actor "
						+ actor.getName());
			}
		}

		if (disableAnnotation) {
			new DisableAnnotations().doSwitch(actor);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.orcc.backends.c.CBackend#printInstance(net.sf.orcc.df.Instance)
	 */
	@Override
	protected boolean printInstance(Instance instance) {
		return new InstancePrinter(options).print(srcPath, instance) > 0;
	}

	@Override
	protected boolean printActor(Actor actor) {
		return new InstancePrinter(options).print(srcPath, actor) > 0;
	}

	@Override
	public boolean exportRuntimeLibrary() {
		String target = path + File.separator + "libs";
		OrccLogger.traceln("Export libraries sources into " + target + "... ");

		// Copy specific windows batch file
		if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
			copyFileToFilesystem("/runtime/C/run_cmake_with_VS_env.bat", target
					+ File.separator + "run_cmake_with_VS_env.bat", debug);
		}

		// copyFileToFilesystem("/runtime/C/README.txt", target + File.separator
		// + "README.txt", debug);

		return copyFolderToFileSystem("/runtime/C/libs", target, debug);
	}

	@Override
	protected void printCMake(Network network) {
		new CMakePrinter(network).printCMakeFiles(path);
	}
}
