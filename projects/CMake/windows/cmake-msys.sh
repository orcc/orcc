#!/bin/sh
export CMAKE_PREFIX_PATH=$PWD/SDL-1.2.13:$PWD/SDL_image-1.2.7
sh -c "cmake-gui"
