#!/usr/bin/env python3.3
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

class VideoBenchData:
    def __init__(self, sequence):
        self.sequence = sequence
        self.fps = 0.0
        self.speedUp = 0.0
        self.loadBalancing = 0.0
        self.edgeCut = 0
        self.communicationVolume = 0
        self.mappingTime = 0
        self.nbPartitions = 0

class OrccBench:
    def __init__(self, tag):
        self.SUMMARY_TXT = tag + ".txt"
        self.SUMMARY_CSV = tag + ".csv"
        self.SUMMARY_XML = tag + ".xml"
        self.SUMMARY_HTML = tag + ".html"
        self.OUTPUT_TAG = tag
        self.logCSV = True
        self.logTXT = True
        self.logXML = True
        self.logHTML = True

    def start(self):
        self.performBench()
        self.extractData()
        self.printData()
        self.archiveLogs()

    def performBench(self):
        raise NotImplementedError

    def extractData(self):
        raise NotImplementedError

    def logInTXT(self):
        pass

    def logInCSV(self):
        pass

    def logInXML(self):
        pass

    def logInHTML(self):
        pass

    def printData(self):
        if self.logCSV:
            self.logInCSV()
        if self.logTXT:
            self.logInTXT()
        if self.logXML:
            self.logInXML()
        if self.logHTML:
            self.logInHTML()

    def archiveLogs(self):
        print ("\n*********************************************************************")
        print ("Archiving all in directory " + self.OUTPUT_TAG)
        save = 0
        if os.path.exists(self.OUTPUT_TAG):
            backup = self.OUTPUT_TAG+"_"+str(save)
            while os.path.exists(backup):
                save += 1
                backup = self.OUTPUT_TAG+"_"+str(save)
            shutil.move(self.OUTPUT_TAG, backup)
            print ("!! Making a backup of a directory with same name in " + backup)
        os.mkdir(self.OUTPUT_TAG)
        os.system("mv " + self.FILE_HEAD + "*" + self.DEFAULT_LOG_EXT + " " + self.OUTPUT_TAG)
        if self.logCSV:
            os.system("mv " + self.OUTPUT_TAG + "*.csv " + self.OUTPUT_TAG)
        if self.logTXT:
            os.system("mv " + self.OUTPUT_TAG + "*.txt " + self.OUTPUT_TAG)
        if self.logXML:
            os.system("mv " + self.OUTPUT_TAG + "*.xml " + self.OUTPUT_TAG)
        if self.logHTML:
            os.system("mv " + self.OUTPUT_TAG + "*.html " + self.OUTPUT_TAG)
            shutil.copy(os.path.join(os.path.dirname(sys.argv[0]), "style.css"), self.OUTPUT_TAG)


