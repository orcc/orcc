#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include "orcc-graph.h"

int main (int argc, char **argv) {
    int c;
    int digit_optind = 0;
    int aopt = 0, bopt = 0;
    char *copt = 0, *dopt = 0;

//    static struct option long_options[] = {
//        {"add", 1, 0, 0},
//        {"append", 0, 0, 0},
//        {"delete", 1, 0, 0},
//        {"verbose", 0, 0, 0},
//        {"core", 1, 0, 'c'},
//        {NULL, 0, NULL, 0}
//    };

//    int option_index = 0;

//    while ((c = getopt_long(argc, argv, "abc:d:012", long_options, &option_index)) != -1) {
//        int this_option_optind = optind ? optind : 1;
        
//        switch (c) {
//        case 0:
//            printf ("option %s", long_options[option_index].name);
//            if (optarg)
//                printf (" with arg %s", optarg);
//            printf ("\n");
//            break;
//        case 'n':
//            printf ("option a\n");
//            aopt = 1;
//            break;
//        case 'c':
//            printf ("option c with value '%s'\n", optarg);
//            copt = optarg;
//            break;
//        case 'd':
//            printf ("option d with value '%s'\n", optarg);
//            dopt = optarg;
//            break;
//        case '?':
//            break;
//        default:
//            printf ("?? getopt returned character code 0%o ??\n", c);
//        }
//    }

//    if (optind < argc) {
//        printf ("non-option ARGV-elements: ");
//        while (optind < argc)
//            printf ("%s ", argv[optind++]);
//        printf ("\n");
//    }

    network_t *network;
    network = (network_t*) malloc(sizeof(network_t));
//    loadNetwork("/home/asanchez/tools/hevc-flat.xdf", network);
//    loadNetwork("/home/asanchez/tools/metis-example.xdf", network);
    loadNetwork("/home/asanchez/tools/metis-directed-example.xdf", network);

    options_t opt;
    opt.strategy = ORCC_LB_METIS_KWAY;
    opt.nb_processors = 4;
    mapping_t *mapping = allocate_mapping(opt.nb_processors);

    doMapping(*network, opt, mapping);

//    saveMapping("/home/asanchez/tools/partition.xml", mapping);

//    delete_mapping(mapping);
//    free(network->actors);
//    free(network->connections);
//    free(network);

    exit (0);
}
