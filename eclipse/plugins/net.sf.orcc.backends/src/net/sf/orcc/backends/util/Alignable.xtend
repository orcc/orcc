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

import net.sf.orcc.df.Action
import net.sf.orcc.df.Actor
import net.sf.orcc.df.Network
import net.sf.orcc.df.Pattern
import net.sf.orcc.df.Port
import net.sf.orcc.ir.util.ValueUtil
import net.sf.orcc.util.Attribute
import org.eclipse.emf.common.util.EList

import static net.sf.orcc.backends.BackendsConstants.*
import static net.sf.orcc.util.OrccAttributes.*

/**
 * Class containing static methods useful to set potential alignment information of ports
 * and actions.
 *
 * @author Alexandre Sanchez
 */
class Alignable {

	def static private boolean isPortAlignable(Pattern pattern, Port port) {
		return pattern.getNumTokens(port) >= MIN_REPEAT_ALIGNABLE
	}

	def static private filterAlignableAttributs(EList<Attribute> attrs) {
		attrs.filter[it.name.endsWith("_" + ALIGNABLE)]
	}

	def static private filterNotAlignableAttributs(EList<Attribute> attrs) {
		attrs.filter[it.name.endsWith("_NOT_" + ALIGNABLE)]
	}

	def static private boolean setAlignableAttributs(Pattern pattern, Port port, String actionName) {
		var isAlignable = isPortAlignable(pattern, port)

		if (isAlignable) {
			port.addAttribute(actionName + "_" + ALIGNABLE)
			port.setAttribute(actionName + "_" + ALIGNABLE, pattern.getNumTokens(port))
		} else {
			port.addAttribute(actionName + "_NOT_" + ALIGNABLE)
		}

		return isAlignable
	}

	def static private void setAlwaysAlignedAttributs(Pattern pattern, Port port) {
		val numTokens = pattern.getNumTokens(port)
		var isAlwaysAligned = pattern.isPortAlignable(port) && ValueUtil.isPowerOfTwo(numTokens)

		if (isAlwaysAligned) {
			isAlwaysAligned = isAlwaysAligned && (port.attributes.filterAlignableAttributs.size == 1) &&
				(port.attributes.filterNotAlignableAttributs.size == 0)

			if (port.attributes.filterAlignableAttributs.size > 1) {
				for (Attribute attr : port.attributes.filterAlignableAttributs) {
					isAlwaysAligned = isAlwaysAligned && (attr.stringValue == numTokens.toString())
				}
			}
		}

		if (isAlwaysAligned) {
			port.addAttribute(ALIGNED_ALWAYS)
		}
	}

	def static private void setAlignableAttributs(Action action) {
		var boolean isVectorizable = false

		for (Port port : action.inputPattern.ports) {
			isVectorizable = action.inputPattern.setAlignableAttributs(port, action.name) || isVectorizable
		}
		for (Port port : action.outputPattern.ports) {
			isVectorizable = action.outputPattern.setAlignableAttributs(port, action.name) || isVectorizable
		}

		if (isVectorizable) {
			action.addAttribute(ALIGNABLE)
		}
	}

	def static private void setAlwaysAlignedAttributs(Action action) {
		var boolean isAlwaysAligned = true

		for (Port port : action.inputPattern.ports) {
			action.inputPattern.setAlwaysAlignedAttributs(port)
			isAlwaysAligned = isAlwaysAligned && port.hasAttribute(ALIGNED_ALWAYS)
		}
		for (Port port : action.outputPattern.ports) {
			action.outputPattern.setAlwaysAlignedAttributs(port)
			isAlwaysAligned = isAlwaysAligned && port.hasAttribute(ALIGNED_ALWAYS)
		}

		if (isAlwaysAligned) {
			action.addAttribute(ALIGNED_ALWAYS)
		}
	}

	def static void setAlignability(Actor actor) {
		for (Action action : actor.actions) {
			action.setAlignableAttributs()
		}
		for (Action action : actor.actions) {
			if (action.hasAttribute(ALIGNABLE)) {
				action.setAlwaysAlignedAttributs()
			}
		}
	}

	def static void setAlignability(Network network) {
		for (Actor actor : network.allActors) {
			actor.setAlignability
		}
	}
}
