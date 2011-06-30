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

class Instance:
    name = ''
    inputs = []
    outputs = []
    
    def __init__(self, name, inputs, outputs):
        self.name = name
        self.inputs = inputs
        self.outputs = outputs
    
    def compile(self, scriptPath):
        instancePath = os.path.join(scriptPath, self.name)
        os.chdir(instancePath)
        
        adfFile = 'processor_' + self.name + '.adf'
        llFile = self.name + '.ll'
        tpefFile = self.name + '.tpef'

        status,output = commands.getstatusoutput('tcecc -o ' + tpefFile + ' -a ' + adfFile + ' ' + llFile)
        if status != 0:
            print '** ERROR **'
            print output
        else:
            asmFile = instancePath + os.sep + self.name + '.tceasm'
            commands.getstatusoutput('tcedisasm -n -o ' + asmFile + ' ' + adfFile + ' ' + tpefFile) 
            
    def simulate(self, scriptPath): 
        instancePath = os.path.join(scriptPath, self.name)
        os.chdir(instancePath)
        
        # Copy trace to the instance folder
        for input in self.inputs:
            srcTrace = scriptPath + os.sep + 'trace' + os.sep + 'decoder_' + self.name + '_' + input.name + '.txt'
            tgtTrace = instancePath + os.sep + 'tta_stream_v%d.in' % (input.index)
            status,output = commands.getstatusoutput('cp ' + srcTrace + ' ' + tgtTrace)
            if status != 0:
                print output
        
        adfFile = 'processor_' + self.name + '.adf'
        tpefFile = self.name + '.tpef'
        status,output = commands.getstatusoutput('ttasim --no-debugmode -q -a ' + adfFile + ' -p ' + tpefFile)

