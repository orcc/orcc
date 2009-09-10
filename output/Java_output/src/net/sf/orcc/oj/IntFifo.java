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
package net.sf.orcc.oj;

/**
 * A FIFO of integers.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IntFifo {

	private int[] contents;

	private int read;

	private int write;

	public IntFifo(int size) {
		contents = new int[size];
	}

	public void get(boolean[] target) {
		int n = target.length;
		for (int i = 0; i < n; i++) {
			target[i] = (contents[i] != 0);
		}
		read += n;
	}

	public void get(int[] target) {
		int n = target.length;
		System.arraycopy(contents, read, target, 0, n);
		read += n;
	}

	public boolean hasRoom(int n) {
		return write < contents.length;
	}

	public boolean hasTokens(int n) {
		return read < contents.length;
	}

	public void peek(boolean[] target) {
		int n = target.length;
		for (int i = 0; i < n; i++) {
			target[i] = (contents[i] != 0);
		}
	}

	public void peek(int[] target) {
		int n = target.length;
		System.arraycopy(contents, read, target, 0, n);
	}

	public void put(boolean[] source) {
		int n = source.length;
		for (int i = 0; i < n; i++) {
			contents[i] = source[i] ? 1 : 0;
		}
		write += n;
	}

	public void put(int[] source) {
		int n = source.length;
		System.arraycopy(source, 0, contents, write, n);
		write += n;
	}

}
