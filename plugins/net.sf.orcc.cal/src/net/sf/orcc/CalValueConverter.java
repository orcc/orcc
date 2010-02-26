package net.sf.orcc;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.parsetree.AbstractNode;
import org.eclipse.xtext.util.Strings;

/**
 * Converts hexadecimal to integer.
 */
public class CalValueConverter extends DefaultTerminalConverters {

	@ValueConverter(rule = "HEX")
	public IValueConverter<Integer> HEX() {
		return new IValueConverter<Integer>() {
			
			public Integer toValue(String string, AbstractNode node) {
				if (Strings.isEmpty(string))
					throw new ValueConverterException("Couldn't convert empty string to int", node, null);
				try {
					return Integer.valueOf(string.substring(2), 16);
				} catch (NumberFormatException e) {
					throw new ValueConverterException("Couldn't convert '"+string+"' to int", node, e);
				}
			}

			public String toString(Integer value) {
				return value.toString();
			}

		};
	}
	
}
