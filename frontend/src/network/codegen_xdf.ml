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
open Xdfast
open Options

module SH = Asthelper.SH

(** [copy_subgraph graph network] copies all instances and edges between them in 
[graph]. When copied, the instances are renamed if necessary. *)
let copy_subgraph ht_names graph network =
	let subgraph = network.Ast.n_graph in

	let rename instance =
		(try
			let suffix =
				ref (match SH.find ht_names instance.Ast.i_name with
					| None -> 1
					| Some i -> i + 1)
			in

			if !suffix >= 100 then
				failwith
					"Whoa, this flattened graph contains more than 100 instances with the same name. \
					If you are sure there is no other way for you to do what you want with less, \
					please contact us and we will see what we can do.";

			let name = instance.Ast.i_name in
			instance.Ast.i_name <- sprintf "%s_%02i" name !suffix;
			while G.mem_vertex graph (Ast.Instance instance) do
				incr suffix;
				instance.Ast.i_name <- sprintf "%s_%02i" name !suffix;
			done;
			SH.replace ht_names instance.Ast.i_name (Some !suffix)
		with Not_found -> SH.add ht_names instance.Ast.i_name None);
		G.add_vertex graph (Ast.Instance instance)
	in

	G.iter_vertex
		(fun vertex ->
			match vertex with
				| Ast.Instance instance ->
					(match instance.Ast.i_contents with
						| Ast.Actor _ -> rename instance
						| Ast.Network _ -> ())
				| _ -> ())
	subgraph;

	G.iter_edges_e
		(fun ((src, _, dst) as edge) ->
			match (src, dst) with
				| (Ast.Instance _, Ast.Instance _) -> G.add_edge_e graph edge
				| _ -> ())
	subgraph

let get_size src p_src src_size dst p_dst dst_size =
	match (src_size, dst_size) with
		| (None, None) -> None
		| (Some size, None)
		| (None, Some size) -> Some size
		| (Some size1, Some size2) ->
			if size1 = size2 then
				Some size1
			else
				failwith
					(sprintf "connection from %s.%s to %s.%s: incompatible FIFO sizes"
						src p_src dst p_dst)

let name_of_vertex = function
	| Ast.Input port
	| Ast.Output port -> port.po_name
	| Ast.Instance instance -> instance.Ast.i_name

let name_of_port = function
	| None -> ""
	| Some port -> port.po_name

(** [link_preds graph network instance] links each predecessor of [instance]
to the successors of the target (input) port in the subgraph. *)
let link_preds graph network instance =
	let subgraph = network.Ast.n_graph in
	let preds = G.pred_e graph (Ast.Instance instance) in

	List.iter
		(fun ((src, (p_src, p_dst, src_size), _) as edge) ->
			let port =
				match p_dst with
					| None -> failwith "expected a port info"
					| Some port -> port
			in
			
			let succ = G.succ_e subgraph (Ast.Input port) in
			List.iter
				(fun (_, (_, p_dst, dst_size), dst) ->
					let size =
						get_size
							(name_of_vertex src) (name_of_port p_src) src_size
							network.Ast.n_name port.po_name dst_size
					in
					
					G.add_edge_e graph (src, (p_src, p_dst, size), dst))
			succ;
			
			G.remove_edge_e graph edge)
	preds

(** [link_succs graph network instance] links each successor of [instance]
to the predecessors of the source (output) port in the subgraph. *)
let link_succs graph network instance =
	let subgraph = network.Ast.n_graph in
	let succs = G.succ_e graph (Ast.Instance instance) in

	List.iter
		(fun ((_, (p_src, p_dst, src_size), dst) as edge) ->
			let port =
				match p_src with
					| None -> failwith "expected a port info"
					| Some port -> port
			in

			let pred = G.pred_e subgraph (Ast.Output port) in
			List.iter
				(fun (src, (p_src, _, dst_size), _) ->
					let size =
						get_size
							network.Ast.n_name port.po_name src_size
							(name_of_vertex dst) (name_of_port p_dst) dst_size
					in
					
					G.add_edge_e graph (src, (p_src, p_dst, size), dst))
			pred;
			
			G.remove_edge_e graph edge)
	succs

