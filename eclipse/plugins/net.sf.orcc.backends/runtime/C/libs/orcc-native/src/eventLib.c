#include "eventLib.h"
#include "papi.h"

int eventSet0 = PAPI_NULL;
int eventSet1 = PAPI_NULL;
int * eventSet;


//int total1 = 0;						   /* total overflows */
//int total2 = 0;						   /* total overflows */
int total = 0;

void structure_test(papi_action_s *someAction){
	int i;
	printf("Action name: %s\n", someAction->action_id);
	printf("Event Code Set:\n");
	for(i=0; i<someAction->eventCodeSetSize; i++){
		printf("\t-%s\n", someAction->eventCodeSet[i]);
	}
}


void handler( int EventSet, void *address, long long overflow_vector, void *context)
{
	( void ) context;

	/*if ( !TESTS_QUIET ) {
		fprintf( stderr, OVER_FMT, EventSet, address, overflow_vector );
	}*/

	/* it should be more elaborated to automate the process.
	if (threadID == 0)
		total1++;
	else
		total2++; */
	total++;
}


static void test_fail( char *file, int line, char *call, int retval ) {

	int line_pad;
	char buf[128];

	line_pad = (int) (50 - strlen(file));
	if (line_pad < 0) {
		line_pad = 0;
	}

	memset(buf, '\0', sizeof(buf));

	if (TESTS_COLOR) {
		fprintf(stdout, "%-*s %sFAILED%s\nLine # %d\n", line_pad, file, RED,
				NORMAL, line);
	} else {
		fprintf(stdout, "%-*s FAILED\nLine # %d\n", line_pad, file, line);
	}

	if (retval == PAPI_ESYS) {
		sprintf(buf, "System error in %s", call);
		perror(buf);
	} else if (retval > 0) {
		fprintf(stdout, "Error: %s\n", call);
	} else if (retval == 0) {
#if defined(sgi)
		fprintf( stdout, "SGI requires root permissions for this test\n" );
#else
		fprintf(stdout, "Error: %s\n", call);
#endif
	} else {
		fprintf(stdout, "Error in %s: %s\n", call, PAPI_strerror(retval));
	}

	fprintf(stdout, "\n");

	/* NOTE: Because test_fail is called from thread functions,
	 calling PAPI_shutdown here could prevent some threads
	 from being able to free memory they have allocated.
	 */

	/* This is stupid.  Threads are the rare case */
	/* and in any case an exit() should clear everything out */
	/* adding back the exit() call */

	exit(1);
}

static void init_multiplex( void ) {

	int retval;
	const PAPI_hw_info_t *hw_info;
	const PAPI_component_info_t *cmpinfo;

	/* Initialize the library */

	/* for now, assume multiplexing on CPU compnent only */
	cmpinfo = PAPI_get_component_info(0);
	if (cmpinfo == NULL) {
		test_fail(__FILE__, __LINE__, "PAPI_get_component_info", 2);
	}

	hw_info = PAPI_get_hardware_info();
	if (hw_info == NULL) {
		test_fail(__FILE__, __LINE__, "PAPI_get_hardware_info", 2);
	}

	if ((strstr(cmpinfo->name, "perfctr.c")) && (hw_info != NULL )
			&& strcmp(hw_info->model_string, "POWER6") == 0) {
		retval = PAPI_set_domain(PAPI_DOM_ALL);
		if (retval != PAPI_OK) {
			test_fail(__FILE__, __LINE__, "PAPI_set_domain", retval);
		}
	}
	retval = PAPI_multiplex_init();
	if (retval != PAPI_OK) {
		test_fail(__FILE__, __LINE__, "PAPI multiplex init fail\n", retval);
	}
}


void event_init(void) {

	int retval;

	// library initialization
	retval = PAPI_library_init( PAPI_VER_CURRENT );
	if ( retval != PAPI_VER_CURRENT )
		test_fail( __FILE__, __LINE__, "PAPI_library_init", retval );

    // multiplex initialization
	init_multiplex(  );

	// place for initialization in case one makes use of threads
	retval = PAPI_thread_init((unsigned long (*)(void))(pthread_self));
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_thread_init", retval );

    printf("event_init done \n");


}

