import sys, getopt
from subprocess import Popen, PIPE

class UserArgs():
    inputfile = ''
    outputfile = ''
    runchecker=False
    configure=False
    removeactor=None
    setleader=None
    def parseargs(self):
        try:
            opts, args = getopt.getopt(sys.argv[1:],"hsci:o:d:l:")
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
            elif opt in ("-l"):
                self.setleader=arg
    def printhelp(self):
            print ("\nUsage:")
            print ('run_checker.py -i <inputfile> -o <outputfile>')
            print ('run_checker.py -c', '(configure)')
            print ('run_checker.py -s <id>', '(run schedule search)')
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
    
   
    

    
