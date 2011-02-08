-------------------------------------------------------------------------------
-- Title      : comArbiter
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : comArbiter.vhd
-- Author     : Nicolas Siret (nicolas.siret@live.fr)
-- Company    : INSA-Rennes
-- Created    : 
-- Last update: 2011-02-08
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
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
-- 2010-02-09  1.0      Nicolas      Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

-------------------------------------------------------------------------------

entity comArbiter is
  generic (
    width : integer := 16);
  port (
    reset_n      : in  std_logic;
    clock        : in  std_logic;
    --
    actor_data   : in  std_logic_vector (width -1 downto 0);
    actor_send   : in  std_logic;
    actor_rdy    : out std_logic;
    --
    network_data : out std_logic_vector (width -1 downto 0);
    network_send : out std_logic;
    network_ack  : in  std_logic);
end comArbiter;


-------------------------------------------------------------------------------
architecture arch_comArbiter of comArbiter is

  signal management  : std_logic_vector (2 downto 0);
  signal data_in_reg : std_logic;
  
begin

  network_data <= actor_data;
  management   <= actor_send & network_ack & data_in_reg;

  process (management)
  begin
    case management is
      when "000" =>                     -- no data to send
        actor_rdy    <= '1';
        network_send <= '0';
      when "001" =>                     -- data in reg
        actor_rdy    <= '0';
        network_send <= '1';
      when "010" =>                     -- ack but no more data
        actor_rdy    <= '1';
        network_send <= '0';
      when "011" =>                     -- ack and data in reg
        actor_rdy    <= '1';
        network_send <= '1';
      when "100" =>                     --new data compute
        actor_rdy    <= '0';
        network_send <= '1';
      when "101" =>                     -- ""        ""
        actor_rdy    <= '0';
        network_send <= '1';
      when "110" =>                     -- send and ack
        actor_rdy    <= '1';
        network_send <= '1';
      when "111" =>                     -- ""       ""
        actor_rdy    <= '1';
        network_send <= '1';
      when others => null;
    end case;
  end process;

  process (clock, reset_n)
  begin
    if (reset_n = '0') then
      data_in_reg <= '0';
    elsif (rising_edge(clock)) then
      if ((actor_send and network_ack) = '1') then
        data_in_reg <= '0';
      elsif (actor_send = '1') then
        data_in_reg <= '1';
      elsif (network_ack = '1') then
        data_in_reg <= '0';
      end if;
    end if;
  end process;


end arch_comArbiter;

--------------------------------------------------------------------------
