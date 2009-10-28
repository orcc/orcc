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
package net.sf.orcc.network.serialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Network;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * This class defines an XDF network writer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XDFWriter {

	/**
	 * the path of the output directory where XDF files should be written
	 */
	private File path;

	/**
	 * Creates a new network writer with the given output directory.
	 * 
	 * @param path
	 *            a file that represents the absolute path of the output
	 *            directory
	 */
	public XDFWriter(File path) {
		this.path = path;
	}

	/**
	 * Writes the given network to the directory this network writer was built
	 * with. This method recursively writes the networks that are children of
	 * the given network.
	 * 
	 * @param network
	 *            a network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void writeNetwork(Network network) throws OrccException {
		try {
			// output
			DOMImplementationRegistry registry = DOMImplementationRegistry
					.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS) registry
					.getDOMImplementation("Core 3.0 XML 3.0 LS");

			// create document
			Document document = ((DOMImplementation) impl).createDocument("",
					"XDF", null);
			writeXDF(document.getDocumentElement(), network);

			// serialize to XML
			LSOutput output = impl.createLSOutput();
			File file = new File(path, network.getName() + ".xdf");
			output.setByteStream(new FileOutputStream(file));

			// serialize the document, close the stream
			LSSerializer serializer = impl.createLSSerializer();
			serializer.getDomConfig().setParameter("format-pretty-print", true);
			serializer.write(document, output);

			output.getByteStream().close();
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		} catch (ClassCastException e) {
			throw new OrccException("DOM error", e);
		} catch (ClassNotFoundException e) {
			throw new OrccException("DOM error", e);
		} catch (InstantiationException e) {
			throw new OrccException("DOM error", e);
		} catch (IllegalAccessException e) {
			throw new OrccException("DOM error", e);
		}
	}

	/**
	 * Writes the top-level XDF element.
	 * 
	 * @param xdf
	 *            the XDF element
	 * @param network
	 *            the network
	 */
	private void writeXDF(Element xdf, Network network) {
		xdf.setAttribute("name", network.getName());
	}

}
