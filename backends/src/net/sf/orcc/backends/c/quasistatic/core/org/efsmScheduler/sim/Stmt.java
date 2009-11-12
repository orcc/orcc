/*
* Copyright(c)2008, Jani Boutellier, Christophe Lucarz, Veeranjaneyulu Sadhanala 
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*     * Neither the name of the EPFL and University of Oulu nor the
*       names of its contributors may be used to endorse or promote products
*       derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala ``AS IS'' AND ANY 
* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL  Jani Boutellier, Christophe Lucarz, 
* Veeranjaneyulu Sadhanala BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.sim;

import java.util.Vector;

import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.Util;
import net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler.exceptions.UnhandledCaseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class represents one statement used in the bodies of expressions
 * @author Victor Martin
 * 5.12.2008
 *
 */
public class Stmt {
	public StmtType type;
	//asign
	public String lhs;
	public Expr rhs;
	//If
	public Expr ifCond;
	public Stmt ifBlock;
	//Block
	public Vector<Stmt> block;
	
	/**
     * Default Constructor
     */
	public Stmt(){
		type = StmtType.NONE;
		lhs = null;
		rhs = null;
	}

    /**
     * Contructor
     * @param stmtEle
     * @throws org.efsmScheduler.exceptions.UnhandledCaseException
     */
	public Stmt(Element stmtEle) throws UnhandledCaseException{
		String kind = stmtEle.getAttribute("kind");
		type = StmtType.NONE;
		if(kind.equals("Assign")){
			type = StmtType.ASIGN;
		}
		else if(kind.equals("Call")){
			type = StmtType.CALL;
		}
		else if(kind.equals("If")){
			type = StmtType.IF;
		}
		else if(kind.equals("Block")){
			type = StmtType.BLOCK;
		}
		else{
			System.out.println("Statement type "+kind);
		}
		NodeList nlT=null;
		Vector<Node> nl=null;
		switch(type){
		case ASIGN:
			lhs = stmtEle.getAttribute("name");
			
			nlT = stmtEle.getChildNodes();
			nl = Util.removeTextNodes(nlT);
			if(nl.size() == 1){
				rhs = new Expr((Element) nl.get(0));
			}
			else{
				System.out.println("Number of expr on rhs in Stmt: "+nl.size());				
			}
			break;
		case IF:
			nlT = stmtEle.getChildNodes();
			nl = Util.removeTextNodes(nlT);
			ifCond = new Expr((Element) nl.get(0));
			ifBlock = new Stmt((Element) nl.get(1));
			break;
		case BLOCK:
			block = new Vector<Stmt>();
			nlT = stmtEle.getChildNodes();
			nl = Util.removeTextNodes(nlT);
			for(Node node : nl){
				Element blockStmtEle = (Element) node;
				Stmt blockStmt = new Stmt(blockStmtEle);
				block.add(blockStmt);
			}
			break;
		default:
			
		}
	}

    /**
     *
     * @return a description of the statement
     */
    @Override
	public String toString(){
		switch(type){
		case ASIGN:
			if(rhs != null){
				return lhs + "="+rhs.toString();
			}
			else return lhs +"="+null;
		case IF:
			return "If ("+ifCond +")\n"+ifBlock.toString()+"\tendIf";
		case BLOCK:
			String str ="";
			for(Stmt stmt:block){
				str += "\t\t"+stmt.toString()+"\n";
			}
			return str;
		default:
			return "Unhandled";	
		
		}
	}
	public StmtType getType() {
		return type;
	}
	public String getLhs() {
		return lhs;
	}
	public Expr getRhs() {
		return rhs;
	}
}
