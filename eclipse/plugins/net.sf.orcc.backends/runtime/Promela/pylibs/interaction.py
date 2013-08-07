import sys, getopt

class UserArgs():
    inputfile = ''
    outputfile = ''
    runchecker=False
    configure=False
    def parseargs(self):
        try:
            opts, args = getopt.getopt(sys.argv[1:],"hi:o:c")
        except getopt.GetoptError:
            self.printhelp()
            sys.exit(2)
        for opt, arg in opts:
            if opt == '-h':
                self.printhelp()
                sys.exit()
            elif opt in ("-i"):
                inputfile = arg
            elif opt in ("-o"):
                outputfile = arg
            elif opt in ("-c"):
                self.runchecker=True
    def printhelp(self):
            print ('run_checker.py -i <inputfile> -o <outputfile>')
            print ('run_checker.py -c')

    