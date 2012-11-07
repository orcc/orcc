/*
 * Copyright (c) 2008-2011, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.io;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.graphiti.model.Configuration;
import net.sf.orcc.graphiti.model.FileFormat;
import net.sf.orcc.graphiti.model.IRefinementPolicy;
import net.sf.orcc.graphiti.model.IValidator;
import net.sf.orcc.graphiti.model.ObjectType;
import net.sf.orcc.graphiti.model.Parameter;
import net.sf.orcc.graphiti.model.ParameterPosition;
import net.sf.orcc.graphiti.model.Transformation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Color;

/**
 * This class parses all configuration files located in the configuration folder
 * (defined in the plug-in preferences).
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ConfigurationParser {

	private static final String PLUGIN_ID = "net.sf.orcc.graphiti.model";

	private Map<String, Configuration> configurations;

	/**
	 * Creates a new configuration parser that parses all configuration files
	 * located in the configuration folder (defined in the plug-in preferences).
	 * 
	 * @throws CoreException
	 * 
	 * @see FileLocator
	 */
	public ConfigurationParser() throws CoreException {
		configurations = new LinkedHashMap<String, Configuration>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PLUGIN_ID + ".definition");
		for (IConfigurationElement element : elements) {
			Configuration configuration = parseConfiguration(element);
			configurations.put(configuration.getName(), configuration);
		}
	}

	/**
	 * Returns a reference to the list of configurations parsed.
	 * 
	 * @return A reference to the list of configurations parsed.
	 */
	public Map<String, Configuration> getConfigurations() {
		return configurations;
	}

	/**
	 * Parses a configuration definition element.
	 * 
	 * @param element
	 *            A configuration definition element.
	 * @throws CoreException
	 * @throws Exception
	 *             If anything wrong occurs.
	 */
	private Configuration parseConfiguration(IConfigurationElement element)
			throws CoreException {
		String name = element.getAttribute("name");

		String extension = element.getAttribute("extension");
		String type = element.getAttribute("type");
		FileFormat format = new FileFormat(extension, type);

		IConfigurationElement[] children = element.getChildren("import");
		children = children[0].getChildren();
		parseTransformations(format.getImportTransformations(), children);

		children = element.getChildren("export");
		if (children.length > 0) {
			children = children[0].getChildren();
			parseTransformations(format.getExportTransformations(), children);
		}

		Map<String, ObjectType> graphTypes = parseTypes(element
				.getChildren("graphType"));
		Map<String, ObjectType> vertexTypes = parseTypes(element
				.getChildren("vertexType"));
		Map<String, ObjectType> edgeTypes = parseTypes(element
				.getChildren("edgeType"));

		IValidator validator = (IValidator) element
				.createExecutableExtension("validator");

		String refinement = element.getAttribute("refinement");
		IRefinementPolicy refinementPolicy = null;
		if (refinement != null) {
			refinementPolicy = (IRefinementPolicy) element
					.createExecutableExtension("refinement");
		}

		IContributor contributor = element.getContributor();
		Configuration configuration = new Configuration(name,
				contributor.getName(), format, graphTypes, vertexTypes,
				edgeTypes, validator, refinementPolicy);
		return configuration;
	}

	/**
	 * Parses a parameter default value.
	 * 
	 * @param parameterType
	 *            The class of the parameter.
	 * @param child
	 *            The &lt;parameter&gt; element.
	 * @return An object, either a {@link List}, a {@link Map}, an
	 *         {@link Integer}, a {@link Float}, a {@link Boolean}, or a
	 *         {@link String}.
	 */
	private Object parseParameter(Class<?> parameterType,
			IConfigurationElement element) {
		if (parameterType == List.class) {
			List<String> list = new ArrayList<String>();
			return list;
		} else if (parameterType == Map.class) {
			Map<String, String> map = new TreeMap<String, String>();
			return map;
		} else {
			String value = element.getAttribute("default");
			try {
				if (parameterType == Integer.class) {
					return Integer.valueOf(value);
				}
				if (parameterType == Float.class) {
					return Float.valueOf(value);
				}
			} catch (NumberFormatException e) {
				return null;
			}

			if (parameterType == Boolean.class) {
				return Boolean.valueOf(value);
			} else if (parameterType == String.class) {
				return value;
			} else {
				return value;
			}
		}
	}

	/**
	 * Parses the parameters for the given type.
	 * 
	 * @param type
	 *            The type whose parameters are being specified.
	 * @param node
	 *            A child node of &lt;parameters&gt;.
	 */
	private void parseParameters(ObjectType type, IConfigurationElement element) {
		String parameterName = element.getAttribute("name");
		String typeName = element.getAttribute("type");
		Class<?> clz = String.class;
		try {
			clz = Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// creates the parameter
		ParameterPosition position = null;
		Object value = parseParameter(clz, element);
		Parameter parameter = new Parameter(parameterName, value, position, clz);

		// adds the parameter to the type
		type.addParameter(parameter);
	}

	/**
	 * Parses the file format imports.
	 * 
	 * @param format
	 *            The file format to fill.
	 * @param node
	 *            A child node of &lt;imports&gt;.
	 * @throws CoreException
	 */
	private void parseTransformations(List<Transformation> transformations,
			IConfigurationElement[] children) throws CoreException {
		for (IConfigurationElement element : children) {
			String name = element.getAttribute("name");
			String type = element.getName();
			if (type.equals("transformation")) {
				ITransformation instance = (ITransformation) element
						.createExecutableExtension("class");
				transformations.add(new Transformation(instance));
			} else if (type.equals("xslt")) {
				transformations.add(new Transformation(name));
			} else {
				throw new IllegalArgumentException("Unknown type: " + type);
			}
		}
	}

	/**
	 * Parses a type.
	 * 
	 * @param type
	 * @param children
	 */
	private void parseType(ObjectType type, IConfigurationElement[] children) {
		for (IConfigurationElement element : children) {
			if (element.getName().equals(ObjectType.ATTRIBUTE_COLOR)) {
				int red = Integer.parseInt(element.getAttribute("red"));
				int green = Integer.parseInt(element.getAttribute("green"));
				int blue = Integer.parseInt(element.getAttribute("blue"));
				Color color = new Color(null, red, green, blue);
				type.addAttribute(ObjectType.ATTRIBUTE_COLOR, color);
			} else if (element.getName().equals(ObjectType.ATTRIBUTE_DIRECTED)) {
				boolean directed = Boolean.parseBoolean(element
						.getAttribute("directed"));
				type.addAttribute(ObjectType.ATTRIBUTE_DIRECTED, directed);
			} else if (element.getName().equals("size")) {
				int width = Integer.parseInt(element.getAttribute("width"));
				type.addAttribute(ObjectType.ATTRIBUTE_WIDTH, width);
				int height = Integer.parseInt(element.getAttribute("height"));
				type.addAttribute(ObjectType.ATTRIBUTE_HEIGHT, height);
			} else if (element.getName().equals(ObjectType.ATTRIBUTE_SHAPE)) {
				String shape = element.getAttribute("type");
				type.addAttribute(ObjectType.ATTRIBUTE_SHAPE, shape);
			} else if (element.getName().equals("parameter")) {
				parseParameters(type, element);
			}
		}
	}

	/**
	 * Parses the object types.
	 * 
	 * @param children
	 *            configuration elements
	 * @return a map from strings to object types
	 */
	private Map<String, ObjectType> parseTypes(IConfigurationElement[] children) {
		Map<String, ObjectType> types = new TreeMap<String, ObjectType>();

		for (IConfigurationElement element : children) {
			String typeName = element.getAttribute("name");
			ObjectType type = new ObjectType(typeName);

			String isDirected = element.getAttribute("directed");
			if (isDirected != null) {
				boolean value = Boolean.parseBoolean(isDirected);
				type.addAttribute(ObjectType.ATTRIBUTE_DIRECTED, value);
			}
			types.put(typeName, type);
			parseType(type, element.getChildren());
		}

		return types;
	}

}
