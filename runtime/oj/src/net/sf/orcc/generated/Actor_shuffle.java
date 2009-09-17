/**
 * Generated from "shuffle"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_shuffle implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_X0;
	private IntFifo fifo_X1;
	private IntFifo fifo_X2;
	private IntFifo fifo_X3;

	// Output FIFOs
	private IntFifo fifo_Y0;
	private IntFifo fifo_Y1;
	private IntFifo fifo_Y2;
	private IntFifo fifo_Y3;

	// State variables of the actor
	private int sz = 24;

	private int x4;

	private int x5;

	private int x6h;

	private int x7h;

	private int x6l;

	private int x7l;


	
	public Actor_shuffle() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures
	// Actions

	private void a0() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
		int a_1;
		int b_1;
		int c_1;
		int d_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		fifo_X2.get(X2);
		c_1 = X2[0];
		fifo_X3.get(X3);
		d_1 = X3[0];
		x4 = c_1;
		x5 = d_1;
		Y0[0] = a_1;
		fifo_Y0.put(Y0);
		Y1[0] = b_1;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_a0() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		_tmp3_1 = fifo_X2.hasTokens(1);
		_tmp4_1 = fifo_X3.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a1() {
		int[] Y3 = new int[1];
		int[] Y2 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
		int x2_1;
		int a_1;
		int x3_1;
		int b_1;
		int ah0_1;
		int bh0_1;
		int al0_1;
		int bl0_1;

		fifo_X0.get(X0);
		x2_1 = X0[0];
		fifo_X1.get(X1);
		a_1 = X1[0];
		fifo_X2.get(X2);
		x3_1 = X2[0];
		fifo_X3.get(X3);
		b_1 = X3[0];
		ah0_1 = a_1 >> 8;
		bh0_1 = b_1 >> 8;
		al0_1 = a_1 & 255;
		bl0_1 = b_1 & 255;
		x6h = 181 * (ah0_1 + bh0_1);
		x7h = 181 * (ah0_1 - bh0_1);
		x6l = 181 * (al0_1 + bl0_1) + 128;
		x7l = 181 * (al0_1 - bl0_1) + 128;
		Y2[0] = x2_1;
		fifo_Y2.put(Y2);
		Y3[0] = x3_1;
		fifo_Y3.put(Y3);
	}

	private boolean isSchedulable_a1() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		_tmp3_1 = fifo_X2.hasTokens(1);
		_tmp4_1 = fifo_X3.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a2() {
		int[] Y3 = new int[1];
		int[] Y2 = new int[1];
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;

		_tmp0_1 = x4;
		Y0[0] = _tmp0_1;
		fifo_Y0.put(Y0);
		_tmp1_1 = x5;
		Y1[0] = _tmp1_1;
		fifo_Y1.put(Y1);
		_tmp2_1 = x6h;
		_tmp3_1 = x6l;
		Y2[0] = _tmp2_1 + (_tmp3_1 >> 8);
		fifo_Y2.put(Y2);
		_tmp4_1 = x7h;
		_tmp5_1 = x7l;
		Y3[0] = _tmp4_1 + (_tmp5_1 >> 8);
		fifo_Y3.put(Y3);
	}

	private boolean isSchedulable_a2() {
		if (true) {
		}
		return true;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("X0".equals(portName)) {
			fifo_X0 = fifo;
		} else if ("X1".equals(portName)) {
			fifo_X1 = fifo;
		} else if ("X2".equals(portName)) {
			fifo_X2 = fifo;
		} else if ("X3".equals(portName)) {
			fifo_X3 = fifo;
		} else if ("Y0".equals(portName)) {
			fifo_Y0 = fifo;
		} else if ("Y1".equals(portName)) {
			fifo_Y1 = fifo;
		} else if ("Y2".equals(portName)) {
			fifo_Y2 = fifo;
		} else if ("Y3".equals(portName)) {
			fifo_Y3 = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_s0,
		s_s1,
		s_s2
	};

	private States _FSM_state = States.s_s0;

	private boolean s0_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a0()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
				a0();
				_FSM_state = States.s_s1;
				res = true;
			}
		}
		return res;
	}

	private boolean s1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a1()) {
			if (fifo_Y3.hasRoom(1) && fifo_Y2.hasRoom(1)) {
				a1();
				_FSM_state = States.s_s2;
				res = true;
			}
		}
		return res;
	}

	private boolean s2_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a2()) {
			if (fifo_Y3.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_Y1.hasRoom(1) && fifo_Y2.hasRoom(1)) {
				a2();
				_FSM_state = States.s_s0;
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
			case s_s0:
				res = s0_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_s1:
				res = s1_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_s2:
				res = s2_state_scheduler();
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
