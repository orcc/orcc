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

open Printf
open Ast2ir_util
open Calir
open IR

module SM = Asthelper.SM

let local_name name = "local_" ^ name

(* ug = update globals *)

(** [load_var env loaded var_ref] retrieves the var_def binding from the
environment, and if it is a global whose type is not List, returns a new name
["local_" ^ var_ref], and add the binding to [loaded] if not already there. *)
let load_var env loaded var_ref =
	if has_binding_var env var_ref then
		let var_def = get_binding_var env var_ref in
		match (var_def.v_global, var_def.v_type) with
			| (true, TypeList _) -> (loaded, var_ref)
			| (true, _) ->
				let loaded =
					if SM.mem var_ref loaded then
						loaded
					else
						SM.add var_ref var_def loaded
				in
				(loaded, local_name var_ref)
			| (false, _) -> (loaded, var_ref)
	else
		(* no bindings => a local variable *)
		(loaded, var_ref)

(** [ug_expr env loaded expr] returns a [(loaded, expr)] tuple where references to
globals have been replaced appropriately by calling [load_var]. *)
let rec ug_expr env loaded expr =
	match expr with
		| Calast.ExprVar (loc, var_ref) ->
			let (loaded, var_ref) = load_var env loaded var_ref in
			(loaded, Calast.ExprVar (loc, var_ref))

		| Calast.ExprBOp (loc, e1, bop, e2) ->
			let (loaded, e1) = ug_expr env loaded e1 in
			let (loaded, e2) = ug_expr env loaded e2 in
			(loaded, Calast.ExprBOp (loc, e1, bop, e2))

		| Calast.ExprCall (loc, name, parameters) ->
			let (loaded, parameters) = ug_exprs env loaded parameters in
			(loaded, Calast.ExprCall (loc, name, parameters))
		
		| Calast.ExprIdx (loc, (var_loc, var_ref), indexes) ->
			let (loaded, var_ref) = load_var env loaded var_ref in
			let (loaded, indexes) = ug_exprs env loaded indexes in
			(loaded, Calast.ExprIdx (loc, (var_loc, var_ref), indexes))

		| Calast.ExprIf (loc, e1, e2, e3) ->
			let (loaded, e1) = ug_expr env loaded e1 in
			let (loaded, e2) = ug_expr env loaded e2 in
			let (loaded, e3) = ug_expr env loaded e3 in
			(loaded, Calast.ExprIf (loc, e1, e2, e3))

		| Calast.ExprList (loc, exprs, generators) ->
			let (loaded, exprs) = ug_exprs env loaded exprs in
			let (loaded, generators) =
				List.fold_left
					(fun (loaded, generators) (var_ref, expr) ->
						let (loaded, expr) = ug_expr env loaded expr in
						(loaded, (var_ref, expr) :: generators))
				(loaded, []) generators
			in
			(loaded, Calast.ExprList (loc, exprs, generators))

		| Calast.ExprUOp (loc, uop, expr) ->
			let (loaded, expr) = ug_expr env loaded expr in
			(loaded, Calast.ExprUOp (loc, uop, expr))
		
		| _ -> (loaded, expr)

(** [ug_exprs env loaded exprs] returns a [(loaded, exprs)] tuple where references to
globals have been replaced appropriately by calling [load_var] in the given
expression list. *)
and ug_exprs env loaded exprs =
	let (loaded, exprs) =
		List.fold_left
			(fun (loaded, exprs) expr ->
				let (loaded, expr) = ug_expr env loaded expr in
				(loaded, expr :: exprs))
		(loaded, []) exprs
	in
	(loaded, List.rev exprs)

(** [ug_instrs env loaded stored instrs] updates globals in instructions, and
returns a tuple [(loaded, stored, instrs)] where references to globals have been
replaced by ["local_" ^ global]. *)
let ug_instrs env loaded stored instrs =
	let (loaded, stored, instrs) =
		List.fold_left
			(fun (loaded, stored, instrs) instr ->
				match instr with
				| Calast.InstrAssignArray (loc, (loc_var, var), indexes, expr) ->
					(* no need to add the variable to "loaded" nor "stored" because *)
					(* the var is supposed to be an array *)
					let (loaded, expr) = ug_expr env loaded expr in
					let (loaded, indexes) = ug_exprs env loaded indexes in
					let instr = Calast.InstrAssignArray (loc, (loc_var, var), indexes, expr) in
					(loaded, stored, instr :: instrs)
	
				| Calast.InstrAssignVar (loc, (loc_var, var), expr) ->
					let (loaded, expr) = ug_expr env loaded expr in
					let (stored, var) =
						if has_binding_var env var then
							let var_def = get_binding_var env var in
							match (var_def.v_global, var_def.v_type) with
								| (true, TypeList _) -> (stored, var)
								| (true, _) ->
									let stored =
										if SM.mem var stored then
											stored
										else
											SM.add var var_def stored
									in
									(stored, local_name var)
								| _ -> (stored, var)
						else
							(* no bindings => a local variable *)
							(stored, var)
					in
					let instr = Calast.InstrAssignVar (loc, (loc_var, var), expr) in
					(loaded, stored, instr :: instrs)
	
				| Calast.InstrCall (loc, name, parameters) ->
					let (loaded, parameters) = ug_exprs env loaded parameters in
					let instr = Calast.InstrCall (loc, name, parameters) in
					(loaded, stored, instr :: instrs))
		(loaded, stored, []) instrs
	in
	(loaded, stored, List.rev instrs)

