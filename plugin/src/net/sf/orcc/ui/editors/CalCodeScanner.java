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
package net.sf.orcc.ui.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * This class defines a rule-based scanner for CAL. It uses rules to determine
 * the different token types. The rules were copied from the OpenDF CAL editor
 * classes.
 * 
 * <p>
 * Note: This scanner is only used for the default content type, so it is never
 * given single-line, multi-line, or javadoc-style comments.
 * </p>
 * 
 * @author Matthieu Wipliez
 */
public class CalCodeScanner extends RuleBasedScanner {

	private class CalNumberRule implements IRule {

		IToken success;

		public CalNumberRule(ColorManager colorManager) {
			success = new Token(new TextAttribute(colorManager
					.getColor(ICalColorConstants.CONSTANT)));
		}

		public IToken evaluate(ICharacterScanner s) {
			// Try floats first to get the case where mantissa is a valid int
			if (getFloat(s))
				return success;
			if (getInt(s))
				return success;

			return Token.UNDEFINED;
		}

		private int getDecimalDigits(ICharacterScanner s) {
			int c;
			int n = 0;

			while ((c = s.read()) >= '0' && c <= '9')
				++n;
			s.unread();
			return n;
		}

		private boolean getDecimalPoint(ICharacterScanner s) {
			if (s.read() == '.')
				return true;
			s.unread();
			return false;
		}

		private int getExponent(ICharacterScanner s) {
			int c, n1, n2;

			// Required preamble
			if ((c = s.read()) != 'e' && c != 'E') {
				s.unread();
				return 0;
			}

			n1 = 1;

			// Optional sign
			if ((c = s.read()) == '+' || c == '-')
				n1 = 2;
			else
				s.unread();

			// Required exponent value
			n2 = getDecimalDigits(s);
			if (n2 == 0) {
				unread(s, n1);
				return 0;
			}

			return n1 + n2;
		}

		private boolean getFloat(ICharacterScanner s) {

			int n = getDecimalDigits(s);
			if (n == 0)
				return false;

			if (!getDecimalPoint(s)) {
				unread(s, n);
				return false;

			}

			int m = getDecimalDigits(s);
			if (m == 0) {
				unread(s, n + 1);
				return false;
			}

			// The rest is optional
			getExponent(s);

			return true;
		}

		private int getHexDigits(ICharacterScanner s) {
			int c;
			int n = 0;

			while (((c = s.read()) >= '0' && c <= '9')
					|| (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))
				++n;
			s.unread();
			return n;
		}

		private boolean getInt(ICharacterScanner s) {
			int c;

			// Leading zero
			if (getZero(s)) {
				// Look for an octal
				if (getOctalDigits(s) > 0)
					return true;

				// Look for a hex
				if ((c = s.read()) == 'x' || c == 'X') { // If there are no
					// digits after the
					// 'x', ignore the
					// 'x' too
					if (getHexDigits(s) == 0)
						s.unread();
				}

				// Was either a '0', an octal or a hex
				return true;
			}

			return getDecimalDigits(s) > 0;
		}

		private int getOctalDigits(ICharacterScanner s) {
			int c;
			int n = 0;

			while ((c = s.read()) >= '0' && c <= '7')
				++n;
			s.unread();
			return n;
		}

		private boolean getZero(ICharacterScanner s) {
			if (s.read() == '0')
				return true;
			s.unread();
			return false;
		}

		private void unread(ICharacterScanner s, int n) {
			while (n > 0) {
				--n;
				s.unread();
			}
		}

	}

	private class CalOperatorRule implements IRule {

		private static final String operators = "=+-*/<>!@$%^&#:?~|";

		private static final String separators = ".[]{}(),;";

		IToken operatorToken;

		IToken separatorToken;

		public CalOperatorRule(ColorManager manager) {
			operatorToken = new Token(new TextAttribute(manager
					.getColor(ICalColorConstants.DEFAULT)));
			separatorToken = new Token(new TextAttribute(manager
					.getColor(ICalColorConstants.DEFAULT)));
		}

		public IToken evaluate(ICharacterScanner scanner) {
			IToken token = Token.UNDEFINED;

			int c = scanner.read();

			if (operators.indexOf(c) >= 0) {
				token = operatorToken;
				while (operators.indexOf(scanner.read()) >= 0) {
				}
			} else if (separators.indexOf(c) >= 0) {
				token = separatorToken;
				while (separators.indexOf(scanner.read()) >= 0) {
				}
			}

			scanner.unread();
			return token;
		}
	}

	private class CalWordRule implements IRule {

		private IToken identifierToken;

		private IToken keywordToken;

		private Map<String, IToken> map;

		public CalWordRule(ColorManager colorManager) {
			keywordToken = new Token(new TextAttribute(colorManager
					.getColor(ICalColorConstants.KEYWORD), null, SWT.BOLD));
			identifierToken = new Token(new TextAttribute(colorManager
					.getColor(ICalColorConstants.IDENTIFIER)));

			map = new HashMap<String, IToken>();

			int i;

			for (i = 0; i < keywords.length; i++) {
				map.put(keywords[i], keywordToken);
			}
		}

		public IToken evaluate(ICharacterScanner scanner) {
			int c;
			StringBuffer buf = new StringBuffer();
			IToken returnToken;

			// Look for a valid start character
			if (!Character.isJavaIdentifierStart(c = scanner.read())) {
				scanner.unread();
				return Token.UNDEFINED;
			}

			buf.append((char) c);

			// Continue consuming valid identifier characters
			while (Character.isJavaIdentifierPart(c = scanner.read()))
				buf.append((char) c);

			scanner.unread();
			returnToken = map.get(buf.toString());

			if (returnToken == null) {
				// Plain old identifier
				return identifierToken;
			}

			return returnToken;
		}
	}

	private final static String[] keywords = {
			// cal keywords
			"action", "actor", "begin", "do", "else", "end", "for", "foreach",
			"fsm", "function", "guard", "if", "import", "in", "initialize",
			"priority", "proc", "procedure", "repeat", "schedule", "then",
			"var", "while",

			// expressions
			"and", "div", "false", "mod", "not", "or", "true",

			// types
			"bool", "float", "int", "List", "String", "uint", "void" };

	public CalCodeScanner(ColorManager manager) {
		IToken constantToken = new Token(new TextAttribute(manager
				.getColor(ICalColorConstants.CONSTANT)));

		List<IRule> rules = new ArrayList<IRule>();

		rules.add(new WhitespaceRule(new CalWhitespaceDetector()));

		rules.add(new SingleLineRule("\"", "\"", constantToken, '\\'));
		rules.add(new SingleLineRule("'", "'", constantToken, '\\'));
		rules.add(new CalNumberRule(manager));
		rules.add(new CalWordRule(manager));
		rules.add(new CalOperatorRule(manager));

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
