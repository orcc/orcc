-------------------------------------------------------------------------------
-- Title      : Testbench for design "fps_eval"
-- Project    : 
-------------------------------------------------------------------------------
-- File       : fps_eval_tb.vhd
-- Author     : Hervé Yviquel  <hyviquel@laptopy>
-- Company    : 
-- Created    : 2011-10-13
-- Last update: 2011-10-13
-- Platform   : 
-- Standard   : VHDL'87
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
  signal   rst          : std_logic;
  signal   clk          : std_logic                    := '0';
  signal   top_frame    : std_logic_vector(0 downto 0) := "0";
  signal   segment7     : std_logic_vector(6 downto 0);
  signal   segment7_sel : std_logic_vector(1 downto 0);

begin  -- arch_fps_eval_tb

  fps_eval_1 : entity work.fps_eval
    generic map (
      period => period2)
    port map (
      rst          => rst,
      clk          => clk,
      top_frame    => top_frame,
      segment7     => segment7,
      segment7_sel => segment7_sel);


  -- clock generation
  clk <= not clk after PERIOD/2;

  -- reset generation
  rst_proc : process
  begin
    rst <= '0';
    wait for 100 ns;
    rst <= '1';
    wait;
  end process;

  -- top_frame generation
  top_proc : process
  begin
    wait for 1 ms;
    top_frame <= "1";
    top_frame <= not top_frame after PERIOD/2;
  end process;
  
end arch_fps_eval_tb;
-------------------------------------------------------------------------------
