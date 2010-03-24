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

(** IR of CAL: AST + CFG + SSA + constant information. *)

open Printf

type uop =
	| UOpBNot
	| UOpLNot
	| UOpMinus
	| UOpNbElts

type bop =
	| BOpBAnd
	| BOpBOr
	| BOpBXor
	| BOpDiv
	| BOpDivInt
	| BOpEQ
	| BOpExp
	| BOpGE
	| BOpGT
	| BOpLAnd
	| BOpLE
	| BOpLOr
	| BOpLT
	| BOpMinus
	| BOpMod
	| BOpNE
	| BOpPlus
	| BOpShL
	| BOpShR
	| BOpTimes

type constant =
	| CBool of bool
	| CInt of int
	| CList of constant list
	| CStr of string

type lattice =
	| LattTop
	| LattCst of constant
	| LattBot

type type_def =
	| TypeBool
	| TypeInt of int
  | TypeList of type_def * int
	| TypeStr
	| TypeUint of int
	| TypeUnknown
	| TypeVoid

type label = bool ref * int option

module rec IR :
	sig
		type expr =
			| ExprBOp of Loc.t * expr * bop * expr * type_def
			| ExprBool of Loc.t * bool
			| ExprInt of Loc.t * int
			| ExprStr of Loc.t * string
			| ExprUOp of Loc.t * uop * expr * type_def
			| ExprVar of Loc.t * var_use

		and var_use = {
			mutable vu_def: var_def;
			mutable vu_node: node
		}

		and var_def = {
			mutable v_assignable : bool;
			mutable v_global : bool;
			mutable v_index: int;
			mutable v_node: node;
			mutable v_loc: Loc.t;
			mutable v_name : string;
			mutable v_refs : var_use list;
			mutable v_suffix : int option;
			mutable v_type : type_def
		}

		and kind =
			| AssignPhi of var_def * bool ref array * var_use array * var_def * node
			| AssignVar of var_def ref * expr
			| Call of var_def ref option * proc * expr list
			| Empty
			| If of expr * node * node * node
			| Join of bool ref array * node list
			| Load of var_def ref * var_use * expr list
			| Return of expr
			| Store of var_use * expr list * expr
			| While of expr * node * node

			| HasTokens of var_def ref * string * int
			| Peek of string * int * var_def
			| Read of string * int * var_def
			| Write of string * int * var_def

		and node = {
			mutable n_id: int;
			mutable n_kind: kind;
			mutable n_latt: lattice;
			mutable n_loc: Loc.t
		}
		
		and proc = {
			mutable p_cfg: CFG.t;
			mutable p_decls: var_def list;
			mutable p_entry: node;
			mutable p_exit: node;
			mutable p_external: bool;
			mutable p_loc: Loc.t;
			mutable p_name: string;
			mutable p_params: var_def list;
			mutable p_return: type_def
		}
	end = struct
		type expr =
			| ExprBOp of Loc.t * expr * bop * expr * type_def
			| ExprBool of Loc.t * bool
			| ExprInt of Loc.t * int
			| ExprStr of Loc.t * string
			| ExprUOp of Loc.t * uop * expr * type_def
			| ExprVar of Loc.t * var_use

		and var_use = {
			mutable vu_def: var_def;
			mutable vu_node: node
		}

		and var_def = {
			mutable v_assignable : bool;
			mutable v_global : bool;
			mutable v_index: int;
			mutable v_node: node;
			mutable v_loc: Loc.t;
			mutable v_name : string;
			mutable v_refs : var_use list;
			mutable v_suffix : int option;
			mutable v_type : type_def
		}

		and kind =
			| AssignPhi of var_def * bool ref array * var_use array * var_def * node
			| AssignVar of var_def ref * expr
			| Call of var_def ref option * proc * expr list
			| Empty
			| If of expr * node * node * node
			| Join of bool ref array * node list
			| Load of var_def ref * var_use * expr list
			| Return of expr
			| Store of var_use * expr list * expr
			| While of expr * node * node

			| HasTokens of var_def ref * string * int
			| Peek of string * int * var_def
			| Read of string * int * var_def
			| Write of string * int * var_def

		and node = {
			mutable n_id: int;
			mutable n_kind: kind;
			mutable n_latt: lattice;
			mutable n_loc: Loc.t
		}
		
		and proc = {
			mutable p_cfg: CFG.t;
			mutable p_decls: var_def list;
			mutable p_entry: node;
			mutable p_exit: node;
			mutable p_external: bool;
			mutable p_loc: Loc.t;
			mutable p_name: string;
			mutable p_params: var_def list;
			mutable p_return: type_def
		}
	end

