/**
 * Generated from "zzaddr"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_zzaddr implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_START;

	// Output FIFOs
	private IntFifo fifo_ADDR;

	// State variables of the actor
	private int[] zigzag = {0, 1, 5, 6, 14, 15, 27, 28, 2, 4, 7, 13, 16, 26, 29, 42, 
	3, 8, 12, 17, 25, 30, 41, 43, 9, 11, 18, 24, 31, 40, 44, 53, 10, 19, 23, 32, 39, 
	45, 52, 54, 20, 22, 33, 38, 46, 51, 55, 60, 21, 34, 37, 47, 50, 56, 59, 61, 35, 
	36, 48, 49, 57, 58, 62, 63, 0, 4, 6, 20, 22, 36, 38, 52, 1, 5, 7, 21, 23, 37, 39, 
	53, 2, 8, 19, 24, 34, 40, 50, 54, 3, 9, 18, 25, 35, 41, 51, 55, 10, 17, 26, 30, 
	42, 46, 56, 60, 11, 16, 27, 31, 43, 47, 57, 61, 12, 15, 28, 32, 44, 48, 58, 62, 
	13, 14, 29, 33, 45, 49, 59, 63, 0, 1, 2, 3, 10, 11, 12, 13, 4, 5, 8, 9, 17, 16, 
	15, 14, 6, 7, 19, 18, 26, 27, 28, 29, 20, 21, 24, 25, 30, 31, 32, 33, 22, 23, 34, 
	35, 42, 43, 44, 45, 36, 37, 40, 41, 46, 47, 48, 49, 38, 39, 50, 51, 56, 57, 58, 
	59, 52, 53, 54, 55, 60, 61, 62, 63};

	private int addr;

	private int count = 0;



	public Actor_zzaddr() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\ZigzagAddr.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("done", new Location(78, 2, 46)); 
		actionLocation.put("skip", new Location(68, 2, 50)); 
		actionLocation.put("start", new Location(72, 2, 128)); 
		actionLocation.put("zz", new Location(83, 2, 125)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	private String getNextSchedulableAction_start() {
		if (isSchedulable_skip()) {
			return "skip";
		} else if (isSchedulable_start()) {
			return "start";
		}

		return null;
	}

	private String getNextSchedulableAction_zz() {
		if (isSchedulable_done()) {
			return "done";
		} else if (isSchedulable_zz()) {
			if (fifo_ADDR.hasRoom(1)) {
				return "zz";
			}
		}

		return null;
	}

	@Override
	public String getNextSchedulableAction() {
		switch (_FSM_state) {
		case s_start:
			return getNextSchedulableAction_start();
		case s_zz:
			return getNextSchedulableAction_zz();

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
	// Actions

	private void skip() {
		int[] START = new int[1];

		fifo_START.get(START);
	}

	private boolean isSchedulable_skip() {
		int[] START = new int[1];
		boolean _tmp1_1;
		int i_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_START.hasTokens(1);
		if (_tmp1_1) {
			fifo_START.peek(START);
			i_1 = START[0];
			_tmp0_1 = i_1 < 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void start() {
		int[] START = new int[1];
		int i_1;
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;

		fifo_START.get(START);
		i_1 = START[0];
		if (i_1 == 0) {
			_tmp0_1 = 1;
			_tmp0_2 = _tmp0_1;
		} else {
			if (i_1 == 1) {
				_tmp0_3 = 65;
				_tmp0_4 = _tmp0_3;
			} else {
				_tmp0_5 = 129;
				_tmp0_4 = _tmp0_5;
			}
			_tmp0_2 = _tmp0_4;
		}
		addr = _tmp0_2;
		count = 63;
	}

	private boolean isSchedulable_start() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_START.hasTokens(1);
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
			_tmp1_1 = count;
			_tmp0_1 = _tmp1_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void zz() {
		int[] ADDR = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int i0_1;

		_tmp0_1 = addr;
		_tmp1_1 = zigzag[_tmp0_1];
		i0_1 = _tmp1_1;
		addr++;
		count--;
		ADDR[0] = i0_1;
		fifo_ADDR.put(ADDR);
	}

	private boolean isSchedulable_zz() {
		if (true) {
		}
		return true;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("START".equals(portName)) {
			fifo_START = fifo;
		} else if ("ADDR".equals(portName)) {
			fifo_ADDR = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_start,
		s_zz
	};

	private States _FSM_state = States.s_start;

	private boolean start_state_scheduler() {
		boolean res = false;
		if (isSchedulable_skip()) {
			skip();
			_FSM_state = States.s_start;
			res = true;
		} else if (isSchedulable_start()) {
			start();
			_FSM_state = States.s_zz;
			res = true;
		}
		return res;
	}

	private boolean zz_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_start;
			res = true;
		} else if (isSchedulable_zz()) {
			if (fifo_ADDR.hasRoom(1)) {
				zz();
				_FSM_state = States.s_zz;
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
			case s_start:
				res = start_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_zz:
				res = zz_state_scheduler();
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
