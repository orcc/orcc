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

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * This class defines a basic partition scanner for CAL content. It looks for
 * javadoc, multi-line and single-line comments using rules. Note that this
 * could probably done faster, the JDT has a FastPartitionScanner for instance.
 * But do we really care? :)
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StgPartitionScanner extends RuleBasedPartitionScanner {

	/**
	 * The identifier of the comment partition content type.
	 */
	public static final String STG_COMMENT = "__stg_comment";

	/**
	 * The identifier of the template partition content type.
	 */
	public static final String STG_TEMPLATE = "__stg_template"; //$NON-NLS-1$

	/**
	 * Creates the partitioner and sets up the appropriate rules.
	 */
	public StgPartitionScanner() {
		super();

		IToken comment = new Token(STG_COMMENT);
		IToken template = new Token(STG_TEMPLATE);

		List<IRule> rules = new ArrayList<IRule>();

		// Add rule for comments.
		rules.add(new EndOfLineRule("//", comment)); //$NON-NLS-1$
		rules.add(new MultiLineRule("/*", "*/", comment)); //$NON-NLS-1$ //$NON-NLS-2$
		rules.add(new MultiLineRule("<<", ">>", template)); //$NON-NLS-1$ //$NON-NLS-2$

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}

}
