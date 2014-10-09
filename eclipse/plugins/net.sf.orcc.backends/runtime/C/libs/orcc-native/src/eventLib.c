#include "eventLib.h"
#include "papi.h"

int total = 0;

void structure_test(papi_action_s *someAction){
    int i;
    printf("Action name: %s\n", someAction->action_id);
    printf("Event Code Set:\n");
    for(i=0; i<someAction->eventCodeSetSize; i++){
        printf("\t-%d\n", someAction->eventCodeSet[i]);
    }
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

void event_create_eventList(int *eventSet, int eventCodeSetSize, int *eventCodeSet, int threadID) {

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

    printf("event_create_eventList done \n");
}

void event_start(int *eventSet, int threadID){

    int retval;

    retval = PAPI_start( *eventSet );
    if ( retval != PAPI_OK )
        test_fail( __FILE__, __LINE__, "PAPI_start",retval );

}

void event_stop(int *eventSet, int eventCodeSetSize, long long *PMC, int threadID) {

    int i, retval;

    retval = PAPI_stop( *eventSet, PMC );
    if ( retval != PAPI_OK )
        test_fail( __FILE__, __LINE__, "PAPI_stop", retval );

}
