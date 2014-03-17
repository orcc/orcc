
# Orcc library files
set(orcc_sources
    src/access_file.c
    src/dataflow.c
    src/fifos.c
    src/options.c
    src/source.c
    src/trace.c
    src/util.c
    src/writer.c
    include/util.h
    include/types.h
)

add_library(orcc STATIC ${orcc_sources})
