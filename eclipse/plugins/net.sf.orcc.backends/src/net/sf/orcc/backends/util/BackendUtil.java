package net.sf.orcc.backends.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.util.OrccLogger;

/**
 * @author Herve Yviquel
 * 
 */
public class BackendUtil {

	public static void startExec(final AbstractBackend executingBackend,
			String[] cmd) throws IOException {
		Runtime run = Runtime.getRuntime();
		final Process process = run.exec(cmd);

		// Output error message
		new Thread() {
			@Override
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(process.getErrorStream()));
					try {
						String line = reader.readLine();
						if (line != null) {
							OrccLogger.warnln("Generation error : " + line);
						}
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method returns the closest power of 2 of x --> optimal buffer size
	 * 
	 * @param x
	 * @return closest power of 2 of x
	 */
	public static int closestPow_2(double x) {
		int p = 1;
		while (p < x) {
			p = p * 2;
		}
		return p;
	}

	/**
	 * Round up to next power of 2 for example 30000 -> 32768
	 * 
	 * @param value
	 *            the value to round up
	 * @return the next power of 2 after the value
	 */
	public static long quantizeUp(long value) {
		double tmp = Math.log(value) / Math.log(2.0);
		return (long) Math.pow(2, Math.ceil(tmp));
	}

	/**
	 * Convert a string version into a int[4] array with major, minor, release
	 * and qualifier number. Each part is set to -1 if number can't be parsed.
	 * 
	 * @return version number in int[4] form
	 */
	public static int[] getVersionArrayFromString(String version) {
		int[] result = { -1, -1, -1, -1 };
		StringTokenizer t = new StringTokenizer(version, ".");
		for (int i = 0; t.hasMoreTokens() && i < 4; ++i) {
			try {
				int value = Integer.parseInt(t.nextToken());
				result[i] = value;
			} catch (NumberFormatException e) {
			}
		}
		return result;
	}

	/**
	 * Compares version strings.
	 * 
	 * @return result of comparison, as integer : a negative value if left <
	 *         right, a positive one if left > right or 0 if left == right
	 */
	public static int compareVersions(int[] left, int[] right) {
		if (left == null) {
			return -1;
		}

		int result = Integer.valueOf(left[0]).compareTo(right[0]);
		if (result != 0) {
			return result;
		}

		result = Integer.valueOf(left[1]).compareTo(right[1]);
		if (result != 0) {
			return result;
		}

		result = Integer.valueOf(left[2]).compareTo(right[2]);
		if (result != 0) {
			return result;
		}

		return Integer.valueOf(left[3]).compareTo(right[3]);
	}

}
