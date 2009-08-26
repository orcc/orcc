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

(** SSA module. *)

(** time spent transforming to SSA form *)
val time : float ref

(** [compute_ssa actor] takes an actor whose CFG has already been computed,
renames its variables and adds phi functions appropriatedly so that when
the function returns all actions/procedures verify the Static Single
Assignment property. The algorithm used is an implementation from
"Single-Pass Generation of Static Single Assignment Form for Structured Languages",
by Marc Brandis. This algorithm can only be used for structured languages, i.e. 
languages that do not break the control flow with break, continue, goto, or
return. Incidentally this is the case of CAL ^^ *)
val compute_ssa : Calir.actor -> unit
