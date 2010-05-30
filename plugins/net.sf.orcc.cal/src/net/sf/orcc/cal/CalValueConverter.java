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
package net.sf.orcc.cal;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.parsetree.AbstractNode;
import org.eclipse.xtext.util.Strings;

/**
 * Converts "true" and "false" to booleans, and hexadecimal to integer.
 */
public class CalValueConverter extends DefaultTerminalConverters {

	@ValueConverter(rule = "BOOL")
	public IValueConverter<Boolean> BOOL() {
		return new IValueConverter<Boolean>() {

			public String toString(Boolean value) {
				return value.toString();
			}

			public Boolean toValue(String string, AbstractNode node) {
				if (Strings.isEmpty(string)) {
					throw new ValueConverterException(
							"Couldn't convert empty string to boolean", node,
							null);
				}

				if ("true".equals(string)) {
					return true;
				} else {
					return false;
				}
			}

		};
	}

	@ValueConverter(rule = "HEX")
	public IValueConverter<Integer> HEX() {
		return new IValueConverter<Integer>() {

			public String toString(Integer value) {
				return value.toString();
			}

			public Integer toValue(String string, AbstractNode node) {
				if (Strings.isEmpty(string))
					throw new ValueConverterException(
							"Couldn't convert empty string to int", node, null);
				try {
					return Integer.valueOf(string.substring(2), 16);
				} catch (NumberFormatException e) {
					throw new ValueConverterException("Couldn't convert '"
							+ string + "' to int", node, e);
				}
			}

		};
	}

}
