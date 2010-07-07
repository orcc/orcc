-------------------------------------------------------------------------------
-- Title      : FIFO TOP
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : fifo.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-07-07
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
use ieee.numeric_std.all;
library work;
use work.orcc_package.all;
-------------------------------------------------------------------------------

entity controler is
  generic (
    depth : integer := 32;
    width : integer := 32);
  port (
    reset_n     : in    std_logic;
    rd_clk      : in    std_logic;
    rd_ack      : in    std_logic;
    rd_send     : out   std_logic;
    rd_add_gray : inout std_logic_vector(bit_width(depth)-1 downto 0);
    wr_clk      : in    std_logic;
    wr_data     : in    std_logic;
    wr_add_gray : inout std_logic_vector(bit_width(depth)-1 downto 0);
    --
    empty       : out   std_logic;
    full        : out   std_logic
    );
end controler;

-------------------------------------------------------------------------------

architecture archcontroler of controler is


  -----------------------------------------------------------------------------
  -- Internal signals and constants declaration
  -----------------------------------------------------------------------------
  --
  constant depth_bin : std_logic_vector(bit_width(depth)-1 downto 0)
    := std_logic_vector(to_unsigned(depth -1, bit_width(depth)));
  --
  signal wr_add_bin : std_logic_vector(bit_width(depth)-1 downto 0);
  signal rd_add_bin : std_logic_vector(bit_width(depth)-1 downto 0);
  signal reset_rd   : std_logic;
  signal reset_wr   : std_logic;
  --
  
begin

  reset_wr <= '0' when wr_add_bin = depth_bin else
              '1';
  reset_rd <= '0' when rd_add_bin = depth_bin else
              '1';

  -- Counter
  counter_1 : entity work.counter
    generic map (
      depth => depth)
    port map (
      reset_n     => reset_n,
      reset_rd    => reset_rd,
      reset_wr    => reset_wr,
      rd_clk      => rd_clk,
      rd_ack      => rd_ack,
      wr_clk      => wr_clk,
      wr_data     => wr_data,
      rd_add_gray => rd_add_gray,
      wr_add_gray => wr_add_gray);



  -- Address Gray and Bin
  Gray2bin_1 : entity work.Gray2bin
    generic map (
      depth => depth)
    port map (
      rd_add_gray => rd_add_gray,
      wr_add_gray => wr_add_gray,
      rd_add_bin  => rd_add_bin,
      wr_add_bin  => wr_add_bin);


  -- Flags
  Flag_full : process (rd_add_bin, reset_n, wr_add_bin) is
    variable wr_add_bin_f : std_logic_vector(bit_width(depth)-1 downto 0);
  begin
    wr_add_bin_f := std_logic_vector(to_unsigned(to_integer(unsigned(wr_add_bin)) +1, bit_width(depth)));
    if reset_n = '0' then
      full <= '0';
    elsif wr_add_bin_f = rd_add_bin then
      full <= '1';
    else
      full <= '0';
    end if;
  end process Flag_full;

  Flag_empty : process (rd_add_bin, reset_n, wr_add_bin) is
  begin
    if reset_n = '0' then
      empty   <= '1';
      rd_send <= '0';
    elsif rd_add_bin = wr_add_bin then
      empty   <= '1';
      rd_send <= '0';
    else
      empty   <= '0';
      rd_send <= '1';
    end if;
  end process Flag_empty;


end archcontroler;

