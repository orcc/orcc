/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.ui.launching.tabs;

import static net.sf.orcc.OrccLaunchConstants.MAPPING;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ui.OrccUiActivator;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * This class defines a tab for mapping a network onto an architecture.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @author Endri Bezati
 * 
 */
public class MappingTab extends AbstractLaunchConfigurationTab {

	/**
	 * This class provides content for the mapping tree.
	 * 
	 * @author Matthieu Wipliez
	 * @author Herve Yviquel
	 * 
	 */
	private static class TreeContentProvider implements ITreeContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Network) {
				Network network = (Network) inputElement;
				EList<Vertex> vertices = new BasicEList<Vertex>(network.getChildren());
				return vertices.toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Network) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	/**
	 * This class defines editing support for the "component" column.
	 * 
	 * @author Matthieu Wipliez
	 * @author Herve Yviquel
	 * 
	 */
	private class TreeEditingSupport extends EditingSupport {

		private final CellEditor editor;

		public TreeEditingSupport() {
			super(viewer);
			editor = new TextCellEditor(viewer.getTree());
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			String component = labelProvider.getColumnText(element, 1);
			if (component != null) {
				return component;
			}
			return "";
		}

		private void setMapping(Vertex vertex, String component) {
			if (vertex instanceof Instance) {
				Instance instance = (Instance) vertex;
				mapping.put(instance.getHierarchicalName(), component);
			} else if (vertex instanceof Network) {
				Network network = (Network) vertex;
				mapping.put(network.getName(), component);
				for (Vertex subEntity : network.getChildren()) {
					setMapping(subEntity, component);
				}
			}
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof Vertex) {
				Vertex vertex = (Vertex) element;
				String component = (String) value;
				if (component == null || component.contains(",")) {
					return;
				}
				setMapping(vertex, component);
				getViewer().refresh();
				updateLaunchConfigurationDialog();
			}
		}

	}

	/**
	 * This class provides labels for the mapping tree.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class TreeLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return EcoreHelper.getFeature((Vertex) element, "name");
			} else {
				if (element instanceof Instance) {
					Instance instance = (Instance) element;
					return mapping.get(instance.getHierarchicalName());
				}
				if (element instanceof Network) {
					Network network = (Network) element;
					Set<String> subComponents = new TreeSet<String>();
					getComponents(subComponents, network);
					return OrccUtil.toString(subComponents, ", ");
				}
			}
			return null;
		}

		/**
		 * Fills the components set with all the components that instance and if
		 * instance is a network, its sub-instances too, are mapped to.
		 * 
		 * @param components
		 *            a set of components
		 * @param instance
		 *            an instance
		 */
		private void getComponents(Set<String> components, Network network) {
			for (Vertex entity : network.getChildren()) {
				Instance instance = entity.getAdapter(Instance.class);
				if (instance == null) {
					Network subNetwork = entity.getAdapter(Network.class);
					getComponents(components, subNetwork);
				} else {
					String component = mapping.get(instance.getHierarchicalName());
					if (component != null) {
						components.add(component);
					}
				}
			}
		}

		@Override
		public String getText(Object element) {
			// only getColumnText should be called
			return "";
		}

	}

	/**
	 * This class defines an XCF XML parser
	 * 
	 * @author Endri Bezati
	 *
	 */
	private static class XcfParser {
		public static final String ACTOR = "Instance";
		public static final String ACTOR_ID = "id";
		public static final String PARTITION = "Partition";
		public static final String PARTITION_ID = "id";

		Map<String, String> parse(String fileName) {
			Map<String, String> xcfMapping = new HashMap<String, String>();
			File file = new File(fileName);
			XMLStreamReader reader = null;
			try {
				InputStream stream = new BufferedInputStream(new FileInputStream(file));
				reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
			} catch (Exception e) {
				// Do nothing
			}

			String component = null;
			String actor = null;
			try {
				try {
					while (reader.hasNext()) {
						reader.next();
						switch (reader.getEventType()) {
						case XMLEvent.START_ELEMENT: {
							String xmlElement = reader.getName().toString();
							if (xmlElement.equals(PARTITION)) {
								component = reader.getAttributeValue("", PARTITION_ID);
								if (component == null) {
									throw new OrccException("Parsing error in \"" + file.getAbsolutePath()
											+ "\": component name specified. Line "
											+ reader.getLocation().getLineNumber());
								}
							} else if (xmlElement.equals(ACTOR)) {
								actor = reader.getAttributeValue("", ACTOR_ID);
								if (actor == null) {
									throw new OrccException("Parsing error in \"" + file.getAbsolutePath()
											+ "\": actor name not specified. Line "
											+ reader.getLocation().getLineNumber());
								}

								xcfMapping.put(actor, component);
							}
							break;
						}
						case XMLEvent.END_ELEMENT: {
							String xmlElement = reader.getName().toString();
							if (xmlElement.equals(PARTITION)) {
								component = null;
							} else if (xmlElement.equals(ACTOR)) {
								actor = null;
							}
							break;
						}
						}
					}
				} catch (XMLStreamException e1) {
					e1.printStackTrace();
				}
				try {
					reader.close();
				} catch (Exception e) {
					OrccLogger.warnln("File \"%s\" has not been properly closed" + file);
				}
			} catch (OrccException e) {
				OrccLogger.warnln("Error parsing the XML file");

			}

			// Give the hierarchical name
			Map<String, String> hierarchicalMapping = new HashMap<String, String>();
			for (Vertex child : network.getChildren()) {
				Instance instance = child.getAdapter(Instance.class);
				if (instance != null) {
					for (String key : xcfMapping.keySet()) {
						if (instance.getHierarchicalName().contains(key)) {
							hierarchicalMapping.put(instance.getHierarchicalName(), xcfMapping.get(key));
						}
					}
				}
			}

			for (Network subNetwork : network.getAllNetworks()) {
				for (Vertex child : subNetwork.getChildren()) {
					Instance instance = child.getAdapter(Instance.class);
					if (instance != null) {
						for (String key : xcfMapping.keySet()) {
							if (instance.getHierarchicalName().contains(key)) {
								hierarchicalMapping.put(instance.getHierarchicalName(), xcfMapping.get(key));
							}
						}
					}
				}
			}

			return hierarchicalMapping;
		}

	}

	private static Network network;

	private IContentProvider contentProvider;

	private ITableLabelProvider labelProvider;

	private Map<String, String> mapping;

	private Text text;

	private String value;

	private TreeViewer viewer;

	/**
	 * Browses the file system.
	 * 
	 * @param shell
	 *            a shell
	 */
	private void browseFileSystem(Shell shell) {
		String file;

		FileDialog dialog = new FileDialog(shell, SWT.OPEN);

		String extension = "*.xcf";
		if (extension != null) {
			dialog.setFilterExtensions(new String[] { extension });
		}

		// set initial directory
		dialog.setFilterPath(value);

		file = dialog.open();

		if (file != null) {
			text.setText(file);
		}
		XcfParser parser = new XcfParser();
		mapping = parser.parse(file);

		viewer.refresh();
		updateLaunchConfigurationDialog();
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);

		Font font = parent.getFont();
		composite.setFont(font);

		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(data);

		createLabel(composite);
		createTreeViewer(composite);
		createXCFInputFile(composite);
	}

	private void createLabel(Composite composite) {
		String html = "Warning: the mapping feature is experimental and likely to evolve.\n"
				+ "Furthermore, the name of the components is implementation-specific, "
				+ "and is likely to change in the future.\n" + "A component should be named like an identifier"
				+ " (only letters, digits, underscore allowed).\n\n";

		Label label = new Label(composite, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.TOP, false, false);
		label.setLayoutData(data);

		label.setText(html);
	}

	/**
	 * Creates the tree viewer on the given composite. The tree has two columns,
	 * and the right column has editing support so we can modify the components.
	 * 
	 * @param composite
	 */
	private void createTreeViewer(Composite composite) {
		Tree tree = new Tree(composite, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		tree.setLayoutData(data);

		tree.setHeaderVisible(true);

		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText("instance");
		column.setWidth(300);

		column = new TreeColumn(tree, SWT.RIGHT);
		column.setText("component");
		column.setWidth(100);

		viewer = new TreeViewer(tree);
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, column);
		viewerColumn.setEditingSupport(new TreeEditingSupport());

		contentProvider = new TreeContentProvider();
		viewer.setContentProvider(contentProvider);

		labelProvider = new TreeLabelProvider();
		viewer.setLabelProvider(labelProvider);
	}

	private void createXCFInputFile(final Composite composite) {
		Composite comp = new Composite(composite, SWT.NONE);
		comp.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		comp.setLayoutData(data);

		Font font = composite.getFont();
		Label lbl = new Label(comp, SWT.NONE);
		lbl.setFont(font);
		lbl.setText("XCF Mapping File" + ":");
		lbl.setToolTipText("An XCF Mapping file containing the mapping configuration.");
		data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);

		text = new Text(comp, SWT.BORDER | SWT.SINGLE);
		text.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		text.setLayoutData(data);
		text.setText("");
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				value = text.getText();

			}
		});

		Button buttonBrowse = new Button(comp, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseFileSystem(composite.getShell());
			}
		});
	}

	@Override
	public Image getImage() {
		return OrccUiActivator.getImage("icons/orcc.png");
	}

	@Override
	public String getName() {
		return "Mapping";
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initializeFrom(ILaunchConfiguration configuration) {
		IFile xdfFile = null;
		try {
			mapping = new HashMap<String, String>(configuration.getAttribute(MAPPING, Collections.EMPTY_MAP));

			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			String name = configuration.getAttribute(PROJECT, "");
			if (root.getFullPath().isValidSegment(name)) {
				IProject project = root.getProject(name);
				if (project.exists()) {
					xdfFile = OrccUtil.getFile(project, configuration.getAttribute(XDF_FILE, ""),
							OrccUtil.NETWORK_SUFFIX);
				}
			}
		} catch (CoreException e) {
			mapping = new HashMap<String, String>(0);
			e.printStackTrace();
		}

		if (xdfFile != null) {
			ResourceSet set = new ResourceSetImpl();
			network = EcoreHelper.getEObject(set, xdfFile);
			new Instantiator(false).doSwitch(network);

			Set<String> instances = new HashSet<String>();
			for (Vertex child : network.getChildren()) {
				Instance instance = child.getAdapter(Instance.class);
				if (instance != null) {
					instances.add(instance.getHierarchicalName());
				}
			}
			for (Network subNetwork : network.getAllNetworks()) {
				for (Vertex child : subNetwork.getChildren()) {
					Instance instance = child.getAdapter(Instance.class);
					if (instance != null) {
						instances.add(instance.getHierarchicalName());
					}
				}
			}

			Iterator<Entry<String, String>> it = mapping.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				if (!instances.contains(entry.getKey())) {
					it.remove();
				}
			}
		}

		viewer.setInput(network);
		viewer.refresh();
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (isValid(configuration)) {
			configuration.setAttribute(MAPPING, new HashMap<String, String>(mapping));
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(MAPPING, new HashMap<String, String>());
	}

}
