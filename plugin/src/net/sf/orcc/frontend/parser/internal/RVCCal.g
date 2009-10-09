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
  // actor structure
  ACTOR;
  INPUTS;
  OUTPUTS;
  
  // actor parameters
  PARAMETER;
  PARAMETERS;
  
  // actor declarations
  ACTOR_DECLS;
  FUNCTION;
  PROCEDURE;
  STATE_VAR;
  
  // FSM
  SCHEDULE;
  TRANSITION;
  TRANSITIONS;
      
  // priority
  INEQUALITY;
  PRIORITY;
    
  // action
  GUARDS;
  TAG;
  
  // statements
  STATEMENTS;
  VARS;

  // expressions  
  EXPR;
  EXPR_OR;
  EXPR_AND;
  EXPR_BITOR;
  EXPR_BITAND;
  EXPR_EQ;
  EXPR_REL;
  EXPR_SHIFT;
  EXPR_ADD;
  EXPR_MUL;
  EXPR_EXP;
  EXPR_UN;
  
  EXPR_LIST;
  EXPR_IF;
  EXPR_CALL;
  EXPR_IDX;
  EXPR_VAR;

  EXPR_BOOL;
  EXPR_FLOAT;
  EXPR_INT;
  EXPR_STRING;
  
  // variable
  VAR;

  // type definitions
  TYPE;
  TYPE_ATTRS;
  
  // variable declarations
  ASSIGNABLE;
  NON_ASSIGNABLE;
  
  // others
  QID;
}

@lexer::header {
package net.sf.orcc.frontend.parser.internal;
}

@parser::header {
package net.sf.orcc.frontend.parser.internal;

// @SuppressWarnings("unused")
}

///////////////////////////////////////////////////////////////////////////////

actionGuards: 'guard' expressions -> expressions;

actionInput:
	(ID ':')? '[' idents ']' actionRepeat?
	{ };

actionInputs: actionInput (',' actionInput)* -> actionInput+;

actionOutput:
	(ID ':')? '[' expressions ']' actionRepeat?
	{ };

actionOutputs: actionOutput (',' actionOutput)* -> actionOutput+;

actionRepeat: 'repeat' expression -> expression;

actionStatements: 'do' statement* -> statement*;

/***************************************************************************/
/* a CAL actor. */

actor: actorImport* 'actor' id=ID ('[' ']')? '(' actorParameters? ')'
	inputs=actorPortDecls? '==>' outputs=actorPortDecls? ':'
	actorDeclarations 'end' EOF
	-> 'actor' $id
	^(PARAMETERS actorParameters?)
	^(INPUTS $inputs?)
	^(OUTPUTS $outputs?)
	^(ACTOR_DECLS actorDeclarations);

/*****************************************************************************/
/* actor declarations */

actorDeclaration:
  // hack so grammar can be expressed as LL(1)
  // why is the keyword "action" *after* the identifier of the action?
  // parsing "action a.b:" would be much easier than parsing "a.b: action"

  ID (
    ((('.' tag=ID)* -> $tag*) ':'
      ('action' inputs=actionInputs? '==>' outputs=actionOutputs? guards=actionGuards? ('var' varDecls)? actionStatements? 'end'
        -> ^('action' ^(TAG ID $tag*) ^(INPUTS $inputs?) ^(OUTPUTS $outputs?) ^(GUARDS $guards?) ^(VARS varDecls?) ^(STATEMENTS actionStatements?))

    | 'initialize' '==>' actionOutputs? actionGuards? ('var' varDecls)? actionStatements? 'end'
        -> ^('initialize' ^(TAG ID $tag*) INPUTS ^(OUTPUTS $outputs?) ^(GUARDS $guards?) ^(VARS varDecls?) ^(STATEMENTS actionStatements?))
	)
    )

  // a variable declaration.
  // NOTE: a variable declared with no initial value is assignable (hence the last line)
  | ('(' attrs=typeAttrs ')')?
    varName=ID
    (  '=' expression -> ^(STATE_VAR ^(TYPE ID ^(TYPE_ATTRS $attrs?)) $varName NON_ASSIGNABLE expression)
     | ':=' expression -> ^(STATE_VAR ^(TYPE ID ^(TYPE_ATTRS $attrs?)) $varName ASSIGNABLE expression)
     | -> ^(STATE_VAR ^(TYPE ID ^(TYPE_ATTRS $attrs?)) $varName ASSIGNABLE)) ';'
  )

// anonymous action
| 'action' actionInputs? '==>' actionOutputs? actionGuards? ('var' varDecls)? actionStatements? 'end'
  -> ^('action' TAG ^(INPUTS $inputs?) ^(OUTPUTS $outputs?) ^(GUARDS $guards?) ^(VARS varDecls?) ^(STATEMENTS actionStatements?))

// anonymous initialize
| 'initialize' '==>' actionOutputs? actionGuards? ('var' varDecls)? actionStatements? 'end'
  -> ^('initialize' TAG INPUTS ^(OUTPUTS $outputs?) ^(GUARDS $guards?) ^(VARS varDecls?) ^(STATEMENTS actionStatements?))

| priorityOrder -> priorityOrder

| 'function' ID '(' (varDeclNoExpr (',' varDeclNoExpr)*)? ')' '-->' typeDef
    ('var' varDecls)? ':'
      expression
    'end'
	-> FUNCTION

| 'procedure' ID '(' (varDeclNoExpr (',' varDeclNoExpr)*)? ')'
    ('var' varDecls)?
    'begin' statement* 'end'
	-> PROCEDURE;

