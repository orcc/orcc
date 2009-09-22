/**
 * Generated from "searchwin"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_searchwin implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_MV;
	private IntFifo fifo_BTYPE;
	private IntFifo fifo_DI;

	// Output FIFOs
	private IntFifo fifo_DO;
	private IntFifo fifo_FLAGS;

	// State variables of the actor
	private int SEARCHWIN_IN_MB = 3;

	private int FLAG_SZ = 4;

	private int MV_SZ = 9;

	private int MB_COORD_SZ = 8;

	private int BTYPE_SZ = 12;

	private int PIX_SZ = 9;

	private int INTRA = 1024;

	private int INTER = 512;

	private int NEWVOP = 2048;

	private int MOTION = 8;

	private int ROUND_TYPE = 32;

	private int LUMA_X_BITS = 4;

	private int LUMA_Y_BITS = 6;

	private int LUMA_X_SIZE = 16;

	private int LUMA_Y_SIZE = 64;

	private int CHROMA_X_BITS = 3;

	private int CHROMA_Y_BITS = 5;

	private int CHROMA_X_SIZE = 8;

	private int CHROMA_Y_SIZE = 32;

	private int[] luma = new int[1024];

	private int[] chroma = new int[512];

	private boolean prediction_is_INTRA;

	private boolean is_motion;

	private boolean round;

	private int count;

	private int comp;

	private int mbw_max;

	private int mbh_max;

	private int mbx;

	private int mby;

	private int mbx_ptr = 0;

	private int y_offset;

	private int mvx;

	private int mvy;

	private boolean interp_x;

	private int mvxcount;

	private int mvycount;



	public Actor_searchwin() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\SearchWindow.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("bypass", new Location(156, 2, 265)); 
		actionLocation.put("cmd_motion", new Location(136, 2, 183)); 
		actionLocation.put("cmd_newVop", new Location(104, 2, 399)); 
		actionLocation.put("cmd_noMotion", new Location(143, 2, 150)); 
		actionLocation.put("cmd_other", new Location(151, 2, 70)); 
		actionLocation.put("done", new Location(184, 2, 114)); 
		actionLocation.put("geth", new Location(131, 2, 61)); 
		actionLocation.put("getmvx", new Location(229, 2, 104)); 
		actionLocation.put("getmvy", new Location(235, 2, 214)); 
		actionLocation.put("getw", new Location(126, 2, 61)); 
		actionLocation.put("motion", new Location(245, 2, 88)); 
		actionLocation.put("next", new Location(165, 2, 298)); 
		actionLocation.put("noMotion", new Location(252, 2, 26)); 
		actionLocation.put("read_chroma", new Location(206, 2, 528)); 
		actionLocation.put("read_luma", new Location(195, 2, 505)); 
		actionLocation.put("search_chroma", new Location(287, 2, 1043)); 
		actionLocation.put("search_done", new Location(254, 2, 53)); 
		actionLocation.put("search_luma", new Location(258, 2, 813)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	private String getNextSchedulableAction_cmd() {
		if (isSchedulable_cmd_newVop()) {
			return "cmd_newVop";
		} else if (isSchedulable_cmd_motion()) {
			return "cmd_motion";
		} else if (isSchedulable_cmd_noMotion()) {
			if (fifo_FLAGS.hasRoom(1)) {
				return "cmd_noMotion";
			}
		} else if (isSchedulable_cmd_other()) {
			return "cmd_other";
		}

		return null;
	}

	private String getNextSchedulableAction_fill() {
		if (isSchedulable_bypass()) {
			return "bypass";
		} else if (isSchedulable_done()) {
			return "done";
		} else if (isSchedulable_read_luma()) {
			return "read_luma";
		} else if (isSchedulable_read_chroma()) {
			return "read_chroma";
		}

		return null;
	}

	private String getNextSchedulableAction_geth() {
		if (isSchedulable_geth()) {
			return "geth";
		}

		return null;
	}

	private String getNextSchedulableAction_getmvx() {
		if (isSchedulable_getmvx()) {
			return "getmvx";
		}

		return null;
	}

	private String getNextSchedulableAction_getmvy() {
		if (isSchedulable_getmvy()) {
			if (fifo_FLAGS.hasRoom(1)) {
				return "getmvy";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_getw() {
		if (isSchedulable_getw()) {
			return "getw";
		}

		return null;
	}

	private String getNextSchedulableAction_motion() {
		if (isSchedulable_motion()) {
			return "motion";
		} else if (isSchedulable_noMotion()) {
			return "noMotion";
		}

		return null;
	}

	private String getNextSchedulableAction_next() {
		if (isSchedulable_next()) {
			return "next";
		}

		return null;
	}

	private String getNextSchedulableAction_search() {
		if (isSchedulable_search_done()) {
			return "search_done";
		} else if (isSchedulable_search_luma()) {
			if (fifo_DO.hasRoom(1)) {
				return "search_luma";
			}
		} else if (isSchedulable_search_chroma()) {
			if (fifo_DO.hasRoom(1)) {
				return "search_chroma";
			}
		}

		return null;
	}

	@Override
	public String getNextSchedulableAction() {
		switch (_FSM_state) {
		case s_cmd:
			return getNextSchedulableAction_cmd();
		case s_fill:
			return getNextSchedulableAction_fill();
		case s_geth:
			return getNextSchedulableAction_geth();
		case s_getmvx:
			return getNextSchedulableAction_getmvx();
		case s_getmvy:
			return getNextSchedulableAction_getmvy();
		case s_getw:
			return getNextSchedulableAction_getw();
		case s_motion:
			return getNextSchedulableAction_motion();
		case s_next:
			return getNextSchedulableAction_next();
		case s_search:
			return getNextSchedulableAction_search();

		default:
			System.out.println("unknown state: %s\n" + _FSM_state);
			return null;
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

	private int maskBits(int v, int n) {
		return v & (1 << n) - 1;
	}

	// Actions

	private void cmd_newVop() {
		int[] BTYPE = new int[1];
		int cmd_1;

		fifo_BTYPE.get(BTYPE);
		cmd_1 = BTYPE[0];
		round = (cmd_1 & 32) != 0;
		prediction_is_INTRA = (cmd_1 & 1024) != 0;
		comp = 0;
		is_motion = false;
		mbx = -1;
		mby = 0;
		y_offset = -1;
		count = 0;
	}

	private boolean isSchedulable_cmd_newVop() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp0_1 = (cmd_1 & 2048) != 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void getw() {
		int[] BTYPE = new int[1];
		int w_1;

		fifo_BTYPE.get(BTYPE);
		w_1 = BTYPE[0];
		mbw_max = w_1 - 1;
	}

	private boolean isSchedulable_getw() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void geth() {
		int[] BTYPE = new int[1];
		int h_1;

		fifo_BTYPE.get(BTYPE);
		h_1 = BTYPE[0];
		mbh_max = h_1 - 1;
	}

	private boolean isSchedulable_geth() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void cmd_motion() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
		is_motion = true;
	}

	private boolean isSchedulable_cmd_motion() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp0_1 = (cmd_1 & 520) == 520;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void cmd_noMotion() {
		int[] FLAGS = new int[1];
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
		mvx = 0;
		mvy = 0;
		is_motion = true;
		FLAGS[0] = 0;
		fifo_FLAGS.put(FLAGS);
	}

	private boolean isSchedulable_cmd_noMotion() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp0_1 = (cmd_1 & 512) == 512;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void cmd_other() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
		is_motion = false;
	}

	private boolean isSchedulable_cmd_other() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void bypass() {
		int _tmp0_1;
		int _tmp1_1;

		_tmp0_1 = comp;
		if (_tmp0_1 == 0) {
			_tmp1_1 = mbx_ptr;
			mbx_ptr = _tmp1_1 + 1;
		}
	}

	private boolean isSchedulable_bypass() {
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = prediction_is_INTRA;
			_tmp2_1 = comp;
			_tmp3_1 = mbx;
			_tmp4_1 = mbw_max;
			_tmp5_1 = mby;
			_tmp6_1 = mbh_max;
			_tmp0_1 = _tmp1_1 || _tmp2_1 != 0 || _tmp3_1 == _tmp4_1 && _tmp5_1 == _tmp6_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void next() {
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;

		_tmp0_1 = mbx;
		if (_tmp0_1 < 0) {
			mbx = 0;
		} else {
			_tmp1_1 = comp;
			if (_tmp1_1 == 5) {
				comp = 0;
				_tmp2_1 = mbx;
				_tmp3_1 = mbw_max;
				if (_tmp2_1 == _tmp3_1) {
					mbx = 0;
					_tmp4_1 = mby;
					mby = _tmp4_1 + 1;
				} else {
					_tmp5_1 = mbx;
					mbx = _tmp5_1 + 1;
				}
			} else {
				_tmp6_1 = comp;
				comp = _tmp6_1 + 1;
			}
		}
	}

	private boolean isSchedulable_next() {
		if (true) {
		}
		return true;
	}

	private void done() {
		mbx_ptr++;
		y_offset = -1;
		count = 0;
	}

	private boolean isSchedulable_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = y_offset;
			_tmp0_1 = _tmp1_1 == 2;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_luma() {
		int[] DI = new int[1];
		int d_1;
		int _tmp0_1;
		int comp_local0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp3_2;
		int _tmp3_3;
		int _tmp4_1;
		int y0_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp8_2;
		int _tmp8_3;
		int _tmp9_1;
		int x0_1;

		fifo_DI.get(DI);
		d_1 = DI[0];
		_tmp0_1 = count;
		comp_local0_1 = maskBits(_tmp0_1 >> 4, 2);
		_tmp1_1 = count;
		_tmp2_1 = maskBits(_tmp1_1 >> 1, 3);
		if ((comp_local0_1 & 2) != 0) {
			_tmp3_1 = 8;
			_tmp3_2 = _tmp3_1;
		} else {
			_tmp3_3 = 0;
			_tmp3_2 = _tmp3_3;
		}
		_tmp4_1 = y_offset;
		y0_1 = maskBits(_tmp2_1 + _tmp3_2 + (_tmp4_1 << 4), 6);
		_tmp6_1 = count;
		_tmp7_1 = maskBits(_tmp6_1, 1);
		if ((comp_local0_1 & 1) != 0) {
			_tmp8_1 = 2;
			_tmp8_2 = _tmp8_1;
		} else {
			_tmp8_3 = 0;
			_tmp8_2 = _tmp8_3;
		}
		_tmp9_1 = mbx_ptr;
		x0_1 = maskBits(_tmp7_1 + _tmp8_2 + (_tmp9_1 << 2), 4);
		luma[(y0_1 << 4) + x0_1] = d_1;
		count++;
	}

	private boolean isSchedulable_read_luma() {
		int[] DI = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_DI.hasTokens(1);
		if (_tmp1_1) {
			fifo_DI.peek(DI);
			_tmp2_1 = count;
			_tmp0_1 = _tmp2_1 <= 63;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_chroma() {
		int[] DI = new int[1];
		int d_1;
		int _tmp0_1;
		int comp_local0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int y0_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int x0_1;
		int _tmp13_1;
		int _tmp14_1;

		fifo_DI.get(DI);
		d_1 = DI[0];
		_tmp0_1 = count;
		comp_local0_1 = maskBits(_tmp0_1 >> 4, 1);
		_tmp1_1 = count;
		_tmp2_1 = maskBits(_tmp1_1 >> 1, 3);
		_tmp3_1 = y_offset;
		y0_1 = maskBits(_tmp2_1 + (_tmp3_1 << 3), 5);
		_tmp5_1 = count;
		_tmp6_1 = maskBits(_tmp5_1, 1);
		_tmp7_1 = mbx_ptr;
		x0_1 = maskBits(_tmp6_1 + (_tmp7_1 << 1), 3);
		chroma[(comp_local0_1 << 8) + (y0_1 << 3) + x0_1] = d_1;
		count++;
		_tmp13_1 = count;
		if (_tmp13_1 == 96) {
			count = 0;
			_tmp14_1 = y_offset;
			y_offset = _tmp14_1 + 1;
		}
	}

	private boolean isSchedulable_read_chroma() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_DI.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void getmvx() {
		int[] MV = new int[1];
		int v_1;

		fifo_MV.get(MV);
		v_1 = MV[0];
		interp_x = (v_1 & 1) != 0;
		mvx = v_1 >> 1;
	}

	private boolean isSchedulable_getmvx() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_MV.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void getmvy() {
		int[] FLAGS = new int[1];
		int[] MV = new int[1];
		int v_1;
		boolean _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp1_3;
		int _tmp2_1;
		int _tmp2_2;
		int _tmp2_3;
		boolean _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;
		int f0_1;

		fifo_MV.get(MV);
		v_1 = MV[0];
		_tmp0_1 = interp_x;
		if (_tmp0_1) {
			_tmp1_1 = 4;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp1_3 = 0;
			_tmp1_2 = _tmp1_3;
		}
		if ((v_1 & 1) != 0) {
			_tmp2_1 = 2;
			_tmp2_2 = _tmp2_1;
		} else {
			_tmp2_3 = 0;
			_tmp2_2 = _tmp2_3;
		}
		_tmp3_1 = round;
		if (_tmp3_1) {
			_tmp4_1 = 1;
			_tmp4_2 = _tmp4_1;
		} else {
			_tmp4_3 = 0;
			_tmp4_2 = _tmp4_3;
		}
		f0_1 = _tmp1_2 + _tmp2_2 + _tmp4_2;
		mvy = v_1 >> 1;
		FLAGS[0] = f0_1;
		fifo_FLAGS.put(FLAGS);
	}

	private boolean isSchedulable_getmvy() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_MV.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void motion() {
		mvxcount = 0;
		mvycount = 0;
	}

	private boolean isSchedulable_motion() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = is_motion;
			_tmp0_1 = _tmp1_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void noMotion() {
	}

	private boolean isSchedulable_noMotion() {
		if (true) {
		}
		return true;
	}

	private void search_done() {
	}

	private boolean isSchedulable_search_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = mvycount;
			_tmp0_1 = _tmp1_1 == 9;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void search_luma() {
		int[] DO = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp3_2;
		int _tmp3_3;
		int y0_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp7_2;
		int _tmp7_3;
		int x0_1;
		int _tmp8_1;
		int y0_2;
		int y0_3;
		int y0_4;
		int _tmp9_1;
		int _tmp10_1;
		int y0_5;
		int y0_6;
		int y0_7;
		int _tmp11_1;
		int x0_2;
		int x0_3;
		int x0_4;
		int _tmp12_1;
		int _tmp13_1;
		int x0_5;
		int x0_6;
		int x0_7;
		int _tmp15_1;
		int _tmp16_1;
		int _tmp18_1;
		int _tmp20_1;
		int _tmp22_1;
		int _tmp23_1;

		_tmp0_1 = mvy;
		_tmp1_1 = mvycount;
		_tmp2_1 = comp;
		if ((_tmp2_1 & 2) != 0) {
			_tmp3_1 = 8;
			_tmp3_2 = _tmp3_1;
		} else {
			_tmp3_3 = 0;
			_tmp3_2 = _tmp3_3;
		}
		y0_1 = _tmp0_1 + _tmp1_1 + _tmp3_2;
		_tmp4_1 = mvx;
		_tmp5_1 = mvxcount;
		_tmp6_1 = comp;
		if ((_tmp6_1 & 1) != 0) {
			_tmp7_1 = 8;
			_tmp7_2 = _tmp7_1;
		} else {
			_tmp7_3 = 0;
			_tmp7_2 = _tmp7_3;
		}
		x0_1 = _tmp4_1 + (_tmp5_1 << 2) + _tmp7_2;
		_tmp8_1 = mby;
		if (_tmp8_1 == 0) {
			if (y0_1 < 0) {
				y0_2 = 0;
				y0_3 = y0_2;
			} else {
				y0_3 = y0_1;
			}
			y0_4 = y0_3;
		} else {
			_tmp9_1 = mby;
			_tmp10_1 = mbh_max;
			if (_tmp9_1 == _tmp10_1) {
				if (y0_1 > 15) {
					y0_5 = 15;
					y0_6 = y0_5;
				} else {
					y0_6 = y0_1;
				}
				y0_7 = y0_6;
			} else {
				y0_7 = y0_1;
			}
			y0_4 = y0_7;
		}
		_tmp11_1 = mbx;
		if (_tmp11_1 == 0) {
			if (x0_1 < 0) {
				x0_2 = 0;
				x0_3 = x0_2;
			} else {
				x0_3 = x0_1;
			}
			x0_4 = x0_3;
		} else {
			_tmp12_1 = mbx;
			_tmp13_1 = mbw_max;
			if (_tmp12_1 == _tmp13_1) {
				if (x0_1 > 12) {
					x0_5 = 12;
					x0_6 = x0_5;
				} else {
					x0_6 = x0_1;
				}
				x0_7 = x0_6;
			} else {
				x0_7 = x0_1;
			}
			x0_4 = x0_7;
		}
		mvxcount++;
		_tmp15_1 = mvxcount;
		if (_tmp15_1 == 3) {
			mvxcount = 0;
			_tmp16_1 = mvycount;
			mvycount = _tmp16_1 + 1;
		}
		_tmp18_1 = maskBits(y0_4, 6);
		_tmp20_1 = mbx_ptr;
		_tmp22_1 = maskBits((x0_4 + ((_tmp20_1 - 2) << 4)) >> 2, 4);
		_tmp23_1 = luma[(_tmp18_1 << 4) + _tmp22_1];
		DO[0] = _tmp23_1;
		fifo_DO.put(DO);
	}

	private boolean isSchedulable_search_luma() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = comp;
			_tmp0_1 = _tmp1_1 <= 3;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void search_chroma() {
		int[] DO = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int y0_1;
		int _tmp2_1;
		int _tmp3_1;
		int x0_1;
		int _tmp4_1;
		int y0_2;
		int y0_3;
		int y0_4;
		int _tmp5_1;
		int _tmp6_1;
		int y0_5;
		int y0_6;
		int y0_7;
		int _tmp7_1;
		int x0_2;
		int x0_3;
		int x0_4;
		int _tmp8_1;
		int _tmp9_1;
		int x0_5;
		int x0_6;
		int x0_7;
		int _tmp11_1;
		int _tmp12_1;
		int _tmp13_1;
		int _tmp14_1;
		int _tmp18_1;
		int _tmp20_1;
		int _tmp22_1;
		int _tmp23_1;

		_tmp0_1 = mvy;
		_tmp1_1 = mvycount;
		y0_1 = _tmp0_1 + _tmp1_1;
		_tmp2_1 = mvx;
		_tmp3_1 = mvxcount;
		x0_1 = _tmp2_1 + (_tmp3_1 << 2);
		_tmp4_1 = mby;
		if (_tmp4_1 == 0) {
			if (y0_1 < 0) {
				y0_2 = 0;
				y0_3 = y0_2;
			} else {
				y0_3 = y0_1;
			}
			y0_4 = y0_3;
		} else {
			_tmp5_1 = mby;
			_tmp6_1 = mbh_max;
			if (_tmp5_1 == _tmp6_1) {
				if (y0_1 > 7) {
					y0_5 = 7;
					y0_6 = y0_5;
				} else {
					y0_6 = y0_1;
				}
				y0_7 = y0_6;
			} else {
				y0_7 = y0_1;
			}
			y0_4 = y0_7;
		}
		_tmp7_1 = mbx;
		if (_tmp7_1 == 0) {
			if (x0_1 < 0) {
				x0_2 = 0;
				x0_3 = x0_2;
			} else {
				x0_3 = x0_1;
			}
			x0_4 = x0_3;
		} else {
			_tmp8_1 = mbx;
			_tmp9_1 = mbw_max;
			if (_tmp8_1 == _tmp9_1) {
				if (x0_1 > 4) {
					x0_5 = 4;
					x0_6 = x0_5;
				} else {
					x0_6 = x0_1;
				}
				x0_7 = x0_6;
			} else {
				x0_7 = x0_1;
			}
			x0_4 = x0_7;
		}
		mvxcount++;
		_tmp11_1 = mvxcount;
		if (_tmp11_1 == 3) {
			mvxcount = 0;
			_tmp12_1 = mvycount;
			mvycount = _tmp12_1 + 1;
		}
		_tmp13_1 = comp;
		_tmp14_1 = maskBits(_tmp13_1, 1);
		_tmp18_1 = maskBits(y0_4, 5);
		_tmp20_1 = mbx_ptr;
		_tmp22_1 = maskBits((x0_4 + ((_tmp20_1 - 2) << 3)) >> 2, 3);
		_tmp23_1 = chroma[(_tmp14_1 << 8) + (_tmp18_1 << 3) + _tmp22_1];
		DO[0] = _tmp23_1;
		fifo_DO.put(DO);
	}

	private boolean isSchedulable_search_chroma() {
		if (true) {
		}
		return true;
	}

	// Initializes

	private void untagged01() {
		int i0_1;
		int i0_2;
		int i0_3;
		int i1_1;
		int i1_2;
		int i1_3;

		i0_1 = 1;
		i0_3 = i0_1;
		while (i0_3 < 513) {
			chroma[i0_3 - 1] = 0;
			i0_2 = i0_3 + 1;
			i0_3 = i0_2;
		}
		i1_1 = 1;
		i1_3 = i1_1;
		while (i1_3 < 1025) {
			luma[i1_3 - 1] = 0;
			i1_2 = i1_3 + 1;
			i1_3 = i1_2;
		}
	}

	private boolean isSchedulable_untagged01() {
		if (true) {
		}
		return true;
	}
	@Override
	public void initialize() {
		boolean res = true;
		int i = 0;

		if (isSchedulable_untagged01()) {
			untagged01();
			res = true;
			i++;
		}	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("MV".equals(portName)) {
			fifo_MV = fifo;
		} else if ("BTYPE".equals(portName)) {
			fifo_BTYPE = fifo;
		} else if ("DI".equals(portName)) {
			fifo_DI = fifo;
		} else if ("DO".equals(portName)) {
			fifo_DO = fifo;
		} else if ("FLAGS".equals(portName)) {
			fifo_FLAGS = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_cmd,
		s_fill,
		s_geth,
		s_getmvx,
		s_getmvy,
		s_getw,
		s_motion,
		s_next,
		s_search
	};

	private States _FSM_state = States.s_cmd;

	private boolean cmd_state_scheduler() {
		boolean res = false;
		if (isSchedulable_cmd_newVop()) {
			cmd_newVop();
			_FSM_state = States.s_getw;
			res = true;
		} else if (isSchedulable_cmd_motion()) {
			cmd_motion();
			_FSM_state = States.s_getmvx;
			res = true;
		} else if (isSchedulable_cmd_noMotion()) {
			if (fifo_FLAGS.hasRoom(1)) {
				cmd_noMotion();
				_FSM_state = States.s_fill;
				res = true;
			}
		} else if (isSchedulable_cmd_other()) {
			cmd_other();
			_FSM_state = States.s_fill;
			res = true;
		}
		return res;
	}

	private boolean fill_state_scheduler() {
		boolean res = false;
		if (isSchedulable_bypass()) {
			bypass();
			_FSM_state = States.s_motion;
			res = true;
		} else if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_motion;
			res = true;
		} else if (isSchedulable_read_luma()) {
			read_luma();
			_FSM_state = States.s_fill;
			res = true;
		} else if (isSchedulable_read_chroma()) {
			read_chroma();
			_FSM_state = States.s_fill;
			res = true;
		}
		return res;
	}

	private boolean geth_state_scheduler() {
		boolean res = false;
		if (isSchedulable_geth()) {
			geth();
			_FSM_state = States.s_fill;
			res = true;
		}
		return res;
	}

	private boolean getmvx_state_scheduler() {
		boolean res = false;
		if (isSchedulable_getmvx()) {
			getmvx();
			_FSM_state = States.s_getmvy;
			res = true;
		}
		return res;
	}

	private boolean getmvy_state_scheduler() {
		boolean res = false;
		if (isSchedulable_getmvy()) {
			if (fifo_FLAGS.hasRoom(1)) {
				getmvy();
				_FSM_state = States.s_fill;
				res = true;
			}
		}
		return res;
	}

	private boolean getw_state_scheduler() {
		boolean res = false;
		if (isSchedulable_getw()) {
			getw();
			_FSM_state = States.s_geth;
			res = true;
		}
		return res;
	}

	private boolean motion_state_scheduler() {
		boolean res = false;
		if (isSchedulable_motion()) {
			motion();
			_FSM_state = States.s_search;
			res = true;
		} else if (isSchedulable_noMotion()) {
			noMotion();
			_FSM_state = States.s_next;
			res = true;
		}
		return res;
	}

	private boolean next_state_scheduler() {
		boolean res = false;
		if (isSchedulable_next()) {
			next();
			_FSM_state = States.s_cmd;
			res = true;
		}
		return res;
	}

	private boolean search_state_scheduler() {
		boolean res = false;
		if (isSchedulable_search_done()) {
			search_done();
			_FSM_state = States.s_next;
			res = true;
		} else if (isSchedulable_search_luma()) {
			if (fifo_DO.hasRoom(1)) {
				search_luma();
				_FSM_state = States.s_search;
				res = true;
			}
		} else if (isSchedulable_search_chroma()) {
			if (fifo_DO.hasRoom(1)) {
				search_chroma();
				_FSM_state = States.s_search;
				res = true;
			}
		}
		return res;
	}

	@Override
	public int schedule() {
		boolean res = !suspended;
		int i = 0;

		while (res) {
			res = false;
			switch (_FSM_state) {
			case s_cmd:
				res = cmd_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_fill:
				res = fill_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_geth:
				res = geth_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_getmvx:
				res = getmvx_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_getmvy:
				res = getmvy_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_getw:
				res = getw_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_motion:
				res = motion_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_next:
				res = next_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_search:
				res = search_state_scheduler();
				if (res) {
					i++;
				}
				break;

			default:
				System.out.println("unknown state: %s\n" + _FSM_state);
				break;
			}
		}

		return i;
	}

}
