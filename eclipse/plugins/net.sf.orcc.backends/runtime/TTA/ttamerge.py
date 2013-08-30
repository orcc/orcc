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

from ttalogs import *
import argparse
import os
import glob
import subprocess
import shutil
import sys

class TtaMergeDirs():
    def __init__(self):
        pass

    def mergeDirs(self, dirs):
        print "*********************************************************************"
        print "* Merge TTA logs"
        print "*********************************************************************"
        destPath = "tta_merge"
        print "\nCheck logs directories..."
        self.checkDirs(args.log_dirs)
        print "\nBuild merge directory : " + destPath
        shutil.rmtree(destPath, ignore_errors=True)
        os.mkdir(destPath)
        self.copyFiles(args.log_dirs)
        print "\nPerform merge..."
        self.performMerge(args.log_dirs)

    def checkDirs(self, dirs):
        for d in dirs:
            dn = os.path.dirname(d)
            dp = os.path.join(os.getcwd(), dn)
            print "===> Checking " + d
            ficHTML = os.path.join(dp, dn + ".html")
            ficCSV = os.path.join(dp, dn + ".csv")
            if not os.path.isdir(d):
                print "Error ===> " + d + " is not a directory"
                exit(1)
            if not os.path.exists(ficHTML):
                print "Error ===> " + ficHTML + " do not exits"
                exit(1)
            if not os.path.exists(ficCSV):
                print "Error ===> " + ficCSV + " do not exits"
                exit(1)

    def copyFiles(self, dirs):
        destPath = "tta_merge"
        shutil.copy(os.path.join(os.path.dirname(sys.argv[0]), "style.css"), destPath)
        for d in dirs:
            dn = os.path.dirname(d)
            dp = os.path.join(os.getcwd(), dn)
            print "===> Copying files from " + dp
            ficHTML = os.path.join(dp, dn + ".html")
            ficCSV = os.path.join(dp, dn + ".csv")
            shutil.copy(ficHTML, destPath)
            shutil.copy(ficCSV, destPath)

    def performMerge(self, dirs):
        destPath = "tta_merge"
        ficsHTML = []
        ficsCSV = []
        for d in dirs:
            dn = os.path.dirname(d)
            dp = os.path.join(os.getcwd(), dn)
            ficsHTML.append(os.path.join(dp, dn + ".html"))
            ficsCSV.append(os.path.join(dp, dn + ".csv"))
        srcPath = os.getcwd()
        os.chdir(destPath)
        ttah = TtaMergeHtml()
        ttah.mergeAllFiles(ficsHTML)
        ttac = TtaMergeCsv()
        ttac.mergeAllFiles(ficsCSV)

        # Generate a PDF file from HTML Merge
        print "\nGenerate PDF file..."
        subprocess.call(["wkhtmltopdf", "tta_merge.html", "tta_merge.pdf"])

# Main
parser = argparse.ArgumentParser()
parser.add_argument('log_dirs', metavar='dir', nargs='+',
    help='at least 2 directories containing logs from ttaextractlog')
args = parser.parse_args()

if len(args.log_dirs) <2:
    print ("! Error : At least 2 directories must be given to process a merge")
    exit(1)

# Begin
ttam = TtaMergeDirs()
ttam.mergeDirs(args.log_dirs)