-------------------------------------------------------------------------------
-- Title      : True Dual Port RAM
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : inferredRAM_2p.vhd
-- Author     : Nicolas Siret (nicolas.siret@live.fr)
-- Company    : INSA - Rennes
-- Created    : 
-- Last update: 2012-05-08
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2010-2011, IETR/INSA of Rennes
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
-- 2011-02-21  1.0      nsiret       Created
-- 2012-05-08  1.1      hyviquel     Updated for TTA backend
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library work;
-------------------------------------------------------------------------------


entity inferredRAM_2p is
  generic (
    depth      : integer;
    addr_width : integer;
    byte_width : integer;
    bytes      : integer;
    initVal    : integer := 0);
  port (
    clk        : in  std_logic;
    --
    wren_p1    : in  std_logic;
    address_p1 : in  std_logic_vector(addr_width-1 downto 0);
    byteen_p1  : in  std_logic_vector(bytes-1 downto 0);
    data_p1    : in  std_logic_vector(bytes*byte_width-1 downto 0);
    queue_p1   : out std_logic_vector(bytes*byte_width-1 downto 0);
    --
    wren_p2    : in  std_logic;
    address_p2 : in  std_logic_vector(addr_width-1 downto 0);
    byteen_p2  : in  std_logic_vector(bytes-1 downto 0);
    data_p2    : in  std_logic_vector(bytes*byte_width-1 downto 0);
    queue_p2   : out std_logic_vector(bytes*byte_width-1 downto 0));
end inferredRAM_2p;

-------------------------------------------------------------------------------


architecture arch_inferredRAM_2p of inferredRAM_2p is

  -----------------------------------------------------------------------------
  -- Internal type declarations
  -----------------------------------------------------------------------------
  
  type word_type is array (0 to bytes -1) of std_logic_vector(byte_width-1 downto 0);
  type ram_type is array (0 to depth -1) of word_type;

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  shared variable ram   : ram_type := (others => (others => std_logic_vector(to_signed(initVal, byte_width))));
  signal iaddress_p1    : integer range depth - 1 downto 0;
  signal iaddress_p2    : integer range depth - 1 downto 0;
  signal queue_p1_local : word_type;
  signal queue_p2_local : word_type;
  -----------------------------------------------------------------------------
  
begin

  iaddress_p1 <= to_integer(unsigned(address_p1));
  iaddress_p2 <= to_integer(unsigned(address_p2));
  
  -- Reorganize the read data from the RAM to match the output
  unpack: for i in 0 to bytes - 1 generate
    queue_p1(byte_width*(i+1) - 1 downto byte_width*i) <= queue_p1_local(i);
    queue_p2(byte_width*(i+1) - 1 downto byte_width*i) <= queue_p2_local(i);    
  end generate unpack;

  -- Read and write data process for p1
  rdwrData_p1 : process (clk)
  begin
    if rising_edge(clk) then
      if (wren_p1 = '1') then
        -- edit this code if using other than four bytes per word
        if(byteen_p1(0) = '1') then
          ram(iaddress_p1)(0) <= data_p1;
        end if;
        if(byteen_p1(1) = '1') then
          ram(iaddress_p1)(1) <= data_p1;
        end if;
        if(byteen_p1(2) = '1') then
          ram(iaddress_p1)(2) <= data_p1;
        end if;
        if(byteen_p1(3) = '1') then
          ram(iaddress_p1)(3) <= data_p1;
        end if;
      end if;
      queue_p1_local <= ram(iaddress_p1);
    end if;
  end process rdwrData_p1;

  -- Read and write data process for p2
  rdwrData_p2 : process (clk)
  begin
   if rising_edge(clk) then
      if (wren_p2 = '1') then
        -- edit this code if using other than four bytes per word
        if(byteen_p2(0) = '1') then
          ram(iaddress_p2)(0) <= data_p2;
        end if;
        if(byteen_p2(1) = '1') then
          ram(iaddress_p2)(1) <= data_p2;
        end if;
        if(byteen_p2(2) = '1') then
          ram(iaddress_p2)(2) <= data_p2;
        end if;
        if(byteen_p2(3) = '1') then
          ram(iaddress_p2)(3) <= data_p2;
        end if;
      end if;
      queue_p2_local <= ram(iaddress_p2);
    end if;
  end process rdwrData_p2;


end arch_inferredRAM_2p;
