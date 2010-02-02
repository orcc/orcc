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
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * This class defines a rule-based scanner for STG. It uses rules to determine
 * the different token types.
 * 
 * <p>
 * Note: This scanner is only used for the default content type, so it is never
 * given single-line, multi-line, or javadoc-style comments.
 * </p>
 * 
 * @author Matthieu Wipliez
 */
public class StgScanner extends RuleBasedScanner {

	private class SeparatorsRule implements IRule {

		private static final String separators = "():=";

		IToken separatorToken;

		public SeparatorsRule(ColorManager manager) {
			separatorToken = new Token(new TextAttribute(manager
					.getColor(IStgColorConstants.SEPARATOR)));
		}

		public IToken evaluate(ICharacterScanner scanner) {
			IToken token = Token.UNDEFINED;

			int c = scanner.read();

			if (separators.indexOf(c) >= 0) {
				token = separatorToken;
				while (separators.indexOf(scanner.read()) >= 0) {
				}
			}

			scanner.unread();
			return token;
		}
	}

	private class KeywordRule implements IRule {

		private IToken identifierToken;

		private IToken keywordToken;

		private Map<String, IToken> map;

		public KeywordRule(ColorManager colorManager) {
			keywordToken = new Token(new TextAttribute(colorManager
					.getColor(IStgColorConstants.KEYWORD), null, SWT.BOLD));
			identifierToken = new Token(new TextAttribute(colorManager
					.getColor(IStgColorConstants.DEFAULT)));

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

	private final static String[] keywords = { "group", "implements",
			"interface", "super" };

	public StgScanner(ColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		rules.add(new KeywordRule(manager));
		rules.add(new SeparatorsRule(manager));

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
