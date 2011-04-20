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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sf.orcc.runtime.CLIParameters;
import net.sf.orcc.runtime.Fifo;
import net.sf.orcc.runtime.Fifo_int;

public class Actor_ReadImage implements IActor {

	private Fifo_int fifo_R;

	private Fifo_int fifo_G;

	private Fifo_int fifo_B;

	private Fifo_int fifo_SizeOfImage;

	private String fileName;

	private BufferedImage image;

	public int x;

	public int y;

	public boolean sizeDone;

	public boolean imageDone;

	private int width;

	private int height;

	public Actor_ReadImage() {
		fileName = CLIParameters.getInstance().getSourceFile();
	}

	public Actor_ReadImage(String fileName) {
		this.fileName = fileName;
	}

	public String getNextSchedulableAction() {
		if (fifo_SizeOfImage.hasRoom(2)) {
			return "setImageSize";
		}

		if (fifo_R.hasRoom(1) && fifo_G.hasRoom(1) && fifo_B.hasRoom(1)) {
			return "writeImage";
		}

		return null;
	}

	@Override
	public void initialize() {
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			String msg = "image error";
			throw new RuntimeException(msg, e);
		}
		sizeDone = false;
		imageDone = false;
	}

	@Override
	public int schedule() {
		boolean res;
		int i = 0;

		do {
			res = false;
			if (sizeDone == false) {
				if (fifo_SizeOfImage.hasRoom(2)) {
					setImageSize();
					res = true;
					i++;
				}
			}

			if (imageDone == false) {
				if (fifo_R.hasRoom(1) && fifo_G.hasRoom(1) && fifo_B.hasRoom(1)) {
					writeImage();
					res = true;
					i++;
				}
			}
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

		width = image.getWidth();
		height = image.getHeight();

		int[] sizeOfImage = fifo_SizeOfImage.getWriteArray(2);
		int sizeOfImageIndex = fifo_SizeOfImage.getWriteIndex(2);
		sizeOfImage[sizeOfImageIndex] = width;
		sizeOfImage[sizeOfImageIndex + 1] = height;
		fifo_SizeOfImage.writeEnd(2, sizeOfImage);

		sizeDone = true;
	}

	private void writeImage() {

		int[] R = fifo_R.getWriteArray(1);
		int R_Index = fifo_R.getWriteIndex(1);
		R[R_Index] = getRed(image.getRGB(x, y));
		fifo_R.writeEnd(1, R);

		int[] G = fifo_G.getWriteArray(1);
		int G_Index = fifo_G.getWriteIndex(1);
		G[G_Index] = getGreen(image.getRGB(x, y));
		fifo_G.writeEnd(1, G);

		int[] B = fifo_B.getWriteArray(1);
		int B_Index = fifo_B.getWriteIndex(1);
		B[B_Index] = getBlue(image.getRGB(x, y));
		fifo_B.writeEnd(1, B);

		x++;
		if (x == width) {
			x = 0;
			y++;
		}

		if (y == height) {
			x = 0;
			y = 0;
			imageDone = true;
		}
	}

	private int getBlue(int pixel) {
		return ((pixel) & 0xff);
	}

	private int getGreen(int pixel) {
		return ((pixel >> 8) & 0xff);
	}

	private int getRed(int pixel) {
		return ((pixel >> 16) & 0xff);
	}

}
