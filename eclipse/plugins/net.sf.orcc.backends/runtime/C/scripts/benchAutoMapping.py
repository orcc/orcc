#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
# Copyright (c) 2014, INSA Rennes
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

from orccAnalyse import OrccAnalyse
import os
import glob
import shutil
import sys
import subprocess
import argparse
import time

# Constants
DEFAULT_OUTPUT_TAG = "bench"
DEFAULT_NBFRAME = 600
DEFAULT_TIMEOUT = 600
DEFAULT_RECONF = 100
DECODER_CONF = "HEVC"

# MPEG4 Conf 
MPEG4_SEQUENCE_EXT = "" # Not used with MPEG4 decoders
MPEG4_SEQUENCE_NAMES = ["foreman_qcif_30.bit", "old_town_cross_420_720p_MPEG4_SP_6Mbps.bit"]
MPEG4_SEQTYPE_LIST = ["mpeg4"]
MPEG4_QP_LIST = ["mpeg4"]

# HEVC Conf
HEVC_SEQUENCE_EXT = ".bin"
HEVC_SEQUENCE_NAMES = ["BasketballDrive_1920x1080_50_qp"]
HEVC_SEQTYPE_LIST = ["i_main", "ld_main", "ra_main"]
HEVC_QP_LIST = ["37", "32", "27", "22"]

# Mapping Conf
# MR   : METIS Recursive graph partition mapping
# MKCV : METIS KWay graph partition mapping (Optimize Communication volume)
# MKEC : METIS KWay graph partition mapping (Optimize Edge-cut)
# RR   : A simple Round-Robin mapping
# WLB  : Weighted Load Balancing
# KLR  : Kernighan Lin Refinement Weighted Load Balancing
PROCS_LIST = ["1", "2", "3", "4", "5", "6"]
STRATEGIES = ["MR", "MKCV", "MKEC", "RR", "WLB"]


class OrccMappingData:
    def __init__(self, nbPartitions=0, loadBalancing=0.0, edgeCut=0, communicationVolume=0, fps=0.0, mappingTime=0.0):
        self.nbPartitions = nbPartitions
        self.loadBalancing = loadBalancing
        self.edgeCut = edgeCut
        self.communicationVolume = communicationVolume
        self.fps = fps
        self.mappingTime = mappingTime
        self.speedUp = 0.0

class OrccSequenceData:
    def __init__(self):
        self.mappingData = {}

    def add(self, nbProcs, seqType, seqQp, strategy, mapData):
        self.mappingData[nbProcs, seqType, seqQp, strategy] = mapData

class OrccBenchMappingData:
    def __init__(self):
        self.sequences = {}

    def add(self, name):
        if name not in self.sequences:
            seq = OrccSequenceData()
            self.sequences[name] = seq

