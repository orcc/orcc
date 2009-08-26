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

(** [ir_of_stmts env vars node stmts] returns [(vars, node)] where [node]
is the last node translated. *)
val ir_of_stmts : Ast2ir_util.env -> Calir.IR.var_def list ->
		Calir.CFG.t -> Calir.IR.node -> Calast.stmt list ->
  Calir.IR.var_def list * Calir.IR.node