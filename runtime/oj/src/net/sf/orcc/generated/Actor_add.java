/**
 * Generated from "add"
 */
package net.sf.orcc.generated;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.Location;

public class Actor_add implements IActorDebug {

	private Map<String, Location> actionLocation;

	private Map<String, IntFifo> fifos;
	
	private String file;

	// Input FIFOs
	private IntFifo fifo_MOT;
	private IntFifo fifo_TEX;
	private IntFifo fifo_BTYPE;

	// Output FIFOs
	private IntFifo fifo_VID;

	// State variables of the actor
	private int PIX_SZ = 9;

	private int MB_COORD_SZ = 8;

	private int BTYPE_SZ = 12;

	private int NEWVOP = 2048;

	private int INTRA = 1024;

	private int ACCODED = 2;

	private boolean _CAL_tokenMonitor = true;

	private int count = 0;


	
	public Actor_add() {
		fifos = new HashMap<String, IntFifo>();
		file = "D:\\repositories\\mwipliez\\orcc\\trunk\\examples\\MPEG4_SP_Decoder\\Add.cal";
		actionLocation = new HashMap<String, Location>();
		actionLocation.put("cmd_motionOnly", new Location(76, 2, 87)); 
		actionLocation.put("cmd_newVop", new Location(62, 2, 83)); 
		actionLocation.put("cmd_other", new Location(83, 2, 43)); 
		actionLocation.put("cmd_textureOnly", new Location(70, 2, 87)); 
		actionLocation.put("combine", new Location(103, 2, 187)); 
		actionLocation.put("done", new Location(86, 2, 68)); 
		actionLocation.put("motion", new Location(98, 2, 77)); 
		actionLocation.put("texture", new Location(93, 2, 80)); 
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

	private void cmd_newVop() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
	}

