/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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

grammar RVCCal;

options {
  k = 1;
  output = AST;
}

tokens {
  ACTOR;
  EXPR;
  INPUTS;
  OUTPUTS;
  PARAMETERS;
  PORT;
  
  TYPE;
  TYPE_ATTRS;
}

@lexer::header {
package net.sf.orcc.frontend.parser.internal;
}

@parser::header {
package net.sf.orcc.frontend.parser.internal;

import net.sf.orcc.frontend.ast.*;
}

///////////////////////////////////////////////////////////////////////////////

actionChannelSelector:
  'all' { System.out.println("TODO: throw exception channel selectors"); }
| 'any' { System.out.println("TODO: throw exception channel selectors"); }
| 'at' { System.out.println("TODO: throw exception channel selectors"); }
| 'at*' { System.out.println("TODO: throw exception channel selectors"); };

actionDelay: 'delay' expression
	{ System.out.println("TODO: throw exception no delay"); };

actionGuards: 'guard' expressions { };

actionInput:
	(ID ':')? '[' idents ']' actionRepeat? actionChannelSelector?
	{ };

actionInputs: actionInput (',' actionInput)* { };

actionOutput:
	(ID ':')? '[' expressions ']' actionRepeat? actionChannelSelector?
	{ };

actionOutputs: actionOutput (',' actionOutput)* { };

actionRepeat: 'repeat' expression { };

actionStatements: 'do' statement* { };

/***************************************************************************/
/* a CAL actor. */

actor: actorImport* 'actor' id=ID ('[' typePar+ ']')? '(' actorParameters? ')'
	inputs=actorPortDecls? '==>' outputs=actorPortDecls? ':'
	actorDeclarations 'end' EOF
	-> 'actor' $id ^(PARAMETERS actorParameters?) ^(INPUTS $inputs?) ^(OUTPUTS $outputs?);

/*****************************************************************************/
/* actor declarations */

actorDeclaration:
  // hack so grammar can be expressed as LL(1)
  // why is the keyword "action" *after* the identifier of the action?
  // parsing "action a.b:" would be much easier than parsing "a.b: action"

  ID
    ((('.' ID)? ':')?
      ('action' actionInputs? '==>' actionOutputs? actionGuards? actionDelay? ('var' varDecls)? actionStatements? 'end'
      { }

    | 'initialize' '==>' actionOutputs? actionGuards? actionDelay? ('var' varDecls)? actionStatements? 'end'
      { })

  | ('[' typePar+ ']' | '(' typeAttrs ')')? ID ('=' expression | ':=' expression)? ';') { }

// anonymous action
| 'action' actionInputs? '==>' actionOutputs? actionGuards? actionDelay? ('var' varDecls)? actionStatements? 'end'
  { }

// anonymous initialize
| 'initialize' '==>' actionOutputs? actionGuards? actionDelay? ('var' varDecls)? actionStatements? 'end'
  { }

| priorityOrder { }

| 'function' ID '(' (varDeclNoExpr (',' varDeclNoExpr)*)? ')' '-->' typeDef
    ('var' varDecls)? ':'
      expression
    'end'
	{ }

| 'procedure' ID '(' (varDeclNoExpr (',' varDeclNoExpr)*)? ')'
    ('var' varDecls)?
    'begin' statement* 'end'
	{ };

actorDeclarations: actorDeclaration* (schedule actorDeclaration*)? { };

/*****************************************************************************/

/* imports (ignored by ORCC) */
actorImport: 'import'
  ('all' qualifiedIdent ';' { }
| qualifiedIdent ';' { });

/*****************************************************************************/
/* actor parameters: type name (= expression)? */
actorParameter:
	typeDef ID ('=' expression)? { };

actorParameters: actorParameter (',' actorParameter)* { };

/*****************************************************************************/
/* actor ports */

actorPortDecl:
	'multi' typeDef ID
	{ System.out.println("RVC-CAL does not permit the use of multi ports."); }
| typeDef ID -> ^(PORT typeDef ID);

actorPortDecls: actorPortDecl (',' actorPortDecl)* -> actorPortDecl+;

/*****************************************************************************/
/* expressions */

expression: and_expr (('or' | '||') and_expr)? { };

and_expr: bitor_expr (('and' | '&&') bitor_expr)? { };

bitor_expr: bitand_expr ('|' bitand_expr)? { };

bitand_expr: eq_expr ('&' eq_expr)? { };

