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
package net.sf.orcc.backends.java.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.backends.c.CActorPrinter;
import net.sf.orcc.backends.c.VarDefPrinter;
import net.sf.orcc.backends.java.JavaConstPrinter;
import net.sf.orcc.backends.java.JavaExprPrinter;
import net.sf.orcc.backends.java.JavaTypePrinter;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;

/**
 * Actor printer.
 * 
 * @author Mathieu Wippliez
 * 
 */
public class JavaDebugActorPrinter extends CActorPrinter {

	/**
	 * Creates a new network printer with the template "Java_actor.stg".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public JavaDebugActorPrinter() throws IOException {
		super("Java_actor_debug");
		constPrinter = new JavaConstPrinter(group);
		typePrinter = new JavaTypePrinter();
		varDefPrinter = new VarDefPrinter(typePrinter);
		exprPrinter = new JavaExprPrinter(varDefPrinter);
	}

	@Override
	protected void setAttributes(Actor actor) {
		super.setAttributes(actor);
		String res = actor.getFile().replaceAll("\\\\", "\\\\\\\\");
		template.setAttribute("file", res);

		List<Action> actions = actor.getActions();
		Map<String, List<Integer>> locationMap = new TreeMap<String, List<Integer>>();
		for (Action action : actions) {
			Location location = action.getBody().getLocation();
			List<Integer> list = new ArrayList<Integer>();
			list.add(location.getStartLine());
			list.add(location.getStartColumn());
			list.add(location.getEndColumn());
			locationMap.put(action.getTag(), list);
		}
		
		template.setAttribute("actionLoc", locationMap);
	}

}
