------------------------------------------------------------------------------
-- Generated from parseheaders actor
------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library work;
use work.orcc_package.all;

entity parseheaders is
  port (
    -- Inputs
    clock                     : in  std_logic;
    reset_n                   : in  std_logic;
    parseheaders_bits_data    : in  std_logic;
    parseheaders_bits_empty   : in  std_logic;
    parseheaders_bits_read    : out std_logic;
    -- Outputs
    parseheaders_BTYPE_full   : in  std_logic;
    parseheaders_BTYPE_data   : out std_logic_vector(12 -1 downto 0);
    parseheaders_BTYPE_write  : out std_logic;
    parseheaders_MV_full      : in  std_logic;
    parseheaders_MV_data      : out std_logic_vector(9 -1 downto 0);
    parseheaders_MV_write     : out std_logic;
    parseheaders_RUN_full     : in  std_logic;
    parseheaders_RUN_data     : out std_logic_vector(8 -1 downto 0);
    parseheaders_RUN_write    : out std_logic;
    parseheaders_VALUE_full   : in  std_logic;
    parseheaders_VALUE_data   : out std_logic_vector(13 -1 downto 0);
    parseheaders_VALUE_write  : out std_logic;
    parseheaders_LAST_full    : in  std_logic;
    parseheaders_LAST_data    : out std_logic;
    parseheaders_LAST_write   : out std_logic;
    parseheaders_WIDTH_full   : in  std_logic;
    parseheaders_WIDTH_data   : out std_logic_vector(16 -1 downto 0);
    parseheaders_WIDTH_write  : out std_logic;
    parseheaders_HEIGHT_full  : in  std_logic;
    parseheaders_HEIGHT_data  : out std_logic_vector(16 -1 downto 0);
    parseheaders_HEIGHT_write : out std_logic);  
end parseheaders;


architecture rtl_parseheaders of parseheaders is

  ---------------------------------------------------------------------------
  -- Signal & constant declaration
  signal VOL_START_CODE                : integer range 2147483647 downto -2147483648;
  signal ASPECT_RATIO_INFO_LENGTH      : integer range 2147483647 downto -2147483648;
  signal ASPECT_RATIO_INFO_IS_DETAILED : integer range 2147483647 downto -2147483648;
  signal VOP_START_CODE                : integer range 2147483647 downto -2147483648;
  signal VOP_PREDICTION_LENGTH         : integer range 2147483647 downto -2147483648;
  signal P_VOP                         : integer range 2147483647 downto -2147483648;
  signal I_VOP                         : integer range 2147483647 downto -2147483648;
  signal bits_to_read_count            : integer range 63 downto -64;
  signal read_result_in_progress       : integer range 2147483647 downto -2147483648;
  signal bit_count                     : integer range 7 downto -8;
  signal mylog                         : integer range 63 downto -64;
  signal vol_width                     : integer range 127 downto -128;
  signal vol_height                    : integer range 127 downto -128;
  signal mbx                           : integer range 127 downto -128;
  signal mby                           : integer range 127 downto -128;
  signal prediction_is_IVOP            : std_logic;
  signal prediction_is_PVOP            : std_logic;
  signal prediction_is_BVOP            : std_logic;
  signal comp                          : integer range 7 downto -8;
  signal fcode                         : integer range 7 downto -8;
  signal cbp                           : integer range 63 downto -64;
  signal acpredflag                    : std_logic;
  signal btype_is_INTRA                : std_logic;
  signal cbpc                          : integer range 63 downto -64;
  signal fourmvflag                    : std_logic;
  signal ac_coded                      : std_logic;
  signal mvcomp                        : integer range 7 downto -8;
  signal dc_bits                       : integer range 15 downto -16;
  signal msb_result                    : integer range 8191 downto -8192;
  signal b_last                        : std_logic;
  signal level_lookup_inter            : integer range 4095 downto -4096;
  signal level_lookup_intra            : integer range 4095 downto -4096;
  signal run_lookup_inter              : integer range 4095 downto -4096;
  signal run_lookup_intra              : integer range 4095 downto -4096;
  type memory_type_vld_table is array (760 -1 downto 0) of integer range 524287 downto -524288;
  signal vld_table                     : memory_type_vld_table;
  signal vld_index                     : integer range 2047 downto -2048;
  signal vld_codeword                  : integer range 524287 downto -524288;


  ---------------------------------------------------------------------------
  -- Functions and procedures
  function mask_bits(v : integer; n : integer; dummy : std_logic) return integer is
  begin
    return bitand(v, 32, shift_left(1, 2, n, 32) - 1, 32);
  end mask_bits;

  function done_reading_bits(bits_to_read_count : integer; dummy : std_logic) return std_logic is
    variable done_reading_bits0_1          : integer;
    variable done_reading_bits_bool_expr_1 : std_logic;
  begin
    done_reading_bits0_1 := bits_to_read_count;
    if (done_reading_bits0_1 < 0) then
      done_reading_bits_bool_expr_1 := '1';
    else
      done_reading_bits_bool_expr_1 := '0';
    end if;
    return done_reading_bits_bool_expr_1;
  end done_reading_bits;

  function read_result(read_result_in_progress : integer; dummy : std_logic) return integer is
    variable read_result0_1 : integer;
  begin
    read_result0_1 := read_result_in_progress;
    return read_result0_1;
  end read_result;

  function intra_max_level(last : std_logic; run : integer; dummy : std_logic) return integer is
    variable intra_max_level0_1  : integer;
    variable intra_max_level0_2  : integer;
    variable intra_max_level0_3  : integer;
    variable intra_max_level0_4  : integer;
    variable intra_max_level0_5  : integer;
    variable intra_max_level0_6  : integer;
    variable intra_max_level0_7  : integer;
    variable intra_max_level0_8  : integer;
    variable intra_max_level0_9  : integer;
    variable intra_max_level0_10 : integer;
    variable intra_max_level0_11 : integer;
    variable intra_max_level0_12 : integer;
    variable intra_max_level0_13 : integer;
    variable intra_max_level0_14 : integer;
    variable intra_max_level0_15 : integer;
    variable intra_max_level0_16 : integer;
    variable intra_max_level0_17 : integer;
    variable intra_max_level0_18 : integer;
    variable intra_max_level0_19 : integer;
    variable intra_max_level0_20 : integer;
    variable intra_max_level0_21 : integer;
    variable intra_max_level0_22 : integer;
    variable intra_max_level0_23 : integer;
    variable intra_max_level0_24 : integer;
    variable intra_max_level0_25 : integer;
  begin
    if (last = '0') then
      if (run = 0) then
        intra_max_level0_1 := 27;
        intra_max_level0_2 := intra_max_level0_1;
      else
        if (run = 1) then
          intra_max_level0_3 := 10;
          intra_max_level0_4 := intra_max_level0_3;
        else
          if (run = 2) then
            intra_max_level0_5 := 5;
            intra_max_level0_6 := intra_max_level0_5;
          else
            if (run = 3) then
              intra_max_level0_7 := 4;
              intra_max_level0_8 := intra_max_level0_7;
            else
              if (run < 8) then
                intra_max_level0_9  := 3;
                intra_max_level0_10 := intra_max_level0_9;
              else
                if (run < 10) then
                  intra_max_level0_11 := 2;
                  intra_max_level0_12 := intra_max_level0_11;
                else
                  if (run < 15) then
                    intra_max_level0_13 := 1;
                    intra_max_level0_14 := intra_max_level0_13;
                  else
                    intra_max_level0_15 := 0;
                    intra_max_level0_14 := intra_max_level0_15;
                  end if;
                  intra_max_level0_12 := intra_max_level0_14;
                end if;
                intra_max_level0_10 := intra_max_level0_12;
              end if;
              intra_max_level0_8 := intra_max_level0_10;
            end if;
            intra_max_level0_6 := intra_max_level0_8;
          end if;
          intra_max_level0_4 := intra_max_level0_6;
        end if;
        intra_max_level0_2 := intra_max_level0_4;
      end if;
      intra_max_level0_16 := intra_max_level0_2;
    else
      if (run = 0) then
        intra_max_level0_17 := 8;
        intra_max_level0_18 := intra_max_level0_17;
      else
        if (run = 1) then
          intra_max_level0_19 := 3;
          intra_max_level0_20 := intra_max_level0_19;
        else
          if (run < 7) then
            intra_max_level0_21 := 2;
            intra_max_level0_22 := intra_max_level0_21;
          else
            if (run < 21) then
              intra_max_level0_23 := 1;
              intra_max_level0_24 := intra_max_level0_23;
            else
              intra_max_level0_25 := 0;
              intra_max_level0_24 := intra_max_level0_25;
            end if;
            intra_max_level0_22 := intra_max_level0_24;
          end if;
          intra_max_level0_20 := intra_max_level0_22;
        end if;
        intra_max_level0_18 := intra_max_level0_20;
      end if;
      intra_max_level0_16 := intra_max_level0_18;
    end if;
    return intra_max_level0_16;
  end intra_max_level;

  function inter_max_level(last : std_logic; run : integer; dummy : std_logic) return integer is
    variable inter_max_level0_1  : integer;
    variable inter_max_level0_2  : integer;
    variable inter_max_level0_3  : integer;
    variable inter_max_level0_4  : integer;
    variable inter_max_level0_5  : integer;
    variable inter_max_level0_6  : integer;
    variable inter_max_level0_7  : integer;
    variable inter_max_level0_8  : integer;
    variable inter_max_level0_9  : integer;
    variable inter_max_level0_10 : integer;
    variable inter_max_level0_11 : integer;
    variable inter_max_level0_12 : integer;
    variable inter_max_level0_13 : integer;
    variable inter_max_level0_14 : integer;
    variable inter_max_level0_15 : integer;
    variable inter_max_level0_16 : integer;
    variable inter_max_level0_17 : integer;
    variable inter_max_level0_18 : integer;
    variable inter_max_level0_19 : integer;
    variable inter_max_level0_20 : integer;
    variable inter_max_level0_21 : integer;
  begin
    if (last = '0') then
      if (run = 0) then
        inter_max_level0_1 := 12;
        inter_max_level0_2 := inter_max_level0_1;
      else
        if (run = 1) then
          inter_max_level0_3 := 6;
          inter_max_level0_4 := inter_max_level0_3;
        else
          if (run = 2) then
            inter_max_level0_5 := 4;
            inter_max_level0_6 := inter_max_level0_5;
          else
            if (run < 7) then
              inter_max_level0_7 := 3;
              inter_max_level0_8 := inter_max_level0_7;
            else
              if (run < 11) then
                inter_max_level0_9  := 2;
                inter_max_level0_10 := inter_max_level0_9;
              else
                if (run < 27) then
                  inter_max_level0_11 := 1;
                  inter_max_level0_12 := inter_max_level0_11;
                else
                  inter_max_level0_13 := 0;
                  inter_max_level0_12 := inter_max_level0_13;
                end if;
                inter_max_level0_10 := inter_max_level0_12;
              end if;
              inter_max_level0_8 := inter_max_level0_10;
            end if;
            inter_max_level0_6 := inter_max_level0_8;
          end if;
          inter_max_level0_4 := inter_max_level0_6;
        end if;
        inter_max_level0_2 := inter_max_level0_4;
      end if;
      inter_max_level0_14 := inter_max_level0_2;
    else
      if (run = 0) then
        inter_max_level0_15 := 3;
        inter_max_level0_16 := inter_max_level0_15;
      else
        if (run = 1) then
          inter_max_level0_17 := 2;
          inter_max_level0_18 := inter_max_level0_17;
        else
          if (run < 41) then
            inter_max_level0_19 := 1;
            inter_max_level0_20 := inter_max_level0_19;
          else
            inter_max_level0_21 := 0;
            inter_max_level0_20 := inter_max_level0_21;
          end if;
          inter_max_level0_18 := inter_max_level0_20;
        end if;
        inter_max_level0_16 := inter_max_level0_18;
      end if;
      inter_max_level0_14 := inter_max_level0_16;
    end if;
    return inter_max_level0_14;
  end inter_max_level;

  function intra_max_run(last : std_logic; level : integer; dummy : std_logic) return integer is
    variable intra_max_run0_1  : integer;
    variable intra_max_run0_2  : integer;
    variable intra_max_run0_3  : integer;
    variable intra_max_run0_4  : integer;
    variable intra_max_run0_5  : integer;
    variable intra_max_run0_6  : integer;
    variable intra_max_run0_7  : integer;
    variable intra_max_run0_8  : integer;
    variable intra_max_run0_9  : integer;
    variable intra_max_run0_10 : integer;
    variable intra_max_run0_11 : integer;
    variable intra_max_run0_12 : integer;
    variable intra_max_run0_13 : integer;
    variable intra_max_run0_14 : integer;
    variable intra_max_run0_15 : integer;
    variable intra_max_run0_16 : integer;
    variable intra_max_run0_17 : integer;
    variable intra_max_run0_18 : integer;
    variable intra_max_run0_19 : integer;
    variable intra_max_run0_20 : integer;
    variable intra_max_run0_21 : integer;
  begin
    if (last = '0') then
      if (level = 1) then
        intra_max_run0_1 := 14;
        intra_max_run0_2 := intra_max_run0_1;
      else
        if (level = 2) then
          intra_max_run0_3 := 9;
          intra_max_run0_4 := intra_max_run0_3;
        else
          if (level = 3) then
            intra_max_run0_5 := 7;
            intra_max_run0_6 := intra_max_run0_5;
          else
            if (level = 4) then
              intra_max_run0_7 := 3;
              intra_max_run0_8 := intra_max_run0_7;
            else
              if (level = 5) then
                intra_max_run0_9  := 2;
                intra_max_run0_10 := intra_max_run0_9;
              else
                if (level < 11) then
                  intra_max_run0_11 := 1;
                  intra_max_run0_12 := intra_max_run0_11;
                else
                  intra_max_run0_13 := 0;
                  intra_max_run0_12 := intra_max_run0_13;
                end if;
                intra_max_run0_10 := intra_max_run0_12;
              end if;
              intra_max_run0_8 := intra_max_run0_10;
            end if;
            intra_max_run0_6 := intra_max_run0_8;
          end if;
          intra_max_run0_4 := intra_max_run0_6;
        end if;
        intra_max_run0_2 := intra_max_run0_4;
      end if;
      intra_max_run0_14 := intra_max_run0_2;
    else
      if (level = 1) then
        intra_max_run0_15 := 20;
        intra_max_run0_16 := intra_max_run0_15;
      else
        if (level = 2) then
          intra_max_run0_17 := 6;
          intra_max_run0_18 := intra_max_run0_17;
        else
          if (level = 3) then
            intra_max_run0_19 := 1;
            intra_max_run0_20 := intra_max_run0_19;
          else
            intra_max_run0_21 := 0;
            intra_max_run0_20 := intra_max_run0_21;
          end if;
          intra_max_run0_18 := intra_max_run0_20;
        end if;
        intra_max_run0_16 := intra_max_run0_18;
      end if;
      intra_max_run0_14 := intra_max_run0_16;
    end if;
    return intra_max_run0_14;
  end intra_max_run;

  function inter_max_run(last : std_logic; level : integer; dummy : std_logic) return integer is
    variable inter_max_run0_1  : integer;
    variable inter_max_run0_2  : integer;
    variable inter_max_run0_3  : integer;
    variable inter_max_run0_4  : integer;
    variable inter_max_run0_5  : integer;
    variable inter_max_run0_6  : integer;
    variable inter_max_run0_7  : integer;
    variable inter_max_run0_8  : integer;
    variable inter_max_run0_9  : integer;
    variable inter_max_run0_10 : integer;
    variable inter_max_run0_11 : integer;
    variable inter_max_run0_12 : integer;
    variable inter_max_run0_13 : integer;
    variable inter_max_run0_14 : integer;
    variable inter_max_run0_15 : integer;
    variable inter_max_run0_16 : integer;
    variable inter_max_run0_17 : integer;
  begin
    if (last = '0') then
      if (level = 1) then
        inter_max_run0_1 := 26;
        inter_max_run0_2 := inter_max_run0_1;
      else
        if (level = 2) then
          inter_max_run0_3 := 10;
          inter_max_run0_4 := inter_max_run0_3;
        else
          if (level = 3) then
            inter_max_run0_5 := 6;
            inter_max_run0_6 := inter_max_run0_5;
          else
            if (level = 4) then
              inter_max_run0_7 := 2;
              inter_max_run0_8 := inter_max_run0_7;
            else
              if ((level = 5) or (level = 6)) then
                inter_max_run0_9  := 1;
                inter_max_run0_10 := inter_max_run0_9;
              else
                inter_max_run0_11 := 0;
                inter_max_run0_10 := inter_max_run0_11;
              end if;
              inter_max_run0_8 := inter_max_run0_10;
            end if;
            inter_max_run0_6 := inter_max_run0_8;
          end if;
          inter_max_run0_4 := inter_max_run0_6;
        end if;
        inter_max_run0_2 := inter_max_run0_4;
      end if;
      inter_max_run0_12 := inter_max_run0_2;
    else
      if (level = 1) then
        inter_max_run0_13 := 40;
        inter_max_run0_14 := inter_max_run0_13;
      else
        if (level = 2) then
          inter_max_run0_15 := 1;
          inter_max_run0_16 := inter_max_run0_15;
        else
          inter_max_run0_17 := 0;
          inter_max_run0_16 := inter_max_run0_17;
        end if;
        inter_max_run0_14 := inter_max_run0_16;
      end if;
      inter_max_run0_12 := inter_max_run0_14;
    end if;
    return inter_max_run0_12;
  end inter_max_run;

  function vld_success(vld_codeword : integer; dummy : std_logic) return std_logic is
    variable vld_success0_1          : integer;
    variable vld_success_bool_expr_1 : std_logic;
  begin
    vld_success0_1 := vld_codeword;
    if (bitand(vld_success0_1, 20, 3, 3) = 0) then
      vld_success_bool_expr_1 := '1';
    else
      vld_success_bool_expr_1 := '0';
    end if;
    return vld_success_bool_expr_1;
  end vld_success;

  function vld_failure(vld_codeword : integer; dummy : std_logic) return std_logic is
    variable vld_failure0_1          : integer;
    variable vld_failure_bool_expr_1 : std_logic;
  begin
    vld_failure0_1 := vld_codeword;
    if (bitand(vld_failure0_1, 20, 1, 2) = 1) then
      vld_failure_bool_expr_1 := '1';
    else
      vld_failure_bool_expr_1 := '0';
    end if;
    return vld_failure_bool_expr_1;
  end vld_failure;

  function vld_result(vld_codeword : integer; dummy : std_logic) return integer is
    variable vld_result0_1 : integer;
  begin
    vld_result0_1 := vld_codeword;
    return shift_right(vld_result0_1, 20, 2, 3);
  end vld_result;

  ---------------------------------------------------------------------------
  -- FSM
  type FSM_type is (s_block, s_final_cbpy, s_get_dc, s_get_dc_a, s_get_dc_bits, s_get_mbtype, s_get_residual_x, s_get_residual_y, s_mag_x, s_mag_y, s_mb, s_mv, s_mv_y, s_pvop_uncoded1, s_pvop_uncoded2, s_pvop_uncoded3, s_pvop_uncoded4, s_pvop_uncoded5, s_send_new_vop_height, s_send_new_vop_info, s_send_new_vop_width, s_stuck, s_stuck_1a, s_stuck_1b, s_stuck_2a, s_stuck_2b, s_stuck_3a, s_stuck_3b, s_stuck_4a, s_stuck_4b, s_texac, s_texture, s_vld1, s_vld4, s_vld4a, s_vld6, s_vld6a, s_vld7, s_vld_direct, s_vol_aspect, s_vol_control, s_vol_height, s_vol_misc, s_vol_object, s_vol_shape, s_vol_time_inc_res, s_vol_vbv, s_vol_width, s_vop_coding, s_vop_predict, s_vop_timebase);
  signal FSM : FSM_type;
  ---------------------------------------------------------------------------

