#!/usr/bin/env python3.3
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

from common.simulationAnalyse import SimulationAnalyse
import os
import sys
import argparse

class TTASimulationAnalyse(SimulationAnalyse):
    def __init__(self, tag, frequency, nb_frames, bArchive):
        SimulationAnalyse.__init__(self, tag, frequency, nb_frames)
        self.TOKEN_CYCLE = "Cycle count = "
        self.TOKEN_ERROR = "Error"
        self.FILE_HEAD = "processor_"
        self.ARCHIVE = bArchive

    def performBench(self):
        print ("*********************************************************************")
        print ("* TTA logs Extraction")
        print ("*********************************************************************")
        print ("==> output= %s" % (self.OUTPUT_TAG))
        print ("==> frequency= %d " % (self.FREQUENCY))
        print ("==> nb of frames= %d " % (self.NBFRAME))

    def calculFPS(self, nbCycles):
        fps = 0
        # FPS @ 50MHz = Nb_frame / (CycleCount / 50 000 000)
        if nbCycles > 0:
            fps = round(self.NBFRAME / (nbCycles / (self.FREQUENCY*1000000)), 2)
        return fps

    def getNbCycles(self, fic):
        nbCycles = 0
        fp = open(fic)
        for line in fp:
            if line.count(self.TOKEN_CYCLE) == 1:
                nbCycles = int(line.rsplit(None, 1)[1])
        fp = fp.close()
        return nbCycles


# Main
DEFAULT_OUTPUT_TAG="logs"
DEFAULT_FREQUENCY=1000
DEFAULT_NBFRAME=10
DEFAULT_ARCHIVE=False

# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - TTASimulationAnalyse - TTA Simulation analyse')
parser.add_argument('-n', dest='nb_frames', type=int, default=DEFAULT_NBFRAME, help='Number of frames of the sequence (default value = 10)')
parser.add_argument('-f', dest='frequency', type=int, default=DEFAULT_FREQUENCY, help='Frequency in MHz (default value = 1000)')
parser.add_argument('-o', dest='output_tag', default=DEFAULT_OUTPUT_TAG, help='Allow to tag extraction with a name')
parser.add_argument('-a', dest='archive', action="store_true", help='Archive logs and result files after post-treatment')
args = parser.parse_args()

# Begin
ttasiman = TTASimulationAnalyse(args.output_tag, args.frequency, args.nb_frames, (args.archive or DEFAULT_ARCHIVE))
ttasiman.start()