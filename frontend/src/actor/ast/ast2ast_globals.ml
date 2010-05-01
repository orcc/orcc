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

(* ug = update globals *)

(** [load_var env loaded loc var_ref] retrieves the var_def binding from the
environment, and if it is a global whose type is not List, returns a new name
["local_" ^ var_ref], and add the binding to [loaded] if not already there. *)
let load_var env loaded loc var_ref =
	let var_def = ir_of_var_ref env loc var_ref in
	match (var_def.v_global, var_def.v_type) with
		| (true, TypeList _) -> (loaded, var_ref)
		| (true, _) ->
			let loaded =
				if SM.mem var_ref loaded then
					loaded
				else
					SM.add var_ref var_def loaded
			in
			(loaded, "local_" ^ var_ref)
		| (false, _) -> (loaded, var_ref)

(** [ug_expr env loaded expr] returns a [(loaded, expr)] tuple where references to
globals have been replaced appropriately by calling [load_var]. *)
let rec ug_expr env loaded expr =
	match expr with
		| Calast.ExprVar (loc, var_ref) ->
			let (loaded, var_ref) = load_var env loaded loc var_ref in
			(loaded, Calast.ExprVar (loc, var_ref))

		| Calast.ExprBOp (loc, e1, bop, e2) ->
			let (loaded, e1) = ug_expr env loaded e1 in
			let (loaded, e2) = ug_expr env loaded e2 in
			(loaded, Calast.ExprBOp (loc, e1, bop, e2))

		| Calast.ExprCall (loc, name, parameters) ->
			let (loaded, parameters) = ug_exprs env loaded parameters in
			(loaded, Calast.ExprCall (loc, name, parameters))
		
		| Calast.ExprIdx (loc, (var_loc, var_ref), indexes) ->
			let (loaded, var_ref) = load_var env loaded loc var_ref in
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
					let var_def = ir_of_var_ref env loc_var var in
					let (stored, var) =
						(* no need to check the variable's type because *)
						(* the var is NOT supposed to be an array *)
						if var_def.v_global then
							let stored =
								if SM.mem var stored then
									stored
								else
									SM.add var var_def stored
							in
							(stored, "local_" ^ var)
						else
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

let add_vars vars loaded stored =
	ignore (loaded, stored);
	vars

let add_loads loaded stmts =
	ignore (loaded);
	stmts

let add_stores stored stmts =
	ignore (stored);
	stmts

let add_globals_management env vars stmts =
	(*let (loaded, stored, stmts) = ug_stmts env SM.empty SM.empty stmts in
	let stmts = add_stores stored (List.rev stmts) in
	let stmts = add_loads loaded stmts in
	let vars = add_vars vars loaded stored in*)
	ignore (env);
	(vars, stmts)
