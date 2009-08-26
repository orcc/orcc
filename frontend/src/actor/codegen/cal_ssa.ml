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

(** This module transforms an actor to the SSA form using the algorithm described in
"Single-Pass Generation of Static Single Assignment Form for Structured Languages"
by Marc Brandis.

The principle is to iterate through the statements/CFG and for every assignment
in a "then" branch, an "else" branch, or a "foreach"/"while" branch, call insert_phi.
Once the branch is processed, commit_phi is called to update the hash table so that
variables that are phi-assigned will be correctly referenced in the next
statements. *)

open Printf
open Calir
open IR

let time = ref 0.

(* a var_def hash table. Test for name and suffix only. *)
module VH =
	Hashtbl.Make
		(struct
			type t = var_def
			let equal (a : var_def) (b : var_def) = (*a == b*)
				a.v_name = b.v_name && a.v_suffix = b.v_suffix
			let hash (v : var_def) = Hashtbl.hash (v.v_name, v.v_suffix(*, v.v_index*))
		end)

(** The symbol table. It contains a table that should be used when referring to 
a variable in an assignment, and another one that is to be used when referring to
a variable in RHS. The reason for this is that when we add a PHI assignment,
say:
  if x then
    x_1 := ... ;
    * here
    x_3 := x_1 + 1 ;
  else
  end
  x_2 := phi(x_1, ?);

After "here", we add the phi assignment, and we want to generate x_3 := x_1 + 1, and for
this we need to store the fact that assigning to x should use x_3, and using x should use
x_1. *)
type symtab = {
	sa: var_def VH.t; (** sa: symbol table for assignments. *)
	sr: var_def VH.t; (** sr: symbol table for references. *)
	mutable sv: var_def list; (** sv: list of variables declared. *)
}

(** [create_symtab vars] returns a new [symtab] with freshly-created hash tables.
It should be used before transforming an action/a procedure. *)
let create_symtab vars = {
	sa = VH.create 10;
	sr = VH.create 10;
	sv = vars
}

(** [replace_def symtab var new_var] replaces the variable used when assigning to [var]
by [new_var]. *)
let replace_def symtab var new_var = VH.replace symtab.sa var new_var

(** [replace_use symtab var new_var] replaces the variable used when referring to [var]
by [new_var]. *)
let replace_use symtab var new_var = VH.replace symtab.sr var new_var

(** [get_assign symtab var] returns the variable that should be used when assigning
to [var]. *)
let get_assign symtab var =
	try
		VH.find symtab.sa var
	with Not_found ->
		replace_def symtab var var;
		var

(** [get_use symtab var] returns the variable that should be used when referring
to [var] in RHS: expressions/phi functions. *) 
let get_use symtab var =
	try
		VH.find symtab.sr var
	with Not_found ->
		replace_use symtab var var;
		var

(** [new_assign symtab var node] calls get_assign, copies it to a new var_def and increment
its index, and calls replace_assign. *)
let new_assign symtab var node =
	let var_def = get_assign symtab var in
	let new_var_def =
		{ v_assignable = var_def.v_assignable;
			v_global = var_def.v_global;
			v_index = var_def.v_index + 1;
			v_loc = var_def.v_loc;
			v_name = var_def.v_name;
			v_node = node;
			v_refs = []; (* fresh variable with no references. *)
			v_suffix = var_def.v_suffix;
			v_type = var_def.v_type }
	in
	replace_def symtab var_def new_var_def;
	symtab.sv <- new_var_def :: symtab.sv;
	new_var_def

(** [rename_var_use symtab loc var_use] replaces the variable referenced by
[get_ref symtab var_def]. Calls [replace_var_use] too. *)
let rename_var_use symtab node loc var_use =
	let var_def = var_use.vu_def in
	match (var_def.v_global, var_def.v_type) with
		| (false, _) | (true, TypeList _) ->
			replace_var_use var_use node (get_use symtab var_def)
		| _ ->
			if var_def.v_global then
				Asthelper.failwith loc "a global variable should be loaded/stored"

(** [rename_expr symtab node expr] recursively explores [expr] and replaces the variable
referenced in every [ExprVar] by [get_ref symtab var_def]. *)
let rename_expr symtab node expr =
	let aux expr =
		match expr with
		| ExprVar (loc, var_use) -> rename_var_use symtab node loc var_use
		| _ -> ()
	in
	Iterators.iter_expr aux expr

let compare_var var1 var2 =
	let comp = String.compare var1.v_name var2.v_name in
	if comp = 0 then
		let comp = Pervasives.compare var1.v_suffix var2.v_suffix in
		comp
	else
		comp

