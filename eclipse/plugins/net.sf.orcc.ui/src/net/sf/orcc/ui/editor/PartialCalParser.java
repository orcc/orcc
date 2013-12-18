package net.sf.orcc.ui.editor;

import java.io.Reader;
import java.io.StringReader;

import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.services.CalGrammarAccess;
import net.sf.orcc.cal.ui.internal.CalActivator;
import net.sf.orcc.frontend.StructTransformer;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
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
public class PartialCalParser {

	private final Injector injector;
	private final IParser parser;
	private final CalGrammarAccess grammarAccess;

	private final StructTransformer calToIrTransformer;

	public PartialCalParser() {
		injector = CalActivator.getInstance().getInjector("net.sf.orcc.cal.Cal");
		parser = injector.getInstance(IParser.class);
		grammarAccess = (CalGrammarAccess) injector.getInstance(IGrammarAccess.class);

		calToIrTransformer = new StructTransformer();
	}

	public boolean isVariableDeclaration(final String declString) {
		final Reader reader = new StringReader(declString);
		final IParseResult result = parser.parse(grammarAccess.getVariableDeclarationRule(), reader);

		return !result.hasSyntaxErrors();
	}

	public Var parseVariableDeclaration(final String declString) {
		final Reader reader = new StringReader(declString);
		final IParseResult result = parser.parse(grammarAccess.getVariableDeclarationRule(), reader);

		if (result.hasSyntaxErrors()) {
			return null;
		}

		final Variable astResult = (Variable) result.getRootASTElement();
		final EObject irResult = calToIrTransformer.doSwitch(astResult);
		
		if (irResult instanceof Var) {
			return (Var) irResult;
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
		final EObject irResult = calToIrTransformer.doSwitch(astType);

		if (irResult instanceof Type) {
			return (Type) irResult;
		}
		return null;
	}
}
