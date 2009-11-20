add_executable (Seam_carving
rgb2y.c
sobel.c
Top_sobel.c
y2rgb.c
)

target_link_libraries(Seam_carving liborcc ${SDL_LIBRARY} ${SDLIMAGE_LIBRARY})
