-------------------------------------------------------------------------------
-- Title      : Arbiter
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : arbiter.vhd
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
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;
-------------------------------------------------------------------------------


entity arbiter is
  generic
    (
      width : integer := 32);
  port (
    reset_n  : in  std_logic;
    --
    wr_clk   : in  std_logic;
    wr_data  : in  std_logic;
    data_in  : in  std_logic_vector(width -1 downto 0);
    full     : out std_logic;
    --
    rd_clk   : in  std_logic;
    rd_ack   : in  std_logic;
    send     : out std_logic;
    data_out : out std_logic_vector(width -1 downto 0);
    empty    : out std_logic);
end arbiter;


-------------------------------------------------------------------------------


architecture arch_arbiter of arbiter is


  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  --
  signal register_data : std_logic_vector (width -1 downto 0);
  signal count_in      : std_logic_vector(2 downto 0);
  signal count_out     : std_logic_vector(2 downto 0);
  -----------------------------------------------------------------------------
  
begin

  -- Management of the write operations
  wr_proc : process (wr_clk, reset_n) is
  begin
    if reset_n = '0' then
      register_data <= (others => '0');
    elsif rising_edge(wr_clk) then
      --
      if count_in /= count_out then     -- Write data while a data is present in register  
        if rd_ack = '1' then            -- Data in register read -> save the new data in register
          register_data <= data_in;
        end if;
      elsif wr_data = '1' then          -- No data in register, write new data and 
        register_data <= data_in;
      end if;
      --
    end if;
  end process wr_proc;

  cnt_wr_proc : process (rd_clk, reset_n) is
  begin
    if reset_n = '0' then
      count_in <= (others => '0');
    elsif rising_edge(wr_clk) then
      if wr_data = '1' and count_in = "111" then
        count_in <= "000";
      elsif wr_data = '1' then
        count_in <= count_in +'1';
      end if;
    end if;
  end process cnt_wr_proc;


  -- Management of the read operation
  data_out <= register_data;  -- Data are always store in a register to
  -- enable the input and output frequencies
  -- to be different.
  rd_proc : process (count_in, count_out, reset_n) is
  begin
    if reset_n = '0' then
      send <= '0';
    elsif count_in /= count_out then
      send <= '1';
    else
      send <= '0';
    end if;
  end process rd_proc;


  cnt_rd_proc : process (rd_clk, reset_n) is
  begin
    if reset_n = '0' then
      count_out <= (others => '0');
    elsif rising_edge(rd_clk) then
      if rd_ack = '1' and count_out = "111" then
        count_out <= "000";
      elsif rd_ack = '1' then
        count_out <= count_out +'1';
      end if;
    end if;
  end process cnt_rd_proc;


  -- Management of the write operationsflags
  flag_proc : process (count_in, count_out, rd_ack, reset_n) is
  begin
    if reset_n = '0' then
      empty <= '1';
      full  <= '0';
    elsif count_in /= count_out and rd_ack = '1' then
      empty <= '0';
      full  <= '0';
    elsif count_in /= count_out then
      empty <= '0';
      full  <= '1';
    else
      empty <= '1';
      full  <= '0';
    end if;
  end process flag_proc;

end arch_arbiter;
