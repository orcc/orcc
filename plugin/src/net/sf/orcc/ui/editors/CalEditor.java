package net.sf.orcc.ui.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class CalEditor extends TextEditor {

	private ColorManager colorManager;

	public CalEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new CalConfiguration(colorManager));
		setDocumentProvider(new CalDocumentProvider());
	}

	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
