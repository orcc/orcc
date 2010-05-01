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

(************** environment: map of variable -> variable info **************)
type binding =
	| Procedure of proc
	| Variable of var_def

type env = {
	bindings: binding Asthelper.SM.t;
	suffixes: int Asthelper.SH.t
}

let add_binding env name var =
	{ env with bindings = Asthelper.SM.add name var env.bindings }

let get_binding env var = Asthelper.SM.find var env.bindings
let has_binding env var = Asthelper.SM.mem var env.bindings

let get_binding_proc env var =
	match get_binding env var with
		| Procedure (_ as proc) -> proc
		| Variable _ -> raise Not_found

let get_binding_var env var =
	match get_binding env var with
		| Procedure _ -> raise Not_found
		| Variable (_ as var) -> var 

let has_binding_proc env var =
	try
		ignore (get_binding_proc env var);
		true
	with Not_found -> false

let has_binding_var env var =
	try
		ignore (get_binding_var env var);
		true
	with Not_found -> false

let add_binding_proc env proc =
	add_binding env proc.p_name (Procedure proc)

let add_binding_proc_check env proc =
	if has_binding_proc env proc.p_name then
		let old_proc = get_binding_proc env proc.p_name in
		Asthelper.failwith proc.p_loc
			(sprintf "function/procedure \"%s\" is already defined here: %s"
				proc.p_name (Asthelper.string_of_loc old_proc.p_loc))
	else
		add_binding_proc env proc

let add_binding_var env name var =
	add_binding env name (Variable var)

let add_binding_var_check env var var_def =
	if has_binding_var env var then
		let old_vi = get_binding_var env var in
		Asthelper.failwith var_def.v_loc
			(sprintf "variable \"%s\" is already defined here: %s"
				var (Asthelper.string_of_loc old_vi.v_loc))
	else
		add_binding_var env var var_def

let get_suffix env var =
	let suffix = 
		try
			Asthelper.SH.find env.suffixes var
		with Not_found -> 0
	in
	Asthelper.SH.replace env.suffixes var (suffix + 1);
	Some suffix

let remove_binding_var env var =
	ignore (get_binding_var env var.v_name);
	{env with bindings = Asthelper.SM.remove var.v_name env.bindings}

let reset_suffix env = Asthelper.SH.clear env.suffixes

let mk_external name params return =
	let entry = mk_node Empty in
	let e1 = ref false in
	let exit = mk_node (Join ([|e1|], [])) in
	let graph = CFG.create () in
	CFG.add_edge_e graph (entry, (e1, None), exit);
	mk_proc graph [] entry exit true dummy_loc name params return

let mk_param name typ = mk_var_def false false dummy_loc name typ

let mk_env () =
	List.fold_left
		(fun env (name, params, return) ->
			add_binding_proc env (mk_external name params return))
	{	bindings = Asthelper.SM.empty;
		suffixes = Asthelper.SH.create 50 }
	[
		("println", [mk_param "msg" TypeStr], TypeVoid)
	]

let mk_tmp env vars typ =
	let (vname, suffix) =
		match get_suffix env "_tmp" with
			| None -> failwith "never happens"
			| Some i -> ("_tmp", i)
	in
	let vd =
		mk_var_def true false dummy_loc vname ~suffix typ
	in
	(add_binding_var env vd.v_name vd, vd :: vars, vd)

let ir_of_var_ref env loc var =
	if has_binding_var env var then
		get_binding_var env var
	else
		Asthelper.failwith loc
			(sprintf "reference to undefined variable \"%s\"!" var)

let ir_of_proc_name env loc proc =
	if has_binding_proc env proc then
		get_binding_proc env proc
	else
		Asthelper.failwith loc
			(sprintf "reference to undefined function/procedure \"%s\"!" proc)
