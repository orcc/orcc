/**
 * Generated from "memorymanager"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_memorymanager implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_BTYPE;

	// Output FIFOs
	private IntFifo fifo_RA;
	private IntFifo fifo_WA;

	// State variables of the actor
	private int SEARCHWIN_IN_MB = 3;

	private int MAXW_IN_MB = 121;

	private int MAXH_IN_MB = 69;

	private int ADDR_SZ = 24;

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

	private int COMPONENTS = 6;

	private int BLOCKSIZE = 64;

	private int PIXELS_PER_WORD = 4;

	private int FRAMEBITS = 1;

	private int COMPBITS = 3;

	private int YBITS = 5;

	private int XBITS = 6;

	private int BLOCKBITS = 4;

	private int COMPSHIFT = 4;

	private int XSHIFT = 7;

	private int YSHIFT = 13;

	private int FRAMESHIFT = 18;

	private int comp = 0;

	private int this_mby;

	private int this_mbx;

	private int next_mby;

	private int next_mbx;

	private int width;

	private int this_frame = 0;

	private int last_frame;

	private boolean prediction_is_IVOP;


	
	public Actor_memorymanager() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures

	private int mask_bits(int v, int n) {
		return v & (1 << n) - 1;
	}

	private int address(int f, int y, int x) {
		int _tmp1_1;
		int xm0_1;
		int _tmp4_1;
		int ym0_1;
		int _tmp7_1;
		int fm0_1;

		_tmp1_1 = mask_bits(x, 6);
		xm0_1 = _tmp1_1 << 7;
		_tmp4_1 = mask_bits(y, 5);
		ym0_1 = _tmp4_1 << 13;
		_tmp7_1 = mask_bits(f, 1);
		fm0_1 = _tmp7_1 << 18;
		return fm0_1 | ym0_1 | xm0_1;
	}

	// Actions

	private void cmd_newVop() {
		int[] BTYPE = new int[1];
		int cmd_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp4_1;

		fifo_BTYPE.get(BTYPE);
		cmd_1 = BTYPE[0];
		next_mbx = 0;
		next_mby = 0;
		comp = 0;
		prediction_is_IVOP = (cmd_1 & 1024) != 0;
		_tmp1_1 = this_frame;
		last_frame = _tmp1_1;
		_tmp2_1 = this_frame;
		_tmp4_1 = mask_bits(_tmp2_1 + 1, 1);
		this_frame = _tmp4_1;
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

	private void set_width() {
		int[] BTYPE = new int[1];
		int w_1;

		fifo_BTYPE.get(BTYPE);
		w_1 = BTYPE[0];
		width = w_1;
	}

	private boolean isSchedulable_set_width() {
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

	private void height() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
	}

	private boolean isSchedulable_height() {
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

	private void cmd_y0() {
		int[] WA = new int[1];
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;

		fifo_BTYPE.get(BTYPE);
		_tmp0_1 = next_mbx;
		this_mbx = _tmp0_1;
		_tmp1_1 = next_mby;
		this_mby = _tmp1_1;
		next_mbx++;
		_tmp3_1 = next_mbx;
		_tmp4_1 = width;
		if (_tmp3_1 == _tmp4_1) {
			next_mbx = 0;
			_tmp5_1 = next_mby;
			next_mby = _tmp5_1 + 1;
		}
		comp = 1;
		_tmp6_1 = this_frame;
		_tmp7_1 = this_mby;
		_tmp8_1 = this_mbx;
		_tmp9_1 = address(_tmp6_1, _tmp7_1, _tmp8_1);
		WA[0] = _tmp9_1;
		fifo_WA.put(WA);
	}

	private boolean isSchedulable_cmd_y0() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			_tmp2_1 = comp;
			_tmp0_1 = _tmp2_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void cmd_other() {
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp2_1;
		int _tmp1_3;

		fifo_BTYPE.get(BTYPE);
		_tmp0_1 = comp;
		if (_tmp0_1 == 5) {
			_tmp1_1 = 0;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp2_1 = comp;
			_tmp1_3 = _tmp2_1 + 1;
			_tmp1_2 = _tmp1_3;
		}
		comp = _tmp1_2;
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

	private void read_none() {
	}

	private boolean isSchedulable_read_none() {
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

	private void read_above() {
		int[] RA = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;

		_tmp0_1 = last_frame;
		_tmp1_1 = this_mby;
		_tmp2_1 = this_mbx;
		_tmp3_1 = address(_tmp0_1, _tmp1_1 - 1, _tmp2_1);
		RA[0] = _tmp3_1;
		fifo_RA.put(RA);
	}

	private boolean isSchedulable_read_above() {
		if (true) {
		}
		return true;
	}

	private void read_this() {
		int[] RA = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;

		_tmp0_1 = last_frame;
		_tmp1_1 = this_mby;
		_tmp2_1 = this_mbx;
		_tmp3_1 = address(_tmp0_1, _tmp1_1, _tmp2_1);
		RA[0] = _tmp3_1;
		fifo_RA.put(RA);
	}

	private boolean isSchedulable_read_this() {
		if (true) {
		}
		return true;
	}

	private void read_below() {
		int[] RA = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;

		_tmp0_1 = last_frame;
		_tmp1_1 = this_mby;
		_tmp2_1 = this_mbx;
		_tmp3_1 = address(_tmp0_1, _tmp1_1 + 1, _tmp2_1);
		RA[0] = _tmp3_1;
		fifo_RA.put(RA);
	}

	private boolean isSchedulable_read_below() {
		if (true) {
		}
		return true;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("BTYPE".equals(portName)) {
			fifo_BTYPE = fifo;
		} else if ("RA".equals(portName)) {
			fifo_RA = fifo;
		} else if ("WA".equals(portName)) {
			fifo_WA = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_cmd,
		s_geth,
		s_getw,
		s_readAbove,
		s_readBelow,
		s_readThis
	};

	private States _FSM_state = States.s_cmd;

	private boolean cmd_state_scheduler() {
		boolean res = false;
		if (isSchedulable_cmd_newVop()) {
			cmd_newVop();
			_FSM_state = States.s_getw;
			res = true;
		} else if (isSchedulable_cmd_y0()) {
			if (fifo_WA.hasRoom(1)) {
				cmd_y0();
				_FSM_state = States.s_readAbove;
				res = true;
			}
		} else if (isSchedulable_cmd_other()) {
			cmd_other();
			_FSM_state = States.s_cmd;
			res = true;
		}
		return res;
	}

	private boolean geth_state_scheduler() {
		boolean res = false;
		if (isSchedulable_height()) {
			height();
			_FSM_state = States.s_cmd;
			res = true;
		}
		return res;
	}

	private boolean getw_state_scheduler() {
		boolean res = false;
		if (isSchedulable_set_width()) {
			set_width();
			_FSM_state = States.s_geth;
			res = true;
		}
		return res;
	}

	private boolean readAbove_state_scheduler() {
		boolean res = false;
		if (isSchedulable_read_none()) {
			read_none();
			_FSM_state = States.s_cmd;
			res = true;
		} else if (isSchedulable_read_above()) {
			if (fifo_RA.hasRoom(1)) {
				read_above();
				_FSM_state = States.s_readThis;
				res = true;
			}
		}
		return res;
	}

	private boolean readBelow_state_scheduler() {
		boolean res = false;
		if (isSchedulable_read_below()) {
			if (fifo_RA.hasRoom(1)) {
				read_below();
				_FSM_state = States.s_cmd;
				res = true;
			}
		}
		return res;
	}

	private boolean readThis_state_scheduler() {
		boolean res = false;
		if (isSchedulable_read_this()) {
			if (fifo_RA.hasRoom(1)) {
				read_this();
				_FSM_state = States.s_readBelow;
				res = true;
			}
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
			case s_geth:
				res = geth_state_scheduler();
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
			case s_readAbove:
				res = readAbove_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_readBelow:
				res = readBelow_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_readThis:
				res = readThis_state_scheduler();
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
