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

import net.sf.dftools.util.Attributable;
import net.sf.orcc.ir.Procedure;

/**
 * This class defines an action. An action has a location, a tag, an input and
 * an output pattern. Contrarily to the original CAL model, the scheduling
 * information of an action is decoupled from its body. This is why an action
 * has a "scheduler" and a body.
 * 
 * @author Matthieu Wipliez
 * @author Samuel Keller
 * @model
 */
public interface Action extends Attributable {

	/**
	 * Returns the procedure that holds the body of this action.
	 * 
	 * @return the procedure that holds the body of this action
	 * @model containment="true"
	 */
	Procedure getBody();

	/**
	 * Returns the input pattern of this action.
	 * 
	 * @return the input pattern of this action
	 * @model containment="true"
	 */
	Pattern getInputPattern();

	/**
	 * Returns action name (tag or body name)
	 * 
	 * @return action name
	 */
	String getName();

	/**
	 * Returns the output pattern of this action.
	 * 
	 * @return the output pattern of this action
	 * @model containment="true"
	 */
	Pattern getOutputPattern();

	/**
	 * Returns the peek pattern of this action.
	 * 
	 * @return the peek pattern of this action
	 * @model containment="true"
	 */
	Pattern getPeekPattern();

	/**
	 * Returns the procedure that holds the scheduling information of this
	 * action.
	 * 
	 * @return the procedure that holds the scheduling information of this
	 *         action
	 * @model containment="true"
	 */
	Procedure getScheduler();

	/**
	 * Returns the tag of this action.
	 * 
	 * @return the tag of this action
	 * @model containment="true"
	 */
	Tag getTag();

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Action#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Procedure value);

	/**
	 * Sets the input pattern of this action to the given pattern.
	 * 
	 * @param pattern
	 *            a pattern
	 */
	void setInputPattern(Pattern pattern);

	/**
	 * Sets the output pattern of this action to the given pattern.
	 * 
	 * @param pattern
	 *            a pattern
	 */
	void setOutputPattern(Pattern pattern);

	/**
	 * Sets the peek pattern of this action to the given pattern.
	 * 
	 * @param pattern
	 *            a pattern
	 */
	void setPeekPattern(Pattern pattern);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Action#getScheduler <em>Scheduler</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scheduler</em>' containment reference.
	 * @see #getScheduler()
	 * @generated
	 */
	void setScheduler(Procedure value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Action#getTag <em>Tag</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag</em>' containment reference.
	 * @see #getTag()
	 * @generated
	 */
	void setTag(Tag value);
}
