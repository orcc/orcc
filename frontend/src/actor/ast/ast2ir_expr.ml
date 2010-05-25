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

(** Transforms expressions from Cal AST to Cal IR. *)

open Printf
open Ast2ir_util
open Calir
open IR

let ir_of_uop = function
	| Calast.UOpMinus -> UOpMinus
	| Calast.UOpNbElts -> UOpNbElts
	| Calast.UOpNot -> UOpLNot

let ir_of_bop = function
	| Calast.BOpAnd -> BOpLAnd
	| Calast.BOpBAnd -> BOpBAnd
	| Calast.BOpBOr -> BOpBOr
	| Calast.BOpDiv -> BOpDiv
	| Calast.BOpDivInt -> BOpDivInt
	| Calast.BOpEQ -> BOpEQ
	| Calast.BOpExp -> BOpExp
	| Calast.BOpGE -> BOpGE
	| Calast.BOpGT -> BOpGT
	| Calast.BOpLE -> BOpLE
	| Calast.BOpLT -> BOpLT
	| Calast.BOpMinus -> BOpMinus
	| Calast.BOpMod -> BOpMod
	| Calast.BOpNE -> BOpNE
	| Calast.BOpOr -> BOpLOr
	| Calast.BOpPlus -> BOpPlus
	| Calast.BOpShL -> BOpShL
	| Calast.BOpShR -> BOpShR
	| Calast.BOpTimes -> BOpTimes

type parent =
	| PCall
	| PIf
	| PList
	| POther

type context = parent option * (var_def * expr list) option

let mk_context ?var_def ?indexes () =
	match var_def with
		| None -> (None, None)
		| Some var_def ->
			let indexes =
				match indexes with
					| None -> []
					| Some indexes -> indexes
			in
			(None, Some (var_def, indexes))

let mk_assign graph node var_def expr =
	match expr with
		| ExprVar (_, var_use) when var_use.vu_def == var_def ->
			node
		| _ ->
			let assign = mk_node_loc dummy_loc (AssignVar (ref var_def, expr)) in
			CFG.add_edge graph node assign;
			assign

let mk_store loc graph node var_def indexes expr =
	match expr with
		| ExprVar (_, var_use) when var_use.vu_def == var_def ->
			node
		| _ ->
			let store = mk_node_loc loc (Store (mk_var_use var_def, indexes, expr)) in
			CFG.add_edge graph node store;
			store

let rec add_increments graph node =
	match node.n_kind with
		| While (ExprBOp (_, ExprVar (_, use), BOpLT, _, _), bt, be) ->
			(* if there is an edge from join to while, this node is a while created
			  by ir_of_generators and the following steps are taken:
			    - remove the edge
					- get last node of while
					- add an "increment loop variable" instruction after it
					- add an edge from new last node to the while *)
			if CFG.mem_edge graph bt node then (
				let edge = CFG.find_edge graph bt node in
				(* remove edge from join to while *)
				CFG.remove_edge_e graph edge;
				let last_node = add_increments graph bt in
				
				(* add increment to last node *)
				let expr =
					ExprBOp (dummy_loc,
						ExprVar (dummy_loc, mk_var_use use.vu_def),
						BOpPlus, ExprInt (dummy_loc, 1), TypeUnknown)
				in
				let assign = mk_node (AssignVar (ref use.vu_def, expr)) in
				CFG.add_edge graph last_node assign;
				
				(* add edge from last node to while with correct label. *)
				CFG.add_edge_e graph (CFG.E.create assign (CFG.E.label edge) node)
			);
			be
		| _ ->
			match CFG.succ graph node with
				| [] -> node
				| h :: _ -> add_increments graph h

exception Not_builtin

