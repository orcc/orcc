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

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* mapping from AST to IR and checking stuff *)

(** [f loc env decls port list repeat] does stuff that both mk_header
and mk_footer need. *)
let f loc env decls port list repeat =
	(* evaluate repeat clause and check it is an unsigned int *)
	let repeat =
		try
			match Evaluator.eval env repeat with
				| CInt i -> i
				| _ -> raise (Evaluator.Not_evaluable "constant but not an unsigned integer")
		with Evaluator.Not_evaluable reason ->
			Asthelper.failwith (Asthelper.loc_of_expr repeat)
				("the repeat clause is not acceptable: " ^ reason)
	in

	(* total number of tokens and input pattern *)
	let nb_tok = List.length list in
	let total_tok = nb_tok * repeat in
	
	(* the type of tokens *)
	let token_type =
		if repeat = 1 then
			port.v_type
		else
			TypeList (port.v_type, repeat)
	in

	(* the variable where tokens will be stored *)
	let vi_tok =
		mk_var_def true false loc port.v_name (TypeList (port.v_type, total_tok))
	in
	let (env, decls) =
		(add_binding_var_check env port.v_name vi_tok, vi_tok :: decls)
	in
	(env, decls, repeat, nb_tok, total_tok, vi_tok, token_type)

(** [init_loop_var graph env decls node] creates the loop variable and
initializes it. *)
let init_loop_var graph env decls node =
	let vi =
		mk_var_def true false dummy_loc "_i" (TypeInt 32)
	in
	vi.v_suffix <- get_suffix env vi.v_name;

	let assign = mk_node (AssignVar (ref vi, ExprInt (dummy_loc, 0))) in
	CFG.add_edge graph node assign;

	(* add a binding to "i" *)
	(add_binding_var env vi.v_name vi, vi :: decls, assign, vi)

(** [create_loop graph env node body_first body_last vi repeat e1 e2] creates
a while loop. [body_first] and [body_last] are the first and last nodes of the
body respectively. *)
let create_loop graph env node body_first body_last vi repeat e1 e2 =
	(* increment the loop variable. *)
	let last_child =
		mk_node
			(AssignVar (ref vi,
				ExprBOp (dummy_loc,
					ExprVar (dummy_loc, mk_var_use vi),
					BOpPlus, ExprInt (dummy_loc, 1),
					TypeInt 32)))
	in
	CFG.add_edge graph body_last last_child;
	
	(* while expr *)
	let expr =
		ExprBOp (dummy_loc,
			ExprVar (dummy_loc, mk_var_use vi),
			BOpLT, ExprInt (dummy_loc, repeat), TypeBool)
	in
	
	(* create the while loop *)
	let (_, be) =
		mk_while graph node dummy_loc expr body_first last_child e1 e2
	in

	(* forget the binding so that we can declare the same variable again later *)
	(remove_binding_var env vi, be)

(** [add_init_tokens
graph env decls node tokens port_type token_type vi_tok nb_tok repeat] generates
code that will initialize the tokens to values read from the outside world. *)
let add_init_tokens
	graph env decls node tokens _port_type token_type vi_tok nb_tok repeat =
	
	(* create the variables that will hold the token *)
	let (env, decls, tokens) =
		List.fold_left
			(fun (env, decls, tokens) (loc, token) ->
				let vi = mk_var_def true false loc token token_type in
				(add_binding_var_check env token vi, vi :: decls, vi :: tokens))
		(env, decls, []) tokens
	in

	let tokens = List.rev tokens in
	if repeat = 1 then
		(* repeat = 1 => no need to create a while loop *)
		let (node, _) =
			List.fold_left
				(fun (node, i) token ->
					(* create classic assignments *)
					let vu = mk_var_use vi_tok in
					let idx = [ExprInt (dummy_loc, i)] in
					let load = mk_node (Load (ref token, vu, idx)) in
					CFG.add_edge graph node load;
					(load, i + 1))
			(node, 0) tokens
		in
		(env, decls, node)
	else
		let (env, decls, assign, vi) = init_loop_var graph env decls node in
		
		(* while body *)
		let e1 = ref false in
		let e2 = ref false in
		let bt = mk_node (Join ([|e1; e2|], [])) in
		
		(* create assignments *)
		let (env, decls, node, _) =
			List.fold_left
				(fun (env, decls, node, i) token ->
					let indexes = [ExprVar (dummy_loc, mk_var_use vi)] in
					let idx =
						ExprBOp (dummy_loc,
							ExprBOp (dummy_loc,
								ExprInt(dummy_loc, nb_tok),
								BOpTimes,
								ExprVar (dummy_loc, mk_var_use vi),
								TypeInt 32),
							BOpPlus, ExprInt (dummy_loc, i),
							TypeInt 32)
					in

					let typ =
						try
							Typing.type_of_elt vi_tok.v_type 1
						with Typing.Type_error s ->
							Asthelper.failwith vi_tok.v_loc
								(sprintf "variable \"%s\" cannot be indexed: %s" vi_tok.v_name s)
					in

					let (env, decls, tgt) = mk_tmp env decls typ in
					let load = mk_node (Load(ref tgt, mk_var_use vi_tok, [idx])) in
					let store =
						mk_node
							(Store
								(mk_var_use token, indexes, ExprVar (dummy_loc, mk_var_use tgt)))
					in
					CFG.add_edge graph node load;
					CFG.add_edge graph load store;
					(env, decls, store, i + 1))
			(env, decls, bt, 0) tokens
		in
		
		let (env, node) = create_loop graph env assign bt node vi repeat e1 e2 in
		(env, decls, node)

