/*
 * Copyright (c) 2012, IETR/INSA of Rennes
 * All rights reserved.
 * 4
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * about
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.backends.c.hls

import java.io.File
import java.util.Map
import net.sf.orcc.df.Network
import net.sf.orcc.util.OrccUtil

/**
 * Compile top Network c source code 
 *  
 * @author Antoine Lorence and Khaled Jerbi
 * 
 */
class NetworkPrinter extends net.sf.orcc.backends.c.NetworkPrinter {

	new(Network network, Map<String, Object> options) {
		super(network, options)
	}

	override print(String targetFolder) {
		val contentVhdlTop = fifoFileContent
		val contentSimPack = fifoSimPackContent
		val SimPackFile = new File(targetFolder + File::separator + "TopVHDL" + File::separator + "sim_package" + ".vhd")
		val FifoVhdlFile = new File(
			targetFolder + File::separator + "TopVHDL" + File::separator + "ram_tab" + ".vhd")
		if (needToWriteFile(contentVhdlTop, FifoVhdlFile)) {
			OrccUtil::printFile(contentVhdlTop, FifoVhdlFile)
			OrccUtil::printFile(contentSimPack, SimPackFile)

			return 0
		} else {
			return 1
		}
	}

	/*
	 * Generic FIFO
	 */
	def fifoFileContent() '''
		library ieee; 
use ieee.std_logic_1164.all; 
use ieee.std_logic_unsigned.all;
entity ram_tab is 
    generic(
            dwidth     : integer := 32; 
            awidth     : integer := «closestLog_2(fifoSize)»; 
            mem_size    : integer := «fifoSize»
    ); 
    port (
          addr0     : in std_logic_vector(awidth-1 downto 0); 
          ce0       : in std_logic; 
          q0        : out std_logic_vector(dwidth-1 downto 0);
          addr1     : in std_logic_vector(awidth-1 downto 0); 
          ce1       : in std_logic; 
          d1        : in std_logic_vector(dwidth-1 downto 0); 
          we1       : in std_logic; 
          clk        : in std_logic 
    ); 
end entity; 
architecture rtl of ram_tab is 
type mem_array is array (0 to mem_size-1) of std_logic_vector (dwidth-1 downto 0); 
shared variable ram : mem_array := (others=>(others=>'0'));
begin 
 p_memory_access_1: process (clk)  
begin 
    if (clk'event and clk = '1') then
	
        if (ce1 = '1') then 
            if (we1 = '1') then 
                ram(CONV_INTEGER(addr1)) := d1; 
            end if;
        end if;
		
			
    end if;
   
end process;
p_memory_access_0: process (clk)  
begin 
    if (clk'event and clk = '1') then
       if (ce0 = '1') then 
            q0 <= ram(CONV_INTEGER(addr0)); 
			
       end if;
    end if;
end process;
end rtl;
		'''

