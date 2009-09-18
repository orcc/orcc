/**
 * Generated from "trans"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_trans implements IActor {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_X0;
	private IntFifo fifo_X1;
	private IntFifo fifo_X2;
	private IntFifo fifo_X3;

	// Output FIFOs
	private IntFifo fifo_Y0;
	private IntFifo fifo_Y1;

	// State variables of the actor
	private int sz = 16;

	private int[][][] mem = new int[2][8][8];

	private int rcount = 0;

	private int ccount = 0;

	private int select = 0;


	
	public Actor_trans() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Transpose.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("untagged01", new Location(56, 2, 526)); 
		actionLocation.put("untagged02", new Location(78, 2, 648)); 
		actionLocation.put("untagged03", new Location(106, 2, 132)); 
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

	private void untagged01() {
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
		int a_1;
		int b_1;
		int c_1;
		int d_1;
		int _tmp0_1;
		int row0_1;
		int _tmp1_1;
		int quad0_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;
		int _tmp7_1;
		int _tmp8_1;
		int _tmp9_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		fifo_X2.get(X2);
		c_1 = X2[0];
		fifo_X3.get(X3);
		d_1 = X3[0];
		_tmp0_1 = rcount;
		row0_1 = _tmp0_1 >> 3;
		_tmp1_1 = rcount;
		quad0_1 = _tmp1_1 >> 2 & 1;
		if (quad0_1 == 0) {
			_tmp2_1 = select;
			mem[_tmp2_1][row0_1][0] = a_1;
			_tmp3_1 = select;
			mem[_tmp3_1][row0_1][7] = b_1;
			_tmp4_1 = select;
			mem[_tmp4_1][row0_1][3] = c_1;
			_tmp5_1 = select;
			mem[_tmp5_1][row0_1][4] = d_1;
		} else {
			_tmp6_1 = select;
			mem[_tmp6_1][row0_1][1] = a_1;
			_tmp7_1 = select;
			mem[_tmp7_1][row0_1][6] = b_1;
			_tmp8_1 = select;
			mem[_tmp8_1][row0_1][2] = c_1;
			_tmp9_1 = select;
			mem[_tmp9_1][row0_1][5] = d_1;
		}
		rcount += 4;
	}

	private boolean isSchedulable_untagged01() {
		int[] X3 = new int[1];
		int[] X2 = new int[1];
		int[] X1 = new int[1];
		int[] X0 = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		int _tmp5_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		_tmp3_1 = fifo_X2.hasTokens(1);
		_tmp4_1 = fifo_X3.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
			fifo_X0.peek(X0);
			fifo_X1.peek(X1);
			fifo_X2.peek(X2);
			fifo_X3.peek(X3);
			_tmp5_1 = rcount;
			_tmp0_1 = _tmp5_1 < 64;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void untagged02() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int _tmp0_1;
		int col0_1;
		int _tmp1_1;
		int pair0_1;
		int _tmp2_1;
		int i0_1;
		int a0_1;
		int a0_2;
		int a0_3;
		int a0_4;
		int a0_5;
		int a0_6;
		int a0_7;
		int b0_1;
		int b0_2;
		int b0_3;
		int b0_4;
		int b0_5;
		int b0_6;
		int b0_7;
		int _tmp4_1;
		int _tmp5_1;

		_tmp0_1 = ccount;
		col0_1 = (64 - _tmp0_1) >> 3;
		_tmp1_1 = ccount;
		pair0_1 = (64 - _tmp1_1) >> 1 & 3;
		_tmp2_1 = select;
		i0_1 = _tmp2_1 ^ 1;
		if (pair0_1 == 0) {
			a0_1 = 0;
			a0_2 = a0_1;
		} else {
			if (pair0_1 == 1) {
				a0_3 = 2;
				a0_4 = a0_3;
			} else {
				if (pair0_1 == 2) {
					a0_5 = 1;
					a0_6 = a0_5;
				} else {
					a0_7 = 5;
					a0_6 = a0_7;
				}
				a0_4 = a0_6;
			}
			a0_2 = a0_4;
		}
		if (pair0_1 == 0) {
			b0_1 = 4;
			b0_2 = b0_1;
		} else {
			if (pair0_1 == 1) {
				b0_3 = 6;
				b0_4 = b0_3;
			} else {
				if (pair0_1 == 2) {
					b0_5 = 7;
					b0_6 = b0_5;
				} else {
					b0_7 = 3;
					b0_6 = b0_7;
				}
				b0_4 = b0_6;
			}
			b0_2 = b0_4;
		}
		ccount -= 2;
		_tmp4_1 = mem[i0_1][a0_2][col0_1];
		Y0[0] = _tmp4_1;
		fifo_Y0.put(Y0);
		_tmp5_1 = mem[i0_1][b0_2][col0_1];
		Y1[0] = _tmp5_1;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_untagged02() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = ccount;
			_tmp0_1 = _tmp1_1 > 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void untagged03() {
		select ^= 1;
		ccount = 64;
		rcount = 0;
	}

	private boolean isSchedulable_untagged03() {
		int _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = ccount;
			_tmp2_1 = rcount;
			_tmp0_1 = _tmp1_1 == 0 && _tmp2_1 == 64;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	// Initializes

	private void untagged04() {
		int i0_1;
		int j0_1;
		int k0_1;
		int k0_2;
		int k0_3;
		int k0_4;
		int j0_3;
		int j0_4;
		int k0_5;
		int i0_2;
		int i0_3;

		i0_1 = 1;
		k0_5 = 0;
		i0_3 = i0_1;
		while (i0_3 < 3) {
			j0_1 = 1;
			k0_2 = k0_5;
			j0_4 = j0_1;
			while (j0_4 < 9) {
				k0_1 = 1;
				k0_4 = k0_1;
				while (k0_4 < 9) {
					mem[i0_3 - 1][j0_4 - 1][k0_4 - 1] = 0;
					k0_3 = k0_4 + 1;
					k0_4 = k0_3;
				}
				j0_3 = j0_4 + 1;
				k0_2 = k0_4;
				j0_4 = j0_3;
			}
			i0_2 = i0_3 + 1;
			k0_5 = k0_2;
			i0_3 = i0_2;
		}
	}

	private boolean isSchedulable_untagged04() {
		if (true) {
		}
		return true;
	}
	@Override
	public void initialize() {
		boolean res = true;
		int i = 0;

		if (isSchedulable_untagged04()) {
			untagged04();
			res = true;
			i++;
		}	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("X0".equals(portName)) {
			fifo_X0 = fifo;
		} else if ("X1".equals(portName)) {
			fifo_X1 = fifo;
		} else if ("X2".equals(portName)) {
			fifo_X2 = fifo;
		} else if ("X3".equals(portName)) {
			fifo_X3 = fifo;
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
	@Override
	public int schedule() {
		boolean res = true;
		int i = 0;

		while (res) {
			res = false;
			if (isSchedulable_untagged01()) {
				untagged01();
				res = true;
				i++;
			} else if (isSchedulable_untagged02()) {
				if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
					untagged02();
					res = true;
					i++;
				}
			} else if (isSchedulable_untagged03()) {
				untagged03();
				res = true;
				i++;
			}
		}

		return i;
	}

}
