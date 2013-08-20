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

class ExtractLog:
    def __init__(self, tag, frequency, nb_frames):
        self.OUTPUT_TAG = tag
        self.SUMMARY_TXT = tag + ".txt"
        self.SUMMARY_CSV = tag + ".csv"
        self.SUMMARY_HTML = tag + ".html"
        self.SUMMARY_PDF = tag + ".pdf"
        self.WORST_ACTOR = "None"
        self.WORST_FPS = 10000
        self.TTA_DATA = list()
        self.FREQUENCY = frequency
        self.NBFRAME = nb_frames
        self.TOKEN_CYCLE = ""
        self.TOKEN_ERROR = ""

    def performExtraction(self):
        print "*********************************************************************"
        print "* TTA logs Extraction"
        print "*********************************************************************"
        print "==> output= %s" % (self.OUTPUT_TAG)
        print "==> frequency= %d " % (self.FREQUENCY)
        print "==> nb of frames= %d " % (self.NBFRAME)
        self.extractData()
        self.logInTXT()
        self.logInCSV()
        self.logInHTML()
        self.archiveLogs()
        self.generatePDF()
        self.showSummary()

    def extractData(self):
        print "\n*********************************************************************"
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
                        if line.count(self.TOKEN_ERROR) == 1:
                            STATUS = "KO"
                    if line.count(self.TOKEN_CYCLE) == 1:
                        CYCLES = int(line.rsplit(None, 1)[1])
                fp = fp.close()

                # FPS @ 50MHz = Nb_frame / (CycleCount / 50 000 000)
                if CYCLES > 0:
                    FPS = round(self.NBFRAME / (CYCLES / (self.FREQUENCY*1000000)), 2)

                if FPS < self.WORST_FPS and FPS != 0:
                    self.WORST_ACTOR = ACTOR_NAME
                    self.WORST_FPS = FPS

                self.TTA_DATA.append([ACTOR_NAME, str(CYCLES), str(FPS), STATUS])
                self.TTA_DATA.sort()

    def logInTXT(self):
        print "\n*********************************************************************"
        print "Generate TXT Summary file..." + self.SUMMARY_TXT
        fd = open(self.SUMMARY_TXT, 'w')
        # Header
        fd.write("Summary results for " + self.OUTPUT_TAG + "\n")
        fd.write(".....Frequency    = " + str(self.FREQUENCY) + "\n")
        fd.write(".....Nb of frames = " + str(self.NBFRAME) + "\n")
        fd.write("\n")

        # Body
        for actor in self.TTA_DATA:
            if actor[3] == "KO":
                fd.write('{:>3} {:50} Cycle count = {:>10}    FPS = {:>7}\n'.format(actor[3], actor[0], actor[1], actor[2]));
            else:
                fd.write('{:>3} {:50} Cycle count = {:>10}    FPS = {:>7}\n'.format("", actor[0], actor[1], actor[2]));

        # Footer
        fd.write("\nWorst actor is : " + self.WORST_ACTOR + "    with : " + str(self.WORST_FPS) + " FPS\n")
        fd.close()

    def logInCSV(self):
        print "\n*********************************************************************"
        print "Generate CSV Summary file..." + self.SUMMARY_CSV
        fd = open(self.SUMMARY_CSV, 'w')
        # Header
        fd.write("Actor;Cycle count;FPS@" + str(self.FREQUENCY) + "MHz;Status" + "\n")
        # TODO : Add output_tag information ?

        # Body
        for actor in self.TTA_DATA:
            fd.write(actor[0] + ";" + actor[1] + ";" + actor[2] + ";" + actor[3] + "\n");

        # Footer
        # TODO : Add worst actor ?
        fd.close()

    def logInHTML(self):
        print "\n*********************************************************************"
        print "Generate HTML Summary file..." + self.SUMMARY_HTML
        fd = open(self.SUMMARY_HTML, 'w')
        # Header
        fd.write("<html lang=\"fr\">" + "\n")
        fd.write("  <head>" + "\n")
        fd.write("  <style type=\"text/css\">" + "\n")
        fd.write("  <!--" + "\n")
        fd.write("  @import url(\"style.css\");" + "\n")
        fd.write("  -->" + "\n")
        fd.write("  </style>" + "\n")
        fd.write("  </head>" + "\n")
        fd.write("  <body>" + "\n")
        fd.write("      <table id=\"rounded-corner\">" + "\n")
        fd.write("          <thead>" + "\n")
        fd.write("              <tr>" + "\n")
        fd.write("                  <th scope=\"col\" class=\"rounded-head-left\"></th>" + "\n")
        fd.write("                  <th scope=\"col\" colspan=3 class=\"rounded-head-right\">" + self.OUTPUT_TAG + "</th>" + "\n")
        fd.write("              </tr>" + "\n")
        fd.write("              <tr>" + "\n")
        fd.write("                  <th scope=\"col\">Actor</th>" + "\n")
        fd.write("                  <th scope=\"col\">Cycle count</th>" + "\n")
        fd.write("                  <th scope=\"col\">FPS@" + str(self.FREQUENCY) + "MHz</th>" + "\n")
        fd.write("                  <th scope=\"col\">Status</th>" + "\n")
        fd.write("              </tr>" + "\n")
        fd.write("          </thead>" + "\n")
        fd.write("          <tbody>" + "\n")

        # Body
        for actor in self.TTA_DATA:
            fd.write("              <tr>"+ "\n")
            fd.write("                  <td>" + actor[0] + "</td>"+ "\n")
            fd.write("                  <td class=\"num\">" + actor[1] + "</td>"+ "\n")
            fd.write("                  <td class=\"num\">" + actor[2] + "</td>"+ "\n")
            if actor[3] ==  "KO":
                fd.write("                  <td class=\"ko\">" + actor[3] + "</td>"+ "\n")
            else:
                fd.write("                  <td class=\"ok\">" + actor[3] + "</td>"+ "\n")
            fd.write("              </tr>"+ "\n")

        # Footer
        fd.write("          </tbody>" + "\n")
        fd.write("          <tfoot>" + "\n")
        fd.write("              <tr>" + "\n")
        fd.write("                  <td class=\"rounded-foot-left\">Worst actor :</td>" + "\n")
        fd.write("                  <td colspan=\"3\" class=\"rounded-foot-right\">" + self.WORST_ACTOR + "</td>" + "\n")
        fd.write("              </tr>" + "\n")
        fd.write("          </tfoot>" + "\n")
        fd.write("      </table>" + "\n")
        fd.write("    </body>" + "\n")
        fd.write("</html>" + "\n")
        fd.close()

    def archiveLogs(self):
        print "\n*********************************************************************"
        print "Archiving all in directory " + self.OUTPUT_TAG
        save = 0
        if os.path.exists(self.OUTPUT_TAG):
            backup = self.OUTPUT_TAG+"_"+str(save)
            while os.path.exists(backup):
                save += 1
                backup = self.OUTPUT_TAG+"_"+str(save)
            shutil.move(self.OUTPUT_TAG, backup)
            print "!! Making a backup of a directory with same name in " + backup
        os.mkdir(self.OUTPUT_TAG)
        os.system("mv processor_*.log " + self.OUTPUT_TAG)
        shutil.move(self.SUMMARY_TXT, self.OUTPUT_TAG)
        shutil.move(self.SUMMARY_CSV, self.OUTPUT_TAG)
        shutil.move(self.SUMMARY_HTML, self.OUTPUT_TAG)
        shutil.copy(os.path.join(os.path.dirname(sys.argv[0]), "style.css"), self.OUTPUT_TAG)

    def generatePDF(self):
        print "\n*********************************************************************"
        print "Generate PDF file..." + self.SUMMARY_PDF
        subprocess.call(["wkhtmltopdf", os.path.join(self.OUTPUT_TAG, self.SUMMARY_HTML), os.path.join(self.OUTPUT_TAG, self.SUMMARY_PDF)])

    def showSummary(self):
        print "\n*********************************************************************"
        print ""
        os.system("cat " + os.path.join(self.OUTPUT_TAG, self.SUMMARY_TXT))

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