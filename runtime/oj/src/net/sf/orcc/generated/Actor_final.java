/**
 * Generated from "final"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_final implements IActor {

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
	private IntFifo fifo_Y2;
	private IntFifo fifo_Y3;

	// State variables of the actor
	private int isz = 24;

	private int osz = 16;


	
	public Actor_final() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Final.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("untagged01", new Location(51, 2, 136)); 
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
		int[] Y3 = new int[1];
		int[] Y2 = new int[1];
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int[] X2 = new int[1];
		int[] X3 = new int[1];
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
		Y0[0] = (a_1 + c_1) >> 8;
		fifo_Y0.put(Y0);
		Y1[0] = (a_1 - c_1) >> 8;
		fifo_Y1.put(Y1);
		Y2[0] = (b_1 + d_1) >> 8;
		fifo_Y2.put(Y2);
		Y3[0] = (b_1 - d_1) >> 8;
		fifo_Y3.put(Y3);
	}

	private boolean isSchedulable_untagged01() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp3_1;
		boolean _tmp4_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		_tmp3_1 = fifo_X2.hasTokens(1);
		_tmp4_1 = fifo_X3.hasTokens(1);
		if (_tmp1_1 && _tmp2_1 && _tmp3_1 && _tmp4_1) {
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
		} else if ("Y0".equals(portName)) {
			fifo_Y0 = fifo;
		} else if ("Y1".equals(portName)) {
			fifo_Y1 = fifo;
		} else if ("Y2".equals(portName)) {
			fifo_Y2 = fifo;
		} else if ("Y3".equals(portName)) {
			fifo_Y3 = fifo;
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
				if (fifo_Y2.hasRoom(1) && fifo_Y3.hasRoom(1) && fifo_Y1.hasRoom(1) && fifo_Y0.hasRoom(1)) {
					untagged01();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
