/*
 * Copyright (c) 2015, INSA of Rennes
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.c.dsp

import net.sf.orcc.backends.CommonPrinter
import net.sf.orcc.backends.util.DSP

class CcsProjectPrinter extends CommonPrinter {
	
	private DSP dsp;
	
	def setDsp(DSP dsp) {
		this.dsp = dsp;
	}
	
	def getProject(String projectName) 
		'''
		<?xml version="1.0" encoding="UTF-8"?>
		<projectDescription>
			<name>«projectName»</name>
			<comment></comment>
			<projects>
			</projects>
			<buildSpec>
				<buildCommand>
					<name>org.eclipse.cdt.managedbuilder.core.genmakebuilder</name>
					<arguments>
					</arguments>
				</buildCommand>
				<buildCommand>
					<name>org.eclipse.cdt.managedbuilder.core.ScannerConfigBuilder</name>
					<triggers>full,incremental,</triggers>
					<arguments>
					</arguments>
				</buildCommand>
			</buildSpec>
			<natures>
				<nature>org.eclipse.rtsc.xdctools.buildDefinitions.XDC.xdcNature</nature>
				<nature>com.ti.ccstudio.core.ccsNature</nature>
				<nature>org.eclipse.cdt.core.cnature</nature>
				<nature>org.eclipse.cdt.managedbuilder.core.managedBuildNature</nature>
				<nature>org.eclipse.cdt.core.ccnature</nature>
				<nature>org.eclipse.cdt.managedbuilder.core.ScannerConfigNature</nature>
			</natures>
		</projectDescription>
		'''

	def getCcsProject(DSP dsp) 
		'''
		<?xml version="1.0" encoding="UTF-8" ?>
		<?ccsproject version="1.0"?>
		<projectOptions>
			<deviceVariant value="«dsp.variant»"/>
			<deviceFamily value="«dsp.family»"/>
			<deviceEndianness value="«dsp.endianness»"/>
			<codegenToolVersion value="7.4.11"/>
			<isElfFormat value="true"/>
			<connection value="common/targetdb/connections/BH-XDS560v2-USB_Mezzanine.xml"/>
			<rts value="libc.a"/>
			<createSlaveProjects value=""/>
			<templateProperties value="id=org.eclipse.rtsc.project.templates.EmptyRtscApplication,buildProfile=release,isHybrid=true,"/>
			<isTargetManual value="true"/>
		</projectOptions>
		'''
		
}