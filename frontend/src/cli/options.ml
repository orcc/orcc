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

type t = {
	o_cache : bool;
	o_debug : bool;
	o_dot_cfg : bool;
	o_dot_prio : (Str.regexp * bool option) list;
	o_file : string;
	o_keep : bool;
	o_mp : string;
	o_outdir : string;
	o_profiling : bool;
	o_values : (string * Calast.expr) list;
}

let get_flag spec name =
	List.fold_left
		(fun acc (regexp, value) ->
			if Str.string_match regexp name 0 then
				value
			else
				acc)
	None spec

let get_dot_prio options name =
	match get_flag options.o_dot_prio name with
		| None -> false
		| Some flag -> flag

(** [init_options ()] sets, checks and returns the options passed to cal2ir. *)
let init_options () =
	let cache = ref false in
	let debug = ref false in
	let dot_cfg = ref false in
	let keep = ref false in
	let input_file = ref "" in
	let output_folder = ref "" in
	let profiling = ref false in

  let speclist =
    [
			("-i", Arg.Set_string input_file,
			"<file> If the argument is a file, specifies the absolute path of the input XDF network.\ 
			Otherwise specifies the absolute path of the VTL folder.");

			("-o", Arg.Set_string output_folder,
			"<dir> Specifies the absolute path of the output folder where generated files will be put.");

			("--cache", Arg.Set cache, "When set, code will only be generated for actors \
			modified after code was last generated.");

			("--debug", Arg.Set debug, "When set, compilation errors are not caught \
			and their backtraces are printed.");

			("--dot-cfg", Arg.Set dot_cfg, "Prints DOT files showing CFG information.");

			("--keep", Arg.Set keep, "Prints files at each compilation stage.");

			("--profiling", Arg.Set profiling, "Prints profiling information.")
		]
	in

	let usage = "cal2ir [options] -i <file> -o <dir>" in
	let anon_fun _ = () in
	Arg.parse speclist anon_fun usage;
	
	(* checks *)
	if !input_file = "" then (
			Arg.usage speclist usage;
			prerr_endline "Orcc frontend: no input file given!";
			exit 1
	);
	
	if !output_folder = "" then (
			Arg.usage speclist usage;
			prerr_endline "Orcc frontend: no output folder given!";
			exit 1
	);
	
	if Sys.file_exists !input_file then (
		if not (Sys.is_directory !input_file) && not (Filename.check_suffix !input_file ".xdf") then (
			prerr_endline "Orcc frontend: the given input file must be a .xdf file.";
			prerr_endline !output_folder;
			exit 1
		)
	) else (
		prerr_endline "Orcc frontend: the given input file does not exist.";
		prerr_endline !input_file;
		exit 1
	);

	if Sys.file_exists !output_folder then (
		if not (Sys.is_directory !output_folder) then (
			prerr_endline "Orcc frontend: the given output path is not a directory.";
			prerr_endline !output_folder;
			exit 1
		)
	) else (
		prerr_endline "Orcc frontend: the given output path does not exist.";
		prerr_endline !output_folder;
		exit 1
	);
	
	let options =
		{
			o_cache = !cache;
			o_debug = !debug;
			o_dot_cfg = !dot_cfg;
			o_dot_prio = [];
			o_file =
				if Sys.is_directory !input_file then
					""
				else
					Filename.basename !input_file;
			o_keep = !keep;
			o_mp =
				if Sys.is_directory !input_file then
					!input_file
				else
					Filename.dirname !input_file;
			o_outdir = !output_folder;
			o_profiling = !profiling;
			o_values = [];
		}
	in

	print_endline "Starting Orcc frontend...";
	printf "model path: %s\n" options.o_mp;
	printf "top network: %s\n" options.o_file;
	printf "output folder: %s\n" options.o_outdir;

	printf "- cache is %s\n" (if options.o_cache then "enabled" else "disabled");
	printf "- debug is %s\n" (if options.o_debug then "enabled" else "disabled");
	printf "- profiling is %s\n" (if options.o_profiling then "enabled" else "disabled");
	if options.o_keep then
		printf "- will keep intermediate files\n";
	flush stdout;

	options
