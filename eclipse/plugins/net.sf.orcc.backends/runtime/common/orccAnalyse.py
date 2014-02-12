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
        self.SRC_DIR = "."
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
        print ("\n  * Generate HTML Result file : " + os.path.join(self.SRC_DIR, self.SUMMARY_HTML))
        fic = open(os.path.join(self.SRC_DIR, self.SUMMARY_HTML), 'w')
        self.addHeaderInHTML(fic)
        self.addBodyInHTML(fic)
        self.addFooterInHTML(fic)
        fic.close()

    def logInPDF(self):
        print ("\n  * Generate PDF Result file : " + os.path.join(self.SRC_DIR, self.SUMMARY_PDF))
        subprocess.call([self.PDF_TOOL, os.path.join(self.SRC_DIR, self.SUMMARY_HTML), os.path.join(self.SRC_DIR, self.SUMMARY_PDF)])

    def addStylesInHTML(self, fic):
        fic.write("  <style type=\"text/css\">" + "\n")
        fic.write("      body"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          line-height: 1.6em;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          font-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;"  + "\n")
        fic.write("          font-size: 12px;"  + "\n")
        fic.write("          margin: 45px;"  + "\n")
        fic.write("          width: 480px;"  + "\n")
        fic.write("          text-align: left;"  + "\n")
        fic.write("          border-collapse: collapse;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner thead th.rounded-head-left"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          background: #b9c9fe;"  + "\n")
        fic.write("          border-radius: 20p;"  + "\n")
        fic.write("          -moz-border-radius-topleft: 10px;"  + "\n")
        fic.write("          border-top-left-radius: 10px;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner thead th.rounded-head-right"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          background: #b9c9fe;"  + "\n")
        fic.write("          -moz-border-radius-topright: 10px;"  + "\n")
        fic.write("          border-top-right-radius: 10px;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner th"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          padding: 8px;"  + "\n")
        fic.write("          font-weight: normal;"  + "\n")
        fic.write("          font-size: 13px;"  + "\n")
        fic.write("          color: #039;"  + "\n")
        fic.write("          background: #b9c9fe;"  + "\n")
        fic.write("          text-align: center;"  + "\n")
        fic.write("          white-space:nowrap;"  + "\n")
        fic.write("          font-weight : bold;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner td"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          padding: 8px;"  + "\n")
        fic.write("          background: #e8edff;"  + "\n")
        fic.write("          border-top: 1px solid #fff;"  + "\n")
        fic.write("          color: #669;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner td.num"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          padding: 8px;"  + "\n")
        fic.write("          background: #e8edff;"  + "\n")
        fic.write("          border-top: 1px solid #fff;"  + "\n")
        fic.write("          color: #669;"  + "\n")
        fic.write("          text-align: right;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner td.ok"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          padding: 8px;"  + "\n")
        fic.write("          background: #4CC417;"  + "\n")
        fic.write("          border-top: 1px solid #fff;"  + "\n")
        fic.write("          font-weight : bold;"  + "\n")
        fic.write("          color: #FFFFFF;"  + "\n")
        fic.write("          text-align: center;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner td.ko"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          padding: 8px;"  + "\n")
        fic.write("          background: #FF0000;"  + "\n")
        fic.write("          border-top: 1px solid #fff;"  + "\n")
        fic.write("          font-weight : bold;"  + "\n")
        fic.write("          color: #FFFFFF;"  + "\n")
        fic.write("          text-align: center;"  + "\n")
        fic.write("      }"  + "\n")
        fic.write("      #rounded-corner tfoot td.rounded-foot-left"  + "\n")
        fic.write("      {"  + "\n")
        fic.write("          background: #b9c9fe;"  + "\n")
        fic.write("          -moz-border-radius-bottomleft: 10px;"  + "\n")
        fic.write("          border-bottom-left-radius: 10px;" + "\n")
        fic.write("          font-weight : bold;" + "\n")
        fic.write("      }" + "\n")
        fic.write("      #rounded-corner tfoot td.rounded-foot-right" + "\n")
        fic.write("      {" + "\n")
        fic.write("          background: #b9c9fe;" + "\n")
        fic.write("          -moz-border-radius-bottomright: 10px;" + "\n")
        fic.write("          border-bottom-right-radius: 10px;" + "\n")
        fic.write("          font-weight : bold;" + "\n")
        fic.write("          text-align: right;  " + "\n")
        fic.write("      }" + "\n")
        fic.write("      #rounded-corner tbody tr:hover td" + "\n")
        fic.write("      {" + "\n")
        fic.write("          background: #d0dafd;" + "\n")
        fic.write("          color: #000000;" + "\n")
        fic.write("      }" + "\n")
        fic.write("  </style>" + "\n")

    def addPieInHTML(self, fic):
        pass

    def addContentInHTMLBody(self, fic):
        pass

    def addHeaderInHTML(self, fic):
        fic.write("<html lang=\"fr\">" + "\n")
        fic.write("  <head>" + "\n")
        fic.write("  <script language=\"javascript\" src=\"http://www.google.com/jsapi\"></script>" + "\n")
        self.addStylesInHTML(fic)
        fic.write("  </head>" + "\n")

    def addBodyInHTML(self, fic):
        fic.write("  <body>" + "\n")
        self.addPieInHTML(fic)
        self.addContentInHTMLBody(fic)
        fic.write("  </body>" + "\n")

    def addFooterInHTML(self, fic):
        fic.write("</html>" + "\n")
        fic.close()

    def printData(self):
        if self.logCSV:
            self.logInCSV()
        if self.logTXT:
            self.logInTXT()
        if self.logXML:
            self.logInXML()
        if self.logHTML:
            self.logInHTML()
        if self.logPDF and self.logHTML and self.PDF_TOOL_INSTALLED:
            self.logInPDF()
        elif self.logPDF and not self.PDF_TOOL_INSTALLED:
             print ("    Warning: Cannot generate PDF file because " + self.PDF_TOOL + " is not installed on your computer.")
        elif self.logPDF and not self.logHTML:
             print ("    Warning: Cannot generate PDF file if you do not generate HTML files.")

    def archiveLogs(self):
        if self.ARCHIVE:
            targetPath = os.path.join(self.SRC_DIR, self.OUTPUT_TAG)
            print ("\n  * Archiving all in directory : " + targetPath)
            save = 0
            if os.path.exists(targetPath):
                backup = targetPath+"_"+str(save)
                while os.path.exists(backup):
                    save += 1
                    backup = targetPath+"_"+str(save)
                shutil.move(targetPath, backup)
                print ("    Warning: Making a backup of a directory with same name in " + backup)
            os.mkdir(targetPath)
            os.system("mv " + self.SRC_DIR + "/*" + self.DEFAULT_LOG_EXT + " " + targetPath)
            if self.logCSV:
                os.system("mv " + self.SRC_DIR + "/*.csv " + targetPath)
            if self.logTXT:
                os.system("mv " + self.SRC_DIR + "/*.txt " + targetPath)
            if self.logXML:
                os.system("mv " + self.SRC_DIR + "/*.xml " + targetPath)
            if self.logPDF:
                os.system("mv " + self.SRC_DIR + "/*.pdf " + targetPath)
            if self.logHTML:
                os.system("mv " + self.SRC_DIR + "/*.html " + targetPath)
        else:
            print ("\n  * Not archiving ")
