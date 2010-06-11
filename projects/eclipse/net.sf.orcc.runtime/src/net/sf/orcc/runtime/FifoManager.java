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
package net.sf.orcc.runtime;

import java.util.HashSet;
import java.util.Set;

/**
 * A FIFO of integers.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FifoManager {

	private static FifoManager instance = new FifoManager();
	
	public static FifoManager getInstance() {
		return instance;
	}
	
	private Set<IntFifo> emptyFifos;
	
	private Set<IntFifo> fullFifos;

	private FifoManager() {
		emptyFifos = new HashSet<IntFifo>();
		fullFifos = new HashSet<IntFifo>();
	}
	
	public void addEmptyFifo(IntFifo fifo) {
		if (!emptyFifos.contains(fifo)) {
			emptyFifos.add(fifo);
		}
	}

	public void addFullFifo(IntFifo fifo) {
		if (!fullFifos.contains(fifo)) {
			fullFifos.add(fifo);
		}
	}

	public void emptyFifos() {
		for (IntFifo fifo : emptyFifos) {
			fifo.moveTokens();
		}
		
		emptyFifos.clear();
		
		for (IntFifo fifo : fullFifos) {
			fifo.moveTokens();
		}
		
		fullFifos.clear();
	}
	
}
