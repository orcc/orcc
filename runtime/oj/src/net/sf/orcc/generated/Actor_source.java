/**
 * Generated from "source"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_source implements IActor {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	// Output FIFOs
	private IntFifo fifo_O;

	// State variables of the actor
	private String fname = "D:/Projects/RVC/OpenDF/models/MPEG4_SP_Decoder/data/foreman_qcif_30.bit";


	
	public Actor_source() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\orcc_fread.cal";
		actionLocation = new HashMap<String, Location>();
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
	// Initializes

	private void untagged01() {
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
		if ("O".equals(portName)) {
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
		}

		return i;
	}

}
