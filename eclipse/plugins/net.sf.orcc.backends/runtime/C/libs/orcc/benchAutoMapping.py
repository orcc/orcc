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
import argparse
import time

def performBench():
    print "*********************************************************************"
    print "* ORCC Auto-Mapping Bench"
    print "*********************************************************************"
    print "==> Decoder : %s" % (DEFAULT_EXE)
    print "==> Sequences : %s" % (SEQ_PATH)
    print "==> nb of frames : %d " % (NBFRAME)
    print "==> nb of procs : %d " % (NBPROC)
    if RANGE:
        minRange = 2
    else:
        minRange = NBPROC

    performMapping(1)
    for nbP in range(minRange,NBPROC+1):
        performMapping(nbP)
        extractFPS(nbP)
        logInCSV(nbP)
#         logInTXT(nbP)
#         logInXML(nbP)
        del BENCH_DATA[:]

    mergeAllCSV()
    archiveLogs()

def performMappingWithStrategy(fic, nbProcs, Strategy):
    SEQUENCE_NAME = fic[0:len(fic)-len(DEFAULT_LOG_EXT)]
    baseName = FILE_HEAD + SEQUENCE_NAME + "_" + str(nbProcs) + "Procs"
    
    if nbProcs == 1:
        log_file = open(baseName + DEFAULT_LOG_EXT, 'w')
    else:
        print "      => Applying mapping strategy : %s" % (MS_LIST[Strategy])
        log_file = open(baseName + "_" + MS_LIST[Strategy]  + DEFAULT_LOG_EXT, 'w')
  
    proc = subprocess.call([DEFAULT_EXE, "-f", str(NBFRAME), "-n", "-v1", "-i", os.path.join(SEQ_PATH, fic), "-c", str(nbProcs), "-r100", "-s", str(Strategy)], stdout=log_file)


def performMapping(nbProcs):
    print "\n*********************************************************************"
    print "Perform mapping for " + str(nbProcs) + " processors for each sequence in " + SEQ_PATH
    for fic in os.listdir(SEQ_PATH):
        if fic.endswith(HEVC_SEQ_FILTER+HEVC_SEQ_EXT) or fic.endswith(MPEG4_SEQ_EXT):
            SEQUENCE_NAME = fic[0:len(fic)-len(HEVC_SEQ_EXT)]

            print "  * Processing on sequence : %s" % (SEQUENCE_NAME)
            
            if nbProcs == 1:
                performMappingWithStrategy(fic, nbProcs, 0)

            else:
                for strategy in range(0, len(MS_LIST)):
                    performMappingWithStrategy(fic, nbProcs, strategy)
            
def getFPS(fic):
    fps = 0
    fp = open(fic)
    for line in fp:
        if line.count(TOKEN_FPS) == 1:
            fps = (line.split()[5])
    fp = fp.close()
    return round(float(fps), 2)

def getFPSPostMapping(fic):
    fps = 0
    fp = open(fic)
    for line in fp:
        if line.count(TOKEN_FPS_POST_MAPPING) == 1:
            fps = (line.split()[7])
    fp = fp.close()
    return round(float(fps), 2)

def getLB(fic):
    val = 0
    fp = open(fic)
    for line in fp:
        if line.count(TOKEN_LB) == 1:
            val = (line.split()[2])
    fp = fp.close()
    return round(float(val), 2)

def getEC(fic):
    val = 0
    fp = open(fic)
    for line in fp:
        if line.count(TOKEN_EC) == 1:
            val = (line.split()[2])
    fp = fp.close()
    return val

def getCV(fic):
    val = 0
    fp = open(fic)
    for line in fp:
        if line.count(TOKEN_CV) == 1:
            val = (line.split()[6])
    fp = fp.close()
    return val

def getMT(fic):
    val = 0
    fp = open(fic)
    for line in fp:
        if line.count(TOKEN_MT) == 1:
            val = (line.split()[3])
    fp = fp.close()
    return val

def extractFPS(nbProcs):
    print "\n  * Extracting FPS from logs"
    for fic in os.listdir("."):
        if fic.count("_1Procs"+DEFAULT_LOG_EXT):
            SEQUENCE_NAME = fic[len(FILE_HEAD):len(fic)-len("_1Procs")-len(DEFAULT_LOG_EXT)]
            fps = getFPS(fic)
            data_seq = list()
            
            data_seq.append(SEQUENCE_NAME)
            data_seq.append(str(fps).replace(".", ","))
            
            baseName = SEQUENCE_NAME + "_" + str(nbProcs) + "Procs" + "_"
            for strategy in MS_LIST:
                log_file = "./" + FILE_HEAD + baseName + strategy + DEFAULT_LOG_EXT
                fps_str = getFPSPostMapping(log_file)
                data_seq.append(str(fps_str).replace(".", ","))
                data_seq.append(str(round(fps_str/fps, 2)).replace(".", ","))
                data_seq.append(str(getLB(log_file)).replace(".", ","))
                data_seq.append(str(getEC(log_file)))
                data_seq.append(str(getCV(log_file)))
                data_seq.append(str(getMT(log_file)))

            BENCH_DATA.append(data_seq)

    BENCH_DATA.sort()

def logInTXT(nbProcs):
    print "\n  * Generate TXT Result file... " + SUMMARY_TXT
    fd = open(SUMMARY_TXT, 'a')
    # Header
    fd.write("Summary results for " + OUTPUT_TAG + "\n")
    fd.write(".....Decoder : " + str(DEFAULT_EXE) + "\n")
    fd.write(".....Nb of frames = " + str(NBFRAME) + "\n")
    fd.write(".....Nb of processors = " + str(nbProcs) + "\n")
    fd.write("\n")

    # Body
    for bData in BENCH_DATA:
        fd.write('{:>3} {:55} FPS = {:>7} ROUND_ROBIN = {:>7} METIS_REC = {:>7} METIS_KWAY = {:>7}\n'.format("", bData[0], bData[1], bData[2], bData[4], bData[6]));

    # Footer
    fd.write("\n")
    fd.close()

