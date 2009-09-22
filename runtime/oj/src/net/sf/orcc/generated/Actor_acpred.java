/**
 * Generated from "acpred"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_acpred implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_AC;
	private IntFifo fifo_PTR;
	private IntFifo fifo_START;

	// Output FIFOs
	private IntFifo fifo_OUT;

	// State variables of the actor
	private int MAXW_IN_MB = 121;

	private int MB_COORD_SZ = 8;

	private int SAMPLE_SZ = 13;

	private int count;

	private int BUF_SIZE = 984;

	private int ptr;

	private int pred_ptr;

	private int[] buf = new int[15744];

	private int comp;

	private boolean top;

	private boolean acpred_flag;



	public Actor_acpred() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\ACPred.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("advance", new Location(96, 2, 180)); 
		actionLocation.put("copy", new Location(107, 2, 602)); 
		actionLocation.put("newvop", new Location(70, 2, 132)); 
		actionLocation.put("skip", new Location(79, 2, 72)); 
		actionLocation.put("start", new Location(88, 2, 133)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	private String getNextSchedulableAction_pred() {
		if (isSchedulable_advance()) {
			return "advance";
		} else if (isSchedulable_copy()) {
			if (fifo_OUT.hasRoom(1)) {
				return "copy";
			}
		}

		return null;
	}

	private String getNextSchedulableAction_start() {
		if (isSchedulable_newvop()) {
			return "newvop";
		} else if (isSchedulable_skip()) {
			return "skip";
		} else if (isSchedulable_start()) {
			return "start";
		}

		return null;
	}

	@Override
	public String getNextSchedulableAction() {
		switch (_FSM_state) {
		case s_pred:
			return getNextSchedulableAction_pred();
		case s_start:
			return getNextSchedulableAction_start();

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

	private void newvop() {
		int[] START = new int[1];

		fifo_START.get(START);
		comp = 0;
		ptr = 8;
	}

	private boolean isSchedulable_newvop() {
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
			_tmp0_1 = s_1 == -2;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void skip() {
		int[] START = new int[1];

		fifo_START.get(START);
		count = 64;
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
		int[] PTR = new int[1];
		int s_1;
		int p_1;

		fifo_START.get(START);
		s_1 = START[0];
		fifo_PTR.get(PTR);
		p_1 = PTR[0];
		count = 1;
		pred_ptr = p_1;
		top = s_1 == 2;
		acpred_flag = s_1 != 0;
	}

	private boolean isSchedulable_start() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_START.hasTokens(1);
		_tmp2_1 = fifo_PTR.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void advance() {
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp3_2;
		int _tmp5_1;
		int _tmp3_3;

		comp++;
		_tmp1_1 = comp;
		if (_tmp1_1 == 6) {
			comp = 0;
			_tmp2_1 = ptr;
			if (_tmp2_1 == 8) {
				_tmp3_1 = 976;
				_tmp3_2 = _tmp3_1;
			} else {
				_tmp5_1 = ptr;
				_tmp3_3 = _tmp5_1 - 8;
				_tmp3_2 = _tmp3_3;
			}
			ptr = _tmp3_2;
		}
	}

	private boolean isSchedulable_advance() {
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

	private void copy() {
		int[] OUT = new int[1];
		int[] AC = new int[1];
		int ac_1;
		int pred0_1;
		int _tmp0_1;
		int v0_1;
		int _tmp1_1;
		int h0_1;
		boolean top_edge0_1;
		boolean left_edge0_1;
		int index0_1;
		int index0_2;
		int index0_3;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int pred0_2;
		int pred0_3;
		int _tmp7_1;
		int _tmp8_1;

		fifo_AC.get(AC);
		ac_1 = AC[0];
		pred0_1 = ac_1;
		_tmp0_1 = count;
		v0_1 = _tmp0_1 & 7;
		_tmp1_1 = count;
		h0_1 = _tmp1_1 >> 3 & 7;
		top_edge0_1 = h0_1 == 0;
		left_edge0_1 = v0_1 == 0;
		if (top_edge0_1) {
			index0_1 = v0_1;
			index0_2 = index0_1;
		} else {
			index0_3 = h0_1 | 8;
			index0_2 = index0_3;
		}
		_tmp2_1 = acpred_flag;
		_tmp3_1 = top;
		_tmp4_1 = top;
		if (_tmp2_1 && (_tmp3_1 && top_edge0_1 || !_tmp4_1 && left_edge0_1)) {
			_tmp5_1 = pred_ptr;
			_tmp6_1 = buf[_tmp5_1 << 4 | index0_2];
			pred0_2 = pred0_1 + _tmp6_1;
			pred0_3 = pred0_2;
		} else {
			pred0_3 = pred0_1;
		}
		if (left_edge0_1 || top_edge0_1) {
			_tmp7_1 = ptr;
			_tmp8_1 = comp;
			buf[(_tmp7_1 | _tmp8_1) << 4 | index0_2] = pred0_3;
		}
		count++;
		OUT[0] = pred0_3;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_copy() {
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

	// Initializes

	private void untagged01() {
		int k0_1;
		int k0_2;
		int k0_3;

		k0_1 = 1;
		k0_3 = k0_1;
		while (k0_3 < 15745) {
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
		} else if ("PTR".equals(portName)) {
			fifo_PTR = fifo;
		} else if ("START".equals(portName)) {
			fifo_START = fifo;
		} else if ("OUT".equals(portName)) {
			fifo_OUT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_pred,
		s_start
	};

	private States _FSM_state = States.s_start;

	private boolean pred_state_scheduler() {
		boolean res = false;
		if (isSchedulable_advance()) {
			advance();
			_FSM_state = States.s_start;
			res = true;
		} else if (isSchedulable_copy()) {
			if (fifo_OUT.hasRoom(1)) {
				copy();
				_FSM_state = States.s_pred;
				res = true;
			}
		}
		return res;
	}

	private boolean start_state_scheduler() {
		boolean res = false;
		if (isSchedulable_newvop()) {
			newvop();
			_FSM_state = States.s_start;
			res = true;
		} else if (isSchedulable_skip()) {
			skip();
			_FSM_state = States.s_pred;
			res = true;
		} else if (isSchedulable_start()) {
			start();
			_FSM_state = States.s_pred;
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
			case s_pred:
				res = pred_state_scheduler();
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
