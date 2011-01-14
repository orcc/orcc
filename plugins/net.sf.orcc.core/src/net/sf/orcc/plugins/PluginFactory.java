/*
 * Copyright (c) 2009/2010, IETR/INSA of Rennes
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
package net.sf.orcc.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.plugins.backends.Backend;
import net.sf.orcc.plugins.impl.BrowseFileOptionImpl;
import net.sf.orcc.plugins.impl.CheckboxOptionImpl;
import net.sf.orcc.plugins.impl.ComboboxItemImpl;
import net.sf.orcc.plugins.impl.ComboboxOptionImpl;
import net.sf.orcc.plugins.impl.PluginOptionImpl;
import net.sf.orcc.plugins.impl.TextBoxOptionImpl;
import net.sf.orcc.plugins.simulators.Simulator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * A factory class that contains a list of plugins and their options. The
 * pluginss are classes that implement either the {@link Backend} or the
 * {@link Simulator} interface and are declared in either the
 * <code>net.sf.orcc.plugin.backend</code> or the
 * <code>net.sf.orcc.plugin.simulator</code> extension point.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class PluginFactory {

	/**
	 * list of options of a plugin.
	 */
	protected Map<String, List<PluginOption>> pluginOptions;

	/**
	 * list of plugins.
	 */
	protected final Map<String, Plugin> plugins = new TreeMap<String, Plugin>();

	/**
	 * list of options.
	 */
	protected Map<String, PluginOption> options;

	/**
	 * Returns the list of options associated to the plugin with the given name.
	 * 
	 * @return associated to the plugin
	 */
	public List<PluginOption> getOptions(String name) {
		return pluginOptions.get(name);
	}

	/**
	 * Indicate if a plugin contains the "deactivate front-end" option.
	 * 
	 * @return true if "deactivate front-end" is present otherwise false
	 * @throws CoreException
	 *             if something goes wrong
	 */
	public boolean hasFEOption(String plugin) throws CoreException {
		List<PluginOption> options = getOptions(plugin);
		if (options != null) {
			// return the identifier of the first "browseFile" option
			for (PluginOption option : options) {
				if (option instanceof PluginOptionImpl) {
					String id = option.getIdentifier();
					if ((id.equals("net.sf.orcc.plugins.backends.deactivateFE"))
							|| (id.equals("net.sf.orcc.plugins.simulators.deactivateFE"))) {
						return true;
					}
				}
			}
		}
		// Desactive FE is not present return false
		return false;
	}

	/**
	 * Returns the list of the names of registered backend or simulator plugins
	 * 
	 * @return the list of the names of registered backend or simulator plugins
	 */
	public List<String> listPlugins() {
		return new ArrayList<String>(plugins.keySet());
	}

	/**
	 * Returns the list of options referenced by the list of "option" elements.
	 * 
	 * @param elements
	 *            a list of "option" elements
	 * @return a list of options
	 */
	protected List<PluginOption> parsePluginOptions(
			IConfigurationElement[] elements) {
		List<PluginOption> pluginOptions = new ArrayList<PluginOption>();
		for (IConfigurationElement element : elements) {
			String id = element.getAttribute("id");
			PluginOption option = options.get(id);
			pluginOptions.add(option);
		}

		return pluginOptions;
	}

	/**
	 * Parses the "plugin" elements as plugins.
	 * 
	 * @param elements
	 *            a list of "plugin" elements
	 */
	protected void parsePlugins(IConfigurationElement[] elements) {
		for (IConfigurationElement element : elements) {
			String name = element.getAttribute("name");
			IConfigurationElement[] optionLists = element.getChildren();
			List<PluginOption> options = parsePluginOptions(optionLists);
			this.pluginOptions.put(name, options);

			try {
				Object obj = element.createExecutableExtension("class");
				plugins.put(name, (Plugin) obj);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parses the given configuration element as an "input file" option.
	 * 
	 * @param element
	 *            a configuration element
	 * @return an "input file" option
	 */
	protected BrowseFileOption parseBrowseFile(IConfigurationElement element) {
		String extension = element.getAttribute("extension");
		BrowseFileOption option = new BrowseFileOptionImpl();
		option.setExtension(extension);

		String folder = element.getAttribute("folder");
		option.setFolder(Boolean.parseBoolean(folder));

		String workspace = element.getAttribute("workspace");
		option.setWorkspace(Boolean.parseBoolean(workspace));
		return option;
	}

	/**
	 * Parses the given configuration element as a "checkbox" option.
	 * 
	 * @param element
	 *            a configuration element
	 * @return a "checkbox" option
	 */
	protected CheckboxOption parseCheckbox(IConfigurationElement element) {
		CheckboxOption option = new CheckboxOptionImpl();
		List<PluginOption> options = parseOptions(element.getChildren());
		option.setOptions(options);
		return option;
	}

	/**
	 * Parses the given configuration element as a "textbox" option.
	 * 
	 * @param element
	 *            a configuration element
	 * @return a "textbox" option
	 */
	protected TextBoxOption parseTexbox(IConfigurationElement element) {
		TextBoxOption option = new TextBoxOptionImpl();
		List<PluginOption> options = parseOptions(element.getChildren());
		option.setOptions(options);
		return option;
	}

	/**
	 * t Parses the given configuration element as a "combobox" option.
	 * 
	 * @param element
	 *            a configuration element
	 * @return a "combobox" option
	 */
	protected ComboBoxOption parseCombobox(IConfigurationElement element) {
		ComboBoxOption option = new ComboboxOptionImpl();
		List<ComboBoxItem> items = parseItems(element.getChildren());
		option.setComboBoxItems(items);
		return option;
	}

	protected List<ComboBoxItem> parseItems(IConfigurationElement[] elements) {
		List<ComboBoxItem> items = new ArrayList<ComboBoxItem>();
		for (IConfigurationElement element : elements) {
			ComboboxItemImpl comboBoxItem = new ComboboxItemImpl();

			// Parse id of combo item
			String id = element.getAttribute("id");
			comboBoxItem.setId(id);

			// Parse children options of comboBox
			List<PluginOption> options = parseOptions(element.getChildren());
			comboBoxItem.setOptions(options);

			items.add(comboBoxItem);
		}

		return items;
	}

	/**
	 * Parses the given configuration elements as a list of options. The options
	 * are added to the option map of this factory, and also returned as a list.
	 * This allows checkbox options to have sub-options.
	 * 
	 * @param elements
	 *            a list of configuration elements
	 * @return a list of options
	 */
	protected List<PluginOption> parseOptions(IConfigurationElement[] elements) {
		List<PluginOption> options = new ArrayList<PluginOption>();
		for (IConfigurationElement element : elements) {
			PluginOption option;

			String id = element.getAttribute("id");
			String name = element.getAttribute("name");

			IConfigurationElement[] children = element.getChildren();
			if (children.length > 0) {
				IConfigurationElement child = children[0];
				String type = child.getName();
				if (type.equals("browseFile")) {
					option = parseBrowseFile(child);
				} else if (type.equals("checkBox")) {
					option = parseCheckbox(child);
				} else if (type.equals("comboBox")) {
					option = parseCombobox(child);
				} else if (type.equals("textBox")) {
					option = parseTexbox(child);
				}

				else {
					continue;
				}
			} else {
				if ((id.equals("net.sf.orcc.plugins.backends.deactivateFE"))
						|| (id.equals("net.sf.orcc.plugins.simulators.deactivateFE"))) {
					option = new PluginOptionImpl();
				} else {
					continue;
				}
			}
			option.setIdentifier(id);
			option.setName(name);

			String defaultValue = element.getAttribute("defaultValue");
			if (defaultValue == null) {
				defaultValue = "";
			}
			option.setDefaultValue(defaultValue);

			String description = element.getAttribute("description");
			if (description == null) {
				description = "";
			}
			option.setDescription(description);

			this.options.put(id, option);
			options.add(option);
		}

		return options;
	}
}
