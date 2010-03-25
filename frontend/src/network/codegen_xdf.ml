(*****************************************************************************)
(* ORCC frontend                                                             *)
(* Copyright (c) 2008-2009, IETR/INSA of Rennes.                             *)
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

open Printf
open Calir
open IR
open Xdfast
open Options

module SS = Asthelper.SS

(** [get_actors network] returns the actors instantiated by the network.
For instance for two instances "a1" and "a2" that instantiate the same actor "a", this
function returns "a". *)
let get_actors network =
	let rec aux instances network =
		let graph = network.Ast.n_graph in
		G.fold_vertex
			(fun vertex instances ->
				match vertex with
					| Ast.Instance instance ->
						(match instance.Ast.i_contents with
							| Ast.Actor -> SS.add instance.Ast.i_class instances
							| Ast.Network network -> aux instances network)
					| _ -> instances)
		graph instances
	in
	
	aux SS.empty network

(** whether at least an actor has errors. *)
let model_has_errors = ref false

let load_instance options cls_name =
	let out_base = Filename.concat options.o_outdir cls_name in
	let abs_file = Filename.concat options.o_mp cls_name in

	let cal_file = abs_file ^ ".cal" in
	(* parse actor and convert it to IR. *)
	let ast_actor = Cal_parser_wrapper.parse_actor cal_file in
	let actor =
		Ast2ir.ir_of_ast options out_base ast_actor
	in
	(try
		Codegen_ir.codegen {options with o_file = cls_name ^ ".cal"} actor
	with Asthelper.Compilation_error ->
		if options.o_debug then
			Printexc.print_backtrace stdout;
		model_has_errors := true)

(** [start options] parses the XDF file in options.o_file, flattens the network,
and generates a single file. *)
let start options =
	printf "Parsing actors and networks...\n%!";
	let xdf_file = Filename.concat options.o_mp options.o_file in
	let network = Xdf_parser.parse_network options xdf_file in
	
	let actors = get_actors network in

	printf "Generating actors...\n%!";
	SS.iter (load_instance options) actors
