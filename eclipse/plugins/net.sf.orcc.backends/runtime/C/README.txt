You have to use CMake to compile this generated application.
You can find it at http://cmake.org/

If you use cmake-gui to generate project, please select these folders :
- "Where is the source code" : <this folder>
- "Where to build the binaries" : <this folder>/build

If you use cmake in command line, you must execute the following command
from the 'build' folder :

cmake ..


*** Note, Eclipse users ***

Eclipse projects (.project & .cproject files) must be in the root folder
of a project. If you want to generate an eclipse project, please select
<this folder> as "source code" AND "build location". In command-line,
simply run the following command from <this folder> :

cmake -G"Eclipse CDT4 - Unix Makefiles" .
