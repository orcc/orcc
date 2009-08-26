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
open Xdfast
open Calir
open IR
open Options
open Xdf_expr_parser

(* this one is located in the actor/ast folder. It allows this module to access *)
(* mk_env () and stuff like that. *)
open Ast2ir_util

module SH = Asthelper.SH

let parse_entries children =
	fold_all "Entry"
		(fun acc loc elt_name attributes children ->
			let name = get_attribute loc elt_name attributes "name" in
			(name, children) :: acc)
	children

let find_size_or_default loc children default =
	try
		parse_expr (List.assoc "size" (parse_entries children))
	with Not_found -> Calast.ExprInt (loc, default)

let rec parse_type children =
	find_elt "Type"
		(fun loc elt_name attributes children _next ->
			let name = get_attribute loc elt_name attributes "name" in
			match name with
			| "bool" -> Calast.TypeBool
			| "int" -> Calast.TypeInt (find_size_or_default loc children 32)
			| "list" ->
				Asthelper.failwith loc
					"The type \"list\" is deprecated. Please use \"List\"."
			| "List" ->
				let entries = parse_entries children in

				(* get a type *)
				let t =
					try
						parse_type (List.assoc "type" entries)
				  with Not_found ->
				    Asthelper.failwith loc
							"RVC-CAL requires that all lists have a type."
				in
				
				(* and a size in number of elements *)
				let size =
					try
						parse_expr (List.assoc "size" entries)
					with Not_found ->
				    Asthelper.failwith loc
							"RVC-CAL requires that all lists have a size."
				in
				Calast.TypeList (t, size)
	    | "string" ->
				Asthelper.failwith loc
					"The type \"string\" is deprecated. Please use \"String\"."
			| "String" -> Calast.TypeStr
			| "uint" -> Calast.TypeUint (find_size_or_default loc children 32)
			| t -> Asthelper.failwith loc ("Unknown type \"" ^ t ^ "\"."))
	children

(** [parse_ports graph ht_inputs ht_outputs children]
returns a [(children, inputs, outputs)] tuple obtained by
matching against all "Port" element, sorting between ports with
kind="Input" and kind="Output". *)
let parse_ports graph ht_inputs ht_outputs children =
	fold_partial "Port" ~until:["Decl"; "Instance"]
		(fun (inputs, outputs) loc elt_name attributes children ->
			let kind = get_attribute loc elt_name attributes "kind" in
			let port_name = get_attribute loc elt_name attributes "name" in
			let port_type =
				try
					parse_type children
				with Failure _ ->
					Asthelper.failwith loc
						(sprintf "%s port %s has no type!" kind port_name)
			in
			
			(* Evaluator.ir_of_type calls Asthelper.failwith *)
			let port_type = Evaluator.ir_of_type (mk_env ()) loc port_type in
			let port = { po_name = port_name; po_type = port_type } in

			if kind = "Input" then (
				SH.replace ht_inputs port.po_name port;
				G.add_vertex graph (Ast.Input port);
				(port :: inputs, outputs)
			) else if kind = "Output" then (
				SH.replace ht_outputs port.po_name port;
				G.add_vertex graph (Ast.Output port);
				(inputs, port :: outputs)
			) else
				Asthelper.failwith loc
					"expected kind to be \"Input\" or \"Output\"")
		children ([], [])

(** [parse_decls children] returns a [(children, parameters, variables)] tuple obtained by
matching against all "Decl" element, sorting between declarations with
kind="Param" and kind="Variable". *)
let parse_decls children =
	fold_partial "Decl" ~until:["Instance"]
		(fun (params, vars) loc elt_name attributes children ->
			let kind = get_attribute loc elt_name attributes "kind" in
			let decl_name = get_attribute loc elt_name attributes "name" in
			let decl_type =
				try
					parse_type children
				with Failure _ ->
					Asthelper.failwith loc
						(sprintf "%s %s has no type!" kind decl_name)
			in

			if kind = "Param" then
				let decl =
					Asthelper.mk_var_info false false loc decl_name decl_type None
				in
				(decl :: params, vars)
			else if kind = "Variable" then
				let expr = parse_expr children in
				let decl =
					Asthelper.mk_var_info false false loc decl_name decl_type (Some expr)
				in
				(params, decl :: vars)
			else
				Asthelper.failwith loc
					"expected kind to be \"Input\" or \"Output\"")
		children ([], [])

