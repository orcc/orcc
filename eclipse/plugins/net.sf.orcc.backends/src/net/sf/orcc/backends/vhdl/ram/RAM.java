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

	private Predicate predicate;

	private boolean waitCycleNeeded;

	/**
	 * Creates a new RAM.
	 */
	public RAM() {
	}

	public int getLastPortUsed() {
		return lastPortUsed;
	}

	/**
	 * Returns the predicate of the last instruction that accessed this RAM.
	 * 
	 * @return the predicate of the last instruction that accessed this RAM
	 */
	public Predicate getPredicate() {
		return predicate;
	}

	public boolean isLastAccessRead() {
		return alreadyAccessed && lastAccessRead;
	}

	public boolean isLastAccessWrite() {
		return alreadyAccessed && !lastAccessRead;
	}

	public boolean isWaitCycleNeeded() {
		return waitCycleNeeded;
	}

	/**
	 * Resets this RAM. Sets the <code>alreadyAccessed</code> to
	 * <code>false</code>, and resets the predicate this RAM was last accessed
	 * with.
	 */
	public void reset() {
		alreadyAccessed = false;
		predicate = IrFactory.eINSTANCE.createPredicate();
	}

	public void setLastAccessRead(boolean lastAccessRead) {
		alreadyAccessed = true;
		this.lastAccessRead = lastAccessRead;
	}

	public void setLastPortUsed(int lastPortUsed) {
		this.lastPortUsed = lastPortUsed;
	}

	/**
	 * Sets the predicate this RAM was last accessed with.
	 * 
	 * @param predicate
	 *            the predicate this RAM was last accessed with
	 */
	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public void setWaitCycleNeeded(boolean waitCycleNeeded) {
		this.waitCycleNeeded = waitCycleNeeded;
	}

}
