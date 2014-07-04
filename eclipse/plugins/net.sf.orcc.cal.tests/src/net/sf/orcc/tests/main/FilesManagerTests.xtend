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
package net.sf.orcc.tests.main

import java.io.File
import java.io.FileReader
import net.sf.orcc.util.FilesManager
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Test methods of OrccFileWriter utility class
 */
@RunWith(typeof(JUnit4))
class FilesManagerTests extends Assert {

	static var tempDir = ""
	var jarFile = "/java/lang/Class.class"
	var jarFolder = "/myjar/a"
	var bundleFile = "/test/extract/subfolder/zzz.txt"
	var bundleFolder = "/test/extract"
	var standardFolder = "~/.ssh"

	@BeforeClass
	static def void initialization() {
		tempDir = '''«System.getProperty("java.io.tmpdir")»«File.separatorChar»ORCC_FILE_TESTS'''
		val f = new File(tempDir)
		if (f.exists) {
			FilesManager.recursiveDelete(f)
		}
	}

	/**
	 * Append the given <em>fileName</em> to the current temp directory,
	 * using the OS specific path separator, to build a new temp path.
	 */
	def private getTempFilePath(String fileName) {
		'''«tempDir»«File.separatorChar»«fileName»'''.toString
	}

	@Test
	def testOSdetection() {
		val os = FilesManager.getCurrentOS
		if (os == FilesManager.OS_WINDOWS) {
			new File("C:/Windows").directory.assertTrue
		} else if (os == FilesManager.OS_LINUX) {
			new File("/home").directory.assertTrue
		} else if (os == FilesManager.OS_MACOS) {
			new File("/Library").directory.assertTrue
		} else {
			fail("Unable to detect System")
		}
	}

	@Test
	def testPathSanitization() {
		standardFolder.startsWith("~").assertTrue
		val result = FilesManager.sanitize(standardFolder)
		result.startsWith("~").assertFalse
		if (FilesManager.getCurrentOS.equals(FilesManager.OS_LINUX)) {
			result.startsWith("/home").assertTrue
		}
	}

	@Test
	def testURLDetection() {
		var url = FilesManager.getUrl(jarFile)
		url.protocol.assertEquals("jar")

		url = FilesManager.getUrl(jarFolder)
		url.protocol.assertEquals("jar")

		url = FilesManager.getUrl(bundleFile)
		url.protocol.assertEquals("file")

		url = FilesManager.getUrl(bundleFolder)
		url.protocol.assertEquals("file")

		url = FilesManager.getUrl(standardFolder)
		url.protocol.assertEquals("file")
	}

	@Test
	def testSimpleRead() {
		"azerty".assertEquals(FilesManager.readFile("/test/extract/files/basic.txt"))
	}

	@Test
	def testSimpleWrite() {
		val filePath = "azer".tempFilePath
		FilesManager.writeFile("azerty", filePath)
		"azerty".assertEquals(FilesManager.readFile(filePath))
	}

	@Test
	def testValidWrittenContent() {
		val theFile = "aFile.txt".tempFilePath
		val theContent = '''
			Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
			quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
			consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
			cillum dolore eu fugiat nulla pariatur.
		'''
		FilesManager.writeFile(theContent, theFile)
		theContent.assertEquals(FilesManager.readFile(theFile))
	}

	@Test
	def testContentsEquals() {
		val theContent = '''
			Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam
		'''
		val f1 = "file1".tempFilePath
		val f2 = "file2".tempFilePath

		FilesManager.writeFile(theContent, f1)
		FilesManager.writeFile(theContent, f2)

		FilesManager::isContentEqual(new FileReader(f1), new File(f2)).assertTrue
		theContent.assertEquals(FilesManager::readFile(f1))
		theContent.assertEquals(FilesManager::readFile(f2))
	}

	@Test
	def testFileExtraction() {
		FilesManager::extract("/test/pass/CodegenWhile.cal", tempDir)

		val targetFile = new File('''«tempDir»«File.separatorChar»CodegenWhile.cal''')
		assertTrue(targetFile.exists)

		targetFile.length.assertNotEquals(0)

		FilesManager::isContentEqual(
			new FileReader(targetFile),
			new File(FilesManager.getUrl("/test/pass/CodegenWhile.cal").toURI)
		).assertTrue
	}

	@Test
	def testFolderExtraction() {
		FilesManager::extract("/test/extract", tempDir)

		val targetFolder = new File("extract".tempFilePath)

		// Check if 'extract folder exists in temp dir'
		targetFolder.directory.assertTrue

		// Check if 'files' folder has correctly been extracted
		new File(targetFolder, "files").directory.assertTrue

		// Check if files have correctly been extrated
		new File(targetFolder, "subfolder/aaa.z").file.assertTrue
		new File(targetFolder, "subfolder/qwert/xxxxx.txt").file.assertTrue

		// Check the content of extracted files
		"xxxx".assertEquals(
			FilesManager.readFile('''«tempDir»/extract/subfolder/qwert/xxxxx.txt''')
		)
	}

	@Test
	def testJarExtractions() {
		val targetDirectory = "jarExtract".tempFilePath
		FilesManager.extract(jarFile, targetDirectory)
		FilesManager.extract(jarFolder, targetDirectory)

		new File(targetDirectory, "Class.class").file.assertTrue
		new File(targetDirectory, "a").directory.assertTrue

		new File(targetDirectory, "a/1.txt").file.assertTrue
		"in folder a, 3.txt".assertEquals(
			FilesManager.readFile('''«targetDirectory»/a/3.txt''')
		)
	}

	@Test
	def testCachedFiles() {
		val path = "testCached".tempFilePath
		val file = new File(path)

		val content = '''
			Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
			quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
			consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
			cillum dolore eu fugiat nulla pariatur.
		'''

		var result = FilesManager.writeFile(content, path)
		result.cached.assertEquals(0)
		result.written.assertEquals(1)

		val timestamp = file.lastModified
		timestamp.assertNotEquals(0)
		Thread.sleep(1500)

		result = FilesManager.writeFile(content, path)
		result.cached.assertEquals(1)
		result.written.assertEquals(0)

		timestamp.assertEquals(file.lastModified)
	}
}
