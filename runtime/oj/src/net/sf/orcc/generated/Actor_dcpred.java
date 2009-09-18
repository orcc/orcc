/**
 * Generated from "dcpred"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_dcpred implements IActor {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_BTYPE;
	private IntFifo fifo_A;
	private IntFifo fifo_B;
	private IntFifo fifo_C;
	private IntFifo fifo_IN;

	// Output FIFOs
	private IntFifo fifo_OUT;
	private IntFifo fifo_PTR;
	private IntFifo fifo_START;
	private IntFifo fifo_SIGNED;
	private IntFifo fifo_QUANT;

	// State variables of the actor
	private int DCVAL = 1024;

	private int MAXW_IN_MB = 121;

	private int MB_COORD_SZ = 8;

	private int BTYPE_SZ = 12;

	private int SAMPLE_SZ = 13;

	private int NEWVOP = 2048;

	private int INTRA = 1024;

	private int INTER = 512;

	private int QUANT_MASK = 31;

	private int ACCODED = 2;

	private int ACPRED = 1;

	private int QUANT_SZ = 6;

	private int SCALER_SZ = 7;

	private int QP;

	private int round;

	private int BUF_SIZE = 984;

	private int ptr = 8;

	private int[] dc_buf = new int[984];

	private int comp = 0;

	private int dc_pred;

	private int scaler;

	private boolean is_signed;

	private int dc_val;


	
	public Actor_dcpred() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\DCPred.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("advance", new Location(183, 2, 156)); 
		actionLocation.put("getdc_inter", new Location(154, 2, 261)); 
		actionLocation.put("getdc_intra", new Location(164, 2, 85)); 
		actionLocation.put("read_inter_ac", new Location(94, 2, 200)); 
		actionLocation.put("read_intra", new Location(133, 2, 666)); 
		actionLocation.put("read_other", new Location(103, 2, 94)); 
		actionLocation.put("sat", new Location(176, 2, 134)); 
		actionLocation.put("skip", new Location(91, 2, 36)); 
		actionLocation.put("start", new Location(81, 2, 204)); 
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

	private int abs(int x) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;

		if (x < 0) {
			_tmp0_1 = -x;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = x;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private int dc_scaler() {
		int _tmp0_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp1_3;
		int _tmp1_4;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;
		int _tmp1_5;
		int _tmp1_6;
		int _tmp10_1;
		int _tmp1_7;
		int _tmp1_8;
		int _tmp11_1;
		int _tmp12_1;
		int _tmp1_9;
		int _tmp1_10;
		int _tmp13_1;
		int _tmp14_1;
		int _tmp15_1;
		int _tmp1_11;
		int _tmp1_12;
		int _tmp16_1;
		int _tmp1_13;

		_tmp0_1 = comp;
		if ((_tmp0_1 & 4) == 0) {
			_tmp2_1 = QP;
			_tmp3_1 = QP;
			if (_tmp2_1 > 0 && _tmp3_1 < 5) {
				_tmp1_1 = 8;
				_tmp1_2 = _tmp1_1;
			} else {
				_tmp4_1 = QP;
				_tmp5_1 = QP;
				if (_tmp4_1 > 4 && _tmp5_1 < 9) {
					_tmp6_1 = QP;
					_tmp1_3 = 2 * _tmp6_1;
					_tmp1_4 = _tmp1_3;
				} else {
					_tmp7_1 = QP;
					_tmp8_1 = QP;
					if (_tmp7_1 > 8 && _tmp8_1 < 25) {
						_tmp9_1 = QP;
						_tmp1_5 = _tmp9_1 + 8;
						_tmp1_6 = _tmp1_5;
					} else {
						_tmp10_1 = QP;
						_tmp1_7 = 2 * _tmp10_1 - 16;
						_tmp1_6 = _tmp1_7;
					}
					_tmp1_4 = _tmp1_6;
				}
				_tmp1_2 = _tmp1_4;
			}
			_tmp1_8 = _tmp1_2;
		} else {
			_tmp11_1 = QP;
			_tmp12_1 = QP;
			if (_tmp11_1 > 0 && _tmp12_1 < 5) {
				_tmp1_9 = 8;
				_tmp1_10 = _tmp1_9;
			} else {
				_tmp13_1 = QP;
				_tmp14_1 = QP;
				if (_tmp13_1 > 4 && _tmp14_1 < 25) {
					_tmp15_1 = QP;
					_tmp1_11 = (_tmp15_1 + 13) >> 1;
					_tmp1_12 = _tmp1_11;
				} else {
					_tmp16_1 = QP;
					_tmp1_13 = _tmp16_1 - 6;
					_tmp1_12 = _tmp1_13;
				}
				_tmp1_10 = _tmp1_12;
			}
			_tmp1_8 = _tmp1_10;
		}
		return _tmp1_8;
	}

	private int saturate(int x) {
		boolean minus0_1;
		boolean plus0_1;
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;

		minus0_1 = x < -2048;
		plus0_1 = x > 2047;
		if (minus0_1) {
			_tmp0_1 = -2048;
			_tmp0_2 = _tmp0_1;
		} else {
			if (plus0_1) {
				_tmp0_3 = 2047;
				_tmp0_4 = _tmp0_3;
			} else {
				_tmp0_5 = x;
				_tmp0_4 = _tmp0_5;
			}
			_tmp0_2 = _tmp0_4;
		}
		return _tmp0_2;
	}

	// Actions

	private void start() {
		int[] START = new int[1];
		int[] BTYPE = new int[1];
		int cmd_1;
		int _tmp1_1;

		fifo_BTYPE.get(BTYPE);
		cmd_1 = BTYPE[0];
		comp = 0;
		ptr = 8;
		QP = cmd_1 & 31;
		_tmp1_1 = QP;
		round = _tmp1_1 & 1 ^ 1;
		START[0] = -2;
		fifo_START.put(START);
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

	private void skip() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
	}

	private boolean isSchedulable_skip() {
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

	private void read_inter_ac() {
		int[] QUANT = new int[1];
		boolean[] SIGNED = new boolean[1];
		int[] PTR = new int[1];
		int[] START = new int[1];
		int[] BTYPE = new int[1];
		int _tmp0_1;

		fifo_BTYPE.get(BTYPE);
		is_signed = true;
		START[0] = 0;
		fifo_START.put(START);
		PTR[0] = 0;
		fifo_PTR.put(PTR);
		SIGNED[0] = true;
		fifo_SIGNED.put(SIGNED);
		_tmp0_1 = QP;
		QUANT[0] = _tmp0_1;
		fifo_QUANT.put(QUANT);
	}

	private boolean isSchedulable_read_inter_ac() {
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
			_tmp0_1 = (cmd_1 & 512) != 0 && (cmd_1 & 2) != 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_other() {
		int[] START = new int[1];
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
		START[0] = -1;
		fifo_START.put(START);
	}

	private boolean isSchedulable_read_other() {
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
			_tmp0_1 = (cmd_1 & 1024) == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_intra() {
		int[] QUANT = new int[1];
		boolean[] SIGNED = new boolean[1];
		int[] START = new int[1];
		int[] PTR = new int[1];
		int[] BTYPE = new int[1];
		int[] A = new int[1];
		int[] B = new int[1];
		int[] C = new int[1];
		int cmd_1;
		int a_1;
		int b_1;
		int c_1;
		int _tmp0_1;
		int dca0_1;
		int _tmp1_1;
		int dcb0_1;
		int _tmp2_1;
		int dcc0_1;
		int horiz0_1;
		int vert0_1;
		boolean top0_1;
		boolean ac0_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp5_2;
		int _tmp5_3;
		int _tmp6_1;
		int _tmp7_1;
		int s0_1;
		int s0_2;
		int s0_3;
		int s0_4;
		int s0_5;
		int _tmp8_1;
		int _tmp9_1;
		int _tmp9_2;
		int _tmp9_3;
		boolean _tmp10_1;
		int _tmp11_1;

		fifo_BTYPE.get(BTYPE);
		cmd_1 = BTYPE[0];
		fifo_A.get(A);
		a_1 = A[0];
		fifo_B.get(B);
		b_1 = B[0];
		fifo_C.get(C);
		c_1 = C[0];
		_tmp0_1 = dc_buf[a_1];
		dca0_1 = _tmp0_1;
		_tmp1_1 = dc_buf[b_1];
		dcb0_1 = _tmp1_1;
		_tmp2_1 = dc_buf[c_1];
		dcc0_1 = _tmp2_1;
		horiz0_1 = abs(dcb0_1 - dcc0_1);
		vert0_1 = abs(dca0_1 - dcb0_1);
		top0_1 = vert0_1 < horiz0_1;
		ac0_1 = (cmd_1 & 1) != 0;
		_tmp4_1 = dc_scaler();
		scaler = _tmp4_1;
		if (top0_1) {
			_tmp5_1 = dcc0_1;
			_tmp5_2 = _tmp5_1;
		} else {
			_tmp5_3 = dca0_1;
			_tmp5_2 = _tmp5_3;
		}
		_tmp6_1 = scaler;
		_tmp7_1 = scaler;
		dc_pred = (_tmp5_2 + (_tmp6_1 >> 1)) / _tmp7_1;
		if (!ac0_1) {
			s0_1 = 0;
			s0_2 = s0_1;
		} else {
			if (top0_1) {
				s0_3 = 2;
				s0_4 = s0_3;
			} else {
				s0_5 = 1;
				s0_4 = s0_5;
			}
			s0_2 = s0_4;
		}
		_tmp8_1 = scaler;
		is_signed = _tmp8_1 == 0;
		if (top0_1) {
			_tmp9_1 = c_1;
			_tmp9_2 = _tmp9_1;
		} else {
			_tmp9_3 = a_1;
			_tmp9_2 = _tmp9_3;
		}
		PTR[0] = _tmp9_2;
		fifo_PTR.put(PTR);
		START[0] = s0_2;
		fifo_START.put(START);
		_tmp10_1 = is_signed;
		SIGNED[0] = _tmp10_1;
		fifo_SIGNED.put(SIGNED);
		_tmp11_1 = QP;
		QUANT[0] = _tmp11_1;
		fifo_QUANT.put(QUANT);
	}

	private boolean isSchedulable_read_intra() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		_tmp2_1 = fifo_A.hasTokens(1);
		_tmp3_1 = fifo_B.hasTokens(1);
		_tmp4_1 = fifo_C.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void getdc_inter() {
		int[] IN = new int[1];
		int dc_1;
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int v0_1;
		int _tmp3_1;
		int _tmp3_2;
		boolean _tmp4_1;
		int _tmp3_3;
		int _tmp3_4;
		int _tmp3_5;
		int _tmp3_6;
		int _tmp3_7;

		fifo_IN.get(IN);
		dc_1 = IN[0];
		_tmp0_1 = QP;
		_tmp1_1 = abs(dc_1);
		_tmp2_1 = round;
		v0_1 = _tmp0_1 * ((_tmp1_1 << 1) + 1) - _tmp2_1;
		if (dc_1 == 0) {
			_tmp3_1 = 0;
			_tmp3_2 = _tmp3_1;
		} else {
			_tmp4_1 = is_signed;
			if (!_tmp4_1) {
				_tmp3_3 = dc_1;
				_tmp3_4 = _tmp3_3;
			} else {
				if (dc_1 < 0) {
					_tmp3_5 = -v0_1;
					_tmp3_6 = _tmp3_5;
				} else {
					_tmp3_7 = v0_1;
					_tmp3_6 = _tmp3_7;
				}
				_tmp3_4 = _tmp3_6;
			}
			_tmp3_2 = _tmp3_4;
		}
		dc_val = _tmp3_2;
	}

	private boolean isSchedulable_getdc_inter() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_IN.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void getdc_intra() {
		int[] IN = new int[1];
		int dc_1;
		int _tmp0_1;
		int _tmp1_1;

		fifo_IN.get(IN);
		dc_1 = IN[0];
		_tmp0_1 = dc_pred;
		_tmp1_1 = scaler;
		dc_val = (dc_1 + _tmp0_1) * _tmp1_1;
	}

	private boolean isSchedulable_getdc_intra() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_IN.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void sat() {
		int[] OUT = new int[1];
		int _tmp0_1;
		int dc0_1;
		int _tmp1_1;
		int _tmp2_1;

		_tmp0_1 = dc_val;
		dc0_1 = saturate(_tmp0_1);
		_tmp1_1 = ptr;
		_tmp2_1 = comp;
		dc_buf[_tmp1_1 | _tmp2_1] = dc0_1;
		OUT[0] = dc0_1;
		fifo_OUT.put(OUT);
	}

	private boolean isSchedulable_sat() {
		if (true) {
		}
		return true;
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
		if (true) {
		}
		return true;
	}

	// Initializes

	private void untagged01() {
		int k0_1;
		int k0_2;
		int k0_3;

		k0_1 = 1;
		k0_3 = k0_1;
		while (k0_3 < 985) {
			dc_buf[k0_3 - 1] = 1024;
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
		if ("BTYPE".equals(portName)) {
			fifo_BTYPE = fifo;
		} else if ("A".equals(portName)) {
			fifo_A = fifo;
		} else if ("B".equals(portName)) {
			fifo_B = fifo;
		} else if ("C".equals(portName)) {
			fifo_C = fifo;
		} else if ("IN".equals(portName)) {
			fifo_IN = fifo;
		} else if ("OUT".equals(portName)) {
			fifo_OUT = fifo;
		} else if ("PTR".equals(portName)) {
			fifo_PTR = fifo;
		} else if ("START".equals(portName)) {
			fifo_START = fifo;
		} else if ("SIGNED".equals(portName)) {
			fifo_SIGNED = fifo;
		} else if ("QUANT".equals(portName)) {
			fifo_QUANT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_advance,
		s_geth,
		s_getw,
		s_inter,
		s_intra,
		s_read,
		s_sat
	};

	private States _FSM_state = States.s_read;

	private boolean advance_state_scheduler() {
		boolean res = false;
		if (isSchedulable_advance()) {
			advance();
			_FSM_state = States.s_read;
			res = true;
		}
		return res;
	}

	private boolean geth_state_scheduler() {
		boolean res = false;
		if (isSchedulable_skip()) {
			skip();
			_FSM_state = States.s_read;
			res = true;
		}
		return res;
	}

	private boolean getw_state_scheduler() {
		boolean res = false;
		if (isSchedulable_skip()) {
			skip();
			_FSM_state = States.s_geth;
			res = true;
		}
		return res;
	}

	private boolean inter_state_scheduler() {
		boolean res = false;
		if (isSchedulable_getdc_inter()) {
			getdc_inter();
			_FSM_state = States.s_sat;
			res = true;
		}
		return res;
	}

	private boolean intra_state_scheduler() {
		boolean res = false;
		if (isSchedulable_getdc_intra()) {
			getdc_intra();
			_FSM_state = States.s_sat;
			res = true;
		}
		return res;
	}

	private boolean read_state_scheduler() {
		boolean res = false;
		if (isSchedulable_start()) {
			if (fifo_START.hasRoom(1)) {
				start();
				_FSM_state = States.s_getw;
				res = true;
			}
		} else if (isSchedulable_read_inter_ac()) {
			if (fifo_QUANT.hasRoom(1) && fifo_SIGNED.hasRoom(1) && fifo_START.hasRoom(1) && fifo_PTR.hasRoom(1)) {
				read_inter_ac();
				_FSM_state = States.s_inter;
				res = true;
			}
		} else if (isSchedulable_read_other()) {
			if (fifo_START.hasRoom(1)) {
				read_other();
				_FSM_state = States.s_advance;
				res = true;
			}
		} else if (isSchedulable_read_intra()) {
			if (fifo_START.hasRoom(1) && fifo_QUANT.hasRoom(1) && fifo_PTR.hasRoom(1) && fifo_SIGNED.hasRoom(1)) {
				read_intra();
				_FSM_state = States.s_intra;
				res = true;
			}
		}
		return res;
	}

	private boolean sat_state_scheduler() {
		boolean res = false;
		if (isSchedulable_sat()) {
			if (fifo_OUT.hasRoom(1)) {
				sat();
				_FSM_state = States.s_advance;
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
			case s_advance:
				res = advance_state_scheduler();
				if (res) {
					i++;
				}
				break;
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
			case s_inter:
				res = inter_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_intra:
				res = intra_state_scheduler();
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
			case s_sat:
				res = sat_state_scheduler();
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
