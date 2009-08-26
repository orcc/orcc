// Generated from "parseheaders"

#include <stdio.h>

#include "fifo.h"

////////////////////////////////////////////////////////////////////////////////
// Input FIFOs

extern struct fifo_s *parseheaders_bits;

////////////////////////////////////////////////////////////////////////////////
// Output FIFOs

extern struct fifo_s *parseheaders_BTYPE;
extern struct fifo_s *parseheaders_MV;
extern struct fifo_s *parseheaders_RUN;
extern struct fifo_s *parseheaders_VALUE;
extern struct fifo_s *parseheaders_LAST;
extern struct fifo_s *parseheaders_WIDTH;
extern struct fifo_s *parseheaders_HEIGHT;

////////////////////////////////////////////////////////////////////////////////
// State variables of the actor

static int MAXW_IN_MB = 121;
static int MB_COORD_SZ = 8;
static int BTYPE_SZ = 12;
static int MV_SZ = 9;
static int NEWVOP = 2048;
static int INTRA = 1024;
static int INTER = 512;
static int QUANT_MASK = 31;
static int ROUND_TYPE = 32;
static int FCODE_MASK = 448;
static int FCODE_SHIFT = 6;
static int ACPRED = 1;
static int ACCODED = 2;
static int FOURMV = 4;
static int MOTION = 8;
static int SAMPLE_COUNT_SZ = 8;
static int SAMPLE_SZ = 13;
static int VO_HEADER_LENGTH = 27;
static int VO_NO_SHORT_HEADER = 8;
static int VO_ID_LENGTH = 5;
static int VOL_START_CODE = 32;
static int VOL_ID_LENGTH = 1;
static int VIDEO_OBJECT_TYPE_INDICATION_LENGTH = 8;
static int VISUAL_OBJECT_LAYER_VERID_LENGTH = 4;
static int VISUAL_OBJECT_LAYER_PRIORITY_LENGTH = 3;
static int ASPECT_RATIO_INFO_LENGTH = 4;
static int ASPECT_RATIO_INFO_IS_DETAILED = 15;
static int PAR_WIDTH_LENGTH = 8;
static int PAR_HEIGHT_LENGTH = 8;
static int CHROMA_FORMAT_LENGTH = 2;
static int LOW_DELAY_LENGTH = 1;
static int FIRST_HALF_BIT_RATE_LENGTH = 15;
static int LAST_HALF_BIT_RATE_LENGTH = 15;
static int FIRST_HALF_VBV_BUF_SZ_LENGTH = 15;
static int LAST_HALF_VBV_BUF_SZ_LENGTH = 3;
static int FIRST_HALF_VBV_OCC_LENGTH = 11;
static int LAST_HALF_VBV_OCC_LENGTH = 15;
static int VOL_SHAPE_LENGTH = 2;
static int MARKER_LENGTH = 1;
static int TIME_INC_RES_LENGTH = 16;
static int VOL_WIDTH_LENGTH = 13;
static int VOL_HEIGHT_LENGTH = 13;
static int RUN_LENGTH = 6;
static int RUN_MASK = 63;
static int LEVEL_LENGTH = 12;
static int LEVEL_MASK = 4095;
static int MISC_BIT_LENGTH = 9;
static int VOP_START_CODE = 182;
static int VOP_PREDICTION_LENGTH = 2;
static int B_VOP = 2;
static int P_VOP = 1;
static int I_VOP = 0;
static int INTRA_DC_VLC_THR_LENGTH = 3;
static int VOP_FCODE_FOR_LENGTH = 3;
static int VOP_FCODE_FOR_MASK = 7;
static int BITS_QUANT = 5;
static int BITS_QUANT_MASK = 31;
static int MCBPC_LENGTH = 9;
static int ESCAPE_CODE = 7167;
static char bits_to_read_count = -1;
static int read_result_in_progress;
static char bit_count = 0;
static char mylog;
static char vol_width;
static char vol_height;
static char mbx;
static char mby;
static int prediction_is_IVOP;
static int prediction_is_PVOP;
static int prediction_is_BVOP;
static char comp;
static char fcode;
static int CBP_SZ = 7;
static char cbp;
static int acpredflag;
static int btype_is_INTRA;
static char cbpc;
static int fourmvflag;
static int ac_coded;
static char mvcomp;
static char dc_bits;
static short msb_result;
static int b_last;
static short level_lookup_inter;
static short level_lookup_intra;
static short run_lookup_inter;
static short run_lookup_intra;
static int VLD_TABLE_ADDR_BITS = 12;
static int VLD_TABLE_DATA_BITS = 20;
static int vld_table[760] = {10, 12, 18, 58, 26, 76, 34, 16, 42, 50, 1, 80, 144, 
208, 140, 204, 74, 0, 82, 226, 90, 218, 98, 202, 106, 178, 114, 162, 122, 146, 130, 
138, 1, 1, 208, 144, 154, 140, 80, 196, 170, 204, 76, 200, 186, 194, 136, 72, 132, 
68, 210, 12, 16, 192, 128, 64, 8, 4, 242, 338, 250, 314, 258, 298, 266, 290, 274, 
282, 1, 1, 24, 36, 32, 16, 306, 0, 8, 4, 322, 330, 48, 40, 56, 20, 346, 60, 354, 
362, 52, 12, 44, 28, 378, 466, 386, 458, 394, 16, 402, 20, 410, 24, 418, 28, 426, 
32, 434, 36, 442, 40, 450, 44, 1, 48, 12, 0, 8, 4, 482, 570, 490, 8, 498, 12, 506, 
16, 514, 20, 522, 24, 530, 28, 538, 32, 546, 36, 554, 40, 562, 44, 1, 48, 4, 0, 586, 
1498, 594, 1426, 602, 1338, 610, 1194, 618, 1066, 626, 874, 634, 818, 642, 794, 650, 
770, 658, 714, 666, 690, 674, 682, 1, 1, 1, 1, 698, 706, 1, 1, 1, 1, 722, 746, 730, 
738, 1, 1, 1, 1, 754, 762, 1, 1, 1, 1, 778, 786, 16456, 16396, 44, 40, 802, 810, 
18180, 18116, 18052, 17988, 826, 850, 834, 842, 584, 520, 456, 392, 858, 866, 328, 
204, 140, 80, 882, 28668, 890, 946, 898, 922, 906, 914, 48, 84, 1476, 1540, 930, 
938, 18244, 18308, 18372, 18436, 954, 1010, 962, 986, 970, 978, 88, 144, 268, 332, 
994, 1002, 396, 648, 1604, 1668, 1018, 1042, 1026, 1034, 18500, 18564, 18628, 18692, 
1050, 1058, 18756, 18820, 18884, 18948, 1074, 1138, 1082, 1114, 1090, 1106, 1098, 
17924, 36, 32, 17860, 17796, 1122, 1130, 17732, 17668, 17604, 17540, 1146, 1170, 
1154, 1162, 17476, 16392, 1412, 1348, 1178, 1186, 1284, 1220, 1156, 1092, 1202, 1282, 
1210, 1258, 1218, 1242, 1226, 1234, 1028, 964, 264, 200, 1250, 17412, 28, 24, 1266, 
1274, 17348, 17284, 17220, 17156, 1290, 1314, 1298, 1306, 17092, 17028, 16964, 900, 
1322, 1330, 836, 136, 76, 20, 1346, 1402, 1354, 1378, 1362, 1370, 16900, 16836, 16772, 
16708, 1386, 1394, 772, 708, 644, 16, 1410, 1418, 16644, 16580, 16516, 16452, 1434, 
1482, 1442, 1466, 1450, 1458, 580, 516, 452, 388, 1474, 324, 72, 12, 1490, 16388, 
260, 196, 4, 1506, 68, 1514, 132, 8, 1530, 2442, 1538, 2370, 1546, 2282, 1554, 2138, 
1562, 2010, 1570, 1818, 1578, 1762, 1586, 1738, 1594, 1714, 1602, 1658, 1610, 1634, 
1618, 1626, 1, 1, 1, 1, 1642, 1650, 1, 1, 1, 1, 1666, 1690, 1674, 1682, 1, 1, 1, 
1, 1698, 1706, 1, 1, 1, 1, 1722, 1730, 262172, 262168, 88, 84, 1746, 1754, 264200, 
263180, 262164, 13316, 1770, 1794, 1778, 1786, 5132, 8200, 4108, 3088, 1802, 1810, 
2064, 1052, 80, 76, 1826, 28668, 1834, 1890, 1842, 1866, 1850, 1858, 92, 96, 1056, 
9224, 1874, 1882, 265224, 266248, 277508, 278532, 1898, 1954, 1906, 1930, 1914, 1922, 
100, 104, 108, 1060, 1938, 1946, 6156, 1064, 2068, 7180, 1962, 1986, 1970, 1978, 
14340, 262176, 267272, 268296, 1994, 2002, 279556, 280580, 281604, 282628, 2018, 
2082, 2026, 2058, 2034, 2050, 2042, 276484, 72, 68, 275460, 274436, 2066, 2074, 273412, 
272388, 263176, 262160, 2090, 2114, 2098, 2106, 12292, 11268, 7176, 6152, 2122, 2130, 
5128, 3084, 2060, 1048, 2146, 2226, 2154, 2202, 2162, 2186, 2170, 2178, 1044, 64, 
4104, 60, 2194, 270340, 56, 52, 2210, 2218, 269316, 268292, 262156, 10244, 2234, 
2258, 2242, 2250, 9220, 8196, 271364, 3080, 2266, 2274, 1040, 48, 44, 40, 2290, 2346, 
2298, 2322, 2306, 2314, 266244, 265220, 6148, 267268, 2330, 2338, 7172, 2056, 1036, 
36, 2354, 2362, 262152, 5124, 264196, 263172, 2378, 2426, 2386, 2410, 2394, 2402, 
4100, 3076, 32, 28, 2418, 2052, 1032, 24, 2434, 262148, 20, 16, 4, 2450, 8, 2458, 
1028, 12, 2474, 0, 2482, 3034, 2490, 3026, 2498, 3018, 2506, 2978, 2514, 2890, 2522, 
2770, 2530, 2714, 2538, 2658, 2546, 2634, 2554, 2610, 2562, 2586, 2570, 2578, 1, 
1, 1, 1, 2594, 2602, 1, 1, 1, 1, 2618, 2626, 128, -128, 124, -124, 2642, 2650, 120, 
-120, 116, -116, 2666, 2690, 2674, 2682, 112, -112, 108, -108, 2698, 2706, 104, -104, 
100, -100, 2722, 2746, 2730, 2738, 96, -96, 92, -92, 2754, 2762, 88, -88, 84, -84, 
2778, 2834, 2786, 2810, 2794, 2802, 80, -80, 76, -76, 2818, 2826, 72, -72, 68, -68, 
2842, 2866, 2850, 2858, 64, -64, 60, -60, 2874, 2882, 56, -56, 52, -52, 2898, 2970, 
2906, 2946, 2914, 2938, 2922, 2930, 48, -48, 44, -44, 40, -40, 2954, 2962, 36, -36, 
32, -32, 28, -28, 2986, 3010, 2994, 3002, 24, -24, 20, -20, 16, -16, 12, -12, 8, 
-8, 4, -4};
static int MCBPC_IVOP_START_INDEX = 0;
static int MCBPC_PVOP_START_INDEX = 16;
static int CBPY_START_INDEX = 58;
static int DCBITS_Y_START_INDEX = 92;
static int DCBITS_UV_START_INDEX = 118;
static int COEFF_INTER_START_INDEX = 144;
static int COEFF_INTRA_START_INDEX = 380;
static int MV_START_INDEX = 616;
static short vld_index;
static int vld_codeword = 1;

