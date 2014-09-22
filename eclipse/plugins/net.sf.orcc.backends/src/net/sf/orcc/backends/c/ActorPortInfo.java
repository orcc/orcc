package net.sf.orcc.backends.c.compa;

public class ActorPortInfo {
	String portName;
	String baseType;
	int size;
	String varName;
	ActorPortInfo (String p, int s, String v, String t) {
		portName = p;
		size = s;
		varName = v;
		baseType = t;
	}

}
