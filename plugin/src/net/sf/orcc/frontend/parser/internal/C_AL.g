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

// this grammar defines a grammar for C-AL (C Actor Language).
// C-AL has the same semantics as CAL (CAL Actor Language), but with
// a C-like syntax.

grammar C_AL;

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

/***************************************************************************/
/* a C AL actor. */

actor: actorImport* ACTOR ID '(' actorParameters? ')'
	'(' inputs=parameters? '==>' outputs=parameters? ')' '{'
	actorDeclarations? '}' EOF
	-> ACTOR ID
	^(PARAMETERS actorParameters?)
	^(INPUTS $inputs?)
	^(OUTPUTS $outputs?)
	^(ACTOR_DECLS actorDeclarations?);

/*****************************************************************************/
/* actor declarations */

// action or initialize
// difference syntax-wise is initialize has no inputs

actionOrInitialize:
  // action
  ACTION qualifiedIdent? '(' actionInputs? '==>' actionOutputs? ')'
    actionGuards?
    statement_block ->
      ^(ACTION qualifiedIdent? ^(INPUTS actionInputs?) ^(OUTPUTS actionOutputs?)
      ^(GUARDS actionGuards?)
      statement_block)
      
  // initialize
| INITIALIZE qualifiedIdent? '(' actionOutputs? ')'
    actionGuards? 
    statement_block ->
      ^(INITIALIZE qualifiedIdent? INPUTS ^(OUTPUTS actionOutputs?)
      ^(GUARDS actionGuards?)
      statement_block);

// actor declaration:
//   - action/initialize
//   - priority
//   - assignable state variable: type ID;
//   - assignable state variable with initial value: type ID = expression;
//   - constant state variable with initial value: constant type ID = expression;
actorDeclaration:
  actionOrInitialize
| priorityOrder

| 'const' typeDef ID
  ( '=' expression ';' -> ^(STATE_VAR typeDef ID NON_ASSIGNABLE expression)
  | typeListSpec '=' expression ';' -> ^(STATE_VAR ^(TYPE_LIST typeDef typeListSpec) ID NON_ASSIGNABLE expression))
 
  // NOTE: a variable declared with no initial value is assignable (hence the last line)
| typeDef ID
  ( ('=' expression)? ';' -> ^(STATE_VAR typeDef ID ASSIGNABLE expression?)
  | typeListSpec ('=' expression)? ';' -> ^(STATE_VAR ^(TYPE_LIST typeDef typeListSpec) ID ASSIGNABLE expression?)

  | '(' parameters? ')' '{'
      varDecl*
      'return' expression ';'
    '}' -> ^(FUNCTION ID ^(PARAMETERS parameters?) ^(VARIABLES varDecl*) expression)
  )

| 'void' ID '(' parameters? ')' statement_block -> ^(PROCEDURE ID ^(PARAMETERS parameters?) statement_block);

// actor declarations:
//   - declarations
//   - declarations schedule
//   - declarations schedule declarations
//   - schedule
//   - schedule declarations
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
/* expressions */

// we use an operator precedence parser in the Java code to correctly apply precedence

expression: un_expr
  ((bop un_expr)+ -> ^(EXPR_BINARY ^(EXPR un_expr+) ^(OP bop+))
  | -> un_expr);

bop: '||' -> LOGIC_OR
| '&&' -> LOGIC_AND
| '|' -> BITOR
| '^' -> BITXOR
| '&' -> BITAND
| '==' -> EQ | '!=' -> NE
| '<' -> LT | '>' -> GT | '<=' -> LE | '>=' -> GE
| '<<' -> SHIFT_LEFT | '>>' -> SHIFT_RIGHT
| PLUS -> PLUS | MINUS -> MINUS
| DIV -> DIV | '%' -> MOD | TIMES -> TIMES
| '**' -> EXP;

un_expr: postfix_expression -> postfix_expression
  | (op=(MINUS -> MINUS)
    | op=('~' -> BITNOT)
    | op=('!' -> LOGIC_NOT)
    | op=('#' -> NUM_ELTS)) un_expr -> ^(EXPR_UNARY $op un_expr);

postfix_expression:
  '{' e=expressions '}' -> ^(EXPR_LIST $e)
| 'for' '(' generatorDecls ')' '{' expression '}'
| 'if' e1=expression '{' e2=expression '}' 'else' '{' e3=expression '}' -> ^(EXPR_IF $e1 $e2 $e3)
| constant -> constant
| '(' expression ')' -> expression
| var=ID (
    '(' expressions? ')' -> ^(EXPR_CALL $var expressions?)
  |  ('[' expression ']')+ -> ^(EXPR_IDX $var expression+)
  | -> ^(EXPR_VAR $var));

constant:
  'true' -> ^(EXPR_BOOL 'true')
| 'false' -> ^(EXPR_BOOL 'false')
| FLOAT -> ^(EXPR_FLOAT FLOAT)
| INTEGER -> ^(EXPR_INT INTEGER)
| STRING -> ^(EXPR_STRING STRING);

generatorDecl: parameter ':' expression '..' expression;

generatorDecls: generatorDecl (',' generatorDecl)*;

expressions: expression (',' expression)* -> expression+;

/*****************************************************************************/
/* identifiers */

idents: ID (',' ID)* -> ID+;

/*****************************************************************************/
/* parameters */

parameter: typeDefId -> ^(VARIABLE typeDefId ASSIGNABLE);

parameters: parameter (',' parameter)* -> parameter+;

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
  statement_block
| 'for' '(' parameter ':' e1=expression '..' e2=expression ')' statement_block { }
| 'if' '(' expression ')' s1=statement_block ('else' s2=statement_block)? { }
| 'while' '(' expression ')' statement_block { }

| ID (
    (('[' expression ']')* '=' expression ';') { }
  |  '(' expressions? ')' ';' { } );

statement_block: '{' varDecl* statement* '}' -> ^(VARIABLES varDecl*) ^(STATEMENTS statement*);

/*****************************************************************************/
/* type attributes and definitions */

typeDef:
| 'bool' -> ^(TYPE 'bool')
| 'char' -> ^(TYPE 'char')
| 'short' -> ^(TYPE 'short')
| 'int' ('(' expression ')')? -> ^(TYPE 'int' ^(EXPR expression)?)
| 'unsigned'
  ('char' -> ^(TYPE 'unsigned' 'char')
  | 'short' -> ^(TYPE 'unsigned' 'short')
  | 'int' ('(' expression ')')? -> ^(TYPE 'unsigned' 'int' ^(EXPR expression)?))
| 'float' -> ^(TYPE 'float');

typeDefId:
  typeDef ID
  ( -> typeDef ID
  | typeListSpec -> ^(TYPE_LIST typeDef typeListSpec) ID);

typeListSpec: ('[' expression ']')+ -> expression+;

/*****************************************************************************/
/* variable declarations */

/* simple variable declarations */
varDecl:
  'const' typeDefId '=' expression ';' -> ^(VARIABLE typeDefId NON_ASSIGNABLE expression)
| typeDefId ('=' expression)? ';' -> ^(VARIABLE typeDefId ASSIGNABLE expression?);

///////////////////////////////////////////////////////////////////////////////
// TOKENS
