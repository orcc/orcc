/**
 * Generated from "retrans"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_retrans implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_X0;
	private IntFifo fifo_X1;
	private IntFifo fifo_X2;
	private IntFifo fifo_X3;

	// Output FIFOs
	private IntFifo fifo_Y;

	// State variables of the actor
	private int isz = 10;

	private int osz = 10;

	private int[] mem = new int[128];

	private int rcount = 0;

	private int ccount = 0;

	private int select = 0;



	public Actor_retrans() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Retranspose.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("untagged01", new Location(58, 2, 205)); 
		actionLocation.put("untagged02", new Location(68, 2, 550)); 
		actionLocation.put("untagged03", new Location(91, 2, 132)); 
	}

	@Override
	public String getFile() {
		return file;
	}

	@Override
	public Location getLocation(String action) {
		return actionLocation.get(action);
	}

	@Override
	public String getNextSchedulableAction() {
		if (isSchedulable_untagged01()) {
			if (fifo_Y.hasRoom(1)) {
				return "untagged01";
			}
		} else if (isSchedulable_untagged02()) {
			return "untagged02";
		} else if (isSchedulable_untagged03()) {
			return "untagged03";
		}

		return null;
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

	private void untagged01() {
		int[] Y = new int[1];
		int _tmp0_1;
		int col0_1;
		int _tmp1_1;
		int row0_1;
		int _tmp2_1;
		int i0_1;
		int _tmp4_1;

		_tmp0_1 = ccount;
		col0_1 = (64 - _tmp0_1) >> 3;
		_tmp1_1 = ccount;
		row0_1 = 64 - _tmp1_1 & 7;
		_tmp2_1 = select;
		i0_1 = _tmp2_1 ^ 1;
		ccount--;
		_tmp4_1 = mem[i0_1 * 64 + row0_1 * 8 + col0_1];
		Y[0] = _tmp4_1;
		fifo_Y.put(Y);
	}

	private boolean isSchedulable_untagged01() {
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

	private void untagged02() {
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
			mem[_tmp2_1 * 64 + row0_1 * 8 + 0] = a_1;
			_tmp3_1 = select;
			mem[_tmp3_1 * 64 + row0_1 * 8 + 7] = b_1;
			_tmp4_1 = select;
			mem[_tmp4_1 * 64 + row0_1 * 8 + 3] = c_1;
			_tmp5_1 = select;
			mem[_tmp5_1 * 64 + row0_1 * 8 + 4] = d_1;
		} else {
			_tmp6_1 = select;
			mem[_tmp6_1 * 64 + row0_1 * 8 + 1] = a_1;
			_tmp7_1 = select;
			mem[_tmp7_1 * 64 + row0_1 * 8 + 6] = b_1;
			_tmp8_1 = select;
			mem[_tmp8_1 * 64 + row0_1 * 8 + 2] = c_1;
			_tmp9_1 = select;
			mem[_tmp9_1 * 64 + row0_1 * 8 + 5] = d_1;
		}
		rcount += 4;
	}

	private boolean isSchedulable_untagged02() {
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
		int k0_1;
		int k0_2;
		int k0_3;

		k0_1 = 1;
		k0_3 = k0_1;
		while (k0_3 < 129) {
			mem[k0_3 - 1] = 0;
			k0_2 = k0_3 + 1;
			k0_3 = k0_2;
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
		} else if ("Y".equals(portName)) {
			fifo_Y = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	@Override
	public int schedule() {
		boolean res = !suspended;
		int i = 0;

		while (res) {
			res = false;
			if (isSchedulable_untagged01()) {
				if (fifo_Y.hasRoom(1)) {
					untagged01();
					res = true;
					i++;
				}
			} else if (isSchedulable_untagged02()) {
				untagged02();
				res = true;
				i++;
			} else if (isSchedulable_untagged03()) {
				untagged03();
				res = true;
				i++;
			}
		}

		return i;
	}

}
