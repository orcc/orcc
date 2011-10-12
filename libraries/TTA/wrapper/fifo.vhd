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
      rst     : in  std_logic;
      clk     : in  std_logic;
      rdreq   : in  std_logic;
      data    : in  std_logic_vector(width-1 downto 0);
      q       : out std_logic_vector(width-1 downto 0);
      status  : out std_logic_vector(31 downto 0)
      );
end fifo;


architecture rtl_fifo of fifo is

  signal s_status : std_logic_vector(31 downto 0) := (others => '0');
  signal s_clear  : std_logic;

begin

  s_clear <= not(rst);

  scfifo_component : altera_mf_components.scfifo
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
