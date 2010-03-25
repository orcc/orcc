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

(** Network types *)

type port = {
	po_name : string;
	po_type: Calir.type_def;
}

type label = port option * port option * int option

module rec Ast :
	sig
		type contents =
			| Actor
			| Network of network

		and instance = {
			i_class : string;
			i_contents : contents;
			i_loc: Loc.t;
			mutable i_name : string;
			i_params : (string * Calast.expr) list;
		}

		and network = {
			n_graph : G.t;
			n_inputs : port Asthelper.SH.t;
			n_instances : instance Asthelper.SH.t;
			n_name : string;
			n_outputs : port Asthelper.SH.t
		}
		
		and vertex =
			| Instance of instance
			| Input of port
			| Output of port
	end = struct
		type contents =
			| Actor
			| Network of network

		and instance = {
			i_class : string;
			i_contents : contents;
			i_loc: Loc.t;
			mutable i_name : string;
			i_params : (string * Calast.expr) list;
		}

		and network = {
			n_graph : G.t;
			n_inputs : port Asthelper.SH.t;
			n_instances : instance Asthelper.SH.t;
			n_name : string;
			n_outputs : port Asthelper.SH.t
		}
		
		and vertex =
			| Instance of instance
			| Input of port
			| Output of port
	end

and G : Graph.Sig.I with
	type V.t = Ast.vertex and
	type E.label = label and
	type E.t = Ast.vertex * label * Ast.vertex =
		Graph.Imperative.Digraph.ConcreteLabeled
			(struct
				type t = Ast.vertex
				let compare (a : t) (b : t) =
					match (a, b) with
						| (Ast.Instance i1, Ast.Instance i2) ->
							String.compare i1.Ast.i_name i2.Ast.i_name
						| (Ast.Input v1, Ast.Input v2)
						| (Ast.Output v1, Ast.Output v2) -> String.compare v1.po_name v2.po_name
						| _ -> Pervasives.compare a b

				let equal (a : t) (b : t) = (compare a b = 0)

				let hash (v : t) =
					match v with
						| Ast.Instance i -> Hashtbl.hash i.Ast.i_name
						| Ast.Input port
						| Ast.Output port -> Hashtbl.hash port.po_name
			end)

			(struct
				type t = label
				let compare (a : t) (b : t) = compare a b
				let default = (None, None, None)
			end)
