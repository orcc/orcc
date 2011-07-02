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

class Instance:
    name = ""
    # Ports
    inputs = []
    outputs = []
    # Useful filenames
    _adfFile = ""
    _idfFile = ""
    _llFile = ""
    _tpefFile = ""
    _asmFile = ""
    _bemFile = ""
    # Useful names
    _entity = ""

    def __init__(self, name, inputs, outputs):
        self.name = name
        self.inputs = inputs
        self.outputs = outputs
        self._adfFile = "processor_" + self.name + ".adf"
        self._idfFile = "processor_" + self.name + ".idf"
        self._llFile = self.name + ".ll"
        self._tpefFile = self.name + ".tpef"
        self._asmFile = self.name + ".tceasm"
        self._bemFile = self.name + ".bem"
        self._entity = "processor_" + self.name + "_tl"


    def compile(self, srcPath):
        instancePath = os.path.join(srcPath, self.name)
        os.chdir(instancePath)

        status,output = commands.getstatusoutput("tcecc -o " + self._tpefFile + " -a " + self._adfFile + " " + self._llFile)
        if status != 0:
            print "** ERROR **"
            print output
        else:
            commands.getstatusoutput("tcedisasm -n -o " + self._asmFile + " " + self._adfFile + " " + self._tpefFile)

    def generate(self, srcPath, buildPath, libPath):
		srcPath = os.path.join(srcPath, self.name)
		sharePath = os.path.join(buildPath, "share")
		buildPath = os.path.join(buildPath, self.name)
		os.chdir(srcPath)
		retcode = subprocess.call(["createbem", "-o", self._bemFile, self._adfFile])
		retcode = subprocess.call(["cp", "-f", os.path.join(libPath, "fifo", "many_streams.hdb"), srcPath])
		retcode = subprocess.call(["cp", "-Rf", os.path.join(libPath, "fifo", "vhdl"), srcPath])
		retcode = subprocess.call(["rm", "-rf", buildPath])
		retcode = subprocess.call(["generateprocessor", "-o", buildPath, "-b", self._bemFile, "--shared-files-dir", sharePath, 
		"-l", "vhdl", "-e", self._entity, "-i", self._idfFile, self._adfFile])
		retcode = subprocess.call(["rm", "-f", "many_streams.hdb"])
		retcode = subprocess.call(["rm", "-rf", os.path.join(srcPath, "vhdl")])

    def simulate(self, srcPath):
        instancePath = os.path.join(srcPath, self.name)
        os.chdir(instancePath)

        # Copy trace to the instance folder
        for input in self.inputs:
            srcTrace = srcPath + os.sep + "trace" + os.sep + "decoder_" + self.name + "_" + input.name + ".txt"
            tgtTrace = instancePath + os.sep + "tta_stream_v%d.in" % (input.index)
            status,output = commands.getstatusoutput("cp " + srcTrace + " " + tgtTrace)
            if status != 0:
                print output

        status,output = commands.getstatusoutput("ttasim --no-debugmode -q -a " + self._adfFile + " -p " + self._tpefFile)
        

