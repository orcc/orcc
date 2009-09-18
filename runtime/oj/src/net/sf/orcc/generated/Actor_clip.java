/**
 * Generated from "clip"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_clip implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_I;
	private IntFifo fifo_SIGNED;

	// Output FIFOs
	private IntFifo fifo_O;

	// State variables of the actor
	private int isz = 10;

	private int osz = 9;

	private int count = -1;

	private boolean sflag;


	
	public Actor_clip() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Clip.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("limit", new Location(58, 2, 195)); 
		actionLocation.put("read_signed", new Location(50, 2, 98)); 
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

	private void read_signed() {
		boolean[] SIGNED = new boolean[1];
		boolean s_1;

		fifo_SIGNED.get(SIGNED);
		s_1 = SIGNED[0];
		sflag = s_1;
		count = 63;
	}

	private boolean isSchedulable_read_signed() {
		boolean[] SIGNED = new boolean[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_SIGNED.hasTokens(1);
		if (_tmp1_1) {
			fifo_SIGNED.peek(SIGNED);
			_tmp2_1 = count;
			_tmp0_1 = _tmp2_1 < 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void limit() {
		int[] O = new int[1];
		int[] I = new int[1];
		int i_1;
		boolean _tmp0_1;
		int min0_1;
		int min0_2;
		int min0_3;
		int _tmp2_1;
		int _tmp2_2;
		int _tmp2_3;
		int _tmp2_4;
		int _tmp2_5;

		fifo_I.get(I);
		i_1 = I[0];
		_tmp0_1 = sflag;
		if (_tmp0_1) {
			min0_1 = -255;
			min0_2 = min0_1;
		} else {
			min0_3 = 0;
			min0_2 = min0_3;
		}
		count--;
		if (i_1 > 255) {
			_tmp2_1 = 255;
			_tmp2_2 = _tmp2_1;
		} else {
			if (i_1 < min0_2) {
				_tmp2_3 = min0_2;
				_tmp2_4 = _tmp2_3;
			} else {
				_tmp2_5 = i_1;
				_tmp2_4 = _tmp2_5;
			}
			_tmp2_2 = _tmp2_4;
		}
		O[0] = _tmp2_2;
		fifo_O.put(O);
	}

	private boolean isSchedulable_limit() {
		int[] I = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_I.hasTokens(1);
		if (_tmp1_1) {
			fifo_I.peek(I);
			_tmp2_1 = count;
			_tmp0_1 = _tmp2_1 >= 0;
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
		if ("I".equals(portName)) {
			fifo_I = fifo;
		} else if ("SIGNED".equals(portName)) {
			fifo_SIGNED = fifo;
		} else if ("O".equals(portName)) {
			fifo_O = fifo;
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
			if (isSchedulable_read_signed()) {
				read_signed();
				res = true;
				i++;
			} else if (isSchedulable_limit()) {
				if (fifo_O.hasRoom(1)) {
					limit();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
