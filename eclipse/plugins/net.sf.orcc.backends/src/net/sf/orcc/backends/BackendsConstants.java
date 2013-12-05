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

	public static final String GENETIC_ALGORITHM = "net.sf.orcc.backends.geneticAlgorithm";
	public static final String INSTRUMENT_NETWORK = "net.sf.orcc.backends.instrumentNetwork";
	public static final String NEW_SCHEDULER = "net.sf.orcc.backends.newScheduler";
	public static final String NEW_SCHEDULER_TOPOLOGY = "net.sf.orcc.backends.newScheduler.topology";

	public static enum Topology {
		Ring, Mesh
	}

	public static final String THREADS_NB = "net.sf.orcc.backends.processorsNumber";

	public static final String CONVERT_MULTI2MONO = "net.sf.orcc.backends.multi2mono";
	public static final String ADDITIONAL_TRANSFOS = "net.sf.orcc.backends.add_transfos";

	public static final String PROFILE = "net.sf.orcc.backends.profile";

	public static final String IMPORT_XCF = "net.sf.orcc.backends.importXCF";
	public static final String XCF_FILE = "net.sf.orcc.backends.xcfFile";

	public static final String HMPP_NO_PRAGMAS = "net.sf.orcc.backends.c.hmpp.disablePragma";

	public static final int MIN_REPEAT_SIZE_RWEND = 2;

	public static final int MIN_VECTORIZABLE = 2;

	public static final String VECTORIZABLE = "VECTORIZABLE";
	public static final String VECTORIZABLE_ALWAYS = "VECTORIZABLE_ALWAYS";	
}
