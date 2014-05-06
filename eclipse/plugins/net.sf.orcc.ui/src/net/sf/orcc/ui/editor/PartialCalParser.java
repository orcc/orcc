package net.sf.orcc.ui.editor;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeDouble;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeHalf;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionFloat;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.CalGrammarAccess;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.ui.internal.CalActivator;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;

import com.google.inject.Injector;

/**
 * This class reuse the Xtext CAL logic and the defined transformers to parse
 * Strings and try to convert them into their equivalent object in the IR
 * package.
 * 
 * @author Antoine Lorence
 * 
 */
public class PartialCalParser extends CalSwitch<EObject> {

	private final Injector injector;
	private final IParser parser;
	private final CalGrammarAccess grammarAccess;

	/**
	 * This will be used to perform linking between declaration and usage of
	 * variables. Xtext do it automatically in a classical context, but here we
	 * need to ensure mechanism is working even if variables are declared and
	 * used in a Network or an Instance.
	 */
	private final Map<String, Var> declaredVars;

	public PartialCalParser() {
		injector = CalActivator.getInstance().getInjector("net.sf.orcc.cal.Cal");
		parser = injector.getInstance(IParser.class);
		grammarAccess = (CalGrammarAccess) injector.getInstance(IGrammarAccess.class);

		declaredVars = new HashMap<String, Var>();
	}

	public void setDeclaredVars(final Iterable<Var> vars) {
		declaredVars.clear();

		for (final Var var : vars) {
			declaredVars.put(var.getName(), var);
		}
	}

	public void addDeclaredVar(final Var var) {
		declaredVars.put(var.getName(), var);
	}

	public void addDeclaredVars(final Iterable<Var> vars) {
		for (final Var var : vars) {
			declaredVars.put(var.getName(), var);
		}
	}

	public boolean isVariableDeclaration(final String declString) {
		final Reader reader = new StringReader(declString);
		final IParseResult result = parser.parse(grammarAccess.getValuedVariableDeclarationRule(), reader);

		return !result.hasSyntaxErrors();
	}

	public Var parseVariableDeclaration(final String declString) {
		final Reader reader = new StringReader(declString);
		final IParseResult result = parser.parse(grammarAccess.getValuedVariableDeclarationRule(), reader);

		if (result.hasSyntaxErrors()) {
			return null;
		}

		final Variable astResult = (Variable) result.getRootASTElement();
		final EObject irResult = doSwitch(astResult);
		
		if (irResult instanceof Var) {
			return (Var) irResult;
		}
		return null;
	}

	public boolean isExpression(final String exprString) {
		final Reader reader = new StringReader(exprString);
		final IParseResult result = parser.parse(grammarAccess.getAstExpressionRule(), reader);

		return !result.hasSyntaxErrors();
	}

	public Expression parseExpression(final String exprString) {
		final Reader reader = new StringReader(exprString);
		final IParseResult result = parser.parse(grammarAccess.getAstExpressionRule(), reader);

		if (result.hasSyntaxErrors()) {
			return null;
		}

		final AstExpression astResult = (AstExpression) result.getRootASTElement();
		final EObject irResult = doSwitch(astResult);

		if (irResult instanceof Expression) {
			return (Expression) irResult;
		}
		return null;
	}

	public boolean isType(final String typeString) {
		final Reader reader = new StringReader(typeString);
		final IParseResult result = parser.parse(grammarAccess.getAstTypeRule(), reader);

		return !result.hasSyntaxErrors();
	}

	public Type parseType(final String typeString) {
		final Reader reader = new StringReader(typeString);
		final IParseResult result = parser.parse(grammarAccess.getAstTypeRule(), reader);

		if (result.hasSyntaxErrors()) {
			return null;
		}

		final AstType astType = (AstType) result.getRootASTElement();
		final Object irResult = doSwitch(astType);

		if (irResult instanceof Type) {
			return (Type) irResult;
		}
		return null;
	}

	@Override
	public Type caseAstTypeBool(AstTypeBool type) {
		return IrFactory.eINSTANCE.createTypeBool();
	}

	@Override
	public Type caseAstTypeDouble(AstTypeDouble type) {
		return IrFactory.eINSTANCE.createTypeFloat(64);
	}

	@Override
	public Type caseAstTypeFloat(AstTypeFloat type) {
		return IrFactory.eINSTANCE.createTypeFloat(32);
	}

	@Override
	public Type caseAstTypeHalf(AstTypeHalf type) {
		return IrFactory.eINSTANCE.createTypeFloat(16);
	}

	@Override
	public Type caseAstTypeInt(AstTypeInt type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = ExpressionEvaluator.evaluateAsInteger((Expression) doSwitch(astSize));
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
			size = ExpressionEvaluator.evaluateAsInteger((Expression) doSwitch(astSize));
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
	public ExprInt caseExpressionInteger(ExpressionInteger expression) {
		return IrFactory.eINSTANCE.createExprInt(expression.getValue());
	}

	@Override
	public ExprList caseExpressionList(ExpressionList list) {
		List<Expression> expressions = new ArrayList<Expression>(list.getExpressions().size());
		for (AstExpression expression : list.getExpressions()) {
			expressions.add((Expression) doSwitch(expression));
		}
		return IrFactory.eINSTANCE.createExprList(expressions);
	}

	@Override
	public Expression caseExpressionString(ExpressionString expression) {
		return IrFactory.eINSTANCE.createExprString(OrccUtil.getEscapedString(expression.getValue()));
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
		String variableName = null;
		// This expression references a variable, but parser did not found
		// original variable declaration
		if (variable == null) {
			final ICompositeNode node = NodeModelUtils.getNode(expression);
			// Parse all node elements until a variable name is found
			for (ILeafNode leaf : node.getLeafNodes()) {
				variableName = leaf.getText();
				if (!variableName.trim().isEmpty()) {
					break;
				}
			}
		} else {
			variableName = variable.getName();
		}

		// Get the already declared variable with the given name
		final Var var = declaredVars.get(variableName);
		if (var == null) {
			throw new OrccRuntimeException("Unable to find declaration for variable \"" + variableName + "\".");
		}
		return IrFactory.eINSTANCE.createExprVar(var);
	}

	@Override
	public Var caseVariable(Variable variable) {
		int lineNumber = Util.getLocation(variable);
		Type type = Typer.getType(variable);
		String name = variable.getName();
		boolean assignable = Util.isAssignable(variable);

		final Var result = IrFactory.eINSTANCE.createVar(lineNumber, type, name, assignable);
		if (variable.getValue() != null) {
			result.setInitialValue((Expression) doSwitch(variable.getValue()));
		}

		return result;
	}
}