(** [add_assignments loc graph env vars node outputs vi_tok nb_tok repeat] generates
code that writes the output expressions. *)
let add_assignments loc graph env vars node outputs vi_tok nb_tok repeat =
	if repeat = 1 then
		(* repeat = 1 => no need to create a while loop *)
		let (node, _) =
			List.fold_left
				(fun (node, i) output ->
					(* create classic assignments *)
					let store =
						mk_node (Store (mk_var_use vi_tok, [ExprInt (dummy_loc, i)], output))
					in
					CFG.add_edge graph node store;
					(store, i + 1))
			(node, 0) outputs
		in
		(env, vars, node)
	else
		let (env, vars, assign, vi) = init_loop_var graph env vars node in
		
		(* while body *)
		let e1 = ref false in
		let e2 = ref false in
		let bt = mk_node (Join ([|e1; e2|], [])) in
		
		(* create assignments *)
		let (env, vars, node, _) =
			List.fold_left
				(fun (env, vars, node, i) output ->
					let indexes = [ExprVar (dummy_loc, mk_var_use vi)] in
					let idx =
						ExprBOp (dummy_loc,
							ExprBOp (dummy_loc,
								ExprInt(dummy_loc, nb_tok),
								BOpTimes,
								ExprVar (dummy_loc, mk_var_use vi),
								TypeInt 32),
							BOpPlus, ExprInt (dummy_loc, i),
							TypeInt 32)
					in
					
					let var_use =
						match output with
							| ExprVar (_loc, var_use) ->
								(match var_use.vu_def.v_type with
									| TypeList (_, size) when size >= repeat -> var_use
									| _ ->
										Asthelper.failwith loc
											(sprintf
												"Output port %s: repeat clause requires that expression be \
												a list with length >= %i."
												vi_tok.v_name repeat))
							| _ ->
								Asthelper.failwith loc
									(sprintf
										"Output port %s: repeat clause requires that expression be a list."
										vi_tok.v_name)
					in

					let typ =
						try
							Typing.type_of_elt var_use.vu_def.v_type 1
						with Typing.Type_error s ->
							Asthelper.failwith vi_tok.v_loc
								(sprintf "variable \"%s\" cannot be indexed: %s" var_use.vu_def.v_name s)
					in
					
					let (env, vars, tgt) = mk_tmp env vars typ in
					let load = mk_node (Load (ref tgt, var_use, indexes)) in
					let store =
						mk_node (Store (mk_var_use vi_tok, [idx], ExprVar (dummy_loc, mk_var_use tgt)))
					in
					CFG.add_edge graph node load;
					CFG.add_edge graph load store;
					(env, vars, store, i + 1))
			(env, vars, bt, 0) outputs
		in
		
		let (env, node) = create_loop graph env assign bt node vi repeat e1 e2 in
		(env, vars, node)

