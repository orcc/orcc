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
package net.sf.orcc.frontend.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.frontend.parser.internal.RVCCalLexer;
import net.sf.orcc.frontend.parser.internal.RVCCalParser;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.type.AbstractType;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/**
 * Parses an actor written in RVC-CAL, and translates the resulting tree to an
 * AST.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RVCCalASTParser {

	private String fileName;

	public RVCCalASTParser(String fileName) throws IOException {
		this.fileName = new File(fileName).getCanonicalPath();
	}

	public Actor parse() throws IOException, RecognitionException {
		CharStream stream = new ANTLRFileStream(fileName);
		Lexer lexer = new RVCCalLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RVCCalParser parser = new RVCCalParser(tokens);
		RVCCalParser.actor_return ret = parser.actor();
		return parseActor((Tree) ret.getTree());
	}

	/**
	 * children = actor, id, parameters, inputs, outputs
	 * 
	 * @param tree
	 * @return
	 */
	private Actor parseActor(Tree tree) {
		String name = tree.getChild(1).getText();
		List<VarDef> inputs = parsePorts(tree.getChild(3));
		List<VarDef> outputs = parsePorts(tree.getChild(4));
		return new Actor(name, fileName, inputs, outputs, null, null, null,
				null, null, null);
	}

	/**
	 * children = port 1, ..., port n
	 * 
	 * @param tree
	 * @return
	 */
	private List<VarDef> parsePorts(Tree tree) {
		List<VarDef> ports = new ArrayList<VarDef>();
		int numPorts = tree.getChildCount();
		for (int i = 0; i < numPorts; i++) {
			Tree child = tree.getChild(i);
			ports.add(parsePort(child));
		}

		return ports;
	}

	/**
	 * tree = PORT, children = TYPE, ID
	 * @param tree
	 * @return
	 */
	private VarDef parsePort(Tree tree) {
		AbstractType type = parseType(tree.getChild(0));
		String name = tree.getChild(1).getText();
		
		name.toString();
		type.toString();
		return null;
	}

	/**
	 * tree = TYPE, children = type name (ID), TYPE_ATTRS
	 * @param tree
	 * @return
	 */
	private AbstractType parseType(Tree tree) {
		String typeName = tree.getChild(0).getText();
		typeName.toString();
		return null;
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		new RVCCalASTParser(args[0]).parse();
	}

}
