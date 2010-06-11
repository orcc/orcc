/*
 * Copyright (c) 2009, IETR/INSA Rennes
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
package net.sf.orcc.debug;

/**
 * @author Matthieu Wipliez
 * 
 */
public class Location {

	private int charEnd;

	private int charStart;

	private int lineNumber;

	/**
	 * Constructs a dummy location.
	 */
	public Location() {
	}

	/**
	 * Creates a new location.
	 * 
	 * @param lineNumber
	 * @param charStart
	 * @param charEnd
	 */
	public Location(int lineNumber, int charStart, int charEnd) {
		this.charEnd = charEnd;
		this.charStart = charStart;
		this.lineNumber = lineNumber;
	}

	/**
	 * 
	 * @return end char
	 */
	public int getCharEnd() {
		return charEnd;
	}

	/**
	 * 
	 * @return start char
	 */
	public int getCharStart() {
		return charStart;
	}

	/**
	 * 
	 * @return line number
	 */
	public int getLineNumber() {
		return lineNumber;
	}

}
