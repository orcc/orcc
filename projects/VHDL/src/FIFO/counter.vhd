-------------------------------------------------------------------------------
-- Title      : FIFO TOP
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : fifo.vhd
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

entity counter is
  generic (
    depth : integer := 32);
  port (
    reset_n       : in  std_logic;
    rd_clk        : in  std_logic;
    rd_ack        : in  std_logic;
    wr_clk        : in  std_logic;
    wr_data       : in  std_logic;
    rd_add_gray : inout std_logic_vector(bit_width(depth) -1 downto 0);
    wr_add_gray : inout std_logic_vector(bit_width(depth) -1 downto 0));
end counter;

-------------------------------------------------------------------------------

architecture archcounter of counter is

begin

  rd_cur_add_gray : entity work.gray_cnt_n
    generic map (
      width => bit_width(depth))
    port map (
      reset_n => reset_n,
      clk     => rd_clk,
      en      => rd_ack,
      q       => rd_add_gray);

  wr_cur_add_gray : entity work.gray_cnt_n
    generic map (
      width => bit_width(depth))
    port map (
      reset_n => reset_n,
      clk     => wr_clk,
      en      => wr_data,
      q       => wr_add_gray);

end archcounter;

