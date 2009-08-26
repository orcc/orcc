%{
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

open Lexing
open Printf

let time = ref 0.

let dummy_loc = Calir.dummy_loc

(** [convert_loc start stop] returns a [Loc.t] from two [Lexing.position]s. *)
let convert_loc start stop =
	{
		Loc.file_name = start.pos_fname;
		Loc.start =
			{Loc.line = start.pos_lnum; Loc.bol = start.pos_bol; Loc.off = start.pos_cnum };
		Loc.stop =
			{Loc.line = stop.pos_lnum; Loc.bol = stop.pos_bol; Loc.off = stop.pos_cnum };
	}

let loc () =
	convert_loc (Parsing.symbol_start_pos ()) (Parsing.symbol_end_pos ())

(** Defines different kinds of actor declarations. *)
type actor_decl =
	| Action of Calast.action (** An action of type [Calast.action]. *)
	| FuncDecl of Calast.func (** A function declaration at the actor level. *)
	| Initialize of Calast.action (** An initialization action of type [Calast.action]. *) 
	| PriorityOrder of (Loc.t * Calast.tag) list list (** An actor declaration of type priority order. *)
	| ProcDecl of Calast.proc (** A procedure declaration at the actor level. *)
	| VarDecl of Calast.var_info (** A variable declaration at the actor level. *)

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)

(** [bop loc e1 op e2] returns [Calast.ExprBOp (loc, e1, op, e2)] *)
let bop loc e1 op e2 = Calast.ExprBOp (loc, e1, op, e2)

(** [uop loc e1 op e2] returns [Calast.ExprUOp (loc, e, op)] *)
let uop loc op e = Calast.ExprUOp (loc, op, e)

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* Type definitions *)

(** Defines different kinds of type attributes. *)
type type_attr =
	| ExprAttr of Calast.expr (** A type attribute that references an expression. *)
	| TypeAttr of Calast.type_def (** A type attribute that references a type. *)

(** [find_size loc typeAttrs] attemps to find a [type_attr] named ["size"]
 that is an [ExprAttr]. The function returns a [Calast.expr]. *)
let find_size loc typeAttrs =
	let attr = 
		List.assoc "size" typeAttrs
	in
  match attr with
		| ExprAttr e -> e
		| _ ->
		  Asthelper.failwith loc "size must be an expression!"

(** [find_type loc typeAttrs] attemps to find a [type_attr] named ["type"]
 that is an [TypeAttr]. The function returns a [Calast.type_def]. *)
let find_type loc typeAttrs =
	let attr = 
		List.assoc "type" typeAttrs
	in
  match attr with
		| TypeAttr t -> t
		| _ -> Asthelper.failwith loc "type must be a type!"

(** [find_size_or_default loc typeAttrs] attemps to find a [type_attr]
 named ["size"] that is an [ExprAttr]. If not found, the function returns the
 default size given as an [int]. *)
let find_size_or_default loc typeAttrs default =
	(* size in bits *)
	try
		find_size loc typeAttrs
	with Not_found ->
  	(* no size information found, assuming "default" bits. *)
  	Calast.ExprInt (loc, default)

(** [type_of_typeDef loc name typeAttrs] returns a [Calast.type_def] from a
 name and type attributes that were parsed. *)
let type_of_typeDef loc name typeAttrs =
	match name with
		| "bool" -> Calast.TypeBool
		| "int" -> Calast.TypeInt (find_size_or_default loc typeAttrs 32)
		| "list" ->
			Asthelper.failwith loc
				"The type \"list\" is deprecated. Please use \"List\"."
		| "List" ->
			(* get a type *)
			let t =
				try
					find_type loc typeAttrs
			  with Not_found ->
			    Asthelper.failwith loc
						"RVC-CAL requires that all lists have a type."
			in
			
			(* and a size in number of elements *)
			let size =
				try
					find_size loc typeAttrs
				with Not_found ->
			    Asthelper.failwith loc
						"RVC-CAL requires that all lists have a size."
			in
			Calast.TypeList (t, size)
    | "string" ->
			Asthelper.failwith loc
				"The type \"string\" is deprecated. Please use \"String\"."
		| "String" -> Calast.TypeStr
		| "uint" -> Calast.TypeUint (find_size_or_default loc typeAttrs 32)
		| t ->
			Asthelper.failwith loc ("Unsupported type \"" ^ t ^ "\"")

