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
package net.sf.orcc.backends.c;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.backends.TemplateGroupLoader;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Constant;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Printer;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.AbstractFifo;
import net.sf.orcc.util.OrderedMap;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * This class defines a C actor printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CActorPrinter extends Printer {

	protected StringTemplateGroup group;

	private ListSizePrinter listSizePrinter;

	private boolean printVariableInit;

	private boolean printVariableType;

	protected StringTemplate template;

	private Map<String, String> transformations;

	/**
	 * Creates a new network printer with the template "C.st".
	 * 
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	public CActorPrinter() throws IOException {
		this("C_actor");

		listSizePrinter = new ListSizePrinter();

		transformations = new HashMap<String, String>();
		transformations.put("abs", "abs_");
		transformations.put("index", "index_");
		transformations.put("getw", "getw_");
		transformations.put("select", "select_");
	}

	/**
	 * Creates a new network printer using the given template file name.
	 * 
	 * @param name
	 *            The template file name.
	 * @throws IOException
	 *             If the template file could not be read.
	 */
	protected CActorPrinter(String name) throws IOException {
		group = new TemplateGroupLoader().loadGroup(name);

		// registers this printer as the default printer
		Printer.register(this);
	}

	/**
	 * Returns an instance of the "proc" template with attributes set using the
	 * given Procedure proc.
	 * 
	 * @param proc
	 *            a procedure
	 * @return a string template
	 */
	protected StringTemplate applyProc(String id, Procedure proc) {
		StringTemplate procTmpl = group.getInstanceOf("proc");

		// name
		procTmpl.setAttribute("name", proc.getName());

		// return type
		Type type = proc.getReturnType();
		procTmpl.setAttribute("type", type.toString());

		// parameters
		for (Variable param : proc.getParameters()) {
			printVariableType = true;
			printVariableInit = false;
			procTmpl.setAttribute("parameters", param.toString());
		}

		// locals
		for (Variable local : proc.getLocals()) {
			printVariableType = true;
			printVariableInit = true;
			procTmpl.setAttribute("locals", local.toString());
		}

		// body
		NodePrinterTemplate printer = new NodePrinterTemplate(group, procTmpl,
				id);
		for (CFGNode node : proc.getNodes()) {
			node.accept(printer);
		}

		return procTmpl;
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
		template = group.getInstanceOf("actor");

		setAttributes(id, actor);

		byte[] b = template.toString(80).getBytes();
		OutputStream os = new FileOutputStream(fileName);
		os.write(b);
		os.close();
	}

	protected void setActions(String tmplName, String id, List<Action> actions) {
		for (Action action : actions) {
			StringTemplate procTmpl = applyProc(id, action.getBody());
			template.setAttribute(tmplName, procTmpl);
			Procedure proc = action.getScheduler();
			proc.setName("isSchedulable_" + action);
			procTmpl = applyProc(id, proc);
			template.setAttribute(tmplName, procTmpl);
		}
	}

	/**
	 * Sets attributes of the template from the given actor. Classes may extend,
	 * but should call super.setAttributes(actor) first.
	 * 
	 * @param actor
	 *            An actor
	 */
	protected void setAttributes(String id, Actor actor) {
		template.setAttribute("name", id);
		setFifos("inputs", actor.getInputs());
		setFifos("outputs", actor.getOutputs());
		setStateVars(actor.getStateVars());
		setProcedures(id, actor.getProcs());
		setActions("actions", id, actor.getActions());
		setActions("initializes", id, actor.getInitializes());
		template.setAttribute("scheduler", actor.getActionScheduler());
		template.setAttribute("initialize", actor.getInitializes());
	}

	/**
	 * visibility has been changed to <code>protected</code> in order to be
	 * visible to <code>CQuasiStaticActorPrinter</code>
	 * 
	 * @param attribute
	 * @param ports
	 */
	protected void setFifos(String attribute, OrderedMap<Port> ports) {
		int size = ports.size();
		List<String> names = new ArrayList<String>(size);
		for (Port port : ports) {
			names.add(port.getName());
		}

		template.setAttribute(attribute, names);
	}

	/**
	 * visibility has been changed to <code>protected</code> in order to be
	 * visible to <code>CQuasiStaticActorPrinter</code>
	 * 
	 * @param id
	 * @param procs
	 */
	protected void setProcedures(String id, OrderedMap<Procedure> procs) {
		for (Procedure proc : procs) {
			if (!proc.isExternal()) {
				template.setAttribute("procs", applyProc(id, proc));
			}
		}
	}

	/**
	 * 
	 * visibility has been changed to <code>protected</code> in order to be
	 * visible to <code>CQuasiStaticActorPrinter</code>
	 * 
	 * @param stateVars
	 */
	protected void setStateVars(OrderedMap<Variable> stateVars) {
		for (Variable variable : stateVars) {
			StateVariable stateVar = (StateVariable) variable;

			StringTemplate stateTempl = group.getInstanceOf("stateVar");
			template.setAttribute("stateVars", stateTempl);

			printVariableType = true;
			printVariableInit = true;
			stateTempl.setAttribute("vardef", stateVar.toString());

			// initial value of state var (if any)
			if (stateVar.hasInit()) {
				stateTempl.setAttribute("value", stateVar.getInit().toString());
			}
		}
	}

	@Override
	public String toString(CFGNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Constant constant) {
		CConstPrinter printer = new CConstPrinter(group);
		constant.accept(printer);
		return printer.toString();
	}

	@Override
	public String toString(Expression expression) {
		CExpressionPrinter printer = new CExpressionPrinter();
		expression.accept(printer, Integer.MAX_VALUE);
		return printer.toString();
	}

	@Override
	public String toString(Instruction instruction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Procedure procedure) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Type type) {
		CTypePrinter printer = new CTypePrinter();
		type.accept(printer);
		return printer.toString();
	}

	@Override
	public String toString(Variable variable) {
		if (printVariableType) {
			StringTemplate template;
			if (printVariableInit) {
				template = group.getInstanceOf("vardefInit");
			} else {
				template = group.getInstanceOf("vardef");
			}

			printVariableType = false;
			printVariableInit = false;

			return toString(variable, template);
		} else {
			String name = variable.getName();
			if (transformations.containsKey(name)) {
				return transformations.get(name);
			} else {
				return name;
			}
		}
	}

	/**
	 * Returns a string representation of variable with the given template.
	 * 
	 * @param variable
	 *            a variable definition
	 * @param template
	 *            a template
	 * @return a string
	 */
	public String toString(Variable variable, StringTemplate template) {
		template.setAttribute("name", variable.toString());
		template.setAttribute("type", variable.getType().toString());

		// if varDef is a list, => list of dimensions
		variable.getType().accept(listSizePrinter);

		template.setAttribute("size", listSizePrinter.getSize());
		boolean isPort = false;
		for (Use use : variable.getUses()) {
			if (use.getNode() instanceof AbstractFifo) {
				AbstractFifo fifoNode = (AbstractFifo) use.getNode();
				if (variable.getName().startsWith(fifoNode.getPort().getName())) {
					isPort = true;
					break;
				}
			}
		}
		template.setAttribute("isPort", isPort);

		return template.toString();
	}

}
