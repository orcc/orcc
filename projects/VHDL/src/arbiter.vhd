-------------------------------------------------------------------------------
-- Title      : Arbiter
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : arbiter.vhd
-- Author     : Nicolas Siret (nicolas.siret@ltdsa.com)
-- Company    : Lead Tech Design
-- Created    : 
-- Last update: 2010-04-28
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


entity arbiter is
  generic
    (
      width : integer := 32);
  port
    (
      clock         : in  std_logic;
      reset_n       : in  std_logic;
      --
      data_in       : in  std_logic_vector (width -1 downto 0);
      data_in_send  : in  std_logic;
      dest_ready    : out std_logic;
      --
      data_out      : out std_logic_vector (width -1 downto 0);
      data_out_send : out std_logic;
      data_out_ack  : in  std_logic);
end arbiter;


-------------------------------------------------------------------------------


architecture arch_arbiter of arbiter is


  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  --
  type FLAG_TYPE is (FIRST_SEND, SEND_DATA, SEND_NODATA, SEND_SIGNAL, IDLE, WAIT_ACK);
  signal send          : FLAG_TYPE;
  signal count         : integer range 3 downto 0;
  signal register_data : std_logic_vector (width -1 downto 0);
  signal iready        : std_logic;
  -----------------------------------------------------------------------------
  
begin

  arbiter_proc : process (clock, reset_n) is
  begin
    if reset_n = '0' then
      count <= 0;
    elsif rising_edge(clock) then
      case send is
        when FIRST_SEND =>
          register_data <= data_in;
          count         <= 1;
        when SEND_SIGNAL =>
          register_data <= data_in;
          count         <= 2;
        when WAIT_ACK =>
          count <= 3;
        when SEND_DATA =>
          register_data <= data_in;
        when SEND_NODATA =>
          count <= 0;
        when others =>
          null;
      end case;
    end if;
  end process arbiter_proc;


  Flag_proc : process (count, data_in_send, data_out_ack, register_data,
                       reset_n) is
  begin
    if reset_n = '0' then
      dest_ready    <= '1';
      data_out_send <= '0';
      send          <= IDLE;
      iready        <= '0';
      --
      -- Count = 0 (First read, no data in register)
    elsif data_in_send = '1' and count = 0 then
      dest_ready <= '1';
      send       <= FIRST_SEND;
      --
    elsif (data_in_send = '1' or iready = '1') and data_out_ack = '1' then
      iready <= '0';
      if count = 1 then
        dest_ready    <= '1';
        send          <= SEND_DATA;
        data_out      <= register_data;
        data_out_send <= '1';
      elsif count = 2 then
        dest_ready    <= '1';
        send          <= SEND_DATA;
        data_out      <= register_data;
        data_out_send <= '1';
      elsif count = 3 then
        send          <= FIRST_SEND;
        data_out_send <= '0';
      end if;
    elsif data_in_send = '1' and data_out_ack = '0' then
      if count = 1 then
        dest_ready    <= '1';
        send          <= SEND_SIGNAL;
        data_out      <= register_data;
        data_out_send <= '1';
      elsif count = 2 then
        iready        <= '1';
        dest_ready    <= '0';
        data_out_send <= '1';
      elsif count = 3 then
        iready        <= '1';
        dest_ready    <= '0';
        data_out_send <= '1';
      end if;
    elsif data_in_send = '0' and iready = '0' and data_out_ack = '1' then
      if count = 1 then
        send          <= SEND_NODATA;
        data_out_send <= '0';
      elsif count = 2 then
        send          <= WAIT_ACK;
        data_out_send <= '1';
      elsif count = 3 then
        send          <= SEND_NODATA;
        data_out_send <= '0';
      end if;
    else
      if count = 1 then
        dest_ready    <= '1';
        send          <= WAIT_ACK;
        data_out      <= register_data;
        data_out_send <= '1';
      elsif count = 2 then
        dest_ready    <= '0';
        data_out_send <= '1';
      elsif count = 3 then
        dest_ready    <= '0';
        data_out_send <= '1';
      else
        data_out_send <= '0';
        dest_ready    <= '1';
      end if;
    end if;
  end process Flag_proc;


end arch_arbiter;
