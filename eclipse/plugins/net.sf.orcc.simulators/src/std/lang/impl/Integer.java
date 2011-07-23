package std.lang.impl;

public class Integer {

	/*
	 * Returns a String representing the specified integer.
	 */
	public static String toString(java.lang.Integer i) {
		return i.toString();
	}
	

	/*
    * Returns a string representation of the integer argument as an unsigned integer in base 16.
    */
	public static String toHexString(java.lang.Integer i) {
		return java.lang.Integer.toHexString(i);
	}

	/*
    * Returns a string representation of the integer argument as an unsigned integer in base 8.
    */
	public static String toOctalString(java.lang.Integer i) {
		return java.lang.Integer.toOctalString(i);
	}

	/*
	 * Returns a string representation of the integer argument as an unsigned integer in base 2.
	 */
	public static String toBinaryString(java.lang.Integer i) {
		return java.lang.Integer.toBinaryString(i);
	}
	
	
	/*
	 * Parses the string argument as a signed integer in the radix specified by the second argument.
	 */
	
	public static int toBinaryString(String s, java.lang.Integer radix) {
		return java.lang.Integer.parseInt(s, radix);
	}
	
}
