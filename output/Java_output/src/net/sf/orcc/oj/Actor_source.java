package net.sf.orcc.oj;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Actor_source implements IActor {

	private String fileName;

	private RandomAccessFile in;

	private IntFifo fifo_O;

	public Actor_source() {
		fileName = CLIParameters.getInstance().getSourceFile();
	}

	@Override
	public void initialize() {
		try {
			in = new RandomAccessFile(fileName, "r");
		} catch (FileNotFoundException e) {
			String msg = "file not found: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}
	}

	@Override
	public int schedule() {
		int[] source = new int[1];
		int i = 0;

		try {
			try {
				while (fifo_O.hasRoom(1)) {
					source[0] = in.read();
					fifo_O.put(source);
					i++;
				}
			} catch (EOFException e) {
				in.seek(0);
			}
		} catch (IOException e) {
			String msg = "I/O exception: \"" + fileName + "\"";
			throw new RuntimeException(msg, e);
		}

		return i;
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("O".equals(portName)) {
			fifo_O = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

}
