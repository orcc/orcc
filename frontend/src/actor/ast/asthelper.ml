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

open Calir
open IR
open Printf

exception Compilation_error

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* utility modules *)

module IH =
  Hashtbl.Make
    (struct
       type t = int
       let equal : int -> int -> bool = ( = )
       let hash key : int = key         
     end)

module IM = Map.Make
  (struct
		type t = int
    let compare = compare
  end)

module IS = Set.Make
  (struct
		type t = int
    let compare = compare
  end)

module SH =
  Hashtbl.Make
    (struct
			 type t = string
       let equal (a : string) (b : string) = (a = b)
       let hash (s : string) = Hashtbl.hash s
     end)
  
module SM = Map.Make
  (struct
		type t = string
    let compare = String.compare
  end)
  
module SS = Set.Make
  (struct
		type t = string
    let compare = String.compare
  end)

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* utility functions *)

(* from camlp4 *)
let string_of_loc x =
  let (a, b) = (x.Loc.start, x.Loc.stop) in
  let res = sprintf "File \"%s\", line %d, characters %d-%d"
                    x.Loc.file_name a.Loc.line (a.Loc.off - a.Loc.bol)
										(b.Loc.off - a.Loc.bol) in
  if x.Loc.start.Loc.line <> x.Loc.stop.Loc.line then
    sprintf "%s (end at line %d, character %d)"
            res x.Loc.stop.Loc.line (b.Loc.off - b.Loc.bol)
  else
		res

let failwith loc msg =
	let str = string_of_loc loc in
	prerr_endline str;
	prerr_endline msg;
	raise Compilation_error

let mk_var_info assignable global loc name vtype value_opt =
	{ Calast.v_assignable = assignable;
		Calast.v_global = global;
		Calast.v_loc = loc;
		Calast.v_name = name;
		Calast.v_type = vtype;
		Calast.v_value = value_opt }

external loc_of_expr : Calast.expr -> Loc.t = "%field0"

(** [head_last list] returns a tuple [(head, last)]
 such that [list = head @ [last]]. *)
let head_last list =
	let rec aux head = function
		| [] -> Pervasives.failwith "head_last: empty list!"
		| h :: t ->
			match t with
				| [] -> (List.rev head, h)
				| _ -> aux (h :: head) t
	in
	aux [] list

(* printing functions *)

