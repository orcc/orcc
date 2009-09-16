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

(** This modules outputs the Intermediate Representation defined in Calir. *)

open Printf
open Calir
open IR
open Options

open Json_type.Build

let file_name = ref ""
let time = ref 0.

(** [mk_loc loc] creates a "location" element. If the location is dummy, then
the element has a single "dummy" attribute whose value is "true". Otherwise,
the element has "file", "startLine", "startColumn", "endLine", and "endColumn"
attributes. *)
let mk_loc loc =
	let nodes =
		if loc == dummy_loc then
			[]
		else (
			if !file_name = "" then (
				file_name := loc.Loc.file_name
			);
			[int loc.Loc.start.Loc.line;
			int (loc.Loc.start.Loc.off - loc.Loc.start.Loc.bol);
			int loc.Loc.stop.Loc.line;
			int (loc.Loc.stop.Loc.off - loc.Loc.start.Loc.bol)]
		)
	in
	array nodes

(** [mk_cst cst] returns a single element that encodes the information of the [cst]
[Calir.constant]. *)
let rec mk_cst cst =
	match cst with
	| CBool b -> bool b
	| CInt i -> int i
	| CList list -> array (List.map (fun cst -> mk_cst cst) list)
	| CStr s -> string s

(** [mk_type typ] returns a single element that can be "boolType", "intType",
"listType", "stringType", "uintType", or "voidType". *)
let rec mk_type = function
	| TypeBool -> string "bool"
	| TypeInt size -> array [string "int"; int size]
  | TypeList (typ, size) ->
		array [string "List"; int size; mk_type typ]
	| TypeStr -> string "String"
	| TypeUint size -> array [string "uint"; int size]
	| TypeUnknown -> failwith "unknown type"
	| TypeVoid -> string "void"

(** [mk_suffix suffix]. *)
let mk_suffix = function
	| None -> null
	| Some suffix -> int suffix

(** [mk_var_ref var_def existing] *)
let mk_var_ref var_def =
	array
		[string var_def.v_name;
		mk_suffix var_def.v_suffix;
		int var_def.v_index]

(** [mk_var_use var_use] calls [mk_var_ref] and adds a "node" attribute. *)
let mk_var_use var_use =
	array [mk_var_ref var_use.vu_def; int var_use.vu_node.n_id]

(** [string_of_bop bop] returns an element name from a binary operator. *)
let string_of_bop = function
	| BOpBAnd -> "bitand"
	| BOpBOr -> "bitor"
	| BOpBXor -> "bitxor"
	| BOpDiv -> "div"
	| BOpDivInt -> "intDiv"
	| BOpEQ -> "equal"
	| BOpExp -> "exp"
	| BOpGE -> "ge"
	| BOpGT -> "gt"
	| BOpLAnd -> "and"
	| BOpLE -> "le"
	| BOpLOr -> "or"
	| BOpLT -> "lt"
	| BOpMinus -> "minus"
	| BOpMod -> "mod"
	| BOpNE -> "ne"
	| BOpPlus -> "plus"
	| BOpShL -> "lshift"
	| BOpShR -> "rshift"
	| BOpTimes -> "mul"

(** [string_of_uop uop] returns an element name from a unary operator. *)
let string_of_uop = function
	| UOpBNot -> "bitnot"
	| UOpLNot -> "not"
	| UOpMinus -> "unaryMinus"
	| UOpNbElts -> "numElements"

(** [mk_expr expr] returns a single element that encodes the given [expr]. The
returned element's name may be "bool", "int", "string", "var" or one of the names
defined by [string_of_bop] and [string_of_uop]. *)
let rec mk_expr expr =
	let (loc, node) =
		match expr with
		| ExprBool (loc, b) -> (loc, bool b)
		| ExprInt (loc, i) -> (loc, int i)
		| ExprStr (loc, s) -> (loc, string s)
		| ExprVar (loc, var_use) -> (loc, array [ string "var"; mk_var_use var_use])
	
		| ExprUOp (loc, uop, expr, t) ->
			(loc,	array [string "1 op";
				array [string (string_of_uop uop); mk_expr expr; mk_type t] ])
		| ExprBOp (loc, e1, bop, e2, t) ->
			(loc, array [string "2 op";
				array [string (string_of_bop bop); mk_expr e1; mk_expr e2; mk_type t] ])
	in
	array [mk_loc loc; node]

let mk_uses ref_list =
	array
		(List.map
			(fun var_use -> int var_use.vu_node.n_id)
			ref_list)

