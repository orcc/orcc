library ieee;
use ieee.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use ieee.numeric_std.all;

-- synthesis translate_off
library XilinxCoreLib;
-- synthesis translate_on

library UNISIM;
use UNISIM.VCOMPONENTS.all;

entity fifo is
  generic
    (
      size          : integer := 256;
      width         : integer := 32;
      widthu        : integer := 8;
      device_family : string  := "virtex5"
      );
  port
    (
      wrreq        : in  std_logic;
      rst_n        : in  std_logic;
      clk          : in  std_logic;
      rdreq        : in  std_logic;
      data         : in  std_logic_vector(width-1 downto 0);
      q            : out std_logic_vector(width-1 downto 0);
      nb_freerooms : out std_logic_vector(31 downto 0);
      nb_tokens    : out std_logic_vector(31 downto 0)
      );
end fifo;


architecture rtl_fifo of fifo is

  signal nb_tokens_i : std_logic_vector(31 downto 0) := (others => '0');
  signal rst         : std_logic;
  signal empty       : std_logic;

begin

  rst          <= not(rst_n);
  nb_tokens    <= nb_tokens_i;
  nb_freerooms <= std_logic_vector(to_unsigned(size - to_integer(unsigned(nb_tokens_i)), 32));

  token_count : process(clk)
  begin
    if (clk'event and clk = '1') then
      if ((wrreq = '1' and nb_tokens_i(widthu) = '0') and (rdreq = '1' and empty = '0')) then
        nb_tokens_i(widthu-1 downto 0) <= nb_tokens_i(widthu-1 downto 0);
      else
        if (wrreq = '1' and s_status(widthu) = '0') then
          nb_tokens_i(widthu-1 downto 0) <= nb_tokens_i(widthu-1 downto 0) + 1;
        end if;
        if (rdreq = '1' and empty = '0') then
          nb_tokens_i(widthu-1 downto 0) <= nb_tokens_i(widthu-1 downto 0) - 1;
        end if;
      end if;
    end if;
  end process token_count;

  fifo_component : XilinxCoreLib.fifo_generator_v6_1(behavioral)
    generic map(
      c_has_int_clk                  => 0,
      c_wr_response_latency          => 1,
      c_rd_freq                      => 1,
      c_has_srst                     => 0,
      c_enable_rst_sync              => 1,
      c_has_rd_data_count            => 0,
      c_din_width                    => width,
      c_has_wr_data_count            => 0,
      c_full_flags_rst_val           => 1,
      c_implementation_type          => 0,
      c_family                       => device_family,
      c_use_embedded_reg             => 0,
      c_has_wr_rst                   => 0,
      c_wr_freq                      => 1,
      c_use_dout_rst                 => 1,
      c_underflow_low                => 0,
      c_has_meminit_file             => 0,
      c_has_overflow                 => 0,
      c_preload_latency              => 0,
      c_dout_width                   => width,
      c_msgon_val                    => 1,
      c_rd_depth                     => size,
      c_default_value                => "BlankString",
      c_mif_file_name                => "BlankString",
      c_error_injection_type         => 0,
      c_has_underflow                => 0,
      c_has_rd_rst                   => 0,
      c_has_almost_full              => 0,
      c_has_rst                      => 1,
      c_data_count_width             => widthu+1,
      c_has_wr_ack                   => 0,
      c_use_ecc                      => 0,
      c_wr_ack_low                   => 0,
      c_common_clock                 => 1,
      c_rd_pntr_width                => widthu,
      c_use_fwft_data_count          => 1,
      c_has_almost_empty             => 0,
      c_rd_data_count_width          => widthu+1,
      c_enable_rlocs                 => 0,
      c_wr_pntr_width                => widthu,
      c_overflow_low                 => 0,
      c_prog_empty_type              => 0,
      c_optimization_mode            => 0,
      c_wr_data_count_width          => widthu+1,
      c_preload_regs                 => 1,
      c_dout_rst_val                 => "0",
      c_has_data_count               => 0,
      c_prog_full_thresh_negate_val  => size-2,
      c_wr_depth                     => size,
      c_prog_empty_thresh_negate_val => 5,
      c_prog_empty_thresh_assert_val => 4,
      c_has_valid                    => 0,
      c_init_wr_pntr_val             => 0,
      c_prog_full_thresh_assert_val  => size-1,
      c_use_fifo16_flags             => 0,
      c_has_backup                   => 0,
      c_valid_low                    => 0,
      c_prim_fifo_type               => "512x36",
      c_count_type                   => 0,
      c_prog_full_type               => 0,
      c_memory_type                  => 1)
    port map (
      clk   => clk,
      rst   => rst,
      din   => data,
      wr_en => wrreq,
      rd_en => rdreq,
      dout  => q,
      full  => nb_tokens_i(widthu),
      empty => empty
      );

end rtl_fifo;
