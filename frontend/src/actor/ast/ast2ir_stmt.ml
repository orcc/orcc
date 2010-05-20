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

open Printf
open Ast2ir_util
open Calir
open IR

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)

(* conversion of println *)

let format_of_expr env vars graph node expr =
	let ctx = Ast2ir_expr.mk_context () in
	
	let rec aux left expr =
		match expr with
		| Calast.ExprBOp (_, e1, Calast.BOpPlus, e2) when left ->
			let (env, vars, node, exprs) = aux true e1 in
			let (env, vars, node, expr) =
				Ast2ir_expr.ir_of_expr env vars graph node ctx e2
			in
			(env, vars, node, exprs @ [expr])
		
		| _ ->
			let (env, vars, node, expr) =
				Ast2ir_expr.ir_of_expr env vars graph node ctx expr
			in
			(env, vars, node, [expr])
	in
	aux true expr

let mk_print env graph node loc parameters =
	let proc = Ast2ir_util.ir_of_proc_name env loc "print" in
	let call = mk_node_loc loc (Call (None, proc, parameters)) in
	CFG.add_edge graph node call;
	call

let ir_of_print env vars graph node loc parameters =
	let (env, vars, node, parameters) =
		match parameters with
		| [] -> (env, vars, node, [])
		| [expr] -> format_of_expr env vars graph node expr
		| _ -> Asthelper.failwith loc "print only accepts at most one argument"
	in
	(env, vars, mk_print env graph node loc parameters)

let ir_of_println env vars graph node loc parameters =
	let (env, vars, node, parameters) =
		match parameters with
		| [] -> (env, vars, node, [])
		| [expr] -> format_of_expr env vars graph node expr
		| _ -> Asthelper.failwith loc "println only accepts at most one argument"
	in
	let parameters = (parameters @ [ExprStr (loc, "\\n")]) in 
	(env, vars, mk_print env graph node loc parameters)

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* conversion of statements. During this conversion, variables declared *)
(* in inner blocks are moved up at the action/procedure scope. *)

(** ir_of_store converts an assignment to a store. It is called when var_def
is a List or a global. *)
let ir_of_store env vars graph node loc var_def indexes expr =
	(* typ is the type of the target of the assignment *)
	let typ =
		try
			Typing.type_of_elt var_def.v_type (List.length indexes)
		with Typing.Type_error reason ->
			Asthelper.failwith loc
				(sprintf "%s has an incorrect type: %s" (string_of_var var_def) reason)
	in

	let ctx =
		match typ with
			| TypeList _ -> Ast2ir_expr.mk_context ~var_def ~indexes ()
			| _ -> Ast2ir_expr.mk_context ()
	in
	let (env, vars, node, expr) =
		Ast2ir_expr.ir_of_expr env vars graph node ctx expr
	in

	match typ with
		| TypeList _ ->
			(env, vars, node)
		| _ ->
			(* the target of the assignment is a scalar global, or x[i] *)
			let store = mk_node_loc loc Empty in
			store.n_kind <- Store (mk_var_use var_def, indexes, expr);
			CFG.add_edge graph node store;
			(env, vars, store)

(** [ir_of_instrs env vars graph node instrs] transforms the [Calast.instr list] to
a [stmt list]. *)
let ir_of_instrs env vars graph node instrs =
	List.fold_left
		(fun (env, vars, node) instr ->
			match instr with
			| Calast.InstrAssignArray (loc, (loc_var, var), indexes, expr) ->
				let var_def = ir_of_var_ref env loc_var var in
				let ctx = Ast2ir_expr.mk_context () in
				let (env, vars, node, indexes) =
					Ast2ir_expr.ir_of_expr_list env vars graph node ctx indexes
				in

				ir_of_store env vars graph node loc var_def indexes expr

			| Calast.InstrAssignVar (loc, (loc_var, var), expr) ->
				let var_def = ir_of_var_ref env loc_var var in
				if not var_def.v_assignable then (
					Asthelper.failwith loc
						(sprintf "variable \"%s\" is not assignable" var)
				);
				
				(match (var_def.v_global, var_def.v_type) with
					| (true, _) | (_, TypeList _) ->
						(* a global or a List => need to store *)
						ir_of_store env vars graph node loc var_def [] expr
					| _ ->
						(* a local, scalar variable *)
						let ctx = Ast2ir_expr.mk_context ~var_def () in
						let (env, vars, node, expr) =
							Ast2ir_expr.ir_of_expr env vars graph node ctx expr
						in
						(env, vars, Ast2ir_expr.mk_assign graph node var_def expr))

			| Calast.InstrCall (loc, name, parameters) ->
				match name with
					| "print" -> ir_of_print env vars graph node loc parameters
					| "println" -> ir_of_println env vars graph node loc parameters
					| _ ->
						(* a call to a function not built-in *)
						let ctx = Ast2ir_expr.mk_context () in
						let (env, vars, node, parameters) =
							Ast2ir_expr.ir_of_expr_list env vars graph node ctx parameters
						in
						let proc = Ast2ir_util.ir_of_proc_name env loc name in
						let call = mk_node_loc loc (Call (None, proc, parameters)) in
						CFG.add_edge graph node call;
						(env, vars, call))
	(env, vars, node) instrs