actorDeclarations: actorDeclaration* (schedule actorDeclaration*)? -> actorDeclaration* schedule?;

/*****************************************************************************/

/* imports (ignored by ORCC) */
actorImport: 'import'
  ('all' qualifiedIdent ';' { }
| qualifiedIdent ';' { });

/*****************************************************************************/
/* actor parameters: type name (= expression)? */
actorParameter:
	typeDef ID ('=' expression)? -> ^(PARAMETER typeDef ID expression?);

actorParameters: actorParameter (',' actorParameter)* -> actorParameter+;

/*****************************************************************************/
/* actor ports */

actorPortDecls: varDeclNoExpr (',' varDeclNoExpr)* -> varDeclNoExpr+;

/*****************************************************************************/
/* expressions */

// what we want here is to avoid useless derivations in the AST
// that's why rules have the form X: y1=Y ((op y2=Y)+ -> EXPR_X (y1 y2+) | -> y1)
// this way if X is really only Y, then we just return the tree from Y.

expression: e1=and_expr ((('or' | '||') e2=and_expr)+ -> ^(EXPR_OR $e1 $e2+) | -> $e1 );

and_expr: e1=bitor_expr ((('and' | '&&') e2=bitor_expr)+ -> ^(EXPR_AND $e1 $e2+) | -> $e1 );

bitor_expr: e1=bitand_expr (('|' e2=bitand_expr)+ -> ^(EXPR_BITOR $e1 $e2+) | -> $e1 );

bitand_expr: e1=eq_expr (('&' e2=eq_expr)+ -> ^(EXPR_BITAND $e1 $e2+) | -> $e1 );

eq_expr: e1=rel_expr (((op='=' | op='!=') e2=rel_expr)+ -> ^(EXPR_EQ $e1 ($op $e2)+) | -> $e1 );

rel_expr: e1=shift_expr (((op='<' | op='>' | op='<=' | op='>=') e2=shift_expr)+ -> ^(EXPR_REL $e1 ($op $e2)+) | -> $e1);

shift_expr: e1=add_expr (((op='<<' | op='>>') e2=add_expr)+ -> ^(EXPR_SHIFT $e1 ($op $e2)+) | -> $e1 );

add_expr: e1=mul_expr (((op='+' | op='-') e2=mul_expr)+ -> ^(EXPR_ADD $e1 ($op $e2)+) | -> $e1 );

mul_expr: e1=exp_expr (((op='div' | op='mod' | op='*' | op='/') e2=exp_expr)+ -> ^(EXPR_MUL $e1 ($op $e2)+) | -> $e1 );

exp_expr: e1=un_expr (('^' e2=un_expr)+ -> ^(EXPR_EXP $e1 $e2+) | -> $e1 );

un_expr: postfix_expression -> postfix_expression
	| '-' un_expr -> ^(EXPR_UN '-' un_expr)
	| 'not' un_expr -> ^(EXPR_UN 'not' un_expr)
	| '#' un_expr  -> ^(EXPR_UN '#' un_expr);

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

idents: ID (',' ID)* { };

/*****************************************************************************/
/* priorities */

priorityInequality: qualifiedIdent ('>' qualifiedIdent)+ ';' -> ^(INEQUALITY qualifiedIdent qualifiedIdent+);
	
priorityOrder: 'priority' priorityInequality* 'end' -> ^(PRIORITY priorityInequality*);

/*****************************************************************************/
/* qualified ident */

qualifiedIdent: ID ('.' ID)* -> ^(QID ID+);

/*****************************************************************************/
/* schedule */

schedule:
  'schedule' 'fsm' ID ':' stateTransition* 'end' -> ^(SCHEDULE ID ^(TRANSITIONS stateTransition*));

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
    (('[' expressions ']')? ':=' expression ';') { }
  |  '(' expressions? ')' ';' { } );

/*****************************************************************************/
/* type attributes and definitions */

/* a type attribute, such as "type:" and "size=" */
// thanks to the language designers, there is no specific name for type attributes.
// even though only type and expr attributes are taken into account.

typeAttr: ID (':' typeDef -> ^(TYPE ID typeDef) | '=' expression -> ^(EXPR ID expression)) ;

typeAttrs: typeAttr (',' typeAttr)* -> typeAttr+;

/* a type definition: bool, int(size=5), list(type:int, size=10)... */	
typeDef: ID ('(' attrs=typeAttrs ')')? -> ^(TYPE ID ^(TYPE_ATTRS $attrs?));

/*****************************************************************************/
/* variable declarations */

/* simple variable declarations */
varDecl: typeDef ID ('=' expression | ':=' expression)? { };

varDeclNoExpr: typeDef ID -> ^(VAR typeDef ID);

varDecls: varDecl (',' varDecl)* -> varDecl+;

///////////////////////////////////////////////////////////////////////////////
// TOKENS

ID: LETTER (LETTER | '0'..'9')*;
	
fragment
LETTER:	'$' | 'A'..'Z' | 'a'..'z' | '_';

FLOAT: (('0'..'9')+ '.' ('0'..'9')* Exponent?
	| '.' ('0'..'9')+ Exponent?
	| ('0'..'9')+ Exponent);

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

INTEGER: ('0' | '1'..'9' '0'..'9'*);

STRING: '"' ( EscapeSequence | ~('\\'|'"') )* '"';

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

LINE_COMMENT: '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;};
MULTI_LINE_COMMENT: '/*' .* '*/' {$channel=HIDDEN;};
WHITESPACE: (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;};
