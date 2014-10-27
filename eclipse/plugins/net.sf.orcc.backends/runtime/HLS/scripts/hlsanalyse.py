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
#	 this list of conditions and the following disclaimer.
#   * Redistributions in binary form must reproduce the above copyright notice,
#	 this list of conditions and the following disclaimer in the documentation
#	 and/or other materials provided with the distribution.
#   * Neither the name of INSA Rennes nor the names of its
#	 contributors may be used to endorse or promote products derived from this
#	 software without specific prior written permission.
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
from orccAnalyse import OrccAnalyse
import os
import glob
import shutil
import sys
import subprocess
import argparse

class HlsSimulationData:
	def __init__(self, frame):
		self.frame = frame
		self.tokens = 0
		self.time = 0
		self.fps = 0.0
		self.elapsed = 0.0
		self.errors = 0

class HlsAnalyse(OrccAnalyse):
	def __init__(self, tag, frequency, input_file, height, width, port_nb):
		OrccAnalyse.__init__(self, tag)
		self.DEFAULT_LOG_EXT = ".log"
		self.FREQUENCY = frequency
		self.INPUT_FILE = input_file
		self.TOKEN_CYCLE = "Note: Number of output"
		self.TOKEN_ERROR = "Error: on port"
		self.TOKEN_PORTNB = "_"+str(port_nb)+"_"
		self.height = height
		self.width = width
		self.TOTAL_time = 0
		self.extractedData = list()
		self.ARCHIVE = False
		self.logCSV = False
		self.logTXT = False
		self.logXML = False
		self.logHTML = False
		self.logPDF = False

	def performBench(self):
		print ("*********************************************************************")
		print ("* HLS logs Extraction")
		print ("*********************************************************************")
		print ("==> output= %s" % (self.OUTPUT_TAG))
		print ("==> frequency= %d " % (self.FREQUENCY))

	def calculElapsed(self, frame1, frame2):
		frame2.elapsed = round((frame2.time-frame1.time)/1000000, 2)

	def calculFPS(self, frame):
		frame.fps = round(1000000000*frame.frame/frame.time, 2)

	def extractData(self):
		print ("\n  * Extracting data from logs")

		next_frame_tk = 1
		frame_nb = 0
		errors = 0

		fp = open(self.INPUT_FILE)
		for line in fp:
			if line.count(self.TOKEN_CYCLE) == 1 and line.count(self.TOKEN_PORTNB) == 1 and line.count(self.TOKEN_PORTNB + "read = " + str(next_frame_tk)) == 1:
				data = HlsSimulationData(frame_nb)
				self.extractedData.append(data)
				line = next(fp)
				self.extractedData[-1].time = int(line.split()[2])
				self.extractedData[-1].errors += errors
				self.extractedData[-1].tokens = next_frame_tk

				errors = 0
				frame_nb += 1
				next_frame_tk = self.height * self.width * frame_nb

			if line.count(self.TOKEN_ERROR) == 1 and line.count(self.TOKEN_PORTNB) == 1:
				errors += 1

		fp = fp.close()

	def computeData(self):
		print ("\n  * Computing data from logs")

		for i in range(0,len(self.extractedData)-1):
			self.calculFPS(self.extractedData[i+1])
			self.calculElapsed(self.extractedData[i], self.extractedData[i+1])
			print ("=> frame : " + str(self.extractedData[i+1].frame))
			print ("  => tokens : " + str(self.extractedData[i+1].tokens))
			print ("  => time : " + str(self.extractedData[i+1].time))
			print ("  => fps : " + str(self.extractedData[i+1].fps) + " FPS")
			print ("  => elapsed : " + str(self.extractedData[i+1].elapsed) + " ms")
			print ("  => errors : " + str(self.extractedData[i+1].errors))
			print ("")

# Main
DEFAULT_OUTPUT_TAG="logs"
DEFAULT_INPUT="designYLogFile"
DEFAULT_FREQUENCY=50
DEFAULT_H=240
DEFAULT_W=416
DEFAULT_PORTNB=55

# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - hlsextractlog - HLS Simulation logs extractor')
parser.add_argument('-i', dest='input_file', default=DEFAULT_INPUT, help='Input log file')
parser.add_argument('-f', dest='frequency', type=int, default=DEFAULT_FREQUENCY, help='Frequency in MHz (default value = 50)')
parser.add_argument('-e', dest='height', type=int, default=DEFAULT_H, help='Height of the sequence (default value = 240)')
parser.add_argument('-w', dest='width', type=int, default=DEFAULT_W, help='Width of the sequence (default value = 416)')
parser.add_argument('-p', dest='port_nb', type=int, default=DEFAULT_PORTNB, help='Width of the sequence (default value = 55)')
parser.add_argument('-o', dest='output_tag', default=DEFAULT_OUTPUT_TAG, help='Allow to tag extraction with a name')
args = parser.parse_args()

# Begin
hlsx = HlsAnalyse(args.output_tag, args.frequency, args.input_file, args.height, args.width, args.port_nb)
hlsx.start()
