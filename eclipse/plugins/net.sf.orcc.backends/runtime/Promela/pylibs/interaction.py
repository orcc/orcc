import sys, getopt
from subprocess import Popen, PIPE

class UserArgs():
    inputfile = ''
    outputfile = ''
    runchecker=False
    configure=False
    removeactor=None
    def parseargs(self):
        try:
            opts, args = getopt.getopt(sys.argv[1:],"hsci:o:d:")
        except getopt.GetoptError:
            self.printhelp()
            sys.exit(2)
        for opt, arg in opts:
            if opt == '-h':
                self.printhelp()
                self.test()
                sys.exit()
            elif opt in ("-i"):
                inputfile = arg
            elif opt in ("-o"):
                outputfile = arg
            elif opt in ("-s"):
                self.runchecker=True
            elif opt in ("-c"):
                self.configure=True
            elif opt in ("-d"):
                self.removeactor=arg
    def printhelp(self):
            print ("\nUsage:")
            print ('run_checker.py -i <inputfile> -o <outputfile>')
            print ('run_checker.py -c', '(configure)')
            print ('run_checker.py -s', '(run schedule search)')
            print ('run_checker.py -d <instance_name>', '(delete actor from config)')
    def test(self):
        print("\nChecking if necessary tools are available:")
        proc = Popen(['spin', '-v'], stdout=PIPE, stderr=PIPE, universal_newlines=True)
        if self.testoutput(proc, "Spin Version 6"):
            print ("\t-found Spin version 6")
        else:
            print ("\t-did not find Spin version 6")
        proc.wait()
        proc = Popen(['gcc', '-v'], stdout=PIPE, stderr=PIPE, universal_newlines=True)
        if self.testoutput(proc, "gcc version"):
            print ("\t-found gcc")
        else:
            print ("\t-did not find gcc")
        proc.wait()
    def testoutput(self, proc, string):
        for line in iter(proc.stdout):
            line = str(line.strip())
            if line.find(string) >= 0:
                return True
            proc.stdout.flush()
        for line in iter(proc.stderr):
            line = str(line.strip())
            if line.find(string) >= 0:
                return True
            proc.stderr.flush()
        return False
    
class Configuration():
    actors=None
    actor=None
    def __init__(self, filename='configuration.txt'):
        self.filename=filename
        open(self.filename, "a").close() # create it if it doesnt exist
    def loadconfiguration(self, actors):
        file=open(self.filename, 'r')
        for line in file:
            lst=line.strip().split()
            if lst[0]=='actors':
                self.actors=lst[1:len(lst)]
            elif lst[0]=='actor':
                self.actor=lst[1]
        file.close()
        if self.actors is None:
            self.actors=actors
        print(self.actors)
    def saveconfiguration(self):
        file=open(self.filename, "w")
        tmp=['actors ']
        tmp.extend(self.actors)
        file.writelines(["%s " % item  for item in tmp])
        file.close
    def removeactor(self, actorname):
        if actorname in self.actors:
            self.actors.remove(actorname)
            print ("Actor ", actorname, "deleted from configuration")
        else:
            print ("Actor ", actorname, "not in configuration")

    
    
    
