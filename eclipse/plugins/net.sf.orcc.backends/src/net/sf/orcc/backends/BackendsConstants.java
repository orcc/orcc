/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
package net.sf.orcc.backends;

/**
 * @author Antoine Lorence
 * 
 */
public class BackendsConstants {

	public static final String PROFILE = "net.sf.orcc.backends.profile";
	public static final String CHECK_ARRAY_INBOUNDS = "net.sf.orcc.backends.checkArrayInbounds";
	public static final String NEW_SCHEDULER = "net.sf.orcc.backends.newScheduler";
	
	public static final String CONVERT_MULTI2MONO = "net.sf.orcc.backends.multi2mono";
	public static final String ADDITIONAL_TRANSFOS = "net.sf.orcc.backends.add_transfos";

	public static final String INLINE = "net.sf.orcc.backends.inline";
	public static final String INLINE_NOTACTIONS = "net.sf.orcc.backends.inline.notActions";

	public static final String IMPORT_XCF = "net.sf.orcc.backends.importXCF";
	public static final String XCF_FILE = "net.sf.orcc.backends.xcfFile";
	
	public static final String IMPORT_BXDF = "net.sf.orcc.backends.importBXDF";
	public static final String BXDF_FILE = "net.sf.orcc.backends.bxdfFile";

	public static final String HMPP_NO_PRAGMAS = "net.sf.orcc.backends.c.hmpp.disablePragma";

	public static final int MIN_REPEAT_RWEND = 2;
	public static final int MIN_REPEAT_ALIGNABLE = 2;

	public static final String LLVM_TARGET_DATALAYOUT = "net.sf.orcc.backends.llvm.aot.dataLayout";
	public static final String LLVM_DEFAULT_TARGET_DATALAYOUT = "e-p:64:64:64-i1:8:8-i8:8:8-i16:16:16-i32:32:32-i64:64:64-f32:32:32-f64:64:64-v64:64:64-v128:128:128-a0:0:64-s0:64:64-f80:128:128-n8:16:32:64-S128";
	public static final String LLVM_TARGET_TRIPLE = "net.sf.orcc.backends.llvm.aot.targetTriple";
	public static final String LLVM_DEFAULT_TARGET_TRIPLE = "x86_64";
	
	public static final String TTA_DEFAULT_PROCESSORS_CONFIGURATION = "Standard";
	public static final String TTA_PROCESSORS_CONFIGURATION = "net.sf.orcc.backends.llvm.tta.configuration";
	
	public static final String TTA_CONNECTION_REDUCTION = "net.sf.orcc.backends.llvm.tta.reduceConnections";
	public static final boolean TTA_CONNECTION_REDUCTION_DEFAULT = false;
	
	public static final String FPGA_CONFIGURATION = "net.sf.orcc.backends.fpga";
	public static final String FPGA_DEFAULT_CONFIGURATION = "Stratix III (EP3SL150F1152C2)";

	public static final String JIT_BIT_ACCURATE = "net.sf.orcc.backends.llvm.jit.bitaccurate";
	public static final boolean JIT_BIT_ACCURATE_DEFAULT = false;
	
	public static final String PAPIFY = "net.sf.orcc.backends.papify";
	public static final String PAPIFY_MULTIPLEX = "net.sf.orcc.backends.papify.multiplex";
	
	public static final String GEN_WEIGHTS = "net.sf.orcc.backends.genWeights";
	public static final String GEN_WEIGHTS_FILTER = "net.sf.orcc.backends.genWeightsFltr";
	public static final String GEN_WEIGHTS_DUMP = "net.sf.orcc.backends.genWeightsDump";	

}