(** [mk_var_def var_def] *)
let mk_var_def var_def =
	let var =
		[string var_def.v_name;
		bool var_def.v_assignable;
		bool var_def.v_global;
		mk_suffix var_def.v_suffix;
		int var_def.v_index;
		int var_def.v_node.n_id]
	in
	array
		[array var;
		mk_loc var_def.v_loc;
		mk_type var_def.v_type;
		mk_uses var_def.v_refs]

let mk_ports ports =
	array (List.map (fun port -> mk_var_def port) ports)

let mk_state_vars globals =
	array
		(List.map
			(fun global ->
				array
					[ mk_var_def global.g_def;
					match global.g_init with
					| None -> null
					| Some constant -> mk_cst constant ])
		globals)

let mk_var_defs vars =
	array (List.map (fun var -> mk_var_def var) vars)

let mk_phis phis =
	List.map
		(fun node ->
			match node.n_kind with
				| AssignPhi (var, _, var_uses, _, _) ->
					array
						[mk_var_ref var;
						array (List.map (mk_var_use) (Array.to_list var_uses))]
				| _ -> failwith "mk_nodes: should never happen")
	phis

(** [mk_nodes graph node join] translates the nodes in the given CFG graph from [node]
to [join] to elements. Every element has an "id" attribute that contains the node's
id. This is used by def-use chains so a variable can know in which node it is used. *)
let rec mk_nodes graph node join =
	let nodes =
		Iterators.fold_cfg
				(fun node _join existing ->
					(* yaml_node is a list because when node.n_kind is Empty, yaml_node *)
					(* equals to an empty list. *)
					let (node_name, yaml_node) =
						match node.n_kind with
							| HasTokens (ref, fifo, num_tokens) ->
								("hasTokens",
								[array [mk_var_ref !ref; string fifo; int num_tokens]])

							| Peek (fifo, num_tokens, var) ->
								("peek",
								[array [string fifo; int num_tokens; mk_var_ref var]])

							| Read (fifo, num_tokens, var) ->
								("read",
								[array [string fifo; int num_tokens; mk_var_ref var]])

							| Write (fifo, num_tokens, var) ->
								("write",
								[array [string fifo; int num_tokens; mk_var_ref var]])

							| AssignPhi _ -> failwith "mk_nodes: should never happen"
		
							| AssignVar (var_ref, expr) ->
								("assign", [array [mk_var_ref !var_ref; mk_expr expr]])
		
							| Call (var_ref_opt, proc, parameters) ->
								let proc_name = string proc.p_name in
								let result =
									match var_ref_opt with
										| None -> null
										| Some var_ref -> mk_var_ref !var_ref
								in
								let parameters =
									array (List.map (fun expr -> mk_expr expr) parameters)
								in
								("call", [array [proc_name; result; parameters]])
		
							| Empty -> ("empty", [])
		
							| Join (_, phis) -> ("join", [array (mk_phis phis)])
		
							| Load (var_ref, var_use, indexes) ->
								let indexes =
									array (List.map (fun expr -> mk_expr expr) indexes)
								in
								("load",
									[array [mk_var_ref !var_ref; mk_var_use var_use; indexes] ])
		
							| Store (var_use, indexes, expr) ->
								let indexes =
									array (List.map (fun expr -> mk_expr expr) indexes)
								in
								("store",
									[array [mk_var_use var_use; indexes; mk_expr expr] ])
		
							| Return expr -> ("return", [mk_expr expr])
		
							| If (expr, bt, be, if_join) ->
								let bt = mk_nodes graph bt if_join in
								let be = mk_nodes graph be if_join in
								("if", [array [mk_expr expr; bt; be]])
		
							| While (expr, bt, _be) ->
								let bt = mk_nodes graph bt node in
								("while", [array [mk_expr expr; bt]])
					in
					let node =
						array
							(string node_name :: int node.n_id :: mk_loc node.n_loc ::
							yaml_node)
					in
					node :: existing)
			graph node join []
	in
	array (List.rev nodes)

(** [mk_proc proc] returns a "procedure" element that defines the given [proc]. *)
let mk_proc proc =
	array
		[ array
				[ string proc.p_name; bool proc.p_external ];
			mk_loc proc.p_loc;
			mk_type proc.p_return;
			mk_var_defs proc.p_params;
			mk_var_defs proc.p_decls;
			mk_nodes proc.p_cfg proc.p_entry proc.p_exit ]

let mk_procs procs =
	array (List.map (fun proc -> mk_proc proc) procs)

