
# Orcc library files
set(orcc_sources
    src/access_file.c
    src/dataflow.c
    src/options.c
    #src/source.c
    src/trace.c
    src/util.c
    src/writer.c
)

add_library(orcc STATIC ${orcc_sources})
