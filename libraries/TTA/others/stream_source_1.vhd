-- Copyright (C) 1991-2010 Altera Corporation
-- Your use of Altera Corporation's design tools, logic functions 
-- and other software and tools, and its AMPP partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Altera Program License 
-- Subscription Agreement, Altera MegaCore Function License 
-- Agreement, or other applicable license agreement, including, 
-- without limitation, that your use is for the sole purpose of 
-- programming logic devices manufactured by Altera and sold by 
-- Altera or its authorized distributors.  Please refer to the 
-- applicable agreement for further details.

-- PROGRAM              "Quartus II"
-- VERSION              "Version 10.0 Build 218 06/27/2010 SJ Full Version"
-- CREATED              "Mon Mar 21 15:25:10 2011"

library ieee;
use ieee.std_logic_1164.all;

library work;

entity stream_source_1 is
  port
    (
      ack    : in  std_logic;
      clk    : in  std_logic;
      rstx   : in  std_logic;
      status : out std_logic;
      data   : out std_logic_vector(7 downto 0)
      );
end stream_source_1;

architecture bdf_type of stream_source_1 is

  component data_src1
    port(clock   : in  std_logic;
         address : in  std_logic_vector(12 downto 0);
         q       : out std_logic_vector(7 downto 0)
         );
  end component;

  component addr_gen_1
    port(ack    : in  std_logic;
         clk    : in  std_logic;
         rstx   : in  std_logic;
         status : out std_logic;
         addr   : out std_logic_vector(12 downto 0)
         );
  end component;

  signal SYNTHESIZED_WIRE_0 : std_logic_vector(12 downto 0);


begin



  b2v_inst : data_src1
    port map(clock   => clk,
             address => SYNTHESIZED_WIRE_0,
             q       => data);


  b2v_inst1 : addr_gen_1
    port map(ack    => ack,
             clk    => clk,
             rstx   => rstx,
             status => status,
             addr   => SYNTHESIZED_WIRE_0);


end bdf_type;
