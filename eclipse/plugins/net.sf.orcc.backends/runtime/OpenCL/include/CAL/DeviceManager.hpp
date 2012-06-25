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

#ifndef __DEVICE_MANAGER_HPP__
#define __DEVICE_MANAGER_HPP__

#include <iostream>
#include <fstream>
#include <sstream>

#include "CL/cl.hpp"

#define MAX_PLATFORMS 256

/* !
 *  \class DeviceManager
 *  \author Endri Bezati
 *
 *  \brief This class manages the OpenCL capable device of the host
 */
class DeviceManager {
public:
	DeviceManager();
	DeviceManager(cl_device_type);

	~DeviceManager();

	// Find OpenCL capable devices on the host
	bool findPlatforms();

	// Choosing the OpenCL platform and creating a context
	cl_context createContext(cl_device_type device_type, cl_platform_id &platforms);

	// Choosing an available device and Creating the command queue
	cl_command_queue createCommandQueue(cl_context context,
			cl_device_id *device);

	// Create an OpenCL Program
	cl_program createProgram(cl_context context, cl_device_id device,
			const char* fileName);
private:
	cl_platform_id platforms[MAX_PLATFORMS];
	cl_uint numPlatforms;
	cl_device_id devices;
	cl_device_type deviceType;
	cl_context context;
	cl_command_queue commandQueue;
};
#endif // __DEVICE_MANAGER_HPP__
