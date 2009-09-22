/**
 * Generated from "blkexp"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_blkexp implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_RUN;
	private IntFifo fifo_VALUE;
	private IntFifo fifo_LAST;

	// Output FIFOs
	private IntFifo fifo_OUT;

	// State variables of the actor
	private int SAMPLE_COUNT_SZ = 8;

	private int SAMPLE_SZ = 13;

	private int BLOCK_SIZE = 64;

	private int count = 0;

	private int run = -1;

	private int next_value;

	private boolean last = false;



	public Actor_blkexp() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\BlockExpand.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("done", new Location(60, 2, 110)); 
		actionLocation.put("read_immediate", new Location(90, 2, 141)); 
		actionLocation.put("read_save", new Location(99, 2, 157)); 
		actionLocation.put("write_value", new Location(70, 2, 114)); 
		actionLocation.put("write_zero", new Location(79, 2, 117)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	@Override
	public String getNextSchedulableAction() {
		if (isSchedulable_done()) {
			return "done";
		} else if (isSchedulable_write_value()) {
			if (fifo_OUT.hasRoom(1)) {
				return "write_value";
			}
		} else if (isSchedulable_write_zero()) {
			if (fifo_OUT.hasRoom(1)) {
				return "write_zero";
			}
		} else if (isSchedulable_read_immediate()) {
			if (fifo_OUT.hasRoom(1)) {
				return "read_immediate";
			}
		} else if (isSchedulable_read_save()) {
			if (fifo_OUT.hasRoom(1)) {
				return "read_save";
			}
		}

		return null;
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
	// Actions

	private void done() {
		count = 0;
		run = -1;
		last = false;
	}

	private boolean isSchedulable_done() {
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = count;
			_tmp2_1 = BLOCK_SIZE;
			_tmp0_1 = _tmp1_1 == _tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void write_value() {
		int[] OUT = new int[1];
		int _tmp1_1;

		run = -1;
		count++;
		_tmp1_1 = next_value;
		OUT[0] = _tmp1_1;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_write_value() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = run;
			_tmp0_1 = _tmp1_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void write_zero() {
		int[] OUT = new int[1];

		run--;
		count++;
		OUT[0] = 0;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_write_zero() {
		int _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = run;
			_tmp2_1 = last;
			_tmp0_1 = _tmp1_1 > 0 || _tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_immediate() {
		int[] OUT = new int[1];
		int[] RUN = new int[1];
		int[] VALUE = new int[1];
		boolean[] LAST = new boolean[1];
		int v_1;
		boolean l_1;

		fifo_RUN.get(RUN);
		fifo_VALUE.get(VALUE);
		v_1 = VALUE[0];
		fifo_LAST.get(LAST);
		l_1 = LAST[0];
		last = l_1;
		count++;
		OUT[0] = v_1;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_read_immediate() {
		boolean[] LAST = new boolean[1];
		int[] VALUE = new int[1];
		int[] RUN = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		int r_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_RUN.hasTokens(1);
		_tmp2_1 = fifo_VALUE.hasTokens(1);
		_tmp3_1 = fifo_LAST.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1) {
			fifo_RUN.peek(RUN);
			r_1 = RUN[0];
			fifo_VALUE.peek(VALUE);
			fifo_LAST.peek(LAST);
			_tmp0_1 = r_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_save() {
		int[] OUT = new int[1];
		int[] RUN = new int[1];
		int[] VALUE = new int[1];
		boolean[] LAST = new boolean[1];
		int r_1;
		int v_1;
		boolean l_1;

		fifo_RUN.get(RUN);
		r_1 = RUN[0];
		fifo_VALUE.get(VALUE);
		v_1 = VALUE[0];
		fifo_LAST.get(LAST);
		l_1 = LAST[0];
		run = r_1 - 1;
		next_value = v_1;
		last = l_1;
		count++;
		OUT[0] = 0;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_read_save() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_RUN.hasTokens(1);
		_tmp2_1 = fifo_VALUE.hasTokens(1);
		_tmp3_1 = fifo_LAST.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1) {
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
		if ("RUN".equals(portName)) {
			fifo_RUN = fifo;
		} else if ("VALUE".equals(portName)) {
			fifo_VALUE = fifo;
		} else if ("LAST".equals(portName)) {
			fifo_LAST = fifo;
		} else if ("OUT".equals(portName)) {
			fifo_OUT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	@Override
	public int schedule() {
		boolean res = !suspended;
		int i = 0;

		while (res) {
			res = false;
			if (isSchedulable_done()) {
				done();
				res = true;
				i++;
			} else if (isSchedulable_write_value()) {
				if (fifo_OUT.hasRoom(1)) {
					write_value();
					res = true;
					i++;
				}
			} else if (isSchedulable_write_zero()) {
				if (fifo_OUT.hasRoom(1)) {
					write_zero();
					res = true;
					i++;
				}
			} else if (isSchedulable_read_immediate()) {
				if (fifo_OUT.hasRoom(1)) {
					read_immediate();
					res = true;
					i++;
				}
			} else if (isSchedulable_read_save()) {
				if (fifo_OUT.hasRoom(1)) {
					read_save();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
