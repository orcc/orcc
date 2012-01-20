/*********************************************************
Copyright or © or Copr. IETR/INSA: Maxime Pelcat

Contact mpelcat for more information:
mpelcat@insa-rennes.fr

This software is a computer program whose purpose is to execute
parallel applications.

 *********************************************************/
 
#ifndef SCHEDULER_H
#define SCHEDULER_H

#include "..\expressionParser\XParser.h"
#include "..\graphs\HDAG\HDAGGraph.h"
#include "..\graphs\HDAG\HDAGVertex.h"
#include "..\graphs\HDAG\HDAGEdge.h"
#include "..\graphs\CSDAG\CSDAGGraph.h"
#include "..\graphs\CSDAG\CSDAGVertex.h"
#include "..\graphs\CSDAG\CSDAGEdge.h"
#include "..\transformations\CSDAGTransformer.h"
#include "..\scheduling\ListScheduler.h"
#include "..\tools\DotWriter.h"
#include "..\tools\ScheduleWriter.h"

// Files to write the results
#ifdef RUNTIME
#define CSDAG_FILE_PATH "../../Scheduling/dot/csdag.gv"
#define HDAG_FILE_PATH "../../Scheduling/dot/hdag.gv"
#define GANTT_FILE_PATH "../../Scheduling/gantt/ganttEvents.xml"
#define MATLAB_SPEEDUPS_FILE_PATH "../../Scheduling/gantt/speedups.dat"
#define MATLAB_SPANS_FILE_PATH "../../Scheduling/gantt/spans.dat"
#define MATLAB_WORKS_FILE_PATH "../../Scheduling/gantt/works.dat"
#else
#define CSDAG_FILE_PATH "..\\dot\\csdag.gv"
#define HDAG_FILE_PATH "..\\dot\\hdag.gv"
#define GANTT_FILE_PATH "..\\gantt\\ganttEvents.xml"
#define MATLAB_SPEEDUPS_FILE_PATH "..\\gantt\\speedups.dat"
#define MATLAB_SPANS_FILE_PATH "..\\gantt\\spans.dat"
#define MATLAB_WORKS_FILE_PATH "..\\gantt\\works.dat"
#endif

#if 0

/**
 * Class handling the whole graph transformation and scheduling
 * 
 * @author mpelcat
 */
class Scheduler {

	private :
		/**
		 CSDAG to HDAG transformer
		*/
		CSDAGTransformer transformer;
		/**
		 List scheduler
		*/
		ListScheduler scheduler;
	public : 
		/**
		 Constructor
		*/
		Scheduler();


		/**
		 Destructor
		*/
		~Scheduler();


		/**
		 Generates the HDAG graph and schedules it, 
		 displaying graphs and gantt chart if DISPLAY is defined

		 @param csDag: input graph
		 @param hDag: output graph
		 @param archi: slave architecture
		*/
		void generateAndSchedule(CSDAGGraph* csDag, HDAGGraph* hDag, Architecture* archi);
};



#endif
#endif
