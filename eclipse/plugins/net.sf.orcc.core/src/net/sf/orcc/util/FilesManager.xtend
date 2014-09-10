/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.util

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.PrintStream
import java.net.URI
import java.util.Collections
import java.util.jar.JarEntry
import java.util.jar.JarFile
import org.eclipse.core.runtime.Assert
import org.eclipse.core.runtime.FileLocator
import org.osgi.framework.FrameworkUtil

import static net.sf.orcc.util.Result.*

/**
 * Utility class to manipulate files. It brings everything needed to extract files
 * from a jar plugin to the filesystem, check if 2 files are identical, read/write
 * files, etc.
 */
class FilesManager {

	// TODO: In a future version, a classical java enum could be
	// used here. This is possible only with Xtend 2.4
	public static val OS_WINDOWS = 1
	public static val OS_LINUX = 2
	public static val OS_MACOS = 3
	public static val OS_UNKNOWN = 4

	private static val BUFFER_SIZE = 1024

	/**
	 * <p>Copy the file or the folder at given <em>path</em> to the given
	 * <em>target folder</em>.</p>
	 * 
	 * <p>It is important to understand that the resource (file or folder) at the given path
	 * will be copied <b>into</b> the target folder. For example,
	 * <code>extract("/path/to/file.txt", "/home/johndoe")</code> will copy <em>file.txt</em>
	 * into <em>/home/johndoe</em>, and <code>extract("/path/to/MyFolder", "/home/johndoe")</code>
	 * will create <em>MyFolder</em> directory in <em>/home/johndoe</em> and copy all files
	 * from the source folder into it.
	 * </p>
	 * 
	 * @param path The path of the source (folder or file) to copy
	 * @param targetFolder The directory where to copy the source element
	 * @return A Result object counting exactly how many files have been really
	 * 		written, and how many haven't because they were already up-to-date
	 * @throws FileNotFoundException If not resource have been found at the given path
	 */
	def static extract(String path, String targetFolder) {
		val targetF = new File(targetFolder.sanitize)
		val url = path.url
		if(url == null) {
			throw new FileNotFoundException(path)
		}
		if (url.protocol.equals("jar")) {
			val splittedURL = url.file.split("!")
			val fileUri = new URI(splittedURL.head)
			val jar = new JarFile(new File(fileUri))
			jarExtract(jar, splittedURL.last, targetF)
		} else {
			fsExtract(new File(path.url.toURI), targetF)
		}
	}

	/**
	 * Copy the given <i>source</i> file to the given <em>targetFile</em>
	 * path.
	 * 
	 * @param source
	 * 			An existing File instance
	 * @param targetFolder
	 * 			The target folder to copy the file
	 */
	private def static Result fsExtract(File source, File targetFolder) {
		if (!source.exists) {
			throw new FileNotFoundException(source.path)
		}
		val target = new File(targetFolder, source.name)
		if (source.file) {
			if (source.isContentEqual(target)) {
				newCachedInstance
			} else {
				new FileInputStream(source).streamExtract(target)
			}
		}
		else if (source.directory)
			source.fsDirectoryExtract(target)
	}

	/**
	 * Copy the given <i>source</i> directory and its content into
	 * the given <em>targetFolder</em> directory.
	 * 
	 * @param source
	 * 			The source path of an existing file
	 * @param targetFolder
	 * 			Path to the folder where source will be copied
	 */
	private def static fsDirectoryExtract(File source, File targetFolder) {
		Assert.isTrue(source.directory)
		if (!targetFolder.exists)
			Assert.isTrue(targetFolder.mkdirs)
		else
			Assert.isTrue(targetFolder.directory)

		val result = newInstance
		for (file : source.listFiles) {
			result.merge(
				file.fsExtract(targetFolder)
			)
		}
		return result
	}

	/**
	 * Starting point for extraction of a file/folder resource from a jar. 
	 */
	private def static jarExtract(JarFile jar, String path, File targetFolder) {
		val updatedPath = if (path.startsWith("/")) {
				path.substring(1)
			} else {
				path
			}

		val entry = jar.getJarEntry(updatedPath)
		// Remove the last char if it is '/'
		val name =
			if(entry.name.endsWith("/"))
				entry.name.substring(0, entry.name.length - 1)
			else
				entry.name
		val fileName =
			if(name.lastIndexOf("/") != -1)
				name.substring(name.lastIndexOf("/"))
			else
				name

		if (entry.directory) {
			jarDirectoryExtract(jar, entry, new File(targetFolder, fileName))
		} else {
			val entries = Collections::list(jar.entries).filter[name.startsWith(updatedPath)]
			if (entries.size > 1) {
				jarDirectoryExtract(jar, entry, new File(targetFolder, fileName))
			} else {
				jarFileExtract(jar, entry, new File(targetFolder, fileName))
			}
		}
	}