(*****************************************************************************)
(*****************************************************************************)
(*****************************************************************************)
(* Actor definitions. *)

let get_something p l =
	let (yes, no) =
		List.fold_left
			(fun (yes, no) x ->
				match p x with
					| None -> (yes, x :: no)
					| Some x -> (x :: yes, no))
		([], []) l
	in
	(List.rev yes, List.rev no)

(** [get_actions declarations] returns a tuple [(actions, declarations)] where
 actions is a list of actions and declarations the remaining declarations. *)
let get_actions declarations =
	get_something (function Action a -> Some a | _ -> None) declarations

(** [get_funcs declarations] returns a tuple [(funcs, declarations)] where
 funcs is a list of function declarations and [declarations] the
 remaining declarations. *)
let get_funcs declarations =
	get_something (function FuncDecl f -> Some f | _ -> None) declarations

(** [get_priorities declarations] returns a tuple [(priorities, declarations)] where
 priorities is a list of priorities and declarations the remaining declarations. *)
let get_priorities declarations =
	let (priorities, declarations) =
		get_something (function PriorityOrder p -> Some p | _ -> None) declarations
	in
	let priorities = List.flatten priorities in
	(priorities, declarations)

(** [get_funcs declarations] returns a tuple [(funcs, declarations)] where
 funcs is a list of function declarations and [declarations] the
 remaining declarations. *)
let get_procs declarations =
	get_something (function ProcDecl p -> Some p | _ -> None) declarations

(** [get_initializes declarations] returns a tuple [(initializes, declarations)]
 where initializes is a list of initialize and declarations the remaining
 declarations. *)
let get_initializes declarations =
	get_something (function Initialize i -> Some i | _ -> None) declarations

(** [get_vars declarations] returns a tuple [(vars, declarations)] where
 vars is a list of local variable declarations and [declarations] the
 remaining declarations. *)
let get_vars declarations =
	get_something (function VarDecl v -> Some v | _ -> None) declarations

let var assignable global loc name t v =
	{ Calast.v_assignable = assignable;
		v_global = global;
		v_loc = loc;
		v_name = name;
		v_type = t;
		v_value = v }
%}

/*****************************************************************************/
/*****************************************************************************/
/*****************************************************************************/

/* keywords */
%token ACTION
%token ACTOR
%token ALL
%token AND
%token ANY
%token AT
%token AT_STAR
%token BEGIN
%token CHOOSE
%token DELAY
%token DIV
%token DO
%token ELSE
%token END
%token FALSE
%token FOR
%token FOREACH
%token FSM
%token FUNCTION
%token GUARD
%token IF
%token IN
%token IMPORT
%token INITIALIZE
%token MOD
%token MULTI
%token NOT
%token OR
%token PRIORITY
%token PROCEDURE
%token REGEXP
%token REPEAT
%token SCHEDULE
%token THEN
%token TRUE
%token VAR
%token WHILE

/* tokens */
%token ARROW
%token BITAND
%token BITOR
%token COLON
%token COLON_EQUAL
%token COMMA
%token DIV
%token DOT
%token DOUBLE_DASH_ARROW
%token DOUBLE_EQUAL_ARROW
%token EQ
%token EXP
%token GE
%token GT
%token LBRACE
%token LBRACKET
%token LE
%token LT
%token LPAREN
%token MINUS
%token NE
%token PLUS
%token RBRACE
%token RBRACKET
%token RPAREN
%token SEMICOLON
%token SHARP
%token SHIFT_LEFT
%token SHIFT_RIGHT
%token TIMES

/* parametric and special tokens */
%token <int> INT
%token <float> FLOAT
%token <string> IDENT
%token <string> STRING
%token EOF

/* From low to high precedence */
%left OR
%left AND
%left BITOR
%left BITAND
%left EQ NE
%left LT LE GT GE
%left SHIFT_LEFT SHIFT_RIGHT
%left PLUS MINUS
%left DIV MOD TIMES SLASH
%right EXP
%nonassoc NOT SHARP UMINUS

/* Start rules */
%start actor
%type <Calast.actor> actor

