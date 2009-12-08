package net.sf.orcc.ui.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class CalConfiguration extends SourceViewerConfiguration {

	private ColorManager colorManager;

	private CalCodeScanner scanner;

	public CalConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	protected CalCodeScanner getCalCodeScanner() {
		if (scanner == null) {
			scanner = new CalCodeScanner(colorManager);
			scanner.setDefaultReturnToken(new Token(new TextAttribute(
					colorManager.getColor(ICalColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				CalPartitionScanner.CAL_MULTI_LINE_COMMENT,
				CalPartitionScanner.CAL_SINGLE_LINE_COMMENT };
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr;

		dr = new DefaultDamagerRepairer(getCalCodeScanner());
		reconciler.setDamager(dr, CalPartitionScanner.CAL_MULTI_LINE_COMMENT);
		reconciler.setRepairer(dr, CalPartitionScanner.CAL_MULTI_LINE_COMMENT);

		dr = new DefaultDamagerRepairer(/*new CalSingleLineCommentScanner(
				colorManager)*/getCalCodeScanner());
		reconciler.setDamager(dr, CalPartitionScanner.CAL_SINGLE_LINE_COMMENT);
		reconciler.setRepairer(dr, CalPartitionScanner.CAL_SINGLE_LINE_COMMENT);

		dr = new DefaultDamagerRepairer(/*new CalMultiLineCommentScanner(
				colorManager)*/getCalCodeScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

}