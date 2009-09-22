/**
 * Generated from "scale"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_scale implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_X0;
	private IntFifo fifo_X1;

	// Output FIFOs
	private IntFifo fifo_Y0;
	private IntFifo fifo_Y1;
	private IntFifo fifo_Y2;
	private IntFifo fifo_Y3;

	// State variables of the actor
	private int isz = 16;

	private int osz = 30;

	private int csz = 13;

	private int[] W0 = {2048, 2676, 2841, 1609};

	private int[] W1 = {2048, 1108, 565, 2408};

	private int ww0 = 2048;

	private int ww1 = 2048;

	private int index = 0;



	public Actor_scale() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Scale.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("untagged01", new Location(60, 2, 246)); 
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
			if (fifo_Y1.hasRoom(1) && fifo_Y3.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_Y2.hasRoom(1)) {
				return "untagged01";
			}
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
		int[] Y3 = new int[1];
		int[] Y2 = new int[1];
		int[] Y1 = new int[1];
		int[] Y0 = new int[1];
		int[] X0 = new int[1];
		int[] X1 = new int[1];
		int a_1;
		int b_1;
		int _tmp0_1;
		int w00_1;
		int _tmp1_1;
		int w10_1;
		int _tmp2_1;
		int _tmp3_1;
		int _tmp4_1;
		int _tmp5_1;
		int _tmp6_1;

		fifo_X0.get(X0);
		a_1 = X0[0];
		fifo_X1.get(X1);
		b_1 = X1[0];
		_tmp0_1 = ww0;
		w00_1 = _tmp0_1;
		_tmp1_1 = ww1;
		w10_1 = _tmp1_1;
		_tmp2_1 = index;
		index = _tmp2_1 + 1 & 3;
		_tmp3_1 = index;
		_tmp4_1 = W0[_tmp3_1];
		ww0 = _tmp4_1;
		_tmp5_1 = index;
		_tmp6_1 = W1[_tmp5_1];
		ww1 = _tmp6_1;
		Y0[0] = a_1 * w00_1;
		fifo_Y0.put(Y0);
		Y1[0] = a_1 * w10_1;
		fifo_Y1.put(Y1);
		Y2[0] = b_1 * w00_1;
		fifo_Y2.put(Y2);
		Y3[0] = b_1 * w10_1;
		fifo_Y3.put(Y3);
	}

	private boolean isSchedulable_untagged01() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_X0.hasTokens(1);
		_tmp2_1 = fifo_X1.hasTokens(1);
		if (_tmp1_1 && _tmp2_1) {
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
		boolean res = !suspended;
		int i = 0;

		while (res) {
			res = false;
			if (isSchedulable_untagged01()) {
				if (fifo_Y1.hasRoom(1) && fifo_Y3.hasRoom(1) && fifo_Y0.hasRoom(1) && fifo_Y2.hasRoom(1)) {
					untagged01();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