%start expression
%type <Calast.expr> expression

%%

action:
	actionTagOpt ACTION actionInputsOpt DOUBLE_EQUAL_ARROW actionOutputsOpt
	actionGuards actionDelayOpt varDeclsOpt actionStatements END
		{
			{ Calast.a_guards = $6;
			a_inputs = $3;
			a_loc = loc ();
			a_outputs = $5;
			a_stmts = $9;
			a_tag = $1;
			a_vars = $8 }
		}

actionChannelSelectorOpt:
	{ () }
| actionChannelSelectorNames
		{Asthelper.failwith (loc ()) "RVC-CAL does not support channel selectors." }
	
actionChannelSelectorNames:
	ALL { () }
| ANY { () }
| AT { () }
| AT_STAR { () }

actionDelayOpt:
	{ () }
| DELAY expression
		{ Asthelper.failwith (loc ()) "RVC-CAL does not permit the use of delay." }

actionGuards:
	{ [] }
| GUARD expressions { List.rev $2 }

actionInput:
	actionPortOpt LBRACKET identsOpt RBRACKET actionRepeatOpt actionChannelSelectorOpt
		{ let (_, port) = $1 in
			(port, List.rev $3, $5) }

actionInputs:
	actionInput { [$1] }
| actionInputs COMMA actionInput { $3 :: $1 }

actionInputsOpt:
	{ [] }
| actionInputs { List.rev $1 }

actionOutput:
	actionPortOpt LBRACKET expressions RBRACKET actionRepeatOpt actionChannelSelectorOpt
		{ let (_, port) = $1 in
			(port, List.rev $3, $5) }

actionOutputs:
	actionOutput { [$1] }
| actionOutputs COMMA actionOutput { $3 :: $1 }

actionOutputsOpt:
	{ [] }
| actionOutputs { List.rev $1 }

actionPortOpt:
	{ (dummy_loc, "") }
| ident COLON { $1 }

actionRepeatOpt:
	{ Calast.ExprInt (loc (), 1) }
| REPEAT expression { $2 }

actionStatements:
	{ [] }
| DO statements { List.rev $2 }

actionTag: qualifiedIdent { (loc (), List.rev $1) }

actionTagOpt:
	{ [] }
| actionTag COLON { snd $1 }

/***************************************************************************/
/* a CAL actor. */
actor:
	actorImportsOpt ACTOR ident typeParsOpt LPAREN actorParametersOpt RPAREN
	actorPortDeclsOpt DOUBLE_EQUAL_ARROW actorPortDeclsOpt COLON
	actorDeclarationsOpt
	END EOF
	{
		let (_, name) = $3 in

		let (declarations, schedule) = $12 in
		let (actions, declarations) = get_actions declarations in
		let (funcs, declarations) = get_funcs declarations in
		let (priorities, declarations) = get_priorities declarations in
		let (procs, declarations) = get_procs declarations in
		let (vars, declarations) = get_vars declarations in
		let (initializes, declarations) = get_initializes declarations in
		assert (declarations = []);
		
		{ Calast.ac_actions = actions;
		ac_file = ""; (* this is filled later by parse_actor *)
	  ac_fsm = schedule;
		ac_funcs = funcs;
	  ac_inputs = $8;
		ac_initializes = initializes;
	  ac_name = name;
	  ac_outputs = $10;
	  ac_parameters = $6;
	  ac_priorities = priorities;
	  ac_procs = procs;
	  ac_vars = vars }
	}

/*****************************************************************************/
/* actor declarations */

/*actorActionOrInit: [
		[ "action"; a = action -> Action a
		| "initialize"; i = initializationAction -> Initialization i ]
	];*/

actorDeclaration:
	action { Action $1 }
	
| initialize { Initialize $1 }

| priorityOrder { PriorityOrder $1 }

| varDecl SEMICOLON { VarDecl {$1 with Calast.v_global = true} }

| FUNCTION ident LPAREN varDeclFunctionParamsOpt RPAREN DOUBLE_DASH_ARROW
		typeDef varDeclsOpt COLON expression END
	{ let (_, name) = $2 in
		FuncDecl {
			Calast.f_decls = $8;
			f_expr = $10;
			f_loc = loc ();
			f_name = name;
			f_params = $4;
			f_return = $7;
		} }

