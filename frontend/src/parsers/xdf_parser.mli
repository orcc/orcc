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

(** This modules provides functions to parse XDF networks. *)

(** [parse_network options xdf_file] parses the file whose absolute path is given by [xdf_file],
and returns a [Calast.network] closed using the [options.o_values] supplied.
If anything goes wrong, the frontend exits. *)
val parse_network : Options.t -> string -> Xdfast.Ast.network
