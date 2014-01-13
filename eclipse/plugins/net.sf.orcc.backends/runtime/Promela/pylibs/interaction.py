import sys, getopt
from subprocess import Popen, PIPE

class UserArgs():
    inputfile = ''
    outputfile = ''
    runchecker=False
    runcheckerid=None
    configure=False
    removeactor=None
    setleader=None
    findspecificstate=None
    printXML=False
    shortestPath=False
    def parseargs(self):
        try:
            opts, args = getopt.getopt(sys.argv[1:],"hs:c:d:l:rf:pi")
        except getopt.GetoptError:
            self.printhelp()
            sys.exit(2)
        for opt, arg in opts:
            if opt == '-h':
                self.printhelp()
                self.test()
                sys.exit()
            elif opt in ("-s"):
                self.runcheckerid=arg
            elif opt in ("-r"):
                self.runchecker=True
            elif opt in ("-c"):
                self.configure=True
                self.setleader=arg
            elif opt in ("-d"):
                self.removeactor=arg
            elif opt in ("-f"):
                self.findspecificstate=arg
            elif opt in ("-p"):
                self.printXML=True
            elif opt in ("-i"):
                self.shortestPath=True   
    def printhelp(self):
            print ("\nUsage:")
            print ('run_checker.py -h', '(show help)')
            print ('run_checker.py -d <instance_name>', '(delete actor from config)')
            print ('run_checker.py -c <instance_name>', '(configure according to actor)')
            print ('run_checker.py -s <sched id>', '(setup modelchecker for the schedule)')
            print ('run_checker.py -r', '(run schedule search with the current config)')
            print ('run_checker.py -f <state ID>', '(can be used with -c, to specify a specific state to find e.g. -f s0)')
            print ('run_checker.py -p', '(print the new scheduler to a XML file)')
            print ('run_checker.py -i', '(can be used with -r, to search for shorthest path to state)')
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
    
   
    

    
