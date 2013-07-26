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

from __future__ import division
import argparse
import os
import shutil
import sys
import subprocess

def performExtraction():
	print "*********************************************************************"
	print "* TTA logs Extraction"
	print "*********************************************************************"
	print "==> output= %s" % (OUTPUT_TAG)
	print "==> frequency= %d " % (FREQUENCY)
	print "==> nb of frames= %d " % (NBFRAME)
	extractData()
	logInTXT()
	logInCSV()
	logInHTML()
	archiveLogs()
	generatePDF()
	showSummary()

def extractData():
	global WORST_ACTOR
	global WORST_FPS	
	global TTA_DATA	
	print "\n*********************************************************************"
	TOKEN_CYCLE = "Cycle count = "
	TOKEN_ERROR = "Error"
	for fic in os.listdir("."):
		if fic[len(fic)-4:len(fic)] == ".log":
			FILE_HEAD = "processor_"
			ACTOR_NAME = fic[len(FILE_HEAD):len(fic)-4]
			STATUS = "OK"
			CYCLES = 0
			FPS = 0

			fp = open(fic)
			for line in fp:
				if STATUS == "OK":
					if line.count(TOKEN_ERROR) == 1:
						STATUS = "KO"
				if line.count(TOKEN_CYCLE) == 1:
					CYCLES = int(line.rsplit(None, 1)[1])
			fp = fp.close()

			# FPS @ 50MHz = Nb_frame / (CycleCount / 50 000 000)
			if CYCLES > 0:
				FPS = round(NBFRAME / (CYCLES / (FREQUENCY*1000000)), 2)

			if FPS < WORST_FPS and FPS != 0:
			  	WORST_ACTOR = ACTOR_NAME
			 	WORST_FPS = FPS

			TTA_DATA.append([ACTOR_NAME, str(CYCLES), str(FPS), STATUS])
			TTA_DATA.sort()

def logInTXT():
	print "\n*********************************************************************"
	print "Generate TXT Summary file..." + SUMMARY_TXT
	fd = open(SUMMARY_TXT, 'w')
	# Header
	fd.write("Summary results for " + OUTPUT_TAG + "\n")
	fd.write(".....Frequency    = " + str(FREQUENCY) + "\n")
	fd.write(".....Nb of frames = " + str(NBFRAME) + "\n")
	fd.write("\n")

	# Body
	for actor in TTA_DATA:
		if actor[3] == "KO":
			fd.write('{:>3} {:50} Cycle count = {:>10}    FPS = {:>7}\n'.format(actor[3], actor[0], actor[1], actor[2]));
		else:
			fd.write('{:>3} {:50} Cycle count = {:>10}    FPS = {:>7}\n'.format("", actor[0], actor[1], actor[2]));

	# Footer
	fd.write("\nWorst actor is : " + WORST_ACTOR + "    with : " + str(WORST_FPS) + " FPS\n")
	fd.close()

def logInCSV():
	print "\n*********************************************************************"
	print "Generate CSV Summary file..." + SUMMARY_CSV
	fd = open(SUMMARY_CSV, 'w')
	# Header
	fd.write("Actor;Cycle count;FPS@" + str(FREQUENCY) + "MHz;Status" + "\n")
	# TODO : Add output_tag information ?

	# Body
	for actor in TTA_DATA:
		fd.write(actor[0] + ";" + actor[1] + ";" + actor[2] + ";" + actor[3] + "\n");

	# Footer
	# TODO : Add worst actor ?
	fd.close()

