/**
 * Generated from "dcsplit"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_dcsplit implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;

	private String file;

	private boolean suspended;

	// Input FIFOs
	private IntFifo fifo_IN;

	// Output FIFOs
	private IntFifo fifo_DC;
	private IntFifo fifo_AC;

	// State variables of the actor
	private int SAMPLE_SZ = 13;

	private int count = 0;



	public Actor_dcsplit() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\DCSplit.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("ac", new Location(56, 2, 82)); 
		actionLocation.put("dc", new Location(49, 2, 83)); 
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
		if (isSchedulable_dc()) {
			if (fifo_DC.hasRoom(1)) {
				return "dc";
			}
		} else if (isSchedulable_ac()) {
			if (fifo_AC.hasRoom(1)) {
				return "ac";
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

	private void dc() {
		int[] DC = new int[1];
		int[] IN = new int[1];
		int x_1;

		fifo_IN.get(IN);
		x_1 = IN[0];
		count = 1;
		DC[0] = x_1;
		fifo_DC.put(DC);
	}

	private boolean isSchedulable_dc() {
		int[] IN = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_IN.hasTokens(1);
		if (_tmp1_1) {
			fifo_IN.peek(IN);
			_tmp2_1 = count;
			_tmp0_1 = _tmp2_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void ac() {
		int[] AC = new int[1];
		int[] IN = new int[1];
		int x_1;
		int _tmp0_1;

		fifo_IN.get(IN);
		x_1 = IN[0];
		_tmp0_1 = count;
		count = _tmp0_1 + 1 & 63;
		AC[0] = x_1;
		fifo_AC.put(AC);
	}

	private boolean isSchedulable_ac() {
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

	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("IN".equals(portName)) {
			fifo_IN = fifo;
		} else if ("DC".equals(portName)) {
			fifo_DC = fifo;
		} else if ("AC".equals(portName)) {
			fifo_AC = fifo;
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
			if (isSchedulable_dc()) {
				if (fifo_DC.hasRoom(1)) {
					dc();
					res = true;
					i++;
				}
			} else if (isSchedulable_ac()) {
				if (fifo_AC.hasRoom(1)) {
					ac();
					res = true;
					i++;
				}
			}
		}

		return i;
	}

}