begin
  parseheaders_proc : process (reset_n, clock) is
    variable isSchedulable_untagged01_go                        : std_logic;
    variable isSchedulable_vol_object_layer_identification_go   : std_logic;
    variable isSchedulable_vol_aspect_detailed_go               : std_logic;
    variable isSchedulable_vol_control_detailed_go              : std_logic;
    variable isSchedulable_vol_vbv_detailed_go                  : std_logic;
    variable isSchedulable_vol_shape_go                         : std_logic;
    variable isSchedulable_vol_time_inc_res_go                  : std_logic;
    variable isSchedulable_set_vol_width_go                     : std_logic;
    variable isSchedulable_set_vol_height_go                    : std_logic;
    variable isSchedulable_byte_align_go                        : std_logic;
    variable isSchedulable_vop_predict_supported_go             : std_logic;
    variable isSchedulable_vop_timebase_one_go                  : std_logic;
    variable isSchedulable_vop_timebase_zero_go                 : std_logic;
    variable isSchedulable_vop_coding_uncoded_go                : std_logic;
    variable isSchedulable_vop_coding_coded_go                  : std_logic;
    variable isSchedulable_send_new_vop_cmd_go                  : std_logic;
    variable isSchedulable_send_new_vop_width_go                : std_logic;
    variable isSchedulable_send_new_vop_height_go               : std_logic;
    variable isSchedulable_mb_done_go                           : std_logic;
    variable isSchedulable_get_mcbpc_ivop_go                    : std_logic;
    variable isSchedulable_get_mcbpc_pvop_go                    : std_logic;
    variable isSchedulable_mcbpc_pvop_uncoded_go                : std_logic;
    variable isSchedulable_mcbpc_pvop_uncoded1_go               : std_logic;
    variable isSchedulable_get_mbtype_noac_go                   : std_logic;
    variable isSchedulable_get_mbtype_ac_go                     : std_logic;
    variable isSchedulable_final_cbpy_inter_go                  : std_logic;
    variable isSchedulable_final_cbpy_intra_go                  : std_logic;
    variable isSchedulable_mb_dispatch_done_go                  : std_logic;
    variable isSchedulable_mb_dispatch_intra_go                 : std_logic;
    variable isSchedulable_mb_dispatch_inter_no_ac_go           : std_logic;
    variable isSchedulable_mb_dispatch_inter_ac_coded_go        : std_logic;
    variable isSchedulable_vld_start_intra_go                   : std_logic;
    variable isSchedulable_vld_start_inter_ac_coded_go          : std_logic;
    variable isSchedulable_vld_start_inter_not_ac_coded_go      : std_logic;
    variable isSchedulable_get_dc_bits_none_go                  : std_logic;
    variable isSchedulable_get_dc_bits_some_go                  : std_logic;
    variable isSchedulable_dc_bits_shift_go                     : std_logic;
    variable isSchedulable_get_dc_go                            : std_logic;
    variable isSchedulable_block_done_go                        : std_logic;
    variable isSchedulable_dct_coeff_go                         : std_logic;
    variable isSchedulable_vld_code_go                          : std_logic;
    variable isSchedulable_vld_level_go                         : std_logic;
    variable isSchedulable_vld_run_or_direct_go                 : std_logic;
    variable isSchedulable_vld_run_go                           : std_logic;
    variable isSchedulable_vld_direct_read_go                   : std_logic;
    variable isSchedulable_vld_direct_go                        : std_logic;
    variable isSchedulable_do_level_lookup_go                   : std_logic;
    variable isSchedulable_vld_level_lookup_go                  : std_logic;
    variable isSchedulable_do_run_lookup_go                     : std_logic;
    variable isSchedulable_vld_run_lookup_go                    : std_logic;
    variable isSchedulable_mvcode_done_go                       : std_logic;
    variable isSchedulable_mvcode_go                            : std_logic;
    variable isSchedulable_mag_x_go                             : std_logic;
    variable isSchedulable_get_residual_x_go                    : std_logic;
    variable isSchedulable_mag_y_go                             : std_logic;
    variable isSchedulable_get_residual_y_go                    : std_logic;
    variable isSchedulable_untagged02_go                        : std_logic;
    variable isSchedulable_do_vld_failure_go                    : std_logic;
    variable isSchedulable_generic_done_go                      : std_logic;
    variable isSchedulable_generic_done_with_bitread_go         : std_logic;
    variable isSchedulable_test_zero_byte_go                    : std_logic;
    variable isSchedulable_test_vo_byte_go                      : std_logic;
    variable isSchedulable_test_vol_byte_go                     : std_logic;
    variable isSchedulable_test_vop_byte_go                     : std_logic;
    variable isSchedulable_test_one_byte_go                     : std_logic;
    variable isSchedulable_request_byte_go                      : std_logic;
    --
    variable untagged01_bits                                    : std_logic;
    variable untagged01_b_1                                     : std_logic;
    variable untagged010_1                                      : integer range 2147483647 downto -2147483648;
    variable untagged011_1                                      : integer range 1 downto -2;
    variable untagged011_2                                      : integer range 1 downto -2;
    variable untagged011_3                                      : integer range 1 downto -2;
    variable untagged012_1                                      : integer range 63 downto -64;
    variable untagged013_1                                      : integer range 7 downto -8;
    variable isSchedulable_untagged01_bits                      : std_logic;
    variable isSchedulable_untagged011_1                        : std_logic;
    variable isSchedulable_untagged012_1                        : std_logic;
    variable isSchedulable_untagged010_1                        : std_logic;
    variable isSchedulable_untagged010_2                        : std_logic;
    variable isSchedulable_untagged010_3                        : std_logic;
    variable vol_object_layer_identification_bits               : std_logic;
    variable vol_object_layer_identification_b_1                : std_logic;
    variable vol_object_layer_identification0_1                 : integer range 2147483647 downto -2147483648;
    variable vol_object_layer_identification0_2                 : integer range 2147483647 downto -2147483648;
    variable vol_object_layer_identification0_3                 : integer range 2147483647 downto -2147483648;
    variable vol_object_layer_identification5_1                 : integer range 7 downto -8;
    variable isSchedulable_vol_object_layer_identification_bits : std_logic;
    variable isSchedulable_vol_object_layer_identification1_1   : std_logic;
    variable isSchedulable_vol_object_layer_identification0_1   : std_logic;
    variable isSchedulable_vol_object_layer_identification0_2   : std_logic;
    variable isSchedulable_vol_object_layer_identification0_3   : std_logic;
    variable isSchedulable_vol_aspect_detailed1_1               : std_logic;
    variable isSchedulable_vol_aspect_detailed2_1               : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vol_aspect_detailed3_1               : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vol_aspect_detailed4_1               : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vol_aspect_detailed5_1               : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vol_aspect_detailed0_1               : std_logic;
    variable isSchedulable_vol_aspect_detailed0_2               : std_logic;
    variable vol_control_detailed_bits                          : std_logic;
    variable vol_control_detailed2_1                            : integer range 7 downto -8;
    variable isSchedulable_vol_control_detailed_bits            : std_logic;
    variable isSchedulable_vol_control_detailed1_1              : std_logic;
    variable isSchedulable_vol_control_detailed_b_1             : std_logic;
    variable isSchedulable_vol_control_detailed2_1              : std_logic;
    variable isSchedulable_vol_control_detailed0_1              : std_logic;
    variable isSchedulable_vol_control_detailed0_2              : std_logic;
    variable isSchedulable_vol_control_detailed0_3              : std_logic;
    variable vol_vbv_detailed_bits                              : std_logic;
    variable vol_vbv_detailed11_1                               : integer range 7 downto -8;
    variable isSchedulable_vol_vbv_detailed_bits                : std_logic;
    variable isSchedulable_vol_vbv_detailed1_1                  : std_logic;
    variable isSchedulable_vol_vbv_detailed_b_1                 : std_logic;
    variable isSchedulable_vol_vbv_detailed2_1                  : std_logic;
    variable isSchedulable_vol_vbv_detailed0_1                  : std_logic;
    variable isSchedulable_vol_vbv_detailed0_2                  : std_logic;
    variable isSchedulable_vol_vbv_detailed0_3                  : std_logic;
    variable isSchedulable_vol_shape0_1                         : std_logic;
    variable isSchedulable_vol_shape0_2                         : std_logic;
    variable vol_time_inc_res0_1                                : integer range 2147483647 downto -2147483648;
    variable vol_time_inc_res_time_inc_res0_1                   : integer range 65535 downto -65536;
    variable vol_time_inc_res_count0_1                          : integer range 63 downto -64;
    variable vol_time_inc_res_ones0_1                           : integer range 63 downto -64;
    variable vol_time_inc_res_ones0_2                           : integer range 63 downto -64;
    variable vol_time_inc_res_ones0_3                           : integer range 63 downto -64;
    variable vol_time_inc_res_ones0_4                           : integer range 63 downto -64;
    variable vol_time_inc_res_count0_2                          : integer range 63 downto -64;
    variable vol_time_inc_res_count0_3                          : integer range 63 downto -64;
    variable vol_time_inc_res_time_inc_res0_2                   : integer range 65535 downto -65536;
    variable vol_time_inc_res_time_inc_res0_3                   : integer range 65535 downto -65536;
    variable vol_time_inc_res2_1                                : integer range 63 downto -64;
    variable vol_time_inc_res2_2                                : integer range 63 downto -64;
    variable vol_time_inc_res2_3                                : integer range 63 downto -64;
    variable vol_time_inc_res3_1                                : integer range 63 downto -64;
    variable vol_time_inc_res4_1                                : integer range 63 downto -64;
    variable vol_time_inc_res4_2                                : integer range 63 downto -64;
    variable vol_time_inc_res5_1                                : integer range 63 downto -64;
    variable vol_time_inc_res4_3                                : integer range 63 downto -64;
    variable vol_time_inc_res6_1                                : integer range 2147483647 downto -2147483648;
    variable vol_time_inc_res8_1                                : integer range 63 downto -64;
    variable vol_time_inc_res7_1                                : integer range 63 downto -64;
    variable vol_time_inc_res7_2                                : integer range 63 downto -64;
    variable vol_time_inc_res7_3                                : integer range 63 downto -64;
    variable isSchedulable_vol_time_inc_res0_1                  : std_logic;
    variable isSchedulable_vol_time_inc_res0_2                  : std_logic;
    variable set_vol_width0_1                                   : integer range 2147483647 downto -2147483648;
    variable set_vol_width3_1                                   : integer range 2147483647 downto -2147483648;
    variable isSchedulable_set_vol_width0_1                     : std_logic;
    variable isSchedulable_set_vol_width0_2                     : std_logic;
    variable set_vol_height0_1                                  : integer range 2147483647 downto -2147483648;
    variable set_vol_height3_1                                  : integer range 2147483647 downto -2147483648;
    variable isSchedulable_set_vol_height0_1                    : std_logic;
    variable isSchedulable_set_vol_height0_2                    : std_logic;
    variable byte_align0_1                                      : integer range 7 downto -8;
    variable vop_predict_supported0_1                           : integer range 2147483647 downto -2147483648;
    variable vop_predict_supported2_1                           : integer range 2147483647 downto -2147483648;
    variable vop_predict_supported4_1                           : integer range 2147483647 downto -2147483648;
    variable vop_predict_supported6_1                           : integer range 2147483647 downto -2147483648;
    variable vop_predict_supported8_1                           : integer range 2147483647 downto -2147483648;
    variable vop_predict_supported10_1                          : integer range 2147483647 downto -2147483648;
    variable vop_predict_supported_bool_expr_1                  : std_logic;
    variable vop_predict_supported_bool_expr_2                  : std_logic;
    variable vop_predict_supported_bool_expr_3                  : std_logic;
    variable isSchedulable_vop_predict_supported1_1             : std_logic;
    variable isSchedulable_vop_predict_supported2_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported3_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported4_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported5_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported6_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported7_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported8_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported9_1             : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vop_predict_supported0_1             : std_logic;
    variable isSchedulable_vop_predict_supported0_2             : std_logic;
    variable vop_timebase_one_bits                              : std_logic;
    variable vop_timebase_one0_1                                : integer range 7 downto -8;
    variable isSchedulable_vop_timebase_one_bits                : std_logic;
    variable isSchedulable_vop_timebase_one1_1                  : std_logic;
    variable isSchedulable_vop_timebase_one_b_1                 : std_logic;
    variable isSchedulable_vop_timebase_one0_1                  : std_logic;
    variable isSchedulable_vop_timebase_one0_2                  : std_logic;
    variable isSchedulable_vop_timebase_one0_3                  : std_logic;
    variable vop_timebase_zero_bits                             : std_logic;
    variable vop_timebase_zero0_1                               : integer range 7 downto -8;
    variable vop_timebase_zero2_1                               : integer range 63 downto -64;
    variable isSchedulable_vop_timebase_zero1_1                 : std_logic;
    variable isSchedulable_vop_timebase_zero0_1                 : std_logic;
    variable isSchedulable_vop_timebase_zero0_2                 : std_logic;
    variable isSchedulable_vop_timebase_zero0_3                 : std_logic;
    variable vop_coding_uncoded_bits                            : std_logic;
    variable vop_coding_uncoded0_1                              : integer range 7 downto -8;
    variable isSchedulable_vop_coding_uncoded_bits              : std_logic;
    variable isSchedulable_vop_coding_uncoded1_1                : std_logic;
    variable isSchedulable_vop_coding_uncoded_b_1               : std_logic;
    variable isSchedulable_vop_coding_uncoded2_1                : std_logic;
    variable isSchedulable_vop_coding_uncoded0_1                : std_logic;
    variable isSchedulable_vop_coding_uncoded0_2                : std_logic;
    variable isSchedulable_vop_coding_uncoded0_3                : std_logic;
    variable vop_coding_coded_bits                              : std_logic;
    variable vop_coding_coded0_1                                : std_logic;
    variable vop_coding_coded1_1                                : integer range 2147483647 downto -2147483648;
    variable vop_coding_coded1_2                                : integer range 2147483647 downto -2147483648;
    variable vop_coding_coded1_3                                : integer range 2147483647 downto -2147483648;
    variable vop_coding_coded7_1                                : integer range 7 downto -8;
    variable isSchedulable_vop_coding_coded_bits                : std_logic;
    variable isSchedulable_vop_coding_coded1_1                  : std_logic;
    variable isSchedulable_vop_coding_coded0_1                  : std_logic;
    variable isSchedulable_vop_coding_coded0_2                  : std_logic;
    variable isSchedulable_vop_coding_coded0_3                  : std_logic;
    variable send_new_vop_cmd_BTYPE                             : integer range 2047 downto -2048;
    variable send_new_vop_cmd_round0_1                          : std_logic;
    variable send_new_vop_cmd1_1                                : std_logic;
    variable send_new_vop_cmd2_1                                : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd2_2                                : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd2_3                                : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd_cmd0_1                            : integer range 2047 downto -2048;
    variable send_new_vop_cmd5_1                                : std_logic;
    variable send_new_vop_cmd6_1                                : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd_round0_2                          : std_logic;
    variable send_new_vop_cmd_round0_3                          : std_logic;
    variable send_new_vop_cmd10_1                               : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd_vop_quant0_1                      : integer range 31 downto -32;
    variable send_new_vop_cmd_vop_quant0_2                      : integer range 31 downto -32;
    variable send_new_vop_cmd13_1                               : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd15_1                               : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd_vop_quant0_3                      : integer range 31 downto -32;
    variable send_new_vop_cmd_cmd0_2                            : integer range 2047 downto -2048;
    variable send_new_vop_cmd18_1                               : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd18_2                               : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd18_3                               : integer range 2147483647 downto -2147483648;
    variable send_new_vop_cmd_cmd0_3                            : integer range 2047 downto -2048;
    variable send_new_vop_cmd20_1                               : integer range 7 downto -8;
    variable send_new_vop_cmd_cmd0_4                            : integer range 2047 downto -2048;
    variable isSchedulable_send_new_vop_cmd0_1                  : std_logic;
    variable isSchedulable_send_new_vop_cmd0_2                  : std_logic;
    variable send_new_vop_width_WIDTH                           : integer range 32767 downto -32768;
    variable send_new_vop_width_BTYPE                           : integer range 2047 downto -2048;
    variable send_new_vop_width0_1                              : integer range 127 downto -128;
    variable send_new_vop_width1_1                              : integer range 127 downto -128;
    variable send_new_vop_height_HEIGHT                         : integer range 32767 downto -32768;
    variable send_new_vop_height_BTYPE                          : integer range 2047 downto -2048;
    variable send_new_vop_height0_1                             : integer range 127 downto -128;
    variable send_new_vop_height1_1                             : integer range 127 downto -128;
    variable isSchedulable_mb_done1_1                           : integer range 127 downto -128;
    variable isSchedulable_mb_done2_1                           : integer range 127 downto -128;
    variable isSchedulable_mb_done0_1                           : std_logic;
    variable isSchedulable_mb_done0_2                           : std_logic;
    variable isSchedulable_get_mcbpc_ivop1_1                    : std_logic;
    variable isSchedulable_get_mcbpc_ivop0_1                    : std_logic;
    variable isSchedulable_get_mcbpc_ivop0_2                    : std_logic;
    variable get_mcbpc_pvop_bits                                : std_logic;
    variable get_mcbpc_pvop1_1                                  : integer range 7 downto -8;
    variable isSchedulable_get_mcbpc_pvop_bits                  : std_logic;
    variable isSchedulable_get_mcbpc_pvop1_1                    : std_logic;
    variable isSchedulable_get_mcbpc_pvop_b_1                   : std_logic;
    variable isSchedulable_get_mcbpc_pvop2_1                    : std_logic;
    variable isSchedulable_get_mcbpc_pvop0_1                    : std_logic;
    variable isSchedulable_get_mcbpc_pvop0_2                    : std_logic;
    variable isSchedulable_get_mcbpc_pvop0_3                    : std_logic;
    variable mcbpc_pvop_uncoded_BTYPE                           : integer range 2047 downto -2048;
    variable mcbpc_pvop_uncoded_bits                            : std_logic;
    variable mcbpc_pvop_uncoded0_1                              : integer range 7 downto -8;
    variable isSchedulable_mcbpc_pvop_uncoded_bits              : std_logic;
    variable isSchedulable_mcbpc_pvop_uncoded1_1                : std_logic;
    variable isSchedulable_mcbpc_pvop_uncoded2_1                : std_logic;
    variable isSchedulable_mcbpc_pvop_uncoded0_1                : std_logic;
    variable isSchedulable_mcbpc_pvop_uncoded0_2                : std_logic;
    variable isSchedulable_mcbpc_pvop_uncoded0_3                : std_logic;
    variable mcbpc_pvop_uncoded1_BTYPE                          : integer range 2047 downto -2048;
    variable get_mbtype_noac_mcbpc0_1                           : integer range 2147483647 downto -2147483648;
    variable get_mbtype_noac_type0_1                            : integer range 2147483647 downto -2147483648;
    variable get_mbtype_noac_bool_expr_1                        : std_logic;
    variable get_mbtype_noac_bool_expr_2                        : std_logic;
    variable isSchedulable_get_mbtype_noac1_1                   : std_logic;
    variable isSchedulable_get_mbtype_noac2_1                   : integer range 524287 downto -524288;
    variable isSchedulable_get_mbtype_noac3_1                   : integer range 524287 downto -524288;
    variable isSchedulable_get_mbtype_noac0_1                   : std_logic;
    variable isSchedulable_get_mbtype_noac0_2                   : std_logic;
    variable get_mbtype_ac_bits                                 : std_logic;
    variable get_mbtype_ac_b_1                                  : std_logic;
    variable get_mbtype_ac_mcbpc0_1                             : integer range 2147483647 downto -2147483648;
    variable get_mbtype_ac0_1                                   : integer range 7 downto -8;
    variable isSchedulable_get_mbtype_ac_bits                   : std_logic;
    variable isSchedulable_get_mbtype_ac1_1                     : std_logic;
    variable isSchedulable_get_mbtype_ac0_1                     : std_logic;
    variable isSchedulable_get_mbtype_ac0_2                     : std_logic;
    variable isSchedulable_get_mbtype_ac0_3                     : std_logic;
    variable final_cbpy_inter0_1                                : integer range 524287 downto -524288;
    variable final_cbpy_inter_cbpy0_1                           : integer range 2147483647 downto -2147483648;
    variable final_cbpy_inter1_1                                : integer range 63 downto -64;
    variable isSchedulable_final_cbpy_inter1_1                  : std_logic;
    variable isSchedulable_final_cbpy_inter2_1                  : std_logic;
    variable isSchedulable_final_cbpy_inter0_1                  : std_logic;
    variable isSchedulable_final_cbpy_inter0_2                  : std_logic;
    variable final_cbpy_intra_cbpy0_1                           : integer range 2147483647 downto -2147483648;
    variable final_cbpy_intra0_1                                : integer range 63 downto -64;
    variable isSchedulable_final_cbpy_intra0_1                  : std_logic;
    variable isSchedulable_final_cbpy_intra0_2                  : std_logic;
    variable isSchedulable_mb_dispatch_done1_1                  : integer range 7 downto -8;
    variable isSchedulable_mb_dispatch_done0_1                  : std_logic;
    variable isSchedulable_mb_dispatch_done0_2                  : std_logic;
    variable mb_dispatch_intra_BTYPE                            : integer range 2047 downto -2048;
    variable mb_dispatch_intra1_1                               : integer range 63 downto -64;
    variable mb_dispatch_intra2_1                               : integer range 7 downto -8;
    variable mb_dispatch_intra3_1                               : std_logic;
    variable mb_dispatch_intra4_1                               : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_intra4_2                               : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_intra4_3                               : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_intra_cmd0_2                           : integer range 2047 downto -2048;
    variable mb_dispatch_intra6_1                               : std_logic;
    variable mb_dispatch_intra7_1                               : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_intra7_2                               : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_intra7_3                               : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_intra_cmd0_3                           : integer range 2047 downto -2048;
    variable mb_dispatch_intra_bool_expr_1                      : std_logic;
    variable isSchedulable_mb_dispatch_intra1_1                 : std_logic;
    variable isSchedulable_mb_dispatch_intra0_1                 : std_logic;
    variable isSchedulable_mb_dispatch_intra0_2                 : std_logic;
    variable mb_dispatch_inter_no_ac_BTYPE                      : integer range 2047 downto -2048;
    variable mb_dispatch_inter_no_ac0_1                         : integer range 7 downto -8;
    variable mb_dispatch_inter_no_ac3_1                         : std_logic;
    variable mb_dispatch_inter_no_ac4_1                         : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_inter_no_ac4_2                         : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_inter_no_ac4_3                         : integer range 2147483647 downto -2147483648;
    variable isSchedulable_mb_dispatch_inter_no_ac1_1           : integer range 63 downto -64;
    variable isSchedulable_mb_dispatch_inter_no_ac2_1           : integer range 7 downto -8;
    variable isSchedulable_mb_dispatch_inter_no_ac0_1           : std_logic;
    variable isSchedulable_mb_dispatch_inter_no_ac0_2           : std_logic;
    variable mb_dispatch_inter_ac_coded_BTYPE                   : integer range 2047 downto -2048;
    variable mb_dispatch_inter_ac_coded3_1                      : std_logic;
    variable mb_dispatch_inter_ac_coded4_1                      : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_inter_ac_coded4_2                      : integer range 2147483647 downto -2147483648;
    variable mb_dispatch_inter_ac_coded4_3                      : integer range 2147483647 downto -2147483648;
    variable vld_start_intra0_1                                 : integer range 7 downto -8;
    variable vld_start_intra1_1                                 : integer range 2147483647 downto -2147483648;
    variable vld_start_intra1_2                                 : integer range 2147483647 downto -2147483648;
    variable vld_start_intra1_3                                 : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vld_start_intra1_1                   : std_logic;
    variable isSchedulable_vld_start_intra0_1                   : std_logic;
    variable isSchedulable_vld_start_intra0_2                   : std_logic;
    variable isSchedulable_vld_start_inter_ac_coded1_1          : std_logic;
    variable isSchedulable_vld_start_inter_ac_coded0_1          : std_logic;
    variable isSchedulable_vld_start_inter_ac_coded0_2          : std_logic;
    variable vld_start_inter_not_ac_coded_LAST                  : std_logic;
    variable vld_start_inter_not_ac_coded_VALUE                 : integer range 4095 downto -4096;
    variable vld_start_inter_not_ac_coded_RUN                   : integer range 127 downto -128;
    variable get_dc_bits_none_LAST                              : std_logic;
    variable get_dc_bits_none_VALUE                             : integer range 4095 downto -4096;
    variable get_dc_bits_none_RUN                               : integer range 127 downto -128;
    variable get_dc_bits_none0_1                                : std_logic;
    variable get_dc_bits_none1_1                                : std_logic;
    variable get_dc_bits_none_bool_expr_1                       : std_logic;
    variable get_dc_bits_none_bool_expr_2                       : std_logic;
    variable isSchedulable_get_dc_bits_none1_1                  : std_logic;
    variable isSchedulable_get_dc_bits_none2_1                  : integer range 524287 downto -524288;
    variable isSchedulable_get_dc_bits_none0_1                  : std_logic;
    variable isSchedulable_get_dc_bits_none0_2                  : std_logic;
    variable get_dc_bits_some0_1                                : integer range 524287 downto -524288;
    variable get_dc_bits_some1_1                                : integer range 15 downto -16;
    variable isSchedulable_get_dc_bits_some0_1                  : std_logic;
    variable isSchedulable_get_dc_bits_some0_2                  : std_logic;
    variable dc_bits_shift0_1                                   : integer range 15 downto -16;
    variable dc_bits_shift_count0_1                             : integer range 15 downto -16;
    variable dc_bits_shift_shift0_1                             : integer range 8191 downto -8192;
    variable dc_bits_shift_shift0_2                             : integer range 8191 downto -8192;
    variable dc_bits_shift_shift0_3                             : integer range 8191 downto -8192;
    variable dc_bits_shift_count0_2                             : integer range 15 downto -16;
    variable dc_bits_shift_count0_3                             : integer range 15 downto -16;
    variable get_dc_LAST                                        : std_logic;
    variable get_dc_VALUE                                       : integer range 4095 downto -4096;
    variable get_dc_RUN                                         : integer range 127 downto -128;
    variable get_dc_v0_1                                        : integer range 8191 downto -8192;
    variable get_dc0_1                                          : integer range 8191 downto -8192;
    variable get_dc1_1                                          : integer range 8191 downto -8192;
    variable get_dc_v0_2                                        : integer range 8191 downto -8192;
    variable get_dc_v0_3                                        : integer range 8191 downto -8192;
    variable get_dc2_1                                          : integer range 15 downto -16;
    variable get_dc3_1                                          : integer range 2147483647 downto -2147483648;
    variable get_dc3_2                                          : integer range 2147483647 downto -2147483648;
    variable get_dc3_3                                          : integer range 2147483647 downto -2147483648;
    variable get_dc5_1                                          : std_logic;
    variable get_dc6_1                                          : std_logic;
    variable get_dc_bool_expr_1                                 : std_logic;
    variable get_dc_bool_expr_2                                 : std_logic;
    variable isSchedulable_get_dc0_1                            : std_logic;
    variable isSchedulable_get_dc0_2                            : std_logic;
    variable block_done0_1                                      : integer range 7 downto -8;
    variable isSchedulable_block_done1_1                        : std_logic;
    variable isSchedulable_block_done2_1                        : std_logic;
    variable isSchedulable_block_done0_1                        : std_logic;
    variable isSchedulable_block_done0_2                        : std_logic;
    variable dct_coeff0_1                                       : std_logic;
    variable dct_coeff1_1                                       : integer range 2147483647 downto -2147483648;
    variable dct_coeff1_2                                       : integer range 2147483647 downto -2147483648;
    variable dct_coeff1_3                                       : integer range 2147483647 downto -2147483648;
    variable isSchedulable_dct_coeff0_1                         : std_logic;
    variable isSchedulable_dct_coeff0_2                         : std_logic;
    variable vld_code_LAST                                      : std_logic;
    variable vld_code_RUN                                       : integer range 127 downto -128;
    variable vld_code_VALUE                                     : integer range 4095 downto -4096;
    variable vld_code_bits                                      : std_logic;
    variable vld_code_sign_1                                    : std_logic;
    variable vld_code_val0_1                                    : integer range 524287 downto -524288;
    variable vld_code0_1                                        : std_logic;
    variable vld_code_run0_1                                    : integer range 127 downto -128;
    variable vld_code_run0_2                                    : integer range 127 downto -128;
    variable vld_code_run0_3                                    : integer range 127 downto -128;
    variable vld_code1_1                                        : std_logic;
    variable vld_code_last0_1                                   : std_logic;
    variable vld_code_last0_2                                   : std_logic;
    variable vld_code_last0_3                                   : std_logic;
    variable vld_code2_1                                        : std_logic;
    variable vld_code_level0_1                                  : integer range 4095 downto -4096;
    variable vld_code_level0_2                                  : integer range 4095 downto -4096;
    variable vld_code_level0_3                                  : integer range 4095 downto -4096;
    variable vld_code3_1                                        : integer range 7 downto -8;
    variable vld_code4_1                                        : integer range 4095 downto -4096;
    variable vld_code4_2                                        : integer range 4095 downto -4096;
    variable vld_code4_3                                        : integer range 4095 downto -4096;
    variable isSchedulable_vld_code_bits                        : std_logic;
    variable isSchedulable_vld_code1_1                          : std_logic;
    variable isSchedulable_vld_code2_1                          : std_logic;
    variable isSchedulable_vld_code3_1                          : integer range 524287 downto -524288;
    variable isSchedulable_vld_code0_1                          : std_logic;
    variable isSchedulable_vld_code0_2                          : std_logic;
    variable isSchedulable_vld_code0_3                          : std_logic;
    variable vld_level_bits                                     : std_logic;
    variable vld_level0_1                                       : integer range 7 downto -8;
    variable vld_level1_1                                       : std_logic;
    variable vld_level2_1                                       : integer range 2147483647 downto -2147483648;
    variable vld_level2_2                                       : integer range 2147483647 downto -2147483648;
    variable vld_level2_3                                       : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vld_level_bits                       : std_logic;
    variable isSchedulable_vld_level1_1                         : std_logic;
    variable isSchedulable_vld_level_level_offset_1             : std_logic;
    variable isSchedulable_vld_level2_1                         : std_logic;
    variable isSchedulable_vld_level0_1                         : std_logic;
    variable isSchedulable_vld_level0_2                         : std_logic;
    variable isSchedulable_vld_level0_3                         : std_logic;
    variable vld_run_or_direct_bits                             : std_logic;
    variable vld_run_or_direct0_1                               : integer range 7 downto -8;
    variable isSchedulable_vld_run_or_direct_bits               : std_logic;
    variable isSchedulable_vld_run_or_direct1_1                 : std_logic;
    variable isSchedulable_vld_run_or_direct0_1                 : std_logic;
    variable isSchedulable_vld_run_or_direct0_2                 : std_logic;
    variable isSchedulable_vld_run_or_direct0_3                 : std_logic;
    variable vld_run_bits                                       : std_logic;
    variable vld_run0_1                                         : integer range 7 downto -8;
    variable vld_run1_1                                         : std_logic;
    variable vld_run2_1                                         : integer range 2147483647 downto -2147483648;
    variable vld_run2_2                                         : integer range 2147483647 downto -2147483648;
    variable vld_run2_3                                         : integer range 2147483647 downto -2147483648;
    variable isSchedulable_vld_run_bits                         : std_logic;
    variable isSchedulable_vld_run1_1                           : std_logic;
    variable isSchedulable_vld_run_run_offset_1                 : std_logic;
    variable isSchedulable_vld_run0_1                           : std_logic;
    variable isSchedulable_vld_run0_2                           : std_logic;
    variable isSchedulable_vld_run0_3                           : std_logic;
    variable vld_direct_read_bits                               : std_logic;
    variable vld_direct_read0_1                                 : integer range 7 downto -8;
    variable isSchedulable_vld_direct_read1_1                   : std_logic;
    variable isSchedulable_vld_direct_read0_1                   : std_logic;
    variable isSchedulable_vld_direct_read0_2                   : std_logic;
    variable isSchedulable_vld_direct_read0_3                   : std_logic;
    variable vld_direct_LAST                                    : std_logic;
    variable vld_direct_RUN                                     : integer range 127 downto -128;
    variable vld_direct_VALUE                                   : integer range 4095 downto -4096;
    variable vld_direct0_1                                      : integer range 2147483647 downto -2147483648;
    variable vld_direct_last0_1                                 : std_logic;
    variable vld_direct5_1                                      : integer range 2147483647 downto -2147483648;
    variable vld_direct_run0_1                                  : integer range 127 downto -128;
    variable vld_direct10_1                                     : integer range 2147483647 downto -2147483648;
    variable vld_direct_level0_1                                : integer range 4095 downto -4096;
    variable vld_direct_sign0_1                                 : std_logic;
    variable vld_direct_sign0_2                                 : std_logic;
    variable vld_direct_level0_2                                : integer range 4095 downto -4096;
    variable vld_direct_level0_3                                : integer range 4095 downto -4096;
    variable vld_direct_sign0_3                                 : std_logic;
    variable vld_direct13_1                                     : integer range 4095 downto -4096;
    variable vld_direct13_2                                     : integer range 4095 downto -4096;
    variable vld_direct13_3                                     : integer range 4095 downto -4096;
    variable isSchedulable_vld_direct0_1                        : std_logic;
    variable isSchedulable_vld_direct0_2                        : std_logic;
    variable do_level_lookup_val0_1                             : integer range 131071 downto -131072;
    variable do_level_lookup0_1                                 : integer range 2147483647 downto -2147483648;
    variable do_level_lookup1_1                                 : integer range 2147483647 downto -2147483648;
    variable isSchedulable_do_level_lookup0_1                   : std_logic;
    variable isSchedulable_do_level_lookup0_2                   : std_logic;
    variable vld_level_lookup_LAST                              : std_logic;
    variable vld_level_lookup_RUN                               : integer range 127 downto -128;
    variable vld_level_lookup_VALUE                             : integer range 4095 downto -4096;
    variable vld_level_lookup_bits                              : std_logic;
    variable vld_level_lookup_sign_1                            : std_logic;
    variable vld_level_lookup_val0_1                            : integer range 131071 downto -131072;
    variable vld_level_lookup0_1                                : std_logic;
    variable vld_level_lookup_run0_1                            : integer range 127 downto -128;
    variable vld_level_lookup_run0_2                            : integer range 127 downto -128;
    variable vld_level_lookup_run0_3                            : integer range 127 downto -128;
    variable vld_level_lookup1_1                                : std_logic;
    variable vld_level_lookup_last0_1                           : std_logic;
    variable vld_level_lookup_last0_2                           : std_logic;
    variable vld_level_lookup_last0_3                           : std_logic;
    variable vld_level_lookup2_1                                : std_logic;
    variable vld_level_lookup3_1                                : integer range 4095 downto -4096;
    variable vld_level_lookup_level0_1                          : integer range 4095 downto -4096;
    variable vld_level_lookup_level0_2                          : integer range 4095 downto -4096;
    variable vld_level_lookup4_1                                : integer range 4095 downto -4096;
    variable vld_level_lookup_level0_3                          : integer range 4095 downto -4096;
    variable vld_level_lookup5_1                                : integer range 7 downto -8;
    variable vld_level_lookup6_1                                : integer range 4095 downto -4096;
    variable vld_level_lookup6_2                                : integer range 4095 downto -4096;
    variable vld_level_lookup6_3                                : integer range 4095 downto -4096;
    variable isSchedulable_vld_level_lookup1_1                  : std_logic;
    variable isSchedulable_vld_level_lookup0_1                  : std_logic;
    variable isSchedulable_vld_level_lookup0_2                  : std_logic;
    variable isSchedulable_vld_level_lookup0_3                  : std_logic;
    variable do_run_lookup_val0_1                               : integer range 131071 downto -131072;
    variable do_run_lookup0_1                                   : integer range 2147483647 downto -2147483648;
    variable do_run_lookup1_1                                   : integer range 2147483647 downto -2147483648;
    variable isSchedulable_do_run_lookup0_1                     : std_logic;
    variable isSchedulable_do_run_lookup0_2                     : std_logic;
    variable vld_run_lookup_LAST                                : std_logic;
    variable vld_run_lookup_RUN                                 : integer range 127 downto -128;
    variable vld_run_lookup_VALUE                               : integer range 4095 downto -4096;
    variable vld_run_lookup_bits                                : std_logic;
    variable vld_run_lookup_sign_1                              : std_logic;
    variable vld_run_lookup_val0_1                              : integer range 131071 downto -131072;
    variable vld_run_lookup0_1                                  : std_logic;
    variable vld_run_lookup_last0_1                             : std_logic;
    variable vld_run_lookup_last0_2                             : std_logic;
    variable vld_run_lookup_last0_3                             : std_logic;
    variable vld_run_lookup1_1                                  : std_logic;
    variable vld_run_lookup_level0_1                            : integer range 4095 downto -4096;
    variable vld_run_lookup_level0_2                            : integer range 4095 downto -4096;
    variable vld_run_lookup_level0_3                            : integer range 4095 downto -4096;
    variable vld_run_lookup2_1                                  : std_logic;
    variable vld_run_lookup4_1                                  : integer range 4095 downto -4096;
    variable vld_run_lookup3_1                                  : integer range 2147483647 downto -2147483648;
    variable vld_run_lookup3_2                                  : integer range 2147483647 downto -2147483648;
    variable vld_run_lookup5_1                                  : integer range 4095 downto -4096;
    variable vld_run_lookup3_3                                  : integer range 2147483647 downto -2147483648;
    variable vld_run_lookup_run0_1                              : integer range 127 downto -128;
    variable vld_run_lookup6_1                                  : integer range 7 downto -8;
    variable vld_run_lookup7_1                                  : integer range 4095 downto -4096;
    variable vld_run_lookup7_2                                  : integer range 4095 downto -4096;
    variable vld_run_lookup7_3                                  : integer range 4095 downto -4096;
    variable isSchedulable_vld_run_lookup1_1                    : std_logic;
    variable isSchedulable_vld_run_lookup0_1                    : std_logic;
    variable isSchedulable_vld_run_lookup0_2                    : std_logic;
    variable isSchedulable_vld_run_lookup0_3                    : std_logic;
    variable isSchedulable_mvcode_done1_1                       : integer range 7 downto -8;
    variable isSchedulable_mvcode_done2_1                       : integer range 7 downto -8;
    variable isSchedulable_mvcode_done3_1                       : std_logic;
    variable isSchedulable_mvcode_done0_1                       : std_logic;
    variable isSchedulable_mvcode_done0_2                       : std_logic;
    variable mag_x_MV                                           : integer range 255 downto -256;
    variable mag_x_mvval0_1                                     : integer range 524287 downto -524288;
    variable mag_x0_1                                           : integer range 7 downto -8;
    variable mag_x1_1                                           : integer range 7 downto -8;
    variable mag_x1_2                                           : integer range 7 downto -8;
    variable mag_x2_1                                           : integer range 7 downto -8;
    variable mag_x1_3                                           : integer range 7 downto -8;
    variable isSchedulable_mag_x0_1                             : std_logic;
    variable isSchedulable_mag_x0_2                             : std_logic;
    variable get_residual_x_MV                                  : integer range 255 downto -256;
    variable get_residual_x0_1                                  : integer range 2147483647 downto -2147483648;
    variable isSchedulable_get_residual_x0_1                    : std_logic;
    variable isSchedulable_get_residual_x0_2                    : std_logic;
    variable mag_y_MV                                           : integer range 255 downto -256;
    variable mag_y_mvval0_1                                     : integer range 524287 downto -524288;
    variable mag_y0_1                                           : integer range 7 downto -8;
    variable mag_y1_1                                           : integer range 7 downto -8;
    variable mag_y1_2                                           : integer range 7 downto -8;
    variable mag_y2_1                                           : integer range 7 downto -8;
    variable mag_y1_3                                           : integer range 7 downto -8;
    variable isSchedulable_mag_y0_1                             : std_logic;
    variable isSchedulable_mag_y0_2                             : std_logic;
    variable get_residual_y_MV                                  : integer range 255 downto -256;
    variable get_residual_y0_1                                  : integer range 7 downto -8;
    variable get_residual_y1_1                                  : integer range 2147483647 downto -2147483648;
    variable isSchedulable_get_residual_y0_1                    : std_logic;
    variable isSchedulable_get_residual_y0_2                    : std_logic;
    variable untagged02_bits                                    : std_logic;
    variable untagged02_b_1                                     : std_logic;
    variable untagged020_1                                      : integer range 2047 downto -2048;
    variable untagged021_1                                      : integer range 1 downto -2;
    variable untagged021_2                                      : integer range 1 downto -2;
    variable untagged021_3                                      : integer range 1 downto -2;
    variable untagged022_1                                      : integer range 524287 downto -524288;
    variable untagged023_1                                      : integer range 524287 downto -524288;
    variable untagged024_1                                      : integer range 7 downto -8;
    variable isSchedulable_untagged02_bits                      : std_logic;
    variable isSchedulable_untagged021_1                        : std_logic;
    variable isSchedulable_untagged022_1                        : integer range 524287 downto -524288;
    variable isSchedulable_untagged020_1                        : std_logic;
    variable isSchedulable_untagged020_2                        : std_logic;
    variable isSchedulable_untagged020_3                        : std_logic;
    variable isSchedulable_do_vld_failure0_1                    : std_logic;
    variable isSchedulable_do_vld_failure0_2                    : std_logic;
    variable isSchedulable_generic_done0_1                      : std_logic;
    variable isSchedulable_generic_done0_2                      : std_logic;
    variable generic_done_with_bitread_bits                     : std_logic;
    variable generic_done_with_bitread0_1                       : integer range 7 downto -8;
    variable isSchedulable_generic_done_with_bitread_bits       : std_logic;
    variable isSchedulable_generic_done_with_bitread1_1         : std_logic;
    variable isSchedulable_generic_done_with_bitread0_1         : std_logic;
    variable isSchedulable_generic_done_with_bitread0_2         : std_logic;
    variable isSchedulable_generic_done_with_bitread0_3         : std_logic;
    variable isSchedulable_test_zero_byte1_1                    : std_logic;
    variable isSchedulable_test_zero_byte2_1                    : integer range 2147483647 downto -2147483648;
    variable isSchedulable_test_zero_byte0_1                    : std_logic;
    variable isSchedulable_test_zero_byte0_2                    : std_logic;
    variable isSchedulable_test_vo_byte1_1                      : std_logic;
    variable isSchedulable_test_vo_byte2_1                      : integer range 2147483647 downto -2147483648;
    variable isSchedulable_test_vo_byte0_1                      : std_logic;
    variable isSchedulable_test_vo_byte0_2                      : std_logic;
    variable isSchedulable_test_vol_byte1_1                     : std_logic;
    variable isSchedulable_test_vol_byte2_1                     : integer range 2147483647 downto -2147483648;
    variable isSchedulable_test_vol_byte3_1                     : integer range 2147483647 downto -2147483648;
    variable isSchedulable_test_vol_byte0_1                     : std_logic;
    variable isSchedulable_test_vol_byte0_2                     : std_logic;
    variable isSchedulable_test_vop_byte1_1                     : std_logic;
    variable isSchedulable_test_vop_byte2_1                     : integer range 2147483647 downto -2147483648;
    variable isSchedulable_test_vop_byte3_1                     : integer range 2147483647 downto -2147483648;
    variable isSchedulable_test_vop_byte0_1                     : std_logic;
    variable isSchedulable_test_vop_byte0_2                     : std_logic;
    variable isSchedulable_test_one_byte1_1                     : std_logic;
    variable isSchedulable_test_one_byte2_1                     : integer range 2147483647 downto -2147483648;
    variable isSchedulable_test_one_byte0_1                     : std_logic;
    variable isSchedulable_test_one_byte0_2                     : std_logic;
    variable isSchedulable_request_byte0_1                      : std_logic;
    variable isSchedulable_request_byte0_2                      : std_logic;
    variable next_mbxy0_1                                       : integer;
    variable next_mbxy1_1                                       : integer;
    variable next_mbxy2_1                                       : integer;
    variable next_mbxy3_1                                       : integer;
    variable interm_comput                                       : std_logic;
    --
  begin
    if reset_n = '0' then
      VOL_START_CODE                <= 32;
      ASPECT_RATIO_INFO_LENGTH      <= 4;
      ASPECT_RATIO_INFO_IS_DETAILED <= 15;
      VOP_START_CODE                <= 182;
      VOP_PREDICTION_LENGTH         <= 2;
      P_VOP                         <= 1;
      I_VOP                         <= 0;
      bits_to_read_count            <= -1;
      bit_count                     <= 0;
      vld_table <= (10, 12, 18, 58, 26, 76, 34, 16, 42, 50, 1, 80, 144, 208, 140, 204, 74, 0, 82, 226,
                    90, 218, 98, 202, 106, 178, 114, 162, 122, 146, 130, 138, 1, 1, 208, 144, 154, 140,
                    80, 196, 170, 204, 76, 200, 186, 194, 136, 72, 132, 68, 210, 12, 16, 192, 128, 64,
                    8, 4, 242, 338, 250, 314, 258, 298, 266, 290, 274, 282, 1, 1, 24, 36, 32, 16, 306,
                    0, 8, 4, 322, 330, 48, 40, 56, 20, 346, 60, 354, 362, 52, 12, 44, 28, 378, 466,
                    386, 458, 394, 16, 402, 20, 410, 24, 418, 28, 426, 32, 434, 36, 442, 40, 450, 44,
                    1, 48, 12, 0, 8, 4, 482, 570, 490, 8, 498, 12, 506, 16, 514, 20, 522, 24, 530, 28,
                    538, 32, 546, 36, 554, 40, 562, 44, 1, 48, 4, 0, 586, 1498, 594, 1426, 602, 1338,
                    610, 1194, 618, 1066, 626, 874, 634, 818, 642, 794, 650, 770, 658, 714, 666, 690,
                    674, 682, 1, 1, 1, 1, 698, 706, 1, 1, 1, 1, 722, 746, 730, 738, 1, 1, 1, 1, 754,
                    762, 1, 1, 1, 1, 778, 786, 16456, 16396, 44, 40, 802, 810, 18180, 18116, 18052,
                    17988, 826, 850, 834, 842, 584, 520, 456, 392, 858, 866, 328, 204, 140, 80, 882,
                    28668, 890, 946, 898, 922, 906, 914, 48, 84, 1476, 1540, 930, 938, 18244, 18308,
                    18372, 18436, 954, 1010, 962, 986, 970, 978, 88, 144, 268, 332, 994, 1002, 396,
                    648, 1604, 1668, 1018, 1042, 1026, 1034, 18500, 18564, 18628, 18692, 1050, 1058,
                    18756, 18820, 18884, 18948, 1074, 1138, 1082, 1114, 1090, 1106, 1098, 17924, 36,
                    32, 17860, 17796, 1122, 1130, 17732, 17668, 17604, 17540, 1146, 1170, 1154, 1162,
                    17476, 16392, 1412, 1348, 1178, 1186, 1284, 1220, 1156, 1092, 1202, 1282, 1210,
                    1258, 1218, 1242, 1226, 1234, 1028, 964, 264, 200, 1250, 17412, 28, 24, 1266, 1274,
                    17348, 17284, 17220, 17156, 1290, 1314, 1298, 1306, 17092, 17028, 16964, 900, 1322,
                    1330, 836, 136, 76, 20, 1346, 1402, 1354, 1378, 1362, 1370, 16900, 16836, 16772,
                    16708, 1386, 1394, 772, 708, 644, 16, 1410, 1418, 16644, 16580, 16516, 16452, 1434,
                    1482, 1442, 1466, 1450, 1458, 580, 516, 452, 388, 1474, 324, 72, 12, 1490, 16388,
                    260, 196, 4, 1506, 68, 1514, 132, 8, 1530, 2442, 1538, 2370, 1546, 2282, 1554, 2138,
                    1562, 2010, 1570, 1818, 1578, 1762, 1586, 1738, 1594, 1714, 1602, 1658, 1610, 1634,
                    1618, 1626, 1, 1, 1, 1, 1642, 1650, 1, 1, 1, 1, 1666, 1690, 1674, 1682, 1, 1, 1,
                    1, 1698, 1706, 1, 1, 1, 1, 1722, 1730, 262172, 262168, 88, 84, 1746, 1754, 264200,
                    263180, 262164, 13316, 1770, 1794, 1778, 1786, 5132, 8200, 4108, 3088, 1802, 1810,
                    2064, 1052, 80, 76, 1826, 28668, 1834, 1890, 1842, 1866, 1850, 1858, 92, 96, 1056,
                    9224, 1874, 1882, 265224, 266248, 277508, 278532, 1898, 1954, 1906, 1930, 1914,
                    1922, 100, 104, 108, 1060, 1938, 1946, 6156, 1064, 2068, 7180, 1962, 1986, 1970,
                    1978, 14340, 262176, 267272, 268296, 1994, 2002, 279556, 280580, 281604, 282628,
                    2018, 2082, 2026, 2058, 2034, 2050, 2042, 276484, 72, 68, 275460, 274436, 2066,
                    2074, 273412, 272388, 263176, 262160, 2090, 2114, 2098, 2106, 12292, 11268, 7176,
                    6152, 2122, 2130, 5128, 3084, 2060, 1048, 2146, 2226, 2154, 2202, 2162, 2186, 2170,
                    2178, 1044, 64, 4104, 60, 2194, 270340, 56, 52, 2210, 2218, 269316, 268292, 262156,
                    10244, 2234, 2258, 2242, 2250, 9220, 8196, 271364, 3080, 2266, 2274, 1040, 48, 44,
                    40, 2290, 2346, 2298, 2322, 2306, 2314, 266244, 265220, 6148, 267268, 2330, 2338,
                    7172, 2056, 1036, 36, 2354, 2362, 262152, 5124, 264196, 263172, 2378, 2426, 2386,
                    2410, 2394, 2402, 4100, 3076, 32, 28, 2418, 2052, 1032, 24, 2434, 262148, 20, 16,
                    4, 2450, 8, 2458, 1028, 12, 2474, 0, 2482, 3034, 2490, 3026, 2498, 3018, 2506, 2978,
                    2514, 2890, 2522, 2770, 2530, 2714, 2538, 2658, 2546, 2634, 2554, 2610, 2562, 2586,
                    2570, 2578, 1, 1, 1, 1, 2594, 2602, 1, 1, 1, 1, 2618, 2626, 128, -128, 124, -124,
                    2642, 2650, 120, -120, 116, -116, 2666, 2690, 2674, 2682, 112, -112, 108, -108,
                    2698, 2706, 104, -104, 100, -100, 2722, 2746, 2730, 2738, 96, -96, 92, -92, 2754,
                    2762, 88, -88, 84, -84, 2778, 2834, 2786, 2810, 2794, 2802, 80, -80, 76, -76, 2818,
                    2826, 72, -72, 68, -68, 2842, 2866, 2850, 2858, 64, -64, 60, -60, 2874, 2882, 56,
                    -56, 52, -52, 2898, 2970, 2906, 2946, 2914, 2938, 2922, 2930, 48, -48, 44, -44,
                    40, -40, 2954, 2962, 36, -36, 32, -32, 28, -28, 2986, 3010, 2994, 3002, 24, -24,
                    20, -20, 16, -16, 12, -12, 8, -8, 4, -4);
      vld_codeword              <= 1;
      FSM                       <= s_stuck_1a;
      --
      parseheaders_bits_read    <= '0';
      parseheaders_BTYPE_write  <= '0';
      parseheaders_MV_write     <= '0';
      parseheaders_RUN_write    <= '0';
      parseheaders_VALUE_write  <= '0';
      parseheaders_LAST_write   <= '0';
      parseheaders_WIDTH_write  <= '0';
      parseheaders_HEIGHT_write <= '0';
      --
    elsif rising_edge(clock) then
      parseheaders_bits_read      <= '0';
      parseheaders_BTYPE_write    <= '0';
      parseheaders_MV_write       <= '0';
      parseheaders_RUN_write      <= '0';
      parseheaders_VALUE_write    <= '0';
      parseheaders_LAST_write     <= '0';
      parseheaders_WIDTH_write    <= '0';
      parseheaders_HEIGHT_write   <= '0';
      --
      -- tests if "untagged01" action is schedulable
      isSchedulable_untagged011_1 := not parseheaders_bits_empty;
      if (isSchedulable_untagged011_1 = '1') then
        isSchedulable_untagged012_1 := done_reading_bits(bits_to_read_count, '1');
        if (isSchedulable_untagged012_1 = '0') then
          isSchedulable_untagged010_1 := '1';
        else
          isSchedulable_untagged010_1 := '0';
        end if;
        isSchedulable_untagged010_2 := isSchedulable_untagged010_1;
      else
        isSchedulable_untagged010_3 := '0';
        isSchedulable_untagged010_2 := isSchedulable_untagged010_3;
      end if;
      isSchedulable_untagged01_go := isSchedulable_untagged010_2;

      -- tests if "vol_object_layer_identification" action is schedulable
      isSchedulable_vol_object_layer_identification1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vol_object_layer_identification1_1 = '1') then
        isSchedulable_vol_object_layer_identification0_1 := done_reading_bits(bits_to_read_count, '1');
        isSchedulable_vol_object_layer_identification0_2 := isSchedulable_vol_object_layer_identification0_1;
      else
        isSchedulable_vol_object_layer_identification0_3 := '0';
        isSchedulable_vol_object_layer_identification0_2 := isSchedulable_vol_object_layer_identification0_3;
      end if;
      isSchedulable_vol_object_layer_identification_go := isSchedulable_vol_object_layer_identification0_2;

      -- tests if "vol_aspect_detailed" action is schedulable
      isSchedulable_vol_aspect_detailed1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_vol_aspect_detailed2_1 := read_result(read_result_in_progress, '1');
      isSchedulable_vol_aspect_detailed3_1 := ASPECT_RATIO_INFO_LENGTH;
      isSchedulable_vol_aspect_detailed4_1 := mask_bits(isSchedulable_vol_aspect_detailed2_1, isSchedulable_vol_aspect_detailed3_1, '1');
      isSchedulable_vol_aspect_detailed5_1 := ASPECT_RATIO_INFO_IS_DETAILED;
      if (isSchedulable_vol_aspect_detailed1_1 = '1' and isSchedulable_vol_aspect_detailed4_1 = isSchedulable_vol_aspect_detailed5_1) then
        isSchedulable_vol_aspect_detailed0_1 := '1';
      else
        isSchedulable_vol_aspect_detailed0_1 := '0';
      end if;
      isSchedulable_vol_aspect_detailed0_2 := isSchedulable_vol_aspect_detailed0_1;
      isSchedulable_vol_aspect_detailed_go := isSchedulable_vol_aspect_detailed0_2;

      -- tests if "vol_control_detailed" action is schedulable
      isSchedulable_vol_control_detailed1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vol_control_detailed1_1 = '1') then
        isSchedulable_vol_control_detailed_b_1 := isSchedulable_vol_control_detailed_bits;
        isSchedulable_vol_control_detailed2_1  := done_reading_bits(bits_to_read_count, '1');
        if (isSchedulable_vol_control_detailed2_1 = '1' and isSchedulable_vol_control_detailed_b_1 = '1') then
          isSchedulable_vol_control_detailed0_1 := '1';
        else
          isSchedulable_vol_control_detailed0_1 := '0';
        end if;
        isSchedulable_vol_control_detailed0_2 := isSchedulable_vol_control_detailed0_1;
      else
        isSchedulable_vol_control_detailed0_3 := '0';
        isSchedulable_vol_control_detailed0_2 := isSchedulable_vol_control_detailed0_3;
      end if;
      isSchedulable_vol_control_detailed_go := isSchedulable_vol_control_detailed0_2;

      -- tests if "vol_vbv_detailed" action is schedulable
      isSchedulable_vol_vbv_detailed1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vol_vbv_detailed1_1 = '1') then
        isSchedulable_vol_vbv_detailed_b_1 := isSchedulable_vol_vbv_detailed_bits;
        isSchedulable_vol_vbv_detailed2_1  := done_reading_bits(bits_to_read_count, '1');
        if (isSchedulable_vol_vbv_detailed2_1 = '1' and isSchedulable_vol_vbv_detailed_b_1 = '1') then
          isSchedulable_vol_vbv_detailed0_1 := '1';
        else
          isSchedulable_vol_vbv_detailed0_1 := '0';
        end if;
        isSchedulable_vol_vbv_detailed0_2 := isSchedulable_vol_vbv_detailed0_1;
      else
        isSchedulable_vol_vbv_detailed0_3 := '0';
        isSchedulable_vol_vbv_detailed0_2 := isSchedulable_vol_vbv_detailed0_3;
      end if;
      isSchedulable_vol_vbv_detailed_go := isSchedulable_vol_vbv_detailed0_2;

      -- tests if "vol_shape" action is schedulable
      isSchedulable_vol_shape0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_vol_shape0_2 := isSchedulable_vol_shape0_1;
      isSchedulable_vol_shape_go := isSchedulable_vol_shape0_2;

      -- tests if "vol_time_inc_res" action is schedulable
      isSchedulable_vol_time_inc_res0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_vol_time_inc_res0_2 := isSchedulable_vol_time_inc_res0_1;
      isSchedulable_vol_time_inc_res_go := isSchedulable_vol_time_inc_res0_2;

      -- tests if "set_vol_width" action is schedulable
      isSchedulable_set_vol_width0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_set_vol_width0_2 := isSchedulable_set_vol_width0_1;
      isSchedulable_set_vol_width_go := isSchedulable_set_vol_width0_2;

      -- tests if "set_vol_height" action is schedulable
      isSchedulable_set_vol_height0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_set_vol_height0_2 := isSchedulable_set_vol_height0_1;
      isSchedulable_set_vol_height_go := isSchedulable_set_vol_height0_2;

      -- tests if "byte_align" action is schedulable
      isSchedulable_byte_align_go := '1';

      -- tests if "vop_predict_supported" action is schedulable
      isSchedulable_vop_predict_supported1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_vop_predict_supported2_1 := read_result(read_result_in_progress, '1');
      isSchedulable_vop_predict_supported3_1 := VOP_PREDICTION_LENGTH;
      isSchedulable_vop_predict_supported4_1 := mask_bits(isSchedulable_vop_predict_supported2_1, isSchedulable_vop_predict_supported3_1, '1');
      isSchedulable_vop_predict_supported5_1 := I_VOP;
      isSchedulable_vop_predict_supported6_1 := read_result(read_result_in_progress, '1');
      isSchedulable_vop_predict_supported7_1 := VOP_PREDICTION_LENGTH;
      isSchedulable_vop_predict_supported8_1 := mask_bits(isSchedulable_vop_predict_supported6_1, isSchedulable_vop_predict_supported7_1, '1');
      isSchedulable_vop_predict_supported9_1 := P_VOP;
      if (isSchedulable_vop_predict_supported1_1 = '1' and ((isSchedulable_vop_predict_supported4_1 = isSchedulable_vop_predict_supported5_1) or (isSchedulable_vop_predict_supported8_1 = isSchedulable_vop_predict_supported9_1))) then
        isSchedulable_vop_predict_supported0_1 := '1';
      else
        isSchedulable_vop_predict_supported0_1 := '0';
      end if;
      isSchedulable_vop_predict_supported0_2 := isSchedulable_vop_predict_supported0_1;
      isSchedulable_vop_predict_supported_go := isSchedulable_vop_predict_supported0_2;

      -- tests if "vop_timebase_one" action is schedulable
      isSchedulable_vop_timebase_one1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vop_timebase_one1_1 = '1') then
        isSchedulable_vop_timebase_one_b_1 := isSchedulable_vop_timebase_one_bits;
        isSchedulable_vop_timebase_one0_1  := isSchedulable_vop_timebase_one_b_1;
        isSchedulable_vop_timebase_one0_2  := isSchedulable_vop_timebase_one0_1;
      else
        isSchedulable_vop_timebase_one0_3 := '0';
        isSchedulable_vop_timebase_one0_2 := isSchedulable_vop_timebase_one0_3;
      end if;
      isSchedulable_vop_timebase_one_go := isSchedulable_vop_timebase_one0_2;

      -- tests if "vop_timebase_zero" action is schedulable
      isSchedulable_vop_timebase_zero1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vop_timebase_zero1_1 = '1') then
        isSchedulable_vop_timebase_zero0_1 := '1';
        isSchedulable_vop_timebase_zero0_2 := isSchedulable_vop_timebase_zero0_1;
      else
        isSchedulable_vop_timebase_zero0_3 := '0';
        isSchedulable_vop_timebase_zero0_2 := isSchedulable_vop_timebase_zero0_3;
      end if;
      isSchedulable_vop_timebase_zero_go := isSchedulable_vop_timebase_zero0_2;

      -- tests if "vop_coding_uncoded" action is schedulable
      isSchedulable_vop_coding_uncoded1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vop_coding_uncoded1_1 = '1') then
        isSchedulable_vop_coding_uncoded_b_1 := isSchedulable_vop_coding_uncoded_bits;
        isSchedulable_vop_coding_uncoded2_1  := done_reading_bits(bits_to_read_count, '1');
        if (isSchedulable_vop_coding_uncoded2_1 = '1' and isSchedulable_vop_coding_uncoded_b_1 = '0') then
          isSchedulable_vop_coding_uncoded0_1 := '1';
        else
          isSchedulable_vop_coding_uncoded0_1 := '0';
        end if;
        isSchedulable_vop_coding_uncoded0_2 := isSchedulable_vop_coding_uncoded0_1;
      else
        isSchedulable_vop_coding_uncoded0_3 := '0';
        isSchedulable_vop_coding_uncoded0_2 := isSchedulable_vop_coding_uncoded0_3;
      end if;
      isSchedulable_vop_coding_uncoded_go := isSchedulable_vop_coding_uncoded0_2;

      -- tests if "vop_coding_coded" action is schedulable
      isSchedulable_vop_coding_coded1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vop_coding_coded1_1 = '1') then
        isSchedulable_vop_coding_coded0_1 := done_reading_bits(bits_to_read_count, '1');
        isSchedulable_vop_coding_coded0_2 := isSchedulable_vop_coding_coded0_1;
      else
        isSchedulable_vop_coding_coded0_3 := '0';
        isSchedulable_vop_coding_coded0_2 := isSchedulable_vop_coding_coded0_3;
      end if;
      isSchedulable_vop_coding_coded_go := isSchedulable_vop_coding_coded0_2;

      -- tests if "send_new_vop_cmd" action is schedulable
      isSchedulable_send_new_vop_cmd0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_send_new_vop_cmd0_2 := isSchedulable_send_new_vop_cmd0_1;
      isSchedulable_send_new_vop_cmd_go := isSchedulable_send_new_vop_cmd0_2;

      -- tests if "send_new_vop_width" action is schedulable
      isSchedulable_send_new_vop_width_go := '1';

      -- tests if "send_new_vop_height" action is schedulable
      isSchedulable_send_new_vop_height_go := '1';

      -- tests if "mb_done" action is schedulable
      isSchedulable_mb_done1_1 := mby;
      isSchedulable_mb_done2_1 := vol_height;
      if (isSchedulable_mb_done1_1 = isSchedulable_mb_done2_1) then
        isSchedulable_mb_done0_1 := '1';
      else
        isSchedulable_mb_done0_1 := '0';
      end if;
      isSchedulable_mb_done0_2 := isSchedulable_mb_done0_1;
      isSchedulable_mb_done_go := isSchedulable_mb_done0_2;

      -- tests if "get_mcbpc_ivop" action is schedulable
      isSchedulable_get_mcbpc_ivop1_1 := prediction_is_IVOP;
      isSchedulable_get_mcbpc_ivop0_1 := isSchedulable_get_mcbpc_ivop1_1;
      isSchedulable_get_mcbpc_ivop0_2 := isSchedulable_get_mcbpc_ivop0_1;
      isSchedulable_get_mcbpc_ivop_go := isSchedulable_get_mcbpc_ivop0_2;

      -- tests if "get_mcbpc_pvop" action is schedulable
      isSchedulable_get_mcbpc_pvop1_1 := not parseheaders_bits_empty;
      if (isSchedulable_get_mcbpc_pvop1_1 = '1') then
        isSchedulable_get_mcbpc_pvop_b_1 := isSchedulable_get_mcbpc_pvop_bits;
        isSchedulable_get_mcbpc_pvop2_1  := prediction_is_IVOP;
        if (isSchedulable_get_mcbpc_pvop2_1 = '0' and isSchedulable_get_mcbpc_pvop_b_1 = '0') then
          isSchedulable_get_mcbpc_pvop0_1 := '1';
        else
          isSchedulable_get_mcbpc_pvop0_1 := '0';
        end if;
        isSchedulable_get_mcbpc_pvop0_2 := isSchedulable_get_mcbpc_pvop0_1;
      else
        isSchedulable_get_mcbpc_pvop0_3 := '0';
        isSchedulable_get_mcbpc_pvop0_2 := isSchedulable_get_mcbpc_pvop0_3;
      end if;
      isSchedulable_get_mcbpc_pvop_go := isSchedulable_get_mcbpc_pvop0_2;

      -- tests if "mcbpc_pvop_uncoded" action is schedulable
      isSchedulable_mcbpc_pvop_uncoded1_1 := not parseheaders_bits_empty;
      if (isSchedulable_mcbpc_pvop_uncoded1_1 = '1') then
        isSchedulable_mcbpc_pvop_uncoded2_1 := prediction_is_IVOP;
        if (isSchedulable_mcbpc_pvop_uncoded2_1 = '0') then
          isSchedulable_mcbpc_pvop_uncoded0_1 := '1';
        else
          isSchedulable_mcbpc_pvop_uncoded0_1 := '0';
        end if;
        isSchedulable_mcbpc_pvop_uncoded0_2 := isSchedulable_mcbpc_pvop_uncoded0_1;
      else
        isSchedulable_mcbpc_pvop_uncoded0_3 := '0';
        isSchedulable_mcbpc_pvop_uncoded0_2 := isSchedulable_mcbpc_pvop_uncoded0_3;
      end if;
      isSchedulable_mcbpc_pvop_uncoded_go := isSchedulable_mcbpc_pvop_uncoded0_2;

      -- tests if "mcbpc_pvop_uncoded1" action is schedulable
      isSchedulable_mcbpc_pvop_uncoded1_go := '1';

      -- tests if "get_mbtype_noac" action is schedulable
      isSchedulable_get_mbtype_noac1_1 := vld_success(vld_codeword, '1');
      isSchedulable_get_mbtype_noac2_1 := vld_result(vld_codeword, '1');
      isSchedulable_get_mbtype_noac3_1 := vld_result(vld_codeword, '1');
      if (isSchedulable_get_mbtype_noac1_1 = '1' and bitand(isSchedulable_get_mbtype_noac2_1, 20, 7, 4) /= 3 and bitand(isSchedulable_get_mbtype_noac3_1, 20, 7, 4) /= 4) then
        isSchedulable_get_mbtype_noac0_1 := '1';
      else
        isSchedulable_get_mbtype_noac0_1 := '0';
      end if;
      isSchedulable_get_mbtype_noac0_2 := isSchedulable_get_mbtype_noac0_1;
      isSchedulable_get_mbtype_noac_go := isSchedulable_get_mbtype_noac0_2;

      -- tests if "get_mbtype_ac" action is schedulable
      isSchedulable_get_mbtype_ac1_1 := not parseheaders_bits_empty;
      if (isSchedulable_get_mbtype_ac1_1 = '1') then
        isSchedulable_get_mbtype_ac0_1 := vld_success(vld_codeword, '1');
        isSchedulable_get_mbtype_ac0_2 := isSchedulable_get_mbtype_ac0_1;
      else
        isSchedulable_get_mbtype_ac0_3 := '0';
        isSchedulable_get_mbtype_ac0_2 := isSchedulable_get_mbtype_ac0_3;
      end if;
      isSchedulable_get_mbtype_ac_go := isSchedulable_get_mbtype_ac0_2;

      -- tests if "final_cbpy_inter" action is schedulable
      isSchedulable_final_cbpy_inter1_1 := vld_success(vld_codeword, '1');
      isSchedulable_final_cbpy_inter2_1 := btype_is_INTRA;
      if (isSchedulable_final_cbpy_inter1_1 = '1' and isSchedulable_final_cbpy_inter2_1 = '0') then
        isSchedulable_final_cbpy_inter0_1 := '1';
      else
        isSchedulable_final_cbpy_inter0_1 := '0';
      end if;
      isSchedulable_final_cbpy_inter0_2 := isSchedulable_final_cbpy_inter0_1;
      isSchedulable_final_cbpy_inter_go := isSchedulable_final_cbpy_inter0_2;

      -- tests if "final_cbpy_intra" action is schedulable
      isSchedulable_final_cbpy_intra0_1 := vld_success(vld_codeword, '1');
      isSchedulable_final_cbpy_intra0_2 := isSchedulable_final_cbpy_intra0_1;
      isSchedulable_final_cbpy_intra_go := isSchedulable_final_cbpy_intra0_2;

      -- tests if "mb_dispatch_done" action is schedulable
      isSchedulable_mb_dispatch_done1_1 := comp;
      if (isSchedulable_mb_dispatch_done1_1 = 6) then
        isSchedulable_mb_dispatch_done0_1 := '1';
      else
        isSchedulable_mb_dispatch_done0_1 := '0';
      end if;
      isSchedulable_mb_dispatch_done0_2 := isSchedulable_mb_dispatch_done0_1;
      isSchedulable_mb_dispatch_done_go := isSchedulable_mb_dispatch_done0_2;

      -- tests if "mb_dispatch_intra" action is schedulable
      isSchedulable_mb_dispatch_intra1_1 := btype_is_INTRA;
      isSchedulable_mb_dispatch_intra0_1 := isSchedulable_mb_dispatch_intra1_1;
      isSchedulable_mb_dispatch_intra0_2 := isSchedulable_mb_dispatch_intra0_1;
      isSchedulable_mb_dispatch_intra_go := isSchedulable_mb_dispatch_intra0_2;

      -- tests if "mb_dispatch_inter_no_ac" action is schedulable
      isSchedulable_mb_dispatch_inter_no_ac1_1 := cbp;
      isSchedulable_mb_dispatch_inter_no_ac2_1 := comp;
      if (bitand(isSchedulable_mb_dispatch_inter_no_ac1_1, 7, shift_left(1, 2, 5 - isSchedulable_mb_dispatch_inter_no_ac2_1, 4), 32) = 0) then
        isSchedulable_mb_dispatch_inter_no_ac0_1 := '1';
      else
        isSchedulable_mb_dispatch_inter_no_ac0_1 := '0';
      end if;
      isSchedulable_mb_dispatch_inter_no_ac0_2 := isSchedulable_mb_dispatch_inter_no_ac0_1;
      isSchedulable_mb_dispatch_inter_no_ac_go := isSchedulable_mb_dispatch_inter_no_ac0_2;

      -- tests if "mb_dispatch_inter_ac_coded" action is schedulable
      isSchedulable_mb_dispatch_inter_ac_coded_go := '1';

      -- tests if "vld_start_intra" action is schedulable
      isSchedulable_vld_start_intra1_1 := btype_is_INTRA;
      isSchedulable_vld_start_intra0_1 := isSchedulable_vld_start_intra1_1;
      isSchedulable_vld_start_intra0_2 := isSchedulable_vld_start_intra0_1;
      isSchedulable_vld_start_intra_go := isSchedulable_vld_start_intra0_2;

      -- tests if "vld_start_inter_ac_coded" action is schedulable
      isSchedulable_vld_start_inter_ac_coded1_1 := ac_coded;
      isSchedulable_vld_start_inter_ac_coded0_1 := isSchedulable_vld_start_inter_ac_coded1_1;
      isSchedulable_vld_start_inter_ac_coded0_2 := isSchedulable_vld_start_inter_ac_coded0_1;
      isSchedulable_vld_start_inter_ac_coded_go := isSchedulable_vld_start_inter_ac_coded0_2;

      -- tests if "vld_start_inter_not_ac_coded" action is schedulable
      isSchedulable_vld_start_inter_not_ac_coded_go := '1';

      -- tests if "get_dc_bits_none" action is schedulable
      isSchedulable_get_dc_bits_none1_1 := vld_success(vld_codeword, '1');
      isSchedulable_get_dc_bits_none2_1 := vld_result(vld_codeword, '1');
      if (isSchedulable_get_dc_bits_none1_1 = '1' and isSchedulable_get_dc_bits_none2_1 = 0) then
        isSchedulable_get_dc_bits_none0_1 := '1';
      else
        isSchedulable_get_dc_bits_none0_1 := '0';
      end if;
      isSchedulable_get_dc_bits_none0_2 := isSchedulable_get_dc_bits_none0_1;
      isSchedulable_get_dc_bits_none_go := isSchedulable_get_dc_bits_none0_2;

      -- tests if "get_dc_bits_some" action is schedulable
      isSchedulable_get_dc_bits_some0_1 := vld_success(vld_codeword, '1');
      isSchedulable_get_dc_bits_some0_2 := isSchedulable_get_dc_bits_some0_1;
      isSchedulable_get_dc_bits_some_go := isSchedulable_get_dc_bits_some0_2;

      -- tests if "dc_bits_shift" action is schedulable
      isSchedulable_dc_bits_shift_go := '1';

      -- tests if "get_dc" action is schedulable
      isSchedulable_get_dc0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_get_dc0_2 := isSchedulable_get_dc0_1;
      isSchedulable_get_dc_go := isSchedulable_get_dc0_2;

      -- tests if "block_done" action is schedulable
      isSchedulable_block_done1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_block_done2_1 := b_last;
      if (isSchedulable_block_done1_1 = '1' and isSchedulable_block_done2_1 = '1') then
        isSchedulable_block_done0_1 := '1';
      else
        isSchedulable_block_done0_1 := '0';
      end if;
      isSchedulable_block_done0_2 := isSchedulable_block_done0_1;
      isSchedulable_block_done_go := isSchedulable_block_done0_2;

      -- tests if "dct_coeff" action is schedulable
      isSchedulable_dct_coeff0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_dct_coeff0_2 := isSchedulable_dct_coeff0_1;
      isSchedulable_dct_coeff_go := isSchedulable_dct_coeff0_2;

      -- tests if "vld_code" action is schedulable
      isSchedulable_vld_code1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vld_code1_1 = '1') then
        isSchedulable_vld_code2_1 := vld_success(vld_codeword, '1');
        isSchedulable_vld_code3_1 := vld_result(vld_codeword, '1');
        if (isSchedulable_vld_code2_1 = '1' and isSchedulable_vld_code3_1 /= 7167) then
          isSchedulable_vld_code0_1 := '1';
        else
          isSchedulable_vld_code0_1 := '0';
        end if;
        isSchedulable_vld_code0_2 := isSchedulable_vld_code0_1;
      else
        isSchedulable_vld_code0_3 := '0';
        isSchedulable_vld_code0_2 := isSchedulable_vld_code0_3;
      end if;
      isSchedulable_vld_code_go := isSchedulable_vld_code0_2;

      -- tests if "vld_level" action is schedulable
      isSchedulable_vld_level1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vld_level1_1 = '1') then
        isSchedulable_vld_level_level_offset_1 := isSchedulable_vld_level_bits;
        isSchedulable_vld_level2_1             := vld_success(vld_codeword, '1');
        if (isSchedulable_vld_level2_1 = '1' and isSchedulable_vld_level_level_offset_1 = '0') then
          isSchedulable_vld_level0_1 := '1';
        else
          isSchedulable_vld_level0_1 := '0';
        end if;
        isSchedulable_vld_level0_2 := isSchedulable_vld_level0_1;
      else
        isSchedulable_vld_level0_3 := '0';
        isSchedulable_vld_level0_2 := isSchedulable_vld_level0_3;
      end if;
      isSchedulable_vld_level_go := isSchedulable_vld_level0_2;

      -- tests if "vld_run_or_direct" action is schedulable
      isSchedulable_vld_run_or_direct1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vld_run_or_direct1_1 = '1') then
        isSchedulable_vld_run_or_direct0_1 := vld_success(vld_codeword, '1');
        isSchedulable_vld_run_or_direct0_2 := isSchedulable_vld_run_or_direct0_1;
      else
        isSchedulable_vld_run_or_direct0_3 := '0';
        isSchedulable_vld_run_or_direct0_2 := isSchedulable_vld_run_or_direct0_3;
      end if;
      isSchedulable_vld_run_or_direct_go := isSchedulable_vld_run_or_direct0_2;

      -- tests if "vld_run" action is schedulable
      isSchedulable_vld_run1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vld_run1_1 = '1') then
        isSchedulable_vld_run_run_offset_1 := isSchedulable_vld_run_bits;
        if (isSchedulable_vld_run_run_offset_1 = '0') then
          isSchedulable_vld_run0_1 := '1';
        else
          isSchedulable_vld_run0_1 := '0';
        end if;
        isSchedulable_vld_run0_2 := isSchedulable_vld_run0_1;
      else
        isSchedulable_vld_run0_3 := '0';
        isSchedulable_vld_run0_2 := isSchedulable_vld_run0_3;
      end if;
      isSchedulable_vld_run_go := isSchedulable_vld_run0_2;

      -- tests if "vld_direct_read" action is schedulable
      isSchedulable_vld_direct_read1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vld_direct_read1_1 = '1') then
        isSchedulable_vld_direct_read0_1 := '1';
        isSchedulable_vld_direct_read0_2 := isSchedulable_vld_direct_read0_1;
      else
        isSchedulable_vld_direct_read0_3 := '0';
        isSchedulable_vld_direct_read0_2 := isSchedulable_vld_direct_read0_3;
      end if;
      isSchedulable_vld_direct_read_go := isSchedulable_vld_direct_read0_2;

      -- tests if "vld_direct" action is schedulable
      isSchedulable_vld_direct0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_vld_direct0_2 := isSchedulable_vld_direct0_1;
      isSchedulable_vld_direct_go := isSchedulable_vld_direct0_2;

      -- tests if "do_level_lookup" action is schedulable
      isSchedulable_do_level_lookup0_1 := vld_success(vld_codeword, '1');
      isSchedulable_do_level_lookup0_2 := isSchedulable_do_level_lookup0_1;
      isSchedulable_do_level_lookup_go := isSchedulable_do_level_lookup0_2;

      -- tests if "vld_level_lookup" action is schedulable
      isSchedulable_vld_level_lookup1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vld_level_lookup1_1 = '1') then
        isSchedulable_vld_level_lookup0_1 := '1';
        isSchedulable_vld_level_lookup0_2 := isSchedulable_vld_level_lookup0_1;
      else
        isSchedulable_vld_level_lookup0_3 := '0';
        isSchedulable_vld_level_lookup0_2 := isSchedulable_vld_level_lookup0_3;
      end if;
      isSchedulable_vld_level_lookup_go := isSchedulable_vld_level_lookup0_2;

      -- tests if "do_run_lookup" action is schedulable
      isSchedulable_do_run_lookup0_1 := vld_success(vld_codeword, '1');
      isSchedulable_do_run_lookup0_2 := isSchedulable_do_run_lookup0_1;
      isSchedulable_do_run_lookup_go := isSchedulable_do_run_lookup0_2;

      -- tests if "vld_run_lookup" action is schedulable
      isSchedulable_vld_run_lookup1_1 := not parseheaders_bits_empty;
      if (isSchedulable_vld_run_lookup1_1 = '1') then
        isSchedulable_vld_run_lookup0_1 := '1';
        isSchedulable_vld_run_lookup0_2 := isSchedulable_vld_run_lookup0_1;
      else
        isSchedulable_vld_run_lookup0_3 := '0';
        isSchedulable_vld_run_lookup0_2 := isSchedulable_vld_run_lookup0_3;
      end if;
      isSchedulable_vld_run_lookup_go := isSchedulable_vld_run_lookup0_2;

      -- tests if "mvcode_done" action is schedulable
      isSchedulable_mvcode_done1_1 := mvcomp;
      isSchedulable_mvcode_done2_1 := mvcomp;
      isSchedulable_mvcode_done3_1 := fourmvflag;
      if ((isSchedulable_mvcode_done1_1 = 4) or (isSchedulable_mvcode_done2_1 = 1 and isSchedulable_mvcode_done3_1 = '0')) then
        isSchedulable_mvcode_done0_1 := '1';
      else
        isSchedulable_mvcode_done0_1 := '0';
      end if;
      isSchedulable_mvcode_done0_2 := isSchedulable_mvcode_done0_1;
      isSchedulable_mvcode_done_go := isSchedulable_mvcode_done0_2;

      -- tests if "mvcode" action is schedulable
      isSchedulable_mvcode_go := '1';

      -- tests if "mag_x" action is schedulable
      isSchedulable_mag_x0_1 := vld_success(vld_codeword, '1');
      isSchedulable_mag_x0_2 := isSchedulable_mag_x0_1;
      isSchedulable_mag_x_go := isSchedulable_mag_x0_2;

      -- tests if "get_residual_x" action is schedulable
      isSchedulable_get_residual_x0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_get_residual_x0_2 := isSchedulable_get_residual_x0_1;
      isSchedulable_get_residual_x_go := isSchedulable_get_residual_x0_2;

      -- tests if "mag_y" action is schedulable
      isSchedulable_mag_y0_1 := vld_success(vld_codeword, '1');
      isSchedulable_mag_y0_2 := isSchedulable_mag_y0_1;
      isSchedulable_mag_y_go := isSchedulable_mag_y0_2;

      -- tests if "get_residual_y" action is schedulable
      isSchedulable_get_residual_y0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_get_residual_y0_2 := isSchedulable_get_residual_y0_1;
      isSchedulable_get_residual_y_go := isSchedulable_get_residual_y0_2;

      -- tests if "untagged02" action is schedulable
      isSchedulable_untagged021_1 := not parseheaders_bits_empty;
      if (isSchedulable_untagged021_1 = '1') then
        isSchedulable_untagged022_1 := vld_codeword;
        if (bitand(isSchedulable_untagged022_1, 20, 3, 3) = 2) then
          isSchedulable_untagged020_1 := '1';
        else
          isSchedulable_untagged020_1 := '0';
        end if;
        isSchedulable_untagged020_2 := isSchedulable_untagged020_1;
      else
        isSchedulable_untagged020_3 := '0';
        isSchedulable_untagged020_2 := isSchedulable_untagged020_3;
      end if;
      isSchedulable_untagged02_go := isSchedulable_untagged020_2;

      -- tests if "do_vld_failure" action is schedulable
      isSchedulable_do_vld_failure0_1 := vld_failure(vld_codeword, '1');
      isSchedulable_do_vld_failure0_2 := isSchedulable_do_vld_failure0_1;
      isSchedulable_do_vld_failure_go := isSchedulable_do_vld_failure0_2;

      -- tests if "generic_done" action is schedulable
      isSchedulable_generic_done0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_generic_done0_2 := isSchedulable_generic_done0_1;
      isSchedulable_generic_done_go := isSchedulable_generic_done0_2;

      -- tests if "generic_done_with_bitread" action is schedulable
      isSchedulable_generic_done_with_bitread1_1 := not parseheaders_bits_empty;
      if (isSchedulable_generic_done_with_bitread1_1 = '1') then
        isSchedulable_generic_done_with_bitread0_1 := done_reading_bits(bits_to_read_count, '1');
        isSchedulable_generic_done_with_bitread0_2 := isSchedulable_generic_done_with_bitread0_1;
      else
        isSchedulable_generic_done_with_bitread0_3 := '0';
        isSchedulable_generic_done_with_bitread0_2 := isSchedulable_generic_done_with_bitread0_3;
      end if;
      isSchedulable_generic_done_with_bitread_go := isSchedulable_generic_done_with_bitread0_2;

      -- tests if "test_zero_byte" action is schedulable
      isSchedulable_test_zero_byte1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_test_zero_byte2_1 := read_result(read_result_in_progress, '1');
      if (isSchedulable_test_zero_byte1_1 = '1' and bitand(isSchedulable_test_zero_byte2_1, 32, 255, 9) = 0) then
        isSchedulable_test_zero_byte0_1 := '1';
      else
        isSchedulable_test_zero_byte0_1 := '0';
      end if;
      isSchedulable_test_zero_byte0_2 := isSchedulable_test_zero_byte0_1;
      isSchedulable_test_zero_byte_go := isSchedulable_test_zero_byte0_2;

      -- tests if "test_vo_byte" action is schedulable
      isSchedulable_test_vo_byte1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_test_vo_byte2_1 := read_result(read_result_in_progress, '1');
      if (isSchedulable_test_vo_byte1_1 = '1' and bitand(isSchedulable_test_vo_byte2_1, 32, 254, 9) = 0) then
        isSchedulable_test_vo_byte0_1 := '1';
      else
        isSchedulable_test_vo_byte0_1 := '0';
      end if;
      isSchedulable_test_vo_byte0_2 := isSchedulable_test_vo_byte0_1;
      isSchedulable_test_vo_byte_go := isSchedulable_test_vo_byte0_2;

      -- tests if "test_vol_byte" action is schedulable
      isSchedulable_test_vol_byte1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_test_vol_byte2_1 := read_result(read_result_in_progress, '1');
      isSchedulable_test_vol_byte3_1 := VOL_START_CODE;
      if (isSchedulable_test_vol_byte1_1 = '1' and isSchedulable_test_vol_byte2_1 = isSchedulable_test_vol_byte3_1) then
        isSchedulable_test_vol_byte0_1 := '1';
      else
        isSchedulable_test_vol_byte0_1 := '0';
      end if;
      isSchedulable_test_vol_byte0_2 := isSchedulable_test_vol_byte0_1;
      isSchedulable_test_vol_byte_go := isSchedulable_test_vol_byte0_2;

      -- tests if "test_vop_byte" action is schedulable
      isSchedulable_test_vop_byte1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_test_vop_byte2_1 := read_result(read_result_in_progress, '1');
      isSchedulable_test_vop_byte3_1 := VOP_START_CODE;
      if (isSchedulable_test_vop_byte1_1 = '1' and isSchedulable_test_vop_byte2_1 = isSchedulable_test_vop_byte3_1) then
        isSchedulable_test_vop_byte0_1 := '1';
      else
        isSchedulable_test_vop_byte0_1 := '0';
      end if;
      isSchedulable_test_vop_byte0_2 := isSchedulable_test_vop_byte0_1;
      isSchedulable_test_vop_byte_go := isSchedulable_test_vop_byte0_2;

      -- tests if "test_one_byte" action is schedulable
      isSchedulable_test_one_byte1_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_test_one_byte2_1 := read_result(read_result_in_progress, '1');
      if (isSchedulable_test_one_byte1_1 = '1' and bitand(isSchedulable_test_one_byte2_1, 32, 255, 9) = 1) then
        isSchedulable_test_one_byte0_1 := '1';
      else
        isSchedulable_test_one_byte0_1 := '0';
      end if;
      isSchedulable_test_one_byte0_2 := isSchedulable_test_one_byte0_1;
      isSchedulable_test_one_byte_go := isSchedulable_test_one_byte0_2;

      -- tests if "request_byte" action is schedulable
      isSchedulable_request_byte0_1 := done_reading_bits(bits_to_read_count, '1');
      isSchedulable_request_byte0_2 := isSchedulable_request_byte0_1;
      isSchedulable_request_byte_go := isSchedulable_request_byte0_2;

      -- Actions
      if (isSchedulable_untagged01_go = '1') then
        -- body of "untagged01" action
        untagged01_bits        := parseheaders_bits_data;
        parseheaders_bits_read <= '1';
        untagged01_b_1         := untagged01_bits;
        untagged010_1          := read_result_in_progress;
        if (untagged01_b_1 = '1') then
          untagged011_1 := 1;
          untagged011_2 := untagged011_1;
        else
          untagged011_3 := 0;
          untagged011_2 := untagged011_3;
        end if;
        read_result_in_progress <= bitor(shift_left(untagged010_1, 32, 1, 2), 32, untagged011_2, 2);
        untagged012_1           := bits_to_read_count;
        bits_to_read_count      <= untagged012_1 - 1;
        untagged013_1           := bit_count;
        bit_count               <= untagged013_1 + 1;

      elsif (isSchedulable_untagged02_go = '1') then
        -- body of "untagged02" action
        untagged02_bits        := parseheaders_bits_data;
        parseheaders_bits_read <= '1';
        untagged02_b_1         := untagged02_bits;
        untagged020_1          := vld_index;
        if (untagged02_b_1 = '1') then
          untagged021_1 := 1;
          untagged021_2 := untagged021_1;
        else
          untagged021_3 := 0;
          untagged021_2 := untagged021_3;
        end if;
        untagged022_1 := vld_table(untagged020_1 + untagged021_2);
        vld_codeword  <= untagged022_1;
        untagged023_1 := vld_codeword;
        vld_index     <= shift_right(untagged023_1, 20, 2, 3);
        untagged024_1 := bit_count;
        bit_count     <= untagged024_1 + 1;


      else
        case FSM is
          when s_block =>
            -- block_state_scheduler
            if (isSchedulable_mb_dispatch_done_go = '1') then
              -- body of "mb_dispatch_done" action
              next_mbxy0_1 := mbx;
              mbx          <= next_mbxy0_1 + 1;
              next_mbxy1_1 := mbx;
              next_mbxy2_1 := vol_width;
              if (next_mbxy1_1 = next_mbxy2_1) then
                mbx          <= 0;
                next_mbxy3_1 := mby;
                mby          <= next_mbxy3_1 + 1;
              end if;
              FSM <= s_mb;
            elsif(isSchedulable_mb_dispatch_intra_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mb_dispatch_intra" action
                mb_dispatch_intra1_1 := cbp;
                mb_dispatch_intra2_1 := comp;
                if (bitand(mb_dispatch_intra1_1, 7, shift_left(1, 2, 5 - mb_dispatch_intra2_1, 4), 32) /= 0) then
                  mb_dispatch_intra_bool_expr_1 := '1';
                else
                  mb_dispatch_intra_bool_expr_1 := '0';
                end if;
                ac_coded             <= mb_dispatch_intra_bool_expr_1;
                mb_dispatch_intra3_1 := ac_coded;
                if (mb_dispatch_intra3_1 = '1') then
                  mb_dispatch_intra4_1 := 2;
                  mb_dispatch_intra4_2 := mb_dispatch_intra4_1;
                else
                  mb_dispatch_intra4_3 := 0;
                  mb_dispatch_intra4_2 := mb_dispatch_intra4_3;
                end if;
                mb_dispatch_intra_cmd0_2 := bitor(1024, 12, mb_dispatch_intra4_2, 32);
                mb_dispatch_intra6_1     := acpredflag;
                if (mb_dispatch_intra6_1 = '1') then
                  mb_dispatch_intra7_1 := 1;
                  mb_dispatch_intra7_2 := mb_dispatch_intra7_1;
                else
                  mb_dispatch_intra7_3 := 0;
                  mb_dispatch_intra7_2 := mb_dispatch_intra7_3;
                end if;
                mb_dispatch_intra_cmd0_3 := bitor(mb_dispatch_intra_cmd0_2, 12, mb_dispatch_intra7_2, 32);
                mb_dispatch_intra_BTYPE  := mb_dispatch_intra_cmd0_3;
                parseheaders_BTYPE_data  <= std_logic_vector(to_signed(mb_dispatch_intra_BTYPE, 12));
                parseheaders_BTYPE_write <= '1';
                FSM                      <= s_texture;
              end if;
            elsif(isSchedulable_mb_dispatch_inter_no_ac_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mb_dispatch_inter_no_ac" action
                ac_coded                   <= '0';
                mb_dispatch_inter_no_ac0_1 := comp;
                comp                       <= mb_dispatch_inter_no_ac0_1 + 1;
                mb_dispatch_inter_no_ac3_1 := fourmvflag;
                if (mb_dispatch_inter_no_ac3_1 = '1') then
                  mb_dispatch_inter_no_ac4_1 := 4;
                  mb_dispatch_inter_no_ac4_2 := mb_dispatch_inter_no_ac4_1;
                else
                  mb_dispatch_inter_no_ac4_3 := 0;
                  mb_dispatch_inter_no_ac4_2 := mb_dispatch_inter_no_ac4_3;
                end if;
                mb_dispatch_inter_no_ac_BTYPE := bitor(512, 11, bitor(8, 5, mb_dispatch_inter_no_ac4_2, 32), 32);
                parseheaders_BTYPE_data       <= std_logic_vector(to_signed(mb_dispatch_inter_no_ac_BTYPE, 12));
                parseheaders_BTYPE_write      <= '1';
                FSM                           <= s_block;
              end if;
            elsif(isSchedulable_mb_dispatch_inter_ac_coded_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mb_dispatch_inter_ac_coded" action
                ac_coded                      <= '1';
                mb_dispatch_inter_ac_coded3_1 := fourmvflag;
                if (mb_dispatch_inter_ac_coded3_1 = '1') then
                  mb_dispatch_inter_ac_coded4_1 := 4;
                  mb_dispatch_inter_ac_coded4_2 := mb_dispatch_inter_ac_coded4_1;
                else
                  mb_dispatch_inter_ac_coded4_3 := 0;
                  mb_dispatch_inter_ac_coded4_2 := mb_dispatch_inter_ac_coded4_3;
                end if;
                mb_dispatch_inter_ac_coded_BTYPE := bitor(514, 11, bitor(8, 5, mb_dispatch_inter_ac_coded4_2, 32), 32);
                parseheaders_BTYPE_data          <= std_logic_vector(to_signed(mb_dispatch_inter_ac_coded_BTYPE, 12));
                parseheaders_BTYPE_write         <= '1';
                FSM                              <= s_texture;
              end if;
            end if;

          when s_final_cbpy =>
            -- final_cbpy_state_scheduler
            if (isSchedulable_final_cbpy_inter_go = '1') then
              -- body of "final_cbpy_inter" action
              final_cbpy_inter0_1      := vld_result(vld_codeword, '1');
              final_cbpy_inter_cbpy0_1 := 15 - final_cbpy_inter0_1;
              comp                     <= 0;
              mvcomp                   <= 0;
              final_cbpy_inter1_1      := cbpc;
              cbp                      <= bitor(shift_left(final_cbpy_inter_cbpy0_1, 32, 2, 3), 32, final_cbpy_inter1_1, 7);
              FSM                      <= s_mv;
            elsif(isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            elsif(isSchedulable_final_cbpy_intra_go = '1') then
              -- body of "final_cbpy_intra" action
              final_cbpy_intra_cbpy0_1 := vld_result(vld_codeword, '1');
              comp                     <= 0;
              final_cbpy_intra0_1      := cbpc;
              cbp                      <= bitor(shift_left(final_cbpy_intra_cbpy0_1, 32, 2, 3), 32, final_cbpy_intra0_1, 7);
              FSM                      <= s_block;
            end if;

          when s_get_dc =>
            -- get_dc_state_scheduler
            if (isSchedulable_dc_bits_shift_go = '1') then
              -- body of "dc_bits_shift" action
              dc_bits_shift0_1       := dc_bits;
              dc_bits_shift_count0_1 := dc_bits_shift0_1;
              dc_bits_shift_shift0_1 := 1;
              dc_bits_shift_shift0_3 := dc_bits_shift_shift0_1;
              dc_bits_shift_count0_3 := dc_bits_shift_count0_1;
              while (dc_bits_shift_count0_3 > 1) loop
                dc_bits_shift_shift0_2 := shift_left(dc_bits_shift_shift0_3, 14, 1, 2);
                dc_bits_shift_count0_2 := dc_bits_shift_count0_3 - 1;
                dc_bits_shift_shift0_3 := dc_bits_shift_shift0_2;
                dc_bits_shift_count0_3 := dc_bits_shift_count0_2;

              end loop; msb_result <= dc_bits_shift_shift0_3;
              FSM                  <= s_get_dc_a;
            end if;

          when s_get_dc_a =>
            -- get_dc_a_state_scheduler
            if (isSchedulable_get_dc_go = '1') then
              if (parseheaders_RUN_full = '0' and parseheaders_VALUE_full = '0' and parseheaders_LAST_full = '0') then
                -- body of "get_dc" action
                get_dc_v0_1 := read_result(read_result_in_progress, '1');
                get_dc0_1   := msb_result;
                if (bitand(get_dc_v0_1, 14, get_dc0_1, 14) = 0) then
                  get_dc1_1   := msb_result;
                  get_dc_v0_2 := get_dc_v0_1 + 1 - shift_left(get_dc1_1, 14, 1, 2);
                  get_dc_v0_3 := get_dc_v0_2;
                else
                  get_dc_v0_3 := get_dc_v0_1;
                end if;
                get_dc2_1 := dc_bits;
                if (get_dc2_1 > 8) then
                  get_dc3_1 := 1;
                  get_dc3_2 := get_dc3_1;
                else
                  get_dc3_3 := 0;
                  get_dc3_2 := get_dc3_3;
                end if;
                bits_to_read_count      <= get_dc3_2 - 1;
                read_result_in_progress <= 0;
                get_dc5_1               := ac_coded;
                if (get_dc5_1 = '0') then
                  get_dc_bool_expr_1 := '1';
                else
                  get_dc_bool_expr_1 := '0';
                end if;
                b_last                   <= get_dc_bool_expr_1;
                get_dc_RUN               := 0;
                parseheaders_RUN_data    <= std_logic_vector(to_signed(get_dc_RUN, 8));
                parseheaders_RUN_write   <= '1';
                get_dc_VALUE             := get_dc_v0_3;
                parseheaders_VALUE_data  <= std_logic_vector(to_signed(get_dc_VALUE, 13));
                parseheaders_VALUE_write <= '1';
                get_dc6_1                := ac_coded;
                if (get_dc6_1 = '0') then
                  get_dc_bool_expr_2 := '1';
                else
                  get_dc_bool_expr_2 := '0';
                end if;
                get_dc_LAST             := get_dc_bool_expr_2;
                parseheaders_LAST_data  <= get_dc_LAST;
                parseheaders_LAST_write <= '1';
                FSM                     <= s_texac;
              end if;
            end if;

          when s_get_dc_bits =>
            -- get_dc_bits_state_scheduler
            if (isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            elsif(isSchedulable_get_dc_bits_none_go = '1') then
              if (parseheaders_RUN_full = '0' and parseheaders_VALUE_full = '0' and parseheaders_LAST_full = '0') then
                -- body of "get_dc_bits_none" action
                get_dc_bits_none0_1 := ac_coded;
                if (get_dc_bits_none0_1 = '0') then
                  get_dc_bits_none_bool_expr_1 := '1';
                else
                  get_dc_bits_none_bool_expr_1 := '0';
                end if;
                b_last                   <= get_dc_bits_none_bool_expr_1;
                get_dc_bits_none_RUN     := 0;
                parseheaders_RUN_data    <= std_logic_vector(to_signed(get_dc_bits_none_RUN, 8));
                parseheaders_RUN_write   <= '1';
                get_dc_bits_none_VALUE   := 0;
                parseheaders_VALUE_data  <= std_logic_vector(to_signed(get_dc_bits_none_VALUE, 13));
                parseheaders_VALUE_write <= '1';
                get_dc_bits_none1_1      := ac_coded;
                if (get_dc_bits_none1_1 = '0') then
                  get_dc_bits_none_bool_expr_2 := '1';
                else
                  get_dc_bits_none_bool_expr_2 := '0';
                end if;
                get_dc_bits_none_LAST   := get_dc_bits_none_bool_expr_2;
                parseheaders_LAST_data  <= get_dc_bits_none_LAST;
                parseheaders_LAST_write <= '1';
                FSM                     <= s_texac;
              end if;
            elsif(isSchedulable_get_dc_bits_some_go = '1') then
              -- body of "get_dc_bits_some" action
              get_dc_bits_some0_1     := vld_result(vld_codeword, '1');
              dc_bits                 <= get_dc_bits_some0_1;
              get_dc_bits_some1_1     := dc_bits;
              bits_to_read_count      <= get_dc_bits_some1_1 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_get_dc;
            end if;

          when s_get_mbtype =>
            -- get_mbtype_state_scheduler
            if (isSchedulable_get_mbtype_noac_go = '1') then
              -- body of "get_mbtype_noac" action
              get_mbtype_noac_mcbpc0_1 := vld_result(vld_codeword, '1');
              get_mbtype_noac_type0_1  := bitand(get_mbtype_noac_mcbpc0_1, 32, 7, 4);
              if (get_mbtype_noac_type0_1 >= 3) then
                get_mbtype_noac_bool_expr_1 := '1';
              else
                get_mbtype_noac_bool_expr_1 := '0';
              end if;
              btype_is_INTRA <= get_mbtype_noac_bool_expr_1;
              if (get_mbtype_noac_type0_1 = 2) then
                get_mbtype_noac_bool_expr_2 := '1';
              else
                get_mbtype_noac_bool_expr_2 := '0';
              end if;
              fourmvflag   <= get_mbtype_noac_bool_expr_2;
              cbpc         <= bitand(shift_right(get_mbtype_noac_mcbpc0_1, 32, 4, 4), 32, 3, 3);
              acpredflag   <= '0';
              vld_index    <= 58;
              vld_codeword <= 2;
              FSM          <= s_final_cbpy;
            elsif(isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            elsif(isSchedulable_get_mbtype_ac_go = '1') then
              -- body of "get_mbtype_ac" action
              get_mbtype_ac_bits     := parseheaders_bits_data;
              parseheaders_bits_read <= '1';
              get_mbtype_ac_b_1      := get_mbtype_ac_bits;
              get_mbtype_ac_mcbpc0_1 := vld_result(vld_codeword, '1');
              btype_is_INTRA         <= '1';
              cbpc                   <= bitand(shift_right(get_mbtype_ac_mcbpc0_1, 32, 4, 4), 32, 3, 3);
              acpredflag             <= get_mbtype_ac_b_1;
              get_mbtype_ac0_1       := bit_count;
              bit_count              <= get_mbtype_ac0_1 + 1;
              vld_index              <= 58;
              vld_codeword           <= 2;
              FSM                    <= s_final_cbpy;
            end if;

          when s_get_residual_x =>
            -- get_residual_x_state_scheduler
            if (isSchedulable_get_residual_x_go = '1') then
              if (parseheaders_MV_full = '0') then
                -- body of "get_residual_x" action
                get_residual_x0_1     := read_result(read_result_in_progress, '1');
                get_residual_x_MV     := get_residual_x0_1;
                parseheaders_MV_data  <= std_logic_vector(to_signed(get_residual_x_MV, 9));
                parseheaders_MV_write <= '1';
                FSM                   <= s_mv_y;
              end if;
            end if;

          when s_get_residual_y =>
            -- get_residual_y_state_scheduler
            if (isSchedulable_get_residual_y_go = '1') then
              if (parseheaders_MV_full = '0') then
                -- body of "get_residual_y" action
                get_residual_y0_1     := mvcomp;
                mvcomp                <= get_residual_y0_1 + 1;
                get_residual_y1_1     := read_result(read_result_in_progress, '1');
                get_residual_y_MV     := get_residual_y1_1;
                parseheaders_MV_data  <= std_logic_vector(to_signed(get_residual_y_MV, 9));
                parseheaders_MV_write <= '1';
                FSM                   <= s_mv;
              end if;
            end if;

          when s_mag_x =>
            -- mag_x_state_scheduler
            if (isSchedulable_mag_x_go = '1') then
              if (parseheaders_MV_full = '0') then
                -- body of "mag_x" action
                mag_x_mvval0_1 := vld_result(vld_codeword, '1');
                mag_x0_1       := fcode;
                if ((mag_x0_1  <= 1) or (mag_x_mvval0_1 = 0)) then
                  mag_x1_1 := 0;
                  mag_x1_2 := mag_x1_1;
                else
                  mag_x2_1 := fcode;
                  mag_x1_3 := mag_x2_1 - 1;
                  mag_x1_2 := mag_x1_3;
                end if;
                bits_to_read_count      <= mag_x1_2 - 1;
                read_result_in_progress <= 0;
                mag_x_MV                := mag_x_mvval0_1;
                parseheaders_MV_data    <= std_logic_vector(to_signed(mag_x_MV, 9));
                parseheaders_MV_write   <= '1';
                FSM                     <= s_get_residual_x;
              end if;
            elsif(isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            end if;

          when s_mag_y =>
            -- mag_y_state_scheduler
            if (isSchedulable_mag_y_go = '1') then
              if (parseheaders_MV_full = '0') then
                -- body of "mag_y" action
                mag_y_mvval0_1 := vld_result(vld_codeword, '1');
                mag_y0_1       := fcode;
                if ((mag_y0_1  <= 1) or (mag_y_mvval0_1 = 0)) then
                  mag_y1_1 := 0;
                  mag_y1_2 := mag_y1_1;
                else
                  mag_y2_1 := fcode;
                  mag_y1_3 := mag_y2_1 - 1;
                  mag_y1_2 := mag_y1_3;
                end if;
                bits_to_read_count      <= mag_y1_2 - 1;
                read_result_in_progress <= 0;
                mag_y_MV                := mag_y_mvval0_1;
                parseheaders_MV_data    <= std_logic_vector(to_signed(mag_y_MV, 9));
                parseheaders_MV_write   <= '1';
                FSM                     <= s_get_residual_y;
              end if;
            elsif(isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            end if;

          when s_mb =>
            -- mb_state_scheduler
            if (isSchedulable_mb_done_go = '1') then
              -- body of "mb_done" action
              FSM <= s_stuck;
            elsif(isSchedulable_get_mcbpc_ivop_go = '1') then
              -- body of "get_mcbpc_ivop" action
              vld_index    <= 0;
              vld_codeword <= 2;
              FSM          <= s_get_mbtype;
            elsif(isSchedulable_get_mcbpc_pvop_go = '1') then
              -- body of "get_mcbpc_pvop" action
              get_mcbpc_pvop_bits    := parseheaders_bits_data;
              parseheaders_bits_read <= '1';
              vld_index              <= 16;
              vld_codeword           <= 2;
              get_mcbpc_pvop1_1      := bit_count;
              bit_count              <= get_mcbpc_pvop1_1 + 1;
              FSM                    <= s_get_mbtype;
            elsif(isSchedulable_mcbpc_pvop_uncoded_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mcbpc_pvop_uncoded" action
                mcbpc_pvop_uncoded_bits := parseheaders_bits_data;
                parseheaders_bits_read  <= '1';
                next_mbxy0_1            := mbx;
                mbx                     <= next_mbxy0_1 + 1;
                next_mbxy1_1            := mbx;
                next_mbxy2_1            := vol_width;
                if (next_mbxy1_1 = next_mbxy2_1) then
                  mbx          <= 0;
                  next_mbxy3_1 := mby;
                  mby          <= next_mbxy3_1 + 1;
                end if;
                mcbpc_pvop_uncoded0_1    := bit_count;
                bit_count                <= mcbpc_pvop_uncoded0_1 + 1;
                mcbpc_pvop_uncoded_BTYPE := 512;
                parseheaders_BTYPE_data  <= std_logic_vector(to_signed(mcbpc_pvop_uncoded_BTYPE, 12));
                parseheaders_BTYPE_write <= '1';
                FSM                      <= s_pvop_uncoded1;
              end if;
            end if;

          when s_mv =>
            -- mv_state_scheduler
            if (isSchedulable_mvcode_done_go = '1') then
              -- body of "mvcode_done" action
              FSM <= s_block;
            elsif(isSchedulable_mvcode_go = '1') then
              -- body of "mvcode" action
              vld_index    <= 616;
              vld_codeword <= 2;
              FSM          <= s_mag_x;
            end if;

          when s_mv_y =>
            -- mv_y_state_scheduler
            if (isSchedulable_mvcode_go = '1') then
              -- body of "mvcode" action
              vld_index    <= 616;
              vld_codeword <= 2;
              FSM          <= s_mag_y;
            end if;

          when s_pvop_uncoded1 =>
            -- pvop_uncoded1_state_scheduler
            if (isSchedulable_mcbpc_pvop_uncoded1_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mcbpc_pvop_uncoded1" action
                mcbpc_pvop_uncoded1_BTYPE := 512;
                parseheaders_BTYPE_data   <= std_logic_vector(to_signed(mcbpc_pvop_uncoded1_BTYPE, 12));
                parseheaders_BTYPE_write  <= '1';
                FSM                       <= s_pvop_uncoded2;
              end if;
            end if;

          when s_pvop_uncoded2 =>
            -- pvop_uncoded2_state_scheduler
            if (isSchedulable_mcbpc_pvop_uncoded1_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mcbpc_pvop_uncoded1" action
                mcbpc_pvop_uncoded1_BTYPE := 512;
                parseheaders_BTYPE_data   <= std_logic_vector(to_signed(mcbpc_pvop_uncoded1_BTYPE, 12));
                parseheaders_BTYPE_write  <= '1';
                FSM                       <= s_pvop_uncoded3;
              end if;
            end if;

          when s_pvop_uncoded3 =>
            -- pvop_uncoded3_state_scheduler
            if (isSchedulable_mcbpc_pvop_uncoded1_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mcbpc_pvop_uncoded1" action
                mcbpc_pvop_uncoded1_BTYPE := 512;
                parseheaders_BTYPE_data   <= std_logic_vector(to_signed(mcbpc_pvop_uncoded1_BTYPE, 12));
                parseheaders_BTYPE_write  <= '1';
                FSM                       <= s_pvop_uncoded4;
              end if;
            end if;

          when s_pvop_uncoded4 =>
            -- pvop_uncoded4_state_scheduler
            if (isSchedulable_mcbpc_pvop_uncoded1_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mcbpc_pvop_uncoded1" action
                mcbpc_pvop_uncoded1_BTYPE := 512;
                parseheaders_BTYPE_data   <= std_logic_vector(to_signed(mcbpc_pvop_uncoded1_BTYPE, 12));
                parseheaders_BTYPE_write  <= '1';
                FSM                       <= s_pvop_uncoded5;
              end if;
            end if;

          when s_pvop_uncoded5 =>
            -- pvop_uncoded5_state_scheduler
            if (isSchedulable_mcbpc_pvop_uncoded1_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "mcbpc_pvop_uncoded1" action
                mcbpc_pvop_uncoded1_BTYPE := 512;
                parseheaders_BTYPE_data   <= std_logic_vector(to_signed(mcbpc_pvop_uncoded1_BTYPE, 12));
                parseheaders_BTYPE_write  <= '1';
                FSM                       <= s_mb;
              end if;
            end if;

          when s_send_new_vop_height =>
            -- send_new_vop_height_state_scheduler
            if (isSchedulable_send_new_vop_height_go = '1') then
              if (parseheaders_BTYPE_full = '0' and parseheaders_HEIGHT_full = '0') then
                -- body of "send_new_vop_height" action
                send_new_vop_height0_1     := vol_height;
                send_new_vop_height_BTYPE  := send_new_vop_height0_1;
                parseheaders_BTYPE_data    <= std_logic_vector(to_signed(send_new_vop_height_BTYPE, 12));
                parseheaders_BTYPE_write   <= '1';
                send_new_vop_height1_1     := vol_height;
                send_new_vop_height_HEIGHT := send_new_vop_height1_1;
                parseheaders_HEIGHT_data   <= std_logic_vector(to_signed(send_new_vop_height_HEIGHT, 16));
                parseheaders_HEIGHT_write  <= '1';
                FSM                        <= s_mb;
              end if;
            end if;

          when s_send_new_vop_info =>
            -- send_new_vop_info_state_scheduler
            if (isSchedulable_send_new_vop_cmd_go = '1') then
              if (parseheaders_BTYPE_full = '0') then
                -- body of "send_new_vop_cmd" action
                send_new_vop_cmd_round0_1 := '0';
                send_new_vop_cmd1_1       := prediction_is_IVOP;
                if (send_new_vop_cmd1_1 = '1') then
                  send_new_vop_cmd2_1 := 1024;
                  send_new_vop_cmd2_2 := send_new_vop_cmd2_1;
                else
                  send_new_vop_cmd2_3 := 512;
                  send_new_vop_cmd2_2 := send_new_vop_cmd2_3;
                end if;
                send_new_vop_cmd_cmd0_1 := bitor(2048, 13, send_new_vop_cmd2_2, 32);
                send_new_vop_cmd5_1     := prediction_is_IVOP;
                if (send_new_vop_cmd5_1 = '0') then
                  send_new_vop_cmd6_1 := read_result(read_result_in_progress, '1');
                  if (bitand(shift_right(send_new_vop_cmd6_1, 32, 11, 5), 32, 1, 2) = 1) then
                    send_new_vop_cmd_round0_2 := '1';
                  else
                    send_new_vop_cmd_round0_2 := '0';
                  end if;
                  send_new_vop_cmd10_1          := read_result(read_result_in_progress, '1');
                  send_new_vop_cmd_vop_quant0_1 := bitand(shift_right(send_new_vop_cmd10_1, 32, 3, 3), 32, 31, 6);
                  send_new_vop_cmd13_1          := read_result(read_result_in_progress, '1');
                  fcode                         <= bitand(send_new_vop_cmd13_1, 32, 7, 4);
                  send_new_vop_cmd_round0_3     := send_new_vop_cmd_round0_2;
                  send_new_vop_cmd_vop_quant0_2 := send_new_vop_cmd_vop_quant0_1;
                else
                  send_new_vop_cmd15_1          := read_result(read_result_in_progress, '1');
                  send_new_vop_cmd_vop_quant0_3 := bitand(send_new_vop_cmd15_1, 32, 31, 6);
                  fcode                         <= 0;
                  send_new_vop_cmd_round0_3     := send_new_vop_cmd_round0_1;
                  send_new_vop_cmd_vop_quant0_2 := send_new_vop_cmd_vop_quant0_3;
                end if;
                send_new_vop_cmd_cmd0_2 := bitor(send_new_vop_cmd_cmd0_1, 12, bitand(send_new_vop_cmd_vop_quant0_2, 6, 31, 6), 32);
                if (send_new_vop_cmd_round0_3 = '1') then
                  send_new_vop_cmd18_1 := 32;
                  send_new_vop_cmd18_2 := send_new_vop_cmd18_1;
                else
                  send_new_vop_cmd18_3 := 0;
                  send_new_vop_cmd18_2 := send_new_vop_cmd18_3;
                end if;
                send_new_vop_cmd_cmd0_3  := bitor(send_new_vop_cmd_cmd0_2, 12, send_new_vop_cmd18_2, 32);
                send_new_vop_cmd20_1     := fcode;
                send_new_vop_cmd_cmd0_4  := bitor(send_new_vop_cmd_cmd0_3, 12, bitand(shift_left(send_new_vop_cmd20_1, 4, 6, 4), 32, 448, 10), 32);
                send_new_vop_cmd_BTYPE   := send_new_vop_cmd_cmd0_4;
                parseheaders_BTYPE_data  <= std_logic_vector(to_signed(send_new_vop_cmd_BTYPE, 12));
                parseheaders_BTYPE_write <= '1';
                FSM                      <= s_send_new_vop_width;
              end if;
            end if;

          when s_send_new_vop_width =>
            -- send_new_vop_width_state_scheduler
            if (isSchedulable_send_new_vop_width_go = '1') then
              if (parseheaders_BTYPE_full = '0' and parseheaders_WIDTH_full = '0') then
                -- body of "send_new_vop_width" action
                send_new_vop_width0_1    := vol_width;
                send_new_vop_width_BTYPE := send_new_vop_width0_1;
                parseheaders_BTYPE_data  <= std_logic_vector(to_signed(send_new_vop_width_BTYPE, 12));
                parseheaders_BTYPE_write <= '1';
                send_new_vop_width1_1    := vol_width;
                send_new_vop_width_WIDTH := send_new_vop_width1_1;
                parseheaders_WIDTH_data  <= std_logic_vector(to_signed(send_new_vop_width_WIDTH, 16));
                parseheaders_WIDTH_write <= '1';
                FSM                      <= s_send_new_vop_height;
              end if;
            end if;

          when s_stuck =>
            -- stuck_state_scheduler
            if (isSchedulable_byte_align_go = '1') then
              -- body of "byte_align" action
              byte_align0_1           := bit_count;
              bits_to_read_count      <= 8 - bitand(byte_align0_1, 4, 7, 4) - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_stuck_1a;
            end if;

          when s_stuck_1a =>
            -- stuck_1a_state_scheduler
            if (isSchedulable_request_byte_go = '1') then
              -- body of "request_byte" action
              bits_to_read_count      <= 8 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_stuck_1b;
            end if;

          when s_stuck_1b =>
            -- stuck_1b_state_scheduler
            if (isSchedulable_test_zero_byte_go = '1') then
              -- body of "test_zero_byte" action
              FSM <= s_stuck_2a;
            elsif(isSchedulable_generic_done_go = '1') then
              -- body of "generic_done" action
              FSM <= s_stuck_1a;
            end if;

          when s_stuck_2a =>
            -- stuck_2a_state_scheduler
            if (isSchedulable_request_byte_go = '1') then
              -- body of "request_byte" action
              bits_to_read_count      <= 8 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_stuck_2b;
            end if;

          when s_stuck_2b =>
            -- stuck_2b_state_scheduler
            if (isSchedulable_test_zero_byte_go = '1') then
              -- body of "test_zero_byte" action
              FSM <= s_stuck_3a;
            elsif(isSchedulable_generic_done_go = '1') then
              -- body of "generic_done" action
              FSM <= s_stuck_1a;
            end if;

          when s_stuck_3a =>
            -- stuck_3a_state_scheduler
            if (isSchedulable_request_byte_go = '1') then
              -- body of "request_byte" action
              bits_to_read_count      <= 8 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_stuck_3b;
            end if;

          when s_stuck_3b =>
            -- stuck_3b_state_scheduler
            if (isSchedulable_test_one_byte_go = '1') then
              -- body of "test_one_byte" action
              FSM <= s_stuck_4a;
            elsif(isSchedulable_test_zero_byte_go = '1') then
              -- body of "test_zero_byte" action
              FSM <= s_stuck_3a;
            elsif(isSchedulable_generic_done_go = '1') then
              -- body of "generic_done" action
              FSM <= s_stuck_1a;
            end if;

          when s_stuck_4a =>
            -- stuck_4a_state_scheduler
            if (isSchedulable_request_byte_go = '1') then
              -- body of "request_byte" action
              bits_to_read_count      <= 8 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_stuck_4b;
            end if;

          when s_stuck_4b =>
            -- stuck_4b_state_scheduler
            if (isSchedulable_test_vo_byte_go = '1') then
              -- body of "test_vo_byte" action
              FSM <= s_stuck_1a;
            elsif(isSchedulable_test_vol_byte_go = '1') then
              -- body of "test_vol_byte" action
              bits_to_read_count      <= 9 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vol_object;
            elsif(isSchedulable_test_vop_byte_go = '1') then
              -- body of "test_vop_byte" action
              mbx                     <= 0;
              mby                     <= 0;
              bits_to_read_count      <= 2 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vop_predict;
            elsif(isSchedulable_generic_done_go = '1') then
              -- body of "generic_done" action
              FSM <= s_stuck_1a;
            end if;

          when s_texac =>
            -- texac_state_scheduler
            if (isSchedulable_block_done_go = '1') then
              -- body of "block_done" action
              block_done0_1 := comp;
              comp          <= block_done0_1 + 1;
              FSM           <= s_block;
            elsif(isSchedulable_dct_coeff_go = '1') then
              -- body of "dct_coeff" action
              dct_coeff0_1 := btype_is_INTRA;
              if (dct_coeff0_1 = '1') then
                dct_coeff1_1 := 380;
                dct_coeff1_2 := dct_coeff1_1;
              else
                dct_coeff1_3 := 144;
                dct_coeff1_2 := dct_coeff1_3;
              end if;
              vld_index    <= dct_coeff1_2;
              vld_codeword <= 2;
              FSM          <= s_vld1;
            end if;

          when s_texture =>
            -- texture_state_scheduler
            if (isSchedulable_vld_start_intra_go = '1') then
              -- body of "vld_start_intra" action
              vld_start_intra0_1 := comp;
              if (vld_start_intra0_1 < 4) then
                vld_start_intra1_1 := 92;
                vld_start_intra1_2 := vld_start_intra1_1;
              else
                vld_start_intra1_3 := 118;
                vld_start_intra1_2 := vld_start_intra1_3;
              end if;
              vld_index    <= vld_start_intra1_2;
              vld_codeword <= 2;
              b_last       <= '0';
              FSM          <= s_get_dc_bits;
            elsif(isSchedulable_vld_start_inter_ac_coded_go = '1') then
              -- body of "vld_start_inter_ac_coded" action
              b_last <= '0';
              FSM    <= s_texac;
            elsif(isSchedulable_vld_start_inter_not_ac_coded_go = '1') then
              if (parseheaders_RUN_full = '0' and parseheaders_VALUE_full = '0' and parseheaders_LAST_full = '0') then
                -- body of "vld_start_inter_not_ac_coded" action
                b_last                             <= '1';
                vld_start_inter_not_ac_coded_RUN   := 0;
                parseheaders_RUN_data              <= std_logic_vector(to_signed(vld_start_inter_not_ac_coded_RUN, 8));
                parseheaders_RUN_write             <= '1';
                vld_start_inter_not_ac_coded_VALUE := 0;
                parseheaders_VALUE_data            <= std_logic_vector(to_signed(vld_start_inter_not_ac_coded_VALUE, 13));
                parseheaders_VALUE_write           <= '1';
                vld_start_inter_not_ac_coded_LAST  := '1';
                parseheaders_LAST_data             <= vld_start_inter_not_ac_coded_LAST;
                parseheaders_LAST_write            <= '1';
                FSM                                <= s_texac;
              end if;
            end if;

          when s_vld1 =>
            -- vld1_state_scheduler
            if (isSchedulable_vld_code_go = '1') then
              if (parseheaders_VALUE_full = '0' and parseheaders_RUN_full = '0' and parseheaders_LAST_full = '0') then
                -- body of "vld_code" action
                vld_code_bits          := parseheaders_bits_data;
                parseheaders_bits_read <= '1';
                vld_code_sign_1        := vld_code_bits;
                vld_code_val0_1        := vld_result(vld_codeword, '1');
                vld_code0_1            := btype_is_INTRA;
                if (vld_code0_1 = '1') then
                  vld_code_run0_1 := bitand(shift_right(vld_code_val0_1, 20, 8, 5), 32, 255, 9);
                  vld_code_run0_2 := vld_code_run0_1;
                else
                  vld_code_run0_3 := bitand(shift_right(vld_code_val0_1, 20, 4, 4), 32, 255, 9);
                  vld_code_run0_2 := vld_code_run0_3;
                end if;
                vld_code1_1 := btype_is_INTRA;
                if (vld_code1_1 = '1') then
                  if (bitand(shift_right(vld_code_val0_1, 20, 16, 6), 32, 1, 2) /= 0) then
                    vld_code_last0_1 := '1';
                  else
                    vld_code_last0_1 := '0';
                  end if;
                  vld_code_last0_2 := vld_code_last0_1;
                else
                  if (bitand(shift_right(vld_code_val0_1, 20, 12, 5), 32, 1, 2) /= 0) then
                    vld_code_last0_3 := '1';
                  else
                    vld_code_last0_3 := '0';
                  end if;
                  vld_code_last0_2 := vld_code_last0_3;
                end if;
                vld_code2_1 := btype_is_INTRA;
                if (vld_code2_1 = '1') then
                  vld_code_level0_1 := bitand(vld_code_val0_1, 20, 255, 9);
                  vld_code_level0_2 := vld_code_level0_1;
                else
                  vld_code_level0_3 := bitand(vld_code_val0_1, 20, 15, 5);
                  vld_code_level0_2 := vld_code_level0_3;
                end if;
                b_last      <= vld_code_last0_2;
                vld_code3_1 := bit_count;
                bit_count   <= vld_code3_1 + 1;
                if (vld_code_sign_1 = '1') then
                  vld_code4_1 := -vld_code_level0_2;
                  vld_code4_2 := vld_code4_1;
                else
                  vld_code4_3 := vld_code_level0_2;
                  vld_code4_2 := vld_code4_3;
                end if;
                vld_code_VALUE           := vld_code4_2;
                parseheaders_VALUE_data  <= std_logic_vector(to_signed(vld_code_VALUE, 13));
                parseheaders_VALUE_write <= '1';
                vld_code_RUN             := vld_code_run0_2;
                parseheaders_RUN_data    <= std_logic_vector(to_signed(vld_code_RUN, 8));
                parseheaders_RUN_write   <= '1';
                vld_code_LAST            := vld_code_last0_2;
                parseheaders_LAST_data   <= vld_code_LAST;
                parseheaders_LAST_write  <= '1';
                FSM                      <= s_texac;
              end if;
            elsif(isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            elsif(isSchedulable_vld_level_go = '1') then
              -- body of "vld_level" action
              vld_level_bits         := parseheaders_bits_data;
              parseheaders_bits_read <= '1';
              vld_level0_1           := bit_count;
              bit_count              <= vld_level0_1 + 1;
              vld_level1_1           := btype_is_INTRA;
              if (vld_level1_1 = '1') then
                vld_level2_1 := 380;
                vld_level2_2 := vld_level2_1;
              else
                vld_level2_3 := 144;
                vld_level2_2 := vld_level2_3;
              end if;
              vld_index    <= vld_level2_2;
              vld_codeword <= 2;
              FSM          <= s_vld4;
            elsif(isSchedulable_vld_run_or_direct_go = '1') then
              -- body of "vld_run_or_direct" action
              vld_run_or_direct_bits := parseheaders_bits_data;
              parseheaders_bits_read <= '1';
              vld_run_or_direct0_1   := bit_count;
              bit_count              <= vld_run_or_direct0_1 + 1;
              FSM                    <= s_vld7;
            end if;

          when s_vld4 =>
            -- vld4_state_scheduler
            if (isSchedulable_do_level_lookup_go = '1') then
              -- body of "do_level_lookup" action
              do_level_lookup_val0_1 := vld_result(vld_codeword, '1');
              if (bitand(shift_right(do_level_lookup_val0_1, 18, 12, 5), 32, 1, 2) /= 0) then
                interm_comput := '1';
              else
                interm_comput := '0';
              end if;
              do_level_lookup0_1 := inter_max_level(interm_comput, bitand(shift_right(do_level_lookup_val0_1, 18, 4, 4), 32, 255, 9), '1');
              level_lookup_inter <= do_level_lookup0_1;
              if (bitand(shift_right(do_level_lookup_val0_1, 18, 16, 6), 32, 1, 2) /= 0) then
                interm_comput := '1';
              else
                interm_comput := '0';
              end if;
              do_level_lookup1_1 := intra_max_level(interm_comput, bitand(shift_right(do_level_lookup_val0_1, 18, 8, 5), 32, 255, 9), '1');
              level_lookup_intra <= do_level_lookup1_1;
              FSM                <= s_vld4a;
            elsif(isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            end if;

          when s_vld4a =>
            -- vld4a_state_scheduler
            if (isSchedulable_vld_level_lookup_go = '1') then
              if (parseheaders_VALUE_full = '0' and parseheaders_RUN_full = '0' and parseheaders_LAST_full = '0') then
                -- body of "vld_level_lookup" action
                vld_level_lookup_bits   := parseheaders_bits_data;
                parseheaders_bits_read  <= '1';
                vld_level_lookup_sign_1 := vld_level_lookup_bits;
                vld_level_lookup_val0_1 := vld_result(vld_codeword, '1');
                vld_level_lookup0_1     := btype_is_INTRA;
                if (vld_level_lookup0_1 = '1') then
                  vld_level_lookup_run0_1 := bitand(shift_right(vld_level_lookup_val0_1, 18, 8, 5), 32, 255, 9);
                  vld_level_lookup_run0_2 := vld_level_lookup_run0_1;
                else
                  vld_level_lookup_run0_3 := bitand(shift_right(vld_level_lookup_val0_1, 18, 4, 4), 32, 255, 9);
                  vld_level_lookup_run0_2 := vld_level_lookup_run0_3;
                end if;
                vld_level_lookup1_1 := btype_is_INTRA;
                if (vld_level_lookup1_1 = '1') then
                  if (bitand(shift_right(vld_level_lookup_val0_1, 18, 16, 6), 32, 1, 2) /= 0) then
                    vld_level_lookup_last0_1 := '1';
                  else
                    vld_level_lookup_last0_1 := '0';
                  end if;
                  vld_level_lookup_last0_2 := vld_level_lookup_last0_1;
                else
                  if (bitand(shift_right(vld_level_lookup_val0_1, 18, 12, 5), 32, 1, 2) /= 0) then
                    vld_level_lookup_last0_3 := '1';
                  else
                    vld_level_lookup_last0_3 := '0';
                  end if;
                  vld_level_lookup_last0_2 := vld_level_lookup_last0_3;
                end if;
                vld_level_lookup2_1 := btype_is_INTRA;
                if (vld_level_lookup2_1 = '1') then
                  vld_level_lookup3_1       := level_lookup_intra;
                  vld_level_lookup_level0_1 := bitand(vld_level_lookup_val0_1, 18, 255, 9) + vld_level_lookup3_1;
                  vld_level_lookup_level0_2 := vld_level_lookup_level0_1;
                else
                  vld_level_lookup4_1       := level_lookup_inter;
                  vld_level_lookup_level0_3 := bitand(vld_level_lookup_val0_1, 18, 15, 5) + vld_level_lookup4_1;
                  vld_level_lookup_level0_2 := vld_level_lookup_level0_3;
                end if;
                b_last              <= vld_level_lookup_last0_2;
                vld_level_lookup5_1 := bit_count;
                bit_count           <= vld_level_lookup5_1 + 1;
                if (vld_level_lookup_sign_1 = '1') then
                  vld_level_lookup6_1 := -vld_level_lookup_level0_2;
                  vld_level_lookup6_2 := vld_level_lookup6_1;
                else
                  vld_level_lookup6_3 := vld_level_lookup_level0_2;
                  vld_level_lookup6_2 := vld_level_lookup6_3;
                end if;
                vld_level_lookup_VALUE   := vld_level_lookup6_2;
                parseheaders_VALUE_data  <= std_logic_vector(to_signed(vld_level_lookup_VALUE, 13));
                parseheaders_VALUE_write <= '1';
                vld_level_lookup_RUN     := vld_level_lookup_run0_2;
                parseheaders_RUN_data    <= std_logic_vector(to_signed(vld_level_lookup_RUN, 8));
                parseheaders_RUN_write   <= '1';
                vld_level_lookup_LAST    := vld_level_lookup_last0_2;
                parseheaders_LAST_data   <= vld_level_lookup_LAST;
                parseheaders_LAST_write  <= '1';
                FSM                      <= s_texac;
              end if;
            end if;

          when s_vld6 =>
            -- vld6_state_scheduler
            if (isSchedulable_do_run_lookup_go = '1') then
              -- body of "do_run_lookup" action
              do_run_lookup_val0_1 := vld_result(vld_codeword, '1');
              if (bitand(shift_right(do_run_lookup_val0_1, 18, 12, 5), 32, 1, 2) /= 0) then
                interm_comput := '1';
              else
                interm_comput := '0';
              end if;
              do_run_lookup0_1 := inter_max_run(interm_comput, bitand(do_run_lookup_val0_1, 18, 15, 5), '1');
              run_lookup_inter <= do_run_lookup0_1;
              if (bitand(shift_right(do_run_lookup_val0_1, 18, 16, 6), 32, 1, 2) /= 0) then
                interm_comput := '1';
              else
                interm_comput := '0';
              end if;
              do_run_lookup1_1 := intra_max_run(interm_comput, bitand(do_run_lookup_val0_1, 18, 255, 9), '1');
              run_lookup_intra <= do_run_lookup1_1;
              FSM              <= s_vld6a;
            elsif(isSchedulable_do_vld_failure_go = '1') then
              -- body of "do_vld_failure" action
              FSM <= s_stuck;
            end if;

          when s_vld6a =>
            -- vld6a_state_scheduler
            if (isSchedulable_vld_run_lookup_go = '1') then
              if (parseheaders_VALUE_full = '0' and parseheaders_RUN_full = '0' and parseheaders_LAST_full = '0') then
                -- body of "vld_run_lookup" action
                vld_run_lookup_bits    := parseheaders_bits_data;
                parseheaders_bits_read <= '1';
                vld_run_lookup_sign_1  := vld_run_lookup_bits;
                vld_run_lookup_val0_1  := vld_result(vld_codeword, '1');
                vld_run_lookup0_1      := btype_is_INTRA;
                if (vld_run_lookup0_1 = '1') then
                  if (bitand(shift_right(vld_run_lookup_val0_1, 18, 16, 6), 32, 1, 2) /= 0) then
                    vld_run_lookup_last0_1 := '1';
                  else
                    vld_run_lookup_last0_1 := '0';
                  end if;
                  vld_run_lookup_last0_2 := vld_run_lookup_last0_1;
                else
                  if (bitand(shift_right(vld_run_lookup_val0_1, 18, 12, 5), 32, 1, 2) /= 0) then
                    vld_run_lookup_last0_3 := '1';
                  else
                    vld_run_lookup_last0_3 := '0';
                  end if;
                  vld_run_lookup_last0_2 := vld_run_lookup_last0_3;
                end if;
                vld_run_lookup1_1 := btype_is_INTRA;
                if (vld_run_lookup1_1 = '1') then
                  vld_run_lookup_level0_1 := bitand(vld_run_lookup_val0_1, 18, 255, 9);
                  vld_run_lookup_level0_2 := vld_run_lookup_level0_1;
                else
                  vld_run_lookup_level0_3 := bitand(vld_run_lookup_val0_1, 18, 15, 5);
                  vld_run_lookup_level0_2 := vld_run_lookup_level0_3;
                end if;
                vld_run_lookup2_1 := btype_is_INTRA;
                if (vld_run_lookup2_1 = '1') then
                  vld_run_lookup4_1 := run_lookup_intra;
                  vld_run_lookup3_1 := bitand(shift_right(vld_run_lookup_val0_1, 18, 8, 5), 32, 255, 9) + vld_run_lookup4_1;
                  vld_run_lookup3_2 := vld_run_lookup3_1;
                else
                  vld_run_lookup5_1 := run_lookup_inter;
                  vld_run_lookup3_3 := bitand(shift_right(vld_run_lookup_val0_1, 18, 4, 4), 32, 255, 9) + vld_run_lookup5_1;
                  vld_run_lookup3_2 := vld_run_lookup3_3;
                end if;
                vld_run_lookup_run0_1 := vld_run_lookup3_2 + 1;
                b_last                <= vld_run_lookup_last0_2;
                vld_run_lookup6_1     := bit_count;
                bit_count             <= vld_run_lookup6_1 + 1;
                if (vld_run_lookup_sign_1 = '1') then
                  vld_run_lookup7_1 := -vld_run_lookup_level0_2;
                  vld_run_lookup7_2 := vld_run_lookup7_1;
                else
                  vld_run_lookup7_3 := vld_run_lookup_level0_2;
                  vld_run_lookup7_2 := vld_run_lookup7_3;
                end if;
                vld_run_lookup_VALUE     := vld_run_lookup7_2;
                parseheaders_VALUE_data  <= std_logic_vector(to_signed(vld_run_lookup_VALUE, 13));
                parseheaders_VALUE_write <= '1';
                vld_run_lookup_RUN       := vld_run_lookup_run0_1;
                parseheaders_RUN_data    <= std_logic_vector(to_signed(vld_run_lookup_RUN, 8));
                parseheaders_RUN_write   <= '1';
                vld_run_lookup_LAST      := vld_run_lookup_last0_2;
                parseheaders_LAST_data   <= vld_run_lookup_LAST;
                parseheaders_LAST_write  <= '1';
                FSM                      <= s_texac;
              end if;
            end if;

          when s_vld7 =>
            -- vld7_state_scheduler
            if (isSchedulable_vld_run_go = '1') then
              -- body of "vld_run" action
              vld_run_bits           := parseheaders_bits_data;
              parseheaders_bits_read <= '1';
              vld_run0_1             := bit_count;
              bit_count              <= vld_run0_1 + 1;
              vld_run1_1             := btype_is_INTRA;
              if (vld_run1_1 = '1') then
                vld_run2_1 := 380;
                vld_run2_2 := vld_run2_1;
              else
                vld_run2_3 := 144;
                vld_run2_2 := vld_run2_3;
              end if;
              vld_index    <= vld_run2_2;
              vld_codeword <= 2;
              vld_index    <= vld_run2_2;
              vld_codeword <= 2;
              FSM          <= s_vld6;
            elsif(isSchedulable_vld_direct_read_go = '1') then
              -- body of "vld_direct_read" action
              vld_direct_read_bits    := parseheaders_bits_data;
              parseheaders_bits_read  <= '1';
              vld_direct_read0_1      := bit_count;
              bit_count               <= vld_direct_read0_1 + 1;
              bits_to_read_count      <= 21 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vld_direct;
            end if;

          when s_vld_direct =>
            -- vld_direct_state_scheduler
            if (isSchedulable_vld_direct_go = '1') then
              if (parseheaders_VALUE_full = '0' and parseheaders_RUN_full = '0' and parseheaders_LAST_full = '0') then
                -- body of "vld_direct" action
                vld_direct0_1 := read_result(read_result_in_progress, '1');
                if (bitand(shift_right(vld_direct0_1, 32, 20, 6), 32, 1, 2) /= 0) then
                  vld_direct_last0_1 := '1';
                else
                  vld_direct_last0_1 := '0';
                end if;
                vld_direct5_1       := read_result(read_result_in_progress, '1');
                vld_direct_run0_1   := bitand(shift_right(vld_direct5_1, 32, 14, 5), 32, 63, 7);
                vld_direct10_1      := read_result(read_result_in_progress, '1');
                vld_direct_level0_1 := bitand(shift_right(vld_direct10_1, 32, 1, 2), 32, 4095, 13);
                if (vld_direct_level0_1 >= 2048) then
                  vld_direct_sign0_1  := '1';
                  vld_direct_level0_2 := 4096 - vld_direct_level0_1;
                  vld_direct_sign0_2  := vld_direct_sign0_1;
                  vld_direct_level0_3 := vld_direct_level0_2;
                else
                  vld_direct_sign0_3  := '0';
                  vld_direct_sign0_2  := vld_direct_sign0_3;
                  vld_direct_level0_3 := vld_direct_level0_1;
                end if;
                b_last <= vld_direct_last0_1;
                if (vld_direct_sign0_2 = '1') then
                  vld_direct13_1 := -vld_direct_level0_3;
                  vld_direct13_2 := vld_direct13_1;
                else
                  vld_direct13_3 := vld_direct_level0_3;
                  vld_direct13_2 := vld_direct13_3;
                end if;
                vld_direct_VALUE         := vld_direct13_2;
                parseheaders_VALUE_data  <= std_logic_vector(to_signed(vld_direct_VALUE, 13));
                parseheaders_VALUE_write <= '1';
                vld_direct_RUN           := vld_direct_run0_1;
                parseheaders_RUN_data    <= std_logic_vector(to_signed(vld_direct_RUN, 8));
                parseheaders_RUN_write   <= '1';
                vld_direct_LAST          := vld_direct_last0_1;
                parseheaders_LAST_data   <= vld_direct_LAST;
                parseheaders_LAST_write  <= '1';
                FSM                      <= s_texac;
              end if;
            end if;

          when s_vol_aspect =>
            -- vol_aspect_state_scheduler
            if (isSchedulable_vol_aspect_detailed_go = '1') then
              -- body of "vol_aspect_detailed" action
              bits_to_read_count      <= 16 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vol_control;
            elsif(isSchedulable_generic_done_go = '1') then
              -- body of "generic_done" action
              FSM <= s_vol_control;
            end if;

          when s_vol_control =>
            -- vol_control_state_scheduler
            if (isSchedulable_vol_control_detailed_go = '1') then
              -- body of "vol_control_detailed" action
              vol_control_detailed_bits := parseheaders_bits_data;
              parseheaders_bits_read    <= '1';
              bits_to_read_count        <= 3 - 1;
              read_result_in_progress   <= 0;
              vol_control_detailed2_1   := bit_count;
              bit_count                 <= vol_control_detailed2_1 + 1;
              FSM                       <= s_vol_vbv;
            elsif(isSchedulable_generic_done_with_bitread_go = '1') then
              -- body of "generic_done_with_bitread" action
              generic_done_with_bitread_bits := parseheaders_bits_data;
              parseheaders_bits_read         <= '1';
              generic_done_with_bitread0_1   := bit_count;
              bit_count                      <= generic_done_with_bitread0_1 + 1;
              FSM                            <= s_vol_shape;
            end if;

          when s_vol_height =>
            -- vol_height_state_scheduler
            if (isSchedulable_set_vol_height_go = '1') then
              -- body of "set_vol_height" action
              set_vol_height0_1       := read_result(read_result_in_progress, '1');
              set_vol_height3_1       := mask_bits(shift_right(set_vol_height0_1, 32, 5, 4), 13, '1');
              vol_height              <= set_vol_height3_1;
              bits_to_read_count      <= 9 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vol_misc;
            end if;

          when s_vol_misc =>
            -- vol_misc_state_scheduler
            if (isSchedulable_generic_done_go = '1') then
              -- body of "generic_done" action
              FSM <= s_stuck;
            end if;

          when s_vol_object =>
            -- vol_object_state_scheduler
            if (isSchedulable_vol_object_layer_identification_go = '1') then
              -- body of "vol_object_layer_identification" action
              vol_object_layer_identification_bits := parseheaders_bits_data;
              parseheaders_bits_read               <= '1';
              vol_object_layer_identification_b_1  := vol_object_layer_identification_bits;
              if (vol_object_layer_identification_b_1 = '1') then
                vol_object_layer_identification0_1 := 11;
                vol_object_layer_identification0_2 := vol_object_layer_identification0_1;
              else
                vol_object_layer_identification0_3 := 4;
                vol_object_layer_identification0_2 := vol_object_layer_identification0_3;
              end if;
              bits_to_read_count                 <= vol_object_layer_identification0_2 - 1;
              read_result_in_progress            <= 0;
              vol_object_layer_identification5_1 := bit_count;
              bit_count                          <= vol_object_layer_identification5_1 + 1;
              FSM                                <= s_vol_aspect;
            end if;

          when s_vol_shape =>
            -- vol_shape_state_scheduler
            if (isSchedulable_vol_shape_go = '1') then
              -- body of "vol_shape" action
              bits_to_read_count      <= 21 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vol_time_inc_res;
            end if;

          when s_vol_time_inc_res =>
            -- vol_time_inc_res_state_scheduler
            if (isSchedulable_vol_time_inc_res_go = '1') then
              -- body of "vol_time_inc_res" action
              vol_time_inc_res0_1              := read_result(read_result_in_progress, '1');
              vol_time_inc_res_time_inc_res0_1 := mask_bits(shift_right(vol_time_inc_res0_1, 32, 2, 3), 16, '1');
              vol_time_inc_res_count0_1        := 0;
              vol_time_inc_res_ones0_1         := 0;
              vol_time_inc_res_ones0_4         := vol_time_inc_res_ones0_1;
              vol_time_inc_res_count0_3        := vol_time_inc_res_count0_1;
              vol_time_inc_res_time_inc_res0_3 := vol_time_inc_res_time_inc_res0_1;
              while ((vol_time_inc_res_count0_3 = 0) or (vol_time_inc_res_time_inc_res0_3 /= 0)) loop
                if (bitand(vol_time_inc_res_time_inc_res0_3, 17, 1, 2) = 1) then
                  vol_time_inc_res_ones0_2 := vol_time_inc_res_ones0_4 + 1;
                  vol_time_inc_res_ones0_3 := vol_time_inc_res_ones0_2;
                else
                  vol_time_inc_res_ones0_3 := vol_time_inc_res_ones0_4;
                end if;
                vol_time_inc_res_count0_2        := vol_time_inc_res_count0_3 + 1;
                vol_time_inc_res_time_inc_res0_2 := shift_right(vol_time_inc_res_time_inc_res0_3, 17, 1, 2);
                vol_time_inc_res_ones0_4         := vol_time_inc_res_ones0_3;
                vol_time_inc_res_count0_3        := vol_time_inc_res_count0_2;
                vol_time_inc_res_time_inc_res0_3 := vol_time_inc_res_time_inc_res0_2;

              end loop; if (vol_time_inc_res_ones0_4 > 1) then
                         vol_time_inc_res2_1 := vol_time_inc_res_count0_3;
                         vol_time_inc_res2_2 := vol_time_inc_res2_1;
                       else
                         vol_time_inc_res2_3 := vol_time_inc_res_count0_3 - 1;
                         vol_time_inc_res2_2 := vol_time_inc_res2_3;
                       end if;
              mylog               <= vol_time_inc_res2_2;
              vol_time_inc_res3_1 := mylog;
              if (vol_time_inc_res3_1 < 1) then
                vol_time_inc_res4_1 := 1;
                vol_time_inc_res4_2 := vol_time_inc_res4_1;
              else
                vol_time_inc_res5_1 := mylog;
                vol_time_inc_res4_3 := vol_time_inc_res5_1;
                vol_time_inc_res4_2 := vol_time_inc_res4_3;
              end if;
              mylog               <= vol_time_inc_res4_2;
              vol_time_inc_res6_1 := read_result(read_result_in_progress, '1');
              if (bitand(vol_time_inc_res6_1, 32, 1, 2) = 1) then
                vol_time_inc_res8_1 := mylog;
                vol_time_inc_res7_1 := vol_time_inc_res8_1;
                vol_time_inc_res7_2 := vol_time_inc_res7_1;
              else
                vol_time_inc_res7_3 := 0;
                vol_time_inc_res7_2 := vol_time_inc_res7_3;
              end if;
              bits_to_read_count      <= vol_time_inc_res7_2 + 1 + 13 + 1 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vol_width;
            end if;

          when s_vol_vbv =>
            -- vol_vbv_state_scheduler
            if (isSchedulable_vol_vbv_detailed_go = '1') then
              -- body of "vol_vbv_detailed" action
              vol_vbv_detailed_bits   := parseheaders_bits_data;
              parseheaders_bits_read  <= '1';
              bits_to_read_count      <= 79 - 1;
              read_result_in_progress <= 0;
              vol_vbv_detailed11_1    := bit_count;
              bit_count               <= vol_vbv_detailed11_1 + 1;
              FSM                     <= s_vol_shape;
            elsif(isSchedulable_generic_done_with_bitread_go = '1') then
              -- body of "generic_done_with_bitread" action
              generic_done_with_bitread_bits := parseheaders_bits_data;
              parseheaders_bits_read         <= '1';
              generic_done_with_bitread0_1   := bit_count;
              bit_count                      <= generic_done_with_bitread0_1 + 1;
              FSM                            <= s_vol_shape;
            end if;

          when s_vol_width =>
            -- vol_width_state_scheduler
            if (isSchedulable_set_vol_width_go = '1') then
              -- body of "set_vol_width" action
              set_vol_width0_1        := read_result(read_result_in_progress, '1');
              set_vol_width3_1        := mask_bits(shift_right(set_vol_width0_1, 32, 5, 4), 9, '1');
              vol_width               <= set_vol_width3_1;
              bits_to_read_count      <= 14 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vol_height;
            end if;

          when s_vop_coding =>
            -- vop_coding_state_scheduler
            if (isSchedulable_vop_coding_uncoded_go = '1') then
              -- body of "vop_coding_uncoded" action
              vop_coding_uncoded_bits := parseheaders_bits_data;
              parseheaders_bits_read  <= '1';
              comp                    <= 0;
              vop_coding_uncoded0_1   := bit_count;
              bit_count               <= vop_coding_uncoded0_1 + 1;
              FSM                     <= s_stuck;
            elsif(isSchedulable_vop_coding_coded_go = '1') then
              -- body of "vop_coding_coded" action
              vop_coding_coded_bits  := parseheaders_bits_data;
              parseheaders_bits_read <= '1';
              vop_coding_coded0_1    := prediction_is_IVOP;
              if (vop_coding_coded0_1 = '0') then
                vop_coding_coded1_1 := 12;
                vop_coding_coded1_2 := vop_coding_coded1_1;
              else
                vop_coding_coded1_3 := 8;
                vop_coding_coded1_2 := vop_coding_coded1_3;
              end if;
              bits_to_read_count      <= vop_coding_coded1_2 - 1;
              read_result_in_progress <= 0;
              vop_coding_coded7_1     := bit_count;
              bit_count               <= vop_coding_coded7_1 + 1;
              FSM                     <= s_send_new_vop_info;
            end if;

          when s_vop_predict =>
            -- vop_predict_state_scheduler
            if (isSchedulable_vop_predict_supported_go = '1') then
              -- body of "vop_predict_supported" action
              vop_predict_supported0_1 := read_result(read_result_in_progress, '1');
              vop_predict_supported2_1 := mask_bits(vop_predict_supported0_1, 2, '1');
              if (vop_predict_supported2_1 = 0) then
                vop_predict_supported_bool_expr_1 := '1';
              else
                vop_predict_supported_bool_expr_1 := '0';
              end if;
              prediction_is_IVOP       <= vop_predict_supported_bool_expr_1;
              vop_predict_supported4_1 := read_result(read_result_in_progress, '1');
              vop_predict_supported6_1 := mask_bits(vop_predict_supported4_1, 2, '1');
              if (vop_predict_supported6_1 = 1) then
                vop_predict_supported_bool_expr_2 := '1';
              else
                vop_predict_supported_bool_expr_2 := '0';
              end if;
              prediction_is_PVOP        <= vop_predict_supported_bool_expr_2;
              vop_predict_supported8_1  := read_result(read_result_in_progress, '1');
              vop_predict_supported10_1 := mask_bits(vop_predict_supported8_1, 2, '1');
              if (vop_predict_supported10_1 = 2) then
                vop_predict_supported_bool_expr_3 := '1';
              else
                vop_predict_supported_bool_expr_3 := '0';
              end if;
              prediction_is_BVOP <= vop_predict_supported_bool_expr_3;
              FSM                <= s_vop_timebase;
            elsif(isSchedulable_generic_done_go = '1') then
              -- body of "generic_done" action
              FSM <= s_stuck;
            end if;

          when s_vop_timebase =>
            -- vop_timebase_state_scheduler
            if (isSchedulable_vop_timebase_one_go = '1') then
              -- body of "vop_timebase_one" action
              vop_timebase_one_bits  := parseheaders_bits_data;
              parseheaders_bits_read <= '1';
              vop_timebase_one0_1    := bit_count;
              bit_count              <= vop_timebase_one0_1 + 1;
              FSM                    <= s_vop_timebase;
            elsif(isSchedulable_vop_timebase_zero_go = '1') then
              -- body of "vop_timebase_zero" action
              vop_timebase_zero_bits  := parseheaders_bits_data;
              parseheaders_bits_read  <= '1';
              vop_timebase_zero0_1    := bit_count;
              bit_count               <= vop_timebase_zero0_1 + 1;
              vop_timebase_zero2_1    := mylog;
              bits_to_read_count      <= 1 + vop_timebase_zero2_1 + 1 - 1;
              read_result_in_progress <= 0;
              FSM                     <= s_vop_coding;
            end if;


        end case;
      end if;
    end if;
  end process parseheaders_proc;
end architecture rtl_parseheaders;
