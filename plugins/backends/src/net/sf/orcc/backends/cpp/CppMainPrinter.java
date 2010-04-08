/*
 * Copyright (c) 2010,
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the 
 *     names of its contributors may be used to endorse or promote products 
 *     derived from this software without specific prior written permission.
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
package net.sf.orcc.backends.cpp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.TemplateGroupLoader;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

/**
 * This class defines a C++ CMake printer.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class CppMainPrinter {

	private STGroup group;

	/**
	 * Creates a new CMake printer with the template "Cpp_main".
	 * 
	 * @throws IOException
	 *             if the template file could not be read
	 */
	public CppMainPrinter() throws IOException {
		group = TemplateGroupLoader.loadGroup("Cpp_main");
	}

	/**
	 * Prints the given network to a file whose name is given. debugFifos
	 * specifies whether debug information should be printed about FIFOs, and
	 * fifoSize is the default FIFO size.
	 * 
	 * @param path
	 *            output path
	 * @param network
	 *            the network to generate code for
	 * @throws IOException
	 *             if there is an I/O error
	 */
	public void printMain(String path, List<Network> networks,
			Map<Connection, Integer> globalFifoMap) throws IOException {
		ST template = group.getInstanceOf("Cpp_main");
		template.add("networks", networks);
		template.add("globalFifoMap", globalFifoMap);

		String fileName = path + File.separator + "main.cpp";

		byte[] b = template.render(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

}