class BenchMapping(OrccAnalyse):
    def __init__(self, tag, nb_frames, decoder_path, sequences_path):
        OrccAnalyse.__init__(self, tag)
        self.NBFRAME = nb_frames
        self.DEFAULT_EXE = decoder_path
        self.SEQ_PATH =  sequences_path
        if self.SEQ_PATH.endswith("/"):
            self.SEQ_PATH = self.SEQ_PATH[0:len(self.SEQ_PATH)-1]
        self.FILE_HEAD = "bench_"
        self.DEFAULT_LOG_EXT = ".log"
        self.TOKEN_NEW_PROCESSING = "Processing on sequence "
        self.TOKEN_FPS = "FPS"
        self.TOKEN_FPS_POST_MAPPING = "PostMapping :"
        self.TOKEN_LB = "Load balancing"
        self.TOKEN_EC = "Edgecut :"
        self.TOKEN_CV = "Communication volume :"
        self.TOKEN_MT = "Mapping time :"
        self.TOKEN_PART = "partitions"
        self.logTXT = False
        self.logXML = False
        self.logHTML = False
        self.logCSV = True
        self.logPDF = False
        self.data = OrccBenchMappingData()

    def performBench(self):
        print ("*********************************************************************")
        print ("* ORCC Mapping Bench")
        print ("*********************************************************************")
        print ("==> Decoder : %s" % (self.DEFAULT_EXE))
        print ("==> Decoder configuration : %s" % (DECODER_CONF))
        print ("==> Sequences : %s" % (self.SEQ_PATH))
        print ("==> nb of frames : %d " % (self.NBFRAME))

        print ("\n*********************************************************************")
        for seqName in SEQUENCE_NAMES:
            for nbProcs in PROCS_LIST:
                print ("Perform bench for " + str(nbProcs) + " processors ")
                for seqType in SEQTYPE_LIST:
                    for seqQp in QP_LIST:
                        if nbProcs == "1":
                            self.printSequenceInfo(seqName, nbProcs, seqType, seqQp, "MR")
                            self.performDecoder(seqName, nbProcs, seqType, seqQp, "MR")
                        else:
                            for strategy in STRATEGIES:
                                self.printSequenceInfo(seqName, nbProcs, seqType, seqQp, strategy)
                                self.performDecoder(seqName, nbProcs, seqType, seqQp, strategy)

    def printSequenceInfo(self, seqName, nbProcs, seqType, seqQp, strategy):
        self.log_file = open(self.SUMMARY_TXT, 'a')
        self.log_file.write("Processing on sequence " + seqName + " -- " + seqType + " -- " + seqQp + " -- " + nbProcs + " processors" + " -- Strategy " + strategy +"\n")
        self.log_file.close()

    def performDecoder(self, seqName, nbProcs, seqType, seqQp, strategy):
        self.log_file = open(self.SUMMARY_TXT, 'a')
        if seqType == "mpeg4" or seqQp == "mpeg4":
            seqfile = self.SEQ_PATH + "/" + seqName
        else:
            seqfile = self.SEQ_PATH + "/" + seqType + "/" + seqName + seqQp + SEQUENCE_EXT

        print ("  * Processing on sequence : %s" % (seqfile))
        try:
            proc = subprocess.call([self.DEFAULT_EXE, "-v2", "-r", str(DEFAULT_RECONF), "-s", strategy, "-c", str(nbProcs), "-f", str(self.NBFRAME), "-n", "-i", seqfile], stdout=self.log_file, timeout=DEFAULT_TIMEOUT)
        except subprocess.TimeoutExpired:
            self.log_file.write("Timeout expired !\n")
            print("    => Timeout expired !")
        self.log_file.write("\n")
        self.log_file.close()

    def extractData(self):
        print ("\n  * Extracting data from logs")

        fp = open(self.SUMMARY_TXT)
        seqName = ""
        nbProcs = ""
        seqType = ""
        seqQp = ""
        strategy = ""

        for line in fp:
            if line.count(self.TOKEN_NEW_PROCESSING) == 1:
                seqName = line.split()[3]
                seqType = line.split()[5]
                seqQp = line.split()[7]
                nbProcs = line.split()[9]
                strategy = line.split()[13]
                nbPartitions = 0
                loadBalancing = 0.0
                edgeCut = 0
                communicationVolume = 0
                fps = 0.0
                mappingTime = 0.0
                self.data.add(seqName)
                print (seqName + nbProcs+seqType+seqQp+strategy)
                mappingData = OrccMappingData()
                self.data.sequences[seqName].add(nbProcs, seqType, seqQp, strategy, mappingData)

            if line.count(self.TOKEN_FPS_POST_MAPPING) == 1:
                fps = round(float(line.split()[7]), 2)
                mappingData = OrccMappingData(nbPartitions, loadBalancing, edgeCut, communicationVolume, fps, mappingTime)
                self.data.sequences[seqName].add(nbProcs, seqType, seqQp, strategy, mappingData)

            if line.count(self.TOKEN_LB) == 1 and line.count(self.TOKEN_PART) == 1:
                loadBalancing = round(float(line.split()[2]), 2)
                nbPartitions = int(line.split()[4])

            if line.count(self.TOKEN_EC) == 1 and line.count(self.TOKEN_CV) == 1:
                edgeCut = int(line.split()[2])

            if line.count(self.TOKEN_CV) == 1:
                communicationVolume = int(line.split()[6])

            if line.count(self.TOKEN_MT) == 1:
                mappingTime = int(line.split()[3])

        fp = fp.close()

    def computeData(self):
        print ("\n  * Computing data from logs")
        for seqName in SEQUENCE_NAMES:
            for seqType in SEQTYPE_LIST:
                for seqQp in QP_LIST:
                    refFPS = self.data.sequences[seqName].mappingData["1", seqType, seqQp, "MR"].fps
                    for nbProcs in PROCS_LIST:
                        if nbProcs != "1":
                            for strategy in STRATEGIES:
                                if refFPS != 0:
                                    data = self.data.sequences[seqName].mappingData[nbProcs, seqType, seqQp, strategy]
                                    data.speedUp = round(data.fps/refFPS, 2)

    def logInCSV(self):
        print ("\n  * Generate CSV Result file : " + self.SUMMARY_CSV)
        fd = open(self.SUMMARY_CSV, 'w')

        # Body
        for seqName in SEQUENCE_NAMES:
            seq = self.data.sequences[seqName]
            for seqType in SEQTYPE_LIST:
                for seqQp in QP_LIST:
                    # Header
                    fd.write("Sequence;\n")
                    fd.write(seqType + "/" + seqName + seqQp + ";\n")

                    for nbProcs in PROCS_LIST:
                        if nbProcs != "1":
                            fd.write(";Nb procs;" + nbProcs + ";;;;;;")
                    fd.write("\n")

                    for nbProcs in PROCS_LIST:
                        if nbProcs != "1":
                            fd.write(";Strategy;FPS;Acc;LB;EC;CV;Parts;")
                    fd.write("\n")

                    for nbProcs in PROCS_LIST:
                        if nbProcs != "1":
                            fd.write(";Ref;")
                            fd.write(str(seq.mappingData["1", seqType, seqQp, "MR"].fps).replace(".", ",") + ";;;;;;")
                    fd.write("\n")

                    # Body
                    for strategy in STRATEGIES:
                        for nbProcs in PROCS_LIST:
                            if nbProcs != "1":
                                data = seq.mappingData[nbProcs, seqType, seqQp, strategy]
                                fd.write(";" + strategy + ";")
                                fd.write(str(data.fps).replace(".", ",") + ";")
                                fd.write(str(data.speedUp).replace(".", ",") + ";")
                                fd.write(str(data.loadBalancing).replace(".", ",") + ";")
                                fd.write(str(data.edgeCut) + ";")
                                fd.write(str(data.communicationVolume) + ";")
                                fd.write(str(data.nbPartitions) + ";")
                        fd.write("\n")

                    # Footer
                    fd.write("\n\n")

        fd.close()

    def archiveLogs(self):
        pass

