
library ieee;
use ieee.std_logic_1164.all;

library altera_mf;
use altera_mf.all;

entity test is
  port
    (
      address_a : in  std_logic_vector (4 downto 0);
      address_b : in  std_logic_vector (4 downto 0);
      byteena_a : in  std_logic_vector (3 downto 0);
      byteena_b : in  std_logic_vector (3 downto 0);
      clock     : in  std_logic;
      data_a    : in  std_logic_vector (31 downto 0);
      data_b    : in  std_logic_vector (31 downto 0);
      wren_a    : in  std_logic;
      wren_b    : in  std_logic;
      q_a       : out std_logic_vector (31 downto 0);
      q_b       : out std_logic_vector (31 downto 0)
      );
end test;


architecture SYN of test is

  signal sub_wire0 : std_logic_vector (31 downto 0);
  signal sub_wire1 : std_logic_vector (31 downto 0);



  component altsyncram
    generic (
      address_reg_b                      : string;
      byteena_reg_b                      : string;
      byte_size                          : natural;
      clock_enable_input_a               : string;
      clock_enable_input_b               : string;
      clock_enable_output_a              : string;
      clock_enable_output_b              : string;
      indata_reg_b                       : string;
      intended_device_family             : string;
      lpm_type                           : string;
      numwords_a                         : natural;
      numwords_b                         : natural;
      operation_mode                     : string;
      outdata_aclr_a                     : string;
      outdata_aclr_b                     : string;
      outdata_reg_a                      : string;
      outdata_reg_b                      : string;
      power_up_uninitialized             : string;
      read_during_write_mode_mixed_ports : string;
      read_during_write_mode_port_a      : string;
      read_during_write_mode_port_b      : string;
      widthad_a                          : natural;
      widthad_b                          : natural;
      width_a                            : natural;
      width_b                            : natural;
      width_byteena_a                    : natural;
      width_byteena_b                    : natural;
      wrcontrol_wraddress_reg_b          : string
      );
    port (
      byteena_a : in  std_logic_vector (3 downto 0);
      clock0    : in  std_logic;
      wren_a    : in  std_logic;
      address_b : in  std_logic_vector (4 downto 0);
      byteena_b : in  std_logic_vector (3 downto 0);
      data_b    : in  std_logic_vector (31 downto 0);
      q_a       : out std_logic_vector (31 downto 0);
      wren_b    : in  std_logic;
      address_a : in  std_logic_vector (4 downto 0);
      data_a    : in  std_logic_vector (31 downto 0);
      q_b       : out std_logic_vector (31 downto 0)
      );
  end component;

begin
  q_a <= sub_wire0(31 downto 0);
  q_b <= sub_wire1(31 downto 0);

  altsyncram_component : altsyncram
    generic map (
      address_reg_b                      => "CLOCK0",
      byteena_reg_b                      => "CLOCK0",
      byte_size                          => 8,
      clock_enable_input_a               => "BYPASS",
      clock_enable_input_b               => "BYPASS",
      clock_enable_output_a              => "BYPASS",
      clock_enable_output_b              => "BYPASS",
      indata_reg_b                       => "CLOCK0",
      intended_device_family             => "Cyclone IV GX",
      lpm_type                           => "altsyncram",
      numwords_a                         => 32,
      numwords_b                         => 32,
      operation_mode                     => "BIDIR_DUAL_PORT",
      outdata_aclr_a                     => "NONE",
      outdata_aclr_b                     => "NONE",
      outdata_reg_a                      => "CLOCK0",
      outdata_reg_b                      => "CLOCK0",
      power_up_uninitialized             => "FALSE",
      read_during_write_mode_mixed_ports => "DONT_CARE",
      read_during_write_mode_port_a      => "DONT_CARE",
      read_during_write_mode_port_b      => "DONT_CARE",
      widthad_a                          => 5,
      widthad_b                          => 5,
      width_a                            => 32,
      width_b                            => 32,
      width_byteena_a                    => 4,
      width_byteena_b                    => 4,
      wrcontrol_wraddress_reg_b          => "CLOCK0"
      )
    port map (
      byteena_a => byteena_a,
      clock0    => clock,
      wren_a    => wren_a,
      address_b => address_b,
      byteena_b => byteena_b,
      data_b    => data_b,
      wren_b    => wren_b,
      address_a => address_a,
      data_a    => data_a,
      q_a       => sub_wire0,
      q_b       => sub_wire1
      );



end SYN;
