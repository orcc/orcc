-------------------------------------------------------------------------------
-- Title      : Gray counter
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : gray_cnt.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-07-01
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
-- Date        Version  Author       Description
-- 2010-02-09  1.0      Nicolas      Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library work;
use work.orcc_package.all;
-------------------------------------------------------------------------------


entity ram_generic is
  generic (
    depth : integer := 32;
    width : integer := 32);
  port (
    rd_address : in  std_logic_vector(bit_width(depth)-1 downto 0);
    q          : out std_logic_vector(width -1 downto 0);
    --
    data       : in  std_logic_vector(width -1 downto 0);
    wr_address : in  std_logic_vector(bit_width(depth)-1 downto 0);
    wrclock    : in  std_logic;
    wren       : in  std_logic
    );
end ram_generic;

-------------------------------------------------------------------------------


architecture arch_RAM_generic of RAM_generic is


  -----------------------------------------------------------------------------
  -- Internal type declarations
  -----------------------------------------------------------------------------

  type ram_type is array (depth -1 downto 0) of
    std_logic_vector(width -1 downto 0);

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------

  signal ram       : ram_type;
  signal adress_wr : integer range DEPTH - 1 downto 0;
  signal adress_rd : integer range DEPTH - 1 downto 0;
  --

  -----------------------------------------------------------------------------
  
begin  -- arch_RAM

  adress_wr <= to_integer(unsigned(wr_address));
  adress_rd <= to_integer(unsigned(rd_address));
  q         <= ram(adress_rd);

  -- purpose: to store the data
  RAM_wr : process (wrclock)
  begin
    if rising_edge(wrclock) then
      --
      if wren = '1' then
        ram(adress_wr) <= data;
      end if;
    end if;
  end process RAM_wr;

end arch_RAM_generic;

