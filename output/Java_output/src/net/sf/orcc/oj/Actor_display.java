/**
 * Generated from "display"
 */
package net.sf.orcc.oj;


public class Actor_display implements IActor {

	private IntFifo fifo_B;

	private IntFifo fifo_HEIGHT;

	private IntFifo fifo_WIDTH;

	public Actor_display() {
	}

	@Override
	public void initialize() {
	}

	@Override
	public int schedule() {
		boolean res = true;
		int i = 0;

		while (res) {
			res = false;
			if (fifo_WIDTH.hasTokens(1) && fifo_HEIGHT.hasTokens(1)) {
				setVideoSize();
			}

			if (fifo_B.hasTokens(384)) {
				writeMB();
			}
		}

		return i;
	}

	@Override
	public void setFifo(String portName, IntFifo fifo) {
		if ("B".equals(portName)) {
			fifo_B = fifo;
		} else if ("WIDTH".equals(portName)) {
			fifo_WIDTH = fifo;
		} else if ("HEIGHT".equals(portName)) {
			fifo_HEIGHT = fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	private void setVideoSize() {
		int[] width = new int[1];
		int[] height = new int[1];

		fifo_WIDTH.get(width);
		fifo_HEIGHT.get(height);
	}

	private void writeMB() {
		int[] mb = new int[384];
		fifo_B.get(mb);
	}

}
