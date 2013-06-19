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
import datetime


class Processor:

    def __init__(self, name, actors, inputs, outputs, usePrint):
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
        self.usePrint = usePrint
        # Useful filenames
        self._processorFile = self.id + ".vhd"
        self._memConstantsPkg = self.id + "_mem_constants_pkg.vhd"
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
        # Useful names
        self._entity = self.id + "_tl"


    def compile(self, srcPath, libPath, args, debug):
        processorPath = os.path.join(srcPath, self.id)
        actorsPath = os.path.join(srcPath, "actors")
        tempPath = os.path.join(processorPath, "temp")
        os.chdir(processorPath)
        
        
        sourceFiles = [self._llFile]
        for actor in self.actors:
            sourceFiles.append(os.path.join(actorsPath, actor + ".ll"))

        if not self.needRecompilation(self._tpefFile, sourceFiles + [self._adfFile]):
            return 0

        opt = args + ["-O3", "-o", self._tpefFile, "-a", self._adfFile]

        if debug:
            shutil.rmtree(tempPath, ignore_errors=True)
            os.mkdir(tempPath)
            opt = opt + ["--temp-dir", tempPath]
        if self.usePrint:
            opt = opt + ["-llwpr"]

        retcode = subprocess.call(["tcecc"] + opt + sourceFiles)

        if debug:
            if retcode == 0:
                retcode = subprocess.call(["llvm-dis", os.path.join(tempPath, self.id + ".tpef_optimized.bc")])
            if retcode == 0:
                retcode = subprocess.call(["tcedisasm", "-n", self._adfFile, self._tpefFile])
        
        return retcode

    def generate(self, srcPath, libPath, args, debug, targetAltera):
        instanceSrcPath = os.path.join(srcPath, self.id)
        ttaPath = os.path.join(instanceSrcPath, "tta")
        vhdlPath = os.path.join(ttaPath, "vhdl")
        wrapperPath = os.path.join(srcPath, "wrapper")
        sharePath = os.path.join(srcPath, "share")
        os.chdir(instanceSrcPath)
        
        # Remove existing build directory
        shutil.rmtree(ttaPath, ignore_errors=True)
        
        # Generate the TTA processor
        retcode = subprocess.call(["createbem", "-o", self._bemFile, self._adfFile])    
        if retcode == 0: 
            retcode = subprocess.call(["generateprocessor"] + args + ["-o", ttaPath, "-b", self._bemFile, "--shared-files-dir", sharePath,
                                        "-l", "vhdl", "-e", self._entity, "-i", self._idfFile, self._adfFile])
        if retcode == 0: 
            retcode = subprocess.call(["generatebits", "-e", self._entity, "-b", self._bemFile, "-d", "-w", "4", "-p", self._tpefFile, "-f", "mif", "-o", "mif", self._adfFile])
        if retcode == 0 and not targetAltera: 
            retcode = subprocess.call(["generatebits", "-e", self._entity, "-b", self._bemFile, "-d", "-w", "4", "-p", self._tpefFile, "-x", vhdlPath, "-f", "coe", "-o", "coe", self._adfFile])                

        # Generate processor files
        self.irom = self._readMif(self.id + ".mif")
        self.dram = self._readAdf(self._adfFile)
        if debug: 
            print "ROM: " + str(self.irom.depth) + "x" + str(self.irom.width) + "bits - " + str(self.irom.depth*self.irom.width/ 8) + " bytes"
            print "RAM: " + str(self.dram.depth) + "x" + str(self.dram.width) + "bits - " + str(self.dram.depth*self.dram.width/ 8) + " bytes"
            
        self.generateMemConstants(libPath, os.path.join(vhdlPath, self._memConstantsPkg))
        shutil.copy(self._processorFile, vhdlPath)
        
        if not targetAltera: 
            cgPath = os.path.join(instanceSrcPath, "ipcore_dir_gen")
            shutil.rmtree(cgPath, ignore_errors=True)
            os.mkdir(cgPath)
            shutil.move(self.id + "_data" + ".coe", cgPath)
            shutil.move(self.id + ".coe", cgPath)
            self.generateCgFiles(libPath, cgPath)
            
            if debug:
				retcode = subprocess.call(["coregen", "-intstyle", "xflow", "-b", os.path.join(cgPath, self._xoeRomFile), "-p", "ipcore_dir_gen/cg_project.cgp"])
				retcode = subprocess.call(["coregen", "-intstyle", "xflow", "-b", os.path.join(cgPath, self._xoeRamFile), "-p", "ipcore_dir_gen/cg_project.cgp"])
            else:
				retcode = subprocess.call(["coregen", "-intstyle", "silent", "-b", os.path.join(cgPath, self._xoeRomFile), "-p", "ipcore_dir_gen/cg_project.cgp"])
				retcode = subprocess.call(["coregen", "-intstyle", "silent", "-b", os.path.join(cgPath, self._xoeRamFile), "-p", "ipcore_dir_gen/cg_project.cgp"])
				
            shutil.copy(os.path.join(cgPath, self._ngcRomFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._ngcRamFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._mifRomFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._mifRamFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._vhdRomFile), vhdlPath)
            shutil.copy(os.path.join(cgPath, self._vhdRamFile), vhdlPath)
            
            if not debug:
				shutil.rmtree(cgPath, ignore_errors=True)
        
        # Copy files to build directory
        if targetAltera:
            shutil.move(self.id + ".mif", os.path.join(wrapperPath, self._mifRomFile))
            shutil.move(self.id + "_data.mif", os.path.join(wrapperPath, self._mifRamFile))
        else:
            os.remove(self.id + ".mif")
            os.remove(self.id + "_data.mif")
        shutil.move("imem_mau_pkg.vhdl", vhdlPath)    

        # Clean working directory
        os.remove(self._bemFile)
        shutil.rmtree("vhdl", ignore_errors=True)
        
        return retcode
            
    def profile(self, srcPath):
        instancePath = os.path.join(srcPath, self.id)
        os.chdir(instancePath)
        retcode = subprocess.call(["tcedisasm", "-F", self._adfFile, self._tpefFile])
        retcode = subprocess.call(["generate_cachegrind", self._tpefFile + ".trace"])
        return subprocess.check_output(["cgview", "-e", self._tpefFile + ".trace.cachegrind"])

    def simulate(self):
        if len(self.inputs)>0 and len(self.outputs)>0:
            log_file = open(self.id+'.log', 'w')
            print "Simulating %s" % self.id ,
            retcode=subprocess.call(["ttanetsim", "-n", "top.pndf", "-t", self.id], stdout=log_file)
            return retcode
        else:
            return 0

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
        
        return Memory(fileName, int(width), int(depth))

    def _readAdf(self, fileName):
        adf = parse(fileName)
        for node in adf.getElementsByTagName("address-space"):
            if node.hasAttributes() and (node.attributes["name"].value == "data"):
                width = int(node.getElementsByTagName("width")[0].childNodes[0].nodeValue) * 4
                minAddress = int(node.getElementsByTagName("min-address")[0].childNodes[0].nodeValue)
                maxAddress = int(node.getElementsByTagName("max-address")[0].childNodes[0].nodeValue)
                depth = int((maxAddress - minAddress) / 4)
        return Memory(fileName, width, depth)
        
    def _hasNativePort(self):
        for input in self.inputs:
            if(input.isNative):
                return True
        for output in self.outputs:
            if(output.isNative):
                return True
        return False

    def generateMemConstants(self, libPath, targetFile):
        template = tempita.Template.from_filename(os.path.join(libPath, "templates", "mem_constants.template"), namespace={}, encoding=None)
        result = template.substitute(id=self.id,
            irom_width=self.irom.getWidth(), irom_addr=self.irom.getAddr(), irom_depth=self.irom.getDepth(),
            dram_width=self.dram.getWidth(), dram_addr=self.dram.getAddr(), dram_depth=self.dram.getDepth())
        open(targetFile, "w").write(result)
        
    def generateCgFiles(self, libPath, genPath):
        templatePath = os.path.join(libPath, "templates")
        template = tempita.Template.from_filename(os.path.join(templatePath, "cg_project.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath)
        open(os.path.join(genPath, "cg_project.cgp"), "w").write(result)
        template = tempita.Template.from_filename(os.path.join(templatePath, "xco_ram_1p.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath, id=self.id, width=self.dram.getWidth(), depth=self.dram.getDepth())
        open(os.path.join(genPath, self._xoeRamFile), "w").write(result)
        template = tempita.Template.from_filename(os.path.join(templatePath, "xco_rom.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath, id=self.id, width=self.irom.getWidth(), depth=self.irom.getDepth())
        open(os.path.join(genPath, self._xoeRomFile), "w").write(result)

    def needRecompilation(self, tpefFile, depFiles):
        if not os.path.exists(tpefFile):
            return True
        t = os.path.getmtime(tpefFile)
        for depFile in depFiles:
            if os.path.getmtime(depFile) > t:
                return True
        return False
