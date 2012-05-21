/**
 * @file SimulatorMultiprocessor.hh
 *
 * Declaration of SimulatorMultiprocessor class.
 *
 * @author Herve Yviquel
 */

#ifndef TTA_SIMULATOR_MULTIPROCESSOR
#define TTA_SIMULATOR_MULTIPROCESSOR

#include <list>

class SimpleSimulatorFrontend;
class SimulatorCLI;
class MemorySystem;
class Listener;
class Machine;
class Memory;

using namespace std;

namespace TTAMachine {
  class Machine;
}



struct simulation_data {
	const char* name;
	/* Input files. */
	const char* machineFile;
	const char* programFile;
	/* The ttasim engine handle. */
	SimpleSimulatorFrontend* simulator;
	/* A Command Line Interface for debugging. */
	SimulatorCLI* simulatorCLI;
};


/**
 * A simulator implementation for multiprocessor architecture with 
 * heterogeneous TTA.
 */
class SimulatorMultiprocessor {
public:

    SimulatorMultiprocessor();
    virtual ~SimulatorMultiprocessor();
    
    void addProcessor(const char* machineFile, const char* programFile);
    void initialize();

    void step();
    void run();
    void stop();

private:

    typedef struct simulation_data SimulationData;

    /// Data list of each simulation process
    list<SimulationData> dataList_;
    
    /// Flag indicating that simulation should stop.
    bool stopRequested_;
    
};

#endif