	/*
		 * Sim Package
		 */
	def fifoSimPackContent() '''
		library ieee;
		use ieee.std_logic_1164.all;
		use std.textio.all;
		
		
		package sim_package is
		
		-- converts std_logic into a character
		  function chr(sl : std_logic) return character;
		
		-- converts std_logic into a string (1 to 1)
		  function str(sl : std_logic) return string;
		
		-- converts std_logic_vector into a string (binary base)
		  function str(slv : std_logic_vector) return string;
		
		-- converts boolean into a string
		  function str(b : boolean) return string;
		
		-- converts an integer into a single character
		-- (can also be used for hex conversion and other bases)
		  function chr(int : integer) return character;
		
		-- converts integer into string using specified base
		  function str(int : integer; base : integer) return string;
		
		-- converts integer to string, using base 10
		  function str(int : integer) return string;
		
		-- convert std_logic_vector into a string in hex format
		  function hstr(slv : std_logic_vector) return string;
		
		
		end sim_package;
		
		
		
		package body sim_package is
		
		
		-- converts std_logic into a character
		
		  function chr(sl : std_logic) return character is
		    variable c : character;
		  begin
		    case sl is
		      when 'U' => c := 'U';
		      when 'X' => c := 'X';
		      when '0' => c := '0';
		      when '1' => c := '1';
		      when 'Z' => c := 'Z';
		      when 'W' => c := 'W';
		      when 'L' => c := 'L';
		      when 'H' => c := 'H';
		      when '-' => c := '-';
		    end case;
		    return c;
		  end chr;
		
		
		
		-- converts std_logic into a string (1 to 1)
		
		  function str(sl : std_logic) return string is
		    variable s : string(1 to 1);
		  begin
		    s(1) := chr(sl);
		    return s;
		  end str;
		
		
		
		-- converts std_logic_vector into a string (binary base)
		-- (this also takes care of the fact that the range of
		-- a string is natural while a std_logic_vector may
		-- have an integer range)
		
		  function str(slv : std_logic_vector) return string is
		    variable result : string (1 to slv'length);
		    variable r      : integer;
		  begin
		    r := 1;
		    for i in slv'range loop
		      result(r) := chr(slv(i));
		      r         := r + 1;
		    end loop;
		    return result;
		  end str;
		
		
		  function str(b : boolean) return string is
		
		  begin
		    if b then
		      return "true";
		    else
		      return "false";
		    end if;
		  end str;
		
		
		-- converts an integer into a character
		-- for 0 to 9 the obvious mapping is used, higher
		-- values are mapped to the characters A-Z
		-- (this is usefull for systems with base > 10)
		-- (adapted from Steve Vogwell's posting in comp.lang.vhdl)
		
		  function chr(int : integer) return character is
		    variable c : character;
		  begin
		    case int is
		      when 0      => c := '0';
		      when 1      => c := '1';
		      when 2      => c := '2';
		      when 3      => c := '3';
		      when 4      => c := '4';
		      when 5      => c := '5';
		      when 6      => c := '6';
		      when 7      => c := '7';
		      when 8      => c := '8';
		      when 9      => c := '9';
		      when 10     => c := 'A';
		      when 11     => c := 'B';
		      when 12     => c := 'C';
		      when 13     => c := 'D';
		      when 14     => c := 'E';
		      when 15     => c := 'F';
		      when 16     => c := 'G';
		      when 17     => c := 'H';
		      when 18     => c := 'I';
		      when 19     => c := 'J';
		      when 20     => c := 'K';
		      when 21     => c := 'L';
		      when 22     => c := 'M';
		      when 23     => c := 'N';
		      when 24     => c := 'O';
		      when 25     => c := 'P';
		      when 26     => c := 'Q';
		      when 27     => c := 'R';
		      when 28     => c := 'S';
		      when 29     => c := 'T';
		      when 30     => c := 'U';
		      when 31     => c := 'V';
		      when 32     => c := 'W';
		      when 33     => c := 'X';
		      when 34     => c := 'Y';
		      when 35     => c := 'Z';
		      when others => c := '?';
		    end case;
		    return c;
		  end chr;
		
		
		
		-- convert integer to string using specified base
		-- (adapted from Steve Vogwell's posting in comp.lang.vhdl)
		
		  function str(int : integer; base : integer) return string is
		
		    variable temp    : string(1 to 10);
		    variable num     : integer;
		    variable abs_int : integer;
		    variable len     : integer := 1;
		    variable power   : integer := 1;
		
		  begin
		
		-- bug fix for negative numbers
		    abs_int := abs(int);
		
		    num := abs_int;
		
		    while num >= base loop              -- Determine how many
		      len := len + 1;                   -- characters required
		      num := num / base;                -- to represent the
		    end loop;  -- number.
		
		    for i in len downto 1 loop                 -- Convert the number to
		      temp(i) := chr(abs_int/power mod base);  -- a string starting
		      power   := power * base;                 -- with the right hand
		    end loop;  -- side.
		
		-- return result and add sign if required
		    if int < 0 then
		      return '-'& temp(1 to len);
		    else
		      return temp(1 to len);
		    end if;
		
		  end str;
		
		
		-- convert integer to string, using base 10
		  function str(int : integer) return string is
		
		  begin
		
		    return str(int, 10);
		
		  end str;
		
		-- converts a std_logic_vector into a hex string.
		  function hstr(slv : std_logic_vector) return string is
		    variable hexlen : integer;
		    variable longslv : std_logic_vector(67 downto 0) := (others =>
		                                                         '0');
		    variable hex     : string(1 to 16);
		    variable fourbit : std_logic_vector(3 downto 0);
		  begin
		    hexlen := (slv'left+1)/4;
		    if (slv'left+1) mod 4 /= 0 then
		      hexlen := hexlen + 1;
		    end if;
		    longslv(slv'left downto 0) := slv;
		    for i in (hexlen -1) downto 0 loop
		      fourbit := longslv(((i*4)+3) downto (i*4));
		      case fourbit is
		        when "0000" => hex(hexlen -I) := '0';
		        when "0001" => hex(hexlen -I) := '1';
		        when "0010" => hex(hexlen -I) := '2';
		        when "0011" => hex(hexlen -I) := '3';
		        when "0100" => hex(hexlen -I) := '4';
		        when "0101" => hex(hexlen -I) := '5';
		        when "0110" => hex(hexlen -I) := '6';
		        when "0111" => hex(hexlen -I) := '7';
		        when "1000" => hex(hexlen -I) := '8';
		        when "1001" => hex(hexlen -I) := '9';
		        when "1010" => hex(hexlen -I) := 'A';
		        when "1011" => hex(hexlen -I) := 'B';
		        when "1100" => hex(hexlen -I) := 'C';
		        when "1101" => hex(hexlen -I) := 'D';
		        when "1110" => hex(hexlen -I) := 'E';
		        when "1111" => hex(hexlen -I) := 'F';
		        when "ZZZZ" => hex(hexlen -I) := 'z';
		        when "UUUU" => hex(hexlen -I) := 'u';
		        when "XXXX" => hex(hexlen -I) := 'x';
		        when others => hex(hexlen -I) := '?';
		      end case;
		    end loop;
		    return hex(1 to hexlen);
		  end hstr;
		  
		end sim_package;
	'''

	def closestLog_2(int x) {
		var p = 1;
		var r = 0;
		while (p < x) {
			p = p * 2
			r = r + 1
		}
		return r;
	}
}
