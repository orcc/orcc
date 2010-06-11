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
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * This class defines a rule-based scanner for templates inside of STG files. A
 * template is contents found inside of <tt>&lt;&lt;</tt> and <tt>&gt;&gt;</tt>.
 * 
 * @author Matthieu Wipliez
 */
public class StgTemplateScanner extends RuleBasedScanner {

	private enum RuleState {
		DEFAULT, INSIDE_DOLLAR, INSIDE_LT_LT
	};

	private class STCodeRule implements IRule {

		private IToken betweenDollarsToken;

		private IToken defaultToken;

		private RuleState state;

		public STCodeRule(ColorManager colorManager) {
			defaultToken = new Token(new TextAttribute(
					colorManager.getColor(IStgColorConstants.DEFAULT), null,
					SWT.BOLD));
			betweenDollarsToken = new Token(new TextAttribute(
					colorManager.getColor(IStgColorConstants.SEPARATOR)));

			state = RuleState.DEFAULT;
		}

		public IToken evaluate(ICharacterScanner scanner) {
			int c = scanner.read();
			if (c == ICharacterScanner.EOF) {
				// end of file detected, goes back to default state
				state = RuleState.DEFAULT;
				return Token.EOF;
			}

			switch (state) {
			case INSIDE_DOLLAR:
				if (c == '$') {
					// last dollar, back to <<...<here>...>>
					state = RuleState.INSIDE_LT_LT;
					return betweenDollarsToken;
				} else if (c == '>') {
					c = scanner.read();
					if (c == '>') {
						// dollar not closed, end of template detected
						state = RuleState.DEFAULT;
						return betweenDollarsToken;
					} else {
						scanner.unread();
						return betweenDollarsToken;
					}
				} else {
					// between dollars: $...<here>...$
					return betweenDollarsToken;
				}
			case INSIDE_LT_LT:
				if (c == '>') {
					c = scanner.read();
					if (c == '>') {
						// end of template
						state = RuleState.DEFAULT;
						return betweenDollarsToken;
					} else {
						scanner.unread();
					}
				} else if (c == '$') {
					// start of dollar
					state = RuleState.INSIDE_DOLLAR;
					return betweenDollarsToken;
				}

				return defaultToken;
			case DEFAULT:
				if (c == '<') {
					c = scanner.read();
					if (c == '<') {
						// start of template
						state = RuleState.INSIDE_LT_LT;
						return betweenDollarsToken;
					} else {
						scanner.unread();
					}
				} else if (c == '$') {
					// start of dollar
					state = RuleState.INSIDE_DOLLAR;
					return betweenDollarsToken;
				}

				return defaultToken;
			default:
				break;
			}

			scanner.unread();
			return Token.UNDEFINED;
		}
	}

	public StgTemplateScanner(ColorManager manager) {
		List<IRule> rules = new ArrayList<IRule>();

		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		rules.add(new STCodeRule(manager));

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
