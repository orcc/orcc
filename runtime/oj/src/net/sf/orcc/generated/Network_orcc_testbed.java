/**
 * Generated from "orcc_testbed"
 * 
 * DEBUG version.
 */
package net.sf.orcc.generated;

import java.io.IOException;

import net.sf.orcc.oj.Actor_display;
import net.sf.orcc.oj.Actor_source;
import net.sf.orcc.oj.Broadcast;
import net.sf.orcc.oj.CLIParameters;
import net.sf.orcc.oj.FifoManager;
import net.sf.orcc.oj.IActorDebug;
import net.sf.orcc.oj.ISchedulerDebug;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.InterpreterThread;

public class Network_orcc_testbed implements ISchedulerDebug {

	public static final int SIZE = 10000;

	// FIFO declarations
	private IntFifo fifo_0;
	private IntFifo fifo_1;
	private IntFifo fifo_2;
	private IntFifo fifo_3;
	private IntFifo fifo_4;
	private IntFifo fifo_5;
	private IntFifo fifo_6;
	private IntFifo fifo_7;
	private IntFifo fifo_8;
	private IntFifo fifo_9;
	private IntFifo fifo_10;
	private IntFifo fifo_11;
	private IntFifo fifo_12;
	private IntFifo fifo_13;
	private IntFifo fifo_14;
	private IntFifo fifo_15;
	private IntFifo fifo_16;
	private IntFifo fifo_17;
	private IntFifo fifo_18;
	private IntFifo fifo_19;
	private IntFifo fifo_20;
	private IntFifo fifo_21;
	private IntFifo fifo_22;
	private IntFifo fifo_23;
	private IntFifo fifo_24;
	private IntFifo fifo_25;
	private IntFifo fifo_26;
	private IntFifo fifo_27;
	private IntFifo fifo_28;
	private IntFifo fifo_29;
	private IntFifo fifo_30;
	private IntFifo fifo_31;
	private IntFifo fifo_32;
	private IntFifo fifo_33;
	private IntFifo fifo_34;
	private IntFifo fifo_35;
	private IntFifo fifo_36;
	private IntFifo fifo_37;
	private IntFifo fifo_38;
	private IntFifo fifo_39;
	private IntFifo fifo_40;
	private IntFifo fifo_41;
	private IntFifo fifo_42;
	private IntFifo fifo_43;
	private IntFifo fifo_44;
	private IntFifo fifo_45;
	private IntFifo fifo_46;
	private IntFifo fifo_47;
	private IntFifo fifo_48;
	private IntFifo fifo_49;
	private IntFifo fifo_50;
	private IntFifo fifo_51;
	private IntFifo fifo_52;
	private IntFifo fifo_53;
	private IntFifo fifo_54;
	private IntFifo fifo_55;
	private IntFifo fifo_56;
	private IntFifo fifo_57;
	private IntFifo fifo_58;
	private IntFifo fifo_59;
	private IntFifo fifo_60;
	private IntFifo fifo_61;
	private IntFifo fifo_62;
	private IntFifo fifo_63;
	private IntFifo fifo_64;
	private IntFifo fifo_65;
	private IntFifo fifo_66;
	private IntFifo fifo_67;
	private IntFifo fifo_68;
	private IntFifo fifo_69;
	private IntFifo fifo_70;
	private IntFifo fifo_71;
	private IntFifo fifo_72;
	private IntFifo fifo_73;
	private IntFifo fifo_74;
	private IntFifo fifo_75;
	private IntFifo fifo_76;
	private IntFifo fifo_77;
	private IntFifo fifo_78;
	private IntFifo fifo_79;
	private IntFifo fifo_80;
	private IntFifo fifo_81;
	private IntFifo fifo_82;
	private IntFifo fifo_83;
	private IntFifo fifo_84;
	private IntFifo fifo_85;
	private IntFifo fifo_86;
	private IntFifo fifo_87;
	private IntFifo fifo_88;

