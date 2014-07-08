-------------------------------------------------------------------------------
-- Title      : Performance evaluator
-- Project    : Orcc - TTA back-end
-------------------------------------------------------------------------------
-- File       : fps_eval.vhd
-- Author     : Hervé Yviquel  <herve.yviquel@irisa.fr>
-- Company    : IRISA
-- Created    : 2011-10-17
-- Last update: 2011-10-18
-- Platform   : FPGA
-- Standard   : VHDL'93
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

library work;

-------------------------------------------------------------------------------

entity fps_eval is
  generic (
    period : in integer
    );
  port (
    rst_n          : in  std_logic;
    clk          : in  std_logic;
    top_frame    : in  std_logic_vector(0 downto 0);
    segment7     : out std_logic_vector(6 downto 0);
    segment7_sel : out std_logic_vector(3 downto 0)
    );
end fps_eval;

-------------------------------------------------------------------------------

architecture rtl_fps_eval of fps_eval is
  signal rst        : std_logic;
  --
  signal top_frame2 : std_logic_vector(1 downto 0);
  signal top_frame3 : std_logic;
  signal top_ms     : std_logic;
  signal top_s      : std_logic;
  --
  signal count_u    : std_logic_vector(15 downto 0);
  signal top_u      : std_logic;
  signal count_d    : std_logic_vector(15 downto 0);
  signal top_d      : std_logic;
  signal count_h    : std_logic_vector(15 downto 0);
  signal top_h      : std_logic;
  signal count_t    : std_logic_vector(15 downto 0);
  --
  signal segment7_u : std_logic_vector(6 downto 0);
  signal segment7_d : std_logic_vector(6 downto 0);
  signal segment7_h : std_logic_vector(6 downto 0);
  signal segment7_t : std_logic_vector(6 downto 0);
begin

  rst <= not rst_n;

  process (clk, rst_n)
  begin  -- process
    if rst_n = '0' then
      top_frame2 <= (others => '0');
    elsif clk'event and clk = '1' then
      top_frame2 <= top_frame2(0) & top_frame(0);
    end if;
  end process;
  
  top_frame3 <= top_frame2(0) and not top_frame2(1);

  counter_ms : entity work.counter
    generic map (
      nb_count => 1000*1000/period)
    port map (
      rst   => rst,
      clk   => clk,
      valid => '1',
      top   => top_ms);

  counter_s : entity work.counter
    generic map (
      nb_count => 1000)
    port map (
      rst   => rst,
      clk   => clk,
      valid => top_ms,
      top   => top_s);

  counter_u : entity work.counter
    generic map (
      nb_count => 10)
    port map (
      rst   => rst,
      clk   => clk,
      count => count_u,
      valid => top_ms,
      top   => top_u);

  counter_d : entity work.counter
    generic map (
      nb_count => 10)
    port map (
      rst   => rst,
      clk   => clk,
      count => count_d,
      valid => top_u,
      top   => top_d);

  counter_h : entity work.counter
    generic map (
      nb_count => 10)
    port map (
      rst   => rst,
      clk   => clk,
      count => count_h,
      valid => top_d,
      top   => top_h);

  counter_t : entity work.counter
    generic map (
      nb_count => 10)
    port map (
      rst   => rst,
      clk   => clk,
      count => count_t,
      valid => top_h);

  segment_display_conv_u : entity work.segment_display_conv
    port map (
      clk      => clk,
      rst      => rst,
      bcd      => count_u(3 downto 0),
      segment7 => segment7_u);

  segment_display_conv_d : entity work.segment_display_conv
    port map (
      clk      => clk,
      rst      => rst,
      bcd      => count_d(3 downto 0),
      segment7 => segment7_d);

  segment_display_conv_h : entity work.segment_display_conv
    port map (
      clk      => clk,
      rst      => rst,
      bcd      => count_h(3 downto 0),
      segment7 => segment7_h);

  segment_display_conv_t : entity work.segment_display_conv
    port map (
      clk      => clk,
      rst      => rst,
      bcd      => count_t(3 downto 0),
      segment7 => segment7_t);

  segment_display_sel_component : entity work.segment_display_sel
    port map (
      clk          => clk,
      rst          => rst,
      refresh      => top_ms,
      segment7_u   => segment7_u,
      segment7_d   => segment7_d,
      segment7_h   => segment7_h,
      segment7_t   => segment7_t,
      valid        => top_frame3,
      segment7     => segment7,
      segment7_sel => segment7_sel);

end rtl_fps_eval;

-------------------------------------------------------------------------------