(** [ir_of_expr env vars graph node context expr] translates a functional CAL expression [expr]
into a (possibly empty) list of imperative statements, and returns a [Calir.IR.expr]
expression as the result. *)
let rec ir_of_expr env vars graph node ((_, target) as context) expr =
	match expr with
	| Calast.ExprBool (loc, bool) ->
		let expr = ExprBool (loc, bool) in
		(env, vars, node, expr)
	| Calast.ExprFloat (loc, float) ->
		let expr = ExprFloat (loc, float) in
		(env, vars, node, expr)
	| Calast.ExprInt (loc, int) ->
		let expr = ExprInt (loc, int) in
		(env, vars, node, expr)
	| Calast.ExprStr (loc, str) ->
		let expr = ExprStr (loc, str) in
		(env, vars, node, expr)

	| Calast.ExprBOp (loc, e1, bop, e2) ->
		let (env, vars, node, e1) = ir_of_expr env vars graph node (Some POther, target) e1 in
		let (env, vars, node, e2) = ir_of_expr env vars graph node (Some POther, target) e2 in
		let bop = ir_of_bop bop in
		let expr = ExprBOp (loc, e1, bop, e2, TypeUnknown) in
		let typ = Typing.type_of_expr expr in
		let expr = ExprBOp (loc, e1, bop, e2, typ) in
		(env, vars, node, expr)

	| Calast.ExprVar (loc, var_ref) ->
		let var_def = ir_of_var_ref env loc var_ref in
		(match (var_def.v_global, var_def.v_type) with
		| (false, _) | (true, TypeList _) ->
			let expr = ExprVar (loc, mk_var_use var_def) in
			(env, vars, node, expr)
		| _ ->
			(* creates a temp var with the same type as the global *)
			let (env, vars, tgt) = mk_tmp env vars var_def.v_type in
			let load = mk_node (Load (ref tgt, mk_var_use var_def, [])) in
			let expr = ExprVar (loc, mk_var_use tgt) in
			CFG.add_edge graph node load;
			(env, vars, load, expr))

	| Calast.ExprIdx (loc, (var_loc, var_ref), indexes) ->
		ir_of_idx env vars graph node context loc var_loc var_ref indexes

	| Calast.ExprCall (loc, name, parameters) ->
		ir_of_call env vars graph node context loc name parameters

	| Calast.ExprIf (loc, cond, et, ee) ->
		ir_of_if env vars graph node context loc cond et ee
		
	| Calast.ExprUOp (loc, uop, e) ->
		let (env, vars, node, e) = ir_of_expr env vars graph node (Some POther, target) e in
		let uop = ir_of_uop uop in
		let expr = ExprUOp (loc, uop, e, TypeUnknown) in
		let typ = Typing.type_of_expr expr in
		let expr = ExprUOp (loc, uop, e, typ) in
		(env, vars, node, expr)

	| Calast.ExprList (loc, expr_list, generators) ->
		ir_of_list env vars graph node context loc expr_list generators

and ir_of_expr_list env vars graph node context expr_list =
	let (env, vars, node, expr_list) =
		List.fold_left
			(fun (env, vars, node, expr_list) expr ->
				let (env, vars, node, expr) = ir_of_expr env vars graph node context expr in
				(env, vars, node, expr :: expr_list))
		(env, vars, node, []) expr_list
	in
	(env, vars, node, List.rev expr_list)

