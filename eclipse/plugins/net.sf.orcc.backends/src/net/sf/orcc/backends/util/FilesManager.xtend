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
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintStream
import java.io.Reader
import java.io.StringReader
import java.util.Collections
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.zip.ZipEntry
import org.eclipse.core.runtime.Assert
import org.eclipse.core.runtime.FileLocator
import static net.sf.orcc.backends.util.Result.*

/**
 * Utility class to manipulate files. It brings everything needed to extract files
 * from a jar plugin to the filesystem, check if 2 files are identical, read/write
 * files, etc.
 */
class FilesManager {

	// TODO: In a future version, an classical java enum could be
	// used here. This is possible only with Xtend 2.4
	public static val OS_WINDOWS = 1
	public static val OS_LINUX = 2
	public static val OS_MACOS = 3
	public static val OS_UNKNOWN = 4

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
		val url = path.url
		if(url == null) {
			throw new FileNotFoundException(path)
		}
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
	private def static Result basicExtraction(File source, File targetFolder) {
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
		if (reader.isContentEqual(targetFile)) {
			return CACHED
		}

		val writer = new FileWriter(targetFile)
		var int c
		while ((c = reader.read) != -1) {
			writer.append(c as char)
		}
		reader.close
		writer.close

		return OK
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

		val result = EMPTY_RESULT
		for (file : source.listFiles) {
			result.merge(
				file.basicExtraction(targetFolder)
			)
		}
		return result
	}

	private def static jarExtract(JarFile jar, String path, File targetFolder) {
		val updatedPath = if (path.startsWith("/")) {
				path.substring(1)
			} else {
				path
			}

		val entry = jar.getJarEntry(updatedPath)
		val fileName = entry.name.substring(entry.name.lastIndexOf("/"))
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

	private def static jarDirectoryExtract(JarFile jar, ZipEntry entry, File target) {
		val prefix = entry.name
		val entries = Collections::list(jar.entries).filter[name.startsWith(prefix)]
		val result = EMPTY_RESULT
		for (e : entries) {
			result.merge(
				jarFileExtract(jar, e, new File(target, e.name.substring(prefix.length)))
			)
		}
		return result
	}

	/**
	 * 
	 */
	private def static jarFileExtract(JarFile jar, JarEntry entry, File target) {
		target.parentFile.mkdirs
		if (entry.directory) {
			target.mkdir
			return EMPTY_RESULT
		}
		val is = jar.getInputStream(entry)

		if (is.isContentEqual(target)) {
			return CACHED
		}

		val os = new FileOutputStream(target)

		val byte[] buffer = newByteArrayOfSize(512)
		var readLen = 0
		while ((readLen = is.read(buffer)) != -1) {
			os.write(buffer, 0, readLen)
		}
		is.close
		os.close

		return OK
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

		val url = FilesManager.getResource(sanitizedPath)

		if ("bundleresource".equalsIgnoreCase(url?.protocol))
			FileLocator.resolve(url)
		else
			url
	}

	static def isContentEqual(CharSequence a, File b) {
		new StringReader(a.toString).isContentEqual(b)
	}

	static def isContentEqual(InputStream a, File b) {
		new InputStreamReader(a).isContentEqual(b)
	}

	/**
	 * Check if given files have exactly the same content
	 */
	static def isContentEqual(Reader readerA, File b) {
		if(!b.exists) return false
		val readerB = new FileReader(b)

		var byteA = 0;
		var byteB = 0
		do {
			byteA = readerA.read
			byteB = readerB.read
		} while (byteA == byteB && byteA != -1)
		readerA.close
		readerB.close

		return byteA == -1
	}

	/**
	 * Unconditionally write <em>content</em> to target file <em>path</em>
	 */
	static def writeFile(CharSequence content, String path) {
		val target = new File(path)

		if (content.isContentEqual(target)) {
			return CACHED
		}

		if (!target.parentFile.exists) {
			target.parentFile.mkdirs
		}
		val ps = new PrintStream(new FileOutputStream(target))
		ps.print(content)
		ps.close
		return OK
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
		val contentBuilder = new StringBuilder
		val url = path.url
		if(url == null) {
			throw new FileNotFoundException(path)
		}
		val reader = new FileReader(new File(url.toURI))

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
			OS_WINDOWS
		} else if (systemname.equals("linux")) {
			OS_LINUX
		} else if (systemname.contains("mac")) {
			OS_MACOS
		} else {
			OS_UNKNOWN
		}
	}

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
