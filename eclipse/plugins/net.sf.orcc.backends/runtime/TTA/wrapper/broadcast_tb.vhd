-------------------------------------------------------------------------------
-- Title      : Testbench for design "broadcast"
-- Project    : 
-------------------------------------------------------------------------------
-- File       : broadcast_tb.vhd
-- Author     : hyviquel  <hyviquel@laptopy>
-- Company    : 
-- Created    : 2012-03-20
-- Last update: 2012-03-20
-- Platform   : 
-- Standard   : VHDL'87
-------------------------------------------------------------------------------
-- Description: 
-------------------------------------------------------------------------------
-- Copyright (c) 2012 
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2012-03-20  1.0      hyviquel        Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use ieee.numeric_std.all;

library work;
use work.broadcast_type.all;

-------------------------------------------------------------------------------

entity broadcast_tb is

end broadcast_tb;

-------------------------------------------------------------------------------
architecture tb of broadcast_tb is

  constant PERIOD         : time    := 20 ns;
  constant SIZE           : integer := 4;
  --
  signal   data_input     : std_logic_vector(31 downto 0);
  signal   status_input   : std_logic_vector(31 downto 0);
  signal   ack_input      : std_logic;
  signal   data_outputs   : array_logic_v32(SIZE-1 downto 0);
  signal   status_outputs : array_logic_v32(SIZE-1 downto 0);
  signal   dv_outputs     : std_logic_vector(SIZE-1 downto 0);
  signal   clk            : std_logic := '0';
  signal   rstx           : std_logic;

begin  -- tb

  bcast : entity work.broadcast
    generic map (
      output_number => SIZE)
    port map (
      data_input     => data_input,
      status_input   => status_input,
      ack_input      => ack_input,
      data_outputs   => data_outputs,
      status_outputs => status_outputs,
      dv_outputs     => dv_outputs,
      clk            => clk,
      rstx           => rstx);

  -- clock generation
  clk <= not clk after PERIOD/2;

  -- reset generation
  rst_proc : process
  begin
    rstx <= '0';
    wait for 100 ns;
    rstx <= '1';
    wait;
  end process;

  -- input generation
  input_proc : process (clk, rstx)
  begin  -- process
    if rstx = '0' then                 -- asynchronous reset (active low)
      data_input   <= (others => '0');
      status_input <= std_logic_vector(to_unsigned(1, 32));
    elsif clk'event and clk = '1' then  -- rising clock edge
      status_input <= std_logic_vector(to_unsigned(1, 32));
      if ack_input = '1' then
        data_input   <= std_logic_vector(to_unsigned(to_integer(unsigned(data_input)) + 1, 32));
        status_input <= (others => '0');
      end if;
    end if;
  end process;

  status_outputs(0) <= std_logic_vector(to_unsigned(1, 32));
  status_outputs(1) <= std_logic_vector(to_unsigned(1, 32));
  status_outputs(2) <= std_logic_vector(to_unsigned(1, 32));
  status_outputs(3) <= std_logic_vector(to_unsigned(1, 32));
end tb;
