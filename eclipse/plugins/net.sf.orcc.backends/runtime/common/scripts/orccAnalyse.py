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

    def addPercentBarScript(self, fic):
        fic.write("    <script language=\"javascript\"> " + "\n");
        fic.write("    function drawPercentBar(width, percent, color, background) " + "\n");
        fic.write("    { " + "\n");
        fic.write("      var pixels = width * (percent / 100); " + "\n");
        fic.write("      if (!background) { background = \"white\"; }" + "\n");
        fic.write("      if (percent < 30) { color = \"#99CC33\"; }" + "\n");
        fic.write("      else if (percent < 60) { color = \"#FFCC00\"; }" + "\n");
        fic.write("      else { color = \"#FF6666\"; }" + "\n");
        fic.write("\n");
        fic.write("      document.write(\"<div align=left style=\\\"position: relative; line-height: 1em; background-color: \" " + "\n");
        fic.write("\n");
        fic.write("                     + background + \"; border: 1px solid black; width: \" " + "\n");
        fic.write("                     + width + \"px\\\">\"); " + "\n");
        fic.write("      document.write(\"<div style=\\\"height: 1.5em; width: \" + pixels + \"px; background-color: \"" + "\n");
        fic.write("                     + color + \";\\\"></div>\"); " + "\n");
        fic.write("      document.write(\"<div style=\\\"color: #000000; font-weight: bold; position: absolute; text-align: center; padding-top: .25em; width: \" " + "\n");
        fic.write("                     + width + \"px; top: 0; left: 0\\\">\" + percent + \"%</div>\"); " + "\n");
        fic.write("\n");
        fic.write("      document.write(\"</div>\"); " + "\n");
        fic.write("    } " + "\n");
        fic.write("    </script>" + "\n");

    def addTreeViewStyles(self, fic):
        fic.write("  <style type=\"text/css\">" + "\n")
        fic.write("        .css-treeview ul," + "\n");
        fic.write("        .css-treeview li" + "\n");
        fic.write("        {" + "\n");
        fic.write("            padding: 0;" + "\n");
        fic.write("            margin: 0;" + "\n");
        fic.write("            list-style: none;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview input" + "\n");
        fic.write("        {" + "\n");
        fic.write("            position: absolute;" + "\n");
        fic.write("            opacity: 0;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview" + "\n");
        fic.write("        {" + "\n");
        fic.write("            font-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;" + "\n");
        fic.write("            font-size: 12px;" + "\n");
        fic.write("            -moz-user-select: none;" + "\n");
        fic.write("            -webkit-user-select: none;" + "\n");
        fic.write("            user-select: none;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview a" + "\n");
        fic.write("        {" + "\n");
        fic.write("            color: #00f;" + "\n");
        fic.write("            text-decoration: none;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview a:hover" + "\n");
        fic.write("        {" + "\n");
        fic.write("            text-decoration: underline;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview input + label + ul" + "\n");
        fic.write("        {" + "\n");
        fic.write("            margin: 0 0 0 22px;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview input ~ ul" + "\n");
        fic.write("        {" + "\n");
        fic.write("            display: none;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview label," + "\n");
        fic.write("        .css-treeview label::before" + "\n");
        fic.write("        {" + "\n");
        fic.write("            cursor: pointer;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview input:disabled + label" + "\n");
        fic.write("        {" + "\n");
        fic.write("            cursor: default;" + "\n");
        fic.write("            opacity: .6;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview input:checked:not(:disabled) ~ ul" + "\n");
        fic.write("        {" + "\n");
        fic.write("            display: block;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview label," + "\n");
        fic.write("        .css-treeview label::before" + "\n");
        fic.write("        {" + "\n");
        fic.write("            background: url(data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAACgCAYAAAAFOewUAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3gMNDygpRZkECQAAA6xJREFUaN7t1HtsU1UcB/DvOfe2tMu2Pjc6CnvghjJmpsBWHj7+kIAzgQTFqREdCUYRo6iE/2bWxfAP0c4YHVFjRlAjohOQ8Vo0bDPYMUfaYaFsK90DppUy7GNb293dHv+xxDs3R1AkMefzzz335vf75Tzu+RH8IRaL0SMtZ4UNFTap8UQHa233InAlCDWdwMJCK+4rv3vng8vvqcYkJPUcHLqyzen211FBiEdGk5pW1yAIIUjTqCDFY+jp8eKljasgiir9YxXLwqkCFABc5y+bjrV66qodjUs2rFlqyM3JhC5NDY1agEYtwmhIx7bNj/72yeFOaTgcCzHGtIoCJ77/ydbhGdzX8+0ut9PVG+v2B1xGvRYatQiBAmbdLJj1qmcOf/BKQ1ObFy0dvacU62g62cXCoUj1h/t/cL/xzkEGAHu/6WQ7HMfYltpG9tXRjjdTsQNDweZ7n6g/pZjBkuK5u3c3drYfartYWrV+xT6Xp++1yMiYb9F8c6j/1xEU35n3NQD09AdJnjVr9ZbHyy2KAjmzTVslaeJlTboBhXnZT3VfijhaXIGPq9aVbiovsSKekI4DwIL8LMYYoxXL899PFRABYM+hM7MDwfA6aTyKHbu+fKvLN4y78k3PEqou9vgC2836tKvXj42QJACHYgZUIEQQKIzplJ31XtyuohPsdKd7Yd1HB9tLCi0Oizlz77XoBAEAxhj9JRDcqSigoaPDDHh3LD5OrHOs0GVqSFGBNTl4VbLVH/AKAGDMEJmvL0AIIclmp79SUaDykfslrVp0F+TocMYzAEgJPL3WRkuK5m11efzJVHBhgYWdu9A/sHSRdUyxBwCweI6wR1Tpny8vnb/svYYD0NKH8WLVWudmQhgA+Px9L1zoC9YzIi+Yl5URU/zKXU3PkezcbCRlBmfwSbnbP4Tjzd+RS/290QKrSTKasunKFTb9HXmWNdaRhpPhaIQ+tOmLxPUZxMfiJHg5SPRmAyvTfSaWrHpVXrm4COOJRIZAGHIshnNDba/nsp9V2hCTNADiky8TTu/fmEEpTUvXZ8qj4Sj9vOn8yNsNPyYAyPgbNDWwVX4aTSaTcuRaqEyW5dUPlM0VZ0r+nyB/frHb7WymBLvdrsgRJwfU1NRMm1xbWzv9Kdysf1xAnHJjCPnLN8bYjReYLviWLOHf34Opjorj/eD29oOpbuV/3w9S45vqB4SQGXvDre0HN9KZeD/gOI7jOI7jOI7jOI7jOI7jOI7jOI67jX4HiPRzdvVq1OkAAAAASUVORK5CYII=) no-repeat;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview label," + "\n");
        fic.write("        .css-treeview a," + "\n");
        fic.write("        .css-treeview label::before" + "\n");
        fic.write("        {" + "\n");
        fic.write("            display: inline-block;" + "\n");
        fic.write("            height: 16px;" + "\n");
        fic.write("            line-height: 16px;" + "\n");
        fic.write("            vertical-align: middle;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview label" + "\n");
        fic.write("        {" + "\n");
        fic.write("            background-position: 18px 0;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview label::before" + "\n");
        fic.write("        {" + "\n");
        fic.write("            content: \"\";" + "\n");
        fic.write("            width: 16px;" + "\n");
        fic.write("            margin: 0 22px 0 0;" + "\n");
        fic.write("            vertical-align: middle;" + "\n");
        fic.write("            background-position: 0 -32px;" + "\n");
        fic.write("        }" + "\n");
        fic.write("\n");
        fic.write("        .css-treeview input:checked + label::before" + "\n");
        fic.write("        {" + "\n");
        fic.write("            background-position: 0 -16px;" + "\n");
        fic.write("        }" + "\n");
        fic.write("  </style>" + "\n")

    def addActionsTabStyles(self, fic):
        fic.write("  <style type=\"text/css\">" + "\n")
        fic.write("        #actions-tab" + "\n");
        fic.write("        {" + "\n");
        fic.write("            font-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;" + "\n");
        fic.write("            font-size: 12px;" + "\n");
        fic.write("            margin: 10px;" + "\n");
        fic.write("            width: 450px;" + "\n");
        fic.write("            text-align: left;" + "\n");
        fic.write("            border-collapse: collapse;" + "\n");
        fic.write("        }" + "\n");
        fic.write("        #actions-tab th" + "\n");
        fic.write("        {" + "\n");
        fic.write("            padding: 8px;" + "\n");
        fic.write("            font-weight: normal;" + "\n");
        fic.write("            font-size: 13px;" + "\n");
        fic.write("            color: #039;" + "\n");
        fic.write("            background: #b9c9fe;" + "\n");
        fic.write("            text-align: center;" + "\n");
        fic.write("            white-space:nowrap;" + "\n");
        fic.write("            font-weight : bold;" + "\n");
        fic.write("        }" + "\n");
        fic.write("        #actions-tab td" + "\n");
        fic.write("        {" + "\n");
        fic.write("            padding: 8px;" + "\n");
        fic.write("            background: #e8edff;" + "\n");
        fic.write("            border-top: 1px solid #fff;" + "\n");
        fic.write("            color: #669;" + "\n");
        fic.write("        }" + "\n");
        fic.write("        #actions-tab td.num" + "\n");
        fic.write("        {" + "\n");
        fic.write("            padding: 8px;" + "\n");
        fic.write("            background: #e8edff;" + "\n");
        fic.write("            border-top: 1px solid #fff;" + "\n");
        fic.write("            color: #669;" + "\n");
        fic.write("            text-align: right;" + "\n");
        fic.write("            vertical-align:top;" + "\n");
        fic.write("        }" + "\n");
        fic.write("        #actions-tab tbody tr:hover td" + "\n");
        fic.write("        {" + "\n");
        fic.write("            background: #d0dafd;" + "\n");
        fic.write("            color: #000000;" + "\n");
        fic.write("        }" + "\n");
        fic.write("  </style>" + "\n")

    def addRoundedTabStyles(self, fic):
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
        fic.write("          width: 650px;"  + "\n")
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
        fic.write("          vertical-align:top;"  + "\n")
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
        fic.write("  </style>" + "\n")

    def addStylesInHTML(self, fic):
        self.addRoundedTabStyles(fic)

    def addScriptsInHTML(self, fic):
        pass

    def addPieInHTML(self, fic):
        pass

    def addContentInHTMLBody(self, fic):
        pass

    def addHeaderInHTML(self, fic):
        fic.write("<html lang=\"fr\">" + "\n")
        fic.write("  <head>" + "\n")
        fic.write("  <script language=\"javascript\" src=\"http://www.google.com/jsapi\"></script>" + "\n")
        self.addScriptsInHTML(fic)
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
