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
open Options
open Ast2ir_util

module SM = Asthelper.SM
module SS = Asthelper.SS

(** a tag hash table *)
module TH =
	Hashtbl.Make
		(struct
			type t = tag
			let equal (a : tag) (b : tag) = (a = b)
			let hash (a : tag) = Hashtbl.hash a
		end)

(** a tag set *)
module AS =
	Set.Make
		(struct
			type t = action
			let compare (a : action) (b : action) = compare a.a_tag b.a_tag
		end)

module V =
	struct
		type t = action
		let equal (a : action) (b : action) = (a.a_tag = b.a_tag)
		let hash (a : action) = Hashtbl.hash a.a_tag
		let compare (a : action) (b : action) = compare a.a_tag b.a_tag
	end

module G = Graph.Imperative.Digraph.Concrete(V)
module T = Graph.Topological.Make(G)
module C = Graph.Components.Make(G)

module Display = struct
  include G

	open Graph.Graphviz.DotAttributes

  let default_edge_attributes _ = []
  let default_vertex_attributes _ = []
  let edge_attributes _ = []
	let get_subgraph _ = None
	let graph_attributes _ = []

	let label action = name_of_tag action.a_tag
  let vertex_attributes action =
		let label = String.escaped (name_of_tag action.a_tag) in
		[ `Fontname "Courier"; `Fontsize 8; `Label label; `Shape `Box ]
  let vertex_name action = name_of_tag action.a_tag
end

module DotOutput = Graph.Graphviz.Dot(Display)

let print_cfg file graph =
	let oc = open_out (file ^ ".dot") in
  DotOutput.output_graph oc graph;
	close_out oc;
	
	let res =
		Sys.command
			(sprintf "dot -Tpng -o%s %s" (file ^ ".png") (file ^ ".dot"))
	in
	if res <> 0 then
		fprintf stderr
			"Could not execute dot to create a picture for the CFG of %s\n" file

(** [add_actions ht_tags graph tag_from tag_to] adds edges in [graph] between
actions associated with [tag_from] and actions associated with [tag_to]. This
information is contained in the [ht_tags] hash table. *)
let add_actions ht_tags graph (loc_from, tag_from) (loc_to, tag_to) =
	let actions_from = TH.find_all ht_tags tag_from in
	(match actions_from with
		| [] ->
			Asthelper.failwith loc_from
				(sprintf "No actions are associated with the tag \"%s\""
					(name_of_tag tag_from))
		| _ -> ());
	let actions_to = TH.find_all ht_tags tag_to in
	(match actions_to with
		| [] ->
			Asthelper.failwith loc_to
				(sprintf "No actions are associated with the tag \"%s\""
					(name_of_tag tag_to))
		| _ -> ());

	List.iter
		(fun a_from ->
			List.iter
				(fun a_to -> G.add_edge graph a_from a_to)
			actions_to)
	actions_from
	
(** [sort_by_priority options out_base ht_tags named priorities] creates a graph from [priorities]
 as follows: a > b > ... > z becomes a -> b -> ... -> z.
After that, we simply iterate through the graph using a topological order
to get the order in which actions should be scheduled.
The action hash table [ht_tags] is used to get the action
 associated with the tag. *)
let get_priority_graph options ac_name out_base ht_tags named priorities =
	let graph = G.create () in
	List.iter
		(fun priority ->
			match priority with
			| [] -> failwith "Empty priority list"
			| h :: t ->
				ignore
					(List.fold_left
						(fun tag_from tag_to ->
							add_actions ht_tags graph tag_from tag_to;
							tag_to)
					h t))
	priorities;
	
	(* add all actions from named. Because some actions in an actor may not be *)
	(* constrained by priorities, but they must be present in the *)
	(* schedule. CAL does not specify whether they should come before or *)
	(* after other actions though. In our case they will come before because *)
	(* of the topological order. *)
	List.iter (G.add_vertex graph) named;
	
	if get_dot_prio options ac_name then print_cfg (out_base ^ "_priorities") graph;

	graph

(** [apply_priority_inside ac_name ht_actions ht_tags actions (init, transitions)]
walks through transitions, and transform them from (s_from, tag, s_to) to
(s_from, [(action1, s_to); (action2, s_to)]). Then each list is ordered
by priority. Finally, gensched_fsm is called to generate a schedule
for each list. *)
let apply_priority_inside ht_actions ht_tags actions (init, transitions) =
	(* we use a set so that states are listed in the lexical order, and *)
	(* a map so the transitions are listed by lexical order on the "from" *)
	(* state. *)
	let (actions, states, sm_transitions) =
		List.fold_left
			(fun (actions, states, sm_transitions) (s_from, tag, s_to) ->
				(* retrieve the actions associated with "tag" *)
				let (actions, transitions) =
					List.fold_left
						(fun (actions, transitions) action ->
							(AS.remove action actions, (action, s_to) :: transitions))
						(actions, []) (TH.find_all ht_tags tag)
				in

				(* append those actions to the existing actions. *)
				let transitions =
					try
						SM.find s_from sm_transitions @ transitions
					with Not_found -> transitions
				in
				
				let states = SS.add s_from (SS.add s_to states) in
				(actions, states, SM.add s_from transitions sm_transitions))
		(actions, SS.empty, SM.empty) transitions
	in

	(* converts the map to a list, and sorts the actions according to *)
	(* the priority that they were assigned earlier. *)
	let transitions =
		SM.fold
			(fun s_from to_list transitions ->
				let to_list =
					List.sort
						(fun (a1, _) (a2, _) ->
							compare (TH.find ht_actions a1.a_tag) (TH.find ht_actions a2.a_tag))
					to_list
				in
				(s_from, to_list) :: transitions)
		sm_transitions []
	in

	let fsm =
		{ f_init = init;
			f_states = SS.elements states;
			f_transitions = List.rev transitions }
	in
	(AS.elements actions, Some fsm)

(** [gen_sched_info options ac_name out_base actions priorities fsm]
orders actions according to the
 following criteria: untagged actions come first, then actions not
 concerned by priorities, and finally other actions sorted by priorities. *)
let gen_sched_info options ac_name out_base actions priorities fsm =
	(* an entry "t" in the ht_tags table contains t_{hat}. This makes *)
	(* the graph construction a lot easier and faster. Values are *)
	(* a tuple (action, int) where the integer is the topological *)
	(* order in the final graph *)
	let ht_tags = TH.create 77 in

	let (anonymous, named) =
		List.fold_left
			(fun (anonymous, named) action ->
				match action.a_tag with
					| [] -> (action :: anonymous, named)
					| h :: t ->
						(* for a tag a.b.c, add "action" to entry a. The reason for doing *)
						(* this particular case is that most actions' tag are composed *)
						(* of a single identifier. *)
						TH.add ht_tags [h] action;

						(* for a tag a.b.c, add "action" to entry a.b and a.b.c. *)
						ignore
							(List.fold_left
								(fun tag id ->
									let tag = tag @ [id] in
									TH.add ht_tags tag action;
									tag)
							[h] t);

						(anonymous, action :: named))
		([], []) actions
	in

	let graph = get_priority_graph options ac_name out_base ht_tags named priorities in
	let (prioritized, fsm) =
		match fsm with
			| None ->
				let actions =
					List.rev
						(T.fold
							(fun action actions -> action :: actions)
						graph [])
				in
				(actions, None)
				
			| Some fsm ->
				(* this hash table contains the order associated with each action *)
				let ht_actions = TH.create 77 in

				(* we associate an order with each order, and at the same time generate *)
				(* a set of all actions. This is useful so we know which actions must *)
				(* be executed out of the FSM. *)
				let (_, actions) =
					T.fold
						(fun action (i, actions) ->
							TH.replace ht_actions action.a_tag i;
							(i + 1, AS.add action actions))
						graph (0, AS.empty)
				in
				apply_priority_inside ht_actions ht_tags actions fsm
	in
	{ si_actions = List.rev_append anonymous prioritized;
	si_fsm = fsm }
