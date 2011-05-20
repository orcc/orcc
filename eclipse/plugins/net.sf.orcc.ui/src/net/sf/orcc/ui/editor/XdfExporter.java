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
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.graphiti.io.ITransformation;
import net.sf.graphiti.io.LayoutWriter;
import net.sf.graphiti.model.Edge;
import net.sf.graphiti.model.Graph;
import net.sf.graphiti.model.ObjectType;
import net.sf.graphiti.model.Vertex;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionFloat;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.AstVariableReference;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.ui.internal.CalActivator;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.StringAttribute;
import net.sf.orcc.network.serialize.XDFWriter;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.antlr.IAntlrParser;
import org.eclipse.xtext.parsetree.CompositeNode;
import org.eclipse.xtext.parsetree.LeafNode;
import org.eclipse.xtext.parsetree.NodeUtil;

import com.google.inject.Injector;

/**
 * This class defines an XDF exporter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XdfExporter extends CalSwitch<Object> implements ITransformation {

	private IAntlrParser parser;

	private Map<String, Var> varMap;

	private Map<Vertex, net.sf.orcc.network.Vertex> vertexMap;

	private Injector injector;

	private void addEdge(Network network, Edge edge) {
		net.sf.orcc.network.Vertex source = vertexMap.get(edge.getSource());
		net.sf.orcc.network.Vertex target = vertexMap.get(edge.getTarget());

		Port sourcePort = null;
		if ("Instance".equals(edge.getSource().getType().getName())) {
			String sourceName = (String) edge
					.getValue(ObjectType.PARAMETER_SOURCE_PORT);
			if (sourceName != null) {
				sourcePort = IrFactory.eINSTANCE.createPort(null, sourceName);
			}
		}

		Port targetPort = null;
		if ("Instance".equals(edge.getTarget().getType().getName())) {
			String targetName = (String) edge
					.getValue(ObjectType.PARAMETER_TARGET_PORT);
			if (targetName != null) {
				targetPort = IrFactory.eINSTANCE.createPort(null, targetName);
			}
		}

		// buffer size
		Map<String, IAttribute> attributes = new HashMap<String, IAttribute>();
		String bufferSize = (String) edge.getValue("bufferSize");
		if (bufferSize != null) {
			attributes.put("size", new StringAttribute(bufferSize));
		}

		// connection
		Connection connection = new Connection(sourcePort, targetPort,
				attributes);
		network.getGraph().addEdge(source, target, connection);
	}

	private void addParameters(Network network, Graph graph) {
		List<?> parameters = (List<?>) graph.getValue("network parameter");
		for (Object parameter : parameters) {
			AstVariable variable = parseVariable(parameter);

			Type type = (Type) doSwitch(variable.getType());
			String name = variable.getName();
			Var var = IrFactory.eINSTANCE.createVar(0, type, name, false, null);

			varMap.put(variable.getName(), var);
			network.getParameters().put(var.getName(), var);
		}
	}

	private void addVariables(Network network, Graph graph) {
		Map<?, ?> variables = (Map<?, ?>) graph
				.getValue("network variable declaration");
		for (Entry<?, ?> entry : variables.entrySet()) {
			AstVariable variable = parseVariable(entry.getKey());
			Expression expression = parseExpression(entry.getValue());

			Type type = (Type) doSwitch(variable.getType());
			String name = variable.getName();
			Var var = IrFactory.eINSTANCE.createVar(0, type, name, false,
					expression);

			varMap.put(variable.getName(), var);
			network.getVariables().put(var.getName(), var);
		}
	}

	private void addVertex(Network network, Vertex vertex) {
		String name = (String) vertex.getValue(PARAMETER_ID);
		net.sf.orcc.network.Vertex networkVertex;
		if ("Input port".equals(vertex.getType().getName())) {
			Type type = parseType(vertex.getValue("port type"));
			Port port = IrFactory.eINSTANCE.createPort(type, name);

			network.getInputs().put(name, port);
			networkVertex = new net.sf.orcc.network.Vertex("Input", port);
		} else if ("Output port".equals(vertex.getType().getName())) {
			Type type = parseType(vertex.getValue("port type"));
			Port port = IrFactory.eINSTANCE.createPort(type, name);

			network.getOutputs().put(name, port);
			networkVertex = new net.sf.orcc.network.Vertex("Output", port);
		} else {
			String clasz = (String) vertex.getValue(PARAMETER_REFINEMENT);
			Instance instance = new Instance(name, clasz);
			networkVertex = new net.sf.orcc.network.Vertex(instance);

			Map<?, ?> variables = (Map<?, ?>) vertex
					.getValue("instance parameter");
			for (Entry<?, ?> entry : variables.entrySet()) {
				AstVariable variable = parseVariable(entry.getKey());
				Expression expression = parseExpression(entry.getValue());
				instance.getParameters().put(variable.getName(), expression);
			}
		}

		network.getGraph().addVertex(networkVertex);
		vertexMap.put(vertex, networkVertex);
	}

	@Override
	public Expression caseAstExpressionBinary(AstExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Expression val1 = (Expression) doSwitch(expression.getLeft());
		Expression val2 = (Expression) doSwitch(expression.getRight());
		return IrFactory.eINSTANCE.createExprBinary(val1, op, val2, null);
	}

	@Override
	public Expression caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return IrFactory.eINSTANCE.createExprBool(expression.isValue());
	}

	@Override
	public Expression caseAstExpressionFloat(AstExpressionFloat expression) {
		return IrFactory.eINSTANCE.createExprFloat(expression.getValue());
	}

	@Override
	public Object caseAstExpressionInteger(AstExpressionInteger expression) {
		return IrFactory.eINSTANCE.createExprInt(expression.getValue());
	}

	@Override
	public Expression caseAstExpressionString(AstExpressionString expression) {
		return IrFactory.eINSTANCE.createExprString(OrccUtil
				.getEscapedString(expression.getValue()));
	}

	@Override
	public Expression caseAstExpressionUnary(AstExpressionUnary expression) {
		OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());
		Expression expr = (Expression) doSwitch(expression.getExpression());
		return IrFactory.eINSTANCE.createExprUnary(op, expr, null);
	}

	@Override
	public Expression caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		Var var = varMap.get(variable.getName());
		if (var == null) {
			throw new OrccRuntimeException("unknown variable "
					+ variable.getName());
		}
		return IrFactory.eINSTANCE.createExprVar(var);
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

	private Expression parseExpression(Object value) {
		Reader reader = new StringReader((String) value);
		IParseResult result = parser.parse("AstExpression", reader);
		AstExpression expression = (AstExpression) result.getRootASTElement();

		linkModel(expression);

		return (Expression) doSwitch(expression);
	}

	private Type parseType(Object value) {
		Reader reader = new StringReader((String) value);
		IParseResult result = parser.parse("AstType", reader);
		AstType type = (AstType) result.getRootASTElement();

		linkModel(type);

		return (Type) doSwitch(type);
	}

	private void linkModel(EObject object) {
		TreeIterator<EObject> it = object.eAllContents();
		while (it.hasNext()) {
			EObject obj = it.next();
			if (obj instanceof AstVariableReference) {
				AstVariableReference ref = (AstVariableReference) obj;

				CompositeNode node = NodeUtil.getNode(obj);
				for (LeafNode leaf : node.getLeafNodes()) {
					AstVariable variable = CalFactory.eINSTANCE
							.createAstVariable();
					variable.setName(leaf.getText());
					ref.setVariable(variable);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T parseVariable(Object parameter) {
		Reader reader = new StringReader((String) parameter);
		IParseResult result = parser.parse("AstVariableDeclaration", reader);
		return (T) result.getRootASTElement();
	}

	@Override
	public void transform(Graph graph, OutputStream out) {
		injector = CalActivator.getInstance()
				.getInjector("net.sf.orcc.cal.Cal");
		parser = injector.getInstance(IAntlrParser.class);
		varMap = new HashMap<String, Var>();
		vertexMap = new HashMap<Vertex, net.sf.orcc.network.Vertex>();

		Network network = new Network("");
		network.setName((String) graph.getValue(PARAMETER_ID));

		addParameters(network, graph);
		addVariables(network, graph);

		for (Vertex vertex : graph.vertexSet()) {
			addVertex(network, vertex);
		}

		for (Edge edge : graph.edgeSet()) {
			addEdge(network, edge);
		}

		XDFWriter writer = new XDFWriter();
		writer.write(network, out);

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

	@Override
	public Graph transform(IFile file) {
		return null;
	}

}
