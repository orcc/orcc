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

// Actor Language Base Lexer

lexer grammar ALBaseLexer;

tokens {
  // common
  INPUT;
  INPUTS;
  OUTPUT;
  OUTPUTS;
  PORT;
  PARAMETERS;
  REPEAT;
  STATEMENTS;
  VARIABLE;
  VARIABLES;
  
  // actor declarations
  ACTOR_DECLS;
  STATE_VAR;
  
  // FSM
  TRANSITION;
  TRANSITIONS;
      
  // priority
  INEQUALITY;
    
  // action
  GUARDS;
  TAG;

  // expressions  
  EXPR;
  EXPR_BINARY;
  EXPR_UNARY;
  OP;
  
  EXPR_LIST;
  EXPR_IF;
  EXPR_CALL;
  EXPR_IDX;
  EXPR_VAR;

  EXPR_BOOL;
  EXPR_FLOAT;
  EXPR_INT;
  EXPR_STRING;

  // type definitions
  TYPE;
  TYPE_ATTRS;
  TYPE_LIST;
  
  // variable declarations
  ASSIGNABLE;
  NON_ASSIGNABLE;
  
  // binary operators
  LOGIC_OR;
  LOGIC_AND;
  BITOR;
  BITXOR;
  BITAND;
  EQ; NE;
  LT; GT; LE; GE;
  SHIFT_LEFT; SHIFT_RIGHT;
  DIV_INT; MOD;
  EXP;

  // unary operators
  BITNOT; LOGIC_NOT; NUM_ELTS;
  
  // statement
  ASSIGN;
  CALL;
  EXPRESSIONS;
}

///////////////////////////////////////////////////////////////////////////////
// TOKENS

// actor declarations
ACTION: 'action';
ACTOR: 'actor';
FUNCTION: 'function';
GUARD: 'guard';
INITIALIZE: 'initialize';
PRIORITY: 'priority';
PROCEDURE: 'procedure';
REPEAT: 'repeat';
SCHEDULE: 'schedule';

// core binary operators
PLUS: '+';
MINUS: '-';
TIMES: '*';
DIV: '/';

// literals
ID: LETTER (LETTER | '0'..'9')*;
	
fragment
LETTER:	'$' | 'A'..'Z' | 'a'..'z' | '_';

FLOAT: (('0'..'9')+ '.' ('0'..'9')* Exponent?
	| '.' ('0'..'9')+ Exponent?
	| ('0'..'9')+ Exponent);

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
Decimal: ('0' | '1'..'9' '0'..'9'*);

fragment
HexDigit: ('0'..'9'|'a'..'f'|'A'..'F');

fragment
Hexadecimal: '0' ('x'|'X') HexDigit+;

INTEGER: Decimal | Hexadecimal;

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