(** [ir_of_stmts_rec env vars stmts] transforms the [Calast.stmt list] to
a [stmt list]. During this transformation, any variable declared in an inner block
(block, foreach, while) is moved at the action/procedure scope. This is why we need the 
[vars] variables, so that new variables can be added there. *)
let rec ir_of_stmts_rec env vars graph entry stmts =
	let (vars, exit) =
		List.fold_left
			(fun (vars, node) stmt ->
				match stmt with
					(* block statement. for this one, start by declaring the variables. *)
					| Calast.StmtBlock (_loc, var_info_list, children) ->
						let (env, vars, node) =
							Ast2ir_expr.declare_vars env vars var_info_list graph node
						in
						let (vars, children) = ir_of_stmts_rec env vars graph node children in
						
						(* note that we add the assignment instructions statement before *)
						(* children because children are likely to depend on those *)
						(* instructions. *)
						(vars, children)

					(* instructions. actually in this case without a 's'. *)
					| Calast.StmtInstr (_loc, instrs) ->
						let (_, vars, node) = ir_of_instrs env vars graph node instrs in
						(vars, node)
					
					(* if statement *)
					| Calast.StmtIf (loc, expr, stmts1, stmts2) ->
						create_if env vars graph node loc expr stmts1 stmts2

					(* foreach statement *)
					| Calast.StmtForeach (loc, vi, expr, var_info_list, children) ->
						let (env, vars, node, expr) =
							Ast2ir_expr.ir_of_foreach_expr env vars graph node vi expr
						in
						let loop_var = Ast2ir_util.get_binding_var env vi.Calast.v_name in
						let ((vars, node), (while_node, last_child, e2)) =
							create_while env vars graph node loc expr var_info_list children
						in

						(* appends an instruction to increment the foreach variable *)
						let expr =
							ExprBOp (loc,
								ExprVar (loc, mk_var_use loop_var),
								BOpPlus, ExprInt (loc, 1), TypeInt 32)
						in
						let assign = mk_node (AssignVar (ref loop_var, expr)) in
						CFG.remove_edge graph last_child while_node;
						CFG.add_edge graph last_child assign;
						CFG.add_edge_e graph (assign, (e2, Some 2), while_node);
						(vars, node)

					(* while *)
					| Calast.StmtWhile (loc, expr, var_info_list, children) ->
						let ctx = Ast2ir_expr.mk_context () in
						let (env, vars, node, expr) =
							Ast2ir_expr.ir_of_expr env vars graph node ctx expr
						in
						fst (create_while env vars graph node loc expr var_info_list children))
		(vars, entry) stmts
	in
	(vars, exit)

and create_if env vars graph node loc expr stmts1 stmts2 =
	let ctx = Ast2ir_expr.mk_context () in
	let (env, vars, node, expr) =
		Ast2ir_expr.ir_of_expr env vars graph node ctx expr
	in
	let (bt, be, e1, e2, join) = mk_header_if () in

	(* creates the if *)
	let kind = If (expr, bt, be, join) in
	let if_node = mk_node_loc loc kind in
	CFG.add_edge graph node if_node;
	
	mk_links_if_start graph if_node bt be;
	
	(* recurse on children of then and else branches *)
	let (vars, bt_last) = ir_of_stmts_rec env vars graph bt stmts1 in
	let (vars, be_last) = ir_of_stmts_rec env vars graph be stmts2 in
	
	(match (bt == bt_last, be == be_last) with
	| (true, true) ->
		Asthelper.failwith loc
			"an \"if\" statement does not contain anything"
	| (true, false) ->
		Asthelper.failwith loc
			"an \"if\" statement has an \"else\" branch but its \"then\" branch does not \ 
			contain any statements."
	| _ -> ());

	mk_links_if_end graph bt_last e1 be_last e2 join;
	(vars, join)

and create_while env vars graph node loc expr var_info_list children =
	let e1 = ref false in
	let e2 = ref false in
	let bt = mk_node (Join ([|e1; e2|], [])) in
	let (env, vars, child) =
		Ast2ir_expr.declare_vars env vars var_info_list graph bt
	in
	let (vars, last_child) = ir_of_stmts_rec env vars graph child children in

	(* adds the stmt returned by declare_vars before the children, because the variable *)
	(* declared in var_info_list should be initialized in the loop body. *)
	let (while_node, be) =
		mk_while graph node loc expr bt last_child e1 e2
	in

	((vars, be), (while_node, last_child, e2))

(** [ir_of_stmts env vars stmts] converts the [Calast.stmt list] to a
[stmt list]. It will add variables defined in local (inner) blocks
to the action local variables. This is why the function returns a
(vars, stmts) couple. *)
let ir_of_stmts env vars graph node stmts =
	let vars = List.rev vars in
	(*let (env, vars, stmts) =
		Ast2ast_globals.add_globals_management env vars stmts
	in*)
	
	let (vars, last) = ir_of_stmts_rec env vars graph node stmts in
	(vars, last)
