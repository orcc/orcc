/*
 * Copyright (c) 2011, Abo Akademi University
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
 *   * Neither the name of the Abo Akademi University nor the names of its
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
 
package net.sf.orcc.backends.promela

import java.io.File
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil
import net.sf.orcc.backends.promela.transform.PromelaSchedulabilityTest

/**
 * Generated an initial schedule with only actor level scheduling completed 
 *  
 * @author Johan Ersfolk
 * 
 */
class ScriptPrinter extends PromelaTemplate {
	
	val Network network;
	
	new(Network network) {
		this.network = network
	}
	
	def print(String targetFolder) {
		
		val content = networkFileContent
		val file = new File(targetFolder + File::separator + "run_checker_" + network.simpleName + ".py")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}

	def getNetworkFileContent() '''
		# Generated from "«network.name»"
		
		from subprocess import Popen, PIPE
		from pylibs.modelchecking import modelchecker
		import sys, getopt
		

		inputfile = ''
		outputfile = ''

		try:
			opts, args = getopt.getopt(sys.argv[1:],"hi:o:",["ifile=","ofile="])
		except getopt.GetoptError:
			print ('run_checker.py -i <inputfile> -o <outputfile>')
			sys.exit(2)

		for opt, arg in opts:
			if opt == '-h':
				print ('test.py -i <inputfile> -o <outputfile>')
				sys.exit()
			elif opt in ("-i", "--ifile"):
				inputfile = arg
			elif opt in ("-o", "--ofile"):
				outputfile = arg

		print ('Input file is: ', inputfile)
		print ('Output file is: ', outputfile)


		print ('\nAbout to perform scheduling in network:\n\t '+ "main_«network.simpleName».pml" +'\n')

		if (len(sys.argv)>1):
			configuration = sys.argv[1]
			print ('\nUsing configuration '+configuration)
		else:
			print ('\nUsing initial state as configuration (specify desired configuration as argument)')

		print("\n\nRuns Program to get Initial value of variables..\n")

		mc = modelchecker()
		mc.simulate('main_«network.simpleName».pml')
		endstate = mc.endstate
		if outputfile == '':
			print (endstate, "\n")
		else:
			f = open(outputfile, 'w')
			f.write(endstate)
			f.close()
		mc.generatemc('main_«network.simpleName».pml')
		mc.compilemc()
		mc.runmc()
	'''

	
	def superActor(PromelaSchedulabilityTest actorSched) { 
	'''
		<superactor name="cluster_«actorSched.instanceName»">
			<actor name="«actorSched.instanceName»"/>
			«actorSched.printFSM»
			«actorSched.printSchedule»
		</superactor>
	'''
	}
	
}