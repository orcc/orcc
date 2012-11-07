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
package net.sf.orcc.df;

import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.MoC;

import net.sf.orcc.util.Adaptable;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> This class defines an actor. An actor has parameters,
 * input and output ports, state variables, procedures, actions and an FSM.<!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.df.Actor#getActions <em>Actions</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getActionsOutsideFsm <em>Actions Outside Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getFileName <em>File Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getFsm <em>Fsm</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getInitializes <em>Initializes</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getInputs <em>Inputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getMoC <em>Mo C</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#isNative <em>Native</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getProcs <em>Procs</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getStateVars <em>State Vars</em>}</li>
 *   <li>{@link net.sf.orcc.df.Actor#getTemplateData <em>Template Data</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.df.DfPackage#getActor()
 * @model
 * @generated
 */
public interface Actor extends Vertex, Adaptable {

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Action}.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_Actions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Action> getActions();

	/**
	 * Returns the value of the '<em><b>Actions Outside Fsm</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Action}.
	 * <!-- begin-user-doc -->Returns the actions that are outside of an FSM. If
	 * this actor has no FSM, all actions of the actor are returned. The actions
	 * are sorted by decreasing priority.<!-- end-user-doc -->
	 * @return the value of the '<em>Actions Outside Fsm</em>' reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_ActionsOutsideFsm()
	 * @model
	 * @generated
	 */
	EList<Action> getActionsOutsideFsm();

	/**
	 * Returns the file this actor is defined in.
	 * 
	 * @return the file this actor is defined in
	 */
	IFile getFile();

	/**
	 * Returns the value of the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->Returns the name of the file this actor is defined
	 * in.<!-- end-user-doc -->
	 * @return the value of the '<em>File Name</em>' attribute.
	 * @see #setFileName(String)
	 * @see net.sf.orcc.df.DfPackage#getActor_FileName()
	 * @model
	 * @generated
	 */
	String getFileName();

	/**
	 * Returns the value of the '<em><b>Fsm</b></em>' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Fsm</em>' containment reference.
	 * @see #setFsm(FSM)
	 * @see net.sf.orcc.df.DfPackage#getActor_Fsm()
	 * @model containment="true"
	 * @generated
	 */
	FSM getFsm();

	/**
	 * Returns the value of the '<em><b>Initializes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Action}.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Initializes</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_Initializes()
	 * @model containment="true"
	 * @generated
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
	 * Returns the value of the '<em><b>Inputs</b></em>' containment reference
	 * list. The list contents are of type {@link net.sf.orcc.df.Port}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inputs</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Inputs</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_Inputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Port> getInputs();

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see net.sf.orcc.df.DfPackage#getActor_LineNumber()
	 * @model
	 * @generated
	 */
	int getLineNumber();

	/**
	 * Returns the value of the '<em><b>Mo C</b></em>' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Mo C</em>' containment reference.
	 * @see #setMoC(MoC)
	 * @see net.sf.orcc.df.DfPackage#getActor_MoC()
	 * @model containment="true"
	 * @generated
	 */
	MoC getMoC();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.df.DfPackage#getActor_Name()
	 * @model default="" transient="true" volatile="true" derived="true"
	 * @generated
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
	 * Returns the value of the '<em><b>Outputs</b></em>' containment reference
	 * list. The list contents are of type {@link net.sf.orcc.df.Port}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputs</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Outputs</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_Outputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Port> getOutputs();

	String getPackage();

	/**
	 * Returns the parameter with the given name.
	 * 
	 * @param name
	 *            name of a parameter
	 * @return the parameter with the given name
	 */
	Var getParameter(String name);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Var}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_Parameters()
	 * @model containment="true"
	 * @generated
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
	 * Returns a procedure of this actor whose name matches the given name.
	 * 
	 * @param name
	 *            the procedure name
	 * @return a procedure whose name matches the given name
	 */
	Procedure getProcedure(String name);

	/**
	 * Returns the value of the '<em><b>Procs</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Procedure}.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Procs</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_Procs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Procedure> getProcs();

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
	 * Returns the value of the '<em><b>State Vars</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Var}.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>State Vars</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getActor_StateVars()
	 * @model containment="true"
	 * @generated
	 */
	EList<Var> getStateVars();

	/**
	 * Returns the value of the '<em><b>Template Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Data</em>' attribute.
	 * @see #setTemplateData(Object)
	 * @see net.sf.orcc.df.DfPackage#getActor_TemplateData()
	 * @model transient="true"
	 * @generated
	 */
	Object getTemplateData();

	/**
	 * Returns true if this actor has an FSM.
	 * 
	 * @return true if this actor has an FSM
	 */
	boolean hasFsm();

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
	 * Sets the value of the '{@link net.sf.orcc.df.Actor#getFileName <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	void setFileName(String value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Actor#getFsm <em>Fsm</em>}' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Fsm</em>' containment reference.
	 * @see #getFsm()
	 * @generated
	 */
	void setFsm(FSM value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Actor#getLineNumber <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 * @generated
	 */
	void setLineNumber(int value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Actor#getMoC <em>Mo C</em>}' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Mo C</em>' containment reference.
	 * @see #getMoC()
	 * @generated
	 */
	void setMoC(MoC value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Actor#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Actor#isNative
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
	 * Sets the value of the '{@link net.sf.orcc.df.Actor#getTemplateData <em>Template Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Data</em>' attribute.
	 * @see #getTemplateData()
	 * @generated
	 */
	void setTemplateData(Object value);

	/**
	 * Returns true if the actor is using a native procedure/function.
	 * 
	 * @return true if actor is using a native procedure/function, otherwise false.
	 */
	public boolean useNativeProcedure();

}
