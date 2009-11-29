package net.sf.orcc.frontend.parser;

import static net.sf.orcc.frontend.parser.Util.parseLocation;
import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.internal.ALBaseLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;

import org.antlr.runtime.tree.Tree;

/**
 * This class defines a parser that can parse AL types and translate them to IR
 * types;
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeParser {

	private final ExpressionParser exprParser;

	private final String file;

	/**
	 * Creates a new type parser with the given file
	 * 
	 * @param file
	 *            the file being parsed
	 * 
	 */
	public TypeParser(String file, ExpressionParser exprParser) {
		this.file = file;
		this.exprParser = exprParser;
	}

	/**
	 * Parses the given tree as an {@link Type}.
	 * 
	 * @param tree
	 *            a tree whose root is TYPE or TYPE_LIST.
	 * @return a type
	 * @throws OrccException
	 */
	public Type parseType(Tree tree) throws OrccException {
		if (tree.getType() == ALBaseLexer.TYPE) {
			return parseTYPE(tree);
		} else if (tree.getType() == ALBaseLexer.TYPE_LIST) {
			return parseTypeList(tree);
		} else {
			throw new OrccException("unknown type node: " + tree.getType());
		}
	}

	private Type parseTYPE(Tree tree) throws OrccException {
		Tree typeTree = tree.getChild(0);
		Location location = parseLocation(typeTree);
		String typeName = typeTree.getText();

		if (typeName.equals(BoolType.NAME)) {
			return new BoolType();
		} else if (typeName.equals(StringType.NAME)) {
			return new StringType();
		} else if (typeName.equals(VoidType.NAME)) {
			return new VoidType();
		} else if (typeName.equals(IntType.NAME)) {
			Expression size = parseTypeAttributeSize(location, tree, 32);
			return new IntType(size);
		} else if (typeName.equals(UintType.NAME)) {
			Expression size = parseTypeAttributeSize(location, tree, 32);
			return new UintType(size);
		} else if (typeName.equals(ListType.NAME)) {
			Expression size = parseTypeAttributeSize(location, tree, null);
			Type subType = parseTypeAttributeType(location, tree);
			return new ListType(size, subType);
		} else if (typeName.equals("float") || typeName.equals("unsigned")) {
			System.out.println("TODO: " + typeName);
			return new VoidType();
		} else {
			throw new OrccException(file, location, "Unknown type: " + typeName);
		}
	}

	private Expression parseTypeAttributeSize(Location location, Tree type,
			Integer defaultSize) throws OrccException {
		int n = type.getChildCount();
		for (int i = 1; i < n; i++) {
			Tree attr = type.getChild(i);
			if (attr.getType() == RVCCalLexer.EXPR) {
				if (attr.getChildCount() > 1
						&& !attr.getChild(1).getText().equals(AST.SIZE)) {
					throw new OrccException(file, location, "unknown \""
							+ attr.getChild(1).getText() + "\" attribute");
				}

				return exprParser.parseExpression(attr.getChild(0));
			}
		}

		// if there is a default size, return it
		if (defaultSize == null) {
			// size attribute not found, and no default size given => error
			throw new OrccException(file, location,
					"missing \"size\" attribute");
		} else {
			return new IntExpr(location, defaultSize);
		}
	}

	private Type parseTypeAttributeType(Location location, Tree typeAttrs)
			throws OrccException {
		int n = typeAttrs.getChildCount();
		for (int i = 1; i < n; i++) {
			Tree attr = typeAttrs.getChild(i);
			if (attr.getType() == RVCCalLexer.TYPE) {
				if (attr.getChildCount() > 1
						&& !attr.getChild(1).getText().equals(AST.TYPE)) {
					throw new OrccException(file, location, "unknown \""
							+ attr.getChild(1).getText() + "\" attribute");
				}

				return parseType(attr.getChild(0));
			}
		}

		// type attribute not found, and no default type given => error
		throw new OrccException(file, location, "missing \"type\" attribute");
	}

	/**
	 * Parses a TYPE_LIST tree and return a {@link ListType}.
	 * 
	 * @param tree
	 *            a tree whose root is TYPE_LIST
	 * @return an {@link Type}
	 * @throws OrccException
	 */
	private Type parseTypeList(Tree tree) throws OrccException {
		Type type = parseType(tree.getChild(0));
		for (int i = tree.getChildCount() - 1; i > 0; i--) {
			Expression size = exprParser.parseExpression(tree.getChild(i));
			type = new ListType(size, type);
		}

		return type;
	}

}
