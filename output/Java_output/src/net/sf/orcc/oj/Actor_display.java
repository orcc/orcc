/**
 * Generated from "display"
 */
package net.sf.orcc.oj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Actor_display implements IActor {

	private IntFifo fifo_B;

	private IntFifo fifo_HEIGHT;

	private IntFifo fifo_WIDTH;

	private JFrame frame;

	private BufferStrategy strategy;

	private Graphics graphics;

	private int x;

	private int width;

	private int y;

	private int height;

	public Actor_display() {
		frame = new JFrame("display");

		frame.pack();
		frame.setVisible(true);
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
				res = true;
				i++;
			}

			if (fifo_B.hasTokens(384)) {
				writeMB();
				res = true;
				i++;
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

		this.width = width[0] << 4;
		this.height = height[0] << 4;

		Rectangle rv = frame.getBounds(null);
		rv.width = this.width;
		rv.height = this.height;
		frame.setBounds(rv);

		frame.createBufferStrategy(2);
		strategy = frame.getBufferStrategy();
		graphics = strategy.getDrawGraphics();
	}

	private void writeMB() {
		int[] mb = new int[384];
		fifo_B.get(mb);

		// ColorSpace cs = ColorSpace.getInstance(ColorSpace.TYPE_YCbCr);

		int r = (255 * x * y / (width * height));
		graphics.setColor(new Color(r, r, r));
		graphics.fillRect(x, y, 16, 16);

		x += 16;
		if (x == width) {
			x = 0;
			y += 16;
		}

		if (y == height) {
			x = 0;
			y = 0;
			strategy.show();
		}
	}

}
