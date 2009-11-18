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
 * This interface defines a communication FIFO to be connected to each port.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public interface ICommunicationFifo {

	/**
	 * Returns true if FIFO free space can contain 'n' Tokens.
	 * 
	 * @param n
	 *            number of tokens to be copied into FIFO
	 * @return true if FIFO free space can contain 'n' Tokens.
	 */
	public boolean hasRoom(int n);

	/**
	 * Returns true if FIFO has at least 'n' Tokens left.
	 * 
	 * @param n
	 *            number of tokens to be read from FIFO
	 * @return true if FIFO has at least 'n' Tokens left.
	 */
	public boolean hasTokens(int n);

	/**
	 * Copy source array to free rooms in FIFO as required by "hasRooms".
	 * 
	 * @param source
	 *            source array to be copied to FIFO
	 */
	public void put(int[] source);	
	//public void put(boolean[] source);	

	/**
	 * Feeds target array with next tokens as required by "hasTokens" 
	 * without flushing them.
	 * 
	 * @param target
	 *            target array for receiving next FIFO tokens
	 */
	public void peek(int[] target);
	//public void peek(boolean[] target);
	
	
	/**
	 * Feeds target array with next tokens as required by "hasTokens" 
	 * without and flush them.
	 * 
	 * @param target
	 *            target array for receiving next FIFO tokens
	 */
	public void get(int[] target);
	//public void get(boolean[] target);
}
