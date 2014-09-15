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
        if os.path.getsize(fic) == 0:
            status = "KO"
        else:
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
        for fic in os.listdir(self.SRC_DIR):
            if fic[len(fic)-4:len(fic)] == self.DEFAULT_LOG_EXT:
                ACTOR_NAME = fic[len(self.FILE_HEAD):len(fic)-4]
                data = SimulationData(ACTOR_NAME)

                nbCycles = self.getNbCycles(os.path.join(self.SRC_DIR, fic))

                status = self.getStatus(os.path.join(self.SRC_DIR, fic))

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
        print ("\n  * Generate TXT Result file : " + os.path.join(self.SRC_DIR, self.SUMMARY_TXT))
        fic = open(os.path.join(self.SRC_DIR, self.SUMMARY_TXT), 'w')
        # Header
        fic.write("Summary results for " + self.OUTPUT_TAG + "\n")
        fic.write(".....Frequency    = " + str(self.FREQUENCY) + "\n")
        fic.write(".....Nb of frames = " + str(self.NBFRAME) + "\n")
        fic.write("\n")

        # Body
        for actor in self.extractedData:
            if actor.status == "KO":
                fic.write('{:>3} {:60} Cycle count = {:>10}    FPS = {:>9}   Workload = {:>6}\n'.format(actor.status, actor.actor_name, str(actor.cycles), str(actor.fps), str(actor.workload)));
            else:
                fic.write('{:>3} {:60} Cycle count = {:>10}    FPS = {:>9}   Workload = {:>6}\n'.format("", actor.actor_name, str(actor.cycles), str(actor.fps), str(actor.workload)));

        # Footer
        fic.write("\nWorst actor is : " + self.WORST_ACTOR + "    with : " + str(self.WORST_FPS) + " FPS\n")
        fic.close()

    def logInXML(self):
        print ("\n  * Generate XML Result file : " + os.path.join(self.SRC_DIR, self.SUMMARY_XML))
        fic = open(os.path.join(self.SRC_DIR, self.SUMMARY_XML), 'w')

        fic.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
        fic.write("<report name=\"ActorsSimulationReport\" categ=\"OrccSimulationReports\">" + "\n");

        for actor in self.extractedData:
            if actor.status == "OK":
                passed = "yes"
            else:
                passed = "no"
            fic.write("    <test name=\"" + actor.actor_name + "\" executed=\"yes\">" + "\n");
            fic.write("        <result>" + "\n");
            fic.write("            <success passed=\"" + passed + "\" state=\"100\" hasTimedOut=\"false\" />" + "\n");
            fic.write("            <performance unit=\"FPS\" mesure=\"" + str(actor.fps) + "\" isRelevant=\"true\" />" + "\n");
            fic.write("        </result>" + "\n");
            fic.write("    </test>" + "\n");
        
        fic.write("</report>" + "\n");
        fic.close()

    def logInCSV(self):
        print ("\n  * Generate CSV Result file : " + os.path.join(self.SRC_DIR, self.SUMMARY_CSV))
        fic = open(os.path.join(self.SRC_DIR, self.SUMMARY_CSV), 'w')
        # Header
        fic.write("Actor;Cycle count;FPS@" + str(self.FREQUENCY) + "MHz;Workload;Status" + "\n")
        # TODO : Add output_tag information ?

        # Body
        for actor in self.extractedData:
            fic.write(actor.actor_name + ";" + str(actor.cycles) + ";" + str(actor.fps).replace(".", ",") + ";" + str(actor.workload).replace(".", ",") + ";" + actor.status + "\n");

        # Footer
        # TODO : Add worst actor ?
        fic.close()

    def addPieInHTML(self, fic):
        fic.write("  <div id=\"chart\"></div>" + "\n")

        fic.write("  <script type=\"text/javascript\">" + "\n")
        fic.write("      var queryString = '';" + "\n")
        fic.write("      var dataUrl = '';" + "\n")

        fic.write("      function onLoadCallback() {" + "\n")
        fic.write("          if (dataUrl.length > 0) {" + "\n")
        fic.write("              var query = new google.visualization.Query(dataUrl);" + "\n")
        fic.write("              query.setQuery(queryString);" + "\n")
        fic.write("              query.send(handleQueryResponse);" + "\n")
        fic.write("          } else {" + "\n")
        fic.write("              var dataTable = new google.visualization.DataTable();" + "\n")
        fic.write("              dataTable.addRows(" + str(len(self.PIE_DATA) + 1) + ");" + "\n")

        fic.write("              dataTable.addColumn('number');" + "\n")
        i=0
        valuesTxt=""
        actorsTxt=""
        othersWl=100
        for actor in self.PIE_DATA:
            fic.write("              dataTable.setValue(" + str(i) + ", 0, " + str(actor.workload) + ");" + "\n")
            valuesTxt = valuesTxt + str(actor.workload) + ","
            actorsTxt = actorsTxt + str(actor.workload) + "%   " + actor.actor_name + "|"
            othersWl -= float(str(actor.workload))
            i += 1

        fic.write("              dataTable.setValue(" + str(i) + ", 0, " + str(othersWl) + ");" + "\n")
        fic.write("              draw(dataTable);" + "\n")
        fic.write("          }" + "\n")
        fic.write("      }" + "\n")

        fic.write("      function draw(dataTable) {" + "\n")
        fic.write("          var vis = new google.visualization.ImageChart(document.getElementById('chart'));" + "\n")
        fic.write("          var options = {" + "\n")
        fic.write("             chs: '900x400'," + "\n")
        fic.write("              cht: 'p3'," + "\n")
        fic.write("              chco: 'FF9900'," + "\n")
        fic.write("              chd: 't:" + valuesTxt + str(othersWl) + "'," + "\n")
        fic.write("              chdl: '" + actorsTxt + "Others'," + "\n")
        fic.write("              chl: '|||'" + "\n")
        fic.write("          };" + "\n")
        fic.write("          vis.draw(dataTable, options);" + "\n")
        fic.write("      }" + "\n")

        fic.write("      function handleQueryResponse(response) {" + "\n")
        fic.write("          if (response.isError()) {" + "\n")
        fic.write("              alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());" + "\n")
        fic.write("              return;" + "\n")
        fic.write("          }" + "\n")
        fic.write("          draw(response.getDataTable());" + "\n")
        fic.write("      }" + "\n")

        fic.write("      google.load(\"visualization\", \"1\", {packages:[\"imagechart\"]});" + "\n")
        fic.write("      google.setOnLoadCallback(onLoadCallback);" + "\n")

        fic.write("  </script>" + "\n")

    def addContentInHTMLBody(self, fic):
        fic.write("      <table id=\"rounded-corner\">" + "\n")
        fic.write("          <thead>" + "\n")
        fic.write("              <tr>" + "\n")
        fic.write("                  <th scope=\"col\" class=\"rounded-head-left\"></th>" + "\n")
        fic.write("                  <th scope=\"col\" colspan=4 class=\"rounded-head-right\">" + self.OUTPUT_TAG + "</th>" + "\n")
        fic.write("              </tr>" + "\n")
        fic.write("              <tr>" + "\n")
        fic.write("                  <th scope=\"col\">Actor</th>" + "\n")
        fic.write("                  <th scope=\"col\">Cycle count</th>" + "\n")
        fic.write("                  <th scope=\"col\">FPS@" + str(self.FREQUENCY) + "MHz</th>" + "\n")
        fic.write("                  <th scope=\"col\">Workload</th>" + "\n")
        fic.write("                  <th scope=\"col\">Status</th>" + "\n")
        fic.write("              </tr>" + "\n")
        fic.write("          </thead>" + "\n")
        fic.write("          <tbody>" + "\n")

        for actor in self.extractedData:
            fic.write("              <tr>"+ "\n")
            fic.write("                  <td>" + actor.actor_name + "</td>"+ "\n")
            fic.write("                  <td class=\"num\">" + str(actor.cycles) + "</td>"+ "\n")
            fic.write("                  <td class=\"num\">" + str(actor.fps) + "</td>"+ "\n")
            fic.write("                  <td class=\"num\">" + str(actor.workload) + "</td>"+ "\n")
            if actor.status ==  "KO":
                fic.write("                  <td class=\"ko\">" + actor.status + "</td>"+ "\n")
            else:
                fic.write("                  <td class=\"ok\">" + actor.status + "</td>"+ "\n")
            fic.write("              </tr>"+ "\n")

        fic.write("          </tbody>" + "\n")
        fic.write("          <tfoot>" + "\n")
        fic.write("              <tr>" + "\n")
        fic.write("                  <td class=\"rounded-foot-left\">Worst actor :</td>" + "\n")
        fic.write("                  <td colspan=\"4\" class=\"rounded-foot-right\">" + self.WORST_ACTOR + "</td>" + "\n")
        fic.write("              </tr>" + "\n")
        fic.write("          </tfoot>" + "\n")
        fic.write("      </table>" + "\n")
