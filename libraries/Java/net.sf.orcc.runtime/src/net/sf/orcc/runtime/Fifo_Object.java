package net.sf.orcc.runtime;

import java.io.IOException;

public class Fifo_Object extends Fifo {

	/**
	 * The contents of the FIFO.
	 */
	private Object[] contents;

	/**
	 * Creates a new FIFO with the given size.
	 * 
	 * @param size
	 *            the size of this FIFO
	 */
	public Fifo_Object(int size) {
		super(size);
		contents = new Object[size];
	}

	/**
	 * Creates a new FIFO with the given size and a file for tracing exchanged
	 * data.
	 * 
	 * @param size
	 *            the size of the FIFO
	 * @param folderName
	 *            output traces folder
	 * @param fifoName
	 *            name of the fifo (and the trace file)
	 */
	public Fifo_Object(int size, String folderName, String fifoName, boolean enableTraces) {
		super(size, folderName, fifoName, enableTraces);
		contents = new Object[size];
	}

	/**
	 * Returns the array where <code>numTokens</code> can be read.
	 * 
	 * @param numTokens
	 *            a number of tokens to read
	 * @return the array where <code>numTokens</code> can be read
	 */
	final public Object[] getReadArray(int numTokens) {
		if (read + numTokens <= size) {
			return contents;
		} else {
			Object[] buffer = new Object[numTokens];

			int numEnd = size - read;
			int numBeginning = numTokens - numEnd;

			// Copy the end of the fifo
			if (numEnd != 0) {
				System.arraycopy(contents, read, buffer, 0, numEnd);
			}

			// Copy the rest of the data at the beginning of the FIFO
			if (numBeginning != 0) {
				System.arraycopy(contents, 0, buffer, numEnd, numBeginning);
			}

			return buffer;
		}
	}

	/**
	 * Returns the array where <code>numTokens</code> can be written.
	 * 
	 * @param numTokens
	 *            a number of tokens to write
	 * @return the array where <code>numTokens</code> can be written
	 */
	final public Object[] getWriteArray(int numTokens) {
		if (write + numTokens <= size) {
			return contents;
		} else {
			return new Object[numTokens];
		}
	}

	public String toString() {
		return "W=" + write + "/" + "R=" + read;
	}

	/**
	 * Signals that writing is finished.
	 * 
	 * @param numTokens
	 *            the number of tokens that were written
	 */
	final public void writeEnd(int numTokens, Object[] buffer) {
		fillCount += numTokens;
		if (write + numTokens <= size) {
			write += numTokens;
		} else {
			int numEnd = size - write;
			int numBeginning = numTokens - numEnd;

			// Copy data at the end of the FIFO
			if (numEnd != 0) {
				System.arraycopy(buffer, 0, contents, write, numEnd);
			}

			// Copy the rest of data at the beginning of the FIFO
			if (numBeginning != 0) {
				System.arraycopy(buffer, numEnd, contents, 0, numBeginning);
			}

			write = numBeginning;
		}
		// Trace writing
		if (out != null) {
			try {
				if (buffer[0] instanceof Boolean) {
					for (int i = 0; i < buffer.length; i++) {
						if (buffer[i] == null)
							break;
						if ((Boolean)buffer[i]) {
							out.write("1\n");
						} else {
							out.write("0\n");
						}
					}
				} else {
					for (int i = 0; i < buffer.length; i++) {
						if (buffer[i] == null)
							break;
						out.write(buffer[i] + "\n");
					}
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
