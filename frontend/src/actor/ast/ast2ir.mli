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

(** Transforms Cal AST to Cal IR: simplify AST, compute CFG and SSA information. *)

val time : float ref

(** [ir_of_ast options out_base actor] converts the AST of a [Calast.actor] to a
[Calir.actor] in the Intermediate Representation of CAL:
no more recursive blocks and CFG information computed *)
val ir_of_ast : Options.t -> string -> Calast.actor -> Calir.actor