(** [mk_header loc graph env entry inputs func] creates the action header
that implements the action's input pattern *)
let mk_header loc graph env entry inputs func =
	List.fold_left
		(fun (env, decls, node, ip) (port, tokens, repeat) ->
			let (env, decls, repeat, nb_tok, total_tok, vi_tok, token_type) =
				f loc env decls port tokens repeat
			in

			let ip = (port, total_tok) :: ip in

			(* add a call to peek/read *)
			let get_tok = mk_node (func port.v_name total_tok vi_tok) in
			CFG.add_edge graph node get_tok;

			(* add a local declaration for each token declared *)
			let (env, decls, node) =
				add_init_tokens
					graph env decls get_tok tokens
					port.v_type token_type vi_tok nb_tok repeat
			in
			(env, decls, node, ip))
	(env, [], entry, []) inputs

(** [mk_footer loc graph env vars node outputs] creates the action footer
that implements the action's output expression. *)
let mk_footer loc graph env vars node outputs =
	List.fold_left
		(fun (env, vars, node, op) (port, outputs, repeat) ->
			let (env, vars, repeat, nb_tok, total_tok, vi_tok, _) =
				f loc env vars port outputs repeat
			in

			let op = (port, total_tok) :: op in

			(* converts expression list of outputs *)
			let ctx = Ast2ir_expr.mk_context () in
			let (env, vars, node, outputs) =
				Ast2ir_expr.ir_of_expr_list env vars graph node ctx outputs
			in
			
			(* add assignments for output tokens *)
			let (env, vars, node) =
				add_assignments loc graph env vars node outputs vi_tok nb_tok repeat
			in
			
			(* add a call to write *)
			let put_tok = mk_node (Write (port.v_name, total_tok, vi_tok)) in
			CFG.add_edge graph node put_tok;

			(env, vars, put_tok, op))
	(env, vars, node, []) outputs

(** [mk_sched_test actor_name name env ip op guards]
returns a procedure that implements a
schedulability test for the given action's input pattern and guard. *)
let mk_sched_test loc name env ip inputs guards =
	let entry = mk_node Empty in
	let graph = CFG.create () in
	CFG.add_vertex graph entry;
	
	let (env, vars, res) = mk_tmp env [] TypeBool in

	(* input pattern check *)
	let (env, vars, node, expr) =
		List.fold_left
			(fun (env, vars, node, expr) (port, size) ->
				let (env, vars, var_def) = mk_tmp env vars TypeBool in
				let call = mk_node (HasTokens (ref var_def, port.v_name, size)) in
				CFG.add_edge graph node call;
				
				let expr =
					match expr with
						| None -> ExprVar (dummy_loc, mk_var_use var_def)
						| Some expr ->
							ExprBOp(dummy_loc, expr, BOpLAnd,
								ExprVar (dummy_loc, mk_var_use var_def), TypeBool)
				in
				(env, vars, call, Some expr))
		(env, vars, entry, None) ip
	in
	
	let expr =
		match expr with
			| None -> ExprBool (dummy_loc, true)
			| Some expr -> expr
	in
	
	(* creates the schedulability test *)
	let (bt, be, e1, e2, join) = mk_header_if () in
	let if_node = mk_node (If (expr, bt, be, join)) in
	CFG.add_edge graph node if_node;
	
	mk_links_if_start graph if_node bt be;

	let (env, vars, bt_last) =
		match guards with
			| [] ->
				let node = mk_node (AssignVar (ref res, ExprBool (dummy_loc, true))) in
				CFG.add_edge graph bt node;
				(env, vars, node)
			| h :: t ->
				let (env, vars, node, _) =
					mk_header loc graph env bt inputs (fun p n v -> Peek (p, n, v))
				in
				let expr =
					List.fold_left
						(fun expr guard ->
							Calast.ExprBOp (dummy_loc, expr, Calast.BOpAnd, guard))
					h t
				in
				
				let ctx = Ast2ir_expr.mk_context ~var_def:res () in
				let (env, vars, node, expr) =
					Ast2ir_expr.ir_of_expr env vars graph node ctx expr
				in
				let bt_last = Ast2ir_expr.mk_assign graph node res expr in
				(env, vars, bt_last)
	in
	let be_last = mk_node (AssignVar (ref res, ExprBool (dummy_loc, false))) in

	CFG.add_edge graph be be_last;
	mk_links_if_end graph bt_last e1 be_last e2 join;
	
	(* returns the result *)
	let node = mk_node (Return (ExprVar (dummy_loc, mk_var_use res))) in
	CFG.add_edge graph join node;
	reset_suffix env;
	
	(* end of procedure *)
	let e1 = ref false in
	let e2 = ref false in
	let exit = mk_node (Join ([|e1; e2|], [])) in
	CFG.add_edge_e graph (entry, (e1, None), exit);
	CFG.add_edge_e graph (node, (e2, None), exit);

	mk_proc graph vars entry exit false dummy_loc ("isSchedulable_" ^ name) [] TypeBool

(** [mk_action loc tag env inputs guards vars stmts outputs]. *)
let mk_action cnt loc tag env_init inputs guards vars stmts outputs =
	let entry = mk_node Empty in
	let graph = CFG.create () in
	CFG.add_vertex graph entry;
	
	(* creates the action header that implements the action's input pattern *)
	let (env, decls, node, ip) =
		mk_header loc graph env_init entry inputs (fun p n v -> Read (p, n, v))
	in
	let ip = List.rev ip in

	(* action name. *)
	let name =
		match tag with
			| [] ->
				incr cnt;
				if !cnt >= 100 then
					Asthelper.failwith loc
						"Whoa, this actor contains more than 100 anonymous actions. If you are \
						sure there is no other way for you to do what you want with less, \
						please contact us and we will see what we can do.";
				sprintf "untagged%02i" !cnt
			| _ -> name_of_tag tag
	in

	(* declare vars and translate statements *)
	let (env, vars, node) = Ast2ir_expr.declare_vars env decls vars graph node in
	let (vars, node) = Ast2ir_stmt.ir_of_stmts env vars graph node stmts in
	
	(* writes the output tokens *)
	let (env, decls, node, op) = mk_footer loc graph env vars node outputs in
	let op = List.rev op in

	(* creates the procedure that contains the schedulability test. *)
	reset_suffix env;
	let sched_test = mk_sched_test loc name env_init ip inputs guards in

	let e1 = ref false in
	let e2 = ref false in
	let exit = mk_node (Join ([|e1; e2|], [])) in
	CFG.add_edge_e graph (entry, (e1, None), exit);
	CFG.add_edge_e graph (node, (e2, None), exit);

	let proc =
		mk_proc graph decls entry exit false loc name [] TypeVoid
	in

	(sched_test, ip, op, proc)

(** [map_actions cnt inputs_map outputs_map env ac_inputs ac_outputs actions]
maps actions from Cal AST to Cal IR. Doing so it checks that input patterns
and output expressions are correct (not using anonymous ports when not allowed),
that input tokens have distinct names than actor variables, and that action's
local variables have distinct names than both actor variables and input tokens. *)
let map_actions cnt inputs_map outputs_map env ac_inputs ac_outputs actions =
	
	(* find_port either uses the hash table [ht] to find a named port, or just *)
	(* walks through the list for anonymous ports. The function controls that *)
	(* anonymous and named ports are not used together, and that the ports *)
	(* references actually exist. *)
	let find_port loc tag dir port allow_unnamed map list =
		if port = "" then (
			if not allow_unnamed then (
				Asthelper.failwith loc
					(sprintf "action %s: you cannot use empty %s ports because the number of \
						input patterns does not match the number of %s port declarations."
						(String.concat "." tag) dir dir)
			);
			(* using an anonymous port shortens the list. *)
			match list with
				| [] -> failwith "impossible"
				| h :: t -> (h, t)
		) else
			(* port is <> "" *)
			try
				(* using a named port does NOT shorten the list. *)
				(get_binding_var map port, list)
			with Not_found ->
				Asthelper.failwith loc
					(sprintf "action %s: %s port %s does not exist"
						(String.concat "." tag) dir port)
	in
	
	(* check_port_list checks that if anonymous ports could be used, either *)
	(* only anonymous or only named ports were used. *)
	let check_port_list loc tag dir allow_unnamed list nb_ports =
		if allow_unnamed && list <> [] && (List.length list <> nb_ports) then
			Asthelper.failwith loc
				(sprintf "action %s: you cannot mix anonymous and named %s ports."
					(String.concat "." tag) dir)
	in
	
	(* transform input ports. uses [find_port] to handle references to ports *)
	let map_inputs loc tag inputs =
		(* we allow anonymous ports *)
		let nb_ports = List.length ac_inputs in
		let allow_unnamed = (List.length inputs = nb_ports) in
		let (inputs, ac_inputs) =
			List.fold_left
				(fun (inputs, ac_inputs) (port, tokens, repeat) ->
					(* find the port var_info, and update the ac_inputs list if using an *)
					(* anonymous port. *)
					let (port, ac_inputs) =
						find_port loc tag "input" port allow_unnamed inputs_map ac_inputs
					in
					
					let input = (port, tokens, repeat) in
					(input :: inputs, ac_inputs))
				([], ac_inputs) inputs
			in
			check_port_list loc tag "input" allow_unnamed ac_inputs nb_ports;
			List.rev inputs
	in
	
	(* transform output ports. uses [find_port] to handle references to ports *)
	let map_outputs loc tag outputs =
		(* we allow anonymous ports *)
		let nb_ports = List.length ac_outputs in
		let allow_unnamed = (List.length outputs = nb_ports) in
		let (outputs, ac_outputs) =
			List.fold_left
				(fun (outputs, ac_outputs) (port, exprs, repeat) ->
					let (port, ac_outputs) =
						find_port loc tag "output" port allow_unnamed outputs_map ac_outputs
					in
					
					((port, exprs, repeat) :: outputs, ac_outputs))
				([], ac_outputs) outputs
			in
			check_port_list loc tag "output" allow_unnamed ac_outputs nb_ports;
			List.rev outputs
	in
	
	(* map over actions *)
	List.map
		(fun action ->
			let loc = action.Calast.a_loc in
			let tag = action.Calast.a_tag in
			let inputs = map_inputs loc tag action.Calast.a_inputs in
			let outputs = map_outputs loc tag action.Calast.a_outputs in
			
			let (sched, input_pattern, output_pattern, proc) =
				mk_action cnt loc tag env inputs action.Calast.a_guards
					action.Calast.a_vars action.Calast.a_stmts outputs
			in 

			{ a_body = proc;
				a_ip = input_pattern;
				a_op = output_pattern;
				a_sched = sched;
				a_tag = tag })
	actions
