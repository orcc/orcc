-------------------------------------------------------------------------------
-- Title      : Counter
-- Project    : Orcc - TTA back-end
-------------------------------------------------------------------------------
-- File       : counter.vhd
-- Author     : Hervé Yviquel  <herve.yviquel@irisa.fr>
-- Company    : IRISA
-- Created    : 2011-10-17
-- Last update: 2011-10-17
-- Platform   : FPGA
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Description: 
-------------------------------------------------------------------------------
-- Copyright (c) 2011 
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2011-10-17  1.0      hyviquel	Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------

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

-------------------------------------------------------------------------------

architecture behavioral of counter is

  signal s_count : std_logic_vector(15 downto 0) := (others => '0');

begin

  count <= s_count;

  process (clk, rst)
    variable tmp : integer range 0 to 2**16-1;
  begin
    if (rst = '0') then
      s_count <= (others => '0');
      tmp   := 0;
      top   <= '0';
      
    elsif (clk'event and clk = '1') then
      top <= '0';
      if(valid = '1') then
        tmp := to_integer(unsigned(s_count));
        if(tmp = nb_count-1) then
          top   <= '1';
          s_count <= (others => '0');
        else
          s_count <= std_logic_vector(to_unsigned(tmp + 1, 16));
        end if;
      end if;
    end if;
  end process;

end behavioral;

-------------------------------------------------------------------------------
