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

import static net.sf.orcc.OrccActivator.PLUGIN_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.plugins.impl.ComboboxItemImpl;
import net.sf.orcc.plugins.impl.OptionBrowseFileImpl;
import net.sf.orcc.plugins.impl.OptionCheckboxImpl;
import net.sf.orcc.plugins.impl.OptionComboboxImpl;
import net.sf.orcc.plugins.impl.OptionSelectNetworkImpl;
import net.sf.orcc.plugins.impl.OptionSelectNetworksImpl;
import net.sf.orcc.plugins.impl.OptionTextBoxImpl;
import net.sf.orcc.plugins.impl.PluginOptionImpl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * A factory class that contains a list of plugins and their options. The
 * plugins are classes that implement either the {@link Backend} or the
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
	 * list of options.
	 */
	protected Map<String, Option> options;

	/**
	 * list of options of a plugin.
	 */
	protected Map<String, List<Option>> pluginOptions;

	/**
	 * list of plugins.
	 */
	protected final Map<String, Object> plugins;

	private IConfigurationElement[] elements;

	protected PluginFactory() {
		plugins = new TreeMap<String, Object>();

		pluginOptions = new HashMap<String, List<Option>>();
		options = new HashMap<String, Option>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		elements = registry.getConfigurationElementsFor(PLUGIN_ID + ".options");

		parseOptions(elements);
	}

	/**
	 * Returns the list of options associated to the plugin with the given name.
	 * 
	 * @return associated to the plugin
	 */
	public List<Option> getOptions(String name) {
		return pluginOptions.get(name);
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
	 * Parses the given configuration element as an "input file" option.
	 * 
	 * @param element
	 *            a configuration element
	 * @return an "input file" option
	 */
	private OptionBrowseFile parseBrowseFile(IConfigurationElement element) {
		String extension = element.getAttribute("extension");
		OptionBrowseFile option = new OptionBrowseFileImpl();
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
	private OptionCheckbox parseCheckbox(IConfigurationElement element) {
		OptionCheckbox option = new OptionCheckboxImpl();
		List<Option> options = parseOptions(element.getChildren());
		option.getOptions().addAll(options);
		return option;
	}

	/**
	 * t Parses the given configuration element as a "combobox" option.
	 * 
	 * @param element
	 *            a configuration element
	 * @return a "combobox" option
	 */
	private OptionComboBox parseCombobox(IConfigurationElement element) {
		OptionComboBox option = new OptionComboboxImpl();
		List<ComboBoxItem> items = parseItems(element.getChildren());
		option.getComboBoxItems().addAll(items);
		return option;
	}

	private List<ComboBoxItem> parseItems(IConfigurationElement[] elements) {
		List<ComboBoxItem> items = new ArrayList<ComboBoxItem>();
		for (IConfigurationElement element : elements) {
			ComboboxItemImpl comboBoxItem = new ComboboxItemImpl();

			// Parse id of combo item
			String id = element.getAttribute("id");
			comboBoxItem.setId(id);

			// Parse children options of comboBox
			List<Option> options = parseOptions(element.getChildren());
			comboBoxItem.getOptions().addAll(options);

			items.add(comboBoxItem);
		}

		return items;
	}

	/**
	 * Parses the given configuration elements as a list of options. The options
	 * are added to the option map of this factory.
	 * 
	 * @param elements
	 *            a list of configuration elements
	 */
	protected List<Option> parseOptions(IConfigurationElement[] elements) {
		List<Option> optionList = new ArrayList<Option>();
		for (IConfigurationElement element : elements) {
			if ("option".equals(element.getName())) {
				Option option = parseOption(element);
				optionList.add(option);
			} else if ("optionRef".equals(element.getName())) {
				String id = element.getAttribute("id");
				Option option = options.get(id);
				if (option == null) {
					option = parseOption(id);
				}
				optionList.add(option);
			}
		}

		return optionList;
	}

	/**
	 * Parses an option with the given id. I know that this is a horrible way of
	 * handling cross-referencing options, hopefully at some point someone will
	 * do better.
	 * 
	 * @param id
	 * @return
	 */
	private Option parseOption(String id) {
		for (IConfigurationElement element : elements) {
			if (id.equals(element.getAttribute("id"))) {
				return parseOption(element);
			}
		}

		return null;
	}

	private Option parseOption(IConfigurationElement element) {
		Option option;
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
			} else if (type.equals("selectNetwork")) {
				option = new OptionSelectNetworkImpl();
			} else if (type.equals("selectNetworks")) {
				option = new OptionSelectNetworksImpl();
			} else if (type.equals("textBox")) {
				option = parseTexbox(child);
			} else {
				return null;
			}
		} else {
			if ((id.equals("net.sf.orcc.plugins.backends.deactivateFE"))
					|| (id.equals("net.sf.orcc.plugins.simulators.deactivateFE"))) {
				option = new PluginOptionImpl();
			} else {
				return null;
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

		options.put(option.getIdentifier(), option);

		return option;
	}

	/**
	 * Returns the list of options referenced by the list of "option" elements.
	 * 
	 * @param elements
	 *            a list of "option" elements
	 * @return a list of options
	 */
	private List<Option> parsePluginOptions(IConfigurationElement[] elements) {
		List<Option> pluginOptions = new ArrayList<Option>();
		for (IConfigurationElement element : elements) {
			String id = element.getAttribute("id");
			Option option = options.get(id);
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
			List<Option> options = parsePluginOptions(optionLists);
			this.pluginOptions.put(name, options);

			try {
				Object obj = element.createExecutableExtension("class");
				plugins.put(name, obj);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parses the given configuration element as a "textbox" option.
	 * 
	 * @param element
	 *            a configuration element
	 * @return a "textbox" option
	 */
	private OptionTextBox parseTexbox(IConfigurationElement element) {
		OptionTextBox option = new OptionTextBoxImpl();
		List<Option> options = parseOptions(element.getChildren());
		option.getOptions().addAll(options);
		return option;
	}

}
