/**
 * Generated from "serialize"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_serialize implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_in8;

	// Output FIFOs
	private IntFifo fifo_out;

	// State variables of the actor
	private boolean _CAL_tokenMonitor = true;

	private int count = 0;

	private int buf;


	
	public Actor_serialize() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures
	// Actions

	private void reload() {
		int[] in8 = new int[1];
		int i_1;

		fifo_in8.get(in8);
		i_1 = in8[0];
		buf = i_1;
		count = 8;
	}

	private boolean isSchedulable_reload() {
		int[] in8 = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_in8.hasTokens(1);
		if (_tmp1_1) {
			fifo_in8.peek(in8);
			_tmp2_1 = count;
			_tmp0_1 = _tmp2_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void shift() {
		boolean[] out = new boolean[1];
		int _tmp0_1;
		boolean bit0_1;

		_tmp0_1 = buf;
		bit0_1 = (_tmp0_1 & 128) != 0;
		count--;
		buf <<= 1;
		out[0] = bit0_1;
		fifo_out.put(out);
	}

	private boolean isSchedulable_shift() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = count;
			_tmp0_1 = _tmp1_1 != 0;
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
		if ("in8".equals(portName)) {
			fifo_in8 = fifo;
		} else if ("out".equals(portName)) {
			fifo_out = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	@Override
	public int schedule() {
		boolean res = true;
		int i = 0;

		while (res) {
			res = false;
			if (isSchedulable_reload()) {
				reload();
				res = true;
				i++;
			} else if (isSchedulable_shift()) {
				if (fifo_out.hasRoom(1)) {
					shift();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