	/**
	 * Extract all files in the given <em>entry</em> from the given <em>jar</em> into
	 * the <em>target folder</em>.
	 */
	private def static jarDirectoryExtract(JarFile jar, JarEntry entry, File targetFolder) {
		val prefix = entry.name
		val entries = Collections::list(jar.entries).filter[name.startsWith(prefix)]
		val result = newInstance
		for (e : entries) {
			result.merge(
				jarFileExtract(jar, e, new File(targetFolder, e.name.substring(prefix.length)))
			)
		}
		return result
	}

	/**
	 * Extract the file <em>entry</em> from the given <em>jar</em> into the <em>target
	 * file</em>.
	 */
	private def static jarFileExtract(JarFile jar, JarEntry entry, File targetFile) {
		targetFile.parentFile.mkdirs
		if (entry.directory) {
			targetFile.mkdir
			return newInstance
		}
		if (jar.getInputStream(entry).isContentEqual(targetFile)) {
			newCachedInstance
		} else {
			jar.getInputStream(entry).streamExtract(targetFile)
		}
	}

	/**
	 * Copy the content represented by the given <em>inputStream</em> into the
	 * <em>target file</em>. No checking is performed for equality between input
	 * stream and target file. Data are always written.
	 * 
	 * @return A Result object with information about extraction status
	 */
	private def static streamExtract(InputStream inputStream, File targetFile) {
		val bufferedInput = new BufferedInputStream(inputStream)
		val outputStream = new BufferedOutputStream(
			new FileOutputStream(targetFile)
		)

		val byte[] buffer = newByteArrayOfSize(BUFFER_SIZE)
		var readLength = 0
		while ((readLength = bufferedInput.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readLength)
		}
		bufferedInput.close
		outputStream.close

		return newOkInstance
	}