| PROCEDURE ident LPAREN varDeclFunctionParamsOpt RPAREN varDeclsOpt
	BEGIN statements END
	{ let (_, name) = $2 in
		ProcDecl {
			Calast.p_decls = $6;
			p_loc = loc ();
			p_name = name;
			p_params = $4;
			p_stmts = List.rev $8
		} }

actorDeclarations:
	{ [] }
| actorDeclarations actorDeclaration { $2 :: $1 }

actorDeclarationsOpt:
| actorDeclarations { (List.rev $1, None) }
| actorDeclarations schedule actorDeclarations
	{ (List.append (List.rev $1) (List.rev $3), Some $2) }

/*****************************************************************************/

/* imports (ignored by ORCC) */
actorImport:
	IMPORT ALL qualifiedIdent SEMICOLON { () }
| IMPORT qualifiedIdent SEMICOLON { () }

actorImportsOpt:
	{ () }
| actorImportsOpt actorImport { () }

/*****************************************************************************/
/* actor parameters: type name (= expression)? */
actorParameter:
	typeDef ident actorParameterValueOpt
	{ let (_, name) = $2 in
		var false true (loc ()) name $1 $3 }

actorParameterValueOpt:
	{ None }
| EQ expression { Some $2 }

actorParameters:
	actorParameter { [$1] }
| actorParameters COMMA actorParameter { $3 :: $1 }

actorParametersOpt:
	{ [] }
| actorParameters { List.rev $1 }

/*****************************************************************************/
/* actor ports */

actorPortDecl:
	MULTI typeDef ident
	{ Asthelper.failwith (loc ()) "RVC-CAL does not permit the use of multi ports." }
| typeDef ident
	{ let (_, name) = $2 in
		var false true (loc ()) name $1 None }

actorPortDecls:
	actorPortDecl { [$1] }
| actorPortDecls COMMA actorPortDecl { $3 :: $1 }

actorPortDeclsOpt:
	{ [] }
| actorPortDecls { List.rev $1 }

/*****************************************************************************/
/* expressions */

expression:
  expression OR expression { bop (loc ()) $1 Calast.BOpOr $3 }
	
| expression AND expression { bop (loc ()) $1 Calast.BOpAnd $3 }

| expression BITOR expression { bop (loc ()) $1 Calast.BOpBOr $3 }

| expression BITAND expression { bop (loc ()) $1 Calast.BOpBAnd $3 }
	
| expression EQ expression { bop (loc ()) $1 Calast.BOpEQ $3 }
| expression NE expression { bop (loc ()) $1 Calast.BOpNE $3 }
| expression LT expression { bop (loc ()) $1 Calast.BOpLT $3 }
| expression LE expression { bop (loc ()) $1 Calast.BOpLE $3 }
| expression GT expression { bop (loc ()) $1 Calast.BOpGT $3 }
| expression GE expression { bop (loc ()) $1 Calast.BOpGE $3 }

| expression SHIFT_LEFT expression { bop (loc ()) $1 Calast.BOpShL $3 }
| expression SHIFT_RIGHT expression { bop (loc ()) $1 Calast.BOpShR $3 }

| expression PLUS expression { bop (loc ()) $1 Calast.BOpPlus $3 }
| expression MINUS expression { bop (loc ()) $1 Calast.BOpMinus $3 }

| expression DIV expression { bop (loc ()) $1 Calast.BOpDivInt $3 }
| expression MOD expression { bop (loc ()) $1 Calast.BOpMod $3 }
| expression TIMES expression { bop (loc ()) $1 Calast.BOpTimes $3 }
| expression SLASH expression { bop (loc ()) $1 Calast.BOpDiv $3 }

| expression EXP expression { bop (loc ()) $1 Calast.BOpExp $3 }

| MINUS expression %prec UMINUS { uop (loc ()) Calast.UOpMinus $2 }
| NOT expression { uop (loc ()) Calast.UOpNot $2 }
| SHARP expression { uop (loc ()) Calast.UOpNbElts $2 }

| LBRACKET expressions expressionGeneratorsOpt RBRACKET
	{ Calast.ExprList (loc (), List.rev $2, $3) }

