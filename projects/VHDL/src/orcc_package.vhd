-------------------------------------------------------------------------------
-- Title      : orcc_package
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : orcc_package.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-02-09
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

  function bitand (ARG0      : integer; ARG1 : integer) return integer;
  function bitor (ARG0       : integer; ARG1 : integer) return integer;
  function bitxor (ARG0      : integer; ARG1 : integer) return integer;
  function div (ARG0         : integer; ARG1 : integer) return integer;
  function get_mod (ARG0     : integer; ARG1 : integer) return integer;
  function shift_left (ARG0  : integer; ARG1 : integer) return integer;
  function shift_right (ARG0 : integer; ARG1 : integer) return integer;

end;

package body orcc_package is

  function bitand (ARG0 : integer; ARG1 : integer) return integer is
    variable result : std_logic_vector(31 downto 0);
  begin
    result := std_logic_vector(to_signed(ARG0, 32)) and std_logic_vector(to_signed(ARG1, 32));
    return to_integer(signed(result));
  end function;

  function bitor (ARG0 : integer; ARG1 : integer) return integer is
    variable result : std_logic_vector(31 downto 0);
  begin
    result := std_logic_vector(to_signed(ARG0, 32)) or std_logic_vector(to_signed(ARG1, 32));
    return to_integer(signed(result));
  end function;

  function bitxor (ARG0 : integer; ARG1 : integer) return integer is
    variable result : std_logic_vector(31 downto 0);
  begin
    result := std_logic_vector(to_signed(ARG0, 32)) xor std_logic_vector(to_signed(ARG1, 32));
    return to_integer(signed(result));
  end function;

  function div (ARG0 : integer; ARG1 : integer) return integer is
    variable result : integer;
  begin
    result := ARG0/ARG1;
    return result;
  end function;

  function get_mod (ARG0 : integer; ARG1 : integer) return integer is
    variable result : integer;
  begin
    result := ARG0 mod ARG1;
    return result;
  end function;

  function shift_left (ARG0 : integer; ARG1 : integer) return integer is
  begin
    return to_integer(to_signed(ARG0, 32) sll ARG1);
  end function;

  function shift_right (ARG0 : integer; ARG1 : integer) return integer is
  begin
    return to_integer(to_signed(ARG0, 32) srl ARG1);
  end function;
  
end package body orcc_package;



