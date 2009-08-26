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

(** A port. *)
type port = {
	po_name : string; (** port name. *)
	po_type: Calir.type_def; (** port type. *)
}

(** The label of an edge of the network graph: two port references, and optional size. *)
type label = port option * port option * int option

module rec Ast :
	sig

		(** XDF instance *)
		type contents =
			| Actor of Calir.actor
			| Network of network
			
		and instance = {
			i_class : string; (** file name. *)
			i_contents : contents; (** contents of the instance. *)
			i_loc: Loc.t;
			mutable i_name : string; (** name. *)
			i_params : (string * Calast.expr) list; (** parameters. *)
		}

		(** XDF network. *)
		and network = {
			n_graph : G.t;
			n_inputs : port Asthelper.SH.t;
			n_instances : instance Asthelper.SH.t;
			n_name : string; (** network name. *)
			n_outputs : port Asthelper.SH.t
		}
		
		(** XDF vertex *)
		and vertex =
			| Instance of instance
			| Input of port
			| Output of port
	end

and G : Graph.Sig.I with
	type V.t = Ast.vertex and
	type E.label = label and
	type E.t = Ast.vertex * label * Ast.vertex
