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

open Printf
open Calir
open IR

let time_1 = ref 0.
let time_2 = ref 0.

let debug = ref false

(* SCC steps 3.c and 5 are dependent on the number of executable incoming edges *)
let nb_preds_executable graph node =
	CFG.fold_pred_e
		(fun edge n ->
			let (executable, _) = CFG.E.label edge in
			if !executable then
				n + 1
			else
				n)
	graph node 0

let meet_latt latt1 latt2 =
	match (latt1, latt2) with
	| (latt, LattTop)
	| (LattTop, latt) -> latt
	| (LattCst c1, LattCst c2) when c1 = c2 -> LattCst c1
	(* not constant! this case includes (bottom, any) and (any, bottom) *)
	| _ -> LattBot

(* SCC step 4 *)
let visit_phi ssa node var_def flags args =
	if !debug then
		printf "visit_phi: assign phi: %s"
			(Asthelper.PrettyPrinter.string_of_node node);
	let lattices =
		let list = ref [] in
		for i = 0 to 1 do
			let latt =
				if !(flags.(i)) then
					args.(i).vu_def.v_node.n_latt
				else
					LattTop
			in
			if !debug then
				printf "visit_phi: operand %i: %s\n" i (string_of_lattice latt);
			list := latt :: !list
		done;
		List.rev !list
	in
	match lattices with
	| [] -> failwith "visit_phi: no operands??"
	| h :: t ->
		let latt = List.fold_left (meet_latt) h t in
		let ssa =
			if node.n_latt <> latt then (
				node.n_latt <- latt;
				ssa @ var_def.v_refs
			) else
				ssa
		in
		if !debug then
			printf "visit_phi: output latt %s\n" (string_of_lattice node.n_latt);
		ssa

let visit_phis ssa list =
	List.fold_left
		(fun ssa phi ->
			match phi.n_kind with
				| AssignPhi (var_def, flags, args, _, _) -> visit_phi ssa phi var_def flags args
				| _ -> failwith "visit_phis: weird CFG")
		ssa list

(* SCC step 5 *)
let visit_expr graph flow ssa node =
	if !debug then
		printf "visit_expr\n";
	let latt1 = node.n_latt in
	match node.n_kind with
		| AssignPhi _ -> failwith "visit_expr: got a phi assignment?"
		| Join _ -> failwith "visit_expr: got a join?"

		| Empty
		| Return _ 
		| Store _ -> (flow, ssa)

		| HasTokens _
		| Peek _
		| Read _
		| Write _ -> (flow, ssa)

		(* a call is considered variable. *)
		| Call (var_ref_opt, _proc, _parameters) ->
			(match var_ref_opt with
				| None -> ()
				| Some var_ref ->
					let var_def = !var_ref in
					var_def.v_node.n_latt <- LattBot);
			(flow, ssa)

		| Load (var_ref, var_use, _indexes) ->
			let var_def = !var_ref in
			if !debug then
				printf "load into %s from %s_ whose latt is %s\n"
					(string_of_var var_def)
					(string_of_var var_use.vu_def)
					(string_of_lattice var_use.vu_def.v_node.n_latt);
			(match var_use.vu_def.v_node.n_latt with
				| LattCst cst ->
					node.n_latt <- LattCst cst;
					(flow, ssa @ var_def.v_refs)
				| _ ->
					node.n_latt <- LattBot;
					(flow, ssa))

		| AssignVar (var_ref, expr) ->
			let var_def = !var_ref in
			if !debug then
				printf "assign var: %s %s\n"
					(string_of_var var_def) (Asthelper.PrettyPrinter.string_of_expr expr);
			let latt = meet_latt latt1 (Evaluator.latt_of_expr expr) in
			if !debug then
				printf "assign var: %s, latt = %s, to node %i (var_def node %i)\n"
					(string_of_var var_def) (string_of_lattice latt) node.n_id var_def.v_node.n_id;
			node.n_latt <- latt;

			if latt1 <> latt then
				(* VisitExpression step 1 *)
				(flow, ssa @ var_def.v_refs)
			else
				(flow, ssa)

		| If (expr, bt, be, _) ->
			let latt = Evaluator.latt_of_expr expr in
			if !debug then
				printf "if %s, latt = %s, expr latt = %s\n"
					(Asthelper.PrettyPrinter.string_of_expr expr)
					(string_of_lattice latt1) (string_of_lattice latt);
			let latt = meet_latt latt1 latt in
			node.n_latt <- latt;
			if !debug then
				printf "if %s, latt = %s\n"
					(Asthelper.PrettyPrinter.string_of_expr expr) (string_of_lattice latt);
			
			if latt1 <> latt then
				(* VisitExpression step 2 *)
				match latt with
					| LattBot ->
						(* add all exit edges *)
						(flow @ CFG.succ_e graph node, ssa)
					| LattCst (CBool true) -> (flow @ [CFG.find_edge graph node bt], ssa)
					| LattCst (CBool false) -> (flow @ [CFG.find_edge graph node be], ssa)
					| _ ->
						Asthelper.failwith node.n_loc
							"type error: condition must be boolean!"
			else if latt = LattTop then (
				node.n_latt <- LattBot;
				(flow @ CFG.succ_e graph node, ssa)
			) else
				(flow, ssa)

		| While (expr, bt, be) ->
			let latt = meet_latt latt1 (Evaluator.latt_of_expr expr) in
			node.n_latt <- latt;
			if !debug then
				printf "while %s, latt = %s\n"
					(Asthelper.PrettyPrinter.string_of_expr expr) (string_of_lattice latt);
			
			if latt1 <> latt then
				(* VisitExpression step 2 *)
				match latt with
					| LattBot ->
						(* add all exit edges *)
						(flow @ CFG.succ_e graph node, ssa)
					| LattCst (CBool true) -> (flow @ [CFG.find_edge graph node bt], ssa)
					| LattCst (CBool false) -> (flow @ [CFG.find_edge graph node be], ssa)
					| _ ->
						Asthelper.failwith node.n_loc
							"type error: condition must be boolean!"
			else if latt = LattTop then (
				(flow @ CFG.succ_e graph node, ssa)
			) else
				(flow, ssa)