let evaluate_vars parameters values vars =
	let aux env var_info value =
		let vd = Evaluator.ir_of_var_info env var_info in
		(try
			match value with
			| None -> vd.v_node.n_latt <- LattTop
			| Some v -> vd.v_node.n_latt <- LattCst (Evaluator.eval env v)
		with Evaluator.Not_evaluable reason ->
			Asthelper.failwith var_info.Calast.v_loc
				("the value supplied for the parameter is not statically evaluable: " ^
				reason));
		
		add_binding_var_check env vd.v_name vd
	in

	let env = mk_env () in
	let env =
		List.fold_left
			(fun env var_info ->
				let value =
					try
						Some (List.assoc var_info.Calast.v_name values)
					with Not_found ->
						Asthelper.failwith var_info.Calast.v_loc
							(sprintf "no value supplied for parameter \"%s\"!" var_info.Calast.v_name)
				in
				aux env var_info value)
		env parameters
	in
	
	List.fold_left
		(fun env var_info -> aux env var_info var_info.Calast.v_value)
	env vars

let find_port_actor port ports =
	let port =
		List.find
			(fun { v_name = name } -> name = port)
			ports
	in
	{ po_name = port.v_name; po_type = port.v_type }

let find_port_network ports port = SH.find ports port
	
let find_port_in loc instance port =
	try
		match instance.Ast.i_contents with
			| Ast.Actor actor -> find_port_actor port actor.ac_inputs
			| Ast.Network network -> find_port_network network.Ast.n_inputs port
	with Not_found ->
		Asthelper.failwith loc
			(sprintf "could not find an input port named \"%s\"" port)

let find_port_out loc instance port =
	try
		match instance.Ast.i_contents with
			| Ast.Actor actor -> find_port_actor port actor.ac_outputs
			| Ast.Network network -> find_port_network network.Ast.n_outputs port
	with Not_found ->
		Asthelper.failwith loc
			(sprintf "could not find an output port named \"%s\"" port)

let rec parse_size env = function
	| [] -> None
	| E (l, e, a, c) :: _ when e = "Attribute" &&
		get_attribute l e a "kind" = "Value" &&
		get_attribute l e a "name" = "bufferSize" ->
			let expr = parse_expr c in
			(try
				match Evaluator.eval env expr with
					| CInt int -> Some int
					| _ ->
						Asthelper.failwith l "a FIFO size must be an integer."
			with Evaluator.Not_evaluable msg ->
				Asthelper.failwith l
					("a FIFO size could not be statically evaluated because: " ^ msg))
	| _ :: rest -> parse_size env rest

let parse_structure graph env ht_inputs ht_outputs ht_instances children =
	iter_all "Connection"
		(fun loc e a c ->
			let src = get_attribute loc e a "src" in
			let src_port = get_attribute loc e a "src-port" in
			let dst = get_attribute loc e a "dst" in
			let dst_port = get_attribute loc e a "dst-port" in

			let (v_from, p_from) =
				if src = "" then
					try
						(Ast.Input (SH.find ht_inputs src_port), None)
					with Not_found ->
						Asthelper.failwith loc (sprintf "\"%s\" is not an input port" src_port)
				else
					try
						let instance = SH.find ht_instances src in
						(Ast.Instance instance, Some (find_port_out loc instance src_port))
					with Not_found ->
						Asthelper.failwith loc (sprintf "\"%s\" is not an instance name" src)
			in

			let (v_to, p_to) =
				if dst = "" then
					try
						(Ast.Output (SH.find ht_outputs dst_port), None)
					with Not_found ->
						Asthelper.failwith loc (sprintf "\"%s\" is not an output port" dst_port)
				else
					try
						let instance = SH.find ht_instances dst in
						(Ast.Instance instance, Some (find_port_in loc instance dst_port))
					with Not_found ->
						Asthelper.failwith loc (sprintf "\"%s\" is not an instance name" dst)
			in
			
			let size = parse_size env c in

			G.add_edge_e graph (v_from, (p_from, p_to, size), v_to))
	children;
	graph

