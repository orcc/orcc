/**
 * Generated from "mvrecon"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_mvrecon implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_BTYPE;
	private IntFifo fifo_MVIN;
	private IntFifo fifo_A;

	// Output FIFOs
	private IntFifo fifo_MV;

	// State variables of the actor
	private int MAXW_IN_MB = 121;

	private int MB_COORD_SZ = 8;

	private int BTYPE_SZ = 12;

	private int MV_SZ = 9;

	private int NEWVOP = 2048;

	private int INTER = 512;

	private int FCODE_MASK = 448;

	private int FCODE_SHIFT = 6;

	private int FOURMV = 4;

	private int MOTION = 8;

	private int mbx = 0;

	private boolean top_edge = true;

	private int comp = 0;

	private int mbwidth = 0;

	private int BUF_SIZE = 968;

	private int Y_SELECT = 4;

	private int ptr;

	private int[] buf = new int[968];

	private int mv_rsize;

	private int mv_range;

	private int mv_low;

	private int mv_high;

	private boolean fourmv;

	private int aptr;

	private int bptr;

	private int cptr;

	private int apred;

	private int bpred;

	private int cpred;

	private int mag;

	private int mv_x;

	private int mv_y;

	private int pred_x;

	private int pred_y;

	private int sum_x;

	private int sum_y;

	private boolean x_flag;

	private int res_shift;


	
	public Actor_mvrecon() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures

	private int middle(int a, int b, int c) {
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;
		int _tmp0_4;
		int _tmp0_5;
		int _tmp0_6;
		int _tmp0_7;
		int _tmp0_8;
		int _tmp0_9;
		int _tmp0_10;
		int _tmp0_11;

		if (a < b) {
			if (a > c) {
				_tmp0_1 = a;
				_tmp0_2 = _tmp0_1;
			} else {
				if (b < c) {
					_tmp0_3 = b;
					_tmp0_4 = _tmp0_3;
				} else {
					_tmp0_5 = c;
					_tmp0_4 = _tmp0_5;
				}
				_tmp0_2 = _tmp0_4;
			}
			_tmp0_6 = _tmp0_2;
		} else {
			if (b > c) {
				_tmp0_7 = b;
				_tmp0_8 = _tmp0_7;
			} else {
				if (a < c) {
					_tmp0_9 = a;
					_tmp0_10 = _tmp0_9;
				} else {
					_tmp0_11 = c;
					_tmp0_10 = _tmp0_11;
				}
				_tmp0_8 = _tmp0_10;
			}
			_tmp0_6 = _tmp0_8;
		}
		return _tmp0_6;
	}

	private int mvcalc(int pred, int mv_mag, int mag_shift) {
		int _tmp0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp1_3;
		int _tmp1_4;
		int _tmp1_5;

		_tmp0_1 = mv_rsize;
		if (_tmp0_1 == 0 || mv_mag == 0) {
			_tmp1_1 = pred + mv_mag;
			_tmp1_2 = _tmp1_1;
		} else {
			if (mv_mag < 0) {
				_tmp1_3 = pred - mag_shift;
				_tmp1_4 = _tmp1_3;
			} else {
				_tmp1_5 = pred + mag_shift;
				_tmp1_4 = _tmp1_5;
			}
			_tmp1_2 = _tmp1_4;
		}
		return _tmp1_2;
	}

	private int mvclip(int v) {
		int _tmp0_1;
		int _tmp2_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp1_3;
		int _tmp1_4;
		int _tmp1_5;

		_tmp0_1 = mv_low;
		if (v < _tmp0_1) {
			_tmp2_1 = mv_range;
			_tmp1_1 = v + _tmp2_1;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp3_1 = mv_high;
			if (v > _tmp3_1) {
				_tmp4_1 = mv_range;
				_tmp1_3 = v - _tmp4_1;
				_tmp1_4 = _tmp1_3;
			} else {
				_tmp1_5 = v;
				_tmp1_4 = _tmp1_5;
			}
			_tmp1_2 = _tmp1_4;
		}
		return _tmp1_2;
	}

	private int uvclip_1(int v) {
		int vv0_1;
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;

		vv0_1 = v >> 1;
		if ((v & 3) == 0) {
			_tmp0_1 = 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = 1;
			_tmp0_2 = _tmp0_3;
		}
		return vv0_1 | _tmp0_2;
	}

	private int uvclip_4(int v) {
		boolean sign0_1;
		int absv0_1;
		int absv0_2;
		int absv0_3;
		int delta0_1;
		int delta0_2;
		int delta0_3;
		int delta0_4;
		int delta0_5;
		int vv0_1;
		int _tmp0_1;
		int _tmp0_2;
		int _tmp0_3;

		sign0_1 = v < 0;
		if (sign0_1) {
			absv0_1 = -v;
			absv0_2 = absv0_1;
		} else {
			absv0_3 = v;
			absv0_2 = absv0_3;
		}
		if (v < 3) {
			delta0_1 = 0;
			delta0_2 = delta0_1;
		} else {
			if (v > 13) {
				delta0_3 = 2;
				delta0_4 = delta0_3;
			} else {
				delta0_5 = 1;
				delta0_4 = delta0_5;
			}
			delta0_2 = delta0_4;
		}
		vv0_1 = ((absv0_2 >> 4) << 1) + delta0_2;
		if (sign0_1) {
			_tmp0_1 = -vv0_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = vv0_1;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	// Actions

	private void start() {
		int[] BTYPE = new int[1];
		int cmd_1;
		int fcode0_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;

		fifo_BTYPE.get(BTYPE);
		cmd_1 = BTYPE[0];
		fcode0_1 = (cmd_1 & 448) >> 6;
		mbx = 0;
		top_edge = true;
		comp = 0;
		if (fcode0_1 > 0) {
			mv_rsize = fcode0_1 - 1;
			_tmp2_1 = mv_rsize;
			mv_range = 1 << (_tmp2_1 + 5);
			_tmp3_1 = mv_range;
			mv_low = -_tmp3_1;
			_tmp4_1 = mv_range;
			mv_high = _tmp4_1 - 1;
			_tmp5_1 = mv_range;
			mv_range = _tmp5_1 << 1;
		}
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
		ptr = 8;
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

	private void read_noMotion() {
		int[] BTYPE = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int p0_1;
		int _tmp2_1;

		fifo_BTYPE.get(BTYPE);
		_tmp0_1 = ptr;
		_tmp1_1 = comp;
		p0_1 = _tmp0_1 | _tmp1_1;
		_tmp2_1 = comp;
		if (_tmp2_1 < 4) {
			buf[p0_1] = 0;
			buf[p0_1 | 4] = 0;
		}
	}

	private boolean isSchedulable_read_noMotion() {
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
			_tmp0_1 = (cmd_1 & 512) == 0 || (cmd_1 & 8) == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void read_motion() {
		int[] BTYPE = new int[1];
		int cmd_1;
		int _tmp0_1;

		fifo_BTYPE.get(BTYPE);
		cmd_1 = BTYPE[0];
		_tmp0_1 = comp;
		if (_tmp0_1 == 0) {
			fourmv = (cmd_1 & 4) != 0;
			sum_x = 0;
			sum_y = 0;
		}
	}

	private boolean isSchedulable_read_motion() {
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

	private void compute_done() {
	}

	private boolean isSchedulable_compute_done() {
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = comp;
			_tmp2_1 = comp;
			_tmp3_1 = fourmv;
			_tmp0_1 = _tmp1_1 > 3 || _tmp2_1 != 0 && !_tmp3_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void compute_start() {
		int[] A = new int[1];
		int a_1;
		int _tmp0_1;
		int _tmp1_1;

		fifo_A.get(A);
		a_1 = A[0];
		_tmp0_1 = bptr;
		aptr = _tmp0_1;
		_tmp1_1 = cptr;
		bptr = _tmp1_1;
		cptr = a_1;
	}

	private boolean isSchedulable_compute_start() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_A.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_pred() {
		int _tmp0_1;
		int t0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;

		_tmp0_1 = aptr;
		t0_1 = _tmp0_1;
		_tmp1_1 = bpred;
		apred = _tmp1_1;
		_tmp2_1 = cpred;
		bpred = _tmp2_1;
		_tmp3_1 = buf[t0_1];
		cpred = _tmp3_1;
		_tmp4_1 = bptr;
		aptr = _tmp4_1;
		_tmp5_1 = cptr;
		bptr = _tmp5_1;
		cptr = t0_1 | 4;
	}

	private boolean isSchedulable_get_pred() {
		if (true) {
		}
		return true;
	}

	private void do_pred() {
		int _tmp0_1;
		boolean _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;

		_tmp0_1 = comp;
		_tmp1_1 = top_edge;
		if (_tmp0_1 >= 2 || !_tmp1_1) {
			_tmp2_1 = apred;
			_tmp3_1 = bpred;
			_tmp4_1 = cpred;
			_tmp5_1 = middle(_tmp2_1, _tmp3_1, _tmp4_1);
			apred = _tmp5_1;
		}
	}

	private boolean isSchedulable_do_pred() {
		if (true) {
		}
		return true;
	}

	private void get_mag() {
		int[] MVIN = new int[1];
		int m_1;

		fifo_MVIN.get(MVIN);
		m_1 = MVIN[0];
		mag = m_1;
	}

	private boolean isSchedulable_get_mag() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_MVIN.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_residual_init() {
		int _tmp0_1;
		int _tmp2_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp3_1;
		int _tmp1_3;

		_tmp0_1 = mag;
		if (_tmp0_1 < 0) {
			_tmp2_1 = mag;
			_tmp1_1 = ~_tmp2_1;
			_tmp1_2 = _tmp1_1;
		} else {
			_tmp3_1 = mag;
			_tmp1_3 = _tmp3_1 - 1;
			_tmp1_2 = _tmp1_3;
		}
		res_shift = _tmp1_2;
	}

	private boolean isSchedulable_get_residual_init() {
		if (true) {
		}
		return true;
	}

	private void get_residual_shift() {
		int _tmp0_1;
		int count0_1;
		int _tmp1_1;
		int count0_2;
		int count0_3;

		_tmp0_1 = mv_rsize;
		count0_1 = _tmp0_1;
		count0_3 = count0_1;
		while (count0_3 > 0) {
			_tmp1_1 = res_shift;
			res_shift = _tmp1_1 << 1;
			count0_2 = count0_3 - 1;
			count0_3 = count0_2;
		}
	}

	private boolean isSchedulable_get_residual_shift() {
		if (true) {
		}
		return true;
	}

	private void get_residual_adjust() {
		int[] MVIN = new int[1];
		int s_1;
		int _tmp0_1;

		fifo_MVIN.get(MVIN);
		s_1 = MVIN[0];
		_tmp0_1 = res_shift;
		res_shift = _tmp0_1 + s_1 + 1;
	}

	private boolean isSchedulable_get_residual_adjust() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_MVIN.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void get_residual_calc() {
		int _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;

		_tmp0_1 = apred;
		_tmp1_1 = mag;
		_tmp2_1 = res_shift;
		_tmp3_1 = mvcalc(_tmp0_1, _tmp1_1, _tmp2_1);
		res_shift = _tmp3_1;
	}

	private boolean isSchedulable_get_residual_calc() {
		if (true) {
		}
		return true;
	}

	private void get_residual_clip() {
		int _tmp0_1;
		int _tmp1_1;

		_tmp0_1 = res_shift;
		_tmp1_1 = mvclip(_tmp0_1);
		res_shift = _tmp1_1;
	}

	private boolean isSchedulable_get_residual_clip() {
		if (true) {
		}
		return true;
	}

	private void get_residual_final() {
		int _tmp0_1;
		int _tmp1_1;
		int sum0_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;

		_tmp0_1 = sum_x;
		_tmp1_1 = res_shift;
		sum0_1 = _tmp0_1 + _tmp1_1;
		_tmp2_1 = mv_y;
		mv_x = _tmp2_1;
		_tmp3_1 = res_shift;
		mv_y = _tmp3_1;
		_tmp4_1 = sum_y;
		sum_x = _tmp4_1;
		sum_y = sum0_1;
		x_flag = true;
	}

	private boolean isSchedulable_get_residual_final() {
		if (true) {
		}
		return true;
	}

	private void write_luma() {
		int[] MV = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int p0_1;
		int _tmp2_1;
		int t0_1;
		boolean _tmp3_1;
		int _tmp4_1;
		int _tmp4_2;
		int _tmp4_3;
		int _tmp6_1;
		int _tmp7_1;
		boolean _tmp8_1;

		_tmp0_1 = ptr;
		_tmp1_1 = comp;
		p0_1 = _tmp0_1 | _tmp1_1;
		_tmp2_1 = mv_x;
		t0_1 = _tmp2_1;
		_tmp3_1 = x_flag;
		if (_tmp3_1) {
			_tmp4_1 = 0;
			_tmp4_2 = _tmp4_1;
		} else {
			_tmp4_3 = 4;
			_tmp4_2 = _tmp4_3;
		}
		_tmp6_1 = mv_x;
		buf[p0_1 | _tmp4_2] = _tmp6_1;
		_tmp7_1 = mv_y;
		mv_x = _tmp7_1;
		mv_y = t0_1;
		_tmp8_1 = x_flag;
		x_flag = !_tmp8_1;
		MV[0] = t0_1;
		fifo_MV.put(MV);
	}

	private boolean isSchedulable_write_luma() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = comp;
			_tmp0_1 = _tmp1_1 < 4;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void write_chroma() {
		int[] MV = new int[1];
		boolean _tmp0_1;
		int _tmp1_1;
		int _tmp2_1;
		int mv0_1;
		int mv0_2;
		int _tmp3_1;
		int _tmp4_1;
		int mv0_3;
		int _tmp5_1;
		int t0_1;
		int _tmp6_1;

		_tmp0_1 = fourmv;
		if (_tmp0_1) {
			_tmp1_1 = sum_x;
			_tmp2_1 = uvclip_4(_tmp1_1);
			mv0_1 = _tmp2_1;
			mv0_2 = mv0_1;
		} else {
			_tmp3_1 = sum_x;
			_tmp4_1 = uvclip_1(_tmp3_1);
			mv0_3 = _tmp4_1;
			mv0_2 = mv0_3;
		}
		_tmp5_1 = sum_x;
		t0_1 = _tmp5_1;
		_tmp6_1 = sum_y;
		sum_x = _tmp6_1;
		sum_y = t0_1;
		MV[0] = mv0_2;
		fifo_MV.put(MV);
	}

	private boolean isSchedulable_write_chroma() {
		if (true) {
		}
		return true;
	}

	private void advance() {
		int _tmp1_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp6_2;
		int _tmp8_1;
		int _tmp6_3;

		comp++;
		_tmp1_1 = comp;
		if (_tmp1_1 == 6) {
			comp = 0;
			_tmp2_1 = mbx;
			mbx = _tmp2_1 + 1;
			_tmp3_1 = mbx;
			_tmp4_1 = mbwidth;
			if (_tmp3_1 == _tmp4_1) {
				top_edge = false;
			}
			_tmp5_1 = ptr;
			if (_tmp5_1 == 8) {
				_tmp6_1 = 960;
				_tmp6_2 = _tmp6_1;
			} else {
				_tmp8_1 = ptr;
				_tmp6_3 = _tmp8_1 - 8;
				_tmp6_2 = _tmp6_3;
			}
			ptr = _tmp6_2;
		}
	}

	private boolean isSchedulable_advance() {
		if (true) {
		}
		return true;
	}

	// Initializes

	private void untagged01() {
		int i0_1;
		int i0_2;
		int i0_3;

		i0_1 = 1;
		i0_3 = i0_1;
		while (i0_3 < 969) {
			buf[i0_3 - 1] = 0;
			i0_2 = i0_3 + 1;
			i0_3 = i0_2;
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
		} else if ("MVIN".equals(portName)) {
			fifo_MVIN = fifo;
		} else if ("A".equals(portName)) {
			fifo_A = fifo;
		} else if ("MV".equals(portName)) {
			fifo_MV = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_advance,
		s_compute,
		s_do_pred_x,
		s_do_pred_y,
		s_get_mag_x,
		s_get_mag_y,
		s_get_pred_p,
		s_get_pred_p1,
		s_get_pred_x,
		s_get_pred_x1,
		s_get_pred_x2,
		s_get_pred_y,
		s_get_pred_y1,
		s_get_pred_y2,
		s_get_res_x,
		s_get_res_x_a,
		s_get_res_x_b,
		s_get_res_x_c,
		s_get_res_x_d,
		s_get_res_x_e,
		s_get_res_y,
		s_get_res_y_a,
		s_get_res_y_b,
		s_get_res_y_c,
		s_get_res_y_d,
		s_get_res_y_e,
		s_geth,
		s_getw,
		s_read,
		s_write,
		s_write_y
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

	private boolean compute_state_scheduler() {
		boolean res = false;
		if (isSchedulable_compute_done()) {
			compute_done();
			_FSM_state = States.s_write;
			res = true;
		} else if (isSchedulable_compute_start()) {
			compute_start();
			_FSM_state = States.s_get_pred_p;
			res = true;
		}
		return res;
	}

	private boolean do_pred_x_state_scheduler() {
		boolean res = false;
		if (isSchedulable_do_pred()) {
			do_pred();
			_FSM_state = States.s_get_mag_x;
			res = true;
		}
		return res;
	}

	private boolean do_pred_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_do_pred()) {
			do_pred();
			_FSM_state = States.s_get_mag_y;
			res = true;
		}
		return res;
	}

	private boolean get_mag_x_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_mag()) {
			get_mag();
			_FSM_state = States.s_get_res_x;
			res = true;
		}
		return res;
	}

	private boolean get_mag_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_mag()) {
			get_mag();
			_FSM_state = States.s_get_res_y;
			res = true;
		}
		return res;
	}

	private boolean get_pred_p_state_scheduler() {
		boolean res = false;
		if (isSchedulable_compute_start()) {
			compute_start();
			_FSM_state = States.s_get_pred_p1;
			res = true;
		}
		return res;
	}

	private boolean get_pred_p1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_compute_start()) {
			compute_start();
			_FSM_state = States.s_get_pred_x;
			res = true;
		}
		return res;
	}

	private boolean get_pred_x_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_pred()) {
			get_pred();
			_FSM_state = States.s_get_pred_x1;
			res = true;
		}
		return res;
	}

	private boolean get_pred_x1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_pred()) {
			get_pred();
			_FSM_state = States.s_get_pred_x2;
			res = true;
		}
		return res;
	}

	private boolean get_pred_x2_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_pred()) {
			get_pred();
			_FSM_state = States.s_do_pred_x;
			res = true;
		}
		return res;
	}

	private boolean get_pred_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_pred()) {
			get_pred();
			_FSM_state = States.s_get_pred_y1;
			res = true;
		}
		return res;
	}

	private boolean get_pred_y1_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_pred()) {
			get_pred();
			_FSM_state = States.s_get_pred_y2;
			res = true;
		}
		return res;
	}

	private boolean get_pred_y2_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_pred()) {
			get_pred();
			_FSM_state = States.s_do_pred_y;
			res = true;
		}
		return res;
	}

	private boolean get_res_x_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_init()) {
			get_residual_init();
			_FSM_state = States.s_get_res_x_a;
			res = true;
		}
		return res;
	}

	private boolean get_res_x_a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_shift()) {
			get_residual_shift();
			_FSM_state = States.s_get_res_x_b;
			res = true;
		}
		return res;
	}

	private boolean get_res_x_b_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_adjust()) {
			get_residual_adjust();
			_FSM_state = States.s_get_res_x_c;
			res = true;
		}
		return res;
	}

	private boolean get_res_x_c_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_calc()) {
			get_residual_calc();
			_FSM_state = States.s_get_res_x_d;
			res = true;
		}
		return res;
	}

	private boolean get_res_x_d_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_clip()) {
			get_residual_clip();
			_FSM_state = States.s_get_res_x_e;
			res = true;
		}
		return res;
	}

	private boolean get_res_x_e_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_final()) {
			get_residual_final();
			_FSM_state = States.s_get_pred_y;
			res = true;
		}
		return res;
	}

	private boolean get_res_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_init()) {
			get_residual_init();
			_FSM_state = States.s_get_res_y_a;
			res = true;
		}
		return res;
	}

	private boolean get_res_y_a_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_shift()) {
			get_residual_shift();
			_FSM_state = States.s_get_res_y_b;
			res = true;
		}
		return res;
	}

	private boolean get_res_y_b_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_adjust()) {
			get_residual_adjust();
			_FSM_state = States.s_get_res_y_c;
			res = true;
		}
		return res;
	}

	private boolean get_res_y_c_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_calc()) {
			get_residual_calc();
			_FSM_state = States.s_get_res_y_d;
			res = true;
		}
		return res;
	}

	private boolean get_res_y_d_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_clip()) {
			get_residual_clip();
			_FSM_state = States.s_get_res_y_e;
			res = true;
		}
		return res;
	}

	private boolean get_res_y_e_state_scheduler() {
		boolean res = false;
		if (isSchedulable_get_residual_final()) {
			get_residual_final();
			_FSM_state = States.s_write;
			res = true;
		}
		return res;
	}

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
		} else if (isSchedulable_read_noMotion()) {
			read_noMotion();
			_FSM_state = States.s_advance;
			res = true;
		} else if (isSchedulable_read_motion()) {
			read_motion();
			_FSM_state = States.s_compute;
			res = true;
		}
		return res;
	}

	private boolean write_state_scheduler() {
		boolean res = false;
		if (isSchedulable_write_luma()) {
			if (fifo_MV.hasRoom(1)) {
				write_luma();
				_FSM_state = States.s_write_y;
				res = true;
			}
		} else if (isSchedulable_write_chroma()) {
			if (fifo_MV.hasRoom(1)) {
				write_chroma();
				_FSM_state = States.s_write_y;
				res = true;
			}
		}
		return res;
	}

	private boolean write_y_state_scheduler() {
		boolean res = false;
		if (isSchedulable_write_luma()) {
			if (fifo_MV.hasRoom(1)) {
				write_luma();
				_FSM_state = States.s_advance;
				res = true;
			}
		} else if (isSchedulable_write_chroma()) {
			if (fifo_MV.hasRoom(1)) {
				write_chroma();
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
			case s_compute:
				res = compute_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_do_pred_x:
				res = do_pred_x_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_do_pred_y:
				res = do_pred_y_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_mag_x:
				res = get_mag_x_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_mag_y:
				res = get_mag_y_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_p:
				res = get_pred_p_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_p1:
				res = get_pred_p1_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_x:
				res = get_pred_x_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_x1:
				res = get_pred_x1_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_x2:
				res = get_pred_x2_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_y:
				res = get_pred_y_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_y1:
				res = get_pred_y1_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_pred_y2:
				res = get_pred_y2_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_x:
				res = get_res_x_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_x_a:
				res = get_res_x_a_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_x_b:
				res = get_res_x_b_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_x_c:
				res = get_res_x_c_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_x_d:
				res = get_res_x_d_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_x_e:
				res = get_res_x_e_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_y:
				res = get_res_y_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_y_a:
				res = get_res_y_a_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_y_b:
				res = get_res_y_b_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_y_c:
				res = get_res_y_c_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_y_d:
				res = get_res_y_d_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_get_res_y_e:
				res = get_res_y_e_state_scheduler();
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
			case s_read:
				res = read_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_write:
				res = write_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_write_y:
				res = write_y_state_scheduler();
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
