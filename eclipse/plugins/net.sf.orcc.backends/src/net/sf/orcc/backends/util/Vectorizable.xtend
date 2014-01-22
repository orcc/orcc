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

import net.sf.orcc.df.Network
import net.sf.orcc.ir.util.ValueUtil
import org.eclipse.emf.common.util.EList
import net.sf.orcc.util.Attribute
import static net.sf.orcc.backends.BackendsConstants.*

/**
 * Class containing static methods useful to set potential vectorization information for each port
 * and action of an actor or for all actors of a network
 *
 * @author Alexandre Sanchez
 */
class Vectorizable {
	def static private boolean isPortVectorizable(Pattern pattern, Port port) {
		return pattern.getNumTokens(port) >= MIN_REPEAT_ALIGNABLE
	}

	def static private filterVectorizableAttributs(EList<Attribute> attrs) {
		attrs.filter[it.name.endsWith("_" + ALIGNABLE)]
	}

	def static private filterNotVectorizableAttributs(EList<Attribute> attrs) {
		attrs.filter[it.name.endsWith("_NOT_" + ALIGNABLE)]
	}

	def static private boolean setVectorizableAttributs(Pattern pattern, Port port, String actionName) {
		var bIsVectorizable = isPortVectorizable(pattern, port)
		
		if (bIsVectorizable) {
			port.addAttribute(actionName + "_" + ALIGNABLE)
			port.setAttribute(actionName + "_" + ALIGNABLE, pattern.getNumTokens(port))
		} else {
			port.addAttribute(actionName + "_NOT_" + ALIGNABLE)			
		}
		
		return bIsVectorizable
	}

	def static private void setAlwaysVectorizableAttributs(Pattern pattern, Port port) {
		val iNumToken = pattern.getNumTokens(port)
		var bIsAlwaysVectorizable = pattern.isPortVectorizable(port) && ValueUtil.isPowerOfTwo(iNumToken)
		
		if (bIsAlwaysVectorizable) {
			bIsAlwaysVectorizable = bIsAlwaysVectorizable 
				&& (port.attributes.filterVectorizableAttributs.size == 1) 
				&& (port.attributes.filterNotVectorizableAttributs.size == 0) 
		
			if (port.attributes.filterVectorizableAttributs.size > 1) {
				for (Attribute attr : port.attributes.filterVectorizableAttributs) {
					bIsAlwaysVectorizable = bIsAlwaysVectorizable && (attr.stringValue == iNumToken.toString())
				}
			}
		}
		
		if (bIsAlwaysVectorizable) {
			port.addAttribute(ALIGNED_ALWAYS)			
		}
	}
	
	def static private void setVectorizableAttributs(Action action) {
		var boolean bIsVectorizable = false

		for (Port port : action.inputPattern.ports) {
			bIsVectorizable = action.inputPattern.setVectorizableAttributs(port, action.name) || bIsVectorizable
		}
		for (Port port : action.outputPattern.ports) {
			bIsVectorizable = action.outputPattern.setVectorizableAttributs(port, action.name) || bIsVectorizable
		}
		
		if (bIsVectorizable){
			action.addAttribute(ALIGNABLE)			
		} 
	}	

	def static private void setAlwaysVectorizableAttributs(Action action) {
		var boolean bIsAlwaysVectorizable = true
		
		for (Port port : action.inputPattern.ports) {
			action.inputPattern.setAlwaysVectorizableAttributs(port)
			bIsAlwaysVectorizable = bIsAlwaysVectorizable && port.hasAttribute(ALIGNED_ALWAYS) 
		}
		for (Port port : action.outputPattern.ports) {
			action.outputPattern.setAlwaysVectorizableAttributs(port)
			bIsAlwaysVectorizable = bIsAlwaysVectorizable && port.hasAttribute(ALIGNED_ALWAYS) 
		}
		
		if (bIsAlwaysVectorizable){
			action.addAttribute(ALIGNED_ALWAYS)			
		} 
	}	

	def static void setVectorizableAttributs(Actor actor) {
		for (Action action : actor.actions) {
			action.setVectorizableAttributs()	
		}		
		for (Action action : actor.actions) {
			if (action.hasAttribute(ALIGNABLE)){					
				action.setAlwaysVectorizableAttributs()
			} 			
		}		
	}	

	def static void setVectorizableAttributs(Network network) {
		for (Actor actor : network.allActors) {
			actor.setVectorizableAttributs
		}
	}		
}