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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.TypeMismatchException;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim.Variable;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Contains some general methods used in others classes
 * 
 * @author Victor Martin 5.12.2008
 */
public abstract class Util {

	/**
	 * Remove all the nodes which name is "#text"
	 * 
	 * @param nl
	 *            : node list
	 * @return a Vector of nodes which doesn't contain any node with "#text"
	 *         name
	 */
	public static Vector<Node> removeTextNodes(NodeList nl) {
		Vector<Node> nonTextNodes = new Vector<Node>();
		for (int i = 0; i < nl.getLength(); i++) {
			if (!nl.item(i).getNodeName().equals("#text"))
				nonTextNodes.add(nl.item(i));
		}
		return nonTextNodes;
	}

	/**
	 * Union of two sets of Strings.
	 * 
	 * @param s1
	 * @param s2
	 * @return union of s1 and s2
	 */
	public static Set<String> union(Set<String> s1, Set<String> s2) {
		// Checks if s1 or s2 are nulls
		if (s1 == null)
			s1 = new HashSet<String>();
		if (s2 == null)
			s2 = new HashSet<String>();

		Set<String> s = new HashSet<String>(s1);
		s.addAll(s2);

		return s;
	}

	/**
	 * intersection of two sets of Strings.
	 * 
	 * @param s1
	 * @param s2
	 * @return intersection of s1 and s2
	 */
	public static Set<String> intersection(Set<String> s1, Set<String> s2) {
		// Checks if s1 or s2 are nulls
		if (s1 == null)
			s1 = new HashSet<String>();
		if (s2 == null)
			s2 = new HashSet<String>();

		Set<String> s = new HashSet<String>(s1);
		s.retainAll(s2);

		return s;
	}

	/**
	 * BitAND between a and b
	 * 
	 * @param a
	 * @param b
	 * @return bitant opeation
	 */
	public static int bitand(int a, int b) {
		int c = a & b;
		if (c != 0)
			c = 1;
		return c;

	}

