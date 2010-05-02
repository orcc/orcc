(*****************************************************************************)
(* Orcc frontend                                                             *)
(* Copyright (c) 2010, IETR/INSA of Rennes.                                  *)
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

(** [ir_of_stmts env vars node stmts] returns [(vars, node)] where [node]
is the last node translated. *)
val add_globals_management : Ast2ir_util.env -> Calir.IR.var_def list ->
	Calast.stmt list ->
	Ast2ir_util.env * Calir.IR.var_def list * Calast.stmt list
