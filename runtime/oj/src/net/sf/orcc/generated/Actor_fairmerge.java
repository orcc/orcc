/**
 * Generated from "fairmerge"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_fairmerge implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_R0;
	private IntFifo fifo_R1;
	private IntFifo fifo_C0;
	private IntFifo fifo_C1;

	// Output FIFOs
	private IntFifo fifo_Y0;
	private IntFifo fifo_Y1;
	private IntFifo fifo_ROWOUT;

	// State variables of the actor
	private int rsz = 13;

	private int csz = 16;



	public Actor_fairmerge() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\FairMerge.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("col", new Location(53, 6, 71)); 
		actionLocation.put("col_low", new Location(55, 2, 71)); 
		actionLocation.put("row", new Location(52, 6, 70)); 
		actionLocation.put("row_low", new Location(54, 2, 70)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	private String getNextSchedulableAction_c0() {
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				return "col";
			}
		} else if (isSchedulable_row_low()) {
			if (fifo_Y0.hasRoom(1) && fifo_Y1.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				return "row_low";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_c1() {
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				return "col";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_c2() {
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				return "col";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_c3() {
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				return "col";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_r0() {
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				return "row";
			}
		} else if (isSchedulable_col_low()) {
			if (fifo_ROWOUT.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				return "col_low";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_r1() {
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				return "row";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_r2() {
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				return "row";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_r3() {
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				return "row";
			}
		}

		return null;
	}

	@Override
	public String getNextSchedulableAction() {
		switch (_FSM_state) {
		case s_c0:
			return getNextSchedulableAction_c0();
		case s_c1:
			return getNextSchedulableAction_c1();
		case s_c2:
			return getNextSchedulableAction_c2();
		case s_c3:
			return getNextSchedulableAction_c3();
		case s_r0:
			return getNextSchedulableAction_r0();
		case s_r1:
			return getNextSchedulableAction_r1();
		case s_r2:
			return getNextSchedulableAction_r2();
		case s_r3:
			return getNextSchedulableAction_r3();

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

	private void row() {
		boolean[] ROWOUT = new boolean[1];
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] R0 = new int[1];
		int[] R1 = new int[1];
		int a_1;
		int b_1;

		fifo_R0.get(R0);
		a_1 = R0[0];
		fifo_R1.get(R1);
		b_1 = R1[0];
		Y0[0] = a_1;
		fifo_Y0.put(Y0);
		Y1[0] = b_1;
		fifo_Y1.put(Y1);
		ROWOUT[0] = true;
		fifo_ROWOUT.put(ROWOUT);
	}

	private boolean isSchedulable_row() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_R0.hasTokens(1);
		_tmp2_1 = fifo_R1.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void col() {
		boolean[] ROWOUT = new boolean[1];
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] C0 = new int[1];
		int[] C1 = new int[1];
		int a_1;
		int b_1;

		fifo_C0.get(C0);
		a_1 = C0[0];
		fifo_C1.get(C1);
		b_1 = C1[0];
		Y0[0] = a_1;
		fifo_Y0.put(Y0);
		Y1[0] = b_1;
		fifo_Y1.put(Y1);
		ROWOUT[0] = false;
		fifo_ROWOUT.put(ROWOUT);
	}

	private boolean isSchedulable_col() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_C0.hasTokens(1);
		_tmp2_1 = fifo_C1.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void row_low() {
		boolean[] ROWOUT = new boolean[1];
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] R0 = new int[1];
		int[] R1 = new int[1];
		int a_1;
		int b_1;

		fifo_R0.get(R0);
		a_1 = R0[0];
		fifo_R1.get(R1);
		b_1 = R1[0];
		Y0[0] = a_1;
		fifo_Y0.put(Y0);
		Y1[0] = b_1;
		fifo_Y1.put(Y1);
		ROWOUT[0] = true;
		fifo_ROWOUT.put(ROWOUT);
	}

	private boolean isSchedulable_row_low() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_R0.hasTokens(1);
		_tmp2_1 = fifo_R1.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void col_low() {
		boolean[] ROWOUT = new boolean[1];
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] C0 = new int[1];
		int[] C1 = new int[1];
		int a_1;
		int b_1;

		fifo_C0.get(C0);
		a_1 = C0[0];
		fifo_C1.get(C1);
		b_1 = C1[0];
		Y0[0] = a_1;
		fifo_Y0.put(Y0);
		Y1[0] = b_1;
		fifo_Y1.put(Y1);
		ROWOUT[0] = false;
		fifo_ROWOUT.put(ROWOUT);
	}

	private boolean isSchedulable_col_low() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_C0.hasTokens(1);
		_tmp2_1 = fifo_C1.hasTokens(1);
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
		if ("R0".equals(portName)) {
			fifo_R0 = fifo;
		} else if ("R1".equals(portName)) {
			fifo_R1 = fifo;
		} else if ("C0".equals(portName)) {
			fifo_C0 = fifo;
		} else if ("C1".equals(portName)) {
			fifo_C1 = fifo;
		} else if ("Y0".equals(portName)) {
			fifo_Y0 = fifo;
		} else if ("Y1".equals(portName)) {
			fifo_Y1 = fifo;
		} else if ("ROWOUT".equals(portName)) {
			fifo_ROWOUT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_c0,
		s_c1,
		s_c2,
		s_c3,
		s_r0,
		s_r1,
		s_r2,
		s_r3
	};

	private States _FSM_state = States.s_r0;

	private boolean c0_state_scheduler() {
		boolean res = false;
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				col();
				_FSM_state = States.s_c1;
				res = true;
			}
		} else if (isSchedulable_row_low()) {
			if (fifo_Y0.hasRoom(1) && fifo_Y1.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				row_low();
				_FSM_state = States.s_r1;
				res = true;
			}
		}
		return res;
	}

	private boolean c1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				col();
				_FSM_state = States.s_c2;
				res = true;
			}
		}
		return res;
	}

	private boolean c2_state_scheduler() {
		boolean res = false;
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				col();
				_FSM_state = States.s_c3;
				res = true;
			}
		}
		return res;
	}

	private boolean c3_state_scheduler() {
		boolean res = false;
		if (isSchedulable_col()) {
			if (fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				col();
				_FSM_state = States.s_r0;
				res = true;
			}
		}
		return res;
	}

	private boolean r0_state_scheduler() {
		boolean res = false;
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				row();
				_FSM_state = States.s_r1;
				res = true;
			}
		} else if (isSchedulable_col_low()) {
			if (fifo_ROWOUT.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				col_low();
				_FSM_state = States.s_c1;
				res = true;
			}
		}
		return res;
	}

	private boolean r1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				row();
				_FSM_state = States.s_r2;
				res = true;
			}
		}
		return res;
	}

	private boolean r2_state_scheduler() {
		boolean res = false;
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				row();
				_FSM_state = States.s_r3;
				res = true;
			}
		}
		return res;
	}

	private boolean r3_state_scheduler() {
		boolean res = false;
		if (isSchedulable_row()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_ROWOUT.hasRoom(1)) {
				row();
				_FSM_state = States.s_c0;
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
			case s_c0:
				res = c0_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_c1:
				res = c1_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_c2:
				res = c2_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_c3:
				res = c3_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_r0:
				res = r0_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_r1:
				res = r1_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_r2:
				res = r2_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_r3:
				res = r3_state_scheduler();
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
