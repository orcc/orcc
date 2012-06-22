EPFL OpenCL Backend

Before compiling the code you should install one of the following OpenCL APIs:
	- Intel SDK for OpenCL Applications 2012 or higher
	- Nvidia CUDA Toolkit 4.2 or higher
	- AMD Accelerated Parallel Processing (APP) SDK v2.7 or higher
	
Also the following Applications/Libraries:

	- Ubuntu 12.04 LTS:
		- Eclipse 4.2 with CDT plug-in (optional)
		- CMake 2.8 or higher
		- FreeGLUT 3 (freeglut3-dev)
		- OpenGL Extension Wrangler (libglew1.6-dev)
	
	- Windows 7
		- CMake 2.8 or higher 
		- Visual Studio 
		- more to come
	
	- Mac OS X 10.6 or higher
		- more to come
	
	
Cmake should find automatically the OpenCL installation directory for you if 
this not the case then you should edit the FindOpenCL.cmake (cmake directory)
and add your installation directory in the line 73.


For compiling the generated code :
	cd build
	cmake ../
	
Or if you are going to use Eclipse CDT use only this command:
	cmake -G"Eclipse CDT4 - Unix Makefiles" .
	