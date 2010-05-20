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

(** Managing options. *)

(** options *)
type t = {
	o_debug : bool; (** activate debug mode: compilation errors are not caught. *)
	o_dot_cfg : bool; (** output DOT files for CFG. *)
	o_dot_prio : (Str.regexp * bool option) list; (** output DOT files for priorities. *)
	o_file : string; (** input file *)
	o_keep : bool; (** keep intermediate files. *)
	o_outdir : string; (** output directory *)
	o_profiling : bool; (** print profiling information *)
	o_vtl : string; (** VTL folder *)
}

val get_dot_prio : t -> string -> bool

(** [init_options ()] parses options from the command-line, checks them, and returns
a record of type {t}. *)
val init_options : unit -> t
