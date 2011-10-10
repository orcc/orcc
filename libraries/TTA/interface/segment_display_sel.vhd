library ieee;
use ieee.std_logic_1164.all;

entity segment_display_sel is
  port (
    clk          : in  std_logic;
    rst          : in  std_logic;
    segment7_u   : in  std_logic_vector(6 downto 0);
    segment7_t   : in  std_logic_vector(6 downto 0);
    segment7_h   : in  std_logic_vector(6 downto 0);
    segment7     : out std_logic_vector(6 downto 0);
    segment7_sel : out std_logic_vector(1 downto 0));
end segment_display_sel;

architecture behavioral of segment_display_sel is

  type level is (unit, ten, hundred);

  signal current_level : level := unit;

begin

  process (clk, rst)
  begin  -- process
    if (rst = '1') then
      segment7_sel <= (others => '0');
    elsif (clk'event and clk = '1') then
      case current_level is
        when unit =>
          segment7_sel  <= "00";
          segment7      <= segment7_u;
          current_level <= ten;
        when ten =>
          segment7_sel  <= "01";
          segment7      <= segment7_t;
          current_level <= hundred;
        when hundred =>
          segment7_sel  <= "10";
          segment7      <= segment7_h;
          current_level <= unit;
        when others => null;
      end case;
    end if;
  end process;

end behavioral;


