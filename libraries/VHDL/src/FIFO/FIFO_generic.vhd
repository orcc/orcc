-------------------------------------------------------------------------------
-- Title      : FIFO TOP
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : FIFO_generic.vhd
-- Author     : Nicolas Siret (nicolas.siret@live.fr)
-- Company    : INSA - Rennes
-- Created    : 
-- Last update: 2011-05-06
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2009-2010, IETR/INSA of Rennes
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

library work;
use work.orcc_package.all;
-------------------------------------------------------------------------------

entity FIFO_generic is
  generic (
    depth : integer := 32;
    width : integer := 32);
  port (
    reset_n  : in  std_logic;
    --
    wr_clk   : in  std_logic;
    wr_data  : in  std_logic;
    wr_ack   : out std_logic;
    data_in  : in  std_logic_vector(width -1 downto 0);
    --
    rd_clk   : in  std_logic;
    send     : out std_logic;
    rd_ack   : in  std_logic;
    data_out : out std_logic_vector(width -1 downto 0);
    --
    full     : out std_logic;
    empty    : out std_logic);
end FIFO_generic;

-------------------------------------------------------------------------------

architecture arch_FIFO_generic of FIFO_generic is

  signal rd_address : std_logic_vector(bit_width(depth)-1 downto 0);
  signal wr_address : std_logic_vector(bit_width(depth)-1 downto 0);
  signal iwr_ack    : std_logic;
begin
  
  wr_ack <= iwr_ack;
  controler_2 : entity work.FIFO_controler
    generic map (
      depth => depth,
      width => width)
    port map (
      reset_n => reset_n,
      wr_clk  => wr_clk,
      wr_data => wr_data,
      wr_ack  => iwr_ack,
      wr_add  => wr_address,
      rd_clk  => rd_clk,
      send    => send,
      rd_ack  => rd_ack,
      rd_add  => rd_address,
      empty   => empty,
      full    => full);

  
  ram_generic_1 : entity work.FIFO_ram
    generic map (
      depth => depth,
      width => width)
    port map (
      q          => data_out,
      rd_address => rd_address,
      rdclock      => rd_clk,
      data       => data_in,
      wr_address => wr_address,
      wrclock    => wr_clk,
      wren       => iwr_ack);

end arch_FIFO_generic;
