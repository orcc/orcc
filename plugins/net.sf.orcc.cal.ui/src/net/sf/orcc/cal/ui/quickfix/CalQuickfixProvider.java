
package net.sf.orcc.cal.ui.quickfix;

import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;

public class CalQuickfixProvider extends DefaultQuickfixProvider {
	
	// org.eclipse.xtext.ui.editor.quickfix.Fix
	// org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor
	// org.eclipse.xtext.validation.Issue

//	@Fix(MyJavaValidator.INVALID_TYPE_NAME)
//	public void capitalizeName(final Issue issue, IssueResolutionAcceptor acceptor) {
//		acceptor.accept(issue, "Capitalize name", "Capitalize name of type", "upcase.png", new IModification() {
//			public void apply(IModificationContext context) throws BadLocationException {
//				IXtextDocument xtextDocument = context.getXtextDocument();
//				String firstLetter = xtextDocument.get(issue.getOffset(), 1);
//				xtextDocument.replace(issue.getOffset(), 1, Strings.toFirstUpper(firstLetter));
//			}
//		});
//	}

}
