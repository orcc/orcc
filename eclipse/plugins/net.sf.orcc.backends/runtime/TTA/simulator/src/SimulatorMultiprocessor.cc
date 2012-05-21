#include "SimulatorMultiprocessor.hh"

#include <SimpleSimulatorFrontend.hh>
#include <Machine.hh>
#include <MemorySystem.hh>
#include <SimulatorCLI.hh>
#include <Listener.hh>

#include <stdio.h>

/**
 * Constructor.
 */
SimulatorMultiprocessor::SimulatorMultiprocessor() {
}

SimulatorMultiprocessor::~SimulatorMultiprocessor() {
}


/**
 * A handler class for Ctrl-c signal.
 *
 * Stops the simulation (if it's running) and attaches the ttasim
 * console to it.
 */
class SigINTHandler : public Application::UnixSignalHandler {
public:
    /**
     * Constructor.
     *
     * @param target The target SimulatorFrontend instance.
     */
    SigINTHandler(SimulatorMultiprocessor& simulator) :
        simulator_(simulator) {
    }

    /**
     * Stops the simulation.
     */
    virtual void execute(int /*data*/, siginfo_t* /*info*/) {
        std::cerr << "### ctrl-C handler" << std::endl;
        simulator_.stop();
        std::cerr << "### handler exit" << std::endl;
        /* Make it a one shot handler. Next Ctrl-C should kill the program. */
        Application::restoreSignalHandler(SIGINT);
    }
private:
    SimulatorMultiprocessor& simulator_;
};

void
SimulatorMultiprocessor::addProcessor(const char* machineFile, const char* programFile) {
  SimulationData *sim;
  
  if (machineFile == NULL || programFile == NULL) {
    fprintf(stderr, "Need ADF and TPEF files.");
    exit(2);
  }
    
  sim = (SimulationData *) malloc (sizeof (SimulationData));
    
  sim->machineFile = machineFile;
  sim->programFile = programFile;
  
  dataList_.push_back(*sim);
}
  

void
SimulatorMultiprocessor::initialize() {
  list<SimulationData>::iterator iter;
  
  // Create a simulator for each processor
  for(iter = dataList_.begin(); iter != dataList_.end(); ++iter) {
    iter->simulator = new SimpleSimulatorFrontend(iter->machineFile);
  }
  
  // Link shared memories by looking for existing ones in each pair of TTA.
  list<SimulationData> list(dataList_);
  while(list.size() > 1) {
    MemorySystem &current = list.front().simulator->memorySystem();
    list.pop_front();
    
    for(iter = list.begin(); iter != list.end(); ++iter) {
      current.shareMemoriesWith(iter->simulator->memorySystem());
    }
  }
  
  // Load programs
  for(iter = dataList_.begin(); iter != dataList_.end(); ++iter) {
    iter->simulator->loadProgram(iter->programFile);
    iter->simulatorCLI = new SimulatorCLI(iter->simulator->frontend());
  }
  
}

void 
SimulatorMultiprocessor::step() {
  list<SimulationData>::iterator iter;
  
  for(iter = dataList_.begin(); iter != dataList_.end(); ++iter) {
    iter->simulator->step();    
    if (iter->simulator->hadRuntimeError()) {
      iter->simulatorCLI->run();
      fprintf(stderr, "Runtime error in a ttasim device.");
      exit(2);
    }
  }
}

void 
SimulatorMultiprocessor::run() {
  stopRequested_ = false;
  int i=0;
  
  do{
	//fprintf(stdout, "Cycle: %d\n", i);
    step();
    i++;
    
    if (stopRequested_) {
      stopRequested_ = false;
      dataList_.front().simulatorCLI->run();
    }
  } while(true);

}

void 
SimulatorMultiprocessor::stop() {
  stopRequested_ = true;
}


int main() {
  
  SimulatorMultiprocessor* simulator = new SimulatorMultiprocessor();
    
  fprintf(stdout, "Add processors\n");
  simulator->addProcessor("/home/hyviquel/Workspaces/experimental/rvc/tta/simple/processor_Source/processor_Source.adf", 
                            "/home/hyviquel/Workspaces/experimental/rvc/tta/simple/processor_Source/processor_Source.tpef");
  simulator->addProcessor("/home/hyviquel/Workspaces/experimental/rvc/tta/simple/processor_Process/processor_Process.adf", 
                            "/home/hyviquel/Workspaces/experimental/rvc/tta/simple/processor_Process/processor_Process.tpef");
  simulator->addProcessor("/home/hyviquel/Workspaces/experimental/rvc/tta/simple/processor_Target/processor_Target.adf", 
                            "/home/hyviquel/Workspaces/experimental/rvc/tta/simple/processor_Target/processor_Target.tpef");
  
  fprintf(stdout, "Initialize the simulators\n");
  simulator->initialize();
  
  SigINTHandler* ctrlcHandler = new SigINTHandler(*simulator);
  Application::setSignalHandler(SIGINT, *ctrlcHandler);
  
  fprintf(stdout, "Run\n");
  simulator->run();
    
  return 0;
}