| IF expression THEN expression ELSE expression END
	{ Calast.ExprIf (loc (), $2, $4, $6) }

/* simple */
| LPAREN expression RPAREN { $2 }
| TRUE { Calast.ExprBool (loc (), true) }
| FALSE { Calast.ExprBool (loc (), false) }
| INT { Calast.ExprInt (loc (), $1) }
| STRING { Calast.ExprStr (loc (), $1) }
| ident LPAREN expressionsOpt RPAREN
	{ let (_, name) = $1 in
	Calast.ExprCall (loc (), name, $3) }

/* Was changed for compatibility with OpenDF.
To remove: replace $2 by List.rev $2 */
| ident expressionIndexes
	{ let (loc_ident, name) = $1 in
	Calast.ExprIdx (loc (), (loc_ident, name), $2) }

| ident
	{ let (loc, name) = $1 in
	Calast.ExprVar (loc, name) }

expressionGenerator:
	FOR typeDef ident IN expression
	{ let (loc, name) = $3 in
	let var = var false false loc name $2 None in
	(var, $5) }

expressionGenerators:
	expressionGenerator { [$1] }
| expressionGenerators COMMA expressionGenerator { $3 :: $1 }
	
expressionGeneratorsOpt:
	{ [] }
| COLON expressionGenerators { List.rev $2 }

/* C-like indexes and CAL-like indexes are supported. */
/* Was changed for compatibility with OpenDF.
To remove: replace expressions by expression and List.rev $2 by $2 */
expressionIndex: LBRACKET expressions RBRACKET { List.rev $2 }

/* Was changed for compatibility with OpenDF. */
expressionIndexes:
	expressionIndex { $1 } /* To remove: replace $1 by [$1] */
| expressionIndexes expressionIndex { $1 @ $2 } /* To remove: replace $1 @ $2 by $2 :: $1 */

expressions:
	expression { [$1] }
| expressions COMMA expression { $3 :: $1 }

expressionsOpt:
	{ [] }
| expressions { List.rev $1 }

/*****************************************************************************/
/* identifiers */

ident: IDENT { (loc (), $1) }

idents:
	ident { [$1] }
| idents COMMA ident { $3 :: $1 }

identsOpt:
  { [] }
| idents { $1 }

/*****************************************************************************/
/* initialize */

initialize:
	actionTagOpt INITIALIZE DOUBLE_EQUAL_ARROW actionOutputsOpt
	actionGuards actionDelayOpt varDeclsOpt actionStatements END
		{
			{ Calast.a_guards = $5;
			a_inputs = [];
			a_loc = loc ();
			a_outputs = $4;
			a_stmts = $8;
			a_tag = $1;
			a_vars = $7 }
		}

/*****************************************************************************/
/* priorities */

priorityInequality:
	actionTag GT actionTag { [$3; $1] }
| priorityInequality GT actionTag { $3 :: $1 }

priorityInequalities:
	{ [] }
| priorityInequalities priorityInequality SEMICOLON { (List.rev $2) :: $1 }
	
priorityOrder:
	PRIORITY priorityInequalities END { List.rev $2 }

/*****************************************************************************/
/* qualified ident */

qualifiedIdent:
	ident { [snd $1] }
| qualifiedIdent DOT ident { snd $3 :: $1 }

/*****************************************************************************/
/* schedule */

schedule:
	SCHEDULE FSM ident COLON stateTransitions END
	{ let (_, name) = $3 in
		(name, List.rev $5) }
| SCHEDULE REGEXP
	{ Asthelper.failwith (loc ())
			"RVC-CAL does not support \"regexp\" schedules." }

stateTransition:
	ident LPAREN actionTag RPAREN DOUBLE_DASH_ARROW ident SEMICOLON
	{ let (_, from_state) = $1 in
		let (_, to_state) = $6 in
		let (_, action) = $3 in
		(from_state, action, to_state) }
	
stateTransitions:
	{ [] }
| stateTransitions stateTransition { $2 :: $1 }

/*****************************************************************************/
/* statements */

statement:
	BEGIN varDeclsAndDoOpt statements END
	{ Calast.StmtBlock (loc (), $2, List.rev $3) }
| CHOOSE
	{ Asthelper.failwith (loc ())
			"RVC-CAL does not support the \"choose\" statement." }