def logInHTML():
	print "\n*********************************************************************"
	print "Generate HTML Summary file..." + SUMMARY_HTML
	fd = open(SUMMARY_HTML, 'w')
	# Header
	fd.write("<html lang=\"fr\">" + "\n")
	fd.write("  <head>" + "\n")
	fd.write("  <style type=\"text/css\">" + "\n")
	fd.write("  <!--" + "\n")
	fd.write("  @import url(\"style.css\");" + "\n")
	fd.write("  -->" + "\n")
	fd.write("  </style>" + "\n")
	fd.write("  </head>" + "\n")
	fd.write("	<body>" + "\n")
	fd.write("		<table id=\"rounded-corner\">" + "\n")
	fd.write("			<thead>" + "\n")
	fd.write("				<tr>" + "\n")
	fd.write("					<th scope=\"col\" class=\"rounded-head-left\"></th>" + "\n")
	fd.write("					<th scope=\"col\" colspan=3 class=\"rounded-head-right\">" + OUTPUT_TAG + "</th>" + "\n")
	fd.write("				</tr>" + "\n")
	fd.write("				<tr>" + "\n")
	fd.write("					<th scope=\"col\">Actor</th>" + "\n")
	fd.write("					<th scope=\"col\">Cycle count</th>" + "\n")
	fd.write("					<th scope=\"col\">FPS@" + str(FREQUENCY) + "MHz</th>" + "\n")
	fd.write("					<th scope=\"col\">Status</th>" + "\n")
	fd.write("				</tr>" + "\n")
	fd.write("			</thead>" + "\n")
	fd.write("			<tbody>" + "\n")

	# Body
	for actor in TTA_DATA:
		fd.write("				<tr>"+ "\n")
		fd.write("					<td>" + actor[0] + "</td>"+ "\n")
		fd.write("					<td class=\"num\">" + actor[1] + "</td>"+ "\n")
		fd.write("					<td class=\"num\">" + actor[2] + "</td>"+ "\n")
		if actor[3] ==  "KO":
			fd.write("					<td class=\"ko\">" + actor[3] + "</td>"+ "\n")
		else:
			fd.write("					<td class=\"ok\">" + actor[3] + "</td>"+ "\n")
		fd.write("				</tr>"+ "\n")

	# Footer
	fd.write("			</tbody>" + "\n")
	fd.write("			<tfoot>" + "\n")
	fd.write("				<tr>" + "\n")
	fd.write("					<td class=\"rounded-foot-left\">Worst actor :</td>" + "\n")
	fd.write("					<td colspan=\"3\" class=\"rounded-foot-right\">" + WORST_ACTOR + "</td>" + "\n")
	fd.write("				</tr>" + "\n")
	fd.write("			</tfoot>" + "\n")
	fd.write("		</table>" + "\n")
	fd.write("    </body>" + "\n")
	fd.write("</html>" + "\n")
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
	os.system("mv processor_*.log " + OUTPUT_TAG)
	shutil.move(SUMMARY_TXT, OUTPUT_TAG)
	shutil.move(SUMMARY_CSV, OUTPUT_TAG)
	shutil.move(SUMMARY_HTML, OUTPUT_TAG)
	shutil.copy(os.path.join(os.path.dirname(sys.argv[0]), "style.css"), OUTPUT_TAG)

def generatePDF():
	print "\n*********************************************************************"
	print "Generate PDF file..." + SUMMARY_PDF
	subprocess.call(["wkhtmltopdf", os.path.join(OUTPUT_TAG, SUMMARY_HTML), os.path.join(OUTPUT_TAG, SUMMARY_PDF)])

def showSummary():
	print "\n*********************************************************************"
	print ""
	os.system("cat " + os.path.join(OUTPUT_TAG, SUMMARY_TXT))

# Main
DEFAULT_OUTPUT_TAG="logs"
DEFAULT_FREQUENCY=50
DEFAULT_NBFRAME=10

# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - ttaextractlog - TTA Simulation logs extractor')
parser.add_argument('-n', dest='nb_frames', type=int, default=DEFAULT_NBFRAME, help='Number of frames of the sequence (default value = 10)')
parser.add_argument('-f', dest='frequency', type=int, default=DEFAULT_FREQUENCY, help='Frequency in MHz (default value = 50)')
parser.add_argument('-o', dest='output_tag', default=DEFAULT_OUTPUT_TAG, help='Allow to tag extraction with a name')
args = parser.parse_args()

OUTPUT_TAG = args.output_tag
FREQUENCY = args.frequency
NBFRAME = args.nb_frames

# Set variables
SUMMARY_TXT = OUTPUT_TAG + ".txt"
SUMMARY_CSV = OUTPUT_TAG + ".csv"
SUMMARY_HTML = OUTPUT_TAG + ".html"
SUMMARY_PDF = OUTPUT_TAG + ".pdf"
WORST_ACTOR = "None"
WORST_FPS = 10000
TTA_DATA = list()

# Begin
performExtraction()