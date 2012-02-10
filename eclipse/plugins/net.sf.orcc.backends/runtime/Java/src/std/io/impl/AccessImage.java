package std.io.impl;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.orcc.runtime.SimulatorDescriptor;
import net.sf.orcc.runtime.source.GenericSource;

public class AccessImage extends GenericSource {

	private enum AccessImageColorSpace {

		CS_RGB(0, ColorSpace.CS_GRAY), CS_LINEAR_RGB(1,
				ColorSpace.CS_LINEAR_RGB), CS_GRAY(2, ColorSpace.CS_GRAY), CS_YCrCb_444(
				3, ColorSpace.TYPE_YCbCr), CS_YCrCb_422(4,
				ColorSpace.TYPE_YCbCr), CS_YCrCb_420(5, ColorSpace.TYPE_YCbCr);

		private int typeId;
		private int typeFilter;

		private static Map<Integer, AccessImageColorSpace> v = new HashMap<Integer, AccessImageColorSpace>();

		static {
			for (AccessImageColorSpace cs : AccessImageColorSpace.values()) {
				v.put(cs.getId(), cs);
			}
		}

		AccessImageColorSpace(int id, int filter) {
			typeId = id;
			typeFilter = filter;
		}

		public int getId() {
			return typeId;
		}

		@SuppressWarnings("unused")
		public int getFilter() {
			return typeFilter;
		}

		public static AccessImageColorSpace getColorSpace(int id) {
			return v.get(id);
		}

	}

	private static Map<Integer, BufferedImage> bufferedImages = new HashMap<Integer, BufferedImage>();
	private static Map<Integer, AccessImageColorSpace> colorSpaceImages = new HashMap<Integer, AccessImageColorSpace>();
	private static Map<Integer, Boolean> modified = new HashMap<Integer, Boolean>();

	public static Integer openImage(String fileName, Integer colorSpace) {
		try {
			if(fileName.isEmpty())
					fileName = getFileName();
			Integer desc = SimulatorDescriptor.create(fileName,
					AccessImage.class.getMethod("closeImage", Integer.class));

			BufferedImage image = null;
			colorSpaceImages.put(desc,
					AccessImageColorSpace.getColorSpace(colorSpace));

			if (SimulatorDescriptor.get(desc).exists()) {
				image = ImageIO.read(SimulatorDescriptor.get(desc));

			}

			if (image == null) {
				image = new BufferedImage(1, 1, ColorSpace.TYPE_RGB);
			}

			bufferedImages.put(desc, image);
			modified.put(desc, false);

			return desc;
		} catch (Exception e) {
			String msg = "I/O error : An Image cannot be open";
			throw new RuntimeException(msg, e);
		}
	}

	public static Integer closeImage(Integer desc) {
		if (modified.get(desc)) {
			File file = SimulatorDescriptor.get(desc);
			try {
				String path = file.getPath();
				int dotPos = path.lastIndexOf(".");
				String extension = path.substring(dotPos + 1);
				ImageIO.write(bufferedImages.get(desc), extension, file);
			} catch (IOException e) {
				String msg = "I/O error : An Image cannot be close";
				throw new RuntimeException(msg, e);
			}
		}
		bufferedImages.remove(desc);
		modified.remove(desc);
		SimulatorDescriptor.finalize(desc);
		return 0;
	}

	public static void writeImage(Integer desc, int image[][][]) {
		if (SimulatorDescriptor.contains(desc)) {
			BufferedImage img = bufferedImages.get(desc);
			int width = image.length < img.getWidth() ? image.length : img
					.getWidth();
			int height = image[0].length < img.getHeight() ? image[0].length
					: img.getHeight();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int color = (image[i][j][0] << 16) + (image[i][j][1] << 8)
							+ image[i][j][2];
					img.setRGB(i, j, color);
				}
			}
			modified.put(desc, true);
		}
	}

	public static void readImage(Integer desc, int image[][][]) {
		if (SimulatorDescriptor.contains(desc)) {
			BufferedImage img = bufferedImages.get(desc);
			int width = image.length < img.getWidth() ? image.length : img
					.getWidth();
			int height = image[0].length < img.getHeight() ? image[0].length
					: img.getHeight();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int color = bufferedImages.get(desc).getRGB(i, j);
					image[i][j][2] = (short) ((color) & 0xff);
					image[i][j][1] = (short) ((color >> 8) & 0xff);
					image[i][j][0] = (short) ((color >> 16) & 0xff);
				}
			}
		}
	}

	public static void getImageResolution(Integer desc, int[] resolution) {
		if (SimulatorDescriptor.contains(desc)) {
			BufferedImage img = bufferedImages.get(desc);
			resolution[0] = img.getWidth();
			resolution[1] = img.getHeight();
		}
	}

	public static void setImageResolution(Integer desc, int[] resolution) {
		if (SimulatorDescriptor.contains(desc)) {

			BufferedImage oldImg = bufferedImages.get(desc);
			BufferedImage newImg = new BufferedImage(resolution[0],
					resolution[1], oldImg.getType());

			int hybridWidth = oldImg.getWidth() < newImg.getWidth() ? oldImg
					.getWidth() : newImg.getWidth();
			int hybridHeight = oldImg.getHeight() < newImg.getHeight() ? oldImg
					.getHeight() : newImg.getHeight();

			int x = 0;
			int y = 0;
			do {
				newImg.setRGB(x, y, oldImg.getRGB(x, y));
				// avance
				x++;
				if (x == hybridWidth) {
					x = 0;
					y++;
				}
				if (y == hybridHeight) {
					x = 0;
					y = 0;
				}
			} while ((x != 0) || (y != 0));

			bufferedImages.put(desc, newImg);
			modified.put(desc, true);

		}

	}

}
