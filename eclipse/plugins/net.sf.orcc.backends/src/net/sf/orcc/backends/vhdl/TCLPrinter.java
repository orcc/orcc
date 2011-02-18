/*
 * Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.sf.orcc.backends.c.CMakePrinter;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.util.OrccUtil;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

/**
 * This class defines a TCL printer.
 * 
 * @author Nicolas Siret
 * 
 */
public class TCLPrinter {

	private List<String> entities;

	private HashSet<String> entitySet;

	private STGroup group;

	/**
	 * Creates a new TCL printer with the template "TCLMakeLists".
	 * 
	 * @throws IOException
	 *             if the template file could not be read
	 */
	public TCLPrinter() throws IOException {
		group = OrccUtil.loadGroup("TCLLists", "net/sf/orcc/templates/",
				CMakePrinter.class.getClassLoader());
	}

	private void computeEntityList(Instance instance) {
		if (instance.isActor()) {
			Actor actor = instance.getActor();
			String name = actor.getName();
			if (!entitySet.contains(name)) {
				entitySet.add(name);
				entities.add(name);
			}
		} else if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			String name = network.getName();
			if (!entitySet.contains(name)) {
				for (Instance subInstance : network.getInstances()) {
					computeEntityList(subInstance);
				}

				entitySet.add(name);
				entities.add(name);
			}
		}
	}

	private List<String> getEntities(Instance instance) {
		entities = new ArrayList<String>();
		entitySet = new HashSet<String>();
		computeEntityList(instance);
		return entities;
	}

	/**
	 * Prints the given network, subnetwork and instance to a file whose name is
	 * given.
	 * 
	 * @param path
	 *            output path
	 * @param network
	 *            the network to generate code for
	 * @throws IOException
	 *             if there is an I/O error
	 */
	public void printTCL(String path, Instance instance) throws IOException {
		ST template = group.getInstanceOf("TCLLists");
		template.add("name", instance.getNetwork().getName());
		template.add("entities", getEntities(instance));

		String fileName = path + File.separator + "TCLLists.tcl";

		byte[] b = template.render(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

}