(** [flatten options network] flattens the given network.
The algorithm works as follows:
1) iterate over each vertex in the graph
2) if the vertex is a network
  a) flatten it
	b) import its instances and connections in the current graph
	c) links its predecessors and successors
	d) remove it from this graph. *)
let rec flatten network =
	let ht_names = SH.create 77 in
	let graph = network.Ast.n_graph in
	
	(* fill in the hash table with actors at this level. *)
	G.iter_vertex
		(fun vertex ->
			match vertex with
				| Ast.Instance instance ->
					(match instance.Ast.i_contents with
						| Ast.Actor _ -> SH.add ht_names instance.Ast.i_name None
						| Ast.Network _ -> ())
				| _ -> ())
	graph;
	
	G.iter_vertex
		(fun vertex ->
			match vertex with
				| Ast.Instance instance ->
					(match instance.Ast.i_contents with
						| Ast.Network network ->
							flatten network;
							copy_subgraph ht_names graph network;
							link_preds graph network instance;
							link_succs graph network instance;
							G.remove_vertex graph vertex

						| Ast.Actor _ -> ())
				| _ -> ())
	graph

(* output XDF from network. *)

open Ast

type tree =
	| E of Xmlm.tag * tree list
	| D of string

let mk_attribute a_name a_value = (("", a_name), a_value)

let mk_elt name attributes children = E ((("", name), attributes), children)

let mk_literal kind lit_value =
	let attributes =
		[mk_attribute "kind" "Literal"; mk_attribute "literal-kind" kind;
			mk_attribute "value" lit_value]
	in
	mk_elt "Expr" attributes []

let rec mk_expr = function
	| Calast.ExprBool (_, bool) -> mk_literal "Boolean" (string_of_bool bool)
	| Calast.ExprInt (_, int) -> mk_literal "Integer" (string_of_int int)
	| Calast.ExprList (_, exprs, []) -> mk_list exprs
	| Calast.ExprStr (_, str) -> mk_literal "String" str
	| Calast.ExprVar (_, var) ->
		mk_elt "Expr" [mk_attribute "kind" "Variable"; mk_attribute "value" var] []
	
	| _ -> failwith "not implemented"

and mk_list exprs =
	mk_elt
		"Expr" [mk_attribute "kind" "List"]
		(List.map (fun expr -> mk_expr expr) exprs)

let mk_type_name name children =
	mk_elt "Type" [mk_attribute "name" name] children

let mk_entry_size size =
	mk_elt "Entry" [mk_attribute "kind" "Expr"; mk_attribute "name" "size"]
		[mk_expr (Calast.ExprInt (dummy_loc, size))]

let rec mk_type = function
	| Calir.TypeBool -> mk_type_name "bool" []
	| Calir.TypeInt size -> mk_type_name "int" [mk_entry_size size]
	| Calir.TypeList (typ, size) ->
		mk_type_name "List" [mk_entry_type typ; mk_entry_size size]
	| Calir.TypeStr -> mk_type_name "String" []
	| Calir.TypeUint size -> mk_type_name "uint" [mk_entry_size size]
	| Calir.TypeUnknown -> failwith "type unknown not supported"
	| Calir.TypeVoid -> failwith "type void not supported"

and mk_entry_type typ =
	mk_elt "Entry" [mk_attribute "kind" "Type"; mk_attribute "name" "type"]
		[mk_type typ]

let mk_ports kind graph existing =
	G.fold_vertex
		(fun vertex ports ->
			match vertex with
				| Input port
				| Output port ->
					let attributes =
						[mk_attribute "kind" kind; mk_attribute "name" port.po_name]
					in
					mk_elt "Port" attributes [mk_type port.po_type] :: ports
				| Instance _ -> ports)
	graph existing

let mk_parameters parameters existing =
	List.fold_left
		(fun existing (name, expr) ->
			mk_elt "Parameter" [mk_attribute "name" name] [mk_expr expr] :: existing)
	existing parameters

