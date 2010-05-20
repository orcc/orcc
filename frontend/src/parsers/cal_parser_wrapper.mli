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

(** This modules provides functions to parse CAL expressions and actors. *)

val time : float ref

(** [parse_actor path] parses the file whose absolute path is given by [path]
 and returns a [Calast.actor]. If anything goes wrong, the frontend exits. *)
val parse_actor : string -> Calast.actor

val parse_expr : string -> Calast.expr
