/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.runtime.actors;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_int;

public class Actor_Display implements IActor, ActionListener {

	private static Actor_Display instance;

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
		int C = y - 16;
		int D = cb - 128;
		int E = cr - 128;

		int r = clip((298 * C + 409 * E + 128) >> 8);
		int g = clip((298 * C - 100 * D - 208 * E + 128) >> 8);
		int b = clip((298 * C + 516 * D + 128) >> 8);

		return (r << 16) | (g << 8) | b;
	}

	private BufferStrategy buffer;

	private Canvas canvas;

	private Fifo_int fifo_B;

	private Fifo_int fifo_HEIGHT;

	private Fifo_int fifo_WIDTH;

	private JFrame frame;

	public int height;

	private BufferedImage image;

	private int numImages;

	private Timer timer;

	public int width;

	public int x;

	public int y;

	private long startTime;

	private boolean userInterruption;

	public Actor_Display() {
		frame = new JFrame("display");

		canvas = new Canvas();
		frame.add(canvas);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Indicate the end of interpretation (will be returned to main)
				userInterruption = true;
			}

		});

		timer = new Timer(40, this);
		timer.start();

		instance = this;
		userInterruption = false;
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
	public void initialize() {
	}

	@Override
	public int schedule() {
		boolean res = true;
		int i = 0;

		if (userInterruption) {
			return -1;
		}

		if (startTime == 0L) {
			startTime = System.currentTimeMillis();
		}

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
	public void setFifo(String portName, Fifo fifo) {
		if ("B".equals(portName)) {
			fifo_B = (Fifo_int) fifo;
		} else if ("WIDTH".equals(portName)) {
			fifo_WIDTH = (Fifo_int) fifo;
		} else if ("HEIGHT".equals(portName)) {
			fifo_HEIGHT = (Fifo_int) fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	private void setVideoSize() {
		int[] width = fifo_WIDTH.getReadArray(1);
		int width_Index = fifo_WIDTH.getReadIndex(1);
		int[] height = fifo_HEIGHT.getReadArray(1);
		int height_Index = fifo_HEIGHT.getReadIndex(1);

		int newWidth = width[width_Index] << 4;
		int newHeight = height[height_Index] << 4;

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

		fifo_WIDTH.readEnd(1);
		fifo_HEIGHT.readEnd(1);
	}

	private void writeMB() {
		int[] mb = fifo_B.getReadArray(384);
		int mb_Index = fifo_B.getReadIndex(384);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				int u = (Integer) mb[mb_Index + 256 + 8 * i + j];
				int v = (Integer) mb[mb_Index + 320 + 8 * i + j];
				int y0 = (Integer) mb[mb_Index + 2 * i * 16 + 2 * j];
				int y1 = (Integer) mb[mb_Index + 2 * i * 16 + 2 * j + 1];
				int y2 = (Integer) mb[mb_Index + (2 * i + 1) * 16 + 2 * j];
				int y3 = (Integer) mb[mb_Index + (2 * i + 1) * 16 + 2 * j + 1];

				int rgb0 = convertYCbCrtoRGB(y0, u, v);
				int rgb1 = convertYCbCrtoRGB(y1, u, v);
				int rgb2 = convertYCbCrtoRGB(y2, u, v);
				int rgb3 = convertYCbCrtoRGB(y3, u, v);

				image.setRGB(x + j * 2, y + i * 2, rgb0);
				image.setRGB(x + j * 2 + 1, y + i * 2, rgb1);
				image.setRGB(x + j * 2, y + i * 2 + 1, rgb2);
				image.setRGB(x + j * 2 + 1, y + i * 2 + 1, rgb3);
			}
		}

		fifo_B.readEnd(384);

		x += 16;
		if (x == width) {
			x = 0;
			y += 16;
		}

		if (y == height) {
			x = 0;
			y = 0;
			numImages++;
			long t = System.currentTimeMillis();
			long t2 = t - startTime;
			System.out.println(numImages + " in " + t2);
			startTime = t;
		}
	}

}
