-------------------------------------------------------------------------------
-- Title      : FIFO controler
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : FIFO_controler.vhd
-- Author     : Nicolas Siret (nicolas.siret@live.fr)
-- Company    : INSA - Rennes
-- Created    : 
-- Last update: 2011-05-06
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2009-2011, IETR/INSA of Rennes
-- Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
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
--  -- Neither the name of the LEAD TECH DESIGN and INSA/IETR nor the names of its
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
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;

library work;
use work.orcc_package.all;
-------------------------------------------------------------------------------

entity FIFO_controler is
  generic (
    depth : integer := 32;
    width : integer := 32);
  port (
    reset_n : in  std_logic;
    --
    wr_clk  : in  std_logic;
    wr_data : in  std_logic;
    wr_ack  : out std_logic;
    wr_add  : out std_logic_vector(bit_width(depth)-1 downto 0);
    --
    rd_clk  : in  std_logic;
    send    : out std_logic;
    rd_ack  : in  std_logic;
    rd_add  : out std_logic_vector(bit_width(depth)-1 downto 0);
    --
    empty   : out std_logic;
    full    : out std_logic);
end FIFO_controler;

-------------------------------------------------------------------------------

architecture archFIFO_controler of FIFO_controler is

  -----------------------------------------------------------------------------
  -- Constants and signals declaration
  -----------------------------------------------------------------------------
  constant zero    : std_logic_vector(bit_width(depth)-1 downto 0) := (others => '0');
  constant one     : std_logic_vector(bit_width(depth)-1 downto 0) := (others => '0');
  constant pfull   : std_logic_vector(bit_width(depth)-1 downto 0) := '0' & one(bit_width(depth)-2 downto 0);
  constant nfull   : std_logic_vector(bit_width(depth)-1 downto 0) := '1' & zero(bit_width(depth)-2 downto 0);
  --
  signal   ird_add : std_logic_vector(bit_width(depth)-1 downto 0);
  signal   ird_ack : std_logic;
  signal   iwr_add : std_logic_vector(bit_width(depth)-1 downto 0);
  signal   iempty  : std_logic;
  signal   ifull   : std_logic;
  signal   iwr_ack : std_logic;
  signal   level   : std_logic_vector(bit_width(depth)-1 downto 0);
  ------------------------------------------------------------------------------
  
begin
  
  empty   <= iempty;
  full    <= ifull;
  iwr_ack <= wr_data and not ifull;
  ird_ack <= rd_ack and not iempty;
  wr_ack  <= iwr_ack;
  send    <= not iempty;
  rd_add  <= ird_add;
  wr_add  <= iwr_add;
  --
  level   <= iwr_add - ird_add;

  -- A counter
  counter_1 : entity work.FIFO_counter
    generic map (
      depth => depth)
    port map (
      reset_n => reset_n,
      wr_clk  => wr_clk,
      wr_data => iwr_ack,
      wr_add  => iwr_add,
      rd_clk  => rd_clk,
      rd_data => ird_ack,
      rd_add  => ird_add);

  -- Management of the flags
  Flags : process (level) is
  begin
    if(level = zero) then
      iempty <= '1';
      ifull  <= '0';
    elsif(level = pfull) then           -- positive full
      iempty <= '0';
      ifull  <= '1';
    elsif(level = nfull) then           -- negative full
      iempty <= '0';
      ifull  <= '1';
    else
      iempty <= '0';
      ifull  <= '0';
    end if;
  end process Flags;

end archFIFO_controler;

