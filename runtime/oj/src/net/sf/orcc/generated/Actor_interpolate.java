/**
 * Generated from "interpolate"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_interpolate implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_RD;
	private IntFifo fifo_halfpel;

	// Output FIFOs
	private IntFifo fifo_MOT;

	// State variables of the actor
	private int PIX_SZ = 9;

	private int FLAG_SZ = 4;

	private boolean _CAL_tokenMonitor = true;

	private int x;

	private int y;

	private int flags;

	private int round;

	private int d0;

	private int d1;

	private int d2;

	private int d3;

	private int d4;

	private int d5;

	private int d6;

	private int d7;

	private int d8;

	private int d9;


	
	public Actor_interpolate() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Interpolate.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("done", new Location(83, 2, 42)); 
		actionLocation.put("other", new Location(120, 2, 307)); 
		actionLocation.put("row_col_0", new Location(99, 2, 286)); 
		actionLocation.put("start", new Location(75, 2, 120)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	// Functions/procedures

	private int compensate(int p00, int p10, int p01, int p11) {
		int _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp1_3;
		int _tmp1_4;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp1_5;
		int _tmp1_6;
		int _tmp6_1;
		int _tmp1_7;

		_tmp0_1 = flags;
		if (_tmp0_1 == 0) {
			_tmp1_1 = p00;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp2_1 = flags;
			if (_tmp2_1 == 1) {
				_tmp3_1 = round;
				_tmp1_3 = (p00 + p01 + 1 - _tmp3_1) >> 1;
				_tmp1_4 = _tmp1_3;
			} else {
				_tmp4_1 = flags;
				if (_tmp4_1 == 2) {
					_tmp5_1 = round;
					_tmp1_5 = (p00 + p10 + 1 - _tmp5_1) >> 1;
					_tmp1_6 = _tmp1_5;
				} else {
					_tmp6_1 = round;
					_tmp1_7 = (p00 + p10 + p01 + p11 + 2 - _tmp6_1) >> 2;
					_tmp1_6 = _tmp1_7;
				}
				_tmp1_4 = _tmp1_6;
			}
			_tmp1_2 = _tmp1_4;
		}
		return _tmp1_2;
	}

	// Actions

	private void start() {
		int[] halfpel = new int[1];
		int f_1;

		fifo_halfpel.get(halfpel);
		f_1 = halfpel[0];
		x = 0;
		y = 0;
		flags = f_1 >> 1;
		round = f_1 & 1;
	}

	private boolean isSchedulable_start() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_halfpel.hasTokens(1);
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
	}

	private boolean isSchedulable_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = y;
			_tmp0_1 = _tmp1_1 == 9;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void row_col_0() {
		int[] RD = new int[1];
		int d_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp10_1;
		int _tmp11_1;

		fifo_RD.get(RD);
		d_1 = RD[0];
		_tmp0_1 = d8;
		d9 = _tmp0_1;
		_tmp1_1 = d7;
		d8 = _tmp1_1;
		_tmp2_1 = d6;
		d7 = _tmp2_1;
		_tmp3_1 = d5;
		d6 = _tmp3_1;
		_tmp4_1 = d4;
		d5 = _tmp4_1;
		_tmp5_1 = d3;
		d4 = _tmp5_1;
		_tmp6_1 = d2;
		d3 = _tmp6_1;
		_tmp7_1 = d1;
		d2 = _tmp7_1;
		_tmp8_1 = d0;
		d1 = _tmp8_1;
		d0 = d_1;
		x++;
		_tmp10_1 = x;
		if (_tmp10_1 >= 9) {
			x = 0;
			_tmp11_1 = y;
			y = _tmp11_1 + 1;
		}
	}

	private boolean isSchedulable_row_col_0() {
		int[] RD = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_RD.hasTokens(1);
		if (_tmp1_1) {
			fifo_RD.peek(RD);
			_tmp2_1 = x;
			_tmp3_1 = y;
			_tmp0_1 = _tmp2_1 == 0 || _tmp3_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void other() {
		int[] MOT = new int[1];
		int[] RD = new int[1];
		int d_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int p0_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;
		int _tmp10_1;
		int _tmp11_1;
		int _tmp13_1;
		int _tmp14_1;

		fifo_RD.get(RD);
		d_1 = RD[0];
		_tmp0_1 = d9;
		_tmp1_1 = d8;
		_tmp2_1 = d0;
		p0_1 = compensate(_tmp0_1, _tmp1_1, _tmp2_1, d_1);
		_tmp3_1 = d8;
		d9 = _tmp3_1;
		_tmp4_1 = d7;
		d8 = _tmp4_1;
		_tmp5_1 = d6;
		d7 = _tmp5_1;
		_tmp6_1 = d5;
		d6 = _tmp6_1;
		_tmp7_1 = d4;
		d5 = _tmp7_1;
		_tmp8_1 = d3;
		d4 = _tmp8_1;
		_tmp9_1 = d2;
		d3 = _tmp9_1;
		_tmp10_1 = d1;
		d2 = _tmp10_1;
		_tmp11_1 = d0;
		d1 = _tmp11_1;
		d0 = d_1;
		x++;
		_tmp13_1 = x;
		if (_tmp13_1 >= 9) {
			x = 0;
			_tmp14_1 = y;
			y = _tmp14_1 + 1;
		}
		MOT[0] = p0_1;
		fifo_MOT.put(MOT);
	}

	private boolean isSchedulable_other() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_RD.hasTokens(1);
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
		if ("RD".equals(portName)) {
			fifo_RD = fifo;
		} else if ("halfpel".equals(portName)) {
			fifo_halfpel = fifo;
		} else if ("MOT".equals(portName)) {
			fifo_MOT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_interpolate,
		s_start
	};

	private States _FSM_state = States.s_start;

	private boolean interpolate_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_start;
			res = true;
		} else if (isSchedulable_row_col_0()) {
			row_col_0();
			_FSM_state = States.s_interpolate;
			res = true;
		} else if (isSchedulable_other()) {
			if (fifo_MOT.hasRoom(1)) {
				other();
				_FSM_state = States.s_interpolate;
				res = true;
			}
		}
		return res;
	}

	private boolean start_state_scheduler() {
		boolean res = false;
		if (isSchedulable_start()) {
			start();
			_FSM_state = States.s_interpolate;
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
			case s_interpolate:
				res = interpolate_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_start:
				res = start_state_scheduler();
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
