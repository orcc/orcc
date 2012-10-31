/*
 * Copyright (c) 2011, IRISA
 * Copyright (c) 2011, IETR/INSA of Rennes
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;

import org.eclipse.core.resources.IFile;

/**
 * This class defines a printer for "standard" objects, namely actors,
 * instances, and networks. This class supports caching in order not to
 * regenerate all files all the time, which can be annoying.
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * @author Antoine Lorence
 * 
 */
public abstract class CommonPrinter {

	/**
	 * If true, files which were not modified since the last compile will be
	 * cached and not be printed a second time.
	 */
	protected boolean keepUnchangedFiles;

	protected Map<String, Object> options;

	/**
	 * The algorithm used with MessageDigest. Can be MD, SHA, etc (see <a
	 * href="http://docs
	 * .oracle.com/javase/1.4.2/docs/guide/security/CryptoSpec.html#AppA">
	 * http://docs
	 * .oracle.com/javase/1.4.2/docs/guide/security/CryptoSpec.html#AppA</a>)
	 */
	private static final String digestAlgo = "MD5";

	/**
	 * Creates a new common printer.
	 * 
	 * @param templateName
	 *            the name of the template
	 */
	protected CommonPrinter() {
		this(true);
	}

	protected CommonPrinter(boolean keepUnchangedFiles) {
		this.keepUnchangedFiles = keepUnchangedFiles;
		options = new HashMap<String, Object>();
	}

	/**
	 * Returns the map containing options passed to backend printer
	 * 
	 * @return the options map
	 */
	public Map<String, Object> getOptions() {
		return options;
	}

	/**
	 * Returns the time of the last modification on an Actor
	 * 
	 * @param actor
	 *            an actor
	 * @return the time of the last modification
	 * @deprecated please use needToReplace() instead
	 */
	@Deprecated
	protected long getLastModified(Actor actor) {
		IFile actorFile = actor.getFile();
		if (actorFile == null) {
			// if source file does not exist, force to generate
			return Long.MAX_VALUE;
		} else {
			return actorFile.getLocalTimeStamp();
		}
	}

	/**
	 * Returns the time of the last modification on a Network
	 * 
	 * @param network
	 *            a network
	 * @return the time of the last modification
	 * @deprecated please use needToReplace() instead
	 */
	@Deprecated
	protected long getLastModified(Network network) {
		IFile networkFile = network.getFile();
		if (networkFile == null) {
			// if source file does not exist, force to generate
			return Long.MAX_VALUE;
		} else {
			return networkFile.getLocalTimeStamp();
		}
	}

	/**
	 * Return the hash array for the byte[] content
	 * 
	 * @param content
	 * @return a byte[] containing the hash
	 */
	protected byte[] hash(byte[] content) {
		try {
			// MessageDigest is NOT thread safe, it must be created locally on
			// each call, it can't be a member of this class
			MessageDigest messageDigest = MessageDigest.getInstance(digestAlgo);
			return messageDigest.digest(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	/**
	 * Return the hash array for the String content
	 * 
	 * @param content
	 * @return a byte[] containing the hash
	 */
	protected byte[] hash(String content) {
		return hash(content.getBytes());
	}

	/**
	 * Return the hash array for the file
	 * 
	 * @param file
	 * @return a byte[] containing the hash
	 */
	protected byte[] hash(File file) {
		BufferedInputStream in;
		try {
			// MessageDigest is NOT thread safe, it must be created locally on
			// each call, it can't be a member of this class
			MessageDigest messageDigest = MessageDigest.getInstance(digestAlgo);

			in = new BufferedInputStream(new FileInputStream(file));
			int theByte = 0;
			try {
				while ((theByte = in.read()) != -1) {
					messageDigest.update((byte) theByte);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				in.close();
			}
			return messageDigest.digest();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new byte[0];
	}

	/**
	 * Return true if targetFile content need to be replaced by the content's
	 * value
	 * 
	 * @param targetFile
	 * @param content
	 */
	protected boolean needToReplace(File targetFile, String content) {
		return !keepUnchangedFiles || !targetFile.exists()
				|| !MessageDigest.isEqual(hash(targetFile), hash(content));
	}

	/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param filePath
	 *            complete path of the file
	 * @return true if the file has correctly been written
	 */
	public static boolean printFile(String content, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(filePath));
			ps.print(content);
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param filePath
	 *            complete path of the file
	 * @return true if the file has correctly been written
	 */
	public static boolean printFile(CharSequence content, String filePath) {
		return printFile(content.toString(), filePath);
	}

	/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param path
	 *            complete path of the file
	 * @param fileName
	 *            name of the file
	 * @return true if the file has correctly been written
	 */
	public static boolean printFile(CharSequence content, String path,
			String fileName) {
		return printFile(content.toString(), path + File.separator + fileName);
	}

	/**
	 * Prints the given actor to a file in the folder <i>folder</i>.
	 * 
	 * @param folder
	 *            output directory
	 * @param actor
	 *            the actor to generate code for
	 * @return <code>true</code> if the actor file was cached
	 */
	public boolean print(String folder, Actor actor) {
		return true;
	}

	/**
	 * Prints the given network to a file in the folder <i>folder</i>.
	 * 
	 * @param folder
	 *            output directory
	 * @param network
	 *            the network to generate code for
	 * @return <code>true</code> if the network file was cached
	 */
	public boolean print(String folder, Network network) {
		return true;
	}

	/**
	 * Prints the given instance to a file in the folder <i>folder</i>.
	 * 
	 * @param folder
	 *            output directory
	 * @param instance
	 *            the instance to generate code for
	 * @return <code>true</code> if the instance file was cached
	 */
	public boolean print(String folder, Instance instance) {
		return true;
	}
}
