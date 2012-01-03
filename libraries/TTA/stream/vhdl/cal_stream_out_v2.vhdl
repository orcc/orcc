-------------------------------------------------------------------------------
-- Opcode package
-------------------------------------------------------------------------------
library IEEE;
use IEEE.Std_Logic_1164.all;

package opcodes_cal_stream_out is

  constant CAL_STREAM_OUT_STATUS : std_logic_vector(1-1 downto 0) := "0";
  constant CAL_STREAM_OUT_WRITE  : std_logic_vector(1-1 downto 0) := "1";
  
end opcodes_cal_stream_out;

-------------------------------------------------------------------------------
-- Stream out unit
-------------------------------------------------------------------------------
library IEEE;
use IEEE.Std_Logic_1164.all;
use IEEE.numeric_std.all;
use work.opcodes_cal_stream_out.all;

entity cal_stream_out is

  port (
    t1data   : in  std_logic_vector(31 downto 0);
    t1load   : in  std_logic;
    t1opcode : in  std_logic_vector(1-1 downto 0);
    o1data   : in  std_logic_vector(31 downto 0);
    o1load   : in  std_logic;
    r1data   : out std_logic_vector(31 downto 0);
    clk      : in  std_logic;
    rstx     : in  std_logic;
    glock    : in  std_logic;

    -- external port interface
    -- FIFO n°0
    ext_data0   : out std_logic_vector(31 downto 0);  -- acquired data comes through this
    ext_status0 : in  std_logic_vector(31 downto 0);  -- status signal provided from outside
    ext_dv0     : out std_logic_vector(0 downto 0);  -- data acknowledge to outside
    -- FIFO n°1
    ext_data1   : out std_logic_vector(31 downto 0);
    ext_status1 : in  std_logic_vector(31 downto 0);
    ext_dv1     : out std_logic_vector(0 downto 0);
    -- FIFO n°2
    ext_data2   : out std_logic_vector(31 downto 0);
    ext_status2 : in  std_logic_vector(31 downto 0);
    ext_dv2     : out std_logic_vector(0 downto 0);
    -- FIFO n°3
    ext_data3   : out std_logic_vector(31 downto 0);
    ext_status3 : in  std_logic_vector(31 downto 0);
    ext_dv3     : out std_logic_vector(0 downto 0);
    -- FIFO n°4
    ext_data4   : out std_logic_vector(31 downto 0);
    ext_status4 : in  std_logic_vector(31 downto 0);
    ext_dv4     : out std_logic_vector(0 downto 0);
    -- FIFO n°5
    ext_data5   : out std_logic_vector(31 downto 0);
    ext_status5 : in  std_logic_vector(31 downto 0);
    ext_dv5     : out std_logic_vector(0 downto 0);
    -- FIFO n°6
    ext_data6   : out std_logic_vector(31 downto 0);
    ext_status6 : in  std_logic_vector(31 downto 0);
    ext_dv6     : out std_logic_vector(0 downto 0);
    -- FIFO n°7
    ext_data7   : out std_logic_vector(31 downto 0);
    ext_status7 : in  std_logic_vector(31 downto 0);
    ext_dv7     : out std_logic_vector(0 downto 0)
    );

end cal_stream_out;


architecture rtl of cal_stream_out is
  signal r1reg   : std_logic_vector(31 downto 0);
  signal datareg : std_logic_vector(31 downto 0);
  signal dvreg   : std_logic_vector(0 downto 0);

  type data_array is array (0 to 7) of std_logic_vector(31 downto 0);
  type ack_array is array (0 to 7) of std_logic_vector(0 downto 0);

  signal ext_data_current   : data_array;
  signal ext_status_current : data_array;
  signal ext_dv_current     : ack_array;

begin
  
  ext_data0 <= ext_data_current(0);
  ext_data1 <= ext_data_current(1);
  ext_data2 <= ext_data_current(2);
  ext_data3 <= ext_data_current(3);
  ext_data4 <= ext_data_current(4);
  ext_data5 <= ext_data_current(5);
  ext_data6 <= ext_data_current(6);
  ext_data7 <= ext_data_current(7);

  ext_status_current(0) <= ext_status0;
  ext_status_current(1) <= ext_status1;
  ext_status_current(2) <= ext_status2;
  ext_status_current(3) <= ext_status3;
  ext_status_current(4) <= ext_status4;
  ext_status_current(5) <= ext_status5;
  ext_status_current(6) <= ext_status6;
  ext_status_current(7) <= ext_status7;

  ext_dv0 <= ext_dv_current(0);
  ext_dv1 <= ext_dv_current(1);
  ext_dv2 <= ext_dv_current(2);
  ext_dv3 <= ext_dv_current(3);
  ext_dv4 <= ext_dv_current(4);
  ext_dv5 <= ext_dv_current(5);
  ext_dv6 <= ext_dv_current(6);
  ext_dv7 <= ext_dv_current(7);

  regs : process (clk, rstx)
    variable index : integer := 0;
  begin  -- process regs
    if rstx = '0' then
      ext_data_current(0) <= (others => '0');
      ext_data_current(1) <= (others => '0');
      ext_data_current(2) <= (others => '0');
      ext_data_current(3) <= (others => '0');
      ext_data_current(4) <= (others => '0');
      ext_data_current(5) <= (others => '0');
      ext_data_current(6) <= (others => '0');
      ext_data_current(7) <= (others => '0');

      ext_dv0 <= (others => '0');
      ext_dv1 <= (others => '0');
      ext_dv2 <= (others => '0');
      ext_dv3 <= (others => '0');
      ext_dv4 <= (others => '0');
      ext_dv5 <= (others => '0');
      ext_dv6 <= (others => '0');
      ext_dv7 <= (others => '0');
      
    elsif clk'event and clk = '1' then
      if glock = '0' then

        -- reset the datavalid signal
        ext_dv_current(0) <= (others => '0');
        ext_dv_current(1) <= (others => '0');
        ext_dv_current(2) <= (others => '0');
        ext_dv_current(3) <= (others => '0');
        ext_dv_current(4) <= (others => '0');
        ext_dv_current(5) <= (others => '0');
        ext_dv_current(6) <= (others => '0');
        ext_dv_current(7) <= (others => '0');

        if t1load = '1' then
          index := to_integer(unsigned(t1data));

          case t1opcode is
            when CAL_STREAM_OUT_WRITE =>
              ext_data_current(index) <= o1data;
              ext_dv_current(index)   <= (0 => '1', others => '0');
              -- enable the datavalid signal for a while
            when CAL_STREAM_OUT_STATUS =>
              r1data <= ext_status_current(index);
            when others => null;
          end case;
        end if;

      end if;
    end if;
    
  end process regs;
  
end rtl;
