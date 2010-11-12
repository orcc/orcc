-------------------------------------------------------------------------------
-- Title      : FIFO TOP
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : fifo_top.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-11-12
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

library ieee;
use ieee.std_logic_1164.all;

-------------------------------------------------------------------------------


entity fifo_top is
  generic
    (
      depth : integer := 32;
      width : integer := 32);
  port
    (
      reset_n  : in    std_logic;
      --
      wr_clk   : in    std_logic;
      wr_data  : in    std_logic;
      data_in  : in    std_logic_vector (width -1 downto 0);
      full     : inout std_logic;
      --
      rd_clk   : in    std_logic;
      rd_ack   : in    std_logic;
      send     : out   std_logic;
      data_out : out   std_logic_vector (width -1 downto 0);
      empty    : out   std_logic);
end fifo_top;


-------------------------------------------------------------------------------


architecture arch_fifo_top of fifo_top is

begin

  create_FIFO : if depth > 1 generate
    FIFO_generic_1 : entity work.FIFO_generic
      generic map (
        depth => depth,
        width => width)
      port map (
        reset_n  => reset_n,
        wr_clk   => wr_clk,
        wr_data  => wr_data,
        data_in  => data_in,
        full     => full,
        rd_clk   => rd_clk,
        rd_ack   => rd_ack,
        send     => send,
        data_out => data_out,
        empty    => empty);
  end generate;

  create_link : if depth = 1 generate
    arbiter_1 : entity work.arbiter
      generic map (
        width => width)
      port map (
        reset_n  => reset_n,
        wr_clk   => wr_clk,
        wr_data  => wr_data,
        data_in  => data_in,
        full     => full,
        rd_clk   => rd_clk,
        rd_ack   => rd_ack,
        send     => send,
        data_out => data_out,
        empty    => empty);
  end generate;




end arch_fifo_top;

--------------------------------------------------------------------------
