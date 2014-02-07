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

from __future__ import division
from .orccAnalyse import OrccAnalyse
import os
import glob
import shutil
import sys
import subprocess

class SimulationData:
    def __init__(self, sequence):
        self.actor_name = sequence
        self.cycles = 0
        self.fps = 0.0
        self.workload = 0.0
        self.status = ""

class SimulationAnalyse(OrccAnalyse):
    def __init__(self, tag, frequency, nb_frames):
        OrccAnalyse.__init__(self, tag)
        self.logXML = False
        self.DEFAULT_LOG_EXT = ".log"
        self.FREQUENCY = frequency
        self.NBFRAME = nb_frames
        self.TOKEN_CYCLE = ""
        self.TOKEN_ERROR = ""
        self.WORST_ACTOR = "None"
        self.WORST_FPS = 10000
        self.TOTAL_CYCLES = 0
        self.PIE_DATA = list()
        self.MIN_WL_FOR_PIE = 5
        self.extractedData = list()

    def performBench(self):
        pass

    def calculFPS(self, nbCycles):
        raise NotImplementedError

    def getNbCycles(self, fic):
        raise NotImplementedError

    def getStatus(self, fic):
        status = "OK"
        fp = open(fic)
        for line in fp:
            if line.count(self.TOKEN_ERROR) == 1:
                status = "KO"
                break
        fp = fp.close()
        return status

    def extractData(self):
        datal = list()
        print ("\n  * Extracting data from logs")
        for fic in os.listdir("."):
            if fic[len(fic)-4:len(fic)] == self.DEFAULT_LOG_EXT:
                ACTOR_NAME = fic[len(self.FILE_HEAD):len(fic)-4]
                data = SimulationData(ACTOR_NAME)

                nbCycles = self.getNbCycles(fic)

                status = self.getStatus(fic)

                fps = self.calculFPS(nbCycles)

                if fps < self.WORST_FPS and fps != 0:
                    self.WORST_ACTOR = ACTOR_NAME
                    self.WORST_FPS = fps

                self.TOTAL_CYCLES += nbCycles
                data.cycles = nbCycles
                data.fps = fps
                data.status = status
                datal.append(data)

        print ("\n  * Computing data from logs")
        self.extractedData = sorted(datal, key=lambda actor: actor.actor_name)

    def computeData(self):
        wl = list()
        for actor in self.extractedData:
            rep = round((actor.cycles * 100) / self.TOTAL_CYCLES, 2)
            actor.workload = rep
            if rep > self.MIN_WL_FOR_PIE:
                wl.append(actor)

        self.PIE_DATA = sorted(wl, key=lambda actor: actor.workload, reverse=True)

    def logInTXT(self):
        print ("\n  * Generate TXT Result file : " + self.SUMMARY_TXT)
        fd = open(self.SUMMARY_TXT, 'w')
        # Header
        fd.write("Summary results for " + self.OUTPUT_TAG + "\n")
        fd.write(".....Frequency    = " + str(self.FREQUENCY) + "\n")
        fd.write(".....Nb of frames = " + str(self.NBFRAME) + "\n")
        fd.write("\n")

        # Body
        for actor in self.extractedData:
            if actor.status == "KO":
                fd.write('{:>3} {:60} Cycle count = {:>10}    FPS = {:>9}   Workload = {:>6}\n'.format(actor.status, actor.actor_name, str(actor.cycles), str(actor.fps), str(actor.workload)));
            else:
                fd.write('{:>3} {:60} Cycle count = {:>10}    FPS = {:>9}   Workload = {:>6}\n'.format("", actor.actor_name, str(actor.cycles), str(actor.fps), str(actor.workload)));

        # Footer
        fd.write("\nWorst actor is : " + self.WORST_ACTOR + "    with : " + str(self.WORST_FPS) + " FPS\n")
        fd.close()

    def logInCSV(self):
        print ("\n  * Generate CSV Result file : " + self.SUMMARY_CSV)
        fd = open(self.SUMMARY_CSV, 'w')
        # Header
        fd.write("Actor;Cycle count;FPS@" + str(self.FREQUENCY) + "MHz;Workload;Status" + "\n")
        # TODO : Add output_tag information ?

        # Body
        for actor in self.extractedData:
            fd.write(actor.actor_name + ";" + str(actor.cycles) + ";" + str(actor.fps).replace(".", ",") + ";" + str(actor.workload).replace(".", ",") + ";" + actor.status + "\n");

        # Footer
        # TODO : Add worst actor ?
        fd.close()

    def addPieInHTML(self, fd):
        fd.write("  <div id=\"chart\"></div>" + "\n")

        fd.write("  <script type=\"text/javascript\">" + "\n")
        fd.write("      var queryString = '';" + "\n")
        fd.write("      var dataUrl = '';" + "\n")

        fd.write("      function onLoadCallback() {" + "\n")
        fd.write("          if (dataUrl.length > 0) {" + "\n")
        fd.write("              var query = new google.visualization.Query(dataUrl);" + "\n")
        fd.write("              query.setQuery(queryString);" + "\n")
        fd.write("              query.send(handleQueryResponse);" + "\n")
        fd.write("          } else {" + "\n")
        fd.write("              var dataTable = new google.visualization.DataTable();" + "\n")
        fd.write("              dataTable.addRows(" + str(len(self.PIE_DATA) + 1) + ");" + "\n")

        fd.write("              dataTable.addColumn('number');" + "\n")
        i=0
        valuesTxt=""
        actorsTxt=""
        othersWl=100
        for actor in self.PIE_DATA:
            fd.write("              dataTable.setValue(" + str(i) + ", 0, " + str(actor.workload) + ");" + "\n")
            valuesTxt = valuesTxt + str(actor.workload) + ","
            actorsTxt = actorsTxt + str(actor.workload) + "%   " + actor.actor_name + "|"
            othersWl -= float(str(actor.workload))
            i += 1

        fd.write("              dataTable.setValue(" + str(i) + ", 0, " + str(othersWl) + ");" + "\n")
        fd.write("              draw(dataTable);" + "\n")
        fd.write("          }" + "\n")
        fd.write("      }" + "\n")

        fd.write("      function draw(dataTable) {" + "\n")
        fd.write("          var vis = new google.visualization.ImageChart(document.getElementById('chart'));" + "\n")
        fd.write("          var options = {" + "\n")
        fd.write("             chs: '900x400'," + "\n")
        fd.write("              cht: 'p3'," + "\n")
        fd.write("              chco: 'FF9900'," + "\n")
        fd.write("              chd: 't:" + valuesTxt + str(othersWl) + "'," + "\n")
        fd.write("              chdl: '" + actorsTxt + "Others'," + "\n")
        fd.write("              chl: '|||'" + "\n")
        fd.write("          };" + "\n")
        fd.write("          vis.draw(dataTable, options);" + "\n")
        fd.write("      }" + "\n")

        fd.write("      function handleQueryResponse(response) {" + "\n")
        fd.write("          if (response.isError()) {" + "\n")
        fd.write("              alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());" + "\n")
        fd.write("              return;" + "\n")
        fd.write("          }" + "\n")
        fd.write("          draw(response.getDataTable());" + "\n")
        fd.write("      }" + "\n")

        fd.write("      google.load(\"visualization\", \"1\", {packages:[\"imagechart\"]});" + "\n")
        fd.write("      google.setOnLoadCallback(onLoadCallback);" + "\n")

        fd.write("  </script>" + "\n")

    def logInHTML(self):
        print ("\n  * Generate HTML Result file : " + self.SUMMARY_HTML)
        fd = open(self.SUMMARY_HTML, 'w')
        # Header
        fd.write("<html lang=\"fr\">" + "\n")
        fd.write("  <head>" + "\n")
        fd.write("  <style type=\"text/css\">" + "\n")
        fd.write("  <!--" + "\n")
        fd.write("  @import url(\"style.css\");" + "\n")
        fd.write("  -->" + "\n")
        fd.write("  </style>" + "\n")
        fd.write("  <script language=\"javascript\" src=\"http://www.google.com/jsapi\"></script>" + "\n")
        fd.write("  </head>" + "\n")

        self.addPieInHTML(fd)

        fd.write("  <body>" + "\n")
        fd.write("      <table id=\"rounded-corner\">" + "\n")
        fd.write("          <thead>" + "\n")
        fd.write("              <tr>" + "\n")
        fd.write("                  <th scope=\"col\" class=\"rounded-head-left\"></th>" + "\n")
        fd.write("                  <th scope=\"col\" colspan=4 class=\"rounded-head-right\">" + self.OUTPUT_TAG + "</th>" + "\n")
        fd.write("              </tr>" + "\n")
        fd.write("              <tr>" + "\n")
        fd.write("                  <th scope=\"col\">Actor</th>" + "\n")
        fd.write("                  <th scope=\"col\">Cycle count</th>" + "\n")
        fd.write("                  <th scope=\"col\">FPS@" + str(self.FREQUENCY) + "MHz</th>" + "\n")
        fd.write("                  <th scope=\"col\">Workload</th>" + "\n")
        fd.write("                  <th scope=\"col\">Status</th>" + "\n")
        fd.write("              </tr>" + "\n")
        fd.write("          </thead>" + "\n")
        fd.write("          <tbody>" + "\n")

        # Body
        for actor in self.extractedData:
            fd.write("              <tr>"+ "\n")
            fd.write("                  <td>" + actor.actor_name + "</td>"+ "\n")
            fd.write("                  <td class=\"num\">" + str(actor.cycles) + "</td>"+ "\n")
            fd.write("                  <td class=\"num\">" + str(actor.fps) + "</td>"+ "\n")
            fd.write("                  <td class=\"num\">" + str(actor.workload) + "</td>"+ "\n")
            if actor.status ==  "KO":
                fd.write("                  <td class=\"ko\">" + actor.status + "</td>"+ "\n")
            else:
                fd.write("                  <td class=\"ok\">" + actor.status + "</td>"+ "\n")
            fd.write("              </tr>"+ "\n")

        # Footer
        fd.write("          </tbody>" + "\n")
        fd.write("          <tfoot>" + "\n")
        fd.write("              <tr>" + "\n")
        fd.write("                  <td class=\"rounded-foot-left\">Worst actor :</td>" + "\n")
        fd.write("                  <td colspan=\"4\" class=\"rounded-foot-right\">" + self.WORST_ACTOR + "</td>" + "\n")
        fd.write("              </tr>" + "\n")
        fd.write("          </tfoot>" + "\n")
        fd.write("      </table>" + "\n")
        fd.write("    </body>" + "\n")
        fd.write("</html>" + "\n")
        fd.close()