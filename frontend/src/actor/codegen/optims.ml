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

let time = ref 0.

let rec remove_dead actor_name graph decls =
	let (changed, decls) =
		List.fold_left
			(fun (changed, decls) vd ->
				match (vd.v_refs, vd.v_node.n_kind) with
					| ([], Call (Some _, proc, params)) ->
						(* the variable is not used, and the RHS instruction is a call. *)
						(* changes Call (Some _) to Call (None). Do NOT remove it because it might *)
						(* have side-effects. *)
						vd.v_node.n_kind <- Call (None, proc, params);
						
						(* removes the variable, but not the node. *)
						(true, decls)

					| ([], _) ->
						(* the variable is not used, and the RHS instruction is not a call. *)
						(* removes the node, and the variable. *)
						remove_node graph vd.v_node;
						(true, decls)

					| _ ->
						(* the variable is used, do not remove it. *)
						(changed, vd :: decls))
		(false, []) decls
	in
	let decls = List.rev decls in
	if changed then
		remove_dead actor_name graph decls
	else
		decls

let remove_dead_stores actor =
	let t1 = Sys.time () in
	Iterators.iter_actor_proc
		(fun proc ->
			proc.p_decls <- remove_dead actor.ac_name proc.p_cfg proc.p_decls)
		actor;
	let t2 = Sys.time () in
	time := !time +. (t2 -. t1)
