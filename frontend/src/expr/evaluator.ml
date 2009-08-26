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

exception Not_null

(** [null_list expr_list] returns the null element (false, or zero, whatever is
appropriate) if expr_list contains only null elements, or lists of null elements,
and raise Not_null otherwise. *)
let rec null_list expr_list =
	match expr_list with
		| [ Calast.ExprBool (_, false) ] -> CList [CBool false]
		| [ Calast.ExprInt (_, 0) ] -> CList [CInt 0]
		| [ Calast.ExprList (_, expr_list, _) ] -> CList [null_list expr_list]
		| _ -> raise Not_null

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
		try
			null_list expr_list
		with Not_null ->
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
		| (var_info, a, b) :: t ->
			let res = ref acc in
			let vd = ir_of_var_info env var_info in
			let env = add_binding_var env vd.v_name vd in
			for i = a to b do
				vd.v_node.n_latt <- LattCst (CInt i);
				res := eval_list env loc expr_list t !res;
			done;
			!res

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

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)

(** [latt_of_expr expr] *)
let rec latt_of_expr expr =
	match expr with
		| ExprBool (_loc, bool) -> LattCst (CBool bool)
		| ExprInt (_loc, int) -> LattCst (CInt int)
		| ExprVar (_loc, var_use) ->
			let var_def = var_use.vu_def in
			var_def.v_node.n_latt
		| ExprStr (_loc, str) -> LattCst (CStr str)

		| ExprUOp (loc, uop, e, _) ->
			let latt = latt_of_expr e in
			(match (uop, latt) with
				(************************ special rules ************************)
				(* a unary operation with top is top. *)
				| (_, LattTop) -> LattTop
				
				(* a unary operation with bottom is bottom. *)
				| (_, LattBot) -> LattBot

				(************************ invalid operations ************************)
				| (UOpBNot, LattCst (CBool _))
				| (UOpBNot, LattCst (CList _))
				| (UOpBNot, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: the operand of \"bitnot\" must be an integer"

				| (UOpLNot, LattCst (CInt _))
				| (UOpLNot, LattCst (CList _))
				| (UOpLNot, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: the operand of \"not\" must be boolean"

				| (UOpMinus, LattCst (CBool _))
				| (UOpMinus, LattCst (CList _))
				| (UOpMinus, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: the operand of \"-\" must be an integer"

				| (UOpNbElts, LattCst (CBool _))
				| (UOpNbElts, LattCst (CInt _))
				| (UOpNbElts, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: the operand of \"#\" must be a list"
				
				(************************ booleans ************************)
				| (UOpLNot, LattCst (CBool b)) -> LattCst (CBool (not b))
				
				(************************ integers ************************)
				| (UOpBNot, LattCst (CInt i)) -> LattCst (CInt (lnot i))
				| (UOpMinus, LattCst (CInt i)) -> LattCst (CInt (- i))

				(************************ lists ************************)
				| (UOpNbElts, LattCst (CList l)) -> LattCst (CInt (List.length l)))

		| ExprBOp (loc, e1, bop, e2, _t) ->
			let latt1 = latt_of_expr e1 in
			let latt2 = latt_of_expr e2 in
			(match (latt1, bop, latt2) with
				(************************ special rules ************************)
				(* these rules state that anything and false is false, and anything or true is true. *)
				(* they must come before the rules on top/bottom lattices. *)
				(* special rule for "and" *)
				| (_, BOpLAnd, LattCst (CBool false))
				| (LattCst (CBool false), BOpLAnd, _) -> LattCst (CBool false)

				(* special rule for "or" *)
				| (_, BOpLOr, LattCst (CBool true))
				| (LattCst (CBool true), BOpLOr, _) -> LattCst (CBool true)
				
				(* a binary operation with bottom is bottom. *)
				| (LattBot, _, _)
				| (_, _, LattBot) -> LattBot

				(* a binary operation with top is top. *)
				| (LattTop, _, _)
				| (_, _, LattTop) -> LattTop

				(************************ invalid operations ************************)
				(* boolean operations. *)
				| (LattCst (CInt _), BOpLAnd, _)
				| (_, BOpLAnd, LattCst (CInt _))
				| (LattCst (CStr _), BOpLAnd, _)
				| (_, BOpLAnd, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"and\" must be booleans"
				| (LattCst (CInt _), BOpLOr, _)
				| (_, BOpLOr, LattCst (CInt _))
				| (LattCst (CStr _), BOpLOr, _)
				| (_, BOpLOr, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"or\" must be booleans"

				(* bitwise integer operations. *)
				| (LattCst (CBool _), BOpBAnd, _)
				| (_, BOpBAnd, LattCst (CBool _))
				| (LattCst (CStr _), BOpBAnd, _)
				| (_, BOpBAnd, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"bitand\" must be integers"
				| (LattCst (CBool _), BOpBOr, _)
				| (_, BOpBOr, LattCst (CBool _))
				| (LattCst (CStr _), BOpBOr, _)
				| (_, BOpBOr, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"bitor\" must be integers"
				| (LattCst (CBool _), BOpBXor, _)
				| (_, BOpBXor, LattCst (CBool _))
				| (LattCst (CStr _), BOpBXor, _)
				| (_, BOpBXor, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"bitxor\" must be integers"
				| (LattCst (CBool _), BOpShL, _)
				| (_, BOpShL, LattCst (CBool _))
				| (LattCst (CStr _), BOpShL, _)
				| (_, BOpShL, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"lshift\" must be integers"
				| (LattCst (CBool _), BOpShR, _)
				| (_, BOpShR, LattCst (CBool _))
				| (LattCst (CStr _), BOpShR, _)
				| (_, BOpShR, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"rshift\" must be integers"

				(* integer operations. *)
				| (LattCst (CBool _), BOpDiv, _)
				| (_, BOpDiv, LattCst (CBool _))
				| (LattCst (CStr _), BOpDiv, _)
				| (_, BOpDiv, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"/\" must be integers"
				| (LattCst (CBool _), BOpDivInt, _)
				| (_, BOpDivInt, LattCst (CBool _))
				| (LattCst (CStr _), BOpDivInt, _)
				| (_, BOpDivInt, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"div\" must be integers"
				| (LattCst (CBool _), BOpExp, _)
				| (_, BOpExp, LattCst (CBool _))
				| (LattCst (CStr _), BOpExp, _)
				| (_, BOpExp, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"exp\" must be integers"
				| (LattCst (CBool _), BOpMinus, _)
				| (_, BOpMinus, LattCst (CBool _))
				| (LattCst (CStr _), BOpMinus, _)
				| (_, BOpMinus, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"-\" must be integers"
				| (LattCst (CBool _), BOpMod, _)
				| (_, BOpMod, LattCst (CBool _))
				| (LattCst (CStr _), BOpMod, _)
				| (_, BOpMod, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"mod\" must be integers"
				| (LattCst (CBool _), BOpTimes, _)
				| (_, BOpTimes, LattCst (CBool _))
				| (LattCst (CStr _), BOpTimes, _)
				| (_, BOpTimes, LattCst (CStr _)) ->
					Asthelper.failwith loc "type error: operands of \"*\" must be integers"
				
				(* concatenation/plus operator. *)
				| (LattCst (CBool _), BOpPlus, LattCst (CBool _))
				| (LattCst (CBool _), BOpPlus, LattCst (CInt _))
				| (LattCst (CInt _), BOpPlus, LattCst (CBool _)) ->
					Asthelper.failwith loc "type error: \"+\" cannot be used with booleans"
				
				(************************ comparisons ************************)
				| (LattCst (CBool b1), BOpEQ, LattCst (CBool b2)) -> LattCst (CBool (b1 = b2))
				| (LattCst (CBool b1), BOpGE, LattCst (CBool b2)) -> LattCst (CBool (b1 >= b2))
				| (LattCst (CBool b1), BOpGT, LattCst (CBool b2)) -> LattCst (CBool (b1 > b2))
				| (LattCst (CBool b1), BOpLE, LattCst (CBool b2)) -> LattCst (CBool (b1 <= b2))
				| (LattCst (CBool b1), BOpLT, LattCst (CBool b2)) -> LattCst (CBool (b1 < b2))
				| (LattCst (CBool b1), BOpNE, LattCst (CBool b2)) -> LattCst (CBool (b1 <> b2))

				| (LattCst (CInt i1), BOpEQ, LattCst (CInt i2)) -> LattCst (CBool (i1 = i2))
				| (LattCst (CInt i1), BOpGE, LattCst (CInt i2)) -> LattCst (CBool (i1 >= i2))
				| (LattCst (CInt i1), BOpGT, LattCst (CInt i2)) -> LattCst (CBool (i1 > i2))
				| (LattCst (CInt i1), BOpLE, LattCst (CInt i2)) -> LattCst (CBool (i1 <= i2))
				| (LattCst (CInt i1), BOpLT, LattCst (CInt i2)) -> LattCst (CBool (i1 < i2))
				| (LattCst (CInt i1), BOpNE, LattCst (CInt i2)) -> LattCst (CBool (i1 <> i2))

				| (LattCst (CStr s1), BOpEQ, LattCst (CStr s2)) -> LattCst (CBool (s1 = s2))
				| (LattCst (CStr s1), BOpGE, LattCst (CStr s2)) -> LattCst (CBool (s1 >= s2))
				| (LattCst (CStr s1), BOpGT, LattCst (CStr s2)) -> LattCst (CBool (s1 > s2))
				| (LattCst (CStr s1), BOpLE, LattCst (CStr s2)) -> LattCst (CBool (s1 <= s2))
				| (LattCst (CStr s1), BOpLT, LattCst (CStr s2)) -> LattCst (CBool (s1 < s2))
				| (LattCst (CStr s1), BOpNE, LattCst (CStr s2)) -> LattCst (CBool (s1 <> s2))
				
				(************************ invalid comparisons ************************)
				| (LattCst (CBool _), BOpEQ, _)
				| (LattCst (CBool _), BOpGE, _)
				| (LattCst (CBool _), BOpGT, _)
				| (LattCst (CBool _), BOpLE, _)
				| (LattCst (CBool _), BOpLT, _)
				| (LattCst (CBool _), BOpNE, _)

				| (LattCst (CInt _), BOpEQ, _)
				| (LattCst (CInt _), BOpGE, _)
				| (LattCst (CInt _), BOpGT, _)
				| (LattCst (CInt _), BOpLE, _)
				| (LattCst (CInt _), BOpLT, _)
				| (LattCst (CInt _), BOpNE, _)

				| (LattCst (CStr _), BOpEQ, _)
				| (LattCst (CStr _), BOpGE, _)
				| (LattCst (CStr _), BOpGT, _)
				| (LattCst (CStr _), BOpLE, _)
				| (LattCst (CStr _), BOpLT, _)
				| (LattCst (CStr _), BOpNE, _) -> 
					Asthelper.failwith loc
						"type error: only operands of similar types should be compared"
				
				(************************ booleans ************************)
				(* bool: other binary operations. *)
				| (LattCst (CBool b1), BOpLAnd, LattCst (CBool b2)) -> LattCst (CBool (b1 && b2))
				| (LattCst (CBool b1), BOpLOr, LattCst (CBool b2)) -> LattCst (CBool (b1 || b2))

				(************************ bitwise integers ************************)
				| (LattCst (CInt i1), BOpBAnd, LattCst (CInt i2)) -> LattCst (CInt (i1 land i2))
				| (LattCst (CInt i1), BOpBOr, LattCst (CInt i2)) -> LattCst (CInt (i1 lor i2))
				| (LattCst (CInt i1), BOpBXor, LattCst (CInt i2)) -> LattCst (CInt (i1 lxor i2))
				| (LattCst (CInt i1), BOpShL, LattCst (CInt i2)) -> LattCst (CInt (i1 lsl i2))
				| (LattCst (CInt i1), BOpShR, LattCst (CInt i2)) -> LattCst (CInt (i1 lsr i2))

				(************************ integers ************************)
				| (LattCst (CInt i1), BOpDiv, LattCst (CInt i2)) -> LattCst (CInt (i1 / i2))
				| (LattCst (CInt i1), BOpDivInt, LattCst (CInt i2)) -> LattCst (CInt (i1 / i2))
				| (LattCst (CInt i1), BOpExp, LattCst (CInt i2)) ->
					LattCst (CInt (int_of_float (float i1 ** float i2)))
				| (LattCst (CInt i1), BOpMinus, LattCst (CInt i2)) -> LattCst (CInt (i1 - i2))
				| (LattCst (CInt i1), BOpMod, LattCst (CInt i2)) -> LattCst (CInt (i1 mod i2))
				| (LattCst (CInt i1), BOpPlus, LattCst (CInt i2)) -> LattCst (CInt (i1 + i2))
				| (LattCst (CInt i1), BOpTimes, LattCst (CInt i2)) -> LattCst (CInt (i1 * i2))

				(************************ strings ************************)
				| (LattCst (CStr s1), BOpPlus, LattCst (CBool b2)) ->
					LattCst (CStr (s1 ^ string_of_bool b2))
				| (LattCst (CStr s1), BOpPlus, LattCst (CInt i2)) ->
					LattCst (CStr (s1 ^ string_of_int i2))
				| (LattCst (CBool b1), BOpPlus, LattCst (CStr s2)) ->
					LattCst (CStr (string_of_bool b1 ^ s2))
				| (LattCst (CInt i1), BOpPlus, LattCst (CStr s2)) ->
					LattCst (CStr (string_of_int i1 ^ s2))
				| (LattCst (CStr s1), BOpPlus, LattCst (CStr s2)) -> LattCst (CStr (s1 ^ s2))
				
				| (LattCst (CList _), _, _)
				| (_, _, LattCst (CList _)) ->
					Asthelper.failwith loc "binary operations over lists are not implemented yet")

(** [replace_const expr] returns a new expression where references to variables
with a constant lattice have been replaced by their values. *)
let rec replace_const expr =
	match expr with
		| ExprBool _
		| ExprInt _
		| ExprStr _ -> expr
 
		| ExprVar (_, var_use) ->
			let var_def = var_use.vu_def in
			(match var_def.v_node.n_latt with
				| LattCst (CBool bool) ->
					remove_var_use var_use;
					ExprBool (dummy_loc, bool)
				| LattCst (CInt int) ->
					remove_var_use var_use;
					ExprInt (dummy_loc, int)
				| LattCst (CStr str) ->
					remove_var_use var_use;
					ExprStr (dummy_loc, str)
				| _ ->
					(* list or not constant => return the original expr *)
					expr)
		
		| ExprUOp (loc, uop, expr, t) ->
			let expr = replace_const expr in
			(match (uop, expr) with
				(************************ invalid operations ************************)
				| (UOpBNot, ExprBool _)
				| (UOpBNot, ExprStr _) ->
					Asthelper.failwith loc "type error: the operand of \"bitnot\" must be an integer"

				| (UOpLNot, ExprInt _)
				| (UOpLNot, ExprStr _) ->
					Asthelper.failwith loc "type error: the operand of \"not\" must be boolean"

				| (UOpMinus, ExprBool _)
				| (UOpMinus, ExprStr _) ->
					Asthelper.failwith loc "type error: the operand of \"-\" must be an integer"

				| (UOpNbElts, _) ->
					Asthelper.failwith loc "not implemented yet"
				
				(************************ booleans ************************)
				| (UOpLNot, ExprBool (loc, b)) -> ExprBool (loc, not b)
				
				(************************ integers ************************)
				| (UOpBNot, ExprInt (loc, i)) -> ExprInt (loc, lnot i)
				| (UOpMinus, ExprInt (loc, i)) -> ExprInt (loc, - i)
				
				| _ ->
					(* cannot return a constant *)
					ExprUOp (loc, uop, expr, t))

		| ExprBOp (loc, e1, bop, e2, t) ->
			let e1 = replace_const e1 in
			let e2 = replace_const e2 in
			(match (e1, bop, e2) with
				(************************ invalid operations ************************)
				(* boolean operations. *)
				| (ExprInt _, BOpLAnd, _)
				| (_, BOpLAnd, ExprInt _)
				| (ExprStr _, BOpLAnd, _)
				| (_, BOpLAnd, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"and\" must be booleans"
				| (ExprInt _, BOpLOr, _)
				| (_, BOpLOr, ExprInt _)
				| (ExprStr _, BOpLOr, _)
				| (_, BOpLOr, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"or\" must be booleans"

				(* bitwise integer operations. *)
				| (ExprBool _, BOpBAnd, _)
				| (_, BOpBAnd, ExprBool _)
				| (ExprStr _, BOpBAnd, _)
				| (_, BOpBAnd, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"bitand\" must be integers"
				| (ExprBool _, BOpBOr, _)
				| (_, BOpBOr, ExprBool _)
				| (ExprStr _, BOpBOr, _)
				| (_, BOpBOr, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"bitor\" must be integers"
				| (ExprBool _, BOpBXor, _)
				| (_, BOpBXor, ExprBool _)
				| (ExprStr _, BOpBXor, _)
				| (_, BOpBXor, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"bitxor\" must be integers"
				| (ExprBool _, BOpShL, _)
				| (_, BOpShL, ExprBool _)
				| (ExprStr _, BOpShL, _)
				| (_, BOpShL, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"lshift\" must be integers"
				| (ExprBool _, BOpShR, _)
				| (_, BOpShR, ExprBool _)
				| (ExprStr _, BOpShR, _)
				| (_, BOpShR, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"rshift\" must be integers"

				(* integer operations. *)
				| (ExprBool _, BOpDiv, _)
				| (_, BOpDiv, ExprBool _)
				| (ExprStr _, BOpDiv, _)
				| (_, BOpDiv, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"/\" must be integers"
				| (ExprBool _, BOpDivInt, _)
				| (_, BOpDivInt, ExprBool _)
				| (ExprStr _, BOpDivInt, _)
				| (_, BOpDivInt, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"div\" must be integers"
				| (ExprBool _, BOpExp, _)
				| (_, BOpExp, ExprBool _)
				| (ExprStr _, BOpExp, _)
				| (_, BOpExp, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"exp\" must be integers"
				| (ExprBool _, BOpMinus, _)
				| (_, BOpMinus, ExprBool _)
				| (ExprStr _, BOpMinus, _)
				| (_, BOpMinus, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"-\" must be integers"
				| (ExprBool _, BOpMod, _)
				| (_, BOpMod, ExprBool _)
				| (ExprStr _, BOpMod, _)
				| (_, BOpMod, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"mod\" must be integers"
				| (ExprBool _, BOpTimes, _)
				| (_, BOpTimes, ExprBool _)
				| (ExprStr _, BOpTimes, _)
				| (_, BOpTimes, ExprStr _) ->
					Asthelper.failwith loc "type error: operands of \"*\" must be integers"
				
				(* concatenation/plus operator. *)
				| (ExprBool _, BOpPlus, ExprBool _)
				| (ExprBool _, BOpPlus, ExprInt _)
				| (ExprInt _, BOpPlus, ExprBool _) ->
					Asthelper.failwith loc "type error: \"+\" cannot be used with booleans"

				(************************ comparisons ************************)
				| (ExprBool (loc, b1), BOpEQ, ExprBool (_, b2)) -> ExprBool (loc, b1 = b2)
				| (ExprBool (loc, b1), BOpGE, ExprBool (_, b2)) -> ExprBool (loc, b1 >= b2)
				| (ExprBool (loc, b1), BOpGT, ExprBool (_, b2)) -> ExprBool (loc, b1 > b2)
				| (ExprBool (loc, b1), BOpLE, ExprBool (_, b2)) -> ExprBool (loc, b1 <= b2)
				| (ExprBool (loc, b1), BOpLT, ExprBool (_, b2)) -> ExprBool (loc, b1 < b2)
				| (ExprBool (loc, b1), BOpNE, ExprBool (_, b2)) -> ExprBool (loc, b1 <> b2)

				| (ExprInt (loc, i1), BOpEQ, ExprInt (_, i2)) -> ExprBool (loc, i1 = i2)
				| (ExprInt (loc, i1), BOpGE, ExprInt (_, i2)) -> ExprBool (loc, i1 >= i2)
				| (ExprInt (loc, i1), BOpGT, ExprInt (_, i2)) -> ExprBool (loc, i1 > i2)
				| (ExprInt (loc, i1), BOpLE, ExprInt (_, i2)) -> ExprBool (loc, i1 <= i2)
				| (ExprInt (loc, i1), BOpLT, ExprInt (_, i2)) -> ExprBool (loc, i1 < i2)
				| (ExprInt (loc, i1), BOpNE, ExprInt (_, i2)) -> ExprBool (loc, i1 <> i2)

				| (ExprStr (loc, s1), BOpEQ, ExprStr (_, s2)) -> ExprBool (loc, s1 = s2)
				| (ExprStr (loc, s1), BOpGE, ExprStr (_, s2)) -> ExprBool (loc, s1 >= s2)
				| (ExprStr (loc, s1), BOpGT, ExprStr (_, s2)) -> ExprBool (loc, s1 > s2)
				| (ExprStr (loc, s1), BOpLE, ExprStr (_, s2)) -> ExprBool (loc, s1 <= s2)
				| (ExprStr (loc, s1), BOpLT, ExprStr (_, s2)) -> ExprBool (loc, s1 < s2)
				| (ExprStr (loc, s1), BOpNE, ExprStr (_, s2)) -> ExprBool (loc, s1 <> s2)
				
				(************************ invalid comparisons ************************)
				| (ExprBool _, BOpEQ, _)
				| (ExprBool _, BOpGE, _)
				| (ExprBool _, BOpGT, _)
				| (ExprBool _, BOpLE, _)
				| (ExprBool _, BOpLT, _)
				| (ExprBool _, BOpNE, _)

				| (ExprInt _, BOpEQ, _)
				| (ExprInt _, BOpGE, _)
				| (ExprInt _, BOpGT, _)
				| (ExprInt _, BOpLE, _)
				| (ExprInt _, BOpLT, _)
				| (ExprInt _, BOpNE, _)

				| (ExprStr _, BOpEQ, _)
				| (ExprStr _, BOpGE, _)
				| (ExprStr _, BOpGT, _)
				| (ExprStr _, BOpLE, _)
				| (ExprStr _, BOpLT, _)
				| (ExprStr _, BOpNE, _) -> 
					Asthelper.failwith loc
						"type error: only operands of similar types should be compared"
				
				(************************ booleans ************************)
				(* bool: other binary operations. *)
				| (ExprBool (_, b1), BOpLAnd, ExprBool (_, b2)) -> ExprBool (loc, b1 && b2)
				| (ExprBool (_, true), BOpLAnd, e2) -> e2
				| (ExprBool (_, b1), BOpLOr, ExprBool (_, b2)) -> ExprBool (loc, b1 || b2)

				(************************ bitwise integers ************************)
				| (ExprInt (loc, i1), BOpBAnd, ExprInt (_, i2)) -> ExprInt (loc, i1 land i2)
				| (ExprInt (loc, i1), BOpBOr, ExprInt (_, i2)) -> ExprInt (loc, i1 lor i2)
				| (ExprInt (loc, i1), BOpBXor, ExprInt (_, i2)) -> ExprInt (loc, i1 lxor i2)
				| (ExprInt (loc, i1), BOpShL, ExprInt (_, i2)) -> ExprInt (loc, i1 lsl i2)
				| (ExprInt (loc, i1), BOpShR, ExprInt (_, i2)) -> ExprInt (loc, i1 lsr i2)

				(************************ integers ************************)
				| (ExprInt (loc, i1), BOpDiv, ExprInt (_, i2)) -> ExprInt (loc, i1 / i2)
				| (ExprInt (loc, i1), BOpDivInt, ExprInt (_, i2)) -> ExprInt (loc, i1 / i2)
				| (ExprInt (loc, i1), BOpExp, ExprInt (_, i2)) ->
					ExprInt (loc, int_of_float (float i1 ** float i2))
				| (ExprInt (loc, i1), BOpMinus, ExprInt (_, i2)) -> ExprInt (loc, i1 - i2)
				| (ExprInt (loc, i1), BOpMod, ExprInt (_, i2)) -> ExprInt (loc, i1 mod i2)
				| (ExprInt (loc, i1), BOpPlus, ExprInt (_, i2)) -> ExprInt (loc, i1 + i2)
				| (ExprInt (loc, i1), BOpTimes, ExprInt (_, i2)) -> ExprInt (loc, i1 * i2)

				(************************ strings ************************)
				| (ExprStr (loc, s1), BOpPlus, ExprBool (_, b2)) ->
					ExprStr (loc, s1 ^ string_of_bool b2)
				| (ExprStr (loc, s1), BOpPlus, ExprInt (_, i2)) ->
					ExprStr (loc, s1 ^ string_of_int i2)
				| (ExprBool (_, b1), BOpPlus, ExprStr (_, s2)) ->
					ExprStr (loc, string_of_bool b1 ^ s2)
				| (ExprInt (loc, i1), BOpPlus, ExprStr (_, s2)) ->
					ExprStr (loc, string_of_int i1 ^ s2)
				| (ExprStr (loc, s1), BOpPlus, ExprStr (_, s2)) -> ExprStr (loc, s1 ^ s2)

				| _ ->
					(* not constant *)
					ExprBOp (loc, e1, bop, e2, t))
