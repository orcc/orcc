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
    r1data   : out std_logic_vector(31 downto 0);
    clk      : in  std_logic;
    rstx     : in  std_logic;
    glock    : in  std_logic;

    -- external interface
    ext_data   : out std_logic_vector(31 downto 0);  -- the actual data to stream out
    ext_status : in  std_logic_vector(31 downto 0);  -- status signal from outside
    ext_dv     : out std_logic_vector(0 downto 0) -- datavalid signal
    );

end cal_stream_out;


architecture rtl of cal_stream_out is
  signal r1reg   : std_logic_vector(31 downto 0);
  signal datareg : std_logic_vector(31 downto 0);
  signal dvreg   : std_logic_vector(0 downto 0);

begin
  
  regs : process (clk, rstx)
  begin  -- process regs
    if rstx = '0' then
      datareg <= (others => '0');
      dvreg   <= (others => '0');
    elsif clk'event and clk = '1' then
      if glock = '0' then

        -- reset the datavalid signal
        dvreg <= (others => '0');

        if t1load = '1' then
          case t1opcode is
            when CAL_STREAM_OUT_WRITE =>
              datareg <= t1data;
              dvreg   <= (0 => '1', others => '0');
              -- enable the datavalid signal for a while
            when others => null;
          end case;
        end if;

      end if;
    end if;
  end process regs;

  --r1data  <= r1reg;
  r1data   <= ext_status;
  ext_data <= datareg;
  ext_dv   <= dvreg;
  
end rtl;