and CFG : Graph.Sig.I with
	type V.t = IR.node and
	type E.label = label and
	type E.t = IR.node * label * IR.node =
	struct
		include Graph.Imperative.Digraph.ConcreteLabeled
			(struct
				type t = IR.node
				let compare (a : t) (b : t) = compare a.IR.n_id b.IR.n_id
				let equal (a : t) (b : t) = (a.IR.n_id = b.IR.n_id)
				let hash (a : t) = Hashtbl.hash a.IR.n_id
			end)

			(* edge *)
			(struct
				type t = label
				let compare (a : t) (b : t) = compare a b
				let default = (ref false, None)
			end)
		
		(* THIS IS NECESSARY because otherwise all edges share the SAME label. *)
		let add_edge graph v1 v2 = add_edge_e graph (v1, (ref false, None), v2)
	end

open IR

(********** dummy stuff **********)
let dummy_loc = {
	Loc.file_name = "";
	Loc.start = {Loc.line = 0; Loc.bol = 0; Loc.off = 0};
	Loc.stop = {Loc.line = 0; Loc.bol = 0; Loc.off = 0}
}

let dummy_node = {
	n_kind = Empty;
	n_id = 0;
	n_latt = LattTop; (* undetermined *)
	n_loc = dummy_loc
}

type tag = string list

type action = {
	mutable a_body : proc;
	mutable a_ip : (var_def * int) list;
	mutable a_op : (var_def * int) list;
	mutable a_tag : tag;
	mutable a_sched : proc;
}

type globvar_def = {
	mutable g_def : var_def;
	mutable g_init : constant option
}

type fsm = {
	mutable f_states : string list;
	mutable f_init : string;
	mutable f_transitions : (string * (action * string) list) list;
}

type schedinfo = {
	mutable si_actions : action list;
	mutable si_fsm : fsm option;
}

type actor = {
	mutable ac_actions : action list;
	ac_file : string;
	mutable ac_initializes : action list;
	mutable ac_inputs : var_def list;
	mutable ac_name : string;
	mutable ac_outputs : var_def list;
	mutable ac_parameters : var_def list;
	mutable ac_procs : proc list;
	mutable ac_sched : schedinfo;
	mutable ac_vars : globvar_def list
}

let id = ref 0

let reset_id () = id := 0

(********** mk something **********)
let mk_node_loc loc kind =
	incr id;
	{ n_kind = kind;
	n_id = !id;
	n_latt = LattTop; (* undetermined *)
	n_loc = loc }

let mk_node kind = mk_node_loc dummy_loc kind

let mk_header_if () =
	let bt = mk_node Empty in
	let be = mk_node Empty in
	let e1 = ref false in
	let e2 = ref false in
	let join = mk_node (Join ([|e1; e2|], [])) in
	(bt, be, e1, e2, join)

let mk_links_if_start graph if_node bt be =
	CFG.add_edge graph if_node bt;
	CFG.add_edge graph if_node be

let mk_links_if_end graph bt_last e1 be_last e2 join =
	CFG.add_edge_e graph (bt_last, (e1, Some 1), join);
	CFG.add_edge_e graph (be_last, (e2, Some 2), join)

let mk_proc graph vars entry exit extern loc name params return =
	{ p_cfg = graph;
		p_decls = vars;
		p_entry = entry;
		p_exit = exit;
		p_external = extern;
		p_loc = loc;
		p_name = name;
		p_params = params;
		p_return = return }

