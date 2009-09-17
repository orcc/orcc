/**
 * Generated from "combine"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_combine implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_X0;
	private IntFifo fifo_X1;
	private IntFifo fifo_X2;
	private IntFifo fifo_X3;
	private IntFifo fifo_ROW;

	// Output FIFOs
	private IntFifo fifo_Y0;
	private IntFifo fifo_Y1;

	// State variables of the actor
	private int isz = 30;

	private int osz = 24;

	private int count = 0;


	
	public Actor_combine() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures
	// Actions

	private void row() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
		boolean[] ROW = new boolean[1];
		int a_1;
		int b_1;
		int c_1;
		int d_1;
		int _tmp0_1;
		boolean s0_1;
		int o0_1;
		int o0_2;
		int o0_3;
		int y00_1;
		int y10_1;
		int _tmp1_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		fifo_X2.get(X2);
		c_1 = X2[0];
		fifo_X3.get(X3);
		d_1 = X3[0];
		fifo_ROW.get(ROW);
		_tmp0_1 = count;
		s0_1 = _tmp0_1 == 0;
		if (s0_1) {
			o0_1 = 128;
			o0_2 = o0_1;
		} else {
			o0_3 = 0;
			o0_2 = o0_3;
		}
		y00_1 = a_1 + d_1 + o0_2;
		y10_1 = b_1 - c_1 + o0_2;
		_tmp1_1 = count;
		count = _tmp1_1 + 1 & 3;
		Y0[0] = y00_1;
		fifo_Y0.put(Y0);
		Y1[0] = y10_1;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_row() {
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

	private void col() {
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
		boolean[] ROW = new boolean[1];
		int a_1;
		int b_1;
		int c_1;
		int d_1;
		int _tmp0_1;
		boolean s0_1;
		int o0_1;
		int o0_2;
		int o0_3;
		int y00_1;
		int y10_1;
		int _tmp1_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		fifo_X2.get(X2);
		c_1 = X2[0];
		fifo_X3.get(X3);
		d_1 = X3[0];
		fifo_ROW.get(ROW);
		_tmp0_1 = count;
		s0_1 = _tmp0_1 == 0;
		if (s0_1) {
			o0_1 = 65536;
			o0_2 = o0_1;
		} else {
			o0_3 = 4;
			o0_2 = o0_3;
		}
		y00_1 = a_1 + d_1 + o0_2;
		y10_1 = b_1 - c_1 + o0_2;
		_tmp1_1 = count;
		count = _tmp1_1 + 1 & 3;
		Y0[0] = y00_1 >> 3;
		fifo_Y0.put(Y0);
		Y1[0] = y10_1 >> 3;
		fifo_Y1.put(Y1);
	}

	private boolean isSchedulable_col() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		boolean _tmp5_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		_tmp3_1 = fifo_X2.hasTokens(1);
		_tmp4_1 = fifo_X3.hasTokens(1);
		_tmp5_1 = fifo_ROW.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1 && _tmp5_1) {
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
		} else if ("X2".equals(portName)) {
			fifo_X2 = fifo;
		} else if ("X3".equals(portName)) {
			fifo_X3 = fifo;
		} else if ("ROW".equals(portName)) {
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
	@Override
	public int schedule() {
		boolean res = true;
		int i = 0;

		while (res) {
			res = false;
			if (isSchedulable_row()) {
				if (fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
					row();
					res = true;
					i++;
				}
			} else if (isSchedulable_col()) {
				if (fifo_Y0.hasRoom(1) && fifo_Y1.hasRoom(1)) {
					col();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