////////////////////////////////////////////////////////////////////////////////
// Functions/procedures

static int mask_bits(int v, int n) {
  return v & (1 << n) - 1;
}

static int done_reading_bits() {
  char _tmp0_1;
  
  _tmp0_1 = bits_to_read_count;
  return _tmp0_1 < 0;
}

static int read_result() {
  int _tmp0_1;
  
  _tmp0_1 = read_result_in_progress;
  return _tmp0_1;
}

static int intra_max_level(int last, int run) {
  unsigned char _tmp0_1;
  unsigned char _tmp0_2;
  unsigned char _tmp0_3;
  unsigned char _tmp0_4;
  unsigned char _tmp0_5;
  unsigned char _tmp0_6;
  unsigned char _tmp0_7;
  unsigned char _tmp0_8;
  unsigned char _tmp0_9;
  unsigned char _tmp0_10;
  unsigned char _tmp0_11;
  unsigned char _tmp0_12;
  unsigned char _tmp0_13;
  unsigned char _tmp0_14;
  unsigned char _tmp0_15;
  unsigned char _tmp0_16;
  unsigned char _tmp0_17;
  unsigned char _tmp0_18;
  unsigned char _tmp0_19;
  unsigned char _tmp0_20;
  unsigned char _tmp0_21;
  unsigned char _tmp0_22;
  unsigned char _tmp0_23;
  unsigned char _tmp0_24;
  unsigned char _tmp0_25;
  
  if (!last) {
    if (run == 0) {
      _tmp0_1 = 27;
      _tmp0_2 = _tmp0_1;
    } else {
      if (run == 1) {
        _tmp0_3 = 10;
        _tmp0_4 = _tmp0_3;
      } else {
        if (run == 2) {
          _tmp0_5 = 5;
          _tmp0_6 = _tmp0_5;
        } else {
          if (run == 3) {
            _tmp0_7 = 4;
            _tmp0_8 = _tmp0_7;
          } else {
            if (run < 8) {
              _tmp0_9 = 3;
              _tmp0_10 = _tmp0_9;
            } else {
              if (run < 10) {
                _tmp0_11 = 2;
                _tmp0_12 = _tmp0_11;
              } else {
                if (run < 15) {
                  _tmp0_13 = 1;
                  _tmp0_14 = _tmp0_13;
                } else {
                  _tmp0_15 = 0;
                  _tmp0_14 = _tmp0_15;
                }
                _tmp0_12 = _tmp0_14;
              }
              _tmp0_10 = _tmp0_12;
            }
            _tmp0_8 = _tmp0_10;
          }
          _tmp0_6 = _tmp0_8;
        }
        _tmp0_4 = _tmp0_6;
      }
      _tmp0_2 = _tmp0_4;
    }
    _tmp0_16 = _tmp0_2;
  } else {
    if (run == 0) {
      _tmp0_17 = 8;
      _tmp0_18 = _tmp0_17;
    } else {
      if (run == 1) {
        _tmp0_19 = 3;
        _tmp0_20 = _tmp0_19;
      } else {
        if (run < 7) {
          _tmp0_21 = 2;
          _tmp0_22 = _tmp0_21;
        } else {
          if (run < 21) {
            _tmp0_23 = 1;
            _tmp0_24 = _tmp0_23;
          } else {
            _tmp0_25 = 0;
            _tmp0_24 = _tmp0_25;
          }
          _tmp0_22 = _tmp0_24;
        }
        _tmp0_20 = _tmp0_22;
      }
      _tmp0_18 = _tmp0_20;
    }
    _tmp0_16 = _tmp0_18;
  }
  return _tmp0_16;
}

static int inter_max_level(int last, int run) {
  unsigned char _tmp0_1;
  unsigned char _tmp0_2;
  unsigned char _tmp0_3;
  unsigned char _tmp0_4;
  unsigned char _tmp0_5;
  unsigned char _tmp0_6;
  unsigned char _tmp0_7;
  unsigned char _tmp0_8;
  unsigned char _tmp0_9;
  unsigned char _tmp0_10;
  unsigned char _tmp0_11;
  unsigned char _tmp0_12;
  unsigned char _tmp0_13;
  unsigned char _tmp0_14;
  unsigned char _tmp0_15;
  unsigned char _tmp0_16;
  unsigned char _tmp0_17;
  unsigned char _tmp0_18;
  unsigned char _tmp0_19;
  unsigned char _tmp0_20;
  unsigned char _tmp0_21;
  
  if (!last) {
    if (run == 0) {
      _tmp0_1 = 12;
      _tmp0_2 = _tmp0_1;
    } else {
      if (run == 1) {
        _tmp0_3 = 6;
        _tmp0_4 = _tmp0_3;
      } else {
        if (run == 2) {
          _tmp0_5 = 4;
          _tmp0_6 = _tmp0_5;
        } else {
          if (run < 7) {
            _tmp0_7 = 3;
            _tmp0_8 = _tmp0_7;
          } else {
            if (run < 11) {
              _tmp0_9 = 2;
              _tmp0_10 = _tmp0_9;
            } else {
              if (run < 27) {
                _tmp0_11 = 1;
                _tmp0_12 = _tmp0_11;
              } else {
                _tmp0_13 = 0;
                _tmp0_12 = _tmp0_13;
              }
              _tmp0_10 = _tmp0_12;
            }
            _tmp0_8 = _tmp0_10;
          }
          _tmp0_6 = _tmp0_8;
        }
        _tmp0_4 = _tmp0_6;
      }
      _tmp0_2 = _tmp0_4;
    }
    _tmp0_14 = _tmp0_2;
  } else {
    if (run == 0) {
      _tmp0_15 = 3;
      _tmp0_16 = _tmp0_15;
    } else {
      if (run == 1) {
        _tmp0_17 = 2;
        _tmp0_18 = _tmp0_17;
      } else {
        if (run < 41) {
          _tmp0_19 = 1;
          _tmp0_20 = _tmp0_19;
        } else {
          _tmp0_21 = 0;
          _tmp0_20 = _tmp0_21;
        }
        _tmp0_18 = _tmp0_20;
      }
      _tmp0_16 = _tmp0_18;
    }
    _tmp0_14 = _tmp0_16;
  }
  return _tmp0_14;
}

static int intra_max_run(int last, int level) {
  unsigned char _tmp0_1;
  unsigned char _tmp0_2;
  unsigned char _tmp0_3;
  unsigned char _tmp0_4;
  unsigned char _tmp0_5;
  unsigned char _tmp0_6;
  unsigned char _tmp0_7;
  unsigned char _tmp0_8;
  unsigned char _tmp0_9;
  unsigned char _tmp0_10;
  unsigned char _tmp0_11;
  unsigned char _tmp0_12;
  unsigned char _tmp0_13;
  unsigned char _tmp0_14;
  unsigned char _tmp0_15;
  unsigned char _tmp0_16;
  unsigned char _tmp0_17;
  unsigned char _tmp0_18;
  unsigned char _tmp0_19;
  unsigned char _tmp0_20;
  unsigned char _tmp0_21;
  
  if (!last) {
    if (level == 1) {
      _tmp0_1 = 14;
      _tmp0_2 = _tmp0_1;
    } else {
      if (level == 2) {
        _tmp0_3 = 9;
        _tmp0_4 = _tmp0_3;
      } else {
        if (level == 3) {
          _tmp0_5 = 7;
          _tmp0_6 = _tmp0_5;
        } else {
          if (level == 4) {
            _tmp0_7 = 3;
            _tmp0_8 = _tmp0_7;
          } else {
            if (level == 5) {
              _tmp0_9 = 2;
              _tmp0_10 = _tmp0_9;
            } else {
              if (level < 11) {
                _tmp0_11 = 1;
                _tmp0_12 = _tmp0_11;
              } else {
                _tmp0_13 = 0;
                _tmp0_12 = _tmp0_13;
              }
              _tmp0_10 = _tmp0_12;
            }
            _tmp0_8 = _tmp0_10;
          }
          _tmp0_6 = _tmp0_8;
        }
        _tmp0_4 = _tmp0_6;
      }
      _tmp0_2 = _tmp0_4;
    }
    _tmp0_14 = _tmp0_2;
  } else {
    if (level == 1) {
      _tmp0_15 = 20;
      _tmp0_16 = _tmp0_15;
    } else {
      if (level == 2) {
        _tmp0_17 = 6;
        _tmp0_18 = _tmp0_17;
      } else {
        if (level == 3) {
          _tmp0_19 = 1;
          _tmp0_20 = _tmp0_19;
        } else {
          _tmp0_21 = 0;
          _tmp0_20 = _tmp0_21;
        }
        _tmp0_18 = _tmp0_20;
      }
      _tmp0_16 = _tmp0_18;
    }
    _tmp0_14 = _tmp0_16;
  }
  return _tmp0_14;
}

static int inter_max_run(int last, int level) {
  unsigned char _tmp0_1;
  unsigned char _tmp0_2;
  unsigned char _tmp0_3;
  unsigned char _tmp0_4;
  unsigned char _tmp0_5;
  unsigned char _tmp0_6;
  unsigned char _tmp0_7;
  unsigned char _tmp0_8;
  unsigned char _tmp0_9;
  unsigned char _tmp0_10;
  unsigned char _tmp0_11;
  unsigned char _tmp0_12;
  unsigned char _tmp0_13;
  unsigned char _tmp0_14;
  unsigned char _tmp0_15;
  unsigned char _tmp0_16;
  unsigned char _tmp0_17;
  
  if (!last) {
    if (level == 1) {
      _tmp0_1 = 26;
      _tmp0_2 = _tmp0_1;
    } else {
      if (level == 2) {
        _tmp0_3 = 10;
        _tmp0_4 = _tmp0_3;
      } else {
        if (level == 3) {
          _tmp0_5 = 6;
          _tmp0_6 = _tmp0_5;
        } else {
          if (level == 4) {
            _tmp0_7 = 2;
            _tmp0_8 = _tmp0_7;
          } else {
            if (level == 5 || level == 6) {
              _tmp0_9 = 1;
              _tmp0_10 = _tmp0_9;
            } else {
              _tmp0_11 = 0;
              _tmp0_10 = _tmp0_11;
            }
            _tmp0_8 = _tmp0_10;
          }
          _tmp0_6 = _tmp0_8;
        }
        _tmp0_4 = _tmp0_6;
      }
      _tmp0_2 = _tmp0_4;
    }
    _tmp0_12 = _tmp0_2;
  } else {
    if (level == 1) {
      _tmp0_13 = 40;
      _tmp0_14 = _tmp0_13;
    } else {
      if (level == 2) {
        _tmp0_15 = 1;
        _tmp0_16 = _tmp0_15;
      } else {
        _tmp0_17 = 0;
        _tmp0_16 = _tmp0_17;
      }
      _tmp0_14 = _tmp0_16;
    }
    _tmp0_12 = _tmp0_14;
  }
  return _tmp0_12;
}