| FOR
	{ Asthelper.failwith (loc ())
			"RVC-CAL does not support the \"for\" statement, please use \"foreach\" instead." }
| FOREACH varDeclNoExpr IN expression varDeclsOpt DO statements END
	{ Calast.StmtForeach (loc (), $2, $4, $5, List.rev $7) }
| FOREACH varDeclNoExpr IN expression DOT DOT expression
	{ Asthelper.failwith (loc ())
			"RVC-CAL does not support the \"..\" construct, please use \"Integers\" instead." }
| IF expression THEN statements statementIfElseOpt END
	{ Calast.StmtIf (loc (), $2, List.rev $4, $5) }
| WHILE expression varDeclsOpt DO statements END
	{ Calast.StmtWhile (loc (), $2, $3, List.rev $5) }

/* Was changed for compatibility with OpenDF.
To remove: replace $2 by List.rev $2 */
| ident expressionIndexes COLON_EQUAL expression SEMICOLON
	{ Calast.StmtInstr (loc (),
			[Calast.InstrAssignArray (loc (), $1, $2, $4)]) }

| ident COLON_EQUAL expression SEMICOLON
	{ Calast.StmtInstr (loc (), [Calast.InstrAssignVar (loc (), $1, $3)]) }

| ident LPAREN expressionsOpt RPAREN SEMICOLON
	{ let (_, name) = $1 in
		Calast.StmtInstr (loc (), [Calast.InstrCall (loc (), name, $3)]) }
	
statementIfElseOpt:
	{ [] }
| ELSE statements { List.rev $2 }

statements:
	{ [] }
| statements statement { $2 :: $1 }

/*****************************************************************************/
/* type attributes and definitions */

/* a type attribute, such as "type:" and "size=" */
typeAttr:
	ident COLON typeDef
	{ let (_, name) = $1 in
		(name, TypeAttr $3) }
| ident EQ expression
	{ let (_, name) = $1 in
		(name, ExprAttr $3) }

typeAttrs:
	typeAttr { [$1] }
| typeAttrs COMMA typeAttr { $3 :: $1 }

/* a type definition: bool, int(size=5), list(type:int, size=10)... */	
typeDef:
	ident
	{ let (_, name) = $1 in
		type_of_typeDef (loc ()) name [] }
| ident LBRACKET typePars RBRACKET 
	{ Asthelper.failwith (loc ()) "RVC-CAL does not support type parameters." }
| ident LPAREN typeAttrs RPAREN
	{ let (_, name) = $1 in
		type_of_typeDef (loc ()) name (List.rev $3) }

/*****************************************************************************/
/* type parameters */

typePar:
	ident { () } 
| ident LT typeDef { () }

typePars:
	typePar { () }
| typePars typePar { () }

typeParsOpt:
	{ () }
| LBRACKET typePars RBRACKET
	{ Asthelper.failwith (loc ()) "RVC-CAL does not support type parameters." }

/*****************************************************************************/
/* variable declarations */

/* simple variable declarations */
varDecl:
	typeDef ident EQ expression
	{ let (loc, name) = $2 in
	var false false loc name $1 (Some $4) }
| typeDef ident COLON_EQUAL expression
	{ let (loc, name) = $2 in
	var true false loc name $1 (Some $4) }
| typeDef ident
	{ let (loc, name) = $2 in
	var true false loc name $1 None }

varDeclFunctionParam:
	typeDef ident
	{ let (loc, name) = $2 in
	var true false loc name $1 None }

varDeclFunctionParams:
	varDeclFunctionParam { [$1] }
| varDeclFunctionParams COMMA varDeclFunctionParam { $3 :: $1 }

varDeclFunctionParamsOpt:
	{ [] }
| varDeclFunctionParams { List.rev $1 }

varDeclNoExpr:
	typeDef ident
	{ let (loc, name) = $2 in
	var false false loc name $1 None }
	
varDecls:
	varDecl { [$1] }
| varDecls COMMA varDecl { $3 :: $1 }

varDeclsAndDoOpt:
	{ [] }
| VAR varDecls DO { List.rev $2 }

varDeclsOpt:
	{ [] }
| VAR varDecls { List.rev $2 }

%%