(** [insert_phi symtab join i var v_old]. *)
let insert_phi symtab join i var v_old =
	let (is_while, join) =
		match join.n_kind with
			| While (_, bt, _be) -> (true, bt)
			| _ -> (false, join)
	in
	
	let find_phi join =
		let rec aux nodes =
			match nodes with
				| [] -> raise Not_found
				| {n_kind = AssignPhi (target, _, args, _, _)} as node :: rest ->
					if compare_var target var = 0 then
						(node, args)
					else
						aux rest
				| _ -> failwith "insert_phi: find_phi: weird CFG"
		in
		aux (get_join_nodes join)
	in

	(* phi is the phi-assignment to v *)
	try
		(* try to find it *)
		let (node, args) = find_phi join in
		replace_var_use args.(i - 1) node var
	with Not_found ->
		(* if there is no phi-assignment, create one *)
		let assign = mk_node Empty in
		let v_j = new_assign symtab var assign in
		let exe_flags = Array.copy (get_join_flags join) in
		let args =
			[| mk_var_use_node assign v_old;	mk_var_use_node assign v_old |]
		in
		assign.n_kind <- AssignPhi (v_j, exe_flags, args, v_old, join);
		replace_var_use args.(i - 1) assign var;

		(* append assign to the existing join *)
		add_join_node join assign;

		(* rename nodes that are not in the join node if the join node belongs to a while. *)
		if is_while then
			let nodes = get_join_nodes join in
			List.iter
				(fun use ->
					let node = use.vu_node in
					if not (List.memq node nodes) then
						replace_var_use use node v_j)
				v_old.v_refs

(** [restore_ref symtab join] is called when the "then" branch of an "if" has
been processed. It walks through the instructions of the join node [join]
and for each instruction v_i := phi(v_0, ..., v_n) / v_old it calls
replace_ref symtab v v_old. *)
let restore_ref symtab join =
	List.iter
		(fun node ->
			match node.n_kind with
				| AssignPhi (var, _, _, old, _) -> replace_use symtab var old
				| _ -> failwith "restore_ref: weird CFG")
	(get_join_nodes join)

(** [commit_phi symtab join outer_join branch_opt] is called after a foreach/if/while
has been processed. It walks through the instructions of the join node [join]
and for each instruction v_i := phi(v_0, ..., v_n) / v_old it calls
replace_use symtab v val, where val = v_old "if".
Then it calls insert_phi. *)
let commit_phi symtab join outer_join branch_opt =
	List.iter
		(fun node ->
			match node.n_kind with
				| AssignPhi (var, _, _, old, _) ->
					replace_use symtab var var;
					(match branch_opt with
						| None -> ()
						| Some i -> insert_phi symtab outer_join i var old)
				| _ -> failwith "commit_phi: weird CFG")
	(get_join_nodes join)

(** [update_var_ref symtab var_ref join branch_opt node] updates var_ref by
generating a new name. Should be used with Assign, Call and Load. *)
let update_var_ref symtab var_ref join branch_opt node =
	let var_def = !var_ref in
	if var_def.v_global then
		Asthelper.failwith var_def.v_loc
		  "a global variable should be loaded/stored";
	let v_i = new_assign symtab var_def node in

	(* this node now references v_i *)
	var_ref := v_i;

	(* saves the current name of var_def in the use table in v_old. *)
	let v_old = get_use symtab var_def in

	(* next uses of var_def will use v_i *)
	replace_use symtab var_def v_i;

	match branch_opt with
		| None -> ()
		| Some i ->
			(* call insert_phi. This is why we saved v_old before, otherwise *)
			(* it would be the same as v_i. *)
			insert_phi symtab join i v_i v_old

(** [add_phi symtab graph join branch_opt node] adds phi functions wherever needed. *)
let rec add_phi symtab graph join branch_opt node =
	Iterators.iter_cfg
		(fun node join ->
			match node.n_kind with
			| AssignPhi _
			| Empty
			| Join _ -> ()

			| Peek _
			| Read _
			| Write _ -> ()

			| Return expr -> rename_expr symtab node expr

			| Store (var_use, indexes, expr) ->
				(* we do not use rename_var_use because var_use is a global/an array *)
				replace_var_use var_use node (get_use symtab var_use.vu_def);
				List.iter (rename_expr symtab node) indexes;
				rename_expr symtab node expr

			| AssignVar (var_ref, expr) ->
				rename_expr symtab node expr;
				update_var_ref symtab var_ref join branch_opt node

			| HasTokens (var_ref, _, _) ->
				update_var_ref symtab var_ref join branch_opt node

			| Call (var_ref_opt, _proc, parameters) ->
				List.iter (rename_expr symtab node) parameters;
				(match var_ref_opt with
					| None -> ()
					| Some var_ref -> update_var_ref symtab var_ref join branch_opt node)

			| Load (var_ref, var_use, indexes) ->
				List.iter (rename_expr symtab node) indexes;
				replace_var_use var_use node var_use.vu_def;
				update_var_ref symtab var_ref join branch_opt node

			| If (expr, bt, be, if_join) ->
				(* rename expression before symtab is altered. *)
				rename_expr symtab node expr;
				
				add_phi symtab graph if_join (Some 1) bt;
				restore_ref symtab if_join;
				add_phi symtab graph if_join (Some 2) be;
				commit_phi symtab if_join join branch_opt
		
			| While (expr, bt, _be) ->
				(* rename expression before symtab is altered. *)
				rename_expr symtab node expr;
				
				add_phi symtab graph node (Some 2) bt;
				(* body is where phi-functions are placed. join is the outer join *)
				commit_phi symtab bt join branch_opt)
	graph node join

(** [compute_ssa actor] just iterates over the actor's actions/procedures,
and calls [add_phi] on each of them. *)
let compute_ssa actor =
	let t1 = Sys.time () in
	Iterators.iter_actor_proc
		(fun proc ->
			let symtab = create_symtab (List.rev proc.p_decls) in
			add_phi symtab proc.p_cfg proc.p_exit None proc.p_entry;
			proc.p_decls <- List.rev symtab.sv)
		actor;
	let t2 = Sys.time () in
	time := !time +. (t2 -. t1)
