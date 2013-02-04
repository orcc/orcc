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

package net.sf.orcc.simulators.runtime.impl;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IntfChannel extends GenericSource implements Intf {

	protected File file;
	protected FileChannel channel;
	protected ByteBuffer buffer = ByteBuffer.allocate(4096);

	public IntfChannel() {
	}

	public IntfChannel(String path) {
		if (path.isEmpty())
			path = super.getInputStimulus();
		if (!path.contains(File.separator)) {
			String dir = null;
			if (super.getInputStimulus().contains("."))
				dir = new File(super.getInputStimulus()).getParent();
			else
				dir = super.getInputStimulus();
			path = dir + File.separator + path;
		}
		file = new File(path);
	}

	@Override
	public boolean isIntfChannel() {
		return true;
	}

	public boolean exists() {
		return false;
	}

	public boolean isInputShutdown() {
		return true;
	}

	public boolean isOutputShutdown() {
		return true;
	}

	public void writeByte(Byte b) {

	}

	public Byte readByte() {
		return 0;
	}

	public void setOption(String name, String value) {

	}

	public String getOption(String name) {
		return "";
	}

	@Override
	public void close() {
		if (channel != null)
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		buffer.clear();
	}

}
