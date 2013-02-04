package net.sf.orcc.simulators.runtime.system.io.impl;

import java.math.BigInteger;

import net.sf.orcc.simulators.SimulatorDescriptor;
import net.sf.orcc.simulators.runtime.RuntimeFactory;
import net.sf.orcc.simulators.runtime.impl.SystemIO;

public class Console {

	public static BigInteger openConsole(String id) {
		return SimulatorDescriptor.create(RuntimeFactory
				.createConsole(id));
	}

	public static BigInteger closeConsole(BigInteger desc) {
		SimulatorDescriptor.finalize(desc);
		return new BigInteger("0");
	}

	public static String read(BigInteger desc) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isConsole()) {
			return SystemIO.toConsole(io).read();
		}
		return "";
	}
	
	public static BigInteger readInteger(BigInteger desc) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isConsole()) {
			return new BigInteger(SystemIO.toConsole(io).read());
		}
		return new BigInteger("0");
	}
	
	public static void write(BigInteger desc, String v) {
		SystemIO io = SimulatorDescriptor.getSystemIO(desc);
		if (io.isConsole()) {
			SystemIO.toConsole(io).write(v);
		}
	}
	
}
