/*
 * Copyright (c) 2012, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
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
package net.sf.orcc.simulators;

import static net.sf.orcc.OrccLaunchConstants.INPUT_STIMULUS;
import static net.sf.orcc.OrccLaunchConstants.LOOP_NUMBER;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.SIMULATOR;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.util.OrccLogger;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * Command-line version of CAL simulator.
 * 
 * @author Antoine Lorence
 * 
 */
public class SimulatorCli implements IApplication {

	private boolean run = true;

	@Override
	public Object start(IApplicationContext context) throws Exception {

		Map<String, Object> options = new HashMap<String, Object>();
		
		Object value;
		if((value = context.getArguments().get("-p")) != null) {
			OrccLogger.traceln("Project : " + value);
			options.put(PROJECT, value);
		} else {
			OrccLogger
					.severeln("You must specify project with -p command line argument");
			run = false;
		}
		if((value = context.getArguments().get("-xdf")) != null) {
			OrccLogger.traceln("Xdf : " + value);
			options.put(XDF_FILE, value);
		} else {
			OrccLogger
					.severeln("You must specify a top network with -xdf command line argument");
			run = false;
		}
		if((value = context.getArguments().get("-l")) != null) {
			OrccLogger.traceln("Loops number : " + value);
			options.put(LOOP_NUMBER, value);
		}
		if((value = context.getArguments().get("-i")) != null) {
			OrccLogger.traceln("Input : " + value);
			options.put(INPUT_STIMULUS, value);
		} else {
			OrccLogger
					.severeln("You must specify a input stimulus with -i command line argument");
			run = false;
		}
		
		options.put(SIMULATOR, "Visitor interpreter and debugger");

		if (run) {
			SimulatorFactory.getInstance().runSimulator(
					new NullProgressMonitor(), "run", options);
			return IApplication.EXIT_OK;
		} else {
			return IApplication.EXIT_RELAUNCH;
		}

	}

	@Override
	public void stop() {
		
	}
}
