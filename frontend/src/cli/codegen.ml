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

(** [get_actors_network network] returns the set of actors instantiated by the network.
For instance for two instances "a1" and "a2" that instantiate the same actor "a", this
function returns "a". *)
let get_actors_network network =
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

(** [get_actors_vtl folder] returns the list of filenames of RVC-CAL actors defined
in the given VTL folder. *)
let get_actors_vtl folder =
	let rec aux instances path =
		let root = Filename.concat folder path in
		let dirs = Sys.readdir root in
		Array.fold_left
			(fun instances dir ->
				if String.length dir > 0 && dir.[0] = '.' then
					(* hidden folder, do not read *)
					instances
				else
					let file = Filename.concat root dir in
					if Sys.is_directory file then
						aux instances (Filename.concat path dir)
					else
						if Filename.check_suffix file ".cal" then
							SS.add (Filename.chop_suffix (Filename.concat path dir) ".cal") instances
						else
							instances)
			instances dirs
	in
	
	aux SS.empty ""

(** whether at least an actor has errors. *)
let model_has_errors = ref false

(** [load_instance options cls_name] loads the actor that has the given class name,
and generates its IR in the output folder given by [options.o_outdir]. *)
let load_instance options cls_name =
	let out_base = Filename.concat options.o_outdir cls_name in
	let abs_file = Filename.concat options.o_mp cls_name in

	let cal_file = abs_file ^ ".cal" in
	(* parse actor and convert it to IR. *)
	let ast_actor = Cal_parser_wrapper.parse_actor cal_file in
	let basename = Filename.basename abs_file in
	if ast_actor.Calast.ac_name <> basename then
		Asthelper.failwith {dummy_loc with Loc.file_name = cal_file}
			("The actor name is \"" ^ ast_actor.Calast.ac_name ^ "\", but it MUST be \
			the same as the file name, i.e. \"" ^ basename ^ "\"!");
	
	let actor =
		Ast2ir.ir_of_ast options out_base ast_actor
	in
	(try
		Codegen_ir.codegen {options with o_file = cls_name ^ ".cal"} actor
	with Asthelper.Compilation_error ->
		if options.o_debug then
			Printexc.print_backtrace stdout;
		model_has_errors := true)

(** [start options] generates the IR of each actor in a network whose top is given by
the options.o_file field, or else of each actor in the folder given by options.o_mp. *)
let start options =
	let actors =
		if options.o_file = "" then (
			printf "Finding actors in the VTL...\n%!";
			get_actors_vtl options.o_mp
		) else (
			printf "Parsing networks...\n%!";
			let xdf_file = Filename.concat options.o_mp options.o_file in
			let network = Xdf_parser.parse_network options xdf_file in
			get_actors_network network
		)
	in

	printf "Generating %i actors...\n%!" (SS.cardinal actors);
	SS.iter (load_instance options) actors
