// Generated from "orcc_testbed"

#include <locale.h>
#include <stdio.h>
#include <stdlib.h>

#ifdef _WIN32
  #include <conio.h>
#else
  #include <termio.h>
#endif

#include "fifo.h"

#define SIZE 10000

////////////////////////////////////////////////////////////////////////////////
// FIFO allocation

static char array_0[SIZE * sizeof(int)];
static struct fifo_s fifo_0 = { sizeof(int), SIZE, array_0, 0, 0 };
static char array_1[SIZE * sizeof(int)];
static struct fifo_s fifo_1 = { sizeof(int), SIZE, array_1, 0, 0 };
static char array_2[SIZE * sizeof(int)];
static struct fifo_s fifo_2 = { sizeof(int), SIZE, array_2, 0, 0 };
static char array_3[SIZE * sizeof(int)];
static struct fifo_s fifo_3 = { sizeof(int), SIZE, array_3, 0, 0 };
static char array_4[SIZE * sizeof(short)];
static struct fifo_s fifo_4 = { sizeof(short), SIZE, array_4, 0, 0 };
static char array_5[SIZE * sizeof(short)];
static struct fifo_s fifo_5 = { sizeof(short), SIZE, array_5, 0, 0 };
static char array_6[SIZE * sizeof(short)];
static struct fifo_s fifo_6 = { sizeof(short), SIZE, array_6, 0, 0 };
static char array_7[384 * sizeof(short)];
static struct fifo_s fifo_7 = { sizeof(short), 384, array_7, 0, 0 };
static char array_8[SIZE * sizeof(short)];
static struct fifo_s fifo_8 = { sizeof(short), SIZE, array_8, 0, 0 };
static char array_9[SIZE * sizeof(short)];
static struct fifo_s fifo_9 = { sizeof(short), SIZE, array_9, 0, 0 };
static char array_10[6 * sizeof(int)];
static struct fifo_s fifo_10 = { sizeof(int), 6, array_10, 0, 0 };
static char array_11[6 * sizeof(short)];
static struct fifo_s fifo_11 = { sizeof(short), 6, array_11, 0, 0 };
static char array_12[6 * sizeof(char)];
static struct fifo_s fifo_12 = { sizeof(char), 6, array_12, 0, 0 };
static char array_13[SIZE * sizeof(int)];
static struct fifo_s fifo_13 = { sizeof(int), SIZE, array_13, 0, 0 };
static char array_14[SIZE * sizeof(int)];
static struct fifo_s fifo_14 = { sizeof(int), SIZE, array_14, 0, 0 };
static char array_15[SIZE * sizeof(int)];
static struct fifo_s fifo_15 = { sizeof(int), SIZE, array_15, 0, 0 };
static char array_16[SIZE * sizeof(int)];
static struct fifo_s fifo_16 = { sizeof(int), SIZE, array_16, 0, 0 };
static char array_17[SIZE * sizeof(int)];
static struct fifo_s fifo_17 = { sizeof(int), SIZE, array_17, 0, 0 };
static char array_18[SIZE * sizeof(int)];
static struct fifo_s fifo_18 = { sizeof(int), SIZE, array_18, 0, 0 };
static char array_19[SIZE * sizeof(int)];
static struct fifo_s fifo_19 = { sizeof(int), SIZE, array_19, 0, 0 };
static char array_20[SIZE * sizeof(int)];
static struct fifo_s fifo_20 = { sizeof(int), SIZE, array_20, 0, 0 };
static char array_21[384 * sizeof(short)];
static struct fifo_s fifo_21 = { sizeof(short), 384, array_21, 0, 0 };
static char array_22[384 * sizeof(short)];
static struct fifo_s fifo_22 = { sizeof(short), 384, array_22, 0, 0 };
static char array_23[384 * sizeof(short)];
static struct fifo_s fifo_23 = { sizeof(short), 384, array_23, 0, 0 };
static char array_24[SIZE * sizeof(int)];
static struct fifo_s fifo_24 = { sizeof(int), SIZE, array_24, 0, 0 };
static char array_25[SIZE * sizeof(char)];
static struct fifo_s fifo_25 = { sizeof(char), SIZE, array_25, 0, 0 };
static char array_26[SIZE * sizeof(short)];
static struct fifo_s fifo_26 = { sizeof(short), SIZE, array_26, 0, 0 };
static char array_27[SIZE * sizeof(int)];
static struct fifo_s fifo_27 = { sizeof(int), SIZE, array_27, 0, 0 };
static char array_28[1 * sizeof(short)];
static struct fifo_s fifo_28 = { sizeof(short), 1, array_28, 0, 0 };
static char array_29[1 * sizeof(short)];
static struct fifo_s fifo_29 = { sizeof(short), 1, array_29, 0, 0 };
static char array_30[SIZE * sizeof(short)];
static struct fifo_s fifo_30 = { sizeof(short), SIZE, array_30, 0, 0 };
static char array_31[SIZE * sizeof(char)];
static struct fifo_s fifo_31 = { sizeof(char), SIZE, array_31, 0, 0 };
static char array_32[SIZE * sizeof(short)];
static struct fifo_s fifo_32 = { sizeof(short), SIZE, array_32, 0, 0 };
static char array_33[SIZE * sizeof(char)];
static struct fifo_s fifo_33 = { sizeof(char), SIZE, array_33, 0, 0 };
static char array_34[SIZE * sizeof(int)];
static struct fifo_s fifo_34 = { sizeof(int), SIZE, array_34, 0, 0 };
static char array_35[SIZE * sizeof(short)];
static struct fifo_s fifo_35 = { sizeof(short), SIZE, array_35, 0, 0 };
static char array_36[SIZE * sizeof(short)];
static struct fifo_s fifo_36 = { sizeof(short), SIZE, array_36, 0, 0 };
static char array_37[SIZE * sizeof(short)];
static struct fifo_s fifo_37 = { sizeof(short), SIZE, array_37, 0, 0 };
static char array_38[SIZE * sizeof(short)];
static struct fifo_s fifo_38 = { sizeof(short), SIZE, array_38, 0, 0 };
static char array_39[9 * sizeof(int)];
static struct fifo_s fifo_39 = { sizeof(int), 9, array_39, 0, 0 };
static char array_40[3 * sizeof(int)];
static struct fifo_s fifo_40 = { sizeof(int), 3, array_40, 0, 0 };
static char array_41[SIZE * sizeof(int)];
static struct fifo_s fifo_41 = { sizeof(int), SIZE, array_41, 0, 0 };
static char array_42[SIZE * sizeof(char)];
static struct fifo_s fifo_42 = { sizeof(char), SIZE, array_42, 0, 0 };
static char array_43[486 * sizeof(short)];
static struct fifo_s fifo_43 = { sizeof(short), 486, array_43, 0, 0 };
static char array_44[SIZE * sizeof(short)];
static struct fifo_s fifo_44 = { sizeof(short), SIZE, array_44, 0, 0 };
static char array_45[SIZE * sizeof(short)];
static struct fifo_s fifo_45 = { sizeof(short), SIZE, array_45, 0, 0 };
static char array_46[SIZE * sizeof(short)];
static struct fifo_s fifo_46 = { sizeof(short), SIZE, array_46, 0, 0 };
static char array_47[SIZE * sizeof(short)];
static struct fifo_s fifo_47 = { sizeof(short), SIZE, array_47, 0, 0 };
static char array_48[378 * sizeof(short)];
static struct fifo_s fifo_48 = { sizeof(short), 378, array_48, 0, 0 };
static char array_49[SIZE * sizeof(short)];
static struct fifo_s fifo_49 = { sizeof(short), SIZE, array_49, 0, 0 };
static char array_50[SIZE * sizeof(short)];
static struct fifo_s fifo_50 = { sizeof(short), SIZE, array_50, 0, 0 };
static char array_51[SIZE * sizeof(int)];
static struct fifo_s fifo_51 = { sizeof(int), SIZE, array_51, 0, 0 };
static char array_52[SIZE * sizeof(int)];
static struct fifo_s fifo_52 = { sizeof(int), SIZE, array_52, 0, 0 };
static char array_53[SIZE * sizeof(int)];
static struct fifo_s fifo_53 = { sizeof(int), SIZE, array_53, 0, 0 };
static char array_54[SIZE * sizeof(int)];
static struct fifo_s fifo_54 = { sizeof(int), SIZE, array_54, 0, 0 };
static char array_55[SIZE * sizeof(short)];
static struct fifo_s fifo_55 = { sizeof(short), SIZE, array_55, 0, 0 };
static char array_56[SIZE * sizeof(short)];
static struct fifo_s fifo_56 = { sizeof(short), SIZE, array_56, 0, 0 };
static char array_57[SIZE * sizeof(short)];
static struct fifo_s fifo_57 = { sizeof(short), SIZE, array_57, 0, 0 };
static char array_58[SIZE * sizeof(short)];
static struct fifo_s fifo_58 = { sizeof(short), SIZE, array_58, 0, 0 };
static char array_59[SIZE * sizeof(short)];
static struct fifo_s fifo_59 = { sizeof(short), SIZE, array_59, 0, 0 };
static char array_60[SIZE * sizeof(short)];
static struct fifo_s fifo_60 = { sizeof(short), SIZE, array_60, 0, 0 };
static char array_61[SIZE * sizeof(short)];
static struct fifo_s fifo_61 = { sizeof(short), SIZE, array_61, 0, 0 };
static char array_62[SIZE * sizeof(short)];
static struct fifo_s fifo_62 = { sizeof(short), SIZE, array_62, 0, 0 };
static char array_63[SIZE * sizeof(int)];
static struct fifo_s fifo_63 = { sizeof(int), SIZE, array_63, 0, 0 };
static char array_64[SIZE * sizeof(short)];
static struct fifo_s fifo_64 = { sizeof(short), SIZE, array_64, 0, 0 };
static char array_65[SIZE * sizeof(short)];
static struct fifo_s fifo_65 = { sizeof(short), SIZE, array_65, 0, 0 };
static char array_66[SIZE * sizeof(short)];
static struct fifo_s fifo_66 = { sizeof(short), SIZE, array_66, 0, 0 };
static char array_67[SIZE * sizeof(int)];
static struct fifo_s fifo_67 = { sizeof(int), SIZE, array_67, 0, 0 };
static char array_68[SIZE * sizeof(int)];
static struct fifo_s fifo_68 = { sizeof(int), SIZE, array_68, 0, 0 };
static char array_69[SIZE * sizeof(int)];
static struct fifo_s fifo_69 = { sizeof(int), SIZE, array_69, 0, 0 };
static char array_70[SIZE * sizeof(short)];
static struct fifo_s fifo_70 = { sizeof(short), SIZE, array_70, 0, 0 };
static char array_71[SIZE * sizeof(short)];
static struct fifo_s fifo_71 = { sizeof(short), SIZE, array_71, 0, 0 };
static char array_72[SIZE * sizeof(short)];
static struct fifo_s fifo_72 = { sizeof(short), SIZE, array_72, 0, 0 };
static char array_73[SIZE * sizeof(char)];
static struct fifo_s fifo_73 = { sizeof(char), SIZE, array_73, 0, 0 };
static char array_74[SIZE * sizeof(char)];
static struct fifo_s fifo_74 = { sizeof(char), SIZE, array_74, 0, 0 };
static char array_75[SIZE * sizeof(char)];
static struct fifo_s fifo_75 = { sizeof(char), SIZE, array_75, 0, 0 };
static char array_76[SIZE * sizeof(char)];
static struct fifo_s fifo_76 = { sizeof(char), SIZE, array_76, 0, 0 };
static char array_77[SIZE * sizeof(short)];
static struct fifo_s fifo_77 = { sizeof(short), SIZE, array_77, 0, 0 };
static char array_78[SIZE * sizeof(short)];
static struct fifo_s fifo_78 = { sizeof(short), SIZE, array_78, 0, 0 };
static char array_79[SIZE * sizeof(short)];
static struct fifo_s fifo_79 = { sizeof(short), SIZE, array_79, 0, 0 };
static char array_80[SIZE * sizeof(short)];
static struct fifo_s fifo_80 = { sizeof(short), SIZE, array_80, 0, 0 };
static char array_81[SIZE * sizeof(short)];
static struct fifo_s fifo_81 = { sizeof(short), SIZE, array_81, 0, 0 };
static char array_82[SIZE * sizeof(short)];
static struct fifo_s fifo_82 = { sizeof(short), SIZE, array_82, 0, 0 };
static char array_83[SIZE * sizeof(short)];
static struct fifo_s fifo_83 = { sizeof(short), SIZE, array_83, 0, 0 };
static char array_84[SIZE * sizeof(short)];
static struct fifo_s fifo_84 = { sizeof(short), SIZE, array_84, 0, 0 };
static char array_85[SIZE * sizeof(short)];
static struct fifo_s fifo_85 = { sizeof(short), SIZE, array_85, 0, 0 };
static char array_86[384 * sizeof(short)];
static struct fifo_s fifo_86 = { sizeof(short), 384, array_86, 0, 0 };
static char array_87[384 * sizeof(short)];
static struct fifo_s fifo_87 = { sizeof(short), 384, array_87, 0, 0 };
static char array_88[384 * sizeof(short)];
static struct fifo_s fifo_88 = { sizeof(short), 384, array_88, 0, 0 };

