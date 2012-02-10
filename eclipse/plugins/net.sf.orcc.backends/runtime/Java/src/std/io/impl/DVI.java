package std.io.impl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DVI {

	private static Canvas canvas;

	private static BufferStrategy buffer;

	private static JFrame frame;

	private static BufferedImage image;

	private static int lastHeight;

	private static int lastWidth;

	private static int i;

	private static int j;

	private static void move() {
		i++;
		if (i >= lastWidth) {
			i = 0;
			j++;
			if (j >= lastHeight) {
				j = 0;
			}
		}
	}

	private static void setResolution(Integer newWidth, Integer newHeight) {
		lastWidth = newWidth;
		lastHeight = newHeight;

		canvas.setSize(lastWidth, lastHeight);
		frame.pack();

		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();

		image = new BufferedImage(lastWidth, lastHeight,
				BufferedImage.TYPE_INT_RGB);
	}

	public static void init(Integer width, Integer height) {
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
		i = 0;
		j = 0;
		setResolution(width, height);
	}

	public static void setRGB(Integer rgb) {
		image.setRGB(i, j, rgb);
		move();

		if ((buffer != null) & (i == 0) & (j == 0)) {
			Graphics graphics = buffer.getDrawGraphics();
			graphics.drawImage(image, 0, 0, null);
			buffer.show();
			graphics.dispose();
		}

	}

}
