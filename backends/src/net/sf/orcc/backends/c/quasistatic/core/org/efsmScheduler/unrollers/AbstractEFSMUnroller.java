/*
 * Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
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
 * THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
 * Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.unrollers;

import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.AbstractSubgraph;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model.EFSM;
/**
 * Abstract class for EFSM unroller.
 * When the unrolling method is updated, this class should be extended.
 * @author sadhanal
 *
 */
public abstract class AbstractEFSMUnroller {
	public AbstractEFSMUnroller(){
		super();
	}
	/**
	 * Unrolls this efsm and returns the set of SDF graphs
	 * originating from the state machine efsm
	 * @param efsm the state machine which should be unrolled
	 * @return the v ector of unrolled subgraphs
	 */
	abstract public Vector<? extends AbstractSubgraph> unrollStaticActor(EFSM efsm);
    abstract public Vector<? extends AbstractSubgraph> unrollBorderLineActor(EFSM efsm);
}
