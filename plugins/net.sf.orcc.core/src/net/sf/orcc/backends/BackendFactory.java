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
package net.sf.orcc.backends;

import static net.sf.orcc.OrccLaunchConstants.BACKEND;
import static net.sf.orcc.OrccLaunchConstants.COMPILE_XDF;
import static net.sf.orcc.OrccLaunchConstants.OUTPUT_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.backends.impl.BackendOptionImpl;
import net.sf.orcc.backends.impl.BrowseFileOptionImpl;
import net.sf.orcc.backends.impl.CheckboxOptionImpl;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.ui.OrccActivator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * A factory class that contains a list of back-ends and their options. The
 * back-ends are classes that implement the {@link IBackend} interface and are
 * declared in the <code>net.sf.orcc.plugin.backend</code> extension point.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * 
 */
public class BackendFactory {

	private static final BackendFactory instance = new BackendFactory();

	/**
	 * Returns the single instance of this factory
	 * 
	 * @return the single instance of this factory
	 */
	public static BackendFactory getInstance() {
		return instance;
	}

	/**
	 * list of options of a back-end.
	 */
	private Map<String, List<BackendOption>> backendOptions;

	/**
	 * list of back-ends.
	 */
	private final Map<String, Backend> backends;

	/**
	 * list of options.
	 */
	private Map<String, BackendOption> options;

	/**
	 * private constructor called when this class is loaded and instance is
	 * initialized
	 */
	private BackendFactory() {
		backends = new TreeMap<String, Backend>();
		backendOptions = new HashMap<String, List<BackendOption>>();
		options = new HashMap<String, BackendOption>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(OrccActivator.PLUGIN_ID
						+ ".options");

		parseOptions(elements);
		elements = registry.getConfigurationElementsFor(OrccActivator.PLUGIN_ID
				+ ".backends");

		parseBackends(elements);
	}

	/**
	 * Returns the list of options associated to the back-end with the given
	 * name.
	 * 
	 * @return associated to the backend
	 */
	public List<BackendOption> getOptions(String name) {
		return backendOptions.get(name);
	}

	/**
	 * Indicate if a backend contains the "deactivate front-end" option.
	 * 
	 * @return true if "deactivate front-end" is present otherwise false
	 * @throws CoreException
	 *             if something goes wrong
	 */
	public boolean hasFEOption(String backend) throws CoreException {
		List<BackendOption> options = getOptions(backend);
		if (options != null) {
			// return the identifier of the first "browseFile" option
			for (BackendOption option : options) {
				if (option instanceof BackendOptionImpl) {
					String id = option.getIdentifier();
					if (id.equals("net.sf.orcc.backends.deactivateFE")) {
						return true;
					}
				}
			}
		}

		// Desactive FE is not present return false
		return false;
	}

	/**
	 * Returns the list of the names of registered back-ends
	 * 
	 * @return the list of the names of registered back-ends
	 */
	public List<String> listBackends() {
		return new ArrayList<String>(backends.keySet());
	}

	/**
	 * Returns the list of options referenced by the list of "option" elements.
	 * 
	 * @param elements
	 *            a list of "option" elements
	 * @return a list of options
	 */
	private List<BackendOption> parseBackendOptions(
			IConfigurationElement[] elements) {
		List<BackendOption> backendOptions = new ArrayList<BackendOption>();
		for (IConfigurationElement element : elements) {
			String id = element.getAttribute("id");
			BackendOption option = options.get(id);
			backendOptions.add(option);
		}

		return backendOptions;
	}

	/**
	 * Parses the "backend" elements as back-ends.
	 * 
	 * @param elements
	 *            a list of "backend" elements
	 */
	private void parseBackends(IConfigurationElement[] elements) {
		for (IConfigurationElement element : elements) {
			String name = element.getAttribute("name");
			IConfigurationElement[] optionLists = element.getChildren();
			List<BackendOption> options = parseBackendOptions(optionLists);
			this.backendOptions.put(name, options);

			try {
				Object obj = element.createExecutableExtension("class");
				backends.put(name, (Backend) obj);
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
	private BrowseFileOption parseBrowseFile(IConfigurationElement element) {
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
	private CheckboxOption parseCheckbox(IConfigurationElement element) {
		CheckboxOption option = new CheckboxOptionImpl();
		List<BackendOption> options = parseOptions(element.getChildren());
		option.setOptions(options);
		return option;
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
	private List<BackendOption> parseOptions(IConfigurationElement[] elements) {
		List<BackendOption> options = new ArrayList<BackendOption>();
		for (IConfigurationElement element : elements) {
			BackendOption option;

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
				} else {
					continue;
				}
			} else {
				if (id.equals("net.sf.orcc.backends.deactivateFE")) {
					option = new BackendOptionImpl();
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

	/**
	 * Runs the back-end specified by the configuration.
	 * 
	 * @param process
	 *            the process that launched the back-end
	 * @param configuration
	 *            launch configuration
	 * @throws Exception
	 */
	public void runBackend(OrccProcess process,
			ILaunchConfiguration configuration) throws Exception {
		String outputFolder = configuration.getAttribute(OUTPUT_FOLDER, "");
		String backend = configuration.getAttribute(BACKEND, "");

		Backend backendObj = backends.get(backend);
		backendObj.setLaunchConfiguration(configuration);

		// always compile VTL.
		// an actor is only compiled if it needs to (based on modification date)
		backendObj.compileVTL(process, outputFolder);

		if (configuration.getAttribute(COMPILE_XDF, false)) {
			String xdfFile = configuration.getAttribute(XDF_FILE, "");
			backendObj.compileXDF(process, xdfFile, outputFolder);
		}
	}

}
