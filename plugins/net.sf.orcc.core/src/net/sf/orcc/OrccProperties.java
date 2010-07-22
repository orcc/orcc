/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc;

import org.eclipse.core.runtime.QualifiedName;

/**
 * Constants associated with an Orcc project.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface OrccProperties {

	/**
	 * Default value for {@value #OUTPUT_FOLDER}.
	 */
	public static final String DEFAULT_OUTPUT = ".generated";
	
	/**
	 * Default value for {@value #COMPACTJSON}.
	 */
	public static final boolean DEFAULT_COMPACTJSON = true;

	/**
	 * Prefix for Orcc properties
	 */
	public static final String PREFIX = "net.sf.orcc";

	/**
	 * Property for output folder for Xtext front-end.
	 */
	public static final QualifiedName PROPERTY_OUTPUT = new QualifiedName(
			PREFIX, "outputFolder");
	
	/**
	 * Property for producing compact json for Xtext front-end.
	 */
	public static final QualifiedName COMPACT_JSON = new QualifiedName(
			PREFIX, "compactJson");

}
