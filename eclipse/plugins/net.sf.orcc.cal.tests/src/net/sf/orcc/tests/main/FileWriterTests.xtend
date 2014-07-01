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
import java.io.FileInputStream
import java.io.IOException
import net.sf.orcc.backends.util.OrccFilesManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Test methods of OrccFileWriter utility class
 */
@RunWith(typeof(JUnit4))
class FileWriterTests extends Assert {

	var tempDir = ""

	@Before
	def void initialization() {
		tempDir = '''«System.getProperty("java.io.tmpdir")»«File.separatorChar»ORCC_FILE_TESTS'''
		val f = new File(tempDir)
		if (!f.exists) {
			f.delete
			if (!f.mkdirs) {
				throw new IOException
			}
		}
		f.deleteOnExit
		return
	}

	@Test
	def testSimpleWrite() {
		assertTrue(OrccFilesManager.writeFile("azerty", '''«tempDir»«File.separatorChar»azertyfile'''))
	}

	@Test
	def testReadFile() {
		"azerty".assertEquals(OrccFilesManager.readFile("/test/files/basic.txt"))
	}

	@Test
	def testValidWrittenContent() {
		val theFile = '''«tempDir»«File.separatorChar»aFile.txt'''
		val theContent = '''
			Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
			tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
			quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
			consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
			cillum dolore eu fugiat nulla pariatur.
		'''
		OrccFilesManager.writeFile(theContent, theFile)
		theContent.assertEquals(OrccFilesManager.readFile(theFile))
	}

	@Test
	def testExtraction() {
		OrccFilesManager::extract("/test/pass/CodegenWhile.cal", tempDir)

		val targetFile = new File('''«tempDir»«File.separatorChar»CodegenWhile.cal''')
		assertTrue(targetFile.exists)

		val source = new File(FileWriterTests.getResource("/test/pass/CodegenWhile.cal").toURI)
		assertTrue(OrccFilesManager::isContentEqual(new FileInputStream(source), targetFile))
	}
}
