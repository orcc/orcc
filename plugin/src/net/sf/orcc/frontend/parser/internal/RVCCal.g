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

import ALBaseLexer;

@lexer::header {
package net.sf.orcc.frontend.parser.internal;
}

@parser::header {
package net.sf.orcc.frontend.parser.internal;

// @SuppressWarnings("unused")
}

///////////////////////////////////////////////////////////////////////////////

actionGuards: GUARD expressions -> expressions;

actionInput:
	(ID ':')? '[' idents ']' actionRepeat?
	{ };

actionInputs: actionInput (',' actionInput)* -> actionInput+;

actionOutput:
	(ID ':')? '[' expressions ']' actionRepeat?
	{ };

actionOutputs: actionOutput (',' actionOutput)* -> actionOutput+;

actionRepeat: REPEAT expression -> expression;

actionStatements: 'do' statement* -> statement*;

/***************************************************************************/
/* a CAL actor. */

actor: actorImport* ACTOR ID ('[' ']')? '(' actorParameters? ')'
	inputs=actorPortDecls? '==>' outputs=actorPortDecls? ':'
	actorDeclarations? 'end' EOF
	-> ACTOR ID
	^(PARAMETERS actorParameters?)
	^(INPUTS $inputs?)
	^(OUTPUTS $outputs?)
	^(ACTOR_DECLS actorDeclarations?);

/*****************************************************************************/
/* actor declarations */

// trick to differentiate the first ID from the others in the rule below
// allows the parser to use a stream for this id and a stream for other IDs

id: ID;

actorDeclaration:
  // hack so grammar can be expressed as LL(1)
  // why is the keyword "action" *after* the identifier of the action?
  // parsing "action a.b:" would be much easier than parsing "a.b: action"

  id (
    ((('.' ID)*) ':'
      (ACTION inputs=actionInputs? '==>' outputs=actionOutputs? guards=actionGuards? ('var' varDecls)? actionStatements? 'end'
        -> ^(ACTION ^(TAG id ID*) ^(INPUTS $inputs?) ^(OUTPUTS $outputs?)
        ^(GUARDS $guards?) ^(VARIABLES varDecls?) ^(STATEMENTS actionStatements?))

    | INITIALIZE '==>' actionOutputs? actionGuards? ('var' varDecls)? actionStatements? 'end'
        -> ^(INITIALIZE ^(TAG id ID*) INPUTS ^(OUTPUTS $outputs?)
        ^(GUARDS $guards?) ^(VARIABLES varDecls?) ^(STATEMENTS actionStatements?))
	)
    )

  // a variable declaration.
  // NOTE: a variable declared with no initial value is assignable (hence the last line)
  | ('(' attrs=typeAttrs ')')?
    varName=ID
    (  '=' expression -> ^(STATE_VAR ^(TYPE id $attrs?) $varName NON_ASSIGNABLE expression)
     | ':=' expression -> ^(STATE_VAR ^(TYPE id $attrs?) $varName ASSIGNABLE expression)
     | -> ^(STATE_VAR ^(TYPE id $attrs?) $varName ASSIGNABLE)) ';'
  )

// anonymous action
| ACTION actionInputs? '==>' actionOutputs? actionGuards? ('var' varDecls)? actionStatements? 'end'
  -> ^(ACTION TAG ^(INPUTS $inputs?) ^(OUTPUTS $outputs?) ^(GUARDS $guards?) ^(VARIABLES varDecls?) ^(STATEMENTS actionStatements?))

// anonymous initialize
| INITIALIZE '==>' actionOutputs? actionGuards? ('var' varDecls)? actionStatements? 'end'
  -> ^(INITIALIZE TAG INPUTS ^(OUTPUTS $outputs?) ^(GUARDS $guards?) ^(VARIABLES varDecls?) ^(STATEMENTS actionStatements?))

| priorityOrder -> priorityOrder

| FUNCTION ID '(' (varDeclNoExpr (',' varDeclNoExpr)*)? ')' '-->' typeDef
    ('var' varDecls)? ':'
      expression
    'end'
	-> ^(FUNCTION ID ^(PARAMETERS varDeclNoExpr*) ^(VARIABLES varDecls?) expression)

| PROCEDURE ID '(' (varDeclNoExpr (',' varDeclNoExpr)*)? ')'
    ('var' varDecls)?
    'begin' statement* 'end'
	-> ^(PROCEDURE ID ^(PARAMETERS varDeclNoExpr*) ^(VARIABLES varDecls?) ^(STATEMENTS statement*));

actorDeclarations: actorDeclaration+ (schedule actorDeclaration*)? -> actorDeclaration+ schedule?
  | schedule actorDeclaration* -> actorDeclaration* schedule;

/*****************************************************************************/

/* imports (ignored by ORCC) */
actorImport: 'import'
  ('all' qualifiedIdent ';' { }
| qualifiedIdent ';' { });

/*****************************************************************************/
/* actor parameters: type name (= expression)? */
actorParameter:
	typeDef ID ('=' expression)? -> ^(VARIABLE typeDef ID expression?);

