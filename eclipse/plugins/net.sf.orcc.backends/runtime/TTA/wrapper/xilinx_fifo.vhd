library ieee;
use ieee.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use ieee.numeric_std.all;

-- synthesis translate_off
library XilinxCoreLib;
-- synthesis translate_on

library UNISIM;
use UNISIM.VCOMPONENTS.all;

entity fifo is
  generic
    (
      size          : integer := 256;
      width         : integer := 32;
      widthu        : integer := 8;
      device_family : string  := "virtex5"
      );
  port
    (
      wrreq  : in  std_logic;
      rst_n  : in  std_logic;
      clk    : in  std_logic;
      rdreq  : in  std_logic;
      data   : in  std_logic_vector(width-1 downto 0);
      queue  : out std_logic_vector(width-1 downto 0);
      rooms  : out std_logic_vector(31 downto 0);
      tokens : out std_logic_vector(31 downto 0)
      );
end fifo;


architecture rtl_fifo of fifo is

  component fifo_br
    port (
      clk   : in  std_logic;
      rst   : in  std_logic;
      din   : in  std_logic_vector(31 downto 0);
      wr_en : in  std_logic;
      rd_en : in  std_logic;
      dout  : out std_logic_vector(31 downto 0);
      full  : out std_logic;
      empty : out std_logic);
  end component;

  signal tokens_i : std_logic_vector(31 downto 0) := (others => '0');
  signal rst      : std_logic;
  signal empty    : std_logic;

begin

  rst    <= not(rst_n);
  tokens <= tokens_i;
  rooms  <= std_logic_vector(to_unsigned(size - to_integer(unsigned(tokens_i)), 32));

  token_count : process(clk)
  begin
    if (clk'event and clk = '1') then
      if ((wrreq = '1' and tokens_i(widthu) = '0') and (rdreq = '1' and empty = '0')) then
        tokens_i <= tokens_i;
      else
        if (wrreq = '1' and tokens_i(widthu) = '0') then
          tokens_i <= tokens_i + 1;
        end if;
        if (rdreq = '1' and empty = '0') then
          tokens_i <= tokens_i - 1;
        end if;
      end if;
    end if;
  end process token_count;


  fifo_component : fifo_br
    port map (
      clk   => clk,
      rst   => rst,
      din   => data,
      wr_en => wrreq,
      rd_en => rdreq,
      dout  => queue,
      empty => empty);
end rtl_fifo;
