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

import os
import glob
import shutil
import sys
import subprocess

class OrccAnalyse:
    def __init__(self, tag):
        self.SUMMARY_TXT = tag + ".txt"
        self.SUMMARY_CSV = tag + ".csv"
        self.SUMMARY_XML = tag + ".xml"
        self.SUMMARY_HTML = tag + ".html"
        self.SUMMARY_PDF = tag + ".pdf"
        self.PDF_TOOL = "wkhtmltopdf"
        self.PDF_TOOL_INSTALLED = shutil.which(self.PDF_TOOL)
        self.OUTPUT_TAG = tag
        self.logCSV = True
        self.logTXT = True
        self.logXML = True
        self.logHTML = True
        self.logPDF = True
        self.ARCHIVE = True

    def start(self):
        self.performBench()
        self.extractData()
        self.computeData()
        self.printData()
        self.archiveLogs()

    def performBench(self):
        raise NotImplementedError

    def extractData(self):
        raise NotImplementedError

    def computeData(self):
        pass

    def logInTXT(self):
        pass

    def logInCSV(self):
        pass

    def logInXML(self):
        pass

    def logInHTML(self):
        pass

    def logInPDF(self):
        print ("\n  * Generate PDF Result file : " + self.SUMMARY_PDF)
        subprocess.call([self.PDF_TOOL, self.SUMMARY_HTML, self.SUMMARY_PDF])

    def printData(self):
        if self.logCSV:
            self.logInCSV()
        if self.logTXT:
            self.logInTXT()
        if self.logXML:
            self.logInXML()
        if self.logHTML:
            shutil.copy(os.path.join(os.path.join(os.path.dirname(sys.argv[0]), "common"), "style.css"), ".")
            self.logInHTML()
        if self.logPDF and self.logHTML and self.PDF_TOOL_INSTALLED:
            self.logInPDF()
        elif self.logPDF and not self.PDF_TOOL_INSTALLED:
             print ("    Warning: Cannot generate PDF file because " + self.PDF_TOOL + " is not installed on your computer.")           
        elif self.logPDF and not self.logHTML:
             print ("    Warning: Cannot generate PDF file if you do not generate HTML files.")           

    def archiveLogs(self):
        if self.ARCHIVE:
            print ("\n  * Archiving all in directory : " + self.OUTPUT_TAG)
            save = 0
            if os.path.exists(self.OUTPUT_TAG):
                backup = self.OUTPUT_TAG+"_"+str(save)
                while os.path.exists(backup):
                    save += 1
                    backup = self.OUTPUT_TAG+"_"+str(save)
                shutil.move(self.OUTPUT_TAG, backup)
                print ("    Warning: Making a backup of a directory with same name in " + backup)
            os.mkdir(self.OUTPUT_TAG)
            os.system("mv " + self.FILE_HEAD + "*" + self.DEFAULT_LOG_EXT + " " + self.OUTPUT_TAG)
            if self.logCSV:
                os.system("mv " + self.OUTPUT_TAG + "*.csv " + self.OUTPUT_TAG)
            if self.logTXT:
                os.system("mv " + self.OUTPUT_TAG + "*.txt " + self.OUTPUT_TAG)
            if self.logXML:
                os.system("mv " + self.OUTPUT_TAG + "*.xml " + self.OUTPUT_TAG)
            if self.logPDF:
                os.system("mv " + self.OUTPUT_TAG + "*.pdf " + self.OUTPUT_TAG)
            if self.logHTML:
                os.system("mv " + self.OUTPUT_TAG + "*.html " + self.OUTPUT_TAG)
                os.system("mv style.css " + self.OUTPUT_TAG)
        else:
            print ("\n  * Not archiving ")