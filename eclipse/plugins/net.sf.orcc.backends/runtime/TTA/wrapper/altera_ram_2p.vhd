-------------------------------------------------------------------------------
-- Title      : True Dual Port RAM (Altera)
-- Project    : Orcc - TTA
-------------------------------------------------------------------------------
-- File       : inferredRAM_2p.vhd
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

entity dram_2p is
  generic (
    depth      : integer;
    addr_width : integer;
    byte_width : integer;
    bytes      : integer;
    initVal    : integer := 0);
  port (
    clk        : in  std_logic;
    --
    wren_p1    : in  std_logic;
    address_p1 : in  std_logic_vector(addr_width-1 downto 0);
    byteen_p1  : in  std_logic_vector(bytes-1 downto 0);
    data_p1    : in  std_logic_vector(bytes*byte_width-1 downto 0);
    queue_p1   : out std_logic_vector(bytes*byte_width-1 downto 0);
    --
    wren_p2    : in  std_logic;
    address_p2 : in  std_logic_vector(addr_width-1 downto 0);
    byteen_p2  : in  std_logic_vector(bytes-1 downto 0);
    data_p2    : in  std_logic_vector(bytes*byte_width-1 downto 0);
    queue_p2   : out std_logic_vector(bytes*byte_width-1 downto 0);
    --
    rst_n      : in  std_logic);
end dram_2p;

-------------------------------------------------------------------------------


architecture altera_dram_2p of dram_2p is

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  signal iqueue_p1 : std_logic_vector (bytes*byte_width-1 downto 0);
  signal iqueue_p2 : std_logic_vector (bytes*byte_width-1 downto 0);
  -----------------------------------------------------------------------------

begin

  queue_p1 <= iqueue_p1(bytes*byte_width-1 downto 0);
  queue_p2 <= iqueue_p2(bytes*byte_width-1 downto 0);

  ram_component : altera_mf_components.altsyncram
    generic map (
      address_reg_b                      => "CLOCK0",
      byteena_reg_b                      => "CLOCK0",
      byte_size                          => byte_width,
      clock_enable_input_a               => "BYPASS",
      clock_enable_input_b               => "BYPASS",
      clock_enable_output_a              => "BYPASS",
      clock_enable_output_b              => "BYPASS",
      indata_reg_b                       => "CLOCK0",
      intended_device_family             => "Cyclone IV GX",
      lpm_type                           => "altsyncram",
      numwords_a                         => depth,
      numwords_b                         => depth,
      operation_mode                     => "BIDIR_DUAL_PORT",
      outdata_aclr_a                     => "NONE",
      outdata_aclr_b                     => "NONE",
      outdata_reg_a                      => "CLOCK0",
      outdata_reg_b                      => "CLOCK0",
      power_up_uninitialized             => "FALSE",
      read_during_write_mode_mixed_ports => "DONT_CARE",
      read_during_write_mode_port_a      => "DONT_CARE",
      read_during_write_mode_port_b      => "DONT_CARE",
      widthad_a                          => addr_width,
      widthad_b                          => addr_width,
      width_a                            => bytes*byte_width,
      width_b                            => bytes*byte_width,
      width_byteena_a                    => bytes,
      width_byteena_b                    => bytes,
      wrcontrol_wraddress_reg_b          => "CLOCK0"
      )
    port map (
      clock0    => clk,
      wren_a    => wren_p1,
      address_a => address_p1,
      byteena_a => byteen_p1,
      data_a    => data_p1,
      q_a       => iqueue_p1,
      wren_b    => wren_p2,
      address_b => address_p2,
      byteena_b => byteen_p2,
      data_b    => data_p2,
      q_b       => iqueue_p2
      );

end altera_dram_2p;
