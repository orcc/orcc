/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.oj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @author mwipliez
 * 
 */
public class InterpreterThread extends Thread {

	private static final String STARTED = "started";

	private SocketServerThread cmdSocket;

	private SocketServerThread eventSocket;

	private IScheduler scheduler;

	private boolean terminateInterpreter;

	public InterpreterThread(int cmdPort, int eventPort, IScheduler scheduler)
			throws IOException {
		cmdSocket = new SocketServerThread(cmdPort);
		eventSocket = new SocketServerThread(eventPort);
		this.scheduler = scheduler;
	}

	@Override
	public void run() {
		// at most 1 minute timeout
		int attempts = 0;
		while (!cmdSocket.isConnected() || !eventSocket.isConnected()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			attempts++;
			if (attempts > 60) {
				cmdSocket.close();
				eventSocket.close();

				System.err.println("Timeout when waiting for connections");
				System.exit(-1);
			}
		}
		writeEvent(STARTED);

		BufferedReader input = new BufferedReader(new InputStreamReader(
				cmdSocket.getInputStream()));
		try {
			while (!terminateInterpreter) {
				String command = input.readLine();
				if (command.startsWith("exit")) {

				} else if (command.startsWith("getComponents")) {
					getComponents();
				} else if (command.startsWith("resume")) {
					resume(command.substring(7));
				} else if (command.startsWith("stack")) {
					stack(command.substring(6));
				} else {
					System.out.println("ignoring received command " + command);
				}
			}
		} catch (IOException e) {
			terminate();
		}
	}

	private void resume(String actorName) {
		writeReply("ok");
	}

	private void stack(String actorName) {
		String actionName = "my_action";
		String response = "fileName|" + actorName + "|" + actionName;
		int lineNumber = 50;
		response += "|" + lineNumber;
		response += "|var_x";
		response += "|var_y";
		// response =
		// "fileName|componentName|function name|location|variable name|variable name|...|variable name"
		writeReply(response);
	}

	private void getComponents() {
		String[] actors = scheduler.getActors();
		if (actors.length > 0) {
			String response = actors[0];
			for (int i = 1; i < actors.length; i++) {
				response += "|" + actors[i];
			}
			writeReply(response);
		}
	}

	private void terminate() {
		writeEvent("terminated");
		System.out.println("Debug session terminated.");
		terminateInterpreter = true;
	}

	private void writeEvent(String event) {
		PrintStream out = eventSocket.getPrintStream();
		out.println(event);
		out.flush();
	}

	private void writeReply(String reply) {
		PrintStream out = cmdSocket.getPrintStream();
		out.println(reply);
		out.flush();
	}

}
