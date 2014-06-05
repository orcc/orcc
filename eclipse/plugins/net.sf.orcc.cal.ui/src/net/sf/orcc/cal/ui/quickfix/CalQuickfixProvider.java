/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.ui.quickfix;

import net.sf.orcc.cal.CalDiagnostic;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.Issue;

/**
 * This class
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalQuickfixProvider extends DefaultQuickfixProvider {

	@Fix(CalDiagnostic.ERROR_LIST)
	public void capitalizeList(final Issue issue,
			IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Capitalize name", "Capitalize name of type",
				"upcase.png", new IModification() {
					public void apply(IModificationContext context)
							throws BadLocationException {
						IXtextDocument xtextDocument = context
								.getXtextDocument();
						String firstLetter = xtextDocument.get(
								issue.getOffset(), 1);
						xtextDocument.replace(issue.getOffset(), 1,
								Strings.toFirstUpper(firstLetter));
					}
				});
	}

	@Fix(CalDiagnostic.ERROR_NAME)
	public void correctEntityName(final Issue issue,
			IssueResolutionAcceptor acceptor) {
		String packageName = issue.getData()[0];
		final String expectedName = issue.getData()[1];

		String message = "Change name from " + packageName + " to "
				+ expectedName;
		acceptor.accept(issue, message, message, "upcase.png",
				new IModification() {

					@Override
					public void apply(IModificationContext context)
							throws Exception {
						IXtextDocument document = context.getXtextDocument();
						document.replace(issue.getOffset(), issue.getLength(),
								expectedName);
					}
				});
	}

	@Fix(CalDiagnostic.ERROR_MISSING_PACKAGE)
	public void correctEntityMissingPackage(final Issue issue,
			IssueResolutionAcceptor acceptor) {
		final String expectedName = issue.getData()[1];

		String message = "Add package " + expectedName;
		acceptor.accept(issue, message, message, "upcase.png",
				new IModification() {

					@Override
					public void apply(IModificationContext context)
							throws Exception {
						IXtextDocument document = context.getXtextDocument();

						int offset = issue.getOffset();
						int length = issue.getLength();

						String text = "package " + expectedName + ";\n\n"
								+ document.get(offset, length);
						document.replace(offset, length, text);
					}
				});
	}

	@Fix(CalDiagnostic.ERROR_PACKAGE)
	public void correctEntityPackage(final Issue issue,
			IssueResolutionAcceptor acceptor) {
		String packageName = issue.getData()[0];
		final String expectedName = issue.getData()[1];

		String message = "Change name from " + packageName + " to "
				+ expectedName;
		acceptor.accept(issue, message, message, "upcase.png",
				new IModification() {

					@Override
					public void apply(IModificationContext context)
							throws Exception {
						IXtextDocument document = context.getXtextDocument();
						document.replace(issue.getOffset(), issue.getLength(),
								expectedName);
					}
				});
	}

}
