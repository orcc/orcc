/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package ch.epfl.mpeg4.part2.impl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * This class defines native functions for the DisplayYUV actor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Display {

	private static BufferStrategy buffer;

	private static Canvas canvas;

	/**
	 * display is disabled.
	 */
	public static final int DISPLAY_DISABLE = 0;

	/**
	 * display is enabled.
	 */
	public static final int DISPLAY_ENABLE = 2;

	/**
	 * display is ready.
	 */
	public static final int DISPLAY_READY = 1;

	private static JFrame frame;

	private static BufferedImage image;

	private static int lastHeight;

	private static int lastWidth;

	private static long t1;

	private static long t2;
	
	private static int clip(int n) {
		if (n < 0) {
			return 0;
		} else if (n > 255) {
			return 255;
		} else {
			return n;
		}
	}

	/**
	 * Compares the contents in the given buffers with a golden reference.
	 * 
	 * @param pictureBufferY
	 *            Y buffer
	 * @param pictureBufferU
	 *            U buffer
	 * @param pictureBufferV
	 *            V buffer
	 * @param pictureWidth
	 *            width
	 * @param pictureHeight
	 *            height
	 */
	public static void compareYUV_comparePicture(short[] pictureBufferY,
			short[] pictureBufferU, short[] pictureBufferV,
			Integer pictureWidth, Integer pictureHeight) {

	}

	/**
	 * Init the YUV comparison.
	 */
	public static void compareYUV_init() {

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

	public static void displayYUV_displayPicture(short[] pictureBufferY,
			short[] pictureBufferU, short[] pictureBufferV,
			Integer pictureWidth, Integer pictureHeight) {
		if (pictureWidth != lastWidth || pictureHeight != lastHeight) {
			setVideoSize(pictureWidth, pictureHeight);
		}

		for (int i = 0; i < pictureWidth / 2; i++) {
			for (int j = 0; j < pictureHeight / 2; j++) {
				int u = pictureBufferU[i + j * pictureWidth / 2];
				int v = pictureBufferV[i + j * pictureWidth / 2];
				int y0 = pictureBufferY[i * 2 + j * 2 * pictureWidth];
				int y1 = pictureBufferY[i * 2 + 1 + j * 2 * pictureWidth];
				int y2 = pictureBufferY[i * 2 + (j * 2 + 1) * pictureWidth];
				int y3 = pictureBufferY[i * 2 + 1 + (j * 2 + 1) * pictureWidth];

				int rgb0 = convertYCbCrtoRGB(y0, u, v);
				int rgb1 = convertYCbCrtoRGB(y1, u, v);
				int rgb2 = convertYCbCrtoRGB(y2, u, v);
				int rgb3 = convertYCbCrtoRGB(y3, u, v);

				image.setRGB(i * 2, j * 2, rgb0);
				image.setRGB(i * 2 + 1, j * 2, rgb1);
				image.setRGB(i * 2, j * 2 + 1, rgb2);
				image.setRGB(i * 2 + 1, j * 2 + 1, rgb3);
			}
		}

		if (buffer != null) {
			Graphics graphics = buffer.getDrawGraphics();
			graphics.drawImage(image, 0, 0, null);
			buffer.show();
			graphics.dispose();
		}
	}

	/**
	 * Returns the flags of the display. This implementation returns a display
	 * always enabled and ready.
	 * 
	 * @return the flags of the display
	 */
	public static int displayYUV_getFlags() {
		return DISPLAY_ENABLE | DISPLAY_READY;
	}

	/**
	 * Initializes the display.
	 */
	public static void displayYUV_init() {
		frame = new JFrame("display");
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (buffer != null) {
					buffer.dispose();
				}

				canvas = null;
				image = null;
				lastHeight = 0;
				lastWidth = 0;
			}

		});

		canvas = new Canvas();
		frame.add(canvas);
		frame.setVisible(true);
	}

	private static void setVideoSize(int newWidth, int newHeight) {
		lastWidth = newWidth;
		lastHeight = newHeight;

		canvas.setSize(lastWidth, lastHeight);
		frame.pack();

		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();

		image = new BufferedImage(lastWidth, lastHeight,
				BufferedImage.TYPE_INT_RGB);
	}
	
	public static void fpsPrintInit()  {
		t1 = System.currentTimeMillis();
	}

	public static void fpsPrintNewPicDecoded() {
		t2 = System.currentTimeMillis();
		System.out.println("image displayed in " + (t2 - t1) + " ms");
		t1 = t2;
	}

}
