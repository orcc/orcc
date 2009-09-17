/**
 * Generated from "dequant"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_dequant implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_DC;
	private IntFifo fifo_AC;
	private IntFifo fifo_QP;

	// Output FIFOs
	private IntFifo fifo_OUT;

	// State variables of the actor
	private int QUANT_SZ = 6;

	private int SAMPLE_SZ = 13;

	private int count;

	private int quant;

	private int round;


	
	public Actor_dequant() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures

	private int saturate(int x) {
		boolean minus0_1;
		boolean plus0_1;
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;

		minus0_1 = x < -2048;
		plus0_1 = x > 2047;
		if (minus0_1) {
			_tmp0_1 = -2048;
			_tmp0_2 = _tmp0_1;
		} else {
			if (plus0_1) {
				_tmp0_3 = 2047;
				_tmp0_4 = _tmp0_3;
			} else {
				_tmp0_5 = x;
				_tmp0_4 = _tmp0_5;
			}
			_tmp0_2 = _tmp0_4;
		}
		return _tmp0_2;
	}

	private int abs(int x) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;

		if (x < 0) {
			_tmp0_1 = -x;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = x;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	// Actions

	private void get_qp() {
		int[] OUT = new int[1];
		int[] QP = new int[1];
		int[] DC = new int[1];
		int q_1;
		int i_1;

		fifo_QP.get(QP);
		q_1 = QP[0];
		fifo_DC.get(DC);
		i_1 = DC[0];
		quant = q_1;
		round = q_1 & 1 ^ 1;
		count = 0;
		OUT[0] = i_1;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_get_qp() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_QP.hasTokens(1);
		_tmp2_1 = fifo_DC.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
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
			_tmp1_1 = count;
			_tmp0_1 = _tmp1_1 == 63;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void ac() {
		int[] OUT = new int[1];
		int[] AC = new int[1];
		int i_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int v0_1;
		int o0_1;
		int o0_2;
		int o0_3;
		int o0_4;
		int o0_5;
		int _tmp4_1;

		fifo_AC.get(AC);
		i_1 = AC[0];
		_tmp0_1 = quant;
		_tmp1_1 = abs(i_1);
		_tmp2_1 = round;
		v0_1 = _tmp0_1 * ((_tmp1_1 << 1) + 1) - _tmp2_1;
		if (i_1 == 0) {
			o0_1 = 0;
			o0_2 = o0_1;
		} else {
			if (i_1 < 0) {
				o0_3 = -v0_1;
				o0_4 = o0_3;
			} else {
				o0_5 = v0_1;
				o0_4 = o0_5;
			}
			o0_2 = o0_4;
		}
		count++;
		_tmp4_1 = saturate(o0_2);
		OUT[0] = _tmp4_1;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_ac() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_AC.hasTokens(1);
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
		if ("DC".equals(portName)) {
			fifo_DC = fifo;
		} else if ("AC".equals(portName)) {
			fifo_AC = fifo;
		} else if ("QP".equals(portName)) {
			fifo_QP = fifo;
		} else if ("OUT".equals(portName)) {
			fifo_OUT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_ac,
		s_start
	};

	private States _FSM_state = States.s_start;

	private boolean ac_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_start;
			res = true;
		} else if (isSchedulable_ac()) {
			if (fifo_OUT.hasRoom(1)) {
				ac();
				_FSM_state = States.s_ac;
				res = true;
			}
		}
		return res;
	}

	private boolean start_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_qp()) {
			if (fifo_OUT.hasRoom(1)) {
				get_qp();
				_FSM_state = States.s_ac;
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
			case s_ac:
				res = ac_state_scheduler();
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
