add_executable (MPEG2
parser.c
testbed.c
)

target_link_libraries(MPEG2 liborcc ${SDL_LIBRARY})
