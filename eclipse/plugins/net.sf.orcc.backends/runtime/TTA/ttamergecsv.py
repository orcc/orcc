#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Copyright (c) 2013, INSA
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
# @author Alexandre Sanchez

import argparse
import os
import glob
import subprocess

def mergeCSVFiles(file1,file2):
    destination = ".ttamerge_"+os.path.basename(file2)
    fs1 = open(file1, 'r')
    fs2 = open(file2, 'r')
    fd = open(destination, 'w')
    while 1:
        txt1 = fs1.readline().rstrip('\n\r')
        fistcol, sep, txt2 = fs2.readline().partition(';')
        fd.write(txt1+";"+txt2)
        if txt1 =='':
            break
    fs1.close()
    fs2.close()
    fd.close()
    return destination

def mergeAllCSVFiles(files):
    print "*********************************************************************"
    print "* Merge TTA logs CSV"
    print "*********************************************************************"
    fileBase = files[0].name
    del files[0]
    print "Merging " + fileBase,

    for fs in files:
        print "\n===> with " + fs.name
        fileBase = mergeCSVFiles(fileBase,fs.name)

    os.rename(fileBase, "tta_merge.csv")
    for tmpfile in glob.glob('.ttamerge*.csv') :
        os.remove( tmpfile ) 

parser = argparse.ArgumentParser()
parser.add_argument('log_files', metavar='file', type=file, nargs='+',
    help='at least 2 csv files containing summary logs from ttaextractlog')
args = parser.parse_args()

if len(args.log_files) <2:
    print ("! Error : At least 2 files must be given to process a merge")
    exit(1)

mergeAllCSVFiles(args.log_files)