/**
 * Generated from "parseheaders"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_parseheaders implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_bits;

	// Output FIFOs
	private IntFifo fifo_BTYPE;
	private IntFifo fifo_MV;
	private IntFifo fifo_RUN;
	private IntFifo fifo_VALUE;
	private IntFifo fifo_LAST;
	private IntFifo fifo_WIDTH;
	private IntFifo fifo_HEIGHT;

	// State variables of the actor
	private int MAXW_IN_MB = 121;

	private int MB_COORD_SZ = 8;

	private int BTYPE_SZ = 12;

	private int MV_SZ = 9;

	private int NEWVOP = 2048;

	private int INTRA = 1024;

	private int INTER = 512;

	private int QUANT_MASK = 31;

	private int ROUND_TYPE = 32;

	private int FCODE_MASK = 448;

	private int FCODE_SHIFT = 6;

	private int ACPRED = 1;

	private int ACCODED = 2;

	private int FOURMV = 4;

	private int MOTION = 8;

	private int SAMPLE_COUNT_SZ = 8;

	private int SAMPLE_SZ = 13;

	private int VO_HEADER_LENGTH = 27;

	private int VO_NO_SHORT_HEADER = 8;

	private int VO_ID_LENGTH = 5;

	private int VOL_START_CODE = 32;

	private int VOL_ID_LENGTH = 1;

	private int VIDEO_OBJECT_TYPE_INDICATION_LENGTH = 8;

	private int VISUAL_OBJECT_LAYER_VERID_LENGTH = 4;

	private int VISUAL_OBJECT_LAYER_PRIORITY_LENGTH = 3;

	private int ASPECT_RATIO_INFO_LENGTH = 4;

	private int ASPECT_RATIO_INFO_IS_DETAILED = 15;

	private int PAR_WIDTH_LENGTH = 8;

	private int PAR_HEIGHT_LENGTH = 8;

	private int CHROMA_FORMAT_LENGTH = 2;

	private int LOW_DELAY_LENGTH = 1;

	private int FIRST_HALF_BIT_RATE_LENGTH = 15;

	private int LAST_HALF_BIT_RATE_LENGTH = 15;

	private int FIRST_HALF_VBV_BUF_SZ_LENGTH = 15;

	private int LAST_HALF_VBV_BUF_SZ_LENGTH = 3;

	private int FIRST_HALF_VBV_OCC_LENGTH = 11;

	private int LAST_HALF_VBV_OCC_LENGTH = 15;

	private int VOL_SHAPE_LENGTH = 2;

	private int MARKER_LENGTH = 1;

	private int TIME_INC_RES_LENGTH = 16;

	private int VOL_WIDTH_LENGTH = 13;

	private int VOL_HEIGHT_LENGTH = 13;

	private int RUN_LENGTH = 6;

	private int RUN_MASK = 63;

	private int LEVEL_LENGTH = 12;

	private int LEVEL_MASK = 4095;

	private int MISC_BIT_LENGTH = 9;

	private int VOP_START_CODE = 182;

	private int VOP_PREDICTION_LENGTH = 2;

	private int B_VOP = 2;

	private int P_VOP = 1;

	private int I_VOP = 0;

	private int INTRA_DC_VLC_THR_LENGTH = 3;

	private int VOP_FCODE_FOR_LENGTH = 3;

	private int VOP_FCODE_FOR_MASK = 7;

	private int BITS_QUANT = 5;

	private int BITS_QUANT_MASK = 31;

	private int MCBPC_LENGTH = 9;

	private int ESCAPE_CODE = 7167;

	private int bits_to_read_count = -1;

	private int read_result_in_progress;

	private int bit_count = 0;

	private int mylog;

	private int vol_width;

	private int vol_height;

	private int mbx;

	private int mby;

	private boolean prediction_is_IVOP;

	private boolean prediction_is_PVOP;

	private boolean prediction_is_BVOP;

	private int comp;

	private int fcode;

	private int CBP_SZ = 7;

	private int cbp;

	private boolean acpredflag;

	private boolean btype_is_INTRA;

	private int cbpc;

	private boolean fourmvflag;

	private boolean ac_coded;

	private int mvcomp;

	private int dc_bits;

	private int msb_result;

	private boolean b_last;

	private int level_lookup_inter;

	private int level_lookup_intra;

	private int run_lookup_inter;

	private int run_lookup_intra;

	private int VLD_TABLE_ADDR_BITS = 12;

	private int VLD_TABLE_DATA_BITS = 20;

	private int[] vld_table = {10, 12, 18, 58, 26, 76, 34, 16, 42, 50, 1, 80, 144, 
	208, 140, 204, 74, 0, 82, 226, 90, 218, 98, 202, 106, 178, 114, 162, 122, 146, 
	130, 138, 1, 1, 208, 144, 154, 140, 80, 196, 170, 204, 76, 200, 186, 194, 136, 
	72, 132, 68, 210, 12, 16, 192, 128, 64, 8, 4, 242, 338, 250, 314, 258, 298, 266, 
	290, 274, 282, 1, 1, 24, 36, 32, 16, 306, 0, 8, 4, 322, 330, 48, 40, 56, 20, 346, 
	60, 354, 362, 52, 12, 44, 28, 378, 466, 386, 458, 394, 16, 402, 20, 410, 24, 418, 
	28, 426, 32, 434, 36, 442, 40, 450, 44, 1, 48, 12, 0, 8, 4, 482, 570, 490, 8, 498, 
	12, 506, 16, 514, 20, 522, 24, 530, 28, 538, 32, 546, 36, 554, 40, 562, 44, 1, 
	48, 4, 0, 586, 1498, 594, 1426, 602, 1338, 610, 1194, 618, 1066, 626, 874, 634, 
	818, 642, 794, 650, 770, 658, 714, 666, 690, 674, 682, 1, 1, 1, 1, 698, 706, 1, 
	1, 1, 1, 722, 746, 730, 738, 1, 1, 1, 1, 754, 762, 1, 1, 1, 1, 778, 786, 16456, 
	16396, 44, 40, 802, 810, 18180, 18116, 18052, 17988, 826, 850, 834, 842, 584, 520, 
	456, 392, 858, 866, 328, 204, 140, 80, 882, 28668, 890, 946, 898, 922, 906, 914, 
	48, 84, 1476, 1540, 930, 938, 18244, 18308, 18372, 18436, 954, 1010, 962, 986, 
	970, 978, 88, 144, 268, 332, 994, 1002, 396, 648, 1604, 1668, 1018, 1042, 1026, 
	1034, 18500, 18564, 18628, 18692, 1050, 1058, 18756, 18820, 18884, 18948, 1074, 
	1138, 1082, 1114, 1090, 1106, 1098, 17924, 36, 32, 17860, 17796, 1122, 1130, 17732, 
	17668, 17604, 17540, 1146, 1170, 1154, 1162, 17476, 16392, 1412, 1348, 1178, 1186, 
	1284, 1220, 1156, 1092, 1202, 1282, 1210, 1258, 1218, 1242, 1226, 1234, 1028, 964, 
	264, 200, 1250, 17412, 28, 24, 1266, 1274, 17348, 17284, 17220, 17156, 1290, 1314, 
	1298, 1306, 17092, 17028, 16964, 900, 1322, 1330, 836, 136, 76, 20, 1346, 1402, 
	1354, 1378, 1362, 1370, 16900, 16836, 16772, 16708, 1386, 1394, 772, 708, 644, 
	16, 1410, 1418, 16644, 16580, 16516, 16452, 1434, 1482, 1442, 1466, 1450, 1458, 
	580, 516, 452, 388, 1474, 324, 72, 12, 1490, 16388, 260, 196, 4, 1506, 68, 1514, 
	132, 8, 1530, 2442, 1538, 2370, 1546, 2282, 1554, 2138, 1562, 2010, 1570, 1818, 
	1578, 1762, 1586, 1738, 1594, 1714, 1602, 1658, 1610, 1634, 1618, 1626, 1, 1, 1, 
	1, 1642, 1650, 1, 1, 1, 1, 1666, 1690, 1674, 1682, 1, 1, 1, 1, 1698, 1706, 1, 1, 
	1, 1, 1722, 1730, 262172, 262168, 88, 84, 1746, 1754, 264200, 263180, 262164, 13316, 
	1770, 1794, 1778, 1786, 5132, 8200, 4108, 3088, 1802, 1810, 2064, 1052, 80, 76, 
	1826, 28668, 1834, 1890, 1842, 1866, 1850, 1858, 92, 96, 1056, 9224, 1874, 1882, 
	265224, 266248, 277508, 278532, 1898, 1954, 1906, 1930, 1914, 1922, 100, 104, 108, 
	1060, 1938, 1946, 6156, 1064, 2068, 7180, 1962, 1986, 1970, 1978, 14340, 262176, 
	267272, 268296, 1994, 2002, 279556, 280580, 281604, 282628, 2018, 2082, 2026, 2058, 
	2034, 2050, 2042, 276484, 72, 68, 275460, 274436, 2066, 2074, 273412, 272388, 263176, 
	262160, 2090, 2114, 2098, 2106, 12292, 11268, 7176, 6152, 2122, 2130, 5128, 3084, 
	2060, 1048, 2146, 2226, 2154, 2202, 2162, 2186, 2170, 2178, 1044, 64, 4104, 60, 
	2194, 270340, 56, 52, 2210, 2218, 269316, 268292, 262156, 10244, 2234, 2258, 2242, 
	2250, 9220, 8196, 271364, 3080, 2266, 2274, 1040, 48, 44, 40, 2290, 2346, 2298, 
	2322, 2306, 2314, 266244, 265220, 6148, 267268, 2330, 2338, 7172, 2056, 1036, 36, 
	2354, 2362, 262152, 5124, 264196, 263172, 2378, 2426, 2386, 2410, 2394, 2402, 4100, 
	3076, 32, 28, 2418, 2052, 1032, 24, 2434, 262148, 20, 16, 4, 2450, 8, 2458, 1028, 
	12, 2474, 0, 2482, 3034, 2490, 3026, 2498, 3018, 2506, 2978, 2514, 2890, 2522, 
	2770, 2530, 2714, 2538, 2658, 2546, 2634, 2554, 2610, 2562, 2586, 2570, 2578, 1, 
	1, 1, 1, 2594, 2602, 1, 1, 1, 1, 2618, 2626, 128, -128, 124, -124, 2642, 2650, 
	120, -120, 116, -116, 2666, 2690, 2674, 2682, 112, -112, 108, -108, 2698, 2706, 
	104, -104, 100, -100, 2722, 2746, 2730, 2738, 96, -96, 92, -92, 2754, 2762, 88, 
	-88, 84, -84, 2778, 2834, 2786, 2810, 2794, 2802, 80, -80, 76, -76, 2818, 2826, 
	72, -72, 68, -68, 2842, 2866, 2850, 2858, 64, -64, 60, -60, 2874, 2882, 56, -56, 
	52, -52, 2898, 2970, 2906, 2946, 2914, 2938, 2922, 2930, 48, -48, 44, -44, 40, 
	-40, 2954, 2962, 36, -36, 32, -32, 28, -28, 2986, 3010, 2994, 3002, 24, -24, 20, 
	-20, 16, -16, 12, -12, 8, -8, 4, -4};

	private int MCBPC_IVOP_START_INDEX = 0;

	private int MCBPC_PVOP_START_INDEX = 16;

	private int CBPY_START_INDEX = 58;

	private int DCBITS_Y_START_INDEX = 92;

	private int DCBITS_UV_START_INDEX = 118;

	private int COEFF_INTER_START_INDEX = 144;

	private int COEFF_INTRA_START_INDEX = 380;

	private int MV_START_INDEX = 616;

	private int vld_index;

	private int vld_codeword = 1;



	public Actor_parseheaders() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\ParseHeaders.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("block_done", new Location(603, 2, 101)); 
		actionLocation.put("byte_align", new Location(285, 2, 87)); 
		actionLocation.put("dc_bits_shift", new Location(576, 2, 223)); 
		actionLocation.put("dct_coeff", new Location(611, 2, 171)); 
		actionLocation.put("do_level_lookup", new Location(740, 2, 348)); 
		actionLocation.put("do_run_lookup", new Location(811, 2, 385)); 
		actionLocation.put("do_vld_failure", new Location(1064, 2, 102)); 
		actionLocation.put("final_cbpy_inter", new Location(479, 2, 206)); 
		actionLocation.put("final_cbpy_intra", new Location(491, 2, 159)); 
		actionLocation.put("generic_done", new Location(1071, 2, 64)); 
		actionLocation.put("generic_done_with_bitread", new Location(1076, 2, 123)); 
		actionLocation.put("get_dc", new Location(588, 2, 341)); 
		actionLocation.put("get_dc_bits_none", new Location(558, 2, 159)); 
		actionLocation.put("get_dc_bits_some", new Location(566, 2, 129)); 
		actionLocation.put("get_mbtype_ac", new Location(461, 2, 312)); 
		actionLocation.put("get_mbtype_noac", new Location(445, 2, 383)); 
		actionLocation.put("get_mcbpc_ivop", new Location(410, 2, 118)); 
		actionLocation.put("get_mcbpc_pvop", new Location(417, 2, 175)); 
		actionLocation.put("get_residual_x", new Location(865, 2, 87)); 
		actionLocation.put("get_residual_y", new Location(879, 2, 118)); 
		actionLocation.put("mag_x", new Location(856, 2, 207)); 
		actionLocation.put("mag_y", new Location(870, 2, 207)); 
		actionLocation.put("mb_dispatch_done", new Location(501, 2, 79)); 
		actionLocation.put("mb_dispatch_inter_ac_coded", new Location(527, 2, 170)); 
		actionLocation.put("mb_dispatch_inter_no_ac", new Location(519, 2, 225)); 
		actionLocation.put("mb_dispatch_intra", new Location(508, 2, 308)); 
		actionLocation.put("mb_done", new Location(405, 2, 56)); 
		actionLocation.put("mcbpc_pvop_uncoded", new Location(427, 2, 158)); 
		actionLocation.put("mcbpc_pvop_uncoded1", new Location(436, 2, 61)); 
		actionLocation.put("mvcode", new Location(851, 2, 71)); 
		actionLocation.put("mvcode_done", new Location(846, 2, 90)); 
		actionLocation.put("request_byte", new Location(1122, 2, 96)); 
		actionLocation.put("send_new_vop_cmd", new Location(357, 2, 925)); 
		actionLocation.put("send_new_vop_height", new Location(382, 0, 81)); 
		actionLocation.put("send_new_vop_width", new Location(380, 0, 77)); 
		actionLocation.put("set_vol_height", new Location(262, 2, 241)); 
		actionLocation.put("set_vol_width", new Location(254, 2, 257)); 
		actionLocation.put("test_one_byte", new Location(1116, 2, 103)); 
		actionLocation.put("test_vo_byte", new Location(1089, 2, 102)); 
		actionLocation.put("test_vol_byte", new Location(1095, 2, 226)); 
		actionLocation.put("test_vop_byte", new Location(1104, 2, 189)); 
		actionLocation.put("test_zero_byte", new Location(1083, 2, 104)); 
		actionLocation.put("untagged01", new Location(148, 2, 254)); 
		actionLocation.put("untagged02", new Location(1056, 2, 216)); 
		actionLocation.put("vld_code", new Location(618, 2, 759)); 
		actionLocation.put("vld_direct", new Location(668, 2, 735)); 
		actionLocation.put("vld_direct_read", new Location(662, 2, 177)); 
		actionLocation.put("vld_level", new Location(638, 2, 239)); 
		actionLocation.put("vld_level_lookup", new Location(750, 2, 752)); 
		actionLocation.put("vld_run", new Location(654, 2, 214)); 
		actionLocation.put("vld_run_lookup", new Location(823, 2, 852)); 
		actionLocation.put("vld_run_or_direct", new Location(647, 2, 120)); 
		actionLocation.put("vld_start_inter_ac_coded", new Location(542, 2, 92)); 
		actionLocation.put("vld_start_inter_not_ac_coded", new Location(550, 2, 105)); 
		actionLocation.put("vld_start_intra", new Location(533, 2, 182)); 
		actionLocation.put("vol_aspect_detailed", new Location(180, 2, 267)); 
		actionLocation.put("vol_control_detailed", new Location(189, 2, 190)); 
		actionLocation.put("vol_object_layer_identification", new Location(166, 2, 383)); 
		actionLocation.put("vol_shape", new Location(212, 2, 166)); 
		actionLocation.put("vol_time_inc_res", new Location(221, 2, 855)); 
		actionLocation.put("vol_vbv_detailed", new Location(198, 2, 538)); 
		actionLocation.put("vop_coding_coded", new Location(339, 2, 441)); 
		actionLocation.put("vop_coding_uncoded", new Location(330, 2, 142)); 
		actionLocation.put("vop_predict_supported", new Location(300, 2, 462)); 
		actionLocation.put("vop_timebase_one", new Location(312, 2, 130)); 
		actionLocation.put("vop_timebase_zero", new Location(320, 2, 145)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	private String getNextSchedulableActionOutsideFSM() {
		if (isSchedulable_untagged01()) {
			return "untagged01";
		} else if (isSchedulable_untagged02()) {
			return "untagged02";
		}
		return null;
	}

	private String getNextSchedulableAction_block() {
		if (isSchedulable_mb_dispatch_done()) {
			return "mb_dispatch_done";
		} else if (isSchedulable_mb_dispatch_intra()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mb_dispatch_intra";
			}
		} else if (isSchedulable_mb_dispatch_inter_no_ac()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mb_dispatch_inter_no_ac";
			}
		} else if (isSchedulable_mb_dispatch_inter_ac_coded()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mb_dispatch_inter_ac_coded";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_final_cbpy() {
		if (isSchedulable_final_cbpy_inter()) {
			return "final_cbpy_inter";
		} else if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		} else if (isSchedulable_final_cbpy_intra()) {
			return "final_cbpy_intra";
		}

		return null;
	}

	private String getNextSchedulableAction_get_dc() {
		if (isSchedulable_dc_bits_shift()) {
			return "dc_bits_shift";
		}

		return null;
	}

	private String getNextSchedulableAction_get_dc_a() {
		if (isSchedulable_get_dc()) {
			if (fifo_RUN.hasRoom(1) && fifo_VALUE.hasRoom(1) && fifo_LAST.hasRoom(1)) {
				return "get_dc";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_get_dc_bits() {
		if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		} else if (isSchedulable_get_dc_bits_none()) {
			if (fifo_LAST.hasRoom(1) && fifo_VALUE.hasRoom(1) && fifo_RUN.hasRoom(1)) {
				return "get_dc_bits_none";
			}
		} else if (isSchedulable_get_dc_bits_some()) {
			return "get_dc_bits_some";
		}

		return null;
	}

	private String getNextSchedulableAction_get_mbtype() {
		if (isSchedulable_get_mbtype_noac()) {
			return "get_mbtype_noac";
		} else if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		} else if (isSchedulable_get_mbtype_ac()) {
			return "get_mbtype_ac";
		}

		return null;
	}

	private String getNextSchedulableAction_get_residual_x() {
		if (isSchedulable_get_residual_x()) {
			if (fifo_MV.hasRoom(1)) {
				return "get_residual_x";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_get_residual_y() {
		if (isSchedulable_get_residual_y()) {
			if (fifo_MV.hasRoom(1)) {
				return "get_residual_y";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_mag_x() {
		if (isSchedulable_mag_x()) {
			if (fifo_MV.hasRoom(1)) {
				return "mag_x";
			}
		} else if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		}

		return null;
	}

	private String getNextSchedulableAction_mag_y() {
		if (isSchedulable_mag_y()) {
			if (fifo_MV.hasRoom(1)) {
				return "mag_y";
			}
		} else if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		}

		return null;
	}

	private String getNextSchedulableAction_mb() {
		if (isSchedulable_mb_done()) {
			return "mb_done";
		} else if (isSchedulable_get_mcbpc_ivop()) {
			return "get_mcbpc_ivop";
		} else if (isSchedulable_get_mcbpc_pvop()) {
			return "get_mcbpc_pvop";
		} else if (isSchedulable_mcbpc_pvop_uncoded()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mcbpc_pvop_uncoded";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_mv() {
		if (isSchedulable_mvcode_done()) {
			return "mvcode_done";
		} else if (isSchedulable_mvcode()) {
			return "mvcode";
		}

		return null;
	}

	private String getNextSchedulableAction_mv_y() {
		if (isSchedulable_mvcode()) {
			return "mvcode";
		}

		return null;
	}

	private String getNextSchedulableAction_pvop_uncoded1() {
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mcbpc_pvop_uncoded1";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_pvop_uncoded2() {
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mcbpc_pvop_uncoded1";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_pvop_uncoded3() {
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mcbpc_pvop_uncoded1";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_pvop_uncoded4() {
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mcbpc_pvop_uncoded1";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_pvop_uncoded5() {
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "mcbpc_pvop_uncoded1";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_send_new_vop_height() {
		if (isSchedulable_send_new_vop_height()) {
			if (fifo_BTYPE.hasRoom(1) && fifo_HEIGHT.hasRoom(1)) {
				return "send_new_vop_height";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_send_new_vop_info() {
		if (isSchedulable_send_new_vop_cmd()) {
			if (fifo_BTYPE.hasRoom(1)) {
				return "send_new_vop_cmd";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_send_new_vop_width() {
		if (isSchedulable_send_new_vop_width()) {
			if (fifo_WIDTH.hasRoom(1) && fifo_BTYPE.hasRoom(1)) {
				return "send_new_vop_width";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_stuck() {
		if (isSchedulable_byte_align()) {
			return "byte_align";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_1a() {
		if (isSchedulable_request_byte()) {
			return "request_byte";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_1b() {
		if (isSchedulable_test_zero_byte()) {
			return "test_zero_byte";
		} else if (isSchedulable_generic_done()) {
			return "generic_done";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_2a() {
		if (isSchedulable_request_byte()) {
			return "request_byte";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_2b() {
		if (isSchedulable_test_zero_byte()) {
			return "test_zero_byte";
		} else if (isSchedulable_generic_done()) {
			return "generic_done";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_3a() {
		if (isSchedulable_request_byte()) {
			return "request_byte";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_3b() {
		if (isSchedulable_test_one_byte()) {
			return "test_one_byte";
		} else if (isSchedulable_test_zero_byte()) {
			return "test_zero_byte";
		} else if (isSchedulable_generic_done()) {
			return "generic_done";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_4a() {
		if (isSchedulable_request_byte()) {
			return "request_byte";
		}

		return null;
	}

	private String getNextSchedulableAction_stuck_4b() {
		if (isSchedulable_test_vo_byte()) {
			return "test_vo_byte";
		} else if (isSchedulable_test_vol_byte()) {
			return "test_vol_byte";
		} else if (isSchedulable_test_vop_byte()) {
			return "test_vop_byte";
		} else if (isSchedulable_generic_done()) {
			return "generic_done";
		}

		return null;
	}

	private String getNextSchedulableAction_texac() {
		if (isSchedulable_block_done()) {
			return "block_done";
		} else if (isSchedulable_dct_coeff()) {
			return "dct_coeff";
		}

		return null;
	}

	private String getNextSchedulableAction_texture() {
		if (isSchedulable_vld_start_intra()) {
			return "vld_start_intra";
		} else if (isSchedulable_vld_start_inter_ac_coded()) {
			return "vld_start_inter_ac_coded";
		} else if (isSchedulable_vld_start_inter_not_ac_coded()) {
			if (fifo_VALUE.hasRoom(1) && fifo_LAST.hasRoom(1) && fifo_RUN.hasRoom(1)) {
				return "vld_start_inter_not_ac_coded";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_vld1() {
		if (isSchedulable_vld_code()) {
			if (fifo_LAST.hasRoom(1) && fifo_RUN.hasRoom(1) && fifo_VALUE.hasRoom(1)) {
				return "vld_code";
			}
		} else if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		} else if (isSchedulable_vld_level()) {
			return "vld_level";
		} else if (isSchedulable_vld_run_or_direct()) {
			return "vld_run_or_direct";
		}

		return null;
	}

	private String getNextSchedulableAction_vld4() {
		if (isSchedulable_do_level_lookup()) {
			return "do_level_lookup";
		} else if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		}

		return null;
	}

	private String getNextSchedulableAction_vld4a() {
		if (isSchedulable_vld_level_lookup()) {
			if (fifo_VALUE.hasRoom(1) && fifo_RUN.hasRoom(1) && fifo_LAST.hasRoom(1)) {
				return "vld_level_lookup";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_vld6() {
		if (isSchedulable_do_run_lookup()) {
			return "do_run_lookup";
		} else if (isSchedulable_do_vld_failure()) {
			return "do_vld_failure";
		}

		return null;
	}

	private String getNextSchedulableAction_vld6a() {
		if (isSchedulable_vld_run_lookup()) {
			if (fifo_VALUE.hasRoom(1) && fifo_LAST.hasRoom(1) && fifo_RUN.hasRoom(1)) {
				return "vld_run_lookup";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_vld7() {
		if (isSchedulable_vld_run()) {
			return "vld_run";
		} else if (isSchedulable_vld_direct_read()) {
			return "vld_direct_read";
		}

		return null;
	}

	private String getNextSchedulableAction_vld_direct() {
		if (isSchedulable_vld_direct()) {
			if (fifo_RUN.hasRoom(1) && fifo_LAST.hasRoom(1) && fifo_VALUE.hasRoom(1)) {
				return "vld_direct";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_vol_aspect() {
		if (isSchedulable_vol_aspect_detailed()) {
			return "vol_aspect_detailed";
		} else if (isSchedulable_generic_done()) {
			return "generic_done";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_control() {
		if (isSchedulable_vol_control_detailed()) {
			return "vol_control_detailed";
		} else if (isSchedulable_generic_done_with_bitread()) {
			return "generic_done_with_bitread";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_height() {
		if (isSchedulable_set_vol_height()) {
			return "set_vol_height";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_misc() {
		if (isSchedulable_generic_done()) {
			return "generic_done";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_object() {
		if (isSchedulable_vol_object_layer_identification()) {
			return "vol_object_layer_identification";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_shape() {
		if (isSchedulable_vol_shape()) {
			return "vol_shape";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_time_inc_res() {
		if (isSchedulable_vol_time_inc_res()) {
			return "vol_time_inc_res";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_vbv() {
		if (isSchedulable_vol_vbv_detailed()) {
			return "vol_vbv_detailed";
		} else if (isSchedulable_generic_done_with_bitread()) {
			return "generic_done_with_bitread";
		}

		return null;
	}

	private String getNextSchedulableAction_vol_width() {
		if (isSchedulable_set_vol_width()) {
			return "set_vol_width";
		}

		return null;
	}

	private String getNextSchedulableAction_vop_coding() {
		if (isSchedulable_vop_coding_uncoded()) {
			return "vop_coding_uncoded";
		} else if (isSchedulable_vop_coding_coded()) {
			return "vop_coding_coded";
		}

		return null;
	}

	private String getNextSchedulableAction_vop_predict() {
		if (isSchedulable_vop_predict_supported()) {
			return "vop_predict_supported";
		} else if (isSchedulable_generic_done()) {
			return "generic_done";
		}

		return null;
	}

	private String getNextSchedulableAction_vop_timebase() {
		if (isSchedulable_vop_timebase_one()) {
			return "vop_timebase_one";
		} else if (isSchedulable_vop_timebase_zero()) {
			return "vop_timebase_zero";
		}

		return null;
	}

	@Override
	public String getNextSchedulableAction() {
		String next = getNextSchedulableActionOutsideFSM();
		if (next == null) {
			switch (_FSM_state) {
			case s_block:
				return getNextSchedulableAction_block();
			case s_final_cbpy:
				return getNextSchedulableAction_final_cbpy();
			case s_get_dc:
				return getNextSchedulableAction_get_dc();
			case s_get_dc_a:
				return getNextSchedulableAction_get_dc_a();
			case s_get_dc_bits:
				return getNextSchedulableAction_get_dc_bits();
			case s_get_mbtype:
				return getNextSchedulableAction_get_mbtype();
			case s_get_residual_x:
				return getNextSchedulableAction_get_residual_x();
			case s_get_residual_y:
				return getNextSchedulableAction_get_residual_y();
			case s_mag_x:
				return getNextSchedulableAction_mag_x();
			case s_mag_y:
				return getNextSchedulableAction_mag_y();
			case s_mb:
				return getNextSchedulableAction_mb();
			case s_mv:
				return getNextSchedulableAction_mv();
			case s_mv_y:
				return getNextSchedulableAction_mv_y();
			case s_pvop_uncoded1:
				return getNextSchedulableAction_pvop_uncoded1();
			case s_pvop_uncoded2:
				return getNextSchedulableAction_pvop_uncoded2();
			case s_pvop_uncoded3:
				return getNextSchedulableAction_pvop_uncoded3();
			case s_pvop_uncoded4:
				return getNextSchedulableAction_pvop_uncoded4();
			case s_pvop_uncoded5:
				return getNextSchedulableAction_pvop_uncoded5();
			case s_send_new_vop_height:
				return getNextSchedulableAction_send_new_vop_height();
			case s_send_new_vop_info:
				return getNextSchedulableAction_send_new_vop_info();
			case s_send_new_vop_width:
				return getNextSchedulableAction_send_new_vop_width();
			case s_stuck:
				return getNextSchedulableAction_stuck();
			case s_stuck_1a:
				return getNextSchedulableAction_stuck_1a();
			case s_stuck_1b:
				return getNextSchedulableAction_stuck_1b();
			case s_stuck_2a:
				return getNextSchedulableAction_stuck_2a();
			case s_stuck_2b:
				return getNextSchedulableAction_stuck_2b();
			case s_stuck_3a:
				return getNextSchedulableAction_stuck_3a();
			case s_stuck_3b:
				return getNextSchedulableAction_stuck_3b();
			case s_stuck_4a:
				return getNextSchedulableAction_stuck_4a();
			case s_stuck_4b:
				return getNextSchedulableAction_stuck_4b();
			case s_texac:
				return getNextSchedulableAction_texac();
			case s_texture:
				return getNextSchedulableAction_texture();
			case s_vld1:
				return getNextSchedulableAction_vld1();
			case s_vld4:
				return getNextSchedulableAction_vld4();
			case s_vld4a:
				return getNextSchedulableAction_vld4a();
			case s_vld6:
				return getNextSchedulableAction_vld6();
			case s_vld6a:
				return getNextSchedulableAction_vld6a();
			case s_vld7:
				return getNextSchedulableAction_vld7();
			case s_vld_direct:
				return getNextSchedulableAction_vld_direct();
			case s_vol_aspect:
				return getNextSchedulableAction_vol_aspect();
			case s_vol_control:
				return getNextSchedulableAction_vol_control();
			case s_vol_height:
				return getNextSchedulableAction_vol_height();
			case s_vol_misc:
				return getNextSchedulableAction_vol_misc();
			case s_vol_object:
				return getNextSchedulableAction_vol_object();
			case s_vol_shape:
				return getNextSchedulableAction_vol_shape();
			case s_vol_time_inc_res:
				return getNextSchedulableAction_vol_time_inc_res();
			case s_vol_vbv:
				return getNextSchedulableAction_vol_vbv();
			case s_vol_width:
				return getNextSchedulableAction_vol_width();
			case s_vop_coding:
				return getNextSchedulableAction_vop_coding();
			case s_vop_predict:
				return getNextSchedulableAction_vop_predict();
			case s_vop_timebase:
				return getNextSchedulableAction_vop_timebase();

			default:
				System.out.println("unknown state: %s\n" + _FSM_state);
				return null;
			}
		} else {
			return next;
		}
	}

	@Override
	public void resume() {
		suspended = false;
	}

	@Override
	public void suspend() {
		suspended = true;
	}

	// Functions/procedures

	private int mask_bits(int v, int n) {
		return v & (1 << n) - 1;
	}

	private boolean done_reading_bits() {
		int _tmp0_1;

		_tmp0_1 = bits_to_read_count;
		return _tmp0_1 < 0;
	}

	private int read_result() {
		int _tmp0_1;

		_tmp0_1 = read_result_in_progress;
		return _tmp0_1;
	}

	private int intra_max_level(boolean last, int run) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;
		int _tmp0_6;
		int _tmp0_7;
		int _tmp0_8;
		int _tmp0_9;
		int _tmp0_10;
		int _tmp0_11;
		int _tmp0_12;
		int _tmp0_13;
		int _tmp0_14;
		int _tmp0_15;
		int _tmp0_16;
		int _tmp0_17;
		int _tmp0_18;
		int _tmp0_19;
		int _tmp0_20;
		int _tmp0_21;
		int _tmp0_22;
		int _tmp0_23;
		int _tmp0_24;
		int _tmp0_25;

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

	private int inter_max_level(boolean last, int run) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;
		int _tmp0_6;
		int _tmp0_7;
		int _tmp0_8;
		int _tmp0_9;
		int _tmp0_10;
		int _tmp0_11;
		int _tmp0_12;
		int _tmp0_13;
		int _tmp0_14;
		int _tmp0_15;
		int _tmp0_16;
		int _tmp0_17;
		int _tmp0_18;
		int _tmp0_19;
		int _tmp0_20;
		int _tmp0_21;

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

	private int intra_max_run(boolean last, int level) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;
		int _tmp0_6;
		int _tmp0_7;
		int _tmp0_8;
		int _tmp0_9;
		int _tmp0_10;
		int _tmp0_11;
		int _tmp0_12;
		int _tmp0_13;
		int _tmp0_14;
		int _tmp0_15;
		int _tmp0_16;
		int _tmp0_17;
		int _tmp0_18;
		int _tmp0_19;
		int _tmp0_20;
		int _tmp0_21;

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

	private int inter_max_run(boolean last, int level) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;
		int _tmp0_6;
		int _tmp0_7;
		int _tmp0_8;
		int _tmp0_9;
		int _tmp0_10;
		int _tmp0_11;
		int _tmp0_12;
		int _tmp0_13;
		int _tmp0_14;
		int _tmp0_15;
		int _tmp0_16;
		int _tmp0_17;

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

	private boolean vld_success() {
		int _tmp0_1;

		_tmp0_1 = vld_codeword;
		return (_tmp0_1 & 3) == 0;
	}

	private boolean vld_failure() {
		int _tmp0_1;

		_tmp0_1 = vld_codeword;
		return (_tmp0_1 & 1) == 1;
	}

	private int vld_result() {
		int _tmp0_1;

		_tmp0_1 = vld_codeword;
		return _tmp0_1 >> 2;
	}

	private void set_bits_to_read(int count) {
		bits_to_read_count = count - 1;
		read_result_in_progress = 0;
	}

	private void next_mbxy() {
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;

		mbx++;
		_tmp1_1 = mbx;
		_tmp2_1 = vol_width;
		if (_tmp1_1 == _tmp2_1) {
			mbx = 0;
			_tmp3_1 = mby;
			mby = _tmp3_1 + 1;
		}
	}

	private void start_vld_engine(int index) {
		vld_index = index;
		vld_codeword = 2;
	}

	// Actions

	private void untagged01() {
		boolean[] bits = new boolean[1];
		boolean b_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp1_3;

		fifo_bits.get(bits);
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

	private boolean isSchedulable_untagged01() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp2_1 = done_reading_bits();
			_tmp0_1 = !_tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vol_object_layer_identification() {
		boolean[] bits = new boolean[1];
		boolean b_1;
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;

		fifo_bits.get(bits);
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

	private boolean isSchedulable_vol_object_layer_identification() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vol_aspect_detailed() {
		set_bits_to_read(16);
	}

	private boolean isSchedulable_vol_aspect_detailed() {
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = read_result();
			_tmp3_1 = ASPECT_RATIO_INFO_LENGTH;
			_tmp4_1 = mask_bits(_tmp2_1, _tmp3_1);
			_tmp5_1 = ASPECT_RATIO_INFO_IS_DETAILED;
			_tmp0_1 = _tmp1_1 && _tmp4_1 == _tmp5_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vol_control_detailed() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		set_bits_to_read(3);
		bit_count++;
	}

	private boolean isSchedulable_vol_control_detailed() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean b_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			b_1 = bits[0];
			_tmp2_1 = done_reading_bits();
			_tmp0_1 = _tmp2_1 && b_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vol_vbv_detailed() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		set_bits_to_read(79);
		bit_count++;
	}

	private boolean isSchedulable_vol_vbv_detailed() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean b_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			b_1 = bits[0];
			_tmp2_1 = done_reading_bits();
			_tmp0_1 = _tmp2_1 && b_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vol_shape() {
		set_bits_to_read(21);
	}

	private boolean isSchedulable_vol_shape() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vol_time_inc_res() {
		int _tmp0_1;
		int time_inc_res0_1;
		int count0_1;
		int ones0_1;
		int ones0_2;
		int ones0_3;
		int ones0_4;
		int count0_2;
		int count0_3;
		int time_inc_res0_2;
		int time_inc_res0_3;
		int _tmp2_1;
		int _tmp2_2;
		int _tmp2_3;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp5_1;
		int _tmp4_3;
		int _tmp6_1;
		int _tmp8_1;
		int _tmp7_1;
		int _tmp7_2;
		int _tmp7_3;

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

	private boolean isSchedulable_vol_time_inc_res() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void set_vol_width() {
		int _tmp0_1;
		int _tmp3_1;

		_tmp0_1 = read_result();
		_tmp3_1 = mask_bits(_tmp0_1 >> 5, 9);
		vol_width = _tmp3_1;
		set_bits_to_read(14);
	}

	private boolean isSchedulable_set_vol_width() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void set_vol_height() {
		int _tmp0_1;
		int _tmp3_1;

		_tmp0_1 = read_result();
		_tmp3_1 = mask_bits(_tmp0_1 >> 5, 13);
		vol_height = _tmp3_1;
		set_bits_to_read(9);
	}

	private boolean isSchedulable_set_vol_height() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void byte_align() {
		int _tmp0_1;

		_tmp0_1 = bit_count;
		set_bits_to_read(8 - (_tmp0_1 & 7));
	}

	private boolean isSchedulable_byte_align() {
		if (true) {
		}
		return true;
	}

	private void vop_predict_supported() {
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

	private boolean isSchedulable_vop_predict_supported() {
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = read_result();
			_tmp3_1 = VOP_PREDICTION_LENGTH;
			_tmp4_1 = mask_bits(_tmp2_1, _tmp3_1);
			_tmp5_1 = I_VOP;
			_tmp6_1 = read_result();
			_tmp7_1 = VOP_PREDICTION_LENGTH;
			_tmp8_1 = mask_bits(_tmp6_1, _tmp7_1);
			_tmp9_1 = P_VOP;
			_tmp0_1 = _tmp1_1 && (_tmp4_1 == _tmp5_1 || _tmp8_1 == _tmp9_1);
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vop_timebase_one() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		bit_count++;
	}

	private boolean isSchedulable_vop_timebase_one() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean b_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			b_1 = bits[0];
			_tmp0_1 = b_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vop_timebase_zero() {
		boolean[] bits = new boolean[1];
		int _tmp2_1;

		fifo_bits.get(bits);
		bit_count++;
		_tmp2_1 = mylog;
		set_bits_to_read(1 + _tmp2_1 + 1);
	}

	private boolean isSchedulable_vop_timebase_zero() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vop_coding_uncoded() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		comp = 0;
		bit_count++;
	}

	private boolean isSchedulable_vop_coding_uncoded() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean b_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			b_1 = bits[0];
			_tmp2_1 = done_reading_bits();
			_tmp0_1 = _tmp2_1 && !b_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vop_coding_coded() {
		boolean[] bits = new boolean[1];
		boolean _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp1_3;

		fifo_bits.get(bits);
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

	private boolean isSchedulable_vop_coding_coded() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void send_new_vop_cmd() {
		int[] BTYPE = new int[1];
		boolean round0_1;
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp2_2;
		int _tmp2_3;
		int cmd0_1;
		boolean _tmp5_1;
		int _tmp6_1;
		boolean round0_2;
		boolean round0_3;
		int _tmp10_1;
		int vop_quant0_1;
		int vop_quant0_2;
		int _tmp13_1;
		int _tmp15_1;
		int vop_quant0_3;
		int cmd0_2;
		int _tmp18_1;
		int _tmp18_2;
		int _tmp18_3;
		int cmd0_3;
		int _tmp20_1;
		int cmd0_4;

		round0_1 = false;
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
		fifo_BTYPE.put(BTYPE);
	}

	private boolean isSchedulable_send_new_vop_cmd() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void send_new_vop_width() {
		int[] WIDTH = new int[1];
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int _tmp1_1;

		_tmp0_1 = vol_width;
		BTYPE[0] = _tmp0_1;
		fifo_BTYPE.put(BTYPE);
		_tmp1_1 = vol_width;
		WIDTH[0] = _tmp1_1;
		fifo_WIDTH.put(WIDTH);
	}

	private boolean isSchedulable_send_new_vop_width() {
		if (true) {
		}
		return true;
	}

	private void send_new_vop_height() {
		int[] HEIGHT = new int[1];
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int _tmp1_1;

		_tmp0_1 = vol_height;
		BTYPE[0] = _tmp0_1;
		fifo_BTYPE.put(BTYPE);
		_tmp1_1 = vol_height;
		HEIGHT[0] = _tmp1_1;
		fifo_HEIGHT.put(HEIGHT);
	}

	private boolean isSchedulable_send_new_vop_height() {
		if (true) {
		}
		return true;
	}

	private void mb_done() {
	}

	private boolean isSchedulable_mb_done() {
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = mby;
			_tmp2_1 = vol_height;
			_tmp0_1 = _tmp1_1 == _tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_mcbpc_ivop() {
		start_vld_engine(0);
	}

	private boolean isSchedulable_get_mcbpc_ivop() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = prediction_is_IVOP;
			_tmp0_1 = _tmp1_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_mcbpc_pvop() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		start_vld_engine(16);
		bit_count++;
	}

	private boolean isSchedulable_get_mcbpc_pvop() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean b_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			b_1 = bits[0];
			_tmp2_1 = prediction_is_IVOP;
			_tmp0_1 = !_tmp2_1 && !b_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mcbpc_pvop_uncoded() {
		int[] BTYPE = new int[1];
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		next_mbxy();
		bit_count++;
		BTYPE[0] = 512;
		fifo_BTYPE.put(BTYPE);
	}

	private boolean isSchedulable_mcbpc_pvop_uncoded() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp2_1 = prediction_is_IVOP;
			_tmp0_1 = !_tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mcbpc_pvop_uncoded1() {
		int[] BTYPE = new int[1];

		BTYPE[0] = 512;
		fifo_BTYPE.put(BTYPE);
	}

	private boolean isSchedulable_mcbpc_pvop_uncoded1() {
		if (true) {
		}
		return true;
	}

	private void get_mbtype_noac() {
		int mcbpc0_1;
		int type0_1;

		mcbpc0_1 = vld_result();
		type0_1 = mcbpc0_1 & 7;
		btype_is_INTRA = type0_1 >= 3;
		fourmvflag = type0_1 == 2;
		cbpc = mcbpc0_1 >> 4 & 3;
		acpredflag = false;
		start_vld_engine(58);
	}

	private boolean isSchedulable_get_mbtype_noac() {
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = vld_success();
			_tmp2_1 = vld_result();
			_tmp3_1 = vld_result();
			_tmp0_1 = _tmp1_1 && (_tmp2_1 & 7) != 3 && (_tmp3_1 & 7) != 4;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_mbtype_ac() {
		boolean[] bits = new boolean[1];
		boolean b_1;
		int mcbpc0_1;

		fifo_bits.get(bits);
		b_1 = bits[0];
		mcbpc0_1 = vld_result();
		btype_is_INTRA = true;
		cbpc = mcbpc0_1 >> 4 & 3;
		acpredflag = b_1;
		bit_count++;
		start_vld_engine(58);
	}

	private boolean isSchedulable_get_mbtype_ac() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void final_cbpy_inter() {
		int _tmp0_1;
		int cbpy0_1;
		int _tmp1_1;

		_tmp0_1 = vld_result();
		cbpy0_1 = 15 - _tmp0_1;
		comp = 0;
		mvcomp = 0;
		_tmp1_1 = cbpc;
		cbp = cbpy0_1 << 2 | _tmp1_1;
	}

	private boolean isSchedulable_final_cbpy_inter() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = vld_success();
			_tmp2_1 = btype_is_INTRA;
			_tmp0_1 = _tmp1_1 && !_tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void final_cbpy_intra() {
		int cbpy0_1;
		int _tmp0_1;

		cbpy0_1 = vld_result();
		comp = 0;
		_tmp0_1 = cbpc;
		cbp = cbpy0_1 << 2 | _tmp0_1;
	}

	private boolean isSchedulable_final_cbpy_intra() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mb_dispatch_done() {
		next_mbxy();
	}

	private boolean isSchedulable_mb_dispatch_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = comp;
			_tmp0_1 = _tmp1_1 == 6;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mb_dispatch_intra() {
		int[] BTYPE = new int[1];
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;
		int cmd0_2;
		boolean _tmp6_1;
		int _tmp7_1;
		int _tmp7_2;
		int _tmp7_3;
		int cmd0_3;

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
		fifo_BTYPE.put(BTYPE);
	}

	private boolean isSchedulable_mb_dispatch_intra() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = btype_is_INTRA;
			_tmp0_1 = _tmp1_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mb_dispatch_inter_no_ac() {
		int[] BTYPE = new int[1];
		boolean _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;

		ac_coded = false;
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
		fifo_BTYPE.put(BTYPE);
	}

	private boolean isSchedulable_mb_dispatch_inter_no_ac() {
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = cbp;
			_tmp2_1 = comp;
			_tmp0_1 = (_tmp1_1 & 1 << (5 - _tmp2_1)) == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mb_dispatch_inter_ac_coded() {
		int[] BTYPE = new int[1];
		boolean _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;

		ac_coded = true;
		_tmp3_1 = fourmvflag;
		if (_tmp3_1) {
			_tmp4_1 = 4;
			_tmp4_2 = _tmp4_1;
		} else {
			_tmp4_3 = 0;
			_tmp4_2 = _tmp4_3;
		}
		BTYPE[0] = 514 | 8 | _tmp4_2;
		fifo_BTYPE.put(BTYPE);
	}

	private boolean isSchedulable_mb_dispatch_inter_ac_coded() {
		if (true) {
		}
		return true;
	}

	private void vld_start_intra() {
		int _tmp0_1;
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
		b_last = false;
	}

	private boolean isSchedulable_vld_start_intra() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = btype_is_INTRA;
			_tmp0_1 = _tmp1_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_start_inter_ac_coded() {
		b_last = false;
	}

	private boolean isSchedulable_vld_start_inter_ac_coded() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = ac_coded;
			_tmp0_1 = _tmp1_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_start_inter_not_ac_coded() {
		boolean[] LAST = new boolean[1];
		int[] VALUE = new int[1];
		int[] RUN = new int[1];

		b_last = true;
		RUN[0] = 0;
		fifo_RUN.put(RUN);
		VALUE[0] = 0;
		fifo_VALUE.put(VALUE);
		LAST[0] = true;
		fifo_LAST.put(LAST);
	}

	private boolean isSchedulable_vld_start_inter_not_ac_coded() {
		if (true) {
		}
		return true;
	}

	private void get_dc_bits_none() {
		boolean[] LAST = new boolean[1];
		int[] VALUE = new int[1];
		int[] RUN = new int[1];
		boolean _tmp0_1;
		boolean _tmp1_1;

		_tmp0_1 = ac_coded;
		b_last = !_tmp0_1;
		RUN[0] = 0;
		fifo_RUN.put(RUN);
		VALUE[0] = 0;
		fifo_VALUE.put(VALUE);
		_tmp1_1 = ac_coded;
		LAST[0] = !_tmp1_1;
		fifo_LAST.put(LAST);
	}

	private boolean isSchedulable_get_dc_bits_none() {
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = vld_success();
			_tmp2_1 = vld_result();
			_tmp0_1 = _tmp1_1 && _tmp2_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_dc_bits_some() {
		int _tmp0_1;
		int _tmp1_1;

		_tmp0_1 = vld_result();
		dc_bits = _tmp0_1;
		_tmp1_1 = dc_bits;
		set_bits_to_read(_tmp1_1);
	}

	private boolean isSchedulable_get_dc_bits_some() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void dc_bits_shift() {
		int _tmp0_1;
		int count0_1;
		int shift0_1;
		int shift0_2;
		int shift0_3;
		int count0_2;
		int count0_3;

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

	private boolean isSchedulable_dc_bits_shift() {
		if (true) {
		}
		return true;
	}

	private void get_dc() {
		boolean[] LAST = new boolean[1];
		int[] VALUE = new int[1];
		int[] RUN = new int[1];
		int v0_1;
		int _tmp0_1;
		int _tmp1_1;
		int v0_2;
		int v0_3;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp3_2;
		int _tmp3_3;
		boolean _tmp5_1;
		boolean _tmp6_1;

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
		fifo_RUN.put(RUN);
		VALUE[0] = v0_3;
		fifo_VALUE.put(VALUE);
		_tmp6_1 = ac_coded;
		LAST[0] = !_tmp6_1;
		fifo_LAST.put(LAST);
	}

	private boolean isSchedulable_get_dc() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void block_done() {
		comp++;
	}

	private boolean isSchedulable_block_done() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = b_last;
			_tmp0_1 = _tmp1_1 && _tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void dct_coeff() {
		boolean _tmp0_1;
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

	private boolean isSchedulable_dct_coeff() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_code() {
		boolean[] LAST = new boolean[1];
		int[] RUN = new int[1];
		int[] VALUE = new int[1];
		boolean[] bits = new boolean[1];
		boolean sign_1;
		int val0_1;
		boolean _tmp0_1;
		int run0_1;
		int run0_2;
		int run0_3;
		boolean _tmp1_1;
		boolean last0_1;
		boolean last0_2;
		boolean last0_3;
		boolean _tmp2_1;
		int level0_1;
		int level0_2;
		int level0_3;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;

		fifo_bits.get(bits);
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
		fifo_VALUE.put(VALUE);
		RUN[0] = run0_2;
		fifo_RUN.put(RUN);
		LAST[0] = last0_2;
		fifo_LAST.put(LAST);
	}

	private boolean isSchedulable_vld_code() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		int _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp2_1 = vld_success();
			_tmp3_1 = vld_result();
			_tmp0_1 = _tmp2_1 && _tmp3_1 != 7167;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_level() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp2_2;
		int _tmp2_3;

		fifo_bits.get(bits);
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

	private boolean isSchedulable_vld_level() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean level_offset_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			level_offset_1 = bits[0];
			_tmp2_1 = vld_success();
			_tmp0_1 = _tmp2_1 && !level_offset_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_run_or_direct() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		bit_count++;
	}

	private boolean isSchedulable_vld_run_or_direct() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_run() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp2_2;
		int _tmp2_3;

		fifo_bits.get(bits);
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

	private boolean isSchedulable_vld_run() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean run_offset_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			run_offset_1 = bits[0];
			_tmp0_1 = !run_offset_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_direct_read() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		bit_count++;
		set_bits_to_read(21);
	}

	private boolean isSchedulable_vld_direct_read() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_direct() {
		boolean[] LAST = new boolean[1];
		int[] RUN = new int[1];
		int[] VALUE = new int[1];
		int _tmp0_1;
		boolean last0_1;
		int _tmp5_1;
		int run0_1;
		int _tmp10_1;
		int level0_1;
		boolean sign0_1;
		boolean sign0_2;
		int level0_2;
		int level0_3;
		boolean sign0_3;
		int _tmp13_1;
		int _tmp13_2;
		int _tmp13_3;

		_tmp0_1 = read_result();
		last0_1 = (_tmp0_1 >> 20 & 1) != 0;
		_tmp5_1 = read_result();
		run0_1 = _tmp5_1 >> 14 & 63;
		_tmp10_1 = read_result();
		level0_1 = _tmp10_1 >> 1 & 4095;
		if (level0_1 >= 2048) {
			sign0_1 = true;
			level0_2 = 4096 - level0_1;
			sign0_2 = sign0_1;
			level0_3 = level0_2;
		} else {
			sign0_3 = false;
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
		fifo_VALUE.put(VALUE);
		RUN[0] = run0_1;
		fifo_RUN.put(RUN);
		LAST[0] = last0_1;
		fifo_LAST.put(LAST);
	}

	private boolean isSchedulable_vld_direct() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void do_level_lookup() {
		int val0_1;
		int _tmp0_1;
		int _tmp1_1;

		val0_1 = vld_result();
		_tmp0_1 = inter_max_level((val0_1 >> 12 & 1) != 0, val0_1 >> 4 & 255);
		level_lookup_inter = _tmp0_1;
		_tmp1_1 = intra_max_level((val0_1 >> 16 & 1) != 0, val0_1 >> 8 & 255);
		level_lookup_intra = _tmp1_1;
	}

	private boolean isSchedulable_do_level_lookup() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_level_lookup() {
		boolean[] LAST = new boolean[1];
		int[] RUN = new int[1];
		int[] VALUE = new int[1];
		boolean[] bits = new boolean[1];
		boolean sign_1;
		int val0_1;
		boolean _tmp0_1;
		int run0_1;
		int run0_2;
		int run0_3;
		boolean _tmp1_1;
		boolean last0_1;
		boolean last0_2;
		boolean last0_3;
		boolean _tmp2_1;
		int _tmp3_1;
		int level0_1;
		int level0_2;
		int _tmp4_1;
		int level0_3;
		int _tmp6_1;
		int _tmp6_2;
		int _tmp6_3;

		fifo_bits.get(bits);
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
		fifo_VALUE.put(VALUE);
		RUN[0] = run0_2;
		fifo_RUN.put(RUN);
		LAST[0] = last0_2;
		fifo_LAST.put(LAST);
	}

	private boolean isSchedulable_vld_level_lookup() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void do_run_lookup() {
		int val0_1;
		int _tmp0_1;
		int _tmp1_1;

		val0_1 = vld_result();
		_tmp0_1 = inter_max_run((val0_1 >> 12 & 1) != 0, val0_1 & 15);
		run_lookup_inter = _tmp0_1;
		_tmp1_1 = intra_max_run((val0_1 >> 16 & 1) != 0, val0_1 & 255);
		run_lookup_intra = _tmp1_1;
	}

	private boolean isSchedulable_do_run_lookup() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void vld_run_lookup() {
		boolean[] LAST = new boolean[1];
		int[] RUN = new int[1];
		int[] VALUE = new int[1];
		boolean[] bits = new boolean[1];
		boolean sign_1;
		int val0_1;
		boolean _tmp0_1;
		boolean last0_1;
		boolean last0_2;
		boolean last0_3;
		boolean _tmp1_1;
		int level0_1;
		int level0_2;
		int level0_3;
		boolean _tmp2_1;
		int _tmp4_1;
		int _tmp3_1;
		int _tmp3_2;
		int _tmp5_1;
		int _tmp3_3;
		int run0_1;
		int _tmp7_1;
		int _tmp7_2;
		int _tmp7_3;

		fifo_bits.get(bits);
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
		fifo_VALUE.put(VALUE);
		RUN[0] = run0_1;
		fifo_RUN.put(RUN);
		LAST[0] = last0_2;
		fifo_LAST.put(LAST);
	}

	private boolean isSchedulable_vld_run_lookup() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mvcode_done() {
	}

	private boolean isSchedulable_mvcode_done() {
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = mvcomp;
			_tmp2_1 = mvcomp;
			_tmp3_1 = fourmvflag;
			_tmp0_1 = _tmp1_1 == 4 || _tmp2_1 == 1 && !_tmp3_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mvcode() {
		start_vld_engine(616);
	}

	private boolean isSchedulable_mvcode() {
		if (true) {
		}
		return true;
	}

	private void mag_x() {
		int[] MV = new int[1];
		int mvval0_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp2_1;
		int _tmp1_3;

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
		fifo_MV.put(MV);
	}

	private boolean isSchedulable_mag_x() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_residual_x() {
		int[] MV = new int[1];
		int _tmp0_1;

		_tmp0_1 = read_result();
		MV[0] = _tmp0_1;
		fifo_MV.put(MV);
	}

	private boolean isSchedulable_get_residual_x() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void mag_y() {
		int[] MV = new int[1];
		int mvval0_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp2_1;
		int _tmp1_3;

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
		fifo_MV.put(MV);
	}

	private boolean isSchedulable_mag_y() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = vld_success();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_residual_y() {
		int[] MV = new int[1];
		int _tmp1_1;

		mvcomp++;
		_tmp1_1 = read_result();
		MV[0] = _tmp1_1;
		fifo_MV.put(MV);
	}

	private boolean isSchedulable_get_residual_y() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void untagged02() {
		boolean[] bits = new boolean[1];
		boolean b_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp1_3;
		int _tmp2_1;
		int _tmp3_1;

		fifo_bits.get(bits);
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

	private boolean isSchedulable_untagged02() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp2_1 = vld_codeword;
			_tmp0_1 = (_tmp2_1 & 3) == 2;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void do_vld_failure() {
	}

	private boolean isSchedulable_do_vld_failure() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = vld_failure();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void generic_done() {
	}

	private boolean isSchedulable_generic_done() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void generic_done_with_bitread() {
		boolean[] bits = new boolean[1];

		fifo_bits.get(bits);
		bit_count++;
	}

	private boolean isSchedulable_generic_done_with_bitread() {
		boolean[] bits = new boolean[1];
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_bits.hasTokens(1);
		if (_tmp1_1) {
			fifo_bits.peek(bits);
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void test_zero_byte() {
	}

	private boolean isSchedulable_test_zero_byte() {
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = read_result();
			_tmp0_1 = _tmp1_1 && (_tmp2_1 & 255) == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void test_vo_byte() {
	}

	private boolean isSchedulable_test_vo_byte() {
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = read_result();
			_tmp0_1 = _tmp1_1 && (_tmp2_1 & 254) == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void test_vol_byte() {
		set_bits_to_read(9);
	}

	private boolean isSchedulable_test_vol_byte() {
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = read_result();
			_tmp3_1 = VOL_START_CODE;
			_tmp0_1 = _tmp1_1 && _tmp2_1 == _tmp3_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void test_vop_byte() {
		mbx = 0;
		mby = 0;
		set_bits_to_read(2);
	}

	private boolean isSchedulable_test_vop_byte() {
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = read_result();
			_tmp3_1 = VOP_START_CODE;
			_tmp0_1 = _tmp1_1 && _tmp2_1 == _tmp3_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void test_one_byte() {
	}

	private boolean isSchedulable_test_one_byte() {
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = done_reading_bits();
			_tmp2_1 = read_result();
			_tmp0_1 = _tmp1_1 && (_tmp2_1 & 255) == 1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void request_byte() {
		set_bits_to_read(8);
	}

	private boolean isSchedulable_request_byte() {
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp0_1 = done_reading_bits();
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("bits".equals(portName)) {
			fifo_bits = fifo;
		} else if ("BTYPE".equals(portName)) {
			fifo_BTYPE = fifo;
		} else if ("MV".equals(portName)) {
			fifo_MV = fifo;
		} else if ("RUN".equals(portName)) {
			fifo_RUN = fifo;
		} else if ("VALUE".equals(portName)) {
			fifo_VALUE = fifo;
		} else if ("LAST".equals(portName)) {
			fifo_LAST = fifo;
		} else if ("WIDTH".equals(portName)) {
			fifo_WIDTH = fifo;
		} else if ("HEIGHT".equals(portName)) {
			fifo_HEIGHT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_block,
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

	private States _FSM_state = States.s_stuck_1a;

	private boolean outside_FSM_scheduler() {
		boolean res = false;
		int i = 0;
		if (isSchedulable_untagged01()) {
			untagged01();
			res = true;
			i++;
		} else if (isSchedulable_untagged02()) {
			untagged02();
			res = true;
			i++;
		}
		return res;
	}

	private boolean block_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mb_dispatch_done()) {
			mb_dispatch_done();
			_FSM_state = States.s_mb;
			res = true;
		} else if (isSchedulable_mb_dispatch_intra()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mb_dispatch_intra();
				_FSM_state = States.s_texture;
				res = true;
			}
		} else if (isSchedulable_mb_dispatch_inter_no_ac()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mb_dispatch_inter_no_ac();
				_FSM_state = States.s_block;
				res = true;
			}
		} else if (isSchedulable_mb_dispatch_inter_ac_coded()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mb_dispatch_inter_ac_coded();
				_FSM_state = States.s_texture;
				res = true;
			}
		}
		return res;
	}

	private boolean final_cbpy_state_scheduler() {
		boolean res = false;
		if (isSchedulable_final_cbpy_inter()) {
			final_cbpy_inter();
			_FSM_state = States.s_mv;
			res = true;
		} else if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		} else if (isSchedulable_final_cbpy_intra()) {
			final_cbpy_intra();
			_FSM_state = States.s_block;
			res = true;
		}
		return res;
	}

	private boolean get_dc_state_scheduler() {
		boolean res = false;
		if (isSchedulable_dc_bits_shift()) {
			dc_bits_shift();
			_FSM_state = States.s_get_dc_a;
			res = true;
		}
		return res;
	}

	private boolean get_dc_a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_dc()) {
			if (fifo_RUN.hasRoom(1) && fifo_VALUE.hasRoom(1) && fifo_LAST.hasRoom(1)) {
				get_dc();
				_FSM_state = States.s_texac;
				res = true;
			}
		}
		return res;
	}

	private boolean get_dc_bits_state_scheduler() {
		boolean res = false;
		if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		} else if (isSchedulable_get_dc_bits_none()) {
			if (fifo_LAST.hasRoom(1) && fifo_VALUE.hasRoom(1) && fifo_RUN.hasRoom(1)) {
				get_dc_bits_none();
				_FSM_state = States.s_texac;
				res = true;
			}
		} else if (isSchedulable_get_dc_bits_some()) {
			get_dc_bits_some();
			_FSM_state = States.s_get_dc;
			res = true;
		}
		return res;
	}

	private boolean get_mbtype_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_mbtype_noac()) {
			get_mbtype_noac();
			_FSM_state = States.s_final_cbpy;
			res = true;
		} else if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		} else if (isSchedulable_get_mbtype_ac()) {
			get_mbtype_ac();
			_FSM_state = States.s_final_cbpy;
			res = true;
		}
		return res;
	}

	private boolean get_residual_x_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_x()) {
			if (fifo_MV.hasRoom(1)) {
				get_residual_x();
				_FSM_state = States.s_mv_y;
				res = true;
			}
		}
		return res;
	}

	private boolean get_residual_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_y()) {
			if (fifo_MV.hasRoom(1)) {
				get_residual_y();
				_FSM_state = States.s_mv;
				res = true;
			}
		}
		return res;
	}

	private boolean mag_x_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mag_x()) {
			if (fifo_MV.hasRoom(1)) {
				mag_x();
				_FSM_state = States.s_get_residual_x;
				res = true;
			}
		} else if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		}
		return res;
	}

	private boolean mag_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mag_y()) {
			if (fifo_MV.hasRoom(1)) {
				mag_y();
				_FSM_state = States.s_get_residual_y;
				res = true;
			}
		} else if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		}
		return res;
	}

	private boolean mb_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mb_done()) {
			mb_done();
			_FSM_state = States.s_stuck;
			res = true;
		} else if (isSchedulable_get_mcbpc_ivop()) {
			get_mcbpc_ivop();
			_FSM_state = States.s_get_mbtype;
			res = true;
		} else if (isSchedulable_get_mcbpc_pvop()) {
			get_mcbpc_pvop();
			_FSM_state = States.s_get_mbtype;
			res = true;
		} else if (isSchedulable_mcbpc_pvop_uncoded()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mcbpc_pvop_uncoded();
				_FSM_state = States.s_pvop_uncoded1;
				res = true;
			}
		}
		return res;
	}

	private boolean mv_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mvcode_done()) {
			mvcode_done();
			_FSM_state = States.s_block;
			res = true;
		} else if (isSchedulable_mvcode()) {
			mvcode();
			_FSM_state = States.s_mag_x;
			res = true;
		}
		return res;
	}

	private boolean mv_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mvcode()) {
			mvcode();
			_FSM_state = States.s_mag_y;
			res = true;
		}
		return res;
	}

	private boolean pvop_uncoded1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mcbpc_pvop_uncoded1();
				_FSM_state = States.s_pvop_uncoded2;
				res = true;
			}
		}
		return res;
	}

	private boolean pvop_uncoded2_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mcbpc_pvop_uncoded1();
				_FSM_state = States.s_pvop_uncoded3;
				res = true;
			}
		}
		return res;
	}

	private boolean pvop_uncoded3_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mcbpc_pvop_uncoded1();
				_FSM_state = States.s_pvop_uncoded4;
				res = true;
			}
		}
		return res;
	}

	private boolean pvop_uncoded4_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mcbpc_pvop_uncoded1();
				_FSM_state = States.s_pvop_uncoded5;
				res = true;
			}
		}
		return res;
	}

	private boolean pvop_uncoded5_state_scheduler() {
		boolean res = false;
		if (isSchedulable_mcbpc_pvop_uncoded1()) {
			if (fifo_BTYPE.hasRoom(1)) {
				mcbpc_pvop_uncoded1();
				_FSM_state = States.s_mb;
				res = true;
			}
		}
		return res;
	}

	private boolean send_new_vop_height_state_scheduler() {
		boolean res = false;
		if (isSchedulable_send_new_vop_height()) {
			if (fifo_BTYPE.hasRoom(1) && fifo_HEIGHT.hasRoom(1)) {
				send_new_vop_height();
				_FSM_state = States.s_mb;
				res = true;
			}
		}
		return res;
	}

	private boolean send_new_vop_info_state_scheduler() {
		boolean res = false;
		if (isSchedulable_send_new_vop_cmd()) {
			if (fifo_BTYPE.hasRoom(1)) {
				send_new_vop_cmd();
				_FSM_state = States.s_send_new_vop_width;
				res = true;
			}
		}
		return res;
	}

	private boolean send_new_vop_width_state_scheduler() {
		boolean res = false;
		if (isSchedulable_send_new_vop_width()) {
			if (fifo_WIDTH.hasRoom(1) && fifo_BTYPE.hasRoom(1)) {
				send_new_vop_width();
				_FSM_state = States.s_send_new_vop_height;
				res = true;
			}
		}
		return res;
	}

	private boolean stuck_state_scheduler() {
		boolean res = false;
		if (isSchedulable_byte_align()) {
			byte_align();
			_FSM_state = States.s_stuck_1a;
			res = true;
		}
		return res;
	}

	private boolean stuck_1a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_request_byte()) {
			request_byte();
			_FSM_state = States.s_stuck_1b;
			res = true;
		}
		return res;
	}

	private boolean stuck_1b_state_scheduler() {
		boolean res = false;
		if (isSchedulable_test_zero_byte()) {
			test_zero_byte();
			_FSM_state = States.s_stuck_2a;
			res = true;
		} else if (isSchedulable_generic_done()) {
			generic_done();
			_FSM_state = States.s_stuck_1a;
			res = true;
		}
		return res;
	}

	private boolean stuck_2a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_request_byte()) {
			request_byte();
			_FSM_state = States.s_stuck_2b;
			res = true;
		}
		return res;
	}

	private boolean stuck_2b_state_scheduler() {
		boolean res = false;
		if (isSchedulable_test_zero_byte()) {
			test_zero_byte();
			_FSM_state = States.s_stuck_3a;
			res = true;
		} else if (isSchedulable_generic_done()) {
			generic_done();
			_FSM_state = States.s_stuck_1a;
			res = true;
		}
		return res;
	}

	private boolean stuck_3a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_request_byte()) {
			request_byte();
			_FSM_state = States.s_stuck_3b;
			res = true;
		}
		return res;
	}

	private boolean stuck_3b_state_scheduler() {
		boolean res = false;
		if (isSchedulable_test_one_byte()) {
			test_one_byte();
			_FSM_state = States.s_stuck_4a;
			res = true;
		} else if (isSchedulable_test_zero_byte()) {
			test_zero_byte();
			_FSM_state = States.s_stuck_3a;
			res = true;
		} else if (isSchedulable_generic_done()) {
			generic_done();
			_FSM_state = States.s_stuck_1a;
			res = true;
		}
		return res;
	}

	private boolean stuck_4a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_request_byte()) {
			request_byte();
			_FSM_state = States.s_stuck_4b;
			res = true;
		}
		return res;
	}

	private boolean stuck_4b_state_scheduler() {
		boolean res = false;
		if (isSchedulable_test_vo_byte()) {
			test_vo_byte();
			_FSM_state = States.s_stuck_1a;
			res = true;
		} else if (isSchedulable_test_vol_byte()) {
			test_vol_byte();
			_FSM_state = States.s_vol_object;
			res = true;
		} else if (isSchedulable_test_vop_byte()) {
			test_vop_byte();
			_FSM_state = States.s_vop_predict;
			res = true;
		} else if (isSchedulable_generic_done()) {
			generic_done();
			_FSM_state = States.s_stuck_1a;
			res = true;
		}
		return res;
	}

	private boolean texac_state_scheduler() {
		boolean res = false;
		if (isSchedulable_block_done()) {
			block_done();
			_FSM_state = States.s_block;
			res = true;
		} else if (isSchedulable_dct_coeff()) {
			dct_coeff();
			_FSM_state = States.s_vld1;
			res = true;
		}
		return res;
	}

	private boolean texture_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vld_start_intra()) {
			vld_start_intra();
			_FSM_state = States.s_get_dc_bits;
			res = true;
		} else if (isSchedulable_vld_start_inter_ac_coded()) {
			vld_start_inter_ac_coded();
			_FSM_state = States.s_texac;
			res = true;
		} else if (isSchedulable_vld_start_inter_not_ac_coded()) {
			if (fifo_VALUE.hasRoom(1) && fifo_LAST.hasRoom(1) && fifo_RUN.hasRoom(1)) {
				vld_start_inter_not_ac_coded();
				_FSM_state = States.s_texac;
				res = true;
			}
		}
		return res;
	}

	private boolean vld1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vld_code()) {
			if (fifo_LAST.hasRoom(1) && fifo_RUN.hasRoom(1) && fifo_VALUE.hasRoom(1)) {
				vld_code();
				_FSM_state = States.s_texac;
				res = true;
			}
		} else if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		} else if (isSchedulable_vld_level()) {
			vld_level();
			_FSM_state = States.s_vld4;
			res = true;
		} else if (isSchedulable_vld_run_or_direct()) {
			vld_run_or_direct();
			_FSM_state = States.s_vld7;
			res = true;
		}
		return res;
	}

	private boolean vld4_state_scheduler() {
		boolean res = false;
		if (isSchedulable_do_level_lookup()) {
			do_level_lookup();
			_FSM_state = States.s_vld4a;
			res = true;
		} else if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		}
		return res;
	}

	private boolean vld4a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vld_level_lookup()) {
			if (fifo_VALUE.hasRoom(1) && fifo_RUN.hasRoom(1) && fifo_LAST.hasRoom(1)) {
				vld_level_lookup();
				_FSM_state = States.s_texac;
				res = true;
			}
		}
		return res;
	}

	private boolean vld6_state_scheduler() {
		boolean res = false;
		if (isSchedulable_do_run_lookup()) {
			do_run_lookup();
			_FSM_state = States.s_vld6a;
			res = true;
		} else if (isSchedulable_do_vld_failure()) {
			do_vld_failure();
			_FSM_state = States.s_stuck;
			res = true;
		}
		return res;
	}

	private boolean vld6a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vld_run_lookup()) {
			if (fifo_VALUE.hasRoom(1) && fifo_LAST.hasRoom(1) && fifo_RUN.hasRoom(1)) {
				vld_run_lookup();
				_FSM_state = States.s_texac;
				res = true;
			}
		}
		return res;
	}

	private boolean vld7_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vld_run()) {
			vld_run();
			_FSM_state = States.s_vld6;
			res = true;
		} else if (isSchedulable_vld_direct_read()) {
			vld_direct_read();
			_FSM_state = States.s_vld_direct;
			res = true;
		}
		return res;
	}

	private boolean vld_direct_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vld_direct()) {
			if (fifo_RUN.hasRoom(1) && fifo_LAST.hasRoom(1) && fifo_VALUE.hasRoom(1)) {
				vld_direct();
				_FSM_state = States.s_texac;
				res = true;
			}
		}
		return res;
	}

	private boolean vol_aspect_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vol_aspect_detailed()) {
			vol_aspect_detailed();
			_FSM_state = States.s_vol_control;
			res = true;
		} else if (isSchedulable_generic_done()) {
			generic_done();
			_FSM_state = States.s_vol_control;
			res = true;
		}
		return res;
	}

	private boolean vol_control_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vol_control_detailed()) {
			vol_control_detailed();
			_FSM_state = States.s_vol_vbv;
			res = true;
		} else if (isSchedulable_generic_done_with_bitread()) {
			generic_done_with_bitread();
			_FSM_state = States.s_vol_shape;
			res = true;
		}
		return res;
	}

	private boolean vol_height_state_scheduler() {
		boolean res = false;
		if (isSchedulable_set_vol_height()) {
			set_vol_height();
			_FSM_state = States.s_vol_misc;
			res = true;
		}
		return res;
	}

	private boolean vol_misc_state_scheduler() {
		boolean res = false;
		if (isSchedulable_generic_done()) {
			generic_done();
			_FSM_state = States.s_stuck;
			res = true;
		}
		return res;
	}

	private boolean vol_object_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vol_object_layer_identification()) {
			vol_object_layer_identification();
			_FSM_state = States.s_vol_aspect;
			res = true;
		}
		return res;
	}

	private boolean vol_shape_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vol_shape()) {
			vol_shape();
			_FSM_state = States.s_vol_time_inc_res;
			res = true;
		}
		return res;
	}

	private boolean vol_time_inc_res_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vol_time_inc_res()) {
			vol_time_inc_res();
			_FSM_state = States.s_vol_width;
			res = true;
		}
		return res;
	}

	private boolean vol_vbv_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vol_vbv_detailed()) {
			vol_vbv_detailed();
			_FSM_state = States.s_vol_shape;
			res = true;
		} else if (isSchedulable_generic_done_with_bitread()) {
			generic_done_with_bitread();
			_FSM_state = States.s_vol_shape;
			res = true;
		}
		return res;
	}

	private boolean vol_width_state_scheduler() {
		boolean res = false;
		if (isSchedulable_set_vol_width()) {
			set_vol_width();
			_FSM_state = States.s_vol_height;
			res = true;
		}
		return res;
	}

	private boolean vop_coding_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vop_coding_uncoded()) {
			vop_coding_uncoded();
			_FSM_state = States.s_stuck;
			res = true;
		} else if (isSchedulable_vop_coding_coded()) {
			vop_coding_coded();
			_FSM_state = States.s_send_new_vop_info;
			res = true;
		}
		return res;
	}

	private boolean vop_predict_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vop_predict_supported()) {
			vop_predict_supported();
			_FSM_state = States.s_vop_timebase;
			res = true;
		} else if (isSchedulable_generic_done()) {
			generic_done();
			_FSM_state = States.s_stuck;
			res = true;
		}
		return res;
	}

	private boolean vop_timebase_state_scheduler() {
		boolean res = false;
		if (isSchedulable_vop_timebase_one()) {
			vop_timebase_one();
			_FSM_state = States.s_vop_timebase;
			res = true;
		} else if (isSchedulable_vop_timebase_zero()) {
			vop_timebase_zero();
			_FSM_state = States.s_vop_coding;
			res = true;
		}
		return res;
	}

	@Override
	public int schedule() {
		boolean res = !suspended;
		int i = 0;

		while (res) {
			res = false;
			if (outside_FSM_scheduler()) {
				res = true;
				i++;
			} else {
				switch (_FSM_state) {
				case s_block:
					res = block_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_final_cbpy:
					res = final_cbpy_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_get_dc:
					res = get_dc_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_get_dc_a:
					res = get_dc_a_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_get_dc_bits:
					res = get_dc_bits_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_get_mbtype:
					res = get_mbtype_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_get_residual_x:
					res = get_residual_x_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_get_residual_y:
					res = get_residual_y_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_mag_x:
					res = mag_x_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_mag_y:
					res = mag_y_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_mb:
					res = mb_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_mv:
					res = mv_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_mv_y:
					res = mv_y_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_pvop_uncoded1:
					res = pvop_uncoded1_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_pvop_uncoded2:
					res = pvop_uncoded2_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_pvop_uncoded3:
					res = pvop_uncoded3_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_pvop_uncoded4:
					res = pvop_uncoded4_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_pvop_uncoded5:
					res = pvop_uncoded5_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_send_new_vop_height:
					res = send_new_vop_height_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_send_new_vop_info:
					res = send_new_vop_info_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_send_new_vop_width:
					res = send_new_vop_width_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck:
					res = stuck_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_1a:
					res = stuck_1a_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_1b:
					res = stuck_1b_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_2a:
					res = stuck_2a_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_2b:
					res = stuck_2b_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_3a:
					res = stuck_3a_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_3b:
					res = stuck_3b_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_4a:
					res = stuck_4a_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_stuck_4b:
					res = stuck_4b_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_texac:
					res = texac_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_texture:
					res = texture_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vld1:
					res = vld1_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vld4:
					res = vld4_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vld4a:
					res = vld4a_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vld6:
					res = vld6_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vld6a:
					res = vld6a_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vld7:
					res = vld7_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vld_direct:
					res = vld_direct_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_aspect:
					res = vol_aspect_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_control:
					res = vol_control_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_height:
					res = vol_height_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_misc:
					res = vol_misc_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_object:
					res = vol_object_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_shape:
					res = vol_shape_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_time_inc_res:
					res = vol_time_inc_res_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_vbv:
					res = vol_vbv_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vol_width:
					res = vol_width_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vop_coding:
					res = vop_coding_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vop_predict:
					res = vop_predict_state_scheduler();
					if (res) {
						i++;
					}
					break;
				case s_vop_timebase:
					res = vop_timebase_state_scheduler();
					if (res) {
						i++;
					}
					break;

				default:
					System.out.println("unknown state: %s\n" + _FSM_state);
					break;
				}
			}
		}

		return i;
	}

}
