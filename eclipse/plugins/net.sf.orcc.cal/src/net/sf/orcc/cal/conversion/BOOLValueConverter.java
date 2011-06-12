/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.sf.orcc.cal.conversion;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * This class defines a value converter for BOOL rule.
 * 
 * @author Matthieu Wipliez
 */
public class BOOLValueConverter extends AbstractLexerBasedConverter<Boolean> {

	public BOOLValueConverter() {
		super();
	}

	@Override
	protected String toEscapedString(Boolean value) {
		return value.toString();
	}

	public Boolean toValue(String string, INode node) {
		if (Strings.isEmpty(string)) {
			throw new ValueConverterException(
					"Couldn't convert empty string to boolean", node, null);
		}

		if ("true".equals(string)) {
			return true;
		} else {
			return false;
		}
	}

}
