-------------------------------------------------------------------------------
-- Title      : Single Port RAM (Altera)
-- Project    : Orcc - TTA
-------------------------------------------------------------------------------
-- File       : altera_ram_1p.vhd
-- Author     : Herve Yviquel
-- Company    : IRISA
-- Created    : 
-- Last update: 2012-05-15
-- Platform   : 
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2012, IRISA
-- All rights reserved.
-- 
-- Redistribution and use in source and binary forms, with or without
-- modification, are permitted provided that the following conditions are met:
-- 
--  -- Redistributions of source code must retain the above copyright notice,
--     this list of conditions and the following disclaimer.
--  -- Redistributions in binary form must reproduce the above copyright notice,
--     this list of conditions and the following disclaimer in the documentation
--     and/or other materials provided with the distribution.
--  -- Neither the name of the IRISA nor the names of its contributors may be used
--     to endorse or promote products derived from this software without specific 
--     prior written permission.
-- 
-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
-- AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
-- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
-- ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
-- LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
-- CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
-- SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
-- INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
-- STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
-- WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
-- SUCH DAMAGE.
-------------------------------------------------------------------------------
-- Revisions  :
-- Date        Version  Author       Description
-- 2012-05-15  1.0      hyviquel     Created
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

library altera_mf;
use altera_mf.all;

library work;
-------------------------------------------------------------------------------


entity dram_1p is
  generic (
      depth         : integer;
      addr_width    : integer;
      byte_width    : integer;
      bytes         : integer;
      init_file     : string;
      device_family : string);
  port (
    clk     : in  std_logic;
    --
    wren    : in  std_logic;
    address : in  std_logic_vector(addr_width-1 downto 0);
    byteen  : in  std_logic_vector(bytes-1 downto 0);
    data    : in  std_logic_vector(bytes*byte_width-1 downto 0);
    queue   : out std_logic_vector(bytes*byte_width-1 downto 0);
    --
    rst_n   : in  std_logic);
end dram_1p;

-------------------------------------------------------------------------------


architecture altera_dram_1p of dram_1p is

begin

  ram_component : altera_mf_components.altsyncram
    generic map (
      byte_size                     => byte_width,
      clock_enable_input_a          => "BYPASS",
      clock_enable_output_a         => "BYPASS",
      init_file                     => init_file,
      intended_device_family        => device_family,
      lpm_hint                      => "ENABLE_RUNTIME_MOD=NO",
      lpm_type                      => "altsyncram",
      numwords_a                    => depth,
      operation_mode                => "SINGLE_PORT",
      outdata_aclr_a                => "NONE",
      outdata_reg_a                 => "UNREGISTERED",
      power_up_uninitialized        => "FALSE",
      ram_block_type                => "AUTO",
      read_during_write_mode_port_a => "NEW_DATA_NO_NBE_READ",
      widthad_a                     => addr_width,
      width_a                       => bytes*byte_width,
      width_byteena_a               => bytes
      )
    port map (
      clock0    => clk,
      wren_a    => wren,
      address_a => address,
      byteena_a => byteen,
      data_a    => data,
      q_a       => queue
      );

end altera_dram_1p;
