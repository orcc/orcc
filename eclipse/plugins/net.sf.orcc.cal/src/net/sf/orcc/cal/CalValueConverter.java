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

import java.math.BigInteger;

import net.sf.orcc.cal.conversion.BOOLValueConverter;
import net.sf.orcc.cal.conversion.DECIMALValueConverter;
import net.sf.orcc.cal.conversion.HEXValueConverter;
import net.sf.orcc.cal.conversion.OCTALValueConverter;
import net.sf.orcc.cal.conversion.REALValueConverter;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.QualifiedNameValueConverter;

import com.google.inject.Inject;

/**
 * Converts "true" and "false" to booleans, and hexadecimal to integer.
 */
public class CalValueConverter extends DefaultTerminalConverters {

	@Inject
	private BOOLValueConverter boolValueConverter;

	@Inject
	private DECIMALValueConverter decimalValueConverter;

	@Inject
	private HEXValueConverter hexValueConverter;

	@Inject
	private OCTALValueConverter octalValueConverter;

	@Inject
	private QualifiedNameValueConverter qualifiedNameValueConverter;

	@Inject
	private REALValueConverter realValueConverter;

	@ValueConverter(rule = "BOOL")
	public IValueConverter<Boolean> BOOL() {
		return boolValueConverter;
	}

	@ValueConverter(rule = "DECIMAL")
	public IValueConverter<BigInteger> DECIMAL() {
		return decimalValueConverter;
	}

	@ValueConverter(rule = "HEX")
	public IValueConverter<BigInteger> HEX() {
		return hexValueConverter;
	}

	@ValueConverter(rule = "OCTAL")
	public IValueConverter<BigInteger> OCTAL() {
		return octalValueConverter;
	}

	@ValueConverter(rule = "QualifiedName")
	public IValueConverter<String> QualifiedName() {
		return qualifiedNameValueConverter;
	}

	@ValueConverter(rule = "QualifiedNameWithWildCard")
	public IValueConverter<String> QualifiedNameWithWildCard() {
		return qualifiedNameValueConverter;
	}

	@ValueConverter(rule = "REAL")
	public IValueConverter<Float> REAL() {
		return realValueConverter;
	}

}
