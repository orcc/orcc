/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.ram;

import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.util.IrUtil;

/**
 * This class defines a RAM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RAM {

	private boolean alreadyAccessed;

	private boolean lastAccessRead;

	private int lastPortUsed;

	private final int numPorts;

	private final RAMPort[] ports;

	private Predicate predicate;

	/**
	 * Creates a new RAM.
	 */
	public RAM(int numPorts) {
		this.numPorts = numPorts;
		ports = new RAMPort[numPorts];
		for (int i = 0; i < numPorts; i++) {
			ports[i] = new RAMPort(i + 1);
		}
	}

	/**
	 * Returns the last port used by this RAM. The port number is 0-based.
	 * 
	 * @return the last port used by this RAM
	 */
	public int getLastPortUsed() {
		return lastPortUsed;
	}

	/**
	 * Returns the number of ports of this RAM.
	 * 
	 * @return the number of ports of this RAM
	 */
	public int getNumPorts() {
		return numPorts;
	}

	/**
	 * Returns the ports of this RAM.
	 * 
	 * @return the ports of this RAM
	 */
	public RAMPort[] getPorts() {
		return ports;
	}

	/**
	 * Returns the predicate of the last instruction that accessed this RAM.
	 * 
	 * @return the predicate of the last instruction that accessed this RAM
	 */
	public Predicate getPredicate() {
		return predicate;
	}

	/**
	 * Returns <code>true</code> if this RAM was already accessed for reading.
	 * 
	 * @return <code>true</code> if this RAM was already accessed for reading
	 */
	public boolean isLastAccessRead() {
		return alreadyAccessed && lastAccessRead;
	}

	/**
	 * Returns <code>true</code> if this RAM was already accessed for writing.
	 * 
	 * @return <code>true</code> if this RAM was already accessed for writing
	 */
	public boolean isLastAccessWrite() {
		return alreadyAccessed && !lastAccessRead;
	}

	/**
	 * Resets this RAM. Sets the <code>alreadyAccessed</code> to
	 * <code>false</code>, and resets the predicate this RAM was last accessed
	 * with.
	 */
	public void reset() {
		alreadyAccessed = false;
		setPredicate(IrFactory.eINSTANCE.createPredicate());
	}

	/**
	 * Updates the state of this RAM as "already accessed for reading".
	 */
	public void setLastAccessRead() {
		alreadyAccessed = true;
		lastAccessRead = true;
	}

	/**
	 * Updates the state of this RAM as "already accessed for writing".
	 */
	public void setLastAccessWrite() {
		alreadyAccessed = true;
		lastAccessRead = false;
	}

	/**
	 * Sets the last port used by this RAM. The port number is 0-based. Updates
	 * the state of the ports that have been used by this RAM at least once.
	 * 
	 * @param port
	 *            last port used
	 */
	public void setLastPortUsed(int port) {
		// set flag for read/write operation depending on lastAccessRead flag
		if (lastAccessRead) {
			ports[port % numPorts].setRead(true);
		} else {
			ports[port % numPorts].setWritten(true);
		}

		this.lastPortUsed = port;
	}

	/**
	 * Sets the predicate this RAM was last accessed with.
	 * 
	 * @param newPredicate
	 *            the predicate this RAM was last accessed with
	 */
	public void setPredicate(Predicate newPredicate) {
		if (predicate != null) {
			IrUtil.delete(predicate);
		}
		predicate = IrUtil.copy(newPredicate);
	}

}
