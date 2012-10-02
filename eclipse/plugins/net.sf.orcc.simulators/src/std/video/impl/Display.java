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
package std.video.impl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

import javax.swing.JFrame;

import net.sf.orcc.runtime.impl.GenericDisplay;

/**
 * This class defines native functions for the DisplayYUV actor.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Display extends GenericDisplay {

	private static RandomAccessFile in;

	private static int frameNumber = 0;

	private static boolean useCompare;

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
	public static void compareYUV_comparePicture(byte[] pictureBufferY,
			byte[] pictureBufferU, byte[] pictureBufferV,
			BigInteger pictureWidth, BigInteger pictureHeight) {
		int width = pictureWidth.intValue();
		int height = pictureHeight.intValue();

		byte[] Y = new byte[width * height];
		byte[] U = new byte[width * height / 4];
		byte[] V = new byte[width * height / 4];

		if (useCompare) {
			try {
				int numErrors = 0;

				System.out.println("Frame number " + frameNumber);
				frameNumber++;

				in.read(Y, 0, width * height);
				in.read(U, 0, width * height / 4);
				in.read(V, 0, width * height / 4);

				numErrors += compareYUV_compareComponent(width, height, Y,
						pictureBufferY, 16);
				numErrors += compareYUV_compareComponent(width / 2, height / 2,
						U, pictureBufferU, 8);
				numErrors += compareYUV_compareComponent(width / 2, height / 2,
						V, pictureBufferV, 8);

				if (numErrors == 0) {
					System.out.println("; no error detected !\n");
				}

				if (in.getFilePointer() == in.length()) {
					in.seek(0L);
					frameNumber = 0;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static int compareYUV_compareComponent(int width, int height,
			byte[] golden, byte[] approximate, int SizeMbSide) {
		int pix_x, pix_y, blk_x, blk_y;
		int error = 0;
		int WidthSzInBlk = width / SizeMbSide;
		int HeightSzInBlk = height / SizeMbSide;

		for (blk_y = 0; blk_y < HeightSzInBlk; blk_y++) {
			for (blk_x = 0; blk_x < WidthSzInBlk; blk_x++) {
				for (pix_y = 0; pix_y < SizeMbSide; pix_y++) {
					for (pix_x = 0; pix_x < SizeMbSide; pix_x++) {
						int Idx_pix = (blk_y * SizeMbSide + pix_y) * width
								+ (blk_x * SizeMbSide + pix_x);
						if (golden[Idx_pix] - approximate[Idx_pix] != 0) {
							error++;
						}
					}
				}
			}
		}
		return error;
	}

	/**
	 * Init the YUV comparison.
	 */
	public static void compareYUV_init() {
		useCompare = false;
		if (!goldenReference.isEmpty()) {
			try {
				in = new RandomAccessFile(goldenReference, "r");
				useCompare = true;
			} catch (FileNotFoundException e) {
				String msg = "file not found: \"" + goldenReference + "\"";
				throw new RuntimeException(msg, e);
			}
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

	public static void displayYUV_displayPicture(byte[] pictureBufferY,
			byte[] pictureBufferU, byte[] pictureBufferV,
			BigInteger biPictureWidth, BigInteger biPictureHeight) {
		int pictureWidth = biPictureWidth.intValue();
		int pictureHeight = biPictureHeight.intValue();
		if (pictureWidth != lastWidth || pictureHeight != lastHeight) {
			setVideoSize(pictureWidth, pictureHeight);
		}

		if (image == null) {
			return;
		}

		for (int i = 0; i < pictureWidth / 2; i++) {
			for (int j = 0; j < pictureHeight / 2; j++) {
				int u = pictureBufferU[i + j * pictureWidth / 2] & 0xFF;
				int v = pictureBufferV[i + j * pictureWidth / 2] & 0xFF;
				int y0 = pictureBufferY[i * 2 + j * 2 * pictureWidth] & 0xFF;
				int y1 = pictureBufferY[i * 2 + 1 + j * 2 * pictureWidth] & 0xFF;
				int y2 = pictureBufferY[i * 2 + (j * 2 + 1) * pictureWidth] & 0xFF;
				int y3 = pictureBufferY[i * 2 + 1 + (j * 2 + 1) * pictureWidth] & 0xFF;

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
	public static BigInteger displayYUV_getFlags() {
		return BigInteger.valueOf(DISPLAY_ENABLE | DISPLAY_READY);
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

		if (canvas != null) {
			canvas.setSize(lastWidth, lastHeight);
			frame.pack();

			canvas.createBufferStrategy(2);
			buffer = canvas.getBufferStrategy();

			image = new BufferedImage(lastWidth, lastHeight,
					BufferedImage.TYPE_INT_RGB);
		}
	}

	public static void fpsPrintInit() {
		t1 = System.currentTimeMillis();
	}

	public static void fpsPrintNewPicDecoded() {
		t2 = System.currentTimeMillis();
		System.out.println("image displayed in " + (t2 - t1) + " ms");
		t1 = t2;
	}

}
