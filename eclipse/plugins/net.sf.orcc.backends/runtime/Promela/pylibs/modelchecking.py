from subprocess import Popen, PIPE

class ModelChecker(object):
    endstate=''
    schedxml=''
    returncode = None
    tracefound=False
    def simulatetrail(self, filename):
        proc = Popen(['spin', '-t', '-DPXML', '-DMANAGED', filename], stdout=PIPE, universal_newlines=True)
        self.getoutput(proc)
    def simulate(self, filename):
        proc = Popen(['spin', '-DMANAGED', filename], stdout=PIPE, universal_newlines=True)
        self.getoutput(proc)
    def getoutput(self, proc):
        self.endstate = ""
        self.schedxml =""
        for line in iter(proc.stdout):
            line = str(line.strip())
            if line.startswith("spin"):
                print(line)
            elif line.find('[') >= 0: # skip lists
                pass
            elif line.find('state_var_') >= 0 or line.find('fsm_state_') >= 0:
                self.endstate += line + ';\n'
            elif line.find('iterand') >= 0 :
                self.schedxml += line + '\n'
            else:
                pass
            proc.stdout.flush()
        proc.wait()
        self.returncode = proc.returncode
    def generatemc(self, filename):
        print ("Generating model checker: spin -a -DXML ",filename)
        proc = Popen(['spin', '-a', '-DPXML', '-DMANAGED', filename])
        proc.wait()
    def compilemc(self):
        print ("Compiling model checker: gcc -DCOLLAPSE -DVECTORS=100000 -o pan pan.c")
        proc = Popen(['gcc', '-DCOLLAPSE', '-DVECTORSZ=100000', '-o', 'pan', 'pan.c'])
        proc.wait()
    def runmc(self):
        print ("Running model checker: pan -E -n")
        proc = Popen(['pan', '-E', '-n'], stdout=PIPE, universal_newlines=True)
        for line in iter(proc.stdout):
            line = str(line.strip())
            if line.find('end state in claim reached') >= 0 :
                self.tracefound=True
                print ("Pan:", line)
            else:
                print("Pan:", line)
            proc.stdout.flush()
        proc.wait()
        self.returncode = proc.returncode

    
    