	private boolean isSchedulable_cmd_newVop() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp0_1 = (cmd_1 & 2048) != 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void cmd_textureOnly() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
	}

	private boolean isSchedulable_cmd_textureOnly() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp0_1 = (cmd_1 & 1024) != 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void cmd_motionOnly() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
	}

	private boolean isSchedulable_cmd_motionOnly() {
		int[] BTYPE = new int[1];
		boolean _tmp1_1;
		int cmd_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			fifo_BTYPE.peek(BTYPE);
			cmd_1 = BTYPE[0];
			_tmp0_1 = (cmd_1 & 2) == 0;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void cmd_other() {
		int[] BTYPE = new int[1];

		fifo_BTYPE.get(BTYPE);
	}

	private boolean isSchedulable_cmd_other() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_BTYPE.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void done() {
		count = 0;
	}

	private boolean isSchedulable_done() {
		int _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		if (true) {
			_tmp1_1 = count;
			_tmp0_1 = _tmp1_1 == 64;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void texture() {
		int[] VID = new int[1];
		int[] TEX = new int[1];
		int tex_1;

		fifo_TEX.get(TEX);
		tex_1 = TEX[0];
		count++;
		VID[0] = tex_1;
		fifo_VID.put(VID);
	}

	private boolean isSchedulable_texture() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_TEX.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void motion() {
		int[] VID = new int[1];
		int[] MOT = new int[1];
		int mot_1;

		fifo_MOT.get(MOT);
		mot_1 = MOT[0];
		count++;
		VID[0] = mot_1;
		fifo_VID.put(VID);
	}

	private boolean isSchedulable_motion() {
		boolean _tmp1_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_MOT.hasTokens(1);
		if (_tmp1_1) {
			_tmp0_1 = true;
			_tmp0_2 = _tmp0_1;
		} else {
			_tmp0_3 = false;
			_tmp0_2 = _tmp0_3;
		}
		return _tmp0_2;
	}

	private void combine() {
		int[] VID = new int[1];
		int[] MOT = new int[1];
		int[] TEX = new int[1];
		int mot_1;
		int tex_1;
		int s0_1;
		int _tmp1_1;
		int _tmp1_2;
		int _tmp1_3;
		int _tmp1_4;
		int _tmp1_5;

		fifo_MOT.get(MOT);
		mot_1 = MOT[0];
		fifo_TEX.get(TEX);
		tex_1 = TEX[0];
		s0_1 = tex_1 + mot_1;
		count++;
		if (s0_1 < 0) {
			_tmp1_1 = 0;
			_tmp1_2 = _tmp1_1;
		} else {
			if (s0_1 > 255) {
				_tmp1_3 = 255;
				_tmp1_4 = _tmp1_3;
			} else {
				_tmp1_5 = s0_1;
				_tmp1_4 = _tmp1_5;
			}
			_tmp1_2 = _tmp1_4;
		}
		VID[0] = _tmp1_2;
		fifo_VID.put(VID);
	}

	private boolean isSchedulable_combine() {
		boolean _tmp1_1;
		boolean _tmp2_1;
		boolean _tmp0_1;
		boolean _tmp0_2;
		boolean _tmp0_3;

		_tmp1_1 = fifo_MOT.hasTokens(1);
		_tmp2_1 = fifo_TEX.hasTokens(1);
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
		if ("MOT".equals(portName)) {
			fifo_MOT = fifo;
		} else if ("TEX".equals(portName)) {
			fifo_TEX = fifo;
		} else if ("BTYPE".equals(portName)) {
			fifo_BTYPE = fifo;
		} else if ("VID".equals(portName)) {
			fifo_VID = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	// Action scheduler
	private enum States {
		s_cmd,
		s_combine,
		s_motion,
		s_skiph,
		s_skipw,
		s_texture
	};

	private States _FSM_state = States.s_cmd;

	private boolean cmd_state_scheduler() {
		boolean res = false;
		if (isSchedulable_cmd_newVop()) {
			cmd_newVop();
			_FSM_state = States.s_skipw;
			res = true;
		} else if (isSchedulable_cmd_textureOnly()) {
			cmd_textureOnly();
			_FSM_state = States.s_texture;
			res = true;
		} else if (isSchedulable_cmd_motionOnly()) {
			cmd_motionOnly();
			_FSM_state = States.s_motion;
			res = true;
		} else if (isSchedulable_cmd_other()) {
			cmd_other();
			_FSM_state = States.s_combine;
			res = true;
		}
		return res;
	}

	private boolean combine_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_cmd;
			res = true;
		} else if (isSchedulable_combine()) {
			if (fifo_VID.hasRoom(1)) {
				combine();
				_FSM_state = States.s_combine;
				res = true;
			}
		}
		return res;
	}

	private boolean motion_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_cmd;
			res = true;
		} else if (isSchedulable_motion()) {
			if (fifo_VID.hasRoom(1)) {
				motion();
				_FSM_state = States.s_motion;
				res = true;
			}
		}
		return res;
	}

	private boolean skiph_state_scheduler() {
		boolean res = false;
		if (isSchedulable_cmd_other()) {
			cmd_other();
			_FSM_state = States.s_cmd;
			res = true;
		}
		return res;
	}

	private boolean skipw_state_scheduler() {
		boolean res = false;
		if (isSchedulable_cmd_other()) {
			cmd_other();
			_FSM_state = States.s_skiph;
			res = true;
		}
		return res;
	}

	private boolean texture_state_scheduler() {
		boolean res = false;
		if (isSchedulable_done()) {
			done();
			_FSM_state = States.s_cmd;
			res = true;
		} else if (isSchedulable_texture()) {
			if (fifo_VID.hasRoom(1)) {
				texture();
				_FSM_state = States.s_texture;
				res = true;
			}
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
			case s_cmd:
				res = cmd_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_combine:
				res = combine_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_motion:
				res = motion_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_skiph:
				res = skiph_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_skipw:
				res = skipw_state_scheduler();
				if (res) {
					i++;
				}
				break;
			case s_texture:
				res = texture_state_scheduler();
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
