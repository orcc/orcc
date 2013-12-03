#!/usr/bin/env python
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

from .processor import *
import os
import shutil
import stat
import sys
import subprocess
from multiprocessing import Process
from multiprocessing import Queue
import multiprocessing


class Design:

    def __init__(self, name, processors, memories, targetAltera):
        self.name = name
        self.processors = processors
        self.memories = memories
        self.targetAltera = targetAltera
        self.sema = multiprocessing.BoundedSemaphore(value=1)

    def compileMulti(self, proc, srcPath, libPath, args, debug, results):
        self.sema.acquire()
        print ">> Compile code of " + proc.id + "."
        retcode = proc.compile(srcPath, libPath, args, debug)
        results.append(retcode)
        self.sema.release()

    def compile(self, srcPath, libPath, args, debug, nbJobs):
        self.sema = multiprocessing.BoundedSemaphore(value=nbJobs)
        os.putenv("TCE_OSAL_PATH", os.path.join(libPath, "opset"))
        retcode = subprocess.call(["buildopset", os.path.join(libPath, "opset", "orcc")])

        if retcode == 0:
            results = multiprocessing.Manager().list()
            jobs = [multiprocessing.Process(target=self.compileMulti, args=(processor, srcPath, libPath, args, debug, results)) 
                for processor in self.processors]
            for job in jobs: job.start()
            for job in jobs: job.join()
            retcode = max(results)

        if retcode != 0: 
            raise Exception("Problem during the compilation")

                
    def profile(self, srcPath):
        f = open("profiling.txt", "w")
        for processor in self.processors:
            f.write("*********************************************************\n")
            f.write("****** TTA: " + processor.id + "\n")
            f.write("*********************************************************\n")
            f.write(processor.profile(srcPath))
            f.write("\n\n\n")
        f.close()

    def generate(self, srcPath, libPath, args, debug):
        print "* Initialize the generation."
        os.chmod(os.path.join(srcPath, "top.tcl"), stat.S_IRWXU)
        shutil.rmtree(os.path.join(srcPath, "wrapper"), ignore_errors=True)
        shutil.copytree(os.path.join(libPath, "wrapper"), os.path.join(srcPath, "wrapper"))
        shutil.rmtree(os.path.join(srcPath, "interface"), ignore_errors=True)
        shutil.copytree(os.path.join(libPath, "interface"), os.path.join(srcPath, "interface"))
        shutil.rmtree(os.path.join(srcPath, "simulation"), ignore_errors=True)
        shutil.copytree(os.path.join(libPath, "simulation"), os.path.join(srcPath, "simulation"))

        if not self.targetAltera: 
            cgPath = os.path.join(srcPath, "ipcore_dir_gen")
            shutil.rmtree(cgPath, ignore_errors=True)
            os.mkdir(cgPath)

            templatePath = os.path.join(libPath, "templates")
            template = tempita.Template.from_filename(os.path.join(templatePath, "cg_project.template"), namespace={}, encoding=None)
            result = template.substitute(path=cgPath)
            open(os.path.join(cgPath, "cg_project.cgp"), "w").write(result)

            for memory in self.memories:
                id = "dram_2p_" + memory.getName()
                ngcRamFile = id + ".ngc"
                vhdRamFile = id + ".vhd"
                xoeRamFile = id + ".xoe"

                template = tempita.Template.from_filename(os.path.join(templatePath, "xco_ram_2p.template"), namespace={}, encoding=None)
                result = template.substitute(path=cgPath, id=memory.getName(), width=memory.getWidth(), depth=memory.getDepth())
                open(os.path.join(cgPath, xoeRamFile), "w").write(result)

                retcode = subprocess.call(["coregen", "-intstyle", "xflow", "-b", os.path.join(cgPath, xoeRamFile), "-p", os.path.join(cgPath, "cg_project.cgp")])

                shutil.copy(os.path.join(cgPath, ngcRamFile), os.path.join(srcPath, "wrapper"))
                shutil.copy(os.path.join(cgPath, vhdRamFile), os.path.join(srcPath, "wrapper"))
                
            if not debug:
                shutil.rmtree(cgPath, ignore_errors=True)

        for processor in self.processors:
            print ">> Generate " + processor.id + "."
            retcode = processor.generate(srcPath, libPath, args, debug, self.targetAltera)
            if retcode != 0: 
                raise Exception("Problem during the generation")
                return;
                
    def generateCgFiles(self, libPath, genPath):
        templatePath = os.path.join(libPath, "templates")
        template = tempita.Template.from_filename(os.path.join(templatePath, "cg_project.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath)
        open(os.path.join(genPath, "cg_project.cgp"), "w").write(result)
        template = tempita.Template.from_filename(os.path.join(templatePath, "xco_ram_2p.template"), namespace={}, encoding=None)
        result = template.substitute(path=genPath, id="ram_2p", width=512, depth=32)
        open(os.path.join(genPath, self._xoeRamFile), "w").write(result)
    
    def simulateMulti(self, proc):
        self.sema.acquire()
        proc.simulate()
        self.sema.release()
        return

    def simulate(self, nbJobs):
        self.sema = multiprocessing.BoundedSemaphore(value=nbJobs)
        for processor in self.processors:
            p = multiprocessing.Process(target=self.simulateMulti, args=(processor, ))
            p.start()
        for processor in self.processors:
            p.join()
