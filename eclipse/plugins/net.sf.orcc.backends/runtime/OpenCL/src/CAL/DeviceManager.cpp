/*
 * Copyright (c) 2011, Ecole Polytechnique Fédérale de Lausanne
 * Author : Endri Bezati <endri.bezati@epfl.ch>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names
 *     of its contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#include "CAL/DeviceManager.hpp"

DeviceManager::DeviceManager() {
	findPlatforms();
}

DeviceManager::~DeviceManager() {
	if (context != 0)
	        clReleaseContext(context);
}

DeviceManager::DeviceManager(cl_device_type deviceType) :
		deviceType(deviceType) {
	if (findPlatforms()){
		context = createContext(deviceType,*platforms);
		commandQueue = createCommandQueue();
	}

}

cl_context DeviceManager::createContext(cl_device_type deviceType, cl_platform_id  &platforms) {
	cl_int errNum;

	// Create a Context depending on the chosen device type
	if (deviceType == CL_DEVICE_TYPE_GPU) {
		cl_context_properties contextProperties[] = { CL_CONTEXT_PLATFORM,
				(cl_context_properties) platforms, 0 };
		context = clCreateContextFromType(contextProperties, CL_DEVICE_TYPE_GPU,
				NULL, NULL, &errNum);

		if (errNum != CL_SUCCESS) {
			std::cout << "Could not create GPU context, trying CPU..."
					<< std::endl;
			context = clCreateContextFromType(contextProperties,
					CL_DEVICE_TYPE_CPU, NULL, NULL, &errNum);
			if (errNum != CL_SUCCESS) {
				std::cerr << "Failed to create an OpenCL GPU or CPU context."
						<< std::endl;
				return NULL;
			}
		}
	} else if (deviceType == CL_DEVICE_TYPE_CPU) {
		cl_context_properties contextProperties[] = { CL_CONTEXT_PLATFORM,
				(cl_context_properties) platforms, 0 };
		context = clCreateContextFromType(contextProperties, CL_DEVICE_TYPE_CPU,
				NULL, NULL, &errNum);

		if (errNum != CL_SUCCESS) {
			std::cout << "Could not create CPU context, trying GPU..."
					<< std::endl;
			context = clCreateContextFromType(contextProperties,
					CL_DEVICE_TYPE_GPU, NULL, NULL, &errNum);
			if (errNum != CL_SUCCESS) {
				std::cerr << "Failed to create an OpenCL CPU or GPU context."
						<< std::endl;
				return NULL;
			}
		}
	} else {
		std::cerr << "Error: Device Type not supported !!!." << std::endl;
		return NULL;
	}

	return context;
}

cl_command_queue DeviceManager::createCommandQueue(){
	return createCommandQueue(context,devices);
}

cl_command_queue DeviceManager::createCommandQueue(cl_context context,
		cl_device_id *device) {
	cl_int errNum;
	cl_device_id *devices;
	cl_command_queue commandQueue = NULL;
	size_t deviceBufferSize = -1;

	// First get the size of the devices buffer
	errNum = clGetContextInfo(context, CL_CONTEXT_DEVICES, 0, NULL,
			&deviceBufferSize);
	if (errNum != CL_SUCCESS) {
		std::cerr
				<< "Failed call to clGetContextInfo(...,GL_CONTEXT_DEVICES,...)";
		return NULL;
	}

	if (deviceBufferSize <= 0) {
		std::cerr << "No devices available.";
		return NULL;
	}

	// Allocate memory for the devices buffer
	devices = new cl_device_id[deviceBufferSize / sizeof(cl_device_id)];
	errNum = clGetContextInfo(context, CL_CONTEXT_DEVICES, deviceBufferSize,
			devices, NULL);
	if (errNum != CL_SUCCESS) {
		delete[] devices;
		std::cerr << "Failed to get device IDs";
		return NULL;
	}

	commandQueue = clCreateCommandQueue(context, devices[0], 0, NULL);
	if (commandQueue == NULL) {
		delete[] devices;
		std::cerr << "Failed to create commandQueue for device 0";
		return NULL;
	}

	*device = devices[0];
	delete[] devices;
	return commandQueue;
}

cl_program DeviceManager::createProgram(const char* fileName){
	return createProgram(context,devices[0],fileName);
}

cl_program DeviceManager::createProgram(cl_context context, cl_device_id device,
		const char* fileName) {
	cl_int errNum;
	cl_program program;

	std::ifstream kernelFile(fileName, std::ios::in);
	if (!kernelFile.is_open()) {
		std::cerr << "Failed to open file for reading: " << fileName
				<< std::endl;
		return NULL;
	}

	std::ostringstream oss;
	oss << kernelFile.rdbuf();

	std::string srcStdStr = oss.str();
	const char *srcStr = srcStdStr.c_str();
	program = clCreateProgramWithSource(context, 1, (const char**) &srcStr,
			NULL, NULL);
	if (program == NULL) {
		std::cerr << "Failed to create CL program from source." << std::endl;
		return NULL;
	}

	errNum = clBuildProgram(program, 0, NULL, NULL, NULL, NULL);
	if (errNum != CL_SUCCESS) {
		// Determine the reason for the error
		char buildLog[16384];
		clGetProgramBuildInfo(program, device, CL_PROGRAM_BUILD_LOG,
				sizeof(buildLog), buildLog, NULL);

		std::cerr << "Error in kernel: " << std::endl;
		std::cerr << buildLog;
		clReleaseProgram(program);
		return NULL;
	}

	return program;
}

bool DeviceManager::findDevices(){
	cl_int errNum;
	// Use always the Default Platform
	errNum = clGetDeviceIDs(platforms[0],CL_DEVICE_TYPE_ALL,MAX_DEVICES,devices,&numDevices);
	if (errNum != CL_SUCCESS) {
			std::cerr << "Error: Failed to get host Device IDs" << std::endl;
			return false;
	}

}

bool DeviceManager::findPlatforms() {
	cl_int errNum;

	errNum = clGetPlatformIDs(MAX_PLATFORMS, platforms, &numPlatforms);
	if (errNum != CL_SUCCESS) {
		std::cerr << "Error: Failed to get host Platforms IDs" << std::endl;
		return false;
	}

	for (int i = 0; i < numPlatforms; i++) {
		char buffer[256];
		std::cout << "Platform: " << numPlatforms << std::endl;
		//Get Platform Vendor
		errNum = clGetPlatformInfo(platforms[i], CL_PLATFORM_VENDOR,
				sizeof(buffer), buffer, NULL);
		if (errNum) {
			std::cerr << "Error: Failed to get the platform vendor"
					<< std::endl;
			return false;
		}
		std::cout << "\tVendor: " << buffer << std::endl;

		// Get Platform Name
		errNum = clGetPlatformInfo(platforms[i], CL_PLATFORM_NAME,
				sizeof(buffer), buffer, NULL);
		if (errNum != CL_SUCCESS) {
			std::cerr << "Error: Failed to get the platform name" << std::endl;
			return false;
		}
		std::cout << "\tName: " << buffer << std::endl;

		//Get Platform Version
		errNum = clGetPlatformInfo(platforms[i], CL_PLATFORM_VERSION,
				sizeof(buffer), buffer, NULL);
		if (errNum != CL_SUCCESS) {
			std::cerr << "Error: Failed to get the platform version"
					<< std::endl;
			return false;
		}
		std::cout << "\tVersion: " << buffer << std::endl;

		//Get Platform Profile
		errNum = clGetPlatformInfo(platforms[i], CL_PLATFORM_PROFILE,
				sizeof(buffer), buffer, NULL);
		if (errNum != CL_SUCCESS) {
			std::cerr << "Error: Failed to get the platform profile"
					<< std::endl;
			return false;
		}
		std::cout << "\tProfile: " << buffer << std::endl;
	}

	return true;
}
