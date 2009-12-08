package net.sf.orcc.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

public class CalSingleLineCommentScanner extends RuleBasedScanner {

	public CalSingleLineCommentScanner(ColorManager manager) {
		IToken commentToken = new Token(new TextAttribute(manager
				.getColor(ICalColorConstants.COMMENT)));

		List<IRule> rules = new ArrayList<IRule>();

		rules.add(new EndOfLineRule("//", commentToken));

		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
