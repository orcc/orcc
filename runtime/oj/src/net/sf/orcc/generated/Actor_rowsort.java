/**
 * Generated from "rowsort"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_rowsort implements IActor {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_ROW;

	// Output FIFOs
	private IntFifo fifo_Y0;
	private IntFifo fifo_Y1;

	// State variables of the actor
	private int sz = 13;

	private int x0;

	private int x1;

	private int x2;

	private int x3;

	private int x5;


	
	public Actor_rowsort() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\RowSort.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("a0", new Location(53, 2, 42)); 
		actionLocation.put("a1", new Location(56, 2, 42)); 
		actionLocation.put("a2", new Location(59, 2, 42)); 
		actionLocation.put("a3", new Location(62, 2, 42)); 
		actionLocation.put("a4", new Location(65, 2, 46)); 
		actionLocation.put("a5", new Location(68, 2, 42)); 
		actionLocation.put("a6", new Location(71, 2, 46)); 
		actionLocation.put("a7", new Location(74, 2, 46)); 
		actionLocation.put("a9", new Location(80, 2, 39)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	// Functions/procedures
	// Actions

	private void a0() {
		int[] ROW = new int[1];
		int a_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		x0 = a_1;
	}

	private boolean isSchedulable_a0() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
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
		int[] ROW = new int[1];
		int a_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		x1 = a_1;
	}

	private boolean isSchedulable_a1() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a2() {
		int[] ROW = new int[1];
		int a_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		x2 = a_1;
	}

	private boolean isSchedulable_a2() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a3() {
		int[] ROW = new int[1];
		int a_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		x3 = a_1;
	}

	private boolean isSchedulable_a3() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a4() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] ROW = new int[1];
		int a_1;
		int _tmp0_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		_tmp0_1 = x0;
		Y0[0] = _tmp0_1;
		fifo_Y0.put(Y0);
		Y1[0] = a_1;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_a4() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a5() {
		int[] ROW = new int[1];
		int a_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		x5 = a_1;
	}

	private boolean isSchedulable_a5() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a6() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] ROW = new int[1];
		int a_1;
		int _tmp0_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		_tmp0_1 = x2;
		Y0[0] = _tmp0_1;
		fifo_Y0.put(Y0);
		Y1[0] = a_1;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_a6() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a7() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] ROW = new int[1];
		int a_1;
		int _tmp0_1;

		fifo_ROW.get(ROW);
		a_1 = ROW[0];
		_tmp0_1 = x1;
		Y0[0] = _tmp0_1;
		fifo_Y0.put(Y0);
		Y1[0] = a_1;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_a7() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void a9() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int _tmp0_1;
		int _tmp1_1;

		_tmp0_1 = x5;
		Y0[0] = _tmp0_1;
		fifo_Y0.put(Y0);
		_tmp1_1 = x3;
		Y1[0] = _tmp1_1;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_a9() {
		if (true) {
		}
		return true;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("ROW".equals(portName)) {
			fifo_ROW = fifo;
		} else if ("Y0".equals(portName)) {
			fifo_Y0 = fifo;
		} else if ("Y1".equals(portName)) {
			fifo_Y1 = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_s0,
		s_s1,
		s_s2,
		s_s3,
		s_s4,
		s_s5,
		s_s6,
		s_s7,
		s_s8
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
			a1();
			_FSM_state = States.s_s2;
			res = true;
		}
		return res;
	}

	private boolean s2_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a2()) {
			a2();
			_FSM_state = States.s_s3;
			res = true;
		}
		return res;
	}

	private boolean s3_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a3()) {
			a3();
			_FSM_state = States.s_s4;
			res = true;
		}
		return res;
	}

	private boolean s4_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a4()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
				a4();
				_FSM_state = States.s_s5;
				res = true;
			}
		}
		return res;
	}

	private boolean s5_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a5()) {
			a5();
			_FSM_state = States.s_s6;
			res = true;
		}
		return res;
	}

	private boolean s6_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a6()) {
			if (fifo_Y0.hasRoom(1) && fifo_Y1.hasRoom(1)) {
				a6();
				_FSM_state = States.s_s7;
				res = true;
			}
		}
		return res;
	}

	private boolean s7_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a7()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
				a7();
				_FSM_state = States.s_s8;
				res = true;
			}
		}
		return res;
	}

	private boolean s8_state_scheduler() {
		boolean res = false;
		if (isSchedulable_a9()) {
			if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
				a9();
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
			case s_s3:
				res = s3_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_s4:
				res = s4_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_s5:
				res = s5_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_s6:
				res = s6_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_s7:
				res = s7_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_s8:
				res = s8_state_scheduler();
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