module PrettyPrinter = struct

	let prec_of_uop = function
		| UOpMinus -> 13
		| UOpBNot
		| UOpLNot
		| UOpNbElts -> 12
	
	let prec_of_bop = function
		| BOpLOr -> 1
		| BOpLAnd -> 2
		| BOpBOr -> 3
		| BOpBXor -> 4
		| BOpBAnd -> 5

		| BOpEQ
		| BOpNE -> 6
	
		| BOpGE
		| BOpGT
		| BOpLE
		| BOpLT -> 7

		| BOpShL
		| BOpShR -> 8
	
		| BOpMinus
		| BOpPlus -> 9
	
		| BOpDiv
		| BOpDivInt
		| BOpMod
		| BOpTimes -> 10
	
		| BOpExp -> 11

	let pp = Format.fprintf

	let pp_uop f = function
		| UOpBNot -> pp f "~@ "
		| UOpLNot -> pp f "not@ "
		| UOpMinus -> pp f "-"
		| UOpNbElts -> pp f "#"

	let pp_bop f = function
		| BOpBAnd -> pp f "&"
		| BOpBOr -> pp f "|"
		| BOpBXor -> pp f "^"
		| BOpDiv -> pp f "/"
		| BOpDivInt -> pp f "div"
		| BOpEQ -> pp f "="
		| BOpExp -> pp f "^"
		| BOpGE -> pp f ">="
		| BOpGT -> pp f ">"
		| BOpLAnd -> pp f "and"
		| BOpLE -> pp f "<="
		| BOpLOr -> pp f "or"
		| BOpLT -> pp f "<"
		| BOpMinus -> pp f "-"
		| BOpMod -> pp f "mod"
		| BOpNE -> pp f "<>"
		| BOpPlus -> pp f "+"
		| BOpShL -> pp f "<<"
		| BOpShR -> pp f ">>"
		| BOpTimes -> pp f "*"

	let pp_var f var = pp f "%s" (string_of_var var)

	let pp_var_use f var = pp_var f var.vu_def

	(** [pp_commas func f list] opens a <hov> and separates elements of [list] by
	 commas. *)
	let pp_commas func f list =
		match list with
			| [] -> ()
			| _ ->
				let (head, last) = head_last list in
				pp f "@[<hov>";
				List.iter
					(fun elt -> pp f "%a,@ " func elt)
					head;
				pp f "%a@]" func last

	let open_paren f pred_prec prec = if pred_prec > prec then pp f "("

	let close_paren f pred_prec prec = if pred_prec > prec then pp f ")"

	let rec pp_expr ?(prec = 0) f = function
		| ExprBool (_, bool) -> pp f "%B" bool
		| ExprInt (_, int) -> pp f "%i" int
		| ExprStr (_, string) -> pp f "\"%s\"" string
		| ExprVar (_, var) -> pp_var_use f var

		| ExprBOp (_, e1, bop, e2, _) ->
			let cur_prec = prec_of_bop bop in
			pp f "@[<hov>";
			open_paren f prec cur_prec;
			pp f "%a@ %a@ %a" (pp_expr ~prec:cur_prec) e1 pp_bop bop (pp_expr ~prec:cur_prec) e2;
			close_paren f prec cur_prec;
			pp f "@]"
		| ExprUOp (_, uop, e, _) ->
			let cur_prec = prec_of_uop uop in
			pp f "@[<hov>";
			open_paren f prec cur_prec;
			pp f "%a%a" pp_uop uop (pp_expr ~prec:cur_prec) e;
			close_paren f prec cur_prec;
			pp f "@]"

	and pp_type f = function
		| TypeBool -> pp f "bool"
		| TypeInt size -> pp f "@[<h>int(size=%i)@]" size
		| TypeStr -> pp f "String"
		| TypeUint size -> pp f "@[<h>uint(size=%i)@]" size
		| TypeUnknown -> pp f "unknown"
		| TypeVoid -> pp f "void"

	  | TypeList (type_def, length) ->
			pp f "@[<h>List(type:@ %a,@ size=%i)@]" pp_type type_def length

	and pp_var_def f var_def =
		pp f "@[<hov>%a@ %a@ /* %i */@]" pp_type var_def.v_type pp_var var_def
			var_def.v_node.n_id

	let pp_indexes f indexes =
		List.iter
			(fun expr -> pp f "[%a]" (pp_expr ~prec:0) expr)
			indexes

	(** [pp_newlines func f list] separates elements of [list] by
	 newlines. *)
	let pp_newlines func f list =
		match list with
			| [] -> ()
			| _ ->
				let (head, last) = head_last list in
				List.iter
					(fun elt -> pp f "%a@ " func elt)
					head;
				pp f "%a" func last

	let rec pp_node_single graph join f node =
		match node.n_kind with
		| Empty -> ()

		| HasTokens (res, fifo, num_tokens) ->
			pp f "@ @[<hov>";
			pp f "%a@ :=@ " pp_var !res;
			pp f "hasTokens(%s, %i); // %i@]" fifo num_tokens node.n_id
			
		| Peek (fifo, num_tokens, var) ->
			pp f "@ @[<hov>peek(%s, %i, %a); // %i@]" fifo num_tokens pp_var var node.n_id
			
		| Read (fifo, num_tokens, var) ->
			pp f "@ @[<hov>read(%s, %i, %a); // %i@]" fifo num_tokens pp_var var node.n_id

		| Write (fifo, num_tokens, var) ->
			pp f "@ @[<hov>write(%s, %i, %a); // %i@]" fifo num_tokens pp_var var node.n_id

		| AssignPhi (var, _, args, old, _) ->
			let args = Array.to_list args in

			pp f "@ @[<hov>%a@ :=@ phi(%a)@ /@ %a; // %i@]"
				pp_var var
				(pp_commas pp_var_use) args
				pp_var old
				node.n_id

		| AssignVar (var, expr) ->
			pp f "@ @[<hov>%a@ :=@ %a; // %i@]" pp_var !var (pp_expr ~prec:0) expr node.n_id

		| Call (var_ref_opt, proc, parameters) ->
			pp f "@ @[<hov>";
			(match var_ref_opt with
				| None -> ()
				| Some ref -> pp f "%a@ :=@ " pp_var !ref);
			pp f "%s(%a); // %i@]" proc.p_name
				(fun f parameters -> pp_commas pp_expr f parameters) parameters
				node.n_id

		| Load (var_ref, var_use, indexes) ->
			pp f "@ @[<hov>%a@ :=@ load(%a%a); // %i@]"
				pp_var !var_ref pp_var_use var_use pp_indexes indexes node.n_id

		| Return expr ->
			pp f "@ @[<hov>return@ %a; // %i@]" (pp_expr ~prec:0) expr node.n_id

		| Store (var_use, indexes, expr) ->
			pp f "@ @[<hov>store(%a%a,@ %a); // %i@]"
				pp_var_use var_use pp_indexes indexes (pp_expr ~prec:0) expr
				node.n_id

		| If (expr, bt, be, if_join) ->
			pp f "@ @[<v>@[<v2>@[<h>if@ %a@ then@]%a@]@ "
				(pp_expr ~prec:0) expr (pp_node graph if_join) bt;
			pp f "@[<v2>else%a@]@ " (pp_node graph if_join) be;
			pp f "end@]"

		| Join (_, list) -> List.iter (pp_node_single graph join f) list

		| While (expr, bt, _be) ->
			pp f "@ @[<v>@[<v2>@[<hov>while@ %a@ do@]%a@]@ end@]"
				(pp_expr ~prec:0) expr (pp_node graph node) bt

	and pp_node graph join f node =
		Iterators.iter_cfg
			(fun node join -> pp_node_single graph join f node)
			graph node join

	let pp_proc f proc =
		if not proc.p_external then ( 
			pp f "@[<v>@ @[<hov>procedure@ %s(%a)@ @]@ "
				proc.p_name (pp_commas pp_var_def) proc.p_params;
			if proc.p_decls <> [] then (
				pp f "@[<v2>var@ %a@]@ " (pp_commas pp_var_def) proc.p_decls
			);
			pp f "@[<v2>begin%a@]@ end@ @]" (pp_node proc.p_cfg proc.p_exit) proc.p_entry
		)

	let pp_decls f decls =
	  List.iter
	    (fun decl -> pp f "%a;@ " pp_var_def decl.g_def)
	  	decls

	let pp_procs f procs =
		List.iter
			(fun proc -> pp f "%a" pp_proc proc)
			procs

	let pp_action f action =
		pp_proc f action.a_sched;
		
		pp f "@[<v>@ @[<hov>";
		if action.a_tag <> [] then (
			pp f "%s:@ " (String.concat "." action.a_tag)
		);
		pp f "action@ ==>@ @]@ ";
		let proc = action.a_body in
		if proc.p_decls <> [] then (
			pp f "@[<v2>var@ %a@]@ " (pp_commas pp_var_def) proc.p_decls
		);
		pp f "@[<v2>do%a@]@ end@]"
			(pp_node proc.p_cfg proc.p_exit) proc.p_entry

	let pp_actions f actions =
	  List.iter
	    (fun action -> pp f "%a@ " pp_action action)
	  	actions

	let pp_actor f actor =
		pp f "@[<v>@[<v2>@[<hov>actor@ %s()@]@ " actor.ac_name;

		pp f "@[<hov>%a@ ==>@ %a@ :@]"
			(pp_commas pp_var_def) actor.ac_inputs
			(pp_commas pp_var_def) actor.ac_outputs;

		pp f "@ @ %a@ %a@ %a%a@]@ end@]@."
			pp_decls actor.ac_vars
			pp_procs actor.ac_procs
			pp_actions actor.ac_actions
			pp_actions actor.ac_initializes

	let pp_flush f = pp f "@?"

	let string_of_from_pp pp_fun something =
		let b = Buffer.create 16 in
		let f = Format.formatter_of_buffer b in
		pp_fun f something;
		pp_flush f;
		Buffer.contents b

	let string_of_bop bop = string_of_from_pp pp_bop bop

	let string_of_expr expr = string_of_from_pp pp_expr expr

	let string_of_node node =
		let graph = CFG.create () in
		string_of_from_pp (pp_node_single graph dummy_node) node

	let string_of_type typ = string_of_from_pp pp_type typ

	let string_of_uop uop = string_of_from_pp pp_uop uop

	let string_of_var_def var_def = string_of_from_pp pp_var_def var_def

