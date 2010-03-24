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

(* transformation from Cal AST to CAL IR *)
open Printf
open Ast2ir_util
open Calir
open IR
open Options

let time = ref 0.

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* mapping from AST to IR and checking stuff *)

(** [map_params env vars] converts function/procedure parameters.
Actor parameters are translated by mk_globals. *)
let map_params env vars =
	let (env, vars) =
		List.fold_left
			(fun (env, vars) var_info ->
				let vi = Evaluator.ir_of_var_info env var_info in
				(* IMPORTANT: a parameter must be considered variable to have proper *)
				(* behavior at constant propagation time. *)
				vi.v_node.n_latt <- LattBot;
				(add_binding_var_check env vi.v_name vi, vi :: vars))
		(env, []) vars
	in
	(env, List.rev vars)

(** [add_value initializes var_info value] adds a statement StmtInstr
that contains a single instruction InstrAssignVar that assigns
value to var_info, at the beginning of every initialize action. If no
initialize action exists, an empty initialize is created. *)
let add_value initializes var_info value =
	let initializes =
		if initializes = [] then
			[{ Calast.a_guards = [];
			a_inputs = [];
			a_loc = dummy_loc;
			a_outputs = [];
			a_stmts = [];
			a_tag = [];
			a_vars = [] }]
		else
			initializes
	in
	List.map
		(fun initialize ->
			let var_ref = (dummy_loc, var_info.Calast.v_name) in
			let instr = Calast.InstrAssignVar (dummy_loc, var_ref, value) in
			let stmt = Calast.StmtInstr (dummy_loc, [instr]) in
			{initialize with Calast.a_stmts = stmt :: initialize.Calast.a_stmts})
		initializes

(** [mk_var_def env globals initializes var_info value] translates
the var_info and value, and adds it to the environment. *)
let mk_var_def env globals initializes var_info value =
	let vd = Evaluator.ir_of_var_info env var_info in

	(* compute initial value *)
	let (init, initializes) =
		match value with
		| None -> (None, initializes)
		| Some v ->
			try
				let constant =
					try
						Evaluator.eval env v
					with Evaluator.Not_evaluable reason ->
						Asthelper.failwith var_info.Calast.v_loc
							("the initial value of the global variable is not statically evaluable: " ^
							reason)
				in
				(try
					ignore (Typing.lub vd.v_type (Typing.type_of_cst constant))
				with Typing.Type_error reason ->
					Asthelper.failwith var_info.Calast.v_loc
						("the global variable has a type incompatible with its initial value: " ^
						reason));
				(Some constant, initializes)
			with Evaluator.List_expression ->
				(None, add_value initializes var_info v)
	in
	let global = { g_def = vd; g_init = init } in

	(* assign the constant found to the lattice, so that all globals can be initialized *)
	(* properly. Later in the transformation process, all assignable will be marked *)
	(* "undetermined". *)
	(match init with
		| None -> vd.v_node.n_latt <- LattTop
		| Some cst -> vd.v_node.n_latt <- LattCst cst);

	(add_binding_var_check env vd.v_name vd, global :: globals, initializes)

(** [mk_globals env parameters values globals] adds global variable declarations for
[parameters] (and their associated [values]) and the global variables [globals]. *)
let mk_globals env globals initializes =
	let (env, globals, initializes) =
		List.fold_left
			(fun (env, vars, initializes) var_info ->
				mk_var_def env vars initializes var_info var_info.Calast.v_value)
		(env, [], initializes) globals
	in

	reset_suffix env;
	(env, List.rev globals, initializes)

(** [map_ports env inputs outputs] converts ports from Cal AST to Cal IR. Checks that they have
distinct names. *)
let map_ports env inputs outputs =
	let (inputs_env, inputs) =
		List.fold_left
			(fun (env, inputs) var_info ->
				if has_binding_var env var_info.Calast.v_name then
					let old_vi = get_binding_var env var_info.Calast.v_name in
					Asthelper.failwith var_info.Calast.v_loc
						(sprintf
							"Duplicated declaration of actor port %s (first declared %s)"
							old_vi.v_name
							(Asthelper.string_of_loc old_vi.v_loc))
				else (
					let vi = Evaluator.ir_of_var_info env var_info in
					(add_binding_var env vi.v_name vi, vi :: inputs)
				)
			)
		(env, []) inputs
	in
	
	(* check port names in ht_o (hash table for output ports) *and* ht_i *)
	(* (hash table for input ports). *)
	let (outputs_env, outputs) =
		List.fold_left
			(fun (outputs_env, outputs) var_info ->
				if has_binding_var inputs_env var_info.Calast.v_name then
					let old_vi = get_binding_var inputs_env var_info.Calast.v_name in
					Asthelper.failwith var_info.Calast.v_loc
						(sprintf
							"Duplicated declaration of actor port %s (first declared %s)"
							old_vi.v_name
							(Asthelper.string_of_loc old_vi.v_loc))
				else if has_binding_var outputs_env var_info.Calast.v_name then
					let old_vi = get_binding_var outputs_env var_info.Calast.v_name in
					Asthelper.failwith var_info.Calast.v_loc
						(sprintf
							"Duplicated declaration of actor port %s (first declared %s)"
							old_vi.v_name
							(Asthelper.string_of_loc old_vi.v_loc))
				else (
					let vi =
						try
							Evaluator.ir_of_var_info outputs_env var_info
						with Evaluator.Not_evaluable reason ->
							Asthelper.failwith var_info.Calast.v_loc
								(sprintf
									"Could not translate the type of actor port %s because: %s"
									var_info.Calast.v_name reason)
					in
					(add_binding_var outputs_env vi.v_name vi, vi :: outputs)
				)
			)
		(env, []) outputs
	in
	(inputs_env, outputs_env, List.rev inputs, List.rev outputs)

