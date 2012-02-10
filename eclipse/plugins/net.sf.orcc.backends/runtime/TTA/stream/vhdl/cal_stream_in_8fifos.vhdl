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
    -- FIFO n°0
    ext_data0   : in  std_logic_vector(31 downto 0);  -- acquired data comes through this
    ext_status0 : in  std_logic_vector(31 downto 0);  -- status signal provided from outside
    ext_ack0    : out std_logic_vector(0 downto 0);  -- data acknowledge to outside
    -- FIFO n°1
    ext_data1   : in  std_logic_vector(31 downto 0);
    ext_status1 : in  std_logic_vector(31 downto 0);
    ext_ack1    : out std_logic_vector(0 downto 0);
    -- FIFO n°2
    ext_data2   : in  std_logic_vector(31 downto 0);
    ext_status2 : in  std_logic_vector(31 downto 0);
    ext_ack2    : out std_logic_vector(0 downto 0);
    -- FIFO n°3
    ext_data3   : in  std_logic_vector(31 downto 0);
    ext_status3 : in  std_logic_vector(31 downto 0);
    ext_ack3    : out std_logic_vector(0 downto 0);
    -- FIFO n°4
    ext_data4   : in  std_logic_vector(31 downto 0);
    ext_status4 : in  std_logic_vector(31 downto 0);
    ext_ack4    : out std_logic_vector(0 downto 0);
    -- FIFO n°5
    ext_data5   : in  std_logic_vector(31 downto 0);
    ext_status5 : in  std_logic_vector(31 downto 0);
    ext_ack5    : out std_logic_vector(0 downto 0);
    -- FIFO n°6
    ext_data6   : in  std_logic_vector(31 downto 0);
    ext_status6 : in  std_logic_vector(31 downto 0);
    ext_ack6    : out std_logic_vector(0 downto 0);
    -- FIFO n°7
    ext_data7   : in  std_logic_vector(31 downto 0);
    ext_status7 : in  std_logic_vector(31 downto 0);
    ext_ack7    : out std_logic_vector(0 downto 0)
    );

end cal_stream_in;


architecture rtl of cal_stream_in is

  type data_array is array (0 to 7) of std_logic_vector(31 downto 0);
  type ack_array is array (0 to 7) of std_logic_vector(0 downto 0);

  signal ext_data_current   : data_array;
  signal ext_status_current : data_array;
  signal ext_ack_current    : ack_array;

begin
  
  ext_data_current(0) <= ext_data0;
  ext_data_current(1) <= ext_data1;
  ext_data_current(2) <= ext_data2;
  ext_data_current(3) <= ext_data3;
  ext_data_current(4) <= ext_data4;
  ext_data_current(5) <= ext_data5;
  ext_data_current(6) <= ext_data6;
  ext_data_current(7) <= ext_data7;

  ext_status_current(0) <= ext_status0;
  ext_status_current(1) <= ext_status1;
  ext_status_current(2) <= ext_status2;
  ext_status_current(3) <= ext_status3;
  ext_status_current(4) <= ext_status4;
  ext_status_current(5) <= ext_status5;
  ext_status_current(6) <= ext_status6;
  ext_status_current(7) <= ext_status7;

  ext_ack0 <= ext_ack_current(0);
  ext_ack1 <= ext_ack_current(1);
  ext_ack2 <= ext_ack_current(2);
  ext_ack3 <= ext_ack_current(3);
  ext_ack4 <= ext_ack_current(4);
  ext_ack5 <= ext_ack_current(5);
  ext_ack6 <= ext_ack_current(6);
  ext_ack7 <= ext_ack_current(7);

  regs : process (clk, rstx)
    variable index : integer := 0;
  begin  -- process regs
    if rstx = '0' then
      r1data          <= (others => '0');
      ext_ack_current <= (others => (others => '0'));
      
    elsif clk'event and clk = '1' then
      if glock = '0' then

        -- reset the acknowledge signal after a while
        ext_ack_current <= (others => (others => '0'));

        if t1load = '1' then
          index := to_integer(unsigned(t1data));
          case t1opcode is
            when CAL_STREAM_IN_READ =>
              r1data                 <= ext_data_current(index);
              ext_ack_current(index) <= (0 => '1');
            when CAL_STREAM_IN_STATUS =>
              -- stream_in_status is placed in the least significant bits of r1reg
              r1data <= ext_status_current(index);
            when CAL_STREAM_IN_PEEK =>
              r1data <= ext_data_current(index);
            when others => null;
          end case;
        end if;
        
      end if;
    end if;
    
  end process regs;
  
end rtl;
