/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.xlim;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.orcc.ir.IConst;
import net.sf.orcc.ir.INode;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.ActionScheduler;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.FSM;
import net.sf.orcc.ir.actor.StateVariable;
import net.sf.orcc.ir.actor.FSM.NextStateInfo;
import net.sf.orcc.ir.actor.FSM.Transition;

public class XlimActorPrinter {
	
	private Document xlim;
	private Element root;
	private static int icount=0;
	private static int ocount=0;
	private XlimNames names;

	public XlimActorPrinter(){
		names = new XlimNames();
	}
	
	private void createXlimDocument(Actor actor) {
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();
	
			//create an instance of DOM
			xlim = db.newDocument();

		}catch(ParserConfigurationException pce) {
			//dump it
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			System.exit(1);
		}
		
		xlim.setXmlVersion("1.0");
		//create the root element <design>
		root = XlimNodeTemplate.newDesign(xlim, actor.getName());
	}
	
	private void addPorts(Actor actor){
		for(Port input: actor.getInputs()){
			addPort(input,"in");
		}
		for(Port output: actor.getOutputs()){
			addPort(output,"out");
		}
	}
	
	private void addPort(Port port, String dir){
		Element newPort = XlimNodeTemplate.newActorPort(root, dir, port.getName());
		port.getType().accept(new XlimTypeSizeVisitor(newPort));
	}
	
	private void addStateVars(Actor actor){
		for(Variable stateVar : actor.getStateVars()){
			if(stateVar.isUsed()){
				
				String sourceName = stateVar.getName();
				Element newState = XlimNodeTemplate.newStateVar(root, names.getVarName(stateVar), sourceName);
				
				StateVariable state = (StateVariable)stateVar;
				//if (state.hasInit()) {
					Element init =  xlim.createElement("initValue");
					init.setAttribute("typeName", "Let");
					newState.appendChild(init);
					
					Element init2 =  xlim.createElement("initValue");
					
					stateVar.getType().accept(new XlimTypeSizeVisitor(init2));
					
					IConst value = state.getInit();
					if(value!=null){
						value.accept(new XlimValueVisitor(init2));
					}
					else{
						System.out.println("STATE: "+state.getUses());
					}
					init.appendChild(init2);
				//}
				
				System.out.println(state.getInit());
			}
		}
	}
	
	
	private void addActions(Actor actor){
		for(Action action : actor.getActions()){
			addAction(action);
		}
	}
	
	private void addAction(Action action){
		String actionName = action.toString();
		
		Element actionE = XlimNodeTemplate.newModule(root, "action", "false", actionName, null, null);
		
		for(INode nodes : action.getBody().getNodes()){
			nodes.accept(new XlimNodeVisitor(names, actionE, actionName));
		}
	}
	
	private void addScheduler(Actor actor){
		
		FSM fsm = actor.getActionScheduler().getFsm(); 
		
		if(fsm!=null){
			String start = fsm.getInitialState().getName();
			
			for(String state: fsm.getStates()){
				String fsmname = "fsm"+state;

				Element fsmState = XlimNodeTemplate.newStateVar(root, fsmname);
				
				Element init =  xlim.createElement("initValue");
				init.setAttribute("size", "1");
				init.setAttribute("typeName", "bool");
				init.setAttribute("value", start.equals(state)?"1":"0");
				fsmState.appendChild(init);
			}
		}
		
		Element schedE = XlimNodeTemplate.newModule(root, "action-scheduler", "true", "action-scheduler", "action-scheduler", null);
		
		Element operationE = XlimNodeTemplate.newValueOperation(schedE, "$literal_Integer", "1");
		XlimNodeTemplate.newOutPort(operationE, "var_sched", "1", "bool");

		Element moduleE = XlimNodeTemplate.newModule(schedE, "loop");
		Element moduledE = XlimNodeTemplate.newTestModule(moduleE, "var_loop");
		
		Element operationdE = XlimNodeTemplate.newOperation(moduledE, "noop");
		
		XlimNodeTemplate.newInPort(operationdE, "var_sched");
		XlimNodeTemplate.newOutPort(operationdE, "var_loop", "1", "bool");
		
		Element modulebE = XlimNodeTemplate.newModule(moduleE, "body");
		
		addScheduler(actor, modulebE);
	}
	
	private void addScheduler(Actor actor, Element body){
		for(Action action: actor.getActions()){
			addConditions(action, body);
		}
		if(actor.getActionScheduler().hasFsm()){
			addFSMStatusCopy(actor,body);
		}
		addScheduler(actor.getActionScheduler(), body);
	}
	
	private void addConditions(Action action, Element body){
		
		// Inputs

		Element guardE = xlim.createElement("operation");
		guardE.setAttribute("kind", "$and");
		String name = action.toString();
		
		Map<Port,Integer> input = action.getInputPattern();
		for(Entry<Port,Integer> entry : input.entrySet()){
			Element operationE = XlimNodeTemplate.newValueOperation(body, "$literal_Integer", "0");
			
			String index = "index"+(icount++);
			XlimNodeTemplate.newOutPort(operationE, index, "1", "int");
			
			Port port = entry.getKey();
			String portname = port.getName();
			
			Element peekE = XlimNodeTemplate.newPortOperation(body, "pinPeek", portname);
			
			XlimNodeTemplate.newInPort(peekE, index);
			Element peekO = XlimNodeTemplate.newOutPort(peekE, index, null, null);
			port.getType().accept(new XlimTypeSizeVisitor(peekO));
			
			Element statusE = XlimNodeTemplate.newPortOperation(body, "pinStatus", portname);
			
			String ready = "ready_"+index;
			
			XlimNodeTemplate.newOutPort(statusE, ready, "1", "bool");
			XlimNodeTemplate.newInPort(guardE, ready);
		}
		
		XlimNodeTemplate.newOutPort(guardE, "guard_"+name, "1", "bool");
		
		body.appendChild(guardE);
		
		// Outputs
		
		Element fireE = xlim.createElement("operation");
		fireE.setAttribute("kind", "$and");
		
		Map<Port,Integer> output = action.getOutputPattern();
		for(Entry<Port,Integer> entry : output.entrySet()){
			String index = "index"+(icount++);
			
			Port port = entry.getKey();
			String portname = port.getName();
			
			Element statusE = XlimNodeTemplate.newPortOperation(body, "pinStatus", portname);
			
			String status = "status_"+index;
			
			XlimNodeTemplate.newOutPort(statusE, status, "1", "bool");
			XlimNodeTemplate.newInPort(fireE, status);
		}
		
		XlimNodeTemplate.newOutPort(fireE, "fire_"+name, "1", "bool");
		
		body.appendChild(fireE);
	}
	
	private void addFSMStatusCopy(Actor actor, Element root){
		FSM fsm = actor.getActionScheduler().getFsm(); 
		
		for(String state: fsm.getStates()){
			String fsmname = "fsm"+state;
			Element operationE = XlimNodeTemplate.newOperation(root, "noop");
			XlimNodeTemplate.newInPort(operationE, fsmname);
			XlimNodeTemplate.newOutPort(operationE, fsmname+"_copy", "1", "bool");
		}
	}
	
	private void addScheduler(ActionScheduler scheduler, Element root){
		for(Action action : scheduler.getActions()){
			Element[] sched = addScheduler(action, names.putDecision(), names.putDecision(), root);
			Element ifE = sched[0];
			root = XlimNodeTemplate.newModule(ifE, "else");
		}
		if(scheduler.hasFsm()){
			addMutex(scheduler.getFsm(), root);
		}	
	}
	
	private void addMutex(FSM fsm, Element root){
		Element mutexE =  XlimNodeTemplate.newModule(root, "block");
		mutexE.setAttribute("mutex", "true");
		
		for(Transition transition: fsm.getTransitions()){
			Element checkstate = createCheckState(transition, mutexE);
			
			Iterator<NextStateInfo> it = transition.getNextStateInfo().iterator();
			NextStateInfo state = it.next();
			if(state==null){
				continue;
			}
			while(it.hasNext()){
				checkstate = addElseTransition(transition,state,checkstate);
				state = it.next();
			}
			addTransition(transition,state,checkstate);
		}
	}
	
	private Element createCheckState(Transition transition, Element mutexE){
		Element ifE =  XlimNodeTemplate.newModule(mutexE, "if");
		
		String source = transition.getSourceState().getName();
		String name = "fsm"+source;
		String decision = "decision_"+ name;
				
		Element testE =  XlimNodeTemplate.newModule(ifE, "test", null, null, null, decision);
		
		Element opE = XlimNodeTemplate.newOperation(testE, "noop");
		
		XlimNodeTemplate.newInPort(opE, name+"_copy");
		
		XlimNodeTemplate.newOutPort(opE, decision, "1", "bool");
		
		Element thenE =  XlimNodeTemplate.newModule(ifE, "then");
		
		return thenE;
	}
	
	private Element addTransition(Transition transition, NextStateInfo nextState, Element base){
		String actionname = nextState.getAction().toString();
		String initialname = "fsm"+transition.getSourceState();
		String finalname = "fsm"+nextState.getTargetState();
		String initialguard = initialname + "_guard_" + actionname;
		String finalfire = finalname + "_fire_" + actionname;
		
		String falseO = "outval"+(ocount++);
		String trueO = "outval"+(ocount++);
		
		Element[] sched = addScheduler(nextState.getAction(), initialguard, finalfire, base);
		
		Element ifE = sched[0];
		Element thenE = sched[1];
		
		if(transition.getSourceState()!=nextState.getTargetState()){
			Element ofalse =  XlimNodeTemplate.newValueOperation(thenE, "$literal_Integer", "0");
			
			XlimNodeTemplate.newOutPort(ofalse, falseO, "1", "bool");
			
			Element otrue = XlimNodeTemplate.newValueOperation(thenE, "$literal_Integer", "1");
			
			XlimNodeTemplate.newOutPort(otrue, trueO, "1", "bool");
			
			Element afalse = XlimNodeTemplate.newTargetOperation(thenE, "assign", initialname);
			
			XlimNodeTemplate.newInPort(afalse, falseO);

			Element atrue =  XlimNodeTemplate.newTargetOperation(thenE, "assign", finalname);
			
			XlimNodeTemplate.newInPort(atrue, trueO);
		}
		return ifE;
	}
	
	private Element addElseTransition(Transition transition, NextStateInfo nextState, Element base){
		Element testE = addTransition(transition,nextState,base);
		return XlimNodeTemplate.newModule(testE, "else");
	}
	
	private Element[] addScheduler(Action action, String initialguard, String finalfire, Element base){
		String actionname = action.toString();
		String guard = "guard_"+actionname;
		String fire = "fire_"+actionname;
		
		Element[] result = new Element[2];
		
		Element if1E =  XlimNodeTemplate.newModule(base, "if");
		
		Element dguardE =  XlimNodeTemplate.newTestModule(if1E, initialguard);
		
		Element oguardE = XlimNodeTemplate.newOperation(dguardE, "noop");
		
		XlimNodeTemplate.newInPort(oguardE, guard);
		
		XlimNodeTemplate.newOutPort(oguardE, initialguard, null, null);
		
		Element then1E =  XlimNodeTemplate.newModule(if1E, "then");
		
		Element if2E =  XlimNodeTemplate.newModule(then1E, "if");
		
		Element dfireE =  XlimNodeTemplate.newTestModule(if2E, finalfire);
		
		Element ofireE = XlimNodeTemplate.newOperation(dfireE, "noop");
		
		XlimNodeTemplate.newInPort(ofireE, fire);
		
		XlimNodeTemplate.newOutPort(ofireE, finalfire, null, null);
		
		Element then2E =  XlimNodeTemplate.newModule(if2E, "then");
		
		XlimNodeTemplate.newTargetOperation(then2E, "taskCall", actionname);
		
		result[0] = if1E;
		result[1] = then2E;
		
		return result;
	}
	
	private void printXlimFile(File file){
		TransformerFactory xff = TransformerFactory.newInstance();
		Transformer serializer = null;
		try {
			serializer = xff.newTransformer();
		} catch (TransformerConfigurationException te) {
			throw new RuntimeException("Could not create transformer. " + te.getMessage());
		}

		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(
				"{http://saxon.sf.net/}indent-spaces", "4");
		serializer.setOutputProperty(OutputKeys.METHOD, "xml");
		try {
			OutputStream os = new FileOutputStream(file);
			serializer.transform(new DOMSource(xlim), new StreamResult(os));
			os.close();
		} catch (Exception e) {
			throw new RuntimeException("Could not create transformer.", e);
		}
	}
	
	public void printActor(String fileName, Actor actor) throws IOException {
		createXlimDocument(actor);
		addPorts(actor);
		addStateVars(actor);
		addActions(actor);
		addScheduler(actor);
		printXlimFile(new File(fileName));		
	}
}
