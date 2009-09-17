/**
 * Generated from "mbpack"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_mbpack implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_DI;
	private IntFifo fifo_AI;

	// Output FIFOs
	private IntFifo fifo_DO;
	private IntFifo fifo_AO;

	// State variables of the actor
	private int PIX_SZ = 9;

	private int ADDR_SZ = 24;

	private int TC = 384;

	private int pix_count = 0;

	private int buf = 0;


	
	public Actor_mbpack() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures
	// Actions

	private void tc() {
	}

	private boolean isSchedulable_tc() {
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = pix_count;
			_tmp2_1 = TC;
			_tmp0_1 = _tmp1_1 == _tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void data_out() {
		int[] DO = new int[1];
		int[] DI = new int[1];
		int d_1;
		int _tmp1_1;

		fifo_DI.get(DI);
		d_1 = DI[0];
		pix_count++;
		_tmp1_1 = buf;
		DO[0] = _tmp1_1 << 8 | d_1;
		fifo_DO.put(DO);
	}

	private boolean isSchedulable_data_out() {
		int[] DI = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_DI.hasTokens(1);
		if (_tmp1_1) {
			fifo_DI.peek(DI);
			_tmp2_1 = pix_count;
			_tmp0_1 = (_tmp2_1 & 3) == 3;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void data_inp() {
		int[] DI = new int[1];
		int d_1;
		int _tmp0_1;

		fifo_DI.get(DI);
		d_1 = DI[0];
		_tmp0_1 = buf;
		buf = _tmp0_1 << 8 | d_1;
		pix_count++;
	}

	private boolean isSchedulable_data_inp() {
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

	private void addr() {
		int[] AO = new int[1];
		int[] AI = new int[1];
		int a_1;

		fifo_AI.get(AI);
		a_1 = AI[0];
		pix_count = 0;
		AO[0] = a_1;
		fifo_AO.put(AO);
	}

	private boolean isSchedulable_addr() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_AI.hasTokens(1);
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
		if ("DI".equals(portName)) {
			fifo_DI = fifo;
		} else if ("AI".equals(portName)) {
			fifo_AI = fifo;
		} else if ("DO".equals(portName)) {
			fifo_DO = fifo;
		} else if ("AO".equals(portName)) {
			fifo_AO = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_addr,
		s_rw
	};

	private States _FSM_state = States.s_rw;

	private boolean addr_state_scheduler() {
		boolean res = false;
		if (isSchedulable_addr()) {
			if (fifo_AO.hasRoom(1)) {
				addr();
				_FSM_state = States.s_rw;
				res = true;
			}
		}
		return res;
	}

	private boolean rw_state_scheduler() {
		boolean res = false;
		if (isSchedulable_tc()) {
			tc();
			_FSM_state = States.s_addr;
			res = true;
		} else if (isSchedulable_data_out()) {
			if (fifo_DO.hasRoom(1)) {
				data_out();
				_FSM_state = States.s_rw;
				res = true;
			}
		} else if (isSchedulable_data_inp()) {
			data_inp();
			_FSM_state = States.s_rw;
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
			case s_addr:
				res = addr_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_rw:
				res = rw_state_scheduler();
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
