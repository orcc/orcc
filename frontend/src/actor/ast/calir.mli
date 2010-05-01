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

(** IR of CAL: AST + CFG + SSA + constant information. *)

(** Unary operator. *)
type uop =
	| UOpBNot (** bitwise complement operator. *)
	| UOpLNot (** logical not operator. *)
	| UOpMinus (** unary minus operator. *)
	| UOpNbElts (** number of elements (#) operator. *)

(** CAL binary operator. *)
type bop =
	| BOpBAnd (** bitwise and operator. *)
	| BOpBOr (** bitwise or operator. *)
	| BOpBXor (** bitwise xor operator. *)
	| BOpDiv (** division operator. *)
	| BOpDivInt (** integral division operator. *)
	| BOpEQ (** equal comparison operator. *)
	| BOpExp (** exponentiation. *)
	| BOpGE (** greater than or equal operator. *)
	| BOpGT (** greater than operator. *)
	| BOpLAnd (** logical and operator. *)
	| BOpLE (** less than or equal operator. *)
	| BOpLOr (** logical or operator. *)
	| BOpLT (** less than operator. *)
	| BOpMinus (** binary minus operator. *)
	| BOpMod (** modulo operator. *)
	| BOpNE (** different operator. *)
	| BOpPlus (** plus operator. *)
	| BOpShL (** shift left *)
	| BOpShR (** shift right *)
	| BOpTimes (** times operator. *)

type constant =
	| CBool of bool (** boolean constant *)
	| CFloat of float (** float constant *)
	| CInt of int (** integer constant *)
	| CList of constant list  (** list constant *)
	| CStr of string (** string constant *)

(** lattice type *)
type lattice =
	| LattTop (** top level: undetermined *)
	| LattCst of constant (** middle level: constant *)
	| LattBot (** bottom level: not constant *)
		
(** A type definition *)
type type_def =
	| TypeBool
	| TypeFloat
	| TypeInt of int (** int(size=) *)
  | TypeList of type_def * int (** list(type:, size=)*)
	| TypeStr
	| TypeUint of int (** uint(size=) *)
	| TypeUnknown
	| TypeVoid (** void *)

(** the label of an edge has an executable flag and a branch number (possibly
None when there is no control) *)
type label = bool ref * int option

module rec IR :
	sig
		(** expression. *)
		type expr =
			| ExprBOp of Loc.t * expr * bop * expr * type_def (** CAL binary expression. *)
			| ExprBool of Loc.t * bool (** CAL boolean literal. *)
			| ExprFloat of Loc.t * float (** CAL float literal. *)
			| ExprInt of Loc.t * int (** CAL integer literal. *)
			| ExprStr of Loc.t * string (** CAL string literal. *)
			| ExprUOp of Loc.t * uop * expr * type_def (** CAL unary expression. *)
			| ExprVar of Loc.t * var_use (** CAL variable expression: location, variable. *)
		
		(** Variable use. *)
		and var_use = {
			mutable vu_def: var_def; (** the variable definition. *)
			mutable vu_node: node; (** node where the variable is used. *)
		}
		
		(** Variable definition. *)
		and var_def = {
			mutable v_assignable : bool; (** whether the variable is assignable. *)
			mutable v_global : bool; (** if the variable is global. *)
			mutable v_index: int; (** if the variable is local, index for SSA. Meaningless otherwise. *)
			mutable v_node: node; (** the node where the variable is assigned. *)
			mutable v_loc: Loc.t; (** location of variable. *)
			mutable v_name : string; (** name of variable. *)
			mutable v_refs : var_use list; (** references to this variable. *)
			mutable v_suffix : int option; (** when local variables have the same name but
		different scopes. *)
			mutable v_type : type_def; (** type of variable. *)
		}

		(** the kind of a node of the program flow graph *)
		and kind =
			| AssignPhi of var_def * bool ref array * var_use array * var_def * node
(** phi assignment: var, executable flags, arguments, old, [node] is the [Join] node this
[AssignPhi] belongs to. *)
			| AssignVar of var_def ref * expr
(** variable instruction: variable, expression. A reference is used so we can rename when transforming
to SSA. *)
			| Call of var_def ref option * proc * expr list
(** call instruction: optional variable reference, procedure, argument list. *)
			| Empty
(** a node that does not do anything: Entry, Exit *)
			| If of expr * node * node * node
(** if statement: test expression, first node of "then", first node of "else",
first node of "join". *)
			| Join of bool ref array * node list (** a join node: executable list, phi-assignment list. *)
			| Load of var_def ref * var_use * expr list
(** [Load (target, source variable, indexes)]
loads a variable from memory (possibly with indexes) and places it into a variable. *)
			| Return of expr
(** return statement: expression returned. Only accepted as the last node of a procedure. *)
			| Store of var_use * expr list * expr
(** [Store (memory, value)] stores a value into memory. *)
			| While of expr * node * node
(** [While (cond, bt, be)]: bt "branch then", be "branch else". *)

			| HasTokens of var_def ref * string * int
(** [HasTokens (result, fifo, num_tokens)] returns true if [fifo] has [num_tokens]. *)
			| Peek of string * int * var_def
(** [Peek (fifo, num_tokens, var)] peeks [num_tokens] from [fifo] and put them
in [var]. *)
			| Read of string * int * var_def
(** [Read (fifo, num_tokens, var)] reads [num_tokens] from [fifo] and put them
in [var]. *)
			| Write of string * int * var_def
(** [Write (fifo, num_tokens, var)] writes [num_tokens] from [var] and put them
in [fifo]. *)
		
		(** a node of the program flow graph *)
		and node = {
			mutable n_id: int; (** node ID. Used by comparison functions in CFG *)
			mutable n_kind: kind;
			mutable n_latt: lattice;
			mutable n_loc: Loc.t;
		}
		
		(** A procedure declaration *)
		and proc = {
			mutable p_cfg: CFG.t;
			mutable p_decls: var_def list;
			mutable p_entry: node; (** entry CFG node of the procedure. *)
			mutable p_exit: node; (** exit CFG node of the procedure. *)
			mutable p_external: bool; (** if the procedure is external. *)
			mutable p_loc: Loc.t;
			mutable p_name: string;
			mutable p_params: var_def list;
			mutable p_return: type_def;
		}
	
	end

and CFG : Graph.Sig.I with
	(** a vertex in the CFG is a simple node. This simplifies the implementation
of the constant propagation algorithm. *)
	type V.t = IR.node and
	type E.label = label and
	type E.t = IR.node * label * IR.node

val dummy_loc : Loc.t
val dummy_node : IR.node

(** An action tag is a list of strings. *)
type tag = string list

(** CAL action. *)
type action = {
	mutable a_body : IR.proc; (** the procedure that contains the action body. *)
	mutable a_ip : (IR.var_def * int) list; (** input pattern *)
	mutable a_op : (IR.var_def * int) list; (** output pattern *)
	mutable a_tag : tag; (** action tag. Two elements are separated
	by a dot in the original source file. *)
	mutable a_sched : IR.proc; (** the procedure that contains the action schedulability test. *)
}

(** Variable definition. *)
type globvar_def = {
	mutable g_def : IR.var_def; (** the variable definition *)
	mutable g_init : constant option; (** the variable value if present *)
}

type fsm = {
	mutable f_states : string list; (** states, sorted by alphabetical order. *)
	mutable f_init : string; (** initial state. *)
	mutable f_transitions : (string * (action * string) list) list;
(** transitions list: for each state,
list of action and destination state if action is fired,
sorted by decreasing priority. *)
}

(** schedule information *)
type schedinfo = {
	mutable si_actions : action list;
(** a list of actions ordered by decreasing priority *)
	mutable si_fsm : fsm option; (** FSM. *)
}

(** actor.*)
type actor = {
	mutable ac_actions : action list; (** actor actions. *)
	ac_file : string; (** absolute file name this actor came from. *)
	mutable ac_initializes : action list; (** initialize actions. *)
	mutable ac_inputs : IR.var_def list; (** actor input ports declarations. *)
	mutable ac_name : string; (** actor name. *)
	mutable ac_outputs : IR.var_def list; (** actor output ports declarations. *)
	mutable ac_parameters : IR.var_def list;
	mutable ac_procs : IR.proc list; (** procedures. *)
	mutable ac_sched : schedinfo; (** schedule information *)
	mutable ac_vars : globvar_def list; (** actor variable declarations. *)
}

(** [reset_id ()] resets the node id counter to 0. Should be called before
an actor is converted from AST to IR. *)
val reset_id : unit -> unit

(** [mk_header_if ()] returns (bt, be, e1, e2, join). *)
val mk_header_if : unit ->
	(IR.node * IR.node * bool ref * bool ref * IR.node) 

(** [mk_links_if_start graph if_node bt be]. *)
val mk_links_if_start : CFG.t -> IR.node -> IR.node -> IR.node -> unit

(** [mk_links_if_end graph bt_last e1 be_last e2 join]. *)
val mk_links_if_end: CFG.t -> IR.node -> bool ref -> IR.node -> bool ref -> IR.node -> unit

(** [mk_pfg_node kind] creates a [Calir.pfg_node] from a
[Calir.pfg_kind] with a dummy location. *)
val mk_node : IR.kind -> IR.node

(** [mk_pfg_node loc kind] creates a [Calir.pfg_node] from a
[Calir.pfg_kind] and a location. *)
val mk_node_loc : Loc.t -> IR.kind -> IR.node

(** [mk_proc graph vars entry exit extern loc name params return] *)
val mk_proc : CFG.t -> IR.var_def list -> IR.node ->
	IR.node -> bool -> Loc.t -> string -> IR.var_def list ->
	type_def -> IR.proc

(** [mk_var_def assignable global loc name vtype] *)
val mk_var_def : bool -> bool -> Loc.t -> string -> ?suffix:int -> type_def ->
	IR.var_def

(** [mk_var_use def] creates a [Calir.var_use] from a [Calir.var_def]. The "node"
field of the var_use created points to dummy_pfg_node. *)
val mk_var_use : IR.var_def -> IR.var_use

(** [mk_var_use_node node def] creates a [Calir.var_use] from a [Calir.var_def] and updates
the reference list of [def]. *)
val mk_var_use_node : IR.node -> IR.var_def -> IR.var_use

(** [mk_while graph node loc expr bt last_child e1 e2] *)
val mk_while : CFG.t -> IR.node ->
	Loc.t -> IR.expr -> IR.node -> IR.node ->
	bool ref -> bool ref ->
	IR.node * IR.node

val name_of_tag : string list -> string

val string_of_lattice : lattice -> string
val string_of_var : IR.var_def -> string

module Iterators :
  sig
		val map_expr : (IR.expr -> IR.expr) -> IR.expr -> IR.expr
		val iter_expr : (IR.expr -> unit) -> IR.expr -> unit
		
		(** [fold_cfg f graph node join x]. [f] has type [node -> join -> x]. *)
		val fold_cfg :
			(IR.node -> IR.node -> 'a -> 'a) -> 
			CFG.t -> IR.node -> IR.node -> 'a -> 'a
		
		(** [iter_cfg f graph node join]. [f] has type [node -> join]. *)
		val iter_cfg :
			(IR.node -> IR.node -> unit) -> 
			CFG.t -> IR.node -> IR.node -> unit

		(** [iter_actor_proc f actor]. [f] has type [proc -> unit]. *)
    val iter_actor_proc :
			(IR.proc -> unit) ->
			actor -> unit
  end

(** [replace_var_use var_use node def] removes [var_use] from the list
[var_use.Calir.v_def.Calir.v_refs] and adds it to the reference list
of the given [Calir.var_def]. *)
val replace_var_use : IR.var_use -> IR.node -> IR.var_def -> unit

val add_join_node : IR.node -> IR.node -> unit

(** [get_join_nodes join] returns the executable flags from [join].
Fails if [join] is not a [Join]. *)
val get_join_flags : IR.node -> bool ref array

(** [get_join_nodes join] returns the nodes from [join].
Fails if [join] is not a [Join]. *)
val get_join_nodes : IR.node -> IR.node list

val set_join_nodes : IR.node -> IR.node list -> unit
