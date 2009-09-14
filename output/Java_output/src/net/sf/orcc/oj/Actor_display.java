/**
 * Generated from "display"
 */
package net.sf.orcc.oj;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Actor_display extends JFrame implements IActor, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int convertYCbCrtoRGB(int y, int cb, int cr) {
		int r = (int) ((298.082f * y + 408.583f * cr) / 256f - 222.921f);
		int g = (int) ((298.082f * y - 100.291f * cb - 208.120f * cr) / 256f + 135.576f);
		int b = (int) ((298.082f * y + 516.412f * cb) / 256f - 276.836f);

		if (r < 0) {
			r = 0;
		} else if (r > 255) {
			r = 255;
		}

		if (g < 0) {
			g = 0;
		} else if (g > 255) {
			g = 255;
		}

		if (b < 0) {
			b = 0;
		} else if (b > 255) {
			b = 255;
		}

		return (r << 16) | (g << 8) | b;
	}

	private BufferStrategy buffer;

	private Canvas canvas;

	private IntFifo fifo_B;

	private IntFifo fifo_HEIGHT;

	private IntFifo fifo_WIDTH;

	private int height;

	private BufferedImage image;

	private int width;

	private int x;

	private int y;

	private Timer timer;
	
	private static Actor_display instance;
	
	public static void closeDisplay() {
		if (instance != null) {
			instance.timer.stop();
			instance.dispose();
		}
	}

	public Actor_display() {
		super("display");

		canvas = new Canvas();
		add(canvas);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timer = new Timer(20, this);
		timer.start();
		
		instance = this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (buffer != null) {
			Graphics graphics = buffer.getDrawGraphics();
			graphics.setColor(Color.RED);
			graphics.clearRect(0, 0, this.width, this.height);
			graphics.drawImage(image, 0, 0, null);
			buffer.show();
			graphics.dispose();
		}
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

		int newWidth = width[0] << 4;
		int newHeight = height[0] << 4;

		if (newWidth != this.width || newHeight != this.height) {
			this.width = newWidth;
			this.height = newHeight;
			
			canvas.setSize(this.width, this.height);
			pack();

			canvas.createBufferStrategy(2);
			buffer = canvas.getBufferStrategy();

			image = new BufferedImage(this.width, this.height,
					BufferedImage.TYPE_INT_RGB);
		}
	}

	private void writeMB() {
		int[] mb = new int[384];
		fifo_B.get(mb);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int u = 128;
				int v = 128;

				int index = 8 * i + j;

				for (int ym = 0; ym < 2; ym++) {
					for (int xm = 0; xm < 2; xm++) {
						int y = mb[index];

						int rgb = convertYCbCrtoRGB(y, u, v);

						image.setRGB(x + i + xm * 8, this.y + j + ym * 8, rgb);
					}
				}
			}
		}

		x += 16;
		if (x == width) {
			x = 0;
			y += 16;
		}

		if (y == height) {
			x = 0;
			y = 0;
		}
	}

}
