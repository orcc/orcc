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
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a state. The state is represented with a name and a boolean value
 * which represents if the state is initial
 * @author vimartin
 */
public class State {
	String name;
	private boolean isInitial;

	public State(String name) {
		this.name = name;
		isInitial = false;
	}

	public void setToInitialState() {
		isInitial = true;
	}

	public boolean isInitialState() {
		return isInitial;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object s) {

		if (s == null)
			return false;
		if (s instanceof State) {

			return name.equals(s.toString());

		} else
			return false;
	}

	public int hashCode() {
		return name.hashCode();
	}

	public static void main(String[] args) {
		State state = new State("cmd");
		State s2 = new State("cmd1");
		State s3 = new State("cmd3");
		Set<State> S = new HashSet<State>();
		S.add(state);

		S.add(s2);
		S.add(s3);

		System.out.println(S.toString());

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the isInitial
	 */
	public boolean isInitial() {
		return isInitial;
	}
}