//void event_create_eventList(int eventCodeSetSize, int *eventCodeSet, int threadID, long long *times_ini) {
void event_create_eventList(int *eventSet, int eventCodeSetSize, int *eventCodeSet, int threadID) { //done?

	int retval, i, maxNumberHwCounters, eventCodeSetMaxSize;
	PAPI_event_info_t info;

	maxNumberHwCounters = PAPI_get_opt( PAPI_MAX_HWCTRS, NULL );
	printf("Max number of hardware counters = %d \n", maxNumberHwCounters);

	eventCodeSetMaxSize = PAPI_get_opt( PAPI_MAX_MPX_CTRS, NULL );
	printf("Max number of multiplexed counters = %d \n", eventCodeSetMaxSize);

    if ( eventCodeSetMaxSize < eventCodeSetSize)
		test_fail( __FILE__, __LINE__, "eventCodeSetMaxSize < eventCodeSetSize, too many performance events defined! ", retval );

    retval = PAPI_register_thread();
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_register_thread", retval );

	// it should be more elaborated to automate the process.
/*	if (threadID == 0)
		eventSet = &eventSet0;
	else
		eventSet = &eventSet1;*/

	retval = PAPI_create_eventset( eventSet );
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_create_eventset", retval );

	retval = PAPI_assign_eventset_component( *eventSet, 0 );
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_assign_eventset_component", retval );

	retval = PAPI_set_multiplex( *eventSet );
    if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_set_multiplex", retval );

	for (i = 0; i < eventCodeSetSize; i++) {
		retval = PAPI_get_event_info(eventCodeSet[i], &info);
		if ( retval != PAPI_OK )
			test_fail( __FILE__, __LINE__, "PAPI_get_event_info", retval );

		retval = PAPI_add_event( *eventSet, info.event_code);
		if ( retval != PAPI_OK )
			test_fail( __FILE__, __LINE__, "PAPI_add_event", retval );
		else
			printf("Adding %s \n", info.symbol);

	}
    //times_ini[0] = PAPI_get_real_usec();
    //times_ini[1] = PAPI_get_real_cyc();

    printf("event_create_eventList done \n");
}

void event_start(int *eventSet, int threadID){ //done?

	int retval;
//	int *EventSet;
	// it should be more elaborated to automate the process.
/*	if (threadID == 0){
		eventSet = &eventSet0;
	}
	else{
		eventSet = &eventSet1;
	}*/

	retval = PAPI_start( *eventSet );
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_start",retval );

    //printf("event_start done \n");
}
void overflows_start(int threadID){

	int retval;
	int *EventSet;
	// it should be more elaborated to automate the process.
	if (threadID == 0){
		eventSet = &eventSet0;
		EventSet = &eventSet0;
	}
	else{
		eventSet = &eventSet1;
		EventSet = &eventSet1;
	}

	retval = PAPI_overflow( *EventSet, PAPI_TOT_INS, 1000000, 0, handler );

	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_overflow", retval );

    printf("There has been %d overflows until now\n",total);
}

void event_stop(int *eventSet, int eventCodeSetSize, long long *PMC, int threadID) { //done?

	int i, retval;
//	FILE *eventFile;

	// it should be more elaborated to automate the process.
	/*if (threadID == 0)
		eventSet = &eventSet0;
	else
		eventSet = &eventSet1;*/

	retval = PAPI_stop( *eventSet, PMC );
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_stop", retval );

/*	eventFile = fopen("event.txt","a+");
	if(!eventFile)
		printf("Cannot open the file 'event.txt'!\n");*/

/*	for(i=0; i < eventCodeSetSize; i++)
		fprintf(eventFile, "%lld \t",PMC[i]);*/

//    fclose(eventFile);

    //printf("event_stop done \n");
}

void event_destroy_eventList(int threadID, long long *times_fin) {

	int retval;

	// it should be more elaborated to automate the process.
	if (threadID == 0)
		eventSet = &eventSet0;
	else
		eventSet = &eventSet1;

	retval = PAPI_cleanup_eventset( *eventSet );
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_cleanup_eventset", retval );

    times_fin[0] = PAPI_get_real_usec();
    times_fin[1] = PAPI_get_real_cyc();

    printf("event_destroy_eventList done \n");
}

void overflows_stop(int threadID){

	int retval;

	// it should be more elaborated to automate the process.
	if (threadID == 0)
		eventSet = &eventSet0;
	else
		eventSet = &eventSet1;

	retval = PAPI_destroy_eventset( eventSet );
	if ( retval != PAPI_OK )
		test_fail( __FILE__, __LINE__, "PAPI_destroy_eventset", retval );

    //printf("El hilo 0 se ha desbordado %d veces \nEl hilo 1 se ha desbordado %d veces", total1,total2);
    //printf("There has been %d overflows at the end of the application in thread\n",total, threadID);
    printf("There has been %d overflows at the end of the thread\n",total, threadID);
}

void execute_task(int threadID){

	int i;
	int loopLength_0 = 10000;
	int loopLength_1 = 10000000;
	double x=1.0;

	if (threadID == 0){
		for(i=0; i < loopLength_0; i++ ){
			x=2.0*x;
		}
	}
	else{
		for(i=0; i < loopLength_1; i++ ){
			x=2.0*x;
		}
	}

    printf("execute_task done \n");
}