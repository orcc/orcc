-- ----------------------------------------------------------------------------------
-- top level model for idct2d
-- Generated: [Language: en]2008-09-16 07:41:11(GMT-7)
-- ----------------------------------------------------------------------------------

-- ----------------------------------------------------------------------------------
--
-- Clock domain report for network idct2d (1 clock domains detected)
--    CLK
--
--  Clock domains for top-level network ports:
--    in (Input) --> CLK
--    signed (Input) --> CLK
--    out (Output) --> CLK
--
--  Clock domains for all actor instances:
--    Scale_0 (Scale) --> CLK
--    Combine_0 (Combine) --> CLK
--    ShuffleFly_0 (ShuffleFly) --> CLK
--    Shuffle_0 (Shuffle) --> CLK
--    Final_0 (Final) --> CLK
--    RowSort_0 (RowSort) --> CLK
--    FairMerge_0 (FairMerge) --> CLK
--    Downsample_0 (Downsample) --> CLK
--    Separate_0 (Separate) --> CLK
--    Transpose_0 (Transpose) --> CLK
--    Retranspose_0 (Retranspose) --> CLK
--    Clip_0 (Clip) --> CLK
--
-- ----------------------------------------------------------------------------------

library ieee, SystemBuilder;
use ieee.std_logic_1164.all;

entity idct2d is
port (
    in_DATA  : in  std_logic_vector(12 downto 0);
    in_SEND  : in  std_logic;
    in_ACK   : out std_logic;
    in_COUNT : in  std_logic_vector(15 downto 0);
    in_RDY   : out std_logic;
    
    signed_DATA  : in  std_logic;
    signed_SEND  : in  std_logic;
    signed_ACK   : out std_logic;
    signed_COUNT : in  std_logic_vector(15 downto 0);
    signed_RDY   : out std_logic;
    
    out_DATA  : out std_logic_vector(8 downto 0);
    out_SEND  : out std_logic;
    out_ACK   : in  std_logic;
    out_COUNT : out std_logic_vector(15 downto 0);
    out_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic );
end entity idct2d;

