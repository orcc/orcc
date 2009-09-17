/**
 * Generated from "display"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActor;
import net.sf.orcc.oj.IntFifo;

public class Actor_display implements IActor {

	private Map<String, IntFifo> fifos;

	// Input FIFOs
	private IntFifo fifo_B;
	private IntFifo fifo_WIDTH;
	private IntFifo fifo_HEIGHT;

	// Output FIFOs
	// State variables of the actor
	
	public Actor_display() {
		fifos = new HashMap<String, IntFifo>();
	}
	
	// Functions/procedures
	// Actions
	@Override
	public void initialize() {
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("B".equals(portName)) {
			fifo_B = fifo;
		} else if ("WIDTH".equals(portName)) {
			fifo_WIDTH = fifo;
		} else if ("HEIGHT".equals(portName)) {
			fifo_HEIGHT = fifo;
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