static int vld_success() {
  int _tmp0_1;
  
  _tmp0_1 = vld_codeword;
  return (_tmp0_1 & 3) == 0;
}

static int vld_failure() {
  int _tmp0_1;
  
  _tmp0_1 = vld_codeword;
  return (_tmp0_1 & 1) == 1;
}

static int vld_result() {
  int _tmp0_1;
  
  _tmp0_1 = vld_codeword;
  return _tmp0_1 >> 2;
}

static void set_bits_to_read(int count) {
  bits_to_read_count = count - 1;
  read_result_in_progress = 0;
}

static void next_mbxy() {
  char _tmp1_1;
  char _tmp2_1;
  char _tmp3_1;
  
  mbx++;
  _tmp1_1 = mbx;
  _tmp2_1 = vol_width;
  if (_tmp1_1 == _tmp2_1) {
    mbx = 0;
    _tmp3_1 = mby;
    mby = _tmp3_1 + 1;
  }
}

static void start_vld_engine(int index_) {
  vld_index = index_;
  vld_codeword = 2;
}


////////////////////////////////////////////////////////////////////////////////
// Actions

static void untagged01() {
  int *bits;
  int b_1;
  int _tmp0_1;
  unsigned char _tmp1_1;
  unsigned char _tmp1_2;
  unsigned char _tmp1_3;
  
  bits = getReadPtr(parseheaders_bits, 1);
  b_1 = bits[0];
  _tmp0_1 = read_result_in_progress;
  if (b_1) {
    _tmp1_1 = 1;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 0;
    _tmp1_2 = _tmp1_3;
  }
  read_result_in_progress = _tmp0_1 << 1 | _tmp1_2;
  bits_to_read_count--;
  bit_count++;
}

