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
package net.sf.orcc.backends.llvm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.PluginGroupLoader;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.StateVar;
import net.sf.orcc.ir.nodes.AbstractNode;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * Actor printer.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ActorPrinterTemplate {

	private ConstPrinter constPrinter;

	private StringTemplateGroup group;

	private StringTemplate template;

	private VarDefPrinter varDefPrinter;

	/**
	 * Creates a new network printer with the template "C.st".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public ActorPrinterTemplate() throws IOException {
		this("LLVM_actor");
	}

	/**
	 * Creates a new network printer using the given template file name.
	 * 
	 * @param name
	 *            The template file name.
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected ActorPrinterTemplate(String name) throws IOException {
		group = new PluginGroupLoader().loadGroup(name);
		constPrinter = new ConstPrinter(group);
	}

	/**
	 * Returns an instance of the "proc" template with attributes set using the
	 * given Procedure proc.
	 * 
	 * @param proc
	 *            a procedure
	 * @return a string template
	 */
	private StringTemplate applyProc(String actorName, Procedure proc) {
	
		StringTemplate procTmpl = group.getInstanceOf("proc");

		// name
		procTmpl.setAttribute("name", proc.getName());

		// return type
		TypeToString type = new TypeToString(proc.getReturnType());
		procTmpl.setAttribute("type", type.toString());

		// parameters
		for (VarDef param : proc.getParameters()) {
			StringTemplate tmpl = varDefPrinter.applyVarDef(param);
			procTmpl.setAttribute("parameters", tmpl);
		}

		// locals
		for (VarDef local : proc.getLocals()) {
			StringTemplate tmpl = varDefPrinter.applyVarDef(local);
			procTmpl.setAttribute("locals", tmpl);
		}

		// body
		NodePrinterTemplate printer = new NodePrinterTemplate(group, procTmpl,
				actorName, varDefPrinter);
		for (AbstractNode node : proc.getNodes()) {
			node.accept(printer);
		}

		return procTmpl;
	}

	private void fillPorts(List<String> portNames, List<VarDef> ports) {
		for (VarDef port : ports) {
			portNames.add(port.getName());
		}
	}

	/**
	 * Prints the given actor to a file whose name is given.
	 * 
	 * @param fileName
	 *            output file name
	 * @param actor
	 *            actor to print
	 * @throws IOException
	 */
	public void printActor(String fileName, Actor actor) throws IOException {
		template = group.getInstanceOf("actor");

		// fill port names list
		List<String> ports = new ArrayList<String>();
		fillPorts(ports, actor.getInputs());
		fillPorts(ports, actor.getOutputs());

		varDefPrinter = new VarDefPrinter(group, ports);

		setAttributes(actor);

		byte[] b = template.toString(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

	private void setActions(String tmplName, String actorName,
			List<Action> actions) {
		for (Action action : actions) {
			StringTemplate procTmpl = applyProc(actorName, action.getBody());
			template.setAttribute(tmplName, procTmpl);
			Procedure proc = action.getScheduler();
			proc.setName("isSchedulable_" + action.getTag());
			procTmpl = applyProc(actorName, proc);
			template.setAttribute(tmplName, procTmpl);
		}
	}

	private void setAttributes(Actor actor) {
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
	}

	private void setFifos(String attribute, List<VarDef> ports) {
		int size = ports.size();
		List<String> names = new ArrayList<String>(size);
		for (VarDef port : ports) {
			names.add(port.getName());
		}

		template.setAttribute(attribute, names);
	}

	private void setProcedures(String actorName, List<Procedure> procs) {
		for (Procedure proc : procs) {
			if (!proc.isExternal()) {
				template.setAttribute("procs", applyProc(actorName, proc));
			}
		}
	}

	private void setStateVars(List<StateVar> stateVars) {
		for (StateVar stateVar : stateVars) {
			StringTemplate stateTempl = group.getInstanceOf("stateVar");
			template.setAttribute("stateVars", stateTempl);

			StringTemplate tmpl = varDefPrinter.applyVarDef(stateVar.getDef());
			stateTempl.setAttribute("vardef", tmpl);

			// initial value of state var (if any)
			if (stateVar.hasInit()) {
				constPrinter.setTemplate(stateTempl);
				stateVar.getInit().accept(constPrinter);
			}
		}
	}

}
