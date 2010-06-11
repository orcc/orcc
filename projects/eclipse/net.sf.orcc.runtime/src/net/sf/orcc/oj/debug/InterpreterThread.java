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
package net.sf.orcc.oj.debug;

import static net.sf.orcc.debug.DDPConstants.ATTR_INDEXES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import net.sf.orcc.debug.DDPConstants;
import net.sf.orcc.debug.DDPServer;
import net.sf.orcc.debug.Location;
import net.sf.orcc.debug.type.AbstractType;
import net.sf.orcc.debug.type.ListType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mwipliez
 * 
 */
public class InterpreterThread extends Thread {

	private static final String STARTED = "started";

	private Map<String, IActorDebug> actors;

	private SocketServerThread cmdSocket;

	private SocketServerThread eventSocket;

	private ISchedulerDebug scheduler;

	private boolean terminateInterpreter;

	public InterpreterThread(int cmdPort, int eventPort,
			ISchedulerDebug scheduler) throws IOException {
		cmdSocket = new SocketServerThread(cmdPort);
		eventSocket = new SocketServerThread(eventPort);
		this.scheduler = scheduler;
		actors = this.scheduler.getActors();
	}

	private void getArrayValue(JSONObject request) throws JSONException {
		String actorName = request.getString(DDPConstants.ATTR_ACTOR_NAME);
		IActorDebug actor = actors.get(actorName);
		String varName = request.getString(DDPConstants.ATTR_VAR_NAME);

		JSONObject reply = new JSONObject();
		reply.put(DDPConstants.REPLY, DDPConstants.REP_GET_VALUE);
		JSONArray varArray = new JSONArray();
		reply.put(DDPConstants.ATTR_VARIABLES, varArray);

		Map<String, AbstractType> variables = actor.getVariables();
		AbstractType type = variables.get(varName);
		if (type != null && type instanceof ListType) {
			JSONArray array = request.getJSONArray(ATTR_INDEXES);
			int offset = array.getInt(0);
			int length = array.getInt(1);
			for (int i = offset; i < length; i++) {
				JSONObject obj = new JSONObject();
				// String val = actor.getValue(varName, i);
				obj.put(DDPConstants.ATTR_VAR_NAME, "[" + i + "]");
				obj.put(DDPConstants.ATTR_VAR_TYPE, DDPServer
						.getType(((ListType) type).getType()));
				varArray.put(obj);
			}
			String value = actor.getValue(varName);
			reply.put(DDPConstants.ATTR_VALUE, value);
		}

		writeReply(reply);
	}

	private void getComponents() throws JSONException {
		Set<String> set = new TreeSet<String>(actors.keySet());
		JSONObject reply = new JSONObject();
		reply.put(DDPConstants.REPLY, DDPConstants.REP_GET_COMPONENTS);
		JSONArray array = new JSONArray(set);
		reply.put(DDPConstants.ATTR_COMPONENTS, array);
		writeReply(reply);
	}

	private void getStateVariableValue(IActorDebug actor, String varName,
			JSONObject request, JSONObject reply, JSONArray varArray)
			throws JSONException {
		Map<String, AbstractType> variables = actor.getVariables();
		AbstractType type = variables.get(varName);
		if (type != null) {
			String value = actor.getValue(varName);
			reply.put(DDPConstants.ATTR_VALUE, value);
		} else {
			// TODO implement that
			reply.put(DDPConstants.ATTR_VALUE, "");
		}
	}

	private void getValue(JSONObject request) throws JSONException {
		String actorName = request.getString(DDPConstants.ATTR_ACTOR_NAME);
		IActorDebug actor = actors.get(actorName);
		String varName = request.getString(DDPConstants.ATTR_VAR_NAME);

		JSONObject reply = new JSONObject();
		reply.put(DDPConstants.REPLY, DDPConstants.REP_GET_VALUE);
		JSONArray array = new JSONArray();
		reply.put(DDPConstants.ATTR_VARIABLES, array);

		if (varName.equals("this")) {
			Map<String, AbstractType> variables = actor.getVariables();
			for (Entry<String, AbstractType> variable : variables.entrySet()) {
				JSONObject obj = new JSONObject();
				obj.put(DDPConstants.ATTR_VAR_NAME, variable.getKey());
				obj.put(DDPConstants.ATTR_VAR_TYPE, DDPServer.getType(variable
						.getValue()));
				array.put(obj);
			}
			reply.put(DDPConstants.ATTR_VALUE, "");
		} else {
			getStateVariableValue(actor, varName, request, reply, array);
		}

		writeReply(reply);
	}

