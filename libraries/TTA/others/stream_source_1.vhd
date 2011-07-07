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

-- PROGRAM		"Quartus II"
-- VERSION		"Version 10.0 Build 218 06/27/2010 SJ Full Version"
-- CREATED		"Mon Mar 21 15:25:10 2011"

LIBRARY ieee;
USE ieee.std_logic_1164.all; 

LIBRARY work;

ENTITY stream_source_1 IS 
	PORT
	(
		ack :  IN  STD_LOGIC;
		clk :  IN  STD_LOGIC;
		rstx :  IN  STD_LOGIC;
		status :  OUT  STD_LOGIC;
		data :  OUT  STD_LOGIC_VECTOR(7 DOWNTO 0)
	);
END stream_source_1;

ARCHITECTURE bdf_type OF stream_source_1 IS 

COMPONENT data_src1
	PORT(clock : IN STD_LOGIC;
		 address : IN STD_LOGIC_VECTOR(12 DOWNTO 0);
		 q : OUT STD_LOGIC_VECTOR(7 DOWNTO 0)
	);
END COMPONENT;

COMPONENT addr_gen_1
	PORT(ack : IN STD_LOGIC;
		 clk : IN STD_LOGIC;
		 rstx : IN STD_LOGIC;
		 status : OUT STD_LOGIC;
		 addr : OUT STD_LOGIC_VECTOR(12 DOWNTO 0)
	);
END COMPONENT;

SIGNAL	SYNTHESIZED_WIRE_0 :  STD_LOGIC_VECTOR(12 DOWNTO 0);


BEGIN 



b2v_inst : data_src1
PORT MAP(clock => clk,
		 address => SYNTHESIZED_WIRE_0,
		 q => data);


b2v_inst1 : addr_gen_1
PORT MAP(ack => ack,
		 clk => clk,
		 rstx => rstx,
		 status => status,
		 addr => SYNTHESIZED_WIRE_0);


END bdf_type;