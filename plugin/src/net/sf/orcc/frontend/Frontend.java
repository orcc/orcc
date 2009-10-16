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
package net.sf.orcc.frontend;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.RVCCalASTParser;
import net.sf.orcc.frontend.writer.ActorWriter;
import net.sf.orcc.ir.actor.Actor;

/**
 * This class defines an RVC-CAL front-end.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Frontend {

	/**
	 * This class implements a file filter that returns true for existing,
	 * non-hidden sub-folders, *.cal, *.xdf files.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class CalFileFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			if (pathname.exists() && !pathname.isHidden()) {
				String name = pathname.getName();
				return (pathname.isDirectory() || name.endsWith(".cal") || name
						.endsWith(".xdf"));
			} else {
				return false;
			}
		}
	}

	private File outputFolder;

	/**
	 * Creates a front-end that compiles RVC-CAL actors present in the given
	 * input folder into IR form and serializes IR actors into the given output
	 * folder. The networks are just copied to the output folder.
	 * 
	 * @param inputFolder
	 * @param outputFolder
	 * @throws OrccException
	 */
	public Frontend(String inputFolder, String outputFolder)
			throws OrccException {
		try {
			this.outputFolder = new File(outputFolder).getCanonicalFile();
			parseFilesInFolder(new File(inputFolder));
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	private void parseFilesInFolder(File folder) throws IOException,
			OrccException {
		File[] files = folder.listFiles(new CalFileFilter());
		for (File file : files) {
			if (file.isDirectory()) {
				parseFilesInFolder(file);
			} else {
				String name = file.getName();
				if (name.endsWith(".cal")) {
					// *.cal file
					Actor actor = new RVCCalASTParser(file.getCanonicalPath())
							.parse();
					new ActorWriter(actor).write(outputFolder.toString());
				} else {
					// *.xdf file
					copy(file);
				}
			}
		}
	}

	/**
	 * Copy the given file to the output folder.
	 * 
	 * @param file
	 *            a file
	 * @throws IOException
	 *             if file could not be found, or there is a I/O error
	 */
	private void copy(File file) throws IOException {
		InputStream in = new FileInputStream(file);
		OutputStream out = new FileOutputStream(outputFolder + File.separator
				+ file.getName());

		byte[] buf = new byte[8192];
		int len = in.read(buf);
		while (len > 0) {
			out.write(buf, 0, len);
			len = in.read(buf);
		}

		in.close();
		out.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws OrccException {
		if (args.length == 2) {
			new Frontend(args[0], args[1]);
		} else {
			System.err.println("Usage: Frontend "
					+ "<absolute path of input folder> "
					+ "<absolute path of output folder>");
		}
	}

}