end

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)

module Node = struct
	type t = string * node
	let equal (_, a) (_, b) = (a.n_id = b.n_id)
	let compare (_, a) (_, b) = Pervasives.compare a.n_id b.n_id
	let hash (_, x) = Hashtbl.hash x.n_id
end

module G = Graph.Imperative.Digraph.ConcreteLabeled(Node)
	(struct
		type t = label
		let compare (a : t) (b : t) = compare a b
		let default = (ref false, None)
	end)

let re_newline = Str.regexp "\\\\n"

module Display(Dot : sig val graph : G.t end) = struct
  include G

	open Graph.Graphviz.DotAttributes

	let subgraph_entry = {sg_name = "entry"; sg_attributes = []}
	let subgraph_exit = {sg_name = "exit"; sg_attributes = []}
	let subgraph_others = {sg_name = "others"; sg_attributes = []}
	
  let get_subgraph v =
		if G.pred Dot.graph v = [] then
			Some subgraph_entry
		else if G.succ Dot.graph v = [] then
			Some subgraph_exit
		else
			Some subgraph_others

  let graph_attributes _ = []
  let default_vertex_attributes _ = []
	let default_edge_attributes _ = []
  let edge_attributes edge =
		let (executable, int_opt) = G.E.label edge in
		let label =
			sprintf "(%b, %s)" !executable 
				(match int_opt with None -> "none" | Some i -> string_of_int i)
		in
		[ `Fontname "Courier"; `Fontsize 8; `Label label ]
	
	let string_of_stmt name node =
		let str =
			match node.n_kind with
				| If (expr, _, _, _) ->
					"if " ^ PrettyPrinter.string_of_expr expr
				| While (expr, _, _) ->
					"while " ^ PrettyPrinter.string_of_expr expr
				| _ -> PrettyPrinter.string_of_node node
		in
		sprintf "%s.%i:\n%s" name node.n_id str

	let label (name, node) = string_of_stmt name node
  let vertex_name (_, node) = string_of_int node.n_id
  let vertex_attributes v =
		let label =
			Str.global_replace re_newline "\\l" (String.escaped (label v))
		in
		[ `Fontname "Courier"; `Fontsize 8; `Label label; `Shape `Box ]

