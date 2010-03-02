/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.oj.actors;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.Timer;

import net.sf.orcc.debug.Location;
import net.sf.orcc.debug.type.AbstractType;
import net.sf.orcc.debug.type.IntType;
import net.sf.orcc.oj.IntFifo;
import net.sf.orcc.oj.debug.AbstractActorDebug;

public class Actor_display extends AbstractActorDebug implements ActionListener {

	private static Actor_display instance;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int clip(int n) {
		if (n < 0) {
			return 0;
		} else if (n > 255) {
			return 255;
		} else {
			return n;
		}
	}

	public static void closeDisplay() {
		if (instance != null) {
			instance.timer.stop();
			instance.frame.dispose();
		}
	}

	private static int convertYCbCrtoRGB(int y, int cb, int cr) {
		y = (76306 * (y - 16)) + 32768;
		int r = (y + (104597 * (cr - 128))) >> 16;
		int g = (y - ((25675 * (cb - 128)) + (53279 * (cr - 128)))) >> 16;
		int b = (y + (132201 * (cb - 128)) >> 16);

		r = clip(r);
		g = clip(g);
		b = clip(b);

		return (r << 16) | (g << 8) | b;
	}

	private BufferStrategy buffer;

	private Canvas canvas;

	private IntFifo fifo_B;

	private IntFifo fifo_HEIGHT;

	private IntFifo fifo_WIDTH;

	private JFrame frame;

	public int height;

	private BufferedImage image;

	private int numImages;

	private Timer timer;

	public int width;

	public int x;

	public int y;

	private long startTime;

	public Actor_display() {
		super("Actor_display.java");

		frame = new JFrame("display");

		canvas = new Canvas();
		frame.add(canvas);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				long t = System.currentTimeMillis() - startTime;
				System.out.println(numImages + " in " + t);
				System.exit(0);
			}

		});

		timer = new Timer(40, this);
		timer.start();

		instance = this;

		actionLocation.put("setVideoSize", new Location(188, 1, 34));
		actionLocation.put("writeMB", new Location(213, 1, 29));

		variables = new TreeMap<String, AbstractType>();
		variables.put("x", new IntType(16));
		variables.put("y", new IntType(16));
		variables.put("width", new IntType(32));
		variables.put("height", new IntType(32));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (buffer != null) {
			Graphics graphics = buffer.getDrawGraphics();
			graphics.drawImage(image, 0, 0, null);
			buffer.show();
			graphics.dispose();
		}
	}

	@Override
	public String getNextSchedulableAction() {
		if (fifo_WIDTH.hasTokens(1) && fifo_HEIGHT.hasTokens(1)) {
			return "setVideoSize";
		}

		if (fifo_B.hasTokens(384)) {
			return "writeMB";
		}

		return null;
	}

	@Override
	public int schedule() {
		boolean res = !suspended;
		int i = 0;

		if (startTime == 0L) {
			startTime = System.currentTimeMillis();
		}

		while (res) {
			res = false;
			if (!suspended && fifo_WIDTH.hasTokens(1)
					&& fifo_HEIGHT.hasTokens(1)) {
				setVideoSize();
				res = true;
				i++;
			}

			if (!suspended && fifo_B.hasTokens(384)) {
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
			frame.pack();

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
				int index = 8 * j + i;

				int u = mb[256 + index];
				int v = mb[320 + index];

				for (int ym = 0; ym < 2; ym++) {
					for (int xm = 0; xm < 2; xm++) {
						int y = mb[64 * (xm + 2 * ym) + index];

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
			numImages++;
		}
	}

}
