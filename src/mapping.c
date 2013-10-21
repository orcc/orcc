#include <stdio.h>
#include <string.h>
#include <getopt.h>
#include <assert.h>
#include "orccmap.h"

void print_usage() {
    /* !TODO: Find a kind way to format this text */
    char *IDENT = "    ";

    printf("\nUsage: orccmap [options] -n nbproc -i filename");
    printf("\n");
    printf("\nDescription:");
    printf("\nGenerates a xml file containing a partition of a given Orcc Network in a given number of partitions.");
    printf("\nVarious mapping strategies can be used, all based on actors and connections weighs.");

    printf("\n");
    printf("\nRequired parameters:");
    printf("\n%s-n, --nb_processors <nbproc>", IDENT);
    printf("\n%s%sThe number of processors (partitions).", IDENT, IDENT);
    printf("\n");
    printf("\n%s-i, --input_file <filename>", IDENT);
    printf("\n%s%sThe name of the input file containing a network description.", IDENT, IDENT);

    printf("\n");
    printf("\nOptional parameters:");
    printf("\n%s-o, --output_file <filename>", IDENT);
    printf("\n%s%sThe name of the output file.", IDENT, IDENT);

    printf("\n");
    printf("\n%s-m, --mapping_strategy <strategy>", IDENT);
    printf("\n%s%sThe strategy to apply to do the mapping.", IDENT, IDENT);
    printf("\n%s%sThe possible values are: {Default : ROUND_ROBIN}", IDENT, IDENT);
    printf("\n%s%s%sROUND_ROBIN", IDENT, IDENT, IDENT);
    printf("\n%s%s%sMETIS_REC", IDENT, IDENT, IDENT);
    printf("\n%s%s%sMETIS_KWAY", IDENT, IDENT, IDENT);

    printf("\n");
    printf("\n%s-v, --verbose [level]", IDENT);
    printf("\n%s%sPrint informations.", IDENT, IDENT);
    printf("\n%s%sThe possible values are: {Default : 1}", IDENT, IDENT);
    printf("\n%s%s%s1 : summary and results", IDENT, IDENT, IDENT);
    printf("\n%s%s%s2 : debug informations", IDENT, IDENT, IDENT);

    printf("\n");
    printf("\n%s-h, --help", IDENT);
    printf("\n%s%sPrints this message.", IDENT, IDENT);
    printf("\n");
}

void set_nb_processors(char *arg_value, options_t *opt) {
    assert(arg_value != NULL);

    opt->nb_processors = atoi(arg_value);
    if (opt->nb_processors < 1) {
        print_orcc_error(ORCC_ERR_BAD_ARGS_NBPROC);
        printf("\n");
        exit(ORCC_ERR_BAD_ARGS_NBPROC);
    }
}

void set_mapping_strategy(char *arg_value, options_t *opt) {
    assert(arg_value != NULL);
    assert(opt != NULL);

    if (strcmp(arg_value, "METIS_REC") == 0) {
        opt->strategy = ORCC_MS_METIS_REC;
    } else if (strcmp(arg_value, "METIS_KWAY") == 0) {
        opt->strategy = ORCC_MS_METIS_KWAY;
    } else if (strcmp(arg_value, "ROUND_ROBIN") == 0) {
        opt->strategy = ORCC_MS_ROUND_ROBIN;
    } else {
        print_orcc_error(ORCC_ERR_BAD_ARGS_MS);
        printf("\n");
        exit(ORCC_ERR_BAD_ARGS_MS);
    }
}

void set_verbose_level(char *arg_value, options_t *opt) {
    assert(opt != NULL);
    int trace_level = 0;

    if (arg_value == NULL) {
        set_trace_level(ORCC_VL_VERBOSE_1);
    } else {
        trace_level = atoi(arg_value);
        if (trace_level < 1 || trace_level > ORCC_VL_VERBOSE_2-ORCC_VL_QUIET) {
            print_orcc_error(ORCC_ERR_BAD_ARGS_VERBOSE);
            printf("\n");
            exit(ORCC_ERR_BAD_ARGS_VERBOSE);
        }
        set_trace_level(ORCC_VL_QUIET+trace_level);
    }
}

void set_default_output_filename(char *arg_value, options_t *opt) {
    assert(arg_value != NULL);
    assert(opt != NULL);
    char *pDot, *output;

    if (arg_value != NULL) {
        output = (char *)malloc(strlen(arg_value)*sizeof(char));
        strcpy(output, arg_value);
        pDot = strrchr(output, '.');
        if(pDot != NULL)
            *pDot = '\0';
        strcat(output, ".xcf");
    } else {
        print_orcc_error(ORCC_ERR_DEF_OUTPUT);
        printf("\n");
        exit(ORCC_ERR_DEF_OUTPUT);
    }

    opt->output_file = output;
}

int main (int argc, char **argv) {
    int nFlag, iFlag = 0;
    int c;

    options_t *opt = set_default_options();

    static struct option long_options[] =
    {
        {"nb_processors", required_argument, 0, 'n'},
        {"input_file", required_argument, 0, 'i'},
        {"output_file", required_argument, 0, 'o'},
        {"mapping_strategy", required_argument, 0, 'm'},
        {"verbose", optional_argument, 0, 'v'},
        {"help", no_argument, 0, 'h'},
        {0, 0, 0, 0}
    };

    /* getopt_long stores the option index here. */
    int option_index = 0;

    while ((c = getopt_long (argc, argv, "n:i:o:m:v::h", long_options, &option_index)) != -1)
        switch (c) {
        case 'n':
            nFlag = 1;
            set_nb_processors(optarg, opt);
            break;
        case 'i':
            iFlag = 1;
            opt->input_file = optarg;
            set_default_output_filename(optarg, opt);
            break;
        case 'o':
            opt->output_file = optarg;
            break;
        case 'm':
            set_mapping_strategy(optarg, opt);
            break;
        case 'v':
            set_verbose_level(optarg, opt);
            break;
        case 'h':
            print_usage();
            exit (ORCC_OK);
            break;
        case '?':
            break;
        default:
            abort();
        }

    if (optind < argc) {
        print_orcc_error(ORCC_ERR_BAD_ARGS);
        while (optind < argc)
            fprintf(stderr," [%s]", argv[optind++]);
        printf("\n");
        print_usage();
        exit(ORCC_ERR_BAD_ARGS);
    }
    if (!nFlag || !iFlag) {
        print_orcc_error(ORCC_ERR_MANDATORY_ARGS);
        printf("\n");
        print_usage();
        exit(ORCC_ERR_MANDATORY_ARGS);
    }

    start_orcc_mapping(opt);

    exit (ORCC_OK);
}
