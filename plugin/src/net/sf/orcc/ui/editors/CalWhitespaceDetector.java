package net.sf.orcc.ui.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class CalWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}
