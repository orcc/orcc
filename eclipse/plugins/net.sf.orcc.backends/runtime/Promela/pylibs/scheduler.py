import subprocess
import sys
from pylibs.schedconfig import Configuration

import xml.etree.ElementTree as et

class Transition:
    sequence=None
    def __init__(self, src, dst, action, tid, nsrc='?', ndst='?'):
        self.action=action
        self.src=src  #the state in the actor fsm
        self.dst=dst  #the state in the actor fsm
        self.tid=tid
        self.nsrc=nsrc #the state in the new scheduler
        self.ndst=ndst #the state in the new scheduler
    def tostring(self):
        return "\t["+self.tid+"] "+self.action+" : "+self.src+" -> "+self.dst+' : ('+self.nsrc+" -> "+ self.ndst+')'

class FSM:
    nrtrans=0
    initial = None
    transitions = list()
    def __init__(self, initial=None):
        self.initial = initial
    def addTransition(self, trans):
        self.transitions.append(trans)
    def gettransition(self, transid):
        for trans in self.transitions:
            if trans.tid==transid:
                return trans
    def getnstates(self):
        lst=[]
        for i in self.transitions:
            if i.nsrc not in lst:
                lst.append(i.nsrc)
        return lst
    def getnewstatename(self):
        nr=len(self.getnstates())
        name="s"+str(nr)
        return name
    def addnewnstate(self, origstate, name):
        templist=list()
        for trans in self.transitions:
            if trans.src == origstate:
                ntrns = Transition(trans.src, trans.dst, trans.action, str(self.nrtrans), name)
                self.nrtrans=self.nrtrans+1
                templist.append(ntrns)
        self.transitions.extend(templist)
    def sortTransitions(self):
        """Sorts such that scheduling always starts form a known state"""
        newList = []
        nstateid=0
        visited =[self.initial]
        while len(self.transitions) != len(newList):
            for trans in self.transitions:
                if trans.src in visited and not trans in newList:
                    newList.append(trans)
                    trans.nsrc='s'+str(nstateid)
                    if trans.dst not in visited:
                        visited.append(trans.dst)
                        nstateid+=1
        self.transitions = newList
    def printfsm(self):
        print ("\nCurrent scheduler for the composition:")
        for trans in self.transitions:
            print (trans.tostring())
    def savefsm(self, folder, filen):
        filename=folder+'/'+filen
        open(filename, "a").close()
        file=open(filename, "w")
        file.writelines([item.tid+' '+item.action+' '+item.src+' '+item.dst+' '+item.nsrc+' '+item.ndst+'\n' for item in self.transitions])
        file.close()
        for trans in self.transitions:
            if trans.sequence is not None:
                filename=folder+'/sequence'+trans.tid+'.xml'
                open(filename, "a").close()#create
                file=open(filename, "w")
                content="<superaction name='"+trans.nsrc+"_"+trans.src+"_"+trans.action+"' guard='NULL'>\n"+trans.sequence+"\n</superaction>"
                file.write(content)
                file.close()
    def loadfsm(self, folder, filen):
        filename=folder+'/'+filen
        file=open(filename, 'r')
        for line in file:
            lst=line.strip().split()
            self.addTransition(Transition(lst[2],lst[3],lst[1],lst[0],lst[4],lst[5]))
        file.close()
        self.nrtrans=len(self.transitions)

class SchedulerXML():
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
            trns = Transition(trans.get('src'), trans.get('dst'), trans.get('action'), str(fsm.nrtrans))
            fsm.nrtrans=fsm.nrtrans+1
            fsm.addTransition(trns)
        fsm.sortTransitions()
        return fsm
    def savenewfsm(self, fsm, conf, newfilename):
        tree = et.parse(self.xmlfilename)
        xfsm = tree.find(".//actor/[@name='dcpred']")
        fsm = FSM(xfsm.get('initial'))
        for trans in xfsm.findall('transition'):
            print (trans)
        
    