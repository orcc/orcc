/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.ui.editor;

import static net.sf.graphiti.model.ObjectType.PARAMETER_ID;
import static net.sf.graphiti.model.ObjectType.PARAMETER_REFINEMENT;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.dftools.graph.Attribute;
import net.sf.dftools.graph.GraphFactory;
import net.sf.graphiti.io.LayoutWriter;
import net.sf.graphiti.model.Edge;
import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.ObjectType;
import net.sf.graphiti.model.Vertex;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionFloat;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.VariableReference;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.CalGrammarAccess;
import net.sf.orcc.cal.ui.internal.CalActivator;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Argument;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.DfVertex;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;

import com.google.inject.Injector;

/**
 * This class defines an XDF exporter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfExporter extends CalSwitch<Object> {

	private class PortComparator implements Comparator<Port> {

		@Override
		public int compare(Port p1, Port p2) {
			Vertex v1 = portMap.get(p1);
			Rectangle r1 = (Rectangle) v1.getValue(Vertex.PROPERTY_SIZE);
			Vertex v2 = portMap.get(p2);
			Rectangle r2 = (Rectangle) v2.getValue(Vertex.PROPERTY_SIZE);
			return (r1.y < r2.y ? -1 : (r1.y == r2.y ? 0 : 1));
		}

	}

	private CalGrammarAccess access;

	private Injector injector;

	private IParser parser;

	private Map<Port, Vertex> portMap;

	private Map<String, Var> varMap;

	private Map<Vertex, DfVertex> vertexMap;

	private void addEdge(Network network, Edge edge) {
		DfVertex source = vertexMap.get(edge.getSource());
		DfVertex target = vertexMap.get(edge.getTarget());

		Port sourcePort = null;
		if ("Instance".equals(edge.getSource().getType().getName())) {
			String sourceName = (String) edge
					.getValue(ObjectType.PARAMETER_SOURCE_PORT);
			if (sourceName != null) {
				sourcePort = DfFactory.eINSTANCE.createPort(null, sourceName);
			}
		}

		Port targetPort = null;
		if ("Instance".equals(edge.getTarget().getType().getName())) {
			String targetName = (String) edge
					.getValue(ObjectType.PARAMETER_TARGET_PORT);
			if (targetName != null) {
				targetPort = DfFactory.eINSTANCE.createPort(null, targetName);
			}
		}

		if (source.isInstance() && sourcePort == null) {
			throw new OrccRuntimeException("the source port of a connection "
					+ "from instance " + source.getName()
					+ " must be specified");
		}
		if (target.isInstance() && targetPort == null) {
			throw new OrccRuntimeException("the target port of a connection "
					+ "to instance " + target.getName() + " must be specified");
		}

		// buffer size
		Connection connection = DfFactory.eINSTANCE.createConnection(source,
				sourcePort, target, targetPort);
		Integer bufferSize = (Integer) edge.getValue("buffer size");
		if (bufferSize != null) {
			connection.setAttribute(Connection.BUFFER_SIZE, (int) bufferSize);
		}

		network.getConnections().add(connection);
	}

	private void addParameters(Network network, Graph graph) {
		List<?> parameters = (List<?>) graph.getValue("network parameter");
		for (Object parameter : parameters) {
			Variable variable = parseVariable(parameter);
			if (variable == null) {
				throw new OrccRuntimeException(
						"The network parameter declaration \"" + parameter
								+ "\" is not valid");
			}

			Type type = (Type) doSwitch(variable.getType());
			String name = variable.getName();
			Var var = IrFactory.eINSTANCE.createVar(0, type, name, false, null);

			varMap.put(variable.getName(), var);
			network.getParameters().add(var);
		}
	}

	private void addVariables(Network network, Graph graph) {
		Map<?, ?> variables = (Map<?, ?>) graph
				.getValue("network variable declaration");
		for (Entry<?, ?> entry : variables.entrySet()) {
			Variable variable = parseVariable(entry.getKey());
			if (variable == null) {
				throw new OrccRuntimeException(
						"The network variable declaration \"" + entry.getKey()
								+ "\" is not valid");
			}

			Expression expression = parseExpression(entry.getValue());
			if (expression == null) {
				throw new OrccRuntimeException("The expression \""
						+ entry.getValue() + "\" associated with variable \""
						+ entry.getKey() + "\" is not valid");
			}

			Type type = (Type) doSwitch(variable.getType());
			String name = variable.getName();
			Var var = IrFactory.eINSTANCE.createVar(0, type, name, false,
					expression);

			varMap.put(variable.getName(), var);
			network.getVariables().add(var);
		}
	}

	private void addVertex(Network network, Vertex vertex) {
		String name = (String) vertex.getValue(PARAMETER_ID);
		if ("Input port".equals(vertex.getType().getName())) {
			Type type = parseType(vertex.getValue("port type"));
			boolean native_ = (Boolean) vertex.getValue("native");
			Port port = DfFactory.eINSTANCE.createPort(type, name, native_);
			portMap.put(port, vertex);

			network.getInputs().add(port);
			vertexMap.put(vertex, port);
		} else if ("Output port".equals(vertex.getType().getName())) {
			Type type = parseType(vertex.getValue("port type"));
			boolean native_ = (Boolean) vertex.getValue("native");
			Port port = DfFactory.eINSTANCE.createPort(type, name, native_);
			portMap.put(port, vertex);

			network.getOutputs().add(port);
			vertexMap.put(vertex, port);
		} else {
			String clasz = (String) vertex.getValue(PARAMETER_REFINEMENT);
			Actor actor = DfFactory.eINSTANCE.createActor();
			actor.setName(clasz);

			Instance instance = DfFactory.eINSTANCE.createInstance(name, actor);
			network.getInstances().add(instance);
			vertexMap.put(vertex, instance);

			Map<?, ?> variables = (Map<?, ?>) vertex
					.getValue("instance parameter");
			for (Entry<?, ?> entry : variables.entrySet()) {
				String varName = (String) entry.getKey();
				Expression expression = parseExpression(entry.getValue());
				if (expression == null) {
					throw new OrccRuntimeException("The expression \""
							+ entry.getValue()
							+ "\" associated with parameter \""
							+ entry.getKey() + "\" is not valid");
				}

				Argument argument = DfFactory.eINSTANCE.createArgument(
						IrFactory.eINSTANCE.createVar(expression.getType(),
								varName, true, 0), expression);
				instance.getArguments().add(argument);
			}

			// part name attribute
			String partName = (String) vertex.getValue("part name");
			if (partName != null) {
				partName = partName.replaceAll("^\"|\"$", "");
				if (partName.length() != 0) {
					// remove extra quotes
					Expression expr = IrFactory.eINSTANCE
							.createExprString(partName);
					Attribute attr = GraphFactory.eINSTANCE.createAttribute(
							"partName", expr);
					instance.getAttributes().add(attr);
				}
			}
		}
	}

	@Override
	public Type caseAstTypeBool(AstTypeBool type) {
		return IrFactory.eINSTANCE.createTypeBool();
	}

	@Override
	public Type caseAstTypeFloat(AstTypeFloat type) {
		return IrFactory.eINSTANCE.createTypeFloat();
	}

	@Override
	public Type caseAstTypeInt(AstTypeInt type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = new ExpressionEvaluator()
					.evaluateAsInteger((Expression) doSwitch(astSize));
		}
		return IrFactory.eINSTANCE.createTypeInt(size);
	}

	@Override
	public Type caseAstTypeList(AstTypeList listType) {
		Type type = (Type) doSwitch(listType.getType());
		AstExpression expression = listType.getSize();
		Expression size = (Expression) doSwitch(expression);
		return IrFactory.eINSTANCE.createTypeList(size, type);
	}

	@Override
	public Type caseAstTypeString(AstTypeString type) {
		return IrFactory.eINSTANCE.createTypeString();
	}

	@Override
	public Type caseAstTypeUint(AstTypeUint type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = new ExpressionEvaluator()
					.evaluateAsInteger((Expression) doSwitch(astSize));
		}

		return IrFactory.eINSTANCE.createTypeUint(size);
	}

	@Override
	public Expression caseExpressionBinary(ExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Expression val1 = (Expression) doSwitch(expression.getLeft());
		Expression val2 = (Expression) doSwitch(expression.getRight());
		return IrFactory.eINSTANCE.createExprBinary(val1, op, val2, null);
	}

	@Override
	public Expression caseExpressionBoolean(ExpressionBoolean expression) {
		return IrFactory.eINSTANCE.createExprBool(expression.isValue());
	}

	@Override
	public Expression caseExpressionFloat(ExpressionFloat expression) {
		return IrFactory.eINSTANCE.createExprFloat(expression.getValue());
	}

	@Override
	public Object caseExpressionInteger(ExpressionInteger expression) {
		return IrFactory.eINSTANCE.createExprInt(expression.getValue());
	}

	@Override
	public Object caseExpressionList(ExpressionList list) {
		List<Expression> expressions = new ArrayList<Expression>(list
				.getExpressions().size());
		for (AstExpression expression : list.getExpressions()) {
			expressions.add((Expression) doSwitch(expression));
		}
		return IrFactory.eINSTANCE.createExprList(expressions);
	}

	@Override
	public Expression caseExpressionString(ExpressionString expression) {
		return IrFactory.eINSTANCE.createExprString(OrccUtil
				.getEscapedString(expression.getValue()));
	}

	@Override
	public Expression caseExpressionUnary(ExpressionUnary expression) {
		OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());
		Expression expr = (Expression) doSwitch(expression.getExpression());
		return IrFactory.eINSTANCE.createExprUnary(op, expr, null);
	}

	@Override
	public Expression caseExpressionVariable(ExpressionVariable expression) {
		Variable variable = expression.getValue().getVariable();
		Var var = varMap.get(variable.getName());
		if (var == null) {
			throw new OrccRuntimeException("unknown variable "
					+ variable.getName());
		}
		return IrFactory.eINSTANCE.createExprVar(var);
	}

	private void linkModel(EObject object) {
		TreeIterator<EObject> it = object.eAllContents();
		while (it.hasNext()) {
			EObject obj = it.next();
			if (obj instanceof VariableReference) {
				VariableReference ref = (VariableReference) obj;

				ICompositeNode node = NodeModelUtils.getNode(obj);
				for (ILeafNode leaf : node.getLeafNodes()) {
					Variable variable = CalFactory.eINSTANCE.createVariable();
					variable.setName(leaf.getText());
					ref.setVariable(variable);
				}
			}
		}
	}

	private Expression parseExpression(Object value) {
		Reader reader = new StringReader((String) value);
		IParseResult result = parser.parse(access.getAstExpressionRule(),
				reader);
		if (result.hasSyntaxErrors()) {
			return null;
		}

		AstExpression expression = (AstExpression) result.getRootASTElement();

		linkModel(expression);

		return (Expression) doSwitch(expression);
	}

	private Type parseType(Object value) {
		Reader reader = new StringReader((String) value);
		IParseResult result = parser.parse(access.getAstTypeRule(), reader);
		AstType type = (AstType) result.getRootASTElement();

		linkModel(type);

		return (Type) doSwitch(type);
	}

	@SuppressWarnings("unchecked")
	private <T> T parseVariable(Object parameter) {
		Reader reader = new StringReader((String) parameter);
		IParseResult result = parser.parse(access.getVariableDeclarationRule(),
				reader);
		if (result.hasSyntaxErrors()) {
			return null;
		}
		return (T) result.getRootASTElement();
	}

	/**
	 * Transforms the given graph to XDF, and writes it to the output stream.
	 * 
	 * @param graph
	 *            a graph
	 * @param out
	 *            an output stream
	 */
	public void transform(Graph graph, OutputStream out) {
		injector = CalActivator.getInstance()
				.getInjector("net.sf.orcc.cal.Cal");
		parser = injector.getInstance(IParser.class);
		access = (CalGrammarAccess) injector.getInstance(IGrammarAccess.class);

		varMap = new HashMap<String, Var>();
		portMap = new HashMap<Port, Vertex>();
		vertexMap = new HashMap<Vertex, DfVertex>();

		Network network = DfFactory.eINSTANCE.createNetwork();
		network.setName((String) graph.getValue(PARAMETER_ID));

		addParameters(network, graph);
		addVariables(network, graph);

		// add vertices
		for (Vertex vertex : graph.vertexSet()) {
			addVertex(network, vertex);
		}

		// sort ports based on their position in the window (top to bottom)
		ECollections.sort(network.getInputs(), new PortComparator());
		ECollections.sort(network.getOutputs(), new PortComparator());

		// add edges
		for (Edge edge : graph.edgeSet()) {
			addEdge(network, edge);
		}

		// saves to XDF
		ResourceSet set = new ResourceSetImpl();
		URI uri = URI.createPlatformResourceURI(graph.getFileName().toString(),
				true);
		Resource resource = set.createResource(uri);
		resource.getContents().add(network);
		try {
			resource.save(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// write layout
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = graph.getFile();
		file = root.getFile(file.getFullPath().removeFileExtension()
				.addFileExtension("layout"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		new LayoutWriter().write(graph, bos);
		try {
			OrccUtil.setFileContents(file,
					new ByteArrayInputStream(bos.toByteArray()));
		} catch (CoreException e) {
			throw new OrccRuntimeException("error when writing layout", e);
		}
	}

}
