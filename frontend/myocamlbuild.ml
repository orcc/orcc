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

open Ocamlbuild_plugin
open Command

let _ =
  dispatch
    (function
     | After_rules ->
         (* External libraries: graph. *)
         ocaml_lib ~dir:"+ocamlgraph" ~extern:true "graph";
		 
		 Options.include_dirs := !Options.include_dirs @ ["+ocamlgraph"];

         Options.ocamlc := A "ocamlc.opt";
         Options.ocamldep := A "ocamldep.opt";
         Options.ocamllex := A "ocamllex.opt";
         Options.ocamlopt := A "ocamlopt.opt";

         (* Documentation: colorize code. *)
         flag [ "doc" ]
           (S
              [ A "-colorize-code"; A "-t"; A "Cal2ir documentation" ])
     | _ -> ())
