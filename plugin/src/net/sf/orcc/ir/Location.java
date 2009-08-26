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
package net.sf.orcc.ir;

/**
 * @author Matthieu Wipliez
 * 
 */
public class Location {

	private int endColumn;

	private int endLine;

	private String file;

	private int startCol;

	private int startLine;

	/**
	 * Constructs a dummy location.
	 */
	public Location() {
		file = "";
		// other fields are initialized to 0.
	}

	/**
	 * Constructs a location from the specified file, start line and column, end
	 * line and column.
	 * 
	 * @param file
	 *            The file name.
	 * @param startLine
	 *            The line where the location starts.
	 * @param startColumn
	 *            The column where the location starts.
	 * @param endLine
	 *            The line where the location ends.
	 * @param endColumn
	 *            The column where the location ends.
	 */
	public Location(String file, int startLine, int startColumn, int endLine,
			int endColumn) {
		this.file = file;
		this.startLine = startLine;
		this.startCol = startColumn;
		this.endLine = endLine;
		this.endColumn = endColumn;
	}

	public String toString() {
		String res = "File \"" + file + "\", line " + startLine;
		if (startLine == endLine) {
			res += ", characters " + startCol + "-" + endColumn;
		} else {
			res += ", character " + startCol + " (end at line " + endLine;
			res += ", character " + endColumn + ")";
		}
		return res;
	}

}
