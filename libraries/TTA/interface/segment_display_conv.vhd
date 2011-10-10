library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.std_logic_unsigned.all;

entity segment_display_conv is
  port (
    clk      : in  std_logic;
    bcd      : in  std_logic_vector(3 downto 0);  -- BCD input
    segment7 : out std_logic_vector(6 downto 0)   -- 7 bit decoded output.
    );
end segment_display_conv;


architecture behavioral of segment_display_conv is

begin

  process (clk, bcd)
  begin
    if (clk'event and clk = '1') then
      case bcd is
        when "0000" => segment7 <= "0000001";  -- '0'
        when "0001" => segment7 <= "1001111";  -- '1'
        when "0010" => segment7 <= "0010010";  -- '2'
        when "0011" => segment7 <= "0000110";  -- '3'
        when "0100" => segment7 <= "1001100";  -- '4' 
        when "0101" => segment7 <= "0100100";  -- '5'
        when "0110" => segment7 <= "0100000";  -- '6'
        when "0111" => segment7 <= "0001111";  -- '7'
        when "1000" => segment7 <= "0000000";  -- '8'
        when "1001" => segment7 <= "0000100";  -- '9'
                      --nothing is displayed when a number more than 9 is given as input. 
        when others => segment7 <= "1111111";
      end case;
    end if;
  end process;

end behavioral;