////////////////////////////////////////////////////////////////////////////////
// FIFO pointer assignments

struct fifo_s *acpred_OUT = &fifo_48;
struct fifo_s *dequant_AC = &fifo_48;
struct fifo_s *add_VID = &fifo_86;
struct fifo_s *broadcast_add_VID_input = &fifo_86;
struct fifo_s *blkexp_OUT = &fifo_8;
struct fifo_s *dcsplit_IN = &fifo_8;
struct fifo_s *broadcast_add_VID_output_0 = &fifo_87;
struct fifo_s *mbpack_DI = &fifo_87;
struct fifo_s *broadcast_add_VID_output_1 = &fifo_88;
struct fifo_s *display_B = &fifo_88;
struct fifo_s *broadcast_dcpred_START_output_0 = &fifo_74;
struct fifo_s *zigzag_START = &fifo_74;
struct fifo_s *broadcast_dcpred_START_output_1 = &fifo_75;
struct fifo_s *zzaddr_START = &fifo_75;
struct fifo_s *broadcast_dcpred_START_output_2 = &fifo_76;
struct fifo_s *acpred_START = &fifo_76;
struct fifo_s *broadcast_fairmerge_ROWOUT_output_0 = &fifo_68;
struct fifo_s *downsample_R = &fifo_68;
struct fifo_s *broadcast_fairmerge_ROWOUT_output_1 = &fifo_69;
struct fifo_s *combine_ROW = &fifo_69;
struct fifo_s *broadcast_mvrecon_MV_output_0 = &fifo_71;
struct fifo_s *searchwin_MV = &fifo_71;
struct fifo_s *broadcast_mvrecon_MV_output_1 = &fifo_72;
struct fifo_s *unpack_MV = &fifo_72;
struct fifo_s *broadcast_parseheaders_BTYPE_output_0 = &fifo_78;
struct fifo_s *memorymanager_BTYPE = &fifo_78;
struct fifo_s *broadcast_parseheaders_BTYPE_output_1 = &fifo_79;
struct fifo_s *mvrecon_BTYPE = &fifo_79;
struct fifo_s *broadcast_parseheaders_BTYPE_output_2 = &fifo_80;
struct fifo_s *dcpred_BTYPE = &fifo_80;
struct fifo_s *broadcast_parseheaders_BTYPE_output_3 = &fifo_81;
struct fifo_s *add_BTYPE = &fifo_81;
struct fifo_s *broadcast_parseheaders_BTYPE_output_4 = &fifo_82;
struct fifo_s *searchwin_BTYPE = &fifo_82;
struct fifo_s *broadcast_parseheaders_BTYPE_output_5 = &fifo_83;
struct fifo_s *unpack_BTYPE = &fifo_83;
struct fifo_s *broadcast_parseheaders_BTYPE_output_6 = &fifo_84;
struct fifo_s *mvseq_BTYPE = &fifo_84;
struct fifo_s *broadcast_parseheaders_BTYPE_output_7 = &fifo_85;
struct fifo_s *seq_BTYPE = &fifo_85;
struct fifo_s *clip_O = &fifo_5;
struct fifo_s *add_TEX = &fifo_5;
struct fifo_s *combine_Y0 = &fifo_13;
struct fifo_s *shufflefly_X0 = &fifo_13;
struct fifo_s *combine_Y1 = &fifo_14;
struct fifo_s *shufflefly_X1 = &fifo_14;
struct fifo_s *dcpred_PTR = &fifo_9;
struct fifo_s *acpred_PTR = &fifo_9;
struct fifo_s *dcpred_SIGNED = &fifo_10;
struct fifo_s *clip_SIGNED = &fifo_10;
struct fifo_s *dcpred_OUT = &fifo_11;
struct fifo_s *dequant_DC = &fifo_11;
struct fifo_s *dcpred_QUANT = &fifo_12;
struct fifo_s *dequant_QP = &fifo_12;
struct fifo_s *dcpred_START = &fifo_73;
struct fifo_s *broadcast_dcpred_START_input = &fifo_73;
struct fifo_s *dcsplit_DC = &fifo_6;
struct fifo_s *dcpred_IN = &fifo_6;
struct fifo_s *dcsplit_AC = &fifo_7;
struct fifo_s *zigzag_AC = &fifo_7;
struct fifo_s *ddr_RD = &fifo_63;
struct fifo_s *searchwin_DI = &fifo_63;
struct fifo_s *dequant_OUT = &fifo_21;
struct fifo_s *rowsort_ROW = &fifo_21;
struct fifo_s *downsample_R2 = &fifo_41;
struct fifo_s *sep_ROW = &fifo_41;
struct fifo_s *fairmerge_Y0 = &fifo_49;
struct fifo_s *scale_X0 = &fifo_49;
struct fifo_s *fairmerge_Y1 = &fifo_50;
struct fifo_s *scale_X1 = &fifo_50;
struct fifo_s *fairmerge_ROWOUT = &fifo_67;
struct fifo_s *broadcast_fairmerge_ROWOUT_input = &fifo_67;
struct fifo_s *final_Y0 = &fifo_44;
struct fifo_s *sep_X0 = &fifo_44;
struct fifo_s *final_Y1 = &fifo_45;
struct fifo_s *sep_X1 = &fifo_45;
struct fifo_s *final_Y2 = &fifo_46;
struct fifo_s *sep_X2 = &fifo_46;
struct fifo_s *final_Y3 = &fifo_47;
struct fifo_s *sep_X3 = &fifo_47;
struct fifo_s *interpolate_MOT = &fifo_22;
struct fifo_s *add_MOT = &fifo_22;
struct fifo_s *mbpack_DO = &fifo_15;
struct fifo_s *ddr_WD = &fifo_15;
struct fifo_s *mbpack_AO = &fifo_16;
struct fifo_s *ddr_WA = &fifo_16;
struct fifo_s *memorymanager_RA = &fifo_39;
struct fifo_s *ddr_RA = &fifo_39;
struct fifo_s *memorymanager_WA = &fifo_40;
struct fifo_s *mbpack_AI = &fifo_40;
struct fifo_s *mvrecon_MV = &fifo_70;
struct fifo_s *broadcast_mvrecon_MV_input = &fifo_70;
struct fifo_s *mvseq_A = &fifo_4;
struct fifo_s *mvrecon_A = &fifo_4;
struct fifo_s *parseheaders_RUN = &fifo_25;
struct fifo_s *blkexp_RUN = &fifo_25;
struct fifo_s *parseheaders_VALUE = &fifo_26;
struct fifo_s *blkexp_VALUE = &fifo_26;
struct fifo_s *parseheaders_LAST = &fifo_27;
struct fifo_s *blkexp_LAST = &fifo_27;
struct fifo_s *parseheaders_WIDTH = &fifo_28;
struct fifo_s *display_WIDTH = &fifo_28;
struct fifo_s *parseheaders_HEIGHT = &fifo_29;
struct fifo_s *display_HEIGHT = &fifo_29;
struct fifo_s *parseheaders_MV = &fifo_30;
struct fifo_s *mvrecon_MVIN = &fifo_30;
struct fifo_s *parseheaders_BTYPE = &fifo_77;
struct fifo_s *broadcast_parseheaders_BTYPE_input = &fifo_77;
struct fifo_s *retrans_Y = &fifo_32;
struct fifo_s *clip_I = &fifo_32;
struct fifo_s *rowsort_Y0 = &fifo_35;
struct fifo_s *fairmerge_R0 = &fifo_35;
struct fifo_s *rowsort_Y1 = &fifo_36;
struct fifo_s *fairmerge_R1 = &fifo_36;
struct fifo_s *scale_Y0 = &fifo_51;
struct fifo_s *combine_X0 = &fifo_51;
struct fifo_s *scale_Y1 = &fifo_52;
struct fifo_s *combine_X1 = &fifo_52;
struct fifo_s *scale_Y2 = &fifo_53;
struct fifo_s *combine_X2 = &fifo_53;
struct fifo_s *scale_Y3 = &fifo_54;
struct fifo_s *combine_X3 = &fifo_54;
struct fifo_s *searchwin_FLAGS = &fifo_33;
struct fifo_s *interpolate_halfpel = &fifo_33;
struct fifo_s *searchwin_DO = &fifo_34;
struct fifo_s *unpack_DI = &fifo_34;
struct fifo_s *sep_C0 = &fifo_55;
struct fifo_s *retrans_X0 = &fifo_55;
struct fifo_s *sep_C1 = &fifo_56;
struct fifo_s *retrans_X1 = &fifo_56;
struct fifo_s *sep_C2 = &fifo_57;
struct fifo_s *retrans_X2 = &fifo_57;
struct fifo_s *sep_C3 = &fifo_58;
struct fifo_s *retrans_X3 = &fifo_58;
struct fifo_s *sep_R0 = &fifo_59;
struct fifo_s *trans_X0 = &fifo_59;
struct fifo_s *sep_R1 = &fifo_60;
struct fifo_s *trans_X1 = &fifo_60;
struct fifo_s *sep_R2 = &fifo_61;
struct fifo_s *trans_X2 = &fifo_61;
struct fifo_s *sep_R3 = &fifo_62;
struct fifo_s *trans_X3 = &fifo_62;
struct fifo_s *seq_A = &fifo_64;
struct fifo_s *dcpred_A = &fifo_64;
struct fifo_s *seq_B = &fifo_65;
struct fifo_s *dcpred_B = &fifo_65;
struct fifo_s *seq_C = &fifo_66;
struct fifo_s *dcpred_C = &fifo_66;
struct fifo_s *serialize_out = &fifo_24;
struct fifo_s *parseheaders_bits = &fifo_24;
struct fifo_s *shuffle_Y0 = &fifo_0;
struct fifo_s *final_X0 = &fifo_0;
struct fifo_s *shuffle_Y1 = &fifo_1;
struct fifo_s *final_X1 = &fifo_1;
struct fifo_s *shuffle_Y2 = &fifo_2;
struct fifo_s *final_X2 = &fifo_2;
struct fifo_s *shuffle_Y3 = &fifo_3;
struct fifo_s *final_X3 = &fifo_3;
struct fifo_s *shufflefly_Y0 = &fifo_17;
struct fifo_s *shuffle_X0 = &fifo_17;
struct fifo_s *shufflefly_Y1 = &fifo_18;
struct fifo_s *shuffle_X1 = &fifo_18;
struct fifo_s *shufflefly_Y2 = &fifo_19;
struct fifo_s *shuffle_X2 = &fifo_19;
struct fifo_s *shufflefly_Y3 = &fifo_20;
struct fifo_s *shuffle_X3 = &fifo_20;
struct fifo_s *source_O = &fifo_31;
struct fifo_s *serialize_in8 = &fifo_31;
struct fifo_s *trans_Y0 = &fifo_37;
struct fifo_s *fairmerge_C0 = &fifo_37;
struct fifo_s *trans_Y1 = &fifo_38;
struct fifo_s *fairmerge_C1 = &fifo_38;
struct fifo_s *unpack_DO = &fifo_43;
struct fifo_s *interpolate_RD = &fifo_43;
struct fifo_s *zigzag_OUT = &fifo_23;
struct fifo_s *acpred_AC = &fifo_23;
struct fifo_s *zzaddr_ADDR = &fifo_42;
struct fifo_s *zigzag_ADDR = &fifo_42;

