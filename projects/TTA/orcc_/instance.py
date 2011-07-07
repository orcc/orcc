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


import os
import commands
import subprocess
import shutil
import tempita

from .port import *
from .memory import *

class Instance:

    def __init__(self, name, inputs, outputs, isNative):
        # General
        self.id = name
        self.isNative = isNative
        # Ports
        self.inputs = inputs
        self.outputs = outputs
        # Memories
        self.irom = None
        self.dram = None
        # Useful filenames
        self._processorFile = "processor_" + self.id + ".vhd"
        self._adfFile = "processor_" + self.id + ".adf"
        self._idfFile = "processor_" + self.id + ".idf"
        self._llFile = self.id + ".ll"
        self._tpefFile = self.id + ".tpef"
        self._asmFile = self.id + ".tceasm"
        self._bemFile = self.id + ".bem"
        self._mifFile = self.id + ".mif"
        self._mifDataFile = self.id + "_data" + ".mif"
        self._romFile = "irom_" + self.id + ".vhd"
        self._ramFile = "dram_" + self.id + ".vhd"
        # Useful names
        self._entity = "processor_" + self.id + "_tl"


    def compile(self, srcPath, libPath):
        instancePath = os.path.join(srcPath, self.id)
        os.chdir(instancePath)
        if self.isNative:
            shutil.copy(os.path.join(libPath, "native", self._llFile), instancePath)
            shutil.copy(os.path.join(libPath, "native", self._adfFile), instancePath)
            shutil.copy(os.path.join(libPath, "native", self._idfFile), instancePath)
        retcode = subprocess.call(["tcecc", "-o", self._tpefFile, "-a", self._adfFile, self._llFile])
        if retcode >= 0: retcode = subprocess.call(["tcedisasm", "-n", "-o", self._asmFile, self._adfFile, self._tpefFile])
        if retcode >= 0: retcode = subprocess.call(["createbem", "-o", self._bemFile, self._adfFile])
        if retcode >= 0: retcode = subprocess.call(["generatebits", "-e", self._entity, "-b", self._bemFile, "-d", "-w", "4", "-p", self._tpefFile, "-x", "images", "-f", "mif", "-o", "mif", self._adfFile])

    def initializeMemories(self, srcPath):
        srcPath = os.path.join(srcPath, self.id)
        os.chdir(srcPath)
        self.irom = self._readMemory(self._mifFile)
        self.dram = self._readMemory(self._mifDataFile)

    def generate(self, srcPath, buildPath, libPath, pylibPath, iromAddrMax, dramAddrMax):
        srcPath = os.path.join(srcPath, self.id)
        sharePath = os.path.join(buildPath, "share")
        buildPath = os.path.join(buildPath, self.id)
        os.chdir(srcPath)
        # Copy libraries in working directory
        shutil.copy(os.path.join(libPath, "fifo", "many_streams.hdb"), srcPath)
        shutil.rmtree("vhdl", ignore_errors=True)
        shutil.copytree(os.path.join(libPath, "fifo", "vhdl"), "vhdl")
        # Remove existing build directory
        shutil.rmtree(buildPath, ignore_errors=True)
        # Generate the processor
        subprocess.call(["generateprocessor", "-o", buildPath, "-b", self._bemFile, "--shared-files-dir", sharePath,
                                        "-l", "vhdl", "-e", self._entity, "-i", self._idfFile, self._adfFile])
        # Generate vhdl memory and processor files
        self.irom.generate(self.id, os.path.join(libPath, "memory", "rom.template"), os.path.join(buildPath, self._romFile))
        self.dram.generate(self.id, os.path.join(libPath, "memory", "ram.template"), os.path.join(buildPath, self._ramFile))
        if self.isNative:
            shutil.copy(os.path.join(libPath, "native", self._processorFile), buildPath)
        else:
            self.generateProcessor(os.path.join(libPath, "processor", "processor.template"), os.path.join(buildPath, self._processorFile), iromAddrMax, dramAddrMax)
        # Copy files to build directory
        shutil.copy(self._mifFile, buildPath)
        shutil.copy(self._mifDataFile, buildPath)
        shutil.copy("imem_mau_pkg.vhdl", buildPath)

        # Clean working directory
        os.remove("many_streams.hdb")
        shutil.rmtree("vhdl", ignore_errors=True)


    def simulate(self, srcPath):
        instancePath = os.path.join(srcPath, self.id)
        os.chdir(instancePath)

        # Copy trace to the instance folder
        for input in self.inputs:
            traceName = "decoder_" + self.id + "_" + input.name + ".txt"
            fifoName = "tta_stream_v%d.in" % (input.index)
            srcTrace = os.path.join(srcPath, "trace", traceName)
            tgtTrace = os.path.join(instancePath, "test")
            retcode = subprocess.call(["cp", srcTrace, tgtTrace])

        retcode = subprocess.call(["ttasim", "--no-debugmode", "-q", "-a", self._adfFile, "-p", self._tpefFile])


    def _readMemory(self, fileName):
        fh = open(fileName, "r")
        igot = fh.readlines()

        for line in igot:
            if line.find("WIDTH") > -1:
                content = line.split()
                width = content[2]
                width = width[:len(width)-1]
            if line.find("DEPTH") > -1:
                content = line.split()
                depth = content[2]
                depth = depth[:len(depth)-1]

        return Memory(int(width), int(depth))


    def generateProcessor(self, templateFile, targetFile, iromAddrMax, dramAddrMax):
        template = tempita.Template.from_filename(templateFile, namespace={}, encoding=None)
        result = template.substitute(id=self.id, inputs=self.inputs, outputs=self.outputs,
                            irom_width=self.irom.getWidth(), irom_addr=self.irom.getAddr(),
                            dram_width=self.dram.getWidth(), dram_addr=self.dram.getAddr(),
                            irom_addr_max=iromAddrMax, dram_addr_max=dramAddrMax)
        open(targetFile, "w").write(result)


