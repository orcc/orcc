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

(** Transforms expressions from Cal AST to Cal IR. *)

(** abstract context type. *)
type context

(** creates a new context with an optional var_def. *)
val mk_context:
	?var_def:Calir.IR.var_def -> ?indexes: Calir.IR.expr list -> unit ->
		context

(** if necessary, creates an assignment from expr to var_def. *)
val mk_assign:
	Calir.CFG.t -> Calir.IR.node -> Calir.IR.var_def -> Calir.IR.expr ->
			Calir.IR.node

(** [ir_of_expr env vars graph node context expr] returns
[(env, vars, node, expr)]. *)
val ir_of_expr:
	Ast2ir_util.env -> Calir.IR.var_def list ->
		Calir.CFG.t -> Calir.IR.node -> context -> Calast.expr ->
			Ast2ir_util.env * Calir.IR.var_def list * Calir.IR.node * Calir.IR.expr

(** [ir_of_expr_list env vars graph node context expr_list] returns
[(env, vars, node, expr_list)]. *)
val ir_of_expr_list:
	Ast2ir_util.env -> Calir.IR.var_def list ->
		Calir.CFG.t -> Calir.IR.node -> context -> Calast.expr list ->
			Ast2ir_util.env * Calir.IR.var_def list * Calir.IR.node * Calir.IR.expr list

(** [declare_vars env vars var_info_list graph node] returns
[(env, vars, node)]. A new pfg_node is added to the 
end of [node] for each variable declared in [var_info_list]. *)
val declare_vars :
	Ast2ir_util.env -> Calir.IR.var_def list -> Calast.var_info list -> Calir.CFG.t -> Calir.IR.node ->
	Ast2ir_util.env * Calir.IR.var_def list * Calir.IR.node

(** [ir_of_foreach_expr env vars graph node var expr] translates the 
foreach [var] in [expr].
[expr] is expected to be Integers(x, y) where x and y are expressions
that can be translated to IR, but need not be constant.
The loop initialization is
  var := x;
and the loop condition becomes
  (var < y + 1)
as in standard C practice. *)
val ir_of_foreach_expr :
	Ast2ir_util.env -> Calir.IR.var_def list -> Calir.CFG.t -> Calir.IR.node ->
		Calast.var_info -> Calast.expr ->
	Ast2ir_util.env * Calir.IR.var_def list * Calir.IR.node * Calir.IR.expr
