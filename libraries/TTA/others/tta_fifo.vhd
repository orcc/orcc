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
-- CREATED              "Mon Mar 14 11:27:27 2011"

library ieee;
use ieee.std_logic_1164.all;

library work;

entity tta_fifo is
  port
    (
      wrreq   : in  std_logic;
      reset_n : in  std_logic;
      clk     : in  std_logic;
      rdreq   : in  std_logic;
      data    : in  std_logic_vector(31 downto 0);
      q       : out std_logic_vector(31 downto 0);
      status  : out std_logic_vector(8 downto 0)
      );
end tta_fifo;

architecture bdf_type of tta_fifo is

  component orcc_fifo
    port(wrreq : in  std_logic;
         rdreq : in  std_logic;
         clock : in  std_logic;
         sclr  : in  std_logic;
         data  : in  std_logic_vector(31 downto 0);
         full  : out std_logic;
         q     : out std_logic_vector(31 downto 0);
         usedw : out std_logic_vector(7 downto 0)
         );
  end component;

  component usedw_to_status
    port(full   : in  std_logic;
         usedw  : in  std_logic_vector(7 downto 0);
         status : out std_logic_vector(8 downto 0)
         );
  end component;

  signal SYNTHESIZED_WIRE_0 : std_logic;
  signal SYNTHESIZED_WIRE_1 : std_logic;
  signal SYNTHESIZED_WIRE_2 : std_logic_vector(7 downto 0);


begin



  b2v_inst2 : orcc_fifo
    port map(wrreq => wrreq,
             rdreq => rdreq,
             clock => clk,
             sclr  => SYNTHESIZED_WIRE_0,
             data  => data,
             full  => SYNTHESIZED_WIRE_1,
             q     => q,
             usedw => SYNTHESIZED_WIRE_2);


  SYNTHESIZED_WIRE_0 <= not(reset_n);



  b2v_inst99 : usedw_to_status
    port map(full   => SYNTHESIZED_WIRE_1,
             usedw  => SYNTHESIZED_WIRE_2,
             status => status);


end bdf_type;