def performMerge(fs1,fs2,fd):
    while 1:
        txt1 = fs1.readline().rstrip('\n\r')
        col1, sep, tmp = fs2.readline().partition(';')
        col1, sep, txt2 = tmp.partition(';')
        if txt1 == '':
            fd.write(txt2)
        else:
            fd.write(txt1+";"+txt2)
        if txt2 =='':
            break

def mergeFiles(file1,file2):
    destination = ".csvmerge_"+os.path.basename(file2)
    fs1 = open(file1, 'r')
    fs2 = open(file2, 'r')
    fd = open(destination, 'w')
    performMerge(fs1, fs2, fd)
    fs1.close()
    fs2.close()
    fd.close()
    return destination

def mergeAllCSV():
    print "\nMerging CSV Files"

    files = list()
    for fs in os.listdir("."):
        if fs.endswith("Procs.csv"):
            files.append(fs)

    files.sort()
    fileBase = files[0]
    del files[0]

    for fs in files:
        fileBase = mergeFiles(fileBase,fs)

    os.rename(fileBase, SUMMARY_CSV)
    for tmpfile in glob.glob('.csvmerge_*') :
        os.remove( tmpfile ) 
       

def logInCSV(nbProcs):
    print "\n  * Generate CSV Result file... " + OUTPUT_TAG + "_" + str(nbProcs) + "Procs" + ".csv"
    fd = open(OUTPUT_TAG + "_" + str(nbProcs) + "Procs" + ".csv", 'w')
    # Header
    fd.write("Nb of processors;;" + str(nbProcs) + ";;;;;\n")
    fd.write("Sequence;FPS")
    for strategy in MS_LIST:
        fd.write(";" + strategy + ";Acc")
        fd.write(";LB;EC;CV;MT")
    fd.write("\n")

    # Body
    for bData in BENCH_DATA:
        for iData in range(0, len(bData)):
            fd.write(bData[iData] + ";");
        fd.write("\n")
          
    # Footer
    fd.write("\n")
    fd.close()

def logInXML(nbProcs):
    print "\n  * Generate XML Result file... " + SUMMARY_XML
    fd = open(SUMMARY_XML, 'w')
    # Header
    fd.write("<report name=\"{REPORT_NAME}\" categ=\"{CATEGORY_NAME}\">\n")
    
    # Body

    # Footer
    fd.write("</report>\n")
    fd.close()

def archiveLogs():
    print "\nArchiving all in directory " + OUTPUT_TAG
    save = 0
    if os.path.exists(OUTPUT_TAG):
        backup = OUTPUT_TAG+"_"+str(save)
        while os.path.exists(backup):
            save += 1
            backup = OUTPUT_TAG+"_"+str(save)
        shutil.move(OUTPUT_TAG, backup)
        print "!! Making a backup of a directory with same name in " + backup
    os.mkdir(OUTPUT_TAG)
    os.system("mv " + FILE_HEAD+"*"+ DEFAULT_LOG_EXT + " " + OUTPUT_TAG)
    os.system("mv " + OUTPUT_TAG+"*"+ ".csv" + " " + OUTPUT_TAG)
#     os.system("mv " + OUTPUT_TAG+"*"+ ".txt" + " " + OUTPUT_TAG)
#     os.system("mv " + OUTPUT_TAG+"*"+ ".xml" + " " + OUTPUT_TAG)

# Main
# DEFAULT
HEVC_SEQ_FILTER = "_qp27"
HEVC_SEQ_EXT = ".bin"
MPEG4_SEQ_EXT = ".bit"
DEFAULT_LOG_EXT = ".log"
DEFAULT_OUTPUT_TAG = "bench"
DEFAULT_NBFRAME = 500
DEFAULT_NBPROC = 4
DEFAULT_RANGE = False
FILE_HEAD = "bench_"
TOKEN_FPS = "FPS"
TOKEN_FPS_POST_MAPPING = "PostMapping :"
TOKEN_LB = "Load balancing"
TOKEN_EC = "Edgecut :"
TOKEN_CV = "Communication volume :"
TOKEN_MT = "Mapping time :"
MS_LIST = ["MR", "MKV", "MKC", "RR"]
BENCH_DATA = list()

# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - benchAutoMapping - ??????')
parser.add_argument('-d', dest='decoder_path', help='Path to your orcc decoder binary', required=True)
parser.add_argument('-s', dest='sequences_path', help='Path to the directory containing the sequences', required=True)
parser.add_argument('-n', dest='nb_procs', type=int, default=DEFAULT_NBPROC, help='Number of processors for mapping (default value = 4)')
parser.add_argument('-f', dest='nb_frames', type=int, default=DEFAULT_NBFRAME, help='Number of frames of the sequence (default value = 500)')
parser.add_argument('-o', dest='output_tag', default=DEFAULT_OUTPUT_TAG, help='Allow to tag bench output with a name')
parser.add_argument('-r', dest='range', action="store_true", help='Make mapping for all numbers of processors setted')
args = parser.parse_args()

SUMMARY_TXT=args.output_tag + ".txt"
SUMMARY_CSV=args.output_tag + ".csv"
SUMMARY_XML=args.output_tag + ".xml"
OUTPUT_TAG=args.output_tag
NBFRAME=args.nb_frames
DEFAULT_EXE=args.decoder_path
SEQ_PATH=args.sequences_path
NBPROC=args.nb_procs
RANGE=args.range or DEFAULT_RANGE

# Begin
performBench()