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
package net.sf.orcc.backends.multicore;

/**
 * 
 * @author Jérôme GORIN
 * 
 */
public class MultiCoreNodePrinter {

	// protected String actorName;
	//
	// protected final BinaryOpPrinter binaryOpPrinter;
	//
	// protected ExprVisitor exprPrinter;
	//
	// protected PrettyPrinter pp;
	//
	// protected VarDefPrinter varDefPrinter;
	//
	// public MultiCoreNodePrinter(PrettyPrinter pp, String actorName,
	// ExprVisitor exprPrinter, VarDefPrinter varDefPrinter) {
	// this.actorName = actorName;
	// this.binaryOpPrinter = new BinaryOpPrinter();
	// this.exprPrinter = exprPrinter;
	// this.pp = pp;
	// this.varDefPrinter = varDefPrinter;
	// }
	//
	// @Override
	// public void visit(AssignVarNode node, Object... args) {
	// pp.println();
	//
	// varDefPrinter.printVarDefName(node.getVar());
	// pp.print(" = ");
	// node.getValue().accept(exprPrinter, 0);
	// pp.print(";");
	// }
	//
	// @Override
	// public void visit(CallNode node, Object... args) {
	// pp.println();
	//
	// if (node.hasRes()) {
	// varDefPrinter.printVarDefName(node.getRes());
	// pp.print(" = ");
	// }
	//
	// varDefPrinter.printName(node.getProcedure().getName());
	// pp.print("(");
	// pp.printList(", ", node.getParameters(), new PrinterCallback() {
	//
	// @Override
	// public void print(Object... args) {
	// ((AbstractExpr) args[0]).accept(exprPrinter, 0);
	// }
	//
	// });
	// pp.print(");");
	// }
	//
	// @Override
	// public void visit(DecrementNode node, Object... args) {
	// pp.println();
	// varDefPrinter.printVarDefName(node.getVar());
	// pp.print("--;");
	// }
	//
	// @Override
	// public void visit(EmptyNode node, Object... args) {
	// // nothing to print
	// }
	//
	// @Override
	// public void visit(HasTokensNode node, Object... args) {
	// pp.println();
	//
	// varDefPrinter.printVarDefName(node.getVarDef());
	// pp.print(" = lff_hasTokens(" + actorName + "_" + node.getFifoName());
	// pp.print(", " + node.getNumTokens() + ");");
	// }
	//
	// @Override
	// public void visit(IfNode node, Object... args) {
	// pp.println();
	//
	// pp.print("if (");
	// node.getCondition().accept(exprPrinter, 0);
	// pp.print(") {");
	//
	// pp.indent();
	// for (AbstractNode subNode : node.getThenNodes()) {
	// subNode.accept(this, args);
	// }
	// pp.unindent();
	// pp.println();
	// pp.print("}");
	//
	// List<AbstractNode> elseNodes = node.getElseNodes();
	// if (!(elseNodes.size() == 1 && elseNodes.get(0) instanceof EmptyNode)) {
	// pp.print(" else {");
	// pp.indent();
	//
	// for (AbstractNode subNode : elseNodes) {
	// subNode.accept(this, args);
	// }
	//
	// pp.unindent();
	// pp.println();
	// pp.print("}");
	// }
	// }
	//
	// @Override
	// public void visit(IncrementNode node, Object... args) {
	// pp.println();
	// varDefPrinter.printVarDefName(node.getVar());
	// pp.print("++;");
	// }
	//
	// @Override
	// public void visit(JoinNode node, Object... args) {
	// // there is nothing to print.
	// }
	//
	// @Override
	// public void visit(LoadNode node, Object... args) {
	// pp.println();
	//
	// varDefPrinter.printVarDefName(node.getTarget());
	// pp.print(" = ");
	//
	// varDefPrinter.printVarDefName(node.getSource().getVarDef());
	// List<AbstractExpr> indexes = node.getIndexes();
	// if (!indexes.isEmpty()) {
	// for (AbstractExpr expr : indexes) {
	// pp.print("[");
	// expr.accept(exprPrinter, 0);
	// pp.print("]");
	// }
	// }
	// pp.print(";");
	// }
	//
	// @Override
	// public void visit(PeekNode node, Object... args) {
	// pp.println();
	//
	// if (node.getNumTokens() == 1) {
	// pp.print("lff_peek(" + actorName + "_" + node.getFifoName() + ", ");
	// varDefPrinter.printVarDefName(node.getVarDef());
	// pp.print(");");
	// } else {
	// pp.print("lff_peek_n(" + actorName + "_" + node.getFifoName()
	// + ", ");
	// varDefPrinter.printVarDefName(node.getVarDef());
	// pp.print(", " + node.getNumTokens() + ");");
	// }
	// }
	//
	// @Override
	// public void visit(ReadNode node, Object... args) {
	// pp.println();
	//
	// if (node.getNumTokens() == 1) {
	// pp.print("lff_read(" + actorName + "_" + node.getFifoName() + ", ");
	// varDefPrinter.printVarDefName(node.getVarDef());
	// pp.print(");");
	// } else {
	// pp.print("lff_read_n(" + actorName + "_" + node.getFifoName()
	// + ", ");
	// varDefPrinter.printVarDefName(node.getVarDef());
	// pp.print(", " + node.getNumTokens() + ");");
	// }
	// }
	//
	// @Override
	// public void visit(ReturnNode node, Object... args) {
	// pp.println();
	//
	// pp.print("return ");
	// node.getExpr().accept(exprPrinter, 0);
	// pp.print(";");
	// }
	//
	// @Override
	// public void visit(SelfAssignment node, Object... args) {
	// pp.println();
	// varDefPrinter.printVarDefName(node.getVar());
	// pp.print(" ");
	// pp.print(binaryOpPrinter.toString(node.getOp()));
	// pp.print("= ");
	// node.getValue().accept(exprPrinter, 0);
	// pp.print(";");
	// }
	//
	// @Override
	// public void visit(StoreNode node, Object... args) {
	// pp.println();
	//
	// varDefPrinter.printVarDefName(node.getTarget().getVarDef());
	// List<AbstractExpr> indexes = node.getIndexes();
	// if (!indexes.isEmpty()) {
	// for (AbstractExpr expr : indexes) {
	// pp.print("[");
	// expr.accept(exprPrinter, 0);
	// pp.print("]");
	// }
	// }
	//
	// pp.print(" = ");
	// node.getValue().accept(exprPrinter, 0);
	// pp.print(";");
	// }
	//
	// @Override
	// public void visit(WhileNode node, Object... args) {
	// pp.println();
	//
	// pp.print("while (");
	// node.getCondition().accept(exprPrinter, 0);
	// pp.print(") {");
	//
	// pp.indent();
	// for (AbstractNode subNode : node.getNodes()) {
	// subNode.accept(this, args);
	// }
	// pp.unindent();
	// pp.println();
	// pp.print("}");
	// }
	//
	// @Override
	// public void visit(WriteNode node, Object... args) {
	// pp.println();
	// if (node.getNumTokens() == 1) {
	// pp
	// .print("lff_write(" + actorName + "_" + node.getFifoName()
	// + ", ");
	// varDefPrinter.printVarDefName(node.getVarDef());
	// pp.print(");");
	// } else {
	// pp.print("lff_write_n(" + actorName + "_" + node.getFifoName()
	// + ", ");
	// varDefPrinter.printVarDefName(node.getVarDef());
	// pp.print(", " + node.getNumTokens() + ");");
	// }
	// }
}
