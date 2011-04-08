/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import java.lang.String;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import java.util.List;

import net.sf.orcc.moc.MoC;

/**
 * This class defines an actor. An actor has parameters, input and output ports,
 * state variables, procedures, actions and an action scheduler. The action
 * scheduler has information about the FSM if the actor has one, and the order
 * in which actions should be scheduled.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Actor extends EObject {

	/**
	 * Returns all the actions of this actor.
	 * 
	 * @return all the actions of this actor
	 * @model containment="true"
	 */
	EList<Action> getActions();

	/**
	 * Returns the action scheduler of this actor.
	 * 
	 * @return the action scheduler of this actor
	 * @model containment="true"
	 */
	ActionScheduler getActionScheduler();

	/**
	 * Returns the RVC-CAL file this actor was declared in.
	 * 
	 * @return the RVC-CAL file this actor was declared in
	 * @model dataType="org.eclipse.emf.ecore.EString"
	 */
	String getFile();

	/**
	 * Returns the list of initialize actions.
	 * 
	 * @return the list of initialize actions
	 * @model containment="true"
	 */
	EList<Action> getInitializes();

	/**
	 * Returns the input port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an input port whose name matches the given name
	 */
	Port getInput(String name);

	/**
	 * Returns the ordered map of input ports.
	 * 
	 * @return the ordered map of input ports
	 * @model containment="true"
	 */
	EList<Port> getInputs();

	/**
	 * Returns the MoC of this actor.
	 * 
	 * @return an MoC
	 */
	MoC getMoC();

	/**
	 * Returns the name of this actor.
	 * 
	 * @return the name of this actor
	 * @model dataType="org.eclipse.emf.ecore.EString"
	 */
	String getName();

	/**
	 * Returns the output port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an output port whose name matches the given name
	 */
	Port getOutput(String name);

	/**
	 * Returns the ordered map of output ports.
	 * 
	 * @return the ordered map of output ports
	 * @model containment="true"
	 */
	EList<Port> getOutputs();

	/**
	 * Returns the package of this actor.
	 * 
	 * @return the package of this actor
	 */
	String getPackage();

	/**
	 * Returns the package of this actor as a list of strings.
	 * 
	 * @return the package of this actor as a list of strings
	 */
	List<String> getPackageAsList();

	/**
	 * Returns the ordered map of parameters.
	 * 
	 * @return the ordered map of parameters
	 * @model containment="true"
	 */
	EList<Var> getParameters();

	/**
	 * Returns the port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return a port whose name matches the given name
	 */
	Port getPort(String name);

	/**
	 * Returns the ordered map of procedures.
	 * 
	 * @return the ordered map of procedures
	 * @model containment="true"
	 */
	EList<Procedure> getProcs();

	/**
	 * Returns the simple name of this actor.
	 * 
	 * @return the simple name of this actor
	 */
	String getSimpleName();

	/**
	 * Returns the state variable with the given name.
	 * 
	 * @param name
	 *            name of a state variable
	 * @return the state variable with the given name
	 */
	Var getStateVar(String name);

	/**
	 * Returns the ordered map of state variables.
	 * 
	 * @return the ordered map of state variables
	 * @model containment="true"
	 */
	EList<Var> getStateVars();

	/**
	 * Returns an object with template-specific data.
	 * 
	 * @return an object with template-specific data
	 */
	Object getTemplateData();

	/**
	 * Returns true if the actor has a Model of Computation.
	 * 
	 * @return true if actor has MoC, otherwise false.
	 */
	boolean hasMoC();

	/**
	 * Returns <code>true</code> if this actor is a <code>system</code> actor,
	 * which means that it is supposed to be replaced by a hand-written
	 * implementation. An actor is identified as "system" if it does not contain
	 * any actions.
	 * 
	 * @return <code>true</code> if this actor is a <code>system</code> actor,
	 *         <code>false</code> otherwise
	 * @model
	 */
	boolean isNative();

	/**
	 * Resets input consumption rates.
	 */
	void resetTokenConsumption();

	/**
	 * Resets output production rates.
	 */
	void resetTokenProduction();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Actor#getActionScheduler <em>Action Scheduler</em>}' containment reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Scheduler</em>' containment reference.
	 * @see #getActionScheduler()
	 * @generated
	 */
	void setActionScheduler(ActionScheduler value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Actor#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(String value);

	/**
	 * Sets the MoC of this actor.
	 * 
	 * @param moc
	 *            an MoC
	 */
	void setMoC(MoC moc);

	/**
	 * Sets the name of this actor.
	 * 
	 * @param name
	 *            the new name of this actor
	 */
	void setName(String name);

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Actor#isNative
	 * <em>Native</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Native</em>' attribute.
	 * @see #isNative()
	 * @generated
	 */
	void setNative(boolean value);

	/**
	 * Sets the template data associated with this actor. Template data should
	 * hold data that is specific to a given template.
	 * 
	 * @param templateData
	 *            an object with template-specific data
	 */
	void setTemplateData(Object templateData);

}