let mk_instances graph existing =
	G.fold_vertex
		(fun vertex existing ->
			match vertex with
				| Ast.Instance instance ->
					let attributes = [mk_attribute "id" instance.i_name] in
					let children =
						mk_parameters instance.i_params
							[mk_elt "Class" [mk_attribute "name" instance.i_class] []]
					in
					mk_elt "Instance" attributes (List.rev children) :: existing
				| _ -> existing)
	graph existing

let mk_conn_children src_port _dst_port size =
	let children =
		[mk_elt "Attribute"
			[mk_attribute "kind" "Type"; mk_attribute "name" "fifoType"]
			[mk_type src_port.po_type]]
	in

	match size with
		| None -> children
		| Some size ->
			let elt =
				mk_elt "Attribute"
					[mk_attribute "kind" "Value"; mk_attribute "name" "bufferSize"]
					[mk_literal "Integer" (string_of_int size)]
			in
			elt :: children

let mk_connections graph existing =
	G.fold_edges_e
		(fun (src, (src_port, dst_port, size), dst) existing ->
			let (src, src_port) =
				match (src, src_port) with
					| (Input vi, None) -> ("", vi)
					| (Instance i, Some vi) -> (i.i_name, vi)
					| _ -> failwith "Inconsistent graph"
			in

			let (dst, dst_port) =
				match (dst, dst_port) with
					| (Output vi, None) -> ("", vi)
					| (Instance i, Some vi) -> (i.i_name, vi)
					| _ -> failwith "Inconsistent graph"
			in

			let attributes =
				[ mk_attribute "src" src; mk_attribute "src-port" src_port.po_name;
					mk_attribute "dst" dst; mk_attribute "dst-port" dst_port.po_name ]
			in
			
			let children = mk_conn_children src_port dst_port size in
			
			mk_elt "Connection" attributes children :: existing)
	graph existing

let mk_tree network =
	let attributes = [mk_attribute "name" network.n_name] in
	let children =
		mk_connections network.n_graph
			(mk_instances network.n_graph
				(mk_ports "Output" network.n_graph
					(mk_ports "Input" network.n_graph [])))
	in
	mk_elt "XDF" attributes (List.rev children)

(** [output_network out_base network] writes an XDF file named
[out_base ^ ".xdf"]. *)
let output_network out_base network =
	let frag = function
		| E (tag, children) -> `El (tag, children)
		| D d -> `Data d
  in

	let oc = open_out (out_base ^ ".xdf") in
	let output = Xmlm.make_output ~indent:(Some 3) (`Channel oc) in
	Xmlm.output_doc_tree frag output (None, mk_tree network);
	close_out oc

(** whether at least an actor has errors. *)
let model_has_errors = ref false

(** [generate_actors options network] calls [Codegen_ir.convert_codegen]
on each actor. *)
let generate_actors options network =
	G.iter_vertex
		(fun vertex ->
			match vertex with
				| Ast.Instance instance ->
					(match instance.Ast.i_contents with
						| Ast.Actor actor ->
							let file = instance.Ast.i_name ^ ".cal" in
							actor.ac_name <- instance.Ast.i_name;
							(try
								Codegen_ir.codegen {options with o_file = file} actor
							with Asthelper.Compilation_error ->
								if options.o_debug then
									Printexc.print_backtrace stdout;
								model_has_errors := true)

						| Ast.Network _ -> failwith "Never happens after flattening")

				(* do nothing with ports *)
				| _ -> ())
	network.Ast.n_graph

(** [start options] parses the XDF file in options.o_file, flattens the network,
and generates a single file. *)
let start options =
	printf "Parsing actors and networks...\n%!";
	let xdf_file = Filename.concat options.o_mp options.o_file in
	let network = Xdf_parser.parse_network options xdf_file in
	printf "Flattening network \"%s\"...\n%!" network.Ast.n_name;
	flatten network;

	output_network (Filename.concat options.o_outdir network.Ast.n_name) network;

	printf "Generating actors...\n%!";
	generate_actors options network
