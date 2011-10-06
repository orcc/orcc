library ieee;
use ieee.std_logic_1164.all;

library altera_mf;
use altera_mf.all;

entity dram is
  generic
    (
      size          : integer;
      width         : integer := 32;
      widthu		: integer;
	  init_file     : string;
	  device_family : string  := "Stratix III"
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

begin

  altsyncram_component : altera_mf_components.altsyncram
    generic map (
      byte_size                     => 8,
      clock_enable_input_a          => "BYPASS",
      clock_enable_output_a         => "BYPASS",
      init_file                     => init_file,
      intended_device_family        => device_family,
      lpm_hint                      => "ENABLE_RUNTIME_MOD=NO",
      lpm_type                      => "altsyncram",
      numwords_a                    => size,
      operation_mode                => "SINGLE_PORT",
      outdata_aclr_a                => "NONE",
      outdata_reg_a                 => "UNREGISTERED",
      power_up_uninitialized        => "FALSE",
      ram_block_type                => "AUTO",
      read_during_write_mode_port_a => "NEW_DATA_NO_NBE_READ",
      widthad_a                     => widthu,
      width_a                       => width,
      width_byteena_a               => 4
      )
    port map (
      address_a => address,
      byteena_a => byteena,
      clock0    => clock,
      data_a    => data,
      wren_a    => wren,
      q_a       => q
      );
  
end rtl_dram;
