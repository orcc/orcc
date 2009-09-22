/**
 * Generated from "shufflefly"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_shufflefly implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_X0;
	private IntFifo fifo_X1;

	// Output FIFOs
	private IntFifo fifo_Y0;
	private IntFifo fifo_Y1;
	private IntFifo fifo_Y2;
	private IntFifo fifo_Y3;

	// State variables of the actor
	private int sz = 24;

	private int D0;

	private int D1;



	public Actor_shufflefly() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\ShuffleFly.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("a0", new Location(54, 2, 68)); 
		actionLocation.put("a1", new Location(60, 2, 86)); 
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
			if (fifo_Y3.hasRoom(1) && fifo_Y2.hasRoom(1) && fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
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
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int a_1;
		int b_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		D0 = a_1;
		D1 = b_1;
	}

	private boolean isSchedulable_a0() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
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
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int d2_1;
		int d3_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;

		fifo_X0.get(X0);
		d2_1 = X0[0];
		fifo_X1.get(X1);
		d3_1 = X1[0];
		_tmp0_1 = D0;
		Y0[0] = _tmp0_1 + d2_1;
		fifo_Y0.put(Y0);
		_tmp1_1 = D0;
		Y1[0] = _tmp1_1 - d2_1;
		fifo_Y1.put(Y1);
		_tmp2_1 = D1;
		Y2[0] = _tmp2_1 + d3_1;
		fifo_Y2.put(Y2);
		_tmp3_1 = D1;
		Y3[0] = _tmp3_1 - d3_1;
		fifo_Y3.put(Y3);
	}

	private boolean isSchedulable_a1() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
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
		if ("X0".equals(portName)) {
			fifo_X0 = fifo;
		} else if ("X1".equals(portName)) {
			fifo_X1 = fifo;
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
			if (fifo_Y3.hasRoom(1) && fifo_Y2.hasRoom(1) && fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
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
