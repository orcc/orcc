package net.sf.orcc.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class CalPartitionScanner extends RuleBasedPartitionScanner {

	/**
	 * The identifier of the single-line end comment partition content type.
	 */
	public static final String CAL_SINGLE_LINE_COMMENT = "__cal_singleline_comment"; //$NON-NLS-1$

	/**
	 * The identifier multi-line comment partition content type.
	 */
	public static final String CAL_MULTI_LINE_COMMENT = "__cal_multiline_comment"; //$NON-NLS-1$

	/**
	 * Creates the partitioner and sets up the appropriate rules.
	 */
	public CalPartitionScanner() {
		super();

		IToken singleComment = new Token(CAL_SINGLE_LINE_COMMENT);
		IToken multiComment = new Token(CAL_MULTI_LINE_COMMENT);

		List<IRule> rules = new ArrayList<IRule>();

		// Add rule for single line comments.
		rules.add(new EndOfLineRule("//", singleComment)); //$NON-NLS-1$
		rules.add(new MultiLineRule("/*", "*/", multiComment)); //$NON-NLS-1$ //$NON-NLS-2$

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}
}
