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

(* this one is located in the actor/ast folder. It allows this module to access *)
(* mk_env () and stuff like that. *)
open Ast2ir_util

module SH = Asthelper.SH

type tree =
	| E of Loc.t * string * Xmlm.attribute list * tree list
	| D of Loc.t * string

let get_attribute loc elt_name attributes name =
	try
		List.assoc ("", name) attributes
	with Not_found ->
		Asthelper.failwith loc
			(sprintf "element \"%s\", expected \"%s\" attribute" elt_name name)

let find_elt_get_rest elt f children =
	let rec aux children =
		match children with
			| [] -> raise Not_found
			| E (loc, name, attributes, children) :: tl when name = elt ->
				(f loc name attributes children tl, tl)
			| _ :: tl -> aux tl
	in
	try
		aux children
	with Not_found ->
		failwith (sprintf "expected \"%s\" element" elt) 

let find_elt elt f children = fst (find_elt_get_rest elt f children) 

let iter_all elt f children =
	let rec aux children =
		match children with
			| [] -> ()
			| E (loc, name, attributes, children) :: tl when name = elt ->
				f loc name attributes children;
				aux tl
			| _ :: tl -> aux tl
	in
	aux children

let fold_all elt f children =
	let rec aux acc children =
		match children with
			| [] -> acc
			| E (loc, name, attributes, children) :: tl when name = elt ->
				aux (f acc loc name attributes children) tl
			| _ :: tl -> aux acc tl
	in
	List.rev (aux [] children)

(** [fold_partial elt ~until f children acc] iterates over [children], calls [f] when
elements have a name that matches [elt], and returns when an element has a name that
matches [until]. *)
let fold_partial elt ~until f children acc =
	let rec aux acc children =
		match children with
			| [] -> ([], acc)
			| E (loc, name, attributes, children) :: tl when name = elt ->
				aux (f acc loc name attributes children) tl
			| E (_, name, _, _) :: tl ->
				if List.mem name until then
					(children, acc)
				else
					aux acc tl
			| _ :: tl -> aux acc tl
	in
	aux acc children

let get_attribute loc elt_name attributes name =
	try
		List.assoc ("", name) attributes
	with Not_found ->
		Asthelper.failwith loc
			(sprintf "element \"%s\", expected \"%s\" attribute" elt_name name)

let find_elt_get_rest elt f children =
	let rec aux children =
		match children with
			| [] -> raise Not_found
			| E (loc, name, attributes, children) :: tl when name = elt ->
				(f loc name attributes children tl, tl)
			| _ :: tl -> aux tl
	in
	try
		aux children
	with Not_found ->
		failwith (sprintf "expected \"%s\" element" elt) 

let find_elt elt f children = fst (find_elt_get_rest elt f children) 

let iter_all elt f children =
	let rec aux children =
		match children with
			| [] -> ()
			| E (loc, name, attributes, children) :: tl when name = elt ->
				f loc name attributes children;
				aux tl
			| _ :: tl -> aux tl
	in
	aux children

let fold_all elt f children =
	let rec aux acc children =
		match children with
			| [] -> acc
			| E (loc, name, attributes, children) :: tl when name = elt ->
				aux (f acc loc name attributes children) tl
			| _ :: tl -> aux acc tl
	in
	List.rev (aux [] children)

(** [fold_partial elt ~until f children acc] iterates over [children], calls [f] when
elements have a name that matches [elt], and returns when an element has a name that
matches [until]. *)
let fold_partial elt ~until f children acc =
	let rec aux acc children =
		match children with
			| [] -> ([], acc)
			| E (loc, name, attributes, children) :: tl when name = elt ->
				aux (f acc loc name attributes children) tl
			| E (_, name, _, _) :: tl ->
				if List.mem name until then
					(children, acc)
				else
					aux acc tl
			| _ :: tl -> aux acc tl
	in
	aux acc children

