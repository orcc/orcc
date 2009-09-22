/**
 * Generated from "zigzag"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_zigzag implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_AC;
	private IntFifo fifo_START;
	private IntFifo fifo_ADDR;

	// Output FIFOs
	private IntFifo fifo_OUT;

	// State variables of the actor
	private int SAMPLE_SZ = 13;

	private int BUF_SIZE = 128;

	private int count = 1;

	private boolean half = false;

	private int[] buf = new int[128];



	public Actor_zigzag() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Zigzag.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("done", new Location(60, 2, 86)); 
		actionLocation.put("read_only", new Location(76, 2, 91)); 
		actionLocation.put("read_write", new Location(88, 2, 132)); 
		actionLocation.put("skip", new Location(53, 2, 50)); 
		actionLocation.put("start", new Location(57, 2, 37)); 
		actionLocation.put("write_only", new Location(83, 2, 99)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	private String getNextSchedulableAction_both() {
		if (isSchedulable_done()) {
			return "done";
		} else if (isSchedulable_read_write()) {
			if (fifo_OUT.hasRoom(1)) {
				return "read_write";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_drain() {
		if (isSchedulable_done()) {
			return "done";
		} else if (isSchedulable_write_only()) {
			if (fifo_OUT.hasRoom(1)) {
				return "write_only";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_empty() {
		if (isSchedulable_skip()) {
			return "skip";
		} else if (isSchedulable_start()) {
			return "start";
		}

		return null;
	}

	private String getNextSchedulableAction_full() {
		if (isSchedulable_skip()) {
			return "skip";
		} else if (isSchedulable_start()) {
			return "start";
		}

		return null;
	}

	private String getNextSchedulableAction_read() {
		if (isSchedulable_done()) {
			return "done";
		} else if (isSchedulable_read_only()) {
			return "read_only";
		}

		return null;
	}

	@Override
	public String getNextSchedulableAction() {
		switch (_FSM_state) {
		case s_both:
			return getNextSchedulableAction_both();
		case s_drain:
			return getNextSchedulableAction_drain();
		case s_empty:
			return getNextSchedulableAction_empty();
		case s_full:
			return getNextSchedulableAction_full();
		case s_read:
			return getNextSchedulableAction_read();

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

	private int wa() {
		int _tmp0_1;
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp2_2;
		int _tmp2_3;

		_tmp0_1 = count;
		_tmp1_1 = half;
		if (_tmp1_1) {
			_tmp2_1 = 64;
			_tmp2_2 = _tmp2_1;
		} else {
			_tmp2_3 = 0;
			_tmp2_2 = _tmp2_3;
		}
		return _tmp0_1 & 63 | _tmp2_2;
	}

	private int ra(int addr) {
		boolean _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp1_3;

		_tmp0_1 = half;
		if (_tmp0_1) {
			_tmp1_1 = 0;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp1_3 = 64;
			_tmp1_2 = _tmp1_3;
		}
		return addr & 63 | _tmp1_2;
	}

	// Actions

	private void skip() {
		int[] START = new int[1];

		fifo_START.get(START);
	}

	private boolean isSchedulable_skip() {
		int[] START = new int[1];
		boolean _tmp1_1;
		int s_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_START.hasTokens(1);
		if (_tmp1_1) {
			fifo_START.peek(START);
			s_1 = START[0];
			_tmp0_1 = s_1 < 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void start() {
		int[] START = new int[1];

		fifo_START.get(START);
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
		boolean _tmp0_1;

		count = 1;
		_tmp0_1 = half;
		half = !_tmp0_1;
	}

	private boolean isSchedulable_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = count;
			_tmp0_1 = _tmp1_1 == 64;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_only() {
		int[] AC = new int[1];
		int ac_1;
		int _tmp0_1;

		fifo_AC.get(AC);
		ac_1 = AC[0];
		_tmp0_1 = wa();
		buf[_tmp0_1] = ac_1;
		count++;
	}

	private boolean isSchedulable_read_only() {
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

	private void write_only() {
		int[] OUT = new int[1];
		int[] ADDR = new int[1];
		int addr_1;
		int _tmp1_1;
		int _tmp2_1;

		fifo_ADDR.get(ADDR);
		addr_1 = ADDR[0];
		count++;
		_tmp1_1 = ra(addr_1);
		_tmp2_1 = buf[_tmp1_1];
		OUT[0] = _tmp2_1;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_write_only() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ADDR.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_write() {
		int[] OUT = new int[1];
		int[] ADDR = new int[1];
		int[] AC = new int[1];
		int addr_1;
		int ac_1;
		int _tmp0_1;
		int _tmp2_1;
		int _tmp3_1;

		fifo_ADDR.get(ADDR);
		addr_1 = ADDR[0];
		fifo_AC.get(AC);
		ac_1 = AC[0];
		_tmp0_1 = wa();
		buf[_tmp0_1] = ac_1;
		count++;
		_tmp2_1 = ra(addr_1);
		_tmp3_1 = buf[_tmp2_1];
		OUT[0] = _tmp3_1;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_read_write() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ADDR.hasTokens(1);
		_tmp2_1 = fifo_AC.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	// Initializes

	private void untagged01() {
		int k0_1;
		int k0_2;
		int k0_3;

		k0_1 = 1;
		k0_3 = k0_1;
		while (k0_3 < 129) {
			buf[k0_3 - 1] = 0;
			k0_2 = k0_3 + 1;
			k0_3 = k0_2;
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
		if ("AC".equals(portName)) {
			fifo_AC = fifo;
		} else if ("START".equals(portName)) {
			fifo_START = fifo;
		} else if ("ADDR".equals(portName)) {
			fifo_ADDR = fifo;
		} else if ("OUT".equals(portName)) {
			fifo_OUT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_both,
		s_drain,
		s_empty,
		s_full,
		s_read
	};

	private States _FSM_state = States.s_empty;

	private boolean both_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_full;
			res = true;
		} else if (isSchedulable_read_write()) {
			if (fifo_OUT.hasRoom(1)) {
				read_write();
				_FSM_state = States.s_both;
				res = true;
			}
		}
		return res;
	}

	private boolean drain_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_empty;
			res = true;
		} else if (isSchedulable_write_only()) {
			if (fifo_OUT.hasRoom(1)) {
				write_only();
				_FSM_state = States.s_drain;
				res = true;
			}
		}
		return res;
	}

	private boolean empty_state_scheduler() {
		boolean res = false;
		if (isSchedulable_skip()) {
			skip();
			_FSM_state = States.s_empty;
			res = true;
		} else if (isSchedulable_start()) {
			start();
			_FSM_state = States.s_read;
			res = true;
		}
		return res;
	}

	private boolean full_state_scheduler() {
		boolean res = false;
		if (isSchedulable_skip()) {
			skip();
			_FSM_state = States.s_drain;
			res = true;
		} else if (isSchedulable_start()) {
			start();
			_FSM_state = States.s_both;
			res = true;
		}
		return res;
	}

	private boolean read_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_full;
			res = true;
		} else if (isSchedulable_read_only()) {
			read_only();
			_FSM_state = States.s_read;
			res = true;
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
			case s_both:
				res = both_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_drain:
				res = drain_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_empty:
				res = empty_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_full:
				res = full_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_read:
				res = read_state_scheduler();
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