(** [ir_of_call env vars graph node target loc name parameters] translates a
[Calast.ExprCall] to a statement [Calir.IR.Call] whose result is
placed into a temporary variable. [ir_of_call] returns a [Calir.IR.ExprVar]
with a reference to this variable. *)
and ir_of_call env vars graph node (parent, target) loc name parameters =
	if name = "bitnot" then
		match parameters with
			| [e] ->
				let (env, vars, node, e) =
					ir_of_expr env vars graph node (Some POther, target) e
				in
				(env, vars, node, ExprUOp(loc, UOpBNot, e, TypeInt 32))
			| _ ->
				Asthelper.failwith loc
					"The builtin function \"bitnot\" takes one parameter"
	else
		try
			let bop =
			match name with
				| "bitand" -> BOpBAnd
				| "bitor" -> BOpBOr
				| "bitxor" -> BOpBXor
				| "lshift" -> BOpShL
				| "rshift" -> BOpShR
				| _ -> raise Not_builtin
			in
			match parameters with
				| [e1; e2] ->
					let (env, vars, node, e1) = ir_of_expr env vars graph node (Some POther, target) e1 in
					let (env, vars, node, e2) = ir_of_expr env vars graph node (Some POther, target) e2 in
					(env, vars, node, ExprBOp(loc, e1, bop, e2, TypeInt 32))
				| _ ->
					Asthelper.failwith loc
						(sprintf "The builtin function \"%s\" takes two parameters" name)
		with Not_builtin ->
			(* not a built-in function *)
			let proc =
				try
					get_binding_proc env name
				with Not_found -> 
					Asthelper.failwith loc
						(sprintf "reference to undefined function/procedure \"%s\"!" name)
			in
			let (env, vars, node, parameters) =
				ir_of_expr_list env vars graph node (Some POther, target) parameters
			in
			
			(* creates a temp var with the same type as the procedure's return type *)
			let (env, vars, target) =
				match (parent, target) with
					| (None, Some (target, [])) -> (env, vars, target)
					| _ -> mk_tmp env vars proc.p_return
			in
			
			let call = mk_node (Call (Some (ref target), proc, parameters)) in
			CFG.add_edge graph node call;
			(env, vars, call, ExprVar (loc, mk_var_use target))

(** translates expressions from A[x][y] to a store(tmp, A[x][y]).
A[] is not supported. *)
and ir_of_idx env vars graph node (_, target) loc var_loc var_ref indexes = 
	let size = List.length indexes in
	if size = 0 then
		Asthelper.failwith loc
			"empty index list is not supported.";
		
	let var_def = ir_of_var_ref env var_loc var_ref in
	let (env, vars, node, indexes) =
		ir_of_expr_list env vars graph node (Some POther, target) indexes
	in
	
	let typ =
		try
			Typing.type_of_elt var_def.v_type size
		with Typing.Type_error s ->
			Asthelper.failwith loc
				(sprintf "variable \"%s\" cannot be indexed: %s" var_ref s)
	in
	
	(* creates a temp var with the same type as the array *)
	let (env, vars, tgt) = mk_tmp env vars typ in
	let load = mk_node (Load (ref tgt, mk_var_use var_def, indexes)) in
	let expr = ExprVar (loc, mk_var_use tgt) in
	CFG.add_edge graph node load;
	(env, vars, load, expr)

(** [ir_of_if env vars graph node context loc cond et ee] *)
and ir_of_if env vars graph node (parent, target) loc cond et ee =
	let (env, vars, node, cond) = ir_of_expr env vars graph node (parent, target) cond in
	let (bt, be, e1, e2, join) = mk_header_if () in

	(* creates the node *)
	let kind = If (cond, bt, be, join) in
	let if_node = mk_node_loc loc kind in
	CFG.add_edge graph node if_node;
	
	mk_links_if_start graph if_node bt be;

	let (env, vars, parent, target) =
		match (parent, target) with
			| (None, Some (target, []))
			| (Some PIf, Some (target, [])) -> (env, vars, Some PIf, target)
			| _ ->
				let (env, vars, target) = mk_tmp env vars TypeUnknown in
				(env, vars, Some PIf, target)
	in

	(* translates the branches *)
	let (env, vars, bt_last, et) =
		ir_of_expr env vars graph bt (parent, Some (target, [])) et
	in
	
	let (env, vars, be_last, ee) =
		ir_of_expr env vars graph be (parent, Some (target, [])) ee
	in
	
	if target.v_type = TypeUnknown then
		target.v_type <- Typing.lub (Typing.type_of_expr et) (Typing.type_of_expr ee);
	
	(* assignment in "then" *)
	let bt_last = mk_assign graph bt_last target et in
	let be_last = mk_assign graph be_last target ee in

	(* returns a reference to "tmp" *)
	mk_links_if_end graph bt_last e1 be_last e2 join;
	(env, vars, join, ExprVar (loc, mk_var_use target))

(** ir_of_list: translation of lists.
checks size of list.
creates new target if needed.
call function specific to list type: w/ or w/o generators. 
*)
and ir_of_list env vars graph node (parent, target) loc expr_list generators =
	let size = List.length expr_list in
	if size = 0 then
		Asthelper.failwith loc
			"empty lists are not supported yet.";

	let (env, vars, parent, target, indexes) =
		match (parent, target) with
			| (None, Some (target, indexes))
			| (Some PIf, Some (target, indexes))
			| (Some PList, Some (target, indexes)) -> (env, vars, Some PList, target, indexes)
			| _ ->
				let (env, vars, target) = mk_tmp env vars TypeUnknown in
				(env, vars, Some PList, target, [])
	in

	let (env, vars, node, typ, length) =
		match generators with
			| [] -> ir_of_list_simple env vars graph node (parent, (target, indexes)) loc expr_list
			| _ -> ir_of_list_gen env vars graph node (parent, (target, indexes)) loc expr_list generators
	in

	(match target.v_type with
		| TypeUnknown -> target.v_type <- TypeList (typ, length)
		| TypeList _ -> ()
		| _ ->
			Asthelper.failwith loc
				"type error: variable should have type List");

	(env, vars, node, ExprVar (dummy_loc, mk_var_use target))

(** ir_of_list: translation of "simple" lists that take the form of an enumeration of values: [5, 2, 4, 3].
This enumeration gets transformed to a sequence of Stores:
tmp0[0] := 5;
tmp0[1] := 2;
tmp0[2] := 4;
tmp0[3] := 3;
Initially, the temporary variable is created with a dummy type, and it is
filled in later. *)
and ir_of_list_simple env vars graph node (parent, (target, indexes)) loc expr_list =
	List.fold_left
		(fun (env, vars, node, typ, i) expr ->
			let indexes = indexes @ [ExprInt(dummy_loc, i)] in
			
			let (env, vars, node, expr) =
				ir_of_expr env vars graph node (parent, Some (target, indexes)) expr
			in
			
			let typ =
				try
					Typing.lub typ (Typing.type_of_expr expr)
				with Typing.Type_error s ->
					Asthelper.failwith loc
						("type error: some types inside a list were not compatible: " ^ s)
			in
			
			let store = mk_store loc graph node target indexes expr in
			(env, vars, store, typ, i + 1))
	(env, vars, node, TypeUnknown, 0) expr_list

(** ir_of_list: translation of lists with generators.
a list such as [ x, y, z : for i in Integers(0, 4), for j in Integers(0, 5) ] becomes:
List(type:int, size=90) _tmp;
i = 0;
while i < 5 do
	j = 0;
	while j < 6 do
		_tmp[(6 * i + j) * 3 + 0] := x;
		_tmp[(6 * i + j) * 3 + 1] := y;
		_tmp[(6 * i + j) * 3 + 2] := z;
		j := j + 1;
	end
	i := i + 1;
end

*)
and ir_of_list_gen env vars graph node context loc expr_list generators =
	let (env, vars, inner_bt, outer_while, index, gen_length) =
		ir_of_generators graph env vars node loc generators
	in
	
	let (index, outer_while) =
		match (index, outer_while) with
			| (Some index, Some node) -> (index, node)
			| _ -> failwith "never happens" (* because generators cannot be empty *)
	in
	
	let (env, vars, _, typ, length) =
		ir_of_inner_expr graph env vars inner_bt loc context index expr_list
	in
	
	ignore (add_increments graph outer_while);
	
	let last_node =
		match outer_while.n_kind with
			| While (_, _, be) -> be
			| _ -> failwith "never happens" (* because last_node is a while *)
	in

	(env, vars, last_node, typ, gen_length * length)

(** [ir_of_generators graph env vars node loc generators] translates the loop generators
to loops.
The function returns a sextuple (env, vars, node, outer_while, index, length).
outer_while is an empty node which is the target of the "else" branch of the outer while.
index is a Calir.IR.expr that contains the index to access the resulting array.
length is the length the resulting would have to be according to the generators.
To obtain the total size, length will have to be multiplied by the number of expressions.
*)
and ir_of_generators graph env vars node loc generators =
	List.fold_left
		(fun (env, vars, node, outer_while, index, gen_length) (vi, expr) ->
			let (env, vars, node, cond, b1, b2) =
				ir_of_integers_expr env vars graph node vi expr
			in
			let length = b2 - b1 + 1 in
			
			let e1 = ref false in
			let e2 = ref false in
			let bt = mk_node (Join ([|e1; e2|], [])) in
			
			let loop_var = Ast2ir_util.get_binding_var env vi.Calast.v_name in
			let expr_var = ExprVar (loop_var.v_loc, mk_var_use loop_var) in
			let expr_var =
				if b1 < 0 then
					ExprBOp (dummy_loc, expr_var, BOpPlus, ExprInt (dummy_loc, -b1), TypeUnknown)
				else if b1 > 0 then
					ExprBOp (dummy_loc, expr_var, BOpMinus, ExprInt (dummy_loc, b1), TypeUnknown)
				else
					expr_var
			in
			let index =
				match index with
					| None -> expr_var
					| Some index ->
						let index =
							ExprBOp (dummy_loc, index, BOpTimes, ExprInt (dummy_loc, length), TypeUnknown)
						in
						ExprBOp (dummy_loc, index, BOpPlus,	expr_var, TypeUnknown)
			in
			
			let (while_node, _) = mk_while graph node loc cond bt bt e1 e2 in
			
			let outer_while =
				match outer_while with
					| None -> Some while_node
					| _ -> outer_while
			in
			
			(env, vars, bt, outer_while, Some index, gen_length * length))
	(env, vars, node, None, None, 1) generators

(** [ir_of_inner_expr graph env vars node loc (parent, target) index expr_list]
translates the expressions in expr_list to Calir.IR.expr expressions.
index is the index that should be used when storing in target.
The function returns a sextuple (env, vars, node, typ).
typ is the type with the least-upper bound of the type of all elements.
*)
and ir_of_inner_expr graph env vars node loc (parent, (target, indexes)) index expr_list =
	let multiplier = List.length expr_list in
	List.fold_left
		(fun (env, vars, node, typ, i) expr ->
			(* when there are several expressions, multiply the index *)
			let index =
				if multiplier = 1 then
					index
				else
					let index =
						ExprBOp (loc, index, BOpTimes, ExprInt (dummy_loc, multiplier), TypeUnknown)
					in
					ExprBOp (loc, index, BOpPlus, ExprInt (dummy_loc, i), TypeUnknown)
			in
			
			let indexes = indexes @ [index] in
			
			let (env, vars, node, expr) =
				ir_of_expr env vars graph node (parent, Some (target, indexes)) expr
			in
			
			let typ =
				try
					Typing.lub typ (Typing.type_of_expr expr)
				with Typing.Type_error s ->
					Asthelper.failwith loc
						("type error: some types inside a list were not compatible: " ^ s)
			in
			
			let store = mk_store loc graph node target indexes expr in
			(env, vars, store, typ, i + 1))
	(env, vars, node, TypeUnknown, 0) expr_list

(** [declare_vars env vars var_info_list] declare variables 
from the var_info_list that occur in a [Calast.StmtBlock] (an inner block)
in the environment [env] and in the variables list [vars]. Each inner variable
is assigned a suffix if it would collide with a variable from a different scope
otherwise. *)
and declare_vars env vars var_info_list graph node =
	List.fold_left
		(fun (env, vars, node) vi_ast ->
			(* we check the binding. *)
			let vi = Evaluator.ir_of_var_info env vi_ast in
			let name = vi.v_name in
			if has_binding_var env name then
				let old_vi = get_binding_var env name in
				Asthelper.failwith vi.v_loc
					(sprintf "variable \"%s\" is already defined here: %s"
						vi.v_name (Asthelper.string_of_loc old_vi.v_loc))
			else (
				(* the variable is not in the environment, retrieve a suffix. *)
				vi.v_suffix <- get_suffix env name
			);

			(* add a binding. *)
			let vars = vi :: vars in
			let env = add_binding_var env name vi in
			
			(* adds an initialization instruction. We don't check whether vi is *)
			(* v_assignable or not at this point because it is not necessary at this point. *)
			(* Later on, ir_of_instr will check that all assignments are legal. *)
			let (env, vars, node) =
				match vi_ast.Calast.v_value with
				| None -> (env, vars, node)
				| Some expr ->
					let loc = vi.v_loc in
					let var_def = ir_of_var_ref env loc name in
					let ctx = mk_context ~var_def () in
					let (env, vars, node, expr) = ir_of_expr env vars graph node ctx expr in
					let node = mk_assign graph node var_def expr in
					(env, vars, node)
			in
			
			(env, vars, node))
		(env, vars, node) var_info_list

(** [ir_of_integers_expr env vars graph node var expr] translates the 
for [var] in [expr].
[expr] is expected to be Integers(x, y) where x and y are expressions
that evaluate to constant integers.
The loop initialization is
  var := x;
and the loop condition becomes
  (var < y + 1)
as in standard C practice. *)
and ir_of_integers_expr env vars graph node var expr =
	let (env, vars, node) = declare_vars env vars [var] graph node in
	match expr with
		| Calast.ExprCall (loc, "Integers", [b1; b2]) ->
			let b1 =
				try
					match Evaluator.eval env b1 with
					| CInt i -> i
					| _ -> raise (Evaluator.Not_evaluable "lower bound is not an integer")
				with Evaluator.Not_evaluable reason ->
					Asthelper.failwith (Asthelper.loc_of_expr b1)
						("cannot translate the generator: " ^ reason)
			in
			let b2 =
				try
					match Evaluator.eval env b2 with
					| CInt i -> i
					| _ -> raise (Evaluator.Not_evaluable "upper bound is not an integer")
				with Evaluator.Not_evaluable reason ->
					Asthelper.failwith (Asthelper.loc_of_expr b2)
						("cannot translate the generator: " ^ reason)
			in
	
			let loop_var = List.hd vars in
			let node = mk_assign graph node loop_var (ExprInt (dummy_loc, b1)) in
			let cond =
				ExprBOp (loc, ExprVar (loc, mk_var_use loop_var), BOpLT,
					ExprInt (dummy_loc, b2 + 1), TypeBool)
			in
			(env, vars, node, cond, b1, b2)
		| _ ->
			Asthelper.failwith (Asthelper.loc_of_expr expr)
				"could not translate foreach condition"

(** [ir_of_foreach_expr env vars graph node var expr] translates the 
foreach [var] in [expr].
[expr] is expected to be Integers(x, y) where x and y are expressions
that can be translated to IR, but need not be constant.
The loop initialization is
  var := x;
and the loop condition becomes
  (var < y + 1)
as in standard C practice. *)
let ir_of_foreach_expr env vars graph node var expr =
	let (env, vars, node) = declare_vars env vars [var] graph node in
	match expr with
		| Calast.ExprCall (loc, "Integers", [b1; b2]) ->
			let loop_var = List.hd vars in
			let ctx = mk_context ~var_def:loop_var () in
			let (env, vars, node, b1) = ir_of_expr env vars graph node ctx b1 in
			let assign = mk_assign graph node loop_var b1 in
			
			let ctx = mk_context () in
			let (env, vars, node, b2) = ir_of_expr env vars graph assign ctx b2 in
			let vu =
				{ vu_def = loop_var; vu_node = dummy_node }
			in
			let ub = ExprBOp (loc, b2, BOpPlus, ExprInt (loc, 1), TypeUnknown) in
			let cond =
				ExprBOp (loc, ExprVar (loc, vu), BOpLT, ub, TypeBool)
			in
			(env, vars, node, cond)
		| _ ->
			Asthelper.failwith (Asthelper.loc_of_expr expr)
				"could not translate foreach condition"
