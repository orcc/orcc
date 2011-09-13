-------------------------------------------------------------------------------
-- Opcode package
-------------------------------------------------------------------------------
library IEEE;
use IEEE.Std_Logic_1164.all;

package opcodes_cal_stream_in is

  constant CAL_STREAM_IN_PEEK   : std_logic_vector(2-1 downto 0) := "00";
  constant CAL_STREAM_IN_READ   : std_logic_vector(2-1 downto 0) := "01";
  constant CAL_STREAM_IN_STATUS : std_logic_vector(2-1 downto 0) := "10";
  
end opcodes_cal_stream_in;

-------------------------------------------------------------------------------
-- Stream In unit
-------------------------------------------------------------------------------
library IEEE;
use IEEE.Std_Logic_1164.all;
use IEEE.numeric_std.all;
use work.opcodes_cal_stream_in.all;

entity cal_stream_in is
  
  port (
    t1data   : in  std_logic_vector(31 downto 0);
    t1load   : in  std_logic;
    t1opcode : in  std_logic_vector(2-1 downto 0);
    r1data   : out std_logic_vector(31 downto 0);
    clk      : in  std_logic;
    rstx     : in  std_logic;
    glock    : in  std_logic;

    -- external port interface
    ext_data   : in  std_logic_vector(31 downto 0);  -- acquired data comes through this
    ext_status : in  std_logic_vector(31 downto 0);  -- status signal provided from outside
    ext_ack    : out std_logic_vector(0 downto 0)  -- data acknowledge to outside
    );

end cal_stream_in;


architecture rtl of cal_stream_in is
  
  signal r1reg  : std_logic_vector(31 downto 0);
  signal ackreg : std_logic_vector(0 downto 0);

begin
  
  regs : process (clk, rstx)
  begin  -- process regs
    if rstx = '0' then
      r1reg  <= (others => '0');
      ackreg <= (others => '0');
    elsif clk'event and clk = '1' then
      if glock = '0' then

        -- reset the acknowledge signal after a while
        ackreg <= (others => '0');

        if t1load = '1' then
          case t1opcode is
            when CAL_STREAM_IN_READ =>
              r1reg  <= ext_data;
              ackreg <= (0 => '1');
            when CAL_STREAM_IN_STATUS =>
              -- stream_in_status is placed in the least significant bits of r1reg
              r1reg <= ext_status;
            when CAL_STREAM_IN_PEEK =>
              r1reg <= ext_data;
            when others => null;
          end case;
        end if;

      end if;
    end if;
  end process regs;

  r1data  <= r1reg;
  ext_ack <= ackreg;
  
end rtl;
