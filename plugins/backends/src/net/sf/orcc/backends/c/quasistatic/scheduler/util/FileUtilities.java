/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.sf.orcc.backends.c.quasistatic.scheduler.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author vimartin
 */
public class FileUtilities {

	public static final int CAL_TO_CALML = 1;
	public static final int NL_TO_XNL = 2;
	public static final int XDF_TO_XNL = 3;

	/**
	 * 
	 * @param sourceFile
	 * @param destinationFile
	 */
	public static void copyFile(File sourceFile, File destinationFile) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					destinationFile));
			BufferedReader reader = new BufferedReader(new FileReader(
					sourceFile));
			int c;
			while ((c = reader.read()) != -1) {
				writer.write(c);
			}
			writer.close();
			reader.close();
		} catch (IOException ex) {
			Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	public static boolean createDirectory(String path) {
		File directory = new File(path + File.separator);

		return directory.mkdirs();

	}

	/**
	 * Creates a file called <code>fileName</code> in the selected output
	 * directory, adding the content to that file.
	 */
	public static void createFile(String outputDirectory, String fileName,
			String content) {
		try {
			File file = new File(outputDirectory + fileName);
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(FileUtilities.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Removes a file
	 * 
	 * @param file
	 *            file to remove
	 * @return operation's success
	 */
	public static boolean deleteFile(File file) {
		// If its a directory, fist of all, removes all the contained files
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}

		return file.delete();

	}

	public static void fprintf(File file, String content) {
		try {
			boolean created = file.createNewFile();
			if (created)
				System.out.println("File created: " + file.getAbsolutePath());
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
	
	public static String[] fscanf(BufferedReader reader, String separator,
			int numArgs) {
		String[] result = new String[numArgs];
		int row = 0;
		try {
			String line = reader.readLine();
			if (line == null)
				return result;
			String[] components = line.split(separator);
			for (int i = 0; row < numArgs && i < components.length; i++)
				if (!components[i].equals("")) {
					result[row] = components[i];
					row++;
				}
		} catch (IOException ex) {
			Logger.getLogger(FileUtilities.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return result;

	}
	
	public static String getFileName(File file){
		String name = file.getName();
		int endIndex = name.contains(".")? name.indexOf("."):name.length();
		return name.substring(0, endIndex);
	}
	
}