let rec scc graph flow ssa =
	match flow with
		(* no items from flow, try with ssa *)
		| [] ->
			(match ssa with
				| [] -> () (* SCC step 2: end of algorithm, halt execution. *)
				| use :: ssa ->
					if !debug then
						printf "from ssa\n";
					let node = use.vu_node in
					match node.n_kind with
						(* SCC step 4 visit phi *)
						| AssignPhi (var_def, flags, args, _, _) ->
							let ssa = visit_phi ssa node var_def flags args in
							scc graph flow ssa
						| _ ->
							(* SCC step 5 if at least one incoming edge is executable, visit expression *)
							let (flow, ssa) =
								if nb_preds_executable graph node >= 1 then
									visit_expr graph flow ssa node
								else
									(flow, ssa)
							in
							scc graph flow ssa)

		(* an item from program flow worklist *)
		| edge :: flow ->
			if !debug then
				printf "from flow\n";
			let (executable, _) = CFG.E.label edge in

			(* SCC step 3 *)
			if !executable then (
				(* SCC step 3: if executable, do nothing, just keep going *)
				scc graph flow ssa
			) else (
				(* SCC step 3.a set executable to true. *)
				executable := true;

				let node = CFG.E.dst edge in
				let (flow, ssa) = 
					match node.n_kind with
					(* SCC step 3.b visit phi expression, if any. *)
					| AssignPhi _ -> failwith "scc: a phi was found outside of a Join"
					| Join (_, list) ->
						let ssa = visit_phis ssa list in
						(flow, ssa)
					| _ ->
						(* SCC step 3.c if only one incoming edge is executable, visit expression. *)
						if nb_preds_executable graph node = 1 then
							visit_expr graph flow ssa node
						else
							(flow, ssa)
				in

				(* SCC step 3.d : if exactly one outgoing flow graph edge, add it to the list. *)
				let flow =
					match CFG.succ_e graph node with
					| [edge] -> edge :: flow
					| _ -> flow
				in
				scc graph flow ssa)

let rec remove graph node join =
	Iterators.iter_cfg
		(fun node _join ->
			remove_var_uses graph node;
			(match node.n_kind with
				| If (_, bt, be, if_join) ->
					remove graph bt if_join;
					remove graph be if_join
				| While (_, bt, _be) ->
					remove graph bt node
				| _ -> ());
			CFG.remove_vertex graph node)
		graph node join

let remove_phi_branch graph branch join =
	let prev = ref join in
	List.iter
		(fun phi ->
			match phi.n_kind with
				| AssignPhi (var_def, _, var_uses, _, _) ->
					let var_use = var_uses.(1 - branch) in
					remove_var_use var_use;

					let var_use = var_uses.(branch) in
					let node =
						mk_node (AssignVar (ref var_def, ExprVar (dummy_loc, var_use)))
					in

					(* this variable is now defined in node. *)
					var_def.v_node <- node;

					(* this variable is now used in node. *)
					var_use.vu_node <- node;
					append_node graph !prev node;
					prev := node
				| _ -> ())
	(get_join_nodes join);
	set_join_nodes join []

