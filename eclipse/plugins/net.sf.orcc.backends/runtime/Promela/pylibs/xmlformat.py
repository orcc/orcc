import subprocess
import sys

import xml.etree.ElementTree as et

class Transition:
    def __init__(self, src, dst, action, tid):
        self.action=action
        self.src=src
        self.dst=dst
        self.tid=tid

class FSM:
    initial = None
    transitions = list()
    def __init__(self, initial):
        self.initial = initial
    def addTransition(self, trans):
        self.transitions.append(trans)
    def sortTransitions(self):
        """Sorts such that scheduling always starts form a known state"""
        newList = []
        visited =[self.initial]
        while len(self.transitions) != len(newList):
            for trans in self.transitions:
                if trans.src in visited and  not trans in newList:
                    newList.append(trans)
                    visited.append(trans.dst)
        self.transitions = newList
    def printfsm(self):
        print ("\nInitial scheduler for the composition:")
        for trans in self.transitions:
            print ("\t", "["+trans.tid+"]", trans.action, " : ", trans.src, "->", trans.dst)

class SchedulerXML():
    tid=0
    xmlfilename=None
    def __init__(self, xmlfilename):
        self.xmlfilename=xmlfilename
    def getactorfsm(self, actorname):
        if actorname is None:
            print ('Failed: No leader actor set. Use: -l<actor>')
            sys.exit()
        tree = et.parse(self.xmlfilename)
        xfsm = tree.find(".//actor/[@name='"+actorname+"']../fsm")
        fsm = FSM(xfsm.get('initial'))
        for trans in xfsm.findall('transition'):
            trns = Transition(trans.get('src'), trans.get('dst'), trans.get('action'), str(self.tid))
            self.tid=self.tid+1
            fsm.addTransition(trns)
        fsm.sortTransitions()
        return fsm
