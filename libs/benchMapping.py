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
    print "* ORCC Mapping Bench"
    print "*********************************************************************"
    print "==> Decoder : %s" % (DEFAULT_EXE)
    print "==> Sequences : %s" % (SEQ_PATH)
    print "==> nb of frames : %d " % (NBFRAME)
    print "==> nb of procs : %d " % (NBPROC)
    if RANGE:
        minRange = 2
    else:
        minRange = NBPROC

    generateProfilingFiles()
    for nbP in range(minRange,NBPROC+1):
        performMapping(nbP)
        extractFPS(nbP)
        logInTXT(nbP)
        logInCSV(nbP)
        # logInXML(nbP)
        del BENCH_DATA[:]

    mergeAllCSV()
    archiveLogs()

def generateProfilingFiles():
    print "\n*********************************************************************"
    print "Generate profiling file for each sequence in " + SEQ_PATH
    for fic in os.listdir(SEQ_PATH):
        if fic.endswith(HEVC_SEQ_FILTER+HEVC_SEQ_EXT) or fic.endswith(MPEG4_SEQ_EXT):
            SEQUENCE_NAME = fic[0:len(fic)-len(HEVC_SEQ_EXT)]

            print "  * Processing on sequence : %s" % (SEQUENCE_NAME)

            pxdfFile = FILE_HEAD + SEQUENCE_NAME + ".pxdf"
            log_file = open(FILE_HEAD + SEQUENCE_NAME + "_" + MS_NM  + DEFAULT_LOG_EXT, 'w')

            # Generate PXDF file
            # print "\tGenerate PXDF file : %s" % (pxdfFile)
            proc = subprocess.call([DEFAULT_EXE, "-f", str(NBFRAME), "-n", "-i", os.path.join(SEQ_PATH, fic), "-b", pxdfFile], stdout=log_file)


def performMappingWithStrategy(fic, nbProcs, Strategy):
    SEQUENCE_NAME = fic[0:len(fic)-len(DEFAULT_LOG_EXT)]
    pxdfFile = FILE_HEAD + SEQUENCE_NAME + ".pxdf"
    baseName = FILE_HEAD + SEQUENCE_NAME + "_" + str(nbProcs) + "Procs"
    xcfFile = baseName + "_" + Strategy + ".xcf"
    log_file = open(baseName + "_" + Strategy  + DEFAULT_LOG_EXT, 'w')
  
    # Generate XCF file with Mapping Strategy
    # print "\tGenerate XCF file : %s" % (xcfFile)
    proc = subprocess.call(["../bin/orccmap", "-n", str(nbProcs), "-v", "-i", pxdfFile, "-m", Strategy, "-o", xcfFile], stdout=log_file)
    # Log FPS with Mapping Strategy
    # print "\tRun sequence with mapping : %s" % (Strategy)
    proc = subprocess.call([DEFAULT_EXE, "-f", str(NBFRAME), "-n", "-i", os.path.join(SEQ_PATH, fic), "-m", xcfFile], stdout=log_file)


def performMapping(nbProcs):
    print "\n*********************************************************************"
    print "Perform mapping for " + str(nbProcs) + " processors for each sequence in " + SEQ_PATH
    for fic in os.listdir(SEQ_PATH):
        if fic.endswith(HEVC_SEQ_FILTER+HEVC_SEQ_EXT) or fic.endswith(MPEG4_SEQ_EXT):
            SEQUENCE_NAME = fic[0:len(fic)-len(HEVC_SEQ_EXT)]

            print "  * Processing on sequence : %s" % (SEQUENCE_NAME)

            performMappingWithStrategy(fic, nbProcs, MS_RR)
            performMappingWithStrategy(fic, nbProcs, MS_MR)
            performMappingWithStrategy(fic, nbProcs, MS_MK)


def getFPS(fic):
    fps = 0
    fp = open(fic)
    for line in fp:
        if line.count(TOKEN_FPS) == 1:
            fps = (line.split()[5])
    fp = fp.close()
    return round(float(fps), 2)