(** [remove_branch graph if_node ~keep bkeep dont_keep join] removes every node in the
[dont_keep] branch, and removes the if. [branch] is the number that we should keep.
The join is kept so it is still possible to iterate on the graph in apply_latt, but more
importantly because the phi function is still necessary. *)
let remove_branch graph if_node ~keep bkeep dont_keep join =
	(* get rid of the whole "dont_keep" branch until the join. *)
	remove graph dont_keep join;
	
	(* remove the branch from the phi *)
	remove_phi_branch graph keep join;

	(* links the predecessors of the "if" to "keep" and removes the "if". *)
	CFG.iter_pred
		(fun pred -> CFG.add_edge graph pred bkeep)
		graph if_node;
	CFG.remove_vertex graph if_node

exception Not_constant

let expr_of_latt latt =
	match latt with
		| LattTop
		| LattCst (CList _)
		| LattBot -> raise Not_constant
		| LattCst (CBool b) -> ExprBool (dummy_loc, b)
		| LattCst (CInt i) -> ExprInt (dummy_loc, i)
		| LattCst (CStr s) -> ExprStr (dummy_loc, s)

let rec apply_latt_single graph node =
	match node.n_kind with
	| Empty
	| Join _ -> ()

	| HasTokens _
	| Peek _
	| Read _
	| Write _ -> ()

	| Call (var_ref_opt, proc, parameters) ->
		let parameters = List.map Evaluator.replace_const parameters in
		node.n_kind <- Call (var_ref_opt, proc, parameters)

	| Load (var_ref, var_use, indexes) ->
		let indexes = List.map Evaluator.replace_const indexes in
		node.n_kind <- Load (var_ref, var_use, indexes)

	| Return expr ->
		node.n_kind <- Return (Evaluator.replace_const expr)

	| Store (var_use, indexes, expr) ->
		let expr = Evaluator.replace_const expr in
		let indexes = List.map Evaluator.replace_const indexes in
		node.n_kind <- Store (var_use, indexes, expr)

	| AssignVar (var_ref, expr) ->
		(try
			node.n_kind <- AssignVar (var_ref, expr_of_latt node.n_latt);
			remove_use expr
		with Not_constant ->
			node.n_kind <- AssignVar (var_ref, Evaluator.replace_const expr))

	| AssignPhi (var_ref, _, var_uses, _, _) ->
		let latt = node.n_latt in
		(try
			node.n_kind <- AssignVar (ref var_ref, expr_of_latt latt);
			Array.iter (remove_var_use) var_uses
		with Not_constant -> ())

	| If (expr, bt, be, if_join) ->
		(try
			let latt = node.n_latt in
			match latt with
			| LattCst (CBool true) ->
				(* remove else branch because it is never taken *)
				(*remove_use expr;
				apply_latt graph bt if_join;
				remove_branch graph node ~keep:0 bt be if_join*)
				()
			| LattCst (CBool false) ->
				(* remove then branch because it is never taken. *)
				remove_use expr;
				apply_latt graph be if_join;
				remove_branch graph node ~keep:1 be bt if_join
			| _ ->
				node.n_kind <- If (Evaluator.replace_const expr, bt, be, if_join);
				apply_latt graph bt if_join;
				apply_latt graph be if_join;
				raise Not_constant
		with Not_constant -> ())

	| While (expr, bt, be) ->
		let latt = node.n_latt in
		(match latt with
		| LattCst (CBool true) ->
			Asthelper.failwith node.n_loc
				"infinite loop"
		| LattCst (CBool false) ->
			Asthelper.failwith node.n_loc "the loop body is never executed!"
		| _ ->
			node.n_kind <- While (Evaluator.replace_const expr, bt, be));
		apply_latt graph bt node

and apply_latt graph node join =
	Iterators.iter_cfg
		(fun node _join -> apply_latt_single graph node)
		graph node join

let const_prop actor =
	let t1 = Sys.time () in
	Iterators.iter_actor_proc
		(fun proc ->
			(* SCC step 1 *)
			let flow = CFG.succ_e proc.p_cfg proc.p_entry in
			let ssa = [] in

			scc proc.p_cfg flow ssa)
		actor;
	let t2 = Sys.time () in
	time_1 := !time_1 +. (t2 -. t1);

	let t1 = Sys.time () in
	Iterators.iter_actor_proc
		(fun proc -> apply_latt proc.p_cfg proc.p_entry proc.p_exit)
		actor;

	let t2 = Sys.time () in
	time_2 := !time_2 +. (t2 -. t1)