	/**
	 * Checks if x is true o false as boolean value
	 * 
	 * @param x
	 * @return 0 -> false 1 -> true
	 * @throws TypeMismatchException
	 */
	public static boolean isTrue(int x) throws TypeMismatchException {
		switch (x) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			throw new TypeMismatchException(x + "");
		}

	}

	/**
	 * @param x
	 * @return if x is boolean
	 */
	public static boolean isBoolean(int x) {
		return x == 0 || x == 1;
	}

	/**
	 * Copy hashMap in other hashMap which returns
	 */
	public static HashMap<String, Variable> copyMap(HashMap<String, Variable> M) {
		HashMap<String, Variable> M1 = new HashMap<String, Variable>(M);
		for (String s : M1.keySet()) {
			Variable w = M1.get(s);
			Variable newW = new Variable(w);
			M1.put(s, newW);
		}
		return M1;
	}

	/**
	 * Get a File with extension extensionFile using FileChooser. If extension
	 * file is null, it only acepts directories. If currentDirectoryPath is
	 * null, it will use the default constructor of JFileChooser
	 * 
	 * @param extensionFile
	 * @param multipleSelectionEnabled
	 * @param currentDirectoryPath
	 * @return the resulting file
	 */
	public static File[] getFile(FileFilter filter,
			boolean multipleSelectionEnabled, String currentDirectoryPath,
			int mode) {
		JFileChooser filechooser;
		if (currentDirectoryPath != null)
			filechooser = new JFileChooser(currentDirectoryPath);
		else
			filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(mode);
		filechooser.setMultiSelectionEnabled(multipleSelectionEnabled);
		filechooser.setFileFilter(filter);

		int output = filechooser.showSaveDialog(null);
		// Si no ha aceptado, salimos
		if (output != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		File[] files;
		if (multipleSelectionEnabled)
			files = filechooser.getSelectedFiles();
		else {
			files = new File[1];
			files[0] = filechooser.getSelectedFile();
		}

		return files;
	}

	/**
	 * Get a File with extension extensionFile using FileChooser. If extension
	 * file is null, it only acepts directories. If currentDirectoryPath is
	 * null, it will use the default constructor of JFileChooser
	 * 
	 * @param extensionFile
	 * @param multipleSelectionEnabled
	 * @param currentDirectoryPath
	 * @return the resulting file
	 */
	public static File[] getFile(final String extensionFile,
			boolean multipleSelectionEnabled, String currentDirectoryPath) {
		JFileChooser filechooser;
		if (currentDirectoryPath != null)
			filechooser = new JFileChooser(currentDirectoryPath);
		else
			filechooser = new JFileChooser();
		int mode = (extensionFile == null) ? JFileChooser.DIRECTORIES_ONLY
				: JFileChooser.FILES_ONLY;
		filechooser.setFileSelectionMode(mode);
		filechooser.setMultiSelectionEnabled(multipleSelectionEnabled);
		filechooser.setFileFilter(new FileFilter() {

			public String getDescription() {
				String description;
				if (extensionFile == null)
					description = "Directories only";
				else
					description = (extensionFile.toUpperCase() + " Files (*."
							+ extensionFile + ")");

				return description;
			}

			public boolean accept(File f) {
				boolean accept = (extensionFile == null) ? f.isDirectory() : f
						.getName().toLowerCase().endsWith(
								"." + extensionFile.toLowerCase())
						|| f.isDirectory();
				return accept;
			}
		});

		int output = filechooser.showSaveDialog(null);
		// Si no ha aceptado, salimos
		if (output != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		File[] files;
		if (multipleSelectionEnabled)
			files = filechooser.getSelectedFiles();
		else {
			files = new File[1];
			files[0] = filechooser.getSelectedFile();
		}
		// Adds extension if file lacks it
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (mode != JFileChooser.DIRECTORIES_ONLY
					&& !file.getName().toLowerCase().endsWith(
							"." + extensionFile.toLowerCase())) {
				File renamedFile = new File(file.getPath() + "."
						+ extensionFile);
				files[i] = renamedFile;
			}
		}
		return files;
	}

	/**
	 * Set enabled of all the components of a container
	 * 
	 * @param container
	 * @param enabled
	 */
	public static void setEnabledAllComponents(JPanel container, boolean enabled) {
		if (container == null)
			return;
		Component[] components = container.getComponents();
		if (components == null)
			return;
		for (int i = 0; i < components.length; i++) {
			components[i].setEnabled(enabled);
		}
	}

	/**
	 * This method reads a text file and insert all the file's content into a
	 * JTextArea
	 * 
	 * @param file
	 *            input text file
	 * @return instance of JTextArea filled with file's data
	 */
	public static JTextArea readFileIntoJTextArea(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str;
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			while ((str = br.readLine()) != null) {
				textArea.append(str + "\n");
			}
			return textArea;
		} catch (IOException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	 * Reads all files from a directory.
	 * 
	 * @param directory
	 *            input directory
	 * @return an array of files
	 */
	public static File[] readAllFilesFromADirectory(File directory) {
		if (!directory.isDirectory())
			return null;
		String[] fileNames = directory.list();
		File[] files = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			files[i] = new File(directory.getAbsolutePath() + File.separator
					+ fileNames[i]);
		}
		return files;
	}

	/**
	 * This method concatenates two ArrayList
	 * 
	 * @param from
	 * @param to
	 * @return an arraylist resulting of concat <code>from<\code><code>to<\code>
	 */
	@SuppressWarnings( { "unchecked" })
	public static ArrayList concatArrayList(ArrayList from, ArrayList to) {
		ArrayList array = to;
		for (int i = 0; i < from.size(); i++) {
			array.add(from.get(i));
		}
		return array;
	}

	/**
	 * 
	 * @param fileName
	 * @return the file name without the extension file <file_name>.<exntension>
	 *         -> <file_name>
	 */
	public static String removeFileExtension(String fileName) {
		int end = fileName.indexOf(".");
		return fileName.substring(0, end);
	}
}
