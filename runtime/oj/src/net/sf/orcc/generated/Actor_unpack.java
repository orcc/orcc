/**
 * Generated from "unpack"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_unpack implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_MV;
	private IntFifo fifo_BTYPE;
	private IntFifo fifo_DI;

	// Output FIFOs
	private IntFifo fifo_DO;

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

	private int comp;

	private int mbw_max;

	private int mbx;

	private int count;

	private int row;

	private int xstart;

	private int mvx;

	private int data;


	
	public Actor_unpack() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures

	private int maskBits(int v, int n) {
		return v & (1 << n) - 1;
	}

	private int extractByte(int v, int n) {
		return v >> ((3 - n) * 8) & 255;
	}

	// Actions

	private void cmd_newVop() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
		comp = 0;
		mbx = 0;
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

		fifo_BTYPE.get(BTYPE);
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
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp5_1;
		int _tmp1_3;

		fifo_BTYPE.get(BTYPE);
		mvx = 0;
		count = 0;
		row = 0;
		_tmp0_1 = comp;
		if (_tmp0_1 < 4) {
			_tmp2_1 = mbx;
			_tmp3_1 = comp;
			if ((_tmp3_1 & 1) != 0) {
				_tmp4_1 = 8;
				_tmp4_2 = _tmp4_1;
			} else {
				_tmp4_3 = 0;
				_tmp4_2 = _tmp4_3;
			}
			_tmp1_1 = (_tmp2_1 << 4) + _tmp4_2;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp5_1 = mbx;
			_tmp1_3 = _tmp5_1 << 3;
			_tmp1_2 = _tmp1_3;
		}
		xstart = _tmp1_2;
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
		row = 9;
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

	private void getmvx() {
		int[] MV = new int[1];
		int v_1;
		int vv0_1;
		int _tmp0_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp5_1;
		int _tmp1_3;

		fifo_MV.get(MV);
		v_1 = MV[0];
		vv0_1 = v_1 >> 1;
		mvx = vv0_1;
		count = 0;
		row = 0;
		_tmp0_1 = comp;
		if (_tmp0_1 < 4) {
			_tmp2_1 = mbx;
			_tmp3_1 = comp;
			if ((_tmp3_1 & 1) != 0) {
				_tmp4_1 = 8;
				_tmp4_2 = _tmp4_1;
			} else {
				_tmp4_3 = 0;
				_tmp4_2 = _tmp4_3;
			}
			_tmp1_1 = vv0_1 + (_tmp2_1 << 4) + _tmp4_2;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp5_1 = mbx;
			_tmp1_3 = vv0_1 + (_tmp5_1 << 3);
			_tmp1_2 = _tmp1_3;
		}
		xstart = _tmp1_2;
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
		int[] MV = new int[1];

		fifo_MV.get(MV);
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

	private void done() {
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp3_2;
		int _tmp4_1;
		int _tmp3_3;
		int _tmp5_1;

		_tmp0_1 = comp;
		if (_tmp0_1 == 5) {
			comp = 0;
			_tmp1_1 = mbx;
			_tmp2_1 = mbw_max;
			if (_tmp1_1 == _tmp2_1) {
				_tmp3_1 = 0;
				_tmp3_2 = _tmp3_1;
			} else {
				_tmp4_1 = mbx;
				_tmp3_3 = _tmp4_1 + 1;
				_tmp3_2 = _tmp3_3;
			}
			mbx = _tmp3_2;
		} else {
			_tmp5_1 = comp;
			comp = _tmp5_1 + 1;
		}
	}

	private boolean isSchedulable_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = row;
			_tmp0_1 = _tmp1_1 == 9;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void extract_noRead() {
		int[] DO = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int x0_1;
		boolean left_clip0_1;
		int _tmp2_1;
		int _tmp3_1;
		boolean right_clip_luma0_1;
		int _tmp4_1;
		int _tmp5_1;
		boolean right_clip_chroma0_1;
		int shift0_1;
		int shift0_2;
		int shift0_3;
		int shift0_4;
		int shift0_5;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;
		int _tmp10_1;

		_tmp0_1 = xstart;
		_tmp1_1 = count;
		x0_1 = _tmp0_1 + _tmp1_1;
		left_clip0_1 = x0_1 < 0;
		_tmp2_1 = comp;
		_tmp3_1 = mbw_max;
		right_clip_luma0_1 = (_tmp2_1 & 4) == 0 && x0_1 >> 4 > _tmp3_1;
		_tmp4_1 = comp;
		_tmp5_1 = mbw_max;
		right_clip_chroma0_1 = (_tmp4_1 & 4) != 0 && x0_1 >> 3 > _tmp5_1;
		if (left_clip0_1) {
			shift0_1 = 0;
			shift0_2 = shift0_1;
		} else {
			if (right_clip_luma0_1 || right_clip_chroma0_1) {
				shift0_3 = 3;
				shift0_4 = shift0_3;
			} else {
				shift0_5 = x0_1 & 3;
				shift0_4 = shift0_5;
			}
			shift0_2 = shift0_4;
		}
		_tmp6_1 = count;
		if (_tmp6_1 == 8) {
			count = 0;
			_tmp7_1 = row;
			row = _tmp7_1 + 1;
		} else {
			_tmp8_1 = count;
			count = _tmp8_1 + 1;
		}
		_tmp9_1 = data;
		_tmp10_1 = extractByte(_tmp9_1, shift0_2);
		DO[0] = _tmp10_1;
		fifo_DO.put(DO);
	}

	private boolean isSchedulable_extract_noRead() {
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = xstart;
			_tmp2_1 = count;
			_tmp3_1 = count;
			_tmp0_1 = (_tmp1_1 + _tmp2_1 & 3) != 0 && _tmp3_1 != 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void extract_read() {
		int[] DO = new int[1];
		int[] DI = new int[1];
		int d_1;
		int _tmp0_1;
		int _tmp1_1;
		int x0_1;
		boolean left_clip0_1;
		int _tmp2_1;
		int _tmp3_1;
		boolean right_clip_luma0_1;
		int _tmp4_1;
		int _tmp5_1;
		boolean right_clip_chroma0_1;
		int shift0_1;
		int shift0_2;
		int shift0_3;
		int shift0_4;
		int shift0_5;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;
		int _tmp10_1;

		fifo_DI.get(DI);
		d_1 = DI[0];
		_tmp0_1 = xstart;
		_tmp1_1 = count;
		x0_1 = _tmp0_1 + _tmp1_1;
		left_clip0_1 = x0_1 < 0;
		_tmp2_1 = comp;
		_tmp3_1 = mbw_max;
		right_clip_luma0_1 = (_tmp2_1 & 4) == 0 && x0_1 >> 4 > _tmp3_1;
		_tmp4_1 = comp;
		_tmp5_1 = mbw_max;
		right_clip_chroma0_1 = (_tmp4_1 & 4) != 0 && x0_1 >> 3 > _tmp5_1;
		if (left_clip0_1) {
			shift0_1 = 0;
			shift0_2 = shift0_1;
		} else {
			if (right_clip_luma0_1 || right_clip_chroma0_1) {
				shift0_3 = 3;
				shift0_4 = shift0_3;
			} else {
				shift0_5 = x0_1 & 3;
				shift0_4 = shift0_5;
			}
			shift0_2 = shift0_4;
		}
		data = d_1;
		_tmp6_1 = count;
		if (_tmp6_1 == 8) {
			count = 0;
			_tmp7_1 = row;
			row = _tmp7_1 + 1;
		} else {
			_tmp8_1 = count;
			count = _tmp8_1 + 1;
		}
		_tmp9_1 = data;
		_tmp10_1 = extractByte(_tmp9_1, shift0_2);
		DO[0] = _tmp10_1;
		fifo_DO.put(DO);
	}

	private boolean isSchedulable_extract_read() {
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

	@Override
	public void initialize() {
	}

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
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_cmd,
		s_extract,
		s_geth,
		s_getmvx,
		s_getmvy,
		s_getw
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
			cmd_noMotion();
			_FSM_state = States.s_extract;
			res = true;
		} else if (isSchedulable_cmd_other()) {
			cmd_other();
			_FSM_state = States.s_extract;
			res = true;
		}
		return res;
	}

	private boolean extract_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_cmd;
			res = true;
		} else if (isSchedulable_extract_noRead()) {
			if (fifo_DO.hasRoom(1)) {
				extract_noRead();
				_FSM_state = States.s_extract;
				res = true;
			}
		} else if (isSchedulable_extract_read()) {
			if (fifo_DO.hasRoom(1)) {
				extract_read();
				_FSM_state = States.s_extract;
				res = true;
			}
		}
		return res;
	}

	private boolean geth_state_scheduler() {
		boolean res = false;
		if (isSchedulable_geth()) {
			geth();
			_FSM_state = States.s_cmd;
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
			getmvy();
			_FSM_state = States.s_extract;
			res = true;
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

	@Override
	public int schedule() {
		boolean res = true;
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
			case s_extract:
				res = extract_state_scheduler();
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

			default:
				System.out.println("unknown state: %s\n" + _FSM_state);
				break;
			}
		}

		return i;
	}

}
