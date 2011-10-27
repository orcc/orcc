-------------------------------------------------------------------------------
-- Title      : Testbench for design "fps_eval"
-- Project    : Orcc - TTA back-end
-------------------------------------------------------------------------------
-- File       : fps_eval_tb.vhd
-- Author     : Hervé Yviquel  <herve.yviquel@irisa.fr>
-- Company    : IRISA
-- Created    : 2011-10-13
-- Last update: 2011-10-17
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Description: 
-------------------------------------------------------------------------------
-- Copyright (c) 2011 
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2011-10-13  1.0      hyviquel        Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

-------------------------------------------------------------------------------

entity fps_eval_tb is

end fps_eval_tb;

architecture arch_fps_eval_tb of fps_eval_tb is

  constant PERIOD       : time                         := 20 ns;
  constant period2      : integer                      := 20;
  --
  signal   rst_n        : std_logic;
  signal   clk          : std_logic                    := '0';
  signal   top_frame    : std_logic_vector(0 downto 0) := "0";
  signal   segment7     : std_logic_vector(6 downto 0);
  signal   segment7_sel : std_logic_vector(3 downto 0);

begin
  
  fps_eval_1 : entity work.fps_eval
    generic map (
      period => period2)
    port map (
      rst_n        => rst,
      clk          => clk,
      top_frame    => top_frame,
      segment7     => segment7,
      segment7_sel => segment7_sel);

  -- clock generation
  clk <= not clk after PERIOD/2;

  -- reset generation
  rst_proc : process
  begin
    rst_n <= '0';
    wait for 100 ns;
    rst_n <= '1';
    wait;
  end process;

  -- top_frame generation
  top_proc : process
  begin
    wait for 1625 us;
    top_frame <= "1";
    wait for 3*PERIOD;
    top_frame <= "0";
    wait for 3256 us;
    top_frame <= "1";
    wait for 3*PERIOD;
    top_frame <= "0";
    wait for 6478 us;
    top_frame <= "1";
    wait for 3*PERIOD;
    top_frame <= "0";
  end process;
  
end arch_fps_eval_tb;
-------------------------------------------------------------------------------
