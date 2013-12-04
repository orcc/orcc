package net.sf.orcc.backends.c;

public class NetworkPortInfo {
	public enum NetworkPortType {
		Input,
		Output
	};
	
	String portName;
	String baseType;
	int size;
	String actorName;
	NetworkPortType kind;
	
	NetworkPortInfo (String p, int s, String v, String t, NetworkPortType k) {
		portName = p;
		size = s;
		actorName = v;
		baseType = t;
		kind = k;
	}

}