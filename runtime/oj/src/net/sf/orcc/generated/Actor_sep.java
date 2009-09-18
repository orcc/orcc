/**
 * Generated from "sep"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_sep implements IActor {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_X0;
	private IntFifo fifo_X1;
	private IntFifo fifo_X2;
	private IntFifo fifo_X3;
	private IntFifo fifo_ROW;

	// Output FIFOs
	private IntFifo fifo_R0;
	private IntFifo fifo_R1;
	private IntFifo fifo_R2;
	private IntFifo fifo_R3;
	private IntFifo fifo_C0;
	private IntFifo fifo_C1;
	private IntFifo fifo_C2;
	private IntFifo fifo_C3;

	// State variables of the actor
	private int isz = 16;

	private int rsz = 16;

	private int csz = 10;


	
	public Actor_sep() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Separate.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("untagged01", new Location(53, 3, 99)); 
		actionLocation.put("untagged02", new Location(56, 3, 143)); 
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
		int[] R3 = new int[1];
		int[] R2 = new int[1];
		int[] R1 = new int[1];
		int[] R0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
		boolean[] ROW = new boolean[1];
		int a_1;
		int b_1;
		int c_1;
		int d_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		fifo_X2.get(X2);
		c_1 = X2[0];
		fifo_X3.get(X3);
		d_1 = X3[0];
		fifo_ROW.get(ROW);
		R0[0] = a_1;
		fifo_R0.put(R0);
		R1[0] = b_1;
		fifo_R1.put(R1);
		R2[0] = c_1;
		fifo_R2.put(R2);
		R3[0] = d_1;
		fifo_R3.put(R3);
	}

	private boolean isSchedulable_untagged01() {
		boolean[] ROW = new boolean[1];
		int[] X3 = new int[1];
		int[] X2 = new int[1];
		int[] X1 = new int[1];
		int[] X0 = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		boolean _tmp5_1;
		boolean r_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		_tmp3_1 = fifo_X2.hasTokens(1);
		_tmp4_1 = fifo_X3.hasTokens(1);
		_tmp5_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1 && _tmp5_1) {
			fifo_X0.peek(X0);
			fifo_X1.peek(X1);
			fifo_X2.peek(X2);
			fifo_X3.peek(X3);
			fifo_ROW.peek(ROW);
			r_1 = ROW[0];
			_tmp0_1 = r_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void untagged02() {
		int[] C3 = new int[1];
		int[] C2 = new int[1];
		int[] C1 = new int[1];
		int[] C0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
		boolean[] ROW = new boolean[1];
		int a_1;
		int b_1;
		int c_1;
		int d_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		fifo_X2.get(X2);
		c_1 = X2[0];
		fifo_X3.get(X3);
		d_1 = X3[0];
		fifo_ROW.get(ROW);
		C0[0] = a_1 >> 6;
		fifo_C0.put(C0);
		C1[0] = b_1 >> 6;
		fifo_C1.put(C1);
		C2[0] = c_1 >> 6;
		fifo_C2.put(C2);
		C3[0] = d_1 >> 6;
		fifo_C3.put(C3);
	}

	private boolean isSchedulable_untagged02() {
		boolean[] ROW = new boolean[1];
		int[] X3 = new int[1];
		int[] X2 = new int[1];
		int[] X1 = new int[1];
		int[] X0 = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		boolean _tmp5_1;
		boolean r_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		_tmp3_1 = fifo_X2.hasTokens(1);
		_tmp4_1 = fifo_X3.hasTokens(1);
		_tmp5_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1 && _tmp5_1) {
			fifo_X0.peek(X0);
			fifo_X1.peek(X1);
			fifo_X2.peek(X2);
			fifo_X3.peek(X3);
			fifo_ROW.peek(ROW);
			r_1 = ROW[0];
			_tmp0_1 = !r_1;
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
		} else if ("X2".equals(portName)) {
			fifo_X2 = fifo;
		} else if ("X3".equals(portName)) {
			fifo_X3 = fifo;
		} else if ("ROW".equals(portName)) {
			fifo_ROW = fifo;
		} else if ("R0".equals(portName)) {
			fifo_R0 = fifo;
		} else if ("R1".equals(portName)) {
			fifo_R1 = fifo;
		} else if ("R2".equals(portName)) {
			fifo_R2 = fifo;
		} else if ("R3".equals(portName)) {
			fifo_R3 = fifo;
		} else if ("C0".equals(portName)) {
			fifo_C0 = fifo;
		} else if ("C1".equals(portName)) {
			fifo_C1 = fifo;
		} else if ("C2".equals(portName)) {
			fifo_C2 = fifo;
		} else if ("C3".equals(portName)) {
			fifo_C3 = fifo;
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
				if (fifo_R3.hasRoom(1) && fifo_R2.hasRoom(1) && fifo_R1.hasRoom(1) && fifo_R0.hasRoom(1)) {
					untagged01();
					res = true;
					i++;
				}
			} else if (isSchedulable_untagged02()) {
				if (fifo_C2.hasRoom(1) && fifo_C3.hasRoom(1) && fifo_C1.hasRoom(1) && fifo_C0.hasRoom(1)) {
					untagged02();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
