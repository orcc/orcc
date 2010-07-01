-------------------------------------------------------------------------------
-- Title      : Gray counter
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : gray_cnt.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-04-18
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
use ieee.Std_Logic_1164.all;

-------------------------------------------------------------------------------


entity gray_cnt_n is
  generic (
    width : integer := 4);
  port(
    reset_n : in    std_logic;
    clk     : in    std_logic;
    en      : in    std_logic;
    q       : inout std_logic_vector(width-1 downto 0));
end gray_cnt_n;


architecture archgray_cnt_n of gray_cnt_n is

  signal z  : std_logic_vector(width downto 0);
  signal qx : std_logic;
  signal sq : std_logic_vector(width downto 0);

  
begin


  -- less significant bits
  create_lsb : for i in 1 to width-1 generate
    gray_counter_i : entity work.gray_counter
      port map (
        reset_n => reset_n,
        clk     => clk,
        zin     => z(i-1),
        qin     => sq(i-1),
        en      => en,
        qout    => sq(i),
        zout    => z(i));
  end generate;

  gray_counter_1 : entity work.gray_counter
    port map (
      reset_n => reset_n,
      clk     => clk,
      zin     => z(width-1),
      qin     => qx,
      en      => en,
      qout    => sq(width),
      zout    => z(width));


-- auxiliary signal for MSB
  qx <= sq(width-1) or sq(width);

-- parity bit generation
  process(reset_n, clk)
  begin
    if reset_n = '0' then
      sq(0) <= '1';
    elsif rising_edge(clk) then
      if en = '1' then
        sq(0) <= not sq(0);
      end if;
    end if;
  end process;
  z(0) <= '1';
  q    <= sq(width downto 1);
  
end archgray_cnt_n;