actorParameters: actorParameter (',' actorParameter)* -> actorParameter+;

/*****************************************************************************/
/* actor ports */

actorPortDecls: varDeclNoExpr (',' varDeclNoExpr)* -> varDeclNoExpr+;

/*****************************************************************************/
/* expressions */

// we use an operator precedence parser in the Java code to correctly apply precedence

expression: un_expr
  ((bop un_expr)+ -> ^(EXPR_BINARY ^(EXPR un_expr+) ^(OP bop+))
  | -> un_expr);

bop: ('or' | '||') -> LOGIC_OR
| ('and' | '&&') -> LOGIC_AND
| '|' -> BITOR
| '&' -> BITAND
| '=' -> EQ | '!=' -> NE
| '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE
| '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT
| PLUS -> PLUS | MINUS -> MINUS
| DIV -> DIV | 'div' -> DIV_INT | 'mod' -> MOD | TIMES -> TIMES
| '^' -> EXP;

un_expr: postfix_expression -> postfix_expression
  | un_op un_expr -> ^(EXPR_UNARY un_op un_expr);

un_op: MINUS -> MINUS | 'not' -> LOGIC_NOT | '#' -> NUM_ELTS;

postfix_expression:
  '[' e=expressions (':' g=expressionGenerators)? ']' -> ^(EXPR_LIST $e $g?)
| 'if' e1=expression 'then' e2=expression 'else' e3=expression 'end' -> ^(EXPR_IF $e1 $e2 $e3)
| constant -> constant
| '(' expression ')' -> expression
| var=ID (
    '(' expressions? ')' -> ^(EXPR_CALL $var expressions?)
  |  ('[' expressions ']')+ -> ^(EXPR_IDX $var expressions+)
  | -> ^(EXPR_VAR $var));

constant:
  'true' -> ^(EXPR_BOOL 'true')
| 'false' -> ^(EXPR_BOOL 'false')
| FLOAT -> ^(EXPR_FLOAT FLOAT)
| INTEGER -> ^(EXPR_INT INTEGER)
| STRING -> ^(EXPR_STRING STRING);

expressionGenerator:
	'for' typeDef ID 'in' expression
	{ };

expressionGenerators: expressionGenerator (',' expressionGenerator)* -> expressionGenerator+;

expressions: expression (',' expression)* -> expression+;

/*****************************************************************************/
/* identifiers */

idents: ID (',' ID)* -> ID+;

/*****************************************************************************/
/* priorities */

priorityInequality: qualifiedIdent ('>' qualifiedIdent)+ ';' -> ^(INEQUALITY qualifiedIdent+);
	
priorityOrder: PRIORITY priorityInequality* 'end' -> ^(PRIORITY priorityInequality*);

/*****************************************************************************/
/* qualified ident */

qualifiedIdent: ID ('.' ID)* -> ^(TAG ID+);

/*****************************************************************************/
/* schedule */

schedule:
  SCHEDULE 'fsm' ID ':' stateTransition* 'end' -> ^(SCHEDULE ID ^(TRANSITIONS stateTransition*));

stateTransition:
	ID '(' qualifiedIdent ')' '-->' ID ';' -> ^(TRANSITION ID qualifiedIdent ID);

/*****************************************************************************/
/* statements */

statement:
  'begin' ('var' varDecls 'do')? statement* 'end' { }
| 'foreach' varDeclNoExpr 'in' (expression ('..' expression)?) ('var' varDecls)? 'do' statement* 'end' { }
| 'if' expression 'then' statement* ('else' statement*)? 'end' {  }
| 'while' expression ('var' varDecls)? 'do' statement* 'end' {  }

| ID (
    (('[' expressions ']')? ':=' expression ';') -> ^(ASSIGN ID ^(EXPRESSIONS expressions?) expression)
  |  '(' expressions? ')' ';' -> ^(CALL ID ^(EXPRESSIONS expressions?)) );

/*****************************************************************************/
/* type attributes and definitions */

/* a type attribute, such as "type:" and "size=" */
// thanks to the language designers, there is no specific name for type attributes.
// even though only type and expr attributes are taken into account.

typeAttr: ID (':' typeDef -> ^(TYPE typeDef ID) | '=' expression -> ^(EXPR expression ID)) ;

typeAttrs: typeAttr (',' typeAttr)* -> typeAttr+;

/* a type definition: bool, int(size=5), list(type:int, size=10)... */	
typeDef: ID ('(' attrs=typeAttrs ')')? -> ^(TYPE ID $attrs?);

/*****************************************************************************/
/* variable declarations */

/* simple variable declarations */
varDecl: typeDef ID
  ('=' expression -> ^(VARIABLE typeDef ID NON_ASSIGNABLE expression)
  | ':=' expression -> ^(VARIABLE typeDef ID ASSIGNABLE expression)
  | -> ^(VARIABLE typeDef ID ASSIGNABLE));

varDeclNoExpr: typeDef ID -> ^(VARIABLE typeDef ID ASSIGNABLE);

varDecls: varDecl (',' varDecl)* -> varDecl+;

///////////////////////////////////////////////////////////////////////////////
// TOKENS