let mk_var_def assignable global loc name ?suffix vtype =
	{ v_assignable = assignable;
		v_global = global;
		v_index = 0;
		v_loc = loc;
		v_name = name;
		v_node = mk_node Empty; (* to hold the lattice *)
		v_refs = [];
		v_suffix = suffix;
		v_type = vtype }

let mk_var_use var_def =
	{ vu_def = var_def;
	vu_node = dummy_node }

let mk_var_use_node node var_def =
	let var_use = {
		vu_def = var_def;
		vu_node = node
	}
	in
	var_def.v_refs <- var_def.v_refs @ [var_use];
	var_use

let mk_while graph node loc expr bt last_child e1 e2 =
	let be = mk_node Empty in
	let while_node = mk_node_loc loc (While (expr, bt, be)) in
	CFG.add_edge_e graph (node, (e1, Some 1), while_node);
	CFG.add_edge_e graph (last_child, (e2, Some 2), while_node);
	CFG.add_edge graph while_node bt;
	CFG.add_edge graph while_node be;
	(while_node, be)

let rec string_of_constant = function
	| CBool b -> string_of_bool b
	| CInt i -> string_of_int i
	| CStr s -> s
	| CList list -> "[" ^ String.concat ", " (List.map (string_of_constant) list) ^ "]"

let string_of_lattice = function
	| LattTop -> "top - undetermined"
	| LattBot -> "bottom - not constant"
	| LattCst cst -> string_of_constant cst

let string_of_var var_def =
	let var_suffix =
		match var_def.v_suffix with
		| None -> var_def.v_name
		| Some i -> var_def.v_name ^ string_of_int i
	in
	if var_def.v_global then
		var_suffix
	else
		var_suffix ^ "_" ^ string_of_int var_def.v_index

let name_of_tag tag = String.concat "_" tag

(********** join nodes **********)
let add_join_node join node =
	match join.n_kind with
		| Join (exe_flags, nodes) -> join.n_kind <- Join (exe_flags, nodes @ [node])
		| _ -> Pervasives.failwith "add_join_node: weird CFG"

let get_join_flags join =
	match join.n_kind with
		| Join (flags, _) -> flags
		| _ -> Pervasives.failwith "get_join_flags: weird CFG"

let get_join_nodes join =
	match join.n_kind with
		| Join (_, nodes) -> nodes
		| _ -> Pervasives.failwith "get_join_nodes: weird CFG"

let set_join_nodes join nodes =
	match join.n_kind with
		| Join (exe_flags, _) -> join.n_kind <- Join (exe_flags, nodes)
		| _ -> Pervasives.failwith "set_join_nodes: weird CFG"

let remove_var_use var_use =
	var_use.vu_def.v_refs <-
		List.filter (fun use -> use != var_use) var_use.vu_def.v_refs

let replace_var_use var_use node var_def =
	(* removes var_use from the the [refs] of the var_def *)
	remove_var_use var_use;
	var_use.vu_def <- var_def;
	var_use.vu_node <- node;
	var_def.v_refs <- var_def.v_refs @ [var_use]

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* walk through the AST *)

