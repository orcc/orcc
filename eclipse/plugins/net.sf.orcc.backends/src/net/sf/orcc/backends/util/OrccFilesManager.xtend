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
import net.sf.orcc.util.OrccLogger
import org.eclipse.core.runtime.Assert
import org.eclipse.core.runtime.FileLocator

/**
 * Utility class to manipulate files. It brings everything needed to extract files
 * from a jar plugin to the filesystem, check if 2 files are identical, read/write
 * files, etc.
 */
class OrccFilesManager {

	def static extract(String path, String targetFolder) {
		extract(path, new File(targetFolder))
	}

	def static extract(String path, File targetFolder) {
		extract(path.fileResource, targetFolder)
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
	def static void extract(File source, File targetFolder) {
		if (!source.exists) {
			throw new FileNotFoundException(source.path)
		}
		val target = new File(targetFolder, source.name)
		if (source.file)
			source.extractFile(target)
		else if (source.directory)
			source.extractDirectory(target)
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
	private def static extractFile(File source, File targetFile) {
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
	private def static extractDirectory(File source, File targetFolder) {
		Assert.isTrue(source.directory)
		if(!targetFolder.exists)
			Assert.isTrue(targetFolder.mkdirs)
		else
			Assert.isTrue(targetFolder.directory)

		for (file : source.listFiles) {
			file.extract(targetFolder)
		}
	}

	/**
	 * Return a valid, existing {@link File} instance from the
	 * given <em>path</em>. To perform that, this method first check
	 * on the file system for an existing file. Then, it try to load
	 * resource from the current classpath.
	 * 
	 * @param path
	 * 			The path to the resource, as a string
	 * @return
	 * 			The path to the resource, as File instance.
	 * @throws FileNotFoundException
	 * 			If the given path can't be found
	 */
	def static getFileResource(String path) {
		val sanitizedPath = path.sanitize

		val fsResource = new File(sanitizedPath)
		if(fsResource.exists) return fsResource

		var uri = OrccFilesManager.getResource(sanitizedPath)?.toURI
		if (uri != null) {
			if(uri.scheme.equalsIgnoreCase("file")) {
				val cpResource = new File(uri)
				if(cpResource.exists) return cpResource
			} else if (uri.scheme.equalsIgnoreCase("bundleresource")) {
				val cpResource = new File(FileLocator.resolve(uri.toURL).toURI)
				if(cpResource.exists) return cpResource
			} else {
				System::out.println("scheme: " + uri.scheme)
			}
		}

		throw new FileNotFoundException(path)
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
		content.writeFile(new File(path))
	}

	/**
	 * Unconditionally write <em>content</em> to given <em>target</em> file
	 */
	static def writeFile(CharSequence content, File target) {
		try {
			if (!target.parentFile.exists) {
				target.parentFile.mkdirs
			}
			val ps = new PrintStream(new FileOutputStream(target))
			ps.print(content)
			ps.close
			return true
		} catch (FileNotFoundException e) {
			OrccLogger.severe('''Unable to write file «target.path»: «e.cause»''')
			OrccLogger.severe(e.getLocalizedMessage)
			e.printStackTrace
			return false;
		}
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
		val reader = new FileReader(path.fileResource)

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
		if (!path.nullOrEmpty && path.charAt(0) == '~') {
			val builder = new StringBuilder(System::getProperty("user.home"))
			builder.append(File.separatorChar).append(path.substring(1))
			return builder.toString()
		}

		return path
	}
}
