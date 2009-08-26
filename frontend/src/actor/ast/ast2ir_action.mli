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

(** Transforms actions from Cal AST to Cal IR *)

(** [map_actions cnt inputs_map outputs_map env ac_inputs ac_outputs actions]
maps actions from Cal AST to Cal IR. Doing so it checks that input patterns
and output expressions are correct (not using anonymous ports when not allowed),
that input tokens have distinct names than actor variables, and that action's
local variables have distinct names than both actor variables and input tokens. *)
val map_actions: int ref -> Ast2ir_util.env ->
	Ast2ir_util.env -> Ast2ir_util.env ->
	Calir.IR.var_def list -> Calir.IR.var_def list -> Calast.action list ->
	Calir.action list
