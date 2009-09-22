/**
 * Generated from "downsample"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_downsample implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_R;

	// Output FIFOs
	private IntFifo fifo_R2;

	// State variables of the actor

	public Actor_downsample() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Downsample.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("a0", new Location(48, 2, 27)); 
		actionLocation.put("a1", new Location(51, 2, 34)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	private String getNextSchedulableAction_s0() {
		if (isSchedulable_a0()) {
			return "a0";
		}

		return null;
	}

	private String getNextSchedulableAction_s1() {
		if (isSchedulable_a1()) {
			if (fifo_R2.hasRoom(1)) {
				return "a1";
			}
		}

		return null;
	}

	@Override
	public String getNextSchedulableAction() {
		switch (_FSM_state) {
		case s_s0:
			return getNextSchedulableAction_s0();
		case s_s1:
			return getNextSchedulableAction_s1();

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

	private void a0() {
		boolean[] R = new boolean[1];

		fifo_R.get(R);
	}

	private boolean isSchedulable_a0() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_R.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a1() {
		boolean[] R2 = new boolean[1];
		boolean[] R = new boolean[1];
		boolean r_1;

		fifo_R.get(R);
		r_1 = R[0];
		R2[0] = r_1;
		fifo_R2.put(R2);
	}

	private boolean isSchedulable_a1() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_R.hasTokens(1);
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
		if ("R".equals(portName)) {
			fifo_R = fifo;
		} else if ("R2".equals(portName)) {
			fifo_R2 = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_s0,
		s_s1
	};

	private States _FSM_state = States.s_s0;

	private boolean s0_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a0()) {
			a0();
			_FSM_state = States.s_s1;
			res = true;
		}
		return res;
	}

	private boolean s1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a1()) {
			if (fifo_R2.hasRoom(1)) {
				a1();
				_FSM_state = States.s_s0;
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

			default:
				System.out.println("unknown state: %s\n" + _FSM_state);
				break;
			}
		}

		return i;
	}

}
