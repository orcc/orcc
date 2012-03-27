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

import intf.net.item.thread.TcpClientThread;

public class TcpClient extends Tcp {

	private TcpClientThread clientThread;

	public TcpClient(String address, int parseInt) {
		clientThread = new TcpClientThread(address, 1234);
		clientThread.start();
	}

	@Override
	public void close() {
		try {
			clientThread.kill();
			clientThread.join();
			clientThread = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean exists() {
		return clientThread.isConnected();
	}

	@Override
	public boolean isInputShutdown() {
		return clientThread.isInputShutdown();
	}

	@Override
	public boolean isOutputShutdown() {
		return clientThread.isOutputShutdown();
	}

	@Override
	public void writeByte(Byte b) {
		clientThread.writeByte(b);
	}

	@Override
	public Byte readByte() {
		return clientThread.readByte();
	}

	@Override
	public void setOption(String name, String value) {
		clientThread.setOption(name, value);
	}

	@Override
	public String getOption(String name) {
		return clientThread.getOption(name);
	}

}

