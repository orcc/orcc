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

import com.google.common.base.Charsets
import com.google.common.io.Files
import java.io.File
import java.io.FileReader
import net.sf.orcc.util.FilesManager
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.osgi.framework.FrameworkUtil

/**
 * Test methods of OrccFileWriter utility class
 */
@RunWith(typeof(JUnit4))
class FilesManagerTests extends Assert {

	static var tempDir = ""
	// In all case these 2 paths reference files/folders in jar
	// archive in the classpath
	var jarFile = "/java/lang/Class.class"
	var jarFolder = "/org/jgrapht/graph"
	// These path are directly in the current classpath if the test
	// suite is ran in eclipse, in the test bundle if test suite is
	// ran from command line
	var bundleFile = "/test/extraction/subfolder/aaa.z"
	var bundleFolder = "/test/extraction"
	// A folder on the machine (real path)
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
	def operatingSystemDetection() {
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
	def pathSanitization() {
		standardFolder.startsWith("~").assertTrue
		val result = FilesManager.sanitize(standardFolder)
		result.startsWith("~").assertFalse
		result.startsWith(System::getProperty("user.home")).assertTrue
	}

	@Test
	def classpathFilesFolderLookup() {
		var url = FilesManager.getUrl(jarFile)
		url.assertNotNull
		"jar".assertEquals(url.protocol)

		url = FilesManager.getUrl(jarFolder)
		url.assertNotNull
		"jar".assertEquals(url.protocol)

		val bundleProtocol =
			if(FrameworkUtil::getBundle(FilesManagerTests) != null) {
				"jar"
			} else {
				"file"
			}
		url = FilesManager.getUrl(bundleFile)
		url.assertNotNull
		bundleProtocol.assertEquals(url.protocol)

		url = FilesManager.getUrl(bundleFolder)
		url.assertNotNull
		bundleProtocol.assertEquals(url.protocol)

		url = FilesManager.getUrl(standardFolder)
		url.assertNotNull
		"file".assertEquals(url.protocol)
	}

	@Test
	def simpleFileRead() {
		"azerty".assertEquals(FilesManager.readFile("/test/extraction/aTextFile.txt"))
	}

	@Test
	def simpleFileWrite() {
		val filePath = "writtenFile.txt".tempFilePath
		FilesManager.writeFile("consectetur adipisicing elit, sed", filePath)

		val targetFile = new File(filePath)
		targetFile.file.assertTrue
		targetFile.length.assertNotEquals(0)

		"consectetur adipisicing elit, sed".assertEquals(Files.toString(targetFile, Charsets.UTF_8))
	}

	@Test
	def validWrittenContent() {
		val theFile = "loremIpsumContentTest.txt".tempFilePath
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
	def contentEquality() {
		val theContent = '''
			Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam
		'''
		val f1 = "contentEquality1.txt".tempFilePath
		val f2 = "contentEquality2.txt".tempFilePath

		FilesManager.writeFile(theContent, f1)
		FilesManager.writeFile(theContent, f2)

		FilesManager::isContentEqual(new FileReader(f1), new File(f2)).assertTrue
		theContent.assertEquals(FilesManager::readFile(f1))
		theContent.assertEquals(FilesManager::readFile(f2))

		FilesManager::isContentEqual(theContent, new File(f1)).assertTrue
		FilesManager::isContentEqual(theContent, new File(f2)).assertTrue
	}

	@Test
	def fileExtraction() {
		FilesManager::extract("/test/pass/CodegenWhile.cal", tempDir)

		val targetFile = new File(tempDir, "CodegenWhile.cal")
		targetFile.exists.assertTrue
		targetFile.length.assertNotEquals(0)

		FilesManager::isContentEqual(
			FilesManager::readFile("/test/pass/CodegenWhile.cal"),
			targetFile
		).assertTrue
	}

	@Test
	def folderExtraction() {
		FilesManager::extract("/test/extraction", tempDir)

		val targetFolder = new File(tempDir, "extraction")

		// Check if 'extract folder exists in temp dir'
		targetFolder.directory.assertTrue

		// Check if 'files' folder has been correctly extracted
		new File(targetFolder, "subfolder").directory.assertTrue

		// Check if files have been correctly extracted
		new File(targetFolder, "subfolder/aaa.z").file.assertTrue
		new File(targetFolder, "subfolder/subsubfolder/zzz.txt").file.assertTrue

		// Check the content of extracted files
		"xxxx".assertEquals(
			FilesManager.readFile('''«tempDir»/extraction/subfolder/subsubfolder/xxxxx.txt''')
		)
	}

	@Test
	def jarExtractions() {
		val targetDirectory = "jarExtract".tempFilePath

		FilesManager.extract(jarFile, targetDirectory)
		new File(targetDirectory, "Class.class").file.assertTrue

		FilesManager.extract(jarFolder, targetDirectory)
		new File(targetDirectory, "graph").directory.assertTrue
		new File(targetDirectory, "graph/DefaultListenableGraph.class").file.assertTrue
	}

	@Test
	def cachedFiles() {
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
