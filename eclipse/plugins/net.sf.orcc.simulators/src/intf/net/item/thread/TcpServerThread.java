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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

public class TcpServerThread extends Thread {

	private int SIZE = 4096;

	private volatile boolean running = true;

	private final Integer port;

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	private Queue<Byte> inputQueue = new LinkedList<Byte>();

	public TcpServerThread(Integer port) {
		this.port = port;
	}

	private ServerSocket serverSocket;

	@Override
	public void run() {
		try {
			while (running) {
				try {
					// 1. creating a server socket
					serverSocket = new ServerSocket(port);
					// 2. Wait for connection
					socket = serverSocket.accept();
					// 3. get Input and Output streams
					outputStream = new ObjectOutputStream(
							socket.getOutputStream());
					outputStream.flush();
					inputStream = new ObjectInputStream(socket.getInputStream());
					// 4. Communicating with the client
					do {
						if (inputQueue.size() < SIZE) {
							inputQueue.add(inputStream.readByte());
						} else {
							Thread.sleep(1);
						}
					} while (running & socket.isConnected());
					System.out.println("Connection Closed");
					inputStream.close();
					outputStream.close();
					socket.close();
					serverSocket.close();
				} catch (SocketException e) {
					Thread.currentThread().interrupt();
				} catch (IOException e) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Server Interrompu via InterruptedException");

		} finally {
			serverSocket = null;
			socket = null;
			outputStream = null;
			inputStream = null;
		}
	}

	@Override
	public void interrupt() {
		if (!running) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void kill() {
		running = false;
		try {
			if (socket != null)
				socket.close();
			else
				serverSocket.close();
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

	public String getOption(String name) {
		if (name.equals("getHostName")) {
			return socket.getInetAddress().getHostName();
		}
		return "";
	}

	public void setOption(String name, String value) {

	}

}
