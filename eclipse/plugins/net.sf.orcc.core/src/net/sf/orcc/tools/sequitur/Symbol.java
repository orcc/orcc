/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.sequitur;

/**
 * This class defines a symbol.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class Symbol {

	private Symbol next;

	private Symbol previous;

	/**
	 * Appends the given symbol to this symbol.
	 * 
	 * @param symbol
	 *            a symbol
	 */
	protected void append(Symbol symbol) {
		next = symbol;
		symbol.previous = this;
	}

	/**
	 * Returns a copy of this symbol without any links to other symbols.
	 * 
	 * @return a copy of this symbol without any links to other symbols
	 */
	public abstract Symbol copy();

	public Symbol getNext() {
		return next;
	}

	public Symbol getPrevious() {
		return previous;
	}

	/**
	 * Inserts this symbol between <code>predecessor</code> and
	 * <code>successor</code> symbols.
	 * 
	 * @param predecessor
	 *            symbol that will be the predecessor of this symbol
	 * @param successor
	 *            symbol that will be the successor of this symbol
	 */
	public void insertBetween(Symbol predecessor, Symbol successor) {
		predecessor.append(this);
		append(successor);
	}

	/**
	 * Returns <code>true</code> if this symbol is a guard.
	 * 
	 * @return <code>true</code> if this symbol is a guard
	 */
	public boolean isGuard() {
		return false;
	}

	/**
	 * Returns <code>true</code> if this symbol is non-terminal.
	 * 
	 * @return <code>true</code> if this symbol is non-terminal
	 */
	public boolean isNonTerminal() {
		return false;
	}

}
