-------------------------------------------------------------------------------
-- Title      : Instance: display
-- Project    : 
-------------------------------------------------------------------------------
-- File       : processor_display.vhd
-- Author     : Orcc - TTA
-- Company    : 
-- Created    : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c)  
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 
-------------------------------------------------------------------------------


------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

library work;
use work.processor_display_tl_globals.all;
use work.processor_display_tl_imem_mau.all;
use work.processor_display_tl_params.all;


------------------------------------------------------------------------------
entity processor_display is
  port
    (
      clk          : in  std_logic;
      data_1_in    : in  std_logic_vector(31 downto 0);
      status_1_in  : in  std_logic_vector(8 downto 0);
      ack_1_in     : out std_logic;
      data_2_in    : in  std_logic_vector(31 downto 0);
      status_2_in  : in  std_logic_vector(8 downto 0);
      ack_2_in     : out std_logic;
      data_3_in    : in  std_logic_vector(31 downto 0);
      status_3_in  : in  std_logic_vector(8 downto 0);
      ack_3_in     : out std_logic;
      reset_n      : in  std_logic;
      leds_out     : out std_logic_vector(7 downto 0)
      );
end processor_display;


------------------------------------------------------------------------------
architecture bdf_type of processor_display is

  ---------------------------------------------------------------------------
  -- Components declaration
  ---------------------------------------------------------------------------
  COMPONENT stream_source_2
	PORT(ack : IN STD_LOGIC;
		 clk : IN STD_LOGIC;
		 rstx : IN STD_LOGIC;
		 status : OUT STD_LOGIC;
		 data : OUT STD_LOGIC_VECTOR(7 DOWNTO 0)
	);
END COMPONENT;
  
  component dram_display
    port(clock   : in  std_logic;
         wren    : in  std_logic;
         address : in  std_logic_vector(fu_LSU_addrw-2-1 downto 0);
         byteena : in  std_logic_vector(fu_LSU_dataw/8-1 downto 0);
         data    : in  std_logic_vector(31 downto 0);
         q       : out std_logic_vector(31 downto 0)
         );
  end component;

  component irom_display
    port(clock   : in  std_logic;
         address : in  std_logic_vector(8 downto 0);
         q       : out std_logic_vector(85 downto 0)
         );
  end component;

  component processor_display_tl is
	PORT(clk : IN STD_LOGIC;
		 rstx : IN STD_LOGIC;
		 busy : IN STD_LOGIC;
		 fu_LSU_dmem_data_in : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_IN_1_ext_data : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_IN_1_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_IN_2_ext_data : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_IN_2_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_IN_3_ext_data : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_IN_3_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_IN_4_ext_data : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_IN_4_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_IN_5_ext_data : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_IN_5_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_IN_6_ext_data : IN STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_IN_6_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_IN_CHAR_ext_data : IN STD_LOGIC_VECTOR(7 DOWNTO 0);
		 fu_STREAM_IN_CHAR_ext_status : IN STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_1_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_OUT_2_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_OUT_3_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_OUT_4_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_OUT_5_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_OUT_6_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_OUT_7_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 fu_STREAM_OUT_8_ext_status : IN STD_LOGIC_VECTOR(8 DOWNTO 0);
		 imem_data : IN STD_LOGIC_VECTOR(85 DOWNTO 0);
		 pc_init : IN STD_LOGIC_VECTOR(12 DOWNTO 0);
		 imem_en_x : OUT STD_LOGIC;
		 fu_LSU_dmem_addr : OUT STD_LOGIC_VECTOR(fu_LSU_addrw-2-1 DOWNTO 0);
		 fu_LSU_dmem_bytemask : OUT STD_LOGIC_VECTOR(3 DOWNTO 0);
		 fu_LSU_dmem_data_out : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_LSU_dmem_mem_en_x : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_LSU_dmem_wr_en_x : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_IN_1_ext_ack : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_IN_2_ext_ack : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_IN_3_ext_ack : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_IN_4_ext_ack : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_IN_5_ext_ack : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_IN_6_ext_ack : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_IN_CHAR_ext_ack : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_1_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_1_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_2_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_2_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_3_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_3_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_4_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_4_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_5_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_5_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_6_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_6_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_7_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_7_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 fu_STREAM_OUT_8_ext_data : OUT STD_LOGIC_VECTOR(31 DOWNTO 0);
		 fu_STREAM_OUT_8_ext_dv : OUT STD_LOGIC_VECTOR(0 TO 0);
		 imem_addr : OUT STD_LOGIC_VECTOR(12 DOWNTO 0)
	);
  end component;
  
  ---------------------------------------------------------------------------

  ---------------------------------------------------------------------------
  -- Signals declaration
  ---------------------------------------------------------------------------
  signal dram_addr          : std_logic_vector(fu_LSU_addrw-2-1 downto 0);
  signal imem_addr          : std_logic_vector(IMEMADDRWIDTH-1 downto 0);
  signal wren_wire          : std_logic;
  signal bytemask_wire      : std_logic_vector(fu_LSU_dataw/8-1 downto 0);
  signal dram_data_in_wire  : std_logic_vector(fu_LSU_dataw-1 downto 0);
  signal dram_data_out_wire : std_logic_vector(fu_LSU_dataw-1 downto 0);
  signal wren_x_wire        : std_logic;
  signal idata_wire         : std_logic_vector(IMEMWIDTHINMAUS*IMEMMAUWIDTH-1 downto 0);
  SIGNAL src_ack			:  STD_LOGIC;
  SIGNAL src_data 			:  STD_LOGIC_VECTOR(7 DOWNTO 0);
  SIGNAL src_status 		:  STD_LOGIC;
  SIGNAL	ledline :  STD_LOGIC_VECTOR(31 DOWNTO 0);
  ---------------------------------------------------------------------------

