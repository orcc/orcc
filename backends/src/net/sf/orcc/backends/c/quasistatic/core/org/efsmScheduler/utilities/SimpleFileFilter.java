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

package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * Represents a file filter which can be used to implement both
 * java.oi.FileFilter and javax.swing.filechooser.FileFilter object.
 * 
 * @author Victor Martin
 */
public class SimpleFileFilter extends javax.swing.filechooser.FileFilter
		implements FileFilter {

	/**
	 * List of allowed extensions. For example {jpg, bmp, png, ... }
	 */
	private ArrayList<String> extensions;

	/**
	 * False if the filter accepts directories.
	 */
	private boolean filesOnly;

	public SimpleFileFilter(ArrayList<String> extensions) {
		this(extensions, false);
	}

	public SimpleFileFilter(ArrayList<String> extensions, boolean filesOnly) {
		super();
		for (int i = 0; i < extensions.size(); i++)
			extensions.get(i).toLowerCase();
		this.extensions = extensions;
		this.filesOnly = filesOnly;
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory())
			return !filesOnly;

		String fileName = file.getName().toLowerCase();
		if (!fileName.contains("."))
			return false;
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1,
				fileName.length());

		return extensions.contains(extension);

	}

	@Override
	public String getDescription() {
		String str = "";
		for (int i = 0; i < extensions.size(); i++)
			str += extensions.get(i).toUpperCase()
					+ ((i == extensions.size() - 1) ? " " : ", ");
		str += "Files";
		return str;
	}

}
