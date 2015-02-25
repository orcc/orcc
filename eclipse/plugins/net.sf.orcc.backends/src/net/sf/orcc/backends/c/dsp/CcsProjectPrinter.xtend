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

	def getTopApp(DSP dsp) 
		'''
		<configuration artifactExtension="out" artifactName="${ProjName}" buildProperties="" cleanCommand="${CG_CLEAN_CMD}" description="" errorParsers="org.eclipse.rtsc.xdctools.parsers.ErrorParser;com.ti.ccstudio.errorparser.CoffErrorParser;com.ti.ccstudio.errorparser.LinkErrorParser;com.ti.ccstudio.errorparser.AsmErrorParser" id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614" name="Debug" parent="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug" postbuildStep="" prebuildStep="">
			<folderInfo id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614." name="/" resourcePath="">
				<toolChain id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.DebugToolchain.1686743686" name="TI Build Tools" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.DebugToolchain" targetTool="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.linkerDebug.1577232013">
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS.1623277499" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS" valueType="stringList">
						<listOptionValue builtIn="false" value="DEVICE_CONFIGURATION_ID=«dsp.variant»"/>
						<listOptionValue builtIn="false" value="DEVICE_ENDIANNESS=«dsp.endianness»"/>
						<listOptionValue builtIn="false" value="OUTPUT_FORMAT=ELF"/>
						<listOptionValue builtIn="false" value="CCS_MBS_VERSION=5.5.0"/>
						<listOptionValue builtIn="false" value="LINKER_COMMAND_FILE="/>
						<listOptionValue builtIn="false" value="RUNTIME_SUPPORT_LIBRARY=libc.a"/>
						<listOptionValue builtIn="false" value="RTSC_MBS_VERSION=2.2.0"/>
						<listOptionValue builtIn="false" value="XDC_VERSION=3.25.6.96"/>
						<listOptionValue builtIn="false" value="RTSC_PRODUCTS=com.ti.rtsc.IPC:3.22.2.11;com.ti.rtsc.openmp:2.1.17.00;com.ti.rtsc.SYSBIOS:6.37.3.30;"/>
						<listOptionValue builtIn="false" value="INACTIVE_REPOS="/>
						<listOptionValue builtIn="false" value="OUTPUT_TYPE=rtscApplication:executable"/>
					</option>
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION.630455925" name="Compiler version" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION" value="7.4.8" valueType="string"/>
					<targetPlatform id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.targetPlatformDebug.364868775" name="Platform" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.targetPlatformDebug"/>
					<builder buildPath="${BuildDirectory}" id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.builderDebug.1086537606" keepEnvironmentInBuildfile="false" name="GNU Make" parallelBuildOn="true" parallelizationNumber="optimal" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.builderDebug"/>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.compilerDebug.1544843732" name="«dsp.family» Compiler" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.compilerDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION.207883854" name="Target processor version (--silicon_version, -mv)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION" value="6600" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.500183416" name="Application binary interface (coffabi, eabi) [See 'General' page to edit] (--abi)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.eabi" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.907720535" name="Optimization level (--opt_level, -O)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.1462544801" name="Optimize for code size (--opt_for_space, -ms)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.77789190" name="Debugging model" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.SYMDEBUG__NONE" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.937239084" name="Optimize fully in the presence of debug directives (--optimize_with_debug, -mn)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.on" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH.1984753222" name="Add dir to #include search path (--include_path, -I)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH" valueType="includePath">
							<listOptionValue builtIn="false" value="&quot;${CG_TOOL_ROOT}/include&quot;"/>
							<listOptionValue builtIn="false" value="/opt/ti/xdctools_3_25_06_96/packages"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/orcc-runtime/include}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/orcc-native/include}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/roxml/include}&quot;"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER.846120419" name="Emit diagnostic identifier numbers (--display_error_number, -pden)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING.1643326401" name="Treat diagnostic &lt;id&gt; as warning (--diag_warning, -pdsw)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING" valueType="stringList">
							<listOptionValue builtIn="false" value="225"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.1711368703" name="Wrap diagnostic messages (--diag_wrap)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.off" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.MEM_MODEL__DATA.483012033" name="Data access model (--mem_model:data)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.MEM_MODEL__DATA" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.MEM_MODEL__DATA.far_aggregates" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST.1638904497" name="Generate optimized source interlisted assembly (--optimizer_interlist, -os)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP.1383789407" name="Enable support for OpenMP 3.0 (--openmp, --omp)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.328989744" name="Optimize for speed (--opt_for_speed, -mf)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.5" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.AUTO_INLINE.1452880460" name="Specify threshold for automatic inlining (--auto_inline, -oi)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.AUTO_INLINE" value="2" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE.1052340172" name="Inline functions only called once. (--single_inline)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE" value="true" valueType="boolean"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS.607588044" name="C Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS.933872911" name="C++ Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS.2124163237" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS.738829112" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS"/>
					</tool>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.linkerDebug.1577232013" name="«dsp.family» Linker" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exe.linkerDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.OUTPUT_FILE.1464076482" name="Specify output file name (--output_file, -o)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.OUTPUT_FILE" value="&quot;${ProjName}.out&quot;" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.MAP_FILE.3341693" name="Input and output sections listed into &lt;file&gt; (--map_file, -m)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.MAP_FILE" value="&quot;${ProjName}.map&quot;" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.LIBRARY.1880073700" name="Include library file or command file as input (--library, -l)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.LIBRARY" valueType="libs">
							<listOptionValue builtIn="false" value="&quot;libc.a&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/orcc-native/Debug/orcc-native.lib}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/orcc-runtime/Debug/orcc-runtime.lib}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/roxml/Debug/roxml.lib}&quot;"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.SEARCH_PATH.59482104" name="Add &lt;dir&gt; to library search path (--search_path, -i)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.SEARCH_PATH" valueType="libPaths">
							<listOptionValue builtIn="false" value="&quot;${CG_TOOL_ROOT}/lib&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${CG_TOOL_ROOT}/include&quot;"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.DISPLAY_ERROR_NUMBER.1834177686" name="Emit diagnostic identifier numbers (--display_error_number)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.DISPLAY_ERROR_NUMBER" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.DIAG_WRAP.1650127156" name="Wrap diagnostic messages (--diag_wrap)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.DIAG_WRAP" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.DIAG_WRAP.off" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.XML_LINK_INFO.196790471" name="Detailed link information data-base into &lt;file&gt; (--xml_link_info, -xml_link_info)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.linkerID.XML_LINK_INFO" value="&quot;${ProjName}_linkInfo.xml&quot;" valueType="string"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exeLinker.inputType__CMD_SRCS.2134594140" name="Linker Command Files" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exeLinker.inputType__CMD_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exeLinker.inputType__CMD2_SRCS.456996609" name="Linker Command Files" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exeLinker.inputType__CMD2_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exeLinker.inputType__GEN_CMDS.206425859" name="Generated Linker Command Files" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.exeLinker.inputType__GEN_CMDS"/>
					</tool>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.hex.329526024" name="«dsp.family» Hex Utility" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.hex"/>
					<tool id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.351455377" name="XDCtools" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool">
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH.1040894313" name="Package repositories (--xdcpath)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH" valueType="stringList">
							<listOptionValue builtIn="false" value="${IPC_CG_ROOT}/packages"/>
							<listOptionValue builtIn="false" value="${COM_TI_RTSC_OPENMP_INSTALL_DIR}/packages"/>
							<listOptionValue builtIn="false" value="${BIOS_CG_ROOT}/packages"/>
							<listOptionValue builtIn="false" value="/opt/ti/pdk_C6678_2_1_3_7/packages"/>
							<listOptionValue builtIn="false" value="${TARGET_CONTENT_BASE}"/>
						</option>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET.1787008133" name="Target (-t)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET" value="ti.targets.elf.C66" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM.918103524" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW.1427748058" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE.510788155" name="Build-profile (-r)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE" value="release" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR.1329903648" name="Compiler tools directory (-c)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR" value="&quot;${CG_TOOL_ROOT}&quot;" valueType="string"/>
					</tool>
				</toolChain>
			</folderInfo>
		</configuration>
		'''

	def getRuntime(DSP dsp) 
		'''
		<configuration artifactExtension="lib" artifactName="${ProjName}" buildProperties="" cleanCommand="${CG_CLEAN_CMD}" description="" errorParsers="org.eclipse.rtsc.xdctools.parsers.ErrorParser;com.ti.ccstudio.errorparser.CoffErrorParser;com.ti.ccstudio.errorparser.LinkErrorParser;com.ti.ccstudio.errorparser.AsmErrorParser" id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614" name="Debug" parent="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug" postbuildStep="" prebuildStep="">
			<folderInfo id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614." name="/" resourcePath="">
				<toolChain id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.libraryDebugToolchain.1282286721" name="TI Build Tools" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.libraryDebugToolchain" targetTool="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug.166649191">
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS.1203545314" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS" valueType="stringList">
						<listOptionValue builtIn="false" value="DEVICE_CONFIGURATION_ID=«dsp.variant»"/>
						<listOptionValue builtIn="false" value="DEVICE_ENDIANNESS=«dsp.endianness»"/>
						<listOptionValue builtIn="false" value="OUTPUT_FORMAT=ELF"/>
						<listOptionValue builtIn="false" value="CCS_MBS_VERSION=5.5.0"/>
						<listOptionValue builtIn="false" value="RTSC_MBS_VERSION=2.2.0"/>
						<listOptionValue builtIn="false" value="XDC_VERSION=3.25.6.96"/>
						<listOptionValue builtIn="false" value="RTSC_PRODUCTS=com.ti.rtsc.IPC:3.22.2.11;com.ti.biosmcsdk.pdk.C6678L:2.1.3.7;com.ti.rtsc.openmp:2.1.17.00;com.ti.rtsc.SYSBIOS:6.37.3.30;"/>
						<listOptionValue builtIn="false" value="INACTIVE_REPOS="/>
						<listOptionValue builtIn="false" value="OUTPUT_TYPE=rtscApplication:staticLibrary"/>
					</option>
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION.1598592306" name="Compiler version" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION" value="7.4.8" valueType="string"/>
					<targetPlatform id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformDebug.159206529" name="Platform" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformDebug"/>
					<builder buildPath="${BuildDirectory}" id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderDebug.1078851691" keepEnvironmentInBuildfile="false" name="GNU Make" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderDebug"/>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerDebug.90278571" name="«dsp.family» Compiler" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION.1247472408" name="Target processor version (--silicon_version, -mv)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION" value="6600" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.1904846258" name="Application binary interface (coffabi, eabi) [See 'General' page to edit] (--abi)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.eabi" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.2144662153" name="Optimization level (--opt_level, -O)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.2103348867" name="Optimize for code size (--opt_for_space, -ms)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.912932500" name="Debugging model" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.SYMDEBUG__DWARF" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.279954912" name="Optimize fully in the presence of debug directives (--optimize_with_debug, -mn)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.on" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH.246519597" name="Add dir to #include search path (--include_path, -I)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH" valueType="includePath">
							<listOptionValue builtIn="false" value="&quot;${CG_TOOL_ROOT}/include&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/${ProjName}/include}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/Top_mpegh_part2_main_no_md5}&quot;"/>
							<listOptionValue builtIn="false" value="/opt/ti/pdk_C6678_2_1_3_7/packages"/>
							<listOptionValue builtIn="false" value="/opt/ti/openmp_dsp_2_01_17_00/packages/ti/runtime/openmp"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/orcc-native/include}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/roxml/include}&quot;"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER.86355221" name="Emit diagnostic identifier numbers (--display_error_number, -pden)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING.1214239350" name="Treat diagnostic &lt;id&gt; as warning (--diag_warning, -pdsw)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING" valueType="stringList">
							<listOptionValue builtIn="false" value="225"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.628747635" name="Wrap diagnostic messages (--diag_wrap)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.off" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST.1769792701" name="Generate optimized source interlisted assembly (--optimizer_interlist, -os)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP.76757050" name="Enable support for OpenMP 3.0 (--openmp, --omp)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.1238032916" name="Optimize for speed (--opt_for_speed, -mf)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.5" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE.1226067413" name="Inline functions only called once. (--single_inline)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEFINE.2116070978" name="Pre-define NAME (--define, -D)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEFINE" valueType="definedSymbols">
							<listOptionValue builtIn="false" value="OPENMP_ENABLE"/>
							<listOptionValue builtIn="false" value="ROXML_ENABLE"/>
							<listOptionValue builtIn="false" value="MDSP_ENABLE"/>
						</option>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS.1184727075" name="C Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS.734479543" name="C++ Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS.1154830514" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS.1991486705" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS"/>
					</tool>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug.166649191" name="«dsp.family» Archiver" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE.257380830" name="Output file" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE" value="&quot;${ProjName}.lib&quot;" valueType="string"/>
					</tool>
					<tool id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.52832058" name="XDCtools" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool">
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH.1728380685" name="Package repositories (--xdcpath)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH" valueType="stringList">
							<listOptionValue builtIn="false" value="&quot;${IPC_CG_ROOT}/packages&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${COM_TI_RTSC_OPENMP_INSTALL_DIR}/packages&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${BIOS_CG_ROOT}/packages&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${TARGET_CONTENT_BASE}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${TI_PDK_C6678_INSTALL_DIR}/packages&quot;"/>
						</option>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET.2098971123" name="Target (-t)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET" value="ti.targets.elf.C66" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM.1588311637" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW.2116773181" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE.1089538190" name="Build-profile (-r)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE" value="release" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR.643658301" name="Compiler tools directory (-c)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR" value="&quot;${CG_TOOL_ROOT}&quot;" valueType="string"/>
					</tool>
				</toolChain>
			</folderInfo>
		</configuration>
		'''

	def getRoxml(DSP dsp) 
		'''
		<configuration artifactExtension="lib" artifactName="${ProjName}" buildProperties="" cleanCommand="${CG_CLEAN_CMD}" description="" errorParsers="org.eclipse.rtsc.xdctools.parsers.ErrorParser;com.ti.ccstudio.errorparser.CoffErrorParser;com.ti.ccstudio.errorparser.LinkErrorParser;com.ti.ccstudio.errorparser.AsmErrorParser" id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614" name="Debug" parent="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug" postbuildStep="" prebuildStep="">
			<folderInfo id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614." name="/" resourcePath="">
				<toolChain id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.libraryDebugToolchain.16177147" name="TI Build Tools" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.libraryDebugToolchain" targetTool="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug.223813571">
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS.660967741" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS" valueType="stringList">
						<listOptionValue builtIn="false" value="DEVICE_CONFIGURATION_ID=«dsp.variant»"/>
						<listOptionValue builtIn="false" value="DEVICE_ENDIANNESS=«dsp.endianness»"/>
						<listOptionValue builtIn="false" value="OUTPUT_FORMAT=ELF"/>
						<listOptionValue builtIn="false" value="CCS_MBS_VERSION=5.5.0"/>
						<listOptionValue builtIn="false" value="RTSC_MBS_VERSION=2.2.0"/>
						<listOptionValue builtIn="false" value="XDC_VERSION=3.25.6.96"/>
						<listOptionValue builtIn="false" value="RTSC_PRODUCTS=com.ti.rtsc.IPC:3.22.2.11;ti.pdk:3.0.2.14;com.ti.rtsc.openmp:2.1.17.00;com.ti.rtsc.SYSBIOS:6.37.3.30;"/>
						<listOptionValue builtIn="false" value="INACTIVE_REPOS="/>
						<listOptionValue builtIn="false" value="OUTPUT_TYPE=rtscApplication:staticLibrary"/>
					</option>
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION.1545564258" name="Compiler version" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION" value="7.4.11" valueType="string"/>
					<targetPlatform id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformDebug.1412392575" name="Platform" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformDebug"/>
					<builder buildPath="${BuildDirectory}" id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderDebug.517430293" keepEnvironmentInBuildfile="false" name="GNU Make" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderDebug"/>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerDebug.910011249" name="«dsp.family» Compiler" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION.2010240607" name="Target processor version (--silicon_version, -mv)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION" value="6600" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.1786193376" name="Debugging model" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.SYMDEBUG__NONE" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING.863625580" name="Treat diagnostic &lt;id&gt; as warning (--diag_warning, -pdsw)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING" valueType="stringList">
							<listOptionValue builtIn="false" value="225"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER.1263916801" name="Emit diagnostic identifier numbers (--display_error_number, -pden)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.763552817" name="Wrap diagnostic messages (--diag_wrap)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.off" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH.822690488" name="Add dir to #include search path (--include_path, -I)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH" valueType="includePath">
							<listOptionValue builtIn="false" value="&quot;${CG_TOOL_ROOT}/include&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/${ProjName}/include}&quot;"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.1280463109" name="Application binary interface (coffabi, eabi) [See 'General' page to edit] (--abi)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.eabi" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.1070538109" name="Optimization level (--opt_level, -O)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.1163485351" name="Optimize for code size (--opt_for_space, -ms)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.947029090" name="Optimize fully in the presence of debug directives (--optimize_with_debug, -mn)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.on" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.AUTO_INLINE.747680595" name="Specify threshold for automatic inlining (--auto_inline, -oi)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.AUTO_INLINE" value="" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP.1691692601" name="Enable support for OpenMP 3.0 (--openmp, --omp)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST.2037086449" name="Generate optimized source interlisted assembly (--optimizer_interlist, -os)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.1444388506" name="Optimize for speed (--opt_for_speed, -mf)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.5" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE.1995081797" name="Inline functions only called once. (--single_inline)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.FP_REASSOC.532838460" name="Allow reassociation of FP arithmetic (--fp_reassoc)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.FP_REASSOC" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.FP_REASSOC.on" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEFINE.1632246990" name="Pre-define NAME (--define, -D)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEFINE" valueType="definedSymbols">
							<listOptionValue builtIn="false" value="OPENMP_ENABLE"/>
							<listOptionValue builtIn="false" value="MDSP_ENABLE"/>
						</option>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS.607330148" name="C Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS.1917869208" name="C++ Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS.288479086" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS.142876907" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS"/>
					</tool>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug.223813571" name="«dsp.family» Archiver" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE.1187892450" name="Output file" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE" value="&quot;${ProjName}.lib&quot;" valueType="string"/>
					</tool>
					<tool id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.1175988807" name="XDCtools" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool">
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR.143747287" name="Compiler tools directory (-c)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR" value="&quot;${CG_TOOL_ROOT}&quot;" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET.1277376667" name="Target (-t)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET" value="ti.targets.elf.C66" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM.1036863663" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW.68138477" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE.1435306203" name="Build-profile (-r)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE" value="release" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH.1069617481" name="Package repositories (--xdcpath)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH" valueType="stringList">
							<listOptionValue builtIn="false" value="${IPC_CG_ROOT}/packages"/>
							<listOptionValue builtIn="false" value="${TI_PDK_INSTALL_DIR}/packages"/>
							<listOptionValue builtIn="false" value="${COM_TI_RTSC_OPENMP_INSTALL_DIR}/packages"/>
							<listOptionValue builtIn="false" value="${BIOS_CG_ROOT}/packages"/>
							<listOptionValue builtIn="false" value="${TARGET_CONTENT_BASE}"/>
						</option>
					</tool>
				</toolChain>
			</folderInfo>
		</configuration>
		'''

	def getNative(DSP dsp) 
		'''
		<configuration artifactExtension="lib" artifactName="${ProjName}" buildProperties="" cleanCommand="${CG_CLEAN_CMD}" description="" errorParsers="org.eclipse.rtsc.xdctools.parsers.ErrorParser;com.ti.ccstudio.errorparser.CoffErrorParser;com.ti.ccstudio.errorparser.LinkErrorParser;com.ti.ccstudio.errorparser.AsmErrorParser" id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614" name="Debug" parent="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug" postbuildStep="" prebuildStep="">
			<folderInfo id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614." name="/" resourcePath="">
				<toolChain id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.libraryDebugToolchain.269619442" name="TI Build Tools" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.libraryDebugToolchain" targetTool="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug.516344497">
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS.1763159995" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS" valueType="stringList">
						<listOptionValue builtIn="false" value="DEVICE_CONFIGURATION_ID=«dsp.variant»"/>
						<listOptionValue builtIn="false" value="DEVICE_ENDIANNESS=«dsp.endianness»"/>
						<listOptionValue builtIn="false" value="OUTPUT_FORMAT=ELF"/>
						<listOptionValue builtIn="false" value="CCS_MBS_VERSION=5.5.0"/>
						<listOptionValue builtIn="false" value="RTSC_MBS_VERSION=2.2.0"/>
						<listOptionValue builtIn="false" value="XDC_VERSION=3.25.6.96"/>
						<listOptionValue builtIn="false" value="RTSC_PRODUCTS=com.ti.rtsc.IPC:3.22.2.11;ti.pdk:3.0.2.14;com.ti.rtsc.openmp:2.1.17.00;com.ti.rtsc.SYSBIOS:6.37.3.30;"/>
						<listOptionValue builtIn="false" value="INACTIVE_REPOS="/>
						<listOptionValue builtIn="false" value="OUTPUT_TYPE=rtscApplication:staticLibrary"/>
					</option>
					<option id="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION.1533823219" name="Compiler version" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION" value="7.4.8" valueType="string"/>
					<targetPlatform id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformDebug.1530808088" name="Platform" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformDebug"/>
					<builder buildPath="${BuildDirectory}" id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderDebug.487695122" keepEnvironmentInBuildfile="false" name="GNU Make" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderDebug"/>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerDebug.1059583533" name="«dsp.family» Compiler" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION.1579288744" name="Target processor version (--silicon_version, -mv)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION" value="6600" valueType="string"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.1476948672" name="Application binary interface (coffabi, eabi) [See 'General' page to edit] (--abi)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.eabi" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.546943050" name="Optimization level (--opt_level, -O)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_LEVEL.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.1039864708" name="Optimize for code size (--opt_for_space, -ms)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPACE.3" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.1271423033" name="Debugging model" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEBUGGING_MODEL.SYMDEBUG__NONE" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.1863550961" name="Optimize fully in the presence of debug directives (--optimize_with_debug, -mn)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZE_WITH_DEBUG.on" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH.1129017821" name="Add dir to #include search path (--include_path, -I)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH" valueType="includePath">
							<listOptionValue builtIn="false" value="&quot;${CG_TOOL_ROOT}/include&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/${ProjName}/include}&quot;"/>
							<listOptionValue builtIn="false" value="/opt/ti/xdctools_3_25_06_96/packages"/>
							<listOptionValue builtIn="false" value="/opt/ti/bios_6_37_03_30/packages"/>
							<listOptionValue builtIn="false" value="/opt/ti/bios_6_37_03_30/packages/ti/bios/include"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/orcc-runtime/include}&quot;"/>
							<listOptionValue builtIn="false" value="&quot;${workspace_loc:/roxml/include}&quot;"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER.220419449" name="Emit diagnostic identifier numbers (--display_error_number, -pden)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING.261859942" name="Treat diagnostic &lt;id&gt; as warning (--diag_warning, -pdsw)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING" valueType="stringList">
							<listOptionValue builtIn="false" value="225"/>
						</option>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.851266843" name="Wrap diagnostic messages (--diag_wrap)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.off" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST.2083495057" name="Generate optimized source interlisted assembly (--optimizer_interlist, -os)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPTIMIZER_INTERLIST" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP.1166653819" name="Enable support for OpenMP 3.0 (--openmp, --omp)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPENMP" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.327026230" name="Optimize for speed (--opt_for_speed, -mf)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.OPT_FOR_SPEED.5" valueType="enumerated"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE.2021828117" name="Inline functions only called once. (--single_inline)" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SINGLE_INLINE" value="true" valueType="boolean"/>
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEFINE.1678467347" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DEFINE" valueType="definedSymbols">
							<listOptionValue builtIn="false" value="OPENMP_ENABLE"/>
							<listOptionValue builtIn="false" value="MDSP_ENABLE"/>
						</option>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS.974567815" name="C Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS.427349228" name="C++ Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS.1746831028" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS"/>
						<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS.1988865275" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS"/>
					</tool>
					<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug.516344497" name="«dsp.family» Archiver" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianDebug">
						<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE.1105045812" name="Output file" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE" value="&quot;${ProjName}.lib&quot;" valueType="string"/>
					</tool>
					<tool id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.1217239547" name="XDCtools" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool">
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH.1918128900" name="Package repositories (--xdcpath)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH" valueType="stringList">
							<listOptionValue builtIn="false" value="${IPC_CG_ROOT}/packages"/>
							<listOptionValue builtIn="false" value="${TI_PDK_INSTALL_DIR}/packages"/>
							<listOptionValue builtIn="false" value="${COM_TI_RTSC_OPENMP_INSTALL_DIR}/packages"/>
							<listOptionValue builtIn="false" value="${BIOS_CG_ROOT}/packages"/>
							<listOptionValue builtIn="false" value="${TARGET_CONTENT_BASE}"/>
						</option>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET.1713115651" name="Target (-t)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET" value="ti.targets.elf.C66" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM.1024037061" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW.994398630" name="Platform (-p)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE.9946288" name="Build-profile (-r)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE" value="release" valueType="string"/>
						<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR.698015033" name="Compiler tools directory (-c)" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR" value="&quot;${CG_TOOL_ROOT}&quot;" valueType="string"/>
					</tool>
				</toolChain>
			</folderInfo>
			<sourceEntries>
				<entry excluding="src/ffmpeg_util.c|src/eventLib.c|src/source_camera.c|src/openhevc_sse.c|src/hevc_sse.c|src/hevc_mc_sse.c|src/hevc_intra_pred_sse.c|src/hevc_idct_sse4.c|src/framerate_sdl.c|src/display_sdl2.c|src/display_sdl.c|src/audio_sdl.c" flags="VALUE_WORKSPACE_PATH|RESOLVED" kind="sourcePath" name=""/>
			</sourceEntries>
		</configuration>
		'''

	def getCProject(DSP dsp, String projectID) 
		'''
		<?xml version="1.0" encoding="UTF-8" standalone="no"?>
		<?fileVersion 4.0.0?>
		
		<cproject storage_type_id="org.eclipse.cdt.core.XmlProjectDescriptionStorage">
			<storageModule configRelations="2" moduleId="org.eclipse.cdt.core.settings">
				<cconfiguration id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614">
					<storageModule buildSystemId="org.eclipse.cdt.managedbuilder.core.configurationDataProvider" id="com.ti.ccstudio.buildDefinitions.«dsp.family».Debug.1387055614" moduleId="org.eclipse.cdt.core.settings" name="Debug">
						<externalSettings/>
						<extensions>
							<extension id="com.ti.ccstudio.binaryparser.CoffParser" point="org.eclipse.cdt.core.BinaryParser"/>
							<extension id="com.ti.ccstudio.errorparser.CoffErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
							<extension id="com.ti.ccstudio.errorparser.LinkErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
							<extension id="com.ti.ccstudio.errorparser.AsmErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
							<extension id="org.eclipse.rtsc.xdctools.parsers.ErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
						</extensions>
					</storageModule>
					<storageModule moduleId="cdtBuildSystem" version="4.0.0">
						«IF projectID.compareTo("orcc-native") == 0»
						«dsp.getNative»
						«ELSEIF projectID.compareTo("orcc-runtime") == 0»
						«dsp.getRuntime»
						«ELSEIF projectID.compareTo("roxml") == 0»
						«dsp.getRoxml»
						«ELSE»
						«dsp.getTopApp»
						«ENDIF»
					</storageModule>
					<storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
				</cconfiguration>		
				<cconfiguration id="com.ti.ccstudio.buildDefinitions.«dsp.family».Release.758124528">
					<storageModule buildSystemId="org.eclipse.cdt.managedbuilder.core.configurationDataProvider" id="com.ti.ccstudio.buildDefinitions.«dsp.family».Release.758124528" moduleId="org.eclipse.cdt.core.settings" name="Release">
						<externalSettings/>
						<extensions>
							<extension id="com.ti.ccstudio.binaryparser.CoffParser" point="org.eclipse.cdt.core.BinaryParser"/>
							<extension id="com.ti.ccstudio.errorparser.CoffErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
							<extension id="com.ti.ccstudio.errorparser.LinkErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
							<extension id="com.ti.ccstudio.errorparser.AsmErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
							<extension id="org.eclipse.rtsc.xdctools.parsers.ErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
						</extensions>
					</storageModule>
					<storageModule moduleId="cdtBuildSystem" version="4.0.0">
						<configuration artifactExtension="lib" artifactName="${ProjName}" buildProperties="" cleanCommand="${CG_CLEAN_CMD}" description="" errorParsers="org.eclipse.rtsc.xdctools.parsers.ErrorParser;com.ti.ccstudio.errorparser.CoffErrorParser;com.ti.ccstudio.errorparser.LinkErrorParser;com.ti.ccstudio.errorparser.AsmErrorParser" id="com.ti.ccstudio.buildDefinitions.«dsp.family».Release.758124528" name="Release" parent="com.ti.ccstudio.buildDefinitions.«dsp.family».Release" postbuildStep="" prebuildStep="">
							<folderInfo id="com.ti.ccstudio.buildDefinitions.«dsp.family».Release.758124528." name="/" resourcePath="">
								<toolChain id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.ReleaseToolchain.746246312" name="TI Build Tools" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.ReleaseToolchain" targetTool="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianRelease.789294139">
									<option id="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS.1600753719" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_TAGS" valueType="stringList">
										<listOptionValue builtIn="false" value="DEVICE_CONFIGURATION_ID=«dsp.variant»"/>
										<listOptionValue builtIn="false" value="DEVICE_ENDIANNESS=«dsp.endianness»"/>
										<listOptionValue builtIn="false" value="OUTPUT_FORMAT=ELF"/>
										<listOptionValue builtIn="false" value="CCS_MBS_VERSION=5.5.0"/>
										<listOptionValue builtIn="false" value="RTSC_MBS_VERSION=2.2.0"/>
										<listOptionValue builtIn="false" value="XDC_VERSION=3.30.3.47_core"/>
										<listOptionValue builtIn="false" value="RTSC_PRODUCTS=com.ti.rtsc.IPC:3.22.2.11;ti.pdk:3.0.2.14;com.ti.rtsc.openmp:2.1.17.00;com.ti.rtsc.SYSBIOS:6.37.3.30;"/>
										<listOptionValue builtIn="false" value="OUTPUT_TYPE=rtscApplication:staticLibrary"/>
									</option>
									<option id="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION.1760508596" superClass="com.ti.ccstudio.buildDefinitions.core.OPT_CODEGEN_VERSION" value="7.4.11" valueType="string"/>
									<targetPlatform id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformRelease.1314776626" name="Platform" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.targetPlatformRelease"/>
									<builder buildPath="${BuildDirectory}" id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderRelease.2134811224" name="GNU Make.Release" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.builderRelease"/>
									<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerRelease.91046386" name="«dsp.family» Compiler" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.compilerRelease">
										<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION.1862595493" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.SILICON_VERSION" value="6600" valueType="string"/>
										<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING.654356604" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WARNING" valueType="stringList">
											<listOptionValue builtIn="false" value="225"/>
										</option>
										<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER.1465624255" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DISPLAY_ERROR_NUMBER" value="true" valueType="boolean"/>
										<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.371241384" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.DIAG_WRAP.off" valueType="enumerated"/>
										<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH.1417762879" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.INCLUDE_PATH" valueType="includePath">
											<listOptionValue builtIn="false" value="&quot;${CG_TOOL_ROOT}/include&quot;"/>
										</option>
										<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.810161531" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI" value="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compilerID.ABI.eabi" valueType="enumerated"/>
										<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS.433498946" name="C Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__C_SRCS"/>
										<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS.1372561398" name="C++ Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__CPP_SRCS"/>
										<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS.373232779" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM_SRCS"/>
										<inputType id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS.1657960016" name="Assembly Sources" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.compiler.inputType__ASM2_SRCS"/>
									</tool>
									<tool id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianRelease.789294139" name="«dsp.family» Archiver" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.library.librarianRelease">
										<option id="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE.1939389961" superClass="com.ti.ccstudio.buildDefinitions.«dsp.family»_7.4.archiverID.OUTPUT_FILE" value="&quot;${ProjName}.lib&quot;" valueType="string"/>
									</tool>
									<tool id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.1918107900" name="XDCtools" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool">
										<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR.1256801284" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.CODEGEN_TOOL_DIR" value="&quot;${CG_TOOL_ROOT}&quot;" valueType="string"/>
										<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET.659150684" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.TARGET" value="ti.targets.elf.C66" valueType="string"/>
										<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM.1407194260" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
										<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW.1958596244" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.PLATFORM_RAW" value="ti.runtime.openmp.platforms.evm6678" valueType="string"/>
										<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE.630394613" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.BUILD_PROFILE" value="release" valueType="string"/>
										<option id="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH.1999409029" superClass="com.ti.rtsc.buildDefinitions.XDC_3.16.tool.XDC_PATH" valueType="stringList">
											<listOptionValue builtIn="false" value="${IPC_CG_ROOT}/packages"/>
											<listOptionValue builtIn="false" value="${TI_PDK_INSTALL_DIR}/packages"/>
											<listOptionValue builtIn="false" value="${COM_TI_RTSC_OPENMP_INSTALL_DIR}/packages"/>
											<listOptionValue builtIn="false" value="${BIOS_CG_ROOT}/packages"/>
											<listOptionValue builtIn="false" value="${TARGET_CONTENT_BASE}"/>
										</option>
									</tool>
								</toolChain>
							</folderInfo>
						</configuration>
					</storageModule>
					<storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
				</cconfiguration>
			</storageModule>
			<storageModule moduleId="org.eclipse.cdt.core.LanguageSettingsProviders"/>
			<storageModule moduleId="cdtBuildSystem" version="4.0.0">
				<project id="«projectID».com.ti.ccstudio.buildDefinitions.«dsp.family».ProjectType.1699442895" name="«dsp.family»" projectType="com.ti.ccstudio.buildDefinitions.«dsp.family».ProjectType"/>
			</storageModule>
			<storageModule moduleId="scannerConfiguration"/>
			<storageModule moduleId="org.eclipse.cdt.core.language.mapping">
				<project-mappings>
					<content-type-mapping configuration="" content-type="org.eclipse.cdt.core.asmSource" language="com.ti.ccstudio.core.TIASMLanguage"/>
					<content-type-mapping configuration="" content-type="org.eclipse.cdt.core.cHeader" language="com.ti.ccstudio.core.TIGCCLanguage"/>
					<content-type-mapping configuration="" content-type="org.eclipse.cdt.core.cSource" language="com.ti.ccstudio.core.TIGCCLanguage"/>
					<content-type-mapping configuration="" content-type="org.eclipse.cdt.core.cxxHeader" language="com.ti.ccstudio.core.TIGPPLanguage"/>
					<content-type-mapping configuration="" content-type="org.eclipse.cdt.core.cxxSource" language="com.ti.ccstudio.core.TIGPPLanguage"/>
				</project-mappings>
			</storageModule>
		</cproject>
		'''

	def getCcsProject(DSP dsp, boolean isRTS) 
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
			«IF isRTS»
			<rts value="libc.a"/>
			«ENDIF»
			<createSlaveProjects value=""/>
			<templateProperties value="id=org.eclipse.rtsc.project.templates.EmptyRtscApplication,buildProfile=release,isHybrid=true,"/>
			<isTargetManual value="true"/>
		</projectOptions>
		'''

	def getCcxxml(DSP dsp) 
		'''
		<?xml version="1.0" encoding="UTF-8" standalone="no"?>
		<configurations XML_version="1.2" id="configurations_0">
		    <configuration XML_version="1.2" id="configuration_0">
		        <instance XML_version="1.2" desc="Blackhawk XDS560v2-USB Mezzanine Emulator" href="connections/BH-XDS560v2-USB_Mezzanine.xml" id="Blackhawk XDS560v2-USB Mezzanine Emulator" xml="BH-XDS560v2-USB_Mezzanine.xml" xmlpath="connections"/>
		        <connection XML_version="1.2" id="Blackhawk XDS560v2-USB Mezzanine Emulator">
		            <instance XML_version="1.2" href="drivers/tixds560icepick_d.xml" id="drivers" xml="tixds560icepick_d.xml" xmlpath="drivers"/>
		            <instance XML_version="1.2" href="drivers/tixds560c66xx.xml" id="drivers" xml="tixds560c66xx.xml" xmlpath="drivers"/>
		            <instance XML_version="1.2" href="drivers/tixds560cs_dap.xml" id="drivers" xml="tixds560cs_dap.xml" xmlpath="drivers"/>
		            <instance XML_version="1.2" href="drivers/tixds560csstm.xml" id="drivers" xml="tixds560csstm.xml" xmlpath="drivers"/>
		            <instance XML_version="1.2" href="drivers/tixds560etbcs.xml" id="drivers" xml="tixds560etbcs.xml" xmlpath="drivers"/>
		            <platform XML_version="1.2" id="platform_0">
		                <instance XML_version="1.2" desc="«dsp.deviceVersion»" href="devices/«dsp.device».xml" id="«dsp.deviceVersion»" xml="«dsp.device».xml" xmlpath="devices"/>
		            <device HW_revision="1" XML_version="1.2" description="«dsp.device» core" id="«dsp.deviceVersion»" partnum="«dsp.deviceVersion»">
		                    <router HW_revision="1.0" XML_version="1.2" description="ICEPick_D Router" id="IcePick_D_0" isa="ICEPICK_D">
		                        <subpath id="subpath_0">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_0" isa="«dsp.familyVersion»">
		                                <property Type="choicelist" Value="0" id="bypass"/>
		                                <property Type="filepathfield" Value="../../../evmc6678l.gel" id="GEL File"/>
		                            </cpu>
		                        </subpath>
		                        <subpath id="subpath_1">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_1" isa="«dsp.familyVersion»">
		                                <property Type="filepathfield" Value="../../../evmc6678l.gel" id="GEL File"/>
		                            </cpu>
		                        </subpath>
		                        <subpath id="subpath_2">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_2" isa="«dsp.familyVersion»">
		                                <property Type="choicelist" Value="1" id="bypass"/>
		                            </cpu>
		                        </subpath>
		                        <subpath id="subpath_3">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_3" isa="«dsp.familyVersion»">
		                                <property Type="choicelist" Value="1" id="bypass"/>
		                            </cpu>
		                        </subpath>
		                        <subpath id="subpath_4">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_4" isa="«dsp.familyVersion»">
		                                <property Type="choicelist" Value="1" id="bypass"/>
		                            </cpu>
		                        </subpath>
		                        <subpath id="subpath_5">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_5" isa="«dsp.familyVersion»">
		                                <property Type="choicelist" Value="1" id="bypass"/>
		                            </cpu>
		                        </subpath>
		                        <subpath id="subpath_6">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_6" isa="«dsp.familyVersion»">
		                                <property Type="choicelist" Value="1" id="bypass"/>
		                            </cpu>
		                        </subpath>
		                        <subpath id="subpath_7">
		                            <cpu HW_revision="1.0" XML_version="1.2" description="«dsp.device» CPU" id="«dsp.device»_7" isa="«dsp.familyVersion»">
		                                <property Type="choicelist" Value="1" id="bypass"/>
		                            </cpu>
		                        </subpath>
		                    </router>
		                </device>
		            </platform>
		        </connection>
		    </configuration>
		</configurations>		
		'''

}