let bop_of_string loc elt_name = function
	| "and" -> Calast.BOpAnd
	| "/" -> Calast.BOpDiv
	| "div" -> Calast.BOpDivInt
	| "=" -> Calast.BOpEQ
	| "^" -> Calast.BOpExp
	| ">=" -> Calast.BOpGE
	| ">" -> Calast.BOpGT
	| "<=" -> Calast.BOpLE
	| "<" -> Calast.BOpLT
	| "-" -> Calast.BOpMinus
	| "mod" -> Calast.BOpMod
	| "!=" -> Calast.BOpNE
	| "or" -> Calast.BOpOr
	| "+" -> Calast.BOpPlus
	| "*" -> Calast.BOpTimes
	| name ->
		Asthelper.failwith loc
			(sprintf "element \"%s\", unknown operator \"%s\"" elt_name name)

let uop_of_string loc elt_name = function
	| "#" -> Calast.UOpNbElts
	| "not" -> Calast.UOpNot
	| "-" -> Calast.UOpMinus
	| name ->
		Asthelper.failwith loc
			(sprintf "element \"%s\", unknown operator \"%s\"" elt_name name)
	
let string_of_uop = function
	| Calast.UOpMinus -> "-"
	| Calast.UOpNbElts -> "#"
	| Calast.UOpNot -> "not"

let string_of_bop = function
	| Calast.BOpAnd -> "and"
	| Calast.BOpBAnd -> "&"
	| Calast.BOpBOr -> "|"
	| Calast.BOpDiv -> "/"
	| Calast.BOpDivInt -> "div"
	| Calast.BOpEQ -> "="
	| Calast.BOpExp -> "^"
	| Calast.BOpGE -> ">="
	| Calast.BOpGT -> ">"
	| Calast.BOpLE -> "<="
	| Calast.BOpLT -> "<"
	| Calast.BOpMinus -> "-"
	| Calast.BOpMod -> "mod"
	| Calast.BOpNE -> "!="
	| Calast.BOpOr -> "or"
	| Calast.BOpPlus -> "+"
	| Calast.BOpShL -> "<<"
	| Calast.BOpShR -> ">>"
	| Calast.BOpTimes -> "*"

type arg =
	| Expr of Calast.expr
	| Op of Calast.bop

let rec string_of_expr = function
	| Calast.ExprBOp (_, e1, bop, e2) ->
		sprintf "(%s %s %s)" (string_of_expr e1) (string_of_bop bop) (string_of_expr e2)
	| Calast.ExprBool (_, bool) -> string_of_bool bool
	| Calast.ExprCall (loc, _, _) ->
		Asthelper.failwith loc "calls not supported yet"
	| Calast.ExprIf (_, e1, e2, e3) ->
		sprintf "(if %s then %s else %s end)"
			(string_of_expr e1) (string_of_expr e2) (string_of_expr e3 ^ " end ")
	| Calast.ExprIdx (loc, _, _) ->
		Asthelper.failwith loc "indexes not supported yet"
	| Calast.ExprInt (_, int) -> string_of_int int
	| Calast.ExprList (loc, _, _) ->
		Asthelper.failwith loc "lists not supported yet"
	| Calast.ExprStr (_, str) -> str
	| Calast.ExprUOp (_, uop, expr) ->
		sprintf "(%s %s)" (string_of_uop uop) (string_of_expr expr)
	| Calast.ExprVar (_, var) -> var

let string_of_exprs exprs =
	String.concat " "
		(List.map
			(fun elt ->
				match elt with
					| Expr e -> string_of_expr e
					| Op op -> string_of_bop op)
			exprs)

