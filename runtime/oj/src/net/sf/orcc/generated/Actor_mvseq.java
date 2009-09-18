/**
 * Generated from "mvseq"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_mvseq implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_BTYPE;

	// Output FIFOs
	private IntFifo fifo_A;

	// State variables of the actor
	private int MAXW_IN_MB = 121;

	private int MB_COORD_SZ = 8;

	private int BTYPE_SZ = 12;

	private int NEWVOP = 2048;

	private int INTER = 512;

	private int MOTION = 8;

	private int FOURMV = 4;

	private boolean _CAL_tokenMonitor = true;

	private int mbx = 0;

	private boolean top_edge = true;

	private boolean right_edge = false;

	private int comp = 0;

	private int mbwidth = 0;

	private int BUF_SIZE = 121;

	private int ptr;

	private int ptr_left;

	private int ptr_above;

	private int ptr_above_right;

	private int old_a;

	private int a;

	private int b;

	private int c;


	
	public Actor_mvseq() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\MVSequence.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("geth", new Location(121, 2, 34)); 
		actionLocation.put("getw", new Location(111, 2, 300)); 
		actionLocation.put("read_noPredict", new Location(124, 2, 632)); 
		actionLocation.put("read_predict_y0", new Location(155, 2, 407)); 
		actionLocation.put("read_predict_y1", new Location(170, 2, 336)); 
		actionLocation.put("read_predict_y2", new Location(184, 2, 243)); 
		actionLocation.put("read_predict_y3", new Location(197, 2, 150)); 
		actionLocation.put("start", new Location(101, 2, 174)); 
		actionLocation.put("write", new Location(205, 2, 80)); 
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

	private int decrement(int p) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;

		if (p == 1) {
			_tmp0_1 = 120;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = p - 1;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private int access(int mbptr, int component) {
		return mbptr << 3 | component & 3;
	}

	// Actions

	private void start() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
		mbx = 0;
		top_edge = true;
		right_edge = false;
		comp = 0;
	}

	private boolean isSchedulable_start() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp0_1 = (cmd_1 & 2048) != 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void getw() {
		int[] BTYPE = new int[1];
		int w_1;

		fifo_BTYPE.get(BTYPE);
		w_1 = BTYPE[0];
		mbwidth = w_1;
		ptr = 1;
		ptr_left = 2;
		ptr_above = w_1 + 1;
		ptr_above_right = w_1;
	}

	private boolean isSchedulable_getw() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void geth() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
	}

	private boolean isSchedulable_geth() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_noPredict() {
		int[] BTYPE = new int[1];
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;
		int _tmp10_1;
		boolean _tmp11_1;
		int _tmp12_1;
		int _tmp13_1;

		fifo_BTYPE.get(BTYPE);
		comp++;
		_tmp1_1 = comp;
		if (_tmp1_1 == 6) {
			comp = 0;
			_tmp2_1 = mbx;
			mbx = _tmp2_1 + 1;
			_tmp3_1 = ptr;
			_tmp4_1 = decrement(_tmp3_1);
			ptr = _tmp4_1;
			_tmp5_1 = ptr_left;
			_tmp6_1 = decrement(_tmp5_1);
			ptr_left = _tmp6_1;
			_tmp7_1 = ptr_above;
			_tmp8_1 = decrement(_tmp7_1);
			ptr_above = _tmp8_1;
			_tmp9_1 = ptr_above_right;
			_tmp10_1 = decrement(_tmp9_1);
			ptr_above_right = _tmp10_1;
			_tmp11_1 = right_edge;
			if (_tmp11_1) {
				mbx = 0;
				right_edge = false;
				top_edge = false;
			} else {
				_tmp12_1 = mbx;
				_tmp13_1 = mbwidth;
				if (_tmp12_1 == _tmp13_1 - 1) {
					right_edge = true;
				}
			}
		}
	}

	private boolean isSchedulable_read_noPredict() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		int _tmp2_1;
		int _tmp5_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp2_1 = comp;
			_tmp5_1 = comp;
			_tmp0_1 = _tmp2_1 > 3 || (cmd_1 & 512) == 0 || (cmd_1 & 8) == 0 || _tmp5_1 != 0 && (cmd_1 & 4) == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_predict_y0() {
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int pl0_1;
		int pl0_2;
		int _tmp1_1;
		int pl0_3;
		boolean _tmp2_1;
		int pa0_1;
		int pa0_2;
		int _tmp3_1;
		int pa0_3;
		boolean _tmp4_1;
		boolean _tmp5_1;
		int par0_1;
		int par0_2;
		int _tmp6_1;
		int par0_3;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;

		fifo_BTYPE.get(BTYPE);
		_tmp0_1 = mbx;
		if (_tmp0_1 == 0) {
			pl0_1 = 0;
			pl0_2 = pl0_1;
		} else {
			_tmp1_1 = ptr_left;
			pl0_3 = _tmp1_1;
			pl0_2 = pl0_3;
		}
		_tmp2_1 = top_edge;
		if (_tmp2_1) {
			pa0_1 = 0;
			pa0_2 = pa0_1;
		} else {
			_tmp3_1 = ptr_above;
			pa0_3 = _tmp3_1;
			pa0_2 = pa0_3;
		}
		_tmp4_1 = top_edge;
		_tmp5_1 = right_edge;
		if (_tmp4_1 || _tmp5_1) {
			par0_1 = 0;
			par0_2 = par0_1;
		} else {
			_tmp6_1 = ptr_above_right;
			par0_3 = _tmp6_1;
			par0_2 = par0_3;
		}
		_tmp7_1 = access(pl0_2, 1);
		a = _tmp7_1;
		_tmp8_1 = access(pa0_2, 2);
		b = _tmp8_1;
		_tmp9_1 = access(par0_2, 3);
		c = _tmp9_1;
		comp = 1;
	}

	private boolean isSchedulable_read_predict_y0() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			_tmp2_1 = comp;
			_tmp0_1 = _tmp2_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_predict_y1() {
		int[] BTYPE = new int[1];
		boolean _tmp0_1;
		int pa0_1;
		int pa0_2;
		int _tmp1_1;
		int pa0_3;
		boolean _tmp2_1;
		boolean _tmp3_1;
		int par0_1;
		int par0_2;
		int _tmp4_1;
		int par0_3;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;

		fifo_BTYPE.get(BTYPE);
		_tmp0_1 = top_edge;
		if (_tmp0_1) {
			pa0_1 = 0;
			pa0_2 = pa0_1;
		} else {
			_tmp1_1 = ptr_above;
			pa0_3 = _tmp1_1;
			pa0_2 = pa0_3;
		}
		_tmp2_1 = top_edge;
		_tmp3_1 = right_edge;
		if (_tmp2_1 || _tmp3_1) {
			par0_1 = 0;
			par0_2 = par0_1;
		} else {
			_tmp4_1 = ptr_above_right;
			par0_3 = _tmp4_1;
			par0_2 = par0_3;
		}
		_tmp5_1 = ptr;
		_tmp6_1 = access(_tmp5_1, 0);
		a = _tmp6_1;
		_tmp7_1 = access(pa0_2, 3);
		b = _tmp7_1;
		_tmp8_1 = access(par0_2, 2);
		c = _tmp8_1;
		comp = 2;
	}

	private boolean isSchedulable_read_predict_y1() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			_tmp2_1 = comp;
			_tmp0_1 = _tmp2_1 == 1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_predict_y2() {
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int pl0_1;
		int pl0_2;
		int _tmp1_1;
		int pl0_3;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;

		fifo_BTYPE.get(BTYPE);
		_tmp0_1 = mbx;
		if (_tmp0_1 == 0) {
			pl0_1 = 0;
			pl0_2 = pl0_1;
		} else {
			_tmp1_1 = ptr_left;
			pl0_3 = _tmp1_1;
			pl0_2 = pl0_3;
		}
		_tmp2_1 = access(pl0_2, 3);
		a = _tmp2_1;
		_tmp3_1 = ptr;
		_tmp4_1 = access(_tmp3_1, 0);
		b = _tmp4_1;
		_tmp5_1 = ptr;
		_tmp6_1 = access(_tmp5_1, 1);
		c = _tmp6_1;
		comp = 3;
	}

	private boolean isSchedulable_read_predict_y2() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			_tmp2_1 = comp;
			_tmp0_1 = _tmp2_1 == 2;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_predict_y3() {
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;

		fifo_BTYPE.get(BTYPE);
		_tmp0_1 = ptr;
		_tmp1_1 = access(_tmp0_1, 2);
		a = _tmp1_1;
		_tmp2_1 = ptr;
		_tmp3_1 = access(_tmp2_1, 0);
		b = _tmp3_1;
		_tmp4_1 = ptr;
		_tmp5_1 = access(_tmp4_1, 1);
		c = _tmp5_1;
		comp = 4;
	}

	private boolean isSchedulable_read_predict_y3() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void write() {
		int[] A = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;

		_tmp0_1 = a;
		old_a = _tmp0_1;
		_tmp1_1 = b;
		a = _tmp1_1;
		_tmp2_1 = c;
		b = _tmp2_1;
		_tmp3_1 = old_a;
		A[0] = _tmp3_1;
		fifo_A.put(A);
	}

	private boolean isSchedulable_write() {
		if (true) {
		}
		return true;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("BTYPE".equals(portName)) {
			fifo_BTYPE = fifo;
		} else if ("A".equals(portName)) {
			fifo_A = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_geth,
		s_getw,
		s_read,
		s_write_a,
		s_write_b,
		s_write_c
	};

	private States _FSM_state = States.s_read;

	private boolean geth_state_scheduler() {
		boolean res = false;
		if (isSchedulable_geth()) {
			geth();
			_FSM_state = States.s_read;
			res = true;
		}
		return res;
	}

	private boolean getw_state_scheduler() {
		boolean res = false;
		if (isSchedulable_getw()) {
			getw();
			_FSM_state = States.s_geth;
			res = true;
		}
		return res;
	}

	private boolean read_state_scheduler() {
		boolean res = false;
		if (isSchedulable_start()) {
			start();
			_FSM_state = States.s_getw;
			res = true;
		} else if (isSchedulable_read_noPredict()) {
			read_noPredict();
			_FSM_state = States.s_read;
			res = true;
		} else if (isSchedulable_read_predict_y0()) {
			read_predict_y0();
			_FSM_state = States.s_write_a;
			res = true;
		} else if (isSchedulable_read_predict_y1()) {
			read_predict_y1();
			_FSM_state = States.s_write_a;
			res = true;
		} else if (isSchedulable_read_predict_y2()) {
			read_predict_y2();
			_FSM_state = States.s_write_a;
			res = true;
		} else if (isSchedulable_read_predict_y3()) {
			read_predict_y3();
			_FSM_state = States.s_write_a;
			res = true;
		}
		return res;
	}

	private boolean write_a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_write()) {
			if (fifo_A.hasRoom(1)) {
				write();
				_FSM_state = States.s_write_b;
				res = true;
			}
		}
		return res;
	}

	private boolean write_b_state_scheduler() {
		boolean res = false;
		if (isSchedulable_write()) {
			if (fifo_A.hasRoom(1)) {
				write();
				_FSM_state = States.s_write_c;
				res = true;
			}
		}
		return res;
	}

	private boolean write_c_state_scheduler() {
		boolean res = false;
		if (isSchedulable_write()) {
			if (fifo_A.hasRoom(1)) {
				write();
				_FSM_state = States.s_read;
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
			case s_geth:
				res = geth_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_getw:
				res = getw_state_scheduler();
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
			case s_write_a:
				res = write_a_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_write_b:
				res = write_b_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_write_c:
				res = write_c_state_scheduler();
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