eq_expr: rel_expr (('=' | '!=') rel_expr)? { };

rel_expr: shift_expr (('<' | '>' | '<=' | '>=') shift_expr)? { };

shift_expr: add_expr (('<<' | '>>') add_expr)? { };

add_expr: mul_expr (('+' | '-') mul_expr)? { };

mul_expr: exp_expr (('div' | 'mod' | '*' | '/') exp_expr)? { };

exp_expr: un_expr ('^' un_expr)? { };

un_expr: postfix_expression
	| '-' un_expr
	| 'not' un_expr
	| '#' un_expr { };

postfix_expression:
  '[' expressions (':' expressionGenerators)? ']'
| 'if' expression 'then' expression 'else' expression 'end'
| constant
| '(' expression ')'
| ID (
    '(' expressions? ')'
  |  ('[' expressions ']')+ )?;

constant:
  'true'
| 'false'
| INTEGER
| STRING { };

expressionGenerator:
	'for' typeDef ID 'in' expression
	{ };

expressionGenerators: expressionGenerator (',' expressionGenerator)* { };

expressions: expression (',' expression)* { };

/*****************************************************************************/
/* identifiers */

idents: ID (',' ID)* { };

/*****************************************************************************/
/* priorities */

priorityInequality: qualifiedIdent ('>' qualifiedIdent)+ ';' { };
	
priorityOrder: 'priority' (priorityInequality)* 'end' { };

/*****************************************************************************/
/* qualified ident */

qualifiedIdent: ID ('.' ID)* { };

/*****************************************************************************/
/* schedule */

schedule:
  'schedule'
    ('fsm' ID ':' stateTransition* 'end' { }
    | 'regexp' { System.out.println("RVC-CAL does not support \"regexp\" schedules."); } );

stateTransition:
	ID '(' qualifiedIdent ')' '-->' ID ';' { };

/*****************************************************************************/
/* statements */

statement:
  'begin' ('var' varDecls 'do')? statement* 'end' { }
| 'choose' { System.out.println("RVC-CAL does not support the \"choose\" statement."); }
| 'for' { System.out.println("RVC-CAL does not support the \"for\" statement, please use \"foreach\" instead."); }
| 'foreach' varDeclNoExpr 'in' (expression ('..' expression)?) ('var' varDecls)? 'do' statement* 'end' { }
| 'if' expression 'then' statement* ('else' statement*)? 'end' {  }
| 'while' expression ('var' varDecls)? 'do' statement* 'end' {  }

| ID (
    (('[' expressions ']')? ':=' expression ';') { }
  |  '(' expressions? ')' ';' { } );

/*****************************************************************************/
/* type attributes and definitions */

/* a type attribute, such as "type:" and "size=" */
typeAttr: ID (':' typeDef -> ^(TYPE ID typeDef) | '=' expression -> ^(EXPR ID expression));

typeAttrs: typeAttr (',' typeAttr)* -> typeAttr+;

/* a type definition: bool, int(size=5), list(type:int, size=10)... */	
typeDef: ID
  ('[' typePar+ ']'
  | '(' attrs=typeAttrs ')')? -> ^(TYPE ID ^(TYPE_ATTRS $attrs?));

/*****************************************************************************/
/* type parameters */

typePar: ID ('<' typeDef)? { System.out.println("RVC-CAL does not support type parameters."); };

/*****************************************************************************/
/* variable declarations */

/* simple variable declarations */
varDecl: typeDef ID ('=' expression | ':=' expression)? { };

varDeclNoExpr: typeDef ID { };

varDecls: varDecl (',' varDecl)* { };

///////////////////////////////////////////////////////////////////////////////
// TOKENS

ID: ('a'..'z' | 'A'..'Z' | '_' | '$') ('a'..'z' | 'A'..'Z' | '_' | '$' | '0' .. '9')* ;

FLOAT: '-'? (('0'..'9')+ '.' ('0'..'9')* (('e' | 'E') ('+' | '-')? ('0'..'9')+)?
	| '.' ('0'..'9')+ (('e' | 'E') ('+' | '-')? ('0'..'9')+)?
	| ('0'..'9')+ (('e' | 'E') ('+' | '-')? ('0'..'9')+));
INTEGER: '-'? ('0'..'9')+ ;
STRING: '\"' .* '\"';

LINE_COMMENT: '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;};
MULTI_LINE_COMMENT: '/*' .* '*/' {$channel=HIDDEN;};
WHITESPACE: (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;};