class BenchAutoMapping(OrccBench):
    def __init__(self, nb_procs, tag, nb_frames, decoder_path, sequences_path, bRange):
        OrccBench.__init__(self, tag)
        self.NBFRAME = nb_frames
        self.DEFAULT_EXE = decoder_path
        self.SEQ_PATH =  sequences_path
        self.NBPROC = nb_procs
        self.RANGE = bRange
        self.FILE_HEAD = "bench_"
        self.TOKEN_FPS = "FPS"
        self.TOKEN_FPS_POST_MAPPING = "PostMapping :"
        self.TOKEN_LB = "Load balancing"
        self.TOKEN_EC = "Edgecut :"
        self.TOKEN_CV = "Communication volume :"
        self.TOKEN_MT = "Mapping time :"
        self.TOKEN_PART = "partitions"
        self.DEFAULT_LOG_EXT = ".log"
        self.SEQ_EXT = ["_qp27.bin", ".bit", ".m4v"]
        self.MS_LIST = ["MR", "MKV", "MKC", "RR", "QM", "WLB", "COWLB", "KRWLB"]
        self.logTXT = False
        self.logXML = False
        self.logHTML = False
        self.benchData = {}
        self.rangeProcs = list()

        self.rangeProcs.append(1)
        if self.RANGE:
            minRange = 2
        else:
            minRange = self.NBPROC

        for nbP in range(minRange, self.NBPROC+1):
            self.rangeProcs.append(nbP)

    def performBench(self):
        print ("*********************************************************************")
        print ("* ORCC Auto-Mapping Bench")
        print ("*********************************************************************")
        print ("==> Decoder : %s" % (self.DEFAULT_EXE))
        print ("==> Sequences : %s" % (self.SEQ_PATH))
        print ("==> nb of frames : %d " % (self.NBFRAME))
        print ("==> nb of procs : %d " % (self.NBPROC))

        for nbP in self.rangeProcs:
            self.performMapping(nbP)

    def performMappingWithStrategy(self, fic, nbProcs, Strategy):
        SEQUENCE_NAME = fic[0:len(fic)-len(self.DEFAULT_LOG_EXT)]
        baseName = self.FILE_HEAD + SEQUENCE_NAME + "_" + str(nbProcs) + "Procs"
        
        if nbProcs == 1:
            log_file = open(baseName + self.DEFAULT_LOG_EXT, 'w')
        else:
            print ("      => Applying mapping strategy : %s" % (self.MS_LIST[Strategy]))
            log_file = open(baseName + "_" + self.MS_LIST[Strategy]  + self.DEFAULT_LOG_EXT, 'w')

        try:
            proc = subprocess.call([self.DEFAULT_EXE, "-f", str(self.NBFRAME), "-n", "-v1", "-i", os.path.join(self.SEQ_PATH, fic), "-c", str(nbProcs), "-r100", "-s", str(Strategy)], stdout=log_file, timeout=600)
        except subprocess.TimeoutExpired:
            print("    => Timeout expired !")


    def performMapping(self, nbProcs):
        print ("\n*********************************************************************")
        print ("Perform mapping for " + str(nbProcs) + " processors for each sequence in " + self.SEQ_PATH)
        for fic in os.listdir(self.SEQ_PATH):
            if fic.endswith(self.SEQ_EXT[0]) or fic.endswith(self.SEQ_EXT[1]) or fic.endswith(self.SEQ_EXT[2]):
                SEQUENCE_NAME = fic[0:len(fic)-4]

                print ("  * Processing on sequence : %s" % (SEQUENCE_NAME))
                
                if nbProcs == 1:
                    self.performMappingWithStrategy(fic, nbProcs, 0)

                else:
                    for strategy in range(0, len(self.MS_LIST)):
                        self.performMappingWithStrategy(fic, nbProcs, strategy)
                
    def extractData(self):
        print ("\n  * Extracting data from logs")
        for fic in os.listdir("."):
            if fic.count("_1Procs"+self.DEFAULT_LOG_EXT):
                SEQUENCE_NAME = fic[len(self.FILE_HEAD):len(fic)-len("_1Procs")-len(self.DEFAULT_LOG_EXT)]
                data = {}

                for nbP in self.rangeProcs:
                    baseName = SEQUENCE_NAME + "_" + str(nbP) + "Procs" + "_"
                    data[nbP] = {}

                    if nbP == 1:
                        data[nbP] = VideoBenchData(SEQUENCE_NAME)
                        fp = open(fic)
                        for line in fp:
                            if line.count(self.TOKEN_FPS) == 1:
                                data[nbP].fps = round(float(line.split()[5]), 2)
                        fp = fp.close()
                    else:
                        for strategy in self.MS_LIST:
                            log_file = "./" + self.FILE_HEAD + baseName + strategy + self.DEFAULT_LOG_EXT
                            
                            data[nbP][strategy] = VideoBenchData(SEQUENCE_NAME)

                            fp = open(log_file)
                            for line in fp:
                                if line.count(self.TOKEN_FPS_POST_MAPPING) == 1:
                                    data[nbP][strategy].fps = round(float(line.split()[7]), 2)

                                if line.count(self.TOKEN_LB) == 1 and line.count(self.TOKEN_PART) == 1:
                                    data[nbP][strategy].loadBalancing = round(float(line.split()[2]), 2)
                                    data[nbP][strategy].nbPartitions = int(line.split()[4])

                                if line.count(self.TOKEN_EC) == 1:
                                    data[nbP][strategy].edgeCut = int(line.split()[2])

                                if line.count(self.TOKEN_CV) == 1:
                                    data[nbP][strategy].communicationVolume = int(line.split()[6])

                                if line.count(self.TOKEN_MT) == 1:
                                    data[nbP][strategy].mappingTime = int(line.split()[3])
                            fp = fp.close()
                            data[nbP][strategy].speedUp = round(data[nbP][strategy].fps/data[1].fps, 2)

                    self.benchData[SEQUENCE_NAME] = data

    def logInCSV(self):
        print ("\n  * Generate CSV Result file... " + self.SUMMARY_CSV)
        fd = open(self.SUMMARY_CSV, 'w')

        for bData in self.benchData:
            # Header
            fd.write("Sequence;\n")
            fd.write(bData + ";\n")
            
            for nbProcs in self.rangeProcs:
                if nbProcs != 1:
                    fd.write(";Nb procs;" + str(nbProcs) + ";;;;;;;")
            fd.write("\n")

            for nbProcs in self.rangeProcs:
                if nbProcs != 1:
                    fd.write(";Strategy;FPS;Acc;LB;EC;CV;MT;Parts;")
            fd.write("\n")

            for nbProcs in self.rangeProcs:
                if nbProcs != 1:
                    fd.write(";Ref;")                        
                    fd.write(str(self.benchData[bData][1].fps).replace(".", ",") + ";;;;;;;")
            fd.write("\n")

            # Body
            for strategy in self.MS_LIST:
                for nbProcs in self.rangeProcs:
                    if nbProcs != 1:
                        data = self.benchData[bData][nbProcs][strategy]
                        fd.write(";" + strategy + ";")
                        fd.write(str(data.fps).replace(".", ",") + ";")
                        fd.write(str(data.speedUp).replace(".", ",") + ";")
                        fd.write(str(data.loadBalancing).replace(".", ",") + ";")
                        fd.write(str(data.edgeCut) + ";")
                        fd.write(str(data.communicationVolume) + ";")
                        fd.write(str(data.mappingTime) + ";")
                        fd.write(str(data.nbPartitions) + ";")
                fd.write("\n")

            # Footer
            fd.write("\n\n")

        fd.close()

# Main
# DEFAULT
DEFAULT_OUTPUT_TAG = "bench"
DEFAULT_NBFRAME = 1000
DEFAULT_NBPROC = 4
DEFAULT_RANGE = False

# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - benchAutoMapping - ??????')
parser.add_argument('-d', dest='decoder_path', help='Path to your orcc decoder binary', required=True)
parser.add_argument('-s', dest='sequences_path', help='Path to the directory containing the sequences', required=True)
parser.add_argument('-n', dest='nb_procs', type=int, default=DEFAULT_NBPROC, help='Number of processors for mapping (default value = 4)')
parser.add_argument('-f', dest='nb_frames', type=int, default=DEFAULT_NBFRAME, help='Number of frames of the sequence (default value = 1000)')
parser.add_argument('-o', dest='output_tag', default=DEFAULT_OUTPUT_TAG, help='Allow to tag bench output with a name')
parser.add_argument('-r', dest='range', action="store_true", help='Make mapping for all numbers of processors setted')
args = parser.parse_args()

# Begin
bench = BenchAutoMapping(args.nb_procs, args.output_tag, args.nb_frames, args.decoder_path, args.sequences_path, (args.range or DEFAULT_RANGE))
bench.start()