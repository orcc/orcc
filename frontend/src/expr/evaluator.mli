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

(** This modules provides evaluators for expressions. *)

(** Raised when a [Calast.expr] is a list expression. *)
exception List_expression

(** Raised when a [Calast.expr] is not statically evaluable by [eval]. *)
exception Not_evaluable of string

(** [eval env e] evaluates the [Calast.expr] [e] and returns its contents as a
[Calir.constant].
@raise NotEvaluable if [e] cannot be statically evaluated. *)
val eval : Ast2ir_util.env -> Calast.expr -> Calir.constant

(** [ir_of_var_info env vi] translates a [Calast.var_info]
to a [Calir.var_info]. *)
val ir_of_var_info : Ast2ir_util.env -> Calast.var_info -> Calir.IR.var_def

(** [ir_of_type env loc typ] translates a [Calast.type_def]
to a [Calir.type_def]. *)
val ir_of_type: Ast2ir_util.env -> Loc.t -> Calast.type_def -> Calir.type_def

(** [latt_of_expr expr] returns a [Calir.lattice] from a [Calir.IR.expr]. *)
val latt_of_expr: Calir.IR.expr -> Calir.lattice

(** [replace_const expr] returns a new expression where references to variables
with a constant lattice have been replaced by their values. *)
val replace_const: Calir.IR.expr -> Calir.IR.expr
