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
      wrreq        : in  std_logic;
      rst_n        : in  std_logic;
      clk          : in  std_logic;
      rdreq        : in  std_logic;
      data         : in  std_logic_vector(width-1 downto 0);
      q            : out std_logic_vector(width-1 downto 0);
      nb_freerooms : out std_logic_vector(31 downto 0);
      nb_tokens    : out std_logic_vector(31 downto 0)
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

  signal nb_tokens_i : std_logic_vector(31 downto 0) := (others => '0');
  signal rst         : std_logic;
  signal empty       : std_logic;

begin

  rst          <= not(rst_n);
  nb_tokens    <= nb_tokens_i;
  nb_freerooms <= std_logic_vector(to_unsigned(size - to_integer(unsigned(nb_tokens_i)), 32));

  token_count : process(clk)
  begin
    if (clk'event and clk = '1') then
      if ((wrreq = '1' and nb_tokens_i(widthu) = '0') and (rdreq = '1' and empty = '0')) then
        nb_tokens_i(widthu-1 downto 0) <= nb_tokens_i(widthu-1 downto 0);
      else
        if (wrreq = '1' and nb_tokens_i(widthu) = '0') then
          nb_tokens_i(widthu-1 downto 0) <= nb_tokens_i(widthu-1 downto 0) + 1;
        end if;
        if (rdreq = '1' and empty = '0') then
          nb_tokens_i(widthu-1 downto 0) <= nb_tokens_i(widthu-1 downto 0) - 1;
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
      dout  => q,
      full  => nb_tokens_i(widthu),
      empty => empty);
end rtl_fifo;
