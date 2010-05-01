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
open Ast2ir_util

exception List_expression

exception Not_evaluable of string

let error_size_not_constant ?reason loc typename =
	let reason =
		match reason with
			| None -> ""
			| Some reason -> ": " ^ reason
	in
	Asthelper.failwith loc
		("the size of this \"" ^ typename ^
		"\" type must be an integer constant" ^ reason)

let rec assert_cst_types_equal loc = function
	| (CBool _, CBool _)
	| (CInt _, CInt _)
	| (CStr _, CStr _) -> ()
	| (CList l1, CList l2) ->
		(match (l1, l2) with
			(* we cannot decide if either list is empty. This is equivalent to *)
			(* a polymorphic type in typed lambda calculus. *)
			| ([], _) | (_, []) -> ()
			| (h1 :: _, h2 :: _) -> assert_cst_types_equal loc (h1, h2))
	| _ ->
		Asthelper.failwith loc
			"all elements must have the same type"

(** [eval e] evaluates the [Calast.expr] [e] and returns its contents as a
[Calir.constant].
@raise NotEvaluable if [e] cannot be statically evaluated. *)
let rec eval env = function
	| Calast.ExprBool (_, bool) -> CBool bool
	| Calast.ExprInt (_, int) -> CInt int
	| Calast.ExprStr (_, str) -> CStr str
	| Calast.ExprVar (loc, var_ref) ->
		let vd = ir_of_var_ref env loc var_ref in
		(match vd.v_node.n_latt with
			| LattCst cst -> cst
			| _ -> raise (Not_evaluable (vd.v_name ^ " is not constant")))

	| Calast.ExprUOp (loc, uop, expr) ->
		(match uop with
			| Calast.UOpMinus ->
				(match eval env expr with
					| CInt int -> CInt (- int)
					| _ ->
						Asthelper.failwith loc
							"cannot negate something that is not an integer")
			| Calast.UOpNbElts ->
				(match eval env expr with
					| CList list -> CInt (List.length list)
					| _ ->
						Asthelper.failwith loc
							"cannot get the number of elements of something that is not a list")
			| Calast.UOpNot ->
				(match eval env expr with
					| CBool bool -> CBool (not bool)
					| _ ->
						Asthelper.failwith loc
							"cannot negate something that is not a boolean"))

	| Calast.ExprIdx (_loc, (loc_var, var_ref), indexes) ->
		let vd = ir_of_var_ref env loc_var var_ref in
		let cst =
			match vd.v_node.n_latt with
			| LattCst cst -> cst
			| _ -> raise (Not_evaluable
				"the target cannot be indexed because it is not constant!")
		in
		List.fold_left
			(fun cst index ->
				let index = eval env index in
				match cst with
					| CList list ->
						(match index with
							| CInt int ->
								(try
									List.nth list int
								with Failure "nth" ->
									raise
										(Not_evaluable
											(sprintf "cannot access element %i on a list of size %i"
												int (List.length list))))
							| _ ->
								raise (Not_evaluable
									"the target cannot be indexed because it is not constant!"))
					| _ ->
						raise (Not_evaluable
							"the target cannot be indexed because it is not constant!"))
			cst indexes
		
	| Calast.ExprBOp (loc, e1, bop, e2) ->
		let cst1 = eval env e1 in
		let cst2 = eval env e2 in
		(match (cst1, bop, cst2) with
			(************************ invalid operations ************************)
			(* boolean operations. *)
			| (CInt _, Calast.BOpAnd, _)
			| (_, Calast.BOpAnd, CInt _)
			| (CStr _, Calast.BOpAnd, _)
			| (_, Calast.BOpAnd, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"and\" must be booleans"
			| (CInt _, Calast.BOpOr, _)
			| (_, Calast.BOpOr, CInt _)
			| (CStr _, Calast.BOpOr, _)
			| (_, Calast.BOpOr, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"or\" must be booleans"

			(* integer operations. *)
			| (CBool _, Calast.BOpDiv, _)
			| (_, Calast.BOpDiv,  CBool _)
			| (CStr _, Calast.BOpDiv, _)
			| (_, Calast.BOpDiv, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"/\" must be integers"
			| (CBool _, Calast.BOpDivInt, _)
			| (_, Calast.BOpDivInt, CBool _)
			| (CStr _, Calast.BOpDivInt, _)
			| (_, Calast.BOpDivInt, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"div\" must be integers"
			| (CBool _, Calast.BOpExp, _)
			| (_, Calast.BOpExp, CBool _)
			| (CStr _, Calast.BOpExp, _)
			| (_, Calast.BOpExp, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"exp\" must be integers"
			| (CBool _, Calast.BOpMinus, _)
			| (_, Calast.BOpMinus, CBool _)
			| (CStr _, Calast.BOpMinus, _)
			| (_, Calast.BOpMinus, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"-\" must be integers"
			| (CBool _, Calast.BOpMod, _)
			| (_, Calast.BOpMod, CBool _)
			| (CStr _, Calast.BOpMod, _)
			| (_, Calast.BOpMod, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"mod\" must be integers"
			| (CBool _, Calast.BOpTimes, _)
			| (_, Calast.BOpTimes, CBool _)
			| (CStr _, Calast.BOpTimes, _)
			| (_, Calast.BOpTimes, CStr _) ->
				Asthelper.failwith loc "type error: operands of \"*\" must be integers"
			
			(* concatenation/plus operator. *)
			| (CBool _, Calast.BOpPlus, CBool _)
			| (CBool _, Calast.BOpPlus, CInt _)
			| (CInt _, Calast.BOpPlus, CBool _) ->
				Asthelper.failwith loc "type error: \"+\" cannot be used with booleans"
			
			(************************ comparisons ************************)
			| (CBool b1, Calast.BOpEQ, CBool b2) -> CBool (b1 = b2)
			| (CBool b1, Calast.BOpGE, CBool b2) -> CBool (b1 >= b2)
			| (CBool b1, Calast.BOpGT, CBool b2) -> CBool (b1 > b2)
			| (CBool b1, Calast.BOpLE, CBool b2) -> CBool (b1 <= b2)
			| (CBool b1, Calast.BOpLT, CBool b2) -> CBool (b1 < b2)
			| (CBool b1, Calast.BOpNE, CBool b2) -> CBool (b1 <> b2)

			| (CInt i1, Calast.BOpEQ, CInt i2) -> CBool (i1 = i2)
			| (CInt i1, Calast.BOpGE, CInt i2) -> CBool (i1 >= i2)
			| (CInt i1, Calast.BOpGT, CInt i2) -> CBool (i1 > i2)
			| (CInt i1, Calast.BOpLE, CInt i2) -> CBool (i1 <= i2)
			| (CInt i1, Calast.BOpLT, CInt i2) -> CBool (i1 < i2)
			| (CInt i1, Calast.BOpNE, CInt i2) -> CBool (i1 <> i2)

			| (CStr s1, Calast.BOpEQ, CStr s2) -> CBool (s1 = s2)
			| (CStr s1, Calast.BOpGE, CStr s2) -> CBool (s1 >= s2)
			| (CStr s1, Calast.BOpGT, CStr s2) -> CBool (s1 > s2)
			| (CStr s1, Calast.BOpLE, CStr s2) -> CBool (s1 <= s2)
			| (CStr s1, Calast.BOpLT, CStr s2) -> CBool (s1 < s2)
			| (CStr s1, Calast.BOpNE, CStr s2) -> CBool (s1 <> s2)
			
			(************************ invalid comparisons ************************)
			| (CBool _, Calast.BOpEQ, _)
			| (CBool _, Calast.BOpGE, _)
			| (CBool _, Calast.BOpGT, _)
			| (CBool _, Calast.BOpLE, _)
			| (CBool _, Calast.BOpLT, _)
			| (CBool _, Calast.BOpNE, _)
				
			| (CInt _, Calast.BOpEQ, _)
			| (CInt _, Calast.BOpGE, _)
			| (CInt _, Calast.BOpGT, _)
			| (CInt _, Calast.BOpLE, _)
			| (CInt _, Calast.BOpLT, _)
			| (CInt _, Calast.BOpNE, _)
				
			| (CStr _, Calast.BOpEQ, _)
			| (CStr _, Calast.BOpGE, _)
			| (CStr _, Calast.BOpGT, _)
			| (CStr _, Calast.BOpLE, _)
			| (CStr _, Calast.BOpLT, _)
			| (CStr _, Calast.BOpNE, _) -> 
				Asthelper.failwith loc
					"type error: only operands of similar types should be compared"
			
			(************************ booleans ************************)
			(* bool: other binary operations. *)
			| (CBool b1, Calast.BOpAnd, CBool b2) -> CBool (b1 && b2)
			| (CBool b1, Calast.BOpOr, CBool b2) -> CBool (b1 || b2)

			(************************ integers ************************)
			| (CInt i1, Calast.BOpDiv, CInt i2) -> CInt (i1 / i2)
			| (CInt i1, Calast.BOpDivInt, CInt i2) -> CInt (i1 / i2)
			| (CInt i1, Calast.BOpExp, CInt i2) ->
				CInt (int_of_float (float i1 ** float i2))
			| (CInt i1, Calast.BOpMinus, CInt i2) -> CInt (i1 - i2)
			| (CInt i1, Calast.BOpMod, CInt i2) -> CInt (i1 mod i2)
			| (CInt i1, Calast.BOpPlus, CInt i2) -> CInt (i1 + i2)
			| (CInt i1, Calast.BOpTimes, CInt i2) -> CInt (i1 * i2)

			(************************ strings ************************)
			| (CStr s1, Calast.BOpPlus, CBool b2) ->
				 (CStr (s1 ^ string_of_bool b2))
			| (CStr s1, Calast.BOpPlus, CInt i2) ->
				 (CStr (s1 ^ string_of_int i2))
			| (CBool b1, Calast.BOpPlus, CStr s2) ->
				 (CStr (string_of_bool b1 ^ s2))
			| (CInt i1, Calast.BOpPlus, CStr s2) ->
				 (CStr (string_of_int i1 ^ s2))
			| (CStr s1, Calast.BOpPlus, CStr s2) -> CStr (s1 ^ s2)

			| _ ->
				Asthelper.failwith loc "TODO eval ExprBOp")
	
	| Calast.ExprCall (loc, name, parameters) ->
		let parameters = List.map (eval env) parameters in
		(match (name, parameters) with
			| ("bitand", [CInt i1; CInt i2]) -> CInt (i1 land i2)
			| ("bitand", _) -> Asthelper.failwith loc "wrong argument list for \"bitand\""

			| ("bitor", [CInt i1; CInt i2]) -> CInt (i1 lor i2)
			| ("bitor", _) -> Asthelper.failwith loc "wrong argument list for \"bitor\""

			| ("bitxor", [CInt i1; CInt i2]) -> CInt (i1 lxor i2)
			| ("bitxor", _) -> Asthelper.failwith loc "wrong argument list for \"bitxor\""
		
			| ("bitnot", [CInt int]) -> CInt (lnot int)
			| ("bitnot", _) -> Asthelper.failwith loc "wrong argument list for \"bitnot\""

			| ("lshift", [CInt i1; CInt i2]) -> CInt (i1 lsl i2)
			| ("lshift", _) -> Asthelper.failwith loc "wrong argument list for \"lshift\""

			| ("rshift", [CInt i1; CInt i2]) -> CInt (i1 lsr i2)
			| ("rshift", _) -> Asthelper.failwith loc "wrong argument list for \"rshift\""

			| _ -> Asthelper.failwith loc "TODO eval ExprCall")

	| Calast.ExprIf (loc, cond, et, ee) ->
		(match eval env cond with
			| CBool true -> eval env et
			| CBool false -> eval env ee
			| _ ->
				Asthelper.failwith loc "type error: condition should evaluate to a boolean")

	| Calast.ExprList (loc, expr_list, generators) ->
		let bounds =
			List.fold_left
				(fun bounds (var_info, expr) ->
					match expr with
						| Calast.ExprCall (_, "Integers", [e1;e2]) ->
							(match (eval env e1, eval env e2) with
								| (CInt a, CInt b) -> (var_info, a, b) :: bounds
								| _ ->
									Asthelper.failwith loc
										"eval: only generators over Integers(<a>, <b>) where \
										a and b are integers are supported")
						| _ ->
							Asthelper.failwith loc
								"eval: only generators over Integers(<a>, <b>) are supported")
			[] generators
		in
		CList (List.rev (eval_list env loc expr_list bounds []))

and eval_list env loc expr_list bounds acc =
	match bounds with
		| [] ->
			List.fold_left
				(fun csts expr ->
					let cst = eval env expr in
					(match csts with
						| [] -> ()
						| hd :: _ -> assert_cst_types_equal loc (hd, cst));
					cst :: csts)
				acc expr_list
		| _ ->
			(* when we have a generator, they are executed in the initialize action *)
			raise List_expression

and ir_of_var_info env var_info =
	mk_var_def
		var_info.Calast.v_assignable var_info.Calast.v_global
		var_info.Calast.v_loc var_info.Calast.v_name
		(ir_of_type env var_info.Calast.v_loc var_info.Calast.v_type)

and ir_of_type env loc = function
	| Calast.TypeBool -> TypeBool

	| Calast.TypeInt expr ->
		(try
			match eval env expr with
				| CInt i -> TypeInt i
				| _ -> error_size_not_constant loc "int"
		with Not_evaluable reason -> error_size_not_constant ~reason loc "int")

	| Calast.TypeList (t, e) ->
		(try
			match eval env e with
				| CInt i -> TypeList (ir_of_type env loc t, i)
				| _ -> error_size_not_constant loc "List"
		with Not_evaluable reason -> error_size_not_constant ~reason loc "List")

	| Calast.TypeStr -> TypeStr

	| Calast.TypeUint expr ->
		(try
			match eval env expr with
				| CInt i -> TypeUint i
				| _ -> error_size_not_constant loc "uint"
		with Not_evaluable reason -> error_size_not_constant ~reason loc "uint")
