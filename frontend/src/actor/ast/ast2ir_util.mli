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

type binding =
  | Procedure of Calir.IR.proc
  | Variable of Calir.IR.var_def

type env = (binding Asthelper.SM.t * int Asthelper.SH.t)

(** [mk_env ()] creates a new environment. *)
val mk_env : unit -> env

val add_binding_proc: env -> Calir.IR.proc -> env

val add_binding_proc_check: env -> Calir.IR.proc -> env

val add_binding_var: env -> string -> Calir.IR.var_def -> env

val add_binding_var_check: env -> string -> Calir.IR.var_def -> env

val get_binding_proc: env -> string -> Calir.IR.proc

val get_binding_var: env -> string -> Calir.IR.var_def

val has_binding_var: env -> string -> bool

val get_suffix: env -> string -> int option

val remove_binding_var: env -> Calir.IR.var_def -> env

val reset_suffix: env -> unit

(** [mk_param name typ] creates an parameter with the given name and type. *)
val mk_param : string -> Calir.type_def -> Calir.IR.var_def

(** [mk_external name params return] creates an empty procedure with the given name,
parameters and return type, with [p_external = true]. *)
val mk_external : string -> Calir.IR.var_def list -> Calir.type_def -> Calir.IR.proc

(** [mk_tmp env vars typ] returns a triple (env, vars, new_var) where new_var is
a new temporary variable created with the given type, and added to env and vars. *)
val mk_tmp : env -> Calir.IR.var_def list -> Calir.type_def ->
	(env * Calir.IR.var_def list * Calir.IR.var_def)

(** [ir_of_var_ref env loc var_name] returns a [Calir.var_def] named [var_name]. *)
val ir_of_var_ref : env -> Loc.t -> string -> Calir.IR.var_def

(** [ir_of_proc_name env loc proc_name] returns the procedure definition
named [proc_name]. *)
val ir_of_proc_name : env -> Loc.t -> string -> Calir.IR.proc
