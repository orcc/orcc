#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Copyright (c) 2013, INSA Rennes
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
#   * Neither the name of INSA Rennes nor the names of its
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
# @author Alexandre Sanchez

from __future__ import division
import os
import glob
import shutil
import sys
import subprocess

class TtaMerge:
    def __init__(self):
        self.ext = ""

    def performMerge(self,file1,file2,fd):
        raise NotImplementedError

    def mergeFiles(self,file1,file2):
        destination = ".ttamerge_"+os.path.basename(file2)
        fs1 = open(file1, 'r')
        fs2 = open(file2, 'r')
        fd = open(destination, 'w')
        self.performMerge(fs1, fs2, fd)
        fs1.close()
        fs2.close()
        fd.close()
        return destination

    def mergeAllFiles(self,files):
        print "*********************************************************************"
        print "* Merge TTA logs " + self.ext
        print "*********************************************************************"
        fileBase = files[0]
        del files[0]
        print "Merging " + fileBase,

        for fs in files:
            print "\n===> with " + fs
            fileBase = self.mergeFiles(fileBase,fs)

        os.rename(fileBase, "tta_merge."+self.ext)
        for tmpfile in glob.glob('.ttamerge*.'+self.ext) :
            os.remove( tmpfile ) 

        self.postMerge()

    def postMerge(self):
        pass

class TtaMergeCsv(TtaMerge):
    def __init__(self):
        self.ext = "csv"

    def performMerge(self,fs1,fs2,fd):
        while 1:
            txt1 = fs1.readline().rstrip('\n\r')
            fistcol, sep, txt2 = fs2.readline().partition(';')
            fd.write(txt1+";"+txt2)
            if txt1 =='':
                break

class TtaMergeHtml(TtaMerge):
    def __init__(self):
        self.ext = "html"

    def performMerge(self,fs1,fs2,fd):
        while 1:
            txt1 = fs1.readline()
            txt2 = fs2.readline()
            if txt1.count("<tr>") == 1:
                txt2 = fs2.readline()
                while txt1.count("</tr>") == 0:
                    fd.write(txt1)
                    txt1 = fs1.readline()
                while txt2.count("</tr>") == 0:
                    if txt2.count("rounded-head-left") == 0 and txt2.count(">Actor<") == 0 and (txt2.count("<th") == 1):
                        fd.write(txt2)
                    elif txt2.count("<td class=") == 1 and txt2.count("rounded-foot-left") == 0:
                        fd.write(txt2)
                    elif txt2.count("rounded-foot-right") == 1:
                        fd.write(txt2)
                    txt2 = fs2.readline()
            if txt1 =='':
                break
            if txt1 == txt2:
                fd.write(txt1)

    def postMerge(self):
        # Generate a PDF file from HTML Summary
        subprocess.call(["wkhtmltopdf", "tta_merge.html", "tta_merge.pdf"])