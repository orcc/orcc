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

open Printf
open Calir
open IR
open Options

module SS = Asthelper.SS

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

let reference_mtime = ref 0.0
let num_cached = ref 0

(** [compile_actor options cls_name] loads the actor that has the given class name,
and generates its IR in the output folder given by [options.o_outdir]. *)
let compile_actor options cls_name =
	let abs_file = Filename.concat options.o_vtl cls_name in
	let cal_file = abs_file ^ ".cal" in

	let file_base = Filename.basename abs_file in
	let out_base = Filename.concat options.o_outdir file_base in
	
	let generate () =
		(* parse actor *)
		let ast_actor = Cal_parser_wrapper.parse_actor cal_file in
		let basename = Filename.basename abs_file in
		if ast_actor.Calast.ac_name <> basename then
			Asthelper.failwith {dummy_loc with Loc.file_name = cal_file}
				("The actor name is \"" ^ ast_actor.Calast.ac_name ^ "\", but it MUST be \
				the same as the file name, i.e. \"" ^ basename ^ "\"!");

		(* convert to IR *)
		let actor =
			Ast2ir.ir_of_ast options out_base ast_actor
		in

		(* apply SSA and generate JSON *)
		Codegen_ir.codegen {options with o_file = cls_name ^ ".cal"} actor
	in

	let ir_file = out_base ^ ".json" in
	if Sys.file_exists ir_file then
		let in_stats = Unix.stat cal_file in
		let out_stats = Unix.stat ir_file in
		
		(* if .cal file is more recent than .json file, *)
		(* or if front-end is more recent than .json file *)
		if in_stats.Unix.st_mtime > out_stats.Unix.st_mtime ||
			!reference_mtime > out_stats.Unix.st_mtime then
				generate ()
		else
			incr num_cached
	else
		(* IR has not been generated for this actor *)
		generate ()

(** [start options] generates the IR of each actor in a network whose top is given by
the options.o_file field, or else of each actor in the folder given by options.o_mp. *)
let start options =
	printf "Finding actors in the VTL...\n%!";
	let actors = get_actors_vtl options.o_vtl in
	
	(* reference time *)
	let exe_filename = Sys.argv.(0) in
	let stats = Unix.stat exe_filename in
	reference_mtime := stats.Unix.st_mtime;
	num_cached := 0;

	printf "Generating %i actors...\n%!" (SS.cardinal actors);
	let num_errors = ref 0 in
	SS.iter
		(fun file ->
			try
				compile_actor options file
			with Asthelper.Compilation_error ->
				if options.o_debug then
					Printexc.print_backtrace stdout;
				incr num_errors)
		actors;
	printf "%i actors were cached...\n%!" !num_cached;

	!num_errors