////////////////////////////////////////////////////////////////////////////////
// Broadcasts

int broadcast_add_VID_scheduler() {
  short *tok_input;
  short *tok_output_0;
  short *tok_output_1;
  
  while (hasTokens(broadcast_add_VID_input, 1) && hasRoom(broadcast_add_VID_output_0, 1)
   && hasRoom(broadcast_add_VID_output_1, 1)) {
    tok_input = getReadPtr(broadcast_add_VID_input, 1);
    tok_output_0 = getWritePtr(broadcast_add_VID_output_0, 1);
    tok_output_1 = getWritePtr(broadcast_add_VID_output_1, 1);
    *tok_output_0 = *tok_input;
    *tok_output_1 = *tok_input;
  }
  
  return 0;
}

int broadcast_dcpred_START_scheduler() {
  char *tok_input;
  char *tok_output_0;
  char *tok_output_1;
  char *tok_output_2;
  
  while (hasTokens(broadcast_dcpred_START_input, 1) && hasRoom(broadcast_dcpred_START_output_0, 1)
   && hasRoom(broadcast_dcpred_START_output_1, 1) && hasRoom(broadcast_dcpred_START_output_2, 1)
  ) {
    tok_input = getReadPtr(broadcast_dcpred_START_input, 1);
    tok_output_0 = getWritePtr(broadcast_dcpred_START_output_0, 1);
    tok_output_1 = getWritePtr(broadcast_dcpred_START_output_1, 1);
    tok_output_2 = getWritePtr(broadcast_dcpred_START_output_2, 1);
    *tok_output_0 = *tok_input;
    *tok_output_1 = *tok_input;
    *tok_output_2 = *tok_input;
  }
  
  return 0;
}

