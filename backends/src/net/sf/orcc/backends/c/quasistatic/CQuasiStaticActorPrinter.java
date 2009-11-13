package net.sf.orcc.backends.c.quasistatic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import net.sf.orcc.backends.c.CActorPrinter;
import net.sf.orcc.backends.c.ConstPrinter;
import net.sf.orcc.backends.c.ExprToString;
import net.sf.orcc.backends.c.TypeToString;
import net.sf.orcc.backends.c.VarDefPrinter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;

import org.antlr.stringtemplate.StringTemplate;

/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Actor printer.
 * 
 * @author Victor Martin
 * 
 */
public class CQuasiStaticActorPrinter extends CActorPrinter {

	/**
	 * Creates a new network printer with the template "C.st".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CQuasiStaticActorPrinter() throws IOException {
		super("C_quasistatic_actor");

		constPrinter = new ConstPrinter(group);
		typePrinter = new TypeToString();
		varDefPrinter = new VarDefPrinter(typePrinter);
		exprPrinter = new ExprToString(varDefPrinter);
	}

	/**
	 * Prints the given actor to a file whose name is given.
	 * 
	 * @param fileName
	 *            output file name
	 * @param id
	 *            the instance id
	 * @param actor
	 *            actor to print
	 * @throws IOException
	 */
	public void printActor(String fileName, String id, Actor actor)
			throws IOException {
		if (!actor.getName().equals("display")) {
			super.printActor(fileName, id, actor);
			return;
		}
		template = group.getInstanceOf("display_actor");

		byte[] b = template.toString(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

	/**
	 * Sets attributes of the template from the given actor. Classes may extend,
	 * but should call super.setAttributes(actor) first.
	 * 
	 * @param actor
	 *            An actor
	 */
	protected void setAttributes(Actor actor) {
		String actorName = actor.getName();
		template.setAttribute("name", actorName);
		setFifos("inputs", actor.getInputs());
		setFifos("outputs", actor.getOutputs());
		setStateVars(actor.getStateVars());
		setProcedures(actorName, actor.getProcs());
		setActions("actions", actorName, actor.getActions());
		setActions("initializes", actorName, actor.getInitializes());
		template.setAttribute("scheduler", actor.getActionScheduler());
		template.setAttribute("initialize", actor.getInitializes());
		setScheduleActions("scheduleActions", actorName, actor.getActions());
	}

	/**
	 * Fills the "sche_proc" template
	 * 
	 * @param tmplName
	 * @param actorName
	 * @param actions
	 */
	protected void setScheduleActions(String tmplName, String actorName,
			List<Action> actions) {
		for (Action action : actions) {
			StringTemplate tmpl = group.getInstanceOf("sche_proc");
			// sets actor name and action
			tmpl.setAttribute("actorName", actorName);
			tmpl.setAttribute("action", action);

			template.setAttribute(tmplName, tmpl);
		}
	}

}
