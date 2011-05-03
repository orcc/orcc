/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.plugins;

/**
 * This interface defines an option of a plugin.
 * 
 * @author Jerome Gorin
 * @author Matthieu Wipliez
 * 
 */
public interface Option {

	/**
	 * Returns the option's default value.
	 * 
	 * @return the option's default value
	 */
	String getDefaultValue();

	/**
	 * Returns the description of the option.
	 * 
	 * @return the description of the option
	 */
	String getDescription();

	/**
	 * Returns the option's identifier.
	 * 
	 * @return the option's identifier
	 */
	String getIdentifier();

	/**
	 * Returns the option's name.
	 * 
	 * @return the option's name
	 */
	String getName();

	/**
	 * Sets the default value of this option.
	 * 
	 * @param defaultValue
	 *            the default value of this option
	 */
	void setDefaultValue(String defaultValue);

	/**
	 * Sets the description of the option.
	 * 
	 * @param description
	 *            the description of the option
	 */
	void setDescription(String description);

	/**
	 * Sets the option's identifier.
	 * 
	 * @param identifier
	 *            the option's identifier
	 */
	void setIdentifier(String identifier);

	/**
	 * Sets the option name.
	 * 
	 * @param name
	 *            the option name
	 */
	void setName(String name);

}
