package net.sf.orcc.backends.c.quasistatic.parsers;

import java.io.File;
import java.util.ArrayList;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.utilities.SimpleFileFilter;

/*
* Copyright(c)2009 Victor Martin, Jani Boutellier
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the EPFL and University of Oulu nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

public class CQuasiStaticNetworksParser extends CQuasiStaticParser {

	public CQuasiStaticNetworksParser(String filePath, String outputPath) {
		super(filePath,outputPath);
	}

	@Override
	public void parse() {
		System.out.print("Parsing Networks located in " + toString() + ": ");
		file.isDirectory();
		file.list();
		ArrayList<String> extensions = new ArrayList<String>();
		extensions.add("xdf");
		File[] networkFiles = file.listFiles(new SimpleFileFilter(extensions));
		
		for(int i = 0 ; i < networkFiles.length ; i++){
			CQuasiStaticNetworkParser parser = new CQuasiStaticNetworkParser(networkFiles[i].getAbsolutePath(),outputDirectory.getAbsolutePath());
			parser.parse();
		}
		
		System.out.println("done");
	}

	


	
}
