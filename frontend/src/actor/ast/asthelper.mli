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

(** helper functions for Cal IR: pretty-printer, iterators, compute CFG. *)

(** An int hash table. *)
module IH : Hashtbl.S with type key = int

(** An int map. *)
module IM : Map.S with type key = int

(** An int set. *)
module IS : Set.S with type elt = int

(** A string hash table. *)
module SH : Hashtbl.S with type key = string

(** A string map. *)
module SM : Map.S with type key = string

(** A string set. *)
module SS : Set.S with type elt = string

exception Compilation_error

val string_of_loc : Loc.t -> string

val failwith : Loc.t -> string -> 'a

(** [mk_var_info assignable global loc name vtype value_opt] *)
val mk_var_info : bool -> bool -> Loc.t -> string -> Calast.type_def -> Calast.expr option ->
	Calast.var_info

external loc_of_expr : Calast.expr -> Loc.t = "%field0"

module PrettyPrinter :
  sig
    val pp_expr : ?prec:int -> Format.formatter -> Calir.IR.expr -> unit
    val pp_type : Format.formatter -> Calir.type_def -> unit
    val pp_var_def : Format.formatter -> Calir.IR.var_def -> unit
		val pp_node : Calir.CFG.t -> Calir.IR.node -> Format.formatter -> Calir.IR.node -> unit
    val pp_actor : Format.formatter -> Calir.actor -> unit
    val pp_flush : Format.formatter -> unit
    val string_of_bop : Calir.bop -> string
    val string_of_expr : Calir.IR.expr -> string
    val string_of_type : Calir.type_def -> string
		val string_of_node : Calir.IR.node -> string
		val string_of_uop : Calir.uop -> string
  end

(** [print_cfg filename actor]. *)
val print_cfg : string -> Calir.actor -> unit
