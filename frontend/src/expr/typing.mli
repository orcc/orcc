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

(** Typing module. *)

exception Type_error of string

val check_list_size: Loc.t -> Calir.type_def -> int ->
	Calir.type_def -> int -> unit

val type_of_cst: Calir.constant -> Calir.type_def

val type_of_expr: Calir.IR.expr -> Calir.type_def

(** [type_of_elt typ n] returns the type of the base element type of the
n-dimension list type [typ], or throw a type error. *)
val type_of_elt: Calir.type_def -> int -> Calir.type_def

(** [lub t1 t2] returns the least upper bound of types [t1] and [t2]. *)
val lub: Calir.type_def -> Calir.type_def -> Calir.type_def