module Iterators = struct

	(** applies [f] to every expression node, bottom-up. *)
	let rec map_expr f expr =
		let expr =
			match expr with
			| ExprBool _ -> expr
			| ExprBOp (loc, e1, bop, e2, t) ->
				ExprBOp (loc, map_expr f e1, bop, map_expr f e2, t)
			| ExprInt _ -> expr
			| ExprStr _ -> expr
			| ExprUOp (loc, uop, e, t) ->
				ExprUOp (loc, uop, map_expr f e, t)
			| ExprVar _ -> expr
		in
		f expr

	(** applies [f] to every expression node, bottom-up. *)
	let rec iter_expr f expr =
		(match expr with
		| ExprBool _ -> ()
		| ExprBOp (_loc, e1, _bop, e2, _t) ->
			iter_expr f e1;
			iter_expr f e2
		| ExprInt _ -> ()
		| ExprStr _ -> ()
		| ExprUOp (_loc, _uop, e, _t) -> iter_expr f e
		| ExprVar _ -> ());
		f expr
	
	let rec fold_cfg f graph node join x =
		if node == join then
			x
		else (
			(* we get the successors *before* calling [f] because [f] might delete nodes, *)
			(* and CFG.succ would be invalid. *)
			let succs =
				match node.n_kind with
				| If (_, _, _, if_join) -> [if_join]
				| While (_, _, be) -> [be]
				| _ -> CFG.succ graph node
			in
			let x = f node join x in
			List.fold_left
				(fun x node -> fold_cfg f graph node join x)
				x succs
		)
	
	let rec iter_cfg f graph node join =
		if node == join then
			()
		else (
			(* we get the successors *before* calling [f] because [f] might delete nodes, *)
			(* and CFG.succ would be invalid. *)
			let succs =
				match node.n_kind with
				| If (_, _, _, if_join) -> [if_join]
				| While (_, _, be) -> [be]
				| _ -> CFG.succ graph node
			in
			f node join;
			List.iter
				(fun node -> iter_cfg f graph node join)
				succs
		)
	
	let iter_actor_proc f actor =
		List.iter
		  (fun proc -> f proc)
		actor.ac_procs;

		List.iter
		  (fun action ->
				f action.a_body;
				f action.a_sched)
		actor.ac_actions;

		List.iter
		  (fun action ->
				f action.a_body;
				f action.a_sched)
		actor.ac_initializes

end

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)

let rec remove_use = function
	| ExprBOp (_, e1, _, e2, _) ->
		remove_use e1;
		remove_use e2
	| ExprUOp (_, _, expr, _) -> remove_use expr
	| ExprVar (_, var_use) -> remove_var_use var_use
	| _ -> ()

let rec remove_var_uses graph node =
	match node.n_kind with
		| AssignPhi (_, _, var_uses, _, _) -> Array.iter remove_var_use var_uses
		| AssignVar (_, expr) -> remove_use expr
		| Call (_, _, parameters) -> List.iter remove_use parameters
		| Return expr -> remove_use expr
		| Load (_, _, indexes) -> List.iter remove_use indexes
		| Store (_, indexes, expr) ->
			List.iter remove_use indexes;
			remove_use expr
		| Empty -> ()
		| Join (_, nodes) -> List.iter (remove_var_uses graph) nodes

		| If (expr, bt, be, if_join) ->
			remove_use expr;
			remove_var_uses_join graph if_join bt;
			remove_var_uses_join graph if_join be
		| While (expr, bt, _be) ->
			remove_use expr;
			remove_var_uses_join graph node bt

		| HasTokens _
		| Peek _
		| Read _
		| Write _ -> ()

and remove_var_uses_join graph join node =
	Iterators.iter_cfg
		(fun node _join -> remove_var_uses graph node)
	graph node join

let remove_node graph node =
	remove_var_uses graph node;
	if CFG.mem_vertex graph node then
		let preds = CFG.pred_e graph node in
		let succs = CFG.succ_e graph node in
		List.iter
			(fun pred_e ->
				CFG.remove_edge_e graph pred_e;
				List.iter
					(fun succ_e ->
						CFG.remove_edge_e graph succ_e;
						CFG.add_edge_e graph (CFG.E.src pred_e, CFG.E.label succ_e, CFG.E.dst succ_e))
				succs)
		preds;
		CFG.remove_vertex graph node
	else
		match node.n_kind with
			| AssignPhi (_, _, _, _, join) ->
				(match join.n_kind with
					| Join (flags, nodes) ->
						let nodes = List.filter (fun phi -> phi != node) nodes in
						join.n_kind <- Join (flags, nodes)
					| _ -> failwith "never happens")
			| _ -> ()

let append_node graph existing new_node =
	let succs = CFG.succ_e graph existing in
	List.iter
		(fun succ_e ->
			CFG.remove_edge_e graph succ_e;
			CFG.add_edge graph new_node (CFG.E.dst succ_e))
	succs;
	CFG.add_edge graph existing new_node
