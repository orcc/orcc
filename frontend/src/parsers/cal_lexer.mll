(*****************************************************************************)
(* Orcc frontend                                                             *)
(* Copyright (c) 2008-2010, IETR/INSA of Rennes.                             *)
(* All rights reserved.                                                      *)
(*                                                                           *)
(* This software is governed by the CeCILL-B license under French law and    *)
(* abiding by the rules of distribution of free software. You can  use,      *)
(* modify and/ or redistribute the software under the terms of the CeCILL-B  *)
(* license as circulated by CEA, CNRS and INRIA at the following URL         *)
(* "http://www.cecill.info".                                                 *)
(*                                                                           *)
(* Matthieu WIPLIEZ <Matthieu.Wipliez@insa-rennes.fr                         *)
(*****************************************************************************)

(* File cal_lexer.mll *)
{
open Printf
open Lexing
open Cal_parser

(* String construction *)
let strbuf = Buffer.create 256

module SH = Asthelper.SH

(* Keywords *)
let keyword_table = SH.create 62
let _ =
  List.iter
    (fun (kwd, tok) -> SH.add keyword_table kwd tok)
    [
			("action", ACTION);
			("actor", ACTOR);
			("all", ALL);
			("and", AND);
			("any", ANY);
			("at", AT);
			("begin", BEGIN);
			("choose", CHOOSE);
			("div", DIV_INT);
			("do", DO);
			("delay", DELAY);
			("else", ELSE);
			("end", END);
			("false", FALSE);
			("for", FOR);
			("foreach", FOREACH);
			("fsm", FSM);
			("function", FUNCTION);
			("guard", GUARD);
			("if", IF);
			("in", IN);
			("import", IMPORT);
			("initialize", INITIALIZE);
			("multi", MULTI);
			("mod", MOD);
			("not", NOT);
			("or", OR);
			("priority", PRIORITY);
			("procedure", PROCEDURE);
			("regexp", REGEXP);
			("repeat", REPEAT);
			("schedule", SCHEDULE);
			("size", SIZE);
			("then", THEN);
			("true", TRUE);
			("type", TYPE);
			("var", VAR);
			("while", WHILE);

			("bool", TYPE_BOOL);
			("float", TYPE_FLOAT);
			("int", TYPE_INT);
			("List", TYPE_LIST);
			("String", TYPE_STRING);
			("uint", TYPE_UINT);
    ]

(* Update the current location with file name and line number. *)
let update_loc lexbuf line chars =
  let pos = lexbuf.lex_curr_p in
  lexbuf.lex_curr_p <- { pos with
    pos_lnum = pos.pos_lnum + line;
    pos_bol = pos.pos_cnum - chars;
  }

(* Matches either \ or $. Why so many backslashes? Because \ has to be escaped*)
(* in strings, so we get \\. \, | and $ also have to be escaped in regexps, *)
(* so we have \\\\ \\| \\$. *)
let re_id = Str.regexp "\\\\\\|\\$"
}

(* Numbers *)
let nonZeroDecimalDigit = ['1'-'9']

let decimalDigit = '0' | nonZeroDecimalDigit
let decimalLiteral = nonZeroDecimalDigit (decimalDigit)*

let hexadecimalDigit = decimalDigit | ['a'-'f'] | ['A'-'F']
let hexadecimalLiteral = '0' ('x'|'X') hexadecimalDigit (hexadecimalDigit)*

let octalDigit = ['0'-'7']
let octalLiteral = '0' (octalDigit)*

let integer = decimalLiteral | hexadecimalLiteral | octalLiteral

let exponent = ('e'|'E') ('+'|'-')? decimalDigit+
let real = decimalDigit+ '.' (decimalDigit)* exponent?
| '.' decimalDigit+ exponent?
| decimalDigit+ exponent

(* Identifiers *)
let char = ['a'-'z' 'A'-'Z']
let any_identifier = (char | '_' | decimalDigit | '$')+
let other_identifier =
    (char | '_') (char | '_' | decimalDigit | '$')*
  | '$' (char | '_' | decimalDigit | '$')+
let identifier = '\\' any_identifier '\\' | other_identifier

let newline = ('\010' | '\013' | "\013\010")

(* Token rule *)
rule token = parse
  | [' ' '\t'] {token lexbuf}
	| newline { update_loc lexbuf 1 0; token lexbuf }

	| "at*" { AT_STAR }
	| "->" { ARROW }
	| '&' { BITAND }
	| '|' { BITOR }
	| ':' { COLON }
	| ":=" { COLON_EQUAL }
	| ',' { COMMA }
	| '/' { DIV }
	| '.' { DOT }
	| "-->" { DOUBLE_DASH_ARROW }
	| "==>" { DOUBLE_EQUAL_ARROW }
	| '=' { EQ }
	| '^' { EXP }
	| ">=" { GE }
	| '>' { GT }
	| '{' { LBRACE }
	| '[' { LBRACKET }
	| "<=" { LE }
	| '<' { LT }
	| '(' { LPAREN }
	| '-' { MINUS }
	| "!=" { NE }
	| '+' { PLUS }
	| '}' { RBRACE }
	| ']' { RBRACKET }
	| ')' { RPAREN }
	| ';' { SEMICOLON }
	| '#' { SHARP }
	| "<<" { SHIFT_LEFT }
	| ">>" { SHIFT_RIGHT }
	| '*' { TIMES }

  | integer as lxm
		{ try
				INT (int_of_string lxm)
			with Failure "int_of_string" ->
				prerr_endline "integer constant is too large";
				raise Parsing.Parse_error }

  | real as lxm { FLOAT (float_of_string lxm) }

  | identifier as ident {
		try
			SH.find keyword_table ident
		with Not_found ->
			let ident = Str.global_replace re_id "_" ident in
			IDENT ident }

  | '"' { let str = string lexbuf in STRING str }
  | "//" { single_line_comment lexbuf }
	| "/*" { multi_line_comment lexbuf }
  | eof { EOF }

and string = parse
	| "\\\"" { Buffer.add_string strbuf "\\\""; string lexbuf }
	| '"' { let s = Buffer.contents strbuf in Buffer.clear strbuf; s }
	| _ as c { Buffer.add_char strbuf c; string lexbuf }
and single_line_comment = parse
  | newline { update_loc lexbuf 1 0; token lexbuf }
	| _ { single_line_comment lexbuf }
and multi_line_comment = parse
  | "*/" { token lexbuf }
	| newline { update_loc lexbuf 1 0; multi_line_comment lexbuf }
	| _ { multi_line_comment lexbuf }
