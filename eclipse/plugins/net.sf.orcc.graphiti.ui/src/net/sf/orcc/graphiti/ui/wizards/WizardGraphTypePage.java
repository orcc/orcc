/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.wizards;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.orcc.graphiti.GraphitiModelPlugin;
import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.ObjectType;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class provides a page for the new graph wizard.
 * 
 * @author Matthieu Wipliez
 */
public class WizardGraphTypePage extends WizardPage {

	private Map<ObjectType, Configuration> graphTypeConfigurations;

	private Map<String, ObjectType> graphTypeNames;

	private Combo listGraphTypes;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param selection
	 */
	public WizardGraphTypePage(IStructuredSelection selection) {
		super("graphType");

		setTitle("Choose graph type");
		fillGraphTypes();
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		layout.verticalSpacing = 9;

		createGraphTypes(container);

		setControl(container);
		setPageComplete(false);
	}

	/**
	 * Creates the {@link #listGraphTypes} component from the graph types in
	 * {@link #graphTypes}.
	 * 
	 * @param parent
	 *            The parent Composite.
	 */
	private void createGraphTypes(Composite parent) {
		Label label = new Label(parent, SWT.NULL);
		label.setText("&Graph type:");

		listGraphTypes = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY
				| SWT.SIMPLE);
		Set<String> typeNames = new TreeSet<String>();
		typeNames.addAll(graphTypeNames.keySet());

		String[] items = typeNames.toArray(new String[] {});
		listGraphTypes.setItems(items);
		listGraphTypes.select(-1);

		listGraphTypes.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);

				IWizard wizard = getWizard();
				IWizardPage page = wizard.getNextPage(WizardGraphTypePage.this);
				updateSelection((IGraphTypeSettable) page);
			}

		});
	}

	/**
	 * Fills the {@link #graphTypes} attribute using the default configuration.
	 */
	private void fillGraphTypes() {
		Collection<Configuration> configurations = GraphitiModelPlugin
				.getDefault().getConfigurations();
		graphTypeConfigurations = new HashMap<ObjectType, Configuration>();
		graphTypeNames = new HashMap<String, ObjectType>();

		for (Configuration configuration : configurations) {
			Set<ObjectType> graphTypes = configuration.getGraphTypes();
			for (ObjectType type : graphTypes) {
				if (!configuration.getFileFormat().getExportTransformations()
						.isEmpty()) {
					// only add graph types that can be exported

					graphTypeConfigurations.put(type, configuration);
					String fileExt = configuration.getFileFormat()
							.getFileExtension();
					graphTypeNames.put(type.getName() + " (*." + fileExt + ")",
							type);
				}
			}
		}
	}

	/**
	 * Calls {@link IGraphTypeSettable#setGraphType(Configuration, GraphType)}
	 * on the given page with the selected graph type and associated
	 * configuration.
	 * 
	 * @param page
	 *            An {@link IGraphTypeSettable} page.
	 */
	private void updateSelection(IGraphTypeSettable page) {
		int index = listGraphTypes.getSelectionIndex();
		String graphType = listGraphTypes.getItem(index);

		ObjectType type = graphTypeNames.get(graphType);
		Configuration configuration = graphTypeConfigurations.get(type);
		page.setGraphType(configuration, type);
	}

}