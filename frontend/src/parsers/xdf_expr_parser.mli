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

(** This modules provides functions to parse XDF expressions. *)

(** tree type *)
type tree =
	| E of Loc.t * string * Xmlm.attribute list * tree list
	| D of Loc.t * string

val get_attribute :
  Loc.t -> string -> ((string * string) * 'a) list -> string -> 'a
	
val find_elt_get_rest :
  string ->
  (Loc.t -> string -> Xmlm.attribute list -> tree list -> tree list -> 'a) ->
  tree list -> 'a * tree list

val find_elt :
  string ->
  (Loc.t -> string -> Xmlm.attribute list -> tree list -> tree list -> 'a) ->
  tree list -> 'a

val iter_all :
  string ->
  (Loc.t -> string -> Xmlm.attribute list -> tree list -> 'a) ->
  tree list -> unit

val fold_all :
  string ->
  ('a list -> Loc.t -> string -> Xmlm.attribute list -> tree list -> 'a list) ->
  tree list -> 'a list

(** [fold_partial elt ~until f children acc] iterates over [children], calls [f] when
elements have a name that matches [elt], and returns when an element has a name that
matches [until]. *)
val fold_partial :
  string ->
  until:string list ->
  ('a -> Loc.t -> string -> Xmlm.attribute list -> tree list -> 'a) ->
  tree list -> 'a -> tree list * 'a

(** [parse_expr children] parses an XDF expression. *)
val parse_expr : tree list -> Calast.expr

val get_tree : string -> tree
