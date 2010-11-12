-------------------------------------------------------------------------------
-- Title      : FIFO TOP
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : fifo.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-11-10
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
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;

library work;
use work.orcc_package.all;
-------------------------------------------------------------------------------

entity counter is
  generic (
    depth : integer := 32);
  port (
    reset_n  : in    std_logic;
    rd_clk   : in    std_logic;
    rd_ack   : in    std_logic;
    wr_clk   : in    std_logic;
    wr_data  : in    std_logic;
    rd_add   : inout std_logic_vector(bit_width(depth) -1 downto 0);
    wr_add   : inout std_logic_vector(bit_width(depth) -1 downto 0));
end counter;

-------------------------------------------------------------------------------

architecture archcounter of counter is

  constant depth_std : std_logic_vector(bit_width(depth)-1 downto 0)
    := std_logic_vector(to_unsigned(depth -1, bit_width(depth)));
  
begin
  
  rd_count : process (rd_clk, reset_n) is
  begin
    if reset_n = '0' then
      rd_add <= (others => '0');
    elsif rising_edge(rd_clk) then
      if (rd_add = depth_std) and rd_ack = '1' then
        rd_add <= (others => '0');
      elsif rd_ack = '1' then
        rd_add <= rd_add +'1';
      end if;
    end if;
  end process rd_count;

  wr_count : process (wr_clk, reset_n) is
  begin
    if reset_n = '0' then
      wr_add <= (others => '0');
    elsif rising_edge(wr_clk) then
      if (wr_add = depth_std) and wr_data = '1' then
        wr_add <= (others => '0');
      elsif wr_data = '1' then
        wr_add <= wr_add +'1';
      end if;
    end if;
  end process wr_count;

end archcounter;

