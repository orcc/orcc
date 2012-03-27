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

package intf.net.item.thread;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class TcpClientThread extends Thread {

	private int SIZE = 4096;

	private volatile boolean running = true;

	private final Integer port;
	private final String address;

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	private Queue<Byte> inputQueue = new LinkedList<Byte>();
	private Queue<Byte> outputQueue = new LinkedList<Byte>();

	public TcpClientThread(String address, Integer port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			SocketAddress sockaddr = new InetSocketAddress(address, port);
			while (running) {
				try {
					// 1. creating a socket to connect to the server
					socket = new Socket();
					socket.connect(sockaddr, 5000);
					// 3. get Input and Output streams
					outputStream = new ObjectOutputStream(
							socket.getOutputStream());
					outputStream.flush();
					inputStream = new ObjectInputStream(socket.getInputStream());
					// 3: Communicating with the server
					do {
						if (inputQueue.size() < SIZE) {
							inputQueue.add(inputStream.readByte());
						} else if (!outputQueue.isEmpty()) {
							outputStream.writeByte(outputQueue.poll());
						} else {
							Thread.sleep(1);
						}
					} while (running & socket.isConnected());
					inputStream.close();
					outputStream.close();
					socket.close();
				} catch (SocketException ex) {
					Thread.currentThread().interrupt();
				} catch (EOFException e) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (InterruptedIOException ex) {
			Thread.currentThread().interrupt();
			System.out.println("Client InterruptedIOException Interrup");
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			System.out.println("Client InterruptedException Interrup");
		} catch (UnknownHostException e) {
			Thread.currentThread().interrupt();
			System.out.println("Client SocketException Interrup");
		} catch (IOException e) {
			Thread.currentThread().interrupt();
			System.out.println("Client SocketException Interrup");
		} finally {
			socket = null;
			outputStream = null;
			inputStream = null;
		}
	}

	@Override
	public void interrupt() {

	}

	public void kill() {
		running = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		if (outputStream != null & inputStream != null)
			return socket.isConnected();
		return false;
	}

	public boolean isInputShutdown() {
		return inputQueue.isEmpty();
	}

	public boolean isOutputShutdown() {
		return outputStream != null;
	}

	public void writeByte(Byte b) {
		try {
			outputStream.writeByte(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Byte readByte() {
		if (!inputQueue.isEmpty())
			return inputQueue.poll();
		return 0;
	}

	public void setOption(String name, String value) {

	}

	public String getOption(String name) {
		if (name.equals("getHostName")) {
			return socket.getInetAddress().getHostName();
		}
		return "";
	}

}
