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

open Lexing
open Printf

let time = ref 0.

let parse_with_msg name f lexbuf =
	try
		f Cal_lexer.token lexbuf
	with Parsing.Parse_error ->
		let pos = lexbuf.lex_curr_p in
		Printf.fprintf stderr
			"File \"%s\", line %i, column %i: error when parsing %s\n"
				pos.pos_fname pos.pos_lnum (pos.pos_cnum - pos.pos_bol)
				name;
		exit 1

module SH = Asthelper.SH

(* memoization of parsed actors. *)
let actors = SH.create 50

(** [parse_actor path] parses the file whose absolute path is given by [path]
 and returns a [Calast.actor]. If anything goes wrong, the frontend exists. *)
let parse_actor file =
	try
		SH.find actors file
	with Not_found ->
		let t1 = Sys.time () in
		let ch = open_in file in
		
		let lexbuf = Lexing.from_channel ch in
		lexbuf.lex_curr_p <- {lexbuf.lex_curr_p with pos_fname = file};
		
		let actor = parse_with_msg "actor" Cal_parser.actor lexbuf in
		let actor = {actor with Calast.ac_file = file} in
		close_in ch;
		let t2 = Sys.time () in
		time := !time +. t2 -. t1;

		SH.replace actors file actor;
		actor

let parse_expr str =
	parse_with_msg "expression" Cal_parser.expression (Lexing.from_string str)