(** [ug_vars env loaded ug_vars] returns a [(loaded, ug_vars)] tuple where
references to globals have been replaced appropriately by calling
[load_var] in the given variable list. *)
let ug_vars env loaded vars =
	let (loaded, vars) =
		List.fold_left
			(fun (loaded, vars) var ->
				match var.Calast.v_value with
					| None -> (loaded, var :: vars)
					| Some expr ->
						let (loaded, expr) = ug_expr env loaded expr in
						let var =
							{var with Calast.v_value = Some expr}
						in
						(loaded, var :: vars))
		(loaded, []) vars
	in
	(loaded, List.rev vars)

(** [ug_stmts env loaded stored stmts] updates references to globals and
returns a tuple [(loaded, stored, stmts)]. *)
let rec ug_stmts env loaded stored stmts =
	let (loaded, stored, stmts) =
		List.fold_left
			(fun (loaded, stored, stmts) stmt ->
				match stmt with
					| Calast.StmtBlock (loc, var_info_list, children) ->
						let (loaded, var_info_list) = ug_vars env loaded var_info_list in
						let (loaded, stored, children) = ug_stmts env loaded stored children in
						let stmt = Calast.StmtBlock (loc, var_info_list, children) in
						(loaded, stored, stmt :: stmts)
	
					(* instructions. actually in this case without a 's'. *)
					| Calast.StmtInstr (loc, instrs) ->
						let (loaded, stored, instrs) = ug_instrs env loaded stored instrs in
						let stmt = Calast.StmtInstr (loc, instrs) in
						(loaded, stored, stmt :: stmts)
					
					(* if statement *)
					| Calast.StmtIf (loc, expr, stmts1, stmts2) ->
						let (loaded, expr) = ug_expr env loaded expr in
						let (loaded, stored, stmts1) = ug_stmts env loaded stored stmts1 in
						let (loaded, stored, stmts2) = ug_stmts env loaded stored stmts2 in
						let stmt = Calast.StmtIf (loc, expr, stmts1, stmts2) in
						(loaded, stored, stmt :: stmts)
	
					(* foreach statement *)
					| Calast.StmtForeach (loc, vi, expr, var_info_list, children) ->
						let (loaded, expr) = ug_expr env loaded expr in
						let (loaded, var_info_list) = ug_vars env loaded var_info_list in
						let (loaded, stored, children) = ug_stmts env loaded stored children in
						let stmt = Calast.StmtForeach (loc, vi, expr, var_info_list, children) in
						(loaded, stored, stmt :: stmts)
	
					(* while *)
					| Calast.StmtWhile (loc, expr, var_info_list, children) ->
						let (loaded, expr) = ug_expr env loaded expr in
						let (loaded, var_info_list) = ug_vars env loaded var_info_list in
						let (loaded, stored, children) = ug_stmts env loaded stored children in
						let stmt = Calast.StmtWhile (loc, expr, var_info_list, children) in
						(loaded, stored, stmt :: stmts))
		(loaded, stored, []) stmts
	in
	(loaded, stored, List.rev stmts)

let add_vars env vars loaded stored =
	let globals =
		SM.fold
			(fun var_name var_def loaded ->
				SM.add var_name var_def loaded)
		stored loaded
	in
	SM.fold
		(fun var_name var_def (env, vars) ->
			(* Note that the local variable must share the node of the global *)
			(* variable it references so that the expressions that reference it *)
			(* can use the lattice information. *)
			let local_var_def =
				{ var_def with
					v_assignable = true; (* may be assigned if loaded. *)
					v_global = false; (* is local *)
					v_name = local_name var_name }
			in
			let vars = local_var_def :: vars in
			let env = add_binding_var env local_var_def.v_name local_var_def in
			(env, vars))
	globals (env, vars)

let add_loads loaded stmts =
	let instrs =
		SM.fold
			(fun var_name _var_def instrs ->
				let expr = Calast.ExprVar (dummy_loc, var_name) in
				let instr =
					Calast.InstrAssignVar (dummy_loc, (dummy_loc, local_name var_name), expr)
				in
				instr :: instrs)
		loaded []
	in
	Calast.StmtInstr (dummy_loc, List.rev instrs) :: stmts

let add_stores stored stmts =
	let instrs =
		SM.fold
			(fun var_name _var_def instrs ->
				let expr = Calast.ExprVar (dummy_loc, local_name var_name) in
				let instr =
					Calast.InstrAssignVar (dummy_loc, (dummy_loc, var_name), expr)
				in
				instr :: instrs)
		stored []
	in
	stmts @ [Calast.StmtInstr (dummy_loc, List.rev instrs)]

let add_globals_management env vars stmts =
	let (loaded, stored, stmts) = ug_stmts env SM.empty SM.empty stmts in
	let stmts = add_loads loaded stmts in
	let stmts = add_stores stored stmts in
	let (env, vars) = add_vars env vars loaded stored in
	(env, vars, stmts)
