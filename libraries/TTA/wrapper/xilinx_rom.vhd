library ieee;
use ieee.std_logic_1164.all;

library altera_mf;
use altera_mf.all;

entity irom is
  generic
    (
      size          : integer;
      width         : integer := 32;
      widthu		: integer;
	  init_file     : string;
	  device_family : string  := "virtex5"
      );
  port
    (
      address : in  std_logic_vector (widthu-1 downto 0);
      clock   : in  std_logic;
      q       : out std_logic_vector (width-1 downto 0)
      );
end irom;

architecture rtl_irom of irom is

begin
      
      rom_component : entity XilinxCoreLib.blk_mem_gen_v4_1(behavioral)
		generic map(
			c_has_regceb => 0,
			c_has_regcea => 0,
			c_mem_type => 3,
			c_rstram_b => 0,
			c_rstram_a => 0,
			c_has_injecterr => 0,
			c_rst_type => "SYNC",
			c_prim_type => 1,
			c_read_width_b => width,
			c_initb_val => "0",
			c_family => device_family,
			c_read_width_a => width,
			c_disable_warn_bhv_coll => 0,
			c_use_softecc => 0,
			c_write_mode_b => "WRITE_FIRST",
			c_init_file_name => init_file,
			c_write_mode_a => "WRITE_FIRST",
			c_mux_pipeline_stages => 0,
			c_has_softecc_output_regs_b => 0,
			c_has_softecc_output_regs_a => 0,
			c_has_mem_output_regs_b => 0,
			c_has_mem_output_regs_a => 0,
			c_load_init_file => 1,
			c_xdevicefamily => "virtex5",
			c_write_depth_b => 111,
			c_write_depth_a => 111,
			c_has_rstb => 0,
			c_has_rsta => 0,
			c_has_mux_output_regs_b => 0,
			c_inita_val => "0",
			c_has_mux_output_regs_a => 0,
			c_addra_width => widthu,
			c_has_softecc_input_regs_b => 0,
			c_has_softecc_input_regs_a => 0,
			c_addrb_width => widthu,
			c_default_data => "0",
			c_use_ecc => 0,
			c_algorithm => 1,
			c_disable_warn_bhv_range => 0,
			c_write_width_b => width,
			c_write_width_a => width,
			c_read_depth_b => 111,
			c_read_depth_a => 111,
			c_byte_size => 9,
			c_sim_collision_check => "ALL",
			c_common_clk => 0,
			c_wea_width => 1,
			c_has_enb => 0,
			c_web_width => 1,
			c_has_ena => 0,
			c_use_byte_web => 0,
			c_use_byte_wea => 0,
			c_rst_priority_b => "CE",
			c_rst_priority_a => "CE",
			c_use_default_data => 0)
		port map (
			clka => clock,
			addra => address,
			douta => q);
  
end rtl_irom;