let map_funcs env existing_procs funcs =
	List.fold_left
		(fun (global_env, procs) func ->
			let (env, params) = map_params global_env func.Calast.f_params in
			
			(* entry and declare vars. *)
			let entry = mk_node Empty in
			let graph = CFG.create () in
			CFG.add_vertex graph entry;

			let (env, vars, node) =
				Ast2ir_expr.declare_vars env [] func.Calast.f_decls graph entry
			in
			
			(* expr *)
			let ctx = Ast2ir_expr.mk_context () in
			let (env, decls, node, expr) =
				Ast2ir_expr.ir_of_expr env vars graph node ctx func.Calast.f_expr
			in

			(* add a return, and an exit node. *)
			let return = mk_node (Return expr) in
			let e1 = ref false in
			let e2 = ref false in
			let exit = mk_node (Join ([|e1; e2|], [])) in
			CFG.add_edge_e graph (entry, (e1, None), exit);
			CFG.add_edge graph node return;
			CFG.add_edge_e graph (return, (e2, None), exit);
			
			let return =
				try
					Evaluator.ir_of_type env func.Calast.f_loc func.Calast.f_return
				with Evaluator.Not_evaluable reason ->
					Asthelper.failwith func.Calast.f_loc
						("return type is not translatable: " ^ reason)
			in
			let proc =
				mk_proc
					graph decls entry exit false func.Calast.f_loc func.Calast.f_name
					params return
			in
			reset_suffix env;
			(add_binding_proc_check global_env proc, proc :: procs))
	(env, existing_procs) funcs

let map_procs env existing_procs procs =
	List.fold_left
		(fun (global_env, procs) proc ->
			let (env, params) = map_params global_env proc.Calast.p_params in

			(* entry and declare vars. *)
			let entry = mk_node Empty in
			let graph = CFG.create () in
			CFG.add_vertex graph entry;

			let (env, vars, node) =
				Ast2ir_expr.declare_vars env [] proc.Calast.p_decls graph entry
			in

			(* translate statements *)
			let (decls, node) =
				Ast2ir_stmt.ir_of_stmts env vars graph node proc.Calast.p_stmts
			in
			
			let e1 = ref false in
			let e2 = ref false in
			let exit = mk_node (Join ([|e1; e2|], [])) in
			CFG.add_edge_e graph (entry, (e1, None), exit);
			CFG.add_edge_e graph (node, (e2, None), exit);

			let proc =
				mk_proc
					graph decls entry exit false proc.Calast.p_loc proc.Calast.p_name
					params TypeVoid
			in
			reset_suffix env;
			(add_binding_proc_check global_env proc, proc :: procs))
	(env, existing_procs) procs

(** [ir_of_ast options out_base actor] converts the AST of a [Calast.actor] to a
[Calir.actor] in the Intermediate Representation of CAL:
no more recursive blocks and CFG information computed *)
let ir_of_ast options out_base actor =
	let t1 = Sys.time () in
	
	(* first map parameters and globals because types of ports may depend on them. *)
	(* Lists initializations are done at the beginning of initialize actions. *)
	let env = mk_env () in
	let (env, parameters) =
		map_params env actor.Calast.ac_parameters
	in
	
	let (env, vars, initializes) =
		mk_globals env actor.Calast.ac_vars actor.Calast.ac_initializes
	in

	(* set all globals that are assignable to LattTop so that their values will not be *)
	(* propagated anymore. *)
	List.iter
		(fun gvar ->
			let var_def = gvar.g_def in
			if var_def.v_assignable then
				var_def.v_node.n_latt <- LattTop)
		vars;

	(* proceed with ports *)
	let (inputs_map, outputs_map, inputs, outputs) =
		map_ports env actor.Calast.ac_inputs actor.Calast.ac_outputs
	in

	(* generates interface functions, translates functions and procedures *)
	let procs = [] in
	let (env, procs) = map_funcs env procs actor.Calast.ac_funcs in
	let (env, procs) = map_procs env procs actor.Calast.ac_procs in

	(* actions *)
	let cnt = ref 0 in
	let actions =
		Ast2ir_action.map_actions
			cnt inputs_map outputs_map env inputs outputs actor.Calast.ac_actions
	in
	
	(* initialize actions *)
	let initializes =
		Ast2ir_action.map_actions
			cnt inputs_map outputs_map env inputs outputs initializes
	in

	let sched =
		Action_sched.gen_sched_info options actor.Calast.ac_name out_base
			actions actor.Calast.ac_priorities actor.Calast.ac_fsm
	in

	let actor =
		{ac_actions = actions;
		ac_file = actor.Calast.ac_file;
		ac_initializes = initializes;
		ac_inputs = inputs;
		ac_name = actor.Calast.ac_name;
		ac_outputs = outputs;
		ac_parameters = parameters;
		ac_sched = sched;
		ac_procs = List.rev procs;
		ac_vars = vars}
	in
	
	let t2 = Sys.time () in
	time := !time +. t2 -. t1;

	actor
