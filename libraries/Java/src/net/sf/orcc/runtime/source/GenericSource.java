package net.sf.orcc.runtime.source;

public class GenericSource {

	protected static String fileName = "";
	
	/**
	 * Sets the file name used by this Source class.
	 * 
	 * @param fileName
	 *            name of a file to read
	 */

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		GenericSource.fileName = fileName;
	}
	
}
