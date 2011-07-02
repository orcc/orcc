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

class Instance:

    def __init__(self, name, inputs, outputs):
        self.name = name
        # Ports
        self.inputs = inputs
        self.outputs = outputs
        # Memories
        self.rom = None
        self.ram = None
        # Useful filenames
        self._adfFile = "processor_" + self.name + ".adf"
        self._idfFile = "processor_" + self.name + ".idf"
        self._llFile = self.name + ".ll"
        self._tpefFile = self.name + ".tpef"
        self._asmFile = self.name + ".tceasm"
        self._bemFile = self.name + ".bem"
        self._mifFile = self.name + ".mif"
        self._mifDataFile = self.name + "_data" + ".mif"
        self._romFile = "irom_" + self.name + ".vhd"
        self._ramFile = "dram_" + self.name + ".vhd"
        # Useful names
        self._entity = "processor_" + self.name + "_tl"

    def compile(self, srcPath):
        instancePath = os.path.join(srcPath, self.name)
        os.chdir(instancePath)
        retcode = subprocess.call(["tcecc", "-o", self._tpefFile, "-a", self._adfFile, self._llFile])
        if retcode >= 0: retcode = subprocess.call(["tcedisasm", "-n", "-o", self._asmFile, self._adfFile, self._tpefFile])
        if retcode >= 0: retcode = subprocess.call(["createbem", "-o", self._bemFile, self._adfFile])
        if retcode >= 0: retcode = subprocess.call(["generatebits", "-e", self._entity, "-b", self._bemFile, "-d", "-w", "4", "-p", self._tpefFile, "-x", "images", "-f", "mif", "-o", "mif", self._adfFile])

    def generate(self, srcPath, buildPath, libPath):
        srcPath = os.path.join(srcPath, self.name)
        sharePath = os.path.join(buildPath, "share")
        buildPath = os.path.join(buildPath, self.name)
        os.chdir(srcPath)
        # Copy libraries in working directory
        shutil.copy(os.path.join(libPath, "fifo", "many_streams.hdb"), srcPath)
        shutil.rmtree("vhdl", ignore_errors=True)
        shutil.copytree(os.path.join(libPath, "fifo", "vhdl"), "vhdl")
        # Remove existing build directory
        shutil.rmtree(buildPath)
        # Generate the processor in build directory
        subprocess.call(["generateprocessor", "-o", buildPath, "-b", self._bemFile, "--shared-files-dir", sharePath, 
                                        "-l", "vhdl", "-e", self._entity, "-i", self._idfFile, self._adfFile])
        # Copy memories to build directory
        shutil.copy(self._mifFile, buildPath)
        shutil.copy(self._mifDataFile, buildPath)
        shutil.copy("imem_mau_pkg.vhdl", buildPath)
        shutil.copy(self._romFile, buildPath)
        shutil.copy(self._ramFile, buildPath)
        
        # Clean working directory
        os.remove("many_streams.hdb")
        shutil.rmtree("vhdl", ignore_errors=True)

    def simulate(self, srcPath):
        instancePath = os.path.join(srcPath, self.name)
        os.chdir(instancePath)

        # Copy trace to the instance folder
        for input in self.inputs:
            traceName = "decoder_" + self.name + "_" + input.name + ".txt"
            fifoName = "tta_stream_v%d.in" % (input.index)
            srcTrace = os.path.join(srcPath, "trace", traceName)
            tgtTrace = os.path.join(instancePath, "test")
            retcode = subprocess.call(["cp", srcTrace, tgtTrace])

        retcode = subprocess.call(["ttasim", "--no-debugmode", "-q", "-a", self._adfFile, "-p", self._tpefFile])
        

