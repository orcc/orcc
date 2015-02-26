/*
 * Copyright (c) 2015, IETR/INSA of Rennes, UPM Madrid
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
package net.sf.orcc.backends.c.dsp;

import static net.sf.orcc.backends.BackendsConstants.BXDF_FILE;
import static net.sf.orcc.backends.BackendsConstants.IMPORT_BXDF;
import static net.sf.orcc.backends.BackendsConstants.DSP_CONFIGURATION;
import static net.sf.orcc.backends.BackendsConstants.DSP_DEFAULT_CONFIGURATION;

import java.io.File;

import net.sf.orcc.backends.c.CBackend;
import net.sf.orcc.backends.util.DSP;
import net.sf.orcc.backends.util.Mapping;
import net.sf.orcc.df.Network;
import net.sf.orcc.tools.mapping.XmlBufferSizeConfiguration;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.Result;

/**
 * C backend for DSP
 * 
 * @author Miguel Chavarrias
 * @author Alexandre Sanchez
 * 
 */
public class DSPBackend extends CBackend {
	
	private CcsProjectPrinter ccsProjectPrinter;
	private DSP dsp;
	
	public DSPBackend() {
		ccsProjectPrinter = new CcsProjectPrinter();
	}

	@Override
	protected void doInitializeOptions() {
		super.doInitializeOptions();

		// Configure paths
		srcPath = outputPath + File.separator + "src";

		// Configure DSP
		dsp = DSP.builder(getOption(DSP_CONFIGURATION, DSP_DEFAULT_CONFIGURATION));
		ccsProjectPrinter.setDsp(dsp);
	}
	
	@Override
	protected void beforeGeneration(Network network) {
		network.computeTemplateMaps();

		// if required, load the buffer size from the mapping file
		if (getOption(IMPORT_BXDF, false)) {
			File f = new File(getOption(BXDF_FILE, ""));
			new XmlBufferSizeConfiguration().load(f, network);
		}

		if(network.getVertex(network.getSimpleName()) != null) {
			final StringBuilder warnMsg = new StringBuilder();
			warnMsg.append('"').append(network.getSimpleName()).append('"');
			warnMsg.append(" is the name of both the network you want to generate");
			warnMsg.append(" and a vertex in this network.").append('\n');
			warnMsg.append("The 2 entities will be generated");
			warnMsg.append(" in the same file. Please rename one of these elements to prevent");
			warnMsg.append(" unwanted overwriting.");
			OrccLogger.warnln(warnMsg.toString());
		}
	}

	@Override
	protected Result doLibrariesExtraction() {
		final Result result = FilesManager.extract("/runtime/DSP/README.txt", outputPath);

		result.merge(FilesManager.extract("/runtime/C/libs/orcc-native", outputPath));
		result.merge(FilesManager.extract("/runtime/C/libs/orcc-runtime", outputPath));
		result.merge(FilesManager.extract("/runtime/C/libs/roxml", outputPath));

		result.merge(FilesManager.extract("/runtime/DSP/config/config.cfg", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/config/config.h", srcPath));

		//! TODO : Merge DSP-native and orcc-native in this backend ASAP
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/cache.c", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/cache.h", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/medidas.c", srcPath));
		result.merge(FilesManager.extract("/runtime/DSP/libs/dsp-native/medidas.h", srcPath));
		
		return result;
	}

	@Override
	protected Result doAdditionalGeneration(Network network) {
		final Result result = Result.newInstance();
		final Mapping mapper = new Mapping(network, mapping);

		result.merge(FilesManager.writeFile(mapper.getContentFile(), srcPath,
				network.getSimpleName() + ".xcf"));

		String libOrccNativePath = outputPath + File.separator + "orcc-native";
		String libOrccRuntimePath = outputPath + File.separator + "orcc-runtime";
		String libRoxmlPath = outputPath + File.separator + "roxml";

		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcxxml(dsp), srcPath, dsp.getDevice() + ".ccxml"));

		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject(network.getSimpleName()), srcPath, ".project"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject("orcc-native"), libOrccNativePath, ".project"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject("orcc-runtime"), libOrccRuntimePath, ".project"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getProject("roxml"), libRoxmlPath, ".project"));

		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, network.getSimpleName()), srcPath, ".cproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, "orcc-native"), libOrccNativePath, ".cproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, "orcc-runtime"), libOrccRuntimePath, ".cproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCProject(dsp, "roxml"), libRoxmlPath, ".cproject"));
		
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, true), srcPath, ".ccsproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, false), libOrccNativePath, ".ccsproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, false), libOrccRuntimePath, ".ccsproject"));
		result.merge(FilesManager.writeFile(ccsProjectPrinter.getCcsProject(dsp, false), libRoxmlPath, ".ccsproject"));
		
		return result;
	}
		
}
