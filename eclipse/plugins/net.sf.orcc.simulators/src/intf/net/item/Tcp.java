/*
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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

package intf.net.item;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import net.sf.orcc.runtime.impl.IntfNet;

public class Tcp extends IntfNet {

	private int SIZE = 1024;

	private boolean isServer;
	private String hostName;
	private int port;

	private ServerSocketChannel server = null;
	private SocketChannel socket = null;

	private ByteBuffer inputBuffer = ByteBuffer.allocateDirect(SIZE);
	private ByteBuffer outputBuffer = ByteBuffer.allocateDirect(SIZE);

	public Tcp(String hostName, int port) {
		inputBuffer.flip();
		outputBuffer.clear();
		setConfig(hostName, port);
		try {
			if (isServer)
				createServer();
			else
				createClient();
		} catch (IOException e) {
			this.close();
			e.printStackTrace();
		}
	}

	private void setConfig(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
		this.isServer = hostName.isEmpty();
	}

	private void createServer() throws IOException {
		server = ServerSocketChannel.open();
		server.configureBlocking(false);
		server.socket().bind(new InetSocketAddress(port));
	}

	private void createClient() throws IOException {
		socket = SocketChannel.open();
		socket.configureBlocking(false);
		socket.connect(new InetSocketAddress(hostName, port));
		socket.finishConnect();
	}

	@Override
	public boolean exists() {
		try {
			if (isServer) {
				if (socket == null) {
					socket = server.accept();
				}
				return true;
			} else {
				if (socket != null) {
					if (socket.isConnectionPending()){
						while(!socket.finishConnect());
					}
					return socket.isConnected();
				}
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void close() {
		try {
			if (socket != null)
				socket.close();
			if (isServer) {
				if (server != null) {
					server.socket().close();
					server.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		server = null;
		socket = null;
	}

	@Override
	public boolean isInputShutdown() {
		int size = inputBuffer.limit() - inputBuffer.position();
		if (socket != null) {
			if (socket.isConnected() && (size == 0)) {
				int numBytesRead = 0;
				try {
					socket.configureBlocking(false);
					inputBuffer.clear();
					numBytesRead = socket.read(inputBuffer);
					inputBuffer.flip();
				} catch (IOException e) {
					e.printStackTrace();
					return super.isInputShutdown();
				}
				if (numBytesRead > 0) {
					size = inputBuffer.limit();
				}
			}
		}
		return size == 0;
	}

	@Override
	public boolean isOutputShutdown() {
		if (socket != null) {
			if (socket.isConnected())
				return false;
		}
		return true;
	}

	@Override
	public Byte readByte() {
		return inputBuffer.get();
	}

	@Override
	public void writeByte(Byte b) {
		outputBuffer.put(b);
		outputBuffer.flip();
		try {
			socket.write(outputBuffer);
			if (outputBuffer.position() == outputBuffer.limit())
				outputBuffer.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