(** [parse_expr children] returns a [Calast.expr] obtained by matching against the
first "Expr" element. *)
let rec parse_expr children =
	find_elt "Expr"
		(fun loc elt_name attributes children _ ->
			let kind = get_attribute loc elt_name attributes "kind" in
			match kind with
				| "Application" ->
					find_elt "Expr"
						(fun l e a c next_siblings ->
							let proc =
								match parse_expr [E (l, e, a, c)] with
								| Calast.ExprVar (_, proc) -> proc
								| _ ->
									Asthelper.failwith loc
										(sprintf "element \"%s\", expected <Expr kind=\"Var\">" e)
							in
							let args =
								find_elt "Args"
									(fun _ _ _ children _ ->
										fold_all "Expr"
											(fun exprs l e a c -> parse_expr [E (l, e, a, c)] :: exprs)
										children)
								next_siblings
							in
							Calast.ExprCall (loc, proc, args))
						children

				| "BinOpSeq" ->
					let (e1, rest) =
						find_elt_get_rest "Expr" (fun l e a c _ -> parse_expr [E (l, e, a, c)]) children
					in
					parse_binop loc [Expr e1] rest

				| "List" ->
					let exprs =
						fold_all "Expr"
							(fun exprs l e a c -> parse_expr [E (l, e, a, c)] :: exprs)
						children
					in
					Calast.ExprList (loc, exprs, [])

				| "Literal" ->
					(let lit_kind = get_attribute loc elt_name attributes "literal-kind" in
					let value = get_attribute loc elt_name attributes "value" in
					match lit_kind with
						| "Boolean" ->
							(try
								Calast.ExprBool (loc, bool_of_string value)
							with Failure "bool_of_string" ->
								Asthelper.failwith loc "value cannot be represented as a boolean")
						| "Integer" ->
							(try
								Calast.ExprInt (loc, int_of_string value)
							with Failure "int_of_string" ->
								Asthelper.failwith loc "value cannot be represented as an integer")
						| "Real" -> Asthelper.failwith loc "floats are not supported yet"
						| "String" -> Calast.ExprStr (loc, value)
						| "Character" -> Asthelper.failwith loc "chars are not supported yet"
						| _ ->
							Asthelper.failwith loc
								(sprintf "element \"%s\", unknown literal kind \"%s\"" elt_name lit_kind))

				| "UnaryOp" ->
					find_elt "Op"
						(fun loc elt_name attributes _children next_siblings ->
							let op = get_attribute loc elt_name attributes "name" in
							let expr = parse_expr next_siblings in
							Calast.ExprUOp (loc, uop_of_string loc elt_name op, expr))
						children

				| "Var" ->
					let name = get_attribute loc elt_name attributes "name" in
					Calast.ExprVar (loc, name)

				| _ ->
					Asthelper.failwith loc
						(sprintf "element \"%s\", unknown expression \"%s\"" elt_name kind))
		children

(** this sh*t is necessary because XDF does not describe priorities. *) 
and parse_binop loc exprs rest =
	match rest with
		| [] ->
			let str = string_of_exprs exprs in
			Cal_parser_wrapper.parse_expr str
		| _ ->
			let (bop, rest) =
				find_elt_get_rest "Op"
					(fun l e a _ _ ->
						let op = get_attribute l e a "name" in
						bop_of_string l e op)
				rest
			in
			let (e2, rest) =
				find_elt_get_rest "Expr" (fun l e a c _ -> parse_expr [E (l, e, a, c)]) rest
			in
			parse_binop loc (Expr e2 :: Op bop :: exprs) rest

let get_tree file =
	let loc_of_pos (row, col) =
		{ Loc.file_name = file;
			Loc.start = { Loc.line = row; Loc.bol = 0; Loc.off = col };
			Loc.stop = { Loc.line = row; Loc.bol = 0; Loc.off = col } }
	in

  let el pos ((_, local), attributes) children =
		E (loc_of_pos pos, local, attributes, children)
	in
  let data pos str = D (loc_of_pos pos, str) in

	let ic = open_in file in
	let input = Xmlm.make_input ~strip:true (`Channel ic) in
  let (_dtd, tree) =
		try
			Xmlm.input_doc_tree ~el ~data input
		with Xmlm.Error (pos, error) ->
			Asthelper.failwith (loc_of_pos pos) (Xmlm.error_message error)
	in
	close_in ic;
	tree