int broadcast_fairmerge_ROWOUT_scheduler() {
  int *tok_input;
  int *tok_output_0;
  int *tok_output_1;
  
  while (hasTokens(broadcast_fairmerge_ROWOUT_input, 1) && hasRoom(broadcast_fairmerge_ROWOUT_output_0, 1)
   && hasRoom(broadcast_fairmerge_ROWOUT_output_1, 1)) {
    tok_input = getReadPtr(broadcast_fairmerge_ROWOUT_input, 1);
    tok_output_0 = getWritePtr(broadcast_fairmerge_ROWOUT_output_0, 1);
    tok_output_1 = getWritePtr(broadcast_fairmerge_ROWOUT_output_1, 1);
    *tok_output_0 = *tok_input;
    *tok_output_1 = *tok_input;
  }
  
  return 0;
}

int broadcast_mvrecon_MV_scheduler() {
  short *tok_input;
  short *tok_output_0;
  short *tok_output_1;
  
  while (hasTokens(broadcast_mvrecon_MV_input, 1) && hasRoom(broadcast_mvrecon_MV_output_0, 1)
   && hasRoom(broadcast_mvrecon_MV_output_1, 1)) {
    tok_input = getReadPtr(broadcast_mvrecon_MV_input, 1);
    tok_output_0 = getWritePtr(broadcast_mvrecon_MV_output_0, 1);
    tok_output_1 = getWritePtr(broadcast_mvrecon_MV_output_1, 1);
    *tok_output_0 = *tok_input;
    *tok_output_1 = *tok_input;
  }
  
  return 0;
}

