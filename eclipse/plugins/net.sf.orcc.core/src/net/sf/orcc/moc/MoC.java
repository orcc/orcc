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
package net.sf.orcc.moc;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a Model of Computation.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @model abstract="true"
 */
public interface MoC extends EObject {
	/**
	 * Return the short name of the current MoC
	 * 
	 * @return The short name of the current MoC
	 */
	public String getShortName();

	/**
	 * Returns true if this MoC is CSDF.
	 * 
	 * @return true if this MoC is CSDF
	 */
	public boolean isCSDF();

	/**
	 * Returns true if this MoC is Dataflow Process Networks (equivalent to
	 * dynamic <b>and</b> time-dependent).
	 * 
	 * @return true if this MoC is Dataflow Process Networks
	 */
	public boolean isDPN();

	/**
	 * Returns true if this MoC is Kahn Process Networks (equivalent to dynamic
	 * but not time-dependent).
	 * 
	 * @return true if this MoC is Kahn Process Networks
	 */
	public boolean isKPN();

	/**
	 * Returns true if this MoC is quasi-static.
	 * 
	 * @return true if this MoC is quasi-static
	 */
	public boolean isQuasiStatic();

	/**
	 * Returns true if this MoC is SDF.
	 * 
	 * @return true if this MoC is SDF
	 */
	public boolean isSDF();

}
