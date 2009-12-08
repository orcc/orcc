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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * This class defines the configuration of a CAL editor. The configuration
 * consists in (1) the content types allowed in a CAL document, which are
 * default, and single-line, multi-line, javadoc comments (2) the presentation
 * reconcilier configured for these content types.
 * 
 * <p>
 * Each content type is associated with a scanner, though the scanner for
 * comments are presently empty. They may be extended if we decide to put
 * annotations in comments.
 * </p>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalConfiguration extends SourceViewerConfiguration {

	private ColorManager manager;

	private CalCodeScanner scanner;

	private CalMultiLineCommentScanner multiLineCommentScanner;

	private CalSingleLineCommentScanner singleLineCommentScanner;

	private CalJavadocCommentScanner javadocCommentScanner;

	/**
	 * Creates a new configuration based on the given color manager.
	 * 
	 * @param colorManager
	 *            a color manager
	 */
	public CalConfiguration(ColorManager colorManager) {
		this.manager = colorManager;

		scanner = new CalCodeScanner(manager);
		scanner.setDefaultReturnToken(new Token(new TextAttribute(manager
				.getColor(ICalColorConstants.DEFAULT))));

		javadocCommentScanner = new CalJavadocCommentScanner(manager);
		multiLineCommentScanner = new CalMultiLineCommentScanner(manager);
		singleLineCommentScanner = new CalSingleLineCommentScanner(manager);
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				CalPartitionScanner.CAL_JAVADOC_COMMENT,
				CalPartitionScanner.CAL_MULTI_LINE_COMMENT,
				CalPartitionScanner.CAL_SINGLE_LINE_COMMENT };
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr;

		dr = new DefaultDamagerRepairer(scanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(javadocCommentScanner);
		reconciler.setDamager(dr, CalPartitionScanner.CAL_JAVADOC_COMMENT);
		reconciler.setRepairer(dr, CalPartitionScanner.CAL_JAVADOC_COMMENT);

		dr = new DefaultDamagerRepairer(multiLineCommentScanner);
		reconciler.setDamager(dr, CalPartitionScanner.CAL_MULTI_LINE_COMMENT);
		reconciler.setRepairer(dr, CalPartitionScanner.CAL_MULTI_LINE_COMMENT);

		dr = new DefaultDamagerRepairer(singleLineCommentScanner);
		reconciler.setDamager(dr, CalPartitionScanner.CAL_SINGLE_LINE_COMMENT);
		reconciler.setRepairer(dr, CalPartitionScanner.CAL_SINGLE_LINE_COMMENT);

		return reconciler;
	}

}
