/*
 * Copyright (c) 2009, IETR/INSA Rennes
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
package net.sf.orcc.debug;

/**
 * Dataflow Debug Protocol constants.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface DDPConstants {

	/**
	 * string to indicate the "components" attribute of reply of
	 * "get components" request.
	 */
	public static final String ATTR_COMPONENTS = "components";

	/**
	 * string to indicate the "expression" attribute.
	 */
	public static final String ATTR_EXPRESSION = "expression";

	/**
	 * string to indicate the "frame name" attribute.
	 */
	public static final String ATTR_FRAME_NAME = "frame name";

	/**
	 * string to indicate the "frames" attribute.
	 */
	public static final String ATTR_FRAMES = "frames";

	/**
	 * string to indicate the "line" attribute.
	 */
	public static final String ATTR_LINE = "line";

	/**
	 * string to indicate the "name" attribute.
	 */
	public static final String ATTR_NAME = "name";

	/**
	 * string to indicate the "var name" attribute.
	 */
	public static final String ATTR_VAR_NAME = "var name";

	public static final String REP_EXIT = "exit";

	/**
	 * string to indicate the "get components" reply.
	 */
	public static final String REP_GET_COMPONENTS = "get components";

	/**
	 * string to indicate the "resume" actor reply.
	 */
	public static final String REP_RESUME = "resume";

	/**
	 * string to indicate the "suspend" actor reply.
	 */
	public static final String REP_SUSPEND = "suspend";

	/**
	 * string to indicate the reply type.
	 */
	public static final String REPLY = "reply";

	/**
	 * string to indicate the "clear" breakpoint request.
	 */
	public static final String REQ_CLEAR = "clear";

	/**
	 * string to indicate the "exit" request.
	 */
	public static final String REQ_EXIT = "exit";

	/**
	 * string to indicate the "get components" request.
	 */
	public static final String REQ_GET_COMPONENTS = "get components";

	/**
	 * string to indicate the "get variable" request.
	 */
	public static final String REQ_GET_VARIABLE = "get variable";

	/**
	 * string to indicate the "resume" actor request.
	 */
	public static final String REQ_RESUME = "resume";

	/**
	 * string to indicate the "set" breakpoint request.
	 */
	public static final String REQ_SET = "set";

	/**
	 * string to indicate the "set variable" request.
	 */
	public static final String REQ_SET_VARIABLE = "set variable";

	/**
	 * string to indicate the "stack" request.
	 */
	public static final String REQ_STACK = "stack";

	/**
	 * string to indicate the "suspend" actor request.
	 */
	public static final String REQ_SUSPEND = "suspend";

	/**
	 * string to indicate the request type.
	 */
	public static final String REQUEST = "request";

}
