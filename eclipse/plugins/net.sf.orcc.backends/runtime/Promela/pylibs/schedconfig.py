import os, errno


class Configuration():
    actors=None
    leader=None
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
    def printconfiguration(self):
        print ("\nCurrent configuration:")
        print ("\nLeader Actor: ", self.leader)
        print("Actors:\t", "".join(["%s \n\t" % item for item in self.actors]))

class RunConfiguration(object):
    name=None
    stateconf=None
    conf=None
    file1='tmp_start_actors.pml'
    file2='tmp_state.pml'
    file3='tmp_ltl_expr.pml'
    def __init__(self, conf):
        self.conf=conf
    def configure(self, statedesc):
        open(self.file1, "a").close() # create the file for starting processes if it doesnt exist
        file=open(self.file1, "w")
        file.writelines(["run %s();\n" % item  for item in self.conf.actors])
        file.close()
        open(self.file2, "a").close() # create the file for vars if it doesnt exist
        file=open(self.file2, "w")
        file.write(statedesc.tostring())
        file.close
        open(self.file3, "a").close() # create the file for ltl if it doesnt exist
        file=open(self.file3, "w")
        file.write("#define emptyBuffer (len(chan_0)==0 && len(chan_1)==0 && len(chan_2)==0 && len(chan_3)==0)\n")
        file.write("ltl test {[]!(state == state_id) && emptyBuffer}\n")
        file.close()
    def confinitsearch(self):
        open(self.file1, "a").close() # create it if it doesnt exist
        file=open(self.file1, "w")
        file.writelines(['run dummy();'])
        file.close()
        open(self.file2, "w").close() # create it if it doesnt exist
        open(self.file3, "w").close() # create it if it doesnt exist
        
class StateDescription(object):
    state={}
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

    