def extractFPS(nbProcs):
    fps = "NaN"
    fps_RR = "NaN"
    fps_MR = "NaN"
    fps_MK = "NaN"
    print "\n*********************************************************************"
    print "Extracting FPS from logs"
    for fic in os.listdir("."):
        if fic.count(MS_NM+DEFAULT_LOG_EXT):
            SEQUENCE_NAME = fic[len(FILE_HEAD):len(fic)-len(MS_NM)-len(DEFAULT_LOG_EXT)-1]
            print "  * Processing on sequence : %s" % (SEQUENCE_NAME)
            fps = getFPS(fic)
            
            baseName = SEQUENCE_NAME + "_" + str(nbProcs) + "Procs" + "_"
            for fic in os.listdir("."):
                if fic.count(baseName + MS_RR+ DEFAULT_LOG_EXT):
                    fps_RR = getFPS(fic)
                elif fic.count(baseName + MS_MR + DEFAULT_LOG_EXT):
                    fps_MR = getFPS(fic)
                elif fic.count(baseName + MS_MK + DEFAULT_LOG_EXT):
                    fps_MK = getFPS(fic)

            BENCH_DATA.append([SEQUENCE_NAME, str(fps), str(fps_RR), str(round(fps_RR/fps, 2)), str(fps_MR), str(round(fps_MR/fps, 2)), str(fps_MK), str(round(fps_MK/fps, 2))])

    BENCH_DATA.sort()

def logInTXT(nbProcs):
    print "\n*********************************************************************"
    print "Generate TXT Result file... " + SUMMARY_TXT
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
    print "*********************************************************************"
    print "Merging CSV Files"

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
    print "\n*********************************************************************"
    print "Generate CSV Result file... " + OUTPUT_TAG + "_" + str(nbProcs) + "Procs" + ".csv"
    fd = open(OUTPUT_TAG + "_" + str(nbProcs) + "Procs" + ".csv", 'w')
    # Header
    fd.write("Nb of processors;;" + str(nbProcs) + ";;;;;\n")
    fd.write("Sequence;FPS;Round Robin;Acc;Metis Recursive;Acc;Metis Kway;Acc\n")

    # Body
    for bData in BENCH_DATA:
        fd.write(bData[0] + ";" + bData[1].replace(".", ",") +  ";" + bData[2].replace(".", ",") + ";" + bData[3].replace(".", ",") + \
        ";" + bData[4].replace(".", ",") + ";" + bData[5].replace(".", ",") +  ";" + bData[6].replace(".", ",") + ";" + bData[7].replace(".", ",") + "\n");

    # Footer
    fd.write("\n")
    fd.close()

def logInXML(nbProcs):
    print "\n*********************************************************************"
    print "Generate XML Result file... " + SUMMARY_XML
    fd = open(SUMMARY_XML, 'w')
    # Header
    fd.write("<report name=\"{REPORT_NAME}\" categ=\"{CATEGORY_NAME}\">\n")
    
    # Body

    # Footer
    fd.write("</report>\n")
    fd.close()

def archiveLogs():
    print "\n*********************************************************************"
    print "Archiving all in directory " + OUTPUT_TAG
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
    os.system("mv " + FILE_HEAD+"*"+ ".pxdf" + " " + OUTPUT_TAG)
    os.system("mv " + FILE_HEAD+"*"+ ".xcf" + " " + OUTPUT_TAG)
    os.system("mv " + OUTPUT_TAG+"*"+ ".txt" + " " + OUTPUT_TAG)
    os.system("mv " + OUTPUT_TAG+"*"+ ".csv" + " " + OUTPUT_TAG)
    os.system("mv " + OUTPUT_TAG+"*"+ ".xml" + " " + OUTPUT_TAG)

# Main
# DEFAULT
HEVC_SEQ_FILTER = "_qp32"
HEVC_SEQ_EXT = ".bin"
MPEG4_SEQ_EXT = ".bit"
DEFAULT_LOG_EXT = ".log"
DEFAULT_OUTPUT_TAG = "bench"
DEFAULT_NBFRAME = 500
DEFAULT_NBPROC = 4
DEFAULT_RANGE = False
FILE_HEAD = "bench_"
TOKEN_FPS = "FPS"
MS_RR = "ROUND_ROBIN"
MS_MR = "METIS_REC"
MS_MK = "METIS_KWAY"
MS_NM = "NO_MAPPING"
BENCH_DATA = list()

# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - benchMapping - ??????')
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