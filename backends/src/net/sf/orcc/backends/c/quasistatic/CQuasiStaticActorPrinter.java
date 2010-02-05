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

package net.sf.orcc.backends.c.quasistatic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import net.sf.orcc.backends.TemplateGroupLoader;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Printer;
import net.sf.orcc.ir.Type;
import net.sf.orcc.util.INameable;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * Actor printer.
 * 
 * @author Victor Martin
 * 
 */
public class CQuasiStaticActorPrinter extends Printer {

	private StringTemplateGroup extensionGroup;

	private StringTemplate extensionTemplate;

	private StringTemplateGroup group;

	/**
	 * Creates a new network printer with the template "C.st".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CQuasiStaticActorPrinter() {
		group = new TemplateGroupLoader().loadGroup("C_actor");
		extensionGroup = new TemplateGroupLoader()
				.loadGroup("C_quasistatic_actor");
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
		OutputStream os = new FileOutputStream(fileName);

		StringTemplate template;
		if (actor.getName().equals("display")) {
			template = extensionGroup.getInstanceOf("display_actor");
			byte[] b = template.toString(80).getBytes();
			os.write(b);
		} else {
			template = group.getInstanceOf("actor");
			extensionTemplate = extensionGroup
					.getInstanceOf("schedule_methods");
			setAttributes(id, actor);
			byte[] b = template.toString(80).getBytes();
			byte[] b1 = extensionTemplate.toString(80).getBytes();
			os.write(b);
			os.write(b1);
		}
		os.close();
	}

	/**
	 * Sets attributes of the template from the given actor. Classes may extend,
	 * but should call super.setAttributes(actor) first.
	 * 
	 * @param actor
	 *            An actor
	 */
	protected void setAttributes(String id, Actor actor) {
		// TODO: super.setAttributes(id,actor);
		setScheduleActions("scheduleActions", id, actor.getActions());
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
			StringTemplate tmpl = extensionGroup.getInstanceOf("sche_proc");
			// sets actor name and action
			tmpl.setAttribute("actorName", actorName);
			tmpl.setAttribute("action", action);
			extensionTemplate.setAttribute(tmplName, tmpl);
		}
	}

	@Override
	public String toString(Constant constant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Expression expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(INameable nameable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Type type) {
		// TODO Auto-generated method stub
		return null;
	}

}
