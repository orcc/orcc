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

/**
 * A FIFO of integers.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IntFifo {

	private int[] contents;

	private int read;

	private int size;

	private int write;

	public IntFifo(int size) {
		this.size = size;
		contents = new int[size];
	}

	public void get(boolean[] target) {
		peek(target);
		read += target.length;
	}

	public void get(int[] target) {
		peek(target);
		read += target.length;
	}

	public boolean hasRoom(int n) {
		if ((size - write) >= n) {
			return true;
		} else {
			FifoManager.getInstance().addFullFifo(this);
			return false;
		}
	}

	public boolean hasTokens(int n) {
		if ((write - read) >= n) {
			return true;
		} else {
			FifoManager.getInstance().addEmptyFifo(this);
			return false;
		}
	}
	
	void moveTokens() {
		// number of tokens in the FIFO
		int n = write - read;

		// if the read pointer is greater than the number of tokens, we can safely move
		// the tokens at the beginning of the FIFO
		if (read > n) {
			// there is room to copy the not-read-yet tokens at the beginning of the FIFO
			if (n > 0) {
				// only copy if there are tokens
				System.arraycopy(contents, read, contents, 0, n);
			}
			
			read = 0;
			write = n;
		}
	}

	public void peek(boolean[] target) {
		int n = target.length;
		for (int i = 0; i < n; i++) {
			target[i] = (contents[read + i] != 0);
		}
	}

	public void peek(int[] target) {
		int n = target.length;
		System.arraycopy(contents, read, target, 0, n);
	}

	public void put(boolean[] source) {
		int n = source.length;
		for (int i = 0; i < n; i++) {
			contents[write + i] = source[i] ? 1 : 0;
		}
		write += n;
	}

	public void put(int[] source) {
		int n = source.length;
		System.arraycopy(source, 0, contents, write, n);
		write += n;
	}
	
	public String toString() {
		return write + "/" + read;
	}

	public void put(String[] check) {
		// TODO Auto-generated method stub
		
	}

}
