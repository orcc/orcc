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
package std.video.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sf.orcc.runtime.impl.GenericSource;

public class ReadImage extends GenericSource {

	private static int height;

	private static BufferedImage image;

	private static int width;

	private static int x;

	private static int y;

	public static void advance() {
		x++;
		if (x == width) {
			x = 0;
			y++;
		}

		if (y == height) {
			x = 0;
			y = 0;
		}
	}

	public static int getBlue() {
		return getBlue(image.getRGB(x, y));
	}

	private static int getBlue(int pixel) {
		return ((pixel) & 0xff);
	}

	public static int getGreen() {
		return getGreen(image.getRGB(x, y));
	}

	private static int getGreen(int pixel) {
		return ((pixel >> 8) & 0xff);
	}

	public static int getHeight() {
		return image.getHeight();
	}

	public static int getRed() {
		return getRed(image.getRGB(x, y));
	}

	private static int getRed(int pixel) {
		return ((pixel >> 16) & 0xff);
	}

	public static int getWidth() {
		return image.getWidth();
	}

	public static void readImage_initialize() {
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			String msg = "image error";
			throw new RuntimeException(msg, e);
		}

		width = image.getWidth();
		height = image.getHeight();
	}

}
