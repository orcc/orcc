-------------------------------------------------------------------------------
-- Title      : Broadcast
-- Project    : Orcc - TTA backend
-------------------------------------------------------------------------------
-- File       : broadcast.vhd
-- Author     : hyviquel  <herve.yviquel@irisa.fr>
-- Company    : IRISA
-- Created    : 2012-03-14
-- Last update: 2012-03-25
-- Platform   : All
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Description: Implementation of a generic hardware broadcast
-------------------------------------------------------------------------------
-- Copyright (c) 2012 
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author  Description
-- 2012-03-14  0.1      hyviquel        Created
-- 2012-03-20  1.0      hyviquel        First working version
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
-- Type package
-------------------------------------------------------------------------------
library ieee;
use ieee.Std_Logic_1164.all;

package broadcast_type is

  type array_logic_v32 is array (natural range <>) of std_logic_vector(31 downto 0);
  
end broadcast_type;

-------------------------------------------------------------------------------
-- Broadcast
-------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_misc.all;
use ieee.numeric_std.all;

library work;
use work.broadcast_type.all;

entity broadcast is
  
  generic (
    output_number : integer := 2);

  port (
    data_input     : in  std_logic_vector(31 downto 0);
    status_input   : in  std_logic_vector(31 downto 0);
    ack_input      : out std_logic;
    data_outputs   : out array_logic_v32(output_number-1 downto 0);
    status_outputs : in  array_logic_v32(output_number-1 downto 0);
    dv_outputs     : out std_logic_vector(output_number-1 downto 0);
    clk            : in  std_logic;
    rstx           : in  std_logic
    );

end broadcast;


architecture rtl of broadcast is

  type   state_type is (ready, waiting);  --defintion of state machine type
  --
  signal state         : state_type;      --declare the state machine signal.
  signal outputs_ready : std_logic_vector(output_number-1 downto 0);
  signal copy          : std_logic;
  signal count         : integer;

begin

  data_outputs <= (others => data_input);

  outputs_ready_gen : for i in status_outputs'range generate
    outputs_ready(i) <= or_reduce(status_outputs(i));
  end generate outputs_ready_gen;

  filter : process (clk, rstx)
  begin  -- process filter
    if rstx = '0' then
      copy <= '0';
    elsif clk'event and clk = '1' then
      copy <= or_reduce(status_input) and and_reduce(outputs_ready);
    end if;
  end process filter;

  ack_gen : process (clk, rstx)
  begin  -- process ack_gen
    if rstx = '0' then                  -- asynchronous reset (active low)
      ack_input  <= '0';
      dv_outputs <= (others => '0');
      count      <= 0;
      
    elsif clk'event and clk = '1' then
      ack_input  <= '0';
      dv_outputs <= (others => '0');
      case state is
        when ready =>
          if copy = '1' then            -- rising clock edge
            ack_input  <= '1';
            dv_outputs <= (others => '1');
            state      <= waiting;
          end if;
        when waiting =>
          if count = 2 then
            count <= 0;
            state <= ready;
          else
            count <= count + 1;
          end if;
        when others =>
          null;
      end case;
    end if;
  end process ack_gen;

end rtl;


