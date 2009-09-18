/**
 * Generated from "ddr"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_ddr implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_RA;
	private IntFifo fifo_WA;
	private IntFifo fifo_WD;

	// Output FIFOs
	private IntFifo fifo_RD;

	// State variables of the actor
	private int MAXW_IN_MB = 121;

	private int MAXH_IN_MB = 69;

	private int COMPONENTS = 6;

	private int BLOCKSIZE = 64;

	private int PIXELS_PER_WORD = 4;

	private int FRAMEBITS = 1;

	private int COMPBITS = 3;

	private int YBITS = 5;

	private int XBITS = 6;

	private int BLOCKBITS = 4;

	private int MEMBITS = 19;

	private int MEMSIZE = 524268;

	private int BURSTSIZE = 96;

	private int[] buf = new int[524268];

	private int address_t = 0;

	private int burstSize = 0;

	private boolean preferRead = true;


	
	public Actor_ddr() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\DDRModel.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("data_done", new Location(162, 2, 53)); 
		actionLocation.put("data_read", new Location(139, 2, 196)); 
		actionLocation.put("data_write", new Location(150, 2, 218)); 
		actionLocation.put("select_read_low", new Location(121, 2, 192)); 
		actionLocation.put("select_read_prefer", new Location(103, 2, 196)); 
		actionLocation.put("select_write_low", new Location(130, 2, 189)); 
		actionLocation.put("select_write_prefer", new Location(112, 2, 200)); 
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

	private void select_read_prefer() {
		int[] RA = new int[1];
		int a_1;

		fifo_RA.get(RA);
		a_1 = RA[0];
		address_t = a_1;
		burstSize = 96;
		preferRead = false;
	}

	private boolean isSchedulable_select_read_prefer() {
		int[] RA = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_RA.hasTokens(1);
		if (_tmp1_1) {
			fifo_RA.peek(RA);
			_tmp2_1 = preferRead;
			_tmp0_1 = _tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void select_write_prefer() {
		int[] WA = new int[1];
		int a_1;

		fifo_WA.get(WA);
		a_1 = WA[0];
		address_t = a_1;
		burstSize = 96;
		preferRead = true;
	}

	private boolean isSchedulable_select_write_prefer() {
		int[] WA = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_WA.hasTokens(1);
		if (_tmp1_1) {
			fifo_WA.peek(WA);
			_tmp2_1 = preferRead;
			_tmp0_1 = !_tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void select_read_low() {
		int[] RA = new int[1];
		int a_1;

		fifo_RA.get(RA);
		a_1 = RA[0];
		address_t = a_1;
		burstSize = 96;
		preferRead = false;
	}

	private boolean isSchedulable_select_read_low() {
		int[] RA = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_RA.hasTokens(1);
		if (_tmp1_1) {
			fifo_RA.peek(RA);
			_tmp2_1 = preferRead;
			_tmp0_1 = !_tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void select_write_low() {
		int[] WA = new int[1];
		int a_1;

		fifo_WA.get(WA);
		a_1 = WA[0];
		address_t = a_1;
		burstSize = 96;
		preferRead = true;
	}

	private boolean isSchedulable_select_write_low() {
		int[] WA = new int[1];
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_WA.hasTokens(1);
		if (_tmp1_1) {
			fifo_WA.peek(WA);
			_tmp2_1 = preferRead;
			_tmp0_1 = _tmp2_1;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void data_read() {
		int[] RD = new int[1];
		int _tmp0_1;
		int _tmp1_1;
		int dat0_1;

		_tmp0_1 = address_t;
		_tmp1_1 = buf[_tmp0_1];
		dat0_1 = _tmp1_1;
		address_t++;
		burstSize--;
		RD[0] = dat0_1;
		fifo_RD.put(RD);
	}

	private boolean isSchedulable_data_read() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = burstSize;
			_tmp0_1 = _tmp1_1 > 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void data_write() {
		int[] WD = new int[1];
		int dat_1;
		int _tmp0_1;
		int wa_t0_1;

		fifo_WD.get(WD);
		dat_1 = WD[0];
		_tmp0_1 = address_t;
		wa_t0_1 = _tmp0_1;
		burstSize--;
		address_t++;
		buf[wa_t0_1] = dat_1;
	}

	private boolean isSchedulable_data_write() {
		int[] WD = new int[1];
		boolean _tmp1_1;
		int _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_WD.hasTokens(1);
		if (_tmp1_1) {
			fifo_WD.peek(WD);
			_tmp2_1 = burstSize;
			_tmp0_1 = _tmp2_1 > 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void data_done() {
	}

	private boolean isSchedulable_data_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = burstSize;
			_tmp0_1 = _tmp1_1 == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	// Initializes

	private void untagged01() {
		int i0_1;
		int i0_2;
		int i0_3;

		i0_1 = 1;
		i0_3 = i0_1;
		while (i0_3 < 524269) {
			buf[i0_3 - 1] = 0;
			i0_2 = i0_3 + 1;
			i0_3 = i0_2;
		}
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
		if ("RA".equals(portName)) {
			fifo_RA = fifo;
		} else if ("WA".equals(portName)) {
			fifo_WA = fifo;
		} else if ("WD".equals(portName)) {
			fifo_WD = fifo;
		} else if ("RD".equals(portName)) {
			fifo_RD = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_doDataRead,
		s_doDataWrite,
		s_getAddr
	};

	private States _FSM_state = States.s_getAddr;

	private boolean doDataRead_state_scheduler() {
		boolean res = false;
		if (isSchedulable_data_read()) {
			if (fifo_RD.hasRoom(1)) {
				data_read();
				_FSM_state = States.s_doDataRead;
				res = true;
			}
		} else if (isSchedulable_data_done()) {
			data_done();
			_FSM_state = States.s_getAddr;
			res = true;
		}
		return res;
	}

	private boolean doDataWrite_state_scheduler() {
		boolean res = false;
		if (isSchedulable_data_write()) {
			data_write();
			_FSM_state = States.s_doDataWrite;
			res = true;
		} else if (isSchedulable_data_done()) {
			data_done();
			_FSM_state = States.s_getAddr;
			res = true;
		}
		return res;
	}

	private boolean getAddr_state_scheduler() {
		boolean res = false;
		if (isSchedulable_select_read_prefer()) {
			select_read_prefer();
			_FSM_state = States.s_doDataRead;
			res = true;
		} else if (isSchedulable_select_write_prefer()) {
			select_write_prefer();
			_FSM_state = States.s_doDataWrite;
			res = true;
		} else if (isSchedulable_select_read_low()) {
			select_read_low();
			_FSM_state = States.s_doDataRead;
			res = true;
		} else if (isSchedulable_select_write_low()) {
			select_write_low();
			_FSM_state = States.s_doDataWrite;
			res = true;
		}
		return res;
	}

	@Override
	public int schedule() {
		boolean res = true;
		int i = 0;

		while (res) {
			res = false;
			switch (_FSM_state) {
			case s_doDataRead:
				res = doDataRead_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_doDataWrite:
				res = doDataWrite_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_getAddr:
				res = getAddr_state_scheduler();
				if (res) {
					i++;
				}
				break;

			default:
				System.out.println("unknown state: %s\n" + _FSM_state);
				break;
			}
		}

		return i;
	}

}
