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

	def static extract(File source, File targetFolder) {
		if (!source.exists) {
			throw new FileNotFoundException(source.path)
		}
		if (source.file)
			source.extractFile(new File(targetFolder, source.name))
		else if (source.directory)
			source.extractDirectory(targetFolder)
	}

	def static extractFile(File source, File targetFile) {
		val reader = new FileReader(source)
		val writer = new FileWriter(targetFile)

		var int c
		while ((c = reader.read) != -1) {
			writer.append(c as char)
		}

		reader.close
		writer.close
	}

	def static extractDirectory(File source, File targetFolder) {
		throw new UnsupportedOperationException
	}

	def static getFileResource(String path) {
		val fsResource = new File(path)
		if(fsResource.exists) return fsResource

		val res = OrccFilesManager.getResource(path)
		if (res != null) {
			val cpResource = new File(res.toURI)
			if(cpResource.exists) return cpResource
		}

		throw new FileNotFoundException()
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
	 * Unconditionally write content to target path
	 */
	static def writeFile(CharSequence content, String path) {
		content.writeFile(new File(path))
	}

	/**
	 * Unconditionally write content to target file
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
