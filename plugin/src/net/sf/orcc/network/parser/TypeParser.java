/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.network.parser;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.Entry;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class defines a parser of XDF types.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeParser {

	/**
	 * Default size of an signed/unsigned integer.
	 */
	private static final int defaultSize = 32;

	/**
	 * XDF expression parser.
	 */
	private final ExprParser exprParser;

	/**
	 * Creates a new type parser using the given expression parser.
	 * 
	 * @param exprParser
	 *            an XDF expression parser
	 */
	public TypeParser(ExprParser exprParser) {
		this.exprParser = exprParser;
	}

	/**
	 * Parses the given node as an IType.
	 * 
	 * @param node
	 *            the node to parse as a type.
	 * @return a type
	 * @throws OrccException
	 *             if the node could not be parsed as a type
	 */
	public ParseContinuation<IType> parseType(Node node) throws OrccException {
		while (node != null) {
			if (node.getNodeName().equals("Type")) {
				Element eltType = (Element) node;
				String name = eltType.getAttribute("name");
				if (name.equals(BoolType.NAME)) {
					return new ParseContinuation<IType>(node, new BoolType());
				} else if (name.equals(IntType.NAME)) {
					Map<String, Entry> entries = parseTypeEntries(node
							.getFirstChild());
					IExpr size = parseTypeSize(entries);
					return new ParseContinuation<IType>(node, new IntType(size));
				} else if (name.equals(ListType.NAME)) {
					return new ParseContinuation<IType>(node,
							parseTypeList(node));
				} else if (name.equals(StringType.NAME)) {
					return new ParseContinuation<IType>(node, new StringType());
				} else if (name.equals(UintType.NAME)) {
					Map<String, Entry> entries = parseTypeEntries(node
							.getFirstChild());
					IExpr size = parseTypeSize(entries);
					return new ParseContinuation<IType>(node,
							new UintType(size));
				} else {
					throw new OrccException("unknown type name: \"" + name
							+ "\"");
				}
			}

			node = node.getNextSibling();
		}

		throw new OrccException("Expected a Type element");
	}

	/**
	 * Parses the node and its siblings as type entries, and returns a map of
	 * entry names to contents.
	 * 
	 * @param node
	 *            The first node susceptible to be an entry, or
	 *            <code>null</code>.
	 * @return A map of entry names to contents.
	 * @throws OrccException
	 *             if something goes wrong
	 */
	private Map<String, Entry> parseTypeEntries(Node node) throws OrccException {
		Map<String, Entry> entries = new HashMap<String, Entry>();
		while (node != null) {
			if (node.getNodeName().equals("Entry")) {
				Element element = (Element) node;
				String name = element.getAttribute("name");
				String kind = element.getAttribute("kind");

				Entry entry = null;
				if (kind.equals("Expr")) {
					IExpr expr = exprParser.parseExpr(node.getFirstChild());
					entry = new Entry(expr);
				} else if (kind.equals("Type")) {
					entry = new Entry(parseType(node.getFirstChild())
							.getResult());
				} else {
					throw new OrccException("unsupported entry type: \"" + kind
							+ "\"");
				}

				entries.put(name, entry);
			}

			node = node.getNextSibling();
		}

		return entries;
	}

	/**
	 * Parses a List type.
	 * 
	 * @param node
	 *            the Type node where this List is defined
	 * @return a ListType
	 * @throws OrccException
	 *             if something is wrong, like a missing entry
	 */
	private IType parseTypeList(Node node) throws OrccException {
		Map<String, Entry> entries = parseTypeEntries(node.getFirstChild());
		Entry entry = entries.get("size");
		if (entry == null) {
			throw new OrccException("List type must have a \"size\" entry");
		}
		IExpr size = entry.getEntryAsExpr();

		entry = entries.get("type");
		if (entry == null) {
			throw new OrccException("List type must have a \"type\" entry");
		}
		IType type = entry.getEntryAsType();

		return new ListType(size, type);
	}

	/**
	 * Gets a "size" entry from the given entry map, if found return its value,
	 * otherwise return {@link #defaultSize}.
	 * 
	 * @param entries
	 *            a map of entries
	 * @return an expression
	 * @throws OrccException
	 *             if the "size" entry does not contain an expression
	 */
	private IExpr parseTypeSize(Map<String, Entry> entries)
			throws OrccException {
		Entry entry = entries.get("size");
		if (entry == null) {
			return new IntExpr(defaultSize);
		} else {
			return entry.getEntryAsExpr();
		}
	}

}