	// Actors
	private IActorDebug actor_acpred;
	private IActorDebug actor_add;
	private IActorDebug actor_blkexp;
	private IActorDebug actor_clip;
	private IActorDebug actor_combine;
	private IActorDebug actor_dcpred;
	private IActorDebug actor_dcsplit;
	private IActorDebug actor_ddr;
	private IActorDebug actor_dequant;
	private IActorDebug actor_display;
	private IActorDebug actor_downsample;
	private IActorDebug actor_fairmerge;
	private IActorDebug actor_final;
	private IActorDebug actor_interpolate;
	private IActorDebug actor_mbpack;
	private IActorDebug actor_memorymanager;
	private IActorDebug actor_mvrecon;
	private IActorDebug actor_mvseq;
	private IActorDebug actor_parseheaders;
	private IActorDebug actor_retrans;
	private IActorDebug actor_rowsort;
	private IActorDebug actor_scale;
	private IActorDebug actor_searchwin;
	private IActorDebug actor_sep;
	private IActorDebug actor_seq;
	private IActorDebug actor_serialize;
	private IActorDebug actor_shuffle;
	private IActorDebug actor_shufflefly;
	private IActorDebug actor_source;
	private IActorDebug actor_trans;
	private IActorDebug actor_unpack;
	private IActorDebug actor_zigzag;
	private IActorDebug actor_zzaddr;

	// suspended indicators
	public boolean isSuspended_acpred;
	public boolean isSuspended_add;
	public boolean isSuspended_blkexp;
	public boolean isSuspended_clip;
	public boolean isSuspended_combine;
	public boolean isSuspended_dcpred;
	public boolean isSuspended_dcsplit;
	public boolean isSuspended_ddr;
	public boolean isSuspended_dequant;
	public boolean isSuspended_display;
	public boolean isSuspended_downsample;
	public boolean isSuspended_fairmerge;
	public boolean isSuspended_final;
	public boolean isSuspended_interpolate;
	public boolean isSuspended_mbpack;
	public boolean isSuspended_memorymanager;
	public boolean isSuspended_mvrecon;
	public boolean isSuspended_mvseq;
	public boolean isSuspended_parseheaders;
	public boolean isSuspended_retrans;
	public boolean isSuspended_rowsort;
	public boolean isSuspended_scale;
	public boolean isSuspended_searchwin;
	public boolean isSuspended_sep;
	public boolean isSuspended_seq;
	public boolean isSuspended_serialize;
	public boolean isSuspended_shuffle;
	public boolean isSuspended_shufflefly;
	public boolean isSuspended_source;
	public boolean isSuspended_trans;
	public boolean isSuspended_unpack;
	public boolean isSuspended_zigzag;
	public boolean isSuspended_zzaddr;

	// Broadcasts
	private Broadcast actor_broadcast_add_VID;
	private Broadcast actor_broadcast_dcpred_START;
	private Broadcast actor_broadcast_fairmerge_ROWOUT;
	private Broadcast actor_broadcast_mvrecon_MV;
	private Broadcast actor_broadcast_parseheaders_BTYPE;
	
	public Network_orcc_testbed(int cmdPort, int eventPort, String[] args)
			throws IOException {
		new InterpreterThread(cmdPort, eventPort, this).start();

		CLIParameters.getInstance().setArguments(args);
		initialize();
		schedule();
		Actor_display.closeDisplay();
	}
	
	@Override
	public String[] getActors() {
		return new String[] {
			"acpred", "add", "blkexp", "clip", "combine", "dcpred", "dcsplit", "ddr", "dequant", 
			"display", "downsample", "fairmerge", "final", "interpolate", "mbpack", "memorymanager", 
			"mvrecon", "mvseq", "parseheaders", "retrans", "rowsort", "scale", "searchwin", 
			"sep", "seq", "serialize", "shuffle", "shufflefly", "source", "trans", "unpack", 
			"zigzag", "zzaddr"
		};
	}

