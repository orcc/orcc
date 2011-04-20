/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes and EPFL
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
 *   * Neither the name of the IETR/INSA of Rennes and EPFL nor the names of its
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

public class Actor_DisplayImage implements IActor, ActionListener {

	public interface OutputStreamProxy {
		public void write(String text);
	}

	public static void setOutputStreamProxy(Object proxy) {
		if (instance != null) {
			instance.proxy = (OutputStreamProxy) proxy;
		}
	}

	private static int setRGB(int r, int g, int b) {
		return (r << 16) | (g << 8) | b;
	}

	private OutputStreamProxy proxy;

	private static Actor_DisplayImage instance;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void closeDisplay() {
		if (instance != null) {
			instance.timer.stop();
			instance.frame.dispose();
		}
	}

	private BufferStrategy buffer;

	private Canvas canvas;

	private Fifo_int fifo_R;

	private Fifo_int fifo_G;

	private Fifo_int fifo_B;

	private Fifo_int fifo_SizeOfImage;

	private JFrame frame;

	public int height;

	private BufferedImage image;

	private Timer timer;

	public int width;

	public int x;

	public int y;

	private long startTime;

	private boolean userInterruption;

	public boolean sizeDone;

	public boolean imageDone;

	public Actor_DisplayImage() {
		frame = new JFrame("display image");

		canvas = new Canvas();
		frame.add(canvas);
		//frame.setVisible(true);

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
		proxy = null;
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
		if (fifo_SizeOfImage.hasTokens(2)) {
			return "setImageSize";
		}

		if (fifo_R.hasTokens(1) && fifo_G.hasTokens(1) && fifo_B.hasTokens(1)) {
			return "writeImage";
		}

		return null;
	}

	@Override
	public void initialize() {
		sizeDone = false;
		imageDone = false;
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

		do {
			res = false;

			if (sizeDone == false) {
				imageDone = false;
				if (fifo_SizeOfImage.hasTokens(2)) {
					setImageSize();
					res = true;
					i++;
				}
			}

			if (sizeDone == true && imageDone == false) {
				if (fifo_R.hasTokens(1) && fifo_G.hasTokens(1)
						&& fifo_B.hasTokens(1)) {
					writeImage();
					res = true;
					i++;
				}
			}
			
			// scb@epfl : 2010.01.25, GNU/Linux bug
			// frame must be displayed when the image is already available
			if(imageDone == true) frame.setVisible(true);
			
			
		} while (res);

		return i;
	}

	@Override
	public void setFifo(String portName, Fifo fifo) {
		if ("R".equals(portName)) {
			fifo_R = (Fifo_int) fifo;
		} else if ("G".equals(portName)) {
			fifo_G = (Fifo_int) fifo;
		} else if ("B".equals(portName)) {
			fifo_B = (Fifo_int) fifo;
		} else if ("SizeOfImage".equals(portName)) {
			fifo_SizeOfImage = (Fifo_int) fifo;
		} else {
			String msg = "unknown port \"" + portName + "\"";
			throw new IllegalArgumentException(msg);
		}
	}

	private void setImageSize() {

		int[] SizeOfImage = fifo_SizeOfImage.getReadArray(2);
		int newWidth = SizeOfImage[0];
		int newHeight = SizeOfImage[1];
		fifo_SizeOfImage.readEnd(2);

		if (newWidth != this.width || newHeight != this.height) {
			this.width = newWidth;
			this.height = newHeight;

			canvas.setSize(this.width, this.height);
			frame.pack();

			try{
				canvas.createBufferStrategy(2);
			}
			catch(IllegalStateException e){
				System.out.println("Image not displayable: "+e);
			}
			
			buffer = canvas.getBufferStrategy();

			image = new BufferedImage(this.width, this.height,
					BufferedImage.TYPE_INT_RGB);
		}

		sizeDone = true;
		System.out.println("setImageSize - Image " + newWidth + "x" + newHeight);
	}

	private void writeImage() {
		int[] R = fifo_R.getReadArray(1);
		int R_Index = fifo_R.getReadIndex(1);

		int[] G = fifo_G.getReadArray(1);
		int G_Index = fifo_G.getReadIndex(1);

		int[] B = fifo_B.getReadArray(1);
		int B_Index = fifo_B.getReadIndex(1);

		image.setRGB(x, y, setRGB(R[R_Index], G[G_Index], B[B_Index]));

		fifo_R.readEnd(1);
		fifo_G.readEnd(1);
		fifo_B.readEnd(1);

		x++;
		if (x == width) {
			x = 0;
			y++;
		}

		if (y == height) {
			x = 0;
			y = 0;
			long t = System.currentTimeMillis();
			long t2 = t - startTime;
			if (proxy != null) {
				proxy.write("Image " + width + "x" + height + " computed in "
						+ t2 + "ms\n");
			} else {
				System.out.println("Image " + width + "x" + height
						+ " computed in " + t2 + "ms");
			}
			startTime = t;
			imageDone = true;
			sizeDone = false;
			
		}
	}

}
