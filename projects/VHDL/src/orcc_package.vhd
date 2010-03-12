-------------------------------------------------------------------------------
-- Title      : orcc_package
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : orcc_package.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-03-12
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
-- Copyright (c) 2009-2010, IETR/INSA of Rennes
-- All rights reserved.
-- 
-- Redistribution and use in source and binary forms, with or without
-- modification, are permitted provided that the following conditions are met:
-- 
--  -- Redistributions of source code must retain the above copyright notice,
--     this list of conditions and the following disclaimer.
--  -- Redistributions in binary form must reproduce the above copyright notice,
--     this list of conditions and the following disclaimer in the documentation
--     and/or other materials provided with the distribution.
--  -- Neither the name of the IETR/INSA of Rennes nor the names of its
--     contributors may be used to endorse or promote products derived from this
--     software without specific prior written permission.
-- 
-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
-- AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
-- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
-- ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
-- LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
-- CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
-- SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
-- INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
-- STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
-- WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
-- SUCH DAMAGE.
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2010-02-09  1.0      LTD      Created
-------------------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

package orcc_package is

  function bitand(op1      : integer; op2 : integer; size : integer) return integer;
  function bitor(op1       : integer; op2 : integer; size : integer) return integer;
  function bitxor(op1      : integer; op2 : integer; size : integer) return integer;
  function bitnot(op1      : integer; size : integer) return integer;
  function div (op1        : integer; op2 : integer; size : integer) return integer;
  function get_mod (ARG0   : integer; ARG1 : integer) return integer;
  function shift_left(op1  : integer; op2 : integer; size : integer) return integer;
  function shift_right(op1 : integer; op2 : integer; size : integer) return integer;

end;

package body orcc_package is

  function bitand(op1 : integer; op2 : integer; size : integer) return integer is
    variable arg1   : std_logic_vector(size - 1 downto 0);
    variable arg2   : std_logic_vector(size - 1 downto 0);
  begin
      arg1   := std_logic_vector(to_signed(op1, size));
      arg2   := std_logic_vector(to_signed(op2, size));
    return to_integer(signed(arg1 and arg2));
  end function;

  function bitor(op1 : integer; op2 : integer; size : integer) return integer is
    variable arg1   : std_logic_vector(size - 1 downto 0);
    variable arg2   : std_logic_vector(size - 1 downto 0);
  begin
      arg1   := std_logic_vector(to_signed(op1, size));
      arg2   := std_logic_vector(to_signed(op2, size));
    return to_integer(signed(arg1 or arg2));
  end function;

  function bitxor(op1 : integer; op2 : integer; size : integer) return integer is
    variable arg1   : std_logic_vector(size - 1 downto 0);
    variable arg2   : std_logic_vector(size - 1 downto 0);
  begin
      arg1   := std_logic_vector(to_signed(op1, size));
      arg2   := std_logic_vector(to_signed(op2, size));
    return to_integer(signed(arg1 xor arg2));
  end function;

  function bitnot(op1 : integer; size : integer) return integer is
    variable result : std_logic_vector(size - 1 downto 0);
  begin
    result := not std_logic_vector(to_signed(op1, size));
    return to_integer(signed(result));
  end function;

  function div (op1 : integer; op2 : integer; size : integer) return integer is
    variable result : integer;
  begin
    result := op1/op2;
    return result;
  end function;

  function get_mod (ARG0 : integer; ARG1 : integer) return integer is
    variable result : integer;
  begin
    result := ARG0 mod ARG1;
    return result;
  end function;

  -- logical left shift
  -- result type is the same as the type of the first operand
  function shift_left(op1 : integer; op2 : integer; size : integer) return integer is
  begin
    return to_integer(to_signed(op1, size) sll op2);
  end function;

  -- logical right shift
  -- result type is the same as the type of the first operand
  function shift_right(op1 : integer; op2 : integer; size : integer) return integer is
  begin
    return to_integer(to_signed(op1, size) srl op2);
  end function;
  
end package body orcc_package;
