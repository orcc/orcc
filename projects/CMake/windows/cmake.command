if [ ! -f "cmake.command" ] ; then
newdir=`echo $0 | sed 's/cmake.command//'`
cd $newdir
echo "hi and hello from:"pwd #just for proof of concept
fi
export CMAKE_PREFIX_PATH=$PWD/SDL-1.2.14:$PWD/SDL_image-1.2.7:$PWD/orcc
export CMAKE_INSTALL_PREFIX=$PWD/orcc
cmake-gui
