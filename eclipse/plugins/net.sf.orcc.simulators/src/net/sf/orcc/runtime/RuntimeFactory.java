package net.sf.orcc.runtime;

import intf.channel.item.FileInputChannel;
import intf.channel.item.FileOutputChannel;
import intf.net.item.Tcp;
import net.sf.orcc.runtime.impl.IntfChannel;
import net.sf.orcc.runtime.impl.IntfNet;
import net.sf.orcc.runtime.impl.SystemIO;
import system.io.item.AccessFile;

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

	public static AccessFile createAccessFile(String path) {
		return new AccessFile(path);
	}

	public static IntfChannel createIntfChannel() {
		return new IntfChannel();
	}

}
