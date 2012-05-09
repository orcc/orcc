# -*- coding: utf-8 -*-
#
# Copyright (c) 2011, IRISA
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
#   * Redistributions of source code must retain the above copyright notice,
#     this list of conditions and the following disclaimer.
#   * Redistributions in binary form must reproduce the above copyright notice,
#     this list of conditions and the following disclaimer in the documentation
#     and/or other materials provided with the distribution.
#   * Neither the name of IRISA nor the names of its
#     contributors may be used to endorse or promote products derived from this
#     software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
# ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
# LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
# INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
# STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
# WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
# SUCH DAMAGE.
#
# @author Herve Yviquel

from .memory import *
from .port import *
from xml.dom.minidom import parse
import commands
import math
import os
import shutil
import stat
import subprocess
import tempita


class Processor:

    def __init__(self, name, actors, inputs, outputs):
        # General
        self.id = name
        # Ports
        self.inputs = inputs
        self.outputs = outputs
        # Memories
        self.irom = None
        self.dram = None
        # Actors
        self.actors = actors
        # Useful filenames
        self._processorFile = self.id + ".vhd"
        self._tbFile = self.id + "_tb.vhd"
        self._tclFile = self.id + ".tcl"
        self._adfFile = self.id + ".adf"
        self._idfFile = self.id + ".idf"
        self._llFile = self.id + ".ll"
        self._bcFile = self.id + ".bc"
        self._llOptFile = self.id + "_opt.ll"
        self._tpefFile = self.id + ".tpef"
        self._asmFile = self.id + ".tceasm"
        self._bemFile = self.id + ".bem"
        self._mifRomFile = "irom_" + self.id + ".mif"
        self._vhdRomFile = "irom_" + self.id + ".vhd"
        self._xoeRomFile = "irom_" + self.id + ".xoe"
        self._ngcRomFile = "irom_" + self.id + ".ngc"
        self._mifRamFile = "dram_" + self.id + ".mif"
        self._vhdRamFile = "dram_" + self.id + ".vhd"
        self._xoeRamFile = "dram_" + self.id + ".xoe"
        self._ngcRamFile = "dram_" + self.id + ".ngc"
        self._waveFile = "wave.do"
        # Useful names
        self._entity = self.id + "_tl"


    def compile(self, srcPath, libPath, args, debug):
        processorPath = os.path.join(srcPath, self.id)
        actorsPath = os.path.join(srcPath, "actors")
        os.chdir(processorPath)
        
        sourceFiles = [self._llFile]
        for actor in self.actors:
            sourceFiles.append(os.path.join(actorsPath, actor + ".ll"))
        
        retcode = subprocess.call(["tcecc"] + args + ["-o", self._tpefFile, "-a", self._adfFile] + sourceFiles)
        if retcode == 0 and debug: retcode = subprocess.call(["tcecc", "-O3", "-o", self._bcFile, self._llFile, "--emit-llvm"])
        if retcode == 0 and debug: retcode = subprocess.call(["llvm-dis", "-o", self._llOptFile, self._bcFile])
        if retcode == 0 and debug: retcode = subprocess.call(["tcedisasm", "-n", "-o", self._asmFile, self._adfFile, self._tpefFile])
        return retcode

    def generate(self, srcPath, libPath, args, debug, targetAltera):
        instanceSrcPath = os.path.join(srcPath, self.id)
        ttaPath = os.path.join(instanceSrcPath, "tta")
        vhdlPath = os.path.join(ttaPath, "vhdl")
        wrapperPath = os.path.join(srcPath, "wrapper")
        sharePath = os.path.join(srcPath, "share")
        os.chdir(instanceSrcPath)
        
        # Clean old generation
        shutil.rmtree(ttaPath, ignore_errors=True)

        # Copy libraries in working directory
        shutil.copy(os.path.join(libPath, "stream", "stream_units.hdb"), instanceSrcPath)
        shutil.rmtree("vhdl", ignore_errors=True)
        shutil.copytree(os.path.join(libPath, "stream", "vhdl"), "vhdl")
        shutil.copy(os.path.join(libPath, "stream", "opset", "stream_units.opb"), instanceSrcPath)
        shutil.copy(os.path.join(libPath, "stream", "opset", "stream_units.opp"), instanceSrcPath)
        # Remove existing build directory
        shutil.rmtree(ttaPath, ignore_errors=True)
        # Generate the TTA processor
        retcode = subprocess.call(["createbem", "-o", self._bemFile, self._adfFile])    
        if retcode == 0: retcode = subprocess.call(["generateprocessor"] + args + ["-o", ttaPath, "-b", self._bemFile, "--shared-files-dir", sharePath,
                                        "-l", "vhdl", "-e", self._entity, "-i", self._idfFile, self._adfFile])
        if retcode == 0: 
            retcode = subprocess.call(["generatebits", "-e", self._entity, "-b", self._bemFile, "-d", "-w", "4", "-p", self._tpefFile, "-x", vhdlPath, "-f", "mif", "-o", "mif", self._adfFile])
            if not targetAltera:
                retcode = subprocess.call(["generatebits", "-e", self._entity, "-b", self._bemFile, "-d", "-w", "4", "-p", self._tpefFile, "-x", vhdlPath, "-f", "coe", "-o", "coe", self._adfFile])                

        # Generate processor files
        self.irom = self._readMif(self.id + ".mif")
        self.dram = self._readAdf(self._adfFile)
        if debug: 
            print "ROM: " + str(self.irom.depth) + "x" + str(self.irom.width) + "bits"
            print "RAM: " + str(self.dram.depth) + "x" + str(self.dram.width) + "bits"
            
        self.generateProcessor(libPath, os.path.join(vhdlPath, self._processorFile), targetAltera)
        if not targetAltera: 
            cgPath = os.path.join(instanceSrcPath, "ipcore_dir_gen")
            shutil.rmtree(cgPath, ignore_errors=True)
            os.mkdir(cgPath)
            shutil.move(self.id + "_data" + ".coe", cgPath)
            shutil.move(self.id + ".coe", cgPath)
            self.generateCgFiles(libPath, cgPath)
            retcode = subprocess.call(["coregen", "-intstyle", "xflow", "-b", os.path.join(cgPath, self._xoeRomFile), "-p", "ipcore_dir_gen/cg_project.cgp"])
            retcode = subprocess.call(["coregen", "-intstyle", "xflow", "-b", os.path.join(cgPath, self._xoeRamFile), "-p", "ipcore_dir_gen/cg_project.cgp"])
            shutil.copy(os.path.join(cgPath, self._ngcRomFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._ngcRamFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._mifRomFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._mifRamFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._vhdRomFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._vhdRamFile), vhdlPath)
            #shutil.rmtree(cgPath, ignore_errors=True)
        
        # Copy files to build directory
        if targetAltera:
            shutil.move(self.id + ".mif", os.path.join(wrapperPath, self._mifRomFile))
            shutil.move(self.id + "_data.mif", os.path.join(wrapperPath, self._mifRamFile))
        else:
            os.remove(self.id + ".mif")
            os.remove(self.id + "_data.mif")
        shutil.move("imem_mau_pkg.vhdl", vhdlPath)
        
        # Manage simulation files
        os.chmod(os.path.join(instanceSrcPath, self._tclFile), stat.S_IRWXU)        

        # Clean working directory
        os.remove("stream_units.hdb")
        os.remove(self._bemFile)
        shutil.rmtree("vhdl", ignore_errors=True)
        os.remove("stream_units.opp")
        os.remove("stream_units.opb")
        
        return retcode


    def simulate(self, srcPath, libPath, tracePath):
        if self.inputs and not self._hasNativePort() :
            instancePath = os.path.join(srcPath, self.id)
            os.chdir(instancePath)
            
            shutil.copy(os.path.join(libPath, "stream", "opset", "stream_units.opb"), instancePath)
            shutil.copy(os.path.join(libPath, "stream", "opset", "stream_units.opp"), instancePath)
            shutil.copy(os.path.join(libPath, "stream", "opset", "stream_units.cc"), instancePath)

            # Copy trace to the instance folder
            for input in self.inputs:
                traceName = self.id + "_" + input.name + ".txt"
                fifoName = "tta_stream_%d.in" % (input.index)
                srcTrace = os.path.join(tracePath, traceName)
                tgtTrace = os.path.join(instancePath, fifoName)
                shutil.copy(srcTrace, tgtTrace)

            # Launch the simulation
            retcode = subprocess.call(["ttasim", "--no-debugmode", "-e", "run; set filename stats.txt; set fileId [open $filename w]; puts $fileId [info proc cycles]; puts $fileId [info proc stats]; close $fileId; exit;", "-a", self._adfFile, "-p", self._tpefFile])

            # Check generated data
            i = 0
            for output in self.outputs:
                i += 1
                traceName = self.id + "_" + output.name + ".txt"
                fifoName = "tta_stream_%d.out" % (output.index)
                srcTrace = os.path.join(tracePath, traceName)
                tgtTrace = os.path.join(instancePath, fifoName)
                self.diff(srcTrace, tgtTrace, output)
                
            os.remove("stream_units.opp")
            os.remove("stream_units.opb")
            os.remove("stream_units.cc")


    def _readMif(self, fileName):
        fh = open(fileName, "r")
        igot = fh.readlines()

        for line in igot:
            if line.find("WIDTH") > -1:
                content = line.split()
                width = content[2]
                width = width[:len(width) - 1]
            if line.find("DEPTH") > -1:
                content = line.split()
                depth = content[2]
                depth = depth[:len(depth) - 1]
        
        return Memory(int(width), int(depth))

    def _readAdf(self, fileName):
        adf = parse(fileName)
        for node in adf.getElementsByTagName("address-space"):
            if node.hasAttributes() and (node.attributes["name"].value == "data"):
                width = int(node.getElementsByTagName("width")[0].childNodes[0].nodeValue) * 4
                minAddress = int(node.getElementsByTagName("min-address")[0].childNodes[0].nodeValue)
                maxAddress = int(node.getElementsByTagName("max-address")[0].childNodes[0].nodeValue)
                depth = int((maxAddress - minAddress) / 4)
        return Memory(width, depth)
        
    def _hasNativePort(self):
        for input in self.inputs:
            if(input.isNative):
                return True;
        for output in self.outputs:
            if(output.isNative):
                return True;
        return False        

    def generateProcessor(self, libPath, targetFile, targetAltera):
        template = tempita.Template.from_filename(os.path.join(libPath, "templates", "processor.template"), namespace={}, encoding=None)
        result = template.substitute(id=self.id, targetAltera=targetAltera, inputs=self.inputs, outputs=self.outputs,
            irom_width=self.irom.getWidth(), irom_addr=self.irom.getAddr(), irom_depth=self.irom.getDepth(),
            dram_width=self.dram.getWidth(), dram_addr=self.dram.getAddr(), dram_depth=self.dram.getDepth())
        open(targetFile, "w").write(result)
        
    def generateCgFiles(self, libPath, genPath):
        templatePath = os.path.join(libPath, "templates")
        template = tempita.Template.from_filename(os.path.join(templatePath, "cg_project.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath)
        open(os.path.join(genPath, "cg_project.cgp"), "w").write(result)
        template = tempita.Template.from_filename(os.path.join(templatePath, "xco_dram.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath, id=self.id, width=self.dram.getWidth(), depth=self.dram.getDepth())
        open(os.path.join(genPath, self._xoeRamFile), "w").write(result)
        template = tempita.Template.from_filename(os.path.join(templatePath, "xco_irom.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath, id=self.id, width=self.irom.getWidth(), depth=self.irom.getDepth())
        open(os.path.join(genPath, self._xoeRomFile), "w").write(result)

    def diff(self, traceFile, genFile, port):
        f_trace = open(traceFile, 'r')
        f_gen = open(genFile, 'r')

        # Compare files line after line
        i = 0
        for ligne1 in f_gen:
            i += 1
            ligne2 = f_trace.readline()
            if ligne1 != ligne2:
                break

        # Compute the number of line
        f_gen.seek(0)
        j = 0
        for ligne1 in f_gen:
            j += 1

        if i != j:
            print "ERROR: Wrong generated data on '" + port.name + "' (index " + str(port.index) + " - line " + str(i) + ")."


