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
package net.sf.orcc.runtime.debug;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.debug.Location;
import net.sf.orcc.debug.type.AbstractType;

public abstract class AbstractActorDebug implements IActorDebug {

	protected Map<String, Location> actionLocation;

	protected String file;

	protected boolean suspended;

	protected Map<String, AbstractType> variables;

	protected AbstractActorDebug(String file) {
		this.file = file;

		actionLocation = new HashMap<String, Location>();
		variables = new HashMap<String, AbstractType>();
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	@Override
	public String getValue(String variable) {
		try {
			Field field = getClass().getField(variable);
			Object obj = field.get(this);
			if (obj == null) {
				return "null";
			} else if (obj.getClass().isArray()) {
				StringBuilder builder = new StringBuilder();
				int length = Array.getLength(obj);
				if (length > 0) {
					Object entry = Array.get(obj, 0);
					builder.append('[');
					builder.append(entry.toString());
					int maxLength = Math.min(length, 100);
					for (int i = 1; i < maxLength; i++) {
						builder.append(", ");
						entry = Array.get(obj, i);
						builder.append(entry.toString());
					}
				}

				if (length > 100) {
					builder.append(", ...");
				}
				builder.append(']');

				return builder.toString();
			} else {
				return obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "could not get value of \"" + variable + "\"";
	}

	@Override
	public String getValue(String variable, int index) {
		try {
			Field field = getClass().getField(variable);
			Object obj = field.get(this);
			if (obj == null) {
				return "null";
			} else if (obj.getClass().isArray()) {
				return Array.get(obj, index).toString();
			} else {
				return "\"" + variable + "\" is not an array!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "could not get value of \"" + variable + "\"";
	}

	@Override
	public Map<String, AbstractType> getVariables() {
		return variables;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void resume() {
		suspended = false;
	}

	@Override
	public void suspend() {
		suspended = true;
	}

}
