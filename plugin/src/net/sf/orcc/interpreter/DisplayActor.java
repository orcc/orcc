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
package net.sf.orcc.interpreter;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import net.sf.orcc.ir.Actor;

public class DisplayActor extends AbstractInterpretedActor {

	private static DisplayActor instance;

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

	private CommunicationFifo fifo_B;

	private CommunicationFifo fifo_HEIGHT;

	private CommunicationFifo fifo_WIDTH;

	private JFrame frame;

	public int height;

	private BufferedImage image;

	private int numImages;

	public int width;

	public int x;

	public int y;

	public DisplayActor(String id, Actor actor) {
		super(id, actor);

		frame = new JFrame("display");

		canvas = new Canvas();
		frame.add(canvas);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Display closed after "+numImages+" images");
			}

		});

		instance = this;
	}

	@Override
	public void initialize() {
		// Connect to FIFOs
		fifo_WIDTH = (CommunicationFifo) actor.getInput("WIDTH").fifo();
		fifo_HEIGHT = (CommunicationFifo) actor.getInput("HEIGHT").fifo();
		fifo_B = (CommunicationFifo) actor.getInput("B").fifo();
	}

	@Override
	public Integer schedule() {
		int running = 0;

		if (fifo_WIDTH.hasTokens(1) && fifo_HEIGHT.hasTokens(1)) {
			setVideoSize();
			running = 1;
		}

		if (fifo_B.hasTokens(384)) {
			writeMB();
			running = 1;
			// TODO: print when frame is complete => ?
			if (buffer != null) {
				Graphics graphics = buffer.getDrawGraphics();
				graphics.drawImage(image, 0, 0, null);
				buffer.show();
				graphics.dispose();
			}
		}

		return running;
	}

	private void setVideoSize() {
		Object[] width = (Object[]) new Integer[1];
		Object[] height = (Object[]) new Integer[1];

		fifo_WIDTH.get(width);
		fifo_HEIGHT.get(height);

		int newWidth = (Integer) width[0] << 4;
		int newHeight = (Integer) height[0] << 4;

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
		Object[] mb = (Object[]) new Integer[384];
		fifo_B.get(mb);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int index = 8 * j + i;

				int u = (Integer) mb[256 + index];
				int v = (Integer) mb[320 + index];

				for (int ym = 0; ym < 2; ym++) {
					for (int xm = 0; xm < 2; xm++) {
						int y = (Integer) mb[64 * (xm + 2 * ym) + index];

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