int broadcast_parseheaders_BTYPE_scheduler() {
  short *tok_input;
  short *tok_output_0;
  short *tok_output_1;
  short *tok_output_2;
  short *tok_output_3;
  short *tok_output_4;
  short *tok_output_5;
  short *tok_output_6;
  short *tok_output_7;
  
  while (hasTokens(broadcast_parseheaders_BTYPE_input, 1) && hasRoom(broadcast_parseheaders_BTYPE_output_0, 1)
   && hasRoom(broadcast_parseheaders_BTYPE_output_1, 1) && hasRoom(broadcast_parseheaders_BTYPE_output_2, 1)
   && hasRoom(broadcast_parseheaders_BTYPE_output_3, 1) && hasRoom(broadcast_parseheaders_BTYPE_output_4, 1)
   && hasRoom(broadcast_parseheaders_BTYPE_output_5, 1) && hasRoom(broadcast_parseheaders_BTYPE_output_6, 1)
   && hasRoom(broadcast_parseheaders_BTYPE_output_7, 1)) {
    tok_input = getReadPtr(broadcast_parseheaders_BTYPE_input, 1);
    tok_output_0 = getWritePtr(broadcast_parseheaders_BTYPE_output_0, 1);
    tok_output_1 = getWritePtr(broadcast_parseheaders_BTYPE_output_1, 1);
    tok_output_2 = getWritePtr(broadcast_parseheaders_BTYPE_output_2, 1);
    tok_output_3 = getWritePtr(broadcast_parseheaders_BTYPE_output_3, 1);
    tok_output_4 = getWritePtr(broadcast_parseheaders_BTYPE_output_4, 1);
    tok_output_5 = getWritePtr(broadcast_parseheaders_BTYPE_output_5, 1);
    tok_output_6 = getWritePtr(broadcast_parseheaders_BTYPE_output_6, 1);
    tok_output_7 = getWritePtr(broadcast_parseheaders_BTYPE_output_7, 1);
    *tok_output_0 = *tok_input;
    *tok_output_1 = *tok_input;
    *tok_output_2 = *tok_input;
    *tok_output_3 = *tok_input;
    *tok_output_4 = *tok_input;
    *tok_output_5 = *tok_input;
    *tok_output_6 = *tok_input;
    *tok_output_7 = *tok_input;
  }
  
  return 0;
}

