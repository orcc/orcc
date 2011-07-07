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
-- CREATED		"Mon Mar 14 11:27:27 2011"

LIBRARY ieee;
USE ieee.std_logic_1164.all; 

LIBRARY work;

ENTITY tta_fifo IS 
	PORT
	(
		wrreq :  IN  STD_LOGIC;
		reset_n :  IN  STD_LOGIC;
		clk :  IN  STD_LOGIC;
		rdreq :  IN  STD_LOGIC;
		data :  IN  STD_LOGIC_VECTOR(31 DOWNTO 0);
		q :  OUT  STD_LOGIC_VECTOR(31 DOWNTO 0);
		status :  OUT  STD_LOGIC_VECTOR(8 DOWNTO 0)
	);
END tta_fifo;

ARCHITECTURE bdf_type OF tta_fifo IS 

COMPONENT orcc_fifo
	PORT(wrreq : IN STD_LOGIC;
		 rdreq : IN STD_LOGIC;
		 clock : IN STD_LOGIC;
		 sclr : IN STD_LOGIC;
		 data : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 full : OUT STD_LOGIC;
		 q : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 usedw : OUT STD_LOGIC_VECTOR(7 DOWNTO 0)
	);
END COMPONENT;

COMPONENT usedw_to_status
	PORT(full : IN STD_LOGIC;
		 usedw : IN STD_LOGIC_VECTOR(7 DOWNTO 0);
		 status : OUT STD_LOGIC_VECTOR(8 DOWNTO 0)
	);
END COMPONENT;

SIGNAL	SYNTHESIZED_WIRE_0 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_1 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_2 :  STD_LOGIC_VECTOR(7 DOWNTO 0);


BEGIN 



b2v_inst2 : orcc_fifo
PORT MAP(wrreq => wrreq,
		 rdreq => rdreq,
		 clock => clk,
		 sclr => SYNTHESIZED_WIRE_0,
		 data => data,
		 full => SYNTHESIZED_WIRE_1,
		 q => q,
		 usedw => SYNTHESIZED_WIRE_2);


SYNTHESIZED_WIRE_0 <= NOT(reset_n);



b2v_inst99 : usedw_to_status
PORT MAP(full => SYNTHESIZED_WIRE_1,
		 usedw => SYNTHESIZED_WIRE_2,
		 status => status);


END bdf_type;