import os, errno
import xml.etree.ElementTree as et

class Configuration():
    actors=None
    leader=None
    schedule=None
    filename=""
    states=[]
    def __init__(self, folder, filename):
        try:
            os.makedirs(folder)
        except OSError as exception:
            if exception.errno != errno.EEXIST:
                raise
        self.filename=folder+"/"+filename
        open(self.filename, "a").close() # create it if it doesnt exist
    def loadconfiguration(self, actors):
        file=open(self.filename, 'r')
        for line in file:
            lst=line.strip().split()
            if lst[0]=='actors':
                self.actors=lst[1:len(lst)]
            elif lst[0]=='leader':
                self.leader=lst[1]
            elif lst[0]=='schedule':
                self.schedule=lst[1]
        file.close()
        if self.actors is None:
            self.actors=actors
    def saveconfiguration(self):
        file=open(self.filename, "w")
        tmp=['actors ']
        tmp.extend(self.actors)
        file.writelines(["%s " % item  for item in tmp])
        if self.leader is not None:
            file.write("\nleader " +self.leader)
        if self.schedule is not None:
            file.write("\nschedule " +self.schedule)
        file.close
    def removeactor(self, actorname):
        if actorname in self.actors:
            self.actors.remove(actorname)
            print ("\nActor", actorname, "deleted from configuration")
        else:
            print ("\nActor", actorname, "not in configuration")
    def setleader(self, actor):
        if actor in self.actors:
            self.leader=actor
            print ("\nActor", actor, "was set as leader.")
        else:
            print ("\nWarning: Actor", actor, "not in configuration.")
            exit()
    def setschedule(self, scheduleid):
        self.schedule=scheduleid
    def printconfiguration(self):
        print ("\nCurrent configuration:")
        print ("\nLeader Actor: ", self.leader)
        print("Actors:\t", "".join(["%s \n\t" % item for item in self.actors]))
        print ("Setup for schedule with ID:", self.schedule,"\n\n")

class RunConfiguration(object):
    name=None
    stateconf=None
    conf=None
    file1='tmp_start_actors.pml'
    file2='tmp_state.pml'
    file3='tmp_ltl_expr.pml'
    def __init__(self, conf):
        self.conf=conf
    def configure(self, statedesc, inputseq, currstate, action, nextstate, nextfsmstate):
        open(self.file1, "a").close() # create the file for starting processes if it doesnt exist
        file=open(self.file1, "w")
        file.writelines(["run %s();\n" % item  for item in self.conf.actors])
        file.close()
        open(self.file2, "a").close() # create the file for vars if it doesnt exist
        file=open(self.file2, "w")
        file.write(statedesc.tostring())
        file.write("\n//Inputs:\n")
        file.write(inputseq.tostring(currstate, action))
        file.close
        open(self.file3, "a").close() # create the file for ltl if it doesnt exist
        file=open(self.file3, "w")
        buffers='&&'.join(["len(chan_"+str(elem)+")==0" for elem in inputseq.channels])
        file.write("#define emptyBuffer ("+buffers+")\n")
        file.write(self.__ltl(inputseq, nextstate, nextfsmstate))
        file.close()
    def confinitsearch(self):
        open(self.file1, "a").close() # create it if it doesnt exist
        file=open(self.file1, "w")
        file.writelines(['run dummy();'])
        file.close()
        open(self.file2, "w").close() # create it if it doesnt exist
        open(self.file3, "w").close() # create it if it doesnt exist
    def __ltl(self, inputseq, nextstate, actortostatesdic):
        # the morefsmstates is of type {fsm_state_???:[state, state,..]}
        s=  "ltl test {[]!(promela_prog_initiated==1 &&"
        s+= "fsm_state_"+self.conf.leader+"=="+self.conf.leader+"_state_"+nextstate
        for state in actortostatesdic.keys():
            s+= "&& ("
            s+='||'.join([state+"=="+val for val in actortostatesdic[state]])
            s+=")"
        s+= "&& emptyBuffer)}\n"
        return s
    
        
class StateDescription(object):
    state=None
    varfilter=None
    def __init__(self):
        self.state={}
        self.varfilter=[]
    def fromstring(self, string):
        lst=string.split(';')
        for line in lst:
            var=line.split('=')
            if len(var)==2:
                self.state[var[0].strip()]=var[1].strip()
    def tostring(self):
        string=""
        for elem in self.state.keys():
            string+=elem +" = "+self.state[elem]+";\n"
        return string
    def savestate(self, folder, filename):
        open(folder+'/'+filename, "a").close() # create it if it doesnt exist
        f = open(folder+'/'+filename, 'w')
        f.write(self.tostring())
        f.close()
    def loadstate(self, folder, filename):
        f = open(folder+'/'+filename, 'r')
        string=f.read()
        self.fromstring(string)
    def isequalto(self, folder, otherstates):
        ret=None
        sd=StateDescription()
        for st in otherstates:
            if not os.path.exists(folder+'/'+st+'.txt'):
                # as the file does not exist, this state is not yet defined; so we define it
                self.savestate(folder, st+'.txt')
            sd.loadstate(folder, st+'.txt')
            issame=True
            for elem in self.state.keys():
                if elem not in self.varfilter:
                    if not(self.state[elem] == sd.state[elem]):
                        issame=False
            if issame:
                return st
        return None
    def getfsmstates(self):
        dic={}
        for var in self.state.keys():
            if var.find('fsm_state_') >= 0 :
                dic[var]=[self.state[var]]
        return dic
    def setfilter(self, xmlfilename):
        tree = et.parse(xmlfilename)
        for xactor in tree.findall('.//actor'):
            actorname=xactor.get('name')
            temp=[]
            # all vars for this actor
            for allstate in xactor.findall('.//allstates'):
                for xvar in allstate.findall('.//variable'):
                    temp.append(xvar.get('name'))
            # if a better description in found..
            for xstate in xactor.findall('.//fsmstate'):
                statename=xstate.get('name')
                if "fsm_state_"+actorname in self.state.keys():
                    if self.state["fsm_state_"+actorname]==self.state[actorname+"_state_"+statename]:
                        adescribedstate=True
                        if actorname+"_state_"+statename in self.state.keys():
                            for xvar in xstate.findall('.//variable'):
                                temp.remove(xvar.get('name'))
                            self.varfilter.extend(temp)
        print (actorname, self.varfilter)

