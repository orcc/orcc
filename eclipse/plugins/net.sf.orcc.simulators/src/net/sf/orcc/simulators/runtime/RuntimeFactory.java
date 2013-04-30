package net.sf.orcc.simulators.runtime;

import net.sf.orcc.simulators.runtime.impl.IntfChannel;
import net.sf.orcc.simulators.runtime.impl.IntfNet;
import net.sf.orcc.simulators.runtime.impl.SystemIO;
import net.sf.orcc.simulators.runtime.intf.channel.item.FileInputChannel;
import net.sf.orcc.simulators.runtime.intf.channel.item.FileOutputChannel;
import net.sf.orcc.simulators.runtime.intf.net.item.Tcp;
import net.sf.orcc.simulators.runtime.system.io.item.AccessFile;
import net.sf.orcc.simulators.runtime.system.io.item.Console;

public class RuntimeFactory {

	public static IntfNet createIntfNet() {
		return new IntfNet();
	}

	public static Tcp createTcp(String hostName, String port) {
		return new Tcp(hostName, Integer.parseInt(port));
	}

	public static FileInputChannel createFileInputChannel(String path,
			String mode) {
		return new FileInputChannel(path);
	}

	public static FileOutputChannel createFileOutputChannel(String path,
			String mode) {
		return new FileOutputChannel(path);
	}

	public static SystemIO createSystemIO() {
		return new SystemIO();
	}

	public static SystemIO createConsole(String id){
		return new Console(id);
	}
	
	public static AccessFile createAccessFile(String path) {
		return new AccessFile(path);
	}

	public static IntfChannel createIntfChannel() {
		return new IntfChannel();
	}

}