begin

  inst_dram_decoder_motion_add : dram_display
    port map(clock   => clk,
             wren    => wren_wire,
             address => dram_addr,
             byteena => bytemask_wire,
             data    => dram_data_in_wire,
             q       => dram_data_out_wire);

  wren_wire <= not(wren_x_wire);

  inst_irom_display : irom_display
    port map(clock   => clk,
             address => imem_addr(8 downto 0),
             q       => idata_wire);

  processor_display : processor_display_tl
    port map(clk                        => clk,
             busy                       => '0',
             imem_addr                  => imem_addr,
             imem_data                  => idata_wire,
             pc_init                    => (others => '0'),
             fu_LSU_dmem_data_in        => dram_data_out_wire,
             fu_LSU_dmem_data_out       => dram_data_in_wire,
             fu_LSU_dmem_addr           => dram_addr,
             fu_LSU_dmem_wr_en_x(0)     => wren_x_wire,
             fu_LSU_dmem_bytemask       => bytemask_wire,
             fu_STREAM_IN_1_ext_data    => data_1_in,
             fu_STREAM_IN_1_ext_status  => status_1_in,
             fu_STREAM_IN_1_ext_ack(0)  => ack_1_in,
             fu_STREAM_IN_2_ext_data    => data_2_in,
             fu_STREAM_IN_2_ext_status  => status_2_in,
             fu_STREAM_IN_2_ext_ack(0)  => ack_2_in,
             fu_STREAM_IN_3_ext_data    => data_3_in,
             fu_STREAM_IN_3_ext_status  => status_3_in,
             fu_STREAM_IN_3_ext_ack(0)  => ack_3_in,
             fu_STREAM_IN_CHAR_ext_data    	=> src_data,
             fu_STREAM_IN_CHAR_ext_status(0)=> src_status,
             fu_STREAM_IN_CHAR_ext_ack(0)  	=> src_ack,
             fu_STREAM_OUT_1_ext_data   	=> ledline,
             rstx                       	=> reset_n,
		     fu_STREAM_IN_4_ext_data 		=> (others => '0'),
		     fu_STREAM_IN_4_ext_status 		=> (others => '0'),
		     fu_STREAM_IN_5_ext_data 		=> (others => '0'),
		     fu_STREAM_IN_5_ext_status 		=> (others => '0'),
		     fu_STREAM_IN_6_ext_data 		=> (others => '0'),
		     fu_STREAM_IN_6_ext_status 		=> (others => '0'),
		     fu_STREAM_OUT_1_ext_status => (others => '0'),
		     fu_STREAM_OUT_2_ext_status => (others => '0'),
		     fu_STREAM_OUT_3_ext_status => (others => '0'),
		     fu_STREAM_OUT_4_ext_status => (others => '0'),
		     fu_STREAM_OUT_5_ext_status => (others => '0'),
	    	 fu_STREAM_OUT_6_ext_status => (others => '0'),
	    	 fu_STREAM_OUT_7_ext_status => (others => '0'),
		     fu_STREAM_OUT_8_ext_status => (others => '0'));
             
  b2v_checksum : stream_source_2
    PORT MAP(ack 	=> src_ack,
             clk 	=> clk,
             rstx 	=> reset_n,
             status => src_status,
             data 	=> src_data);
             
       leds_out(7 DOWNTO 0) <= ledline(7 DOWNTO 0);
		 
end bdf_type;