	private void resume(JSONObject request) throws JSONException {
		String actorName = request.getString(DDPConstants.ATTR_ACTOR_NAME);
		actors.get(actorName).resume();

		JSONObject reply = new JSONObject();
		reply.put(DDPConstants.REPLY, DDPConstants.REP_RESUME);
		writeReply(reply);
		writeEvent("resumed " + actorName + ":client");
	}

	@Override
	public void run() {
		// wait for connection (or exit after one minute)
		waitForConnection();

		BufferedReader input = new BufferedReader(new InputStreamReader(
				cmdSocket.getInputStream()));
		try {
			while (!terminateInterpreter) {
				String line = input.readLine();
				JSONObject request = new JSONObject(line);
				System.out.println(request);

				String requestType = request.getString(DDPConstants.REQUEST);
				if (requestType.equals(DDPConstants.REQ_EXIT)) {
					terminate();
				} else if (requestType.equals(DDPConstants.REQ_GET_ARRAY_VALUE)) {
					getArrayValue(request);
				} else if (requestType.equals(DDPConstants.REQ_GET_COMPONENTS)) {
					getComponents();
				} else if (requestType.equals(DDPConstants.REQ_GET_VALUE)) {
					getValue(request);
				} else if (requestType.equals(DDPConstants.REQ_RESUME)) {
					resume(request);
				} else if (requestType.equals(DDPConstants.REQ_STACK)) {
					stack(request);
				} else if (requestType.equals(DDPConstants.REQ_SUSPEND)) {
					suspend(request);
				} else {
					System.out.println("ignoring request");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			terminate();
		}

		cmdSocket.close();
		eventSocket.close();
		System.exit(0);
	}

	private void stack(JSONObject request) throws JSONException {
		String actorName = request.getString(DDPConstants.ATTR_ACTOR_NAME);
		IActorDebug actor = actors.get(actorName);

		String fileName = actor.getFile();
		Location location;

		String actionName = actor.getNextSchedulableAction();
		if (actionName == null) {
			actionName = "<no schedulable action>";
			location = new Location();
		} else {
			location = actor.getLocation(actionName);
		}

		JSONObject frame = new JSONObject();
		frame.put(DDPConstants.ATTR_FILE, fileName);
		frame.put(DDPConstants.ATTR_ACTOR_NAME, actorName);
		frame.put(DDPConstants.ATTR_ACTION_NAME, actionName);
		frame.put(DDPConstants.ATTR_LOCATION, DDPServer.getArray(location));

		JSONArray variables = new JSONArray();
		frame.put(DDPConstants.ATTR_VARIABLES, variables);

		JSONArray frames = new JSONArray();
		frames.put(0, frame);

		JSONObject reply = new JSONObject();
		reply.put(DDPConstants.ATTR_FRAMES, frames);
		writeReply(reply);
	}

	private void suspend(JSONObject request) throws JSONException {
		String actorName = request.getString(DDPConstants.ATTR_ACTOR_NAME);
		actors.get(actorName).suspend();

		JSONObject reply = new JSONObject();
		reply.put(DDPConstants.REPLY, DDPConstants.REP_SUSPEND);
		writeReply(reply);
		writeEvent("suspended " + actorName + ":client");
	}

	private void terminate() {
		try {
			JSONObject reply = new JSONObject();
			reply.put(DDPConstants.REPLY, DDPConstants.REP_EXIT);
			writeReply(reply);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writeEvent("terminated");
		System.out.println("Debug session terminated.");
		terminateInterpreter = true;
	}

	private void waitForConnection() {
		// at most 1 minute timeout
		int attempts = 0;
		while (!cmdSocket.isConnected() || !eventSocket.isConnected()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}

			attempts++;
			if (attempts > 600) {
				cmdSocket.close();
				eventSocket.close();

				System.err.println("Timeout when waiting for connections");
				System.exit(-1);
			}
		}

		writeEvent(STARTED);
	}

	private void writeEvent(String event) {
		PrintStream out = eventSocket.getPrintStream();
		out.println(event);
		out.flush();
	}

	private void writeReply(JSONObject reply) {
		PrintStream out = cmdSocket.getPrintStream();
		out.println(reply);
		out.flush();
	}

}