	/**
	 * Search on the file system for a file or folder corresponding to the
	 * given path. If not found, search on the current classpath. If this method
	 * returns an URL, it always represents an existing file.
	 * 
	 * @param path
	 * 			A path
	 * @return
	 * 			An URL for an existing file, or null
	 */
	def static getUrl(String path) {
		val sanitizedPath = path.sanitize

		val file = new File(sanitizedPath)
		if (file.exists)
			return file.toURI.toURL

		// Search in all reachable bundles for the given path resource
		val bundle = FrameworkUtil::getBundle(FilesManager)
		val url =
			if(bundle != null) {
				val bundles = bundle.bundleContext.bundles
				bundles
					// Search only in Orcc plugins
					.filter[symbolicName.contains("orcc")]
					// We want an URL to the resource
					.map[getEntry(path)]
					// We keep the first URL not null (we found the resource)
					.findFirst[it != null]
			}
			// Fallback, we are not in a bundle context (maybe unit tests execution?),
			// we use the default ClassLoader method. The problem with this method is
			// that it is not possible to locate resources in other jar archives (even
			// if they are in the classpath)
			else {
				FilesManager.getResource(path)
			}

		if (#["bundle", "bundleresource", "bundleentry"].contains(url?.protocol?.toLowerCase))
			FileLocator.resolve(url)
		else
			url
	}

	/**
	 * Check if given a CharSequence have exactly the same content
	 * than file b.
	 */
	static def isContentEqual(CharSequence a, File b) {
		new ByteArrayInputStream(a.toString.bytes).isContentEqual(b)
	}

	/**
	 * Check if given File a have exactly the same content
	 * than File b.
	 */
	static def isContentEqual(File a, File b) {
		new FileInputStream(a).isContentEqual(b)
	}

	/**
	 * <p>Compare the content of input stream <em>a</em> and file <em>b</em>.
	 * Returns true if the </p>
	 * 
	 * <p><strong>Important</strong>: This method will close the input stream
	 * <em>a</em> before returning the result.</p>
	 * 
	 * @param a An input stream
	 * @param b A file
	 * @return true if content in a is equals to content in b
	 */
	static def isContentEqual(InputStream a, File b) {
		if(!b.exists) return false
		val inputStreamA = new BufferedInputStream(a)
		val inputStreamB = new BufferedInputStream(new FileInputStream(b))

		var byteA = 0
		var byteB = 0
		do {
			byteA = inputStreamA.read
			byteB = inputStreamB.read
		} while (byteA == byteB && byteA != -1)
		inputStreamA.close
		inputStreamB.close

		return byteA == -1
	}

	/**
	 * Write <em>content</em> to the given <em>targetFolder</em> in a new file called
	 * <em>fileName</em>. This method will write the content to the target file only if
	 * it is empty or if its content is different than that given.
	 * 
	 * @param content The text content to write
	 * @param targetFolder The folder where the file should be created
	 * @param fileName The name of the new file 
	 */
	static def writeFile(CharSequence content, String targetFolder, String fileName) {
		content.writeFile(new File(targetFolder.sanitize, fileName))
	}

	/**
	 * Write <em>content</em> to the given file <em>path</em>. This method will write the
	 * content to the target file only if it is empty or if its content is different than
	 * that given.
	 * 
	 * @param content The text content to write
	 * @param path The path of the file to write
	 */
	static def writeFile(CharSequence content, String path) {
		content.writeFile(new File(path.sanitize))
	}

	/**
	 * Write the <em>content</em> into the <em>targetFile</em> only if necessary.
	 */
	private static def writeFile(CharSequence content, File targetFile) {
		if (content.isContentEqual(targetFile)) {
			return newCachedInstance
		}

		if (!targetFile.parentFile.exists) {
			targetFile.parentFile.mkdirs
		}
		val ps = new PrintStream(new FileOutputStream(targetFile))
		ps.print(content)
		ps.close
		return newOkInstance
	}

	/**
	 * Read the file at the given <em>path</em> and returns its content
	 * as a String.
	 * 
	 * @param path
	 * 			The path of the file to read
	 * @returns
	 * 			The content of the file
	 * @throws FileNotFoundException
	 * 			If the file doesn't exists
	 */
	static def readFile(String path) {

		val url = path.url
		if(url == null) {
			throw new FileNotFoundException(path)
		}

		val inputStream =
			if (url.protocol.equals("jar")) {
				val splittedURL = url.file.split("!")
				val jar = new JarFile(splittedURL.head.substring(5))
				val entryPath = splittedURL.last
				val updatedPath = if (entryPath.startsWith("/")) {
						entryPath.substring(1)
					} else {
						path
					}
				jar.getInputStream(jar.getEntry(updatedPath))
			} else {
				new FileInputStream(new File(url.toURI))
			}

		var readLength = 0
		var buffer = newByteArrayOfSize(BUFFER_SIZE)

		val bufferedInput = new BufferedInputStream(inputStream)
		val outputStream = new ByteArrayOutputStream

		while ((readLength = bufferedInput.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readLength)
		}

		bufferedInput.close
		outputStream.toString
	}

	/**
	 * Transform the given path to a valid filesystem one.
	 * 
	 * <ul>
	 * <li>It replaces first '~' by the home directory of the current user.</li>
	 * </ul>
	 */
	static def sanitize(String path) {
		// We use the following construction because Xtend infer '~' as a String instead of a char
		// path.substring(0,1).equals('~')
		if (!path.nullOrEmpty && path.substring(0, 1).equals('~')) {
			val builder = new StringBuilder(System::getProperty("user.home"))
			builder.append(File.separatorChar).append(path.substring(1))
			return builder.toString()
		}

		return path
	}

	/**
	 * <p>Detect the current user operating system, and return one of the constants
	 * <em>OS_WINDOWS, OS_LINUX, OS_MACOS, OS_UNKNOWN</em> to indicate which system
	 * has been detected.</p>
	 * 
	 * <p>TODO: this method should be tested and fixed on Mac OS</p>
	 */
	static def getCurrentOS() {
		val systemname = System.getProperty("os.name").toLowerCase()
		if (systemname.startsWith("win")) {
			OS_WINDOWS
		} else if (systemname.equals("linux")) {
			OS_LINUX
		} else if (systemname.contains("mac")) {
			OS_MACOS
		} else {
			OS_UNKNOWN
		}
	}

	/**
	 * Delete the given d directory and all its content
	 */
	static def void recursiveDelete(File d) {
		for (e : d.listFiles) {
			if (e.file) {
				e.delete
			} else if (e.directory) {
				e.recursiveDelete
			}
		}
		d.delete
	}
}
