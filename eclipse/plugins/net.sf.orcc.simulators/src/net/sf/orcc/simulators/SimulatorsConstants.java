/*
 * Copyright (c) 2013, University of Rennes 1 / IRISA
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
 *   * Neither the name of the University of Rennes 1 / IRISA nor the names of its
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
package net.sf.orcc.simulators;

/**
 * 
 * @author Hervé Yviquel
 * 
 */
public class SimulatorsConstants {

	/**
	 * When enabled, interpreter compare the output of the decoder with a
	 * reference video.
	 */
	public static final String ENABLE_COMP = "net.sf.orcc.simulators.enableComparison";

	/**
	 * When set, a golden reference has to be compared with the ouput data of
	 * the network.
	 */
	public static final String GOLDEN_REFERENCE = "net.sf.orcc.simulators.goldenReference";

	/**
	 * Golden reference file associated with an Orcc configuration.
	 */
	public static final String GOLDEN_REFERENCE_FILE = "net.sf.orcc.simulators.goldenReferenceFile";
	/**
	 * Input stimulus file associated with an Orcc configuration.
	 */
	public static final String INPUT_STIMULUS = "net.sf.orcc.simulators.inputStimulus";

	/**
	 * Output file associated with an Orcc configuration.
	 */
	public static final String OUTPUT_FILE = "net.sf.orcc.simulators.outputFile";
	
	/**
	 * Parameter used to set max number of times an input file is read.
	 */
	public static final String LOOP_NUMBER = "net.sf.orcc.simulators.loopNumber";

	/**
	 * Parameter used to set max number of times an input file is read.
	 */
	public static final String FRAMES_NUMBER = "net.sf.orcc.simulators.framesNumber";

	/**
	 * Folder where the compiled Actors can be found.
	 */
	public static final String VTL_FOLDER = "net.sf.orcc.simulators.VTLFolder";

	/**
	 * Simulator reference video for testing video output
	 */
	public static final String REFERENCE_FILE = "net.sf.orcc.simulators.compareFile";

	public static final String PROFILE = "net.sf.orcc.simulators.profile";

	public static final String PROFILE_FOLDER = "net.sf.orcc.simulators.profileFolder";

	/**
	 * Cast the variables or ports using the TypeResizer transformation
	 */
	public static final String TYPE_RESIZER = "net.sf.orcc.core.transform.typeResizer";
	/** TypeResizer Transformation Option: cast to 2^n bits */
	public static final String TYPE_RESIZER_CAST_TO2NBITS = "net.sf.orcc.core.transform.typeResizer.to2nbits";
	/** TypeResizer Transformation Option: cast to 32 bits */
	public static final String TYPE_RESIZER_CAST_TO32BITS = "net.sf.orcc.core.transform.typeResizer.to32bits";
	/** TypeResizer Transformation Option: cast native ports */
	public static final String TYPE_RESIZER_CAST_NATIVEPORTS = "net.sf.orcc.core.transform.typeResizer.nativePorts";
	/** TypeResizer Transformation Option: cast boolean to integer */
	public static final String TYPE_RESIZER_CAST_BOOLTOINT = "net.sf.orcc.core.transform.typeResizer.booltoint";

}