architecture rtl of idct2d is

  signal clocks, resets: std_logic_vector(0 downto 0);

  signal ni_in_DATA  : std_logic_vector(12 downto 0);
  signal ni_in_SEND  : std_logic;
  signal ni_in_ACK   : std_logic;
  signal ni_in_COUNT : std_logic_vector(15 downto 0);
  signal ni_in_RDY   : std_logic;

  signal nif_in_DATA  : std_logic_vector(12 downto 0);
  signal nif_in_SEND  : std_logic_vector(0 downto 0);
  signal nif_in_ACK   : std_logic_vector(0 downto 0);
  signal nif_in_COUNT : std_logic_vector(15 downto 0);
  signal nif_in_RDY   : std_logic_vector(0 downto 0);

  signal ni_signed_DATA  : std_logic;
  signal ni_signed_SEND  : std_logic;
  signal ni_signed_ACK   : std_logic;
  signal ni_signed_COUNT : std_logic_vector(15 downto 0);
  signal ni_signed_RDY   : std_logic;

  signal nif_signed_DATA  : std_logic;
  signal nif_signed_SEND  : std_logic_vector(0 downto 0);
  signal nif_signed_ACK   : std_logic_vector(0 downto 0);
  signal nif_signed_COUNT : std_logic_vector(15 downto 0);
  signal nif_signed_RDY   : std_logic_vector(0 downto 0);

  signal no_out_DATA  : std_logic_vector(8 downto 0);
  signal no_out_SEND  : std_logic;
  signal no_out_ACK   : std_logic;
  signal no_out_COUNT : std_logic_vector(15 downto 0);
  signal no_out_RDY   : std_logic;

  signal ao_Scale_0_Y0_DATA  : std_logic_vector(29 downto 0);
  signal ao_Scale_0_Y0_SEND  : std_logic;
  signal ao_Scale_0_Y0_ACK   : std_logic;
  signal ao_Scale_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_Scale_0_Y0_RDY   : std_logic;

  signal aof_Scale_0_Y0_DATA  : std_logic_vector(29 downto 0);
  signal aof_Scale_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_Scale_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_Scale_0_Y1_DATA  : std_logic_vector(29 downto 0);
  signal ao_Scale_0_Y1_SEND  : std_logic;
  signal ao_Scale_0_Y1_ACK   : std_logic;
  signal ao_Scale_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_Scale_0_Y1_RDY   : std_logic;

  signal aof_Scale_0_Y1_DATA  : std_logic_vector(29 downto 0);
  signal aof_Scale_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_Scale_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_Scale_0_Y2_DATA  : std_logic_vector(29 downto 0);
  signal ao_Scale_0_Y2_SEND  : std_logic;
  signal ao_Scale_0_Y2_ACK   : std_logic;
  signal ao_Scale_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal ao_Scale_0_Y2_RDY   : std_logic;

  signal aof_Scale_0_Y2_DATA  : std_logic_vector(29 downto 0);
  signal aof_Scale_0_Y2_SEND  : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y2_ACK   : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal aof_Scale_0_Y2_RDY   : std_logic_vector(0 downto 0);

  signal ao_Scale_0_Y3_DATA  : std_logic_vector(29 downto 0);
  signal ao_Scale_0_Y3_SEND  : std_logic;
  signal ao_Scale_0_Y3_ACK   : std_logic;
  signal ao_Scale_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal ao_Scale_0_Y3_RDY   : std_logic;

  signal aof_Scale_0_Y3_DATA  : std_logic_vector(29 downto 0);
  signal aof_Scale_0_Y3_SEND  : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y3_ACK   : std_logic_vector(0 downto 0);
  signal aof_Scale_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal aof_Scale_0_Y3_RDY   : std_logic_vector(0 downto 0);

  signal ao_Combine_0_Y0_DATA  : std_logic_vector(23 downto 0);
  signal ao_Combine_0_Y0_SEND  : std_logic;
  signal ao_Combine_0_Y0_ACK   : std_logic;
  signal ao_Combine_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_Combine_0_Y0_RDY   : std_logic;

  signal aof_Combine_0_Y0_DATA  : std_logic_vector(23 downto 0);
  signal aof_Combine_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_Combine_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_Combine_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_Combine_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_Combine_0_Y1_DATA  : std_logic_vector(23 downto 0);
  signal ao_Combine_0_Y1_SEND  : std_logic;
  signal ao_Combine_0_Y1_ACK   : std_logic;
  signal ao_Combine_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_Combine_0_Y1_RDY   : std_logic;

  signal aof_Combine_0_Y1_DATA  : std_logic_vector(23 downto 0);
  signal aof_Combine_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_Combine_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_Combine_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_Combine_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_ShuffleFly_0_Y0_DATA  : std_logic_vector(23 downto 0);
  signal ao_ShuffleFly_0_Y0_SEND  : std_logic;
  signal ao_ShuffleFly_0_Y0_ACK   : std_logic;
  signal ao_ShuffleFly_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_ShuffleFly_0_Y0_RDY   : std_logic;

  signal aof_ShuffleFly_0_Y0_DATA  : std_logic_vector(23 downto 0);
  signal aof_ShuffleFly_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_ShuffleFly_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_ShuffleFly_0_Y1_DATA  : std_logic_vector(23 downto 0);
  signal ao_ShuffleFly_0_Y1_SEND  : std_logic;
  signal ao_ShuffleFly_0_Y1_ACK   : std_logic;
  signal ao_ShuffleFly_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_ShuffleFly_0_Y1_RDY   : std_logic;

  signal aof_ShuffleFly_0_Y1_DATA  : std_logic_vector(23 downto 0);
  signal aof_ShuffleFly_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_ShuffleFly_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_ShuffleFly_0_Y2_DATA  : std_logic_vector(23 downto 0);
  signal ao_ShuffleFly_0_Y2_SEND  : std_logic;
  signal ao_ShuffleFly_0_Y2_ACK   : std_logic;
  signal ao_ShuffleFly_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal ao_ShuffleFly_0_Y2_RDY   : std_logic;

  signal aof_ShuffleFly_0_Y2_DATA  : std_logic_vector(23 downto 0);
  signal aof_ShuffleFly_0_Y2_SEND  : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y2_ACK   : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal aof_ShuffleFly_0_Y2_RDY   : std_logic_vector(0 downto 0);

  signal ao_ShuffleFly_0_Y3_DATA  : std_logic_vector(23 downto 0);
  signal ao_ShuffleFly_0_Y3_SEND  : std_logic;
  signal ao_ShuffleFly_0_Y3_ACK   : std_logic;
  signal ao_ShuffleFly_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal ao_ShuffleFly_0_Y3_RDY   : std_logic;

  signal aof_ShuffleFly_0_Y3_DATA  : std_logic_vector(23 downto 0);
  signal aof_ShuffleFly_0_Y3_SEND  : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y3_ACK   : std_logic_vector(0 downto 0);
  signal aof_ShuffleFly_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal aof_ShuffleFly_0_Y3_RDY   : std_logic_vector(0 downto 0);

  signal ao_Shuffle_0_Y0_DATA  : std_logic_vector(23 downto 0);
  signal ao_Shuffle_0_Y0_SEND  : std_logic;
  signal ao_Shuffle_0_Y0_ACK   : std_logic;
  signal ao_Shuffle_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_Shuffle_0_Y0_RDY   : std_logic;

  signal aof_Shuffle_0_Y0_DATA  : std_logic_vector(23 downto 0);
  signal aof_Shuffle_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_Shuffle_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_Shuffle_0_Y1_DATA  : std_logic_vector(23 downto 0);
  signal ao_Shuffle_0_Y1_SEND  : std_logic;
  signal ao_Shuffle_0_Y1_ACK   : std_logic;
  signal ao_Shuffle_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_Shuffle_0_Y1_RDY   : std_logic;

  signal aof_Shuffle_0_Y1_DATA  : std_logic_vector(23 downto 0);
  signal aof_Shuffle_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_Shuffle_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_Shuffle_0_Y2_DATA  : std_logic_vector(23 downto 0);
  signal ao_Shuffle_0_Y2_SEND  : std_logic;
  signal ao_Shuffle_0_Y2_ACK   : std_logic;
  signal ao_Shuffle_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal ao_Shuffle_0_Y2_RDY   : std_logic;

  signal aof_Shuffle_0_Y2_DATA  : std_logic_vector(23 downto 0);
  signal aof_Shuffle_0_Y2_SEND  : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y2_ACK   : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal aof_Shuffle_0_Y2_RDY   : std_logic_vector(0 downto 0);

  signal ao_Shuffle_0_Y3_DATA  : std_logic_vector(23 downto 0);
  signal ao_Shuffle_0_Y3_SEND  : std_logic;
  signal ao_Shuffle_0_Y3_ACK   : std_logic;
  signal ao_Shuffle_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal ao_Shuffle_0_Y3_RDY   : std_logic;

  signal aof_Shuffle_0_Y3_DATA  : std_logic_vector(23 downto 0);
  signal aof_Shuffle_0_Y3_SEND  : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y3_ACK   : std_logic_vector(0 downto 0);
  signal aof_Shuffle_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal aof_Shuffle_0_Y3_RDY   : std_logic_vector(0 downto 0);

  signal ao_Final_0_Y0_DATA  : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y0_SEND  : std_logic;
  signal ao_Final_0_Y0_ACK   : std_logic;
  signal ao_Final_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y0_RDY   : std_logic;

  signal aof_Final_0_Y0_DATA  : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_Final_0_Y1_DATA  : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y1_SEND  : std_logic;
  signal ao_Final_0_Y1_ACK   : std_logic;
  signal ao_Final_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y1_RDY   : std_logic;

  signal aof_Final_0_Y1_DATA  : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_Final_0_Y2_DATA  : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y2_SEND  : std_logic;
  signal ao_Final_0_Y2_ACK   : std_logic;
  signal ao_Final_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y2_RDY   : std_logic;

  signal aof_Final_0_Y2_DATA  : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y2_SEND  : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y2_ACK   : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y2_COUNT : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y2_RDY   : std_logic_vector(0 downto 0);

  signal ao_Final_0_Y3_DATA  : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y3_SEND  : std_logic;
  signal ao_Final_0_Y3_ACK   : std_logic;
  signal ao_Final_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal ao_Final_0_Y3_RDY   : std_logic;

  signal aof_Final_0_Y3_DATA  : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y3_SEND  : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y3_ACK   : std_logic_vector(0 downto 0);
  signal aof_Final_0_Y3_COUNT : std_logic_vector(15 downto 0);
  signal aof_Final_0_Y3_RDY   : std_logic_vector(0 downto 0);

  signal ao_RowSort_0_Y0_DATA  : std_logic_vector(12 downto 0);
  signal ao_RowSort_0_Y0_SEND  : std_logic;
  signal ao_RowSort_0_Y0_ACK   : std_logic;
  signal ao_RowSort_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_RowSort_0_Y0_RDY   : std_logic;

  signal aof_RowSort_0_Y0_DATA  : std_logic_vector(12 downto 0);
  signal aof_RowSort_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_RowSort_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_RowSort_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_RowSort_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_RowSort_0_Y1_DATA  : std_logic_vector(12 downto 0);
  signal ao_RowSort_0_Y1_SEND  : std_logic;
  signal ao_RowSort_0_Y1_ACK   : std_logic;
  signal ao_RowSort_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_RowSort_0_Y1_RDY   : std_logic;

  signal aof_RowSort_0_Y1_DATA  : std_logic_vector(12 downto 0);
  signal aof_RowSort_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_RowSort_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_RowSort_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_RowSort_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_FairMerge_0_Y0_DATA  : std_logic_vector(15 downto 0);
  signal ao_FairMerge_0_Y0_SEND  : std_logic;
  signal ao_FairMerge_0_Y0_ACK   : std_logic;
  signal ao_FairMerge_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_FairMerge_0_Y0_RDY   : std_logic;

  signal aof_FairMerge_0_Y0_DATA  : std_logic_vector(15 downto 0);
  signal aof_FairMerge_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_FairMerge_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_FairMerge_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_FairMerge_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_FairMerge_0_Y1_DATA  : std_logic_vector(15 downto 0);
  signal ao_FairMerge_0_Y1_SEND  : std_logic;
  signal ao_FairMerge_0_Y1_ACK   : std_logic;
  signal ao_FairMerge_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_FairMerge_0_Y1_RDY   : std_logic;

  signal aof_FairMerge_0_Y1_DATA  : std_logic_vector(15 downto 0);
  signal aof_FairMerge_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_FairMerge_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_FairMerge_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_FairMerge_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_FairMerge_0_ROWOUT_DATA  : std_logic;
  signal ao_FairMerge_0_ROWOUT_SEND  : std_logic;
  signal ao_FairMerge_0_ROWOUT_ACK   : std_logic;
  signal ao_FairMerge_0_ROWOUT_COUNT : std_logic_vector(15 downto 0);
  signal ao_FairMerge_0_ROWOUT_RDY   : std_logic;

  signal aof_FairMerge_0_ROWOUT_DATA  : std_logic;
  signal aof_FairMerge_0_ROWOUT_SEND  : std_logic_vector(1 downto 0);
  signal aof_FairMerge_0_ROWOUT_ACK   : std_logic_vector(1 downto 0);
  signal aof_FairMerge_0_ROWOUT_COUNT : std_logic_vector(15 downto 0);
  signal aof_FairMerge_0_ROWOUT_RDY   : std_logic_vector(1 downto 0);

  signal ao_Downsample_0_R2_DATA  : std_logic;
  signal ao_Downsample_0_R2_SEND  : std_logic;
  signal ao_Downsample_0_R2_ACK   : std_logic;
  signal ao_Downsample_0_R2_COUNT : std_logic_vector(15 downto 0);
  signal ao_Downsample_0_R2_RDY   : std_logic;

  signal aof_Downsample_0_R2_DATA  : std_logic;
  signal aof_Downsample_0_R2_SEND  : std_logic_vector(0 downto 0);
  signal aof_Downsample_0_R2_ACK   : std_logic_vector(0 downto 0);
  signal aof_Downsample_0_R2_COUNT : std_logic_vector(15 downto 0);
  signal aof_Downsample_0_R2_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_R0_DATA  : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R0_SEND  : std_logic;
  signal ao_Separate_0_R0_ACK   : std_logic;
  signal ao_Separate_0_R0_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R0_RDY   : std_logic;

  signal aof_Separate_0_R0_DATA  : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R0_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R0_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R0_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R0_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_R1_DATA  : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R1_SEND  : std_logic;
  signal ao_Separate_0_R1_ACK   : std_logic;
  signal ao_Separate_0_R1_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R1_RDY   : std_logic;

  signal aof_Separate_0_R1_DATA  : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R1_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R1_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R1_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R1_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_R2_DATA  : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R2_SEND  : std_logic;
  signal ao_Separate_0_R2_ACK   : std_logic;
  signal ao_Separate_0_R2_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R2_RDY   : std_logic;

  signal aof_Separate_0_R2_DATA  : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R2_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R2_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R2_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R2_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_R3_DATA  : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R3_SEND  : std_logic;
  signal ao_Separate_0_R3_ACK   : std_logic;
  signal ao_Separate_0_R3_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_R3_RDY   : std_logic;

  signal aof_Separate_0_R3_DATA  : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R3_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R3_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_R3_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_R3_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_C0_DATA  : std_logic_vector(9 downto 0);
  signal ao_Separate_0_C0_SEND  : std_logic;
  signal ao_Separate_0_C0_ACK   : std_logic;
  signal ao_Separate_0_C0_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_C0_RDY   : std_logic;

  signal aof_Separate_0_C0_DATA  : std_logic_vector(9 downto 0);
  signal aof_Separate_0_C0_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C0_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C0_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_C0_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_C1_DATA  : std_logic_vector(9 downto 0);
  signal ao_Separate_0_C1_SEND  : std_logic;
  signal ao_Separate_0_C1_ACK   : std_logic;
  signal ao_Separate_0_C1_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_C1_RDY   : std_logic;

  signal aof_Separate_0_C1_DATA  : std_logic_vector(9 downto 0);
  signal aof_Separate_0_C1_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C1_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C1_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_C1_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_C2_DATA  : std_logic_vector(9 downto 0);
  signal ao_Separate_0_C2_SEND  : std_logic;
  signal ao_Separate_0_C2_ACK   : std_logic;
  signal ao_Separate_0_C2_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_C2_RDY   : std_logic;

  signal aof_Separate_0_C2_DATA  : std_logic_vector(9 downto 0);
  signal aof_Separate_0_C2_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C2_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C2_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_C2_RDY   : std_logic_vector(0 downto 0);

  signal ao_Separate_0_C3_DATA  : std_logic_vector(9 downto 0);
  signal ao_Separate_0_C3_SEND  : std_logic;
  signal ao_Separate_0_C3_ACK   : std_logic;
  signal ao_Separate_0_C3_COUNT : std_logic_vector(15 downto 0);
  signal ao_Separate_0_C3_RDY   : std_logic;

  signal aof_Separate_0_C3_DATA  : std_logic_vector(9 downto 0);
  signal aof_Separate_0_C3_SEND  : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C3_ACK   : std_logic_vector(0 downto 0);
  signal aof_Separate_0_C3_COUNT : std_logic_vector(15 downto 0);
  signal aof_Separate_0_C3_RDY   : std_logic_vector(0 downto 0);

  signal ao_Transpose_0_Y0_DATA  : std_logic_vector(15 downto 0);
  signal ao_Transpose_0_Y0_SEND  : std_logic;
  signal ao_Transpose_0_Y0_ACK   : std_logic;
  signal ao_Transpose_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal ao_Transpose_0_Y0_RDY   : std_logic;

  signal aof_Transpose_0_Y0_DATA  : std_logic_vector(15 downto 0);
  signal aof_Transpose_0_Y0_SEND  : std_logic_vector(0 downto 0);
  signal aof_Transpose_0_Y0_ACK   : std_logic_vector(0 downto 0);
  signal aof_Transpose_0_Y0_COUNT : std_logic_vector(15 downto 0);
  signal aof_Transpose_0_Y0_RDY   : std_logic_vector(0 downto 0);

  signal ao_Transpose_0_Y1_DATA  : std_logic_vector(15 downto 0);
  signal ao_Transpose_0_Y1_SEND  : std_logic;
  signal ao_Transpose_0_Y1_ACK   : std_logic;
  signal ao_Transpose_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal ao_Transpose_0_Y1_RDY   : std_logic;

  signal aof_Transpose_0_Y1_DATA  : std_logic_vector(15 downto 0);
  signal aof_Transpose_0_Y1_SEND  : std_logic_vector(0 downto 0);
  signal aof_Transpose_0_Y1_ACK   : std_logic_vector(0 downto 0);
  signal aof_Transpose_0_Y1_COUNT : std_logic_vector(15 downto 0);
  signal aof_Transpose_0_Y1_RDY   : std_logic_vector(0 downto 0);

  signal ao_Retranspose_0_Y_DATA  : std_logic_vector(9 downto 0);
  signal ao_Retranspose_0_Y_SEND  : std_logic;
  signal ao_Retranspose_0_Y_ACK   : std_logic;
  signal ao_Retranspose_0_Y_COUNT : std_logic_vector(15 downto 0);
  signal ao_Retranspose_0_Y_RDY   : std_logic;

  signal aof_Retranspose_0_Y_DATA  : std_logic_vector(9 downto 0);
  signal aof_Retranspose_0_Y_SEND  : std_logic_vector(0 downto 0);
  signal aof_Retranspose_0_Y_ACK   : std_logic_vector(0 downto 0);
  signal aof_Retranspose_0_Y_COUNT : std_logic_vector(15 downto 0);
  signal aof_Retranspose_0_Y_RDY   : std_logic_vector(0 downto 0);

  signal ao_Clip_0_O_DATA  : std_logic_vector(8 downto 0);
  signal ao_Clip_0_O_SEND  : std_logic;
  signal ao_Clip_0_O_ACK   : std_logic;
  signal ao_Clip_0_O_COUNT : std_logic_vector(15 downto 0);
  signal ao_Clip_0_O_RDY   : std_logic;

  signal aof_Clip_0_O_DATA  : std_logic_vector(8 downto 0);
  signal aof_Clip_0_O_SEND  : std_logic_vector(0 downto 0);
  signal aof_Clip_0_O_ACK   : std_logic_vector(0 downto 0);
  signal aof_Clip_0_O_COUNT : std_logic_vector(15 downto 0);
  signal aof_Clip_0_O_RDY   : std_logic_vector(0 downto 0);

  signal ai_Scale_0_X0_DATA  : std_logic_vector(15 downto 0);
  signal ai_Scale_0_X0_SEND  : std_logic;
  signal ai_Scale_0_X0_ACK   : std_logic;
  signal ai_Scale_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Scale_0_X1_DATA  : std_logic_vector(15 downto 0);
  signal ai_Scale_0_X1_SEND  : std_logic;
  signal ai_Scale_0_X1_ACK   : std_logic;
  signal ai_Scale_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Combine_0_X0_DATA  : std_logic_vector(29 downto 0);
  signal ai_Combine_0_X0_SEND  : std_logic;
  signal ai_Combine_0_X0_ACK   : std_logic;
  signal ai_Combine_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Combine_0_X1_DATA  : std_logic_vector(29 downto 0);
  signal ai_Combine_0_X1_SEND  : std_logic;
  signal ai_Combine_0_X1_ACK   : std_logic;
  signal ai_Combine_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Combine_0_X2_DATA  : std_logic_vector(29 downto 0);
  signal ai_Combine_0_X2_SEND  : std_logic;
  signal ai_Combine_0_X2_ACK   : std_logic;
  signal ai_Combine_0_X2_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Combine_0_X3_DATA  : std_logic_vector(29 downto 0);
  signal ai_Combine_0_X3_SEND  : std_logic;
  signal ai_Combine_0_X3_ACK   : std_logic;
  signal ai_Combine_0_X3_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Combine_0_ROW_DATA  : std_logic;
  signal ai_Combine_0_ROW_SEND  : std_logic;
  signal ai_Combine_0_ROW_ACK   : std_logic;
  signal ai_Combine_0_ROW_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_ShuffleFly_0_X0_DATA  : std_logic_vector(23 downto 0);
  signal ai_ShuffleFly_0_X0_SEND  : std_logic;
  signal ai_ShuffleFly_0_X0_ACK   : std_logic;
  signal ai_ShuffleFly_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_ShuffleFly_0_X1_DATA  : std_logic_vector(23 downto 0);
  signal ai_ShuffleFly_0_X1_SEND  : std_logic;
  signal ai_ShuffleFly_0_X1_ACK   : std_logic;
  signal ai_ShuffleFly_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Shuffle_0_X0_DATA  : std_logic_vector(23 downto 0);
  signal ai_Shuffle_0_X0_SEND  : std_logic;
  signal ai_Shuffle_0_X0_ACK   : std_logic;
  signal ai_Shuffle_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Shuffle_0_X1_DATA  : std_logic_vector(23 downto 0);
  signal ai_Shuffle_0_X1_SEND  : std_logic;
  signal ai_Shuffle_0_X1_ACK   : std_logic;
  signal ai_Shuffle_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Shuffle_0_X2_DATA  : std_logic_vector(23 downto 0);
  signal ai_Shuffle_0_X2_SEND  : std_logic;
  signal ai_Shuffle_0_X2_ACK   : std_logic;
  signal ai_Shuffle_0_X2_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Shuffle_0_X3_DATA  : std_logic_vector(23 downto 0);
  signal ai_Shuffle_0_X3_SEND  : std_logic;
  signal ai_Shuffle_0_X3_ACK   : std_logic;
  signal ai_Shuffle_0_X3_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Final_0_X0_DATA  : std_logic_vector(23 downto 0);
  signal ai_Final_0_X0_SEND  : std_logic;
  signal ai_Final_0_X0_ACK   : std_logic;
  signal ai_Final_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Final_0_X1_DATA  : std_logic_vector(23 downto 0);
  signal ai_Final_0_X1_SEND  : std_logic;
  signal ai_Final_0_X1_ACK   : std_logic;
  signal ai_Final_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Final_0_X2_DATA  : std_logic_vector(23 downto 0);
  signal ai_Final_0_X2_SEND  : std_logic;
  signal ai_Final_0_X2_ACK   : std_logic;
  signal ai_Final_0_X2_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Final_0_X3_DATA  : std_logic_vector(23 downto 0);
  signal ai_Final_0_X3_SEND  : std_logic;
  signal ai_Final_0_X3_ACK   : std_logic;
  signal ai_Final_0_X3_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_RowSort_0_ROW_DATA  : std_logic_vector(12 downto 0);
  signal ai_RowSort_0_ROW_SEND  : std_logic;
  signal ai_RowSort_0_ROW_ACK   : std_logic;
  signal ai_RowSort_0_ROW_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_FairMerge_0_R0_DATA  : std_logic_vector(12 downto 0);
  signal ai_FairMerge_0_R0_SEND  : std_logic;
  signal ai_FairMerge_0_R0_ACK   : std_logic;
  signal ai_FairMerge_0_R0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_FairMerge_0_R1_DATA  : std_logic_vector(12 downto 0);
  signal ai_FairMerge_0_R1_SEND  : std_logic;
  signal ai_FairMerge_0_R1_ACK   : std_logic;
  signal ai_FairMerge_0_R1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_FairMerge_0_C0_DATA  : std_logic_vector(15 downto 0);
  signal ai_FairMerge_0_C0_SEND  : std_logic;
  signal ai_FairMerge_0_C0_ACK   : std_logic;
  signal ai_FairMerge_0_C0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_FairMerge_0_C1_DATA  : std_logic_vector(15 downto 0);
  signal ai_FairMerge_0_C1_SEND  : std_logic;
  signal ai_FairMerge_0_C1_ACK   : std_logic;
  signal ai_FairMerge_0_C1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Downsample_0_R_DATA  : std_logic;
  signal ai_Downsample_0_R_SEND  : std_logic;
  signal ai_Downsample_0_R_ACK   : std_logic;
  signal ai_Downsample_0_R_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Separate_0_X0_DATA  : std_logic_vector(15 downto 0);
  signal ai_Separate_0_X0_SEND  : std_logic;
  signal ai_Separate_0_X0_ACK   : std_logic;
  signal ai_Separate_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Separate_0_X1_DATA  : std_logic_vector(15 downto 0);
  signal ai_Separate_0_X1_SEND  : std_logic;
  signal ai_Separate_0_X1_ACK   : std_logic;
  signal ai_Separate_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Separate_0_X2_DATA  : std_logic_vector(15 downto 0);
  signal ai_Separate_0_X2_SEND  : std_logic;
  signal ai_Separate_0_X2_ACK   : std_logic;
  signal ai_Separate_0_X2_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Separate_0_X3_DATA  : std_logic_vector(15 downto 0);
  signal ai_Separate_0_X3_SEND  : std_logic;
  signal ai_Separate_0_X3_ACK   : std_logic;
  signal ai_Separate_0_X3_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Separate_0_ROW_DATA  : std_logic;
  signal ai_Separate_0_ROW_SEND  : std_logic;
  signal ai_Separate_0_ROW_ACK   : std_logic;
  signal ai_Separate_0_ROW_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Transpose_0_X0_DATA  : std_logic_vector(15 downto 0);
  signal ai_Transpose_0_X0_SEND  : std_logic;
  signal ai_Transpose_0_X0_ACK   : std_logic;
  signal ai_Transpose_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Transpose_0_X1_DATA  : std_logic_vector(15 downto 0);
  signal ai_Transpose_0_X1_SEND  : std_logic;
  signal ai_Transpose_0_X1_ACK   : std_logic;
  signal ai_Transpose_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Transpose_0_X2_DATA  : std_logic_vector(15 downto 0);
  signal ai_Transpose_0_X2_SEND  : std_logic;
  signal ai_Transpose_0_X2_ACK   : std_logic;
  signal ai_Transpose_0_X2_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Transpose_0_X3_DATA  : std_logic_vector(15 downto 0);
  signal ai_Transpose_0_X3_SEND  : std_logic;
  signal ai_Transpose_0_X3_ACK   : std_logic;
  signal ai_Transpose_0_X3_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Retranspose_0_X0_DATA  : std_logic_vector(9 downto 0);
  signal ai_Retranspose_0_X0_SEND  : std_logic;
  signal ai_Retranspose_0_X0_ACK   : std_logic;
  signal ai_Retranspose_0_X0_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Retranspose_0_X1_DATA  : std_logic_vector(9 downto 0);
  signal ai_Retranspose_0_X1_SEND  : std_logic;
  signal ai_Retranspose_0_X1_ACK   : std_logic;
  signal ai_Retranspose_0_X1_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Retranspose_0_X2_DATA  : std_logic_vector(9 downto 0);
  signal ai_Retranspose_0_X2_SEND  : std_logic;
  signal ai_Retranspose_0_X2_ACK   : std_logic;
  signal ai_Retranspose_0_X2_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Retranspose_0_X3_DATA  : std_logic_vector(9 downto 0);
  signal ai_Retranspose_0_X3_SEND  : std_logic;
  signal ai_Retranspose_0_X3_ACK   : std_logic;
  signal ai_Retranspose_0_X3_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Clip_0_I_DATA  : std_logic_vector(9 downto 0);
  signal ai_Clip_0_I_SEND  : std_logic;
  signal ai_Clip_0_I_ACK   : std_logic;
  signal ai_Clip_0_I_COUNT : std_logic_vector(15 downto 0);
  
  signal ai_Clip_0_SIGNED_DATA  : std_logic;
  signal ai_Clip_0_SIGNED_SEND  : std_logic;
  signal ai_Clip_0_SIGNED_ACK   : std_logic;
  signal ai_Clip_0_SIGNED_COUNT : std_logic_vector(15 downto 0);
  
  component Scale_0 is
  port(
    X0_DATA  : in  std_logic_vector(15 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(15 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(29 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(29 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    Y2_DATA  : out std_logic_vector(29 downto 0);
    Y2_SEND  : out std_logic;
    Y2_ACK   : in  std_logic;
    Y2_COUNT : out std_logic_vector(15 downto 0);
    Y2_RDY   : in  std_logic;

    Y3_DATA  : out std_logic_vector(29 downto 0);
    Y3_SEND  : out std_logic;
    Y3_ACK   : in  std_logic;
    Y3_COUNT : out std_logic_vector(15 downto 0);
    Y3_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Scale_0;

  component Combine_0 is
  port(
    X0_DATA  : in  std_logic_vector(29 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(29 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    X2_DATA  : in  std_logic_vector(29 downto 0);
    X2_SEND  : in  std_logic;
    X2_ACK   : out std_logic;
    X2_COUNT : in  std_logic_vector(15 downto 0);
    
    X3_DATA  : in  std_logic_vector(29 downto 0);
    X3_SEND  : in  std_logic;
    X3_ACK   : out std_logic;
    X3_COUNT : in  std_logic_vector(15 downto 0);
    
    ROW_DATA  : in  std_logic;
    ROW_SEND  : in  std_logic;
    ROW_ACK   : out std_logic;
    ROW_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(23 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(23 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Combine_0;

  component ShuffleFly_0 is
  port(
    X0_DATA  : in  std_logic_vector(23 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(23 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(23 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(23 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    Y2_DATA  : out std_logic_vector(23 downto 0);
    Y2_SEND  : out std_logic;
    Y2_ACK   : in  std_logic;
    Y2_COUNT : out std_logic_vector(15 downto 0);
    Y2_RDY   : in  std_logic;

    Y3_DATA  : out std_logic_vector(23 downto 0);
    Y3_SEND  : out std_logic;
    Y3_ACK   : in  std_logic;
    Y3_COUNT : out std_logic_vector(15 downto 0);
    Y3_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component ShuffleFly_0;

  component Shuffle_0 is
  port(
    X0_DATA  : in  std_logic_vector(23 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(23 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    X2_DATA  : in  std_logic_vector(23 downto 0);
    X2_SEND  : in  std_logic;
    X2_ACK   : out std_logic;
    X2_COUNT : in  std_logic_vector(15 downto 0);
    
    X3_DATA  : in  std_logic_vector(23 downto 0);
    X3_SEND  : in  std_logic;
    X3_ACK   : out std_logic;
    X3_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(23 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(23 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    Y2_DATA  : out std_logic_vector(23 downto 0);
    Y2_SEND  : out std_logic;
    Y2_ACK   : in  std_logic;
    Y2_COUNT : out std_logic_vector(15 downto 0);
    Y2_RDY   : in  std_logic;

    Y3_DATA  : out std_logic_vector(23 downto 0);
    Y3_SEND  : out std_logic;
    Y3_ACK   : in  std_logic;
    Y3_COUNT : out std_logic_vector(15 downto 0);
    Y3_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Shuffle_0;

  component Final_0 is
  port(
    X0_DATA  : in  std_logic_vector(23 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(23 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    X2_DATA  : in  std_logic_vector(23 downto 0);
    X2_SEND  : in  std_logic;
    X2_ACK   : out std_logic;
    X2_COUNT : in  std_logic_vector(15 downto 0);
    
    X3_DATA  : in  std_logic_vector(23 downto 0);
    X3_SEND  : in  std_logic;
    X3_ACK   : out std_logic;
    X3_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(15 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(15 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    Y2_DATA  : out std_logic_vector(15 downto 0);
    Y2_SEND  : out std_logic;
    Y2_ACK   : in  std_logic;
    Y2_COUNT : out std_logic_vector(15 downto 0);
    Y2_RDY   : in  std_logic;

    Y3_DATA  : out std_logic_vector(15 downto 0);
    Y3_SEND  : out std_logic;
    Y3_ACK   : in  std_logic;
    Y3_COUNT : out std_logic_vector(15 downto 0);
    Y3_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Final_0;

  component RowSort_0 is
  port(
    ROW_DATA  : in  std_logic_vector(12 downto 0);
    ROW_SEND  : in  std_logic;
    ROW_ACK   : out std_logic;
    ROW_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(12 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(12 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component RowSort_0;

  component FairMerge_0 is
  port(
    R0_DATA  : in  std_logic_vector(12 downto 0);
    R0_SEND  : in  std_logic;
    R0_ACK   : out std_logic;
    R0_COUNT : in  std_logic_vector(15 downto 0);
    
    R1_DATA  : in  std_logic_vector(12 downto 0);
    R1_SEND  : in  std_logic;
    R1_ACK   : out std_logic;
    R1_COUNT : in  std_logic_vector(15 downto 0);
    
    C0_DATA  : in  std_logic_vector(15 downto 0);
    C0_SEND  : in  std_logic;
    C0_ACK   : out std_logic;
    C0_COUNT : in  std_logic_vector(15 downto 0);
    
    C1_DATA  : in  std_logic_vector(15 downto 0);
    C1_SEND  : in  std_logic;
    C1_ACK   : out std_logic;
    C1_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(15 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(15 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    ROWOUT_DATA  : out std_logic;
    ROWOUT_SEND  : out std_logic;
    ROWOUT_ACK   : in  std_logic;
    ROWOUT_COUNT : out std_logic_vector(15 downto 0);
    ROWOUT_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component FairMerge_0;

  component Downsample_0 is
  port(
    R_DATA  : in  std_logic;
    R_SEND  : in  std_logic;
    R_ACK   : out std_logic;
    R_COUNT : in  std_logic_vector(15 downto 0);
    
    R2_DATA  : out std_logic;
    R2_SEND  : out std_logic;
    R2_ACK   : in  std_logic;
    R2_COUNT : out std_logic_vector(15 downto 0);
    R2_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Downsample_0;

  component Separate_0 is
  port(
    X0_DATA  : in  std_logic_vector(15 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(15 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    X2_DATA  : in  std_logic_vector(15 downto 0);
    X2_SEND  : in  std_logic;
    X2_ACK   : out std_logic;
    X2_COUNT : in  std_logic_vector(15 downto 0);
    
    X3_DATA  : in  std_logic_vector(15 downto 0);
    X3_SEND  : in  std_logic;
    X3_ACK   : out std_logic;
    X3_COUNT : in  std_logic_vector(15 downto 0);
    
    ROW_DATA  : in  std_logic;
    ROW_SEND  : in  std_logic;
    ROW_ACK   : out std_logic;
    ROW_COUNT : in  std_logic_vector(15 downto 0);
    
    R0_DATA  : out std_logic_vector(15 downto 0);
    R0_SEND  : out std_logic;
    R0_ACK   : in  std_logic;
    R0_COUNT : out std_logic_vector(15 downto 0);
    R0_RDY   : in  std_logic;

    R1_DATA  : out std_logic_vector(15 downto 0);
    R1_SEND  : out std_logic;
    R1_ACK   : in  std_logic;
    R1_COUNT : out std_logic_vector(15 downto 0);
    R1_RDY   : in  std_logic;

    R2_DATA  : out std_logic_vector(15 downto 0);
    R2_SEND  : out std_logic;
    R2_ACK   : in  std_logic;
    R2_COUNT : out std_logic_vector(15 downto 0);
    R2_RDY   : in  std_logic;

    R3_DATA  : out std_logic_vector(15 downto 0);
    R3_SEND  : out std_logic;
    R3_ACK   : in  std_logic;
    R3_COUNT : out std_logic_vector(15 downto 0);
    R3_RDY   : in  std_logic;

    C0_DATA  : out std_logic_vector(9 downto 0);
    C0_SEND  : out std_logic;
    C0_ACK   : in  std_logic;
    C0_COUNT : out std_logic_vector(15 downto 0);
    C0_RDY   : in  std_logic;

    C1_DATA  : out std_logic_vector(9 downto 0);
    C1_SEND  : out std_logic;
    C1_ACK   : in  std_logic;
    C1_COUNT : out std_logic_vector(15 downto 0);
    C1_RDY   : in  std_logic;

    C2_DATA  : out std_logic_vector(9 downto 0);
    C2_SEND  : out std_logic;
    C2_ACK   : in  std_logic;
    C2_COUNT : out std_logic_vector(15 downto 0);
    C2_RDY   : in  std_logic;

    C3_DATA  : out std_logic_vector(9 downto 0);
    C3_SEND  : out std_logic;
    C3_ACK   : in  std_logic;
    C3_COUNT : out std_logic_vector(15 downto 0);
    C3_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Separate_0;

  component Transpose_0 is
  port(
    X0_DATA  : in  std_logic_vector(15 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(15 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    X2_DATA  : in  std_logic_vector(15 downto 0);
    X2_SEND  : in  std_logic;
    X2_ACK   : out std_logic;
    X2_COUNT : in  std_logic_vector(15 downto 0);
    
    X3_DATA  : in  std_logic_vector(15 downto 0);
    X3_SEND  : in  std_logic;
    X3_ACK   : out std_logic;
    X3_COUNT : in  std_logic_vector(15 downto 0);
    
    Y0_DATA  : out std_logic_vector(15 downto 0);
    Y0_SEND  : out std_logic;
    Y0_ACK   : in  std_logic;
    Y0_COUNT : out std_logic_vector(15 downto 0);
    Y0_RDY   : in  std_logic;

    Y1_DATA  : out std_logic_vector(15 downto 0);
    Y1_SEND  : out std_logic;
    Y1_ACK   : in  std_logic;
    Y1_COUNT : out std_logic_vector(15 downto 0);
    Y1_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Transpose_0;

  component Retranspose_0 is
  port(
    X0_DATA  : in  std_logic_vector(9 downto 0);
    X0_SEND  : in  std_logic;
    X0_ACK   : out std_logic;
    X0_COUNT : in  std_logic_vector(15 downto 0);
    
    X1_DATA  : in  std_logic_vector(9 downto 0);
    X1_SEND  : in  std_logic;
    X1_ACK   : out std_logic;
    X1_COUNT : in  std_logic_vector(15 downto 0);
    
    X2_DATA  : in  std_logic_vector(9 downto 0);
    X2_SEND  : in  std_logic;
    X2_ACK   : out std_logic;
    X2_COUNT : in  std_logic_vector(15 downto 0);
    
    X3_DATA  : in  std_logic_vector(9 downto 0);
    X3_SEND  : in  std_logic;
    X3_ACK   : out std_logic;
    X3_COUNT : in  std_logic_vector(15 downto 0);
    
    Y_DATA  : out std_logic_vector(9 downto 0);
    Y_SEND  : out std_logic;
    Y_ACK   : in  std_logic;
    Y_COUNT : out std_logic_vector(15 downto 0);
    Y_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Retranspose_0;

  component Clip_0 is
  port(
    I_DATA  : in  std_logic_vector(9 downto 0);
    I_SEND  : in  std_logic;
    I_ACK   : out std_logic;
    I_COUNT : in  std_logic_vector(15 downto 0);
    
    SIGNED_DATA  : in  std_logic;
    SIGNED_SEND  : in  std_logic;
    SIGNED_ACK   : out std_logic;
    SIGNED_COUNT : in  std_logic_vector(15 downto 0);
    
    O_DATA  : out std_logic_vector(8 downto 0);
    O_SEND  : out std_logic;
    O_ACK   : in  std_logic;
    O_COUNT : out std_logic_vector(15 downto 0);
    O_RDY   : in  std_logic;

    CLK: in std_logic;
    RESET: in std_logic   );
  end component Clip_0;

begin

  rcon: entity SystemBuilder.resetController( behavioral )
  generic map( count => 1 )
  port map( clocks => clocks, reset_in => RESET, resets => resets );

  clocks(0) <= CLK;

  i_Scale_0 : component Scale_0
  port map (
    X0_DATA  => ai_Scale_0_X0_DATA ,
    X0_SEND  => ai_Scale_0_X0_SEND ,
    X0_ACK   => ai_Scale_0_X0_ACK  ,
    X0_COUNT => ai_Scale_0_X0_COUNT,

    X1_DATA  => ai_Scale_0_X1_DATA ,
    X1_SEND  => ai_Scale_0_X1_SEND ,
    X1_ACK   => ai_Scale_0_X1_ACK  ,
    X1_COUNT => ai_Scale_0_X1_COUNT,

    Y0_DATA  => ao_Scale_0_Y0_DATA ,
    Y0_SEND  => ao_Scale_0_Y0_SEND ,
    Y0_ACK   => ao_Scale_0_Y0_ACK  ,
    Y0_COUNT => ao_Scale_0_Y0_COUNT,
    Y0_RDY   => ao_Scale_0_Y0_RDY  ,

    Y1_DATA  => ao_Scale_0_Y1_DATA ,
    Y1_SEND  => ao_Scale_0_Y1_SEND ,
    Y1_ACK   => ao_Scale_0_Y1_ACK  ,
    Y1_COUNT => ao_Scale_0_Y1_COUNT,
    Y1_RDY   => ao_Scale_0_Y1_RDY  ,

    Y2_DATA  => ao_Scale_0_Y2_DATA ,
    Y2_SEND  => ao_Scale_0_Y2_SEND ,
    Y2_ACK   => ao_Scale_0_Y2_ACK  ,
    Y2_COUNT => ao_Scale_0_Y2_COUNT,
    Y2_RDY   => ao_Scale_0_Y2_RDY  ,

    Y3_DATA  => ao_Scale_0_Y3_DATA ,
    Y3_SEND  => ao_Scale_0_Y3_SEND ,
    Y3_ACK   => ao_Scale_0_Y3_ACK  ,
    Y3_COUNT => ao_Scale_0_Y3_COUNT,
    Y3_RDY   => ao_Scale_0_Y3_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Scale_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 30 )
  port map (
    In_DATA  => ao_Scale_0_Y0_DATA,
    In_SEND  => ao_Scale_0_Y0_SEND,
    In_ACK   => ao_Scale_0_Y0_ACK,
    In_COUNT => ao_Scale_0_Y0_COUNT,
    In_RDY   => ao_Scale_0_Y0_RDY,

    Out_DATA  => aof_Scale_0_Y0_DATA,
    Out_SEND  => aof_Scale_0_Y0_SEND,
    Out_ACK   => aof_Scale_0_Y0_ACK,
    Out_COUNT => aof_Scale_0_Y0_COUNT,
    Out_RDY   => aof_Scale_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Scale_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 30 )
  port map (
    In_DATA  => ao_Scale_0_Y1_DATA,
    In_SEND  => ao_Scale_0_Y1_SEND,
    In_ACK   => ao_Scale_0_Y1_ACK,
    In_COUNT => ao_Scale_0_Y1_COUNT,
    In_RDY   => ao_Scale_0_Y1_RDY,

    Out_DATA  => aof_Scale_0_Y1_DATA,
    Out_SEND  => aof_Scale_0_Y1_SEND,
    Out_ACK   => aof_Scale_0_Y1_ACK,
    Out_COUNT => aof_Scale_0_Y1_COUNT,
    Out_RDY   => aof_Scale_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Scale_0_Y2 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 30 )
  port map (
    In_DATA  => ao_Scale_0_Y2_DATA,
    In_SEND  => ao_Scale_0_Y2_SEND,
    In_ACK   => ao_Scale_0_Y2_ACK,
    In_COUNT => ao_Scale_0_Y2_COUNT,
    In_RDY   => ao_Scale_0_Y2_RDY,

    Out_DATA  => aof_Scale_0_Y2_DATA,
    Out_SEND  => aof_Scale_0_Y2_SEND,
    Out_ACK   => aof_Scale_0_Y2_ACK,
    Out_COUNT => aof_Scale_0_Y2_COUNT,
    Out_RDY   => aof_Scale_0_Y2_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Scale_0_Y3 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 30 )
  port map (
    In_DATA  => ao_Scale_0_Y3_DATA,
    In_SEND  => ao_Scale_0_Y3_SEND,
    In_ACK   => ao_Scale_0_Y3_ACK,
    In_COUNT => ao_Scale_0_Y3_COUNT,
    In_RDY   => ao_Scale_0_Y3_RDY,

    Out_DATA  => aof_Scale_0_Y3_DATA,
    Out_SEND  => aof_Scale_0_Y3_SEND,
    Out_ACK   => aof_Scale_0_Y3_ACK,
    Out_COUNT => aof_Scale_0_Y3_COUNT,
    Out_RDY   => aof_Scale_0_Y3_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Scale_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Scale_0_X0_DATA,
    Out_SEND  => ai_Scale_0_X0_SEND,
    Out_ACK   => ai_Scale_0_X0_ACK,
    Out_COUNT => ai_Scale_0_X0_COUNT,

    In_DATA  => aof_FairMerge_0_Y0_DATA,
    In_SEND  => aof_FairMerge_0_Y0_SEND( 0 ),
    In_ACK   => aof_FairMerge_0_Y0_ACK( 0 ),
    In_COUNT => aof_FairMerge_0_Y0_COUNT,
    In_RDY   => aof_FairMerge_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Scale_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Scale_0_X1_DATA,
    Out_SEND  => ai_Scale_0_X1_SEND,
    Out_ACK   => ai_Scale_0_X1_ACK,
    Out_COUNT => ai_Scale_0_X1_COUNT,

    In_DATA  => aof_FairMerge_0_Y1_DATA,
    In_SEND  => aof_FairMerge_0_Y1_SEND( 0 ),
    In_ACK   => aof_FairMerge_0_Y1_ACK( 0 ),
    In_COUNT => aof_FairMerge_0_Y1_COUNT,
    In_RDY   => aof_FairMerge_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Combine_0 : component Combine_0
  port map (
    X0_DATA  => ai_Combine_0_X0_DATA ,
    X0_SEND  => ai_Combine_0_X0_SEND ,
    X0_ACK   => ai_Combine_0_X0_ACK  ,
    X0_COUNT => ai_Combine_0_X0_COUNT,

    X1_DATA  => ai_Combine_0_X1_DATA ,
    X1_SEND  => ai_Combine_0_X1_SEND ,
    X1_ACK   => ai_Combine_0_X1_ACK  ,
    X1_COUNT => ai_Combine_0_X1_COUNT,

    X2_DATA  => ai_Combine_0_X2_DATA ,
    X2_SEND  => ai_Combine_0_X2_SEND ,
    X2_ACK   => ai_Combine_0_X2_ACK  ,
    X2_COUNT => ai_Combine_0_X2_COUNT,

    X3_DATA  => ai_Combine_0_X3_DATA ,
    X3_SEND  => ai_Combine_0_X3_SEND ,
    X3_ACK   => ai_Combine_0_X3_ACK  ,
    X3_COUNT => ai_Combine_0_X3_COUNT,

    ROW_DATA  => ai_Combine_0_ROW_DATA ,
    ROW_SEND  => ai_Combine_0_ROW_SEND ,
    ROW_ACK   => ai_Combine_0_ROW_ACK  ,
    ROW_COUNT => ai_Combine_0_ROW_COUNT,

    Y0_DATA  => ao_Combine_0_Y0_DATA ,
    Y0_SEND  => ao_Combine_0_Y0_SEND ,
    Y0_ACK   => ao_Combine_0_Y0_ACK  ,
    Y0_COUNT => ao_Combine_0_Y0_COUNT,
    Y0_RDY   => ao_Combine_0_Y0_RDY  ,

    Y1_DATA  => ao_Combine_0_Y1_DATA ,
    Y1_SEND  => ao_Combine_0_Y1_SEND ,
    Y1_ACK   => ao_Combine_0_Y1_ACK  ,
    Y1_COUNT => ao_Combine_0_Y1_COUNT,
    Y1_RDY   => ao_Combine_0_Y1_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Combine_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_Combine_0_Y0_DATA,
    In_SEND  => ao_Combine_0_Y0_SEND,
    In_ACK   => ao_Combine_0_Y0_ACK,
    In_COUNT => ao_Combine_0_Y0_COUNT,
    In_RDY   => ao_Combine_0_Y0_RDY,

    Out_DATA  => aof_Combine_0_Y0_DATA,
    Out_SEND  => aof_Combine_0_Y0_SEND,
    Out_ACK   => aof_Combine_0_Y0_ACK,
    Out_COUNT => aof_Combine_0_Y0_COUNT,
    Out_RDY   => aof_Combine_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Combine_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_Combine_0_Y1_DATA,
    In_SEND  => ao_Combine_0_Y1_SEND,
    In_ACK   => ao_Combine_0_Y1_ACK,
    In_COUNT => ao_Combine_0_Y1_COUNT,
    In_RDY   => ao_Combine_0_Y1_RDY,

    Out_DATA  => aof_Combine_0_Y1_DATA,
    Out_SEND  => aof_Combine_0_Y1_SEND,
    Out_ACK   => aof_Combine_0_Y1_ACK,
    Out_COUNT => aof_Combine_0_Y1_COUNT,
    Out_RDY   => aof_Combine_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Combine_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 30 )
  port map (
    Out_DATA  => ai_Combine_0_X0_DATA,
    Out_SEND  => ai_Combine_0_X0_SEND,
    Out_ACK   => ai_Combine_0_X0_ACK,
    Out_COUNT => ai_Combine_0_X0_COUNT,

    In_DATA  => aof_Scale_0_Y0_DATA,
    In_SEND  => aof_Scale_0_Y0_SEND( 0 ),
    In_ACK   => aof_Scale_0_Y0_ACK( 0 ),
    In_COUNT => aof_Scale_0_Y0_COUNT,
    In_RDY   => aof_Scale_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Combine_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 30 )
  port map (
    Out_DATA  => ai_Combine_0_X1_DATA,
    Out_SEND  => ai_Combine_0_X1_SEND,
    Out_ACK   => ai_Combine_0_X1_ACK,
    Out_COUNT => ai_Combine_0_X1_COUNT,

    In_DATA  => aof_Scale_0_Y1_DATA,
    In_SEND  => aof_Scale_0_Y1_SEND( 0 ),
    In_ACK   => aof_Scale_0_Y1_ACK( 0 ),
    In_COUNT => aof_Scale_0_Y1_COUNT,
    In_RDY   => aof_Scale_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Combine_0_X2 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 30 )
  port map (
    Out_DATA  => ai_Combine_0_X2_DATA,
    Out_SEND  => ai_Combine_0_X2_SEND,
    Out_ACK   => ai_Combine_0_X2_ACK,
    Out_COUNT => ai_Combine_0_X2_COUNT,

    In_DATA  => aof_Scale_0_Y2_DATA,
    In_SEND  => aof_Scale_0_Y2_SEND( 0 ),
    In_ACK   => aof_Scale_0_Y2_ACK( 0 ),
    In_COUNT => aof_Scale_0_Y2_COUNT,
    In_RDY   => aof_Scale_0_Y2_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Combine_0_X3 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 30 )
  port map (
    Out_DATA  => ai_Combine_0_X3_DATA,
    Out_SEND  => ai_Combine_0_X3_SEND,
    Out_ACK   => ai_Combine_0_X3_ACK,
    Out_COUNT => ai_Combine_0_X3_COUNT,

    In_DATA  => aof_Scale_0_Y3_DATA,
    In_SEND  => aof_Scale_0_Y3_SEND( 0 ),
    In_ACK   => aof_Scale_0_Y3_ACK( 0 ),
    In_COUNT => aof_Scale_0_Y3_COUNT,
    In_RDY   => aof_Scale_0_Y3_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Combine_0_ROW : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 1 )
  port map (
    Out_DATA(0)  => ai_Combine_0_ROW_DATA,
    Out_SEND  => ai_Combine_0_ROW_SEND,
    Out_ACK   => ai_Combine_0_ROW_ACK,
    Out_COUNT => ai_Combine_0_ROW_COUNT,

    In_DATA(0)  => aof_FairMerge_0_ROWOUT_DATA,
    In_SEND  => aof_FairMerge_0_ROWOUT_SEND( 1 ),
    In_ACK   => aof_FairMerge_0_ROWOUT_ACK( 1 ),
    In_COUNT => aof_FairMerge_0_ROWOUT_COUNT,
    In_RDY   => aof_FairMerge_0_ROWOUT_RDY( 1 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_ShuffleFly_0 : component ShuffleFly_0
  port map (
    X0_DATA  => ai_ShuffleFly_0_X0_DATA ,
    X0_SEND  => ai_ShuffleFly_0_X0_SEND ,
    X0_ACK   => ai_ShuffleFly_0_X0_ACK  ,
    X0_COUNT => ai_ShuffleFly_0_X0_COUNT,

    X1_DATA  => ai_ShuffleFly_0_X1_DATA ,
    X1_SEND  => ai_ShuffleFly_0_X1_SEND ,
    X1_ACK   => ai_ShuffleFly_0_X1_ACK  ,
    X1_COUNT => ai_ShuffleFly_0_X1_COUNT,

    Y0_DATA  => ao_ShuffleFly_0_Y0_DATA ,
    Y0_SEND  => ao_ShuffleFly_0_Y0_SEND ,
    Y0_ACK   => ao_ShuffleFly_0_Y0_ACK  ,
    Y0_COUNT => ao_ShuffleFly_0_Y0_COUNT,
    Y0_RDY   => ao_ShuffleFly_0_Y0_RDY  ,

    Y1_DATA  => ao_ShuffleFly_0_Y1_DATA ,
    Y1_SEND  => ao_ShuffleFly_0_Y1_SEND ,
    Y1_ACK   => ao_ShuffleFly_0_Y1_ACK  ,
    Y1_COUNT => ao_ShuffleFly_0_Y1_COUNT,
    Y1_RDY   => ao_ShuffleFly_0_Y1_RDY  ,

    Y2_DATA  => ao_ShuffleFly_0_Y2_DATA ,
    Y2_SEND  => ao_ShuffleFly_0_Y2_SEND ,
    Y2_ACK   => ao_ShuffleFly_0_Y2_ACK  ,
    Y2_COUNT => ao_ShuffleFly_0_Y2_COUNT,
    Y2_RDY   => ao_ShuffleFly_0_Y2_RDY  ,

    Y3_DATA  => ao_ShuffleFly_0_Y3_DATA ,
    Y3_SEND  => ao_ShuffleFly_0_Y3_SEND ,
    Y3_ACK   => ao_ShuffleFly_0_Y3_ACK  ,
    Y3_COUNT => ao_ShuffleFly_0_Y3_COUNT,
    Y3_RDY   => ao_ShuffleFly_0_Y3_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_ShuffleFly_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_ShuffleFly_0_Y0_DATA,
    In_SEND  => ao_ShuffleFly_0_Y0_SEND,
    In_ACK   => ao_ShuffleFly_0_Y0_ACK,
    In_COUNT => ao_ShuffleFly_0_Y0_COUNT,
    In_RDY   => ao_ShuffleFly_0_Y0_RDY,

    Out_DATA  => aof_ShuffleFly_0_Y0_DATA,
    Out_SEND  => aof_ShuffleFly_0_Y0_SEND,
    Out_ACK   => aof_ShuffleFly_0_Y0_ACK,
    Out_COUNT => aof_ShuffleFly_0_Y0_COUNT,
    Out_RDY   => aof_ShuffleFly_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_ShuffleFly_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_ShuffleFly_0_Y1_DATA,
    In_SEND  => ao_ShuffleFly_0_Y1_SEND,
    In_ACK   => ao_ShuffleFly_0_Y1_ACK,
    In_COUNT => ao_ShuffleFly_0_Y1_COUNT,
    In_RDY   => ao_ShuffleFly_0_Y1_RDY,

    Out_DATA  => aof_ShuffleFly_0_Y1_DATA,
    Out_SEND  => aof_ShuffleFly_0_Y1_SEND,
    Out_ACK   => aof_ShuffleFly_0_Y1_ACK,
    Out_COUNT => aof_ShuffleFly_0_Y1_COUNT,
    Out_RDY   => aof_ShuffleFly_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_ShuffleFly_0_Y2 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_ShuffleFly_0_Y2_DATA,
    In_SEND  => ao_ShuffleFly_0_Y2_SEND,
    In_ACK   => ao_ShuffleFly_0_Y2_ACK,
    In_COUNT => ao_ShuffleFly_0_Y2_COUNT,
    In_RDY   => ao_ShuffleFly_0_Y2_RDY,

    Out_DATA  => aof_ShuffleFly_0_Y2_DATA,
    Out_SEND  => aof_ShuffleFly_0_Y2_SEND,
    Out_ACK   => aof_ShuffleFly_0_Y2_ACK,
    Out_COUNT => aof_ShuffleFly_0_Y2_COUNT,
    Out_RDY   => aof_ShuffleFly_0_Y2_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_ShuffleFly_0_Y3 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_ShuffleFly_0_Y3_DATA,
    In_SEND  => ao_ShuffleFly_0_Y3_SEND,
    In_ACK   => ao_ShuffleFly_0_Y3_ACK,
    In_COUNT => ao_ShuffleFly_0_Y3_COUNT,
    In_RDY   => ao_ShuffleFly_0_Y3_RDY,

    Out_DATA  => aof_ShuffleFly_0_Y3_DATA,
    Out_SEND  => aof_ShuffleFly_0_Y3_SEND,
    Out_ACK   => aof_ShuffleFly_0_Y3_ACK,
    Out_COUNT => aof_ShuffleFly_0_Y3_COUNT,
    Out_RDY   => aof_ShuffleFly_0_Y3_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_ShuffleFly_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_ShuffleFly_0_X0_DATA,
    Out_SEND  => ai_ShuffleFly_0_X0_SEND,
    Out_ACK   => ai_ShuffleFly_0_X0_ACK,
    Out_COUNT => ai_ShuffleFly_0_X0_COUNT,

    In_DATA  => aof_Combine_0_Y0_DATA,
    In_SEND  => aof_Combine_0_Y0_SEND( 0 ),
    In_ACK   => aof_Combine_0_Y0_ACK( 0 ),
    In_COUNT => aof_Combine_0_Y0_COUNT,
    In_RDY   => aof_Combine_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_ShuffleFly_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_ShuffleFly_0_X1_DATA,
    Out_SEND  => ai_ShuffleFly_0_X1_SEND,
    Out_ACK   => ai_ShuffleFly_0_X1_ACK,
    Out_COUNT => ai_ShuffleFly_0_X1_COUNT,

    In_DATA  => aof_Combine_0_Y1_DATA,
    In_SEND  => aof_Combine_0_Y1_SEND( 0 ),
    In_ACK   => aof_Combine_0_Y1_ACK( 0 ),
    In_COUNT => aof_Combine_0_Y1_COUNT,
    In_RDY   => aof_Combine_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Shuffle_0 : component Shuffle_0
  port map (
    X0_DATA  => ai_Shuffle_0_X0_DATA ,
    X0_SEND  => ai_Shuffle_0_X0_SEND ,
    X0_ACK   => ai_Shuffle_0_X0_ACK  ,
    X0_COUNT => ai_Shuffle_0_X0_COUNT,

    X1_DATA  => ai_Shuffle_0_X1_DATA ,
    X1_SEND  => ai_Shuffle_0_X1_SEND ,
    X1_ACK   => ai_Shuffle_0_X1_ACK  ,
    X1_COUNT => ai_Shuffle_0_X1_COUNT,

    X2_DATA  => ai_Shuffle_0_X2_DATA ,
    X2_SEND  => ai_Shuffle_0_X2_SEND ,
    X2_ACK   => ai_Shuffle_0_X2_ACK  ,
    X2_COUNT => ai_Shuffle_0_X2_COUNT,

    X3_DATA  => ai_Shuffle_0_X3_DATA ,
    X3_SEND  => ai_Shuffle_0_X3_SEND ,
    X3_ACK   => ai_Shuffle_0_X3_ACK  ,
    X3_COUNT => ai_Shuffle_0_X3_COUNT,

    Y0_DATA  => ao_Shuffle_0_Y0_DATA ,
    Y0_SEND  => ao_Shuffle_0_Y0_SEND ,
    Y0_ACK   => ao_Shuffle_0_Y0_ACK  ,
    Y0_COUNT => ao_Shuffle_0_Y0_COUNT,
    Y0_RDY   => ao_Shuffle_0_Y0_RDY  ,

    Y1_DATA  => ao_Shuffle_0_Y1_DATA ,
    Y1_SEND  => ao_Shuffle_0_Y1_SEND ,
    Y1_ACK   => ao_Shuffle_0_Y1_ACK  ,
    Y1_COUNT => ao_Shuffle_0_Y1_COUNT,
    Y1_RDY   => ao_Shuffle_0_Y1_RDY  ,

    Y2_DATA  => ao_Shuffle_0_Y2_DATA ,
    Y2_SEND  => ao_Shuffle_0_Y2_SEND ,
    Y2_ACK   => ao_Shuffle_0_Y2_ACK  ,
    Y2_COUNT => ao_Shuffle_0_Y2_COUNT,
    Y2_RDY   => ao_Shuffle_0_Y2_RDY  ,

    Y3_DATA  => ao_Shuffle_0_Y3_DATA ,
    Y3_SEND  => ao_Shuffle_0_Y3_SEND ,
    Y3_ACK   => ao_Shuffle_0_Y3_ACK  ,
    Y3_COUNT => ao_Shuffle_0_Y3_COUNT,
    Y3_RDY   => ao_Shuffle_0_Y3_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Shuffle_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_Shuffle_0_Y0_DATA,
    In_SEND  => ao_Shuffle_0_Y0_SEND,
    In_ACK   => ao_Shuffle_0_Y0_ACK,
    In_COUNT => ao_Shuffle_0_Y0_COUNT,
    In_RDY   => ao_Shuffle_0_Y0_RDY,

    Out_DATA  => aof_Shuffle_0_Y0_DATA,
    Out_SEND  => aof_Shuffle_0_Y0_SEND,
    Out_ACK   => aof_Shuffle_0_Y0_ACK,
    Out_COUNT => aof_Shuffle_0_Y0_COUNT,
    Out_RDY   => aof_Shuffle_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Shuffle_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_Shuffle_0_Y1_DATA,
    In_SEND  => ao_Shuffle_0_Y1_SEND,
    In_ACK   => ao_Shuffle_0_Y1_ACK,
    In_COUNT => ao_Shuffle_0_Y1_COUNT,
    In_RDY   => ao_Shuffle_0_Y1_RDY,

    Out_DATA  => aof_Shuffle_0_Y1_DATA,
    Out_SEND  => aof_Shuffle_0_Y1_SEND,
    Out_ACK   => aof_Shuffle_0_Y1_ACK,
    Out_COUNT => aof_Shuffle_0_Y1_COUNT,
    Out_RDY   => aof_Shuffle_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Shuffle_0_Y2 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_Shuffle_0_Y2_DATA,
    In_SEND  => ao_Shuffle_0_Y2_SEND,
    In_ACK   => ao_Shuffle_0_Y2_ACK,
    In_COUNT => ao_Shuffle_0_Y2_COUNT,
    In_RDY   => ao_Shuffle_0_Y2_RDY,

    Out_DATA  => aof_Shuffle_0_Y2_DATA,
    Out_SEND  => aof_Shuffle_0_Y2_SEND,
    Out_ACK   => aof_Shuffle_0_Y2_ACK,
    Out_COUNT => aof_Shuffle_0_Y2_COUNT,
    Out_RDY   => aof_Shuffle_0_Y2_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Shuffle_0_Y3 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 24 )
  port map (
    In_DATA  => ao_Shuffle_0_Y3_DATA,
    In_SEND  => ao_Shuffle_0_Y3_SEND,
    In_ACK   => ao_Shuffle_0_Y3_ACK,
    In_COUNT => ao_Shuffle_0_Y3_COUNT,
    In_RDY   => ao_Shuffle_0_Y3_RDY,

    Out_DATA  => aof_Shuffle_0_Y3_DATA,
    Out_SEND  => aof_Shuffle_0_Y3_SEND,
    Out_ACK   => aof_Shuffle_0_Y3_ACK,
    Out_COUNT => aof_Shuffle_0_Y3_COUNT,
    Out_RDY   => aof_Shuffle_0_Y3_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Shuffle_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Shuffle_0_X0_DATA,
    Out_SEND  => ai_Shuffle_0_X0_SEND,
    Out_ACK   => ai_Shuffle_0_X0_ACK,
    Out_COUNT => ai_Shuffle_0_X0_COUNT,

    In_DATA  => aof_ShuffleFly_0_Y0_DATA,
    In_SEND  => aof_ShuffleFly_0_Y0_SEND( 0 ),
    In_ACK   => aof_ShuffleFly_0_Y0_ACK( 0 ),
    In_COUNT => aof_ShuffleFly_0_Y0_COUNT,
    In_RDY   => aof_ShuffleFly_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Shuffle_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Shuffle_0_X1_DATA,
    Out_SEND  => ai_Shuffle_0_X1_SEND,
    Out_ACK   => ai_Shuffle_0_X1_ACK,
    Out_COUNT => ai_Shuffle_0_X1_COUNT,

    In_DATA  => aof_ShuffleFly_0_Y1_DATA,
    In_SEND  => aof_ShuffleFly_0_Y1_SEND( 0 ),
    In_ACK   => aof_ShuffleFly_0_Y1_ACK( 0 ),
    In_COUNT => aof_ShuffleFly_0_Y1_COUNT,
    In_RDY   => aof_ShuffleFly_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Shuffle_0_X2 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Shuffle_0_X2_DATA,
    Out_SEND  => ai_Shuffle_0_X2_SEND,
    Out_ACK   => ai_Shuffle_0_X2_ACK,
    Out_COUNT => ai_Shuffle_0_X2_COUNT,

    In_DATA  => aof_ShuffleFly_0_Y2_DATA,
    In_SEND  => aof_ShuffleFly_0_Y2_SEND( 0 ),
    In_ACK   => aof_ShuffleFly_0_Y2_ACK( 0 ),
    In_COUNT => aof_ShuffleFly_0_Y2_COUNT,
    In_RDY   => aof_ShuffleFly_0_Y2_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Shuffle_0_X3 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Shuffle_0_X3_DATA,
    Out_SEND  => ai_Shuffle_0_X3_SEND,
    Out_ACK   => ai_Shuffle_0_X3_ACK,
    Out_COUNT => ai_Shuffle_0_X3_COUNT,

    In_DATA  => aof_ShuffleFly_0_Y3_DATA,
    In_SEND  => aof_ShuffleFly_0_Y3_SEND( 0 ),
    In_ACK   => aof_ShuffleFly_0_Y3_ACK( 0 ),
    In_COUNT => aof_ShuffleFly_0_Y3_COUNT,
    In_RDY   => aof_ShuffleFly_0_Y3_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Final_0 : component Final_0
  port map (
    X0_DATA  => ai_Final_0_X0_DATA ,
    X0_SEND  => ai_Final_0_X0_SEND ,
    X0_ACK   => ai_Final_0_X0_ACK  ,
    X0_COUNT => ai_Final_0_X0_COUNT,

    X1_DATA  => ai_Final_0_X1_DATA ,
    X1_SEND  => ai_Final_0_X1_SEND ,
    X1_ACK   => ai_Final_0_X1_ACK  ,
    X1_COUNT => ai_Final_0_X1_COUNT,

    X2_DATA  => ai_Final_0_X2_DATA ,
    X2_SEND  => ai_Final_0_X2_SEND ,
    X2_ACK   => ai_Final_0_X2_ACK  ,
    X2_COUNT => ai_Final_0_X2_COUNT,

    X3_DATA  => ai_Final_0_X3_DATA ,
    X3_SEND  => ai_Final_0_X3_SEND ,
    X3_ACK   => ai_Final_0_X3_ACK  ,
    X3_COUNT => ai_Final_0_X3_COUNT,

    Y0_DATA  => ao_Final_0_Y0_DATA ,
    Y0_SEND  => ao_Final_0_Y0_SEND ,
    Y0_ACK   => ao_Final_0_Y0_ACK  ,
    Y0_COUNT => ao_Final_0_Y0_COUNT,
    Y0_RDY   => ao_Final_0_Y0_RDY  ,

    Y1_DATA  => ao_Final_0_Y1_DATA ,
    Y1_SEND  => ao_Final_0_Y1_SEND ,
    Y1_ACK   => ao_Final_0_Y1_ACK  ,
    Y1_COUNT => ao_Final_0_Y1_COUNT,
    Y1_RDY   => ao_Final_0_Y1_RDY  ,

    Y2_DATA  => ao_Final_0_Y2_DATA ,
    Y2_SEND  => ao_Final_0_Y2_SEND ,
    Y2_ACK   => ao_Final_0_Y2_ACK  ,
    Y2_COUNT => ao_Final_0_Y2_COUNT,
    Y2_RDY   => ao_Final_0_Y2_RDY  ,

    Y3_DATA  => ao_Final_0_Y3_DATA ,
    Y3_SEND  => ao_Final_0_Y3_SEND ,
    Y3_ACK   => ao_Final_0_Y3_ACK  ,
    Y3_COUNT => ao_Final_0_Y3_COUNT,
    Y3_RDY   => ao_Final_0_Y3_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Final_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Final_0_Y0_DATA,
    In_SEND  => ao_Final_0_Y0_SEND,
    In_ACK   => ao_Final_0_Y0_ACK,
    In_COUNT => ao_Final_0_Y0_COUNT,
    In_RDY   => ao_Final_0_Y0_RDY,

    Out_DATA  => aof_Final_0_Y0_DATA,
    Out_SEND  => aof_Final_0_Y0_SEND,
    Out_ACK   => aof_Final_0_Y0_ACK,
    Out_COUNT => aof_Final_0_Y0_COUNT,
    Out_RDY   => aof_Final_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Final_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Final_0_Y1_DATA,
    In_SEND  => ao_Final_0_Y1_SEND,
    In_ACK   => ao_Final_0_Y1_ACK,
    In_COUNT => ao_Final_0_Y1_COUNT,
    In_RDY   => ao_Final_0_Y1_RDY,

    Out_DATA  => aof_Final_0_Y1_DATA,
    Out_SEND  => aof_Final_0_Y1_SEND,
    Out_ACK   => aof_Final_0_Y1_ACK,
    Out_COUNT => aof_Final_0_Y1_COUNT,
    Out_RDY   => aof_Final_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Final_0_Y2 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Final_0_Y2_DATA,
    In_SEND  => ao_Final_0_Y2_SEND,
    In_ACK   => ao_Final_0_Y2_ACK,
    In_COUNT => ao_Final_0_Y2_COUNT,
    In_RDY   => ao_Final_0_Y2_RDY,

    Out_DATA  => aof_Final_0_Y2_DATA,
    Out_SEND  => aof_Final_0_Y2_SEND,
    Out_ACK   => aof_Final_0_Y2_ACK,
    Out_COUNT => aof_Final_0_Y2_COUNT,
    Out_RDY   => aof_Final_0_Y2_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Final_0_Y3 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Final_0_Y3_DATA,
    In_SEND  => ao_Final_0_Y3_SEND,
    In_ACK   => ao_Final_0_Y3_ACK,
    In_COUNT => ao_Final_0_Y3_COUNT,
    In_RDY   => ao_Final_0_Y3_RDY,

    Out_DATA  => aof_Final_0_Y3_DATA,
    Out_SEND  => aof_Final_0_Y3_SEND,
    Out_ACK   => aof_Final_0_Y3_ACK,
    Out_COUNT => aof_Final_0_Y3_COUNT,
    Out_RDY   => aof_Final_0_Y3_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Final_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Final_0_X0_DATA,
    Out_SEND  => ai_Final_0_X0_SEND,
    Out_ACK   => ai_Final_0_X0_ACK,
    Out_COUNT => ai_Final_0_X0_COUNT,

    In_DATA  => aof_Shuffle_0_Y0_DATA,
    In_SEND  => aof_Shuffle_0_Y0_SEND( 0 ),
    In_ACK   => aof_Shuffle_0_Y0_ACK( 0 ),
    In_COUNT => aof_Shuffle_0_Y0_COUNT,
    In_RDY   => aof_Shuffle_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Final_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Final_0_X1_DATA,
    Out_SEND  => ai_Final_0_X1_SEND,
    Out_ACK   => ai_Final_0_X1_ACK,
    Out_COUNT => ai_Final_0_X1_COUNT,

    In_DATA  => aof_Shuffle_0_Y1_DATA,
    In_SEND  => aof_Shuffle_0_Y1_SEND( 0 ),
    In_ACK   => aof_Shuffle_0_Y1_ACK( 0 ),
    In_COUNT => aof_Shuffle_0_Y1_COUNT,
    In_RDY   => aof_Shuffle_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Final_0_X2 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Final_0_X2_DATA,
    Out_SEND  => ai_Final_0_X2_SEND,
    Out_ACK   => ai_Final_0_X2_ACK,
    Out_COUNT => ai_Final_0_X2_COUNT,

    In_DATA  => aof_Shuffle_0_Y2_DATA,
    In_SEND  => aof_Shuffle_0_Y2_SEND( 0 ),
    In_ACK   => aof_Shuffle_0_Y2_ACK( 0 ),
    In_COUNT => aof_Shuffle_0_Y2_COUNT,
    In_RDY   => aof_Shuffle_0_Y2_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Final_0_X3 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 24 )
  port map (
    Out_DATA  => ai_Final_0_X3_DATA,
    Out_SEND  => ai_Final_0_X3_SEND,
    Out_ACK   => ai_Final_0_X3_ACK,
    Out_COUNT => ai_Final_0_X3_COUNT,

    In_DATA  => aof_Shuffle_0_Y3_DATA,
    In_SEND  => aof_Shuffle_0_Y3_SEND( 0 ),
    In_ACK   => aof_Shuffle_0_Y3_ACK( 0 ),
    In_COUNT => aof_Shuffle_0_Y3_COUNT,
    In_RDY   => aof_Shuffle_0_Y3_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_RowSort_0 : component RowSort_0
  port map (
    ROW_DATA  => ai_RowSort_0_ROW_DATA ,
    ROW_SEND  => ai_RowSort_0_ROW_SEND ,
    ROW_ACK   => ai_RowSort_0_ROW_ACK  ,
    ROW_COUNT => ai_RowSort_0_ROW_COUNT,

    Y0_DATA  => ao_RowSort_0_Y0_DATA ,
    Y0_SEND  => ao_RowSort_0_Y0_SEND ,
    Y0_ACK   => ao_RowSort_0_Y0_ACK  ,
    Y0_COUNT => ao_RowSort_0_Y0_COUNT,
    Y0_RDY   => ao_RowSort_0_Y0_RDY  ,

    Y1_DATA  => ao_RowSort_0_Y1_DATA ,
    Y1_SEND  => ao_RowSort_0_Y1_SEND ,
    Y1_ACK   => ao_RowSort_0_Y1_ACK  ,
    Y1_COUNT => ao_RowSort_0_Y1_COUNT,
    Y1_RDY   => ao_RowSort_0_Y1_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_RowSort_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 13 )
  port map (
    In_DATA  => ao_RowSort_0_Y0_DATA,
    In_SEND  => ao_RowSort_0_Y0_SEND,
    In_ACK   => ao_RowSort_0_Y0_ACK,
    In_COUNT => ao_RowSort_0_Y0_COUNT,
    In_RDY   => ao_RowSort_0_Y0_RDY,

    Out_DATA  => aof_RowSort_0_Y0_DATA,
    Out_SEND  => aof_RowSort_0_Y0_SEND,
    Out_ACK   => aof_RowSort_0_Y0_ACK,
    Out_COUNT => aof_RowSort_0_Y0_COUNT,
    Out_RDY   => aof_RowSort_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_RowSort_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 13 )
  port map (
    In_DATA  => ao_RowSort_0_Y1_DATA,
    In_SEND  => ao_RowSort_0_Y1_SEND,
    In_ACK   => ao_RowSort_0_Y1_ACK,
    In_COUNT => ao_RowSort_0_Y1_COUNT,
    In_RDY   => ao_RowSort_0_Y1_RDY,

    Out_DATA  => aof_RowSort_0_Y1_DATA,
    Out_SEND  => aof_RowSort_0_Y1_SEND,
    Out_ACK   => aof_RowSort_0_Y1_ACK,
    Out_COUNT => aof_RowSort_0_Y1_COUNT,
    Out_RDY   => aof_RowSort_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_RowSort_0_ROW : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 13 )
  port map (
    Out_DATA  => ai_RowSort_0_ROW_DATA,
    Out_SEND  => ai_RowSort_0_ROW_SEND,
    Out_ACK   => ai_RowSort_0_ROW_ACK,
    Out_COUNT => ai_RowSort_0_ROW_COUNT,

    In_DATA  => nif_in_DATA,
    In_SEND  => nif_in_SEND( 0 ),
    In_ACK   => nif_in_ACK( 0 ),
    In_COUNT => nif_in_COUNT,
    In_RDY   => nif_in_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_FairMerge_0 : component FairMerge_0
  port map (
    R0_DATA  => ai_FairMerge_0_R0_DATA ,
    R0_SEND  => ai_FairMerge_0_R0_SEND ,
    R0_ACK   => ai_FairMerge_0_R0_ACK  ,
    R0_COUNT => ai_FairMerge_0_R0_COUNT,

    R1_DATA  => ai_FairMerge_0_R1_DATA ,
    R1_SEND  => ai_FairMerge_0_R1_SEND ,
    R1_ACK   => ai_FairMerge_0_R1_ACK  ,
    R1_COUNT => ai_FairMerge_0_R1_COUNT,

    C0_DATA  => ai_FairMerge_0_C0_DATA ,
    C0_SEND  => ai_FairMerge_0_C0_SEND ,
    C0_ACK   => ai_FairMerge_0_C0_ACK  ,
    C0_COUNT => ai_FairMerge_0_C0_COUNT,

    C1_DATA  => ai_FairMerge_0_C1_DATA ,
    C1_SEND  => ai_FairMerge_0_C1_SEND ,
    C1_ACK   => ai_FairMerge_0_C1_ACK  ,
    C1_COUNT => ai_FairMerge_0_C1_COUNT,

    Y0_DATA  => ao_FairMerge_0_Y0_DATA ,
    Y0_SEND  => ao_FairMerge_0_Y0_SEND ,
    Y0_ACK   => ao_FairMerge_0_Y0_ACK  ,
    Y0_COUNT => ao_FairMerge_0_Y0_COUNT,
    Y0_RDY   => ao_FairMerge_0_Y0_RDY  ,

    Y1_DATA  => ao_FairMerge_0_Y1_DATA ,
    Y1_SEND  => ao_FairMerge_0_Y1_SEND ,
    Y1_ACK   => ao_FairMerge_0_Y1_ACK  ,
    Y1_COUNT => ao_FairMerge_0_Y1_COUNT,
    Y1_RDY   => ao_FairMerge_0_Y1_RDY  ,

    ROWOUT_DATA  => ao_FairMerge_0_ROWOUT_DATA ,
    ROWOUT_SEND  => ao_FairMerge_0_ROWOUT_SEND ,
    ROWOUT_ACK   => ao_FairMerge_0_ROWOUT_ACK  ,
    ROWOUT_COUNT => ao_FairMerge_0_ROWOUT_COUNT,
    ROWOUT_RDY   => ao_FairMerge_0_ROWOUT_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_FairMerge_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_FairMerge_0_Y0_DATA,
    In_SEND  => ao_FairMerge_0_Y0_SEND,
    In_ACK   => ao_FairMerge_0_Y0_ACK,
    In_COUNT => ao_FairMerge_0_Y0_COUNT,
    In_RDY   => ao_FairMerge_0_Y0_RDY,

    Out_DATA  => aof_FairMerge_0_Y0_DATA,
    Out_SEND  => aof_FairMerge_0_Y0_SEND,
    Out_ACK   => aof_FairMerge_0_Y0_ACK,
    Out_COUNT => aof_FairMerge_0_Y0_COUNT,
    Out_RDY   => aof_FairMerge_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_FairMerge_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_FairMerge_0_Y1_DATA,
    In_SEND  => ao_FairMerge_0_Y1_SEND,
    In_ACK   => ao_FairMerge_0_Y1_ACK,
    In_COUNT => ao_FairMerge_0_Y1_COUNT,
    In_RDY   => ao_FairMerge_0_Y1_RDY,

    Out_DATA  => aof_FairMerge_0_Y1_DATA,
    Out_SEND  => aof_FairMerge_0_Y1_SEND,
    Out_ACK   => aof_FairMerge_0_Y1_ACK,
    Out_COUNT => aof_FairMerge_0_Y1_COUNT,
    Out_RDY   => aof_FairMerge_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_FairMerge_0_ROWOUT : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 2, width => 1 )
  port map (
    In_DATA(0)  => ao_FairMerge_0_ROWOUT_DATA,
    In_SEND  => ao_FairMerge_0_ROWOUT_SEND,
    In_ACK   => ao_FairMerge_0_ROWOUT_ACK,
    In_COUNT => ao_FairMerge_0_ROWOUT_COUNT,
    In_RDY   => ao_FairMerge_0_ROWOUT_RDY,

    Out_DATA(0)  => aof_FairMerge_0_ROWOUT_DATA,
    Out_SEND  => aof_FairMerge_0_ROWOUT_SEND,
    Out_ACK   => aof_FairMerge_0_ROWOUT_ACK,
    Out_COUNT => aof_FairMerge_0_ROWOUT_COUNT,
    Out_RDY   => aof_FairMerge_0_ROWOUT_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_FairMerge_0_R0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 13 )
  port map (
    Out_DATA  => ai_FairMerge_0_R0_DATA,
    Out_SEND  => ai_FairMerge_0_R0_SEND,
    Out_ACK   => ai_FairMerge_0_R0_ACK,
    Out_COUNT => ai_FairMerge_0_R0_COUNT,

    In_DATA  => aof_RowSort_0_Y0_DATA,
    In_SEND  => aof_RowSort_0_Y0_SEND( 0 ),
    In_ACK   => aof_RowSort_0_Y0_ACK( 0 ),
    In_COUNT => aof_RowSort_0_Y0_COUNT,
    In_RDY   => aof_RowSort_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_FairMerge_0_R1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 13 )
  port map (
    Out_DATA  => ai_FairMerge_0_R1_DATA,
    Out_SEND  => ai_FairMerge_0_R1_SEND,
    Out_ACK   => ai_FairMerge_0_R1_ACK,
    Out_COUNT => ai_FairMerge_0_R1_COUNT,

    In_DATA  => aof_RowSort_0_Y1_DATA,
    In_SEND  => aof_RowSort_0_Y1_SEND( 0 ),
    In_ACK   => aof_RowSort_0_Y1_ACK( 0 ),
    In_COUNT => aof_RowSort_0_Y1_COUNT,
    In_RDY   => aof_RowSort_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_FairMerge_0_C0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_FairMerge_0_C0_DATA,
    Out_SEND  => ai_FairMerge_0_C0_SEND,
    Out_ACK   => ai_FairMerge_0_C0_ACK,
    Out_COUNT => ai_FairMerge_0_C0_COUNT,

    In_DATA  => aof_Transpose_0_Y0_DATA,
    In_SEND  => aof_Transpose_0_Y0_SEND( 0 ),
    In_ACK   => aof_Transpose_0_Y0_ACK( 0 ),
    In_COUNT => aof_Transpose_0_Y0_COUNT,
    In_RDY   => aof_Transpose_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_FairMerge_0_C1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_FairMerge_0_C1_DATA,
    Out_SEND  => ai_FairMerge_0_C1_SEND,
    Out_ACK   => ai_FairMerge_0_C1_ACK,
    Out_COUNT => ai_FairMerge_0_C1_COUNT,

    In_DATA  => aof_Transpose_0_Y1_DATA,
    In_SEND  => aof_Transpose_0_Y1_SEND( 0 ),
    In_ACK   => aof_Transpose_0_Y1_ACK( 0 ),
    In_COUNT => aof_Transpose_0_Y1_COUNT,
    In_RDY   => aof_Transpose_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Downsample_0 : component Downsample_0
  port map (
    R_DATA  => ai_Downsample_0_R_DATA ,
    R_SEND  => ai_Downsample_0_R_SEND ,
    R_ACK   => ai_Downsample_0_R_ACK  ,
    R_COUNT => ai_Downsample_0_R_COUNT,

    R2_DATA  => ao_Downsample_0_R2_DATA ,
    R2_SEND  => ao_Downsample_0_R2_SEND ,
    R2_ACK   => ao_Downsample_0_R2_ACK  ,
    R2_COUNT => ao_Downsample_0_R2_COUNT,
    R2_RDY   => ao_Downsample_0_R2_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Downsample_0_R2 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 1 )
  port map (
    In_DATA(0)  => ao_Downsample_0_R2_DATA,
    In_SEND  => ao_Downsample_0_R2_SEND,
    In_ACK   => ao_Downsample_0_R2_ACK,
    In_COUNT => ao_Downsample_0_R2_COUNT,
    In_RDY   => ao_Downsample_0_R2_RDY,

    Out_DATA(0)  => aof_Downsample_0_R2_DATA,
    Out_SEND  => aof_Downsample_0_R2_SEND,
    Out_ACK   => aof_Downsample_0_R2_ACK,
    Out_COUNT => aof_Downsample_0_R2_COUNT,
    Out_RDY   => aof_Downsample_0_R2_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Downsample_0_R : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 1 )
  port map (
    Out_DATA(0)  => ai_Downsample_0_R_DATA,
    Out_SEND  => ai_Downsample_0_R_SEND,
    Out_ACK   => ai_Downsample_0_R_ACK,
    Out_COUNT => ai_Downsample_0_R_COUNT,

    In_DATA(0)  => aof_FairMerge_0_ROWOUT_DATA,
    In_SEND  => aof_FairMerge_0_ROWOUT_SEND( 0 ),
    In_ACK   => aof_FairMerge_0_ROWOUT_ACK( 0 ),
    In_COUNT => aof_FairMerge_0_ROWOUT_COUNT,
    In_RDY   => aof_FairMerge_0_ROWOUT_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Separate_0 : component Separate_0
  port map (
    X0_DATA  => ai_Separate_0_X0_DATA ,
    X0_SEND  => ai_Separate_0_X0_SEND ,
    X0_ACK   => ai_Separate_0_X0_ACK  ,
    X0_COUNT => ai_Separate_0_X0_COUNT,

    X1_DATA  => ai_Separate_0_X1_DATA ,
    X1_SEND  => ai_Separate_0_X1_SEND ,
    X1_ACK   => ai_Separate_0_X1_ACK  ,
    X1_COUNT => ai_Separate_0_X1_COUNT,

    X2_DATA  => ai_Separate_0_X2_DATA ,
    X2_SEND  => ai_Separate_0_X2_SEND ,
    X2_ACK   => ai_Separate_0_X2_ACK  ,
    X2_COUNT => ai_Separate_0_X2_COUNT,

    X3_DATA  => ai_Separate_0_X3_DATA ,
    X3_SEND  => ai_Separate_0_X3_SEND ,
    X3_ACK   => ai_Separate_0_X3_ACK  ,
    X3_COUNT => ai_Separate_0_X3_COUNT,

    ROW_DATA  => ai_Separate_0_ROW_DATA ,
    ROW_SEND  => ai_Separate_0_ROW_SEND ,
    ROW_ACK   => ai_Separate_0_ROW_ACK  ,
    ROW_COUNT => ai_Separate_0_ROW_COUNT,

    R0_DATA  => ao_Separate_0_R0_DATA ,
    R0_SEND  => ao_Separate_0_R0_SEND ,
    R0_ACK   => ao_Separate_0_R0_ACK  ,
    R0_COUNT => ao_Separate_0_R0_COUNT,
    R0_RDY   => ao_Separate_0_R0_RDY  ,

    R1_DATA  => ao_Separate_0_R1_DATA ,
    R1_SEND  => ao_Separate_0_R1_SEND ,
    R1_ACK   => ao_Separate_0_R1_ACK  ,
    R1_COUNT => ao_Separate_0_R1_COUNT,
    R1_RDY   => ao_Separate_0_R1_RDY  ,

    R2_DATA  => ao_Separate_0_R2_DATA ,
    R2_SEND  => ao_Separate_0_R2_SEND ,
    R2_ACK   => ao_Separate_0_R2_ACK  ,
    R2_COUNT => ao_Separate_0_R2_COUNT,
    R2_RDY   => ao_Separate_0_R2_RDY  ,

    R3_DATA  => ao_Separate_0_R3_DATA ,
    R3_SEND  => ao_Separate_0_R3_SEND ,
    R3_ACK   => ao_Separate_0_R3_ACK  ,
    R3_COUNT => ao_Separate_0_R3_COUNT,
    R3_RDY   => ao_Separate_0_R3_RDY  ,

    C0_DATA  => ao_Separate_0_C0_DATA ,
    C0_SEND  => ao_Separate_0_C0_SEND ,
    C0_ACK   => ao_Separate_0_C0_ACK  ,
    C0_COUNT => ao_Separate_0_C0_COUNT,
    C0_RDY   => ao_Separate_0_C0_RDY  ,

    C1_DATA  => ao_Separate_0_C1_DATA ,
    C1_SEND  => ao_Separate_0_C1_SEND ,
    C1_ACK   => ao_Separate_0_C1_ACK  ,
    C1_COUNT => ao_Separate_0_C1_COUNT,
    C1_RDY   => ao_Separate_0_C1_RDY  ,

    C2_DATA  => ao_Separate_0_C2_DATA ,
    C2_SEND  => ao_Separate_0_C2_SEND ,
    C2_ACK   => ao_Separate_0_C2_ACK  ,
    C2_COUNT => ao_Separate_0_C2_COUNT,
    C2_RDY   => ao_Separate_0_C2_RDY  ,

    C3_DATA  => ao_Separate_0_C3_DATA ,
    C3_SEND  => ao_Separate_0_C3_SEND ,
    C3_ACK   => ao_Separate_0_C3_ACK  ,
    C3_COUNT => ao_Separate_0_C3_COUNT,
    C3_RDY   => ao_Separate_0_C3_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_R0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Separate_0_R0_DATA,
    In_SEND  => ao_Separate_0_R0_SEND,
    In_ACK   => ao_Separate_0_R0_ACK,
    In_COUNT => ao_Separate_0_R0_COUNT,
    In_RDY   => ao_Separate_0_R0_RDY,

    Out_DATA  => aof_Separate_0_R0_DATA,
    Out_SEND  => aof_Separate_0_R0_SEND,
    Out_ACK   => aof_Separate_0_R0_ACK,
    Out_COUNT => aof_Separate_0_R0_COUNT,
    Out_RDY   => aof_Separate_0_R0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_R1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Separate_0_R1_DATA,
    In_SEND  => ao_Separate_0_R1_SEND,
    In_ACK   => ao_Separate_0_R1_ACK,
    In_COUNT => ao_Separate_0_R1_COUNT,
    In_RDY   => ao_Separate_0_R1_RDY,

    Out_DATA  => aof_Separate_0_R1_DATA,
    Out_SEND  => aof_Separate_0_R1_SEND,
    Out_ACK   => aof_Separate_0_R1_ACK,
    Out_COUNT => aof_Separate_0_R1_COUNT,
    Out_RDY   => aof_Separate_0_R1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_R2 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Separate_0_R2_DATA,
    In_SEND  => ao_Separate_0_R2_SEND,
    In_ACK   => ao_Separate_0_R2_ACK,
    In_COUNT => ao_Separate_0_R2_COUNT,
    In_RDY   => ao_Separate_0_R2_RDY,

    Out_DATA  => aof_Separate_0_R2_DATA,
    Out_SEND  => aof_Separate_0_R2_SEND,
    Out_ACK   => aof_Separate_0_R2_ACK,
    Out_COUNT => aof_Separate_0_R2_COUNT,
    Out_RDY   => aof_Separate_0_R2_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_R3 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Separate_0_R3_DATA,
    In_SEND  => ao_Separate_0_R3_SEND,
    In_ACK   => ao_Separate_0_R3_ACK,
    In_COUNT => ao_Separate_0_R3_COUNT,
    In_RDY   => ao_Separate_0_R3_RDY,

    Out_DATA  => aof_Separate_0_R3_DATA,
    Out_SEND  => aof_Separate_0_R3_SEND,
    Out_ACK   => aof_Separate_0_R3_ACK,
    Out_COUNT => aof_Separate_0_R3_COUNT,
    Out_RDY   => aof_Separate_0_R3_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_C0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 10 )
  port map (
    In_DATA  => ao_Separate_0_C0_DATA,
    In_SEND  => ao_Separate_0_C0_SEND,
    In_ACK   => ao_Separate_0_C0_ACK,
    In_COUNT => ao_Separate_0_C0_COUNT,
    In_RDY   => ao_Separate_0_C0_RDY,

    Out_DATA  => aof_Separate_0_C0_DATA,
    Out_SEND  => aof_Separate_0_C0_SEND,
    Out_ACK   => aof_Separate_0_C0_ACK,
    Out_COUNT => aof_Separate_0_C0_COUNT,
    Out_RDY   => aof_Separate_0_C0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_C1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 10 )
  port map (
    In_DATA  => ao_Separate_0_C1_DATA,
    In_SEND  => ao_Separate_0_C1_SEND,
    In_ACK   => ao_Separate_0_C1_ACK,
    In_COUNT => ao_Separate_0_C1_COUNT,
    In_RDY   => ao_Separate_0_C1_RDY,

    Out_DATA  => aof_Separate_0_C1_DATA,
    Out_SEND  => aof_Separate_0_C1_SEND,
    Out_ACK   => aof_Separate_0_C1_ACK,
    Out_COUNT => aof_Separate_0_C1_COUNT,
    Out_RDY   => aof_Separate_0_C1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_C2 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 10 )
  port map (
    In_DATA  => ao_Separate_0_C2_DATA,
    In_SEND  => ao_Separate_0_C2_SEND,
    In_ACK   => ao_Separate_0_C2_ACK,
    In_COUNT => ao_Separate_0_C2_COUNT,
    In_RDY   => ao_Separate_0_C2_RDY,

    Out_DATA  => aof_Separate_0_C2_DATA,
    Out_SEND  => aof_Separate_0_C2_SEND,
    Out_ACK   => aof_Separate_0_C2_ACK,
    Out_COUNT => aof_Separate_0_C2_COUNT,
    Out_RDY   => aof_Separate_0_C2_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Separate_0_C3 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 10 )
  port map (
    In_DATA  => ao_Separate_0_C3_DATA,
    In_SEND  => ao_Separate_0_C3_SEND,
    In_ACK   => ao_Separate_0_C3_ACK,
    In_COUNT => ao_Separate_0_C3_COUNT,
    In_RDY   => ao_Separate_0_C3_RDY,

    Out_DATA  => aof_Separate_0_C3_DATA,
    Out_SEND  => aof_Separate_0_C3_SEND,
    Out_ACK   => aof_Separate_0_C3_ACK,
    Out_COUNT => aof_Separate_0_C3_COUNT,
    Out_RDY   => aof_Separate_0_C3_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Separate_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Separate_0_X0_DATA,
    Out_SEND  => ai_Separate_0_X0_SEND,
    Out_ACK   => ai_Separate_0_X0_ACK,
    Out_COUNT => ai_Separate_0_X0_COUNT,

    In_DATA  => aof_Final_0_Y0_DATA,
    In_SEND  => aof_Final_0_Y0_SEND( 0 ),
    In_ACK   => aof_Final_0_Y0_ACK( 0 ),
    In_COUNT => aof_Final_0_Y0_COUNT,
    In_RDY   => aof_Final_0_Y0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Separate_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Separate_0_X1_DATA,
    Out_SEND  => ai_Separate_0_X1_SEND,
    Out_ACK   => ai_Separate_0_X1_ACK,
    Out_COUNT => ai_Separate_0_X1_COUNT,

    In_DATA  => aof_Final_0_Y1_DATA,
    In_SEND  => aof_Final_0_Y1_SEND( 0 ),
    In_ACK   => aof_Final_0_Y1_ACK( 0 ),
    In_COUNT => aof_Final_0_Y1_COUNT,
    In_RDY   => aof_Final_0_Y1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Separate_0_X2 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Separate_0_X2_DATA,
    Out_SEND  => ai_Separate_0_X2_SEND,
    Out_ACK   => ai_Separate_0_X2_ACK,
    Out_COUNT => ai_Separate_0_X2_COUNT,

    In_DATA  => aof_Final_0_Y2_DATA,
    In_SEND  => aof_Final_0_Y2_SEND( 0 ),
    In_ACK   => aof_Final_0_Y2_ACK( 0 ),
    In_COUNT => aof_Final_0_Y2_COUNT,
    In_RDY   => aof_Final_0_Y2_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Separate_0_X3 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Separate_0_X3_DATA,
    Out_SEND  => ai_Separate_0_X3_SEND,
    Out_ACK   => ai_Separate_0_X3_ACK,
    Out_COUNT => ai_Separate_0_X3_COUNT,

    In_DATA  => aof_Final_0_Y3_DATA,
    In_SEND  => aof_Final_0_Y3_SEND( 0 ),
    In_ACK   => aof_Final_0_Y3_ACK( 0 ),
    In_COUNT => aof_Final_0_Y3_COUNT,
    In_RDY   => aof_Final_0_Y3_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Separate_0_ROW : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 1 )
  port map (
    Out_DATA(0)  => ai_Separate_0_ROW_DATA,
    Out_SEND  => ai_Separate_0_ROW_SEND,
    Out_ACK   => ai_Separate_0_ROW_ACK,
    Out_COUNT => ai_Separate_0_ROW_COUNT,

    In_DATA(0)  => aof_Downsample_0_R2_DATA,
    In_SEND  => aof_Downsample_0_R2_SEND( 0 ),
    In_ACK   => aof_Downsample_0_R2_ACK( 0 ),
    In_COUNT => aof_Downsample_0_R2_COUNT,
    In_RDY   => aof_Downsample_0_R2_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Transpose_0 : component Transpose_0
  port map (
    X0_DATA  => ai_Transpose_0_X0_DATA ,
    X0_SEND  => ai_Transpose_0_X0_SEND ,
    X0_ACK   => ai_Transpose_0_X0_ACK  ,
    X0_COUNT => ai_Transpose_0_X0_COUNT,

    X1_DATA  => ai_Transpose_0_X1_DATA ,
    X1_SEND  => ai_Transpose_0_X1_SEND ,
    X1_ACK   => ai_Transpose_0_X1_ACK  ,
    X1_COUNT => ai_Transpose_0_X1_COUNT,

    X2_DATA  => ai_Transpose_0_X2_DATA ,
    X2_SEND  => ai_Transpose_0_X2_SEND ,
    X2_ACK   => ai_Transpose_0_X2_ACK  ,
    X2_COUNT => ai_Transpose_0_X2_COUNT,

    X3_DATA  => ai_Transpose_0_X3_DATA ,
    X3_SEND  => ai_Transpose_0_X3_SEND ,
    X3_ACK   => ai_Transpose_0_X3_ACK  ,
    X3_COUNT => ai_Transpose_0_X3_COUNT,

    Y0_DATA  => ao_Transpose_0_Y0_DATA ,
    Y0_SEND  => ao_Transpose_0_Y0_SEND ,
    Y0_ACK   => ao_Transpose_0_Y0_ACK  ,
    Y0_COUNT => ao_Transpose_0_Y0_COUNT,
    Y0_RDY   => ao_Transpose_0_Y0_RDY  ,

    Y1_DATA  => ao_Transpose_0_Y1_DATA ,
    Y1_SEND  => ao_Transpose_0_Y1_SEND ,
    Y1_ACK   => ao_Transpose_0_Y1_ACK  ,
    Y1_COUNT => ao_Transpose_0_Y1_COUNT,
    Y1_RDY   => ao_Transpose_0_Y1_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Transpose_0_Y0 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Transpose_0_Y0_DATA,
    In_SEND  => ao_Transpose_0_Y0_SEND,
    In_ACK   => ao_Transpose_0_Y0_ACK,
    In_COUNT => ao_Transpose_0_Y0_COUNT,
    In_RDY   => ao_Transpose_0_Y0_RDY,

    Out_DATA  => aof_Transpose_0_Y0_DATA,
    Out_SEND  => aof_Transpose_0_Y0_SEND,
    Out_ACK   => aof_Transpose_0_Y0_ACK,
    Out_COUNT => aof_Transpose_0_Y0_COUNT,
    Out_RDY   => aof_Transpose_0_Y0_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Transpose_0_Y1 : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 16 )
  port map (
    In_DATA  => ao_Transpose_0_Y1_DATA,
    In_SEND  => ao_Transpose_0_Y1_SEND,
    In_ACK   => ao_Transpose_0_Y1_ACK,
    In_COUNT => ao_Transpose_0_Y1_COUNT,
    In_RDY   => ao_Transpose_0_Y1_RDY,

    Out_DATA  => aof_Transpose_0_Y1_DATA,
    Out_SEND  => aof_Transpose_0_Y1_SEND,
    Out_ACK   => aof_Transpose_0_Y1_ACK,
    Out_COUNT => aof_Transpose_0_Y1_COUNT,
    Out_RDY   => aof_Transpose_0_Y1_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Transpose_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Transpose_0_X0_DATA,
    Out_SEND  => ai_Transpose_0_X0_SEND,
    Out_ACK   => ai_Transpose_0_X0_ACK,
    Out_COUNT => ai_Transpose_0_X0_COUNT,

    In_DATA  => aof_Separate_0_R0_DATA,
    In_SEND  => aof_Separate_0_R0_SEND( 0 ),
    In_ACK   => aof_Separate_0_R0_ACK( 0 ),
    In_COUNT => aof_Separate_0_R0_COUNT,
    In_RDY   => aof_Separate_0_R0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Transpose_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Transpose_0_X1_DATA,
    Out_SEND  => ai_Transpose_0_X1_SEND,
    Out_ACK   => ai_Transpose_0_X1_ACK,
    Out_COUNT => ai_Transpose_0_X1_COUNT,

    In_DATA  => aof_Separate_0_R1_DATA,
    In_SEND  => aof_Separate_0_R1_SEND( 0 ),
    In_ACK   => aof_Separate_0_R1_ACK( 0 ),
    In_COUNT => aof_Separate_0_R1_COUNT,
    In_RDY   => aof_Separate_0_R1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Transpose_0_X2 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Transpose_0_X2_DATA,
    Out_SEND  => ai_Transpose_0_X2_SEND,
    Out_ACK   => ai_Transpose_0_X2_ACK,
    Out_COUNT => ai_Transpose_0_X2_COUNT,

    In_DATA  => aof_Separate_0_R2_DATA,
    In_SEND  => aof_Separate_0_R2_SEND( 0 ),
    In_ACK   => aof_Separate_0_R2_ACK( 0 ),
    In_COUNT => aof_Separate_0_R2_COUNT,
    In_RDY   => aof_Separate_0_R2_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Transpose_0_X3 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 16 )
  port map (
    Out_DATA  => ai_Transpose_0_X3_DATA,
    Out_SEND  => ai_Transpose_0_X3_SEND,
    Out_ACK   => ai_Transpose_0_X3_ACK,
    Out_COUNT => ai_Transpose_0_X3_COUNT,

    In_DATA  => aof_Separate_0_R3_DATA,
    In_SEND  => aof_Separate_0_R3_SEND( 0 ),
    In_ACK   => aof_Separate_0_R3_ACK( 0 ),
    In_COUNT => aof_Separate_0_R3_COUNT,
    In_RDY   => aof_Separate_0_R3_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Retranspose_0 : component Retranspose_0
  port map (
    X0_DATA  => ai_Retranspose_0_X0_DATA ,
    X0_SEND  => ai_Retranspose_0_X0_SEND ,
    X0_ACK   => ai_Retranspose_0_X0_ACK  ,
    X0_COUNT => ai_Retranspose_0_X0_COUNT,

    X1_DATA  => ai_Retranspose_0_X1_DATA ,
    X1_SEND  => ai_Retranspose_0_X1_SEND ,
    X1_ACK   => ai_Retranspose_0_X1_ACK  ,
    X1_COUNT => ai_Retranspose_0_X1_COUNT,

    X2_DATA  => ai_Retranspose_0_X2_DATA ,
    X2_SEND  => ai_Retranspose_0_X2_SEND ,
    X2_ACK   => ai_Retranspose_0_X2_ACK  ,
    X2_COUNT => ai_Retranspose_0_X2_COUNT,

    X3_DATA  => ai_Retranspose_0_X3_DATA ,
    X3_SEND  => ai_Retranspose_0_X3_SEND ,
    X3_ACK   => ai_Retranspose_0_X3_ACK  ,
    X3_COUNT => ai_Retranspose_0_X3_COUNT,

    Y_DATA  => ao_Retranspose_0_Y_DATA ,
    Y_SEND  => ao_Retranspose_0_Y_SEND ,
    Y_ACK   => ao_Retranspose_0_Y_ACK  ,
    Y_COUNT => ao_Retranspose_0_Y_COUNT,
    Y_RDY   => ao_Retranspose_0_Y_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Retranspose_0_Y : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 10 )
  port map (
    In_DATA  => ao_Retranspose_0_Y_DATA,
    In_SEND  => ao_Retranspose_0_Y_SEND,
    In_ACK   => ao_Retranspose_0_Y_ACK,
    In_COUNT => ao_Retranspose_0_Y_COUNT,
    In_RDY   => ao_Retranspose_0_Y_RDY,

    Out_DATA  => aof_Retranspose_0_Y_DATA,
    Out_SEND  => aof_Retranspose_0_Y_SEND,
    Out_ACK   => aof_Retranspose_0_Y_ACK,
    Out_COUNT => aof_Retranspose_0_Y_COUNT,
    Out_RDY   => aof_Retranspose_0_Y_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Retranspose_0_X0 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 10 )
  port map (
    Out_DATA  => ai_Retranspose_0_X0_DATA,
    Out_SEND  => ai_Retranspose_0_X0_SEND,
    Out_ACK   => ai_Retranspose_0_X0_ACK,
    Out_COUNT => ai_Retranspose_0_X0_COUNT,

    In_DATA  => aof_Separate_0_C0_DATA,
    In_SEND  => aof_Separate_0_C0_SEND( 0 ),
    In_ACK   => aof_Separate_0_C0_ACK( 0 ),
    In_COUNT => aof_Separate_0_C0_COUNT,
    In_RDY   => aof_Separate_0_C0_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Retranspose_0_X1 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 10 )
  port map (
    Out_DATA  => ai_Retranspose_0_X1_DATA,
    Out_SEND  => ai_Retranspose_0_X1_SEND,
    Out_ACK   => ai_Retranspose_0_X1_ACK,
    Out_COUNT => ai_Retranspose_0_X1_COUNT,

    In_DATA  => aof_Separate_0_C1_DATA,
    In_SEND  => aof_Separate_0_C1_SEND( 0 ),
    In_ACK   => aof_Separate_0_C1_ACK( 0 ),
    In_COUNT => aof_Separate_0_C1_COUNT,
    In_RDY   => aof_Separate_0_C1_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Retranspose_0_X2 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 10 )
  port map (
    Out_DATA  => ai_Retranspose_0_X2_DATA,
    Out_SEND  => ai_Retranspose_0_X2_SEND,
    Out_ACK   => ai_Retranspose_0_X2_ACK,
    Out_COUNT => ai_Retranspose_0_X2_COUNT,

    In_DATA  => aof_Separate_0_C2_DATA,
    In_SEND  => aof_Separate_0_C2_SEND( 0 ),
    In_ACK   => aof_Separate_0_C2_ACK( 0 ),
    In_COUNT => aof_Separate_0_C2_COUNT,
    In_RDY   => aof_Separate_0_C2_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Retranspose_0_X3 : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 10 )
  port map (
    Out_DATA  => ai_Retranspose_0_X3_DATA,
    Out_SEND  => ai_Retranspose_0_X3_SEND,
    Out_ACK   => ai_Retranspose_0_X3_ACK,
    Out_COUNT => ai_Retranspose_0_X3_COUNT,

    In_DATA  => aof_Separate_0_C3_DATA,
    In_SEND  => aof_Separate_0_C3_SEND( 0 ),
    In_ACK   => aof_Separate_0_C3_ACK( 0 ),
    In_COUNT => aof_Separate_0_C3_COUNT,
    In_RDY   => aof_Separate_0_C3_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  i_Clip_0 : component Clip_0
  port map (
    I_DATA  => ai_Clip_0_I_DATA ,
    I_SEND  => ai_Clip_0_I_SEND ,
    I_ACK   => ai_Clip_0_I_ACK  ,
    I_COUNT => ai_Clip_0_I_COUNT,

    SIGNED_DATA  => ai_Clip_0_SIGNED_DATA ,
    SIGNED_SEND  => ai_Clip_0_SIGNED_SEND ,
    SIGNED_ACK   => ai_Clip_0_SIGNED_ACK  ,
    SIGNED_COUNT => ai_Clip_0_SIGNED_COUNT,

    O_DATA  => ao_Clip_0_O_DATA ,
    O_SEND  => ao_Clip_0_O_SEND ,
    O_ACK   => ao_Clip_0_O_ACK  ,
    O_COUNT => ao_Clip_0_O_COUNT,
    O_RDY   => ao_Clip_0_O_RDY  ,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ao_Clip_0_O : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 9 )
  port map (
    In_DATA  => ao_Clip_0_O_DATA,
    In_SEND  => ao_Clip_0_O_SEND,
    In_ACK   => ao_Clip_0_O_ACK,
    In_COUNT => ao_Clip_0_O_COUNT,
    In_RDY   => ao_Clip_0_O_RDY,

    Out_DATA  => aof_Clip_0_O_DATA,
    Out_SEND  => aof_Clip_0_O_SEND,
    Out_ACK   => aof_Clip_0_O_ACK,
    Out_COUNT => aof_Clip_0_O_COUNT,
    Out_RDY   => aof_Clip_0_O_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_ai_Clip_0_I : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 10 )
  port map (
    Out_DATA  => ai_Clip_0_I_DATA,
    Out_SEND  => ai_Clip_0_I_SEND,
    Out_ACK   => ai_Clip_0_I_ACK,
    Out_COUNT => ai_Clip_0_I_COUNT,

    In_DATA  => aof_Retranspose_0_Y_DATA,
    In_SEND  => aof_Retranspose_0_Y_SEND( 0 ),
    In_ACK   => aof_Retranspose_0_Y_ACK( 0 ),
    In_COUNT => aof_Retranspose_0_Y_COUNT,
    In_RDY   => aof_Retranspose_0_Y_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  q_ai_Clip_0_SIGNED : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 1 )
  port map (
    Out_DATA(0)  => ai_Clip_0_SIGNED_DATA,
    Out_SEND  => ai_Clip_0_SIGNED_SEND,
    Out_ACK   => ai_Clip_0_SIGNED_ACK,
    Out_COUNT => ai_Clip_0_SIGNED_COUNT,

    In_DATA(0)  => nif_signed_DATA,
    In_SEND  => nif_signed_SEND( 0 ),
    In_ACK   => nif_signed_ACK( 0 ),
    In_COUNT => nif_signed_COUNT,
    In_RDY   => nif_signed_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  f_ni_in : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 13 )
  port map (
    In_DATA  => ni_in_DATA,
    In_SEND  => ni_in_SEND,
    In_ACK   => ni_in_ACK,
    In_COUNT => ni_in_COUNT,
    In_RDY   => ni_in_RDY,

    Out_DATA  => nif_in_DATA,
    Out_SEND  => nif_in_SEND,
    Out_ACK   => nif_in_ACK,
    Out_COUNT => nif_in_COUNT,
    Out_RDY   => nif_in_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  f_ni_signed : entity SystemBuilder.Fanout( behavioral )
  generic map ( fanout => 1, width => 1 )
  port map (
    In_DATA(0)  => ni_signed_DATA,
    In_SEND  => ni_signed_SEND,
    In_ACK   => ni_signed_ACK,
    In_COUNT => ni_signed_COUNT,
    In_RDY   => ni_signed_RDY,

    Out_DATA(0)  => nif_signed_DATA,
    Out_SEND  => nif_signed_SEND,
    Out_ACK   => nif_signed_ACK,
    Out_COUNT => nif_signed_COUNT,
    Out_RDY   => nif_signed_RDY,

    CLK   => clocks(0),
    RESET => resets(0)
  );

  q_no_out : entity SystemBuilder.Queue( behavioral )
  generic map( length => 1, width => 9 )
  port map (
    Out_DATA  => no_out_DATA,
    Out_SEND  => no_out_SEND,
    Out_ACK   => no_out_ACK,
    Out_COUNT => no_out_COUNT,

    In_DATA  => aof_Clip_0_O_DATA,
    In_SEND  => aof_Clip_0_O_SEND( 0 ),
    In_ACK   => aof_Clip_0_O_ACK( 0 ),
    In_COUNT => aof_Clip_0_O_COUNT,
    In_RDY   => aof_Clip_0_O_RDY( 0 ),

    clk => clocks(0),
    reset => resets(0)
  );

  out_DATA <= no_out_DATA;
  out_SEND <= no_out_SEND;
  no_out_ACK <= out_ACK;
  out_COUNT <= no_out_COUNT;
  no_out_RDY <= out_RDY;

  ni_in_DATA <= in_DATA;
  ni_in_SEND <= in_SEND;
  in_ACK <= ni_in_ACK;
  ni_in_COUNT <= in_COUNT;
  in_RDY <= ni_in_RDY;

  ni_signed_DATA <= signed_DATA;
  ni_signed_SEND <= signed_SEND;
  signed_ACK <= ni_signed_ACK;
  ni_signed_COUNT <= signed_COUNT;
  signed_RDY <= ni_signed_RDY;

end architecture rtl;

-- ----------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------