# Main
# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - BenchMapping - Massive dynamic mapping bench')
parser.add_argument('-d', dest='decoder_path', help='Path to your orcc decoder binary', required=True)
parser.add_argument('-s', dest='sequences_path', help='Path to the directory containing the sequences', required=True)
parser.add_argument('-f', dest='nb_frames', type=int, default=DEFAULT_NBFRAME, help='Number of frames of the sequence (default value = 600)')
parser.add_argument('-o', dest='output_tag', default=DEFAULT_OUTPUT_TAG, help='Allow to tag bench output with a name')
parser.add_argument('-m', dest='mpeg4', action="store_true", help='Set configuration to MPEG4 instead of HEVC')
args = parser.parse_args()

if args.mpeg4 == True:
    DECODER_CONF = "MPEG4"
    SEQUENCE_EXT = MPEG4_SEQUENCE_EXT
    SEQUENCE_NAMES = MPEG4_SEQUENCE_NAMES
    SEQTYPE_LIST = MPEG4_SEQTYPE_LIST
    QP_LIST = MPEG4_QP_LIST
else:
    SEQUENCE_EXT = HEVC_SEQUENCE_EXT
    SEQUENCE_NAMES = HEVC_SEQUENCE_NAMES
    SEQTYPE_LIST = HEVC_SEQTYPE_LIST
    QP_LIST = HEVC_QP_LIST

# Begin
bench = BenchMapping(args.output_tag, args.nb_frames, args.decoder_path, args.sequences_path)
bench.start()
