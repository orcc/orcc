/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package std.stdio.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

import net.sf.orcc.runtime.impl.GenericSource;
import net.sf.orcc.simulators.AbstractSimulator;
import net.sf.orcc.util.OrccLogger;

/**
 * This class defines native functions for the Source actor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Source extends GenericSource {

	// private static String inputStimulus;

	private static RandomAccessFile in;

	private static int loopsCount;

	public static void source_exit(BigInteger status) {
		OrccLogger.traceln("Exit signal called by application. Return code: "
				+ status.toString());
		AbstractSimulator.stop(status);
	}

	public static BigInteger source_getNbLoop() {
		return BigInteger.valueOf(nbLoops);
	}

	public static Boolean source_isMaxLoopsReached() {
		return loopsCount <= 0;
	}

	public static void source_decrementNbLoops() {
		--loopsCount;
	}

	public static void source_init() {
		try {
			in = new RandomAccessFile(inputStimulus, "r");
		} catch (FileNotFoundException e) {
			String msg = "File not found: \"" + inputStimulus + "\"";
			throw new RuntimeException(msg, e);
		}
		loopsCount = nbLoops;
	}

	public static void source_readNBytes(byte outTable[],
			BigInteger nbTokenToRead) {
		try {
			in.read(outTable, 0, nbTokenToRead.intValue());
		} catch (IOException e) {
			String msg = "I/O error when reading file \"" + inputStimulus
					+ "\"";
			throw new RuntimeException(msg, e);
		}
	}

	public static void source_rewind() {
		try {
			// and for Damien, no there are no rewind on RandomAccessFile :)
			in.seek(0L);
		} catch (IOException e) {
			String msg = "I/O error when rewinding file \"" + inputStimulus
					+ "\"";
			throw new RuntimeException(msg, e);
		}
	}

	public static BigInteger source_sizeOfFile() {
		try {
			if (in == null) {
				return BigInteger.ZERO;
			}
			return BigInteger.valueOf(in.length());
		} catch (IOException e) {
			String msg = "I/O error when getting size of file \""
					+ inputStimulus + "\"";
			throw new RuntimeException(msg, e);
		}
	}

}