let mk_tag tag = array (List.map (fun str -> string str) tag)

let mk_pattern pattern =
	array
		(List.map
			(fun (port, num_tokens) ->
				array [mk_var_ref port; int num_tokens])
		pattern)

let mk_action action =
	try
		array
			[ mk_tag action.a_tag;
				mk_pattern action.a_ip;
				mk_pattern action.a_op;
				mk_proc action.a_sched;
				mk_proc action.a_body ]
	with Failure "unknown type" ->
		Asthelper.failwith action.a_body.p_loc
			"a variable has an unknown type"

let mk_actions actions =
	array (List.map (fun action -> mk_action action) actions)

let mk_transitions transitions =
	let mk_actions actions =
		array
			(List.map
				(fun (action, s_to) -> array [mk_tag action.a_tag; string s_to])
			actions)
	in
	
	array
		(List.map
			(fun (s_from, actions) -> array [string s_from; mk_actions actions])
			transitions)

let mk_fsm = function
	| None -> null
	| Some fsm ->
		array
			[ string fsm.f_init;
			array (List.map (fun state -> string state) fsm.f_states);
			mk_transitions fsm.f_transitions ]

let mk_action_sched schedinfo =
	array
		[ array (List.map (fun action -> mk_tag action.a_tag) schedinfo.si_actions);
		mk_fsm schedinfo.si_fsm ]

(** [mk_node actor] creates an abstract JSON representation of the actor in IR form. *)
let mk_node actor =
	let inputs = mk_ports actor.ac_inputs in
	let outputs = mk_ports actor.ac_outputs in
	objekt
		[ ("source file", string !file_name);
			("name", string actor.ac_name);
			("inputs", inputs);
			("outputs", outputs);
			("state variables", mk_state_vars actor.ac_vars);
			("procedures", mk_procs actor.ac_procs);
			("actions", mk_actions actor.ac_actions);
			("initializes", mk_actions actor.ac_initializes);
			("action scheduler", mk_action_sched actor.ac_sched) ]

(** [print out_base actor n] prints an actor in IR form to a pseudo CAL actor.
Useful to check how transformations perform. *)
let print out_base actor n =
	let oc = open_out (out_base ^ ".cal" ^ string_of_int n) in	
	let f = Format.make_formatter (output oc) (fun () -> flush oc) in
	Asthelper.PrettyPrinter.pp_actor f actor;
	close_out oc

(** [transform_actor options out_base actor] transforms actor by applying
SSA transformation, constant propagation, peephole optims *)
let transform_actor options out_base actor =
	if options.o_keep then print out_base actor 1;

	Cal_ssa.compute_ssa actor;
	if options.o_keep then print out_base actor 2;
	if options.o_dot_cfg then Asthelper.print_cfg (out_base ^ "_a") actor;
	
	Constprop.const_prop actor;
	if options.o_keep then print out_base actor 3;
	if options.o_dot_cfg then Asthelper.print_cfg (out_base ^ "_b") actor;
	
	Optims.remove_dead_stores actor;
	if options.o_keep then print out_base actor 4;
	if options.o_dot_cfg then Asthelper.print_cfg (out_base ^ "_c") actor;

	(* resets the node ID counter so IR nodes don't end up with huge IDs *)
	(* no IR nodes should be created in *this* actor's graph after that. *)
	reset_id ()

let cached = ref false

(** [codegen options actor] apply transformations to an actor in IR form,
and prints it in JSON. *)
let codegen options actor =
	let file_base = Filename.chop_suffix options.o_file ".cal" in
	let out_base = Filename.concat options.o_outdir file_base in
	let out_name = out_base ^ ".json" in
	
	(** generates code *)
	let go_for_it () =
		transform_actor options out_base actor;
		let t1 = Sys.time () in

		(* in debug mode, prints pretty JSON *)
		Json_io.save_json ~compact:(not options.o_debug) out_name (mk_node actor);
		file_name := "";
		
		let t2 = Sys.time () in
		time := !time +. (t2 -. t1)
	in
	
	if Sys.file_exists out_name then (
		let in_stats = Unix.stat actor.Calir.ac_file in
		let out_stats = Unix.stat out_name in
		if not options.o_cache ||
			in_stats.Unix.st_mtime > out_stats.Unix.st_mtime then
			go_for_it ()
		else (
			(* tell the user that at least one actor was cached *)
			if not !cached then (
				printf "Note: Some actors were already generated, skipped them.\n";
				cached := true
			)
		)
	) else (
		go_for_it ()
	)
