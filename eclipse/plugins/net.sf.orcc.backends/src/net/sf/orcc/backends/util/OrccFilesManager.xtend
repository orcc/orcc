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
package net.sf.orcc.backends.util

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStream
import java.io.PrintStream
import java.util.Collections
import java.util.jar.JarFile
import java.util.zip.ZipEntry
import org.eclipse.core.runtime.Assert
import org.eclipse.core.runtime.FileLocator

/**
 * Utility class to manipulate files. It brings everything needed to extract files
 * from a jar plugin to the filesystem, check if 2 files are identical, read/write
 * files, etc.
 */
class OrccFilesManager {

	enum OS {
		WINDOWS,
		LINUX,
		MACOS,
		UNKNOWN
	}

	/**
	 * Copy the file or the folder at given <em>path</em> to the given
	 * <em>target folder</em>.
	 * 
	 * @param path
	 * @param targetFolder
	 * @return
	 * @throws FileNotFoundException
	 */
	def static extract(String path, String targetFolder) {
		val targetF = new File(targetFolder)
		val url = getUrl(path)
		if (url.protocol.equals("jar")) {
			val splittedURL = url.file.split("!")
			val jar = new JarFile(splittedURL.head.substring(5))
			jarExtract(jar, splittedURL.last, targetF)
		} else {
			basicExtraction(new File(path.url.toURI), targetF)
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
	private def static void basicExtraction(File source, File targetFolder) {
		if (!source.exists) {
			throw new FileNotFoundException(source.path)
		}
		val target = new File(targetFolder, source.name)
		if (source.file)
			source.basicFileExtraction(target)
		else if (source.directory)
			source.basicDirectoryExtraction(target)
	}

	/**
	 * Copy the file at the given <i>source</i> path to the path
	 * encapsulated in the given <em>targetFile</em>. It is called
	 * 'extract' because in most case, this method is used to extract
	 * files from a plugin in classpath to the filesystem.
	 * 
	 * @param source
	 * 			The source path of an existing file
	 * @param targetFile
	 * 			The target path of a writable file
	 */
	private def static basicFileExtraction(File source, File targetFile) {
		val reader = new FileReader(source)
		val writer = new FileWriter(targetFile)

		var int c
		while ((c = reader.read) != -1) {
			writer.append(c as char)
		}

		reader.close
		writer.close
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
	private def static basicDirectoryExtraction(File source, File targetFolder) {
		Assert.isTrue(source.directory)
		if (!targetFolder.exists)
			Assert.isTrue(targetFolder.mkdirs)
		else
			Assert.isTrue(targetFolder.directory)

		for (file : source.listFiles) {
			file.basicExtraction(targetFolder)
		}
	}

	private def static jarExtract(JarFile jar, String path, File targetFolder) {
		val updatedPath =
			if(path.startsWith("/")) {
				path.substring(1)
			} else {
				path
			}

		val entry = jar.getEntry(updatedPath)
		val fileName = entry.name.substring(entry.name.lastIndexOf("/"))
		if (entry.directory) {
			jarDirectoryExtract(jar, entry, new File(targetFolder, fileName))
		} else {
			val entries = Collections::list(jar.entries).filter[name.startsWith(updatedPath)]
			if(entries.size > 1) {
				jarDirectoryExtract(jar, entry, new File(targetFolder, fileName))
			} else {
				jarFileExtract(jar, entry, new File(targetFolder, fileName))
			}
		}
	}

	private def static jarDirectoryExtract(JarFile jar, ZipEntry entry, File target) {
		val prefix = entry.name
		val entries = Collections::list(jar.entries).filter[name.startsWith(prefix)]
		for (e : entries) {
			jarFileExtract(jar, e, new File(target, e.name.substring(prefix.length)))
		}
	}

	/**
	 * 
	 */
	private def static jarFileExtract(JarFile jar, ZipEntry entry, File target) {
		target.parentFile.mkdirs
		if(entry.directory) {
			target.mkdir
			return
		}
		val is = jar.getInputStream(entry)
		val os = new FileOutputStream(target)

		val byte[] buffer = newByteArrayOfSize(512)
		var readLen = 0
		while( (readLen = is.read(buffer)) != -1) {
			os.write(buffer, 0, readLen)
		}
	}

	/**
	 * Search on the file system for a file or folder corresponding to the
	 * given path. If not found, search on the current classpath. If this method
	 * return  an URL, this URL always point to an existing file.
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

		val url = OrccFilesManager.getResource(sanitizedPath)

		if ("bundleresource".equalsIgnoreCase(url?.protocol))
			FileLocator.resolve(url)
		else
			url
	}

	/**
	 * Check if given files have exactly the same content
	 */
	static def isContentEqual(InputStream streamA, File b) {
		if(!b.exists) return false
		val streamB = new FileInputStream(b)

		var theByte = 0
		var theByte2 = 0
		do {
			theByte = streamA.read
			theByte2 = streamB.read
		} while (theByte == theByte2 && theByte != -1)

		streamA.close
		streamB.close

		return theByte == -1
	}

	/**
	 * Unconditionally write <em>content</em> to target file <em>path</em>
	 */
	static def writeFile(CharSequence content, String path) {
		val target = new File(path)

		if (!target.parentFile.exists) {
			target.parentFile.mkdirs
		}
		val ps = new PrintStream(new FileOutputStream(target))
		ps.print(content)
		ps.close
	}

	/**
	 * Read the file at the given <em>path</em> and returns its content
	 * as a String.
	 * 
	 * @param path
	 * 			The path of the file tio read
	 * @returns
	 * 			The content of the file
	 * @throws FileNotFoundException
	 * 			If the file doesn't exists
	 */
	static def readFile(String path) {
		val contentBuilder = new StringBuilder
		val reader = new FileReader(new File(path.url.toURI))

		var int c
		while ((c = reader.read) != -1) {
			contentBuilder.append(c as char)
		}

		reader.close
		contentBuilder.toString
	}

	/**
	 * 
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

	static def getCurrentOS() {
		val systemname = System.getProperty("os.name").toLowerCase()
		if (systemname.startsWith("win")) {
			OrccFilesManager.OS.WINDOWS
		} else if (systemname.equals("linux")) {
			OrccFilesManager.OS.LINUX
		} else if (systemname.contains("mac")) {
			OrccFilesManager.OS.MACOS
		} else {
			OrccFilesManager.OS.UNKNOWN
		}
	}
}
