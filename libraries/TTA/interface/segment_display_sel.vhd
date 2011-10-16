library ieee;
use ieee.std_logic_1164.all;

entity segment_display_sel is
  port (
    clk          : in  std_logic;
    rst          : in  std_logic;
    refresh      : in  std_logic;
    segment7_u   : in  std_logic_vector(6 downto 0);
    segment7_d   : in  std_logic_vector(6 downto 0);
    segment7_h   : in  std_logic_vector(6 downto 0);
    segment7_t   : in  std_logic_vector(6 downto 0);
    valid        : in  std_logic;
    segment7     : out std_logic_vector(6 downto 0);
    segment7_sel : out std_logic_vector(3 downto 0));
end segment_display_sel;

architecture behavioral of segment_display_sel is

  type level is (unit, dozen, hundred, thousand);

  signal current_level      : level     := unit;
  signal current_segment7   : std_logic_vector(6 downto 0);
  signal current_segment7_u : std_logic_vector(6 downto 0);
  signal current_segment7_d : std_logic_vector(6 downto 0);
  signal current_segment7_h : std_logic_vector(6 downto 0);
  signal current_segment7_t : std_logic_vector(6 downto 0);
  signal refreshing         : std_logic := '0';

begin

  process (clk, rst)
  begin  -- process
    if (rst = '0') then
      segment7_sel <= (others => '0');
    elsif (clk'event and clk = '1') then
      if(valid = '1') then
        current_segment7_u <= segment7_u;
        current_segment7_d <= segment7_d;
        current_segment7_h <= segment7_h;
        current_segment7_t <= segment7_t;
      end if;
      if(refresh = '1') then
        refreshing <= '1';
      end if;
      if(refreshing = '1') then
        case current_level is
          when unit =>
            segment7_sel  <= "0001";
            segment7      <= current_segment7_u;
            current_level <= dozen;
          when dozen =>
            segment7_sel  <= "0010";
            segment7      <= current_segment7_d;
            current_level <= hundred;
          when hundred =>
            segment7_sel  <= "0100";
            segment7      <= current_segment7_h;
            current_level <= thousand;
          when thousand =>
            segment7_sel  <= "1000";
            segment7      <= current_segment7_t;
            current_level <= unit;
            refreshing    <= '0';
          when others => null;
        end case;
      end if;
    end if;
  end process;

end behavioral;


