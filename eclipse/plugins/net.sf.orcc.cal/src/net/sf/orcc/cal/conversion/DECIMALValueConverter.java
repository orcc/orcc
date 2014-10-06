/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sf.orcc.cal.conversion;

import java.math.BigInteger;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * This class defines a value converter for HEX rule.
 * 
 * @author Matthieu Wipliez
 */
public class DECIMALValueConverter extends AbstractLexerBasedConverter<BigInteger> {

	public DECIMALValueConverter() {
		super();
	}

	@Override
	protected void assertValidValue(BigInteger value) {
		super.assertValidValue(value);
		if (value.compareTo(BigInteger.ZERO) < 0)
			throw new ValueConverterException(getRuleName()
					+ "-value may not be negative (value:" + value + ").",
					null, null);
	}

	@Override
	protected String toEscapedString(BigInteger value) {
		return value.toString();
	}

	public BigInteger toValue(String string, INode node) {
		if (Strings.isEmpty(string))
			throw new ValueConverterException(
					"Couldn't convert empty string to int.", node, null);
		try {
			return new BigInteger(string);
		} catch (NumberFormatException e) {
			throw new ValueConverterException("Couldn't convert '" + string
					+ "' to int.", node, e);
		}
	}

}
