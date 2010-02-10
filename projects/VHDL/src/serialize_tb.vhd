-------------------------------------------------------------------------------
-- Title      : Testbench for design "serialize"
-- Project    : 
-------------------------------------------------------------------------------
-- File       : serialize_tb.vhd
-- Author     : 
-- Company    : 
-- Created    : 2010-02-10
-- Last update: 2010-02-10
-- Platform   : 
-- Standard   : VHDL'93/02
-------------------------------------------------------------------------------
-- Description: 
-------------------------------------------------------------------------------
-- Copyright (c) 2010 
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2010-02-10  1.0      Nicolas	Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

-------------------------------------------------------------------------------

entity serialize_tb is

end entity serialize_tb;

-------------------------------------------------------------------------------

architecture rtl of serialize_tb is

  -- component ports
  signal reset_n             : std_logic;
  signal serialize_in8_data  : integer range 127 downto -127;
  signal serialize_in8_empty : std_logic;
  signal serialize_in8_read  : std_logic;
  signal serialize_out_full  : std_logic;
  signal serialize_out_data  : std_logic;
  signal serialize_out_write : std_logic;

  -- clock
  signal Clk : std_logic := '1';

begin  -- architecture rtl

  -- component instantiation
  DUT: entity work.serialize
    port map (
      clock               => Clk,
      reset_n             => reset_n,
      serialize_in8_data  => serialize_in8_data,
      serialize_in8_empty => serialize_in8_empty,
      serialize_in8_read  => serialize_in8_read,
      serialize_out_full  => '0',
      serialize_out_data  => serialize_out_data,
      serialize_out_write => serialize_out_write);

  -- clock generation
  Clk <= not Clk after 10 ns;

  -- waveform generation
  WaveGen_Proc: process
  begin
    reset_n <= '0';
    serialize_in8_empty <= '1';
    wait for 100 ns;
    reset_n <= '1';
    serialize_in8_data <= 10;
    wait for 100 ns;
    serialize_in8_empty <= '0';
    wait for 500 ns;
  end process WaveGen_Proc;

  

end architecture rtl;

-------------------------------------------------------------------------------

configuration serialize_tb_rtl_cfg of serialize_tb is
  for rtl
  end for;
end serialize_tb_rtl_cfg;

-------------------------------------------------------------------------------