static int isSchedulable_untagged01() {
  int *bits;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp2_1 = done_reading_bits();
    _tmp0_1 = !_tmp2_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vol_object_layer_identification() {
  int *bits;
  int b_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  bits = getReadPtr(parseheaders_bits, 1);
  b_1 = bits[0];
  if (b_1) {
    _tmp0_1 = 11;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 4;
    _tmp0_2 = _tmp0_3;
  }
  set_bits_to_read(_tmp0_2);
  bit_count++;
}

static int isSchedulable_vol_object_layer_identification() {
  int *bits;
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp0_1 = done_reading_bits();
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vol_aspect_detailed() {
  set_bits_to_read(16);
}

static int isSchedulable_vol_aspect_detailed() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp4_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = read_result();
  _tmp4_1 = mask_bits(_tmp2_1, 4);
  _tmp0_1 = _tmp1_1 && _tmp4_1 == 15;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vol_control_detailed() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  set_bits_to_read(3);
  bit_count++;
}

static int isSchedulable_vol_control_detailed() {
  int *bits;
  int _tmp1_1;
  int b_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    b_1 = bits[0];
    _tmp2_1 = done_reading_bits();
    _tmp0_1 = _tmp2_1 && b_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vol_vbv_detailed() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  set_bits_to_read(79);
  bit_count++;
}

static int isSchedulable_vol_vbv_detailed() {
  int *bits;
  int _tmp1_1;
  int b_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    b_1 = bits[0];
    _tmp2_1 = done_reading_bits();
    _tmp0_1 = _tmp2_1 && b_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vol_shape() {
  set_bits_to_read(21);
}

static int isSchedulable_vol_shape() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vol_time_inc_res() {
  int _tmp0_1;
  int time_inc_res0_1;
  char count0_1;
  char ones0_1;
  char ones0_2;
  char ones0_3;
  char ones0_4;
  char count0_2;
  char count0_3;
  int time_inc_res0_2;
  int time_inc_res0_3;
  char _tmp2_1;
  char _tmp2_2;
  char _tmp2_3;
  char _tmp3_1;
  char _tmp4_1;
  char _tmp4_2;
  char _tmp5_1;
  char _tmp4_3;
  int _tmp6_1;
  char _tmp8_1;
  char _tmp7_1;
  char _tmp7_2;
  char _tmp7_3;
  
  _tmp0_1 = read_result();
  time_inc_res0_1 = mask_bits(_tmp0_1 >> 2, 16);
  count0_1 = 0;
  ones0_1 = 0;
  ones0_4 = ones0_1;
  count0_3 = count0_1;
  time_inc_res0_3 = time_inc_res0_1;
  while (count0_3 == 0 || time_inc_res0_3 != 0) {
    if ((time_inc_res0_3 & 1) == 1) {
      ones0_2 = ones0_4 + 1;
      ones0_3 = ones0_2;
    } else {
      ones0_3 = ones0_4;
    }
    count0_2 = count0_3 + 1;
    time_inc_res0_2 = time_inc_res0_3 >> 1;
    ones0_4 = ones0_3;
    count0_3 = count0_2;
    time_inc_res0_3 = time_inc_res0_2;
  }
  if (ones0_4 > 1) {
    _tmp2_1 = count0_3;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp2_3 = count0_3 - 1;
    _tmp2_2 = _tmp2_3;
  }
  mylog = _tmp2_2;
  _tmp3_1 = mylog;
  if (_tmp3_1 < 1) {
    _tmp4_1 = 1;
    _tmp4_2 = _tmp4_1;
  } else {
    _tmp5_1 = mylog;
    _tmp4_3 = _tmp5_1;
    _tmp4_2 = _tmp4_3;
  }
  mylog = _tmp4_2;
  _tmp6_1 = read_result();
  if ((_tmp6_1 & 1) == 1) {
    _tmp8_1 = mylog;
    _tmp7_1 = _tmp8_1;
    _tmp7_2 = _tmp7_1;
  } else {
    _tmp7_3 = 0;
    _tmp7_2 = _tmp7_3;
  }
  set_bits_to_read(_tmp7_2 + 1 + 13 + 1);
}

static int isSchedulable_vol_time_inc_res() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void set_vol_width() {
  int _tmp0_1;
  int _tmp3_1;
  
  _tmp0_1 = read_result();
  _tmp3_1 = mask_bits(_tmp0_1 >> 5, 9);
  vol_width = _tmp3_1;
  set_bits_to_read(14);
}

static int isSchedulable_set_vol_width() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void set_vol_height() {
  int _tmp0_1;
  int _tmp3_1;
  
  _tmp0_1 = read_result();
  _tmp3_1 = mask_bits(_tmp0_1 >> 5, 13);
  vol_height = _tmp3_1;
  set_bits_to_read(9);
}

static int isSchedulable_set_vol_height() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void byte_align() {
  char _tmp0_1;
  
  _tmp0_1 = bit_count;
  set_bits_to_read(8 - (_tmp0_1 & 7));
}

static int isSchedulable_byte_align() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void vop_predict_supported() {
  int _tmp0_1;
  int _tmp2_1;
  int _tmp4_1;
  int _tmp6_1;
  int _tmp8_1;
  int _tmp10_1;
  
  _tmp0_1 = read_result();
  _tmp2_1 = mask_bits(_tmp0_1, 2);
  prediction_is_IVOP = _tmp2_1 == 0;
  _tmp4_1 = read_result();
  _tmp6_1 = mask_bits(_tmp4_1, 2);
  prediction_is_PVOP = _tmp6_1 == 1;
  _tmp8_1 = read_result();
  _tmp10_1 = mask_bits(_tmp8_1, 2);
  prediction_is_BVOP = _tmp10_1 == 2;
}

static int isSchedulable_vop_predict_supported() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp4_1;
  int _tmp6_1;
  int _tmp8_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = read_result();
  _tmp4_1 = mask_bits(_tmp2_1, 2);
  _tmp6_1 = read_result();
  _tmp8_1 = mask_bits(_tmp6_1, 2);
  _tmp0_1 = _tmp1_1 && (_tmp4_1 == 0 || _tmp8_1 == 1);
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vop_timebase_one() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  bit_count++;
}

static int isSchedulable_vop_timebase_one() {
  int *bits;
  int _tmp1_1;
  int b_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    b_1 = bits[0];
    _tmp0_1 = b_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vop_timebase_zero() {
  int *bits;
  char _tmp2_1;
  
  bits = getReadPtr(parseheaders_bits, 1);
  bit_count++;
  _tmp2_1 = mylog;
  set_bits_to_read(1 + _tmp2_1 + 1);
}

static int isSchedulable_vop_timebase_zero() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vop_coding_uncoded() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  comp = 0;
  bit_count++;
}

static int isSchedulable_vop_coding_uncoded() {
  int *bits;
  int _tmp1_1;
  int b_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    b_1 = bits[0];
    _tmp2_1 = done_reading_bits();
    _tmp0_1 = _tmp2_1 && !b_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vop_coding_coded() {
  int *bits;
  int _tmp0_1;
  int _tmp1_1;
  int _tmp1_2;
  int _tmp1_3;
  
  bits = getReadPtr(parseheaders_bits, 1);
  _tmp0_1 = prediction_is_IVOP;
  if (!_tmp0_1) {
    _tmp1_1 = 12;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 8;
    _tmp1_2 = _tmp1_3;
  }
  set_bits_to_read(_tmp1_2);
  bit_count++;
}

static int isSchedulable_vop_coding_coded() {
  int *bits;
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp0_1 = done_reading_bits();
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void send_new_vop_cmd() {
  short *BTYPE;
  int round0_1;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp2_2;
  int _tmp2_3;
  short cmd0_1;
  int _tmp5_1;
  int _tmp6_1;
  int round0_2;
  int round0_3;
  int _tmp10_1;
  char vop_quant0_1;
  char vop_quant0_2;
  int _tmp13_1;
  int _tmp15_1;
  char vop_quant0_3;
  short cmd0_2;
  int _tmp18_1;
  int _tmp18_2;
  int _tmp18_3;
  short cmd0_3;
  char _tmp20_1;
  short cmd0_4;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  round0_1 = 0;
  _tmp1_1 = prediction_is_IVOP;
  if (_tmp1_1) {
    _tmp2_1 = 1024;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp2_3 = 512;
    _tmp2_2 = _tmp2_3;
  }
  cmd0_1 = 2048 | _tmp2_2;
  _tmp5_1 = prediction_is_IVOP;
  if (!_tmp5_1) {
    _tmp6_1 = read_result();
    round0_2 = (_tmp6_1 >> 11 & 1) == 1;
    _tmp10_1 = read_result();
    vop_quant0_1 = _tmp10_1 >> 3 & 31;
    _tmp13_1 = read_result();
    fcode = _tmp13_1 & 7;
    round0_3 = round0_2;
    vop_quant0_2 = vop_quant0_1;
  } else {
    _tmp15_1 = read_result();
    vop_quant0_3 = _tmp15_1 & 31;
    fcode = 0;
    round0_3 = round0_1;
    vop_quant0_2 = vop_quant0_3;
  }
  cmd0_2 = cmd0_1 | vop_quant0_2 & 31;
  if (round0_3) {
    _tmp18_1 = 32;
    _tmp18_2 = _tmp18_1;
  } else {
    _tmp18_3 = 0;
    _tmp18_2 = _tmp18_3;
  }
  cmd0_3 = cmd0_2 | _tmp18_2;
  _tmp20_1 = fcode;
  cmd0_4 = cmd0_3 | _tmp20_1 << 6 & 448;
  BTYPE[0] = cmd0_4;
}

static int isSchedulable_send_new_vop_cmd() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void send_new_vop_width() {
  short *WIDTH;
  short *BTYPE;
  char _tmp0_1;
  char _tmp1_1;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  WIDTH = getWritePtr(parseheaders_WIDTH, 1);
  _tmp0_1 = vol_width;
  BTYPE[0] = _tmp0_1;
  _tmp1_1 = vol_width;
  WIDTH[0] = _tmp1_1;
}

static int isSchedulable_send_new_vop_width() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void send_new_vop_height() {
  short *HEIGHT;
  short *BTYPE;
  char _tmp0_1;
  char _tmp1_1;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  HEIGHT = getWritePtr(parseheaders_HEIGHT, 1);
  _tmp0_1 = vol_height;
  BTYPE[0] = _tmp0_1;
  _tmp1_1 = vol_height;
  HEIGHT[0] = _tmp1_1;
}

static int isSchedulable_send_new_vop_height() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void mb_done() {
}

static int isSchedulable_mb_done() {
  char _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = mby;
  _tmp2_1 = vol_height;
  _tmp0_1 = _tmp1_1 == _tmp2_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void get_mcbpc_ivop() {
  start_vld_engine(0);
}

static int isSchedulable_get_mcbpc_ivop() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = prediction_is_IVOP;
  _tmp0_1 = _tmp1_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void get_mcbpc_pvop() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  start_vld_engine(16);
  bit_count++;
}

static int isSchedulable_get_mcbpc_pvop() {
  int *bits;
  int _tmp1_1;
  int b_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    b_1 = bits[0];
    _tmp2_1 = prediction_is_IVOP;
    _tmp0_1 = !_tmp2_1 && !b_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void mcbpc_pvop_uncoded() {
  short *BTYPE;
  int *bits;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  bits = getReadPtr(parseheaders_bits, 1);
  next_mbxy();
  bit_count++;
  BTYPE[0] = 512;
}

static int isSchedulable_mcbpc_pvop_uncoded() {
  int *bits;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp2_1 = prediction_is_IVOP;
    _tmp0_1 = !_tmp2_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void mcbpc_pvop_uncoded1() {
  short *BTYPE;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  BTYPE[0] = 512;
}

static int isSchedulable_mcbpc_pvop_uncoded1() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_mbtype_noac() {
  int mcbpc0_1;
  int type0_1;
  
  mcbpc0_1 = vld_result();
  type0_1 = mcbpc0_1 & 7;
  btype_is_INTRA = type0_1 >= 3;
  fourmvflag = type0_1 == 2;
  cbpc = mcbpc0_1 >> 4 & 3;
  acpredflag = 0;
  start_vld_engine(58);
}

static int isSchedulable_get_mbtype_noac() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = vld_success();
  _tmp2_1 = vld_result();
  _tmp3_1 = vld_result();
  _tmp0_1 = _tmp1_1 && (_tmp2_1 & 7) != 3 && (_tmp3_1 & 7) != 4;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void get_mbtype_ac() {
  int *bits;
  int b_1;
  int mcbpc0_1;
  
  bits = getReadPtr(parseheaders_bits, 1);
  b_1 = bits[0];
  mcbpc0_1 = vld_result();
  btype_is_INTRA = 1;
  cbpc = mcbpc0_1 >> 4 & 3;
  acpredflag = b_1;
  bit_count++;
  start_vld_engine(58);
}

static int isSchedulable_get_mbtype_ac() {
  int *bits;
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp0_1 = vld_success();
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void final_cbpy_inter() {
  int _tmp0_1;
  int cbpy0_1;
  char _tmp1_1;
  
  _tmp0_1 = vld_result();
  cbpy0_1 = 15 - _tmp0_1;
  comp = 0;
  mvcomp = 0;
  _tmp1_1 = cbpc;
  cbp = cbpy0_1 << 2 | _tmp1_1;
}

static int isSchedulable_final_cbpy_inter() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = vld_success();
  _tmp2_1 = btype_is_INTRA;
  _tmp0_1 = _tmp1_1 && !_tmp2_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void final_cbpy_intra() {
  int cbpy0_1;
  char _tmp0_1;
  
  cbpy0_1 = vld_result();
  comp = 0;
  _tmp0_1 = cbpc;
  cbp = cbpy0_1 << 2 | _tmp0_1;
}

static int isSchedulable_final_cbpy_intra() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = vld_success();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void mb_dispatch_done() {
  next_mbxy();
}

static int isSchedulable_mb_dispatch_done() {
  char _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = comp;
  _tmp0_1 = _tmp1_1 == 6;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void mb_dispatch_intra() {
  short *BTYPE;
  char _tmp1_1;
  char _tmp2_1;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp4_2;
  int _tmp4_3;
  short cmd0_2;
  int _tmp6_1;
  int _tmp7_1;
  int _tmp7_2;
  int _tmp7_3;
  short cmd0_3;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  _tmp1_1 = cbp;
  _tmp2_1 = comp;
  ac_coded = (_tmp1_1 & 1 << (5 - _tmp2_1)) != 0;
  _tmp3_1 = ac_coded;
  if (_tmp3_1) {
    _tmp4_1 = 2;
    _tmp4_2 = _tmp4_1;
  } else {
    _tmp4_3 = 0;
    _tmp4_2 = _tmp4_3;
  }
  cmd0_2 = 1024 | _tmp4_2;
  _tmp6_1 = acpredflag;
  if (_tmp6_1) {
    _tmp7_1 = 1;
    _tmp7_2 = _tmp7_1;
  } else {
    _tmp7_3 = 0;
    _tmp7_2 = _tmp7_3;
  }
  cmd0_3 = cmd0_2 | _tmp7_2;
  BTYPE[0] = cmd0_3;
}

static int isSchedulable_mb_dispatch_intra() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = btype_is_INTRA;
  _tmp0_1 = _tmp1_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void mb_dispatch_inter_no_ac() {
  short *BTYPE;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp4_2;
  int _tmp4_3;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  ac_coded = 0;
  comp++;
  _tmp3_1 = fourmvflag;
  if (_tmp3_1) {
    _tmp4_1 = 4;
    _tmp4_2 = _tmp4_1;
  } else {
    _tmp4_3 = 0;
    _tmp4_2 = _tmp4_3;
  }
  BTYPE[0] = 512 | 8 | _tmp4_2;
}

static int isSchedulable_mb_dispatch_inter_no_ac() {
  char _tmp1_1;
  char _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = cbp;
  _tmp2_1 = comp;
  _tmp0_1 = (_tmp1_1 & 1 << (5 - _tmp2_1)) == 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void mb_dispatch_inter_ac_coded() {
  short *BTYPE;
  int _tmp3_1;
  int _tmp4_1;
  int _tmp4_2;
  int _tmp4_3;
  
  BTYPE = getWritePtr(parseheaders_BTYPE, 1);
  ac_coded = 1;
  _tmp3_1 = fourmvflag;
  if (_tmp3_1) {
    _tmp4_1 = 4;
    _tmp4_2 = _tmp4_1;
  } else {
    _tmp4_3 = 0;
    _tmp4_2 = _tmp4_3;
  }
  BTYPE[0] = 514 | 8 | _tmp4_2;
}

static int isSchedulable_mb_dispatch_inter_ac_coded() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void vld_start_intra() {
  char _tmp0_1;
  int _tmp1_1;
  int _tmp1_2;
  int _tmp1_3;
  
  _tmp0_1 = comp;
  if (_tmp0_1 < 4) {
    _tmp1_1 = 92;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 118;
    _tmp1_2 = _tmp1_3;
  }
  start_vld_engine(_tmp1_2);
  b_last = 0;
}

static int isSchedulable_vld_start_intra() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = btype_is_INTRA;
  _tmp0_1 = _tmp1_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vld_start_inter_ac_coded() {
  b_last = 0;
}

static int isSchedulable_vld_start_inter_ac_coded() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = ac_coded;
  _tmp0_1 = _tmp1_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vld_start_inter_not_ac_coded() {
  int *LAST;
  short *VALUE;
  char *RUN;
  
  RUN = getWritePtr(parseheaders_RUN, 1);
  VALUE = getWritePtr(parseheaders_VALUE, 1);
  LAST = getWritePtr(parseheaders_LAST, 1);
  b_last = 1;
  RUN[0] = 0;
  VALUE[0] = 0;
  LAST[0] = 1;
}

static int isSchedulable_vld_start_inter_not_ac_coded() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_dc_bits_none() {
  int *LAST;
  short *VALUE;
  char *RUN;
  int _tmp0_1;
  int _tmp1_1;
  
  RUN = getWritePtr(parseheaders_RUN, 1);
  VALUE = getWritePtr(parseheaders_VALUE, 1);
  LAST = getWritePtr(parseheaders_LAST, 1);
  _tmp0_1 = ac_coded;
  b_last = !_tmp0_1;
  RUN[0] = 0;
  VALUE[0] = 0;
  _tmp1_1 = ac_coded;
  LAST[0] = !_tmp1_1;
}

static int isSchedulable_get_dc_bits_none() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = vld_success();
  _tmp2_1 = vld_result();
  _tmp0_1 = _tmp1_1 && _tmp2_1 == 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void get_dc_bits_some() {
  int _tmp0_1;
  char _tmp1_1;
  
  _tmp0_1 = vld_result();
  dc_bits = _tmp0_1;
  _tmp1_1 = dc_bits;
  set_bits_to_read(_tmp1_1);
}

static int isSchedulable_get_dc_bits_some() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = vld_success();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void dc_bits_shift() {
  char _tmp0_1;
  char count0_1;
  short shift0_1;
  short shift0_2;
  short shift0_3;
  char count0_2;
  char count0_3;
  
  _tmp0_1 = dc_bits;
  count0_1 = _tmp0_1;
  shift0_1 = 1;
  shift0_3 = shift0_1;
  count0_3 = count0_1;
  while (count0_3 > 1) {
    shift0_2 = shift0_3 << 1;
    count0_2 = count0_3 - 1;
    shift0_3 = shift0_2;
    count0_3 = count0_2;
  }
  msb_result = shift0_3;
}

static int isSchedulable_dc_bits_shift() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void get_dc() {
  int *LAST;
  short *VALUE;
  char *RUN;
  short v0_1;
  short _tmp0_1;
  short _tmp1_1;
  short v0_2;
  short v0_3;
  char _tmp2_1;
  int _tmp3_1;
  int _tmp3_2;
  int _tmp3_3;
  int _tmp5_1;
  int _tmp6_1;
  
  RUN = getWritePtr(parseheaders_RUN, 1);
  VALUE = getWritePtr(parseheaders_VALUE, 1);
  LAST = getWritePtr(parseheaders_LAST, 1);
  v0_1 = read_result();
  _tmp0_1 = msb_result;
  if ((v0_1 & _tmp0_1) == 0) {
    _tmp1_1 = msb_result;
    v0_2 = v0_1 + 1 - (_tmp1_1 << 1);
    v0_3 = v0_2;
  } else {
    v0_3 = v0_1;
  }
  _tmp2_1 = dc_bits;
  if (_tmp2_1 > 8) {
    _tmp3_1 = 1;
    _tmp3_2 = _tmp3_1;
  } else {
    _tmp3_3 = 0;
    _tmp3_2 = _tmp3_3;
  }
  set_bits_to_read(_tmp3_2);
  _tmp5_1 = ac_coded;
  b_last = !_tmp5_1;
  RUN[0] = 0;
  VALUE[0] = v0_3;
  _tmp6_1 = ac_coded;
  LAST[0] = !_tmp6_1;
}

static int isSchedulable_get_dc() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void block_done() {
  comp++;
}

static int isSchedulable_block_done() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = b_last;
  _tmp0_1 = _tmp1_1 && _tmp2_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void dct_coeff() {
  int _tmp0_1;
  int _tmp1_1;
  int _tmp1_2;
  int _tmp1_3;
  
  _tmp0_1 = btype_is_INTRA;
  if (_tmp0_1) {
    _tmp1_1 = 380;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 144;
    _tmp1_2 = _tmp1_3;
  }
  start_vld_engine(_tmp1_2);
}

static int isSchedulable_dct_coeff() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vld_code() {
  int *LAST;
  char *RUN;
  short *VALUE;
  int *bits;
  int sign_1;
  int val0_1;
  int _tmp0_1;
  char run0_1;
  char run0_2;
  char run0_3;
  int _tmp1_1;
  int last0_1;
  int last0_2;
  int last0_3;
  int _tmp2_1;
  short level0_1;
  short level0_2;
  short level0_3;
  short _tmp4_1;
  short _tmp4_2;
  short _tmp4_3;
  
  VALUE = getWritePtr(parseheaders_VALUE, 1);
  RUN = getWritePtr(parseheaders_RUN, 1);
  LAST = getWritePtr(parseheaders_LAST, 1);
  bits = getReadPtr(parseheaders_bits, 1);
  sign_1 = bits[0];
  val0_1 = vld_result();
  _tmp0_1 = btype_is_INTRA;
  if (_tmp0_1) {
    run0_1 = val0_1 >> 8 & 255;
    run0_2 = run0_1;
  } else {
    run0_3 = val0_1 >> 4 & 255;
    run0_2 = run0_3;
  }
  _tmp1_1 = btype_is_INTRA;
  if (_tmp1_1) {
    last0_1 = (val0_1 >> 16 & 1) != 0;
    last0_2 = last0_1;
  } else {
    last0_3 = (val0_1 >> 12 & 1) != 0;
    last0_2 = last0_3;
  }
  _tmp2_1 = btype_is_INTRA;
  if (_tmp2_1) {
    level0_1 = val0_1 & 255;
    level0_2 = level0_1;
  } else {
    level0_3 = val0_1 & 15;
    level0_2 = level0_3;
  }
  b_last = last0_2;
  bit_count++;
  if (sign_1) {
    _tmp4_1 = -level0_2;
    _tmp4_2 = _tmp4_1;
  } else {
    _tmp4_3 = level0_2;
    _tmp4_2 = _tmp4_3;
  }
  VALUE[0] = _tmp4_2;
  RUN[0] = run0_2;
  LAST[0] = last0_2;
}

static int isSchedulable_vld_code() {
  int *bits;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp2_1 = vld_success();
    _tmp3_1 = vld_result();
    _tmp0_1 = _tmp2_1 && _tmp3_1 != 7167;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vld_level() {
  int *bits;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp2_2;
  int _tmp2_3;
  
  bits = getReadPtr(parseheaders_bits, 1);
  bit_count++;
  _tmp1_1 = btype_is_INTRA;
  if (_tmp1_1) {
    _tmp2_1 = 380;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp2_3 = 144;
    _tmp2_2 = _tmp2_3;
  }
  start_vld_engine(_tmp2_2);
}

static int isSchedulable_vld_level() {
  int *bits;
  int _tmp1_1;
  int level_offset_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    level_offset_1 = bits[0];
    _tmp2_1 = vld_success();
    _tmp0_1 = _tmp2_1 && !level_offset_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vld_run_or_direct() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  bit_count++;
}

static int isSchedulable_vld_run_or_direct() {
  int *bits;
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp0_1 = vld_success();
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vld_run() {
  int *bits;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp2_2;
  int _tmp2_3;
  
  bits = getReadPtr(parseheaders_bits, 1);
  bit_count++;
  _tmp1_1 = btype_is_INTRA;
  if (_tmp1_1) {
    _tmp2_1 = 380;
    _tmp2_2 = _tmp2_1;
  } else {
    _tmp2_3 = 144;
    _tmp2_2 = _tmp2_3;
  }
  start_vld_engine(_tmp2_2);
}

static int isSchedulable_vld_run() {
  int *bits;
  int _tmp1_1;
  int run_offset_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    run_offset_1 = bits[0];
    _tmp0_1 = !run_offset_1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vld_direct_read() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  bit_count++;
  set_bits_to_read(21);
}

static int isSchedulable_vld_direct_read() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void vld_direct() {
  int *LAST;
  char *RUN;
  short *VALUE;
  int _tmp0_1;
  int last0_1;
  int _tmp5_1;
  char run0_1;
  int _tmp10_1;
  short level0_1;
  int sign0_1;
  int sign0_2;
  short level0_2;
  short level0_3;
  int sign0_3;
  short _tmp13_1;
  short _tmp13_2;
  short _tmp13_3;
  
  VALUE = getWritePtr(parseheaders_VALUE, 1);
  RUN = getWritePtr(parseheaders_RUN, 1);
  LAST = getWritePtr(parseheaders_LAST, 1);
  _tmp0_1 = read_result();
  last0_1 = (_tmp0_1 >> 20 & 1) != 0;
  _tmp5_1 = read_result();
  run0_1 = _tmp5_1 >> 14 & 63;
  _tmp10_1 = read_result();
  level0_1 = _tmp10_1 >> 1 & 4095;
  if (level0_1 >= 2048) {
    sign0_1 = 1;
    level0_2 = 4096 - level0_1;
    sign0_2 = sign0_1;
    level0_3 = level0_2;
  } else {
    sign0_3 = 0;
    sign0_2 = sign0_3;
    level0_3 = level0_1;
  }
  b_last = last0_1;
  if (sign0_2) {
    _tmp13_1 = -level0_3;
    _tmp13_2 = _tmp13_1;
  } else {
    _tmp13_3 = level0_3;
    _tmp13_2 = _tmp13_3;
  }
  VALUE[0] = _tmp13_2;
  RUN[0] = run0_1;
  LAST[0] = last0_1;
}

static int isSchedulable_vld_direct() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void do_level_lookup() {
  int val0_1;
  int _tmp0_1;
  int _tmp1_1;
  
  val0_1 = vld_result();
  _tmp0_1 = inter_max_level((val0_1 >> 12 & 1) != 0, val0_1 >> 4 & 255);
  level_lookup_inter = _tmp0_1;
  _tmp1_1 = intra_max_level((val0_1 >> 16 & 1) != 0, val0_1 >> 8 & 255);
  level_lookup_intra = _tmp1_1;
}

static int isSchedulable_do_level_lookup() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = vld_success();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vld_level_lookup() {
  int *LAST;
  char *RUN;
  short *VALUE;
  int *bits;
  int sign_1;
  int val0_1;
  int _tmp0_1;
  char run0_1;
  char run0_2;
  char run0_3;
  int _tmp1_1;
  int last0_1;
  int last0_2;
  int last0_3;
  int _tmp2_1;
  short _tmp3_1;
  short level0_1;
  short level0_2;
  short _tmp4_1;
  short level0_3;
  short _tmp6_1;
  short _tmp6_2;
  short _tmp6_3;
  
  VALUE = getWritePtr(parseheaders_VALUE, 1);
  RUN = getWritePtr(parseheaders_RUN, 1);
  LAST = getWritePtr(parseheaders_LAST, 1);
  bits = getReadPtr(parseheaders_bits, 1);
  sign_1 = bits[0];
  val0_1 = vld_result();
  _tmp0_1 = btype_is_INTRA;
  if (_tmp0_1) {
    run0_1 = val0_1 >> 8 & 255;
    run0_2 = run0_1;
  } else {
    run0_3 = val0_1 >> 4 & 255;
    run0_2 = run0_3;
  }
  _tmp1_1 = btype_is_INTRA;
  if (_tmp1_1) {
    last0_1 = (val0_1 >> 16 & 1) != 0;
    last0_2 = last0_1;
  } else {
    last0_3 = (val0_1 >> 12 & 1) != 0;
    last0_2 = last0_3;
  }
  _tmp2_1 = btype_is_INTRA;
  if (_tmp2_1) {
    _tmp3_1 = level_lookup_intra;
    level0_1 = (val0_1 & 255) + _tmp3_1;
    level0_2 = level0_1;
  } else {
    _tmp4_1 = level_lookup_inter;
    level0_3 = (val0_1 & 15) + _tmp4_1;
    level0_2 = level0_3;
  }
  b_last = last0_2;
  bit_count++;
  if (sign_1) {
    _tmp6_1 = -level0_2;
    _tmp6_2 = _tmp6_1;
  } else {
    _tmp6_3 = level0_2;
    _tmp6_2 = _tmp6_3;
  }
  VALUE[0] = _tmp6_2;
  RUN[0] = run0_2;
  LAST[0] = last0_2;
}

static int isSchedulable_vld_level_lookup() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void do_run_lookup() {
  int val0_1;
  int _tmp0_1;
  int _tmp1_1;
  
  val0_1 = vld_result();
  _tmp0_1 = inter_max_run((val0_1 >> 12 & 1) != 0, val0_1 & 15);
  run_lookup_inter = _tmp0_1;
  _tmp1_1 = intra_max_run((val0_1 >> 16 & 1) != 0, val0_1 & 255);
  run_lookup_intra = _tmp1_1;
}

static int isSchedulable_do_run_lookup() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = vld_success();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void vld_run_lookup() {
  int *LAST;
  char *RUN;
  short *VALUE;
  int *bits;
  int sign_1;
  int val0_1;
  int _tmp0_1;
  int last0_1;
  int last0_2;
  int last0_3;
  int _tmp1_1;
  short level0_1;
  short level0_2;
  short level0_3;
  int _tmp2_1;
  short _tmp4_1;
  int _tmp3_1;
  int _tmp3_2;
  short _tmp5_1;
  int _tmp3_3;
  char run0_1;
  short _tmp7_1;
  short _tmp7_2;
  short _tmp7_3;
  
  VALUE = getWritePtr(parseheaders_VALUE, 1);
  RUN = getWritePtr(parseheaders_RUN, 1);
  LAST = getWritePtr(parseheaders_LAST, 1);
  bits = getReadPtr(parseheaders_bits, 1);
  sign_1 = bits[0];
  val0_1 = vld_result();
  _tmp0_1 = btype_is_INTRA;
  if (_tmp0_1) {
    last0_1 = (val0_1 >> 16 & 1) != 0;
    last0_2 = last0_1;
  } else {
    last0_3 = (val0_1 >> 12 & 1) != 0;
    last0_2 = last0_3;
  }
  _tmp1_1 = btype_is_INTRA;
  if (_tmp1_1) {
    level0_1 = val0_1 & 255;
    level0_2 = level0_1;
  } else {
    level0_3 = val0_1 & 15;
    level0_2 = level0_3;
  }
  _tmp2_1 = btype_is_INTRA;
  if (_tmp2_1) {
    _tmp4_1 = run_lookup_intra;
    _tmp3_1 = (val0_1 >> 8 & 255) + _tmp4_1;
    _tmp3_2 = _tmp3_1;
  } else {
    _tmp5_1 = run_lookup_inter;
    _tmp3_3 = (val0_1 >> 4 & 255) + _tmp5_1;
    _tmp3_2 = _tmp3_3;
  }
  run0_1 = _tmp3_2 + 1;
  b_last = last0_2;
  bit_count++;
  if (sign_1) {
    _tmp7_1 = -level0_2;
    _tmp7_2 = _tmp7_1;
  } else {
    _tmp7_3 = level0_2;
    _tmp7_2 = _tmp7_3;
  }
  VALUE[0] = _tmp7_2;
  RUN[0] = run0_1;
  LAST[0] = last0_2;
}

static int isSchedulable_vld_run_lookup() {
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    _tmp0_1 = 1;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void mvcode_done() {
}

static int isSchedulable_mvcode_done() {
  char _tmp1_1;
  char _tmp2_1;
  int _tmp3_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = mvcomp;
  _tmp2_1 = mvcomp;
  _tmp3_1 = fourmvflag;
  _tmp0_1 = _tmp1_1 == 4 || _tmp2_1 == 1 && !_tmp3_1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void mvcode() {
  start_vld_engine(616);
}

static int isSchedulable_mvcode() {
  int _tmp0_2;
  
  _tmp0_2 = 1;
  return _tmp0_2;
}

static void mag_x() {
  short *MV;
  int mvval0_1;
  char _tmp0_1;
  char _tmp1_1;
  char _tmp1_2;
  char _tmp2_1;
  char _tmp1_3;
  
  MV = getWritePtr(parseheaders_MV, 1);
  mvval0_1 = vld_result();
  _tmp0_1 = fcode;
  if (_tmp0_1 <= 1 || mvval0_1 == 0) {
    _tmp1_1 = 0;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp2_1 = fcode;
    _tmp1_3 = _tmp2_1 - 1;
    _tmp1_2 = _tmp1_3;
  }
  set_bits_to_read(_tmp1_2);
  MV[0] = mvval0_1;
}

static int isSchedulable_mag_x() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = vld_success();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void get_residual_x() {
  short *MV;
  int _tmp0_1;
  
  MV = getWritePtr(parseheaders_MV, 1);
  _tmp0_1 = read_result();
  MV[0] = _tmp0_1;
}

static int isSchedulable_get_residual_x() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void mag_y() {
  short *MV;
  int mvval0_1;
  char _tmp0_1;
  char _tmp1_1;
  char _tmp1_2;
  char _tmp2_1;
  char _tmp1_3;
  
  MV = getWritePtr(parseheaders_MV, 1);
  mvval0_1 = vld_result();
  _tmp0_1 = fcode;
  if (_tmp0_1 <= 1 || mvval0_1 == 0) {
    _tmp1_1 = 0;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp2_1 = fcode;
    _tmp1_3 = _tmp2_1 - 1;
    _tmp1_2 = _tmp1_3;
  }
  set_bits_to_read(_tmp1_2);
  MV[0] = mvval0_1;
}

static int isSchedulable_mag_y() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = vld_success();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void get_residual_y() {
  short *MV;
  int _tmp1_1;
  
  MV = getWritePtr(parseheaders_MV, 1);
  mvcomp++;
  _tmp1_1 = read_result();
  MV[0] = _tmp1_1;
}

static int isSchedulable_get_residual_y() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void untagged02() {
  int *bits;
  int b_1;
  short _tmp0_1;
  unsigned char _tmp1_1;
  unsigned char _tmp1_2;
  unsigned char _tmp1_3;
  int _tmp2_1;
  int _tmp3_1;
  
  bits = getReadPtr(parseheaders_bits, 1);
  b_1 = bits[0];
  _tmp0_1 = vld_index;
  if (b_1) {
    _tmp1_1 = 1;
    _tmp1_2 = _tmp1_1;
  } else {
    _tmp1_3 = 0;
    _tmp1_2 = _tmp1_3;
  }
  _tmp2_1 = vld_table[_tmp0_1 + _tmp1_2];
  vld_codeword = _tmp2_1;
  _tmp3_1 = vld_codeword;
  vld_index = _tmp3_1 >> 2;
  bit_count++;
}

static int isSchedulable_untagged02() {
  int *bits;
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp2_1 = vld_codeword;
    _tmp0_1 = (_tmp2_1 & 3) == 2;
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void do_vld_failure() {
}

static int isSchedulable_do_vld_failure() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = vld_failure();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void generic_done() {
}

static int isSchedulable_generic_done() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void generic_done_with_bitread() {
  int *bits;
  
  bits = getReadPtr(parseheaders_bits, 1);
  bit_count++;
}

static int isSchedulable_generic_done_with_bitread() {
  int *bits;
  int _tmp1_1;
  int _tmp0_1;
  int _tmp0_2;
  int _tmp0_3;
  
  _tmp1_1 = hasTokens(parseheaders_bits, 1);
  if (_tmp1_1) {
    bits = getPeekPtr(parseheaders_bits, 1);
    _tmp0_1 = done_reading_bits();
    _tmp0_2 = _tmp0_1;
  } else {
    _tmp0_3 = 0;
    _tmp0_2 = _tmp0_3;
  }
  return _tmp0_2;
}

static void test_zero_byte() {
}

static int isSchedulable_test_zero_byte() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = read_result();
  _tmp0_1 = _tmp1_1 && (_tmp2_1 & 255) == 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void test_vo_byte() {
}

static int isSchedulable_test_vo_byte() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = read_result();
  _tmp0_1 = _tmp1_1 && (_tmp2_1 & 254) == 0;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void test_vol_byte() {
  set_bits_to_read(9);
}

static int isSchedulable_test_vol_byte() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = read_result();
  _tmp0_1 = _tmp1_1 && _tmp2_1 == 32;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void test_vop_byte() {
  mbx = 0;
  mby = 0;
  set_bits_to_read(2);
}

static int isSchedulable_test_vop_byte() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = read_result();
  _tmp0_1 = _tmp1_1 && _tmp2_1 == 182;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void test_one_byte() {
}

static int isSchedulable_test_one_byte() {
  int _tmp1_1;
  int _tmp2_1;
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp1_1 = done_reading_bits();
  _tmp2_1 = read_result();
  _tmp0_1 = _tmp1_1 && (_tmp2_1 & 255) == 1;
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}

static void request_byte() {
  set_bits_to_read(8);
}

static int isSchedulable_request_byte() {
  int _tmp0_1;
  int _tmp0_2;
  
  _tmp0_1 = done_reading_bits();
  _tmp0_2 = _tmp0_1;
  return _tmp0_2;
}


////////////////////////////////////////////////////////////////////////////////
// Action scheduler

enum states {
  s_block = 0,
  s_final_cbpy,
  s_get_dc,
  s_get_dc_a,
  s_get_dc_bits,
  s_get_mbtype,
  s_get_residual_x,
  s_get_residual_y,
  s_mag_x,
  s_mag_y,
  s_mb,
  s_mv,
  s_mv_y,
  s_pvop_uncoded1,
  s_pvop_uncoded2,
  s_pvop_uncoded3,
  s_pvop_uncoded4,
  s_pvop_uncoded5,
  s_send_new_vop_height,
  s_send_new_vop_info,
  s_send_new_vop_width,
  s_stuck,
  s_stuck_1a,
  s_stuck_1b,
  s_stuck_2a,
  s_stuck_2b,
  s_stuck_3a,
  s_stuck_3b,
  s_stuck_4a,
  s_stuck_4b,
  s_texac,
  s_texture,
  s_vld1,
  s_vld4,
  s_vld4a,
  s_vld6,
  s_vld6a,
  s_vld7,
  s_vld_direct,
  s_vol_aspect,
  s_vol_control,
  s_vol_height,
  s_vol_misc,
  s_vol_object,
  s_vol_shape,
  s_vol_time_inc_res,
  s_vol_vbv,
  s_vol_width,
  s_vop_coding,
  s_vop_predict,
  s_vop_timebase
};

static char *stateNames[] = {
  "s_block",
  "s_final_cbpy",
  "s_get_dc",
  "s_get_dc_a",
  "s_get_dc_bits",
  "s_get_mbtype",
  "s_get_residual_x",
  "s_get_residual_y",
  "s_mag_x",
  "s_mag_y",
  "s_mb",
  "s_mv",
  "s_mv_y",
  "s_pvop_uncoded1",
  "s_pvop_uncoded2",
  "s_pvop_uncoded3",
  "s_pvop_uncoded4",
  "s_pvop_uncoded5",
  "s_send_new_vop_height",
  "s_send_new_vop_info",
  "s_send_new_vop_width",
  "s_stuck",
  "s_stuck_1a",
  "s_stuck_1b",
  "s_stuck_2a",
  "s_stuck_2b",
  "s_stuck_3a",
  "s_stuck_3b",
  "s_stuck_4a",
  "s_stuck_4b",
  "s_texac",
  "s_texture",
  "s_vld1",
  "s_vld4",
  "s_vld4a",
  "s_vld6",
  "s_vld6a",
  "s_vld7",
  "s_vld_direct",
  "s_vol_aspect",
  "s_vol_control",
  "s_vol_height",
  "s_vol_misc",
  "s_vol_object",
  "s_vol_shape",
  "s_vol_time_inc_res",
  "s_vol_vbv",
  "s_vol_width",
  "s_vop_coding",
  "s_vop_predict",
  "s_vop_timebase"
};

static enum states _FSM_state = s_stuck_1a;

static int outside_FSM_scheduler() {
  int res;
  
  if (isSchedulable_untagged01()) {
    untagged01();
    res = 1;
  } else {
    if (isSchedulable_untagged02()) {
      untagged02();
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int block_state_scheduler() {
  int res;
  
  if (isSchedulable_mb_dispatch_done()) {
    mb_dispatch_done();
    _FSM_state = s_mb;
    res = 1;
  } else {
    if (isSchedulable_mb_dispatch_intra()) {
      if (hasRoom(parseheaders_BTYPE, 1)) {
        mb_dispatch_intra();
        _FSM_state = s_texture;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_mb_dispatch_inter_no_ac()) {
        if (hasRoom(parseheaders_BTYPE, 1)) {
          mb_dispatch_inter_no_ac();
          _FSM_state = s_block;
          res = 1;
        } else {
          res = 0;
        }
      } else {
        if (isSchedulable_mb_dispatch_inter_ac_coded()) {
          if (hasRoom(parseheaders_BTYPE, 1)) {
            mb_dispatch_inter_ac_coded();
            _FSM_state = s_texture;
            res = 1;
          } else {
            res = 0;
          }
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int final_cbpy_state_scheduler() {
  int res;
  
  if (isSchedulable_final_cbpy_inter()) {
    final_cbpy_inter();
    _FSM_state = s_mv;
    res = 1;
  } else {
    if (isSchedulable_do_vld_failure()) {
      do_vld_failure();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      if (isSchedulable_final_cbpy_intra()) {
        final_cbpy_intra();
        _FSM_state = s_block;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int get_dc_state_scheduler() {
  int res;
  
  if (isSchedulable_dc_bits_shift()) {
    dc_bits_shift();
    _FSM_state = s_get_dc_a;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int get_dc_a_state_scheduler() {
  int res;
  
  if (isSchedulable_get_dc()) {
    if (hasRoom(parseheaders_LAST, 1) && hasRoom(parseheaders_RUN, 1) && hasRoom(parseheaders_VALUE, 1)) {
      get_dc();
      _FSM_state = s_texac;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int get_dc_bits_state_scheduler() {
  int res;
  
  if (isSchedulable_do_vld_failure()) {
    do_vld_failure();
    _FSM_state = s_stuck;
    res = 1;
  } else {
    if (isSchedulable_get_dc_bits_none()) {
      if (hasRoom(parseheaders_LAST, 1) && hasRoom(parseheaders_RUN, 1) && hasRoom(parseheaders_VALUE, 1)) {
        get_dc_bits_none();
        _FSM_state = s_texac;
        res = 1;
      } else {
        res = 0;
      }
    } else {
      if (isSchedulable_get_dc_bits_some()) {
        get_dc_bits_some();
        _FSM_state = s_get_dc;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int get_mbtype_state_scheduler() {
  int res;
  
  if (isSchedulable_get_mbtype_noac()) {
    get_mbtype_noac();
    _FSM_state = s_final_cbpy;
    res = 1;
  } else {
    if (isSchedulable_do_vld_failure()) {
      do_vld_failure();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      if (isSchedulable_get_mbtype_ac()) {
        get_mbtype_ac();
        _FSM_state = s_final_cbpy;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int get_residual_x_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_x()) {
    if (hasRoom(parseheaders_MV, 1)) {
      get_residual_x();
      _FSM_state = s_mv_y;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int get_residual_y_state_scheduler() {
  int res;
  
  if (isSchedulable_get_residual_y()) {
    if (hasRoom(parseheaders_MV, 1)) {
      get_residual_y();
      _FSM_state = s_mv;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int mag_x_state_scheduler() {
  int res;
  
  if (isSchedulable_mag_x()) {
    if (hasRoom(parseheaders_MV, 1)) {
      mag_x();
      _FSM_state = s_get_residual_x;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_do_vld_failure()) {
      do_vld_failure();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int mag_y_state_scheduler() {
  int res;
  
  if (isSchedulable_mag_y()) {
    if (hasRoom(parseheaders_MV, 1)) {
      mag_y();
      _FSM_state = s_get_residual_y;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_do_vld_failure()) {
      do_vld_failure();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int mb_state_scheduler() {
  int res;
  
  if (isSchedulable_mb_done()) {
    mb_done();
    _FSM_state = s_stuck;
    res = 1;
  } else {
    if (isSchedulable_get_mcbpc_ivop()) {
      get_mcbpc_ivop();
      _FSM_state = s_get_mbtype;
      res = 1;
    } else {
      if (isSchedulable_get_mcbpc_pvop()) {
        get_mcbpc_pvop();
        _FSM_state = s_get_mbtype;
        res = 1;
      } else {
        if (isSchedulable_mcbpc_pvop_uncoded()) {
          if (hasRoom(parseheaders_BTYPE, 1)) {
            mcbpc_pvop_uncoded();
            _FSM_state = s_pvop_uncoded1;
            res = 1;
          } else {
            res = 0;
          }
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int mv_state_scheduler() {
  int res;
  
  if (isSchedulable_mvcode_done()) {
    mvcode_done();
    _FSM_state = s_block;
    res = 1;
  } else {
    if (isSchedulable_mvcode()) {
      mvcode();
      _FSM_state = s_mag_x;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int mv_y_state_scheduler() {
  int res;
  
  if (isSchedulable_mvcode()) {
    mvcode();
    _FSM_state = s_mag_y;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int pvop_uncoded1_state_scheduler() {
  int res;
  
  if (isSchedulable_mcbpc_pvop_uncoded1()) {
    if (hasRoom(parseheaders_BTYPE, 1)) {
      mcbpc_pvop_uncoded1();
      _FSM_state = s_pvop_uncoded2;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int pvop_uncoded2_state_scheduler() {
  int res;
  
  if (isSchedulable_mcbpc_pvop_uncoded1()) {
    if (hasRoom(parseheaders_BTYPE, 1)) {
      mcbpc_pvop_uncoded1();
      _FSM_state = s_pvop_uncoded3;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int pvop_uncoded3_state_scheduler() {
  int res;
  
  if (isSchedulable_mcbpc_pvop_uncoded1()) {
    if (hasRoom(parseheaders_BTYPE, 1)) {
      mcbpc_pvop_uncoded1();
      _FSM_state = s_pvop_uncoded4;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int pvop_uncoded4_state_scheduler() {
  int res;
  
  if (isSchedulable_mcbpc_pvop_uncoded1()) {
    if (hasRoom(parseheaders_BTYPE, 1)) {
      mcbpc_pvop_uncoded1();
      _FSM_state = s_pvop_uncoded5;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int pvop_uncoded5_state_scheduler() {
  int res;
  
  if (isSchedulable_mcbpc_pvop_uncoded1()) {
    if (hasRoom(parseheaders_BTYPE, 1)) {
      mcbpc_pvop_uncoded1();
      _FSM_state = s_mb;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int send_new_vop_height_state_scheduler() {
  int res;
  
  if (isSchedulable_send_new_vop_height()) {
    if (hasRoom(parseheaders_BTYPE, 1) && hasRoom(parseheaders_HEIGHT, 1)) {
      send_new_vop_height();
      _FSM_state = s_mb;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int send_new_vop_info_state_scheduler() {
  int res;
  
  if (isSchedulable_send_new_vop_cmd()) {
    if (hasRoom(parseheaders_BTYPE, 1)) {
      send_new_vop_cmd();
      _FSM_state = s_send_new_vop_width;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int send_new_vop_width_state_scheduler() {
  int res;
  
  if (isSchedulable_send_new_vop_width()) {
    if (hasRoom(parseheaders_BTYPE, 1) && hasRoom(parseheaders_WIDTH, 1)) {
      send_new_vop_width();
      _FSM_state = s_send_new_vop_height;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int stuck_state_scheduler() {
  int res;
  
  if (isSchedulable_byte_align()) {
    byte_align();
    _FSM_state = s_stuck_1a;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int stuck_1a_state_scheduler() {
  int res;
  
  if (isSchedulable_request_byte()) {
    request_byte();
    _FSM_state = s_stuck_1b;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int stuck_1b_state_scheduler() {
  int res;
  
  if (isSchedulable_test_zero_byte()) {
    test_zero_byte();
    _FSM_state = s_stuck_2a;
    res = 1;
  } else {
    if (isSchedulable_generic_done()) {
      generic_done();
      _FSM_state = s_stuck_1a;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int stuck_2a_state_scheduler() {
  int res;
  
  if (isSchedulable_request_byte()) {
    request_byte();
    _FSM_state = s_stuck_2b;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int stuck_2b_state_scheduler() {
  int res;
  
  if (isSchedulable_test_zero_byte()) {
    test_zero_byte();
    _FSM_state = s_stuck_3a;
    res = 1;
  } else {
    if (isSchedulable_generic_done()) {
      generic_done();
      _FSM_state = s_stuck_1a;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int stuck_3a_state_scheduler() {
  int res;
  
  if (isSchedulable_request_byte()) {
    request_byte();
    _FSM_state = s_stuck_3b;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int stuck_3b_state_scheduler() {
  int res;
  
  if (isSchedulable_test_one_byte()) {
    test_one_byte();
    _FSM_state = s_stuck_4a;
    res = 1;
  } else {
    if (isSchedulable_test_zero_byte()) {
      test_zero_byte();
      _FSM_state = s_stuck_3a;
      res = 1;
    } else {
      if (isSchedulable_generic_done()) {
        generic_done();
        _FSM_state = s_stuck_1a;
        res = 1;
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int stuck_4a_state_scheduler() {
  int res;
  
  if (isSchedulable_request_byte()) {
    request_byte();
    _FSM_state = s_stuck_4b;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int stuck_4b_state_scheduler() {
  int res;
  
  if (isSchedulable_test_vo_byte()) {
    test_vo_byte();
    _FSM_state = s_stuck_1a;
    res = 1;
  } else {
    if (isSchedulable_test_vol_byte()) {
      test_vol_byte();
      _FSM_state = s_vol_object;
      res = 1;
    } else {
      if (isSchedulable_test_vop_byte()) {
        test_vop_byte();
        _FSM_state = s_vop_predict;
        res = 1;
      } else {
        if (isSchedulable_generic_done()) {
          generic_done();
          _FSM_state = s_stuck_1a;
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int texac_state_scheduler() {
  int res;
  
  if (isSchedulable_block_done()) {
    block_done();
    _FSM_state = s_block;
    res = 1;
  } else {
    if (isSchedulable_dct_coeff()) {
      dct_coeff();
      _FSM_state = s_vld1;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int texture_state_scheduler() {
  int res;
  
  if (isSchedulable_vld_start_intra()) {
    vld_start_intra();
    _FSM_state = s_get_dc_bits;
    res = 1;
  } else {
    if (isSchedulable_vld_start_inter_ac_coded()) {
      vld_start_inter_ac_coded();
      _FSM_state = s_texac;
      res = 1;
    } else {
      if (isSchedulable_vld_start_inter_not_ac_coded()) {
        if (hasRoom(parseheaders_LAST, 1) && hasRoom(parseheaders_RUN, 1) && hasRoom(parseheaders_VALUE, 1)) {
          vld_start_inter_not_ac_coded();
          _FSM_state = s_texac;
          res = 1;
        } else {
          res = 0;
        }
      } else {
        res = 0;
      }
    }
  }
  
  return res;
}

static int vld1_state_scheduler() {
  int res;
  
  if (isSchedulable_vld_code()) {
    if (hasRoom(parseheaders_LAST, 1) && hasRoom(parseheaders_RUN, 1) && hasRoom(parseheaders_VALUE, 1)) {
      vld_code();
      _FSM_state = s_texac;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    if (isSchedulable_do_vld_failure()) {
      do_vld_failure();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      if (isSchedulable_vld_level()) {
        vld_level();
        _FSM_state = s_vld4;
        res = 1;
      } else {
        if (isSchedulable_vld_run_or_direct()) {
          vld_run_or_direct();
          _FSM_state = s_vld7;
          res = 1;
        } else {
          res = 0;
        }
      }
    }
  }
  
  return res;
}

static int vld4_state_scheduler() {
  int res;
  
  if (isSchedulable_do_level_lookup()) {
    do_level_lookup();
    _FSM_state = s_vld4a;
    res = 1;
  } else {
    if (isSchedulable_do_vld_failure()) {
      do_vld_failure();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vld4a_state_scheduler() {
  int res;
  
  if (isSchedulable_vld_level_lookup()) {
    if (hasRoom(parseheaders_LAST, 1) && hasRoom(parseheaders_RUN, 1) && hasRoom(parseheaders_VALUE, 1)) {
      vld_level_lookup();
      _FSM_state = s_texac;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int vld6_state_scheduler() {
  int res;
  
  if (isSchedulable_do_run_lookup()) {
    do_run_lookup();
    _FSM_state = s_vld6a;
    res = 1;
  } else {
    if (isSchedulable_do_vld_failure()) {
      do_vld_failure();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vld6a_state_scheduler() {
  int res;
  
  if (isSchedulable_vld_run_lookup()) {
    if (hasRoom(parseheaders_LAST, 1) && hasRoom(parseheaders_RUN, 1) && hasRoom(parseheaders_VALUE, 1)) {
      vld_run_lookup();
      _FSM_state = s_texac;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int vld7_state_scheduler() {
  int res;
  
  if (isSchedulable_vld_run()) {
    vld_run();
    _FSM_state = s_vld6;
    res = 1;
  } else {
    if (isSchedulable_vld_direct_read()) {
      vld_direct_read();
      _FSM_state = s_vld_direct;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vld_direct_state_scheduler() {
  int res;
  
  if (isSchedulable_vld_direct()) {
    if (hasRoom(parseheaders_LAST, 1) && hasRoom(parseheaders_RUN, 1) && hasRoom(parseheaders_VALUE, 1)) {
      vld_direct();
      _FSM_state = s_texac;
      res = 1;
    } else {
      res = 0;
    }
  } else {
    res = 0;
  }
  
  return res;
}

static int vol_aspect_state_scheduler() {
  int res;
  
  if (isSchedulable_vol_aspect_detailed()) {
    vol_aspect_detailed();
    _FSM_state = s_vol_control;
    res = 1;
  } else {
    if (isSchedulable_generic_done()) {
      generic_done();
      _FSM_state = s_vol_control;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vol_control_state_scheduler() {
  int res;
  
  if (isSchedulable_vol_control_detailed()) {
    vol_control_detailed();
    _FSM_state = s_vol_vbv;
    res = 1;
  } else {
    if (isSchedulable_generic_done_with_bitread()) {
      generic_done_with_bitread();
      _FSM_state = s_vol_shape;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vol_height_state_scheduler() {
  int res;
  
  if (isSchedulable_set_vol_height()) {
    set_vol_height();
    _FSM_state = s_vol_misc;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int vol_misc_state_scheduler() {
  int res;
  
  if (isSchedulable_generic_done()) {
    generic_done();
    _FSM_state = s_stuck;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int vol_object_state_scheduler() {
  int res;
  
  if (isSchedulable_vol_object_layer_identification()) {
    vol_object_layer_identification();
    _FSM_state = s_vol_aspect;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int vol_shape_state_scheduler() {
  int res;
  
  if (isSchedulable_vol_shape()) {
    vol_shape();
    _FSM_state = s_vol_time_inc_res;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int vol_time_inc_res_state_scheduler() {
  int res;
  
  if (isSchedulable_vol_time_inc_res()) {
    vol_time_inc_res();
    _FSM_state = s_vol_width;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int vol_vbv_state_scheduler() {
  int res;
  
  if (isSchedulable_vol_vbv_detailed()) {
    vol_vbv_detailed();
    _FSM_state = s_vol_shape;
    res = 1;
  } else {
    if (isSchedulable_generic_done_with_bitread()) {
      generic_done_with_bitread();
      _FSM_state = s_vol_shape;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vol_width_state_scheduler() {
  int res;
  
  if (isSchedulable_set_vol_width()) {
    set_vol_width();
    _FSM_state = s_vol_height;
    res = 1;
  } else {
    res = 0;
  }
  
  return res;
}

static int vop_coding_state_scheduler() {
  int res;
  
  if (isSchedulable_vop_coding_uncoded()) {
    vop_coding_uncoded();
    _FSM_state = s_stuck;
    res = 1;
  } else {
    if (isSchedulable_vop_coding_coded()) {
      vop_coding_coded();
      _FSM_state = s_send_new_vop_info;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vop_predict_state_scheduler() {
  int res;
  
  if (isSchedulable_vop_predict_supported()) {
    vop_predict_supported();
    _FSM_state = s_vop_timebase;
    res = 1;
  } else {
    if (isSchedulable_generic_done()) {
      generic_done();
      _FSM_state = s_stuck;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

static int vop_timebase_state_scheduler() {
  int res;
  
  if (isSchedulable_vop_timebase_one()) {
    vop_timebase_one();
    _FSM_state = s_vop_timebase;
    res = 1;
  } else {
    if (isSchedulable_vop_timebase_zero()) {
      vop_timebase_zero();
      _FSM_state = s_vop_coding;
      res = 1;
    } else {
      res = 0;
    }
  }
  
  return res;
}

int parseheaders_scheduler() {
  int res = 1;
  
  while (res) {
    if (outside_FSM_scheduler()) {
      res = 1;
    } else {
      switch (_FSM_state) {
        case s_block:
          res = block_state_scheduler();
          break;
        case s_final_cbpy:
          res = final_cbpy_state_scheduler();
          break;
        case s_get_dc:
          res = get_dc_state_scheduler();
          break;
        case s_get_dc_a:
          res = get_dc_a_state_scheduler();
          break;
        case s_get_dc_bits:
          res = get_dc_bits_state_scheduler();
          break;
        case s_get_mbtype:
          res = get_mbtype_state_scheduler();
          break;
        case s_get_residual_x:
          res = get_residual_x_state_scheduler();
          break;
        case s_get_residual_y:
          res = get_residual_y_state_scheduler();
          break;
        case s_mag_x:
          res = mag_x_state_scheduler();
          break;
        case s_mag_y:
          res = mag_y_state_scheduler();
          break;
        case s_mb:
          res = mb_state_scheduler();
          break;
        case s_mv:
          res = mv_state_scheduler();
          break;
        case s_mv_y:
          res = mv_y_state_scheduler();
          break;
        case s_pvop_uncoded1:
          res = pvop_uncoded1_state_scheduler();
          break;
        case s_pvop_uncoded2:
          res = pvop_uncoded2_state_scheduler();
          break;
        case s_pvop_uncoded3:
          res = pvop_uncoded3_state_scheduler();
          break;
        case s_pvop_uncoded4:
          res = pvop_uncoded4_state_scheduler();
          break;
        case s_pvop_uncoded5:
          res = pvop_uncoded5_state_scheduler();
          break;
        case s_send_new_vop_height:
          res = send_new_vop_height_state_scheduler();
          break;
        case s_send_new_vop_info:
          res = send_new_vop_info_state_scheduler();
          break;
        case s_send_new_vop_width:
          res = send_new_vop_width_state_scheduler();
          break;
        case s_stuck:
          res = stuck_state_scheduler();
          break;
        case s_stuck_1a:
          res = stuck_1a_state_scheduler();
          break;
        case s_stuck_1b:
          res = stuck_1b_state_scheduler();
          break;
        case s_stuck_2a:
          res = stuck_2a_state_scheduler();
          break;
        case s_stuck_2b:
          res = stuck_2b_state_scheduler();
          break;
        case s_stuck_3a:
          res = stuck_3a_state_scheduler();
          break;
        case s_stuck_3b:
          res = stuck_3b_state_scheduler();
          break;
        case s_stuck_4a:
          res = stuck_4a_state_scheduler();
          break;
        case s_stuck_4b:
          res = stuck_4b_state_scheduler();
          break;
        case s_texac:
          res = texac_state_scheduler();
          break;
        case s_texture:
          res = texture_state_scheduler();
          break;
        case s_vld1:
          res = vld1_state_scheduler();
          break;
        case s_vld4:
          res = vld4_state_scheduler();
          break;
        case s_vld4a:
          res = vld4a_state_scheduler();
          break;
        case s_vld6:
          res = vld6_state_scheduler();
          break;
        case s_vld6a:
          res = vld6a_state_scheduler();
          break;
        case s_vld7:
          res = vld7_state_scheduler();
          break;
        case s_vld_direct:
          res = vld_direct_state_scheduler();
          break;
        case s_vol_aspect:
          res = vol_aspect_state_scheduler();
          break;
        case s_vol_control:
          res = vol_control_state_scheduler();
          break;
        case s_vol_height:
          res = vol_height_state_scheduler();
          break;
        case s_vol_misc:
          res = vol_misc_state_scheduler();
          break;
        case s_vol_object:
          res = vol_object_state_scheduler();
          break;
        case s_vol_shape:
          res = vol_shape_state_scheduler();
          break;
        case s_vol_time_inc_res:
          res = vol_time_inc_res_state_scheduler();
          break;
        case s_vol_vbv:
          res = vol_vbv_state_scheduler();
          break;
        case s_vol_width:
          res = vol_width_state_scheduler();
          break;
        case s_vop_coding:
          res = vop_coding_state_scheduler();
          break;
        case s_vop_predict:
          res = vop_predict_state_scheduler();
          break;
        case s_vop_timebase:
          res = vop_timebase_state_scheduler();
          break;
        default:
          printf("unknown state\n");
          break;
      }
    }
  }
  
  return 0;
}
