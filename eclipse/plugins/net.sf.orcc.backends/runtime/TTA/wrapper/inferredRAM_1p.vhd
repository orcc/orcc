-------------------------------------------------------------------------------
-- Title      : Single-Port RAM
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : inferredRAM_1p.vhd
-- Author     : Nicolas Siret (nicolas.siret@live.fr)
-- Company    : INSA - Rennes
-- Created    : 
-- Last update: 2011-09-13
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2011, IETR/INSA of Rennes
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
-- Date        Version  Author       Description
-- 2011-02-21  1.0      Nicolas      Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library work;
use work.synflow_package.all;
-------------------------------------------------------------------------------


entity inferredRAM_1p is
  generic (
    depth : integer := 32;
    width : integer := 16;
    initVal: integer := 0);
  port (
    clk        : in  std_logic;
    --
    wren_p1    : in  std_logic;
    address_p1 : in  std_logic_vector(bit_width(depth - 1) -1 downto 0);
    data_p1    : in  std_logic_vector(width -1 downto 0);
    q_p1       : out std_logic_vector(width -1 downto 0));
end inferredRAM_1p;

-------------------------------------------------------------------------------


architecture arch_inferredRAM_1p of inferredRAM_1p is


  -----------------------------------------------------------------------------
  -- Internal type declarations
  -----------------------------------------------------------------------------

  type ram_type is array (0 to depth -1) of
    std_logic_vector(width -1 downto 0);

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  shared variable ram : ram_type := (others => std_logic_vector(to_signed(initVal, width)));
  signal iaddress_p1 : integer range DEPTH - 1 downto 0;
  --
  -----------------------------------------------------------------------------
  
begin

  iaddress_p1 <= to_integer(unsigned(address_p1));

  -- read and write data process
  rdwrData_p1 : process (clk)
  begin
    if rising_edge(clk) then
      if (wren_p1 = '1') then
        ram(iaddress_p1) := data_p1;
        q_p1             <= data_p1;
      else
        q_p1 <= ram(iaddress_p1);
      end if;
    end if;
  end process rdwrData_p1;

end arch_inferredRAM_1p;
