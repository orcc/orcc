package net.sf.orcc.backends.c;

public class EdgeInfo {
	String srcPortName;
	String srcActorName;
	String dstPortName;
	String dstActorName;
	
	EdgeInfo (String sa, String sp, String da, String dp ) {
		srcActorName = sa;
		srcPortName = sp;
		dstActorName = da;
		dstPortName = dp;
	}

}