class InputSeq(object):
    actortostates={}
    leaders=None
    channels=None
    def __init__(self):
        self.channels=[]
    def addpeek(self, actor, port, schedulestate, scheduleaction, value):
        self.check(actor, port, schedulestate, scheduleaction)
        self.actortostates[actor][schedulestate][scheduleaction][port].append(value)
    def addread(self, actor, port, schedulestate, scheduleaction, value):
        self.check(actor, port, schedulestate, scheduleaction)
        nrpeek=len(self.actortostates[actor][schedulestate][scheduleaction][port])
        self.actortostates[actor][schedulestate][scheduleaction][port].extend(['0']*((int(value))-nrpeek))
    def getseq(self, actor, port, schedulestate, scheduleaction, value):
        if self.check(actor, port, schedulestate, scheduleaction):
            return actortostates[actor][schedulestate][scheduleaction][port]
        else:
            return []
    def check(self, actor, port, schedulestate, scheduleaction):
        if actor not in self.actortostates:
            self.actortostates[actor]={}
        if schedulestate not in self.actortostates[actor]:
            self.actortostates[actor][schedulestate]={}
        if scheduleaction not in self.actortostates[actor][schedulestate]:
            self.actortostates[actor][schedulestate][scheduleaction]={}
        if port not in self.actortostates[actor][schedulestate][scheduleaction]:
            self.actortostates[actor][schedulestate][scheduleaction][port]=[]
    def tostring(self, lstate, laction):
        s=""
        for actor in self.actortostates.keys():
            if actor in self.leaders:
                if laction in self.actortostates[actor][lstate].keys():
                    for port in self.actortostates[actor][lstate][laction]:
                        for val in self.actortostates[actor][lstate][laction][port]:
                            s+=("chan_"+actor+"_"+port+"!"+val+";\n")
            else:
                for state in self.actortostates[actor]:
                    for action in self.actortostates[actor][state]:
                        for port in self.actortostates[actor][state][action]:
                            for val in self.actortostates[actor][state][action][port]:
                                s+=("chan_"+actor+"_"+port+"!"+val+";\n")
                                if actor+"_"+port in self.channels: # remove the data inputs
                                    self.channels.remove(actor+"_"+port)
        return s


class ChannelConfigXML():
    xmlfilename=None
    partitioninput=None
    def __init__(self, xmlfilename):
        self.partitioninput=InputSeq()
        self.xmlfilename=xmlfilename
    def findinputs(self, configuration):
        self.partitioninput.leaders=[configuration.leader]
        tree = et.parse(self.xmlfilename)
        for xactor in tree.findall('.//actor'):        
            for xinput in xactor.findall('.//input'):
                self.partitioninput.channels.append(xactor.get('name')+'_'+xinput.get('port'))
                #check if port is input to partition
                if xinput.get('instance') not in configuration.actors:
                    if xactor.get('name').find('_bcast')>=0: #special case, check downstream
                        feeds_leader=False
                        for xoutput in xactor.findall('.//connections/output'):
                            xother_actorID=xoutput.get('instance')
                            channel=xoutput.get('channelID')
                            if xother_actorID==configuration.leader:
                                self.partitioninput.leaders.append(xactor.get('name'))
                                xother_actor=tree.find(".//actor[@name='"+xother_actorID+"']")
                                xother_input=xother_actor.find(".//connections/input[@channelID='"+channel+"']")
                                self.__getrates(xother_actor, xother_input, xactor, xinput)
                                feeds_leader=True
                        if not feeds_leader:
                            self.__getrates(xactor, xinput, xactor, xinput)
                    else:
                        self.__getrates(xactor, xinput, xactor, xinput)
        return self.partitioninput
    def __getrates(self, xactor, xinput, aactor, ainput):
        #x-args is where to look, a-args is where to place..
        for xschedule in xactor.findall('.//schedule'):
            for xrates in xschedule.findall('.//rates'):
                for xpeek in xrates.findall('.//peek'):
                    if xpeek.get('port') == xinput.get('port'):
                        self.partitioninput.addpeek(aactor.get('name'), ainput.get('port'), xschedule.get('initstate'),xschedule.get('action'),xpeek.get('value'))
                for xread in xrates.findall('.//read'):
                    if xread.get('port') == xinput.get('port'):
                        self.partitioninput.addread(aactor.get('name'), ainput.get('port'), xschedule.get('initstate'),xschedule.get('action'),xread.get('value'))


