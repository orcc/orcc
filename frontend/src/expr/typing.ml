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

exception Type_error of string

let type_of_int int =
	let length = int_of_float (floor (log (float int) /. (log 2.0))) + 1 in
  TypeUint length

let rec type_of_elt typ n =
	match typ with
		| TypeList (typ, _) when n > 0 -> type_of_elt typ (n - 1)
		| typ when n = 0 -> typ
		| typ ->
			let typ = Asthelper.PrettyPrinter.string_of_type typ in
			raise
				(Type_error
					(sprintf "expected %i-dimensional List type, got %s" n typ))

(** [lub t1 t2] returns the least upper bound of types [t1] and [t2]. *)
let rec lub t1 t2 =
	match (t1, t2) with
		| (TypeBool, TypeBool) -> TypeBool
		| (TypeStr, TypeStr) -> TypeStr
		| (TypeVoid, TypeVoid) -> TypeVoid
		| (TypeUnknown, t)
		| (t, TypeUnknown) -> t

		| (TypeInt s1, TypeInt s2) -> TypeInt (max s1 s2)
		| (TypeUint s1, TypeUint s2) -> TypeUint (max s1 s2)

		| (TypeInt si, TypeUint su)
		| (TypeUint su, TypeInt si) ->
			if si > su then
				TypeInt si
			else
				TypeInt (su + 1)

		| (TypeList (t1, s1), TypeList (t2, s2)) -> TypeList (lub t1 t2, max s1 s2)
		
		| (t1, t2) ->
			let t1 = Asthelper.PrettyPrinter.string_of_type t1 in
			let t2 = Asthelper.PrettyPrinter.string_of_type t2 in
			raise
				(Type_error (sprintf "type %s is not compatible with type %s" t1 t2))

let rec check_list_size loc tgt_typ tgt_length typ length =
	if tgt_length < length then
		Asthelper.failwith loc
			(sprintf "target list is too small! target = %i, source = %i" tgt_length length);
	match (tgt_typ, typ) with
		| (TypeList (tgt_typ, tgt_length), TypeList (typ, length)) ->
			check_list_size loc tgt_typ tgt_length typ length
		| _ ->
			try
				ignore (lub tgt_typ typ)
			with Type_error reason ->
				Asthelper.failwith loc
					("target list is not compatible with list expression: " ^ reason)

let rec type_of_cst cst =
	match cst with
		| CBool _ -> TypeBool
		| CFloat _ -> TypeFloat
		| CInt int -> type_of_int int
		| CList list ->
			(match list with
				| [] -> TypeList (TypeUnknown, 0)
				| h::t ->
					let (typ, size) =
						List.fold_left
							(fun (typ, size) cst -> (lub typ (type_of_cst cst), size + 1))
						(type_of_cst h, 1) t
					in
					TypeList (typ, size))
		| CStr _ -> TypeStr 

let type_of_bop loc t1 bop t2 =
	match (t1, t2) with
		| (TypeBool, TypeBool) ->
			(match bop with
				| BOpLAnd | BOpLOr
				| BOpEQ | BOpGE | BOpGT | BOpLE | BOpLT | BOpNE -> TypeBool
				| _ ->
					let bop = Asthelper.PrettyPrinter.string_of_bop bop in
					Asthelper.failwith loc
						(sprintf "cannot use operator %s with boolean operators" bop))

		| (TypeInt _, TypeInt _) | (TypeUint _, TypeUint _)
		| (TypeInt _, TypeUint _) | (TypeUint _, TypeInt _) ->
			(match bop with
				| BOpEQ | BOpGE | BOpGT | BOpLE | BOpLT | BOpNE -> TypeBool
				| BOpLAnd | BOpLOr ->
					let bop = Asthelper.PrettyPrinter.string_of_bop bop in
					Asthelper.failwith loc
						(sprintf "cannot use operator %s with integer operators" bop)
				| _ -> lub t1 t2)
		
		| (TypeList _, TypeList _) ->
			(match bop with
				| BOpPlus ->
					Asthelper.failwith loc "concatenation of Lists not supported"
				| _ ->
					let bop = Asthelper.PrettyPrinter.string_of_bop bop in
					Asthelper.failwith loc
						(sprintf "binary operator %s not supported on Lists" bop))
		
		| (TypeStr, TypeStr) ->
			Asthelper.failwith loc "no binary operation supported on Strings"
		
		| _ ->
			let t1 = Asthelper.PrettyPrinter.string_of_type t1 in
			let t2 = Asthelper.PrettyPrinter.string_of_type t2 in
			Asthelper.failwith loc
				(sprintf "incompatible types: %s and %s" t1 t2)

let type_of_uop loc uop t =
	match (uop, t) with
		| (UOpMinus, TypeInt s) -> TypeInt s
		| (UOpMinus, TypeUint s) -> TypeInt (s + 1)
		| (UOpBNot, TypeInt _)
		| (UOpBNot, TypeUint _) -> t
		| (UOpLNot, TypeBool) -> TypeBool
		| (UOpNbElts, _) ->
			Asthelper.failwith loc "# operator not supported yet"
		| (uop, t) ->
			let uop = Asthelper.PrettyPrinter.string_of_uop uop in
			let t = Asthelper.PrettyPrinter.string_of_type t in
			Asthelper.failwith loc
				(sprintf "operator %s not valid with type %s" uop t)

let rec type_of_expr = function
	| ExprBOp (loc, e1, bop, e2, typ) ->
		if typ = TypeUnknown then
			let t1 = type_of_expr e1 in
			let t2 = type_of_expr e2 in
			type_of_bop loc t1 bop t2
		else
			typ
		
	| ExprBool _ -> TypeBool
	| ExprFloat _ -> TypeFloat
	| ExprInt (_, int) -> type_of_int int
	| ExprStr _ -> TypeStr
	| ExprUOp (loc, uop, expr, typ) ->
		if typ = TypeUnknown then
			let t = type_of_expr expr in
			type_of_uop loc uop t
		else
			typ
	| ExprVar (_loc, var_use) -> var_use.vu_def.v_type
