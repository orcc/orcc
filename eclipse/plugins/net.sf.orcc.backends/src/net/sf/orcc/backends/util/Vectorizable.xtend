/*
 * Copyright (c) 2013, INSA Rennes
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
 *   * Neither the name of INSA Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
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
package net.sf.orcc.backends.util

import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor

import static net.sf.orcc.backends.BackendsConstants.*
import net.sf.orcc.df.Network
import net.sf.orcc.ir.util.ValueUtil

/**
 * Class containing static methods useful to set potential vectorization information for each port
 * and action of an actor or for all actors of a network
 *
 * @author Alexandre Sanchez
 */
class Vectorizable {
	def static private boolean isPortVectorizable(Pattern pattern, Port port) {
		return pattern.getNumTokens(port) >= MIN_VECTORIZABLE
	}

	def static private boolean setVectorizableAttributs( Pattern pattern, String actionName, Port port) {
		var bIsVectorizable = isPortVectorizable(pattern, port)
		
		if (bIsVectorizable){
			port.addAttribute(actionName + "_" + VECTORIZABLE)
		}
		
		return bIsVectorizable
	}

	def static private boolean isAlwaysVectorizable(Action action, int nbActions) {
		var boolean bIsAlwaysVectorizable = false
		
		if (nbActions == 1) {
			bIsAlwaysVectorizable = true
			for (Port port : action.inputPattern.ports) {
				bIsAlwaysVectorizable = bIsAlwaysVectorizable && ValueUtil.isPowerOfTwo(action.inputPattern.getNumTokens(port)) 
			}
			for (Port port : action.outputPattern.ports) {
				bIsAlwaysVectorizable = bIsAlwaysVectorizable && ValueUtil.isPowerOfTwo(action.outputPattern.getNumTokens(port))
			}
		} 
		
		return bIsAlwaysVectorizable
	}
	
	def static private boolean setVectorizableAttributs(Action action, int nbActions) {
		var boolean bIsVectorizable = false

		for (Port port : action.inputPattern.ports) {
			bIsVectorizable = action.inputPattern.setVectorizableAttributs(action.name, port) || bIsVectorizable
		}
		for (Port port : action.outputPattern.ports) {
			bIsVectorizable = action.outputPattern.setVectorizableAttributs(action.name, port) || bIsVectorizable
		}
		
		if (bIsVectorizable){
			action.addAttribute(VECTORIZABLE)
			
			if (action.isAlwaysVectorizable(nbActions)){
				action.addAttribute(ALWAYS_VECTORIZABLE)		
			}
		} 
		
		return bIsVectorizable
	}	

	def static void setVectorizableAttributs(Actor actor) {
		val nbActions = actor.actions.size
		for (Action action : actor.actions) {
			action.setVectorizableAttributs(nbActions)	
		}		
	}	

	def static void setVectorizableAttributs(Network network) {
		for (Actor actor : network.allActors) {
			actor.setVectorizableAttributs
		}
	}		
}