-------------------------------------------------------------------------------
-- Title      : Test of performance evaluator
-- Project    : 
-------------------------------------------------------------------------------
-- File       : fps_eval_test.
-- Author     : Herv Yviquel  <hyviquel@laptopy>
-- Company    : 
-- Created    : 2011-10-17
-- Last update: 2011-10-17
-- Platform   : 
-- Standard   : VHDL'87
-------------------------------------------------------------------------------
-- Description: 
-------------------------------------------------------------------------------
-- Copyright (c) 2011 
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2011-10-17  1.0      hyviquel        Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity fps_eval_test is
  port (
    button       : in  std_logic;
    rst          : in  std_logic;
    clk          : in  std_logic;
    segment7     : out std_logic_vector(6 downto 0);
    segment7_sel : out std_logic_vector(3 downto 0)
    );
end fps_eval_test;

architecture arch_fps_eval_test of fps_eval_test is

  signal top_frame : std_logic_vector(0 downto 0);

begin
  top_frame <= (0 => not button, others => '0');

  fps_eval_1 : entity work.fps_eval
    generic map (
      period => 20)
    port map (
      rst          => rst,
      clk          => clk,
      top_frame    => top_frame,
      segment7     => segment7,
      segment7_sel => segment7_sel);

end arch_fps_eval_test;
