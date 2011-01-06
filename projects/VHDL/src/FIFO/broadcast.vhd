-------------------------------------------------------------------------------
-- Title      : BroadCast
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : broadcast.vhd
-- Author     : Nicolas Siret (nicolas.siret@live.fr)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2011-01-06
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
-- 2010-02-09  1.0      Nicolas      Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

-------------------------------------------------------------------------------

entity broadcast is
  generic
    (
      width : integer := 16;
      size  : integer := 2);
  port
    (
      reset_n  : in  std_logic;
      --
      data_in  : in  std_logic_vector (width -1 downto 0);
      send_in  : in  std_logic;
      ack_in   : out std_logic;
      --
      data_out : out std_logic_vector (width -1 downto 0);
      send_out : out std_logic_vector (size -1 downto 0);
      ack_out  : in  std_logic_vector (size -1 downto 0)
      );
end broadcast;


-------------------------------------------------------------------------------


architecture arch_broadcast of broadcast is

-------------------------------------------------------------------------------
-- Signals and constants declaration
-----------------------------------------------------------------------------
  constant ones        : std_logic_vector (size -1 downto 0) := (others => '1');
  --
  signal   internalAck : std_logic_vector (size -1 downto 0);
  
begin

  data_out <= data_in;

  -- Management of the external SEND (destination actor)
  externalSend : for i in 0 to size -1 generate
    send_out(i) <= send_in and not internalAck(i);
  end generate externalSend;


  -- Management of the external ACK (source actor)
  externalAck : process (ack_out, internalAck, send_in) is
  begin
    if (send_in = '1' and (ack_out or internalAck) = ones) then
      ack_in <= '1';
    else
      ack_in <= '0';
    end if;
  end process externalAck;


  -- Management of the internal ACK (source actor)
  counterGen : for i in 0 to size -1 generate
    process (ack_out, internalAck, send_in) is
    begin
      if reset_n = '0' then
        internalAck(i) <= '0';
      elsif (send_in = '1' and (ack_out or internalAck) = ones) then
        internalAck(i) <= '0';
      elsif (send_in = '1' and ack_out(i) = '1') then
        internalAck(i) <= '1';
      end if;
    end process;
  end generate counterGen;
  
  
end arch_broadcast;

--------------------------------------------------------------------------
