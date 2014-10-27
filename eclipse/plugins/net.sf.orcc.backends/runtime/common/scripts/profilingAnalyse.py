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
from orccAnalyse import OrccAnalyse
import os
import glob
import shutil
import sys
import subprocess
import argparse
from xml.dom.minidom import parse


class ProfilingData:
    def __init__(self):
        self.name = ""
        self.workload = 0.0
        self.actions = list()

class ProfilingAnalyse(OrccAnalyse):
    def __init__(self, input_file):
        OrccAnalyse.__init__(self, os.path.splitext(os.path.basename(input_file))[0])
        self.logCSV = True
        self.logTXT = False
        self.logXML = False
        self.logHTML = True
        self.logPDF = False
        self.ARCHIVE = False
        self.input_file = input_file
        self.PIE_DATA = list()
        self.MIN_WL_FOR_PIE = 5
        self.extractedData = list()
        self.TOTAL_WL = 0.0

    def performBench(self):
        print ("*********************************************************************")
        print ("* Orcc Profiling Pretty Print")
        print ("*********************************************************************")
        if os.path.isfile(self.input_file) == False:
            print ("Error : " + self.input_file + " is not a file.")
            exit(1)
        print ("==> input file  : %s" % (self.input_file))
        print ("==> output file : %s" % (self.SUMMARY_HTML))

    def extractData(self):
        dataActorList = list()
        print ("\n  * Extracting data from orcc's profiling file")

        # Load XML into tree structure
        tree = parse(self.input_file)

        #Find all <actor>
        actors_list = tree.getElementsByTagName('actor')
        
        for actor in actors_list:
            dataActor = ProfilingData()
            dataActor.name = actor.attributes.getNamedItem('id').nodeValue
            dataActor.workload = float(actor.attributes.getNamedItem('workload').nodeValue)
            dataActionList = list()
            if actor.attributes.getNamedItem('schedulerWorkload') != None:
                dataScheduler = ProfilingData()
                dataScheduler.name = "scheduler"
                dataScheduler.workload = float(actor.attributes.getNamedItem('schedulerWorkload').nodeValue)
                dataActionList.append(dataScheduler)
            for action in actor.getElementsByTagName('action'):
                dataAction = ProfilingData()
                dataAction.name = action.attributes.getNamedItem('id').nodeValue
                dataAction.workload = float(action.attributes.getNamedItem('workload').nodeValue)
                dataActionList.append(dataAction)
            dataActor.actions = sorted(dataActionList, key=lambda action: action.workload, reverse=True)
            dataActorList.append(dataActor)
            self.TOTAL_WL += dataActor.workload

        print ("\n  * Computing data from orcc's profiling file")
        self.extractedData = sorted(dataActorList, key=lambda actor: actor.name)

    def computeData(self):
        wl = list()
        for actor in self.extractedData:
            for action in actor.actions:
                action.workload = round((action.workload * 100) / actor.workload, 2)
            actor.workload = round((actor.workload * 100) / self.TOTAL_WL, 2)
            if actor.workload > self.MIN_WL_FOR_PIE:
                wl.append(actor)

        self.PIE_DATA = sorted(wl, key=lambda actor: actor.workload, reverse=True)

    def addScriptsInHTML(self, fic):
        self.addPercentBarScript(fic)

    def addStylesInHTML(self, fic):
        self.addRoundedTabStyles(fic)
        self.addActionsTabStyles(fic)
        self.addTreeViewStyles(fic)

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
            actorsTxt = actorsTxt + str(actor.workload) + "%   " + actor.name + "|"
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
        fic.write("            <div class=\"css-treeview\" align=center>" + "\n");
        fic.write("                <ul><table id=\"rounded-corner\">" + "\n");
        fic.write("                    <thead>" + "\n");
        fic.write("                      <tr>" + "\n");
        fic.write("                          <th scope=\"col\" width=85% class=\"rounded-head-left\">Actors</th>" + "\n");
        fic.write("                          <th scope=\"col\" class=\"rounded-head-right\">Workload</th>" + "\n");
        fic.write("                      </tr>" + "\n");
        fic.write("                    </thead>" + "\n");
        index = 0
        for actor in self.extractedData:
            fic.write("                    <tr>" + "\n");
            fic.write("                          <li><td><input type=\"checkbox\" id=\"item-" + str(index) + "\" /><label for=\"item-" + str(index) + "\">" + actor.name + "</label>" + "\n");
            fic.write("                              <ul>" + "\n");
            fic.write("                                 <table id=\"actions-tab\">" + "\n");
            for action in actor.actions:
                fic.write("                                  <tr><li><td>" + action.name + "</td><td align=right><script language=\"javascript\">drawPercentBar(100, " + str(action.workload) + "); </script></td></li></tr>" + "\n");
            fic.write("                                 </table>" + "\n");
            fic.write("                              </ul>" + "\n");
            fic.write("                              </td><td align=right><script language=\"javascript\">drawPercentBar(100, " + str(actor.workload) + "); </script></td>" + "\n");
            fic.write("                          </li>" + "\n");
            fic.write("                    </tr>" + "\n");
            index = index + 1
        fic.write("                          <tfoot>" + "\n");
        fic.write("                              <tr>" + "\n");
        fic.write("                                  <td class=\"rounded-foot-left\"> </td>" + "\n");
        fic.write("                                  <td class=\"rounded-foot-right\"> </td>" + "\n");
        fic.write("                              </tr>" + "\n");
        fic.write("                          </tfoot>" + "\n");
        fic.write("                      </table></ul>" + "\n");
        fic.write("                  </div>" + "\n");
        
    def logInCSV(self):
        print ("\n  * Generate CSV Result file : " + os.path.join(self.SRC_DIR, self.SUMMARY_CSV))
        fic = open(os.path.join(self.SRC_DIR, self.SUMMARY_CSV), 'w')
        # Header
        fic.write("Actor;Workload" + "\n")
        # TODO : Add output_tag information ?

        # Body
        for actor in self.extractedData:
            fic.write(actor.name + ";" + str(actor.workload).replace(".", ",") + "\n");

        # Footer
        # TODO : Add worst actor ?
        fic.close()

# Main

# Parse args
parser = argparse.ArgumentParser(description='Open RVC-CAL Compiler - Profiling Pretty Print')
parser.add_argument('input_file', help='The profiling file given by orcc.')
args = parser.parse_args()

# Begin
oppp = ProfilingAnalyse(args.input_file)
oppp.start()