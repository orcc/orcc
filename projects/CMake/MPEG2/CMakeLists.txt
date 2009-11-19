add_executable (MPEG2
parser.c
testbed.c
)

target_link_libraries(MPEG2 liborcc ${APR_LIBRARY} ${SDL_LIBRARY})