let rec expr_of_constant loc = function
	| CBool bool -> Calast.ExprBool (loc, bool)
	| CInt int -> Calast.ExprInt (loc, int)
	| CList list -> Calast.ExprList (loc, List.map (expr_of_constant loc) list, [])
	| CStr str -> Calast.ExprStr (loc, str)

(** [parse_instances graph ht_instances options env children]
returns a list of all the instances obtained by
matching agains "Instance" elements. *)
let rec parse_instances graph ht_instances options env children =
	let parse_parameters children =
		fold_all "Parameter"
			(fun parameters loc elt_name attributes children ->
				let name = get_attribute loc elt_name attributes "name" in
				let expr =
					try
						expr_of_constant loc (Evaluator.eval env (parse_expr children))
					with Evaluator.Not_evaluable reason ->
						Asthelper.failwith loc
							("the value is not statically evaluable: " ^ reason)
				in
				let param = (name, expr) in
				param :: parameters)
		children
	in

	let (children, instances) =
		fold_partial "Instance" ~until:["Connection"]
			(fun instances loc elt_name attributes children ->
				let name = get_attribute loc elt_name attributes "id" in
				let instance =
					find_elt "Class"
						(fun loc_cls elt_name attributes _children next_siblings ->
							let cls_name = get_attribute loc_cls elt_name attributes "name" in
							let parameters = parse_parameters next_siblings in
							let contents = load_instance options loc cls_name parameters in
							let instance =
								{ Ast.i_class = cls_name;
									Ast.i_contents = contents;
									Ast.i_loc = loc;
									Ast.i_name = name;
									Ast.i_params = parameters }
							in
							
							SH.replace ht_instances instance.Ast.i_name instance;
							G.add_vertex graph (Ast.Instance instance);
							
							instance)
						children
				in
				instance :: instances)
		children []
	in
	(children, List.rev instances)

and load_instance options loc cls_name parameters =
	let out_base = Filename.concat options.o_outdir cls_name in
	let abs_file = Filename.concat options.o_mp cls_name in

	let cal_file = abs_file ^ ".cal" in
	if Sys.file_exists cal_file then
		(* parse actor and convert it to IR. *)
		let ast_actor = Cal_parser_wrapper.parse_actor cal_file in
		let actor =
			Ast2ir.ir_of_ast {options with o_values = parameters} out_base ast_actor
		in
		Ast.Actor actor
	else
		let xdf_file = abs_file ^ ".xdf" in
		if Sys.file_exists xdf_file then
			let network =
				parse_network
					{options with o_file = xdf_file; o_values = parameters}
					xdf_file
			in
			Ast.Network network
		else
			Asthelper.failwith loc
				("Unknown instance: " ^ cls_name)

(** [ast_of_tree root] parses the [root] document [tree] assuming it is XDF, and fails
otherwise. It parses (1) input ports (2) output ports (3) parameters (4) variables
(5) instances (6) connections. Attributes are not supported yet.
The function returns a [Xdfast.Ast.network]. *)
and ast_of_tree options root =
	match root with
		| E (loc, elt_name, attributes, children) ->
			if elt_name = "XDF" then
				let name = get_attribute loc elt_name attributes "name" in
				let ht_inputs = SH.create 13 in
				let ht_outputs = SH.create 13 in
				let ht_instances = SH.create 50 in
				(* initializes the graph *)
				let graph = G.create () in

				let (children, _) = parse_ports graph ht_inputs ht_outputs children in
				let (children, (parameters, vars)) = parse_decls children in
				let env = evaluate_vars parameters options.o_values vars in
				let (children, _) =
					parse_instances graph ht_instances options env children
				in
				let graph = parse_structure graph env ht_inputs ht_outputs ht_instances children in

				{ Ast.n_graph = graph;
					Ast.n_inputs = ht_inputs;
					Ast.n_instances = ht_instances;
					Ast.n_name = name;
					Ast.n_outputs = ht_outputs }
			else
				Asthelper.failwith loc "expected \"XDF\" root element"

		| D (loc, _) -> Asthelper.failwith loc "expected \"XDF\" root element"

(** [parse_network options xdf_file] parses the XML file [xdf_file] as a [tree] and
calls [ast_of_tree] to obtain and return a [Xdfast.Ast.network]. *)
and parse_network options xdf_file =
	ast_of_tree options (get_tree xdf_file)
