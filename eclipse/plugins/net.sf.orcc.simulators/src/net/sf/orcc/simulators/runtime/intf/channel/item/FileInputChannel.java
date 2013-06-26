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

package net.sf.orcc.simulators.runtime.intf.channel.item;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.orcc.simulators.runtime.impl.IntfChannel;

public class FileInputChannel extends IntfChannel {

	private FileInputStream fileInputStream;

	public FileInputChannel(String path) {
		super(path);
		try {
			fileInputStream = new FileInputStream(file);
			channel = fileInputStream.getChannel();
			buffer.flip();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isInputShutdown() {
		try {
			long position = channel.position();
			long size = channel.size();
			int remains = buffer.remaining();
			return (position >= size) & (remains == 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.isInputShutdown();
	}

	@Override
	public Byte readByte() {
		try {
			if (buffer.remaining() == 0) {
				buffer.limit(buffer.capacity());
				channel.read(buffer);
				buffer.flip();
			}
			return buffer.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.readByte();
	}
	
	@Override
	public void close(){
		super.close();
		try {
			if(fileInputStream!=null)
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
