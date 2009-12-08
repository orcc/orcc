package net.sf.orcc.ui.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

public class CalCodeScanner extends RuleBasedScanner {

	private class OpendfNumberRule implements IRule {

		IToken success;

		public OpendfNumberRule(ColorManager colorManager) {
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

	private class OpendfOperatorRule implements IRule {

		IToken operatorToken;

		IToken separatorToken;

		public OpendfOperatorRule(ColorManager manager) {
			operatorToken = new Token(new TextAttribute(manager
					.getColor(ICalColorConstants.OPERATOR)));
			separatorToken = new Token(new TextAttribute(manager
					.getColor(ICalColorConstants.SEPARATOR)));
		}

		public IToken evaluate(ICharacterScanner scanner) {
			IToken token = Token.UNDEFINED;

			int c = scanner.read();

			switch (c) {
			case '+':
			case '-':
			case '*':
			case '/':
			case '<':
			case '>':
				return operatorToken;

			case '(':
			case ')':
			case '[':
			case ']':
				return separatorToken;
			}

			scanner.unread();
			return token;
		}
	}

	private class OpendfWordRule implements IRule {

		private IToken constantToken;
		private IToken identifierToken;
		private IToken keywordToken;
		private Map<String, IToken> map;

		private IToken operatorToken;

		public OpendfWordRule(ColorManager colorManager) {
			keywordToken = new Token(new TextAttribute(colorManager
					.getColor(ICalColorConstants.KEYWORD)));
			constantToken = new Token(new TextAttribute(colorManager
					.getColor(ICalColorConstants.CONSTANT)));
			operatorToken = new Token(new TextAttribute(colorManager
					.getColor(ICalColorConstants.OPERATOR)));
			identifierToken = new Token(new TextAttribute(colorManager
					.getColor(ICalColorConstants.IDENTIFIER)));

			map = new HashMap<String, IToken>();

			int i;

			for (i = 0; i < keywords.length; i++) {
				map.put(keywords[i], keywordToken);
			}

			for (i = 0; i < wordOperators.length; i++) {
				map.put(wordOperators[i], operatorToken);
			}

			for (i = 0; i < wordConstants.length; i++) {
				map.put(wordConstants[i], constantToken);
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
			"action", "actor", "begin", "do", "else", "end", "foreach", "fsm",
			"function", "guard", "if", "import", "initialize", "or",
			"priority", "proc", "procedure", "repeat", "schedule", "then",
			"var", "while" };

	private final static String[] wordConstants = { "false", "true" };

	private final static String[] wordOperators = { "and", "div", "mod", "not" };

	public CalCodeScanner(ColorManager manager) {
		IToken commentToken = new Token(new TextAttribute(manager
				.getColor(ICalColorConstants.COMMENT)));
		IToken constantToken = new Token(new TextAttribute(manager
				.getColor(ICalColorConstants.CONSTANT)));

		List<IRule> rules = new ArrayList<IRule>();

		rules.add(new WhitespaceRule(new CalWhitespaceDetector()));

		rules.add(new MultiLineRule("/*", "*/", commentToken));
		rules.add(new EndOfLineRule("//", commentToken));
		rules.add(new SingleLineRule("\"", "\"", constantToken));
		rules.add(new SingleLineRule("'", "'", constantToken));
		rules.add(new OpendfNumberRule(manager));
		rules.add(new OpendfWordRule(manager));
		rules.add(new OpendfOperatorRule(manager));

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
