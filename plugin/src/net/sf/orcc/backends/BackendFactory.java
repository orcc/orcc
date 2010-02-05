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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.backends.options.AbstractOption;
import net.sf.orcc.backends.options.BrowseFileOption;
import net.sf.orcc.backends.options.CheckBoxOption;
import net.sf.orcc.backends.options.InputFileOption;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * A factory class that contains a list of back-ends and their options. The back-ends 
 * are classes that implement the {@link IBackend} interface and are declared in the
 * <code>net.sf.orcc.plugin.backend</code> extension point.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * 
 */
public class BackendFactory {

	/**
	 * list of options of a back-end.
	 */
	private static Map<String, AbstractOption[]> AbtractBackendOptions;

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
	 * Returns the given option from a all backends
	 * 
	 * @param optionName
	 * 		String containing the name of the option looking for
	 *
	 * @return AbtractOption[] associated to the option
	 */
	public static AbstractOption[] getOption(String optionName) {
		List<AbstractOption> optionsList = new ArrayList<AbstractOption>(); 
		Iterator<String> it = AbtractBackendOptions.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			AbstractOption[] abstractOptions = AbtractBackendOptions.get(key);
			
			for (AbstractOption abstractOption : abstractOptions){
				String[] options = abstractOption.getOption();
				for (String option : options){
					if (option.equals(optionName)){
						optionsList.add(abstractOption);
					}
				}
			}
			
		}
		
		return optionsList.toArray(new AbstractOption[]{});
	}
	
	/**
	 * Returns options of of the selected backend
	 * 
	 * @return AbtractOption[] associated to the backend
	 */
	public static AbstractOption[] getOptions(String name) {
		return AbtractBackendOptions.get(name);
	}

	/**
	 * Parse a list of IConfigurationElement
	 * 
	 * @return AbtractOption[] corresponding to the IConfigurationElement[]
	 */
	public static AbstractOption[] parseConfigurationElement(IConfigurationElement[] configurationElements){
		List<AbstractOption> backendOptions = new ArrayList<AbstractOption>();
	
		for (IConfigurationElement configurationElement :configurationElements){
			String elementName = configurationElement.getName();
			
			if (elementName.equals("browseFile")){
				String stringRequired = configurationElement.getAttribute("required");
				boolean required = Boolean.valueOf(stringRequired.toLowerCase());
				String option = configurationElement.getAttribute("name");
				String caption = configurationElement.getAttribute("caption");
				String defaultVal = configurationElement.getAttribute("default");
				if (defaultVal == null){
					defaultVal = new String("");				
				}
				String extension = configurationElement.getAttribute("extension");
				String stringWorkspace = configurationElement.getAttribute("workspace");
				boolean workspace = Boolean.valueOf(stringWorkspace.toLowerCase());
				backendOptions.add(new BrowseFileOption(option, caption, required, workspace, defaultVal, extension));

			}else if (elementName.equals("inputFile")){
				String caption = configurationElement.getAttribute("caption");
				String defaultVal = configurationElement.getAttribute("default");
				if (defaultVal == null){
					defaultVal = new String("");				
				}
				String extension = configurationElement.getAttribute("extension");
							
				backendOptions.add(new InputFileOption(caption, defaultVal, extension));
			}else if (elementName.equals("checkBox")){
				String caption = configurationElement.getAttribute("caption");
				String option = configurationElement.getAttribute("name");
				String defaultVal = configurationElement.getAttribute("defaultValue");
				if (defaultVal == null){
					defaultVal = new String("");				
				}
							
				backendOptions.add(new CheckBoxOption(option, caption, defaultVal, configurationElement.getChildren()));
			}			
		}
		
		return backendOptions.toArray(new AbstractOption[]{});
		
		
	}

	/**
	 * list of back-ends.
	 */
	private final Map<String, IBackend> backends;
	
	/**
	 * private constructor called when this class is loaded and instance is
	 * initialized
	 */
	private BackendFactory() {
		backends = new TreeMap<String, IBackend>();
		AbtractBackendOptions = new HashMap<String, AbstractOption[]>();
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor("net.sf.orcc.plugin.backend");
		for (IConfigurationElement element : elements) {
			String name = element.getAttribute("name");
			IConfigurationElement[] optionLists = element.getChildren();
			for (IConfigurationElement optionList : optionLists) {
				IConfigurationElement[] optionElements = optionList.getChildren();
				AbstractOption[] backendOptions = parseConfigurationElement(optionElements);
				
				AbtractBackendOptions.put(name, backendOptions);
			}
			
			try {
				Object obj = element.createExecutableExtension("class");
				backends.put(name, (IBackend) obj);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
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
	 * Runs the given back-end on the given input file with the given FIFO size.
	 * 
	 * @param name
	 *            back-end name. Must belong to the list returned by
	 *            {@link #listBackends()}
	 * @param fileName
	 *            name of top-level input network
	 * @param fifoSize
	 *            default size of FIFOs
	 * @throws Exception
	 */
	public void runBackend(String name, String fileName, int fifoSize)
			throws Exception {
		IBackend backend = backends.get(name);	
		AbstractOption[] abstractOptions = AbtractBackendOptions.get(name);
		Map<String, String> optionMap = new HashMap<String, String>();
		
		for (AbstractOption abstractOption : abstractOptions){
			String[] options = abstractOption.getOption();
			String[] values = abstractOption.getValue();
			int i = 0;
			
			for(String option : options){
				optionMap.put(option, values[i++]);
			}
		}
		
		backend.setOptions(optionMap);
		backend.generateCode(fileName, fifoSize);
	}
}
