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
	  device_family : string  := "Stratix III"
      );
  port
    (
      address : in  std_logic_vector (widthu-1 downto 0);
      clock   : in  std_logic;
      q       : out std_logic_vector (width-1 downto 0)
      );
end irom;

architecture rtl_irom of irom is

  component altsyncram
    generic (
      address_aclr_a         : string;
      clock_enable_input_a   : string;
      clock_enable_output_a  : string;
      init_file              : string;
      intended_device_family : string;
      lpm_hint               : string;
      lpm_type               : string;
      numwords_a             : natural;
      operation_mode         : string;
      outdata_aclr_a         : string;
      outdata_reg_a          : string;
      ram_block_type         : string;
      widthad_a              : natural;
      width_a                : natural;
      width_byteena_a        : natural
      );
    port (
      address_a : in  std_logic_vector (widthad_a-1 downto 0);
      clock0    : in  std_logic;
      q_a       : out std_logic_vector (width_a-1 downto 0)
      );
  end component;

begin

  altsyncram_component : altsyncram
    generic map (
      address_aclr_a         => "NONE",
      clock_enable_input_a   => "BYPASS",
      clock_enable_output_a  => "BYPASS",
      init_file              => init_file,
      intended_device_family => device_family,
      lpm_hint               => "ENABLE_RUNTIME_MOD=NO",
      lpm_type               => "altsyncram",
      numwords_a             => size,
      operation_mode         => "ROM",
      outdata_aclr_a         => "NONE",
      outdata_reg_a          => "UNREGISTERED",
      ram_block_type         => "AUTO",
      widthad_a              => widthu,
      width_a                => width,
      width_byteena_a        => 1
      )
    port map (
      address_a => address,
      clock0    => clock,
      q_a       => q
      );
  
end rtl_irom;