////////////////////////////////////////////////////////////////////////////////
// Action schedulers

extern int acpred_scheduler();
extern int add_scheduler();
extern int blkexp_scheduler();
extern int clip_scheduler();
extern int combine_scheduler();
extern int dcpred_scheduler();
extern int dcsplit_scheduler();
extern int ddr_scheduler();
extern int dequant_scheduler();
extern int display_scheduler();
extern int downsample_scheduler();
extern int fairmerge_scheduler();
extern int final_scheduler();
extern int interpolate_scheduler();
extern int mbpack_scheduler();
extern int memorymanager_scheduler();
extern int mvrecon_scheduler();
extern int mvseq_scheduler();
extern int parseheaders_scheduler();
extern int retrans_scheduler();
extern int rowsort_scheduler();
extern int scale_scheduler();
extern int searchwin_scheduler();
extern int sep_scheduler();
extern int seq_scheduler();
extern int serialize_scheduler();
extern int shuffle_scheduler();
extern int shufflefly_scheduler();
extern int source_scheduler();
extern int trans_scheduler();
extern int unpack_scheduler();
extern int zigzag_scheduler();
extern int zzaddr_scheduler();

////////////////////////////////////////////////////////////////////////////////
// Actor scheduler

static void scheduler() {
  while (1) {
    acpred_scheduler();
    add_scheduler();
    blkexp_scheduler();
    broadcast_add_VID_scheduler();
    broadcast_dcpred_START_scheduler();
    broadcast_fairmerge_ROWOUT_scheduler();
    broadcast_mvrecon_MV_scheduler();
    broadcast_parseheaders_BTYPE_scheduler();
    clip_scheduler();
    combine_scheduler();
    dcpred_scheduler();
    dcsplit_scheduler();
    ddr_scheduler();
    dequant_scheduler();
    display_scheduler();
    downsample_scheduler();
    fairmerge_scheduler();
    final_scheduler();
    interpolate_scheduler();
    mbpack_scheduler();
    memorymanager_scheduler();
    mvrecon_scheduler();
    mvseq_scheduler();
    parseheaders_scheduler();
    retrans_scheduler();
    rowsort_scheduler();
    scale_scheduler();
    searchwin_scheduler();
    sep_scheduler();
    seq_scheduler();
    serialize_scheduler();
    shuffle_scheduler();
    shufflefly_scheduler();
    source_scheduler();
    trans_scheduler();
    unpack_scheduler();
    zigzag_scheduler();
    zzaddr_scheduler();
  }
}

////////////////////////////////////////////////////////////////////////////////

void pause() {
#ifndef _WIN32
  struct termios oldT, newT;
  char c;
#endif
  printf("Press a key to continue\n");

#ifdef _WIN32
  _getch();
#else
  ioctl(0, TCGETS, &oldT);
  newT.c_lflag &= ~ICANON; // one char @ a time
  ioctl(0, TCSETS, &newT); // set new terminal mode
  read(0, &c, 1); // read 1 char @ a time from stdin
  ioctl(0, TCSETS, &oldT); // restore previous terminal mode
#endif
}

extern void source_set_file_name(const char *file_name);

int main(int argc, char *argv[]) {
  if (argc < 2) {
    printf("No input file provided!\n");
    pause();
    return 1;
  }
  
  source_set_file_name(argv[1]);
  
  scheduler();
  
  printf("End of simulation! Press a key to continue\n");
  pause();
  return 0;
}

