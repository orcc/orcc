library ieee;
use ieee.std_logic_1164.all;

library altera_mf;
use altera_mf.all;

entity fifo is
  generic
    (
      size          : integer := 256;
      width         : integer := 32;
      widthu        : integer := 8;
      device_family : string  := "Stratix III"
      );
  port
    (
      wrreq   : in  std_logic;
      reset_n : in  std_logic;
      clk     : in  std_logic;
      rdreq   : in  std_logic;
      data    : in  std_logic_vector(width-1 downto 0);
      q       : out std_logic_vector(width-1 downto 0);
      status  : out std_logic_vector(31 downto 0)
      );
end fifo;


architecture rtl_fifo of fifo is

  component scfifo
    generic (
      add_ram_output_register : string;
      intended_device_family  : string;
      lpm_numwords            : natural;
      lpm_showahead           : string;
      lpm_type                : string;
      lpm_width               : natural;
      lpm_widthu              : natural;
      overflow_checking       : string;
      underflow_checking      : string;
      use_eab                 : string
      );
    port (
      clock : in  std_logic;
      data  : in  std_logic_vector(width-1 downto 0);
      rdreq : in  std_logic;
      sclr  : in  std_logic;
      usedw : out std_logic_vector(widthu-1 downto 0);
      full  : out std_logic;
      q     : out std_logic_vector(width-1 downto 0);
      wrreq : in  std_logic
      );
  end component;

  signal s_status : std_logic_vector(31 downto 0) := (others => '0');
  signal s_clear  : std_logic;

begin

  s_clear <= not(reset_n);

  scfifo_component : scfifo
    generic map (
      add_ram_output_register => "OFF",
      intended_device_family  => device_family,
      lpm_numwords            => size,
      lpm_showahead           => "ON",
      lpm_type                => "scfifo",
      lpm_width               => width,
      lpm_widthu              => widthu,
      overflow_checking       => "ON",
      underflow_checking      => "ON",
      use_eab                 => "ON"
      )
    port map (
      clock => clk,
      data  => data,
      rdreq => rdreq,
      sclr  => s_clear,
      wrreq => wrreq,
      usedw => s_status(widthu-1 downto 0),
      q     => q,
      full  => s_status(widthu)
      );

  status <= s_status;
  
end rtl_fifo;
