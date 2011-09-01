-------------------------------------------------------------------------------
-- Title      : orcc_package
-- Project    : ORCC
-------------------------------------------------------------------------------
-- File       : sim_package.vhd
-- Author     : sarma.nedunuri@gmail.com
-- Created    : 02-15-2006
-- Platform   : SIMULATION ONLY
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- downloaded from http://www.velocityreviews.com/forums/t55417-read-from-file.html
-------------------------------------------------------------------------------


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