	@Override
	public void resume(String actorName) {
		try {
			getClass().getField("isSuspended_" + actorName).setBoolean(this,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void suspend(String actorName) {
		try {
			getClass().getField("isSuspended_" + actorName).setBoolean(this,
					true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() {
		actor_acpred = new Actor_acpred();
		actor_add = new Actor_add();
		actor_blkexp = new Actor_blkexp();
		actor_clip = new Actor_clip();
		actor_combine = new Actor_combine();
		actor_dcpred = new Actor_dcpred();
		actor_dcsplit = new Actor_dcsplit();
		actor_ddr = new Actor_ddr();
		actor_dequant = new Actor_dequant();
		actor_display = new Actor_display();
		actor_downsample = new Actor_downsample();
		actor_fairmerge = new Actor_fairmerge();
		actor_final = new Actor_final();
		actor_interpolate = new Actor_interpolate();
		actor_mbpack = new Actor_mbpack();
		actor_memorymanager = new Actor_memorymanager();
		actor_mvrecon = new Actor_mvrecon();
		actor_mvseq = new Actor_mvseq();
		actor_parseheaders = new Actor_parseheaders();
		actor_retrans = new Actor_retrans();
		actor_rowsort = new Actor_rowsort();
		actor_scale = new Actor_scale();
		actor_searchwin = new Actor_searchwin();
		actor_sep = new Actor_sep();
		actor_seq = new Actor_seq();
		actor_serialize = new Actor_serialize();
		actor_shuffle = new Actor_shuffle();
		actor_shufflefly = new Actor_shufflefly();
		actor_source = new Actor_source();
		actor_trans = new Actor_trans();
		actor_unpack = new Actor_unpack();
		actor_zigzag = new Actor_zigzag();
		actor_zzaddr = new Actor_zzaddr();

		actor_broadcast_add_VID = new Broadcast(2);
		actor_broadcast_dcpred_START = new Broadcast(3);
		actor_broadcast_fairmerge_ROWOUT = new Broadcast(2);
		actor_broadcast_mvrecon_MV = new Broadcast(2);
		actor_broadcast_parseheaders_BTYPE = new Broadcast(8);

		fifo_0 = new IntFifo(SIZE);
		fifo_1 = new IntFifo(SIZE);
		fifo_2 = new IntFifo(SIZE);
		fifo_3 = new IntFifo(SIZE);
		fifo_4 = new IntFifo(SIZE);
		fifo_5 = new IntFifo(SIZE);
		fifo_6 = new IntFifo(SIZE);
		fifo_7 = new IntFifo(384);
		fifo_8 = new IntFifo(SIZE);
		fifo_9 = new IntFifo(SIZE);
		fifo_10 = new IntFifo(6);
		fifo_11 = new IntFifo(6);
		fifo_12 = new IntFifo(6);
		fifo_13 = new IntFifo(SIZE);
		fifo_14 = new IntFifo(SIZE);
		fifo_15 = new IntFifo(SIZE);
		fifo_16 = new IntFifo(SIZE);
		fifo_17 = new IntFifo(SIZE);
		fifo_18 = new IntFifo(SIZE);
		fifo_19 = new IntFifo(SIZE);
		fifo_20 = new IntFifo(SIZE);
		fifo_21 = new IntFifo(384);
		fifo_22 = new IntFifo(384);
		fifo_23 = new IntFifo(384);
		fifo_24 = new IntFifo(SIZE);
		fifo_25 = new IntFifo(SIZE);
		fifo_26 = new IntFifo(SIZE);
		fifo_27 = new IntFifo(SIZE);
		fifo_28 = new IntFifo(1);
		fifo_29 = new IntFifo(1);
		fifo_30 = new IntFifo(SIZE);
		fifo_31 = new IntFifo(SIZE);
		fifo_32 = new IntFifo(SIZE);
		fifo_33 = new IntFifo(SIZE);
		fifo_34 = new IntFifo(SIZE);
		fifo_35 = new IntFifo(SIZE);
		fifo_36 = new IntFifo(SIZE);
		fifo_37 = new IntFifo(SIZE);
		fifo_38 = new IntFifo(SIZE);
		fifo_39 = new IntFifo(9);
		fifo_40 = new IntFifo(3);
		fifo_41 = new IntFifo(SIZE);
		fifo_42 = new IntFifo(SIZE);
		fifo_43 = new IntFifo(486);
		fifo_44 = new IntFifo(SIZE);
		fifo_45 = new IntFifo(SIZE);
		fifo_46 = new IntFifo(SIZE);
		fifo_47 = new IntFifo(SIZE);
		fifo_48 = new IntFifo(378);
		fifo_49 = new IntFifo(SIZE);
		fifo_50 = new IntFifo(SIZE);
		fifo_51 = new IntFifo(SIZE);
		fifo_52 = new IntFifo(SIZE);
		fifo_53 = new IntFifo(SIZE);
		fifo_54 = new IntFifo(SIZE);
		fifo_55 = new IntFifo(SIZE);
		fifo_56 = new IntFifo(SIZE);
		fifo_57 = new IntFifo(SIZE);
		fifo_58 = new IntFifo(SIZE);
		fifo_59 = new IntFifo(SIZE);
		fifo_60 = new IntFifo(SIZE);
		fifo_61 = new IntFifo(SIZE);
		fifo_62 = new IntFifo(SIZE);
		fifo_63 = new IntFifo(SIZE);
		fifo_64 = new IntFifo(SIZE);
		fifo_65 = new IntFifo(SIZE);
		fifo_66 = new IntFifo(SIZE);
		fifo_67 = new IntFifo(SIZE);
		fifo_68 = new IntFifo(SIZE);
		fifo_69 = new IntFifo(SIZE);
		fifo_70 = new IntFifo(SIZE);
		fifo_71 = new IntFifo(SIZE);
		fifo_72 = new IntFifo(SIZE);
		fifo_73 = new IntFifo(SIZE);
		fifo_74 = new IntFifo(SIZE);
		fifo_75 = new IntFifo(SIZE);
		fifo_76 = new IntFifo(SIZE);
		fifo_77 = new IntFifo(SIZE);
		fifo_78 = new IntFifo(SIZE);
		fifo_79 = new IntFifo(SIZE);
		fifo_80 = new IntFifo(SIZE);
		fifo_81 = new IntFifo(SIZE);
		fifo_82 = new IntFifo(SIZE);
		fifo_83 = new IntFifo(384);
		fifo_84 = new IntFifo(384);
		fifo_85 = new IntFifo(384);
		fifo_86 = new IntFifo(SIZE);
		fifo_87 = new IntFifo(SIZE);
		fifo_88 = new IntFifo(SIZE);

		actor_shuffle.setFifo("Y0", fifo_0);
		actor_final.setFifo("X0", fifo_0);
		actor_shuffle.setFifo("Y1", fifo_1);
		actor_final.setFifo("X1", fifo_1);
		actor_shuffle.setFifo("Y2", fifo_2);
		actor_final.setFifo("X2", fifo_2);
		actor_shuffle.setFifo("Y3", fifo_3);
		actor_final.setFifo("X3", fifo_3);
		actor_mvseq.setFifo("A", fifo_4);
		actor_mvrecon.setFifo("A", fifo_4);
		actor_clip.setFifo("O", fifo_5);
		actor_add.setFifo("TEX", fifo_5);
		actor_dcsplit.setFifo("DC", fifo_6);
		actor_dcpred.setFifo("IN", fifo_6);
		actor_dcsplit.setFifo("AC", fifo_7);
		actor_zigzag.setFifo("AC", fifo_7);
		actor_blkexp.setFifo("OUT", fifo_8);
		actor_dcsplit.setFifo("IN", fifo_8);
		actor_dcpred.setFifo("PTR", fifo_9);
		actor_acpred.setFifo("PTR", fifo_9);
		actor_dcpred.setFifo("SIGNED", fifo_10);
		actor_clip.setFifo("SIGNED", fifo_10);
		actor_dcpred.setFifo("OUT", fifo_11);
		actor_dequant.setFifo("DC", fifo_11);
		actor_dcpred.setFifo("QUANT", fifo_12);
		actor_dequant.setFifo("QP", fifo_12);
		actor_combine.setFifo("Y0", fifo_13);
		actor_shufflefly.setFifo("X0", fifo_13);
		actor_combine.setFifo("Y1", fifo_14);
		actor_shufflefly.setFifo("X1", fifo_14);
		actor_mbpack.setFifo("AO", fifo_15);
		actor_ddr.setFifo("WA", fifo_15);
		actor_mbpack.setFifo("DO", fifo_16);
		actor_ddr.setFifo("WD", fifo_16);
		actor_shufflefly.setFifo("Y0", fifo_17);
		actor_shuffle.setFifo("X0", fifo_17);
		actor_shufflefly.setFifo("Y1", fifo_18);
		actor_shuffle.setFifo("X1", fifo_18);
		actor_shufflefly.setFifo("Y2", fifo_19);
		actor_shuffle.setFifo("X2", fifo_19);
		actor_shufflefly.setFifo("Y3", fifo_20);
		actor_shuffle.setFifo("X3", fifo_20);
		actor_dequant.setFifo("OUT", fifo_21);
		actor_rowsort.setFifo("ROW", fifo_21);
		actor_interpolate.setFifo("MOT", fifo_22);
		actor_add.setFifo("MOT", fifo_22);
		actor_zigzag.setFifo("OUT", fifo_23);
		actor_acpred.setFifo("AC", fifo_23);
		actor_serialize.setFifo("out", fifo_24);
		actor_parseheaders.setFifo("bits", fifo_24);
		actor_parseheaders.setFifo("LAST", fifo_25);
		actor_blkexp.setFifo("LAST", fifo_25);
		actor_parseheaders.setFifo("RUN", fifo_26);
		actor_blkexp.setFifo("RUN", fifo_26);
		actor_parseheaders.setFifo("VALUE", fifo_27);
		actor_blkexp.setFifo("VALUE", fifo_27);
		actor_parseheaders.setFifo("HEIGHT", fifo_28);
		actor_display.setFifo("HEIGHT", fifo_28);
		actor_parseheaders.setFifo("WIDTH", fifo_29);
		actor_display.setFifo("WIDTH", fifo_29);
		actor_parseheaders.setFifo("MV", fifo_30);
		actor_mvrecon.setFifo("MVIN", fifo_30);
		actor_source.setFifo("O", fifo_31);
		actor_serialize.setFifo("in8", fifo_31);
		actor_retrans.setFifo("Y", fifo_32);
		actor_clip.setFifo("I", fifo_32);
		actor_searchwin.setFifo("FLAGS", fifo_33);
		actor_interpolate.setFifo("halfpel", fifo_33);
		actor_searchwin.setFifo("DO", fifo_34);
		actor_unpack.setFifo("DI", fifo_34);
		actor_rowsort.setFifo("Y0", fifo_35);
		actor_fairmerge.setFifo("R0", fifo_35);
		actor_rowsort.setFifo("Y1", fifo_36);
		actor_fairmerge.setFifo("R1", fifo_36);
		actor_trans.setFifo("Y0", fifo_37);
		actor_fairmerge.setFifo("C0", fifo_37);
		actor_trans.setFifo("Y1", fifo_38);
		actor_fairmerge.setFifo("C1", fifo_38);
		actor_memorymanager.setFifo("RA", fifo_39);
		actor_ddr.setFifo("RA", fifo_39);
		actor_memorymanager.setFifo("WA", fifo_40);
		actor_mbpack.setFifo("AI", fifo_40);
		actor_downsample.setFifo("R2", fifo_41);
		actor_sep.setFifo("ROW", fifo_41);
		actor_zzaddr.setFifo("ADDR", fifo_42);
		actor_zigzag.setFifo("ADDR", fifo_42);
		actor_unpack.setFifo("DO", fifo_43);
		actor_interpolate.setFifo("RD", fifo_43);
		actor_final.setFifo("Y0", fifo_44);
		actor_sep.setFifo("X0", fifo_44);
		actor_final.setFifo("Y1", fifo_45);
		actor_sep.setFifo("X1", fifo_45);
		actor_final.setFifo("Y2", fifo_46);
		actor_sep.setFifo("X2", fifo_46);
		actor_final.setFifo("Y3", fifo_47);
		actor_sep.setFifo("X3", fifo_47);
		actor_acpred.setFifo("OUT", fifo_48);
		actor_dequant.setFifo("AC", fifo_48);
		actor_fairmerge.setFifo("Y0", fifo_49);
		actor_scale.setFifo("X0", fifo_49);
		actor_fairmerge.setFifo("Y1", fifo_50);
		actor_scale.setFifo("X1", fifo_50);
		actor_scale.setFifo("Y0", fifo_51);
		actor_combine.setFifo("X0", fifo_51);
		actor_scale.setFifo("Y1", fifo_52);
		actor_combine.setFifo("X1", fifo_52);
		actor_scale.setFifo("Y2", fifo_53);
		actor_combine.setFifo("X2", fifo_53);
		actor_scale.setFifo("Y3", fifo_54);
		actor_combine.setFifo("X3", fifo_54);
		actor_sep.setFifo("C0", fifo_55);
		actor_retrans.setFifo("X0", fifo_55);
		actor_sep.setFifo("C1", fifo_56);
		actor_retrans.setFifo("X1", fifo_56);
		actor_sep.setFifo("C2", fifo_57);
		actor_retrans.setFifo("X2", fifo_57);
		actor_sep.setFifo("C3", fifo_58);
		actor_retrans.setFifo("X3", fifo_58);
		actor_sep.setFifo("R0", fifo_59);
		actor_trans.setFifo("X0", fifo_59);
		actor_sep.setFifo("R1", fifo_60);
		actor_trans.setFifo("X1", fifo_60);
		actor_sep.setFifo("R2", fifo_61);
		actor_trans.setFifo("X2", fifo_61);
		actor_sep.setFifo("R3", fifo_62);
		actor_trans.setFifo("X3", fifo_62);
		actor_ddr.setFifo("RD", fifo_63);
		actor_searchwin.setFifo("DI", fifo_63);
		actor_seq.setFifo("A", fifo_64);
		actor_dcpred.setFifo("A", fifo_64);
		actor_seq.setFifo("B", fifo_65);
		actor_dcpred.setFifo("B", fifo_65);
		actor_seq.setFifo("C", fifo_66);
		actor_dcpred.setFifo("C", fifo_66);
		actor_parseheaders.setFifo("BTYPE", fifo_67);
		actor_broadcast_parseheaders_BTYPE.setFifo("input", fifo_67);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_0", fifo_68);
		actor_memorymanager.setFifo("BTYPE", fifo_68);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_1", fifo_69);
		actor_add.setFifo("BTYPE", fifo_69);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_2", fifo_70);
		actor_mvseq.setFifo("BTYPE", fifo_70);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_3", fifo_71);
		actor_unpack.setFifo("BTYPE", fifo_71);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_4", fifo_72);
		actor_seq.setFifo("BTYPE", fifo_72);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_5", fifo_73);
		actor_dcpred.setFifo("BTYPE", fifo_73);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_6", fifo_74);
		actor_searchwin.setFifo("BTYPE", fifo_74);
		actor_broadcast_parseheaders_BTYPE.setFifo("output_7", fifo_75);
		actor_mvrecon.setFifo("BTYPE", fifo_75);
		actor_mvrecon.setFifo("MV", fifo_76);
		actor_broadcast_mvrecon_MV.setFifo("input", fifo_76);
		actor_broadcast_mvrecon_MV.setFifo("output_0", fifo_77);
		actor_unpack.setFifo("MV", fifo_77);
		actor_broadcast_mvrecon_MV.setFifo("output_1", fifo_78);
		actor_searchwin.setFifo("MV", fifo_78);
		actor_dcpred.setFifo("START", fifo_79);
		actor_broadcast_dcpred_START.setFifo("input", fifo_79);
		actor_broadcast_dcpred_START.setFifo("output_0", fifo_80);
		actor_zigzag.setFifo("START", fifo_80);
		actor_broadcast_dcpred_START.setFifo("output_1", fifo_81);
		actor_zzaddr.setFifo("START", fifo_81);
		actor_broadcast_dcpred_START.setFifo("output_2", fifo_82);
		actor_acpred.setFifo("START", fifo_82);
		actor_add.setFifo("VID", fifo_83);
		actor_broadcast_add_VID.setFifo("input", fifo_83);
		actor_broadcast_add_VID.setFifo("output_0", fifo_84);
		actor_mbpack.setFifo("DI", fifo_84);
		actor_broadcast_add_VID.setFifo("output_1", fifo_85);
		actor_display.setFifo("B", fifo_85);
		actor_fairmerge.setFifo("ROWOUT", fifo_86);
		actor_broadcast_fairmerge_ROWOUT.setFifo("input", fifo_86);
		actor_broadcast_fairmerge_ROWOUT.setFifo("output_0", fifo_87);
		actor_combine.setFifo("ROW", fifo_87);
		actor_broadcast_fairmerge_ROWOUT.setFifo("output_1", fifo_88);
		actor_downsample.setFifo("R", fifo_88);

	}

	@Override
	public void schedule() {
		actor_acpred.initialize();
		actor_dcpred.initialize();
		actor_ddr.initialize();
		actor_mvrecon.initialize();
		actor_retrans.initialize();
		actor_searchwin.initialize();
		actor_seq.initialize();
		actor_source.initialize();
		actor_trans.initialize();
		actor_zigzag.initialize();

		int i = 1;
		while (true) {
			i = 0;
			if (!isSuspended_acpred) {
				i += actor_acpred.schedule();
			}
			if (!isSuspended_add) {
				i += actor_add.schedule();
			}
			if (!isSuspended_blkexp) {
				i += actor_blkexp.schedule();
			}
			if (!isSuspended_clip) {
				i += actor_clip.schedule();
			}
			if (!isSuspended_combine) {
				i += actor_combine.schedule();
			}
			if (!isSuspended_dcpred) {
				i += actor_dcpred.schedule();
			}
			if (!isSuspended_dcsplit) {
				i += actor_dcsplit.schedule();
			}
			if (!isSuspended_ddr) {
				i += actor_ddr.schedule();
			}
			if (!isSuspended_dequant) {
				i += actor_dequant.schedule();
			}
			if (!isSuspended_display) {
				i += actor_display.schedule();
			}
			if (!isSuspended_downsample) {
				i += actor_downsample.schedule();
			}
			if (!isSuspended_fairmerge) {
				i += actor_fairmerge.schedule();
			}
			if (!isSuspended_final) {
				i += actor_final.schedule();
			}
			if (!isSuspended_interpolate) {
				i += actor_interpolate.schedule();
			}
			if (!isSuspended_mbpack) {
				i += actor_mbpack.schedule();
			}
			if (!isSuspended_memorymanager) {
				i += actor_memorymanager.schedule();
			}
			if (!isSuspended_mvrecon) {
				i += actor_mvrecon.schedule();
			}
			if (!isSuspended_mvseq) {
				i += actor_mvseq.schedule();
			}
			if (!isSuspended_parseheaders) {
				i += actor_parseheaders.schedule();
			}
			if (!isSuspended_retrans) {
				i += actor_retrans.schedule();
			}
			if (!isSuspended_rowsort) {
				i += actor_rowsort.schedule();
			}
			if (!isSuspended_scale) {
				i += actor_scale.schedule();
			}
			if (!isSuspended_searchwin) {
				i += actor_searchwin.schedule();
			}
			if (!isSuspended_sep) {
				i += actor_sep.schedule();
			}
			if (!isSuspended_seq) {
				i += actor_seq.schedule();
			}
			if (!isSuspended_serialize) {
				i += actor_serialize.schedule();
			}
			if (!isSuspended_shuffle) {
				i += actor_shuffle.schedule();
			}
			if (!isSuspended_shufflefly) {
				i += actor_shufflefly.schedule();
			}
			if (!isSuspended_source) {
				i += actor_source.schedule();
			}
			if (!isSuspended_trans) {
				i += actor_trans.schedule();
			}
			if (!isSuspended_unpack) {
				i += actor_unpack.schedule();
			}
			if (!isSuspended_zigzag) {
				i += actor_zigzag.schedule();
			}
			if (!isSuspended_zzaddr) {
				i += actor_zzaddr.schedule();
			}

			i += actor_broadcast_add_VID.schedule();
			i += actor_broadcast_dcpred_START.schedule();
			i += actor_broadcast_fairmerge_ROWOUT.schedule();
			i += actor_broadcast_mvrecon_MV.schedule();
			i += actor_broadcast_parseheaders_BTYPE.schedule();

			FifoManager.getInstance().emptyFifos();

			if (i == 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.err.println("error: expecting at least two arguments");
			System.err.println("usage: main <command port> <event port> ...");
			System.exit(-1);
		}
		int cmdPort = Integer.decode(args[0]);
		int eventPort = Integer.decode(args[1]);

		String[] otherArgs = new String[args.length - 2];
		System.arraycopy(args, 2, otherArgs, 0, args.length - 2);
		new Network_orcc_testbed(cmdPort, eventPort, otherArgs);
	}
}
