-------------------------------------------------------------------------------
-- Title      : Testbench for design "clip"
-- Project    : 
-------------------------------------------------------------------------------
-- File       : clip_tb.vhd
-- Author     : 
-- Company    : 
-- Created    : 2010-01-29
-- Last update: 2010-02-04
-- Platform   : 
-- Standard   : VHDL'93/02
-------------------------------------------------------------------------------
-- Description: 
-------------------------------------------------------------------------------
-- Copyright (c) 2010 
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2010-01-29  1.0      Nicolas Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------

entity clip_tb is

end entity clip_tb;

-------------------------------------------------------------------------------

architecture arch_tb of clip_tb is

  -- component ports
  signal reset_n           : std_logic;
  signal clip_I_data       : integer range 511 downto -511;
  signal clip_I_empty      : std_logic;
  signal clip_I_read       : std_logic;
  signal clip_I_read_md       : std_logic;
  signal clip_SIGNED_data  : std_logic;
  signal clip_SIGNED_empty : std_logic;
  signal clip_SIGNED_read  : std_logic;
  signal clip_SIGNED_read_md  : std_logic;
  signal clip_O_data       : integer range 255 downto -255;
  signal clip_O_write      : std_logic;
  signal clip_O_data_md       : integer range 255 downto -255;
  signal clip_O_write_md      : std_logic;
  --
  signal I_DATA            : std_logic_vector(9 downto 0);
  signal I_SEND            : std_logic;
  signal I_ACK             : std_logic;
  signal I_COUNT           : std_logic_vector(15 downto 0);
  signal SIGNED_DATA       : std_logic;
  signal SIGNED_SEND       : std_logic;
  signal SIGNED_ACK        : std_logic;
  signal SIGNED_COUNT      : std_logic_vector(15 downto 0);
  signal O_DATA            : std_logic_vector(8 downto 0);
  signal O_SEND            : std_logic;
  signal O_ACK             : std_logic;
  signal O_RDY             : std_logic;
  signal O_COUNT           : std_logic_vector(15 downto 0);
  signal reset             : std_logic;
  -- clock
  signal Clk               : std_logic := '1';


  component Clip_0 is
    port(
      I_DATA       : in  std_logic_vector(9 downto 0);
      I_SEND       : in  std_logic;
      I_ACK        : out std_logic;
      I_COUNT      : in  std_logic_vector(15 downto 0);
      SIGNED_DATA  : in  std_logic;
      SIGNED_SEND  : in  std_logic;
      SIGNED_ACK   : out std_logic;
      SIGNED_COUNT : in  std_logic_vector(15 downto 0);
      O_DATA       : out std_logic_vector(8 downto 0);
      O_SEND       : out std_logic;
      O_ACK        : in  std_logic;
      O_RDY        : in  std_logic;
      O_COUNT      : out std_logic_vector(15 downto 0);
      CLK          : in  std_logic;
      RESET        : in  std_logic);
  end component Clip_0;


  
begin  -- architecture arch_tb

  -- component instantiation
  clip_md : entity work.clip
    port map (
      clock             => Clk,
      reset_n           => reset_n,
      clip_I_data       => clip_I_data,
      clip_I_empty      => clip_I_empty,
      clip_I_read       => clip_I_read_md,
      clip_SIGNED_data  => clip_SIGNED_data,
      clip_SIGNED_empty => clip_SIGNED_empty,
      clip_SIGNED_read  => clip_SIGNED_read_md,
      clip_O_full       => '0',
      clip_O_data       => clip_O_data_md,
      clip_O_write      => clip_O_write_md);


  -- component instantiation
  clip_orcc : entity work.clip_sv
    port map (
      clock             => Clk,
      reset_n           => reset_n,
      clip_I_data       => clip_I_data,
      clip_I_empty      => clip_I_empty,
      clip_I_read       => clip_I_read,
      clip_SIGNED_data  => clip_SIGNED_data,
      clip_SIGNED_empty => clip_SIGNED_empty,
      clip_SIGNED_read  => clip_SIGNED_read,
      clip_O_full       => '0',
      clip_O_data       => clip_O_data,
      clip_O_write      => clip_O_write);
  
  
  clip_cal2 : component Clip_0
    port map (
      I_DATA       => I_DATA,
      I_SEND       => I_SEND,
      I_ACK        => I_ACK,
      I_COUNT      => I_COUNT,
      --
      SIGNED_DATA  => SIGNED_DATA,
      SIGNED_SEND  => SIGNED_SEND,
      SIGNED_ACK   => SIGNED_ACK,
      SIGNED_COUNT => SIGNED_COUNT,
      --
      O_DATA       => O_DATA,
      O_SEND       => O_SEND,
      O_ACK        => O_SEND,
      O_COUNT      => O_COUNT,
      O_RDY        => '1',
      --
      CLK          => Clk,
      RESET        => reset
      );

  -- clock generation
  Clk <= not Clk after 10 ns;

  -- reset generation
  process
  begin
    reset_n <= '0';
    reset   <= '1';
    wait for 40 ns;
    reset_n <= '1';
    reset   <= '0';
    wait;
  end process;

  -- waveform generation
  WaveGen_Proc : process
  begin
    clip_I_data       <= 10;
    clip_I_empty      <= '1';
    clip_SIGNED_data  <= '0';
    clip_SIGNED_empty <= '1';
    I_DATA            <= std_logic_vector(to_signed(10, I_DATA'length));
    I_SEND            <= '0';
    SIGNED_DATA       <= '0';
    SIGNED_SEND       <= '0';
    wait for 100 ns;
    I_DATA            <= std_logic_vector(to_signed(511, I_DATA'length));
    I_SEND            <= '1';
    SIGNED_DATA       <= '0';
    SIGNED_SEND       <= '1';
    --
    clip_SIGNED_empty <= '0';
    clip_I_empty      <= '0';
    clip_I_data       <= 511;
    clip_SIGNED_data  <= '0';
        wait for 100 ns;
    I_DATA            <= std_logic_vector(to_signed(-222, I_DATA'length));
    I_SEND            <= '1';
    SIGNED_DATA       <= '0';
    SIGNED_SEND       <= '1';
    --
    clip_SIGNED_empty <= '0';
    clip_I_empty      <= '0';
    clip_I_data       <= -222;
    clip_SIGNED_data  <= '0';
        wait for 100 ns;
    I_DATA            <= std_logic_vector(to_signed(125, I_DATA'length));
    I_SEND            <= '1';
    SIGNED_DATA       <= '0';
    SIGNED_SEND       <= '1';
    --
    clip_SIGNED_empty <= '0';
    clip_I_empty      <= '0';
    clip_I_data       <= 125;
    clip_SIGNED_data  <= '0';
    wait for 100 ns;
    clip_SIGNED_data  <= '1';
    SIGNED_DATA       <= '1';
    wait for 100 ns;
    clip_I_data       <= 55;
    I_DATA            <= std_logic_vector(to_signed(55, I_DATA'length));
    wait for 100 ns;
    clip_SIGNED_data  <= '0';
    SIGNED_DATA       <= '0';
    wait for 100 ns;
  end process WaveGen_Proc;

  

end architecture arch_tb;
