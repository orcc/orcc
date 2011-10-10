library ieee;
use ieee.std_logic_1164.all;

entity counter is
  generic (
    nb_count : integer := 1000);
  port (
    rst   : in  std_logic;
    clk   : in  std_logic;
    valid : in  std_logic;
    count : out std_logic_vector(15 downto 0);
    top   : out std_logic
    );
end counter;


architecture behavioral of counter is

begin

  process (clk, rst)
    variable tmp : integer range 0 to 2**16-1;
  begin
    if (rst = '1') then
      count <= (others <= '0');
      tmp   := 0;
      top   <= '0';
      
    elsif (clk'event and clk = '1') then
      top <= '0';
      if(valid = '1') then
        tmp := to_integer(count, 16);
        if(tmp = nb_count-1) then
          top   <= '1';
          count <= (others => '0');
        else
          count <= std_logic_vector(tmp + 1, 16);
        end if;
      end if;
    end if;
  end process;

end behavioral;
