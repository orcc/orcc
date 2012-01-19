library ieee;
use ieee.std_logic_1164.all;

-- synthesis translate_off
library XilinxCoreLib;
-- synthesis translate_on

library UNISIM;
use UNISIM.VCOMPONENTS.all;

entity dram is
  generic
    (
      size          : integer;
      width         : integer := 32;
      widthu        : integer;
      init_file     : string;
      device_family : string  := "virtex5"
      );
  port
    (
      address : in  std_logic_vector (widthu-1 downto 0);
      byteena : in  std_logic_vector (3 downto 0) := (others => '1');
      clock   : in  std_logic;
      data    : in  std_logic_vector (width-1 downto 0);
      wren    : in  std_logic;
      q       : out std_logic_vector (width-1 downto 0)
      );
end dram;

architecture rtl_dram of dram is

  signal bytemask_i  : std_logic_vector(3 downto 0);
  signal bytemask_i2 : std_logic_vector(3 downto 0);

begin

  bytemask_i2 <= wren & wren & wren & wren;
  bytemask_i  <= bytemask_i2 and byteena;

  ram_component : entity XilinxCoreLib.blk_mem_gen_v4_1(behavioral)
    generic map(
      c_has_regceb                => 0,
      c_has_regcea                => 0,
      c_mem_type                  => 0,
      c_rstram_b                  => 0,
      c_rstram_a                  => 0,
      c_has_injecterr             => 0,
      c_rst_type                  => "SYNC",
      c_prim_type                 => 1,
      c_read_width_b              => width,
      c_initb_val                 => "0",
      c_family                    => device_family,
      c_read_width_a              => width,
      c_disable_warn_bhv_coll     => 0,
      c_use_softecc               => 0,
      c_write_mode_b              => "WRITE_FIRST",
      c_init_file_name            => init_file,
      c_write_mode_a              => "WRITE_FIRST",
      c_mux_pipeline_stages       => 0,
      c_has_softecc_output_regs_b => 0,
      c_has_softecc_output_regs_a => 0,
      c_has_mem_output_regs_b     => 0,
      c_has_mem_output_regs_a     => 0,
      c_load_init_file            => 1,
      c_xdevicefamily             => device_family,
      c_write_depth_b             => size,
      c_write_depth_a             => size,
      c_has_rstb                  => 0,
      c_has_rsta                  => 0,
      c_has_mux_output_regs_b     => 0,
      c_inita_val                 => "0",
      c_has_mux_output_regs_a     => 0,
      c_addra_width               => widthu,
      c_has_softecc_input_regs_b  => 0,
      c_has_softecc_input_regs_a  => 0,
      c_addrb_width               => widthu,
      c_default_data              => "0",
      c_use_ecc                   => 0,
      c_algorithm                 => 1,
      c_disable_warn_bhv_range    => 0,
      c_write_width_b             => width,
      c_write_width_a             => width,
      c_read_depth_b              => size,
      c_read_depth_a              => size,
      c_byte_size                 => 8,
      c_sim_collision_check       => "ALL",
      c_common_clk                => 0,
      c_wea_width                 => 4,
      c_has_enb                   => 0,
      c_web_width                 => 4,
      c_has_ena                   => 0,
      c_use_byte_web              => 1,
      c_use_byte_wea              => 1,
      c_rst_priority_b            => "CE",
      c_rst_priority_a            => "CE",
      c_use_default_data          => 0);
  port map (
    clka  => clock,
    wea   => bytemask_i,
    addra => address,
    dina  => data,
    douta => q);


end rtl_dram;
