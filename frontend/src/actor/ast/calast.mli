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

(** AST types. *)

(** Unary operator. *)
type uop =
	| UOpMinus (** unary minus operator. *)
	| UOpNbElts (** number of elements (#) operator. *)
	| UOpNot (** unary not operator. *)

(** CAL binary operator. *)
type bop =
	| BOpAnd (** boolean and operator. *)
	| BOpBAnd (** bitwise and operator. *)
	| BOpBOr (** bitwise or operator. *)
	| BOpDiv (** division operator. *)
	| BOpDivInt (** integral division operator. *)
	| BOpEQ (** equal comparison operator. *)
	| BOpExp (** exponentiation. *)
	| BOpGE (** greater than or equal operator. *)
	| BOpGT (** greater than operator. *)
	| BOpLE (** less than or equal operator. *)
	| BOpLT (** less than operator. *)
	| BOpMinus (** binary minus operator. *)
	| BOpMod (** modulo operator. *)
	| BOpNE (** different operator. *)
	| BOpOr (** boolean or operator. *)
	| BOpPlus (** plus operator. *)
	| BOpShL (** shift left *)
	| BOpShR (** shift right *)
	| BOpTimes (** times operator. *)

(** reference to a variable *)
type var_ref = Loc.t * string (** forward reference. Occurs only at parsing time. *)

(** expression. *)
type expr =
	| ExprBOp of Loc.t * expr * bop * expr (** CAL binary expression. *)
	| ExprBool of Loc.t * bool (** CAL boolean literal. *)
	| ExprCall of Loc.t * string * expr list (** CAL function call expression. *)
	| ExprFloat of Loc.t * float (** CAL float literal. *)
	| ExprIf of Loc.t * expr * expr * expr (** CAL if expression. *)
	| ExprIdx of Loc.t * var_ref * expr list
	(** CAL array access expression: array var, indexes. *)
	| ExprInt of Loc.t * int (** CAL integer literal. *)
	| ExprList of Loc.t * expr list * (var_info * expr) list
	(** CAL list expression: expressions, generator variable declarations and their values. *)
	| ExprStr of Loc.t * string (** CAL string literal. *)
	| ExprUOp of Loc.t * uop * expr (** CAL unary expression. *)
	| ExprVar of Loc.t * string
	(** CAL variable expression: location, variable. *)

(** A type definition *)
and type_def =
	| TypeBool
	| TypeFloat
	| TypeInt of expr (** int(size=) *)
  | TypeList of type_def * expr (** list(type:, size=)*)
	| TypeStr
	| TypeUint of expr (** uint(size=) *)

(** Variable information. *)
and var_info = {
	v_assignable : bool; (** whether the variable is assignable. *)
	v_global : bool; (** whether the variable is global. *)
	v_loc: Loc.t; (** location of variable. *)
	v_name : string; (** name of variable. *)
	v_type : type_def; (** type of variable. *)
	v_value : expr option; (** optional value of variable. *)
}

(** instructions *)
type instr =
	| InstrAssignArray of Loc.t * var_ref * expr list * expr
	(** array instruction: location, variable, indexes, expression. *)
	| InstrAssignVar of Loc.t * var_ref * expr
	(** variable instruction: location, variable, expression. *)
	| InstrCall of Loc.t * string * expr list
	(** call instruction: location, procedure name, argument list. *)

type stmt =
	| StmtBlock of Loc.t * var_info list * stmt list
	(** a block of statements, with optional variable declarations. *)
  | StmtForeach of Loc.t * var_info * expr * var_info list * stmt list
	(** foreach statement. List of loop variables and their associated expression,
 variable declarations (if any), and statements. *)
	| StmtIf of Loc.t * expr * stmt list * stmt list
	(** if statement: test expression, then statement list, else statement list. *)
	| StmtInstr of Loc.t * instr list
	(** a basic block: a list of instructions. *)
	| StmtWhile of Loc.t * expr * var_info list * stmt list
	(** while statement: test expression, variable declarations, statement list. *)

(** An action tag is a list of strings. *)
type tag = string list

(** CAL action. *)
type action = {
	a_guards : expr list; (** action guards. *)
	a_inputs : (string * (Loc.t * string) list * expr) list;
	(** action input tokens. port name, token declarations, repeat. *)
	a_loc : Loc.t; (** location of the action. *)
	a_outputs : (string * expr list * expr) list;
	(** action output tokens. port name, expressions, repeat. *)
	a_stmts : stmt list; (** action body statements. *)
	a_tag : tag; (** action tag. Two elements are separated
	by a dot in the original source file. *)
	a_vars : var_info list; (** action local declarations. *)
}

(** A function declaration *)
type func = {
	f_decls: var_info list;
	f_expr: expr;
	f_loc: Loc.t;
	f_name: string;
	f_params: var_info list;
	f_return: type_def;
}

(** A procedure declaration *)
type proc = {
	p_decls: var_info list;
	p_loc: Loc.t;
	p_name: string;
	p_params: var_info list;
	p_stmts: stmt list;
}

type fsm = (string * (string * tag * string) list) option

(** actor.*)
type actor = {
	ac_actions : action list; (** actor actions. *)
	ac_file : string; (** absolute file name this actor came from. *)
	ac_fsm : fsm; (** FSM *)
	ac_funcs : func list; (** function declarations *)
	ac_initializes : action list; (** initialize actions. *)
	ac_inputs : var_info list; (** actor input ports declarations. *)
	ac_name : string; (** actor name. *)
	ac_outputs : var_info list; (** actor output ports declarations. *)
	ac_parameters : var_info list; (** actor parameters. *)
	ac_procs : proc list; (** procedure declarations *)
	ac_priorities : (Loc.t * tag) list list; (** priorities. *)
	ac_vars : var_info list; (** actor variable declarations. *)
}