end

let rec print_cfg_rec dot_graph name graph node join =
	Iterators.iter_cfg
		(fun node _ ->
			CFG.iter_succ_e
				(fun edge ->
					let succ = CFG.E.dst edge in
					G.add_edge_e dot_graph ((name, node), (CFG.E.label edge), (name, succ)))
			graph node;

			(* recurse on children *)
			match node.n_kind with
				| If (_expr, bt, be, join) ->
					print_cfg_rec dot_graph name graph bt join;
					print_cfg_rec dot_graph name graph be join
				| While (_, bt, _be) ->
					print_cfg_rec dot_graph name graph bt node
				| _ -> ())
	graph node join

let re_dot1 = "subgraph cluster_"
let re_dot2 = " { \\(\\([0-9]+;\\)*\\) };"
let re_entry = Str.regexp (re_dot1 ^ "entry" ^ re_dot2)
let re_others = Str.regexp (re_dot1 ^ "others" ^ re_dot2)
let re_exit = Str.regexp (re_dot1 ^ "exit" ^ re_dot2)

(** [read_whole_text_file file] is pretty self-explanatory. *)
let read_whole_text_file file =
	let ic = open_in file in
	(* this function makes the assumption that the translation from file to memory *)
	(* will never end up in a string that is bigger than the original file. Why? *)
	(* If file is CR/LF -> LF, the string is *smaller* than the file. *)
	(* If file is CR (Mac) or LF (Unix) the string has the same size as the file. *)
	let maxlen = in_channel_length ic in
	let buf = String.create maxlen in
	let nb_read = ref (input ic buf 0 maxlen) in
	let total_len = ref !nb_read in
	while !nb_read > 0 do
		nb_read := input ic buf !total_len (maxlen - !total_len);
		total_len := !total_len + !nb_read;
	done;
	close_in ic;
	String.sub buf 0 !total_len

(* transform the DOT file to make it prettier *)
let remove_clusters_add_rank file =
	let str = read_whole_text_file file in
	let str = Str.global_replace re_entry "{rank=\"min\"; \\1};" str in
	let str = Str.global_replace re_others "{\\1};" str in
	let str = Str.global_replace re_exit "{rank=\"max\"; \\1};" str in
	let oc = open_out file in
	output_string oc str;
	close_out oc

(* used by print_cfg *)
let iter_proc_name f actor =
	List.iter
	  (fun proc -> f proc.p_name proc)
	actor.ac_procs;

	List.iter
	  (fun action ->
			f action.a_body.p_name action.a_body;
			f action.a_sched.p_name action.a_sched)
	actor.ac_actions

let print_cfg file actor =
	iter_proc_name
		(fun name proc ->
			let file_name = sprintf "%s_%s.dot" file name in
			let oc = open_out file_name in
			let dot_graph = G.create () in
			if not proc.p_external then
				print_cfg_rec dot_graph name proc.p_cfg proc.p_entry proc.p_exit;

			let module DotOutput =
				Graph.Graphviz.Dot(Display(struct let graph = dot_graph end))
			in
		  DotOutput.output_graph oc dot_graph;
			close_out oc;

			remove_clusters_add_rank file_name)
	actor
