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

package net.sf.orcc.simulators.runtime.system.io.item;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.orcc.simulators.runtime.impl.SystemIO;

/**
 * This class defines native functions for the File unit.
 * 
 * This class uses the SimulatorDecriptor class to handle descriptors.
 * 
 * @author Thavot Richard
 * 
 */
public class AccessFile extends SystemIO {

	private RandomAccessFile randomAccessFile;

	public AccessFile(String path) {
		super(path);
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void close() {
		try {
			randomAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.close();
	}

	@Override
	public boolean isAccessFile() {
		return true;
	}

	public Byte readByte() {
		try {
			return randomAccessFile.readByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Long sizeOfFile() {
		try {
			return randomAccessFile.length();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Long(0L);
	}

	public void seek(Integer pos) {
		try {
			randomAccessFile.seek(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer filePointer() {
		try {
			return (int) randomAccessFile.getFilePointer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void readByte(byte[] buf, Integer count) {
		try {
			randomAccessFile.read(buf, 0, count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeByte(byte[] buf, Integer count) {
		try {
			randomAccessFile.write(buf, 0, count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeByte(Byte v) {
		try {
			randomAccessFile.writeByte(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
