import subprocess
import sys

import xml.etree.ElementTree as et

class Transition:
    def __init__(self, src, dst, action):
        self.action=action
        self.src=src
        self.dst=dst

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
        print ("\nInitial Scheduler for the composition:")
        for trans in newList:
            print ("\t", trans.action, " : ", trans.src, "->", trans.dst)

class SchedulerXML():
    xmlfilename=None
    def __init__(self, xmlfilename):
        self.xmlfilename=xmlfilename
    def getactorfsm(self, actorname):
        tree = et.parse(self.xmlfilename)
        xfsm = tree.find(".//actor/[@name='"+actorname+"']../fsm")
        fsm = FSM(xfsm.get('initial'))
        for trans in xfsm.findall('transition'):
            trans = Transition(trans.get('src'), trans.get('dst'), trans.get('action'))
            fsm.addTransition(trans)
        fsm.sortTransitions()
        return fsm



#if (len(sys.argv)>1):
#    print ('\nAbout to perform scheduling in network:\n\t '+ sys.argv[1]+'\n')
#    filename = ('schedule_'+ sys.argv[1]+'.xml')
#else:
#    print ('Give code generated network name as first argument.\n')
#    sys.exit()

#if (len(sys.argv)>3):
#    actorname = sys.argv[2]
#    print ('Searching for schedules for the following Actors:')
#    for i in range (2, len(sys.argv)):
#        print ("\t", sys.argv[i])
#else:
#    print ('HINT: Give the Actors to compose, the lead actor as argument 2 followed by the other actors\n')
#    sys.exit()




#filename = 'sched_states.txt'

#open(filename, "a").close() # create it if it doesnt exist

#proc = subprocess.Popen(['spin', 'main_Acdc.pml'],stdout=subprocess.PIPE)

#for line in iter(proc.stdout):
#    print(str(line.rstrip()))
#    proc.stdout.flush()

#f = open(filename, 'r')
#text = f.read()
#f.close()
#f = open(filename, 'w')
#f.write(text + "\n abc")
#f.close()
