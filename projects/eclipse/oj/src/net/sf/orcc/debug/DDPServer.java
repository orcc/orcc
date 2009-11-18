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

import net.sf.orcc.debug.type.AbstractType;
import net.sf.orcc.debug.type.BoolType;
import net.sf.orcc.debug.type.IntType;
import net.sf.orcc.debug.type.ListType;
import net.sf.orcc.debug.type.StringType;
import net.sf.orcc.debug.type.UintType;
import net.sf.orcc.debug.type.VoidType;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Implementation of a Dataflow Debug Protocol server.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DDPServer {

	/**
	 * Returns a JSON array from a Location.
	 * 
	 * @param location
	 *            a location
	 * @return a JSON array
	 * @throws JSONException
	 */
	public static JSONArray getArray(Location location) throws JSONException {
		JSONArray array = new JSONArray();
		array.put(0, location.getLineNumber());
		array.put(1, location.getCharStart());
		array.put(2, location.getCharEnd());
		return array;
	}

	/**
	 * Returns an object from the given abstract type.
	 * 
	 * @param type
	 *            an abstract type
	 * @return an object
	 * @throws JSONException
	 */
	public static Object getType(AbstractType type) throws JSONException {
		if (type instanceof BoolType) {
			return BoolType.NAME;
		} else if (type instanceof StringType) {
			return StringType.NAME;
		} else if (type instanceof VoidType) {
			return VoidType.NAME;
		} else {
			JSONArray array = new JSONArray();
			if (type instanceof IntType) {
				array.put(IntType.NAME);
				array.put(((IntType) type).getSize());
			} else if (type instanceof UintType) {
				array.put(UintType.NAME);
				array.put(((UintType) type).getSize());
			} else if (type instanceof ListType) {
				array.put(ListType.NAME);
				array.put(((ListType) type).getSize());
				array.put(getType(((ListType) type).getType()));
			} else {
				throw new JSONException("Invalid type definition: "
						+ type.toString());
			}

			return array;
		}
	}

}
